package com.company;

import java.awt.*;
import java.io.*;
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

    public void writeText(PrintStream out) { out.print(name+" "+id+" "+weight); }
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

    @Override
    public void writeText(PrintStream out) {
        super.writeText(out);
        out.print(" "+department+" "+year+" "+GPA);
        // 행의 끝을 나타내는 [엔터]는 위 textFileSave(String fileName)에서 삽입함
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
    @Override
    public void writeText(PrintStream out) {
        super.writeText(out);
        out.print(" "+department+" "+year+" "+GPA);
        // 행의 끝을 나타내는 [엔터]는 위 textFileSave(String fileName)에서 삽입함
        out.print(":"+married+":");
        for(int i=0; i<career.length-1; i++)    out.print(career[i] + ",");
        out.print(career[career.length-1] + ":" + address);
    }
}

interface Factory<T>
{
    // 스캐너로부터 제네릭 타입 T형의 사람 정보를 입력 받아 T형의 새로운 사람 객체를 생성하여 반환함
    T newPerson(Scanner scanner);
    // 이 메소드는 추후 Factory<T>를 implements하는 여러 클래스에서 직접 구현해야 한다.
    // 5번 메뉴항목 입력 후 "Soon" 뒤에 ([엔터] 또는 ' ' 또는 '\t')가 있고
    // 그 뒤에 "사람 인적정보[엔터]" 입력되었을 때 중간에 있는 ([엔터] 또는 ' ' 또는 '\t')를
    // 제거하고 "사람 인적정보"를 읽어 내는 메소드
    public static String getNextLine(Scanner s) {
        String line = s.nextLine().trim();
        for ( ; line.equals(""); line = s.nextLine().trim())
            ;
        return line;
    }
    String HOME_DIR = "c:/tmp";
    String getDefaultTextPathName();
    String getTextDelimiter(Person p);
}

class FileManager<T extends Person> {

    private Scanner scanner;
    private LinkedList<T> list;
    private Factory<T> factory;

    FileManager(LinkedList<T> list, Scanner scanner, Factory<T> factory) {
        this.scanner = scanner;
        this.factory = factory;
        this.list = list;
    }

    void display() {
        for(var p : list) System.out.println(p);
        System.out.println("Person count: " + list.size());
    }
    void deleteAllPersons() {
        list.clear();
    }
    void printFileInfo(File f) { // [교재 예제 8-8] 참조
        long t = f.lastModified(); // 마지막으로 수정된 시간 구하기
        t = 0;  // 자동 체크 때 사용할 예정임
        System.out.printf("%-20s\t%tF %Tp %tI:%tM %9d\n",
                (f.isDirectory()? "[D] ":"") + f.getName(), t, t, t, t, f.length());
        // "파일이름 수정년-월-일 오전/오후 수정시간:분 파일크기"
        // 디렉토리일 일 경우 디렉토리 이름 앞에 "[D] "를 아니면 ""를 추가
        // 날짜 및 시간 정보 출력과 관련한 포맷은 아래 URL에서 java.util.Formatter 클래스 참조할 것
        // https://docs.oracle.com/javase/10/docs/api/index.html?overview-summary.html
    }

