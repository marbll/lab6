package tools.commands.commands;

import tools.commands.Command;

public class Remove_first extends Command {
    public Remove_first(){
        super("Remove_first","удалить первый элемент из коллекции");
        hasData = false;
    }
    @Override
    public void execute() {
        //
    }

    @Override
    public String getAnswer(){
        return res;
    }
}
