# Experiment 08 — Applet with JDBC — Store Name and Password in a Database

## Question / Problem Statement

Write an applet that accepts a name and password and stores them in a database table using JDBC.

## Aim

To connect a Java applet to a MySQL database through JDBC and insert records using a `PreparedStatement`.

## Concepts Used

- JDBC: `DriverManager`, `Connection`, `PreparedStatement`, `Statement`
- MySQL Connector/J driver (`com.mysql.cj.jdbc.Driver`)
- DDL: `CREATE TABLE IF NOT EXISTS user_credentials`
- AWT applet UI, background `Thread` for table creation

## Files

| File | Description |
|------|-------------|
| `Experiment08_JdbcLogin.java` | Complete source code |
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
javac Experiment08_JdbcLogin.java
java -cp .:mysql-connector-j-8.x.x.jar Experiment08_JdbcLogin
```
(Windows use `;` instead of `:` in the classpath)

> **Note about applets:** browsers and `appletviewer` no longer support applets (appletviewer was removed in JDK 11).
> This program therefore includes a `main()` that hosts the applet inside a `JFrame`, so it runs with a plain
> `java` command. Applets need a JDK version that still ships `java.applet` (JDK 25 or earlier;
> the Applet API is removed in newer JDKs).

## Database

```sql
-- created automatically by the program
CREATE TABLE IF NOT EXISTS user_credentials (
  id INT AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(100) NOT NULL,
  password VARCHAR(100) NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

Verify the stored data in MySQL:

```sql
USE university_db;
SELECT * FROM user_credentials;
```

## Expected Output

A 580×420 window titled **"University Lab - Exp 08: Applet JDBC Storage"** with *Username / Name* and *Password* fields and
buttons **Store in Database**, **Clear**, **Test Connection**.

| Action | Status line shown |
|--------|-------------------|
| Click **Store in Database** with an empty field | `Validation Error: Please fill both fields!` |
| Name `Rahul`, Password `secret123` → **Store in Database** | `SUCCESS: Record inserted into database for 'Rahul'!` |
| Click **Test Connection** | `Database Connection Established Successfully!` |
| Driver jar missing / MySQL not running | `JDBC Error: ...` / `Connection Failed: ...` (red) |

Resulting table:

```text
+----+----------+-----------+---------------------+
| id | username | password  | created_at          |
+----+----------+-----------+---------------------+
|  1 | Rahul    | secret123 | (insert timestamp)  |
+----+----------+-----------+---------------------+
```

> The success output above is the expected behaviour from the code. It could not be captured here because
> no MySQL server or JDBC driver is available in the environment — add your own screenshot after running it.

## Screenshot

Run the program and paste your own screenshot(s) in the `output/` folder (e.g. `output/screenshot1.png`).
The window could not be captured automatically because this environment has no display; the expected output above
is derived directly from the program's code.