    // 앞으로 우리는 항상 "c:/tmp" 폴더에서만 작업할 것이다.
    File[] displayFileList() {  // [교재 예제 8-8] 참조
        String pathname = Factory.HOME_DIR;  // "c:/tmp"로 지정되어 있음, 아래 3) 참조
        System.out.println("\n" + pathname + ": File list");
        //pathname을 이용하여 File 객체 dir 생성
        File dir = new File(pathname);

        //dir 디렉토리에 포한된 파일과 하부 디렉토리 이름의 리스트를 얻어
        File[] files = dir.listFiles();
        for(int i=0; i<files.length; i++) {
            System.out.printf("%2d: ", i);
            printFileInfo(files[i]);
        }
        return files;
    }
    File getExistingFile(String preMsg, String postMsg) {
        File[] files = displayFileList();
        if (preMsg.length() != 0) // preMsg가 ""가 아닌 경우
            preMsg += " ";
        while (true) {
            System.out.print("Index number of "+preMsg+"file to "+postMsg+"?[-1:exit] ");
            try {
                int val = scanner.nextInt();
                if (val > -1 && val < files.length) {
                    return files[val];
                }
                else if (val == -1) return null;
                else System.out.println("Invalid number.");
            } catch (InputMismatchException e) { // 정수 외의 값 입력했을 때
                System.out.println("Input an index NUMBER.");
                scanner.nextLine(); // 입력된 그 행의 나머지 내용 스킵
            }
        }
    }
    void delete() {
        File f = getExistingFile("", "delete");
        if(f == null)   return;
        f.delete();
        displayFileList();
    }
    void rename() {
        File src = getExistingFile("source", "rename");
        if(src == null) return;

        System.out.print("Target file name? ");
        String target = scanner.next();
        String pathname = src.getParent() + "/" + target;

        File dst = new File(pathname);
        if(dst.exists()) {
            System.out.println(dst.getName() + " already exists");
            return;
        }
        src.renameTo(dst);
        displayFileList();
    }
    void copy()   {
        File src = getExistingFile("source", "copy");
        if(src == null) return;

        System.out.print("Target file name? ");
        String target = scanner.next();
        String pathname = src.getParent() + "/" + target;

        File dst = new File(pathname);
        if(dst.exists()) {
            displayFileList();
            System.out.println(dst.getName() + " already exists");
            return;
        }

        try {
            FileInputStream fi = new FileInputStream(src);
            FileOutputStream fo = new FileOutputStream(dst);
            byte [] buf = new byte [1024*8];
            for (int n = 0; (n = fi.read(buf)) > 0; )
                fo.write(buf, 0, n);

            fi.close();
            fo.close();
        }catch(IOException e) {
            System.out.println("File copy error");
        }

        displayFileList();
    }
    void textFileSave(String fileName) {
        try {
            // 기존에 동일한 이름의 파일이 있으면 파일이 열린 후 파일 내용이 모두 삭제됨 (덮어쓰기)
            var fout = new PrintStream(fileName); // 텍스트 파일을 출력용으로 열기
            for (T t: list) {
                String delimiter = factory.getTextDelimiter(t);
                if (delimiter != null) 		// Main 메뉴에서 4.AllKindPerson을
                    fout.print(delimiter+" ");	// 선택한 경우 사람 구분자 출력
                t.writeText(fout); 		// 각 사람 정보를 문자열로 변환하여 출력
                fout.println(); 		// 행의 끝에 [엔터] 출력
            }
            fout.close();
        }
        catch (IOException e) { // 파일이 존재하지 않을 경우 발생함
            System.out.println(fileName+": "+e);
            return;
        }
        displayFileList();
    }
    void textFileLoard(String fileName) {
        FileInputStream fin;
        try {
            fin = new FileInputStream(fileName);
        }
        catch (IOException e) { // 파일이 존재하지 않을 경우
            System.out.println(fileName+": "+e);
            return;
        }
        // 키보드가 아닌 파일에서 읽어 들이는 스캐너 생성
        Scanner sc = new Scanner(fin); // System.in이 아님에 주의
        list.clear(); // 기존 list에 있는 모든 인적 정보들 삭제(새로 읽어 들이기 위해)
        while (true) {
            try { // sc를 통해 파일에서 한 사람의 인적정보 읽어 들인 후 해당 사람객체 생성
                T p = factory.newPerson(sc);
                // 메인 메뉴 4.AllKindPerson 항목일 경우 파일에서 사람 구분자가 잘못
                // 지정된 경우 newPerson(sc)에서 null이 리턴될 수 있음
                if (p != null)	// 사람 객체가 정상적으로 생성되었으면
                    list.add(p);// 파일에서 새로 읽어 들인 사람 객체를 list에 추가
            }
            catch (NoSuchElementException e) {
                break;  // 파일 끝까지 다 읽었는데 또 읽으라고 요청하면 이 Exception 발행함
            }	// 중요: 파일을 정상적으로 다 읽은 후 이 Exception을 이용해 while 문을 빠져 나감
        }
        sc.close();
        display();
    }
    // 디폴트 텍스트 파일에 저장
    void textSave()    { textFileSave (factory.getDefaultTextPathName()); }
    // 디폴트 텍스트 파일에서 불러오기
    void textLoard()   { textFileLoard(factory.getDefaultTextPathName()); }
    private final int 종료=0, 모든항목보기=1, 모든항목삭제=2, 파일목록보기=3, 파일삭제=4, 파일이름변경=5, 파일복사=6
            , Text저장 = 11, Text불러오기=12;


