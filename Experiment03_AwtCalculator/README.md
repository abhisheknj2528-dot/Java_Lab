# Experiment 03 — AWT Calculator

## Question / Problem Statement

Design a simple calculator using AWT components (Frame, Button, TextField) that performs addition, subtraction, multiplication, division and modulus.

## Aim

To build a GUI calculator using the AWT package and event handling.

## Concepts Used

- `Frame`, `Panel`, `Button`, `TextField`
- Layout managers: `BorderLayout`, `GridLayout`
- Event handling: `ActionListener`, `WindowAdapter`
- `switch` on strings, `double` arithmetic, divide-by-zero handling

## Files

| File | Description |
|------|-------------|
| `Experiment03_AwtCalculator.java` | Complete source code |
| `README.md` | This file |
| `output/` | Program output / screenshots |

## How to Compile and Run

```bash
javac Experiment03_AwtCalculator.java
java Experiment03_AwtCalculator
```

## Program Logic

- A 5×4 grid of buttons: `C CE % /`, `7 8 9 *`, `4 5 6 -`, `1 2 3 +`, `+/- 0 . =`.
- Digits/`.` build the current number in the display.
- An operator stores the first operand; `=` computes the result using `calculate()`.
- `C` clears everything, `CE` deletes the last digit, `+/-` toggles the sign.
- Dividing by zero shows `Error: Div by 0` and locks the keypad until `C` is pressed.

## Expected Output

A 360×460 calculator window titled **"University Lab - Exp 03: AWT Calculator"** with the display showing `0`.

| Key presses | Display |
|-------------|---------|
| `1` `2` `+` `8` `=` | `20` |
| `9` `*` `7` `=` | `63` |
| `5` `/` `2` `=` | `2.5` |
| `1` `0` `%` `3` `=` | `1` |
| `5` `/` `0` `=` | `Error: Div by 0` |
| `7` `+/-` | `-7` |

## Screenshot

Run the program and paste your own screenshot(s) in the `output/` folder (e.g. `output/screenshot1.png`).
The window could not be captured automatically because this environment has no display; the expected output above
is derived directly from the program's code.
