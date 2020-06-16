package tools.commands.commands;

import data.LabworksStorage;
import tools.commands.Command;

public class Show extends Command {
    public Show(){
        super("Show","вывести в стандартный поток вывода все элементы коллекции в строковом представлении");
        hasData = false;
    }
    @Override
    public void execute(){
        this.res = "";
        String oldRes = res;
        LabworksStorage.getData().forEach(l -> res += l.printString());
        if (oldRes.equals(res)){
            res += "Коллекция пуста";
        }
    }

    @Override
    public String getAnswer(){
        return res;
    }
}