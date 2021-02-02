package pakiet_Slownik.menu_Slownika.Insert_Menu;

import javax.swing.*;

public class Insert_Date extends JMenu {
    JMenuItem menu_Manual_Insert = new JMenuItem("R�czne wprowadzanie danych do bazy");
    JMenuItem menu_Auto_Insert = new JMenuItem("Auto wprowadzanie danych z pliku do bazy");
    JMenuItem menu_Manual_Insert_Fraze = new JMenuItem("R�czne wprowadzanie fraz do bazy");

    public Insert_Date(String tittle,JFrame owner) {
        this.setText(tittle);

        this.add(menu_Manual_Insert);
        menu_Manual_Insert.setToolTipText("Opcja pozwalaj�ca na r�czne wprowadzanie pojedy�czych s��wek do bazy danych");
        menu_Manual_Insert.addActionListener(
                (event)->{
                    new HandInsertedWordsToDatabase(owner);
                }
        );

        this.add(menu_Auto_Insert);
        menu_Auto_Insert.setToolTipText("Opcja pozwalaj�ca na automatyczne wprowadzanie danych z pliku.txt do bazy");

        this.add(menu_Manual_Insert_Fraze);
        menu_Manual_Insert_Fraze.setToolTipText("Opcja pozwalaj�ca na r�czne wprowadzanie fraz wyrazowych(rozm�wki) do bazy danych");
        menu_Manual_Insert_Fraze.addActionListener(
                (event)->{
                    new HandInsertedPhraseToDatabase(owner);
                }
        );
    }
}
