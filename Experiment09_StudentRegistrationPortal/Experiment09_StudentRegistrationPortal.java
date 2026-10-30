import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Vector;

public class Experiment09_StudentRegistrationPortal extends JFrame implements ActionListener {

    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL    = "jdbc:mysql://localhost:3306/university_db?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true";
    private static final String DB_USER   = "root";
    private static final String DB_PASS   = "root";

    private JTextField txtRollNo, txtFullName, txtEmail, txtContact;
    private JComboBox<String> comboBranch, comboSemester;
    private JRadioButton rbMale, rbFemale, rbOther;
    private ButtonGroup bgGender;

    private JButton btnRegister, btnClear, btnViewTable, btnDelete;
    private JTable tableStudents;
    private DefaultTableModel tableModel;
    private JTabbedPane tabs;

    public Experiment09_StudentRegistrationPortal() {
        super("University Student Registration Portal - JDBC");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(850, 600);
        setLocationRelativeTo(null);

        tabs = new JTabbedPane();
        tabs.setFont(new Font("SansSerif", Font.BOLD, 13));

        tabs.addTab("Student Registration Form", createFormPanel());

        tabs.addTab("Registered Students Directory", createTablePanel());

        add(tabs, BorderLayout.CENTER);

        initDatabase();
    }

    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        panel.setBackground(new Color(248, 250, 252));

