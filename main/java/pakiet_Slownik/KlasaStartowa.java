package pakiet_Slownik;

import javax.swing.*;
import java.awt.*;

public class KlasaStartowa {
    public static void main(String[] args){
        Ramka_Glowna ramka_glowna = new Ramka_Glowna();
        Toolkit tool = Toolkit.getDefaultToolkit();
        Dimension dimension = tool.getScreenSize();
        int widthSize = (int)(dimension.getWidth()/2.5);
        int heightSize = (int)dimension.getHeight()/2;
        ramka_glowna.setPreferredSize(new Dimension(widthSize,heightSize));
        ramka_glowna.setName("Nowy_Samouczek_Angielskich_Słówek");
        ramka_glowna.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ramka_glowna.setVisible(true);
        ramka_glowna.pack();
    }
}
