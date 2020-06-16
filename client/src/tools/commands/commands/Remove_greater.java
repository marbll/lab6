package tools.commands.commands;

import data.LabworksStorage;
import data.LabWork;
import tools.commands.Command;

import java.util.stream.Stream;

public class Remove_greater extends Command {

    //private boolean hasData = true;
    public Remove_greater(){
        super("Remove_greater", "Removes all elements greater than provided.");
        hasData = true;
    }
    @Override
    public void execute(String data){
        try {
            int id = Integer.parseInt(data);
            LabWork labWork = LabworksStorage.searchById(id);
            if (labWork == null){
                throw new NumberFormatException();
            }else {
                String oldres = res;
                LabworksStorage.getData().stream().filter(w -> labWork.compareTo(w) < 0).forEach(w -> res += LabworksStorage.remove((LabWork) w));
                if (oldres.equals(res)) {
                    //LabworksStorage.getData().stream().forEach(w -> res += LabworksStorage.remove((LabWork) w));
                    res += "No elements lower found";
                }
            }
        }catch (NumberFormatException e){
            res += "No such id";
        }
    }

    @Override
    public String getAnswer(){
        return res;
    }
}