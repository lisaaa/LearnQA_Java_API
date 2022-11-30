package other;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.Assert.assertTrue;

public class StringLenghtTest {

    public static final int LENGHT = 15;

    @ParameterizedTest
    @ValueSource(strings = {"меньше15","длинабольше15символов"})
    public void checkStringlenght(String text){

        assertTrue("В этом тексте больше 15 сиволов",text.length() > LENGHT);

    }
}
