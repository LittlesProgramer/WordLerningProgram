package pakiet_Slownik.menu_Slownika.Exit_Menu;
import javax.swing.*;

public class Exit_Menu extends JMenu {
    JMenuItem exit_Aplic = new JMenuItem("Wyjscie z aplikacji");

    public Exit_Menu(String tittle) {
        this.setText(tittle);
        this.add(exit_Aplic);
        exit_Aplic.addActionListener(
                (event)->{
                    System.exit(0);
                }
        );
    }
}
