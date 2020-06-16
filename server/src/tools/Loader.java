package tools;

import data.*;

import java.io.IOException;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.Optional;
import java.util.Scanner;

public class Loader {

    private String name;
    private Coordinates coordinates; //Поле не может быть null
    private Float minimalPoint; //Поле не может быть null, Значение поля должно быть больше 0
    private long maximumPoint = 0; //Значение поля должно быть больше 0
    private Difficulty difficulty; //Поле может быть null
    private Discipline discipline; //Поле может быть null

    public void checkMin(Scanner sc, long min){
        checkMax = true;
        checker = min;
        search(sc);
    }
    private long checker = 0;
    private boolean checkMax = false;

    public LabWork search(Scanner sc){
        Scanner reader = new Scanner(System.in);
        System.out.print("Введите name: ");
        String name = reader.nextLine();
        while (name.equals("")) {
            System.out.println("Поле не может быть null или пустой строкой ");
            System.out.print("Введите name: ");
            name = reader.nextLine();
        }
        System.out.println("Введите coordinates: ");
        String a;
        boolean p = false;
        int x = 0;
        while (!p) {
            System.out.print("Введите x: ");
            a = reader.nextLine();
            try {
                x = Integer.parseInt(a);
                if (x > -985)
                    p = true;
                else {
                    System.out.println("Значение поля должно быть больше -985");
                }
            } catch (NumberFormatException e) {
                System.out.println("Аргумент не является значением типа int");
            }
        }
        Double y = null;
        while (p) {
            System.out.print("Введите y: ");
            a = reader.nextLine();
            try {
                y = Double.parseDouble(a);
                p = false;
            } catch (NumberFormatException e) {
                System.out.println("Аргумент не является значением типа Double");
            }
        }
        long minimalPoint = 0;
        while (!p) {
            System.out.print("Введите minimalPoint: ");
            a = reader.nextLine();
            try {
                minimalPoint = Long.parseLong(a);
                if (minimalPoint > 0)
                    p = true;
                else {
                    System.out.println("Значение поля должно быть больше 0");
                }
            } catch (NumberFormatException e) {
                System.out.println("Аргумент не является значением типа long");
            }
        }
        System.out.print("Введите description: ");
        String description = reader.nextLine();
        while (description.equals("")) {
            System.out.println("Поле не может быть null или пустой строкой ");
            System.out.print("Введите description: ");
            description = reader.nextLine();
        }
        Double averagePoint = null;
        while (p) {
            System.out.print("Введите averagePoint: ");
            a = reader.nextLine();
            try {
                averagePoint = Double.parseDouble(a);
                if (averagePoint > 0)
                    p = false;
                else {
                    System.out.println("Значение поля должно быть больше 0");
                }
            } catch (NumberFormatException e) {
                System.out.println("Аргумент не является значением типа Double");
            }
        }
        System.out.print("Введите Difficulty (VERY_EASY, EASY, NORMAL, INSANE, null): ");
        String difficulty_s = reader.nextLine();
        while (!difficulty_s.equals("") && !difficulty_s.equals("VERY_EASY") && !difficulty_s.equals("EASY") && !difficulty_s.equals("NORMAL") && !difficulty_s.equals("INSANE")) {
            System.out.println("Значение поля неверное");
            System.out.print("Введите Difficulty (VERY_EASY, EASY, NORMAL, INSANE, null): ");
            difficulty_s = reader.nextLine();
        }
        Difficulty difficulty = null;
        if (!difficulty_s.equals("")) difficulty = Difficulty.valueOf(difficulty_s);
        System.out.println("Введите Discipline: ");
        System.out.print("Введите name: ");
        String nameDiscipline = reader.nextLine();
        while (nameDiscipline.equals("")) {
            System.out.println("Поле не может быть null");
            System.out.print("Введите name: ");
            nameDiscipline = reader.nextLine();
        }
        Long labsCount = null;
        while (!p) {
            System.out.print("Введите labsCount: ");
            a = reader.nextLine();
            try {
                if (a.equals("")) p = true;
                else {
                    labsCount = Long.parseLong(a);
                    p = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("Аргумент не является значением типа Long");
            }
        }

        Integer id;
        Optional<LabWork> maxlab = LabworksStorage.getData().stream().max(Comparator.comparing(LabWork::getId));
        id = maxlab.map(labWork -> labWork.getId() + 1).orElse(1);
        //System.out.println("Id " + id);

        LocalDate creationDate;
        creationDate = LocalDate.now();
        System.out.println("Все значения элемента успешно получены");
        return new LabWork(id, name, new Coordinates(x, y), creationDate, minimalPoint, description, averagePoint, difficulty, new Discipline(nameDiscipline, labsCount));
    }
    public void load(LabWork lab){
        LabworksStorage.put(lab);
    }
}