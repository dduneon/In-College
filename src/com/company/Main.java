package com.company;

import java.awt.*;
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
    void calculate(String expr);
}

abstract class SmartPhone implements Phone, Calculator {
    static Calendar userDate = null;
    static boolean isNotQuested = true;
    static void setDate(String line) {
        // 키보드가 아닌 문자열 line으로부터 읽어 들이는 Scanner를 생성할 수 있음
        Scanner s = new Scanner(line);
        userDate = Calendar.getInstance(); // [교재 예제 6-11] 참조
        userDate.set(s.nextInt(), s.nextInt()-1, s.nextInt());
        // 위에서 달(month) 값 설정에 유의할 것. 출력 때는 반대로 해야 함.
        userDate.set(Calendar.HOUR_OF_DAY, s.nextInt());
        userDate.set(Calendar.MINUTE, s.nextInt());
        userDate.set(Calendar.SECOND, s.nextInt());
        s.close(); // 문자열에서 읽어 들이는 Scanner 닫음
    }
    String owner;  // 스마트폰 소유주 이름
    Calendar date;
    public SmartPhone(String owner) { this.owner = owner; date = (userDate == null)? Calendar.getInstance() : userDate; }
    public abstract String getMaker();
    public String toString() {
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH) + 1;
        int day = date.get(Calendar.DAY_OF_MONTH);
        int ampm = date.get(Calendar.AM_PM);
        String AP = (ampm == Calendar.AM)? "AM" : "PM";
        int hour = date.get(Calendar.HOUR);
        int minute = date.get(Calendar.MINUTE);
        int second = date.get(Calendar.SECOND);
        return getMaker() + " Phone\t" + "(" + year + "." + month + "." + day + " " + AP + " " + hour + ":" + + minute +
                ":" + second + ")";
    }
}
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
    public void calculate(String expr) {
        String st[] = expr.trim().split(" ");
        calculate(Double.parseDouble(st[0]), st[1], Double.parseDouble(st[2]));
    }

    @Override
    public String getMaker() { return "SAMSUNG"; }
}
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
        calculate(s.nextLine());
    }

    @Override
    public void calculate(String expr) {
        String oprs[] = { "+", "-", "*", "/"};
        String splt[] = null;
        for(int i=0; i<oprs.length; i++) {
            if (expr.indexOf(oprs[i]) >= 0) {
                splt = expr.split("\\" + oprs[i]);
                calculate(Double.parseDouble(splt[0]), oprs[i], Double.parseDouble(splt[1]));
                return;
            }
        }
        System.out.println(owner + "'s IPhone " + model + ": " + expr + " = NOT supported operator");
    }

    @Override
    public String getMaker() { return "Apple"; }
}

class Person implements Comparable<Person>{
    public String name; // 사람 이름
    public int id; // Identifier
    public double weight; // 체중
    SmartPhone smartPhone;

    public Phone getPhone() { return smartPhone; }
    public Calculator getCalculator() { return smartPhone; }
    public void setSmartPhone(SmartPhone smartPhone) {
        this.smartPhone = smartPhone;
    }

    @Override
    public int compareTo(Person p) {
        return this.name.compareTo(p.name);
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
    //public void println() { print(); System.out.println(); }

    //public void print() {System.out.print(name + ", ID:" + id + ", W:" + weight + ", " + smartPhone.getMaker());}

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

    public String toString() {
        return name + ", ID:" + id + ", W:" + weight + ", " + smartPhone.getMaker();
    }

    public boolean equals(Object obj) {
        Person p = (Person)obj;
        if( this.name.equals(p.name) && this.id == p.id ) {
            return true;
        }
        return false;
    }
}
class Student extends Person {
    protected String department; // 학과
    protected int year; // 학년
    protected double GPA; // 평균평점

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

    //@Override
    //public void print() { super.print(); System.out.print(",\tD:" + department + ", Y:" + year + ", GPA:" + GPA);}

    @Override
    public void update(Scanner s) {
        super.update(s);
        setStudent(s.next(), s.nextInt(), s.nextDouble());
    }
    public String toString() {
        return super.toString() + ",\tD:" + department + ", Y:" + year + ", GPA:" + GPA;
    }
    public boolean equals(Object obj) {
        Student p = (Student)obj;
        if( super.equals(p) && this.department.equals(p.department) && this.year == p.year) {
            return true;
        }
        return false;
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

    // @Override
    //  public void print() { super.print();System.out.print(",\tC:" + company + ", P:" + position); }
    public String toString() {
        return super.toString() + ",\tC:" + company + ", P:" + position;
    }
    public boolean equals(Object obj) {
        Worker p = (Worker)obj;
        if( super.equals(p) && this.company.equals(p.company) && this.position.equals(p.position)) {
            return true;
        }
        return false;
    }
}
class StudWork extends Student {
    protected boolean married; // 결혼유무
    protected String career[]; // 알바경력
    protected String address;  // 현근무지주소

