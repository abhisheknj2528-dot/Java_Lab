# Experiment 10 — JavaBean for Addition of Two Numbers

## Question / Problem Statement

Create a JavaBean that adds two numbers. The bean must have properties, getters/setters, be serializable, and notify listeners when a property changes. Demonstrate it with a test program.

## Aim

To create and test a reusable JavaBean component.

## Concepts Used

- JavaBean conventions: public no-arg constructor, private properties, getters/setters
- `Serializable` (`ObjectOutputStream` / `ObjectInputStream`)
- Bound properties: `PropertyChangeSupport`, `PropertyChangeListener`
- Introspection: `Introspector`, `BeanInfo`, `PropertyDescriptor`

## Files

| File | Description |
|------|-------------|
| `Experiment10_JavaBeanAddition.java` | Complete source code |
| `README.md` | This file |
| `output/` | Program output / screenshots |

## How to Compile and Run

```bash
javac Experiment10_JavaBeanAddition.java
java Experiment10_JavaBeanAddition
```

## Program Logic

- `AdditionBean` has properties `firstNumber`, `secondNumber`, `sum`. Setting either number fires a property-change event and recalculates `sum`.
- `main()` creates the bean, registers a listener, sets values, reads them back, introspects the bean and then verifies serialization (save → restore → delete file).

## Output (captured from an actual run)

```text
==================================================
   EXPERIMENT 10: JAVABEAN COMPONENT DEMONSTRATION
==================================================

[1] Instantiating AdditionBean via Default Constructor...

[2] Setting firstNumber to 45.5...
    -> Event: Property 'firstNumber' mutated: 0.0 -> 45.5
    -> Event: Property 'sum' mutated: 0.0 -> 45.5

[3] Setting secondNumber to 24.5...
    -> Event: Property 'secondNumber' mutated: 0.0 -> 24.5
    -> Event: Property 'sum' mutated: 45.5 -> 70.0

[4] Accessing Bean Properties via Getters:
    First Number  : 45.5
    Second Number : 24.5
    Calculated Sum: 70.0
    Full State    : AdditionBean [firstNumber=45.5, secondNumber=24.5, sum=70.0]

[5] JavaBeans Introspection (Dynamic Component Analysis):
    Discovered Component Properties:
     - Property: firstNumber     [Type: double, Getter: getFirstNumber, Setter: setFirstNumber]
     - Property: secondNumber    [Type: double, Getter: getSecondNumber, Setter: setSecondNumber]
     - Property: sum             [Type: double, Getter: getSum, Setter: setSum]

[6] Serialization Test (Verifying Serializable Contract):
    Saved bean state to AdditionBean_State.ser
    Restored bean from file: AdditionBean [firstNumber=45.5, secondNumber=24.5, sum=70.0]
    Serialization verification passed.

==================================================
JavaBean Addition Component verified successfully.
==================================================
```

## Result

The JavaBean was created, its bound properties fired events, it was introspected successfully and survived serialization.
