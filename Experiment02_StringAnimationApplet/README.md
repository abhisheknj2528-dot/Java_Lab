# Experiment 02 — Applet String Animation using Multithreading

## Question / Problem Statement

Write an applet that animates a string (scrolling banner) across the applet window using multithreading.

## Aim

To create an animated moving text banner in an applet using a separate thread.

## Concepts Used

- `Applet` life cycle: `init()`, `start()`, `stop()`, `destroy()`, `paint()`
- `Runnable` interface and `Thread`
- `Thread.sleep()` for frame timing, `repaint()`
- `Graphics`, `Font`, `FontMetrics`, `Color`
- `volatile` flag to stop the thread safely

## Files

| File | Description |
|------|-------------|
| `Experiment02_StringAnimationApplet.java` | Complete source code |
| `README.md` | This file |
| `output/` | Program output / screenshots |

## How to Compile and Run

```bash
javac Experiment02_StringAnimationApplet.java
java Experiment02_StringAnimationApplet
```

> **Note about applets:** browsers and `appletviewer` no longer support applets (appletviewer was removed in JDK 11).
> This program therefore includes a `main()` that hosts the applet inside a `JFrame`, so it runs with a plain
> `java` command. Applets need a JDK version that still ships `java.applet` (JDK 25 or earlier;
> the Applet API is removed in newer JDKs).

## Program Logic

- `init()` sets the colours, font and the starting x-position (off-screen left).
- `start()` creates and starts the worker thread `StringAnimationWorker`.
- `run()` adds 4 px to the x-position every ~33 ms (≈30 FPS) and calls `repaint()`.
  When the text leaves the right edge it re-enters from the left (width measured with `FontMetrics`).
- `paint()` draws the header bar, a white banner box, the moving string and a status line.
- `stop()` clears the flag and interrupts the thread.

## Expected Output

A 750×350 window titled **"University Lab - Exp 02: Applet String Animation"** containing:

- A dark header bar: `EXPERIMENT 02: MULTITHREADED APPLET STRING ANIMATION`
- A white rounded box in which the blue text
  `Welcome to Java Programming Lab! Multithreaded String Animation` scrolls from left to right continuously
- A status line at the bottom: `Worker Thread: StringAnimationWorker | X-Coord: <changing value> px`

## Screenshot

Run the program and paste your own screenshot(s) in the `output/` folder (e.g. `output/screenshot1.png`).
The window could not be captured automatically because this environment has no display; the expected output above
is derived directly from the program's code.
