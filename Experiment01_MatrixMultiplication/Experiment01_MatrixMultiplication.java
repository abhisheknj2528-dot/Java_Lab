import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class Experiment01_MatrixMultiplication {

    public static void main(String[] args) {

        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);

        try {

            System.out.print("Enter number of rows in Matrix A (r1): ");
            int r1 = Integer.parseInt(br.readLine().trim());

            System.out.print("Enter number of columns in Matrix A (c1): ");
            int c1 = Integer.parseInt(br.readLine().trim());

            System.out.print("Enter number of rows in Matrix B (r2): ");
            int r2 = Integer.parseInt(br.readLine().trim());

            System.out.print("Enter number of columns in Matrix B (c2): ");
            int c2 = Integer.parseInt(br.readLine().trim());

            if (c1 != r2) {
                System.err.println("\n[ERROR] Matrix multiplication is NOT possible!");
                System.err.println("Condition: Columns of Matrix A (" + c1 + ") must equal Rows of Matrix B (" + r2 + ").");
                return;
            }

            int[][] matrixA = new int[r1][c1];
            int[][] matrixB = new int[r2][c2];
            int[][] resultMatrix = new int[r1][c2];

            System.out.println("\nEnter elements of Matrix A (" + r1 + "x" + c1 + "):");
            for (int i = 0; i < r1; i++) {
                for (int j = 0; j < c1; j++) {
                    System.out.print("A[" + i + "][" + j + "]: ");
                    matrixA[i][j] = Integer.parseInt(br.readLine().trim());
                }
            }

            System.out.println("\nEnter elements of Matrix B (" + r2 + "x" + c2 + "):");
            for (int i = 0; i < r2; i++) {
                for (int j = 0; j < c2; j++) {
                    System.out.print("B[" + i + "][" + j + "]: ");
                    matrixB[i][j] = Integer.parseInt(br.readLine().trim());
                }
            }

            for (int i = 0; i < r1; i++) {
                for (int j = 0; j < c2; j++) {
                    resultMatrix[i][j] = 0;
                    for (int k = 0; k < c1; k++) {
                        resultMatrix[i][j] += matrixA[i][k] * matrixB[k][j];
                    }
                }
            }

            System.out.println("\n--- Matrix A (" + r1 + "x" + c1 + ") ---");
            displayMatrix(matrixA);

            System.out.println("--- Matrix B (" + r2 + "x" + c2 + ") ---");
            displayMatrix(matrixB);

            System.out.println("--- Product Matrix C = A x B (" + r1 + "x" + c2 + ") ---");
            displayMatrix(resultMatrix);

            System.out.println("==================================================");
            System.out.println("Matrix multiplication finished successfully.");

        } catch (NumberFormatException e) {
            System.err.println("[ERROR] Invalid numeric input: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("[ERROR] I/O Exception occurred: " + e.getMessage());
        }
    }

    private static void displayMatrix(int[][] mat) {
        for (int[] row : mat) {
            for (int val : row) {
                System.out.printf("%6d", val);
            }
            System.out.println();
        }
        System.out.println();
    }
}
