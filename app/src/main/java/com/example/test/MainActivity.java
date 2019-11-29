package com.example.test;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity implements ListViewAdapter.ClickListener {

    private Button delete;
    private CheckBox edit;
    private CheckBox all;
    private ListView listView;
    private ArrayList<String> data;
    private ArrayList<String> selectData;
    private ArrayList<Boolean> state;
    private ListViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initData();
        initAdapter();
    }

    private void initAdapter() {
        adapter = new ListViewAdapter(this, state, data);
        adapter.setClickListener(this);
        listView.setAdapter(adapter);
    }

    private void initView() {
        delete = ((Button) findViewById(R.id.btn_delete));
        edit = ((CheckBox) findViewById(R.id.cb_edit));
        all = ((CheckBox) findViewById(R.id.check_all));
        listView = ((ListView) findViewById(R.id.listView));

        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (all.isChecked()) {
                    Collections.replaceAll(state, false, true);
                } else {
                    Collections.replaceAll(state, true, false);
                }
                adapter.setState(state);
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edit.isChecked()) {
                    all.setVisibility(View.VISIBLE);
                    delete.setVisibility(View.VISIBLE);
                    adapter.setVisibilityFlag(true);
                } else {
                    all.setVisibility(View.GONE);
                    delete.setVisibility(View.GONE);
                    adapter.setVisibilityFlag(false);
                }
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (state.indexOf(false) == -1) {
                    data.removeAll(data);
                    state.removeAll(state);
                } else {
                    removeData();
                }
                adapter.setData(data);
                adapter.setState(state);
            }
        });
    }

    private void removeData() {

        for (int i = 0; i < state.size(); i++) {
            if (state.get(i)) {            //如果被选中则把该条数据添加到selectData集合中
                selectData.add(data.get(i));
            }
        }

        for (int j = 0; j < selectData.size(); j++) {
            for (int i = 0; i < data.size(); i++) {
                if (data.get(i) != null) {
                    if (selectData.get(j) == data.get(i)) {
                        data.set(i, null);        //把被选中的数据设置为null
                    }
                }
            }
        }

        for (int i = 0; i < data.size(); i++) {
            if (data.get(i) == null) {            //对为null的数据删除
                data.remove(i);
                i--;
            }
        }

        for (int i = 0; i < state.size(); i++) {
            if (state.get(i)) {
                state.remove(i);            //把对应的标记删除
                i--;
            }
        }
    }

    private void initData() {
        data = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            data.add("NAME_" + i);
        }

        state = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            state.add(false);
        }

        selectData = new ArrayList<>();
    }


    @Override
    public void itemClickIsCheckedListener(String str) {
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).equals(str)) {
                if (state.get(i)) {
                    state.set(i, false);
                } else {
                    state.set(i, true);
                }
            }
        }
        adapter.setState(state);
    }
}