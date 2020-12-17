package Connection;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionUtil {
    public static Connection connectDB(String Schema)
    {
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/"+Schema+"?useSSL=false","root","1234");
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, e);
            return null;
        }
    }
}