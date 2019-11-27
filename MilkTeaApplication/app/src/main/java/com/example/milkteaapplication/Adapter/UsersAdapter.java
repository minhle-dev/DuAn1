package com.example.milkteaapplication.Adapter;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.milkteaapplication.DAO.UserDAO;
import com.example.milkteaapplication.View.Fragment.FragmentQuanLyTaiKhoan;
import com.example.milkteaapplication.Model.User;
import com.example.milkteaapplication.R;

import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder> {
    public Context context;
    public List<User> arrUser;
    public LayoutInflater inflater;
    public UserDAO userDAO;
    FragmentQuanLyTaiKhoan fr;
    private int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public UsersAdapter(Context context, List<User> arrUser, FragmentQuanLyTaiKhoan fr) {
        this.context = context;
        this.arrUser = arrUser;
        this.fr = fr;
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    //tao view
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_nguoi_dung, null);
        return new ViewHolder(view);
    }

    //lay du lieu
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserDAO userDAO = new UserDAO(context, fr);
                userDAO.deleteUser(arrUser.get(position));
                arrUser.remove(position);
                notifyDataSetChanged();
            }
        });
        final User user = arrUser.get(position);
        if (user != null) {
            //set len list
            holder.tvFullname.setText("Fullname: " + user.getFullname());
            holder.tvPassword.setText("Password: " + user.getPassword());

            if (user.getRole().equals("0")) {
                holder.tvRole.setText("Phuc Vu");
            } else if (user.getRole().equals("1")) {
                holder.tvRole.setText("Thu Ngan");
            } else if (user.getRole().equals("2")) {
                holder.tvRole.setText("Pha Che");
            } else {
                holder.tvRole.setText("Admin");
            }
            holder.tvPhone.setText("Phone: " + user.getPhone());


        }

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                setPosition(holder.getPosition());
                return false;
            }
        });
    }

    //lay ve so luong ite,
    @Override
    public int getItemCount() {
        return arrUser.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        ImageView ivIcon;
        TextView tvPhone;
        TextView tvFullname;
        ImageView ivDelete;
        TextView tvPassword;
        TextView tvRole;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.ivIcon = itemView.findViewById(R.id.ivIcon);
            this.tvPhone = itemView.findViewById(R.id.tvPhone);
            this.tvFullname = itemView.findViewById(R.id.tvFullname);
            this.tvRole = itemView.findViewById(R.id.tvRole);
            this.ivDelete = itemView.findViewById(R.id.ivDelete);
            this.tvPassword = itemView.findViewById(R.id.tvPassword);
            itemView.setOnCreateContextMenuListener(this);

        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            //menuInfo is null

            menu.add(Menu.NONE, R.id.context_edit_acc,
                    Menu.NONE, "Sửa thông tin");
        }
    }

    @Override
    public long getItemId(int position) {

        return super.getItemId(position);
    }


}
