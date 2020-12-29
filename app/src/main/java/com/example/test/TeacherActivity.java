package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class TeacherActivity extends AppCompatActivity {

    private TextView nameTeacher;
    private TextView phone;
    private TextView mail;
    private TextView exp;
    private TextView quf;
    private TextView subjects;

    private String[] teacher;
    private Button phoneButton;
    private Button mailButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ImageButton btn = (ImageButton) findViewById(R.id.back);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        Intent intent = getIntent();
        teacher = intent.getStringArrayExtra("item");
        nameTeacher = findViewById(R.id.nameTeacher);
        phone = findViewById(R.id.phone);
        mail = findViewById(R.id.mail);
        exp = findViewById(R.id.exp);
        quf = findViewById(R.id.quf);
        subjects = findViewById(R.id.subjects);

        nameTeacher.setText(teacher[0]);
        phone.setText(teacher[1]);
        mail.setText(teacher[2]);
        exp.setText(teacher[3]);
        quf.setText(teacher[4]);
        subjects.setText(teacher[5]);

       phoneButton = (Button) findViewById(R.id.call);
       mailButton = (Button) findViewById(R.id.write);

       phoneButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if(teacher[1].charAt(0) != '*')
               {
                   String tel = teacher[1].substring(1, teacher[1].length());
                   Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", tel, null));
                   startActivity(intent);
               }
               else
               {
                   Toast.makeText(TeacherActivity.this, "Номер не найден", Toast.LENGTH_LONG).show();
               }
           }
       });

        mailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(teacher[2].charAt(0) != '*')
                {
                    Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                            "mailto",teacher[2], null));
                    startActivity(Intent.createChooser(emailIntent, "Send email"));
                }
                else
                {
                    Toast.makeText(TeacherActivity.this, "Адрес не найден", Toast.LENGTH_LONG).show();
                }
            }
        });



    }
}