    public void run() {
        System.out.println("FileManager run() starts");
        displayFileList();
        while (true) {

            System.out.println("------------------------- File Management Menu --------------------------------");
            System.out.println("- 0.Exit  1.DisplayAllPerson  2.DeleteAllPerson                               -");
            System.out.println("- 3.FileList 4.RemoveFile 5.RenameFile 6.CopyFile                             -");
            System.out.println("- 11.SaveDefaultText   12.LoadDefaultText                                     -");
            System.out.println("-------------------------------------------------------------------------------");
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
                case 모든항목보기:
                    display();
                    break;
                case 모든항목삭제:
                    deleteAllPersons();
                    break;
                case 파일목록보기:
                    displayFileList();
                    break;
                case 파일삭제:
                    delete();
                    break;
                case 파일이름변경:
                    rename();
                    break;
                case 파일복사:
                    copy();
                    break;
                case Text저장:
                    textSave();
                    break;
                case Text불러오기:
                    textLoard();
                    break;
                case 종료: System.out.println("FileManager run() returned\n"); return;
                default:  System.out.println("WRONG menu item"); break;
            }
        }
    }
}

class PersonManager<T extends Person> {

    private LinkedList<T> list;
    private Factory<T> factory;

    private Scanner scanner;

    PersonManager(T array[], Scanner scanner, Factory<T> factory) {
        this.scanner = scanner;
        this.factory = factory;
        list = new LinkedList<>();

        for(var p : array)
            list.add(p);
    }

    int findIndex(String name) {
        for(int i=0; i<list.size(); i++) {
            if(name.equals(list.get(i).getName()))
                return i;
        }
        System.out.println(name + " is NOT found.");
        return -1;
    }

    T find(String name) {
        int index = findIndex(name);
        return (index < 0) ? null : list.get(index);
    }

    void display() {
        for(var p : list) System.out.println(p);
        System.out.println("Person count: " + list.size());
    }

    void search() {
        System.out.print("Name to search? ");
        String find = scanner.next();
        if(findIndex(find) != -1)
            System.out.println(list.get(findIndex(find)));
    }

    void update() {
        System.out.print("Information to update? ");
        T p = find(scanner.next());
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
        list.remove(idx);
    }
    public void printNotice(String preMessage, String postMessage) {
        System.out.print(preMessage);
        if (factory instanceof PersonFactory)
            System.out.print("[Person delimiter(S or W or SW)] ");
        System.out.println("[Person information] "+postMessage);
    }

    public T getNewPerson() {
        return factory.newPerson(scanner);
    }
    void insert() {
        int idx=0;
        T p=null;
        while(true) {
            if (list.size() != 0) {
                System.out.print("Existing name to insert in front? ");
                String iname = scanner.next();
                printNotice("", "to insert? ");
                p = getNewPerson();
                idx = findIndex(iname);
            }
            else {
                printNotice("", "to insert? ");
                p = getNewPerson();
                idx = 0;
            }
            if(idx != -1) {
                if(p == null) {
                    break;
                }
                list.add(idx, p);
                break;
            }
            else    break;
        }
    }

    void append() {
        printNotice("Continuously input ", "\nto append, and input \"end\" at the end.");
        while(true) {
            if(scanner.hasNext("end")) {
                scanner.next();
                break;
            }
            T p = getNewPerson();
            if(p!=null) list.add(p);
        }
    }

