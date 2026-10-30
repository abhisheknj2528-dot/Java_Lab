# Experiment 05 — Notepad using Swing

## Question / Problem Statement

Develop a Notepad application using Swing with File, Edit, Format and Help menus, supporting file open/save, cut/copy/paste, word wrap, zoom and a status bar.

## Aim

To create a text editor using Swing components, menus and file I/O.

## Concepts Used

- `JFrame`, `JTextArea`, `JScrollPane`, `JLabel`
- `JMenuBar`, `JMenu`, `JMenuItem`, `JCheckBoxMenuItem`, keyboard accelerators
- `JFileChooser`, `FileNameExtensionFilter`, `JOptionPane`
- `CaretListener` for the live status bar
- File I/O with `BufferedReader` / `BufferedWriter`

## Files

| File | Description |
|------|-------------|
| `Experiment05_SwingNotepad.java` | Complete source code |
| `README.md` | This file |
| `output/` | Program output / screenshots |

## How to Compile and Run

```bash
javac Experiment05_SwingNotepad.java
java Experiment05_SwingNotepad
```

## Features

| Menu | Items |
|------|-------|
| **File** | New, Open…, Save, Save As…, Exit (with *Save changes?* prompt) |
| **Edit** | Cut, Copy, Paste, Select All, Time/Date |
| **Format** | Word Wrap (toggle), Zoom In, Zoom Out |
| **Help** | About Notepad |

Shortcuts: Ctrl+N, Ctrl+O, Ctrl+S, Ctrl+X, Ctrl+C, Ctrl+V, Ctrl+A (Cmd on macOS).

## Expected Output

An 850×600 window titled **"Untitled - Java Swing Notepad"** with an empty text area and a status bar at the bottom right:
`Line: 1 | Col: 1 | Chars: 0`.

- Typing updates the status bar live, e.g. `Line: 3 | Col: 12 | Chars: 98`.
- *Save As…* adds `.txt` automatically and the title changes to `<filename>.txt - Java Swing Notepad`.
- *Time/Date* inserts a stamp such as `2026-10-30 14:25:10` at the cursor.
- *Exit* with unsaved text asks **"Save changes to Untitled?"**.
- *About Notepad* shows a dialog with the experiment details.

## Screenshot

Run the program and paste your own screenshot(s) in the `output/` folder (e.g. `output/screenshot1.png`).
The window could not be captured automatically because this environment has no display; the expected output above
is derived directly from the program's code.
