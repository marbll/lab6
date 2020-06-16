package tools.commands.commands;

import tools.commands.Command;

import java.io.Serializable;

public class Exit extends Command implements Serializable {
    public Exit(){
        super("Exit","завершить программу (без сохранения в файл)");
        hasData = false;
    }
    @Override
    public void execute() {
        this.res = "QUIT";
    }

    @Override
    public String getAnswer(){
        return res;
    }
}