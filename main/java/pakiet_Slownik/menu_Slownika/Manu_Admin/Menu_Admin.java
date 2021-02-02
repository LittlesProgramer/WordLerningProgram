package pakiet_Slownik.menu_Slownika.Manu_Admin;
import pakiet_Slownik.GBC;
import javax.swing.*;
import java.awt.*;
import java.io.*;

public class Menu_Admin extends JMenu {

    JMenuItem connect_To_DataBase = new JMenuItem("Konfiguracja Po³¹czenia z Baz¹ Danych");
    JMenuItem set_Regex_Pattern = new JMenuItem("Ustawienia wyra¿eñ regularnych do odpytywania");
    JMenuItem make_Copy_This_Database = new JMenuItem("Wykonuje kopiê ca³ej bazy Slownika");

    public Menu_Admin(String tittle, JFrame owner) {
        this.setText(tittle);

        this.add(connect_To_DataBase);
        connect_To_DataBase.setToolTipText("Ta opcja pozwala skonfigurowaæ dostêp do bazy danych");
        connect_To_DataBase.addActionListener(
                (a)->{
                    new Panel_Menu_DataBase(owner).setVisible(true);
                }
        );

        this.add(set_Regex_Pattern);
        set_Regex_Pattern.setToolTipText("Ta opcja pozwala skonfigurowaæ przydatne opcje podczas odpytywania ze s³ówek");
        set_Regex_Pattern.addActionListener(
                (event)->{
                    new Panel_Menu_Regex(owner).setVisible(true);
                }
        );

        this.add(make_Copy_This_Database);
        make_Copy_This_Database.setToolTipText("Ta opcja pozwala wykonaæ kopiê bezpieczeñstwa bazy Slownika");
        make_Copy_This_Database.addActionListener(
                (event)->{
                    new Panel_Menu_Copy_Database(owner).setVisible(true);
                }
        );
    }
}

class Panel_Menu_DataBase extends JDialog{

    private JPanel panel = new JPanel();
    private JPanel panel1 = new JPanel();

    private JLabel userRoot = new JLabel("userRoot");
    private JTextField userRootT = new JTextField(10);
    private JLabel passRoot = new JLabel("passRoot");
    private JTextField passRootT = new JTextField(10);

    private JLabel newDataBaseName = new JLabel("Nazwa bazy S³ownika");
    private JTextField newDataBaseNameT = new JTextField(10);

    private JLabel newUser = new JLabel("Nazwa nowego u¿ytkownika");
    private JTextField newUserT = new JTextField(10);
    private JLabel passForNewUser = new JLabel("Has³o nowego u¿ytkownika");
    private JTextField passForNewUserT = new JTextField(10);

    private JLabel tableForWordsName = new JLabel("Nazwa tabeli s³ówek");
    private JTextField tableForWordsNameT = new JTextField(10);

    private JLabel tableForFrazesName = new JLabel("Nazwa tabeli fraz");
    private JTextField tableForFrazesNameT = new JTextField(10);

    private JButton saveDate = new JButton("Zapisz wszystkie pola potrzebne do konfiguracji bazy");
    private JTextArea showResult = new JTextArea("Konfiguracja i testowe po³¹czenie z baz¹");
    private JButton testConnectToDataBase = new JButton("Testowe po³¹czenie i konfiguracja bazy danych");

    private String user = "";
    private String password = "";
    private String zmiennaConf = "t23r3a"; //Zmienna opisuj¹ca ju¹ skonfigurowan¹ bazê
    private String nazwaPlikuConfig = ".\\ConfigBazaDanych.sql"; // Nazwa pliku konfiguracji bazy danych tworzony w zak³adace konfiguracja po³¹czenia z baz¹ danych

