package com.example.milkteaapplication.View.Fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
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
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.view.SupportActionModeWrapper;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.milkteaapplication.Adapter.SanPhamAdapter;
import com.example.milkteaapplication.Adapter.SpinnerDanhMucAdapter;
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
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;
import static com.example.milkteaapplication.R.id.recycler_san_pham;


public class FragmentSanPham extends Fragment {
    SanPhamAdapter sanPhamAdapter;
    DanhMucDao danhMucDao;
    SanPhamDAO sanPhamDAO;
    List<DanhMucSanPham> mDanhMuc;
    List<SanPham> mSanPham;
    FloatingActionButton fabSanPham;
    Spinner spDanhMuc, spListDanhMuc;
    SpinnerDanhMucAdapter spinnerDanhMucAdapter;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView recyclerView_SanPham;
    EditText edtTenSanPham, edtGiaTienSanPham;
    String idDanhMuc;

    //Upload image
    StorageReference storageReference;
    public final int IMAGE_REQUEST = 1;
    private Uri imageUri;
    private StorageTask uploadTask;
    DatabaseReference reference;


    private FragmentActivity myContext;

    public FragmentSanPham() {

    }

    public FragmentSanPham(String idDanhMuc) {
        this.idDanhMuc = idDanhMuc;
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_san_pham, container, false);

        init(view);


        buildAction();

        fabSanPham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ThemSanPham();
            }
        });
        mDanhMuc = danhMucDao.readAllCategoryToSpinner(spListDanhMuc);
        spinnerDanhMucAdapter = new SpinnerDanhMucAdapter(getContext(), mDanhMuc);
        spListDanhMuc.setAdapter(spinnerDanhMucAdapter);
        spinnerDanhMucAdapter.notifyDataSetChanged();

        return view;
    }


    private void init(View view) {

        recyclerView_SanPham = view.findViewById(recycler_san_pham);
        spListDanhMuc = view.findViewById(R.id.spDanhMucSanPham);

        fabSanPham = view.findViewById(R.id.fabSanPham);


        recyclerView_SanPham.setHasFixedSize(true);
        mDanhMuc = new ArrayList<>();
        mSanPham = new ArrayList<>();

        danhMucDao = new DanhMucDao(getContext());
        sanPhamDAO = new SanPhamDAO(getContext(), this);
        mSanPham = sanPhamDAO.readAllSanPhamOrderByDanhMuc(idDanhMuc);
        storageReference = FirebaseStorage.getInstance().getReference("Uploads");
        //Gridlayout with 3 cols
        recyclerView_SanPham.setLayoutManager(new GridLayoutManager(getContext(), 3));

    }

    private void ThemSanPham() {
        AlertDialog.Builder builder =
                new AlertDialog.Builder(getLayoutInflater().getContext());
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.item_dialog_them_san_pham, null);
        edtTenSanPham = v.findViewById(R.id.edtTenSanPham);
        spDanhMuc = v.findViewById(R.id.sp_danhmuc);
        edtGiaTienSanPham = v.findViewById(R.id.edt_giaSanPham);
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
                    Toast.makeText(getContext(), "Không được để trống", Toast.LENGTH_SHORT).show();
                } else {
                    SanPham sanPham = new SanPham();
                    sanPham.setTenSanPham(txtTenSP);
                    sanPham.setMaDanhMuc(txtCategory);
                    sanPham.setTenDanhMuc(txtCatTitle);
                    sanPham.setGiaTienSanPham(Long.parseLong(txtGiaSP));

                    sanPhamDAO.insertSanPham(sanPham);
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

    private void SuaSanPham(int position) {
        AlertDialog.Builder builder =
                new AlertDialog.Builder(getLayoutInflater().getContext());
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.item_dialog_sua_san_pham, null);
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
                String txtMaSP = sanPham.getMaSanPham();

                if (TextUtils.isEmpty(txtTenSP) || TextUtils.isEmpty(txtGiaSP)) {
                    Toast.makeText(getContext(), "Không được để trống", Toast.LENGTH_SHORT).show();
                } else {
                    SanPham sanPham = new SanPham();
                    sanPham.setTenSanPham(txtTenSP);
                    sanPham.setMaDanhMuc(txtCategory);
                    sanPham.setTenDanhMuc(txtCatTitle);
                    sanPham.setMaSanPham(txtMaSP);
                    sanPham.setGiaTienSanPham(Long.parseLong(txtGiaSP));

                    sanPhamDAO.updateSanPham(sanPham);
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

    private void buildAction() {
        sanPhamAdapter = new SanPhamAdapter(getContext(), mSanPham);
        recyclerView_SanPham.setAdapter(sanPhamAdapter);

        sanPhamAdapter.setItemClickListener(new SanPhamAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                //cick do đây
            }

            @Override
            public void onOptionItemClick(final int position) {
                PopupMenu popupMenu = new PopupMenu(getContext(), recyclerView_SanPham.getChildAt(position));
                popupMenu.inflate(R.menu.menu_sanpham_item);

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.add_thumbnail:
                                openImage(position);
                                break;
                            case R.id.edit_sanpham:
                                SuaSanPham(position);
                                break;
                            case R.id.remove_sanpham:
                                // khởi tạo AlertDialog từ đối tượng Builder. tham số truyền vào ở đây là context.
                                androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(getContext());
                                // set Message là phương thức thiết lập câu thông báo
                                builder.setMessage("Bạn muốn xóa sản phẩm này ?")
                                        // positiveButton là nút thuận : đặt là OK
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                sanPhamDAO.delete(mSanPham.get(position));
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


                                break;
                            default:
                                break;
                        }


                        return false;
                    }
                });
                popupMenu.show();
            }
        });
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


    @Override
    public void onAttach(Activity activity) {
        myContext = (FragmentActivity) activity;
        super.onAttach(activity);
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    public void updateRecyclerView() {

        sanPhamAdapter.notifyItemInserted(mSanPham.size());

        sanPhamAdapter.notifyDataSetChanged();


    }

}
