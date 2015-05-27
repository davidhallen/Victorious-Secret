package bodega.timeline_alfa.testing;

import android.test.ActivityInstrumentationTestCase2;
import android.test.ActivityUnitTestCase;
import android.test.InstrumentationTestCase;

import bodega.timeline_alfa.ExtrasActivity;

/**
 * Created by theYellowBird on 2015-05-27.
 */
public class UnitTests extends ActivityUnitTestCase <QuestionAdder> {

    private Solo solo;

    public UnitTests() {
        super(ExtrasActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        solo = new Solo(getInstrumentation(), getActivity());
    }


}
