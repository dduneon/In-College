package com.company;

import java.io.*;
import java.util.InputMismatchException;
import java.util.Scanner;

class Main{
    public static void printArray(double arr[][]) {
        System.out.println("arr length: " + arr.length);

        for(int i=0; i<arr.length; i++) {
            System.out.print("arr[" + i + "]");
            for(int j=0; j<arr[i].length; j++) {
                System.out.print(" " + arr[i][j]);
            }
            System.out.println();
        }
    }
    public static double[][] run1(Scanner s) {
        System.out.print("The number of rows of non-square array of doubles to create? ");
        int N = s.nextInt();

        double [][] darr = new double[N][];
        for(int i=0; i<N; i++) {
            System.out.print("Input continuously "+ Integer.toString(i+1) + " doubles to be stored in line " + Integer.toString(i+1) + ": ");
            darr[i] = new double[i+1];
            for(int j=0; j<=i; j++) {
                darr[i][j] = s.nextDouble();
            }
        }
        return darr;
    }
    public static double[][] run2(Scanner s) {

        int N;
        double[][] darr;
        while(true) {
            try {
                System.out.print("The number of rows of non-square array of doubles to create? ");
                N = s.nextInt();
                darr = new double[N][];
                break;
            }
            catch(NegativeArraySizeException e) {
                System.out.println("Input a POSITIVE integer. Try again.");
            }
            catch(InputMismatchException ee) {
                System.out.println("Input an INTEGER. Try again.");
                s.next();
            }
        }

        for(int i=0; i<N; i++) {
            System.out.print("Input continuously "+ Integer.toString(i+1) + " doubles to be stored in line " + Integer.toString(i+1) + ": ");
            darr[i] = new double[i+1];
            for(int j=0; j<=i; j++) {
                while(true) {
                    try {
                        darr[i][j] = s.nextDouble();
                        break;
                    }
                    catch(InputMismatchException e) {
                        j = 0;
                        System.out.println("Input a integer or double. Try again.");
                        System.out.print("Input continuously "+ Integer.toString(i+1) + " doubles to be stored in line " + Integer.toString(i+1) + ": ");
                        s.nextLine();
                    }
                }
            }
        }
        return darr;
    }

    public static void run3(Scanner s) {
        final int USER = 0;      // 상수 값 정의
        final int COMPUTER = 1;
        // 0, 1, 2 중 하나의 난수를 미리 발생하여 저장해 놓은 난수 배열
        // 이 배열에 저장된 값은 나중에 MJBarray[] 배열의 인덱스 값으로 사용됨
        int randArray[] = { 2, 1, 2, 2, 2, 1, 1, 0, 0, 2,
                0, 2, 0, 2, 0, 1, 1, 2, 0, 0,
                2, 0, 2, 2, 1, 2, 0, 0, 1, 2, };
        String MJBarray[] = { "m", "j", "b" }; // 묵(m)찌(j)빠(b) 문자열을 가진 배열

        System.out.println("Start the MUK-JJI-BBA game.");
        System.out.print("Select any index for random number[0~29]? ");
        int randIdx = s.nextInt();
        s.nextLine(); // 입력 버퍼에 있는 '\n' 제거

        int priority = USER; // 누가 우선권을 가졌는지 저장하고 있음, USER:사용자 우선권, COMPUTER:computer 우선권
        String priStr[] = { "USER", "COMPUTER"}; // 화면에 출력할 때 사용함

        while(true) {
            System.out.println();
            System.out.println(priStr[priority] + " has the higher priotiy.");
            System.out.print("m(muk), j(jji), b(bba) or stop? ");
            String user = s.nextLine();
            if(user.equals("stop")) break;
            else if(!(user.equals("m") || user.equals("j") || user.equals("b"))) {
                System.out.println("Select one among m, j, b.");
                continue;
            }

            int comIdx = randArray[randIdx++];
            if(randIdx == 30) randIdx = 0;

            String computer = MJBarray[comIdx];
            System.out.print("User = "+ user +", Computer = "+ computer +", ");

            if(user.equals(computer)) {
                if (priority == USER) {
                    System.out.println("USER WINs.");
                    priority = USER;
                } else {
                    System.out.println("COMPUTER WINs.");
                    priority = COMPUTER;
                }
            }
            else {
                System.out.println("SAME.");
                if(( user.equals("m") && computer.equals("j") ) ||
                        (user.equals("b") && computer.equals("m")) ||
                        (user.equals("j") && computer.equals("b")))
                    priority = USER;
                else
                    priority = COMPUTER;
            }
        }
    }

    public static void main(String[] args) {
        double array[][] = { {0}, {1,2}, {3,4,5} };
        printArray(array);
        System.out.println();
        Scanner sc = new Scanner(System.in); // 필요한 파일 import시킬 것
        double dArr1[][] = run1(sc);
        printArray(dArr1);
        System.out.println();
        double dArr2[][] = run2(sc);
        printArray(dArr2);
        System.out.println();
        run3(sc);
        System.out.println();
        sc.close();
        System.out.println("Done.");
    }
}