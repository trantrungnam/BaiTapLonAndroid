package com.example.baitaplonandroid.ui.Bill;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.baitaplonandroid.R;
import com.example.baitaplonandroid.ui.Models.Bill;
import com.example.baitaplonandroid.ui.Models.Bill_Food;
import com.example.baitaplonandroid.ui.SQLHelper.BillFoodHelper;
import com.example.baitaplonandroid.ui.SQLHelper.BillHelper;
import com.example.baitaplonandroid.ui.gallery.Bill_Food_Custom_Adapter;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Bill_Admin_Update extends Fragment {


    private ListView listViewCategories;
    private TextView txtTongTieng;
    private BillFoodHelper billFoodHelper;
    private BillHelper billHelper;
    private Button btnThemMon, btnThanhToan;
    private FragmentManager manager;
    private FragmentTransaction transaction;
    private List<Bill_Food> bill_foods_item;
    private ListView listViewCategories1;

    public Bill_Admin_Update() {

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bill__admin__update, container, false);


        //BILL FOOD HELPER
        billFoodHelper = new BillFoodHelper(getContext());
        billHelper = new BillHelper(getContext());

        listViewCategories = view.findViewById(R.id.listViewCategories);

        BillFoodHelper billFoodHelper = new BillFoodHelper(getContext());

        final String billId = getArguments().getString("billid");;

        Bill bill = billHelper.getBillById(Integer.parseInt(billId));

        List<Bill_Food> bill_foods = billFoodHelper.getAllBill_food_By_ID(Integer.parseInt(billId));


        Bill_Food_Custom_Adapter bill_food_custom_adapter = new Bill_Food_Custom_Adapter(getActivity(), getContext(), bill_foods);
        listViewCategories.setAdapter(bill_food_custom_adapter);

        btnThanhToan = view.findViewById(R.id.btnThanhToan);

        if (bill.getIsPayed() == 0) {
            btnThanhToan.setClickable(true);
            btnThanhToan.setText("Chưa thanh toán");
            btnThanhToan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getContext(), "Thanh toán", Toast.LENGTH_SHORT).show();
                    billHelper.updatePayment(Integer.parseInt(billId));
                    Bill_Admin_Update billAdminUpdate = new Bill_Admin_Update();
                    Bundle bundle = new Bundle();
                    bundle.putString("billid", billId);
                    manager = getFragmentManager();
                    transaction = manager.beginTransaction();
                    billAdminUpdate.setArguments(bundle);
                    transaction.replace(R.id.nav_host_fragment, billAdminUpdate);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            });
        } else {
            btnThanhToan.setClickable(false);
            btnThanhToan.setText("Đã thanh toán");
        }



        txtTongTieng = view.findViewById(R.id.txtTongTieng);
        txtTongTieng.setText(bill.getTongTien() + "");


//        btnTao = view.findViewById(R.id.btnThanhToan);

//        btnTao.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("restaurant", 0);
//                SharedPreferences.Editor editor = sharedPreferences.edit();
//                String bill_order = sharedPreferences.getString("bill_order_list", "");
//                Gson gson = new Gson();
//                if (!bill_order.equals("")) {
//                    Bill item = new Bill();
//                    item.setTongTien(Double.parseDouble(txtTongTieng.getText().toString()));
//                    Bill result = billHelper.addBill(item);
//                    if (result != null) {
//                        Bill_Food_List_Type bill_food_list_type = gson.fromJson(bill_order, Bill_Food_List_Type.class);
//                        bill_foods_item = bill_food_list_type.getFoodList();
//                        for (Bill_Food x : bill_foods_item
//                        ) {
//                            billFoodHelper.addBillFood(x, result.getId());
//                        }
//                    }
//                    editor.putString("bill_order_list", "");
//                    editor.commit();
//                    Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();
//
//                    Bill_Home_Fragment bill_home_fragment = new Bill_Home_Fragment();
//
//                    manager = getFragmentManager();
//                    transaction = manager.beginTransaction();
//                    transaction.replace(R.id.nav_host_fragment, bill_home_fragment);
//                    transaction.commit();
//                }
//            }
//        });

        return view;
    }
}
