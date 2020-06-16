import IO.IOClient;
import IO.IOinterface;
import data.LabWork;
import data.LabworksStorage;
import org.jdom2.JDOMException;
import tools.*;
import tools.commands.Command;
import tools.commands.CommandInvoker;
import tools.connector.ClientHandler;
import tools.io.Transport;


import java.io.File;
import java.io.IOException;
import java.net.BindException;
import java.net.ConnectException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.*;

public class ServerMain {
    public static boolean sent;

    public static void main(String[] args) {
        try {


            System.out.println("Введите порт\n>");
            Scanner sc = new Scanner(System.in);
            String port = sc.nextLine();
            loadLabs();

            ClientHandler clientHandler = null;

            try {
                clientHandler = new ClientHandler(Integer.parseInt(port.trim()));


                Transport collectionSender = new Transport("map");
                HashMap<String, Command> l = CommandInvoker.getCommands();
                collectionSender.putObject(l);

                Transport trans = null;
                IOinterface ioClient = null;
                Command comm = null;


                while (true) {
                    clientHandler.getSelector().select();
                    Iterator iter = clientHandler.getSelector().selectedKeys().iterator();

                    while (iter.hasNext()) {
                        SelectionKey selKey = (SelectionKey) iter.next();
                        iter.remove();

                        try {
                            if (selKey.isValid()) {
                                if (selKey.isAcceptable()) {
                                    clientHandler.acceptConnect();
                                    sent = false;
                                }

                                if (selKey.isWritable()) {
                                    if (!sent) {
                                        ioClient = new IOClient((SocketChannel) selKey.channel(), true);
                                        ioClient.writeObj(collectionSender);
                                        sent = true;
                                    } else {
                                        System.out.println("COMMAND1 " + comm);
                                        processCommand(comm, ioClient);
                                    }

                                    selKey.interestOps(1);
                                }

                                if (selKey.isReadable()) {
                                    ioClient = new IOClient((SocketChannel) selKey.channel(), true);
                                    trans = (Transport) ioClient.readObj();
                                    comm = (Command) trans.getObject();
                                    selKey.interestOps(4);
                                }
                            }
                        } catch (ConnectException e) {
                            System.out.println(e.getMessage());
                            sent = false;
                        }
                    }
                }
            } catch (IndexOutOfBoundsException ex) {
                System.out.println("Please provide PORT");
                System.exit(0);
            } catch (BindException e) {
                System.out.println("Address & port already in use");
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private static void processCommand(Command comm, IOinterface ioClient) throws IOException {

        System.out.println("COMMAND " + comm);

        String res = "";
        if (comm.getName().equals("QUIT")){
            Java2XML j2 = new Java2XML();
            j2.writeXML();
            System.out.println("Collection saved");
        }else {
            if (comm.needsScanner){
                LabWork labWork = comm.lab;
                Optional<LabWork> maxlab= LabworksStorage.getData().stream().max(Comparator.comparing(LabWork::getId));
                int id = maxlab.map(lab -> lab.getId() + 1).orElse(1);
                labWork.setId(id);
                comm.res += LabworksStorage.put(labWork);
                System.out.println("LLLAABBBBB");
            }
            CommandInvoker.loadToSavedCommands(comm);
            if (comm.hasData){
                comm.execute(comm.data);
            }else {
                comm.execute();
            }

            res = comm.getAnswer();
        }

        ioClient.writeln(res);
    }

    private static void loadLabs(){
        File file = new File("data.xml");
        XMLScanner xsc = new XMLScanner();
        try{
            xsc.scan(file);
        }catch (IOException | JDOMException e){
            System.out.println("File not found, loading empty collection");
        }
    }
}
