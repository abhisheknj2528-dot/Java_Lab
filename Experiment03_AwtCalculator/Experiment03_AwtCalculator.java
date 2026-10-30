import java.awt.Frame;
import java.awt.Button;
import java.awt.TextField;
import java.awt.Panel;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Experiment03_AwtCalculator extends Frame implements ActionListener {

    private TextField displayField;
    private double firstOperand = 0.0;
    private String pendingOperator = "";
    private boolean isOperatorPressed = false;
    private boolean isError = false;

    private final String[] keypadLabels = {
        "C", "CE", "%", "/",
        "7", "8", "9", "*",
        "4", "5", "6", "-",
        "1", "2", "3", "+",
        "+/-", "0", ".", "="
    };

    public Experiment03_AwtCalculator() {
        super("University Lab - Exp 03: AWT Calculator");
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(230, 235, 240));

        Panel screenPanel = new Panel(new BorderLayout(5, 5));
        displayField = new TextField("0");
        displayField.setEditable(false);
        displayField.setFont(new Font("Monospaced", Font.BOLD, 26));
        displayField.setBackground(Color.WHITE);
        displayField.setForeground(new Color(20, 20, 20));
        screenPanel.add(displayField, BorderLayout.CENTER);
        add(screenPanel, BorderLayout.NORTH);

        Panel keypadPanel = new Panel();
        keypadPanel.setLayout(new GridLayout(5, 4, 6, 6));

        Font btnFont = new Font("SansSerif", Font.BOLD, 16);

        for (String label : keypadLabels) {
            Button btn = new Button(label);
            btn.setFont(btnFont);
            btn.addActionListener(this);

            if (label.equals("=")) {
                btn.setBackground(new Color(37, 99, 235));
                btn.setForeground(Color.WHITE);
            } else if (label.equals("C") || label.equals("CE")) {
                btn.setBackground(new Color(239, 68, 68));
                btn.setForeground(Color.WHITE);
            } else {
                btn.setBackground(Color.WHITE);
                btn.setForeground(new Color(30, 41, 59));
            }
            keypadPanel.add(btn);
        }
        add(keypadPanel, BorderLayout.CENTER);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
                System.exit(0);
            }
        });

        setSize(360, 460);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();

        if (isError && !cmd.equals("C")) {
            return;
        }

        if (cmd.charAt(0) >= '0' && cmd.charAt(0) <= '9') {
            if (isOperatorPressed || displayField.getText().equals("0")) {
                displayField.setText(cmd);
                isOperatorPressed = false;
            } else {
                displayField.setText(displayField.getText() + cmd);
            }
        } else if (cmd.equals(".")) {
            if (isOperatorPressed) {
                displayField.setText("0.");
                isOperatorPressed = false;
            } else if (!displayField.getText().contains(".")) {
                displayField.setText(displayField.getText() + ".");
            }
        } else if (cmd.equals("C")) {
            firstOperand = 0.0;
            pendingOperator = "";
            displayField.setText("0");
            isOperatorPressed = false;
            isError = false;
        } else if (cmd.equals("CE")) {
            String txt = displayField.getText();
            if (txt.length() > 1 && !txt.equals("0")) {
                displayField.setText(txt.substring(0, txt.length() - 1));
            } else {
                displayField.setText("0");
            }
        } else if (cmd.equals("+/-")) {
            try {
                double val = Double.parseDouble(displayField.getText());
                displayField.setText(formatResult(-val));
            } catch (NumberFormatException ignored) {}
        } else if (cmd.equals("=")) {
            if (!pendingOperator.isEmpty()) {
                double second = Double.parseDouble(displayField.getText());
                calculate(second);
                pendingOperator = "";
                isOperatorPressed = true;
            }
        } else {

            if (!pendingOperator.isEmpty() && !isOperatorPressed) {
                double second = Double.parseDouble(displayField.getText());
                calculate(second);
            } else {
                firstOperand = Double.parseDouble(displayField.getText());
            }
            pendingOperator = cmd;
            isOperatorPressed = true;
        }
    }

    private void calculate(double second) {
        double res = 0.0;
        switch (pendingOperator) {
            case "+": res = firstOperand + second; break;
            case "-": res = firstOperand - second; break;
            case "*": res = firstOperand * second; break;
            case "/":
                if (second == 0) {
                    displayField.setText("Error: Div by 0");
                    isError = true;
                    return;
                }
                res = firstOperand / second;
                break;
            case "%": res = firstOperand % second; break;
            default:  res = second;
        }
        displayField.setText(formatResult(res));
        firstOperand = res;
    }

    private String formatResult(double v) {
        if (v == (long) v) return String.format("%d", (long) v);
        return String.valueOf(v);
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            Experiment03_AwtCalculator calc = new Experiment03_AwtCalculator();
            calc.setVisible(true);
        });
    }
}