    public void setCareer(String sCareer) {
        // ","로 구분된 하나의 문자열 즉,
        // "CU KangNam,Seven Eleven,GS Convenient Store Suwon"로 주어진
        // 경력 리스트(sCareer)를 토큰별로 쪼개어 문자열 배열 career[]에 저장
        // String의 split()을 사용해도 되지만 여기서는 StringTokenizer를 이용할 것
        StringTokenizer st = new StringTokenizer(sCareer, ",");
        career = new String[st.countTokens()];
        int count = st.countTokens();
        for(int i=0; i<count; i++)
            career[i] = st.nextToken();
    }

    public void setAddress(String sAddress) {
        // 하나의 문자열 "Gwangju city BongsunDong 12 BeonJi"로 주어진 sAddress를
        // 아래 pseudocode처럼 몇가지 정보를 수정/추가/삭제를 한 후 멤버 address에 저장
        String 번길 = "BeonGil", 번지 = "BeonJi", 남구 = "NamGu";
        StringBuffer sb = new StringBuffer(sAddress);
        // StringBuffer의 메소드를 이용하여 구현할 것, 즉 indexOf(), replace(),
        // sb.append(), delete(), insert(), toString() 활용

        if(sb.indexOf(번지) >= 0)
            sb.replace(sb.indexOf(번지), sb.indexOf(번지) + 번지.length() ,번길);
        else if(sb.indexOf(번길) < 0)
            sb.append("BeonGil");

        if(sb.indexOf("-") >= 0)
            sb.delete(sb.indexOf("-"),sb.indexOf("-")+1);
        if(sb.indexOf("NamGu") < 0) {
            if(sb.indexOf("city") >= 0)
                sb.insert(sb.indexOf("city") + 4, " NamGu");
            else
                sb.insert(0, "NamGu ");
        }
        this.address = sb.toString();
    }

    public void set(String sMarried, String sCareer, String sAddress) {  // Overloading
        married = Boolean.parseBoolean(sMarried);
        setCareer(sCareer);
        setAddress(sAddress);
    }

    public StudWork(String args[], String personArgs[]) {
        super(personArgs[0], Integer.parseInt(personArgs[1]), Double.parseDouble(personArgs[2]),personArgs[3],Integer.parseInt(personArgs[4]),Double.parseDouble(personArgs[5]));
        set(args[1], args[2], args[3]);
    }
    public StudWork(String args[]) {
        this(args,args[0].split(" "));
    }

    // 생성자 StudWork(line)의 인자인 line 문자열은 아래처럼 구분자 ":"로 구분해서 지정해 주어야 한다.
    // StudWork("Kang 22 90.1 Computer 3 3.5:true:CU KangNam,Seven Eleven,"
    //        + "GS Convenient Store Suwon:Gwangju city BongsunDong 12 BeonJi")
    // line 문자열은 "Student인적정보:결혼여부:경력리스트:주소"로 ":"로 구분된 4개의 서브문자열로 이루어진다.
    // Student인적정보는 기존의 Student의 생성자 요소로 이루어져 있고, 결혼여부는 true 또는 false로 지정하고,
    // 경력리스트는 자신이 지금껏 근무했던 근무지 이름을 ","로 구분하여 나열하면 되고,
    // 마지막 주소는 하나의 문자열로 나열하면 된다.
    public StudWork(String line)   {
        this(line.trim().split(":"));
    }
    public String toString() {
        StringBuffer sb = new StringBuffer(super.toString());
        sb.append(" M:"+ married+ ",\n\tCareer:");
        for(int i=0; i<career.length-1; i++) {
            sb.append(career[i]+", ");
        }
        sb.append(career[career.length - 1] + ",\n\tAddr:" + address);
        return sb.toString();
    }

    public void printStudWork(String name, String sYear, String sGPA, String sMarried) {
        System.out.println("name:"+name+",\tyear:"+sYear+", GPA:"+sGPA+", M:"+sMarried);
    }
    public void printStudWork() {
        printStudWork(name, Integer.toString(year), Double.toString(GPA), Boolean.toString(married));
    }
}
class PersonManager {

