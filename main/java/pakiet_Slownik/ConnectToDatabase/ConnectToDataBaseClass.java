package pakiet_Slownik.ConnectToDatabase;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


@SuppressWarnings("SpellCheckingInspection")
public class ConnectToDataBaseClass {
    private static Statement st = null;
    private static String connectOk = null;

    public static Statement getConnectToDataBaseClass(String dataBaseName, String userName, String userPass) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            //Class.forName("com.mysql.cj.jdbc.Driver"); //by³o  bez zmiennej className
            //Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+dataBaseName,userName,userPass);
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+dataBaseName+"?useUnicode=true&characterEncoding=UTF-8",userName,userPass);
            st = connection.createStatement();
        }catch(ClassNotFoundException classExc){
            connectOk = "nie mo¿na odnaleŸæ Klasy Sterownika Bazy";
            JOptionPane.showMessageDialog(null,classExc.getMessage());
        }catch(SQLException sqlExc){
            connectOk = "b³êdne dane dla DriverMenagera sterownika bazy danych";
            JOptionPane.showMessageDialog(null,sqlExc.getMessage());
        }

        connectOk = "po³¹czenie istnieje";
        return st;
    }

    public static String getConnectString(){ return connectOk; }
}
