package com.example.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.telephony.SmsManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText mPhoneNoEt;
    EditText smsEdit;
    Button mDialButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDialButton = (Button) findViewById(R.id.phone);
        mPhoneNoEt = (EditText) findViewById(R.id.getNumber);
        smsEdit = (EditText) findViewById(R.id.getMessage);

        mDialButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String phoneNo = mPhoneNoEt.getText().toString();
                if(!TextUtils.isEmpty(phoneNo))
                {
                    String dial = "tel:" + phoneNo;
                    startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
                }
                else {
                    Toast.makeText(MainActivity.this, "Введите номер телефона", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void onSendMessage(View view)
    {
        String phoneNo = mPhoneNoEt.getText().toString();
        String toSms="smsto:" + mPhoneNoEt.getText().toString();
        String messageText= smsEdit.getText().toString();
        Intent sms=new Intent(Intent.ACTION_SENDTO, Uri.parse(toSms));

//

        sms.putExtra("sms_body", messageText);
        startActivity(sms);
        SmsManager.getDefault().sendTextMessage(phoneNo, null, messageText.toString(), null, null);
    }
}