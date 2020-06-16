package tools.commands;

import data.LabWork;
import tools.Loader;
import tools.io.QuitException;
import tools.io.StreamReadWriter;
import tools.io.Transport;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class CommandInvoker {
    private static HashMap<String, Command> commands;
    private Thread hook;
    private StreamReadWriter ioServer;
    private StreamReadWriter io;
    private Scanner sc;

    public CommandInvoker(HashMap<String, Command> commandList, StreamReadWriter ioServer) {
        this.commands = commandList;
        this.ioServer = ioServer;
    }

    public static Command getCommand(String name){
        Optional<Command> command = Optional.ofNullable(commands.get(name.toUpperCase()));
        return command.orElse(null);
    }

    public static String printSavedCommands(){
        return "";
    }

    public void run(String str, StreamReadWriter ioi, Scanner scanner) throws QuitException, IOException {
        io = ioi;
        sc = scanner;

        try {
            if (!str.trim().equals("")) {
                String[] s = str.trim().toUpperCase().split(" ");
                Command command = commands.get(s[0]);

                if (isCommand(str.trim().toUpperCase(), scanner)) {
                    Transport trans = new Transport(command);
                    this.ioServer.writeObj(trans);
                    long start = System.currentTimeMillis();

                    while (!this.ioServer.ready()) {
                        long finish = System.currentTimeMillis();
                        if (finish - start > 3000L) {
                            throw new QuitException();
                        }
                    }

                    boolean i = true;
                    while (this.ioServer.ready()) {

                        if (i){
                            i = false;
                        }
                            io.writeln(this.ioServer.readLine());

                    }

                } else {

                }
            }
            } catch (NullPointerException e) {
            e.printStackTrace();
            System.out.println("Unknown Command");
        } catch (IllegalArgumentException e) {
            io.writeln("Скрипт составлен неверно");
        }

    }






    public boolean isCommand(String line, Scanner sc)  {
            String[] parts = line.split(" ", 2);
            String COMMAND = parts[0];
            Command command = CommandInvoker.getCommand(COMMAND);

            if (command == null){
                return false;
            }

            if (command.needsScanner){
                Loader loader = new Loader();
                LabWork lab = null;
                lab = loader.search(sc);
                command.lab = lab;
            }

            try {
                if (command.hasData){
                    command.data = parts[1];
                }
            }catch (ArrayIndexOutOfBoundsException e){
                System.out.println("Command must have an argument");
            }


            if (command.needsExecutor){
                try {

                    command.data = parts[1];
                    System.out.println(command.data);
                    File file = new File(command.data.toLowerCase());
                    if (file.exists()){
                        try {
                            Scanner scanner1 = new Scanner(file);
                            while (scanner1.hasNext()) {

                                String l = scanner1.nextLine();
                                if (!(l.trim().toUpperCase().equals("EXIT") | l.trim().toUpperCase().equals("QUIT") )) {
                                    run(l, io, scanner1);
                                    }
                                }
                            } catch (QuitException ex) {
                            ex.printStackTrace();
                        } catch (FileNotFoundException ex) {
                            ex.printStackTrace();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    catch (StackOverflowError e){ System.out.println("Invalid script. Please remove self calls."); }
                    }else { System.out.println("No such file"); }
                    return false;
                }catch (ArrayIndexOutOfBoundsException e){
                    System.out.println("Command must have an argument");
                    return false;
                }
            }
            return true;
    }
}