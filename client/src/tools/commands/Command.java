package tools.commands;

import data.LabWork;

import java.io.Serializable;

public abstract class Command extends AbstractCommand implements Serializable {

    public boolean needsScanner = false;
    public boolean needsExecutor = false;
    public String data;
    public boolean hasData;
    public LabWork lab;
    public String res;

    protected Command(String name, String description){
        super(name, description);
    }

    public void execute() throws ArrayIndexOutOfBoundsException{
        throw new ArrayIndexOutOfBoundsException();
    }
    public void execute(String data) throws ArrayIndexOutOfBoundsException {
        throw new ArrayIndexOutOfBoundsException();
    }

    public String getAnswer() {
        String res = "";
        return res;
    }
}
