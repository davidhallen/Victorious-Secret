package bodega.timeline_alfa;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ActivityTestCase;
import android.test.ActivityUnitTestCase;
import android.widget.TextView;

/**
 * Created by theYellowBird on 2015-05-27.
 */
public class QuestionAdderTest extends ActivityUnitTestCase<GameEngine> {

    GameEngine ge;


    public QuestionAdderTest() {
        super(GameEngine.class);
    }

    protected void setUp () throws Exception {
        startActivity(new Intent(getInstrumentation().getTargetContext(), GameEngine.class), null, null);
        ge = (GameEngine)getActivity();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void isBesidetest(){

        String actual=tvHello.getText().toString();

        // The expected text to be displayed in the textview
        String expected = "Hello world!";

        // Check whether both are equal, otherwise test fails
        assertEquals(expected,actual );
    }


}
