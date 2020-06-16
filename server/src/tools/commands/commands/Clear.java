package tools.commands.commands;

import data.LabworksStorage;
import tools.commands.Command;

import java.io.Serializable;

public class Clear extends Command implements Serializable {
    public Clear(){
        super("Clear","очистить коллекцию");
        hasData = false;
    }
    @Override
    public void execute(){
        this.res = "";
        LabworksStorage.clear();
        res += "Коллекция очищена.\n";
    }

    @Override
    public String getAnswer(){
        return res;
    }
}