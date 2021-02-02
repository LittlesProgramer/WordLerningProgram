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

public class DeleteWordsFromDatabase extends JDialog {

    private JPanel panel = new JPanel();
    private ButtonGroup grupsButton = new ButtonGroup();
    private JRadioButton radioPolish = new JRadioButton("polski",true);
    private JRadioButton radioEnglish = new JRadioButton("angielski",false);

    private JLabel searchWord = new JLabel("Wyszukaj s��wko");
    private JTextField searchWordT = new JTextField(10);
    private JButton search = new JButton("Wyszukaj");
    private JButton searchAnySimilarWords = new JButton("Znajd� wszystkie podobne s��wka");

    private JTextArea showResult = new JTextArea(10,10);
    private JButton deleteWord = new JButton("Usu� wybrane s��wko");
    private JTextField wybranaWartoscT = new JTextField(3);
    private Font newFont = new Font("Arial",Font.PLAIN,30);

    private Statement st ; //na obiekcie st wykonuje si� polecenia SQL
    private ResultSet rs = null; //obiekt przechowuj�cy wszystkie wyniki zapytania select
    private Boolean bool = null; //zmienna logiczna m�wi�ca czy mo�na usun�� dany wyraz
    private static List<Integer> howMuchSimilarWords = new ArrayList<>(); //zmienna opisuj?ca ilo?? podobnych s??wek ale z r?nym obcym t?umaczeniem np levy - pobiera?, levy - sci?ga?
    private static int wybranaWartosc = 0;

