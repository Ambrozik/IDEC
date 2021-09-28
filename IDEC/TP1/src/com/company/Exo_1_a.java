package com.company;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Exo_1_a {
    static String DB_SERVER = "jdbc:mysql://localhost:3306/";
    static String DB = "idec";
    static String DRIVER = "com.mysql.cj.jdbc.Driver";
    static String USER_NAME = "root";
    static String USER_PWD = "";
    static String csvFilePath = "res.csv";
    

    public static void main(String[] args) {
       
        try {
            Class.forName(DRIVER);
            Connection con = DriverManager.getConnection(DB_SERVER+DB, USER_NAME, USER_PWD);
            String myQuery = "SELECT * FROM idec ;" ;
            Statement st = con.createStatement();
            ResultSet res = st.executeQuery(myQuery);

            BufferedWriter fileWriter = new BufferedWriter(new FileWriter(csvFilePath));
            fileWriter.write("id,nom,prenom,age,tel,ville,email");
            while(res.next()){
                String line = String.format("%d,\'%s\',\'%s\',%d,%d,%s,%s",
                        res.getInt("id"),
                        res.getString("nom"),
                        res.getString("prenom"),
                        res.getInt("age"),
                        res.getInt("tel"),
                        res.getString("ville"),
                        res.getString("email"));
                fileWriter.newLine();
                fileWriter.write(line);
            }

            st.close();
            fileWriter.close();
        }catch(ClassNotFoundException e){
            System.err.println("Could not load JDBC driver");
            System.out.println("Exception: " + e);
        }
        catch (SQLException ex){
            System.err.println("SQLException information");
            while(ex!=null){
                System.err.println("Error msg: " + ex.getMessage());
                System.err.println("SQLSTATE: " + ex.getSQLState());
                System.err.println("Error code: " + ex.getErrorCode());
                ex.printStackTrace();
                ex = ex.getNextException();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