    void whatDoing() { System.out.print("Name to know about? ");
        String find = scanner.next();
        if(findIndex(find) != -1)
            list.get(findIndex(find)).whatAreYouDoing();
    }

    public void call() {
        System.out.print("Names of phone caller and callee? ");
        T caller = find(scanner.next());
        T callee = find(scanner.next());
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
        T p = find(s);
        if (p == null ) {
            scanner.nextLine();
            return;
        }
        p.getCalculator().calculate(scanner);

    }

    public void findPerson() {
        printNotice("", "to find by using equals()? ");
        T p = getNewPerson();
        if (p == null)  return;
        T fp = find(p.name);
        if(fp != null) {
            if(fp.equals(p)) {
                System.out.println(fp.toString());
                return;
            }
        }
        System.out.println("can NOT find anyone equal to " + p.name);
    }         // 메뉴항목: FindPerson(equals()이용한 사람 찾기)

    public void displayPhone() {
        for(var p : list)
            System.out.println(p.name + ": " + p.smartPhone);
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
        T p = find(n);
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
        for(var p : list) {
            double weight = rnd.nextDouble();
            p.weight = Math.round(weight * (60) + 40);
        }
        display();
        // 여기에 코드 추가
    }

    public void displayStudWorks() {
        for(int i=0; i<list.size(); i++) {
            StudWork SW;
            if(list.get(i) instanceof StudWork) {
                SW = (StudWork) list.get(i);
                SW.printStudWork();
            }
        }
    }   // 메뉴항목: DispAllAlba(모든알바생들보기)

    public void sort() {
        Collections.sort(list);
        display();
    }                // 메뉴항목: Sort(정렬)

    public void reverse() {
        Collections.reverse(list);
        display();
    }             // 메뉴항목: Reverse(역순배치)

    public void binarySearch() {
        System.out.print("For binary search, it's needed to sort in advance. Name to search? ");
        String name = scanner.next();
        Person p = new Person(name, 0, 0.0);
        int index = Collections.binarySearch(list, p);
        if(index >= 0)   {
            System.out.println(list.get(index));
        }
        else System.out.println(name + " is NOT found.");
    }        // 메뉴항목: BinarySearch(이진검색)

    void fileManager() {
        var fileMng = new FileManager<T>(list, scanner, factory);
        fileMng.run();
    }

    final int 종료 = 0, 모두보기 = 1, 검색 = 2, 수정 = 3, 삭제 = 4,
            삽입 = 5, 추가 = 6, 뭐하니 = 7,전화=8, 계산=9, 사람찾기=10, 모든폰보기=11, 폰변경=12, 자동체중변경=13, 알바생들보기=14,
            정렬 = 15, 역순배치 = 16, 이진검색 = 17, 파일관리 = 18;

