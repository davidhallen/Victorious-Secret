package victorioussecret.timeline_alfa;

import android.content.Intent;
import android.test.ActivityUnitTestCase;

/**
 * Created by theYellowBird on 2015-05-27.
 */
public class QuestionAdderActivityTest extends ActivityUnitTestCase<GameEngine> {

    GameEngine ge;


    public QuestionAdderActivityTest() {
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
