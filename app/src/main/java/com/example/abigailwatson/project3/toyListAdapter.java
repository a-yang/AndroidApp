package com.example.abigailwatson.project3;

/**
 * Created by angelayang316 on 4/8/16.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ToyListAdapter extends BaseAdapter {

    ToyList toyList;
    Context context;

    private static LayoutInflater inflater=null;

    public ToyListAdapter(SearchActivity searchActivity, ToyList tl) {
        // TODO Auto-generated constructor stub
        toyList = tl;
        context=searchActivity;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return toyList.getToyList().size();
    }

    @Override
    public Toy getItem(int position) {
        // TODO Auto-generated method stub
        return toyList.getToy(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder
    {
        TextView name;
        TextView price;
        ImageView img;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.toy_listview, null);
        holder.name=(TextView) rowView.findViewById(R.id.toyName);
        holder.price=(TextView) rowView.findViewById(R.id.toyPrice);
        holder.img=(ImageView) rowView.findViewById(R.id.toyImage);
        holder.name.setText(toyList.getToy(position).getToyName());
        String priceView = "$";
        priceView += Integer.toString(toyList.getToy(position).getPrice());
        holder.price.setText(priceView);
        holder.img.setImageBitmap(toyList.getToy(position).getImage());

        return rowView;
    }
}
