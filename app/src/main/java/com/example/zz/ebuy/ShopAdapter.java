package com.example.zz.ebuy;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import java.util.List;



public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ViewHolder>{

    private Context mContext;

    private List<Shop> ShopList;

    public interface  onItemClickListener{
        void onItemClick(View view ,int position);
        void  onItemLongClick(View view,int position);
    }
    private ShopAdapter.onItemClickListener onItemClickListener;
    public void setOnItemClickListener(ShopAdapter.onItemClickListener onItemClickListener){
        this.onItemClickListener=onItemClickListener;
    }
    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView shopImage;
        TextView shopName;

        ViewHolder(View view) {
            super(view);
            cardView = (CardView) view;
            shopImage = view.findViewById(R.id.shop_image);
            shopName = view.findViewById(R.id.shop_name);
        }
    }

    ShopAdapter(List<Shop> shopList) {
        ShopList = shopList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.shop_item, parent, false);
        return new ViewHolder(view);
    }
            @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            final Shop shop = ShopList.get(position);
            holder.shopName.setText(shop.getName());
            Glide.with(mContext).load(shop.getImageId()).asBitmap().placeholder(R.drawable.shop).error(R.drawable.shop).into(holder.shopImage);
                if(onItemClickListener!=null){
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent=new Intent(mContext,shopitem.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                            Bundle bundle=new Bundle();
                            bundle.putString("shopname_intent",shop.getName());
                            bundle.putString("shopimage_intent",shop.getImageId());
                            intent.putExtras(bundle);
                            mContext.startActivity(intent);
                        }
                    });
                    holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            int layoutPos=holder.getLayoutPosition();
                            onItemClickListener.onItemLongClick(holder.itemView,layoutPos);
                            return false;
                        }
                    });
                }
            }


    @Override
    public int getItemCount() {
        return ShopList.size();
    }

}

