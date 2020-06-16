package data;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;

public class LabworksStorage implements Serializable {
    static {
        date = new Date();
    }
    private static Date date;
    private static PriorityQueue<LabWork> labworks = new PriorityQueue<>();
    public static String put(LabWork l){
        boolean added = labworks.add(l);
        if (added){ return "Labwork " + l.getName() + " added. Id = " + l.getId(); }
        else { return "Lab already exists."; }
    }

    public static String printInfo(){
        return "\nCollection type: "+labworks.getClass().getName() + "\nCreation date: " + date + "\nNumber of elements: " + labworks.size();
    }
    public static ArrayList<LabWork> getByMin(float min) {
        ArrayList<LabWork> lab = new ArrayList<>();

        labworks.stream().filter(w -> w.getMinimalPoint() == min).forEach(lab::add);
        return lab;
    }
    public static LabWork getMinByCoordinates(){
        return labworks.stream().min(new CoordinatesComparator<LabWork>()).get();
    }

    public static ArrayList<LabWork> getData(){
        ArrayList<LabWork> arr = new ArrayList<>();

        labworks.stream().forEach(w -> arr.add(w));
        return arr;
    }
    public static void clear(){
        labworks.clear();
    }
    public static void save(){
        try  {
            File xmlCollection = new File("Collection.xml");

            Document doc = new Document();
            // создаем корневой элемент с пространством имен
            doc.setRootElement(new Element("Labs"));
            // формируем JDOM документ из объектов Student
            Iterator iterator = labworks.iterator();
            while (iterator.hasNext()) {
                LabWork labWork = (LabWork) iterator.next();
                Element element = new Element("LabWork");
                element.addContent(new Element("id").setText( String.valueOf(labWork.getId())));
                element.addContent(new Element("name").setText(labWork.getName()));
                Element element_c = new Element("Coordinates");
                element_c.addContent(new Element("x").setText(String.valueOf(labWork.getCoordinates().getX())));
                element_c.addContent(new Element("y").setText(String.valueOf(labWork.getCoordinates().getY())));
                element.addContent(element_c);
                element.addContent(new Element("creationDate").setText(String.valueOf(labWork.getCreationDate())));
                element.addContent(new Element("minimalPoint").setText(String.valueOf(labWork.getMinimalPoint())));
                element.addContent(new Element("description").setText(labWork.getDescription()));
                element.addContent(new Element("averagePoint").setText(String.valueOf(labWork.getAveragePoint())));
                element.addContent(new Element("difficulty").setText(String.valueOf(labWork.getDifficulty())));
                Element element_d = new Element("Discipline");
                element_d.addContent(new Element("name").setText(labWork.getDiscipline().getName()));
                element_d.addContent(new Element("labsCount").setText(String.valueOf(labWork.getDiscipline().getLabsCount())));
                element.addContent(element_d);
                doc.getRootElement().addContent(element);
            }
            if (!xmlCollection.canWrite()) throw new SecurityException();
            // Документ JDOM сформирован и готов к записи в файл
            XMLOutputter xmlWriter = new XMLOutputter(Format.getPrettyFormat());
            // сохнаряем в файл
            xmlWriter.output(doc, new FileOutputStream(xmlCollection));
            System.out.println("Коллекция успешно сохранена в файл.");
        } catch (IOException ex) {
            System.out.println("Возникла непредвиденная ошибка. Коллекция не может быть записана в файл");
        } catch (SecurityException ex) {
            System.out.println("Файл защищён от записи. Невозможно сохранить коллекцию.");
        }
    }

    public static String remove(LabWork lab){
        labworks.remove(lab);
        String res = "Element with ID: " + lab.getId() + " removed.";
        return res;
    }

    public static LabWork searchById(int id){
        LabWork laboratory =  null;
        Optional<LabWork> lab = labworks.stream().filter(l -> id == l.getId()).findAny();//filter(lab -> lab.checkId(id)).forEach(lab -> laboratory = lab);\
        try{
            laboratory = lab.get();
        }catch (NoSuchElementException e){
            //
        }
        return laboratory;
    }
}