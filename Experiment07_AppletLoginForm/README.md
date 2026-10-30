# Experiment 07 — Applet Login Form

## Question / Problem Statement

Write an applet that takes a user's name and password as input and validates that both fields are filled when the Submit button is clicked.

## Aim

To design a simple form in an applet using AWT components and handle button events.

## Concepts Used

- `Applet`, `Label`, `TextField`, `Button`, `Panel`
- `GridBagLayout` and `GridBagConstraints`
- `setEchoChar('*')` for password masking
- `ActionListener` event handling

## Files

| File | Description |
|------|-------------|
| `Experiment07_AppletLoginForm.java` | Complete source code |
| `README.md` | This file |
| `output/` | Program output / screenshots |

## How to Compile and Run

```bash
javac Experiment07_AppletLoginForm.java
java Experiment07_AppletLoginForm
```

> **Note about applets:** browsers and `appletviewer` no longer support applets (appletviewer was removed in JDK 11).
> This program therefore includes a `main()` that hosts the applet inside a `JFrame`, so it runs with a plain
> `java` command. Applets need a JDK version that still ships `java.applet` (JDK 25 or earlier;
> the Applet API is removed in newer JDKs).

## Expected Output

A 520×360 window titled **"University Lab - Exp 07: Applet Login Form"** showing the heading `USER AUTHENTICATION FORM`,
fields *Username / Name* and *Password* (masked with `*`), buttons **Submit** and **Clear**, and a status line.

| Action | Status line shown |
|--------|-------------------|
| Window opens | `Please enter credentials and click Submit.` |
| Click **Submit** with empty fields | `Validation Error: Both Name and Password are required!` (red) |
| Name `Rahul`, Password `secret123`, click **Submit** | `Form submitted successfully! Welcome, Rahul.` (green) |
| Click **Clear** | `Fields cleared.` (fields emptied) |

## Screenshot

Run the program and paste your own screenshot(s) in the `output/` folder (e.g. `output/screenshot1.png`).
The window could not be captured automatically because this environment has no display; the expected output above
is derived directly from the program's code.
