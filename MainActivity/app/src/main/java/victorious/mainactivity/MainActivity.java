package victorious.mainactivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;


public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
<<<<<<< HEAD

<<<<<<< Updated upstream:Test/app/src/main/java/victorious/test/MainActivity.java
  
=======
<<<<<<< Updated upstream
    // Githubtest_l33tchange
=======

>>>>>>> Stashed changes
>>>>>>> origin/master
=======
    helloButton = (Button).findViewById(R.id.helloButton);
>>>>>>> Stashed changes:MainActivity/app/src/main/java/victorious/mainactivity/MainActivity.java

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
    OnClickListener hello = new OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

    helloButton.setOnClickListener(hello);

    String name = inputName.getText().toString();
    outputName.setText("Hello, " + name + "!");
}
