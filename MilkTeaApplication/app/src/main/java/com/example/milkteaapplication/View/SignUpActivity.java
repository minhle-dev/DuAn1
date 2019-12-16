package com.example.milkteaapplication.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.milkteaapplication.Common.Common;
import com.example.milkteaapplication.Model.NonUI;
import com.example.milkteaapplication.Model.User;
import com.example.milkteaapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class SignUpActivity extends AppCompatActivity {

    MaterialEditText edtPhone, edtName, edtPassword, edtRePass;
    Spinner spRole;
    Button btnSignUp;
    String key;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        anhxa();

        //anh xa firebase
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference(Common.USER_REFERENCES);

        final List<String> listRole = new ArrayList<>();

        listRole.add("Phuc Vu");
        listRole.add("Thu Ngan");
        listRole.add("Pha Che");
        ArrayAdapter<String> adapter = new ArrayAdapter(this, R.layout.spinner_item_role, listRole);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spRole.setAdapter(adapter);
        //
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                key = table_user.push().getKey();
                // dialog xin cho
                final ProgressDialog mDialog = new ProgressDialog(SignUpActivity.this);
                mDialog.setMessage("Xin chờ...");
                mDialog.show();

                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        //check trung user
                        if (dataSnapshot.child(edtPhone.getText().toString()).exists()) {
                            mDialog.dismiss();
                            Toasty.info(SignUpActivity.this, "Số điện thoại đã được sử dụng!!", Toast.LENGTH_SHORT, true).show();
                            return;
                        } else {
                            mDialog.dismiss();
                            if (TextUtils.isEmpty(edtPhone.getText().toString())) {
                                Toasty.info(SignUpActivity.this, "Nhập vào số điện thoại!!", Toast.LENGTH_SHORT, true).show();
                                return;
                            } else if (edtPhone.getText().toString().length() < 10) {
                                Toasty.info(SignUpActivity.this, "Số điện thoại phải lớn hơn 9 số!!", Toast.LENGTH_SHORT, true).show();
                                return;
                            } else if (TextUtils.isEmpty(edtName.getText().toString())) {
                                Toasty.info(SignUpActivity.this, "Nhập vào họ tên!!", Toast.LENGTH_SHORT, true).show();
                                return;
                            } else if (TextUtils.isEmpty(edtPassword.getText().toString())) {
                                Toasty.info(SignUpActivity.this, "Nhập vào mật khẩu!!", Toast.LENGTH_SHORT, true).show();
                                return;
                            } else if (edtPassword.getText().toString().length() < 6) {
                                Toasty.info(SignUpActivity.this, "Mật khẩu phải lớn hơn 6 ký tự!!", Toast.LENGTH_SHORT, true).show();
                                return;
                            } else if (TextUtils.isEmpty(edtRePass.getText().toString())) {
                                Toasty.info(SignUpActivity.this, "Nhập lại mật khẩu!!", Toast.LENGTH_SHORT, true).show();
                                return;
                            } else if (edtRePass.getText().toString().length() < 6) {
                                Toasty.info(SignUpActivity.this, "Mật khẩu phải lớn hơn 6 ký tự!!", Toast.LENGTH_SHORT, true).show();
                                return;
                            }
                            if (edtRePass.getText().toString().equals(edtPassword.getText().toString())) {
                            } else {
                                Toasty.info(SignUpActivity.this, "Mật khẩu không trùng khớp!!", Toast.LENGTH_SHORT, true).show();
                                return;
                            }

                            String role = String.valueOf(spRole.getSelectedItemPosition());

                            User user = new User(edtPhone.getText().toString(), edtName.getText().toString(), edtPassword.getText().toString(), role);
                            table_user.child(edtPhone.getText().toString()).setValue(user);
                            Toasty.success(SignUpActivity.this, "Đăng ký thành công!!", Toast.LENGTH_SHORT, true).show();
                        }
                        finish();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }

    private void anhxa() {
        edtName = findViewById(R.id.edtName);
        edtPhone = findViewById(R.id.edtPhone);
        edtPassword = findViewById(R.id.edtPassword);
        edtRePass = findViewById(R.id.edtRePassword);
        spRole = findViewById(R.id.sp_role);
        btnSignUp = findViewById(R.id.btnSignUp);
    }
}
