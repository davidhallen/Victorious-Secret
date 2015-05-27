package victorioussecret.timeline_beta.testing;

import android.test.ActivityUnitTestCase;

import victorioussecret.timeline_beta.ExtrasActivity;

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
