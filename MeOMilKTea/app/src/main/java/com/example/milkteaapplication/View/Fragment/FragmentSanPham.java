package com.example.milkteaapplication.View.Fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.milkteaapplication.Adapter.SanPhamAdapter;
import com.example.milkteaapplication.Adapter.SpinnerDanhMucAdapter;
import com.example.milkteaapplication.DAO.AddSanPhamListener;
import com.example.milkteaapplication.DAO.DanhMucDao;
import com.example.milkteaapplication.DAO.SanPhamDAO;
import com.example.milkteaapplication.Model.DanhMucSanPham;
import com.example.milkteaapplication.Model.SanPham;
import com.example.milkteaapplication.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.app.Activity.RESULT_OK;


public class FragmentSanPham extends Fragment implements AddSanPhamListener {
    SanPhamAdapter sanPhamAdapter;
    RecyclerView recyclerView_SanPham;

    DanhMucDao danhMucDao;
    SanPhamDAO sanPhamDAO;

    List<DanhMucSanPham> mDanhMuc;
    List<SanPham> mSanPham;

    FloatingActionButton fabSanPham;

    Spinner spDanhMuc, spListDanhMuc;
    SpinnerDanhMucAdapter spinnerDanhMucAdapter;

    EditText edtTenSanPham, edtGiaTienSanPham;
    String idDanhMuc;

    //Upload image
    StorageReference storageReference;
    public final int IMAGE_REQUEST = 1;
    private Uri imageUri;
    private StorageTask uploadTask;
    DatabaseReference reference;


    public FragmentSanPham() {

    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable final ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_san_pham, container, false);

        init(view);

        mDanhMuc = new ArrayList<>();
        mSanPham = new ArrayList<>();

        danhMucDao = new DanhMucDao(getContext());
        sanPhamDAO = new SanPhamDAO(getContext(), this, this);

        storageReference = FirebaseStorage.getInstance().getReference("Uploads");


        //Gridlayout with 3 cols
        recyclerView_SanPham.setLayoutManager(new GridLayoutManager(getContext(), 3));
        sanPhamAdapter = new SanPhamAdapter(getContext());
        recyclerView_SanPham.setAdapter(sanPhamAdapter);

        mDanhMuc = danhMucDao.readAllCategoryToSpinner(spListDanhMuc);
        spinnerDanhMucAdapter = new SpinnerDanhMucAdapter(getContext(), mDanhMuc);
        spListDanhMuc.setAdapter(spinnerDanhMucAdapter);

