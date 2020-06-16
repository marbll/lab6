package tools;

import data.LabworksStorage;
import data.Coordinates;
import data.Difficulty;
import data.Discipline;
import data.LabWork;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class XMLScanner {
    private ArrayList<String> tokens = new ArrayList<>();
    private ArrayList<String> fieldNames = new ArrayList<>();
    private File xmlCollection = new File("Collection.xml");

    private static org.jdom2.Document createJDOMusingSAXParser(String fileName)
            throws JDOMException, IOException {
        SAXBuilder saxBuilder = new SAXBuilder();
        return saxBuilder.build(new File(fileName));
    }

    public void scan(File file) throws IOException, JDOMException {

        System.out.println("Loading a collection " + xmlCollection.getAbsolutePath());
        // мы можем создать экземпляр JDOM Document из классов DOM, SAX и STAX Builder
        org.jdom2.Document jdomDocument = createJDOMusingSAXParser("Collection.xml");
        Element root = jdomDocument.getRootElement();
        // получаем список всех элементов
        List<Element> labWorkListElements = root.getChildren("LabWork");
        // список объектов Student, в которых будем хранить
        // считанные данные по каждому элементу
        for (Element lab : labWorkListElements) {
            Integer id = Integer.parseInt(lab.getChildText("id"));
            String name = lab.getChildText("name");
            List<Element> lab_c = lab.getChildren("Coordinates");
            int x = Integer.parseInt(lab_c.get(0).getChildText("x"));
            Double y = Double.parseDouble(lab_c.get(0).getChildText("y"));
            LocalDate creationDate = LocalDate.parse(lab.getChildText("creationDate"));
            long minimalPoint = Long.parseLong(lab.getChildText("minimalPoint"));
            String description = lab.getChildText("description");
            Double averagePoint = Double.parseDouble(lab.getChildText("averagePoint"));
            Difficulty difficulty = null;
            String difficulty_s = lab.getChildText("difficulty");
            if (!difficulty_s.equals("null")) difficulty = Difficulty.valueOf(difficulty_s);
            List<Element> lab_d = lab.getChildren("Discipline");
            String nameDiscipline = lab_d.get(0).getChildText("name");
            Long labsCount = null;
            String labsCount_s = lab_d.get(0).getChildText("labsCount");
            if (!labsCount_s.equals("null")) labsCount = Long.parseLong(labsCount_s);
            LabworksStorage.put(new LabWork(id, name, new Coordinates(x, y), creationDate, minimalPoint, description, averagePoint, difficulty, new Discipline(nameDiscipline, labsCount)));
        }
    }
}
