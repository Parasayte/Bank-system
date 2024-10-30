# Banking System Project

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
- Java ![Java](https://img.shields.io/badge/Java-%23ED8B00.svg?logo=openjdk&logoColor=white)
- Swing (for GUI) ![Swing](https://img.shields.io/badge/-gray?style=plastic&logo=java&logoColor=white&logoSize=auto&label=Swing&labelColor=maroon)
- MySQL (for database management) ![](https://img.shields.io/badge/MySql-gray?style=plastic&logo=mysql&logoColor=white&logoSize=auto&labelColor=indigo)

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

