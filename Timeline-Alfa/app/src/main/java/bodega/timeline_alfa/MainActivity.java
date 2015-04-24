package bodega.timeline_alfa;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        //WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        final TextView question = (TextView) findViewById(R.id.question);
        final Button BigBang = (Button) findViewById(R.id.BigBang);
        final Button Ragnarok = (Button) findViewById(R.id.Ragnarok);


        BigBang.setOnClickListener(new View.OnClickListener() {
            yearButton y1 = new yearButton(1);
            int clickedButton= 10;
            @Override
            public void onClick(View view){

                if(clickedButton%2 != 0) {
                    BigBang.setBackgroundColor(Color.rgb(0, 155, 0));
                    clickedButton++;
                }

                else{
                    BigBang.setBackgroundColor(Color.rgb(155, 0, 0));
                    clickedButton++;
                }
                 }
        });






        Ragnarok.setOnClickListener(new View.OnClickListener() {
            yearButton y1 = new yearButton(1);
            int clickedButton= 10;
            @Override
            public void onClick(View view){

                if(clickedButton%2 != 0) {
                    Ragnarok.setBackgroundColor(Color.rgb(0, 155, 0));
                    clickedButton++;
                }

                else{
                    Ragnarok.setBackgroundColor(Color.rgb(155, 0, 0));
                    clickedButton++;
                }
            }
        });

    }
}

