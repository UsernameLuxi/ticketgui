import com.example.ticketgui.GUI.util.VerifyTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestVerifyTime {

    @Test
    @DisplayName("Verify time valid input ")
    public void verifyTimeValid(){
        // arrange
        String validInput1 = "10:00";
        String validInput2 = "23:59";
        String validInput3 = "0:00";
        String validInput4 = "00:00";
        String validInput5 = "9:19";
        String validInput6 = "09:19";

        // act
        boolean actual1 = VerifyTime.verifyTime(validInput1);
        boolean expected1 = true;

        boolean actual2 = VerifyTime.verifyTime(validInput2);
        boolean expected2 = true;

        boolean actual3 = VerifyTime.verifyTime(validInput3);
        boolean expected3 = true;

        boolean actual4 = VerifyTime.verifyTime(validInput4);
        boolean expected4 = true;

        boolean actual5 = VerifyTime.verifyTime(validInput5);
        boolean expected5 = true;

        boolean actual6 = VerifyTime.verifyTime(validInput6);
        boolean expected6 = true;

        // assert
        // hvis én fejler kører resten stadig
        Assertions.assertAll(
                () -> Assertions.assertEquals(expected1, actual1),
                () ->Assertions.assertEquals(expected2, actual2),
                () ->Assertions.assertEquals(expected3, actual3),
                () ->Assertions.assertEquals(expected4, actual4),
                () ->Assertions.assertEquals(expected5, actual5),
                () ->Assertions.assertEquals(expected6, actual6)
        );
    }


    @Test
    @DisplayName("Verify time invalid input")
    public void verifyTimeInvalid(){
        // arrange
        String validInput1 = "-10:00";
        String validInput2 = "24:00";
        String validInput3 = "0:-12";
        String validInput4 = "60:60";
        String validInput5 = "8:0-0";
        String validInput6 = "0900";

        // act
        boolean expected = false;

        boolean actual1 = VerifyTime.verifyTime(validInput1);

        boolean actual2 = VerifyTime.verifyTime(validInput2);

        boolean actual3 = VerifyTime.verifyTime(validInput3);

        boolean actual4 = VerifyTime.verifyTime(validInput4);

        boolean actual5 = VerifyTime.verifyTime(validInput5);

        boolean actual6 = VerifyTime.verifyTime(validInput6);

        // assert
        // hvis én fejler kører resten stadig
        Assertions.assertAll(
                () -> Assertions.assertEquals(expected, actual1),
                () ->Assertions.assertEquals(expected, actual2),
                () ->Assertions.assertEquals(expected, actual3),
                () ->Assertions.assertEquals(expected, actual4),
                () ->Assertions.assertEquals(expected, actual5),
                () ->Assertions.assertEquals(expected, actual6)
        );
    }
}
