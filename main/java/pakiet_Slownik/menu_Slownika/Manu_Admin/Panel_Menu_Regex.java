package pakiet_Slownik.menu_Slownika.Manu_Admin;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Panel_Menu_Regex extends JDialog {
    private JPanel panel_CheckBox = new JPanel();
    private static JCheckBox spaceInExpression = new JCheckBox("Okreœlenie spacji pocz¹tkowej i koñcowej wyra¿enia");
    private static JCheckBox bigAndSmallLetter = new JCheckBox("Dopasowanie ca³oœciowe do litera³u z bazy");
    private static JCheckBox matchOnlyWordInLitteral = new JCheckBox("Dopasowanie czêœciowe do litera³u z bazy");

    private JPanel panel_IconRegex = new JPanel();
    private static JLabel iconRegEx = null;//ikona Regex

    public Panel_Menu_Regex(JFrame owner) {
        super(owner,"Panel Konfiguracji Wyra¿eñ Regularnych",false);

        this.add(panel_CheckBox,BorderLayout.NORTH);
        panel_CheckBox.add(bigAndSmallLetter);
        panel_CheckBox.add(spaceInExpression);
        panel_CheckBox.add(matchOnlyWordInLitteral);
        matchOnlyWordInLitteral.addActionListener(
                (event)->{
                    System.out.println("how = "+matchOnlyWordInLitteral.isSelected());
                }
        );


        ImageIcon iconRegex = new ImageIcon("main/resources/ImageFile/regex.png");
        if(iconRegex.getIconHeight() >= 300){
            iconRegex = new ImageIcon(iconRegex.getImage().getScaledInstance((int)iconRegex.getIconWidth()/2,-1,Image.SCALE_DEFAULT));
        }
        iconRegEx = new JLabel(iconRegex,JLabel.CENTER);
        iconRegEx.setIcon(iconRegex);
        panel_IconRegex.add(iconRegEx,BorderLayout.CENTER);
        this.add(panel_IconRegex,BorderLayout.SOUTH);
        this.pack();
    }
    
    public static boolean isMatchOnlyWordInLitteral(){
        if(matchOnlyWordInLitteral.isSelected()){
            return true;
        }else{ return  false; }
    }

    public static boolean isSpaceInExpression(){
        if(spaceInExpression.isSelected()){
            return true;
        }else{ return  false; }
    }

    public static boolean isBigAndSmallLetter(){
        if(bigAndSmallLetter.isSelected()){
            return true;
        }else{ return  false; }
    }

    public static List<String> setCorrectInsertDateToDatabase(String literal, List<String> listAllPolishWord) {
        List<String> tempListRepeatWord = new ArrayList<String>();
        for(String el : listAllPolishWord){
            Pattern pattern = Pattern.compile(el);
            Matcher matcher = pattern.matcher(literal);
            if(matcher.find()){
                tempListRepeatWord.add(el);
            }
        }
        for(String el : listAllPolishWord){
            Pattern pattern = Pattern.compile(literal);
            Matcher matcher = pattern.matcher(el);
            if(matcher.find()){
                if(isRepeated(tempListRepeatWord,el)){
                    tempListRepeatWord.add(el);
                }
            }
        }
        return tempListRepeatWord;
    }

    private static boolean isRepeated(List<String> repeatedWordsList,String word){
        //return false this is repeated word in tempListRepeatWord
        //return true this isn't repeated word in tempListRepeatWord
        for(String elx: repeatedWordsList){
            if(elx.equals(word)) return false;
        }
        return true;
    }
}
