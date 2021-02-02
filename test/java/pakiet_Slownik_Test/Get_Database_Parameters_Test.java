package pakiet_Slownik_Test;
import org.junit.jupiter.api.Test;
import java.io.FileNotFoundException;
import org.junit.jupiter.api.Assertions;
import pakiet_Slownik.ConnectToDatabase.Get_Database_Parameters;


public class Get_Database_Parameters_Test {

    @Test
    void get_Start_Logic_Class_Test(){
        Get_Database_Parameters.setFileNameDatabaseConfig_ForTest();
        Assertions.assertThrows(FileNotFoundException.class,()->{ Get_Database_Parameters.getStart_Logic_Class(false); });
    }

}
