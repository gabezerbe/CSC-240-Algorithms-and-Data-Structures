package com.company;

public class Main {

    public static void main(String[] args) {
//        int X[] = {100, 200, 300, 400, 500};
//        int X[] = {100, 200, 300, 400, 500, 600, 700, 800, 900, 1000};
//        int X[] = {100, 200, 300, 400, 500, 600, 700, 800, 900, 1000, 1100, 1200, 1300, 1400, 1500};
//            int X[] = {100, 200, 300, 400, 500, 600, 700, 800, 900, 1000, 1100, 1200, 1300, 1400, 1500, 1600, 1700, 1800, 1900, 2000};
            int X[] = {100, 200, 300, 400, 500, 600, 700, 800, 900, 1000, 1100, 1200, 1300, 1400, 1500, 1600, 1700, 1800, 1900, 2000, 2100, 2200, 2300, 2400, 2500};
        int n = X.length;

//        System.out.println("ap1 results:");
        long startTime = System.nanoTime();
        ap1(X, n);
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        System.out.println("ap1 runtime: " + duration);
//        System.out.println("ap2 results:");

        long startTime2 = System.nanoTime();
        ap2(X, n);
        long endTime2 = System.nanoTime();
        long duration2 = endTime2 - startTime2;
        System.out.println("ap2 runtime: " + duration2);
    }

    static int[] ap1(int[] X, int n){
        int[] A = new int[n];

        for(int i = 0; i < n; i++) {
            int a = 0;
            for (int j = 0; j <= i; j++) {
                a += X[j];
            }
            A[i] = a / (i + 1);
        }

        return A;
//        for (int element: A){
//            System.out.println(element);
//        }
    }

    static int[] ap2(int[] X, int n){
        int[] A = new int[n];

        int s = 0;
        for(int i = 0; i < n; i++){
            s += X[i];
            A[i] = s/(i+1);
        }

            return A;
//        for (int element: A){
//            System.out.println(element);
//        }
    }
}
