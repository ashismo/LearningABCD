package com.app.ashish.ujanlearning;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String alphabet[][] = {{"A","B","C","D"}, {"E","F","G","H"},
                                {"I","J","K","L"}, {"M","N","O","P"},
                                {"Q","R","S","T"}, {"U","V","W","X"},
                                {"","Y","Z",""}};
        TableLayout tableLayout = (TableLayout)findViewById(R.id.alphabet);
        ImageView imageView = (ImageView)findViewById(R.id.imageView_grid);
        imageView.setVisibility(View.INVISIBLE);

        for(int i = 0 ; i < alphabet.length; i++) {
            TableRow row = new TableRow(this);
            row.setBackgroundColor(Color.GRAY);
            row.setMinimumHeight(70);
            for(int j = 0; j < alphabet[i].length; j++) {
                final TextView textView = new TextView(this);

                textView.setText(alphabet[i][j]);

                //textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                textView.setTextSize(60);
                if((i+j) % 2 == 0) {
                    textView.setBackgroundColor(Color.WHITE);
                }
                textView.setGravity(Gravity.CENTER);

                textView.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v){

                        ImageView imageView = (ImageView)findViewById(R.id.imageView_grid);
//                        Bitmap bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888);
                        final String path = Environment.getRootDirectory().getPath() + "/res/alphabet_a.jpg";
//                        Toast.makeText(getApplicationContext(),textView.getText() + "- " + path ,Toast.LENGTH_SHORT).show();
                        try {
                            InputStream si1 = getAssets().open("english_" + textView.getText().toString().toLowerCase() + ".jpg");
                            Bitmap image = BitmapFactory.decodeStream(si1);
                            Bitmap scaledImage = Bitmap.createScaledBitmap(image, imageView.getWidth(), imageView.getHeight(), true);
                            imageView.setImageBitmap(scaledImage);
                            imageView.setVisibility(View.VISIBLE);
                        } catch(Exception e) {
                            e.printStackTrace();
                        }
//                        imageView.setImageResource(R.drawable.alphabet_a);
                        imageView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ImageView imageView = (ImageView)findViewById(R.id.imageView_grid);
                                imageView.setVisibility(View.INVISIBLE);
                            }
                        });
                    }
                });

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
