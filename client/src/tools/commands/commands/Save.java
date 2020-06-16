package tools.commands.commands;

import data.LabworksStorage;
import tools.commands.Command;

public class Save extends Command {
    public Save(){
        super("Save","сохранить коллекцию в файл");
        hasData = false;
    }
    @Override
    public void execute(){
        this.res = "";
        LabworksStorage.save();
        res += "Collection saved.\n";
    }

    @Override
    public String getAnswer(){
        return res;
    }
}
