package tools.commands.commands;

import data.LabworksStorage;
import tools.commands.Command;

import java.io.Serializable;

public class Info extends Command implements Serializable {
    public Info(){
        super("Info","вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)");
        hasData = false;
    }
    @Override
    public void execute() {
        this.res = "";
        res += LabworksStorage.printInfo();
    }

    @Override
    public String getAnswer(){
        return res;
    }
}