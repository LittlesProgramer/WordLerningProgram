package pakiet_Slownik.menu_Slownika.Manu_Admin;
import pakiet_Slownik.ConnectToDatabase.Get_Database_Parameters;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Panel_Menu_Copy_Database extends JDialog{
    private JLabel labalCopy = new JLabel("<html><h1><i>KOPIA BEZPIECZENSTWA BAZY SLOWNIKA</i></h1><hr></html>");
    private JTextArea showResult = new JTextArea(10,10);
    private JScrollPane scrollPane = new JScrollPane(showResult);
    private JButton copy = new JButton("Wykonaj Kopiê");
    private String zmiennaConf = "t23r3a";

    public Panel_Menu_Copy_Database(JFrame owner) {
        super(owner,"Panel pozwalaj¹cy na kopie bazy Slownika",false);

        this.add(labalCopy, BorderLayout.NORTH);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.blue));
        this.add(scrollPane);
        this.add(copy,BorderLayout.SOUTH);
        copy.addActionListener(
                (event)->{

                    showResult.setForeground(Color.black);
                    showResult.setText("");

                    if(isDataBaseConfigured()){ //Jeêli skonfigurowano po³¹czenie z baz¹
                        try {

                            boolean copyWord = false;
                            boolean copyFraze = false;

                            Statement st = Get_Database_Parameters.getStart_Logic_Class(true);
                            String tableWordName = Get_Database_Parameters.getTableName();
                            String tableFrazeName = Get_Database_Parameters.getTablePhraseName();

                            Date date = new Date();
                            SimpleDateFormat formatter = new SimpleDateFormat("dd MM yyyy  HH mm ss");
                            String actualDate = formatter.format(date);

                            String fileWordName = "kopiaTabeliSlowek_"+actualDate+".txt";
                            String fileFrazeName = "kopiaTabeliFraz_"+actualDate+".txt";

                            String pathAbsolute = new File(".").toPath().toAbsolutePath().normalize().toString().replaceAll("\\\\","/");

                            String pathToCopyWord = pathAbsolute+ "/" +fileWordName;
                            String pathToCopyFraze = pathAbsolute+ "/" +fileFrazeName;

                            String commanWordCopyTable = "select * into outfile \""+pathToCopyWord+"\" fields enclosed by '\"' terminated by ';' lines terminated by '\r\n' from "+tableWordName;
                            String commandFrazeCopyTable = "select * into outfile \""+pathToCopyFraze+"\" fields enclosed by '\"' terminated by ';' lines terminated by '\r\n' from "+tableFrazeName;

                            copyWord = st.execute(commanWordCopyTable);
                            copyFraze = st.execute(commandFrazeCopyTable);
                            st.close();

                            if(!copyFraze){
                                showResult.setText("Wykonano kopiê tabeli z frazami wyrazowymi"+"\n");
                                showResult.setForeground(Color.green);
                            }else{showResult.setText("Nie wykonano kopi tabeli z frazami wyrazowymi"+"\n"); showResult.setForeground(Color.red);}

                            if(!copyWord){
                                showResult.append("Wykonano kopiê tabeli ze s³ówkami"+"\n");
                                showResult.setForeground(Color.green);
                            }else{showResult.append("Nie wykonano kopi tabeli ze s³ówkami"+"\n"); showResult.setForeground(Color.red);}

                        }catch(FileNotFoundException fileExc){
                            JOptionPane.showMessageDialog(null,"Nie mo¿na znaleŸæ pliku");
                        }catch(SQLException sqlExc){
                            JOptionPane.showMessageDialog(null,sqlExc.getMessage());
                        }
                    }else{
                        showResult.setText("Najpierw skonfiguruj po³¹czenie z baz¹ danych w panelu administracyjnym");
                    }
                }
        );
        this.pack();
    }

    public boolean isDataBaseConfigured(){
        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File("configBase.dat")));
            String configuredBase = reader.readLine();
            reader.close();

            if (configuredBase.equals(zmiennaConf)) {
                return true;
            }

        }catch(IOException ioExc){
            JOptionPane.showMessageDialog(null,ioExc.getMessage());
        }
        return false;
    }
}
