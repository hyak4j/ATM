package com.home.atm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private EditText edUserID, edPassWd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edUserID = (EditText)findViewById(R.id.edUSER_ID);
        edPassWd = (EditText)findViewById(R.id.edPassWd);
    }

    public void login(View view){
        String userID = edUserID.getText().toString();
        final String passwd = edPassWd.getText().toString();
        int a=0;

        //取得Firebase資料
        FirebaseDatabase.getInstance()
                .getReference("users")
                .child(userID)
                .child("password")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //成功取得資料
                        String pw = (String) dataSnapshot.getValue();
                        if (pw.equals(passwd)){
                            setResult(RESULT_OK);
                            finish();
                        }else {
                            new AlertDialog.Builder(LoginActivity.this)
                                    .setTitle("登入結果")
                                    .setMessage("登入失敗:請輸入正確帳號或密碼")
                                    .setCancelable(false)
                                    .setPositiveButton("確認", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    })
                                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    }).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(LoginActivity.this, "連線失敗!", Toast.LENGTH_SHORT).show();
                    }
                });
//        if("henry".equals(userID) && "1234".equals(passwd)){
//            //返回MainActivity   執行onActivityResult
//            setResult(RESULT_OK);
//            finish();
//
//        }
    }

    public void close(View view){
        finish();
    }
}
