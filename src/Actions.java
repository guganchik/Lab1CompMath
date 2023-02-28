public class Actions {
    public static int SIZE = -1;
    public static boolean isPerestanovka = false;
    // матрица исходных коэффициентов
    public static double[][] matrixA;
    //матрица свободных членов
    public static double[][] matrixB;
    // старые значения (до итерации)
    public static double[][] matrixX1;
    // новые значения (после итерации)
    public static double[][] matrixX2;
    // введенная погрешность
    public static double epsilon;
    // максимальное число итераций алгоритма
    public static int M;

    public static void setMatrixAandB(double[][] allMatrix) {
        matrixA = new double[SIZE][SIZE];
        matrixB = new double[SIZE][1];

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                matrixA[i][j] = allMatrix[i][j];
            }
            matrixB[i][0] = allMatrix[i][SIZE];
        }

        System.out.println("Before reverse: ");
        for (int i = 0; i < SIZE; ++i) {
            for (int j = 0; j < SIZE; ++j) {
                System.out.print(matrixA[i][j] + "   ");
            }
            System.out.print(matrixB[i][0]);
            System.out.println();
        }

        preReversingLines();

        System.out.println("After reverse: ");
        for (int i = 0; i < SIZE; ++i) {
            for (int j = 0; j < SIZE; ++j) {
                System.out.print(matrixA[i][j] + "   ");
            }
            System.out.print(matrixB[i][0]);
            System.out.println();
        }

    }

    public static void reverseLines(int i, int j) {
        double[] tmp = matrixA[i];
        matrixA[i] = matrixA[j];
        matrixA[j] = tmp;

        double tmpB = matrixB[i][0];
        matrixB[i][0] = matrixB[j][0];
        matrixB[j][0] = tmpB;
    }

    public static int searchLineWithNorma(int numberX) {
        int currentX = numberX;
        double currentCoeff;
        double sumOfOther;

        for (int i = numberX; i < SIZE; i++) {
            currentCoeff = Math.abs(matrixA[i][currentX]);
            sumOfOther = 0;
            for (int j = 0; j < SIZE; j++) {
                sumOfOther += Math.abs(matrixA[i][j]);
            }
            sumOfOther -= currentCoeff;
            if (currentCoeff >= sumOfOther) {
//                if (currentCoeff > sumOfOther) {
//                    isPerestanovka = true;
//                }
                isPerestanovka = true;
                reverseLines(numberX, i);
                return i;
            }
        }
        System.out.println("Не получается переставить строчки так чтобы выполнилось диагональное преобладание");
        System.exit(4);
        return -1;
    }

    public static void preReversingLines() {
        for (int i = 0; i < SIZE; i++) {
            searchLineWithNorma(i);
        }
        if (isPerestanovka) {
        } else {
            System.out.println("НЕ ВЫПОЛНЕНО УСЛОВИЕ О ТОМ ЧТОБЫ ПРИ ЗАМЕНАХ СХОДИЛИСЬ ИТЕРАЦИИ");
            System.exit(5);
        }
    }

    public static void initMatrixX1andX2() {
        // first init with free's coefficients
        matrixX2 = new double[SIZE][1];
        matrixX1 = new double[SIZE][1];
        for (int i = 0; i < SIZE; i++) {
            matrixX2[i][0] = matrixB[i][0] / matrixA[i][i];
        }
    }

    public static void iteration() {
        for (int i = 0; i < SIZE; i++) {
            matrixX1[i][0] = matrixX2[i][0];
        }

        double sumOther;
        for (int i = 0; i < SIZE; i++) {
            sumOther = 0;

            for (int j = 0; j < SIZE; j++) {
                if (j < i) {
                    sumOther += matrixA[i][j] * matrixX2[j][0] / matrixA[i][i];
                } else if (j == i) {
                } else {
                    sumOther += matrixA[i][j] * matrixX1[j][0] / matrixA[i][i];
                }
            }

            matrixX2[i][0] = matrixB[i][0] / matrixA[i][i] - sumOther;
        }
    }

    public static boolean checkAllNewX() {
        for (int i = 0; i < SIZE; i++) {
            if (Math.abs(matrixX2[i][0] - matrixX1[i][0]) > epsilon) {
                return false;
            }
            //if (Math.abs(matrixX2[i][0]))
        }
        return true;
    }

    public static void startComputed() {
        int count = 0;

        while (true) {
            iteration();
            count++;
            if (checkAllNewX() || count >= M) {
                break;
            }
        }

        System.out.println("\nПосле всех итераций");
        for (int i = 0; i < SIZE; i++) {
            System.out.println("X" + (i + 1) + " = " + matrixX2[i][0]);
        }

        if (count >= M) {
            System.out.println("\nИтерации не сходятся(на заданном максимальном их количестве)");
        } else {
            System.out.println("\nОбщее количество проделанных итераций = " + count + "\n");
        }

        for (int i = 0; i < SIZE; i++) {
            System.out.println("вектор погрешности вектора Х_" + (i + 1) + " = " + Math.abs(matrixX2[i][0] - matrixX1[i][0]));
        }
    }
}
