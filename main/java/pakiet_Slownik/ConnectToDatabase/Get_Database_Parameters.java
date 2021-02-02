package pakiet_Slownik.ConnectToDatabase;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Get_Database_Parameters {
    private static String dataBaseName = null;
    private static String tableName = null;
    private static String tablePhraseName = null;
    private static String userName = null;
    private static String userPass = null;
    private static Statement st = null;
    private static Pattern onlyLetter = Pattern.compile("\\D+");
    private static Pattern onlyNumber = Pattern.compile("\\d+");
    private static String fileNameDatabaseConfig = ".\\ConfigBazaDanych.sql";

    public static Statement getStart_Logic_Class(boolean connectBase) throws FileNotFoundException{
        //Connect to databse if connectBase is true

        Pattern patternBase = Pattern.compile("create database (\\D+);");
        Pattern patternTableWords = Pattern.compile("create table (\\D+?)\\(.+;");
        Pattern patternTablePhrases = Pattern.compile("create table (\\D+?)\\(.+;");
        Pattern patternUserName = Pattern.compile("grant all on *.* to (\\D+)@.+;");
        Pattern patternUserPass;
        File file = new File(fileNameDatabaseConfig);

            Scanner scanner = new Scanner(new DataInputStream(new FileInputStream(file)));
            String line;

            while(scanner.hasNext()){
                line = scanner.nextLine();

                Matcher matcherBase = patternBase.matcher(line);
                Matcher matcherTableWords = patternTableWords.matcher(line);
                Matcher matcherTablePhrases = patternTablePhrases.matcher(line);
                Matcher matcherUserName = patternUserName.matcher(line);

                if(matcherBase.matches()){
                    dataBaseName = matcherBase.group(1);
                }
                if(matcherTableWords.matches() && tableName == null){
                    tableName = matcherTableWords.group(1);
                }
                if(matcherTablePhrases.matches()){
                    tablePhraseName = matcherTablePhrases.group(1);
                }
                if(matcherUserName.matches()){
                    userName = matcherUserName.group(1);
                }

                patternUserPass = Pattern.compile("grant all on *.* to "+userName+"@localhost identified by '(\\D+)'.+;");
                Matcher matcherUserPass = patternUserPass.matcher(line);

                if(matcherUserPass.matches()){
                    userPass = matcherUserPass.group(1);
                }
            }

            if(connectBase)  st = ConnectToDataBaseClass.getConnectToDataBaseClass(dataBaseName,userName,userPass);

        return st;
    }

    public static boolean getOnlyLetter(String literal){
        Matcher matcher = onlyLetter.matcher(literal);
        return matcher.matches();
    }

    public static boolean getOnlyNumber(String literal){
        Matcher matcher = onlyNumber.matcher(literal);
        return matcher.matches();
    }

    public static String getTableName() throws FileNotFoundException{
        getStart_Logic_Class(false);
        return tableName;
    }

    public static String getTablePhraseName() throws FileNotFoundException{
        getStart_Logic_Class(false);
        return tablePhraseName;
    }

    //set this value for Test Junit5
    public static void setFileNameDatabaseConfig_ForTest(){
        fileNameDatabaseConfig = "plikKtoregoNieMa.txt";
    }
}
