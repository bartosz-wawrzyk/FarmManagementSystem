package com.example.farmmanagementsystem;

import java.sql.Connection;
import java.sql.DriverManager;

public class database {
    // Connection conn = null;
    public static Connection connectDb(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connect =
                    DriverManager.getConnection("jdbc:mysql://localhost/farmmanagmentsystem", "root", "");
            return connect;
        }catch(Exception e){e.printStackTrace();
            return null;
        }
    }
}
