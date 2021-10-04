package com.example.minipj;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
    Button registerBtn;
    EditText idtext, pwtext, emailtext, phonetext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerBtn=(Button)findViewById(R.id.RegisterButton);
        idtext=(EditText)findViewById(R.id.idText);
        pwtext=(EditText)findViewById(R.id.pwText);
        emailtext=(EditText)findViewById(R.id.emailText);
        phonetext=(EditText)findViewById(R.id.phoneText);

        registerBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                try{
                    String id = idtext.getText().toString();
                    String pw = pwtext.getText().toString();
                    String email = emailtext.getText().toString();
                    String phone = phonetext.getText().toString();

                    DB_Register task = new DB_Register();
                    String result= task.execute(id,pw,email,phone).get();

                    Log.i("결과","결과값:"+result);

                    if(result.equals("succes"))
                    {
                        Toast.makeText(RegisterActivity.this, "회원가입 완료", Toast.LENGTH_SHORT).show();
                        Intent registerIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                        RegisterActivity.this.startActivity(registerIntent);
                    }else if(result.equals("blank")){
                        Toast.makeText(RegisterActivity.this, "공백이 있습니다.", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(RegisterActivity.this, "중복된 아이디 입니다.", Toast.LENGTH_SHORT).show();
                    }


                }catch (Exception e)
                {
                    Toast.makeText(RegisterActivity.this, "중복된 아이디가 있습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
