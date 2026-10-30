# Experiment 06 — RMI Calculator (Addition, Subtraction, Multiplication)

## Question / Problem Statement

Write a Java RMI application in which the client invokes remote methods on the server to perform addition, subtraction and multiplication of two numbers.

## Aim

To implement distributed computing using Java Remote Method Invocation (RMI).

## Concepts Used

- Remote interface (`extends Remote`, methods `throws RemoteException`)
- Remote object implementation (`extends UnicastRemoteObject`)
- `LocateRegistry.createRegistry()`, `registry.rebind()`, `registry.lookup()`
- Client stub invocation

## Files

| File | Description |
|------|-------------|
| `Experiment06_RmiCalculator.java` | Complete source code |
| `README.md` | This file |
| `output/` | Program output / screenshots |

## How to Compile and Run

Use **two terminals**. Start the server first (it creates the registry on port 1099 itself — no separate `rmiregistry` needed).

```bash
javac Experiment06_RmiCalculator.java

# Terminal 1
java Experiment06_RmiCalculator server

# Terminal 2
java Experiment06_RmiCalculator client
```

## Program Logic

- `CalculatorService` — remote interface with `addition`, `subtraction`, `multiply`.
- `CalculatorServiceImpl` — implementation, logs each call on the server console.
- **Server** creates/gets the registry on port 1099 and binds the service as `CalculatorService`.
- **Client** looks up the stub and shows a menu: Addition / Subtraction / Multiply / All three / Exit.

## Output (captured from an actual run)

**Terminal 1 — Server**

```text

[RMI SERVER] Initializing server on port 1099...
[RMI SERVER] RMI Registry created on port 1099
[RMI SERVER] Service 'CalculatorService' bound and ready.
[RMI SERVER] Awaiting remote client invocations...
[RMI SERVER EXECUTING] addition(10.00, 5.00)
[RMI SERVER EXECUTING] subtraction(10.00, 5.00)
[RMI SERVER EXECUTING] multiply(10.00, 5.00)
[RMI SERVER EXECUTING] addition(12.50, 2.00)
[RMI SERVER EXECUTING] subtraction(12.50, 2.00)
[RMI SERVER EXECUTING] multiply(12.50, 2.00)
```

**Terminal 2 — Client**

```text

[RMI CLIENT] Connecting to RMI Registry on localhost:1099...
[RMI CLIENT] Remote CalculatorService stub acquired successfully!

----------------------------------------------
Select Remote Operation:
  1. Addition    (a + b)
  2. Subtraction (a - b)
  3. Multiply    (a * b)
  4. Execute All Three Operations
  5. Exit Client
Enter choice (1-5): 1
Enter number a: 10
Enter number b: 5

[INVOKING REMOTE METHOD VIA NETWORK...]
>>> REMOTE RESULT: 10.00 + 5.00 = 15.00
----------------------------------------------
Select Remote Operation:
  1. Addition    (a + b)
  2. Subtraction (a - b)
  3. Multiply    (a * b)
  4. Execute All Three Operations
  5. Exit Client
Enter choice (1-5): 2
Enter number a: 10
Enter number b: 5

[INVOKING REMOTE METHOD VIA NETWORK...]
>>> REMOTE RESULT: 10.00 - 5.00 = 5.00
----------------------------------------------
Select Remote Operation:
  1. Addition    (a + b)
  2. Subtraction (a - b)
  3. Multiply    (a * b)
  4. Execute All Three Operations
  5. Exit Client
Enter choice (1-5): 3
Enter number a: 10
Enter number b: 5

[INVOKING REMOTE METHOD VIA NETWORK...]
>>> REMOTE RESULT: 10.00 * 5.00 = 50.00
----------------------------------------------
Select Remote Operation:
  1. Addition    (a + b)
  2. Subtraction (a - b)
  3. Multiply    (a * b)
  4. Execute All Three Operations
  5. Exit Client
Enter choice (1-5): 4
Enter number a: 12.5
Enter number b: 2

[INVOKING REMOTE METHOD VIA NETWORK...]
=== COMPREHENSIVE REMOTE RESULTS ===
Addition       : 12.50 + 2.00 = 14.50
Subtraction    : 12.50 - 2.00 = 10.50
Multiplication : 12.50 * 2.00 = 25.00
----------------------------------------------
Select Remote Operation:
  1. Addition    (a + b)
  2. Subtraction (a - b)
  3. Multiply    (a * b)
  4. Execute All Three Operations
  5. Exit Client
Enter choice (1-5): 5
[RMI CLIENT] Exiting. Goodbye!
```

## Result

The client invoked the three remote methods and the server executed and logged each one.
