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
import android.util.Log;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryUpdateFragment extends Fragment {

    private Button btnUpdate, btnBack;
    private EditText edtName, edtDescription;
    private CategoryHelper categoryHelper;
    private FragmentManager manager;
    private FragmentTransaction transaction;
    private ImageButton btnImageurl;
    private String imageurl = "";


    public CategoryUpdateFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_category_update, container, false);

        //HANDLE FRAGMENT MANAGER AND TRANSACTION
        manager = getFragmentManager();
        transaction = manager.beginTransaction();

        //CONFIG DATABASE PROVIDER
        categoryHelper = new CategoryHelper(getContext());

        //GET ARGRUMENT PASS BY DETAIL FRAGMENT
        final int idCategory = getArguments().getInt("id");
        final String name = getArguments().getString("name");
        final String description = getArguments().getString("description");
        final String imageValue = getArguments().getString("imageurl");


        // HANDLE EDIT TEXT NAME
        edtName = view.findViewById(R.id.edtName);
        edtName.setText(name);


        //HANDLE EDITTEXT DESCRIPTION
        edtDescription = view.findViewById(R.id.edtDescription);
        edtDescription.setText(description != null ? description : "");

        btnImageurl = view.findViewById(R.id.imageurl);

        btnImageurl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });

        try {
            Bitmap thumbnail = (BitmapFactory.decodeFile(imageValue));
            thumbnail = getResizedBitmap(thumbnail, 400);
            btnImageurl.setImageBitmap(thumbnail);
        } catch (Exception e) {
            Log.d("myapp", "getView: " + e);
        }

        // HANDLE BTN UPDATE.
        btnUpdate = view.findViewById(R.id.btnUpdateCategory);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = idCategory;
                String name = edtName.getText().toString();
                String description = edtDescription.getText().toString();
                categoryHelper.Update(new Category(idCategory, name, description, imageurl.equals("") ? imageValue : imageurl));
                CategoryFragment categoryFragment = new CategoryFragment();
                transaction.replace(R.id.nav_host_fragment, categoryFragment);
                transaction.addToBackStack(null);
                transaction.commit();
                Toast.makeText(getContext(), "Update Success", Toast.LENGTH_SHORT).show();
            }
        });


        //HANDLE BTN BACK
        btnBack = view.findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CategoryDetailFragment categoryDetailFragment = new CategoryDetailFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("id", idCategory);
                bundle.putString("name", name);
                bundle.putString("description", description);
                bundle.putString("imageurl", imageValue);
                categoryDetailFragment.setArguments(bundle);
                transaction.replace(R.id.nav_host_fragment, categoryDetailFragment);

                transaction.addToBackStack(null);
                transaction.commit();
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
