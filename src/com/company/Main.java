package com.company;

import java.util.*;

interface Phone {
    void sendCall(String callee);
    // 이 메소드는 "Made a call to 수신자_이름(callee)"라고 출력해야 하며 이 출력의 앞 또는 뒤에 
    // 발신자 이름도 함께 출력하되 메이커가 알아서 적절히 회사명, 모델명 등과 함께 표시하면 된다.
    void receiveCall(String caller);
    // 이 메소드는 "Received a call from 송신자_이름(caller)"라고 출력해야 하며 이 출력의 앞 또는
    // 뒤에 수신자 이름도 함께 출력하되 메이커가 알아서 적절히 회사명, 모델명 등과 함께 표시하면 된다. 
}

interface Calculator {
    // +, -, *, / 사칙연산만 지원하고 그 외의 연산자일 경우 "NOT supported operator" 에러 메시지 출력
    // 수식과 계산 결과 또는 에러 메시지를 출력해야 하며 이 출력의 앞 또는 뒤에 
    // 계산기 소유주 이름도 함께 출력하되 메이커가 알아서 적절히 회사명, 모델명 등과 함께 표시하면 된다. 
    void calculate(double oprd1, String op, double oprd2); // 예: calculate(3, "+", 2.0)
    void calculate(Scanner scanner); // 스캐너로부터 수식을 읽어 위 메소드를 호출함
}

// 위 Phone와 Calculator를 implements로 상속 받았지만 여전히 이들 인터페이스의 메소드들을 구현하지
// 않았기 때문에 SmartPhone 클래스를 추상 클래스로 선언해야 함
// 이들 메소드들은 아래 GalaxyPhone과 IPhone 클래스에서 서로 다르게 구현됨
abstract class SmartPhone implements Phone, Calculator {
    String owner;  // 스마트폰 소유주 이름
    public SmartPhone(String owner) { this.owner = owner; }
    public abstract String getMaker();
}

// 위 SmartPhone 클래스를 상속 받아 Phone, Calculator 인터페이스의 모든 메소드들을 구현함
// 아래 GalaxyPhone과 그 아래 IPhone 클래스는 제조회사가 달라서 상속 받은 인터페이스의 모든 메소드들을
// 기능은 모두 동일하게 제공하지만 구현은 서로 다르게 함
class GalaxyPhone extends SmartPhone {
    public GalaxyPhone(String owner)           {
        super(owner);
    } // 슈퍼클래스 생성자 호출

    @Override
    public void sendCall(String callee)        {
        System.out.println("Made a call to " + callee + "\t@" + owner +"'s GalaxyPhone");
    }

    @Override
    public void receiveCall(String caller)     {
        System.out.println("Received a call from " + caller + "\t@" + owner + "'s GalaxyPhone");
    }

    @Override
    public void calculate(double oprd1, String op, double oprd2) {
        double result;
        switch (op) {
            case "+":
                result = oprd1 + oprd2;
                System.out.println(oprd1 + " " + op + " " + oprd2 + " = " + result + "\t@"+ owner +"'s GalaxyPhone");
                break;
            case "-":
                result = oprd1 - oprd2;
                System.out.println(oprd1 + " " + op + " " + oprd2 + " = " + result + "\t@"+ owner +"'s GalaxyPhone");
                break;
            case "*":
                result = oprd1 * oprd2;
                System.out.println(oprd1 + " " + op + " " + oprd2 + " = " + result + "\t@"+ owner +"'s GalaxyPhone");
                break;
            case "/":
                result = oprd1 / oprd2;
                System.out.println(oprd1 + " " + op + " " + oprd2 + " = " + result + "\t@"+ owner +"'s GalaxyPhone");
                break;
            default :
                System.out.println(oprd1 + " " + op + " " + oprd2 + " = NOT supported operator" + "\t@"+ owner +"'s GalaxyPhone");
        }
    }

    @Override
    public void calculate(Scanner s) {
        calculate(s.nextDouble(), s.next(), s.nextDouble());
    }

