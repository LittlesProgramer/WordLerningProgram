package pakiet_Slownik.menu_Slownika.Delete_Menu;
import pakiet_Slownik.ConnectToDatabase.Get_Database_Parameters;
import pakiet_Slownik.GBC;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DeletePhraseFromDatabase extends JDialog{

    private JPanel panel = new JPanel();
    private ButtonGroup grupsButton = new ButtonGroup();
    private JRadioButton radioPolish = new JRadioButton("polski",true);
    private JRadioButton radioEnglish = new JRadioButton("angielski",false);

    private JLabel searchPhrase = new JLabel("Wyszukaj fraze");
    private JTextField searchPhraseT = new JTextField(25);
    private JButton search = new JButton("Wyszukaj");
    private JButton searchAnySimilarPhrases = new JButton("ZnajdŸ wszystkie frazy");

    private JTextArea showResult = new JTextArea(10,10);
    private JTextField insertDeletePhrase = new JTextField(10);
    private JButton deletePhrase = new JButton("Usuñ wybran¹ frazê");
    private Font newFont = new Font("Arial",Font.PLAIN,30);
    private Statement st = null; //na obiekcie st wykonuje siê polecenia SQL
    private ResultSet rs = null; //obiekt przechowuj¹cy wszystkie wyniki zapytania select

    public DeletePhraseFromDatabase(JFrame owner) throws FileNotFoundException {
        super(owner,"Panel do usuwania fraz z bazy danych",false);
        this.add(panel);
        GridBagLayout layout = new GridBagLayout();
        panel.setLayout(layout);
        panel.setBorder(BorderFactory.createLineBorder(Color.GREEN));
        grupsButton.add(radioPolish);
        grupsButton.add(radioEnglish);

        panel.add(radioPolish,new GBC(1,0,1,1).setWeight(100,10).setAnchor(GBC.CENTER).setInsets(3));
        panel.add(radioEnglish,new GBC(1,1,1,1).setWeight(100,10).setAnchor(GBC.CENTER).setInsets(3));
        panel.add(searchPhrase,new GBC(0,2,1,1).setWeight(100,10).setAnchor(GBC.CENTER).setInsets(3));
        panel.add(searchPhraseT,new GBC(1,2,1,1).setWeight(100,10).setFill(GBC.HORIZONTAL).setAnchor(GBC.CENTER).setInsets(3));
        searchPhraseT.setFont(newFont);
        panel.add(search,new GBC(2,2,1,1).setWeight(100,10).setFill(GBC.HORIZONTAL).setAnchor(GBC.CENTER).setInsets(3));
        panel.add(searchAnySimilarPhrases,new GBC(0,3,3,1).setWeight(100,10).setFill(GBC.HORIZONTAL).setAnchor(GBC.CENTER).setInsets(3));

        panel.add(showResult,new GBC(0,4,3,1).setWeight(100,100).setFill(GBC.BOTH).setAnchor(GBC.CENTER).setInsets(3));
        panel.add(insertDeletePhrase,new GBC(0,5,2,1).setWeight(100,10).setFill(GBC.HORIZONTAL).setAnchor(GBC.CENTER).setInsets(3));
        insertDeletePhrase.setFont(newFont);
        panel.add(deletePhrase,new GBC(2,5,1,1).setWeight(100,10).setFill(GBC.HORIZONTAL).setAnchor(GBC.CENTER).setInsets(3));

        //Przypisanie ikonek do przycisków
        ImageIcon iconEnter = new ImageIcon("main/resources/ImageFile/delete_1.png");
        deletePhrase.setSize(30,30);
        if(iconEnter.getIconWidth() > deletePhrase.getWidth()){
            iconEnter = new ImageIcon(iconEnter.getImage().getScaledInstance(deletePhrase.getWidth(),-1,Image.SCALE_DEFAULT));
        }
        deletePhrase.setIcon(iconEnter);

        st = Get_Database_Parameters.getStart_Logic_Class(true);

        search.addActionListener(
                (event)->{
                    try {

                        //Okrelenie czy pole searchWord jest puste czy zawiera wyraz
                        if(searchPhraseT.getText().equals("") && radioPolish.isSelected()){
                            JOptionPane.showMessageDialog(null,"Proszê wprowadziæ frazê w jêzyku polskim"); return;
                        }else if(searchPhraseT.getText().equals("") && (!radioPolish.isSelected())){
                            JOptionPane.showMessageDialog(null,"Proszê wprowadziæ frazê w jêzyku angielskim"); return;
                        }

                        if(searchWordFromJText(rs, radioPolish, searchPhraseT.getText())){
                            showResult.setText("s³owo znajduje siê w bazie");
                            showResult.setForeground(Color.green);
                        }else{
                            showResult.setText("brak podanego s³owa w bazie");
                            showResult.setForeground(Color.red);
                        }

                    }catch(FileNotFoundException fileExc){
                        JOptionPane.showMessageDialog(null,"Brak pliku: "+fileExc.getMessage());
                    }catch(SQLException sqlExc){ JOptionPane.showMessageDialog(null,"sqlExc: "+sqlExc.getMessage());}
                }
        );

        searchAnySimilarPhrases.addActionListener(
                (event)->{
                    try {

                        //Okreœlenie czy pole searchWord jest puste czy zawiera wyraz
                        if(searchPhraseT.getText().equals("") && radioPolish.isSelected()){
                            JOptionPane.showMessageDialog(null,"Proszê wprowadziæ frazê w jêzyku polskim"); return;
                        }else if(searchPhraseT.getText().equals("") && (!radioPolish.isSelected())){
                            JOptionPane.showMessageDialog(null,"Proszê wprowadziæ frazê w jêzyku angielskim"); return;
                        }

                        List<String> listAnySimilarWords;
                        if ((listAnySimilarWords = searchAnySimilarWords(rs, radioPolish, searchPhraseT.getText())) != null) {

                            showResult.setText("");
                            showResult.setForeground(Color.BLACK);
                            for(String words: listAnySimilarWords){
                                showResult.append(words+"\n");
                            }

                        } else {
                            showResult.setText("Brak s³ówka w bazie danych");
                            showResult.setForeground(Color.red);
                        }

                    }catch(SQLException sqlExc){
                        JOptionPane.showMessageDialog(null,sqlExc.getMessage());
                    }catch(FileNotFoundException fileExc){ JOptionPane.showMessageDialog(null,fileExc.getMessage() );}
                }
        );

        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentHidden(ComponentEvent e) {
                try {
                    thisWindowsHiddenClosedStatement();
                }catch(SQLException sqlExc){ JOptionPane.showMessageDialog(null,sqlExc.getMessage() );}
            }
        });


        this.pack();
        this.setVisible(true);
    }

    //zwraca true jeœli s³owo znajduje sie w bazie w przeciwnym wypadku false
    private boolean searchWordFromJText(ResultSet rs, JRadioButton radioPolish, String jText) throws FileNotFoundException, SQLException {

        String command = null;
        String tableName = Get_Database_Parameters.getTablePhraseName();

        if(radioPolish.isSelected()){

            command = "select polishPhrase from "+tableName+" where polishPhrase = "+"'"+jText+"'";
            rs = null;
            rs = st.executeQuery(command);

            if(rs.next()){
                return true;
            }else return false;

        }else{

            command = "select englishPhrase from "+tableName+" where englishPhrase = "+"'"+jText+"'";
            rs = st.executeQuery(command);

            if(rs.next()){
                return true;
            }else return false;

        }
    }

    //zwraca listê wszystkich podobnych s³ówek
    private java.util.List<String> searchAnySimilarWords(ResultSet rs, JRadioButton radioPolish, String jText) throws FileNotFoundException,SQLException{
        List<String> listAnySimilarWords = null;
        String command = null;
        String tablePhraseName = Get_Database_Parameters.getTablePhraseName();

        if(radioPolish.isSelected()){

            command = "select * from "+tablePhraseName+" where polishPhrase like "+"'%"+jText+"%'";
            rs = null;
            rs = st.executeQuery(command);

            if(rs.next()){
                listAnySimilarWords = new ArrayList<String>();

                String pol = rs.getString(2);
                String eng = rs.getString(3);
                listAnySimilarWords.add(pol+"-"+eng);

                while(rs.next()){
                    pol = rs.getString(2);
                    eng = rs.getString(3);
                    listAnySimilarWords.add(pol+"-"+eng);
                }

            }else{showResult.setText("Brak s³ówka w bazie danych"); return null; }

            return  listAnySimilarWords;

        }else{

            command = "select * from "+tablePhraseName+" where englishPhrase like "+"'%"+jText+"%'";
            rs = null;
            rs = st.executeQuery(command);

            if(rs.next()){
                listAnySimilarWords = new ArrayList<String>();

                String pol = rs.getString(2);
                String eng = rs.getString(3);
                listAnySimilarWords.add(eng+"-"+pol);

                while(rs.next()){
                    pol = rs.getString(2);
                    eng = rs.getString(3);
                    listAnySimilarWords.add(eng+"-"+pol);
                }

            }else{showResult.setText("Brak s³ówka w bazie danych"); return null; }

            return  listAnySimilarWords;
        }
    }

    private void thisWindowsHiddenClosedStatement() throws SQLException{
        st.close();
    }
}
