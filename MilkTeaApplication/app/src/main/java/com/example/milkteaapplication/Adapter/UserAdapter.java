package com.example.milkteaapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.milkteaapplication.View.Fragment.FragmentQuanLyTaiKhoan;
import com.example.milkteaapplication.Model.User;
import com.example.milkteaapplication.R;

import java.util.ArrayList;

public class UserAdapter extends ArrayAdapter {
    TextView tvPhone, tvFullname, tvPassword, tvRole;
    Context context;
    ArrayList<User> users;
    FragmentQuanLyTaiKhoan fr;


    public UserAdapter(@NonNull Context context, ArrayList<User> users, FragmentQuanLyTaiKhoan fr) {
        super(context, 0, users);
        this.context = context;
        this.users = users;
        this.fr = fr;

    }

    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        View v = convertView;

        if (v == null) {
            v = LayoutInflater.from(context).inflate(R.layout.item_list_user, parent, false);
        }

        final User user = users.get(position);
        if (user != null) {
            //anh xa
            tvFullname = (TextView) v.findViewById(R.id.tvFullname);
            tvPassword = (TextView) v.findViewById(R.id.tvPassword);
            tvRole = v.findViewById(R.id.tvRole);
            tvPhone = v.findViewById(R.id.tvPhone);
            //set data len layout custom
            tvFullname.setText(user.getFullname());
            tvPassword.setText(user.getPassword());
            tvRole.setText(user.getRole());
            tvPhone.setText(user.getPhone());


        }
        return v;
    }
}
