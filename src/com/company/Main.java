package com.company;

public class Main {

    public static void main(String[] args) {
        int X[] = {100, 200, 300, 400, 500};
        int n = X.length;

        System.out.println("ap1 results:");
        ap1(X, n);

        System.out.println("ap2 results:");
        ap2(X, n);

    }

    static void ap1(int[] X, int n){
        int[] A = new int[n];

        for(int i = 0; i < n; i++) {
            int a = 0;
            for (int j = 0; j <= i; j++) {
                a += X[j];
            }
            A[i] = a / (i + 1);
        }

        for (int element: A){
            System.out.println(element);
        }
    }

    static void ap2(int X[], int n){
        int[] A = new int[n];

        int s = 0;
        for(int i = 0; i < n; i++){
            s += X[i];
            A[i] = s/(i+1);
        }

        for (int element: A){
            System.out.println(element);
        }
    }
}
