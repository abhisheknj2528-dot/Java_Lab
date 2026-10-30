import java.awt.Button;
import java.applet.Applet;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Experiment12_JdbcCrud extends Applet implements ActionListener {

    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL    = "jdbc:mysql://localhost:3306/university_db?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true";
    private static final String DB_USER   = "root";
    private static final String DB_PASS   = "root";

    private TextField txtUsername;
    private TextField txtPassword;
    private Label lblStatus;

    private Button btnCreate;
    private Button btnRead;
    private Button btnUpdate;
    private Button btnDelete;
    private Button btnViewAll;
    private Button btnClear;

    @Override
    public void init() {
        setBackground(new Color(241, 245, 249));
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font titleFont = new Font("SansSerif", Font.BOLD, 18);
        Font labelFont = new Font("SansSerif", Font.BOLD, 13);
        Font textFont = new Font("SansSerif", Font.PLAIN, 14);

        Label lblTitle = new Label("EXPERIMENT 12: JDBC CRUD PORTAL", Label.CENTER);
        lblTitle.setFont(titleFont);
        lblTitle.setForeground(new Color(30, 41, 59));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(lblTitle, gbc);

        Label lblSubtitle = new Label("Reusing Exp 11 Form for Create, Read, Update, Delete Operations", Label.CENTER);
        lblSubtitle.setFont(new Font("SansSerif", Font.ITALIC, 12));
        lblSubtitle.setForeground(new Color(100, 116, 139));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        add(lblSubtitle, gbc);

        Label lblName = new Label("Username / Name:");
        lblName.setFont(labelFont);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        add(lblName, gbc);

        txtUsername = new TextField(22);
        txtUsername.setFont(textFont);
        gbc.gridx = 1;
        gbc.gridy = 2;
        add(txtUsername, gbc);

        Label lblPassword = new Label("Password:");
        lblPassword.setFont(labelFont);
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(lblPassword, gbc);

        txtPassword = new TextField(22);
        txtPassword.setFont(textFont);
        gbc.gridx = 1;
        gbc.gridy = 3;
        add(txtPassword, gbc);

        Panel buttonPanel = new Panel(new GridLayout(2, 3, 10, 8));

        btnCreate = new Button("CREATE (Insert)");
        btnCreate.setFont(labelFont);
        btnCreate.setBackground(new Color(22, 163, 74));
        btnCreate.setForeground(Color.WHITE);
        btnCreate.addActionListener(this);

        btnRead = new Button("READ (Search)");
        btnRead.setFont(labelFont);
        btnRead.setBackground(new Color(37, 99, 235));
        btnRead.setForeground(Color.WHITE);
        btnRead.addActionListener(this);

        btnUpdate = new Button("UPDATE");
        btnUpdate.setFont(labelFont);
        btnUpdate.setBackground(new Color(217, 119, 6));
        btnUpdate.setForeground(Color.WHITE);
        btnUpdate.addActionListener(this);

        btnDelete = new Button("DELETE");
        btnDelete.setFont(labelFont);
        btnDelete.setBackground(new Color(220, 38, 38));
        btnDelete.setForeground(Color.WHITE);
        btnDelete.addActionListener(this);

        btnViewAll = new Button("VIEW ALL");
        btnViewAll.setFont(labelFont);
        btnViewAll.setBackground(new Color(79, 70, 229));
        btnViewAll.setForeground(Color.WHITE);
        btnViewAll.addActionListener(this);

        btnClear = new Button("CLEAR FORM");
        btnClear.setFont(labelFont);
        btnClear.setBackground(new Color(100, 116, 139));
        btnClear.setForeground(Color.WHITE);
        btnClear.addActionListener(this);

        buttonPanel.add(btnCreate);
        buttonPanel.add(btnRead);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnViewAll);
        buttonPanel.add(btnClear);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        add(buttonPanel, gbc);

        lblStatus = new Label("Ready to perform CRUD database operations.", Label.CENTER);
        lblStatus.setFont(new Font("SansSerif", Font.BOLD, 13));
        lblStatus.setForeground(new Color(71, 85, 105));
        gbc.gridx = 0;
        gbc.gridy = 5;
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
                throw new ClassNotFoundException("No JDBC driver found on classpath.");
            }
        }
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
    }

    private void ensureTableExists() {
        new Thread(() -> {
            try (Connection conn = getConnection();
                 Statement stmt = conn.createStatement()) {
                String ddl =
                    "CREATE TABLE IF NOT EXISTS users (" +
                    "  id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "  username VARCHAR(100) UNIQUE NOT NULL, " +
                    "  password VARCHAR(100) NOT NULL, " +
                    "  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP" +
                    ")";
                stmt.executeUpdate(ddl);
            } catch (Exception ignored) {}
        }).start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();

        if (src == btnCreate) {
            performCreate();
        } else if (src == btnRead) {
            performRead();
        } else if (src == btnUpdate) {
            performUpdate();
        } else if (src == btnDelete) {
            performDelete();
        } else if (src == btnViewAll) {
            performViewAll();
        } else if (src == btnClear) {
            txtUsername.setText("");
            txtPassword.setText("");
            setStatus("Form cleared.", new Color(71, 85, 105));
            txtUsername.requestFocus();
        }
    }

    private void performCreate() {
        String username = txtUsername.getText().trim();
        String password = txtPassword.getText();

        if (username.isEmpty() || password.isEmpty()) {
            setStatus("CREATE Error: Both fields must be filled!", Color.RED);
            return;
        }

        String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);

            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                setStatus("SUCCESS: User '" + username + "' created in database!", new Color(22, 163, 74));
                txtPassword.setText("");
            }
        } catch (SQLException ex) {
            if (ex.getErrorCode() == 1062 || ex.getMessage().contains("UNIQUE") || ex.getMessage().contains("duplicate")) {
                setStatus("CREATE Failed: User '" + username + "' already exists!", Color.RED);
            } else {
                setStatus("SQL Error: " + ex.getMessage(), Color.RED);
            }
        } catch (Exception ex) {
            setStatus("Error: " + ex.getMessage(), Color.RED);
        }
    }

    private void performRead() {
        String username = txtUsername.getText().trim();

        if (username.isEmpty()) {
            setStatus("READ Error: Enter a Username to search!", Color.RED);
            return;
        }

        String sql = "SELECT password FROM users WHERE username = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    txtPassword.setText(rs.getString("password"));
                    setStatus("SUCCESS: Found user '" + username + "'. Password retrieved.", new Color(37, 99, 235));
                } else {
                    txtPassword.setText("");
                    setStatus("NOT FOUND: No record for user '" + username + "'.", Color.RED);
                }
            }
        } catch (Exception ex) {
            setStatus("Error: " + ex.getMessage(), Color.RED);
        }
    }

    private void performUpdate() {
        String username = txtUsername.getText().trim();
        String newPassword = txtPassword.getText();

        if (username.isEmpty() || newPassword.isEmpty()) {
            setStatus("UPDATE Error: Enter Username and new Password!", Color.RED);
            return;
        }

        String sql = "UPDATE users SET password = ? WHERE username = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, newPassword);
            pstmt.setString(2, username);

            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                setStatus("SUCCESS: Updated password for user '" + username + "'!", new Color(217, 119, 6));
            } else {
                setStatus("UPDATE Failed: User '" + username + "' not found.", Color.RED);
            }
        } catch (Exception ex) {
            setStatus("Error: " + ex.getMessage(), Color.RED);
        }
    }

    private void performDelete() {
        String username = txtUsername.getText().trim();

        if (username.isEmpty()) {
            setStatus("DELETE Error: Enter Username to delete!", Color.RED);
            return;
        }

        String sql = "DELETE FROM users WHERE username = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            int rows = pstmt.executeUpdate();

            if (rows > 0) {
                setStatus("SUCCESS: Deleted user '" + username + "' from database.", new Color(220, 38, 38));
                txtUsername.setText("");
                txtPassword.setText("");
            } else {
                setStatus("DELETE Failed: User '" + username + "' not found.", Color.RED);
            }
        } catch (Exception ex) {
            setStatus("Error: " + ex.getMessage(), Color.RED);
        }
    }

    private void performViewAll() {
        String sql = "SELECT username, password, updated_at FROM users ORDER BY username ASC";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            StringBuilder sb = new StringBuilder();
            sb.append(String.format("%-25s | %-25s | %s\n", "USERNAME", "PASSWORD", "LAST UPDATED"));
            sb.append("--------------------------------------------------------------------------------\n");

            int count = 0;
            while (rs.next()) {
                count++;
                sb.append(String.format("%-25s | %-25s | %s\n",
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("updated_at")
                ));
            }

            if (count == 0) {
                sb.append("[No user records found in database]\n");
            }

            JTextArea area = new JTextArea(sb.toString(), 15, 55);
            area.setFont(new Font("Monospaced", Font.PLAIN, 12));
            area.setEditable(false);

            JOptionPane.showMessageDialog(this, new JScrollPane(area),
                "Database Records (Total: " + count + ")", JOptionPane.INFORMATION_MESSAGE);

            setStatus("Viewed " + count + " record(s) from database.", new Color(79, 70, 229));

        } catch (Exception ex) {
            setStatus("Error querying database: " + ex.getMessage(), Color.RED);
        }
    }

    private void setStatus(String message, Color color) {
        lblStatus.setForeground(color);
        lblStatus.setText(message);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("University Lab - Exp 12: JDBC CRUD Operations Applet");
        Experiment12_JdbcCrud applet = new Experiment12_JdbcCrud();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(640, 480);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().add(applet);

        frame.setVisible(true);
        applet.init();
        applet.start();
    }
}
