package tools.commands.commands;

import tools.commands.Command;

public class Add extends Command {
    public Add(){
        super("Add","добавить новый элемент в коллекцию");
        hasData = false;
        needsScanner = true;
    }

    public void execute(){
        res = "LabWork добавлена";
    }

    @Override
    public String getAnswer(){
        return res;
    }
}