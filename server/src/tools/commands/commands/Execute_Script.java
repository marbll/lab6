package tools.commands.commands;

import tools.commands.Command;
import tools.Script_Executor;

import java.io.File;

public class Execute_Script extends Command {
    public Execute_Script(){
        super("Execute_Script", "считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме");
        hasData = true;
        needsExecutor = true;
    }
    @Override
    public void execute(String data){
        this.res = "";
        File file = new File(data);
        if (file.exists()){
            Script_Executor se = new Script_Executor(file);
            try {
                se.exec();
            }catch (StackOverflowError e){ res += "Скрипт составлен неверно. Уберите self calls скрипта."; }
        }else {
            res += "Файл не найден";
        }
    }

    @Override
    public String getAnswer(){
        return res;
    }
}