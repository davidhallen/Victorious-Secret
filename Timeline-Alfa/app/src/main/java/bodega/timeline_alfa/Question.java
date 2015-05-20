package bodega.timeline_alfa;

/**
 * Created by victornyden on 15-04-24.
 */
public class Question implements Comparable <Question> {
    int year;
    String question;

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

   /*
   Simple compareTo, will have to handle equal years later on
    */
    @Override public int compareTo (Question y){
        if (year > y.getYear()){
            return 1;
        }
        else return -1;

    }

    @Override public int hashCode () {
      return year + question.hashCode();
    }
}
