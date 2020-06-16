package tools.commands.commands;

import data.LabWork;
import data.LabworksStorage;
import tools.commands.Command;

public class Average_Of_Average_Point extends Command {
    public Average_Of_Average_Point(){
        super("Average_Of_Average_Point", " вывести среднее значение поля averagePoint для всех элементов коллекции");
        hasData = false;
    }
    @Override
    public void execute(){
        res = "";
        if (LabworksStorage.getData().size() != 0) {
            final Double[] sum_average_point = {0D};
            LabworksStorage.getData().stream().forEach(labWork -> sum_average_point[0] += labWork.getAveragePoint());
            res += ("Cреднее значение поля averagePoint для всех элементов коллекции: " + sum_average_point[0] /LabworksStorage.getData().size());
        }
        else res += ("В коллекции отсутствуют элементы. Выполнение команды не возможно.");
    }


    @Override
    public String getAnswer(){
        return res;
    }
}
