Using MySQL

If you're working with MySQL, follow these steps:

    Make sure MySQL is installed and running.

    Open your terminal or command prompt.

    Log into MySQL (you may need to provide your username and password):

    bash

        '''mysql -u your_username -p'''

Once logged in, create the database if it doesn't exist:

sql

        '''CREATE DATABASE IF NOT EXISTS chatapp_db;'''

Then, exit MySQL, and run the following command to execute the SQL script on the MySQL server:

bash

        '''mysql -u your_username -p chatapp_db < script.sql'''

    Replace your_username with your MySQL username.
    chatapp_db is the name of the database specified in the SQL code

OR if you are in the workbench, if you have already created the database, you can create the tables by copying and running the following in the query panel of the app.

        '''USE chatapp_db;'''

then copy the sql code in schema.sql and run it in the query panel of mysql workbench.