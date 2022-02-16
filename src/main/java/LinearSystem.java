public class LinearSystem {
    private Matrix coefficients;
    private Matrix freeMembers;
    private final int iterations;
    private final double accuracy;

    public LinearSystem(double[][] coefs, double[] freeMembrs, int unknownNum, int freeNum, int iterations, double accuracy) {
        this.iterations = iterations;
        this.accuracy = accuracy;
        coefficients = new Matrix(coefs, unknownNum, freeNum);
        freeMembers = new Matrix(freeNum, 1);
        try {
            for (int i = 0; i < freeMembers.getRowCount(); ++i) {
                freeMembers.set(i, 0, freeMembrs[i]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void solveUsingGaussSeidelMethod() {
        try {
            int [] rowOrder = coefficients.checkDiagonal();
            if (rowOrder == null) throw new Exception("cant solve");
            coefficients = coefficients.changeRows(rowOrder);
            freeMembers = freeMembers.changeRows(rowOrder);
            Matrix C = new Matrix(coefficients.getRowCount(), coefficients.getColumnCount());
            Matrix D = new Matrix(freeMembers.getRowCount(), freeMembers.getColumnCount());

            double[] diagonals = coefficients.getDiagonalElements();

            for (int i = 0; i < C.getRowCount(); ++i) {
                for (int j = 0; j < C.getColumnCount(); ++j) {
                    if (i == j) {
                        C.set(i, j, 0);
                        continue;
                    }
                    C.set(i, j, -coefficients.get(i, j) / diagonals[i]);
                }
            }


            for (int i = 0; i < D.getRowCount(); ++i) {
                D.set(i, 0, freeMembers.get(i, 0) / diagonals[i]);
            }

            Matrix iter = D.copy();
            Matrix errors = new Matrix(iter.getRowCount(), iter.getColumnCount());
            double delta;
            int k = 0;
            do {
                if (k > iterations) {
                    System.out.println("Итерации расходятся");
                    return;
                }
                delta = 0;
                for (int i = 0; i < C.getRowCount(); ++i) {
                    double sum = 0;
                    for (int j = 0; j < C.getRowCount(); ++j) {
                        if (i != j) sum += C.get(i, j) * iter.get(j, 0);
                    }

                    double x = (D.get(i, 0) + sum);
                    double d = Math.abs(iter.get(i, 0) - x);
                    errors.set(i, 0, d);
                    iter.set(i, 0, x);
                    if (d > delta) delta = d;

                }
                k++;
            } while (delta >= accuracy);

            System.out.println("Vector of solutions:");
            iter.printMatrix();
            System.out.println("Iterations count: " + k);
            System.out.println("Err: " + errors.getMaxElement());


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
