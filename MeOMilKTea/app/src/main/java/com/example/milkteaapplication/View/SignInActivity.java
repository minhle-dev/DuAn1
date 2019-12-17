package com.example.milkteaapplication.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.milkteaapplication.Common.Common;
import com.example.milkteaapplication.Model.User;
import com.example.milkteaapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

import es.dmoral.toasty.Toasty;

public class SignInActivity extends AppCompatActivity {
    EditText edtPhone;
    EditText edtPassword;
    String key;
    Button btnSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        anhxa();

        //anh xa firebase

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference().child("User");
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // dialog xin cho
                final ProgressDialog mDialog = new ProgressDialog(SignInActivity.this);
                mDialog.setMessage("Xin chờ...");
                mDialog.show();

                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        //kiem tra xem user co tin tai trong database hay k
                        if (dataSnapshot.child(edtPhone.getText().toString()).exists()) {
                            //lay thong tin user
                            mDialog.dismiss();
                            User user = dataSnapshot.child(edtPhone.getText().toString()).getValue(User.class);
                            if (TextUtils.isEmpty(edtPhone.getText().toString())) {
                                Toasty.info(SignInActivity.this,"Nhập vào số điện thoại!!",Toasty.LENGTH_SHORT,true).show();
                                return;
                            } else if (TextUtils.isEmpty(edtPassword.getText().toString())) {
                                Toasty.info(SignInActivity.this,"Nhập vào mật khẩu!!",Toasty.LENGTH_SHORT,true).show();
                                return;
                            } else if (user.getPassword().equals(edtPassword.getText().toString())) {
                                Toasty.success(SignInActivity.this,"Đăng nhập thành công!!",Toasty.LENGTH_SHORT,true).show();
                                Intent homeintent = new Intent(SignInActivity.this, HomeActivity.class);
                                Common.currentUser = user;
                                startActivity(homeintent);
                                finish();
                            } else {
                                Toasty.error(SignInActivity.this,"Mật khẩu không chính xác !!",Toasty.LENGTH_SHORT,true).show();
                            }
                        } else {
                            mDialog.dismiss();
                            Toasty.error(SignInActivity.this,"Người dùng không tồn tại!!",Toasty.LENGTH_SHORT,true).show();
                        }

                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }

    private void anhxa() {
        edtPassword = (MaterialEditText) findViewById(R.id.edtPassword);
        edtPhone = (MaterialEditText) findViewById(R.id.edtPhone);
        btnSignIn = findViewById(R.id.btnSignIn);
    }
}


