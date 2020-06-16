package tools.commands.commands;

import tools.commands.Command;
import tools.commands.CommandInvoker;

public class History extends Command {
    public History(){
        super("History", "Вывести 7 последних команд без аргументов");
        hasData = false;
    }
    @Override
    public void execute(){
      res += CommandInvoker.printSavedCommands();
    }
    @Override
    public String getAnswer(){
        return res;
    }
}