        spListDanhMuc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mSanPham.clear();
                int mPosition = spListDanhMuc.getSelectedItemPosition();
                DanhMucSanPham danhMucSanPham = mDanhMuc.get(mPosition);
                idDanhMuc = danhMucSanPham.getMaDanhMuc();
                sanPhamDAO.readAllSanPhamOrderByDanhMuc(idDanhMuc);
                sanPhamAdapter.setListSanPham(mSanPham);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        fabSanPham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ThemSanPham();
            }
        });
        return view;
    }


    private void init(View view) {
        recyclerView_SanPham = view.findViewById(R.id.recycler_san_pham);
        spListDanhMuc = view.findViewById(R.id.spDanhMucSanPham);
        fabSanPham = view.findViewById(R.id.fabSanPham);
        recyclerView_SanPham.setHasFixedSize(true);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int position;

        try {
            position = ((SanPhamAdapter) recyclerView_SanPham.getAdapter()).getPosition();
        } catch (Exception e) {
            return super.onContextItemSelected(item);
        }
        switch (item.getItemId()) {
            case R.id.add_pic:
                openImage(position);
                break;
            case R.id.edit_sp:
                SuaSanPham(position);
                break;
            case R.id.del_sp:
                DeleteSp(mSanPham.get(sanPhamAdapter.getPosition()));
                break;

        }
        return super.onContextItemSelected(item);
    }

    public void capNhatSanPham() {

        int mPosition = spListDanhMuc.getSelectedItemPosition();

        DanhMucSanPham danhMucSanPham = mDanhMuc.get(mPosition);

        idDanhMuc = danhMucSanPham.getMaDanhMuc();
        mSanPham.clear();
        sanPhamDAO = new SanPhamDAO(getContext(), this, this);

    }

    private void ThemSanPham() {
        AlertDialog.Builder builder =
                new AlertDialog.Builder(getLayoutInflater().getContext());
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.item_dialog_them_san_pham, null);
        edtTenSanPham = v.findViewById(R.id.edtTenSanPham);
        spDanhMuc = v.findViewById(R.id.sp_danhmuc);
        edtGiaTienSanPham = v.findViewById(R.id.edt_giaSanPham);
        builder.setTitle("Thêm sản phẩm");
        builder.setMessage("Vui lòng nhập đủ thông tin!");
        builder.setView(v);

        mDanhMuc = danhMucDao.readAllCategoryToSpinner(spDanhMuc);


        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int pos = spDanhMuc.getSelectedItemPosition();
                String txtCategory = mDanhMuc.get(pos).getMaDanhMuc();
                String txtCatTitle = mDanhMuc.get(pos).getTenDanhMuc();
                String txtTenSP = edtTenSanPham.getText().toString().trim();
                String txtGiaSP = edtGiaTienSanPham.getText().toString().trim();


                if (TextUtils.isEmpty(txtTenSP) || TextUtils.isEmpty(txtGiaSP)) {
                    Toast.makeText(getContext(), "Nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    SanPham sanPham = new SanPham();
                    sanPham.setTenSanPham(txtTenSP);
                    sanPham.setMaDanhMuc(txtCategory);
                    sanPham.setTenDanhMuc(txtCatTitle);
                    sanPham.setGiaTienSanPham(Long.parseLong(txtGiaSP));

                    sanPhamDAO.insertSanPham(sanPham);
                    capNhatSanPham();
                    sanPhamAdapter.notifyDataSetChanged();
                }
            }
        });

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();


    }

    //sua sp//
    private void SuaSanPham(int position) {
        AlertDialog.Builder builder =
                new AlertDialog.Builder(getLayoutInflater().getContext());
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.item_dialog_them_san_pham, null);
        edtTenSanPham = v.findViewById(R.id.edtTenSanPham);
        spDanhMuc = v.findViewById(R.id.sp_danhmuc);
        edtGiaTienSanPham = v.findViewById(R.id.edt_giaSanPham);
        builder.setView(v);

        mDanhMuc = danhMucDao.readAllCategoryToSpinner(spDanhMuc);
        final SanPham sanPham = mSanPham.get(position);
        edtTenSanPham.setText(sanPham.getTenSanPham());
        edtGiaTienSanPham.setText("" + sanPham.getGiaTienSanPham());


        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int pos = spDanhMuc.getSelectedItemPosition();
                String txtCategory = mDanhMuc.get(pos).getMaDanhMuc();
                String txtCatTitle = mDanhMuc.get(pos).getTenDanhMuc();
                String txtTenSP = edtTenSanPham.getText().toString().trim();
                String txtGiaSP = edtGiaTienSanPham.getText().toString().trim();
                String txtMaSp = sanPham.getMaSanPham();


                if (TextUtils.isEmpty(txtTenSP) || TextUtils.isEmpty(txtGiaSP)) {
                    Toast.makeText(getContext(), "Không được để trống", Toast.LENGTH_SHORT).show();
                } else {
                    SanPham sanPham = new SanPham();
                    sanPham.setTenSanPham(txtTenSP);
                    sanPham.setMaDanhMuc(txtCategory);
                    sanPham.setTenDanhMuc(txtCatTitle);
                    sanPham.setGiaTienSanPham(Long.parseLong(txtGiaSP));
                    sanPham.setMaSanPham(txtMaSp);


                    sanPhamDAO.updateSanPham(sanPham);
                    capNhatSanPham();
                    sanPhamAdapter.notifyDataSetChanged();
                }
            }
        });

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();


    }

    private void DeleteSp(final SanPham sanPham) {
        // khởi tạo AlertDialog từ đối tượng Builder. tham số truyền vào ở đây là context.
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(getContext());
        // set Message là phương thức thiết lập câu thông báo
        builder.setMessage("Bạn muốn xóa sản phẩm này ?")
                // positiveButton là nút thuận : đặt là OK
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        sanPhamDAO.delete(sanPham);
                        capNhatSanPham();
                        sanPhamAdapter.notifyDataSetChanged();
                    }
                })
                // ngược lại negative là nút nghịch : đặt là cancel
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        // tạo dialog và hiển thị
        builder.create().show();
    }


    //Choose Image and Upload Image to Firebase
    private void openImage(int position) {
        Intent intent = new Intent();
        intent.setType("image/*");

        //Put position to upload image function
        intent.putExtra("Position", position);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        //Important: setIntent to getIntent on ActivityResult
        getActivity().setIntent(intent);
        startActivityForResult(intent, IMAGE_REQUEST);
    }

    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getContext().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void uploadImage(final int position) {
        final ProgressDialog pd = new ProgressDialog(getContext());
        pd.setMessage("Uploading...");
        pd.show();

        if (imageUri != null) {
            final StorageReference fileReference = storageReference.child(System.currentTimeMillis()
                    + "." + getFileExtension(imageUri));

            uploadTask = fileReference.putFile(imageUri);
            uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }

                    return fileReference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        String mUri = downloadUri.toString();
                        reference = FirebaseDatabase.getInstance().getReference("SanPham").child(mSanPham.get(position).getMaSanPham());
                        HashMap<String, Object> map = new HashMap<>();
                        map.put("thumbUrl", mUri);
                        reference.updateChildren(map);
                        Toast.makeText(getContext(), "Thêm ảnh thành công!", Toast.LENGTH_SHORT).show();
                        capNhatSanPham();
                        sanPhamAdapter.notifyDataSetChanged();
                        pd.dismiss();

                    } else {
                        Toast.makeText(getContext(), "Failed!", Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            });
        } else {
            Toast.makeText(getContext(), "Chưa chọn ảnh", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {

            imageUri = data.getData();

            if (uploadTask != null && uploadTask.isInProgress()) {
                Toast.makeText(getContext(), "Đang upload...", Toast.LENGTH_SHORT).show();
            } else {
                //Get intent with getActivity().getIntent()
                int pos = getActivity().getIntent().getIntExtra("Position", 0);
                uploadImage(pos);

            }

        }
    }


    public void updateRecyclerView() {
        sanPhamAdapter.notifyDataSetChanged();
    }

    @Override
    public void getSanPham(SanPham sanPham) {
        if(mSanPham != null){

            mSanPham.add(sanPham);
        }
        if(sanPhamAdapter != null){
            sanPhamAdapter.setListSanPham(mSanPham);
        }
    }
}