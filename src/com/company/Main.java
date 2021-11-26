package com.company;

import java.util.InputMismatchException;
import java.util.Scanner;

class Person {
    public String name; // 사람 이름
    public int id; // Identifier
    public double weight; // 체중

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
        System.out.print(name + ", ID:" + id + ", W:" + weight);
    }

    public String getName() { return name; }
    public int getID() { return id; }
    public double getWeight() { return weight; }
    public void set(String name) {
        System.out.println("set name: " + name);
        this.name = name; }
    public void set(int id) {
        System.out.println("set id: " + id);
        this.id = id; }
    public void set(double weight) {
        System.out.println("set weight: " + weight);
        this.weight = weight; }

    public void whatAreYouDoing() {
        System.out.println(name + " is taking a rest.");
    }

    public boolean isSame(String name, int id) {
        if(this.name.equals(name) && (this.id == id)) return true;
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
        System.out.print("Information to update? ");
        Person p = find(scanner.next());
        if(p != null) {
            p.update(scanner);
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
    public Person getNewPerson() {
        String tag = scanner.next();
        if (tag.equals("S")) // tag가 "S"
            return new Student(scanner);
        else if (tag.equals("W")) // tag가 "W"
            return new Worker(scanner);
        else {
            System.out.println(tag + ": WRONG delimiter");
            scanner.nextLine();
            return null;
        }
    }
    void insert() {
        int idx=0;
        while(true) {
            if (count != 0) {
                System.out.print("Existing name to insert in front? ");
                idx = findIndex(scanner.next());
            }
            if(idx != -1) {
                System.out.println("[person delimiter(S or W)] [person information to insert]?");
                Person p = getNewPerson();
                if(p == null) {
                    return;
                }
                for(int i=count; i!=idx; i--) {
                    persons[i] = persons[i-1];
                }
                persons[idx] = p;
                count++;
                break;
            }
        }
    }

    void append() {
        System.out.println("Continuously input person information to insert, and input \"end\" at the end.");
        while(true) {
            if(scanner.hasNext("end")) {
                scanner.next();
                break;
            }
            persons[count] = getNewPerson();
            if(persons[count] != null)  count++;
        }
    }

    void whatDoing() { System.out.print("Name to know about? ");
        String find = scanner.next();
        if(findIndex(find) != -1)
            persons[findIndex(find)].whatAreYouDoing();
    }

    final int 종료 = 0, 모두보기 = 1, 검색 = 2, 수정 = 3, 삭제 = 4,
            삽입 = 5, 추가 = 6, 뭐하니 = 7;

    public void run() {
        System.out.println("PersonManage::run() start");
        display();
        while (true) {
            System.out.println();
            System.out.println("Menu: 0.Exit 1.DisplayAll 2.Search 3.Update 4.Remove 5.Insert");
            System.out.println(" 6.Append 7.WhatDoing?");
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

class Student extends Person {
    private String department; // 학과
    private int year; // 학년
    private double GPA; // 평균평점

    public void setStudent(String department, int year, double GPA) {
        this.department = department;
        this.year = year;
        this.GPA = GPA;
        System.out.println("Student::set(" + department +", "+ year +", " + GPA + ")");
    }
    public Student(String name, int id, double weight, String department, int year, double GPA) {
        super(name, id, weight);
        setStudent(department, year, GPA);
        System.out.println("Student(" + department +", "+ year +", " + GPA + ")");
    }
    public Student(Scanner s) {
        super(s);
        setStudent(s.next(), s.nextInt(), s.nextDouble());
        System.out.println("Student(Scanner s)");
    }
    @Override // 부모 클래스인 Person의 whatAreYouDoing() 메소드를 오버라이딩함
    public void whatAreYouDoing() {
        study();
        System.out.println(", ");
        takeClass();
    }

    // 새로 추가된 메소드
    public void study() {
        System.out.print( name + " is studying as a " + year + "-year student in " + department );
    }
    public void takeClass() {
        System.out.println(name + " took several courses and got GPA " + GPA );
    }

    @Override
    public void print() {
        super.print();
        System.out.print(", D:" + department + ", Y:" + year + ", GPA:" + GPA);
    }

    @Override
    public void update(Scanner s) {
        super.update(s);
        setStudent(s.next(), s.nextInt(), s.nextDouble());
        System.out.println("Student::update("+ id + ", "+ weight + ") name:" + name);
    }
}

class Worker extends Person {
    private String company; // 회사명
    private String position; // 직급

    public void set(String company, String position) {
        this.company = company;
        this.position = position;
        System.out.println("Worker::set(" + company + ", " + position +")");
    }
    public Worker(String name, int id, double weight, String company, String position) {
        super(name, id, weight);
        set(company, position);
        System.out.println("Worker(" + company + ", " + position +")");
    }
    public Worker(Scanner s) {
        super(s);
        set(s.next(), s.next());
        System.out.println("Worker(Scanner s)");
    }
    // 새로 추가된 메소드
    public void work() {
        System.out.print(name + " works in " + company +" as " + position);
    }
    public void goOnVacation() {
        System.out.print(name + " is now enjoying his(her) vacation.");
    }

    @Override
    public void update(Scanner s) {
        super.update(s);
        set(s.next(), s.next());
        System.out.println("Worker update(" + company + ", " + position + ") name:" + name);

    }

    @Override
    public void whatAreYouDoing() {
        work();
        System.out.println(", ");
        goOnVacation();
        System.out.println();
    }

    @Override
    public void print() {
        super.print();
        System.out.print(", C:" + company + ", P:" + position);
    }
}

public class Main {

    public static void personTest(Person p) {
        System.out.println("personTest");
        p.println();
        p.whatAreYouDoing();

        System.out.println(p.getName() + "'s ID is " + p.getID() +
                ", weight is " + p.getWeight() + ".");
        p.set("Mong");
        p.set(1);
        p.set(94.3);
        p.println();
        System.out.println("p.isSame(Mong, 1): " + p.isSame("Mong", 1));
        p.set("Hong", 2, 60);
        p.println();
        System.out.println();
    }
    public static void studentTest(Scanner scanner) {
        System.out.println("studentTest");
        Student sp = new Student("Hong", 0, 71.5, "Computer", 2, 3.5);
        System.out.println();
        sp.whatAreYouDoing();
        Person p = sp; // 업캐스팅
        p.whatAreYouDoing();
        System.out.println();
        sp.println();
        sp.setStudent("Electronics", 4, 3.5);
        sp.println();
        System.out.println();
        System.out.print("Student information to insert? ");
        sp = new Student(scanner); // Chung 13 46.1 Literature 1 3.8
        sp.println();
        System.out.println();
        System.out.println("Above student's information(ID weight department year GPA) to upate? ");
        sp.update(scanner);
        sp.println();
        System.out.println();
        System.out.println(sp.getName() + "'s ID is " + sp.getID() +
                ", weight is " + sp.getWeight() + ".");
        sp.set("Mong");
        sp.set(1);
        sp.set(94.3);
        sp.println();
        System.out.println("sp.isSame(Mong, 1): " + sp.isSame("Mong", 1));
        sp.set("Hong", 2, 60);
        sp.setStudent("Electronics", 4, 3.5);
        sp.println();
        System.out.println();
        personTest(sp); // 함수 인자 Up casting
        p = new Person("Mong", 11, 75);
        System.out.println();
        personTest(p);
    }
    public static void workerTest(Scanner scanner) {
        System.out.println("workerTest");
        Worker wp = new Worker ("Mong", 1, 75, "Samsung", "Director");

        System.out.println();
        wp.whatAreYouDoing();
        Person p = wp; // 업캐스팅
        p.whatAreYouDoing();
        System.out.println();

        wp.println();
        wp.set("Naver", "Manager");
        wp.println();
        System.out.println();

        System.out.print("Worker information to insert? ");
        wp = new Worker(scanner); // Choon 12 45.5 LGElectronics DepartmentHead
        wp.println();
        System.out.println();

        System.out.println("Above worker's information(ID weight company job_position) to upate? ");
        wp.update(scanner); // 12 50.5 LGElectronics Director
        wp.println();
        System.out.println();
        personTest(wp); // 함수 인자 Up casting
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        studentTest(scanner);
        workerTest(scanner);
        Person fivePersons[] = {
                new Student("Hong", 10, 64, "Computer", 2, 3.5),
                new Worker ("Mong", 11, 75, "Samsung", "Director"),
                new Worker ("Choon",12, 45.5, "LG", "DepartmentHead"),
                new Student("Chung",13, 46.1, "Physics", 1, 3.8),
                new Student("Soon", 14, 88.5, "Electronics",4, 2.5),
        };
        System.out.println();
        PersonManager pm = new PersonManager(fivePersons, scanner);
        pm.run();
        scanner.close();
    }
}