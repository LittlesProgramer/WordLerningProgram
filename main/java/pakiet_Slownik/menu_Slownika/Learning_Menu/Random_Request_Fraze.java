package pakiet_Slownik.menu_Slownika.Learning_Menu;

import pakiet_Slownik.GBC;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.Map;

public class Random_Request_Fraze extends JDialog{

    private Font newFont = new Font("Arial",Font.PLAIN,24);
    private ButtonGroup grupa = new ButtonGroup();
    private JPanel leftPanel = new JPanel();

    private JLabel chowMuchWordRequest = new JLabel("Z ilu fraz wyrazowych chcesz zostaæ odpytany: ");
    private JTextField chowMuchWordRequestT = new JTextField(3);
    private JLabel pageIs = new JLabel("Strona z zeszytu to: ");
    private JTextField pageIsT = new JTextField(3);

    protected static JRadioButton polishRadio = new JRadioButton("polski");
    private JRadioButton englishRadio = new JRadioButton("angielski");
    private JLabel inPolishLanguage = new JLabel("w jêzyku polskim: ");
    private JLabel inEnglishLanguage = new JLabel("w jêzyku angielskim: ");
    private JTextField inPolishLanguageT = new JTextField(10);
    private JTextField inEnglishLanguageT = new JTextField(10);
    private JButton startRequestButton = new JButton(" = Shift + Enter");
    private JButton checkCorrectAnswer = new JButton(" = sprawdŸ poprawnoœæ");
    private JTextArea showResult = new JTextArea(10,10);
    private JLabel labelShowStatistics = new JLabel("statystyki: ");

