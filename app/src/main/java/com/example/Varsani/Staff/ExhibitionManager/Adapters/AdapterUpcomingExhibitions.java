package com.example.Varsani.Staff.ExhibitionManager.Adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.Varsani.Artists.Models.ExhibitionModal;
import com.example.Varsani.Clients.Models.UserModel;
import com.example.Varsani.R;
import com.example.Varsani.Staff.ExhibitionManager.UpcomingDetails;
import com.example.Varsani.Staff.Finance.Adapters.AdapterDonations;
import com.example.Varsani.Staff.Finance.DonationDetails;
import com.example.Varsani.Staff.Finance.Models.DonationsModel;
import com.example.Varsani.utils.SessionHandler;

import java.util.List;

public class AdapterUpcomingExhibitions extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<ExhibitionModal> items;
    private Context ctx;
    ProgressDialog progressDialog;
//    private OnItemClickListener mOnItemClickListener;
//    private OnMoreButtonClickListener onMoreButtonClickListener;

    //

    private SessionHandler session;
    private UserModel user;
    private String clientId = "";
    private String orderID = "";

    public static final String TAG = "Orders adapter";


    public AdapterUpcomingExhibitions(Context context, List<ExhibitionModal> items) {
        this.items = items;
        ctx = context;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {

        public TextView txv_exhibitionID,txv_title, txv_date,txv_status;


        public OriginalViewHolder(View v) {
            super(v);

            txv_status =v.findViewById(R.id.txv_status);
            txv_title =v.findViewById(R.id.txv_title);
            txv_exhibitionID =v.findViewById(R.id.txv_exhibitionID);
            txv_date = v.findViewById(R.id.txv_date);

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_exhibition_items, parent, false);
        vh = new AdapterUpcomingExhibitions.OriginalViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof AdapterUpcomingExhibitions.OriginalViewHolder) {
            final AdapterUpcomingExhibitions.OriginalViewHolder view = (AdapterUpcomingExhibitions.OriginalViewHolder) holder;

            final ExhibitionModal o= items.get(position);

            view.txv_exhibitionID.setText("#ID: "+o.getExhibitionID());
            view.txv_title.setText("Title: " + o.getTitle());
            view.txv_date.setText("Date: " + o.getStartingDate());
            view.txv_status.setText("Status: Upcoming" );



            view.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent in=new Intent(ctx, UpcomingDetails.class);
                    in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    in.putExtra("exhibitionID", o.getExhibitionID());
                    in.putExtra("title",o.getTitle());
                    in.putExtra("desc",o.getExhibitionDesc());
                    in.putExtra("startingDate",o.getStartingDate());
                    in.putExtra("endDate",o.getEndDate());
                    in.putExtra("venue",o.getVenue());
                    in.putExtra("type",o.getExhibitionType());
                    in.putExtra("bannerImg",o.getBannerImg());
                    ctx.startActivity(in);


                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
