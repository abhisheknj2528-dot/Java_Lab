import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Experiment05_SwingNotepad extends JFrame implements ActionListener {

    private JTextArea textArea;
    private JScrollPane scrollPane;
    private JLabel statusBar;

    private File currentFile = null;
    private boolean isModified = false;

    private JMenuItem itemNew, itemOpen, itemSave, itemSaveAs, itemExit;
    private JMenuItem itemCut, itemCopy, itemPaste, itemSelectAll, itemTimeDate;
    private JCheckBoxMenuItem itemWordWrap;
    private JMenuItem itemZoomIn, itemZoomOut, itemAbout;

    private int fontSize = 16;

    public Experiment05_SwingNotepad() {
        super("Untitled - Java Swing Notepad");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(850, 600);
        setLocationRelativeTo(null);

        textArea = new JTextArea();
        textArea.setFont(new Font("Monospaced", Font.PLAIN, fontSize));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        scrollPane = new JScrollPane(textArea);
        add(scrollPane, BorderLayout.CENTER);

        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        statusBar = new JLabel("Line: 1 | Col: 1 | Chars: 0   ");
        statusBar.setFont(new Font("SansSerif", Font.PLAIN, 12));
        statusPanel.add(statusBar);
        add(statusPanel, BorderLayout.SOUTH);

        textArea.addCaretListener(new CaretListener() {
            @Override
            public void caretUpdate(CaretEvent e) {
                updateStatusBar();
                isModified = true;
            }
        });

        buildMenuBar();

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                promptExit();
            }
        });
    }

    private void buildMenuBar() {
        JMenuBar bar = new JMenuBar();

        JMenu mFile = new JMenu("File");
        itemNew = new JMenuItem("New");
        itemOpen = new JMenuItem("Open...");
        itemSave = new JMenuItem("Save");
        itemSaveAs = new JMenuItem("Save As...");
        itemExit = new JMenuItem("Exit");

        itemNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        itemOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        itemSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));

        itemNew.addActionListener(this);
        itemOpen.addActionListener(this);
        itemSave.addActionListener(this);
        itemSaveAs.addActionListener(this);
        itemExit.addActionListener(this);

        mFile.add(itemNew);
        mFile.add(itemOpen);
        mFile.add(itemSave);
        mFile.add(itemSaveAs);
        mFile.addSeparator();
        mFile.add(itemExit);

        JMenu mEdit = new JMenu("Edit");
        itemCut = new JMenuItem("Cut");
        itemCopy = new JMenuItem("Copy");
        itemPaste = new JMenuItem("Paste");
        itemSelectAll = new JMenuItem("Select All");
        itemTimeDate = new JMenuItem("Time/Date");

        itemCut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        itemCopy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        itemPaste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        itemSelectAll.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));

        itemCut.addActionListener(this);
        itemCopy.addActionListener(this);
        itemPaste.addActionListener(this);
        itemSelectAll.addActionListener(this);
        itemTimeDate.addActionListener(this);

        mEdit.add(itemCut);
        mEdit.add(itemCopy);
        mEdit.add(itemPaste);
        mEdit.addSeparator();
        mEdit.add(itemSelectAll);
        mEdit.add(itemTimeDate);

        JMenu mFormat = new JMenu("Format");
        itemWordWrap = new JCheckBoxMenuItem("Word Wrap", true);
        itemZoomIn = new JMenuItem("Zoom In (Font +)");
        itemZoomOut = new JMenuItem("Zoom Out (Font -)");

        itemWordWrap.addActionListener(this);
        itemZoomIn.addActionListener(this);
        itemZoomOut.addActionListener(this);

        mFormat.add(itemWordWrap);
        mFormat.addSeparator();
        mFormat.add(itemZoomIn);
        mFormat.add(itemZoomOut);

        JMenu mHelp = new JMenu("Help");
        itemAbout = new JMenuItem("About Notepad");
        itemAbout.addActionListener(this);
        mHelp.add(itemAbout);

        bar.add(mFile);
        bar.add(mEdit);
        bar.add(mFormat);
        bar.add(mHelp);

        setJMenuBar(bar);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object s = e.getSource();

        if (s == itemNew) {
            if (confirmSave()) {
                textArea.setText("");
                currentFile = null;
                isModified = false;
                setTitle("Untitled - Java Swing Notepad");
            }
        } else if (s == itemOpen) {
            if (confirmSave()) {
                JFileChooser chooser = new JFileChooser();
                chooser.setFileFilter(new FileNameExtensionFilter("Text Files (*.txt, *.java)", "txt", "java"));
                if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                    currentFile = chooser.getSelectedFile();
                    try (BufferedReader reader = new BufferedReader(new FileReader(currentFile))) {
                        textArea.read(reader, null);
                        isModified = false;
                        setTitle(currentFile.getName() + " - Java Swing Notepad");
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(this, "Error opening file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        } else if (s == itemSave) {
            save();
        } else if (s == itemSaveAs) {
            saveAs();
        } else if (s == itemExit) {
            promptExit();
        } else if (s == itemCut) {
            textArea.cut();
        } else if (s == itemCopy) {
            textArea.copy();
        } else if (s == itemPaste) {
            textArea.paste();
        } else if (s == itemSelectAll) {
            textArea.selectAll();
        } else if (s == itemTimeDate) {
            String stamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            textArea.insert(stamp, textArea.getCaretPosition());
        } else if (s == itemWordWrap) {
            boolean wrap = itemWordWrap.isSelected();
            textArea.setLineWrap(wrap);
            textArea.setWrapStyleWord(wrap);
        } else if (s == itemZoomIn) {
            fontSize = Math.min(48, fontSize + 2);
            textArea.setFont(new Font("Monospaced", Font.PLAIN, fontSize));
        } else if (s == itemZoomOut) {
            fontSize = Math.max(10, fontSize - 2);
            textArea.setFont(new Font("Monospaced", Font.PLAIN, fontSize));
        } else if (s == itemAbout) {
            JOptionPane.showMessageDialog(this,
                "University Lab - Experiment 05\n" +
                "Application: Java Swing Notepad\n" +
                "Features: File IO, Word Wrap, Cut/Copy/Paste, Zoom, Real-time Status",
                "About Notepad", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private boolean save() {
        if (currentFile == null) return saveAs();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(currentFile))) {
            textArea.write(writer);
            isModified = false;
            setTitle(currentFile.getName() + " - Java Swing Notepad");
            return true;
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error saving: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    private boolean saveAs() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter("Text Files (*.txt)", "txt"));
        if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            File f = chooser.getSelectedFile();
            if (!f.getName().contains(".")) {
                f = new File(f.getAbsolutePath() + ".txt");
            }
            currentFile = f;
            return save();
        }
        return false;
    }

    private boolean confirmSave() {
        if (!isModified || textArea.getText().trim().isEmpty()) return true;
        int opt = JOptionPane.showConfirmDialog(this,
            "Save changes to " + (currentFile == null ? "Untitled" : currentFile.getName()) + "?",
            "Unsaved Changes", JOptionPane.YES_NO_CANCEL_OPTION);
        if (opt == JOptionPane.YES_OPTION) return save();
        return (opt == JOptionPane.NO_OPTION);
    }

    private void promptExit() {
        if (confirmSave()) {
            dispose();
            System.exit(0);
        }
    }

    private void updateStatusBar() {
        try {
            int pos = textArea.getCaretPosition();
            int line = textArea.getLineOfOffset(pos) + 1;
            int col = pos - textArea.getLineStartOffset(line - 1) + 1;
            statusBar.setText(String.format("Line: %d | Col: %d | Chars: %d   ", line, col, textArea.getText().length()));
        } catch (Exception ignored) {}
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(() -> {
            Experiment05_SwingNotepad pad = new Experiment05_SwingNotepad();
            pad.setVisible(true);
        });
    }
}
