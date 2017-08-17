package com.example.parth.munimji;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by parth on 18/4/16.
 */
public class Settings extends AppCompatActivity {
    String sharedfile="iniSettings";
    String Sowner="owner";
    String Stenant="tenant";
    EditText et_own,et_tenant;
    Button bt_rent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        et_own=(EditText)findViewById(R.id.et_rento_settings);
        et_tenant=(EditText)findViewById(R.id.et_rentt_settings);
      //  final Globals g = (Globals)getApplication();
       final  SharedPreferences pref = getApplicationContext().getSharedPreferences(sharedfile, MODE_PRIVATE);
        et_own.setHint(String.valueOf(pref.getInt(Sowner,800)));
        et_tenant.setHint(String.valueOf(pref.getInt(Stenant,1000)));

        bt_rent=(Button)findViewById(R.id.bt_rent_settings);
        bt_rent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String temp = et_own.getText().toString();
                    int own = Integer.parseInt(temp);
                    int tenant = Integer.parseInt(et_tenant.getText().toString());
                    pref.edit().putInt(Stenant,tenant).apply();
                    pref.edit().putInt(Sowner,own).apply();
                    Toast.makeText(getApplicationContext(), "settings", Toast.LENGTH_SHORT).show();
                }catch (Exception e)
                {Toast.makeText(getApplicationContext(),"one or more field empty",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
