package tools.commands;

import tools.commands.commands.*;
import tools.commands.commands.Execute_Script;
import tools.commands.commands.Add;
import tools.commands.commands.Info;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class CommandInvoker{
    private static HashMap<String, Command> commands;
    private static List<Command> lastCommands = new ArrayList<>();

    static {
        commands = new HashMap<>();
        Command info = commands.put("INFO", new Info());
        Command help = commands.put("HELP", new Help());
        Command show = commands.put("SHOW", new Show());
        Command clear = commands.put("CLEAR", new Clear());
        Command history = commands.put("HISTORY", new History());
        Command update = commands.put("UPDATE", new Update());
        Command remove_by_id = commands.put("REMOVE_BY_ID", new Remove_by_id());
        Command add = commands.put("ADD", new Add());
        Command remove_greater = commands.put("REMOVE_GREATER", new Remove_greater());
        Command execute_script = commands.put("EXECUTE_SCRIPT", new Execute_Script());
        Command quit = commands.put("QUIT", new Exit());
        Command save = commands.put("SAVE", new Save());
        Command remove_first = commands.put("REMOVE_FIRST", new Remove_first());
        Command average = commands.put("AVERAGE_OF_AVERAGE_POINT", new Average_Of_Average_Point());
    }

    public static Command getCommand(String name){
        Optional<Command> command = Optional.ofNullable(commands.get(name.toUpperCase()));
        return command.orElse(null);
    }

    public static HashMap<String, Command> getCommands(){
        return commands;
    }

    public static void loadToSavedCommands(Command comm){
        if (lastCommands.size() >= 7){ lastCommands = lastCommands.subList(0, 6); }
        lastCommands.add(comm);
    }

    public static String printSavedCommands(){
        String res = "Last commands:\n";
        for (Command comm: lastCommands){
            res += comm.getName() + "\n";
        }
        return res;
    }
}
