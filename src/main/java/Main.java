import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        double[][] elems1 = {{1, 2, 1}, {0, 1, 2}};
        double[][] elems2 = {{1, 0}, {0, 1}, {1, 1}};
        double[][] elem1 = {{1, -3, 2}, {4, -2, 1}, {-1, 2, 4}};
        Matrix matrix1 = new Matrix(elem1, 3, 3);
        Matrix matrix2 = new Matrix(elems1, 2, 3);
        try {
            //resultMatrix.printMatrix();
            System.out.println(Arrays.toString(matrix1.checkDiagonal()));
            matrix1.printMatrix();
            matrix1 = matrix1.changeRows(matrix1.checkDiagonal());
            matrix1.printMatrix();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }


        Matrix c = new Matrix(3, 3);
        for (int i = 0; i < c.getRowCount(); ++i) {
            for (int j = 0; j < c.getColumnCount(); ++j) {
                if (i != j) {

                }
            }
        }
    }
}
