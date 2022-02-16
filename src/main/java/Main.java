import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        double[][] elems1 = {{1, 2, 1}, {0, 1, 2}};
        double[][] elems2 = {{1, 0}, {0, 1}, {1, 1}};
        double[][] elem1 = {{2, 2, 10}, {10, 1, 1}, {2, 10, 1}};
        double[] free = {14, 12, 13};
        Matrix matrix1 = new Matrix(elem1, 3, 3);
        Matrix matrix2 = new Matrix(elems1, 2, 3);
        try {
            //resultMatrix.printMatrix();
            //System.out.println(Arrays.toString(matrix1.checkDiagonal()));
            //matrix1.printMatrix();
            //matrix1 = matrix1.changeRows(matrix1.checkDiagonal());
            //matrix1.printMatrix();
            LinearSystem system = new LinearSystem(elem1, free, 3, 3);
            system.solveUsingGaussSeidelMethod(0.03, 10);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

    }
}
