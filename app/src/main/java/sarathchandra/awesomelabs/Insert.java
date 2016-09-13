package sarathchandra.awesomelabs;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
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
public class Insert extends AppCompatActivity {
    ProgressDialog pDialog;
    private static final String URL="http://10.0.2.2/rest/insert.php";
   public Button submit,clear,back;
   public EditText name,address,phone,zip,email;
   public String Tname,Taddress,Tphone,Tzip,Temail;
    public String desc;
    public TextView error;
    JSONParser jparser= new JSONParser();
    JSONArray result;
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.3F);
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.insert);
        setTitle("Insert");
        submit=(Button)findViewById(R.id.submit);
        back=(Button)findViewById(R.id.back);
        name=(EditText) findViewById(R.id.name);
        address=(EditText)findViewById(R.id.address);
        phone=(EditText)findViewById(R.id.phone);
        email=(EditText)findViewById(R.id.email);
        error=(TextView)findViewById(R.id.error);
        zip=(EditText)findViewById(R.id.zip);
        clear=(Button)findViewById(R.id.clear);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name.setText("");
                address.setText("");
                phone.setText("");
                zip.setText("");
                email.setText("");
                error.setText("");
                name.setFocusable(true);
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(buttonClick);
                 Tname=name.getText().toString();
                 Taddress=address.getText().toString();
                 Tphone=phone.getText().toString();
                 Tzip=zip.getText().toString();
                 Temail=email.getText().toString();
                if(Tname.isEmpty()) error.setText("Name cannot be empty");
                else if(Temail.isEmpty())error.setText("Email cannot be empty");
                else if(!isEmailValid(Temail)) error.setText("Email is not valid");
                else if(!isPinValid(Tzip) || Tzip.isEmpty()) error.setText("Pin code is not valid");
                else if(!isMobileValid(Tphone) || Tphone.isEmpty()) error.setText("Phone number is not valid");
                else
                    new StoreData().execute();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(back);
                finish();
            }
        });
    }
    class StoreData extends AsyncTask<Void,Void,Void>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }


        @Override
        protected Void doInBackground(Void... params) {
            List<NameValuePair> param = new ArrayList<NameValuePair>();
            param.add(new BasicNameValuePair("Name",Tname));
            param.add(new BasicNameValuePair("Address",Taddress));
            param.add(new BasicNameValuePair("Phone_number",Tphone));
            param.add(new BasicNameValuePair("Pin",Tzip));
            param.add(new BasicNameValuePair("Email",Temail));
            JSONObject jObject=jparser.makeHttpRequest(URL,"POST",param);
            try {
                desc = jObject.getString("description");
            }

            catch (JSONException e) {
                e.printStackTrace();
                desc="connection unsuccessful";
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            AlertDialog alertDialog = new AlertDialog.Builder(Insert.this).create();
            alertDialog.setTitle("Alert");
            alertDialog.setMessage(desc);
            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            alertDialog.show();
            super.onPostExecute(aVoid);
        }

    }
    private boolean isMobileValid(String mobile) {
        if(mobile.length()!=10)
            return false;
        for(int i = 1; i<mobile.length(); i++){
            if (mobile.charAt(i)<'0' || mobile.charAt(i)>'9')
                return false;
        }
        return true;
    }
    private boolean isPinValid(String mobile) {
        if(mobile.length()!=6)
            return false;
        for(int i = 1; i<mobile.length(); i++){
            if (mobile.charAt(i)<'0' || mobile.charAt(i)>'9')
                return false;
        }
        return true;
    }

    private boolean isEmailValid(String email) {
        return email.contains("@") && email.contains(".com");
    }
}
