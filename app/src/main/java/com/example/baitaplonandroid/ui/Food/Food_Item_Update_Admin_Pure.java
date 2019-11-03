package com.example.baitaplonandroid.ui.Food;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.baitaplonandroid.R;
import com.example.baitaplonandroid.ui.Models.Category;
import com.example.baitaplonandroid.ui.Models.Food;
import com.example.baitaplonandroid.ui.SQLHelper.CategoryHelper;
import com.example.baitaplonandroid.ui.SQLHelper.FoodHelper;
import com.example.baitaplonandroid.ui.category.CategoryCustomAdapter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Food_Item_Update_Admin_Pure extends Fragment {

    private ImageButton imgBtnHinhAnh;
    private EditText edtMonAn, edtDescription, edtGiaCa, edtUnit;
    private Button btnUpdateFood, btnBackToList;
    private FragmentManager manager;
    private FragmentTransaction transaction;
    private ListView listViewCategories, listViewCategoriesSelect;
    private List<Category> categoriesNotSelected, categoriesSelected;
    private FoodHelper foodHelper;
    private String Document_img1 = "";
    private String imageURL = "/storage/emulated/0/Pictures/1572793569605.jpg";


    public Food_Item_Update_Admin_Pure() {
        // Required empty public constructor
        categoriesNotSelected = new ArrayList<Category>();
        categoriesSelected = new ArrayList<Category>();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_food__item__update__admin__pure, container, false);

        final int id = getArguments().getInt("id", -1);
        final String nameValue = getArguments().getString("name", "");
        final String descriptionValue = getArguments().getString("description", "");
        final String unitValue = getArguments().getString("unit", "");
        final String imageValue = getArguments().getString("image", "");
        final String priceValue = getArguments().getString("price", "");
        final int[] categoriesId = getArguments().getIntArray("category");

        //Config FRAGMENT MANAGER AND FRAMENT.
        manager = getFragmentManager();

        // EDITTEXT

        edtMonAn = view.findViewById(R.id.edtMonAn);
        edtMonAn.setText(nameValue);


        edtDescription = view.findViewById(R.id.edtDescription);
        edtDescription.setText(descriptionValue);


        edtGiaCa = view.findViewById(R.id.edtGiaCa);
        edtGiaCa.setText(priceValue);

        edtUnit = view.findViewById(R.id.edtUnit);
        edtUnit.setText(unitValue);


        // IMAGE BUTTON

        imgBtnHinhAnh = view.findViewById(R.id.imgBtnHinhAnh);
        Bitmap thumbnail = (BitmapFactory.decodeFile(imageValue));
        thumbnail = getResizedBitmap(thumbnail, 400);
        imgBtnHinhAnh.setImageBitmap(thumbnail);
        BitMapToString(thumbnail);


        // HANDLE UPLOAD IMAGE

        imgBtnHinhAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();

            }
        });

        ActivityCompat.requestPermissions(getActivity(),
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 200);
        ActivityCompat.requestPermissions(getActivity(),
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 200);

        CategoryHelper categoryHelper = new CategoryHelper(getActivity());

        foodHelper = new FoodHelper(getActivity());
        categoriesNotSelected = categoryHelper.getAllCategory();

        for (int i = 0; i <= categoriesNotSelected.size(); i++) {
            for (int idItem : categoriesId
            ) {
                if (categoriesNotSelected.get(i).getId() == idItem) {
                    categoriesSelected.add(categoriesNotSelected.get(i));
                    categoriesNotSelected.remove(i);
                }
            }
        }

        listViewCategories = view.findViewById(R.id.listViewCategories);
        CategoryCustomAdapter categoryCustomAdapter = new CategoryCustomAdapter(getContext(), categoriesNotSelected);
        listViewCategories.setAdapter(categoryCustomAdapter);

        listViewCategoriesSelect = view.findViewById(R.id.listViewCategoriesSelect);

        listViewCategories.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                categoriesSelected.add(categoriesNotSelected.get(i));
                CategoryCustomAdapter categoryCustomAdapterSelected = new CategoryCustomAdapter(getContext(), categoriesSelected);
                listViewCategoriesSelect.setAdapter(categoryCustomAdapterSelected);

                categoriesNotSelected.remove(i);
                CategoryCustomAdapter categoryCustomAdapterNotSelected = new CategoryCustomAdapter(getContext(), categoriesNotSelected);
                listViewCategories.setAdapter(categoryCustomAdapterNotSelected);
            }
        });

        listViewCategoriesSelect.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                categoriesNotSelected.add(categoriesSelected.get(i));
                CategoryCustomAdapter categoryCustomAdapterNotSelected = new CategoryCustomAdapter(getContext(), categoriesNotSelected);
                listViewCategories.setAdapter(categoryCustomAdapterNotSelected);

                categoriesSelected.remove(i);
                CategoryCustomAdapter categoryCustomAdapterSelected = new CategoryCustomAdapter(getContext(), categoriesSelected);
                listViewCategoriesSelect.setAdapter(categoryCustomAdapterSelected);
            }
        });


//        listViewCategories.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        //BUTTON SAVE, BACK TO LIST
        btnUpdateFood = view.findViewById(R.id.btnUpdateFood);
        btnUpdateFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = edtMonAn.getText().toString();
                String description = edtDescription.getText().toString();

                String unit = edtUnit.getText().toString();
                String uriImage = imageURL;
//                String categories = categoriesSelected;

                List<Integer> categoriesId = new ArrayList<Integer>();
                for (Category item: categoriesSelected
                ) {
                    categoriesId.add(item.getId());
                }


                Double price = 0.0;
                try {
                    price = Double.parseDouble(edtGiaCa.getText().toString());
                } catch (Exception e) {
                    Toast.makeText(getContext(), "Giá không phù hợp", Toast.LENGTH_SHORT).show();
                }
                List<Integer> categoriesIDs = new ArrayList<Integer>();
                Food food = new Food(name, description, price, unit, uriImage, categoriesId );
                foodHelper.Update(food);

                transaction = manager.beginTransaction();
                Food_Home_Admin food_home_admin = new Food_Home_Admin();
                Toast.makeText(getContext(), "Done", Toast.LENGTH_SHORT).show();
                transaction.replace(R.id.nav_host_fragment, food_home_admin);
                transaction.commit();
            }
        });

        btnBackToList = view.findViewById(R.id.btnBackToList);
        btnBackToList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
            imageURL = picturePath;
//                Log.d("myapp", picturePath + "");
//                edtDescription.setText(picturePath);
            c.close();
//                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
            Bitmap thumbnail = (BitmapFactory.decodeFile("/storage/emulated/0/Pictures/1572793569605.jpg"));
            thumbnail = getResizedBitmap(thumbnail, 400);
            Log.w("path of image from gallery......******************.........", picturePath + "");
            imgBtnHinhAnh.setImageBitmap(thumbnail);
            BitMapToString(thumbnail);

//                MediaStore.Images.Media.insertImage(getActivity().getContentResolver(), thumbnail, "imagefile" , "file");
        }

    }

    public String BitMapToString(Bitmap userImage1) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        userImage1.compress(Bitmap.CompressFormat.PNG, 60, baos);
        byte[] b = baos.toByteArray();
        Document_img1 = Base64.encodeToString(b, Base64.DEFAULT);
        return Document_img1;
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
