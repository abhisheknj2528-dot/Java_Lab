# Experiment 12 — JDBC CRUD Operations (Create, Read, Update, Delete)

## Question / Problem Statement

Using the form of Experiment 11, write a program to perform Create, Read, Update and Delete operations on a database table using JDBC.

## Aim

To perform all four CRUD operations on a MySQL table from a Java applet using JDBC.

## Concepts Used

- JDBC `INSERT`, `SELECT`, `UPDATE`, `DELETE` with `PreparedStatement`
- `UNIQUE` constraint on `username` and duplicate-key handling (MySQL error code 1062)
- `ResultSet`, ordered `SELECT` for *View All*
- `JTextArea` inside `JOptionPane` to display records
- AWT applet UI with `GridLayout` button panel

## Files

| File | Description |
|------|-------------|
| `Experiment12_JdbcCrud.java` | Complete source code |
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
javac Experiment12_JdbcCrud.java
java -cp .:mysql-connector-j-8.x.x.jar Experiment12_JdbcCrud
```
(Windows use `;` instead of `:` in the classpath)

> **Note about applets:** browsers and `appletviewer` no longer support applets (appletviewer was removed in JDK 11).
> This program therefore includes a `main()` that hosts the applet inside a `JFrame`, so it runs with a plain
> `java` command. Applets need a JDK version that still ships `java.applet` (JDK 25 or earlier;
> the Applet API is removed in newer JDKs).

## Database

```sql
-- created automatically by the program
CREATE TABLE IF NOT EXISTS users (
  id INT AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(100) UNIQUE NOT NULL,
  password VARCHAR(100) NOT NULL,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
```

## Expected Output

A 640×480 window titled **"University Lab - Exp 12: JDBC CRUD Operations Applet"** with *Username / Name* and *Password* fields and six
buttons: **CREATE (Insert)**, **READ (Search)**, **UPDATE**, **DELETE**, **VIEW ALL**, **CLEAR FORM**.

| Operation | Input | Status line |
|-----------|-------|-------------|
| CREATE | `Rahul` / `secret123` | `SUCCESS: User 'Rahul' created in database!` |
| CREATE (again) | `Rahul` / `x` | `CREATE Failed: User 'Rahul' already exists!` |
| READ | `Rahul` | `SUCCESS: Found user 'Rahul'. Password retrieved.` (password box filled) |
| READ | `Unknown` | `NOT FOUND: No record for user 'Unknown'.` |
| UPDATE | `Rahul` / `newpass456` | `SUCCESS: Updated password for user 'Rahul'!` |
| DELETE | `Rahul` | `SUCCESS: Deleted user 'Rahul' from database.` |
| VIEW ALL | – | Dialog listing `USERNAME \| PASSWORD \| LAST UPDATED`, status `Viewed N record(s) from database.` |

> Expected behaviour derived from the code — it needs MySQL + Connector/J, which are not available in this environment.
> Add your own screenshots after running it.

## Screenshot

Run the program and paste your own screenshot(s) in the `output/` folder (e.g. `output/screenshot1.png`).
The window could not be captured automatically because this environment has no display; the expected output above
is derived directly from the program's code.
