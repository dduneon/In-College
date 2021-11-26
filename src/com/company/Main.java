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

    public void println() { print(); System.out.println(); }

    public void print() {
        System.out.print("name:" + name + ", ID:" + id + ", weight:" + weight);
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
}

public class Main {

    public static void simpleTest() {
        Person p = new Person("HongGilDong", 0, 71.5);
        p.println();
        System.out.println(p.getName() + "\'s ID is " + p.getID() +
                "and his(her) weight is " + p.getWeight() + ".");
        p.whatAreYouDoing();
        p.set("LeeMongRyong");
        p.set(1);
        p.set(94.3);
        p.println();
        System.out.println("p.isSame(LeeMongRyong, 1): " + p.isSame("LeeMongRyong", 1));
        System.out.println("p.isSame(HongGilDong, 2): " + p.isSame("HongGilDong", 2));
        System.out.println("p.isSame(HongGilDong, 1): " + p.isSame("HongGilDong", 1));
        p.set("HongGilDong", 2, 60);
        p.println();
        System.out.println("p.isSame(LeeMongRyong, 1): " + p.isSame("LeeMongRyong", 1));
        System.out.println(); // 빈줄 출력
    }
    public static Person[] concatPersons(Person p1Arr[], Person p2Arr[]) {
        Person[] sumArr = new Person[p1Arr.length + p2Arr.length];
        System.out.println("concatPersons(p1Arr, p2Arr): concated array length: " +
                sumArr.length);
        System.out.println();

        for(int i=0; i<sumArr.length; i++) {
            if(i<p1Arr.length)
                sumArr[i] = p1Arr[i];
            else
                sumArr[i] = p2Arr[i- p1Arr.length];
        }
        return sumArr;
    }

    public static void arrayTest() {
        Person twoPersons[] = {
                new Person("HongGilDong", 0, 64),
                new Person("LeeMongRyong", 1, 75)
        };
        Person threePersons[] = {
                new Person("SungChoonHyang", 2, 45.5),
                new Person("ShimChungEe", 3, 46.1),
                new Person("LeeSoonShin", 4, 88.5)
        };
        System.out.println();

        // 두 배열을 합친 새로운 배열을 리턴 받음
        Person persons[] = concatPersons(twoPersons, threePersons);

        System.out.println("Person count: " + persons.length);

        for(int i=0; i<persons.length; i++)
            persons[i].println();
        System.out.println();
    }

    public static void main(String[] args) {
        simpleTest();
        arrayTest();
        System.out.println("Done");
    }
}