    public void run() {
        System.out.println("PersonManage::run() start");
        display();
        while (true) {
            System.out.println();
            System.out.println("====================== Person Management Menu ============================");
            System.out.println("= 0.Exit 1.DisplayAll 2.Search 3.Update 4.Remove 5.Insert\t=");
            System.out.println("= 6.Append 7.WhatDoing? 8.PhoneCall 9.Calculator 10.FindPerson(equals())\t=\n" +
                    "= 11.DispAllPhone 12.ChangePhone 13.ChangeWeight 14.DispAllAlba\t=\n" +
                    "= 15.Sort 16.Reverse 17.BinarySearch 18.FileManagement\t=\n" +
                    "==========================================================================");

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
                case 파일관리:
                    fileManager();
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

class PersonFactory implements Factory<Person> {
    // 스캐너를 통해 사용자가 입력한 사람 구분자와 사람 정보를 입력 받은 후
    // 구분자에 따라 기존처럼 Student, Worker, StudWork 객체를 생성한 후 반환함
    @Override
    public Person newPerson(Scanner s) {
        String tag = s.next();
        if (tag.equals("S")) // tag가 "S"
            return new Student(s);
        else if (tag.equals("W")) // tag가 "W"
            return new Worker(s);
        else if (tag.equals("SW"))
            return new StudWork(s.nextLine());
        else {
            System.out.println(tag + ": WRONG delimiter");
            s.nextLine();
            return null;
        }
    }
    @Override
    public String getDefaultTextPathName() { return HOME_DIR+"/person.txt"; }
    // 텍스트 파일에 저장할 때 사용할 사람 구분자를 반환함
    // 메인 메뉴에서 Student를 선택했을 때는 사람 구분자가 필요 없기 때문에 (모두가 학생 객체이므로)
    // null을 반환함, 즉 사람구분자를 사용하지 않는다는 의미
    @Override
    public String getTextDelimiter(Person p) {
        if (p instanceof StudWork) // Student보다 StudWork를 먼저 체크해야 함. why?
            return "SW";

    }

class StudentFactory implements Factory<Student> {
    // 스캐너를 통해 사용자가 지정한 Student 정보를 입력 받은 후 Student 객체를 생성하여 반환함
    @Override
    public Student newPerson(Scanner s) { return new Student(s); }
    @Override
    public String getDefaultTextPathName() { return HOME_DIR+"/student.txt"; }
    // 텍스트 파일에 저장할 때 사용할 사람 구분자를 반환함
    // 메인 메뉴에서 Student를 선택했을 때는 사람 구분자가 필요 없기 때문에 (모두가 학생 객체이므로)
    // null을 반환함, 즉 사람구분자를 사용하지 않는다는 의미
    @Override
    public String getTextDelimiter(Person p) { return null; }
}

// 위 두 클래스를 참고하여 아래 두 클래스를 완성하라.

class WorkerFactory implements Factory<Worker> {
    // 스캐너를 통해 사용자가 지정한 Worker 정보를 입력 받은 후 Worker 객체를 생성하여 반환함

    @Override
    public Worker newPerson(Scanner s) { return new Worker(s); }
    @Override
    public String getDefaultTextPathName() { return HOME_DIR+"/worker.txt"; }
    // 텍스트 파일에 저장할 때 사용할 사람 구분자를 반환함
    // 메인 메뉴에서 Student를 선택했을 때는 사람 구분자가 필요 없기 때문에 (모두가 학생 객체이므로)
    // null을 반환함, 즉 사람구분자를 사용하지 않는다는 의미
    @Override
    public String getTextDelimiter(Person p) { return null; }
}

class StudWorkFactory implements Factory<StudWork> {
    // 스캐너를 통해 사용자가 지정한 StudWork 정보를 입력 받은 후 StudWork 객체를 생성하여 반환함

    @Override
    public StudWork newPerson(Scanner s) {
        return new StudWork(Factory.getNextLine(s)); }
    @Override
    public String getDefaultTextPathName() { return HOME_DIR+"/studwork.txt"; }
    // 텍스트 파일에 저장할 때 사용할 사람 구분자를 반환함
    // 메인 메뉴에서 Student를 선택했을 때는 사람 구분자가 필요 없기 때문에 (모두가 학생 객체이므로)
    // null을 반환함, 즉 사람구분자를 사용하지 않는다는 의미
    @Override
    public String getTextDelimiter(Person p) { return null; }
}

class Managers {
    static Scanner scanner;
    static PersonManager<Student>  studMng;
    static PersonManager<Worker>   workMng;
    static PersonManager<StudWork> studWorkMng;
    static PersonManager<Person>   personMng;

    private static void createStudentManager() {
        Student students[] = {
                new Student("Hong",  10, 64,   "Computer",    2, 3.5),
                new Student("Chung", 11, 46.1, "Physics",     1, 3.8),
                new Student("Soon",  12, 88.5, "Electronics", 4, 2.5),
        };
        var studFact = new StudentFactory();
        studMng = new PersonManager<Student>(students, scanner, studFact);
    }

    private static void createWorkerManager() {
        Worker workers[] = {
                new Worker ("Hong",  10, 64,   "Samsung", "Director"),
                new Worker ("Chung", 11, 46.1, "LG",      "DeparmentHead"),
                new Worker ("Soon",  12, 88.5, "Naver",   "TeamLeader"),
        };
        var workFact = new WorkerFactory();
        workMng = new PersonManager<Worker>(workers, scanner, workFact);
    }

    private static void createStudWorkManager() {
        StudWork studWorks[] = {
                // 중요: StudWork의 기본인적정보의("Kang 22 90.1 Computer 3 3.5") 경우
                //      문자열 내에 필드구분을 위해 하나의 스페이스만으로 각 필드를 구분해야 함
                new StudWork("Hong 10 64 Computer 2 3.5:true:CU KangNam,Seven Eleven,GSConvenientStore Suwon:Gwangju city BongSunDong 12 BeonJi"),
                new StudWork("Chung 11 46.1 Physics 1 3.8:true:Family Mart,7 11,GS BookGu:Gwangju city NamGu 12-2"),
                new StudWork("Soon 12 88.5 Electronics 4 2.5:false:Seven Eleven:12-3 BeonGil"),
        };
        var studWorkFact = new StudWorkFactory();
        studWorkMng = new PersonManager<StudWork>(studWorks, scanner, studWorkFact);
    }

    private static void createPersonManager() {
        Person persons[] = {
                // 중요: StudWork의 기본인적정보의("Kang 22 90.1 Computer 3 3.5") 경우
                //      문자열 내에 필드구분을 위해 하나의 스페이스만으로 각 필드를 구분해야 함
                new StudWork("Kang 22 90.1 Computer 3 3.5:true:CU KangNam,Seven Eleven,GSConvenientStore Suwon:Gwangju city BongSunDong 12 BeonJi"),
                new StudWork("Sham 20 81.5 Electronics 2 2.1:true:Family Mart,7 11,GS BookGu:Gwangju city NamGu 12-2"),
                new StudWork("Jang 21 70.3 Mathematics 4 3.0:false:Seven Eleven:12-3 BeonGil"),
                new Student("Hong",  10, 64,   "Computer",    2, 3.5),
                new Worker ("Mong",  11, 75,   "Samsung",     "Director"),
                new Worker ("Choon", 12, 45.5, "LG",          "DepartmentHead"),
                new Student("Chung", 13, 46.1, "Physics",     1, 3.8),
                new Student("Soon",  14, 88.5, "Electronics", 4, 2.5),
        };
        var personFact = new PersonFactory();
        personMng = new PersonManager<Person>(persons, scanner, personFact);
    }

    public static void create() {
        createStudentManager();
        createWorkerManager();
        createPersonManager();
        createStudWorkManager();
    }

    public static void run(Scanner scanner)
    {
        Managers.scanner = scanner;
        create();

        while (true) {
            System.out.println("************************ Main Menu *********************");
            System.out.println("* 0.Exit 1.Student 2.Worker 3.StudWork 4.AllKindPerson *");
            System.out.println("********************************************************");
            System.out.print("Menu item number? ");
            int idx = scanner.nextInt();
            if (idx == 0)
                break;
            switch (idx) {
                case 1:  System.out.println("\nPersonManager<Student>");
                    studMng.run();
                    break;
                case 2:  System.out.println("\nPersonManager<Worker>");
                    workMng.run();
                    break;
                case 3:  System.out.println("\nPersonManager<StudWork>");
                    studWorkMng.run();
                    break;
                case 4:  System.out.println("\nPersonManager<Person>");
                    personMng.run();
                    break;
                default: System.out.println("WRONG menu item\n");
                    break;
            }
        }
    }
}
public class Main
{
    static void deleteFiles() { // HOME_DIR의 모든 파일을 삭제함
        File files[] = new File(Factory.HOME_DIR).listFiles();
        for (var f: files)
            f.delete();
    }

    public static void main(String[] args)
    {
        // deleteFiles(); // 이 주석은 당분간은 유지하라.
        Scanner scanner = new Scanner(System.in);
        Managers.run(scanner);
        scanner.close();
    }
}