import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class UserInteraction {
    private final Scanner scanner = new Scanner(System.in);

    public void interact() {
        System.out.println("Liner system solution finder ");
        LinearSystem system = askForReadingMethod();

        if (system == null) {
            System.err.println("Cannot read from file");
            return;
        }

        try {
            system.solveUsingGaussSeidelMethod();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private LinearSystem askForReadingMethod() {
        int selection = 0;
        while (selection != 1 && selection != 2) {
            try {
                System.out.println("Please, enter number corresponding to method of getting linear system (only number, without any other symbols):");
                System.out.println("1.\tInteractive input");
                System.out.println("2.\tFrom a file");
                selection = Integer.parseInt(scanner.next());
                scanner.skip("\n");

                switch (selection) {
                    case 1:
                        return askUserInteractively();
                    case 2:
                        return readLinearSystemFromFile();
                    default:
                        System.out.println("Unknown method");
                }
            } catch (Exception e) {
                System.out.println("Not a number.");
            }
        }

        return null;
    }

    private double[] parseDoubleArray() {
        String userInput = scanner.nextLine();
        userInput = userInput.strip();
        String[] splittedUserInput = userInput.split(" ");
        return Arrays.stream(splittedUserInput).mapToDouble(Double::parseDouble).toArray();
    }

    private double[] parseDoubleArray(BufferedReader reader) throws IOException {
        String userInput = reader.readLine();
        userInput = userInput.strip();
        String[] splittedUserInput = userInput.split(" ");
        return Arrays.stream(splittedUserInput).mapToDouble(Double::parseDouble).toArray();
    }

    private LinearSystem askUserInteractively() {
        int size = 0;
        while (size > 20 || size <= 1) {
            try {
                System.out.println("Please enter matrix size [2-20]:");
                size = Integer.parseInt(scanner.next());
                scanner.skip("\n");
            } catch (Exception e) {
                System.out.println("not a number");
            }
        }

        double[][] matrix = new double[size][size];

        for (int i = 0; i < size; ++i) {
            while (true) {
                try {
                    System.out.printf("Enter %d row of the matrix (numbers separated by spaces, e.g 1 2 3):", i + 1);
                    double[] numbers = parseDoubleArray();

                    if (numbers.length != size) {
                        System.out.println("The number of elements doesn't matches the size of matrix");
                        continue;
                    }

                    matrix[i] = numbers;
                    break;
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }

        double[] freeMembers;

        while (true) {
            try {
                System.out.println("Enter vector of free members (numbers separated by spaces, e.g 1 2 3)");
                double[] numbers = parseDoubleArray();

                if (numbers.length != size) {
                    System.out.println("The number of elements doesn't matches the size of matrix");
                    continue;
                }

                freeMembers = numbers;
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        int iterations = 0;

        while (iterations == 0) {
            try {
                System.out.println("Please enter iterations count:");
                iterations = Integer.parseInt(scanner.next());
                scanner.skip("\n");
            } catch (Exception e) {
                System.out.println("Not a number.");
            }
        }

        double accuracy = 1;

        while (accuracy >= 1 || accuracy <= 0.00001) {
            try {
                System.out.println("Please enter accuracy [0.00001; 1]:");
                accuracy = Double.parseDouble(scanner.next());
                scanner.skip("\n");
            } catch (Exception e) {
                System.out.println("Not a number.");
            }
        }

        return new LinearSystem(matrix, freeMembers, size, size, iterations, accuracy);
    }

    private LinearSystem readLinearSystemFromFile() {
        System.out.println("Please enter file name (file name should be without any white characters:");
        String filename = scanner.next();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            int size = Integer.parseInt(reader.readLine());
            if (size > 20 || size <= 1) {
                System.err.println("File: invalid matrix size") ;
                return null;
            }

            double[][] matrix = new double[size][size];

            for (int i = 0; i < size; ++i) {
                double[] numbers = parseDoubleArray(reader);
                if (numbers.length != size) {
                    System.out.println("File: the number of elements doesn't matches the size of matrix");
                    return null;
                }
                matrix[i] = numbers;
            }

            double[] freeMembers = parseDoubleArray(reader);

            if (freeMembers.length != size) {
                System.out.println("The number of elements doesn't matches the size of matrix");
                return null;
            }
            int iterations = Integer.parseInt(reader.readLine());
            double accuracy = Double.parseDouble(reader.readLine());

            return new LinearSystem(matrix, freeMembers, size, size, iterations, accuracy);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}
