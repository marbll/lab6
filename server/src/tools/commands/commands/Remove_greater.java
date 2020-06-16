package tools.commands.commands;

import data.LabworksStorage;
import data.LabWork;
import tools.commands.Command;

import java.util.stream.Stream;

public class Remove_greater extends Command {

    //private boolean hasData = true;
    public Remove_greater(){
        super("Remove_greater", "Удалить все элементы коллекции больше, чем указанный.");
        hasData = true;
    }
    @Override
    public void execute(String data){
        this.res = "";
        try {
            int id = Integer.parseInt(data);
            LabWork labWork = LabworksStorage.searchById(id);
            if (labWork == null){
                throw new NumberFormatException();
            }else {
                String oldres = res;
                LabworksStorage.getData().stream().filter(w -> labWork.compareTo(w) > 0).forEach(w -> res += LabworksStorage.remove((LabWork) w));
                if (oldres.equals(res)) {
                    res += "Не найдено соответствующих элементов";
                }
            }
        }catch (NumberFormatException e){
            res += "Не найдено элемента с указанным Id";
        }
    }

    @Override
    public String getAnswer(){
        return res;
    }
}