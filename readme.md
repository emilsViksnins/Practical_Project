# Expense Tracking Application


## Description
The Expense Tracking Application is a simple JavaFx based application to help people to manage their personal expenses efficiently.
Application provides interface to input, categorize, and track expenses, helping users maintain financial records.

## Prerequisites for launching
 - Maven
 - MySQL
 - JavaFX 

## Launch procedure
- Git clone this repository locally.
- Verify that MySQL is running on the target system.
- Create a database: expense_manager and tables: transactions and user .(All provided in MySQL Dump file)
  For testing purposes, Dump file also contains test users and passwords to log into application. 
 _**IMPORTANT**_
 To successfully connect to the database, change default password and username ("root") in ExpenseManagerApp class to your MySQL password and username.

![Screenshot 2023-09-21 at 16.00.23.png](..%2F..%2F..%2FDesktop%2FScreenshot%202023-09-21%20at%2016.00.23.png)

- **NOTE!** For adding new persons in users table you need:
       1. Drop the Foreign Key.
       2. Truncate table users and/or transactions.
       3. Add the Foreign Key constrain back.

## Features
- Categories for organizing expenses(e.g., food, utilities,).
- Data storage in a MySQL database.
- Viewing and editing of expense records.

## Screenshots

![Screenshot 2023-09-21 at 15.35.31.png](..%2F..%2F..%2FDesktop%2FScreenshot%202023-09-21%20at%2015.35.31.png)

![Screenshot 2023-09-21 at 15.35.10.png](..%2F..%2F..%2FDesktop%2FScreenshot%202023-09-21%20at%2015.35.10.png)