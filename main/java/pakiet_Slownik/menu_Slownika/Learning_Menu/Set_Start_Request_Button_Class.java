package pakiet_Slownik.menu_Slownika.Learning_Menu;

import pakiet_Slownik.ConnectToDatabase.Get_Database_Parameters;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Set_Start_Request_Button_Class {


    protected static boolean correctInsertFields(String howMuchWords,String pageNumber,JTextArea showResult) throws FileNotFoundException, SQLException {

        if(howMuchWords.equals("") && pageNumber.equals("")){
            JOptionPane.showMessageDialog(null,"Proszê uzupe³niæ wymagane pola");
            return false;
        }

        if(!Get_Database_Parameters.getOnlyNumber(howMuchWords)){
            JOptionPane.showMessageDialog(null,"Proszê w miejsce pola SLOWEK wprowadziæ cyfry");
            return false;
        }

        if(!Get_Database_Parameters.getOnlyNumber(pageNumber)){
            JOptionPane.showMessageDialog(null,"Proszê w miejsce pola STRONY wprowadziæ cyfry");
            return false;
        }

        boolean correctInsertPageVal = checkCorrectInsertedPage(Integer.parseInt(pageNumber),getAllPages());
        int howMuchWord;
        if(correctInsertPageVal){
            howMuchWord = howMuchWordsOnThisPage(Integer.parseInt(pageNumber));
            if(Integer.parseInt(howMuchWords) <= howMuchWord){
                return true;
            }else{
                showResult.setText("Wskazana strona przechowuje "+howMuchWordsOnThisPage(Integer.parseInt(pageNumber))+" s³ówek, proszê poprawiæ");
                showResult.setForeground(Color.RED);
                return false;
            }
        }else{
            showResult.setText("Proszê wprowadziæ prawid³ow¹ wartoœæ strony dostêpne to:\n");
            for(int el: getAllPages()){
                showResult.append(el+"\n");
            }
            showResult.setForeground(Color.RED);
            return false;
        }
    }

    protected static List<Integer> getAllPages() {
        List<Integer> listVal = new ArrayList<>();
        try {
            Statement st = Get_Database_Parameters.getStart_Logic_Class(true);
            String commandSql = "select page from " + Get_Database_Parameters.getTableName();
            ResultSet rs = st.executeQuery(commandSql);


            while (rs.next()) {
                listVal.add(Integer.parseInt(rs.getString(1)));
            }

            listVal = listVal.stream().sorted().sorted(new Comparator<Integer>() {
                @Override
                public int compare(Integer o1, Integer o2) {
                    o1.compareTo(o2);
                    return 0;
                }
            }).filter(new Predicate<Integer>() {
                int temp;

                @Override
                public boolean test(Integer integer) {
                    if (temp == integer) {
                        temp = integer;
                        return false;
                    } else {
                        temp = integer;
                        return true;
                    }
                }
            }).collect(Collectors.toList());
        }catch(FileNotFoundException fileExc){
            JOptionPane.showMessageDialog(null,fileExc.getMessage());
        }catch(SQLException sqlExc){
            JOptionPane.showMessageDialog(null,sqlExc.getMessage());
        }

        return listVal;
    }

    protected static int howMuchWordsOnThisPage(int pageNumber){
        List<String> lista = new ArrayList<>();
        try {
            String commandSql = "select * from " + Get_Database_Parameters.getTableName() + " where page = " + pageNumber;
            Statement st = Get_Database_Parameters.getStart_Logic_Class(true);
            ResultSet rs = st.executeQuery(commandSql);

            while (rs.next()) {
                lista.add(rs.getString(1));
            }

        }catch(FileNotFoundException fileExc){
            JOptionPane.showMessageDialog(null,fileExc.getMessage());
        }catch(SQLException sqlExc){JOptionPane.showMessageDialog(null,sqlExc.getMessage());}
        return lista.size();
    }

    protected static boolean checkCorrectInsertedPage(int page,List<Integer> listAllPages){
        return listAllPages.stream().anyMatch(new Predicate<Integer>() {
            @Override
            public boolean test(Integer integer) {
                if(integer == page){
                    return true;
                }else {
                    return false;
                }
            }
        });
    }

}
