package pakiet_Slownik;

import pakiet_Slownik.menu_Slownika.Delete_Menu.Delete_Date;
import pakiet_Slownik.menu_Slownika.Exit_Menu.Exit_Menu;
import pakiet_Slownik.menu_Slownika.Info_Menu.Menu_Info;
import pakiet_Slownik.menu_Slownika.Insert_Menu.Insert_Date;
import pakiet_Slownik.menu_Slownika.Manu_Admin.Menu_Admin;
import pakiet_Slownik.menu_Slownika.Learning_Menu.Request_Question_Menu;
import javax.swing.*;

public class PasekMenu extends JMenuBar {

    Menu_Admin menu_Admin ;
    Insert_Date insert_date;
    Delete_Date delete_date;
    Request_Question_Menu request_User;
    Menu_Info menu_Info ;
    Exit_Menu exit;

    public PasekMenu(JFrame frame) {

        menu_Admin = new Menu_Admin("Administracja Aplikacj¹",frame);
        insert_date = new Insert_Date("Wprowadzanie Danych",frame);
        delete_date = new Delete_Date("Usuwanie Danych",frame);
        request_User = new Request_Question_Menu("Nauka S³ówek",frame);
        menu_Info = new Menu_Info("Info o programie",frame);
        exit = new Exit_Menu("Wyjœcie z Aplikacji");

        this.add(menu_Admin);
        this.add(insert_date);
        this.add(delete_date);
        this.add(request_User);
        this.add(menu_Info);
        this.add(exit);

    }
}
