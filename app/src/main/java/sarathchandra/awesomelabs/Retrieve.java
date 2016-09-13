package sarathchandra.awesomelabs;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SaratChandra on 9/13/2016.
 */
public class Retrieve extends AppCompatActivity {
    private static final String URL="http://10.0.2.2/rest/get.php";
    Button submit1,main;
    public String paramid,jMsg;
    public String jName,jAddress,jEmail,jPhone,jPin;
    EditText getId;
    TextView result;
    JSONArray user;
    JSONParser jparser= new JSONParser();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.retrieve);
        setTitle("Retrieve");
        submit1=(Button)findViewById(R.id.submit1);
        main=(Button)findViewById(R.id.main);
        getId=(EditText)findViewById(R.id.getid);
        result=(TextView)findViewById(R.id.result);
        submit1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paramid=getId.getText().toString();
                new getUser().execute();
            }
        });
         main.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent menu=new Intent(getApplicationContext(),MainActivity.class);
                 startActivity(menu);
                 finish();
             }
         });
    }
    class getUser extends AsyncTask<Void,Void,Void>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            List<NameValuePair> param = new ArrayList<NameValuePair>();
            param.add(new BasicNameValuePair("id",paramid));
            JSONObject jUserObject=jparser.makeHttpRequest(URL,"GET",param);
            try {
                jMsg = jUserObject.getString("msg");
                    if(jMsg.equals("success")) {
                        user = jUserObject.getJSONArray("users");
                        for (int i = 0; i < user.length(); i++) {
                            JSONObject jUser = user.getJSONObject(i);
                             jName = jUser.getString("Name");
                             jAddress = jUser.getString("Address");
                             jPhone = jUser.getString("Phone_number");
                             jEmail = jUser.getString("Email");
                             jPin = jUser.getString("pin");

                        }
                    }

            }

            catch (JSONException e) {
                e.printStackTrace();
                //jMsg="connection unsuccessful";
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(jMsg.equals("success"))
            result.setText("Name :"+ jName+"\nAddress :"+jAddress+"\nPin :"+jPin+"\nPhone :"+jPhone+"\nEmail :"+jEmail);
            else
                result.setText(jMsg);
        }
    }
}
