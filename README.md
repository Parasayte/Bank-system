# Banking System Project [![java](https://img.shields.io/badge/Project-maroon?style=plastic&label=Java&labelColor=gray)](https://www.java.com/en/)

## Overview
This Banking System project is a Java-based application that allows users to manage their bank accounts through a graphical user interface (GUI). Users can register, log in, check their balance, deposit money, withdraw money, and transfer funds.

## Features
- **User Registration**: Create a new account.
- **User Login**: Log in to your account.
- **Balance Inquiry**: Check the current account balance.
- **Deposit**: Add money to the account.
- **Withdrawal**: Remove money from the account.
- **Money Transfer**: Transfer funds between accounts.

## Technologies Used
- Java [![](https://img.shields.io/badge/Java-orangered?style=plastic&label=Java&labelColor=gray)](https://www.java.com/en/)
- Swing (for GUI) [![Swing](https://img.shields.io/badge/Swing-teal?style=plastic&label=Java&labelColor=gray)](https://docs.oracle.com/javase/tutorial/uiswing/)
- MySQL (for database management) [![](https://img.shields.io/badge/SQL-crimson?style=plastic&label=MySql&labelColor=gray)](https://www.mysql.com/)

## Setup Instructions


1. **Database Setup**:
   - Create a MySQL database named `banking`.
   - Create a table named `users` using the following SQL commands:
![image](https://thumbs2.imgbox.com/6a/57/tdQoz49T_t.png).
   ```sql
   CREATE DATABASE banking;
   USE banking;
   CREATE TABLE users (
       id INT AUTO_INCREMENT PRIMARY KEY,
       username VARCHAR(255) NOT NULL UNIQUE,
       password VARCHAR(255) NOT NULL,
       balance DECIMAL(10, 2) DEFAULT 0
   );

 **[Watch the Banking System Project](https://youtu.be/TaL1iPhSQP8)**.

