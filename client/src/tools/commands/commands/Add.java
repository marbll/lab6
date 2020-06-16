package tools.commands.commands;

import data.LabWork;
import tools.commands.Command;

import java.util.Scanner;

public class Add extends Command {
    public Add(){
        super("Add","Adds an element");
        hasData = false;
        needsScanner = true;
    }

    public void execute(){
        res = "LabWork added";
    }

    @Override
    public String getAnswer(){
        return res;
    }
}