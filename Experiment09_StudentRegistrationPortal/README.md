# Experiment 09 — Student Registration Portal (Swing + JDBC)

## Question / Problem Statement

Design a student registration form using Swing components (text fields, combo boxes, radio buttons) and store the data in a database using JDBC. Provide a tab to view and delete registered students.

## Aim

To build a complete database-driven registration application with Swing and JDBC.

## Concepts Used

- Swing: `JFrame`, `JTabbedPane`, `JTextField`, `JComboBox`, `JRadioButton` + `ButtonGroup`, `JTable` + `DefaultTableModel`
- JDBC `INSERT`, `SELECT`, `DELETE` with `PreparedStatement`
- Primary key (`roll_no`) to prevent duplicates
- `GridBagLayout`, `BorderLayout`, `FlowLayout`

## Files

| File | Description |
|------|-------------|
| `Experiment09_StudentRegistrationPortal.java` | Complete source code |
| `README.md` | This file |
| `output/` | Program output / screenshots |

## How to Compile and Run

> **Prerequisites (MySQL)**
> 1. MySQL Server running on `localhost:3306` with user `root` / password `root`
>    (change `DB_USER` / `DB_PASS` in the source if yours differ).
> 2. Download the MySQL Connector/J jar (e.g. `mysql-connector-j-8.x.x.jar`) and keep it in the same folder.
> 3. The database `university_db` and the table are created automatically by the program
>    (`createDatabaseIfNotExist=true` + `CREATE TABLE IF NOT EXISTS`).

```bash
javac Experiment09_StudentRegistrationPortal.java
java -cp .:mysql-connector-j-8.x.x.jar Experiment09_StudentRegistrationPortal
```
(Windows use `;` instead of `:` in the classpath)

## Database

```sql
-- created automatically by the program
CREATE TABLE IF NOT EXISTS students (
  roll_no VARCHAR(20) PRIMARY KEY,
  full_name VARCHAR(100) NOT NULL,
  branch VARCHAR(50), semester VARCHAR(20), gender VARCHAR(10),
  email VARCHAR(100), contact VARCHAR(20),
  registered_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

## Expected Output

An 850×600 window titled **"University Student Registration Portal - JDBC"** with two tabs.

**Tab 1 — Student Registration Form:** University Roll No, Full Name, Branch (combo box), Semester (combo box),
Gender (Male / Female / Other radio buttons), Email, Contact, and buttons **Register Student**, **Clear Form**, **View Directory**.

**Tab 2 — Registered Students Directory:** a table with columns `Roll No | Full Name | Branch | Semester | Gender | Email | Contact`
and buttons **Refresh Records**, **Delete Selected**.

| Action | Result |
|--------|--------|
| Register with empty Roll No / Name | Dialog: `Roll No and Full Name are mandatory!` |
| Register `CS101 / Rahul Sharma / CSE / Semester 5 / Male` | Dialog: `Student registered successfully!`, row appears in the directory |
| Register the same Roll No again | Dialog: `Registration failed: Duplicate entry 'CS101' for key ...` |
| Select a row → **Delete Selected** | Row removed, dialog `Record deleted.` |

> Expected behaviour derived from the code — it needs MySQL + Connector/J to run, which are not available in this environment.
> Add your own screenshots after running it.

## Screenshot

Run the program and paste your own screenshot(s) in the `output/` folder (e.g. `output/screenshot1.png`).
The window could not be captured automatically because this environment has no display; the expected output above
is derived directly from the program's code.