    public Random_Request_Fraze(JFrame owner) {
        super(owner,"Panel losowego odpytywania z fraz wyrazowych",false);
        GridBagLayout layout = new GridBagLayout();
        leftPanel.setLayout(layout);
        this.setLayout(layout);
        polishRadio.setSelected(true);

        this.add(leftPanel,new GBC(0,0,1,1).setWeight(1,1).setFill(GBC.HORIZONTAL).setInsets(3));
        grupa.add(polishRadio); grupa.add(englishRadio);
        leftPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        leftPanel.add(chowMuchWordRequest,new GBC(0,0,1,1).setAnchor(GBC.WEST).setInsets(3));
        chowMuchWordRequest.setFont(newFont);
        leftPanel.add(chowMuchWordRequestT,new GBC(1,0,1,1).setWeight(100,10).setFill(GBC.HORIZONTAL).setInsets(3));
        chowMuchWordRequestT.setFont(newFont);
        leftPanel.add(pageIs,new GBC(2,0,1,1).setAnchor(GBC.CENTER).setInsets(3));
        pageIs.setFont(newFont);
        leftPanel.add(pageIsT,new GBC(3,0,1,1).setWeight(100,10).setFill(GBC.HORIZONTAL).setInsets(3));
        pageIsT.setFont(newFont);
        leftPanel.add(polishRadio,new GBC(1,1,1,1).setWeight(100,10).setFill(GBC.HORIZONTAL).setAnchor(GBC.CENTER).setInsets(3));
        polishRadio.setFont(new Font("Arial",Font.PLAIN,22));
        leftPanel.add(englishRadio,new GBC(1,2,1,1).setWeight(100,10).setFill(GBC.HORIZONTAL).setAnchor(GBC.CENTER).setInsets(3));
        englishRadio.setFont(new Font("Arial",Font.PLAIN,22));
        leftPanel.add(inPolishLanguage,new GBC(0,3,2,1).setWeight(100,10).setAnchor(GBC.CENTER).setInsets(3));
        inPolishLanguage.setFont(newFont);
        leftPanel.add(inEnglishLanguage,new GBC(2,3,2,1).setWeight(100,10).setAnchor(GBC.CENTER).setInsets(3));
        inEnglishLanguage.setFont(newFont);
        leftPanel.add(inPolishLanguageT,new GBC(0,4,2,1).setWeight(100,10).setFill(GBC.HORIZONTAL).setInsets(3));
        inPolishLanguageT.setFont(newFont);
        leftPanel.add(inEnglishLanguageT,new GBC(2,4,2,1).setWeight(100,10).setFill(GBC.HORIZONTAL).setInsets(3));
        inEnglishLanguageT.setFont(newFont);
        leftPanel.add(startRequestButton,new GBC(0,5,4,1).setWeight(100,10).setFill(GBC.HORIZONTAL).setInsets(3));
        startRequestButton.setFont(newFont);
        leftPanel.add(checkCorrectAnswer,new GBC(0,6,4,1).setWeight(100,10).setFill(GBC.HORIZONTAL).setInsets(3));
        checkCorrectAnswer.setFont(newFont);
        leftPanel.add(showResult,new GBC(0,7,4,1).setWeight(100,100).setFill(GBC.BOTH).setInsets(3));
        showResult.setFont(newFont);
        showResult.setBorder(BorderFactory.createLineBorder(Color.BLUE));
        leftPanel.add(labelShowStatistics,new GBC(0,8,4,1).setWeight(100,10).setFill(GBC.HORIZONTAL).setInsets(3));

        //Przypisanie obrazk?w do przycisk?w
        ImageIcon iconStart = new ImageIcon("main/resources/ImageFile/start.png");
        startRequestButton.setSize(60,60);
        if(iconStart.getIconWidth() > startRequestButton.getWidth()){
            iconStart = new ImageIcon(iconStart.getImage().getScaledInstance(startRequestButton.getWidth(),-1,Image.SCALE_DEFAULT));
        }
        startRequestButton.setIcon(iconStart);

        ImageIcon iconEnter = new ImageIcon("main/resources/ImageFile/enter.png");
        checkCorrectAnswer.setSize(60,60);
        if(iconEnter.getIconWidth() > startRequestButton.getWidth()){
            iconEnter = new ImageIcon(iconEnter.getImage().getScaledInstance(checkCorrectAnswer.getWidth(),-1,Image.SCALE_DEFAULT));
        }
        checkCorrectAnswer.setIcon(iconEnter);

        polishRadio.addActionListener(
                (event)->{
                    if(polishRadio.isSelected()){
                        inPolishLanguage.setText("w jêzyku polskim");
                        inEnglishLanguage.setText("w jêzyku angielskim");
                    }
                }
        );

        englishRadio.addActionListener(
                (event)->{
                    if(englishRadio.isSelected()){
                        inPolishLanguage.setText("w jêzyku angielskim");
                        inEnglishLanguage.setText("w jêzyku polskim");
                    }
                }
        );

        startRequestButton.setAction(new StartButton(" = Shift + Enter",iconStart));
        checkCorrectAnswer.setAction(new CheckButton(" = SprawdŸ poprawnoœæ",iconEnter));

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                restartButtonStart();
            }
        });

        this.pack();
        this.setVisible(true);
    }

    private void restartButtonStart(){
        Class_Include_All_Method_Checking_Is_Answer_Is_Correct.check_Correct_Button_Value_Run = false;
    }

    //Klasa Startuj?ca Odpytywanie

    //wystartowanie programu:
    //1.utworzenie listy s??wek wed?ug przekazanych kryteri?w
    //2.Utworzenie Mapy i ustawienie iteratora za pomoc? metody create_Map_And_Get_Iterator()
    //3.Pobranie pierwszego klucza z Mapy za pomoc? ustawionego iteratora
    class StartButton extends AbstractAction{

        public StartButton(String name,Icon icon) {
            putValue(Action.NAME,name);
            putValue(Action.SMALL_ICON,icon);
            putValue(Action.SHORT_DESCRIPTION,"Przycisk Startuj¹cy");

            //Ustawienie skrut?w klawiaturowych
            //Okre?lenie Akcji dla przycisk?w
            InputMap imap = leftPanel.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
            ActionMap amap = leftPanel.getActionMap();
            KeyStroke keyStrokeStart = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,KeyEvent.SHIFT_DOWN_MASK,true);
            String keyStart = "Start";
            imap.put(keyStrokeStart,keyStart);
            amap.put(keyStart,this);
        }

        @Override
        public void actionPerformed(ActionEvent event){

            try {

                if(Set_Start_Request_Button_Class.correctInsertFields(chowMuchWordRequestT.getText(),pageIsT.getText(),showResult)){

                    Dictionary_Polish_English_Words dictionary = new Dictionary_Polish_English_Words(Class_Include_All_Method_Checking_Is_Answer_Is_Correct.inquireListWords);
                    Class_Include_All_Method_Checking_Is_Answer_Is_Correct.inquireListWords.clear();//Wyczyszczenie listy
                    //utworzenie listy s??wek wed?ug podanych kryteri?w
                    Class_Include_All_Method_Checking_Is_Answer_Is_Correct.inquireListWords = dictionary.getArrayList(Integer.parseInt(chowMuchWordRequestT.getText()),Integer.parseInt(pageIsT.getText()),false);

                    showResult.setText("");
                    showResult.setForeground(Color.GREEN);
                    showResult.setText("URUCHOMIONO ODPYTYWANIE ZE WSKAZANYCH FRAZ"+"\n");
                    //----------------------------------------------------------------------------------------------------------------------------
                    Class_Include_All_Method_Checking_Is_Answer_Is_Correct.create_Map_And_Get_Iterator(polishRadio.isSelected()); //Stworzenie mapy s??wek wed?ug ustawie? polishRadio, ustawienie iteratora
                    //----------------------------------------------------------------------------------------------------------------------------

                    if(Class_Include_All_Method_Checking_Is_Answer_Is_Correct.dictionaryPolishEnglishWord_Map.size() < Class_Include_All_Method_Checking_Is_Answer_Is_Correct.inquireListWords.size()){ //sprawdzenie d?ugosci listy i Mapy,Mapa pomija powtarzaj?ce si? elementy
                        showResult.append("List zosta³a skrócona do "+Class_Include_All_Method_Checking_Is_Answer_Is_Correct.dictionaryPolishEnglishWord_Map.size()+" z powodu powtarzaj?cych si? s??wek");
                    }
                    Class_Include_All_Method_Checking_Is_Answer_Is_Correct.nextElementMap = Class_Include_All_Method_Checking_Is_Answer_Is_Correct.iteratorDisctionaryMap.next();//pobiera kolejny element z Mapy dictionaryPolishEnglishWord_Map
                    Class_Include_All_Method_Checking_Is_Answer_Is_Correct.key_From_Map = Class_Include_All_Method_Checking_Is_Answer_Is_Correct.nextElementMap.getKey(); //pobranie pierwszego klucza z Mapy
                    Class_Include_All_Method_Checking_Is_Answer_Is_Correct.value_From_Map = Class_Include_All_Method_Checking_Is_Answer_Is_Correct.nextElementMap.getValue();//pobranie wartosci klucza

                    inPolishLanguageT.setText(Class_Include_All_Method_Checking_Is_Answer_Is_Correct.key_From_Map);
                    Class_Include_All_Method_Checking_Is_Answer_Is_Correct.check_Correct_Button_Value_Run = true;

                    Class_Include_All_Method_Checking_Is_Answer_Is_Correct.restetAllValue();//zresetowanie zmiennych przechowuj?cych ilo?? b??dnych-poprawnych odpowiedzi

                    String space = "        ";
                    String correctAnswer = "poprawnych odpowiedzi:  ";
                    String correctVal = String.valueOf(Class_Include_All_Method_Checking_Is_Answer_Is_Correct.getGoodAnswer());
                    String badAnswer = " b³êdnych odpowiedzi: "+Class_Include_All_Method_Checking_Is_Answer_Is_Correct.getBadAnswer();
                    String sizeMap = " na "+Class_Include_All_Method_Checking_Is_Answer_Is_Correct.getMapLength()+" fraz wyrazowych";
                    labelShowStatistics.setText("statystyki: "+correctAnswer+correctVal+space+badAnswer+space+sizeMap);
                }

            }catch(FileNotFoundException fileExc){
                JOptionPane.showMessageDialog(null,fileExc.getMessage());
            } catch(SQLException sqlExc){ JOptionPane.showMessageDialog(null,sqlExc.getMessage());}
        }
    }

    //Klasa Sprawdzaj?ca podane odpowied?i
    class CheckButton extends AbstractAction{

        public CheckButton(String name,Icon icon) {
            putValue(Action.NAME,name);
            putValue(Action.SMALL_ICON,icon);
            putValue(Action.SHORT_DESCRIPTION,"Przycisk Startujcy");

            //Ustawienie skrut?w klawiaturowych
            //Okre?lenie Akcji dla przycisk?w
            UIManager.put("Button.defaultButtonFollowsFocus",Boolean.TRUE);
            InputMap imap = leftPanel.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
            ActionMap amap = leftPanel.getActionMap();
            KeyStroke keyStrokeStart = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0);
            String keyCheck = "Check";
            imap.put(keyStrokeStart,keyCheck);
            amap.put(keyCheck,this);
        }

        @Override
        public void actionPerformed(ActionEvent event){
            if(Class_Include_All_Method_Checking_Is_Answer_Is_Correct.check_Correct_Button_Value_Run){//sprawdzenie czy wpierw wcisnieto przycisk Start tworz?cy Map? z danymi

                translateWord();
                inEnglishLanguageT.setText("");//zerowanie pola textowego

            }else{ JOptionPane.showMessageDialog(null,"Proszê najpierw wystartowaæ program przyciskiem START");}
        }

        public void translateWord(){

            if(Class_Include_All_Method_Checking_Is_Answer_Is_Correct.iteratorDisctionaryMap.hasNext()) {//sprawdza czy mapa utworzona przyciskiem START zawiera kolejny wyraz

                String pattern = Class_Include_All_Method_Checking_Is_Answer_Is_Correct.getRegexString(Class_Include_All_Method_Checking_Is_Answer_Is_Correct.value_From_Map);//ustawienie find_OR_matches i pattern

                //metoda sprawdza poprawno?? wprowadzonej warto??i na podstawie 3 arg: find_Or_matches,wzorca pattern wraz z kluczem mapy, oraz odpowiedzi usera
                if(Class_Include_All_Method_Checking_Is_Answer_Is_Correct.checkInsertAnswer(Class_Include_All_Method_Checking_Is_Answer_Is_Correct.find_OR_matches,pattern,inEnglishLanguageT.getText())){
                    //metoda zlicza pozytywne i b??dne odpowiedzi,b??dne odpowiedzi s? zapami?tywane w celu p?niejszego wykorzystania do odpytania
                    Class_Include_All_Method_Checking_Is_Answer_Is_Correct.setMapBadAnswerAndCreateStatisticsAnswer(true,null,null);
                    showResult.setText("Poprawna odpowied? \n\n\n");
                    showResult.setForeground(Color.GREEN);

                    String space = "        ";
                    String correctAnswer = "poprawnych odpowiedzi:  ";
                    String correctVal = String.valueOf(Class_Include_All_Method_Checking_Is_Answer_Is_Correct.getGoodAnswer());
                    String badAnswer = " b³êdnych odpowiedzi: "+Class_Include_All_Method_Checking_Is_Answer_Is_Correct.getBadAnswer();
                    String sizeMap = " na "+Class_Include_All_Method_Checking_Is_Answer_Is_Correct.getMapLength()+" fraz wyrazowych";
                    labelShowStatistics.setText("statystyki: "+correctAnswer+correctVal+space+badAnswer+space+sizeMap);

                }else{
                    //metoda zapamietuj¹ca pozytywne odpowiedzi wed³ug kt?rej tworzy statystyki poprawnych i b??dnych
                    Class_Include_All_Method_Checking_Is_Answer_Is_Correct.setMapBadAnswerAndCreateStatisticsAnswer(false, Class_Include_All_Method_Checking_Is_Answer_Is_Correct.key_From_Map, Class_Include_All_Method_Checking_Is_Answer_Is_Correct.value_From_Map);
                    showResult.setText("B³êdna odpowiedŸ \n\n");
                    showResult.append("Poprawna odpowiedŸ to: \n");
                    showResult.append(Class_Include_All_Method_Checking_Is_Answer_Is_Correct.key_From_Map+" = "+Class_Include_All_Method_Checking_Is_Answer_Is_Correct.value_From_Map);
                    showResult.setForeground(Color.RED);

                    String space = "        ";
                    String correctAnswer = "poprawnych odpowiedzi: ";
                    String correctVal = String.valueOf(Class_Include_All_Method_Checking_Is_Answer_Is_Correct.getGoodAnswer());
                    String badVal = " b³êdnych odpowiedzi: "+Class_Include_All_Method_Checking_Is_Answer_Is_Correct.getBadAnswer();
                    String sizeMap = " na "+Class_Include_All_Method_Checking_Is_Answer_Is_Correct.getMapLength()+" fraz wyrazowych";
                    labelShowStatistics.setText("statystyki: "+correctAnswer+correctVal+space+badVal+space+sizeMap);

                    //b??dna odpowiedz zapamietywana w osobnej Mapie B??dnych odpowiedzi
                    //w celu ponownego odpytania u?ytkownika, pyta si? u?ytkownika czy chce by?
                    //ponownie odpytany z b??dnych odpowiedzi
                }

                Class_Include_All_Method_Checking_Is_Answer_Is_Correct.nextElementMap = Class_Include_All_Method_Checking_Is_Answer_Is_Correct.iteratorDisctionaryMap.next();//pobiera kolejny element z Mapy dictionaryPolishEnglishWord_Map
                Class_Include_All_Method_Checking_Is_Answer_Is_Correct.key_From_Map = Class_Include_All_Method_Checking_Is_Answer_Is_Correct.nextElementMap.getKey(); //pobranie pierwszego klucza z Mapy
                Class_Include_All_Method_Checking_Is_Answer_Is_Correct.value_From_Map = Class_Include_All_Method_Checking_Is_Answer_Is_Correct.nextElementMap.getValue();//pobranie wartosci klucza
                inPolishLanguageT.setText(Class_Include_All_Method_Checking_Is_Answer_Is_Correct.key_From_Map);

            }else {//wy?wietlany jest ostatni wyraz po czym program informuje o zako?czeniu lub ponownym odpytaniu z b??dnych odpowiedzi

                String pattern = Class_Include_All_Method_Checking_Is_Answer_Is_Correct.getRegexString(Class_Include_All_Method_Checking_Is_Answer_Is_Correct.value_From_Map);//ustawienie find_OR_matches i pattern

                if(Class_Include_All_Method_Checking_Is_Answer_Is_Correct.checkInsertAnswer(Class_Include_All_Method_Checking_Is_Answer_Is_Correct.find_OR_matches,pattern,inEnglishLanguageT.getText())){

                    //poprawne + b??dne = sumaWyraz?wWMapie, dzi?ki temu warunkowi nie tworzy statystyk ani nie dodaje kolejnych b??dnych odpowiedzi
                    if((Class_Include_All_Method_Checking_Is_Answer_Is_Correct.getGoodAnswer() + Class_Include_All_Method_Checking_Is_Answer_Is_Correct.getBadAnswer()) < Class_Include_All_Method_Checking_Is_Answer_Is_Correct.getMapLength()) {
                        //metoda zapamietuj?ca pozytywne odpowiedzi wed?ug kt?rej tworzy statystyki poprawnych i b??dnych
                        Class_Include_All_Method_Checking_Is_Answer_Is_Correct.setMapBadAnswerAndCreateStatisticsAnswer(true, null, null);
                    }

                    String space = "        ";
                    String correctAnswer = "poprawnych odpowiedzi: ";
                    String correctVal = String.valueOf(Class_Include_All_Method_Checking_Is_Answer_Is_Correct.getGoodAnswer());
                    String badVal = " b³êdnych odpowiedzi: "+Class_Include_All_Method_Checking_Is_Answer_Is_Correct.getBadAnswer();
                    String sizeMap = " na "+Class_Include_All_Method_Checking_Is_Answer_Is_Correct.getMapLength()+" fraz wyrazowych";
                    labelShowStatistics.setText("statystyki: "+correctAnswer+correctVal+space+badVal+space+sizeMap);

                    showResult.setText("Poprawna odpowiedŸ \n\n\n");
                    showResult.setForeground(Color.GREEN);

                }else{

                    //poprawne + b??dne = sumaWyraz?wWMapie, dzi?ki temu warunkowi nie tworzy statystyk ani nnie dodaje kolejnych b??dnych odpowiedzi
                    if((Class_Include_All_Method_Checking_Is_Answer_Is_Correct.getGoodAnswer()+Class_Include_All_Method_Checking_Is_Answer_Is_Correct.getBadAnswer()) < Class_Include_All_Method_Checking_Is_Answer_Is_Correct.getMapLength()) {
                        //metoda zapamietuj?ca pozytywne odpowiedzi wed?ug kt?rej tworzy statystyki poprawnych i b??dnych
                        Class_Include_All_Method_Checking_Is_Answer_Is_Correct.setMapBadAnswerAndCreateStatisticsAnswer(false, Class_Include_All_Method_Checking_Is_Answer_Is_Correct.key_From_Map, Class_Include_All_Method_Checking_Is_Answer_Is_Correct.value_From_Map);
                    }

                    String space = "        ";
                    String correctAnswer = "poprawnych odpowiedzi: ";
                    String correctVal = String.valueOf(Class_Include_All_Method_Checking_Is_Answer_Is_Correct.getGoodAnswer());
                    String badVal = " b³êdnych odpowiedzi: "+Class_Include_All_Method_Checking_Is_Answer_Is_Correct.getBadAnswer();
                    String sizeMap = " na "+Class_Include_All_Method_Checking_Is_Answer_Is_Correct.getMapLength()+" fraz wyrazowych";
                    labelShowStatistics.setText("statystyki: "+correctAnswer+correctVal+space+badVal+space+sizeMap);

                    showResult.setText("B³êdna odpowiedŸ \n\n");
                    showResult.append("Poprawna odpowiedŸ to: \n");
                    showResult.append(Class_Include_All_Method_Checking_Is_Answer_Is_Correct.key_From_Map+" = "+Class_Include_All_Method_Checking_Is_Answer_Is_Correct.value_From_Map);
                    showResult.setForeground(Color.RED);

                    //b??dna odpowiedz zapamietywana w osobnej Mapie B??dnych odpowiedzi
                    //w celu ponownego odpytania u?ytkownika, pyta si? u?ytkownika czy chce by?
                    //ponownie odpytany z b??dnych odpowiedzi

                }

                if(Class_Include_All_Method_Checking_Is_Answer_Is_Correct.getBadAnswer() == 0){ //sprawdzenie czy liczba b?ednych odp udzielona przez usera jest r?wna 0
                    JOptionPane.showMessageDialog(null,"Brawo nauczy³eœ/aœ siê wszystkich fraz wyrazowych");
                    restartButtonStart();
                    return;
                }

                int iloscBlednychOdp = Class_Include_All_Method_Checking_Is_Answer_Is_Correct.getBadAnswer();
                int decyzjaUsera = JOptionPane.showOptionDialog(null,"Czy chcesz zostaæ ponownie odpytany z "+iloscBlednychOdp+" b³êdnie przet³umaczonych fraz wyrazowych ?","Ponowne odpytanie z b³êdnych odpowiedzi",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,null,null);
                startRequestFromBadAnswer(decyzjaUsera,Class_Include_All_Method_Checking_Is_Answer_Is_Correct.tempMapWrongAnswer);
                Class_Include_All_Method_Checking_Is_Answer_Is_Correct.tempMapWrongAnswer.clear();//wyzerowanie Mapy tymczasowej przechowuj?cej b??dne odpowiedzi
            }
        }

        protected void startRequestFromBadAnswer(int decyzjaUsera, Map<String,String> newBadAnswerMap){
            //1.Zeruje Statystyki
            //2.Zeruje Map? iteratorDisctionaryMap
            //3.Przypisanie nowej mapy do starej -> iteratorDisctionaryMap

            if(decyzjaUsera == 0) {
                showResult.setText("");

                //----------------------------------------------------------------------------------------------------------
                //Wyzerowanie Statystyk
                String space = "        ";
                String correctAnswer = "poprawnych odpowiedzi: ";
                String correctVal = "0";
                String badVal = " b³êdnych odpowiedzi: "+"0";
                String sizeMap = " na "+Class_Include_All_Method_Checking_Is_Answer_Is_Correct.tempMapWrongAnswer.size();
                labelShowStatistics.setText("statystyki: "+correctAnswer+correctVal+space+badVal+space+sizeMap);

                Class_Include_All_Method_Checking_Is_Answer_Is_Correct.dictionaryPolishEnglishWord_Map.clear();
                Class_Include_All_Method_Checking_Is_Answer_Is_Correct.dictionaryPolishEnglishWord_Map.putAll(newBadAnswerMap);

                Class_Include_All_Method_Checking_Is_Answer_Is_Correct.iteratorDisctionaryMap = Class_Include_All_Method_Checking_Is_Answer_Is_Correct.dictionaryPolishEnglishWord_Map.entrySet().iterator();
                Class_Include_All_Method_Checking_Is_Answer_Is_Correct.nextElementMap = Class_Include_All_Method_Checking_Is_Answer_Is_Correct.iteratorDisctionaryMap.next();//pobiera kolejny element z Mapy dictionaryPolishEnglishWord_Map
                Class_Include_All_Method_Checking_Is_Answer_Is_Correct.key_From_Map = Class_Include_All_Method_Checking_Is_Answer_Is_Correct.nextElementMap.getKey(); //pobranie pierwszego klucza z Mapy
                Class_Include_All_Method_Checking_Is_Answer_Is_Correct.value_From_Map = Class_Include_All_Method_Checking_Is_Answer_Is_Correct.nextElementMap.getValue();//pobranie wartosci klucza
                inPolishLanguageT.setText(Class_Include_All_Method_Checking_Is_Answer_Is_Correct.key_From_Map);
                Class_Include_All_Method_Checking_Is_Answer_Is_Correct.check_Correct_Button_Value_Run = true;
                Class_Include_All_Method_Checking_Is_Answer_Is_Correct.restetAllValue();

            }else{
                showResult.setText("Dziêkujê êe chcia³eœ/aœ skorzystaæ z mojej pomocy \nw nauce fraz wyrazowych, mi³ego dnia.");
                showResult.setForeground(Color.BLUE);
                restartButtonStart();
            }
        }
    }
}

