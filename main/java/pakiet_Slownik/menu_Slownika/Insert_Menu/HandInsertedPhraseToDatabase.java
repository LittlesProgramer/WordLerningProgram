package pakiet_Slownik.menu_Slownika.Insert_Menu;

import pakiet_Slownik.ConnectToDatabase.Get_Database_Parameters;
import pakiet_Slownik.GBC;
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

public class HandInsertedPhraseToDatabase extends JDialog {
    private JPanel panel = new JPanel();

    private JLabel insertPolishPhrase = new JLabel("jêzyk polski");
    private JLabel insertEnglishPhrase = new JLabel("jêzyk angielski");
    private JLabel pageNumber = new JLabel("strona");

    private JTextField insertPolishPhraseT = new JTextField(25);
    private JTextField insertEnglishPhraseT = new JTextField(25);
    private JTextField pageNumberT = new JTextField(10);

    private JButton insertToDatabase = new JButton("WprowadŸ fraze do bazy");
    private JLabel showResult = new JLabel("Wynik operacji wprowadzania fraz literowych: ");
    private Font newFont = new Font("Arial", Font.PLAIN, 30);
    private Statement st;

    public HandInsertedPhraseToDatabase(JFrame owner) {
        super(owner, "Panel rêcznego wprowadzania fraz literowych", true);
        GridBagLayout layout = new GridBagLayout();
        this.add(panel);
        panel.setLayout(layout);
        panel.setBorder(BorderFactory.createLineBorder(Color.GREEN));

        panel.add(insertPolishPhrase, new GBC(0, 0, 1, 1).setAnchor(GBC.CENTER).setInsets(3));
        insertPolishPhrase.setFont(newFont);
        panel.add(insertEnglishPhrase, new GBC(1, 0, 1, 1).setAnchor(GBC.CENTER).setInsets(3));
        insertEnglishPhrase.setFont(newFont);
        panel.add(pageNumber, new GBC(2, 0, 1, 1).setAnchor(GBC.CENTER).setInsets(3));
        pageNumber.setFont(newFont);

        panel.add(insertPolishPhraseT, new GBC(0, 1, 1, 1).setInsets(3));
        insertPolishPhraseT.setFont(newFont);
        panel.add(insertEnglishPhraseT, new GBC(1, 1, 1, 1).setWeight(100, 10).setFill(GBC.HORIZONTAL).setInsets(3));
        insertEnglishPhraseT.setFont(newFont);
        panel.add(pageNumberT, new GBC(2, 1, 1, 1).setWeight(100, 10).setFill(GBC.HORIZONTAL).setInsets(3));
        pageNumberT.setFont(newFont);

        panel.add(showResult, new GBC(0, 2, 3, 1).setInsets(3).setWeight(100, 10).setFill(GBC.HORIZONTAL));
        showResult.setBorder(BorderFactory.createLineBorder(Color.blue));
        panel.add(insertToDatabase, new GBC(3, 0, 1, 3).setInsets(3).setWeight(100, 10).setFill(GBC.BOTH));

        //Przypisanie ikonek do przycisków
        //ImageIcon iconEnter = new ImageIcon("./src/main/resources/ImageFile/insert1.png");
        ImageIcon iconEnter = new ImageIcon(Ramka_Glowna.class.getResource("/ImageFile/insert1.png"));
        insertToDatabase.setSize(60,60);
        if(iconEnter.getIconWidth() > insertToDatabase.getWidth()){
            iconEnter = new ImageIcon(iconEnter.getImage().getScaledInstance(insertToDatabase.getWidth(),-1,Image.SCALE_DEFAULT));
        }
        insertToDatabase.setIcon(iconEnter);

        insertToDatabase.addActionListener(

                (event) -> {
                    try {

                        st = Get_Database_Parameters.getStart_Logic_Class(true);

                        String pol = insertPolishPhraseT.getText();
                        String eng = insertEnglishPhraseT.getText();
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

                        String commandInsert = "insert into " + Get_Database_Parameters.getTablePhraseName() + "(polishPhrase,englishPhrase,page) values('" + pol + "','" + eng + "'," + page + ")";
                        String commandSelectAll = "select * from " + Get_Database_Parameters.getTablePhraseName();

                        try {
                            ResultSet resultSet = st.executeQuery(commandSelectAll);
                            java.util.List<String> listAllPolishPhrases = new ArrayList<String>();
                            while (resultSet.next()) {
                                String tempPol = resultSet.getString(2);
                                listAllPolishPhrases.add(tempPol);
                            }

                            List<String> allRepeatPhrasesInBase = Panel_Menu_Regex.setCorrectInsertDateToDatabase(pol, listAllPolishPhrases);

                            if (allRepeatPhrasesInBase.size() > 0) {//Je¿eli fraza powtarza siê w bazie lub jest zbli¿ona logicznie
                                // wyœwietl zawartoœæ i spytaj czy dodaæ do bazy
                                JDialog dg = new JDialog(this, "Lista", false);
                                JScrollPane scrollPane = new JScrollPane();
                                GridBagLayout layoutDg = new GridBagLayout();
                                dg.setLayout(layoutDg);

                                JLabel label = new JLabel("Lista Podobnych Fraz");
                                JTextArea area = new JTextArea(10, 10);
                                scrollPane.setViewportView(area);

                                JButton addToBase = new JButton("dodaj frazê");
                                JButton omitAndExit = new JButton("pomiñ");
                                dg.add(label, new GBC(0, 0, 1, 1));
                                dg.add(scrollPane, new GBC(0, 1, 1, 1));
                                dg.add(addToBase, new GBC(0, 2, 1, 1));
                                dg.add(omitAndExit, new GBC(0, 3, 1, 1));
                                dg.setVisible(true);
                                dg.setAlwaysOnTop(true);
                                dg.pack();

                                for (String el : allRepeatPhrasesInBase) {
                                    area.append(el + "\n");
                                }

                                addToBase.addActionListener(
                                        (eventDg) -> {
                                            try {
                                                st.executeUpdate(commandInsert);
                                                dg.setVisible(false);
                                                showResult.setText("Zapisano frazê w bazie");
                                                showResult.setForeground(Color.green);
                                                clearAllFiled(insertPolishPhraseT, insertEnglishPhraseT, pageNumberT);
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
                                            clearAllFiled(insertPolishPhraseT, insertEnglishPhraseT, pageNumberT);
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
                                clearAllFiled(insertPolishPhraseT, insertEnglishPhraseT, pageNumberT);
                            }

                        } catch (SQLException sqlExc) {
                            JOptionPane.showMessageDialog(null, sqlExc.getMessage());
                        }
                        this.validate();
                    } catch (FileNotFoundException fileExc) {
                        JOptionPane.showMessageDialog(null, "Blad metody getStart_Logic_Class(true) z klasy Get_Database_Parameters");
                    }
                }
        );

        this.pack();
        this.setVisible(true);
    }

    public void clearAllFiled(JTextField f1, JTextField f2, JTextField f3) {
        f1.setText(""); f2.setText(""); f3.setText("");
    }
}
