package pakiet_Slownik_Test;

import pakiet_Slownik.ConnectToDatabase.ConnectToDataBaseClass;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Assertions;

public class ConnectToDataBaseClass_Test {

    @BeforeEach
    void initializationConnectDatabase_Test(){
        ConnectToDataBaseClass.getConnectToDataBaseClass("mysql","root","Magda1981");
    }

    @Test
    void getConnectClass_Test(){
        Assertions.assertEquals("po³¹czenie istnieje",ConnectToDataBaseClass.getConnectString());
    }
}
