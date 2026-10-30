# Experiment 01 — Matrix Multiplication

## Question / Problem Statement

Write a Java program to read two matrices from the user and multiply them. Display both input matrices and the product matrix. Show an error if the matrices cannot be multiplied.

## Aim

To perform matrix multiplication in Java using two-dimensional arrays and console input.

## Concepts Used

- 2-D arrays
- `BufferedReader` / `InputStreamReader` for console input
- Nested loops
- Exception handling (`NumberFormatException`, `IOException`)
- Condition for multiplication: columns of A = rows of B

## Files

| File | Description |
|------|-------------|
| `Experiment01_MatrixMultiplication.java` | Complete source code |
| `README.md` | This file |
| `output/` | Program output / screenshots |

## How to Compile and Run

```bash
javac Experiment01_MatrixMultiplication.java
java Experiment01_MatrixMultiplication
```

## Program Logic

1. Read the order of Matrix A (r1 × c1) and Matrix B (r2 × c2).
2. If `c1 != r2` print an error and stop.
3. Read all elements of A and B.
4. Compute `C[i][j] = Σ A[i][k] × B[k][j]` using three nested loops.
5. Display A, B and the product C.

## Output (captured from an actual run)

**Run 1 — valid input (2×3 multiplied by 3×2)**

```text
Enter number of rows in Matrix A (r1): 2
Enter number of columns in Matrix A (c1): 3
Enter number of rows in Matrix B (r2): 3
Enter number of columns in Matrix B (c2): 2

Enter elements of Matrix A (2x3):
A[0][0]: 1
A[0][1]: 2
A[0][2]: 3
A[1][0]: 4
A[1][1]: 5
A[1][2]: 6

Enter elements of Matrix B (3x2):
B[0][0]: 7
B[0][1]: 8
B[1][0]: 9
B[1][1]: 10
B[2][0]: 11
B[2][1]: 12

--- Matrix A (2x3) ---
     1     2     3
     4     5     6

--- Matrix B (3x2) ---
     7     8
     9    10
    11    12

--- Product Matrix C = A x B (2x2) ---
    58    64
   139   154

==================================================
Matrix multiplication finished successfully.
```

**Run 2 — invalid input (A is 2×3 and B is 2×2, so columns of A ≠ rows of B)**

```text
Enter number of rows in Matrix A (r1): 2
Enter number of columns in Matrix A (c1): 3
Enter number of rows in Matrix B (r2): 2
Enter number of columns in Matrix B (c2): 2

[ERROR] Matrix multiplication is NOT possible!
Condition: Columns of Matrix A (3) must equal Rows of Matrix B (2).
```

## Result

The program multiplies two matrices correctly and rejects incompatible dimensions.
