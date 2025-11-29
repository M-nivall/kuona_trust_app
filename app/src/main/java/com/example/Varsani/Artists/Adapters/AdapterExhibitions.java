package com.example.Varsani.Artists.Adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.example.Varsani.Artists.ExhibitionDetails;
import com.example.Varsani.Artists.Models.ExhibitionModal;
import com.example.Varsani.Clients.Adapters.AdapterProducts;
import com.example.Varsani.Clients.Models.ProductModal;
import com.example.Varsani.Clients.Models.UserModel;
import com.example.Varsani.Clients.SingleItem;
import com.example.Varsani.R;
import com.example.Varsani.Staff.Patron.MoreExhibitionDetails;
import com.example.Varsani.utils.SessionHandler;
import com.example.Varsani.utils.Urls;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdapterExhibitions extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ExhibitionModal> items;
    private List<ExhibitionModal> searchView;

    private Context ctx;
    ProgressDialog progressDialog;
    private AdapterExhibitions.OnItemClickListener mOnItemClickListener;
    private AdapterExhibitions.OnMoreButtonClickListener onMoreButtonClickListener;

    //

    private SessionHandler session;
    private UserModel user;
    private String clientId = "";
    private int count = 1;

    private EditText quantity;
    public static final String TAG = "Item_adapter";

    public void setOnItemClickListener(final AdapterExhibitions.OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    public void setOnMoreButtonClickListener(final AdapterExhibitions.OnMoreButtonClickListener onMoreButtonClickListener) {
        this.onMoreButtonClickListener = onMoreButtonClickListener;
    }

    public AdapterExhibitions(Context context, List<ExhibitionModal> items) {
        this.items = items;
        this.searchView = items;
        ctx = context;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {
        public ImageView banner_img;
        public TextView txv_title, txv_date, txv_venue;
        public Button btn_apply,btn_view_details;


        public OriginalViewHolder(View v) {
            super(v);
            banner_img = v.findViewById( R.id.banner_img);
            txv_title =v.findViewById(R.id.txv_title);
            txv_date =v.findViewById(R.id.txv_date);
            txv_venue = v.findViewById(R.id.txv_venue);
            btn_apply = v.findViewById(R.id.btn_apply);
            btn_view_details = v.findViewById(R.id.btn_view_details);

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.lv_item_exhibition, parent, false);
        vh = new AdapterExhibitions.OriginalViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof AdapterExhibitions.OriginalViewHolder) {
            final AdapterExhibitions.OriginalViewHolder view = (AdapterExhibitions.OriginalViewHolder) holder;

            final ExhibitionModal p = items.get(position);
            String url = Urls.ROOT_URL_EXHIBITION_IMAGES;
            Picasso.get()
                    .load(url+p.getBannerImg())
                    .fit()
                    .centerCrop()
                    .into(view.banner_img);
            view.txv_title.setText("Title: " + p.getTitle());
            view.txv_date.setText("Date: " + p.getStartingDate());
            view.txv_venue.setText("Venue: " + p.getVenue());
            session = new SessionHandler(ctx);
            user = session.getUserDetails();

            if(session.isLoggedIn()) {

                if (user.getUser_type().equals("Patron")) {
                    view.btn_apply.setVisibility(View.GONE);
                    view.btn_view_details.setVisibility(View.VISIBLE);
                }
            }

            try{
                clientId= user.getClientID();
            }catch (RuntimeException e){

            }

            view.btn_apply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(session.isLoggedIn()) {

                        if (user.getUser_type().equals("Artist")) {

                            String exhibitionID = p.getExhibitionID();
                            String title = p.getTitle();
                            String desc = p.getExhibitionDesc();
                            String startingDate = p.getStartingDate();
                            String endDate = p.getEndDate();
                            String venue = p.getVenue();
                            String exType = p.getExhibitionType();
                            String bannerImg = p.getBannerImg();
                            Intent in = new Intent(ctx, ExhibitionDetails.class);
                            in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            in.putExtra("exhibitionID", exhibitionID);
                            in.putExtra("title", title);
                            in.putExtra("startingDate", startingDate);
                            in.putExtra("endDate", endDate);
                            in.putExtra("venue", venue);
                            in.putExtra("exType", exType);
                            in.putExtra("bannerImg", bannerImg);
                            in.putExtra("desc", desc);
                            ctx.startActivity(in);

                        } else {

                            Toast.makeText(ctx, "Please login as a Artist to apply", Toast.LENGTH_SHORT).show();
                        }
                    }else {

                        Toast.makeText(ctx, "Please login to apply", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            view.btn_view_details.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (user.getUser_type().equals("Patron")){

                        String exhibitionID = p.getExhibitionID();
                        String title=p.getTitle();
                        String desc=p.getExhibitionDesc();
                        String startingDate=p.getStartingDate();
                        String endDate=p.getEndDate();
                        String venue=p.getVenue();
                        String exType=p.getExhibitionType();
                        String bannerImg=p.getBannerImg();
                        Intent in = new Intent(ctx, MoreExhibitionDetails.class);
                        in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        in.putExtra("exhibitionID", exhibitionID);
                        in.putExtra("title", title);
                        in.putExtra("startingDate", startingDate);
                        in.putExtra("endDate", endDate);
                        in.putExtra("venue", venue);
                        in.putExtra("exType", exType);
                        in.putExtra("bannerImg", bannerImg);
                        in.putExtra("desc", desc);
                        ctx.startActivity(in);

                    }else {

                        Toast.makeText( ctx,"Please login as a Artist to apply",Toast.LENGTH_SHORT ).show();

                    }
                }
            });
        }
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
        void onItemClick(View view, ExhibitionModal obj, int pos);
    }

    public interface OnMoreButtonClickListener {
        void onItemClick(View view, ExhibitionModal obj, MenuItem item);
    }
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();

                if (charString.isEmpty()) {

                    items = searchView;
                } else {

                    ArrayList<ExhibitionModal> filteredList = new ArrayList<>();

                    for (ExhibitionModal androidVersion : items) {

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
                items = (ArrayList<ExhibitionModal>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}
