package pakiet_Slownik.menu_Slownika.Learning_Menu;

import pakiet_Slownik.ConnectToDatabase.Get_Database_Parameters;
import javax.swing.*;
import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class Dictionary_Polish_English_Words implements Get_Dictionary_From_Database{
    private static Statement st;
    private static List<String> list;

    public Dictionary_Polish_English_Words(List<String> list) throws FileNotFoundException {
        this.list = list;
        this.st = Get_Database_Parameters.getStart_Logic_Class(true);
    }

    public List<String> getArrayList(int howMuchWord,int pageNumber,boolean tableOrFraze) {

       if(tableOrFraze){
           try {
               String commandSql = "select * from " + Get_Database_Parameters.getTableName() + " where page = " + pageNumber + " limit " + howMuchWord;
               list = getDownloadWords(commandSql);
           }catch(FileNotFoundException fileExc){
               JOptionPane.showMessageDialog(null,fileExc.getMessage());
           }catch(SQLException sqlExc){ JOptionPane.showMessageDialog(null,sqlExc.getMessage());}
           return list;
       }else{
           try {
               String commandSql = "select * from " + Get_Database_Parameters.getTablePhraseName() + " where page = " + pageNumber + " limit " + howMuchWord;
               list = getDownloadWords(commandSql);
           }catch(FileNotFoundException fileExc){
               JOptionPane.showMessageDialog(null,fileExc.getMessage());
           }catch(SQLException sqlExc){ JOptionPane.showMessageDialog(null,sqlExc.getMessage());}
           return list;
       }

    }

    private static List<String> getDownloadWords(String commandSql) throws SQLException {
        ResultSet rs = st.executeQuery(commandSql);

        while(rs.next()){
            String lp = rs.getString(1);
            String polish = rs.getString(2);
            String english = rs.getString(3);
            String page = rs.getString(4);
            list.add(lp+"-"+polish+"-"+english+"-"+page);
        }
        return list;
    }
}


