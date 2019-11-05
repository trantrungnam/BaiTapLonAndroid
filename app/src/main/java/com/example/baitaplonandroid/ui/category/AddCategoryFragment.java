package com.example.baitaplonandroid.ui.category;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.baitaplonandroid.R;
import com.example.baitaplonandroid.ui.Models.Category;
import com.example.baitaplonandroid.ui.SQLHelper.CategoryHelper;

import java.io.ByteArrayOutputStream;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddCategoryFragment extends Fragment {

    private Button btnAddCategory;
    private EditText edtName;
    private EditText edtDescription;
    private CategoryHelper categoryHelper;
    private FragmentManager manager;
    private FragmentTransaction transaction;
    private ImageButton btnImageurl;
    private String imageurl = "";
    public AddCategoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_category, container, false);
        String message = getArguments().getString("message");
        //Setting global variable
        categoryHelper = new CategoryHelper(getContext());
        manager = getFragmentManager();
        transaction = manager.beginTransaction();


        btnImageurl = view.findViewById(R.id.imageurl);
        btnImageurl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();

            }
        });

        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        edtName = view.findViewById(R.id.name);
        edtDescription = view.findViewById(R.id.description);

        btnAddCategory = view.findViewById(R.id.btnAddCategory);
        btnAddCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = edtName.getText().toString();
                String description = edtDescription.getText().toString();

                Toast.makeText(getContext(), description, Toast.LENGTH_SHORT).show();
                if (name != "" && description != "") {
                    CategoryFragment categoryFragment = new CategoryFragment();
                    categoryHelper.addCategory(new Category(name, description, imageurl));
                    transaction.replace(R.id.nav_host_fragment, categoryFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
                else {
                    Toast.makeText(getContext(), "Giá trị không được rỗng.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    private void selectImage() {

        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 2);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {

            Uri selectedImage = data.getData();
            String[] filePath = {MediaStore.Images.Media.DATA};
            Toast.makeText(getContext(), filePath + "", Toast.LENGTH_SHORT).show();
            Cursor c = getActivity().getContentResolver().query(selectedImage, filePath, null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePath[0]);
            String picturePath = c.getString(columnIndex);
            imageurl = picturePath;
//                Log.d("myapp", picturePath + "");
//                edtDescription.setText(picturePath);
            c.close();
            Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
//            Bitmap thumbnail = (BitmapFactory.decodeFile("/storage/emulated/0/Pictures/1572793569605.jpg"));
            thumbnail = getResizedBitmap(thumbnail, 400);
//            Log.w("path of image from gallery......******************.........", picturePath + "");
            btnImageurl.setImageBitmap(thumbnail);

//                MediaStore.Images.Media.insertImage(getActivity().getContentResolver(), thumbnail, "imagefile" , "file");
        }

    }

    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }
}

