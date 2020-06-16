package tools.commands.commands;

import data.LabWork;
import data.LabworksStorage;
import tools.Loader;
import tools.commands.Command;

import java.util.Scanner;

public class RemoveGreater extends Command {
    public RemoveGreater(){
        super("RemoveGreater","удалить из коллекции все элементы, превышающие заданный");
        needsScanner = true;
    }
    @Override
    public void execute(){
        if (LabworksStorage.getData().size() != 0) {
            Loader loader = new Loader();
            LabWork l = loader.search(new Scanner(System.in));
            LabworksStorage.getData().stream().filter(labWork -> labWork.compareTo(l) > 0).forEach(LabworksStorage::remove);

            res += ("Команда успешно выполнена.");
        } else res += ("В коллекции отсутствуют элементы. Выполнение команды не возможно.");
    }

    @Override
    public String getAnswer(){
        return res;
    }
}
