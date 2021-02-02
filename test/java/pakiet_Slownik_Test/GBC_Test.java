package pakiet_Slownik_Test;
import pakiet_Slownik.GBC;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Assertions;

public class GBC_Test {
    GBC gbc;

    @BeforeEach
    void initialization_GBC_Test(){
        gbc = new GBC(1,1,1,1);
    }

    @Test
    void setAnchorTest(){
        Assertions.assertEquals(gbc,gbc.setAnchor(1));
    }

    @Test
    void setFillTest(){
        Assertions.assertEquals(gbc,gbc.setFill(1));
    }

    @Test
    void setWeightTest(){
        Assertions.assertEquals(gbc,gbc.setWeight(1,1));
    }

    @Test
    @DisplayName("testInsetsMethodWithOneParameter")
    void setInsetsTest1(){
        Assertions.assertEquals(gbc,gbc.setInsets(1));
    }

    @Test
    @DisplayName("testInsetsMethodWithFourParameter")
    void setInsetsTest2(){
        Assertions.assertEquals(gbc,gbc.setInsets(1,1,1,1));
    }

}
