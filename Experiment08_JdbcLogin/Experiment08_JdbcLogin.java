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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import javax.swing.JFrame;

public class Experiment08_JdbcLogin extends Applet implements ActionListener {

    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL    = "jdbc:mysql://localhost:3306/university_db?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true";
    private static final String DB_USER   = "root";
    private static final String DB_PASS   = "root";

    private TextField txtUsername;
    private TextField txtPassword;
    private Button btnSave;
    private Button btnClear;
    private Button btnTestConn;
    private Label lblStatus;

    @Override
    public void init() {
        setBackground(new Color(241, 245, 249));
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font labelFont = new Font("SansSerif", Font.BOLD, 14);
        Font textFont = new Font("SansSerif", Font.PLAIN, 14);
        Font titleFont = new Font("SansSerif", Font.BOLD, 18);

        Label lblTitle = new Label("DATABASE CREDENTIALS STORAGE (JDBC)", Label.CENTER);
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

        txtUsername = new TextField(22);
        txtUsername.setFont(textFont);
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(txtUsername, gbc);

        Label lblPass = new Label("Password:");
        lblPass.setFont(labelFont);
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(lblPass, gbc);

        txtPassword = new TextField(22);
        txtPassword.setFont(textFont);
        txtPassword.setEchoChar('*');
        gbc.gridx = 1;
        gbc.gridy = 2;
        add(txtPassword, gbc);

        Panel buttonPanel = new Panel();
        btnSave = new Button("Store in Database");
        btnSave.setFont(labelFont);
        btnSave.setBackground(new Color(22, 163, 74));
        btnSave.setForeground(Color.WHITE);
        btnSave.addActionListener(this);

        btnClear = new Button("Clear");
        btnClear.setFont(labelFont);
        btnClear.setBackground(new Color(100, 116, 139));
        btnClear.setForeground(Color.WHITE);
        btnClear.addActionListener(this);

        btnTestConn = new Button("Test Connection");
        btnTestConn.setFont(labelFont);
        btnTestConn.setBackground(new Color(37, 99, 235));
        btnTestConn.setForeground(Color.WHITE);
        btnTestConn.addActionListener(this);

        buttonPanel.add(btnSave);
        buttonPanel.add(btnClear);
        buttonPanel.add(btnTestConn);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        add(buttonPanel, gbc);

        lblStatus = new Label("Enter Name and Password, then click 'Store in Database'.", Label.CENTER);
        lblStatus.setFont(new Font("SansSerif", Font.ITALIC, 13));
        lblStatus.setForeground(new Color(71, 85, 105));
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        add(lblStatus, gbc);

        ensureTableExists();
    }

    private Connection getConnection() throws Exception {
        try {
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException e) {
            try {
                Class.forName("org.sqlite.JDBC");
                return DriverManager.getConnection("jdbc:sqlite:university_lab.db");
            } catch (ClassNotFoundException ex) {
                throw new ClassNotFoundException("No JDBC driver found. Add mysql-connector-j.jar to classpath.");
            }
        }
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
    }

    private void ensureTableExists() {
        new Thread(() -> {
            try (Connection conn = getConnection();
                 Statement stmt = conn.createStatement()) {
                String ddl =
                    "CREATE TABLE IF NOT EXISTS user_credentials (" +
                    "  id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "  username VARCHAR(100) NOT NULL, " +
                    "  password VARCHAR(100) NOT NULL, " +
                    "  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                    ")";
                stmt.executeUpdate(ddl);
            } catch (Exception ignored) {}
        }).start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnSave) {
            String name = txtUsername.getText().trim();
            String pass = txtPassword.getText();

            if (name.isEmpty() || pass.isEmpty()) {
                lblStatus.setForeground(new Color(220, 38, 38));
                lblStatus.setText("Validation Error: Please fill both fields!");
                return;
            }

            String sql = "INSERT INTO user_credentials (username, password) VALUES (?, ?)";
            try (Connection conn = getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setString(1, name);
                pstmt.setString(2, pass);

                int rows = pstmt.executeUpdate();
                if (rows > 0) {
                    lblStatus.setForeground(new Color(22, 163, 74));
                    lblStatus.setText("SUCCESS: Record inserted into database for '" + name + "'!");
                    txtUsername.setText("");
                    txtPassword.setText("");
                }
            } catch (Exception ex) {
                lblStatus.setForeground(new Color(220, 38, 38));
                lblStatus.setText("JDBC Error: " + ex.getMessage());
            }
        } else if (e.getSource() == btnClear) {
            txtUsername.setText("");
            txtPassword.setText("");
            lblStatus.setForeground(new Color(71, 85, 105));
            lblStatus.setText("Fields cleared.");
            txtUsername.requestFocus();
        } else if (e.getSource() == btnTestConn) {
            try (Connection conn = getConnection()) {
                lblStatus.setForeground(new Color(22, 163, 74));
                lblStatus.setText("Database Connection Established Successfully!");
            } catch (Exception ex) {
                lblStatus.setForeground(new Color(220, 38, 38));
                lblStatus.setText("Connection Failed: " + ex.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("University Lab - Exp 08: Applet JDBC Storage");
        Experiment08_JdbcLogin applet = new Experiment08_JdbcLogin();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(580, 420);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().add(applet);

        frame.setVisible(true);
        applet.init();
        applet.start();
    }
}
