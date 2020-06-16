package tools;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Script_Executor {
    private File file;
    public Script_Executor(File f){
        this.file = f;
    }
    public void exec(){
        try {
            Scanner scanner = new Scanner(this.file);
            while (scanner.hasNext()){
                String line = scanner.nextLine();
            }
        } catch (FileNotFoundException e) {
            System.out.println("file not found");
        }catch (NullPointerException e){
            System.out.println("invalid file");
        }
    }
}