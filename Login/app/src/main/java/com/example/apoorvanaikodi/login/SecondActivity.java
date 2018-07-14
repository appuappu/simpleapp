package com.example.apoorvanaikodi.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    private EditText Name;
    private EditText Password;
    private TextView Info;
    private Button Login;
    private int counter=5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Name = (EditText) findViewById(R.id.etname);
        Password = (EditText) findViewById(R.id.etPassword);
        Info = (TextView) findViewById(R.id.btInfo);
        Login = (Button) findViewById(R.id.btnLogin);

        Info.setText("no of Attempts remaining: 5 ");
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate(Name.getText().toString(),Password.getText().toString());
            }
        });

    }

    private  void validate(String userName,String userPassword){

        if((userName.equals("Admin"))&& (userPassword.equals("1234")))
        {
            Intent intent=new Intent(SecondActivity.this, ThirdActivity.class);
            startActivity(intent);
        }
        else{
            counter--;

            Info.setText("No of Attempts remaining: "+String.valueOf(counter));
            if(counter==0)
            {
                Login.setEnabled(false);
            }
        }
    }
}
