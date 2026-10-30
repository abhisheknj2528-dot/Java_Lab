# Experiment 11 — Applet Form with Name and Password

## Question / Problem Statement

Write an applet with a form containing Name and Password fields, a Show Password check box, and Submit / Reset buttons.

## Aim

To design an applet form that handles both button (`ActionListener`) and check box (`ItemListener`) events.

## Concepts Used

- `Applet`, `TextField`, `Checkbox`, `Button`, `Label`
- `ActionListener` and `ItemListener`
- Toggling the echo character: `setEchoChar('*')` / `setEchoChar((char) 0)`
- `GridBagLayout`
- Class fields are `protected` so Experiment 12 can reuse the same form idea

## Files

| File | Description |
|------|-------------|
| `Experiment11_AppletForm.java` | Complete source code |
| `README.md` | This file |
| `output/` | Program output / screenshots |

## How to Compile and Run

```bash
javac Experiment11_AppletForm.java
java Experiment11_AppletForm
```

> **Note about applets:** browsers and `appletviewer` no longer support applets (appletviewer was removed in JDK 11).
> This program therefore includes a `main()` that hosts the applet inside a `JFrame`, so it runs with a plain
> `java` command. Applets need a JDK version that still ships `java.applet` (JDK 25 or earlier;
> the Applet API is removed in newer JDKs).

## Expected Output

A 580×400 window titled **"University Lab - Exp 11: Applet Form"** with the heading `USER CREDENTIALS APPLET FORM`,
*Account Name / Username*, *Password* (masked), a **Show Password** check box, buttons **Submit** and **Reset**, and a status line.

| Action | Result |
|--------|--------|
| Window opens | Status: `Ready. Enter Name and Password.` |
| Type password `secret123` | Shown as `*********` |
| Tick **Show Password** | Password becomes visible: `secret123` |
| Click **Submit** with an empty field | `Validation Failed: Both Name and Password are required!` (red) |
| Name `Rahul` + password → **Submit** | `Form submitted successfully for: Rahul` (green) |
| Click **Reset** | Fields cleared, check box unticked, status `Form reset.` |

## Screenshot

Run the program and paste your own screenshot(s) in the `output/` folder (e.g. `output/screenshot1.png`).
The window could not be captured automatically because this environment has no display; the expected output above
is derived directly from the program's code.
