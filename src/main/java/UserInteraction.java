import java.util.Arrays;
import java.util.Scanner;

public class UserInteraction {
    private final Scanner scanner = new Scanner(System.in);

    public void interact() {
        System.out.println("Liner system solution finder ");
        LinearSystem system = askForReadingMethod();
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
                        System.out.println("Invalid option");
                }
            } catch (Exception e) {
                System.out.println("not a number");
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
                System.out.println("not a number");
            }
        }

        double accuracy = .0;

        while (accuracy == .0) {
            try {
                System.out.println("Please enter accuracy:");
                accuracy = Double.parseDouble(scanner.next());
                scanner.skip("\n");
            } catch (Exception e) {
                System.out.println("not a number");
            }
        }

        return new LinearSystem(matrix, freeMembers, size, size, iterations, accuracy);
    }

    private LinearSystem readLinearSystemFromFile() {
        return null;
    }
}
