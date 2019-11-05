package com.example.baitaplonandroid.ui.Bill;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.baitaplonandroid.R;
import com.example.baitaplonandroid.ui.Models.Bill;
import com.example.baitaplonandroid.ui.Models.User;
import com.example.baitaplonandroid.ui.SQLHelper.BillHelper;
import com.example.baitaplonandroid.ui.SQLHelper.UserHelper;
import com.example.baitaplonandroid.ui.User.Admin_Edit_User;
import com.example.baitaplonandroid.ui.User.CustomAdapterUser;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Bill_Home_Admin extends Fragment {

    private List<Bill> bills;
    private BillHelper billHelper;
    private FragmentManager manager;
    private FragmentTransaction transaction;
    private ListView listBill;


    public Bill_Home_Admin() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bill__home__admin, container, false);

        //FRAMENT CONFIG
        manager = getFragmentManager();
        transaction = manager.beginTransaction();

        //CONFIG DATABASE PROVIDER
        billHelper = new BillHelper(getContext());

        //GET LIST USER
        bills = billHelper.getAllBill();

        //CUSTOM ADAPTER
        Custom_Adapter_Bill customAdapterBill = new Custom_Adapter_Bill(getContext(), bills);
        listBill = view.findViewById(R.id.listBill);
        listBill.setAdapter(customAdapterBill);

        listBill.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bill bill = bills.get(i);
                Toast.makeText(getContext(), bill.getTongTien() + "", Toast.LENGTH_SHORT).show();
//                Admin_Edit_User edit_user  = new Admin_Edit_User();
                Bill_Admin_Update billAdminUpdate = new Bill_Admin_Update();
                Bundle bundle = new Bundle();
                bundle.putString("billid", bill.getId() + "");
                bundle.putDouble("tongTien", bill.getTongTien());
                bundle.putInt("ispaid", bill.getIsPayed());
                billAdminUpdate.setArguments(bundle);
//                bundle.putString("tongtien", user.getRole());
//                edit_user.setArguments(bundle);
                transaction.replace(R.id.nav_host_fragment, billAdminUpdate);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return view;
    }


}
