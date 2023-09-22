# Expense Tracking Application


## Description
The Expense Tracking Application is a simple JavaFx-based application to help people manage their personal expenses efficiently.
The application provides an interface to input, categorize, and track expenses, helping users maintain financial records.

## Prerequisites for launching
 - Maven
 - MySQL
 - IntelliJ 

## Launch procedure
Git clone this repository locally.
Verify that MySQL is running on the target system.
Create a database: expense_manager and tables: transactions and user. (All provided in MySQL Dump file)
For testing purposes, the Dump file also contains test users and passwords to log into the application. 
**IMPORTANT** To successfully connect to the database, change the default password and username ("root") in the ExpenseManagerApp class to your MySQL password and username.

<img width="772" alt="Screenshot 2023-09-21 at 16 00 23" src="https://github.com/emilsViksnins/Practical_Project/assets/135007928/013dad30-375c-4c9d-90d3-288cec42b5dc">


- **NOTE!** For adding new persons in the users table you need:
       1. Drop the Foreign Key.
       2. Truncate table users and/or transactions.
       3. Add the Foreign Key constraint back.

## Features
- Categories for organizing expenses(e.g., food, utilities,).
- Data storage in a MySQL database.
- Viewing and editing of expense records.
**NOTE** Since this is personal practicing project, passwords are being stored in plaintext in a database.
  Instead, in the real time project, they should be hashed (and ideally salted) before being stored.

## Screenshots

<img width="698" alt="Screenshot 2023-09-21 at 15 35 31" src="https://github.com/emilsViksnins/Practical_Project/assets/135007928/880f2196-b7d3-4b71-8e6a-72ec057d8e79">


<img width="906" alt="Screenshot 2023-09-21 at 15 35 10" src="https://github.com/emilsViksnins/Practical_Project/assets/135007928/f5538527-3f98-4389-a412-9bb364447933">

## Technologies Used

- MySQL
- JavaFX
- Java version 20


## How to Contribute
This is my personal practicing project.

## Author
Emils Viksnins