    public Panel_Menu_DataBase(JFrame owner) {
        super(owner,"Test Okna o programie",true);

        this.add(panel,BorderLayout.WEST);
        panel.setBorder(BorderFactory.createLineBorder(Color.green));
        GridBagLayout layout = new GridBagLayout();
        panel.setLayout(layout);

        panel.add(newUser,new GBC(0,0,1,1).setWeight(100,10).setFill(GBC.HORIZONTAL).setInsets(3));
        panel.add(newUserT,new GBC(1,0,1,1).setWeight(100,10).setFill(GBC.HORIZONTAL).setInsets(3));
        panel.add(passForNewUser,new GBC(0,1,1,1).setWeight(100,10).setFill(GBC.HORIZONTAL).setInsets(3));
        panel.add(passForNewUserT,new GBC(1,1,1,1).setWeight(100,10).setFill(GBC.HORIZONTAL).setInsets(3));

        panel.add(newDataBaseName,new GBC(0,2,1,1).setAnchor(GBC.CENTER).setWeight(100,10).setInsets(3,3,3,3));
        panel.add(newDataBaseNameT,new GBC(1,2,1,1).setWeight(100,10).setFill(GBC.HORIZONTAL).setInsets(3,3,3,3));

        panel.add(tableForWordsName,new GBC(0,3,1,1).setAnchor(GBC.CENTER).setWeight(100,10).setInsets(3,3,3,3));
        panel.add(tableForWordsNameT,new GBC(1,3,1,1).setWeight(100,10).setFill(GBC.HORIZONTAL).setInsets(3,3,3,3));

        panel.add(tableForFrazesName,new GBC(0,4,1,1).setAnchor(GBC.CENTER).setWeight(100,10).setInsets(3));
        panel.add(tableForFrazesNameT,new GBC(1,4,1,1).setWeight(100,10).setFill(GBC.HORIZONTAL).setInsets(3));

        panel.add(saveDate,new GBC(0,5,2,1).setWeight(100,10).setFill(GBC.HORIZONTAL).setInsets(3));
        //----------------------------------------------------------------------------------------------------------------------------------------------
        this.add(panel1,BorderLayout.EAST);
        panel1.setBorder(BorderFactory.createLineBorder(Color.green));
        panel1.setLayout(layout);

        panel1.add(userRoot,new GBC(0,0,1,1).setAnchor(GBC.CENTER).setWeight(100,10).setInsets(3));
        panel1.add(userRootT,new GBC(1,0,1,1).setWeight(100,10).setFill(GBC.HORIZONTAL).setInsets(3));
        panel1.add(passRoot,new GBC(2,0,1,1).setAnchor(GBC.CENTER).setWeight(100,10).setInsets(3));
        panel1.add(passRootT,new GBC(3,0,1,1).setWeight(100,10).setFill(GBC.HORIZONTAL).setInsets(3));
        panel1.add(showResult,new GBC(0,1,4,1).setWeight(100,100).setFill(GBC.BOTH).setInsets(3,3,3,3));
        showResult.setBorder(BorderFactory.createLineBorder(Color.green));
        showResult.setEditable(false);
        showResult.setLineWrap(true);
        panel1.add(testConnectToDataBase,new GBC(0,2,4,1).setWeight(100,0).setFill(GBC.HORIZONTAL).setInsets(3));

        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File("configBase.dat")));
            String enable = reader.readLine();
            reader.close();

            if(enable.equals(zmiennaConf)){
                this.enabledDatabaseConfigPanel();
            }

        }catch(IOException ioExc){ JOptionPane.showMessageDialog(null,"Proszê w kolejnoœci:\n" +
                " - Wype³niæ i zapisaæ pola Konfiguracji Bazy \n" +
                " - Stworzyæ configuracje bazy i przetestowaæ po³¹czenie"); }

        testConnectToDataBase.addActionListener(

                (a)->{

                    user = userRootT.getText();
                    password = passRootT.getText();

                    try {
                        if(user.equals("") || password.equals("")){
                            user = " ";
                            password = " ";
                        }

                        ProcessBuilder pb = new ProcessBuilder("mysql","-u"+user,"-p"+password);
                        Process pr = pb.start();
                        BufferedWriter output = new BufferedWriter(new OutputStreamWriter(pr.getOutputStream()));
                        BufferedReader error = new BufferedReader(new InputStreamReader(pr.getErrorStream()));
                        output.write("source "+nazwaPlikuConfig);
                        output.flush();
                        output.close();
                        String err = null;

                        if(!error.readLine().equals("mysql: [Warning] Using a password on the command line interface can be insecure")){
                            err = error.readLine();
                        }
                        showResult.setForeground(Color.black);
                        if(err == null){
                            showResult.setText("Success Configured Database");
                            showResult.setForeground(Color.green);
                            PrintWriter print = new PrintWriter("configBase.dat");
                            print.println(zmiennaConf);
                            print.close();
                            this.enabledDatabaseConfigPanel();
                        }else{
                            showResult.setText(err);
                            showResult.setForeground(Color.red);
                            if(err.equals("ERROR at line 1: Failed to open file"+" '"+nazwaPlikuConfig+"'"+", error: 2")){
                                JOptionPane.showMessageDialog(null,"Utwórz plik konfiguracji "+nazwaPlikuConfig+" za pomoc¹ -> Zapisz wszystkie pola potrzebne do konfiguracji bazy");
                            }
                        }

                    }catch(IOException ioExc){
                        JOptionPane.showMessageDialog(null,ioExc.getMessage());
                    }
                }
        );

        saveDate.addActionListener(
                (a)->{
                    try {
                        String newUserName = newUserT.getText();
                        String newUserPasswd = passForNewUserT.getText();
                        String dataBaseName = newDataBaseNameT.getText();
                        String tableForWords = tableForWordsNameT.getText();
                        String tableForFraze = tableForFrazesNameT.getText();

                        if(newUserName.equals("")||newUserPasswd.equals("")||dataBaseName.equals("")||tableForWords.equals("")||tableForFraze.equals("")){
                            JOptionPane.showMessageDialog(null,"Proszê uzupe³niæ wszystkie wymagane pola");
                        }else{

                            PrintWriter pr = new PrintWriter(new File(nazwaPlikuConfig));

                            pr.print("create database "+dataBaseName+";\r\n");
                            pr.print("use "+dataBaseName+";\r\n");
                            pr.print("create table "+tableForWords+"(lp int not null auto_increment primary key,polish varchar(20) not null,english varchar(20) not null,page int not null);\r\n");
                            pr.print("create table "+tableForFraze+"(lp int not null auto_increment primary key,polishPhrase varchar(20) not null,englishPhrase varchar(20) not null,page int not null);\r\n");
                            pr.print("grant all on *.* to "+newUserName+"@localhost identified by "+"'"+newUserPasswd+"'"+" with grant option;");
                            pr.close();

                            showResult.setText("Utworzono plik konfiguracyjny: "+nazwaPlikuConfig);
                            showResult.setForeground(Color.green);
                        }

                    }catch(FileNotFoundException fileNotFound){ JOptionPane.showMessageDialog(null,fileNotFound.getMessage());}
                }
        );
        pack();
    }

    public void enabledDatabaseConfigPanel(){
        userRoot.setEnabled(false);
        userRootT.setEnabled(false);
        passRoot.setEnabled(false);
        passRootT.setEnabled(false);
        newUser.setEnabled(false);
        newUserT.setEnabled(false);
        passForNewUser.setEnabled(false);
        passForNewUserT.setEnabled(false);
        newDataBaseName.setEnabled(false);
        newDataBaseNameT.setEnabled(false);
        tableForWordsName.setEnabled(false);
        tableForWordsNameT.setEnabled(false);
        tableForFrazesName.setEnabled(false);
        tableForFrazesNameT.setEnabled(false);
        saveDate.setEnabled(false);
        testConnectToDataBase.setEnabled(false);
        showResult.setEnabled(false);
    }
}
