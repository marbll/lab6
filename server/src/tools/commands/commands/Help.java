package tools.commands.commands;

import tools.commands.Command;
import tools.commands.CommandInvoker;
//import tools.ParametrizedCommand;

import java.io.Serializable;
//import src.server.ParametrizedCommandInvoker;

public class Help extends Command implements Serializable {
    public Help(){
        super("Help","вывести справку по доступным командам");
        hasData = false;
    }
    @Override
    public void execute(){
        this.res = "";
        CommandInvoker.getCommands().values().forEach(w -> res += "\n" + w.getName() + ": " + w.getDescription());
    }

    @Override
    public String getAnswer(){
        return res;
    }
}