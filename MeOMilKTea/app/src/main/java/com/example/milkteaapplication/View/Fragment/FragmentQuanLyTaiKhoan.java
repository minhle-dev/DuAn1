package com.example.milkteaapplication.View.Fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.milkteaapplication.Adapter.UsersAdapter;
import com.example.milkteaapplication.Common.Common;
import com.example.milkteaapplication.DAO.UserDAO;
import com.example.milkteaapplication.Model.User;
import com.example.milkteaapplication.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;


public class FragmentQuanLyTaiKhoan extends Fragment {

    private FragmentActivity myContext;
    public UsersAdapter usersAdapter;
    UserDAO userDAO;
    ArrayList<User> users;
    FloatingActionButton fabAcc;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView recyclerView_acc;
    Spinner spRole;
    MaterialEditText edtPhone, edtName, edtPassword;

    public FragmentQuanLyTaiKhoan() {

    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quan_ly_tai_khoan, container, false);
        recyclerView_acc = view.findViewById(R.id.recycler_acc);
        recyclerView_acc.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView_acc.setLayoutManager(layoutManager);
        fabAcc = view.findViewById(R.id.fabAcc);
        //Chuan bi data

        userDAO = new UserDAO(getActivity(), this);


        users = (ArrayList<User>) userDAO.getAllUser();

        // gan adapter

        usersAdapter = new UsersAdapter(getActivity(), users, this);

        // dua len listView

        recyclerView_acc.setAdapter(usersAdapter);
        usersAdapter.notifyDataSetChanged();


        fabAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ThemTaiKhoan();
            }
        });


        return view;
    }

    private void ThemTaiKhoan() {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(getContext());
        builder.setTitle("Thêm tài khoản");
        builder.setMessage("Vui lòng nhập đủ thông tin!");

        final ProgressDialog mDialog = new ProgressDialog(getContext());
        mDialog.setMessage("Xin chờ...");
        mDialog.show();

        View itemView = LayoutInflater.from(getContext()).inflate(R.layout.item_dialog_them_acc, null);
        edtPhone = itemView.findViewById(R.id.edtPhone);
        edtName = itemView.findViewById(R.id.edtName);
        edtPassword = itemView.findViewById(R.id.edtPassword);
        spRole = itemView.findViewById(R.id.sp_role);
        final List<String> listRole = new ArrayList<>();

        listRole.add("Phuc Vu");
        listRole.add("Thu Ngan");
        listRole.add("Pha Che");
        ArrayAdapter<String> adapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, listRole);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spRole.setAdapter(adapter);

        //set
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference(Common.USER_REFERENCES);
        builder.setView(itemView);
        builder.setNegativeButton("Thoát", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                mDialog.dismiss();
            }
        });
        builder.setPositiveButton("Thêm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        //check trung user
                        if (dataSnapshot.child(edtPhone.getText().toString()).exists()) {
                            mDialog.dismiss();
                            Toast.makeText(getContext(), "Số điện thoại đã được sử dụng !", Toast.LENGTH_SHORT).show();
                        } else if (TextUtils.isEmpty(edtPhone.getText().toString())) {
                            Toast.makeText(getContext(), "Số điện thoại không được để trống !", Toast.LENGTH_SHORT).show();
                            return;
                        } else if (edtPhone.getText().toString().length() < 9) {
                            Toast.makeText(getContext(), "Số điện thoại ngắn!", Toast.LENGTH_SHORT).show();
                            return;
                        } else if (TextUtils.isEmpty(edtPhone.getText().toString())) {
                            Toast.makeText(getContext(), "Số điện thoại không được để trống !", Toast.LENGTH_SHORT).show();
                            return;
                        } else if (TextUtils.isEmpty(edtName.getText().toString())) {
                            Toast.makeText(getContext(), "Không được để trống tên!", Toast.LENGTH_SHORT).show();
                            return;
                        } else if (TextUtils.isEmpty(edtPassword.getText().toString())) {
                            Toast.makeText(getContext(), "Password không được để trống !", Toast.LENGTH_SHORT).show();
                            return;
                        } else if (edtPhone.getText().toString().length() < 5) {
                            Toast.makeText(getContext(), "Số điện thoại quá ngắn !", Toast.LENGTH_SHORT).show();
                            return;
                        } else {
                            mDialog.dismiss();

                            String role = String.valueOf(spRole.getSelectedItemPosition());

                            User user = new User(edtPhone.getText().toString(), edtName.getText().toString(), edtPassword.getText().toString(), role);
                            table_user.child(edtPhone.getText().toString()).setValue(user);
                            Toast.makeText(getContext(), "Đăng ký thành công !", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });
        builder.setView(itemView);
        androidx.appcompat.app.AlertDialog dialog = builder.create();
        dialog.show();
    }

    ////sua///////


    private void SuaTaiKhoan(int position) {
        final User us = users.get(position);
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(getContext());
        builder.setTitle("Sửa thông tin");
        builder.setMessage("Vui lòng nhập đủ thông tin!");

        final ProgressDialog mDialog = new ProgressDialog(getContext());
        mDialog.setMessage("Xin chờ...");
        mDialog.show();

        View itemView = LayoutInflater.from(getContext()).inflate(R.layout.item_dialog_sua_acc, null);
        edtPhone = itemView.findViewById(R.id.edtPhone);
        edtName = itemView.findViewById(R.id.edtName);
        edtPassword = itemView.findViewById(R.id.edtPassword);
        spRole = itemView.findViewById(R.id.sp_role);
        final List<String> listRole = new ArrayList<>();

        listRole.add("Phuc Vu");
        listRole.add("Thu Ngan");
        listRole.add("Pha Che");
        ArrayAdapter<String> adapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, listRole);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spRole.setAdapter(adapter);

        //dua data len EditText
        edtPhone.setText(us.getPhone());
        edtPassword.setText(us.getPassword());
        edtName.setText(us.getFullname());
        //set
        builder.setView(itemView);
        builder.setNegativeButton("Thoát", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mDialog.dismiss();
                dialogInterface.dismiss();
            }
        });
        builder.setPositiveButton("Sửa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mDialog.dismiss();
                String role = String.valueOf(spRole.getSelectedItemPosition());
                if (TextUtils.isEmpty(edtPhone.getText().toString())) {
                    Toast.makeText(getContext(), "Số điện thoại không được để trống !", Toast.LENGTH_SHORT).show();
                    return;
                } else if (edtPhone.getText().toString().length() < 9) {
                    Toast.makeText(getContext(), "Số điện thoại ngắn!", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(edtPhone.getText().toString())) {
                    Toast.makeText(getContext(), "Số điện thoại không được để trống !", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(edtName.getText().toString())) {
                    Toast.makeText(getContext(), "Không được để trống tên!", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(edtPassword.getText().toString())) {
                    Toast.makeText(getContext(), "Password không được để trống !", Toast.LENGTH_SHORT).show();
                } else if (edtPhone.getText().toString().length() < 5) {
                    Toast.makeText(getContext(), "Số điện thoại quá ngắn !", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    User user = new User(edtPhone.getText().toString(), edtName.getText().toString(), edtPassword.getText().toString(), role);
                    userDAO.updateUser(user);
                }

            }

        });
        builder.setView(itemView);
        androidx.appcompat.app.AlertDialog dialog = builder.create();
        dialog.show();
    }

    ////////////////////click card v
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int position = -1;

        try {
            position = ((UsersAdapter) recyclerView_acc.getAdapter()).getPosition();
        } catch (Exception e) {
            Log.d(TAG, e.getLocalizedMessage(), e);
            return super.onContextItemSelected(item);
        }
        switch (item.getItemId()) {

            case R.id.context_edit_acc:
                SuaTaiKhoan(position);
                break;
        }
        return super.onContextItemSelected(item);
    }


    @Override
    public void onAttach(Activity activity) {
        myContext = (FragmentActivity) activity;
        super.onAttach(activity);
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    public void capnhatLV() {

        usersAdapter.notifyDataSetChanged();

    }

}
