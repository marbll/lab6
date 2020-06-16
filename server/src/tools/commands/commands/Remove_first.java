package tools.commands.commands;

import data.LabWork;
import data.LabworksStorage;
import tools.commands.Command;

import java.util.Iterator;

public class Remove_first extends Command {
    public Remove_first(){
        super("Remove_first","удалить первый элемент из коллекции");
        hasData = false;
    }
    @Override
    public void execute() {
        res = "";
        if (LabworksStorage.getData().size() != 0) {
            LabworksStorage.remove(LabworksStorage.getData().iterator().next());
            res += "Первая LabWork успешно удалена";
        } else res += "В коллекции отсутствуют элементы. Выполнение команды не возможно.";
    }

    @Override
    public String getAnswer(){
        return res;
    }
}