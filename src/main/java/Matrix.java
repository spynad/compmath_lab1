public class Matrix {
    private double[][] elements;
    private final int rowCount;
    private final int columnCount;

    public Matrix(int row, int column) {
        elements = new double[row][column];
        this.rowCount = row;
        this.columnCount = column;
    }

    public Matrix(double[][] elements, int row, int column) {
        this.elements = elements;
        this.rowCount = row;
        this.columnCount = column;
    }

    public Matrix addMatrices(Matrix b) throws Exception {
        Matrix a = this;
        if ((a.getColumnCount() != b.getColumnCount()) && (a.getRowCount() != b.getRowCount())) {
            throw new Exception("Matrices cannot be added to each other: different sizes");
        }
        //the matrices are squared, so we can use any dimension
        int size = a.getRowCount();

        double[][] resultSums = new double[size][size];
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                resultSums[i][j] = a.elements[i][j] + b.elements[i][j];
            }
        }
        return new Matrix(resultSums, size, size);
    }

    public Matrix subtractMatrices(Matrix b) throws Exception {
        Matrix a = this;
        if ((a.getColumnCount() != b.getColumnCount()) && (a.getRowCount() != b.getRowCount())) {
            throw new Exception("Matrices cannot be added to each other: different sizes");
        }
        //the matrices are squared, so we can use any dimension
        int size = a.getRowCount();

        double[][] resultSums = new double[size][size];
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                resultSums[i][j] = a.elements[i][j] - b.elements[i][j];
            }
        }
        return new Matrix(resultSums, size, size);
    }

    public Matrix multiplyMatrices(Matrix b) throws Exception{
        Matrix a = this;
        if (a.getColumnCount() != b.getRowCount()) {
            throw new Exception("Matrices cannot be multiplied: a matrix column count is different from b matrix row count");
        }

        double[][] resultMul = new double[a.getRowCount()][b.getColumnCount()];
        for (int i = 0; i < a.getRowCount(); ++i) {
            for (int j = 0; j < b.getColumnCount(); ++j) {
                for (int k = 0; k < a.getColumnCount(); ++k) {
                    resultMul[i][j] += a.elements[i][k] * b.elements[k][j];
                }
            }
        }

        return new Matrix(resultMul, a.getRowCount(), b.getColumnCount());
    }

    public int[] checkDiagonal() {
        int[] rowOrder = new int[this.getRowCount()];
        boolean[] uniqueRows = new boolean[this.getRowCount()];
        boolean flag = false;

        for (int i = 0; i < this.getRowCount(); ++i) {
            double sum = 0;
            int maxIndex = 0;
            for (int j = 0; j < this.getColumnCount(); ++j) {
                sum += Math.abs(elements[i][j]);

                if (Math.abs(elements[i][j]) > Math.abs(elements[i][maxIndex])) {
                    maxIndex = j;
                }
            }
            if (Math.abs(elements[i][maxIndex]) >= sum - Math.abs(elements[i][maxIndex]) && !uniqueRows[maxIndex]) {
                if (elements[i][maxIndex] > sum - Math.abs(elements[i][maxIndex])) flag = true;
                uniqueRows[maxIndex] = true;
                rowOrder[maxIndex] = i;
            } else return null;
        }

        return flag ? rowOrder : null;
    }

    public Matrix changeRows(int[] rowOrder) {
        Matrix result = new Matrix(this.getRowCount(), this.getColumnCount());
        for (int i = 0; i < rowOrder.length; ++i) {
            for (int j = 0; j < rowOrder.length; ++j) {
                if (i == rowOrder[j]) {
                    result.elements[j] = this.elements[i];
                    break;
                }
            }
        }

        return result;
    }

    public int getColumnCount() {
        return columnCount;
    }

    public int getRowCount() {
        return rowCount;
    }

    public void printMatrix() {
        for (int i = 0; i < this.getRowCount(); ++i) {
            for (int j = 0; j < this.getColumnCount(); ++j) {
                System.out.print(elements[i][j] + "\t");
            }
            System.out.print("\n");
        }
    }

    public void set(int i, int j, double num) throws Exception {
        if (i >= getRowCount() || j >= getColumnCount()) throw new Exception("wrong index");
        elements[i][j] = num;
    }

    public double get(int i, int j) throws Exception {
        if (i >= getRowCount() || j >= getColumnCount()) throw new Exception("wrong index");
        return elements[i][j];
    }

    public double[] getDiagonalElements() {
        double[] diagonals = new double[getRowCount()];
        for (int i = 0; i < getRowCount(); ++i) {
            diagonals[i] = elements[i][i];
        }

        return diagonals;
    }

    public Matrix copy() {
        Matrix newMatrix = new Matrix(getRowCount(), getColumnCount());
        for (int i = 0; i < getRowCount(); ++i) {
            if (getColumnCount() >= 0) System.arraycopy(elements[i], 0, newMatrix.elements[i], 0, getColumnCount());
        }

        return newMatrix;
    }

    public double[][] getElements() {
        return elements;
    }

    public double getMaxElement() {
        double max = Double.MIN_VALUE;
        for (int i = 0; i < getRowCount(); ++i) {
            for (int j = 0; j < getColumnCount(); ++j) {
                if (elements[i][j] > max) max = elements[i][j];
            }
        }
        return max;
    }
}
