package bodega.timeline_alfa;

/**
 * Created by victornyden on 15-04-24.
 */
public class yearButton {
    int year;

    public yearButton(int year){
        this.year= year;

    }

    public void  addYear(){
        this.year= year+1;
    }
    public int getYear() {
        return year;

    }
}
