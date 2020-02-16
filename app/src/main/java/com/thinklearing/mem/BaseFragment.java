package com.thinklearing.mem;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.thinklearing.mem.Data.DataBaseHelper;


public class BaseFragment extends Fragment {
    protected int LayoutId;
    MainActivity activity;
    private DataBaseHelper dbHelper;

    public BaseFragment() {
        if (LayoutId == 0) {
            LayoutId = R.layout.fragment_main;
        }
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(LayoutId, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().invalidateOptionsMenu();
        initFragment();
    }

    protected void initFragment() {
        activity = (MainActivity) getActivity();
    }

    protected DataBaseHelper getDataBase() {
        if (dbHelper == null)
            dbHelper = new DataBaseHelper(getContext());
        return dbHelper;
    }

}
