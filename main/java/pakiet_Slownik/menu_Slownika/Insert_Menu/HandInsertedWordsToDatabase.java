package pakiet_Slownik.menu_Slownika.Insert_Menu;
import pakiet_Slownik.GBC;
import pakiet_Slownik.ConnectToDatabase.Get_Database_Parameters;
import pakiet_Slownik.Ramka_Glowna;
import pakiet_Slownik.menu_Slownika.Manu_Admin.Panel_Menu_Regex;
import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class HandInsertedWordsToDatabase extends JDialog {
    private JPanel panel = new JPanel();

    private JLabel insertPolishWord = new JLabel("jêzyk polski");
    private JLabel insertEnglishWord = new JLabel("jêzyk angielski");
    private JLabel pageNumber = new JLabel("strona");

    private JTextField insertPolishWordT = new JTextField(10);
    private JTextField insertEnglishWordT = new JTextField(10);
    private JTextField pageNumberT = new JTextField(10);

    private JButton insertToDatabase = new JButton("WprowadŸ s³ówko do bazy");
    private JLabel showResult = new JLabel("Wynik operacji dodawania danych: ");
    private Font newFont = new Font("Arial",Font.PLAIN,30);
    private Statement st;

    public HandInsertedWordsToDatabase(JFrame owner) {
        super(owner,"Panel rêcznego wprowadzania s³ówek",true);

        this.add(panel);
        panel.setBorder(BorderFactory.createLineBorder(Color.green));
        GridBagLayout layout = new GridBagLayout();
        panel.setLayout(layout);

        panel.add(insertPolishWord,new GBC(0,0,1,1).setAnchor(GBC.CENTER).setInsets(3));
        insertPolishWord.setFont(newFont);
        panel.add(insertEnglishWord,new GBC(1,0,1,1).setAnchor(GBC.CENTER).setInsets(3));
        insertEnglishWord.setFont(newFont);
        panel.add(pageNumber,new GBC(2,0,1,1).setAnchor(GBC.CENTER).setInsets(3));
        pageNumber.setFont(newFont);

        panel.add(insertPolishWordT,new GBC(0,1,1,1).setInsets(3));
        insertPolishWordT.setFont(newFont);
        panel.add(insertEnglishWordT,new GBC(1,1,1,1).setWeight(100,10).setFill(GBC.HORIZONTAL).setInsets(3));
        insertEnglishWordT.setFont(newFont);
        panel.add(pageNumberT,new GBC(2,1,1,1).setWeight(100,10).setFill(GBC.HORIZONTAL).setInsets(3));
        pageNumberT.setFont(newFont);

        panel.add(showResult,new GBC(0,2,3,1).setInsets(3).setWeight(100,10).setFill(GBC.HORIZONTAL));
        showResult.setBorder(BorderFactory.createLineBorder(Color.blue));
        panel.add(insertToDatabase,new GBC(3,0,1,3).setInsets(3).setWeight(100,10).setFill(GBC.BOTH));
        showResult.setForeground(Color.BLACK);

        //Przypisanie ikonek do przycisk?w
        //ImageIcon iconEnter = new ImageIcon("./src/main/resources/ImageFile/insert1.png");
        ImageIcon iconEnter = new ImageIcon(Ramka_Glowna.class.getResource("/ImageFile/insert1.png"));
        insertToDatabase.setSize(60,60);
        if(iconEnter.getIconWidth() > insertToDatabase.getWidth()){
            iconEnter = new ImageIcon(iconEnter.getImage().getScaledInstance(insertToDatabase.getWidth(),-1,Image.SCALE_DEFAULT));
        }
        insertToDatabase.setIcon(iconEnter);

        insertToDatabase.addActionListener (

                (event)-> {
                    try {

                        st = Get_Database_Parameters.getStart_Logic_Class(true);

                        String pol = insertPolishWordT.getText();
                        String eng = insertEnglishWordT.getText();
                        String page = pageNumberT.getText();

                        if (pol.equals("") || eng.equals("") || page.equals("")) {
                            JOptionPane.showMessageDialog(null, "Proszê wype³niæ brakuj¹ce pola");
                            return;
                        } else {
                            boolean polB = Get_Database_Parameters.getOnlyLetter(pol);
                            boolean angB = Get_Database_Parameters.getOnlyLetter(eng);
                            boolean pageB = Get_Database_Parameters.getOnlyNumber(page);

                            if ((polB != true) || (angB != true)) {
                                JOptionPane.showMessageDialog(null, "W pole 1 i 2 mo¿na wprowadzaæ tylko litery");
                                return;
                            }
                            if (!pageB) {
                                JOptionPane.showMessageDialog(null, "W pole 3 mo¿na wprowadzaæ tylko cyfry");
                                return;
                            }
                        }

                        String commandInsert = "insert into " + Get_Database_Parameters.getTableName() + "(polish,english,page) values('" + pol + "','" + eng + "'," + page + ")";
                        String commandSelectAll = "select * from " + Get_Database_Parameters.getTableName();

                        try {
                            ResultSet resultSet = st.executeQuery(commandSelectAll);
                            List<String> listAllPolishWord = new ArrayList<String>();
                            while (resultSet.next()) {
                                String tempPol = resultSet.getString(2);
                                listAllPolishWord.add(tempPol);
                            }

                            List<String> allRepeatWordsInBase = Panel_Menu_Regex.setCorrectInsertDateToDatabase(pol, listAllPolishWord);

                            if (allRepeatWordsInBase.size() > 0) {//Je?eli s?owo powtarza si? w bazie lub jest zbli?one logicznie
                                // wy?wietl zawarto?? i spytaj czy doda? do bazy
                                JDialog dg = new JDialog(this, "Lista", false);
                                JScrollPane scrollPane = new JScrollPane();
                                GridBagLayout layoutDg = new GridBagLayout();
                                dg.setLayout(layoutDg);

                                JLabel label = new JLabel("Lista Podobnych s³ówek");
                                JTextArea area = new JTextArea(10, 10);
                                scrollPane.setViewportView(area);

                                JButton addToBase = new JButton("dodaj s³ówko");
                                JButton omitAndExit = new JButton("pomiñ");
                                dg.add(label, new GBC(0, 0, 1, 1));
                                dg.add(scrollPane, new GBC(0, 1, 1, 1));
                                dg.add(addToBase, new GBC(0, 2, 1, 1));
                                dg.add(omitAndExit, new GBC(0, 3, 1, 1));
                                dg.setVisible(true);
                                dg.setAlwaysOnTop(true);
                                dg.pack();

                                for (String el : allRepeatWordsInBase) {
                                    area.append(el + "\n");
                                }

                                addToBase.addActionListener(
                                        (eventDg) -> {
                                            try {
                                                st.executeUpdate(commandInsert);
                                                dg.setVisible(false);
                                                showResult.setText("Zapisano dane w bazie");
                                                showResult.setForeground(Color.green);
                                                clearAllFiled(insertPolishWordT, insertEnglishWordT, pageNumberT);
                                                dg.revalidate();
                                                st.close();
                                            } catch (SQLException sqlDgExc) {
                                                JOptionPane.showMessageDialog(null, "sqlDg: " + sqlDgExc.getMessage());
                                            }
                                        }
                                );
                                omitAndExit.addActionListener(
                                        (eventOm) -> {
                                            dg.setVisible(false);
                                            showResult.setText("Nie zapisano powtarzaj¹cych siê danych");
                                            showResult.setForeground(Color.RED);
                                            clearAllFiled(insertPolishWordT, insertEnglishWordT, pageNumberT);
                                            dg.revalidate();
                                            try {
                                                st.close();
                                            } catch (SQLException sqlExc) {
                                                JOptionPane.showMessageDialog(null, "omitButton: " + sqlExc.getMessage());
                                            }
                                            return;
                                        }
                                );

                            } else {
                                st.executeUpdate(commandInsert);
                                st.close();
                                showResult.setText("Zapisano dane w bazie");
                                showResult.setForeground(Color.green);
                                clearAllFiled(insertPolishWordT, insertEnglishWordT, pageNumberT);
                            }

                        } catch (SQLException sqlExc) {
                            JOptionPane.showMessageDialog(null, sqlExc.getMessage());
                            sqlExc.printStackTrace();
                        }
                        this.validate();
                    }catch(FileNotFoundException fileExc){ JOptionPane.showMessageDialog(null,"Blad metody getStart_Logic_Class(true) z klasy Get_Database_Parameters");}
                }
        );

        this.pack();
        this.setVisible(true);
    }

    public void clearAllFiled(JTextField f1,JTextField f2,JTextField f3){
        f1.setText(""); f2.setText(""); f3.setText("");
    }
}
