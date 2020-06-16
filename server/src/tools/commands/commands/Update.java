package tools.commands.commands;

import data.LabworksStorage;
import data.LabWork;
import tools.commands.Command;

public class Update extends Command{
    public Update(){
        super("Update", "обновить значение элемента коллекции, id которого равен заданному");
        hasData = true;
    }
    @Override
    public void execute(String data){
        this.res = "";
        try {
            int id = Integer.parseInt(data);
            LabWork lab = LabworksStorage.searchById(id);
            if (lab == null){
                throw new NumberFormatException();
            }
            res += lab.update();
        }catch (NumberFormatException e){
            res += "Id некорректен";
        }
    }

    @Override
    public String getAnswer(){
        return res;
    }
}