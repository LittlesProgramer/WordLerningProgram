package pakiet_Slownik.menu_Slownika.Manu_Admin;

import org.junit.jupiter.api.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import java.util.List;

class Panel_Menu_RegexTest {

    @Test
    void setCorrectInsertDateToDatabase() {
        //given
        ListAllpolishWordStub stub = new ListAllpolishWordStub();
        System.out.println("stub = "+stub);

        //when
        List<String> listAllPolishWords = Panel_Menu_Regex.setCorrectInsertDateToDatabase("auto",stub.threePolishWords());
        System.out.println("size = "+listAllPolishWords);

        //then
        assertThat(listAllPolishWords,hasSize(3));
    }
}