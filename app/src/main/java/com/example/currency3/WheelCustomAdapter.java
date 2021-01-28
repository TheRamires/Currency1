package com.example.currency3;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;

import com.example.currency3.databinding.ItemBinding;
import com.example.currency3.pojo.Currency;
import com.wx.wheelview.adapter.BaseWheelAdapter;

import java.util.List;

public class WheelCustomAdapter extends BaseWheelAdapter<WheelCustomAdapter.Item> {
    List<Currency> list;

    public WheelCustomAdapter(List<Currency> list){
        this.list=list;
    }

    @Override
    protected View bindView(int position, View convertView, ViewGroup parent) {
        Item item=null;
        if (convertView==null){
            ItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                    R.layout.item, parent, false);

            item=new Item(binding);
            item.view=binding.getRoot();
            item.view.setTag(item);
        } else {
            item=(Item)convertView.getTag();
        }
        item.binding.setItem(list.get(position));
        return item.view;
    }

    class Item {
        private View view;
        private TextView textView;
        private TextView textView2;
        private ItemBinding binding;

        Item(ItemBinding binding){
            this.view=binding.getRoot();
            this. binding=binding;
        }
    }
}