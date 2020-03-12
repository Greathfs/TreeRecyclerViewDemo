package com.hfs.treerecyclerviewdemo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.hfs.treerecyclerviewdemo.R;
import com.hfs.treerecyclerviewdemo.adapter.RecyclerAdapter;
import com.hfs.treerecyclerviewdemo.model.ItemData;

import java.util.List;

/**
 *
 * @author HuangFusheng
 * @date 2020-03-05
 * description MainActivity
 */
public class MainActivity extends Activity {

    private RecyclerView recyclerView;

    private RecyclerAdapter myAdapter;

    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        myAdapter = new RecyclerAdapter(this);
        recyclerView.setAdapter(myAdapter);
        initDatas();
    }

    private void initDatas() {
        List<ItemData> list = myAdapter.getChildrenByPath("0", 0);
        myAdapter.addAll(list, 0);
    }

}
