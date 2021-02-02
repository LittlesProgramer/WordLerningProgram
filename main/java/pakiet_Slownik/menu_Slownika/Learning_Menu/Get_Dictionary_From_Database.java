package pakiet_Slownik.menu_Slownika.Learning_Menu;

import pakiet_Slownik.ConnectToDatabase.Get_Database_Parameters;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.List;

public interface Get_Dictionary_From_Database {
    //zwraca WYBRANA listę słowek w zaleznosci od wypelnienia pol textowych
    public List<String> getArrayList(int howMuchWord,int pageNumber,boolean tableOrFraze);
}
