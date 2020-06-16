import tools.commands.Command;
import tools.commands.CommandInvoker;
import tools.connector.ServerConnector;
import tools.io.StreamReadWriter;
import tools.io.Transport;

import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class ClientMain {
    public static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) throws IOException {
        System.out.println("Введите хост > ");
        String host = scanner.nextLine();
        System.out.println("Введите порт > ");
        int port = Integer.parseInt(scanner.nextLine());
        StreamReadWriter iOclient = new StreamReadWriter(System.in, System.out, true);

        try {
            ServerConnector serverConnector = new ServerConnector();
            serverConnector.connect(host, port);
            StreamReadWriter ioServer = new StreamReadWriter(serverConnector.getInputStream(), serverConnector.getOutputStream(), true);

            while(!ioServer.ready()) {
            }

            Transport fromServer = (Transport)ioServer.readObj();
            HashMap<String, Command> commandMap= (HashMap)fromServer.getObject();

            CommandInvoker commandInvoker = new CommandInvoker(commandMap, ioServer);
            while(true) {
                try {
                    String commName = scanner.nextLine().trim();
                    commandInvoker.run(commName, iOclient, scanner);
                } catch (StackOverflowError e) {
                    System.out.println("Случилась StackOverflowError");
                }
            }
        } catch (NullPointerException | IOException e) {
            e.printStackTrace();
            System.out.println("Работа остановлена");
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
