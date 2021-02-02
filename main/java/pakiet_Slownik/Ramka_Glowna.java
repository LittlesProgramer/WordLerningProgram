package pakiet_Slownik;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;

public class Ramka_Glowna extends JFrame {
    Ramka_Glowna r = this;
    JLabel labelImage;

    public Ramka_Glowna() throws HeadlessException{
        this.setJMenuBar(new PasekMenu(r));
        ImageIcon iconSlownik = new ImageIcon(Ramka_Glowna.class.getResource("/ImageFile/slownik.png"));

        //set and scalled icon if it size is longer than width screen
        if(iconSlownik.getIconWidth() < this.getWidth()) {
            iconSlownik = new ImageIcon(iconSlownik.getImage().getScaledInstance((int)getWidth()/2, -1, Image.SCALE_DEFAULT));
        }

        labelImage = new JLabel(iconSlownik, JLabel.CENTER);
        r.add(labelImage);
    }
}
