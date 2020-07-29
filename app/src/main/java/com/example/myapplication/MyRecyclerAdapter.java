package com.example.myapplication;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.provider.Item;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.ViewHolder>  {

    //List<Item> data = new ArrayList<>();
    private List<Item> mItem = new ArrayList<>();



//    public MyRecyclerAdapter(ArrayList<item> data) {
//        this.data = data;
//    }

    public void setData(List<Item> data){
        this.mItem = data;
    }

    public MyRecyclerAdapter() {

    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //holder.id.setText(mItem.get(position).getId());
        holder.name.setText("Name: " + mItem.get(position).getName() + "");
        holder.price.setText("Price: " + mItem.get(position).getPrice());
        holder.quantity.setText("Quantity: " + mItem.get(position).getQuantity());
        holder.description.setText("Description: " + mItem.get(position).getDescription());
        holder.frozen.setText("Frozen: " + mItem.get(position).getFrozen());


    }

    @Override
    public int getItemCount() {
        if (mItem == null){
            return 0;
        }else {
            return mItem.size();
        }
    }




    public class ViewHolder extends RecyclerView.ViewHolder {
        //public View itemView;
        public TextView id;
        public TextView price;
        public TextView quantity;
        public TextView name;
        public TextView description;
        public TextView frozen;
        public  ViewHolder(View itemView){
            super(itemView);
            //this.itemView = itemView;
            id = itemView.findViewById(R.id.id);
            name = itemView.findViewById(R.id.name_id);
            price = itemView.findViewById(R.id.price_id);
            quantity = itemView.findViewById(R.id.quantity_id);
            description = itemView.findViewById(R.id.description_id);
            frozen = itemView.findViewById(R.id.frozen_id);

        }
    }
}
