package bodega.timeline_alfa;

/**
 * Created by victornyden on 15-04-24.
 */
public class yearButton implements Comparable <yearButton> {
    int year;
    String question;

    public yearButton(int year, String question ){
        this.year= year;
        this.question = question;
    }

    public void  addYear(){
        this.year= year+1;
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
    @Override public int compareTo (yearButton y){
        if (year > y.getYear()){
            return 1;
        }
        else return -1;

    }
}
