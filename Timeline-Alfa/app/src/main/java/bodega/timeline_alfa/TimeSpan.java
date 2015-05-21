package bodega.timeline_alfa;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;


public class TimeSpan extends ActionBarActivity {

    NumberPicker noPicker = null;
    EditText noPicker2 = null;

    private Button playButton;
    private Button backToSetUpButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_span2);

        noPicker = (NumberPicker) findViewById(R.id.pickNumber1);
        noPicker.setMaxValue(1000);
        noPicker.setMinValue(0);
        noPicker.setWrapSelectorWheel(false);

        noPicker2 = (EditText) findViewById(R.id.editLowerBound);

        playButton = (Button) findViewById(R.id.PlayTimeSpan);
        backToSetUpButton = (Button) findViewById(R.id.BackFromTimeSpan);


        playButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
<<<<<<< HEAD
                startActivity(new Intent(TimeSpan.this, GameActivity.class));
=======
                startActivity(new Intent(TimeSpan.this,GameActivity.class));
>>>>>>> origin/Beta_Refactored
            }
        });
        backToSetUpButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(TimeSpan.this,Setup.class));

            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_time_span, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
