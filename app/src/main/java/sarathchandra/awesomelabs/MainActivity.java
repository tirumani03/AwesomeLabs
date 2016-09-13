package sarathchandra.awesomelabs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.3F);
    Button insert,retrieve;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        insert=(Button)findViewById(R.id.insert);
        retrieve=(Button)findViewById(R.id.retrieve);
          insert.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  v.startAnimation(buttonClick);
                  Intent in=new Intent(getApplicationContext(),Insert.class);
                  startActivity(in);
                  finish();
              }
          });
        retrieve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(buttonClick);
                Intent get=new Intent(getApplicationContext(),Retrieve.class);
                startActivity(get);
                finish();
            }
        });
    }
}