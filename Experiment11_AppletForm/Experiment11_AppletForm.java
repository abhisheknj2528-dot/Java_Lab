import java.applet.Applet;
import java.awt.Button;
import java.awt.Checkbox;
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
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JFrame;

public class Experiment11_AppletForm extends Applet implements ActionListener, ItemListener {

    protected TextField txtName;
    protected TextField txtPassword;
    protected Checkbox chkShowPassword;
    protected Button btnSubmit;
    protected Button btnReset;
    protected Label lblStatus;

    @Override
    public void init() {
        setBackground(new Color(248, 250, 252));
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 12, 8, 12);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font titleFont = new Font("SansSerif", Font.BOLD, 18);
        Font labelFont = new Font("SansSerif", Font.BOLD, 14);
        Font textFont = new Font("SansSerif", Font.PLAIN, 14);

        Label lblTitle = new Label("USER CREDENTIALS APPLET FORM", Label.CENTER);
        lblTitle.setFont(titleFont);
        lblTitle.setForeground(new Color(30, 41, 59));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(lblTitle, gbc);

        Label lblName = new Label("Account Name / Username:");
        lblName.setFont(labelFont);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        add(lblName, gbc);

        txtName = new TextField(22);
        txtName.setFont(textFont);
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(txtName, gbc);

        Label lblPassword = new Label("Password:");
        lblPassword.setFont(labelFont);
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(lblPassword, gbc);

        txtPassword = new TextField(22);
        txtPassword.setFont(textFont);
        txtPassword.setEchoChar('*');
        gbc.gridx = 1;
        gbc.gridy = 2;
        add(txtPassword, gbc);

        chkShowPassword = new Checkbox("Show Password");
        chkShowPassword.setFont(new Font("SansSerif", Font.PLAIN, 12));
        chkShowPassword.addItemListener(this);
        gbc.gridx = 1;
        gbc.gridy = 3;
        add(chkShowPassword, gbc);

        Panel btnPanel = new Panel();
        btnSubmit = new Button("Submit");
        btnSubmit.setFont(labelFont);
        btnSubmit.setBackground(new Color(37, 99, 235));
        btnSubmit.setForeground(Color.WHITE);
        btnSubmit.addActionListener(this);

        btnReset = new Button("Reset");
        btnReset.setFont(labelFont);
        btnReset.setBackground(new Color(100, 116, 139));
        btnReset.setForeground(Color.WHITE);
        btnReset.addActionListener(this);

        btnPanel.add(btnSubmit);
        btnPanel.add(btnReset);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        add(btnPanel, gbc);

        lblStatus = new Label("Ready. Enter Name and Password.", Label.CENTER);
        lblStatus.setFont(new Font("SansSerif", Font.ITALIC, 13));
        lblStatus.setForeground(new Color(71, 85, 105));
        gbc.gridx = 0;
        gbc.gridy = 5;
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
                lblStatus.setText("Validation Failed: Both Name and Password are required!");
            } else {
                lblStatus.setForeground(new Color(22, 163, 74));
                lblStatus.setText("Form submitted successfully for: " + name);
            }
        } else if (e.getSource() == btnReset) {
            txtName.setText("");
            txtPassword.setText("");
            chkShowPassword.setState(false);
            txtPassword.setEchoChar('*');
            lblStatus.setForeground(new Color(71, 85, 105));
            lblStatus.setText("Form reset.");
            txtName.requestFocus();
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (chkShowPassword.getState()) {
            txtPassword.setEchoChar((char) 0);
        } else {
            txtPassword.setEchoChar('*');
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("University Lab - Exp 11: Applet Form");
        Experiment11_AppletForm applet = new Experiment11_AppletForm();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(580, 400);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().add(applet);

        frame.setVisible(true);
        applet.init();
        applet.start();
    }
}