    private Vector<Person> vector;

    private Scanner scanner;

    PersonManager(Person array[], Scanner scanner) {
//MAX_PERSONS개의 원소를 가진 persons 배열을 생성
        this.scanner = scanner;

        vector = new Vector<Person>();
        for(var a : array) vector.add(a);
    }

    int findIndex(String name) {
        for(int i=0; i<vector.size(); i++) {
            if(name.equals(vector.elementAt(i).getName()))
                return i;
        }
        System.out.println(name + " is NOT found.");
        return -1;
    }

    Person find(String name) {
        int index = findIndex(name);
        return (index < 0) ? null : vector.elementAt(index);
    }

    void display() {
        for(int i=0; i<vector.size(); i++)
            System.out.println(vector.elementAt(i).toString());
        System.out.println("Person count: " + vector.size());
    }

    void search() {
        System.out.print("Name to search? ");
        String find = scanner.next();
        if(findIndex(find) != -1)
            System.out.println(vector.elementAt(findIndex(find)).toString());
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
        vector.remove(idx);
    }
    public Person getNewPerson() {
        String tag = scanner.next();
        if (tag.equals("S")) // tag가 "S"
            return new Student(scanner);
        else if (tag.equals("W")) // tag가 "W"
            return new Worker(scanner);
        else if (tag.equals("SW"))
            return new StudWork(scanner.nextLine());
        else {
            System.out.println(tag + ": WRONG delimiter");
            scanner.nextLine();
            return null;
        }
    }
    void insert() {
        int idx=0;
        Person p=null;
        while(true) {
            if (vector.size() != 0) {
                System.out.print("Existing name to insert in front? ");
                String iname = scanner.next();
                System.out.println("[Person delimiter(S or W or SW)] [Person information to insert]?");
                p = getNewPerson();
                idx = findIndex(iname);
            }
            else {
                System.out.println("[Person delimiter(S or W or SW)] [Person information to insert]?");
                p = getNewPerson();
                idx = 0;
            }
            if(idx != -1) {
                if(p == null) {
                    break;
                }
                vector.insertElementAt(p, idx);
                break;
            }
            else    break;
        }
    }

    void append() {
        System.out.println("Continuously input [S or W or SW] [person information to insert], and\n" +
                "input \"end\" at the end.");
        while(true) {
            if(scanner.hasNext("end")) {
                scanner.next();
                break;
            }
            Person p = getNewPerson();
            if(p!=null) vector.add(p);
        }
    }

