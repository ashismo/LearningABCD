package com.app.ashish.ujanlearning;

import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String alphabet[][] = {{"A","B","C","D"}, {"E","F","G","H"},
                                {"I","J","K","L"}, {"M","N","O","P"},
                                {"Q","R","S","T"}, {"U","V","W","X"},
                                {"Y","Z"}};
        TableLayout tableLayout = (TableLayout)findViewById(R.id.alphabet);

        for(int i = 0 ; i < alphabet.length; i++) {
            TableRow row = new TableRow(this);
            row.setBackgroundColor(Color.GRAY);
            for(int j = 0; j < alphabet[i].length; j++) {
                TextView textView = new TextView(this);
                textView.setText(alphabet[i][i]);
                if(j % 2 == 0) {
                    textView.setBackgroundColor(Color.WHITE);
                }
                textView.setGravity(Gravity.CENTER);
                row.addView(textView);
            }
            tableLayout.addView(row);
        }
    }


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
}
