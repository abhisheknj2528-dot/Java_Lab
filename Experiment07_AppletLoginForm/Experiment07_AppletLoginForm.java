import java.applet.Applet;
import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;

public class Experiment07_AppletLoginForm extends Applet implements ActionListener {

    private TextField txtName;
    private TextField txtPassword;
    private Button btnSubmit;
    private Button btnClear;
    private Label lblStatus;

    @Override
    public void init() {
        setBackground(new Color(241, 245, 249));
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 12, 8, 12);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font labelFont = new Font("SansSerif", Font.BOLD, 14);
        Font textFont = new Font("SansSerif", Font.PLAIN, 14);
        Font titleFont = new Font("SansSerif", Font.BOLD, 18);

        Label lblTitle = new Label("USER AUTHENTICATION FORM", Label.CENTER);
        lblTitle.setFont(titleFont);
        lblTitle.setForeground(new Color(30, 41, 59));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(lblTitle, gbc);

        Label lblName = new Label("Username / Name:");
        lblName.setFont(labelFont);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        add(lblName, gbc);

        txtName = new TextField(20);
        txtName.setFont(textFont);
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(txtName, gbc);

        Label lblPass = new Label("Password:");
        lblPass.setFont(labelFont);
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(lblPass, gbc);

        txtPassword = new TextField(20);
        txtPassword.setFont(textFont);
        txtPassword.setEchoChar('*');
        gbc.gridx = 1;
        gbc.gridy = 2;
        add(txtPassword, gbc);

        Panel buttonPanel = new Panel();
        btnSubmit = new Button("Submit");
        btnSubmit.setFont(labelFont);
        btnSubmit.setBackground(new Color(37, 99, 235));
        btnSubmit.setForeground(Color.WHITE);
        btnSubmit.addActionListener(this);

        btnClear = new Button("Clear");
        btnClear.setFont(labelFont);
        btnClear.setBackground(new Color(100, 116, 139));
        btnClear.setForeground(Color.WHITE);
        btnClear.addActionListener(this);

        buttonPanel.add(btnSubmit);
        buttonPanel.add(btnClear);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        add(buttonPanel, gbc);

        lblStatus = new Label("Please enter credentials and click Submit.", Label.CENTER);
        lblStatus.setFont(new Font("SansSerif", Font.ITALIC, 13));
        lblStatus.setForeground(new Color(71, 85, 105));
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        add(lblStatus, gbc);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnSubmit) {
            String name = txtName.getText().trim();
            String pass = txtPassword.getText();

            if (name.isEmpty() || pass.isEmpty()) {
                lblStatus.setForeground(new Color(220, 38, 38));
                lblStatus.setText("Validation Error: Both Name and Password are required!");
            } else {
                lblStatus.setForeground(new Color(22, 163, 74));
                lblStatus.setText("Form submitted successfully! Welcome, " + name + ".");
            }
        } else if (e.getSource() == btnClear) {
            txtName.setText("");
            txtPassword.setText("");
            lblStatus.setForeground(new Color(71, 85, 105));
            lblStatus.setText("Fields cleared.");
            txtName.requestFocus();
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("University Lab - Exp 07: Applet Login Form");
        Experiment07_AppletLoginForm applet = new Experiment07_AppletLoginForm();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(520, 360);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().add(applet);

        frame.setVisible(true);
        applet.init();
        applet.start();
    }
}
