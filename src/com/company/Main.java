package com.company;

import java.util.InputMismatchException;
import java.util.Scanner;

class Person {
    private String name;       // 사람 이름
    private int id;            // Identifier
    private double weight;     // 체중

    public void set(String name, int id, double weight) {
        this.name = name;
        this.id = id;
        this.weight = weight;
        System.out.println("Person::set(" + name + ", " + id + ", " + weight + ")");
    }
    public Person(String name, int id, double weight) {
        set(name, id, weight);
        System.out.println("Person(" + name + ", " + id + ", " + weight + ")");
    }
    public Person(Scanner s) {
        this(s.next(), s.nextInt(), s.nextDouble());
        System.out.println("Person(Scanner s)");
    }
    public void println() { print(); System.out.println(); }

    public void print() {
        System.out.print(name + ",  ID:" + id + ", W:" + weight);
    }

    public String getName() {        return name;    }
    public int getID() {    return id;  }
    public double getWeight() { return weight;  }
    public void set(String name) {
        System.out.println("set name: " + name);
        this.name = name;   }
    public void set(int id) {
        System.out.println("set id: " + id);
        this.id = id;   }
    public void set(double weight) {
        System.out.println("set weight: " + weight);
        this.weight = weight;   }

    public void whatAreYouDoing() {
        System.out.println(name + "is taking a rest.");
    }

    public boolean isSame(String name, int id) {
        if(this.name.equals(name) && (this.id == id))   return true;
        return false;
    }

    public void update(Scanner s) {
        set(name, s.nextInt(), s.nextDouble());
        System.out.println("Person::update("+ id + ", "+ weight + ") name:" + name);
    }
}
class PersonManager {

    private final int MAX_PERSONS = 100;

    private Scanner scanner;
    private Person persons[];
    private int count; // persons[] 배열에 실제로 저장된 사람들의 수

    PersonManager(Person array[], Scanner scanner) {
        //MAX_PERSONS개의 원소를 가진 persons 배열을 생성
        persons = new Person[MAX_PERSONS];
        for (int i = 0; i < array.length; i++) {
            persons[i] = array[i];
        }
        this.scanner = scanner;
        count = array.length;
    }

    int findIndex(String name) {
        for(int i=0; i<count; i++) {
            if(name.equals(persons[i].getName()))
                return i;
        }
        System.out.println(name + " is NOT found.");
        return -1;
    }

    Person find(String name) {
        int index = findIndex(name);
        return (index < 0) ? null : persons[index];
    }

    void display() {
        for(int i=0; i<count; i++)
            persons[i].println();
        System.out.println("Person count: " + count);
    }

    void search() {
        System.out.print("Name to search? ");
        String find = scanner.next();
        if(findIndex(find) != -1)
            persons[findIndex(find)].println();
    }

    void update() {
        System.out.print("Person information to update? ");
        Person p = find(scanner.next());
        if(p != null) {
            p.set(p.getName(), scanner.nextInt(), scanner.nextDouble());
            System.out.println("Person::update("+ p.getID() +", "+ p.getWeight() +") name:"+ p.getName());
        }
        else
            scanner.nextLine();
    }

    void delete() {
        System.out.print("Name to delete? ");
        int idx = findIndex(scanner.next());
        if(idx == -1) return;
        for(int i = idx; i < count; i++)
            persons[i] = persons[i+1];
        count--;
    }

    void insert() {
        int idx=0;
        while(true) {
            if (count != 0) {
                System.out.print("Existing name to insert in front? ");
                idx = findIndex(scanner.next());
            }
            if(idx != -1)  {
                System.out.print("Person information to insert? ");
                for(int i=count; i!=idx; i--) {
                    persons[i] = persons[i-1];
                }
                persons[idx] = new Person(scanner);
                count++;
                break;
            }
        }
    }

    void append() {
        System.out.println("Continuously input person information to insert and input \"end\" at the end.");
        while(true) {
            if(scanner.hasNext("end")) {
                scanner.next();
                break;
            }
            persons[count++] = new Person(scanner);
        }
    }

    void whatDoing() {
        System.out.print("Name to know about? ");
        String find = scanner.next();
        if(findIndex(find) != -1)
            System.out.println(persons[findIndex(find)].getName() + " is taking a rest.");
    }

    final int 종료 = 0, 모두보기 = 1, 검색 = 2, 수정 = 3, 삭제 = 4,
            삽입 = 5, 추가 = 6, 뭐하니 = 7;

    public void run() {
        System.out.println("PersonManager::run() start");
        display();
        while (true) {
            System.out.println();
            System.out.println("Menu: 0.Exit 1.DisplayAll 2.Search 3.Update 4.Remove 5.Insert");
            System.out.println("      6.Append 7.WhatDoing?");
            int idx;
            while (true) {
                System.out.print("Menu item number? ");
                try {
                    idx = scanner.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("Input an INTEGER.");
                    scanner.nextLine();
                    continue;
                }
                break;
            }
            switch (idx) {
                case 모두보기:
                    display();
                    break;
                case 검색:
                    search();
                    break;
                case 수정:
                    update();
                    break;
                case 삭제:
                    delete();
                    break;
                case 삽입:
                    insert();
                    break;
                case 추가:
                    append();
                    break;
                case 뭐하니:
                    whatDoing();
                    break;
                case 종료:
                    System.out.println("PersonManager run() returned\n");
                    return;
                default:
                    System.out.println("WRONG menu item");
                    break;
            }
        }
    }
}
public class Main {

    public static void simpleTest1(Scanner s) {
        System.out.print("Any person information? ");
        Person p = new Person(s);
        p.println();
        System.out.print("Update information(ID, weight) of above person? ");
        p.update(s);
        p.println();
        System.out.println();
    }
    public static void simpleTest2(PersonManager pm) {
        pm.display();
        System.out.println();

        String name = "Soon";
        int idx = pm.findIndex(name);
        if (idx >= 0) // 사람을 찾은 경우
            System.out.println(name+"'s index is "+idx);
        pm.findIndex("KimSooJin"); // findIndex()에서 사람을 못찾은 경우 에러 메시지 출력됨

        Person p = pm.find(name);
        if (p != null) // 사람을 찾은 경우
            System.out.println(name+"'s ID is "+p.getID());
        pm.find("KimJwaJin"); // 사람을 못찾은 경우 findIndex()에 의해 에러 메시지 출력됨
        System.out.println();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        simpleTest1(scanner);
        Person fivePersons[] = {
                new Person("Hong", 0, 64),
                new Person("Mong", 1, 75),
                new Person("Choon", 2, 45.5),
                new Person("Chung", 3, 46.1),
                new Person("Soon", 4, 88.5)
        };
        System.out.println();
        PersonManager pm = new PersonManager(fivePersons, scanner);
        simpleTest2(pm);
        pm.run();
        scanner.close();
    }
}