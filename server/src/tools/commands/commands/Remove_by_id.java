package tools.commands.commands;

import data.LabworksStorage;
import data.LabWork;
import tools.commands.Command;

public class Remove_by_id extends Command {
    public Remove_by_id(){
        super("Remove_by_id", "удалить элемент из коллекции по его id");
        hasData = true;
    }

    @Override
    public void execute(String data){
        res = "";
        try {
            int id = Integer.parseInt(data);
            LabWork lab = LabworksStorage.searchById(id);
            res += LabworksStorage.remove(lab);
        }catch (NumberFormatException e){
            res += "Укажите id";
        }catch (NullPointerException e){
            res += "Не найден элемент с указанным Id.";
        }
    }

    @Override
    public String getAnswer(){
        return res;
    }
}