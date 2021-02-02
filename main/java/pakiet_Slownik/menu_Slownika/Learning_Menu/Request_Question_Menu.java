package pakiet_Slownik.menu_Slownika.Learning_Menu;
import javax.swing.*;

public class Request_Question_Menu extends JMenu {
    JMenuItem auto_request_User = new JMenuItem("Losowe odpytywanie ze s³ówek");
    JMenuItem auto_request_User_Fraze = new JMenuItem("Losowe odpytywanie z fraz");

    public Request_Question_Menu(String tittle, JFrame owner) {
        this.setText(tittle);

        this.add(auto_request_User);
        auto_request_User.setToolTipText("Opcja pozwalaj¹ca na odpytywanie ze s³ówek");
        auto_request_User.addActionListener(
                (event)->{
                    new Random_Request_Words(owner);
                }
        );
        this.add(auto_request_User_Fraze);
        auto_request_User_Fraze.setToolTipText("Opcja pozwalaj¹ca na odpytywanie z fraz wyrazowych(rozmówki)");
        auto_request_User_Fraze.addActionListener(
                (event)->{
                    new Random_Request_Fraze(owner);
                }
        );

    }
}
