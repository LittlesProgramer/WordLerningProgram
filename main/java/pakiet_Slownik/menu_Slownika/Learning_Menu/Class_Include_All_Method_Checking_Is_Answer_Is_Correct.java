package pakiet_Slownik.menu_Slownika.Learning_Menu;

import pakiet_Slownik.ConnectToDatabase.Get_Database_Parameters;
import pakiet_Slownik.menu_Slownika.Manu_Admin.Panel_Menu_Regex;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Class_Include_All_Method_Checking_Is_Answer_Is_Correct {

    protected static List<String> inquireListWords = new ArrayList<>(); //lista slowek do odpytywania pobrana z bazy danych według podanych kryteriów
    protected static Map<String,String> dictionaryPolishEnglishWord_Map = new HashMap<>();//słownik przechowujący wyselekcjonowane słowa polsko-angielskie
    protected static Iterator<Map.Entry<String,String>> iteratorDisctionaryMap = null; //iterator pozwalający na poruszanie się po elementach MapyPolishEnglishWord
    protected static boolean check_Correct_Button_Value_Run = false; //określa czy program został wystartowany, i można dokonać sprawdzeń odpowiedzi
    protected static boolean find_OR_matches = false; // określa wybur metody Matcher.find() lub Matcher.matches()
    protected static Map.Entry<String,String> nextElementMap = null;// pobiera kolejny element w mapie dictionaryPolishEnglishWord_Map
    protected static String key_From_Map = null; //klucz z Mapy dictionaryPolishEnglishWord_Map
    protected static String value_From_Map = null; //wartosc klucza z Mapy dictionaryPolishEnglishWord_Map
    protected static List<String> list_Searche_All_Equivalents_Words = new ArrayList<>(); //lista jest wykorzystywana przez metodę
    // getSearche_All_Equivalents_Of_The_Loking_Word() -> wyszukuje w bazie wszystkie odpowiedniki słów które odpowiadają zmiennej -
    // value_From_Map.(Wykorzystywana podczas sprawdzania poprawności odpowiedzi)
    // private JRadioButton polishRadio = null;

    protected static int goodAnswer = 0; //zmienna przechowująca poprawne odpowiedzi udzielane przez użytkownika
    protected static int badAnswer = 0; //zmienna przechowująca błędne odpowiedzi udzielane przez użytkownika
    protected static int mapLength = dictionaryPolishEnglishWord_Map.size();
    protected static Map<String,String> tempMapWrongAnswer = new HashMap<>();

    //metoda sprawdza poprawnosć odpowiedzi na podstawie:
    //1.zmiennej find_Or_matches ustalającej wywołanie Matcher.find() lub Matcher.matches()
    //2.wzorca patter zwracanego przez metodę getRegexString()
    //3.odpowiedzi podanej przez użytkownika
    protected static boolean checkInsertAnswer(boolean find_OR_matches,String pattern,String userAnswer){

        Pattern pat = Pattern.compile(pattern);
        Matcher match = pat.matcher(userAnswer);

        if(find_OR_matches){

            if(match.find()){
                return true;
            }else{

                if (getSearche_All_Equivalents_Of_The_Loking_Word(key_From_Map, userAnswer,find_OR_matches)) {
                    return true;
                } else {
                    return false;
                }
            }

        }else{

            if(match.matches()){
                return true;
            }else{

                if (getSearche_All_Equivalents_Of_The_Loking_Word(key_From_Map, userAnswer,find_OR_matches)) {
                    return true;
                } else {
                    return false;
                }
            }
        }
    }

    //metoda pobierająca pierwszy element z listy:
    //1.kopiuje elementy z listy do Mapy
    //2.ustawia iterator na Mapie
    protected static void create_Map_And_Get_Iterator(boolean polishSelected){

        dictionaryPolishEnglishWord_Map.clear(); //zresetowanie mapy

        if(polishSelected){

            for(String el: inquireListWords){
                String tab[] = el.split("-");
                dictionaryPolishEnglishWord_Map.put(tab[1],tab[2]);
            }

        }else{

            for(String el: inquireListWords){
                String tab[] = el.split("-");
                dictionaryPolishEnglishWord_Map.put(tab[2],tab[1]);
            }

        }

        iteratorDisctionaryMap = dictionaryPolishEnglishWord_Map.entrySet().iterator();
    }

    //Metoda zwracająca String wzorca wyrażenia regularnego
    //skłądającego się z wyrażenia + map_Key_Value
    //w zależności od wybranej opcji w panelu Administracyjnym
    protected static String getRegexString(String map_Key_Value){ //find_Or_matches określa wybór metody Matcher.find() lub Matcher.matches()
        String p0 = map_Key_Value;//matches
        String p1 = "[_ ]*"+map_Key_Value+"[_ ]*"; //matches()
        String p2 = map_Key_Value.toUpperCase();//matches()
        String p3 = "[_ -]*"+map_Key_Value+"[_ -]*";//find()

        boolean space = Panel_Menu_Regex.isSpaceInExpression();
        boolean overAll_Matching = Panel_Menu_Regex.isBigAndSmallLetter();
        boolean matchingOfParts = Panel_Menu_Regex.isMatchOnlyWordInLitteral();

        if(space && overAll_Matching && matchingOfParts){
            find_OR_matches = true;
            return p1+p2+p3;
        }
        if(space && overAll_Matching){
            find_OR_matches = false;
            return  p1+p2;
        }
        if(space && matchingOfParts){
            find_OR_matches = true;
            return p1+p3;
        }
        if(overAll_Matching && matchingOfParts){
            find_OR_matches = true;
            return p2+p3;
        }
        if(space){
            find_OR_matches = false;
            return p1;
        }
        if(overAll_Matching){
            find_OR_matches = false;
            return p2;
        }
        if(matchingOfParts){
            find_OR_matches = true;
            return p3;
        }else{
            find_OR_matches = false;
            return p0;
        }
    }

    //Metoda wyszukuje wszystkich odpowiedników słówek w bazie,
    //które później są porównywane z wyrażeniem wprowadzonym przez usera
    //pobiera wartość klucza z Mapy oraz udzieloną odpowiedz usera
    //---------------------------------------------------------------------------------------------------------
    // Metoda getSearche_All_Equivalents_Of_The_Loking_Word() -
    // wyszukuje wszystkich odpowiedników pytanego słowa w bazie
    // i sprawdza poprawnosc podanej odpowiedzi to na wypadek gdy w bazie występuje wiecej niż
    // jedno słowo ale o różnych znaczeniach np: levy = pobierać ale też levy = sciągać
    //---------------------------------------------------------------------------------------------------------
    protected static boolean getSearche_All_Equivalents_Of_The_Loking_Word(String key_Map,String userAnswer,boolean find_OR_matches){
        String commandSql;

        try {
            if(Random_Request_Words.polishRadio.isSelected()) {
                commandSql = "select * from " + Get_Database_Parameters.getTableName() + " where polish = "+"'"+key_Map+"'";
            }else{commandSql = "select * from " + Get_Database_Parameters.getTableName() + " where english = "+"'"+key_Map+"'";}

            Statement st = Get_Database_Parameters.getStart_Logic_Class(true);
            ResultSet rs = st.executeQuery(commandSql);

            list_Searche_All_Equivalents_Words.clear();
            while(rs.next()){
                String lp = rs.getString(1);
                String pol = rs.getString(2);
                String eng = rs.getString(3);
                String page = rs.getString(4);

                list_Searche_All_Equivalents_Words.add(lp + "-" + pol + "-" + eng + "-" + page);
            }

            if(Random_Request_Words.polishRadio.isSelected()){
                if(find_OR_matches) {
                    for (String el : list_Searche_All_Equivalents_Words) {

                        String tab[] = el.split("-");
                        Pattern pat = Pattern.compile(getRegexString(tab[2]));
                        Matcher matcher = pat.matcher(userAnswer);
                        if (matcher.find()) return true;
                    }
                }else{
                    for (String el : list_Searche_All_Equivalents_Words) {

                        String tab[] = el.split("-");
                        Pattern pat = Pattern.compile(getRegexString(tab[2]));
                        Matcher matcher = pat.matcher(userAnswer);
                        if (matcher.matches()) return true;
                    }
                }

            }else{
                if(find_OR_matches) {
                    for (String el : list_Searche_All_Equivalents_Words) {

                        String tab[] = el.split("-");
                        Pattern pat = Pattern.compile(getRegexString(tab[1]));
                        Matcher matcher = pat.matcher(userAnswer);
                        if (matcher.find()) return true;
                    }
                }else{
                    for (String el : list_Searche_All_Equivalents_Words) {

                        String tab[] = el.split("-");
                        Pattern pat = Pattern.compile(getRegexString(tab[1]));
                        Matcher matcher = pat.matcher(userAnswer);

                        if (matcher.matches()) return true;
                    }
                }
            }

        }catch (FileNotFoundException fileExc){
            JOptionPane.showMessageDialog(null,fileExc.getMessage());
        }catch(SQLException sqlExc){ JOptionPane.showMessageDialog(null,sqlExc.getMessage() );}

        return false;
    }

    // Metoda
    // 1.tworzy Mapę z zapamiętanych błędnych odpowiedzi Użytkownika
    // 2.Tworzy licznik poprawnych i błędnych odp
    protected static void setMapBadAnswerAndCreateStatisticsAnswer(boolean isCorrectAnswer,String key,String value){

        if(isCorrectAnswer){
            goodAnswer = goodAnswer + 1;
        }else{
            badAnswer = badAnswer +1;
            tempMapWrongAnswer.put(key,value);
        }

    }

    protected static int getGoodAnswer(){ return goodAnswer; }
    protected static int getBadAnswer(){ return badAnswer; }
    protected static int getMapLength(){ return mapLength = dictionaryPolishEnglishWord_Map.size(); }
    protected static void restetAllValue(){
        goodAnswer = 0;
        badAnswer = 0;
        mapLength = 0;
    }
}