    @Override
    public String getMaker() { return "SAMSUNG"; }
}

// 위 SmartPhone 클래스를 상속 받아 Phone, Calculator 인터페이스의 모든 메소드들을 구현함
// 위 GalaxyPhone과 아래 IPhone 클래스는 제조회사가 달라서 상속 받은 인터페이스의 모든 메소드들을
// 기능은 모두 동일하게 제공하지만 구현은 서로 다르게 함
class IPhone extends SmartPhone {
    String model;
    public IPhone(String owner, String model)  {
        super(owner);
        this.model = model;
    } // 슈퍼클래스 생성자 호출 및 model 초기화

    @Override
    public void sendCall(String callee)        {
        System.out.println(owner + "'s IPhone " + model + ": made a call to " + callee);
    }

    @Override
    public void receiveCall(String caller)     {
        System.out.println(owner + "'s IPhone " + model + ": received a call from " + caller);
    }

    private double add(double oprd1, double oprd2) { return oprd1 + oprd2; }
    private double sub(double oprd1, double oprd2) { return oprd1 - oprd2; }
    private double mul(double oprd1, double oprd2) { return oprd1 * oprd2; }
    private double div(double oprd1, double oprd2) { return oprd1 / oprd2; }

    @Override
    public void calculate(double oprd1, String op, double oprd2) {
        switch(op) {
            case "+":
                System.out.println(owner + "'s IPhone " + model + ": " + oprd1 + " + " + oprd2 + " = " + add(oprd1,oprd2));
                break;
            case "-":
                System.out.println(owner + "'s IPhone " + model + ": " + oprd1 + " - " + oprd2 + " = " + sub(oprd1,oprd2));
                break;
            case "*":
                System.out.println(owner + "'s IPhone " + model + ": " + oprd1 + " * " + oprd2 + " = " + mul(oprd1,oprd2));
                break;
            case "/":
                System.out.println(owner + "'s IPhone " + model + ": " + oprd1 + " / " + oprd2 + " = " + div(oprd1,oprd2));
                break;
            default:
                System.out.println(owner + "'s IPhone " + model + ": " + op + " = NOT supported operator");
        }
    }

    @Override
    public void calculate(Scanner s) {
        calculate(s.nextDouble(), s.next(), s.nextDouble());
    }

    @Override
    public String getMaker() { return "Apple"; }
}

class Person {
    public String name; // 사람 이름
    public int id; // Identifier
    public double weight; // 체중
    SmartPhone smartPhone;

    public Phone getPhone() { return smartPhone; }
    public Calculator getCalculator() { return smartPhone; }
    public void setSmartPhone(SmartPhone smartPhone) {
        this.smartPhone = smartPhone;
    }

    public void set(String name, int id, double weight) {
        this.name = name;
        this.id = id;
        this.weight = weight;
        setSmartPhone((id%2 == 1)? new GalaxyPhone(name) : new IPhone(name, "13"));
    }
    public Person(String name, int id, double weight) {
        set(name, id, weight);
    }
    public Person(Scanner s) {
        this(s.next(), s.nextInt(), s.nextDouble());
    }
    public void println() { print(); System.out.println(); }

    public void print() {
        System.out.print(name + ", ID:" + id + ", W:" + weight + ", " + smartPhone.getMaker());
    }

    public String getName() { return name; }
    public int getID() { return id; }
    public double getWeight() { return weight; }
    public void set(String name) {
        this.name = name; }
    public void set(int id) {
        this.id = id; }
    public void set(double weight) {
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
    }
    public Student(String name, int id, double weight, String department, int year, double GPA) {
        super(name, id, weight);
        setStudent(department, year, GPA);
    }
    public Student(Scanner s) {
        super(s);
        setStudent(s.next(), s.nextInt(), s.nextDouble());
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
        System.out.print(",\tD:" + department + ", Y:" + year + ", GPA:" + GPA);
    }