    void whatDoing() { System.out.print("Name to know about? ");
        String find = scanner.next();
        if(findIndex(find) != -1)
            vector.elementAt(findIndex(find)).whatAreYouDoing();
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

    public void findPerson() {
        System.out.println("[Delimiter(S or W or SW)] [Person information to find by using equals()]?");
        Person p = getNewPerson();
        if (p == null)  return;
        Person fp = find(p.name);
        if(fp != null) {
            if(fp.equals(p)) {
                System.out.println(fp.toString());
                return;
            }
        }
        System.out.println("can NOT find anyone equal to " + p.name);
    }         // 메뉴항목: FindPerson(equals()이용한 사람 찾기)

    public void displayPhone() {
        for(int i=0; i<vector.size(); i++) {
            System.out.println(vector.elementAt(i).name + ": " + vector.elementAt(i).smartPhone.toString());
        }
    }       // 메뉴항목: DispAllPhone(모든폰보기)

    public void changePhone() {
        if (SmartPhone.isNotQuested) { // changePhone()을 처음 실행했다면 한번만 아래 과정 실행
            scanner.nextLine(); // Menu item number? 12[엔터]: 숫자 뒤의 [엔터]를 skip
            // ex: 년 월 일 시 분 초 순서로 입력
            System.out.print("Date and time to set(ex: 2021 10 1 18 24 30)? ");
            String line = scanner.nextLine();
            if (!line.equals("")) // [년 월 일 시 분 초]를 입력한 경우
                SmartPhone.setDate(line);
            // 날짜를 입력하지 않고 그냥 엔터를 친 경우(line.equals("")) 아무 것도 설정하지 않음
            SmartPhone.isNotQuested = false;
            // 한번만 사용자에게 질의하여 위 과정을 실행하고 다음부터는 위 과정 생략하도록 하기 위해
        }
        System.out.println("Owner name and maker of phone to change(ex: Hong Samsung or Hong Apple)?");
        String n = scanner.next(); String s = scanner.next();
        Person p = find(n);
        if(p==null) return;
        if(s.equals("Samsung"))
            p.setSmartPhone(new GalaxyPhone(p.name));
        else if(s.equals("Apple"))
            p.setSmartPhone(new IPhone(p.name, "13"));
        else {
            System.out.println(s + ": " + "WRONG phone's maker");
            return;
        }
        System.out.println(p.getPhone());
    }        // 메뉴항목: ChangePhone

    private Random rnd = null;

    // Math.random() 대신 rnd.random()을 사용할 것
    public void changeWeight() {        // 메뉴항목: ChangeWeight(자동체중변경)
        if (rnd == null) {
            System.out.print("Seed integer for random number generator? ");
            rnd = new Random(scanner.nextInt());
        }
        for(int i=0; i<vector.size(); i++) {
            double weight = rnd.nextDouble();
            vector.elementAt(i).weight = Math.round(weight * (60) + 40);
        }
        display();
        // 여기에 코드 추가
    }

    public void displayStudWorks() {
        for(int i=0; i<vector.size(); i++) {
            StudWork SW;
            if(vector.elementAt(i) instanceof StudWork) {
                SW = (StudWork) vector.elementAt(i);
                SW.printStudWork();
            }
        }
    }   // 메뉴항목: DispAllAlba(모든알바생들보기)

    public void sort() {
        Collections.sort(vector);
        display();
    }                // 메뉴항목: Sort(정렬)

    public void reverse() {
        Collections.reverse(vector);
        display();
    }             // 메뉴항목: Reverse(역순배치)

    public void binarySearch() {
        System.out.print("For binary search, it's needed to sort in advance. Name to search? ");
        String name = scanner.next();
        Person p = new Person(name, 0, 0.0);
        int index = Collections.binarySearch(vector, p);
        if(index >= 0)   {
            System.out.println(vector.get(index));
        }
        else System.out.println(name + " is NOT found.");
    }        // 메뉴항목: BinarySearch(이진검색)

    final int 종료 = 0, 모두보기 = 1, 검색 = 2, 수정 = 3, 삭제 = 4,
            삽입 = 5, 추가 = 6, 뭐하니 = 7,전화=8, 계산=9, 사람찾기=10, 모든폰보기=11, 폰변경=12, 자동체중변경=13, 알바생들보기=14,
            정렬 = 15, 역순배치 = 16, 이진검색 = 17;

    public void run() {
        System.out.println("PersonManage::run() start");
        display();
        while (true) {
            System.out.println();
            System.out.println("Menu: 0.Exit 1.DisplayAll 2.Search 3.Update 4.Remove 5.Insert");
            System.out.println("\t6.Append 7.WhatDoing? 8.PhoneCall 9.Calculator 10.FindPerson(equals())\n" +
                    "\t11.DispAllPhone 12.ChangePhone 13.ChangeWeight 14.DispAllAlba\n" +
                    "\t15.Sort 16.Reverse 17.BinarySearch\n");
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
                case 사람찾기:
                    findPerson();
                    break;
                case 모든폰보기:
                    displayPhone();
                    break;
                case 폰변경:
                    changePhone();
                    break;
                case 자동체중변경:
                    changeWeight();
                    break;
                case 알바생들보기:
                    displayStudWorks();
                    break;
                case 정렬:
                    sort();
                    break;
                case 역순배치:
                    reverse();
                    break;
                case 이진검색:
                    binarySearch();
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

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Person array[] = {
                new StudWork("Kang 22 90.1 Computer 3 3.5:true:CU KangNam,Seven Eleven,"
                        + "GS Convenient Store Suwon:Gwangju city BongsunDong 12 BeonJi"),
                new StudWork("Sham 20 81.5 Electronics 2 2.1:true:Family Mart,7 11,"
                        + "GS BukGu:Gwangju city NamGu 12-2"),
                new StudWork("Jang 21 70.3 Mathematics 4 3.0:false:Seven Eleven:12-3 BeonGil"),
                new Student("Hong", 10, 64, "Computer", 2, 3.5),
                new Worker("Mong", 11, 75, "Samsung", "Director"),
                new Worker("Choon", 12, 45.5, "LG", "DepartmentHead"),
                new Student("Chung", 13, 46.1, "Physics", 1, 3.8),
                new Student("Soon", 14, 88.5, "Electronics", 4, 2.5),
        };
        PersonManager pm = new PersonManager(array, scanner);
        pm.run();
        scanner.close();
    }
}