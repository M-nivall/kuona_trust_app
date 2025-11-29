package com.example.Varsani.Students.Adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.Varsani.Clients.Models.UserModel;
import com.example.Varsani.R;
import com.example.Varsani.Staff.Mentor.Adapter.AdapterWorkshops;
import com.example.Varsani.Staff.Mentor.Models.WorkshopModel;
import com.example.Varsani.Staff.Mentor.WorkshopInformation;
import com.example.Varsani.Students.Models.UpcomingWorkshopModel;
import com.example.Varsani.Students.WorkshopDetails;
import com.example.Varsani.utils.SessionHandler;

import java.util.List;

public class AdapterUpcomingWorkshops extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<UpcomingWorkshopModel> items;
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


    public AdapterUpcomingWorkshops(Context context, List<UpcomingWorkshopModel> items) {
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
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_workshop_items, parent, false);
        vh = new AdapterUpcomingWorkshops.OriginalViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof AdapterUpcomingWorkshops.OriginalViewHolder) {
            final AdapterUpcomingWorkshops.OriginalViewHolder view = (AdapterUpcomingWorkshops.OriginalViewHolder) holder;

            final UpcomingWorkshopModel o= items.get(position);

            view.txv_exhibitionID.setText("#ID: "+o.getWorkshopID());
            view.txv_title.setText("Topic: " + o.getTitle());
            view.txv_date.setText("Date: " + o.getWorkshopDate());
            view.txv_status.setText("Status: " + o.getWorkshopStatus());



            view.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent in=new Intent(ctx, WorkshopDetails.class);
                    in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    in.putExtra("workshopID", o.getWorkshopID());
                    in.putExtra("mentor",o.getMentor());
                    in.putExtra("title",o.getTitle());
                    in.putExtra("desc",o.getWorkshopDesc());
                    in.putExtra("date",o.getWorkshopDate());
                    in.putExtra("venue",o.getVenue());
                    in.putExtra("type",o.getWorkshopType());
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