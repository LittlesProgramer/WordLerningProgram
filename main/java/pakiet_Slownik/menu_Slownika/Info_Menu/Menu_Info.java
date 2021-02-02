package pakiet_Slownik.menu_Slownika.Info_Menu;

import javax.swing.*;

public class Menu_Info extends JMenu {
    JMenuItem info_Program = new JMenuItem("O Aplikacji");

    public Menu_Info(String tittle,JFrame owner) {
        this.setText(tittle);

        this.add(info_Program);
        info_Program.setToolTipText("Opcja pozwalaj¹ca uzyskaæ informacje o konfiguracji aplikacji");
        info_Program.addActionListener(
                (event)->{
                    new Info_Program_Implement(owner);
                }
        );

    }
}