    @SuppressWarnings("SpellCheckingInspection")
    public DeleteWordsFromDatabase(JFrame owner) throws FileNotFoundException{
        super(owner,"Panel do usuwania wyraz�w z bazy danych",false);
        this.add(panel);
        GridBagLayout layout = new GridBagLayout();
        panel.setLayout(layout);
        panel.setBorder(BorderFactory.createLineBorder(Color.GREEN));
        grupsButton.add(radioPolish);
        grupsButton.add(radioEnglish);

        panel.add(radioPolish,new GBC(1,0,1,1).setWeight(100,10).setAnchor(GBC.CENTER).setInsets(3));
        panel.add(radioEnglish,new GBC(1,1,1,1).setWeight(100,10).setAnchor(GBC.CENTER).setInsets(3));
        panel.add(searchWord,new GBC(0,2,1,1).setWeight(100,10).setAnchor(GBC.CENTER).setInsets(3));

        panel.add(searchWordT,new GBC(1,2,1,1).setWeight(100,10).setFill(GBC.HORIZONTAL).setAnchor(GBC.CENTER).setInsets(3));
        searchWordT.setFont(newFont);
        panel.add(search,new GBC(2,2,1,1).setWeight(100,10).setFill(GBC.HORIZONTAL).setAnchor(GBC.CENTER).setInsets(3));
        panel.add(searchAnySimilarWords,new GBC(0,3,3,1).setWeight(100,10).setFill(GBC.HORIZONTAL).setAnchor(GBC.CENTER).setInsets(3));

        panel.add(showResult,new GBC(0,4,3,1).setWeight(100,100).setFill(GBC.BOTH).setAnchor(GBC.CENTER).setInsets(3));
        showResult.setBorder(BorderFactory.createLineBorder(Color.black));
        panel.add(deleteWord,new GBC(0,5,3,1).setWeight(100,10).setFill(GBC.HORIZONTAL).setAnchor(GBC.CENTER).setInsets(3));

        //Przypisanie ikonek do przycisk�w
        ImageIcon iconEnter = new ImageIcon("main/resources/ImageFile/delete_1.png");
        deleteWord.setSize(30,30);
        if(iconEnter.getIconWidth() > deleteWord.getWidth()){
            iconEnter = new ImageIcon(iconEnter.getImage().getScaledInstance(deleteWord.getWidth(),-1,Image.SCALE_DEFAULT));
        }
        deleteWord.setIcon(iconEnter);

        st = Get_Database_Parameters.getStart_Logic_Class(true);

        search.addActionListener(
                (event)->{
                    try {

                        //Okre�lenie czy pole searchWord jest puste czy zawiera wyraz
                        if(searchWordT.getText().equals("") && radioPolish.isSelected()){
                            JOptionPane.showMessageDialog(null,"Prosz� wprowadzi� s��wko w j�zyku polskim"); return;
                        }else if(searchWordT.getText().equals("") && (!radioPolish.isSelected())){
                            JOptionPane.showMessageDialog(null,"Prosz� wprowadzi� s��wko w j�zyku angielskim"); return;
                        }

                        if(searchWordFromJText(rs, radioPolish, searchWordT.getText())){ //-------------------------------------
                            if(howMuchSimilarWords.size() > 1){
                                showResult.setText("S��wka "+searchWordT.getText()+" znajduj� si� w bazie prosz� wybra� jedn� pozycje\n");

                                for(int lp: howMuchSimilarWords){
                                    showResult.append("lp."+lp+" - "+searchWordT.getText()+"\n");
                                }
                                showResult.setForeground(Color.black);

                                setDeleteLayout();

                            }else{
                                showResult.setText("S��wko "+searchWordT.getText()+" znajduje si� w bazie jest przygotowane do usuni�cia");
                                showResult.setForeground(Color.green);
                            }


                        }else{
                            showResult.setText("Brak podanego s��wka w bazie");
                            showResult.setForeground(Color.red);
                        }

                    }catch(FileNotFoundException fileExc){
                        JOptionPane.showMessageDialog(null,"Brak pliku: "+fileExc.getMessage());
                    }catch(SQLException sqlExc){ JOptionPane.showMessageDialog(null,"sqlExc: "+sqlExc.getMessage());}
                }
        );

        searchAnySimilarWords.addActionListener(
            (event)->{
                try {

                    //Okre�lenie czy pole searchWord jest puste czy zawiera wyraz
                    if(searchWordT.getText().equals("") && radioPolish.isSelected()){
                        JOptionPane.showMessageDialog(null,"Prosz� wprowadzi� s��wko w j�zyku polskim"); return;
                    }else if(searchWordT.getText().equals("") && (!radioPolish.isSelected())){
                        JOptionPane.showMessageDialog(null,"Prosz� wprowadzi� s��wko w j�zyku angielskim"); return;
                    }

                    List<String> listAnySimilarWords;
                    if ((listAnySimilarWords = searchAnySimilarWords(rs, radioPolish, searchWordT.getText())) != null) {

                        showResult.setText("");
                        showResult.setForeground(Color.BLACK);
                        for(String words: listAnySimilarWords){
                            showResult.append(words+"\n");
                        }

                    } else {
                        showResult.setText("Brak s��wka w bazie danych");
                        showResult.setForeground(Color.red);
                    }

                }catch(SQLException sqlExc){
                    JOptionPane.showMessageDialog(null,sqlExc.getMessage());
                }catch(FileNotFoundException fileExc){ JOptionPane.showMessageDialog(null,fileExc.getMessage() );}

                bool = null;
                this.pack();
            }
        );

        deleteWord.addActionListener(
                (event)->{

                    if(bool != null && bool.booleanValue()){ //warunek true usuwamy s��wko

                        if(howMuchSimilarWords.size() > 1){//usuwamy jedyne s��wko lub wybieramy jedno z takich samych po numerze lp
                            boolean insert_Correct_Lp_Value = false;
                            int number_Lp_Word = 0;

                            if(!wybranaWartoscT.getText().equals("")) {
                                number_Lp_Word = Integer.parseInt(wybranaWartoscT.getText());
                            }else{
                                JOptionPane.showMessageDialog(null,"Prosz� wskaza� odpowiedni numer pozycji s��wka na li�cie !");
                                return;
                            }

                            for(int el: howMuchSimilarWords){

                                if(el == number_Lp_Word){
                                    insert_Correct_Lp_Value = true;
                                }
                            }

                            if(insert_Correct_Lp_Value) {
                                deleteWordFormDatabase(searchWordT.getText(), radioPolish.isSelected(), false, Integer.parseInt(wybranaWartoscT.getText()));
                                returnCurrentDeleteLayout();
                                panel.revalidate();
                                showResult.setText("S��wko "+searchWordT.getText()+" o numerze "+wybranaWartoscT.getText()+" zosta�o usuni�te z bazy !");
                                showResult.setForeground(Color.GREEN);
                            }else{
                                JOptionPane.showMessageDialog(null,"Prosz� wskaza� odpowiedni numer pozycji s��wa na li�cie !");
                                return;
                            }

                        }else{
                            deleteWordFormDatabase(searchWordT.getText(),radioPolish.isSelected(),true,0);
                        }

                    }else{
                        JOptionPane.showMessageDialog(null,"Prosz� wyszuka� i wpisa� s��wko znajduj�ce si� w bazie danych");
                    }

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

    //Zwraca true je�li s�owo znajduje sie w bazie w przeciwnym wypadku false
    private boolean searchWordFromJText(ResultSet rs,JRadioButton radioPolish,String jText) throws FileNotFoundException, SQLException {

        String command = null;
        String tableName = Get_Database_Parameters.getTableName();

        if(radioPolish.isSelected()){

            command = "select lp,polish from "+tableName+" where polish = "+"'"+jText+"'";
            rs = null;
            rs = st.executeQuery(command);

            //Zliczenie i dodanie do Arrays wszystkich podobnych s��wek
            howMuchSimilarWords.clear();
            while(rs.next()){
                String lp = rs.getString(1);
                howMuchSimilarWords.add(Integer.parseInt(lp));
            }
            rs.beforeFirst(); //ustawienie wskaznika na po pocz�tku

            if(rs.next()){
                bool = new Boolean(true); //wyraz zosta� znaleziony mo�na usun�� z bazy
                return true;
            }else{
                bool = new Boolean(false); //wyrazu nie znaleziono, brak wyrazu w bazie
                return false;
            }

        }else{

            command = "select lp,english from "+tableName+" where english = "+"'"+jText+"'";
            rs = st.executeQuery(command);

            if(rs.next()){
                bool = new Boolean(true);
                return true;
            }else{
                bool = new Boolean(false);
                return false;
            }

        }
    }

    //zwraca list� wszystkich podobnych s��wek
    private List<String> searchAnySimilarWords(ResultSet rs,JRadioButton radioPolish,String jText) throws FileNotFoundException,SQLException{
        List<String> listAnySimilarWords = null;
        String command = null;
        String tableName = Get_Database_Parameters.getTableName();

        if(radioPolish.isSelected()){

            command = "select * from "+tableName+" where polish like "+"'%"+jText+"%'";
            rs = null;
            rs = st.executeQuery(command);

            if(rs.next()){
                listAnySimilarWords = new ArrayList<String>();

                String lp = rs.getString(1);
                String pol = rs.getString(2);
                String eng = rs.getString(3);
                listAnySimilarWords.add("lp: "+lp+". "+pol+" - "+eng);

                while(rs.next()){
                    lp = rs.getString(1);
                    pol = rs.getString(2);
                    eng = rs.getString(3);
                    listAnySimilarWords.add("lp: "+lp+". "+pol+" - "+eng);
                }

            }else{showResult.setText("Brak s��wka w bazie danych"); return null; }

            return  listAnySimilarWords;

        }else{

            command = "select * from "+tableName+" where english like "+"'%"+jText+"%'";
            rs = null;
            rs = st.executeQuery(command);

            if(rs.next()){
                listAnySimilarWords = new ArrayList<String>();

                String lp = rs.getString(1);
                String pol = rs.getString(2);
                String eng = rs.getString(3);
                listAnySimilarWords.add("lp: "+lp+". "+eng+"-"+pol);

                while(rs.next()){
                    lp = rs.getString(1);
                    pol = rs.getString(2);
                    eng = rs.getString(3);
                    listAnySimilarWords.add("lp: "+lp+". "+eng+"-"+pol);
                }
            }else{showResult.setText("Brak s��wka w bazie danych"); return null; }

            return  listAnySimilarWords;
        }
    }
    //Usuwa jedno z dw�ch lub wiekszej liczby takich samych s��wek w bazie uwzgl�dniaj�c:
    //s�owo z JTextFiel,co zaznaczony polski czy angielski,znalezionych 1 czy wiecej takich samych s��wek,
    //numer lp. z bazy s��wka kt�re chcemy usun��?
    private boolean deleteWordFormDatabase(String delWord,boolean polishRadio,boolean onlyOneWord,int numberWord) {

        try {
            String tableName = Get_Database_Parameters.getTableName();

            if (polishRadio) {
                String command = null;

                if(onlyOneWord){
                       command = "delete from " + tableName + " where polish = " + "'" + delWord + "'";
                }else{ command = "delete from " + tableName + " where polish = " + "'" + delWord + "'" + " and "+" lp = "+numberWord; }

                try {
                    st.executeUpdate(command);
                    bool = null;
                    return true;
                } catch (SQLException sqlExc) {
                    JOptionPane.showMessageDialog(null, sqlExc.getMessage());
                    return false;
                }

            } else {
                String command = "delete from " + tableName + " where english = " + "'" + delWord + "'";
                try {
                    st.executeUpdate(command);
                    bool = null;
                    return true;
                } catch (SQLException sqlExc) {
                    JOptionPane.showMessageDialog(null, sqlExc.getMessage());
                    return false;
                }
            }


        }catch(FileNotFoundException fileExc){ JOptionPane.showMessageDialog(null,fileExc.getMessage()); return false; }
    }

    //ustawia nowy layout dla > 1 takich samych wyrazow do usuniecia
    private void setDeleteLayout(){
        panel.add(deleteWord,new GBC(0,5,2,1).setWeight(100,10).setFill(GBC.HORIZONTAL).setAnchor(GBC.CENTER).setInsets(3));
        panel.add(wybranaWartoscT,new GBC(2,5,1,1).setWeight(100,10).setFill(GBC.HORIZONTAL).setAnchor(GBC.CENTER).setInsets(3));
        wybranaWartoscT.setEnabled(true);
        wybranaWartoscT.setText("");
    }
    //Zwraca aktualny standardowy layout
    private void returnCurrentDeleteLayout(){
        wybranaWartoscT.setEnabled(false);
        panel.add(deleteWord,new GBC(0,5,3,1).setWeight(100,10).setFill(GBC.HORIZONTAL).setAnchor(GBC.CENTER).setInsets(3));
    }
    //Zamyka po��czenie instancji obiektu Statement po wyjsciu z okna
    private void thisWindowsHiddenClosedStatement() throws SQLException{
        st.close();
    }
}