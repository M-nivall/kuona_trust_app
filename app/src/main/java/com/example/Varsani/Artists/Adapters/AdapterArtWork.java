package com.example.Varsani.Artists.Adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.Varsani.Artists.Models.ArtworkModel;
import com.example.Varsani.Artists.ViewDetails;
import com.example.Varsani.Clients.Adapters.AdapterServices;
import com.example.Varsani.Clients.Models.ProductModal;
import com.example.Varsani.Clients.Models.ServicesModal;
import com.example.Varsani.Clients.Models.UserModel;
import com.example.Varsani.Clients.SingleService;
import com.example.Varsani.R;
import com.example.Varsani.utils.SessionHandler;
import com.example.Varsani.utils.Urls;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdapterArtWork extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<ArtworkModel> items;
    private List<ArtworkModel> searchView;

    private Context ctx;
    ProgressDialog progressDialog;
    private AdapterArtWork.OnItemClickListener mOnItemClickListener;
    private AdapterArtWork.OnMoreButtonClickListener onMoreButtonClickListener;

    private SessionHandler session;
    private UserModel user;
    private String clientId = "";
    private String productID = "";
    private int count = 1;

    private EditText quantity;
    public static final String TAG = "Item_adapter";

    public void setOnItemClickListener(final AdapterArtWork.OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    public void setOnMoreButtonClickListener(final AdapterArtWork.OnMoreButtonClickListener onMoreButtonClickListener) {
        this.onMoreButtonClickListener = onMoreButtonClickListener;
    }

    public AdapterArtWork(Context context, List<ArtworkModel> items) {
        this.items = items;
        this.searchView = items;
        ctx = context;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {
        public ImageView artwork_image;
        public TextView artwork_title,artist_name ;
        private ImageView img_details;


        public OriginalViewHolder(View v) {
            super(v);
            artwork_image = v.findViewById( R.id.artwork_image);
            artwork_title = v.findViewById(R.id.artwork_title);
            artist_name = v.findViewById(R.id.artist_name);
            img_details = v.findViewById(R.id.view_details);

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.lv_item_artwork, parent, false);
        vh = new AdapterArtWork.OriginalViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof AdapterArtWork.OriginalViewHolder) {
            final AdapterArtWork.OriginalViewHolder view = (AdapterArtWork.OriginalViewHolder) holder;

            final ArtworkModel p = items.get(position);
            String url = Urls.ROOT_URL_ART_IMAGES;
            Picasso.get()
                    .load(url+p.getImgUrl())
                    .fit()
                    .centerCrop()
                    .into(view.artwork_image);
            view.artwork_title.setText(p.getTitle());
            view.artist_name.setText("Artist: " + p.getUsername());

            session = new SessionHandler(ctx);
            user = session.getUserDetails();
            try{
                clientId= user.getClientID();
            }catch (RuntimeException e){

            }
            view.img_details.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(session.isLoggedIn()) {

                        if (user.getUser_type().equals("Artist") || user.getUser_type().equals("Patron")) {

                            String artID = p.getArtID();
                            String artistID = p.getArtistID();
                            String title = p.getTitle();
                            String desc = p.getDesc();
                            String imgUrl = p.getImgUrl();
                            String fullName = p.getFullName();
                            String username = p.getUsername();
                            Intent in = new Intent(ctx, ViewDetails.class);
                            in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            in.putExtra("artID", artID);
                            in.putExtra("artistID", artistID);
                            in.putExtra("title", title);
                            in.putExtra("desc", desc);
                            in.putExtra("imgUrl", imgUrl);
                            in.putExtra("fullName", fullName);
                            in.putExtra("username", username);
                            ctx.startActivity(in);

                        } else {

                            Toast.makeText(ctx, "Please login as Patron to Book", Toast.LENGTH_SHORT).show();

                        }
                    }else {

                        Toast.makeText(ctx, "Please login to view details", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
    public void addToCart(){
        final String itemQty = quantity.getText().toString().trim();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.URL_ADD_CART,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            Log.e("response ",response);
                            String msg = jsonObject.getString("message");
                            int status = jsonObject.getInt("status");
                            if (status == 1){
                                Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }else {
                                Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show();
                                Log.e(TAG, "error1 "+msg );
                                progressDialog.dismiss();
                            }

                        }catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(ctx, "error"+e.toString(), Toast.LENGTH_SHORT).show();
                            Log.e(TAG, "error2 "+e.toString());
                            progressDialog.dismiss();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ctx, "error"+error.toString(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, "error3 "+error );
                progressDialog.dismiss();


            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("clientID", clientId);
                params.put("itemID", productID);
                params.put("quantity", itemQty);
                Log.e(TAG, "params is "+params);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(ctx);
        requestQueue.add(stringRequest);
    }

    public void progressShow(){
        progressDialog = new ProgressDialog( ctx);
        progressDialog.setMessage(" Please wait...");
        progressDialog.setTitle("Adding to cart... ");
        progressDialog.show();
    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, ProductModal obj, int pos);
    }

    public interface OnMoreButtonClickListener {
        void onItemClick(View view, ProductModal obj, MenuItem item);
    }
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();

                if (charString.isEmpty()) {

                    items = searchView;
                } else {

                    ArrayList<ArtworkModel> filteredList = new ArrayList<>();

                    for (ArtworkModel androidVersion : items) {

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
                items = (ArrayList<ArtworkModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}
