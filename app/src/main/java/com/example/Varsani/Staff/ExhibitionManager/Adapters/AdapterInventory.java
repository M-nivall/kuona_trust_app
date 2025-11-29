package com.example.Varsani.Staff.ExhibitionManager.Adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.Varsani.Clients.Models.UserModel;
import com.example.Varsani.R;
import com.example.Varsani.Staff.Adapters.AdapterGetStock;
import com.example.Varsani.Staff.ExhibitionManager.Models.InventoryModel;
import com.example.Varsani.Staff.Models.GetStockModel;
import com.example.Varsani.Staff.Store_mrg.AddStock;
import com.example.Varsani.utils.SessionHandler;
import com.example.Varsani.utils.Urls;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AdapterInventory extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<InventoryModel> items;
    private List<InventoryModel> searchList;

    private Context ctx;
    ProgressDialog progressDialog;
//    private OnItemClickListener mOnItemClickListener;
//    private OnMoreButtonClickListener onMoreButtonClickListener;

    //

    private SessionHandler session;
    private UserModel user;
    private String clientId = "";
    private String orderID = "";

    public static final String TAG = " adapter";

    public AdapterInventory(Context context, List<InventoryModel> items) {
        this.items = items;
        this.searchList = items;
        ctx = context;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {

        public TextView txtTitle,txtArtistName, txtDatePresented;
        public ImageView imgArtwork;


        public OriginalViewHolder(View v) {
            super(v);

            txtTitle =v.findViewById(R.id.txtTitle);
            txtDatePresented = v.findViewById(R.id.txtDatePresented);
            txtArtistName = v.findViewById(R.id.txtArtistName);
            imgArtwork = v.findViewById(R.id.imgArtwork);

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.lv_inventory, parent, false);
        vh = new AdapterInventory.OriginalViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof AdapterInventory.OriginalViewHolder) {
            final AdapterInventory.OriginalViewHolder view = (AdapterInventory.OriginalViewHolder) holder;

            final InventoryModel o= items.get(position);

            String url = Urls.ROOT_URL_EXHIBITION_ARTS;
            Picasso.get()
                    .load(url+o.getImageName())
                    .fit()
                    .centerCrop()
                    .into(view.imgArtwork);

            view.txtTitle.setText("Title: " + o.getTitle());
            view.txtDatePresented.setText("Date Presented: "+o.getStartingDate());
            view.txtArtistName.setText("Artist: "+o.getArtistName());

           /* view.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent in=new Intent(ctx, AddStock.class);
                    in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    in.putExtra("stockID", o.getStockID());
                    in.putExtra("price", o.getPrice());
                    in.putExtra("productName", o.getProductName());
                    in.putExtra("stock", o.getStock());
                    ctx.startActivity(in);

                }
            });

            */
        }
    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();

                if (charString.isEmpty()) {

                    items = searchList;
                } else {

                    ArrayList<InventoryModel> filteredList = new ArrayList<>();

                    for (InventoryModel androidVersion : items) {

                        if (androidVersion.getTitle().toLowerCase().contains(charString)) {

                            filteredList.add(androidVersion);
                        }
                    }

                    items = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = items;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                items = (ArrayList<InventoryModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}
