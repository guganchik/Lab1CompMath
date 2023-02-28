import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    static boolean isFile = false;
    private static Scanner getInfo() throws FileNotFoundException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Do you want to enter matrix from file or console? (Write a file or console)");
        String str = scanner.nextLine();
        while (true) {
            if (str.equalsIgnoreCase("file")) {
                isFile = true;
                break;
            }
            if (str.equalsIgnoreCase("console")) {
                break;
            }
            System.out.println("Incorrect input. Please repeat");
            str = scanner.nextLine();
        }

        if (isFile) {
            System.out.println("Enter path to file");
            File file = new File(scanner.nextLine());
            scanner = new Scanner(file);
        } else {
            System.out.println("Enter a matrix size, accuracy, max count of steps");
        }
        return scanner;
    }
    public static void main(String[] args) {
        Scanner scanner;
        try {
            scanner = getInfo();
        } catch (FileNotFoundException e) {
            System.out.println("Error! File not found! Read from console");
            scanner = new Scanner(System.in);
        }

        // init size of matrix (n)
        // init epsilon
        // init max count of iterations (M)
        int n = 0;
        double epsilon = 0.01;
        int M = 0;

        while (true) {
            try {
                n = scanner.nextInt();
            } catch (InputMismatchException e) {
                scanner.next();
            }
            try {
                epsilon = scanner.nextDouble();
            } catch (InputMismatchException e) {
                scanner.next();
                System.out.println("incorrect epsilon. We use 0.01");
            }
                M = scanner.nextInt();

            if (n > 0) {break;} else {
                System.out.println("size(n) must be positive and integer");
                if(isFile){
                    System.out.println("incorrect file. Exit.");
                    System.exit(1);
                }
            }

            if (M > 0) {break;} else {
                System.out.println("max count of iterations(M) must be positive and integer");
                if(isFile){
                    System.out.println("incorrect file. Exit.");
                    System.exit(1);
                }
            }

            System.out.println("Incorrect input. Please repeat");

        }

        Actions.epsilon = epsilon;
        Actions.M = M;
        if(isFile){
            System.out.println("size = " + n);
            System.out.println("epsilon = " + epsilon);
            System.out.println("max count of iterations = " + M);
        }

        if(!isFile){System.out.println("Enter matrix arguments"+ n + "x" + n);}

        // init matrix
        scanner.nextLine();
// ==============================================================================================
//        double[][] startMatrix = new double[n][n + 1];
//        for (int i = 0; i < n; ++i) {
//            for (int j = 0; j < n + 1; ++j) {
//                startMatrix[i][j] = scanner.nextDouble();
//            }
//        }
// ==============================================================================================
        double[][] startMatrix = new double[n][n + 1];
        int err = 0;
        int count = 1;
        for (int i = 0; i < n; ++i) {
            String matrixRow = scanner.nextLine();
            String[] rowValues = matrixRow.split(" ");
            if(rowValues.length != n+1) {
                while(true){
                    System.out.println("matrix must be "+n+"x"+n+". Give new arguments for row number "+count);
                    if(isFile){
                        System.out.println("incorrect file. Exit.");
                        System.exit(1);
                    }
                    matrixRow = scanner.nextLine();
                    rowValues = matrixRow.split(" ");
                    if (rowValues.length == n+1){
                        break;
                    }
                }
                count++;

//                err = 1;
            }
            for (int j = 0; j < n + 1; ++j) {
//                if (err == 1){
//                    err = 0;
//                    break;}
                startMatrix[i][j] = Integer.parseInt(rowValues[j]);
            }
        }
        if(isFile) {
            System.out.println("Your matrix arguments"+ n + "x" + n + ":");
            for (int i = 0; i < n; ++i) {
                for (int j = 0; j < n + 1; ++j) {
                    System.out.print(startMatrix[i][j] + "   ");
                }
                System.out.println();
            }
        }

        // do actions with matrix
        Actions.SIZE = n;
        Actions.setMatrixAandB(startMatrix);
        Actions.initMatrixX1andX2();
        Actions.startComputed();
    }

}