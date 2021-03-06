package victorioussecret.timeline_beta;

/**
 * Created by theYellowBird and victornyden on 2015-05-01.
 */
public class TimelineTables {

    public static abstract class Questions {

        public static final String TABLE_NAME = "Questions";
        public static final String COL_CATEGORY = "category";
        public static final String COL_QUESTION = "question";
        public static final String COL_YEAR = "year";
        public static final String COL_BOOLEAN = "boolean";


    }

    public static abstract class HighScore {

        public static final String TABLE_NAME = "HighScore";
        public static final String COL_INTKEY = "intKey";
        public static final String COL_NAME = "playerName";
        public static final String COL_SCORE = "playerScore";

    }

}
