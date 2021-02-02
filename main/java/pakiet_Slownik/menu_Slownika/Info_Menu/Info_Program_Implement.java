package pakiet_Slownik.menu_Slownika.Info_Menu;
import javax.swing.*;
import java.io.File;
import java.io.InputStream;
import java.util.Scanner;

@SuppressWarnings("SpellCheckingInspection")
public class Info_Program_Implement extends JDialog {
    private String werBaza = null;
    private String werApli = null;
    private String releaseDate = null;

    public Info_Program_Implement(JFrame owner) {
        super(owner,"Opis Programu",false);

        InputStream in = Info_Program_Implement.class.getResourceAsStream("/Info_Aplik.txt");
        Scanner scan = new Scanner(in);
        String tab[] = new String[3];
        int x = 0;
        while(scan.hasNext()){
            tab[x] = scan.nextLine();
            x++;
        }

        werBaza = tab[0];
        werApli = tab[1];
        releaseDate = tab[2];

        JLabel lab = new JLabel(
                "<html>" +
                        "<h1> Aplikacja Wspomagaj¹ca Naukê Uczenia S³ówek Obcojêzycznych </h1>" +
                        "<hr>" +
                        "<h3> Wykorzystywana Baza Danych: </h3>"+
                        "<h4><i>"+werBaza+" , wersja sterownika: 6.0.6 </i></h4>"+
                        "<hr>"+
                        "<h3> Wersja aplikacji: </h3>"+
                        "<h4><i>"+werApli+"</i></h4>"+
                        "<hr>"+
                        "<h3> Data wydania: </h3>"+
                        "<h4><i>"+releaseDate+"</i></h4>"+
                        "<hr>"+
                        "<h3> Sporz¹dzono przez:  </h3>" +
                        "<h4><i> Piotr Mirkowski </i></h4>"+
                        "</html>"
        );

        this.add(lab);
        this.pack();
        this.setVisible(true);
    }
}