    @Override
    public void update(Scanner s) {
        super.update(s);
        setStudent(s.next(), s.nextInt(), s.nextDouble());
    }
}
class Worker extends Person {
    private String company; // 회사명
    private String position; // 직급

    public void set(String company, String position) {
        this.company = company;
        this.position = position;
    }
    public Worker(String name, int id, double weight, String company, String position) {
        super(name, id, weight);
        set(company, position);
    }
    public Worker(Scanner s) {
        super(s);
        set(s.next(), s.next());
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
        System.out.print(",\tC:" + company + ", P:" + position);
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

    public void call() {
        System.out.print("Names of phone caller and callee? ");
        Person caller = find(scanner.next());
        Person callee = find(scanner.next());
        if((callee == null) || (caller == null))  {
            scanner.nextLine();
            return;
        }
        caller.getPhone().sendCall(callee.getName());
        callee.getPhone().receiveCall(caller.getName());
    }
    public void calculate() {
        System.out.print("Calculator's owner and expression? ");
        String s = scanner.next();
        Person p = find(s);
        if (p == null ) {
            scanner.nextLine();
            return;
        }
        p.getCalculator().calculate(scanner);

    }

    final int 종료 = 0, 모두보기 = 1, 검색 = 2, 수정 = 3, 삭제 = 4,
            삽입 = 5, 추가 = 6, 뭐하니 = 7,전화=8, 계산=9;

    public void run() {
        System.out.println("PersonManage::run() start");
        display();
        while (true) {
            System.out.println();
            System.out.println("Menu: 0.Exit 1.DisplayAll 2.Search 3.Update 4.Remove 5.Insert");
            System.out.println(" 6.Append 7.WhatDoing? 8.PhoneCall 9.Calculator");
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
                case 전화:
                    call();
                    break;
                case 계산:
                    calculate();
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



// 기존 Main 클래스를 아래로 대체할 것

public class Main {

    public static void phoneTest(Phone p1, Phone p2) {
        p1.sendCall("Choon");
        p2.receiveCall("Hong");
        System.out.println();

        p2.sendCall("Hong");
        p1.receiveCall("Choon");
        System.out.println();
    }

    public static void CalculatorTest(Scanner s, Calculator c) {
        c.calculate(2, "+", 4);
        c.calculate(2, "-", 4);
        c.calculate(2, "*", 4);
        c.calculate(2, "/", 4);
        c.calculate(2, "%", 3);
        System.out.println();

        for (int i = 0; i < 5; i++) {
            System.out.print("Expression to calculate? "); // 2 - 3처럼 입력해야 함. 2-3하면 에러로 종료
            c.calculate(s);
        }
        System.out.println();
    }

    public static void smartPhoneTest(Scanner scanner) {
        GalaxyPhone gp = new GalaxyPhone("Hong");
        IPhone ip = new IPhone("Choon", "12");
        gp.sendCall("Choon");
        ip.receiveCall("Hong");
        System.out.println();

        ip.sendCall("Hong");
        gp.receiveCall("Choon");
        System.out.println();

        phoneTest(gp, ip);

        System.out.println("Calculation in Galaxy phone");
        CalculatorTest(scanner, gp);

        System.out.println("Calculation in Iphone");
        CalculatorTest(scanner, ip);
    }

    public static void main(String[] args) { Scanner scanner = new Scanner(System.in);
        smartPhoneTest(scanner);
        Person fivePersons[] = {
                new Student("Hong",  10, 64,   "Computer",    2, 3.5),
                new Worker ("Mong",  11, 75,   "Samsung",     "Director"),
                new Worker ("Choon", 12, 45.5, "LG",          "DepartmentHead"),
                new Student("Chung", 13, 46.1, "Physics",     1, 3.8),
                new Student("Soon",  14, 88.5, "Electronics", 4, 2.5),
        };
        PersonManager pm = new PersonManager(fivePersons, scanner);
        pm.run();
        scanner.close();
    }
}
