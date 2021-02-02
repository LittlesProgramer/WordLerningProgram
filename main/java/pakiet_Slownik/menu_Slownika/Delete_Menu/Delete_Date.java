package pakiet_Slownik.menu_Slownika.Delete_Menu;

import javax.swing.*;
import java.io.FileNotFoundException;

public class Delete_Date extends JMenu {
    JMenuItem menu_Delete = new JMenuItem("Usuwanie danych z bazy");
    JMenuItem menu_Delete_Fraze = new JMenuItem("Usuwanie fraz wyrazowych z bazy");

    public Delete_Date(String title,JFrame owner) {
        this.setText(title);

        this.add(menu_Delete);
        menu_Delete.setToolTipText("Opcja pozwalaj¹ca usun¹æ niepo¿¹dane s³owo z bazy");
        menu_Delete.addActionListener(
                (event)->{
                    try {
                        new DeleteWordsFromDatabase(owner);
                    }catch(FileNotFoundException fileExc){JOptionPane.showMessageDialog(null,fileExc.getMessage());}
                }
        );

        this.add(menu_Delete_Fraze);
        menu_Delete_Fraze.setToolTipText("Opcja pozwalaj¹ca usun¹æ niepo¿¹dan¹ frazê z bazy");
        menu_Delete_Fraze.addActionListener(
                (event)->{
                    try {
                        new DeletePhraseFromDatabase(owner);
                    }catch(FileNotFoundException fileExc){JOptionPane.showMessageDialog(null,fileExc.getMessage());}
                }
        );

    }
}