        JLabel title = new JLabel("STUDENT REGISTRATION PORTAL", JLabel.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 22));
        title.setForeground(new Color(30, 41, 59));
        panel.add(title, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(new Color(248, 250, 252));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 12, 8, 12);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font lFont = new Font("SansSerif", Font.BOLD, 13);
        Font tFont = new Font("SansSerif", Font.PLAIN, 13);

        gbc.gridx = 0; gbc.gridy = 0;
        form.add(new JLabel("University Roll No:*"), gbc);
        gbc.gridx = 1; gbc.gridy = 0;
        txtRollNo = new JTextField(20);
        txtRollNo.setFont(tFont);
        form.add(txtRollNo, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        form.add(new JLabel("Student Full Name:*"), gbc);
        gbc.gridx = 1; gbc.gridy = 1;
        txtFullName = new JTextField(20);
        txtFullName.setFont(tFont);
        form.add(txtFullName, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        form.add(new JLabel("Branch / Department:*"), gbc);
        gbc.gridx = 1; gbc.gridy = 2;
        String[] branches = {"Computer Science & Engg (CSE)", "Information Technology (IT)",
                             "Electronics & Comm (ECE)", "Electrical Engg (EE)", "Mechanical Engg (ME)"};
        comboBranch = new JComboBox<>(branches);
        comboBranch.setFont(tFont);
        form.add(comboBranch, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        form.add(new JLabel("Current Semester:*"), gbc);
        gbc.gridx = 1; gbc.gridy = 3;
        String[] sems = {"Semester 1", "Semester 2", "Semester 3", "Semester 4",
                         "Semester 5", "Semester 6", "Semester 7", "Semester 8"};
        comboSemester = new JComboBox<>(sems);
        comboSemester.setFont(tFont);
        form.add(comboSemester, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        form.add(new JLabel("Gender:*"), gbc);
        gbc.gridx = 1; gbc.gridy = 4;
        JPanel gPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        gPanel.setBackground(new Color(248, 250, 252));
        rbMale = new JRadioButton("Male", true);
        rbFemale = new JRadioButton("Female");
        rbOther = new JRadioButton("Other");
        bgGender = new ButtonGroup();
        bgGender.add(rbMale);
        bgGender.add(rbFemale);
        bgGender.add(rbOther);
        gPanel.add(rbMale);
        gPanel.add(rbFemale);
        gPanel.add(rbOther);
        form.add(gPanel, gbc);

        gbc.gridx = 0; gbc.gridy = 5;
        form.add(new JLabel("Email Address:"), gbc);
        gbc.gridx = 1; gbc.gridy = 5;
        txtEmail = new JTextField(20);
        txtEmail.setFont(tFont);
        form.add(txtEmail, gbc);

        gbc.gridx = 0; gbc.gridy = 6;
        form.add(new JLabel("Contact Number:"), gbc);
        gbc.gridx = 1; gbc.gridy = 6;
        txtContact = new JTextField(20);
        txtContact.setFont(tFont);
        form.add(txtContact, gbc);

        panel.add(form, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        btnPanel.setBackground(new Color(248, 250, 252));

        btnRegister = new JButton("Register Student");
        btnRegister.setFont(lFont);
        btnRegister.setBackground(new Color(22, 163, 74));
        btnRegister.setForeground(Color.WHITE);
        btnRegister.addActionListener(this);

        btnClear = new JButton("Clear Form");
        btnClear.setFont(lFont);
        btnClear.setBackground(new Color(100, 116, 139));
        btnClear.setForeground(Color.WHITE);
        btnClear.addActionListener(this);

        btnViewTable = new JButton("View Directory");
        btnViewTable.setFont(lFont);
        btnViewTable.setBackground(new Color(37, 99, 235));
        btnViewTable.setForeground(Color.WHITE);
        btnViewTable.addActionListener(this);

        btnPanel.add(btnRegister);
        btnPanel.add(btnClear);
        btnPanel.add(btnViewTable);

        panel.add(btnPanel, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        String[] cols = {"Roll No", "Full Name", "Branch", "Semester", "Gender", "Email", "Contact"};
        tableModel = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int r, int c) { return false; }
        };

        tableStudents = new JTable(tableModel);
        tableStudents.setRowHeight(24);
        panel.add(new JScrollPane(tableStudents), BorderLayout.CENTER);

        JPanel acts = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 5));
        JButton btnRefresh = new JButton("Refresh Records");
        btnRefresh.addActionListener(e -> refreshTable());

        btnDelete = new JButton("Delete Selected");
        btnDelete.setBackground(new Color(239, 68, 68));
        btnDelete.setForeground(Color.WHITE);
        btnDelete.addActionListener(this);

        acts.add(btnRefresh);
        acts.add(btnDelete);
        panel.add(acts, BorderLayout.SOUTH);

        return panel;
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

    private void initDatabase() {
        new Thread(() -> {
            try (Connection conn = getConnection();
                 Statement stmt = conn.createStatement()) {
                String ddl =
                    "CREATE TABLE IF NOT EXISTS students (" +
                    "  roll_no VARCHAR(20) PRIMARY KEY, " +
                    "  full_name VARCHAR(100) NOT NULL, " +
                    "  branch VARCHAR(50), " +
                    "  semester VARCHAR(20), " +
                    "  gender VARCHAR(10), " +
                    "  email VARCHAR(100), " +
                    "  contact VARCHAR(20), " +
                    "  registered_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                    ")";
                stmt.executeUpdate(ddl);
                refreshTable();
            } catch (Exception ignored) {}
        }).start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object s = e.getSource();
        if (s == btnRegister) {
            String roll = txtRollNo.getText().trim();
            String name = txtFullName.getText().trim();
            String branch = (String) comboBranch.getSelectedItem();
            String sem = (String) comboSemester.getSelectedItem();
            String gender = rbMale.isSelected() ? "Male" : (rbFemale.isSelected() ? "Female" : "Other");
            String email = txtEmail.getText().trim();
            String contact = txtContact.getText().trim();

            if (roll.isEmpty() || name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Roll No and Full Name are mandatory!", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String sql = "INSERT INTO students (roll_no, full_name, branch, semester, gender, email, contact) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (Connection conn = getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setString(1, roll);
                pstmt.setString(2, name);
                pstmt.setString(3, branch);
                pstmt.setString(4, sem);
                pstmt.setString(5, gender);
                pstmt.setString(6, email);
                pstmt.setString(7, contact);

                pstmt.executeUpdate();
                JOptionPane.showMessageDialog(this, "Student registered successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                txtRollNo.setText("");
                txtFullName.setText("");
                txtEmail.setText("");
                txtContact.setText("");
                refreshTable();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Registration failed: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (s == btnClear) {
            txtRollNo.setText("");
            txtFullName.setText("");
            txtEmail.setText("");
            txtContact.setText("");
        } else if (s == btnViewTable) {
            refreshTable();
            tabs.setSelectedIndex(1);
        } else if (s == btnDelete) {
            int row = tableStudents.getSelectedRow();
            if (row != -1) {
                String roll = (String) tableModel.getValueAt(row, 0);
                try (Connection conn = getConnection();
                     PreparedStatement pstmt = conn.prepareStatement("DELETE FROM students WHERE roll_no = ?")) {
                    pstmt.setString(1, roll);
                    pstmt.executeUpdate();
                    refreshTable();
                    JOptionPane.showMessageDialog(this, "Record deleted.", "Deleted", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private void refreshTable() {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT roll_no, full_name, branch, semester, gender, email, contact FROM students")) {

            tableModel.setRowCount(0);
            while (rs.next()) {
                Vector<String> row = new Vector<>();
                row.add(rs.getString("roll_no"));
                row.add(rs.getString("full_name"));
                row.add(rs.getString("branch"));
                row.add(rs.getString("semester"));
                row.add(rs.getString("gender"));
                row.add(rs.getString("email"));
                row.add(rs.getString("contact"));
                tableModel.addRow(row);
            }
        } catch (Exception ignored) {}
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(() -> {
            new Experiment09_StudentRegistrationPortal().setVisible(true);
        });
    }
}
