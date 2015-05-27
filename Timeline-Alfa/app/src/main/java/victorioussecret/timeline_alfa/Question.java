package victorioussecret.timeline_alfa;

/**
 * Created by victornyden on 15-04-24.
 *
 */

public class Question implements Comparable <Question> {
    int year;
    String question;
    String label;

    public Question(int year, String question ){
        this.year= year;
        this.question = question;
    }


    public int getYear() {
        return year;

    }
    public String getQuestion() {
        return question;

    }

    public String getYearLabel() {
        if (year > 0)
            label = "A.D";
        else if (year < 0)
            label = "B.C";
        else
            label ="";
        return label;
    }


    @Override public int compareTo (Question y){
        if (year > y.getYear()){
            return 1;
        }
        else if (year<y.getYear()){
            return -1;
        }
        else return 0;
    }

    @Override public int hashCode () {
      return year + question.hashCode();
    }

    @Override public boolean equals (Object object){
        if(object instanceof Question && ((Question)object).getYear() == this.year
                && ((Question)object).getQuestion().equals(this.getQuestion())) {
            return true;
        } else {
            return false;
        }
    }
}
