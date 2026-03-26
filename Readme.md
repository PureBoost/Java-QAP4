# Description of Project
This project is a Java console application that shows two types of data persistence: file I/O and database I/O. Drug records are saved to and read from a text file, while Patient records are saved to, read from, and deleted from a PostgreSQL database using JDBC. The program uses a scanner-based menu so users can choose each operation interactively.

## How It Went
This project helped me understand the difference between saving data to a text file and saving data to a PostgreSQL database with JDBC. The hardest part was setting up the database connection and getting the SQL table ready, but once that was done the menu flow worked well. Overall, it was good practice for building a full Java console app with real data persistence.

# How to Use Program

## 1. Requirements
1. Java JDK installed (so `javac` and `java` work in terminal)
2. PostgreSQL running on your machine
3. PostgreSQL JDBC driver file in this folder: `postgresql-42.7.10.jar`

## 2. Database Setup (One Time)
1. Open pgAdmin and connect to your local PostgreSQL server.
2. Create database `qap4` if it does not exist.
3. Run the SQL script in `create_database.sql` to create the `patient` table.

## 3. Compile
Run this command in the project folder:

javac -cp ".;postgresql-42.7.10.jar" Main.java Drug.java Patient.java

## 4. Run
Run this command in the project folder:

java -cp ".;postgresql-42.7.10.jar" Main

# Q & A

## How many hours did it take for you to complete this assessment?
I think it took about 5 hours

## What Online Resources did you use?
I used Lectures and W3Schools

## Did you get help from any classmates?
No.

## Did you ask for  help from an instructor?
No.

## Rate The Difficulty of each problem and your confidence in solving similar problems in the future.
I felt that the assignment was not too difficult and i could do similar problems.  

## Screenshots
Screenshots of Drug and Patient files being used

### Drug file read/write
![Drug file read/write](Screenshots/Drug%20Read%20and%20Write.jpg)

### Patient database read/write
![Patient database read/write](Screenshots/Patient%20Read%20and%20Write.jpg)