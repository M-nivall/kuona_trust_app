package com.example.Varsani.Artists.Adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.Varsani.Artists.Models.AppliedExhibitionModel;
import com.example.Varsani.Clients.Models.UserModel;
import com.example.Varsani.R;
import com.example.Varsani.Staff.ExhibitionManager.Adapters.AdapterApplicant;
import com.example.Varsani.Staff.ExhibitionManager.ApplicantDetails;
import com.example.Varsani.Staff.ExhibitionManager.Models.ApplicantModel;
import com.example.Varsani.utils.SessionHandler;

import java.util.List;

public class AdapterAppliedExhibition extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<AppliedExhibitionModel> items;
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


    public AdapterAppliedExhibition(Context context, List<AppliedExhibitionModel> items) {
        this.items = items;
        ctx = context;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {

        public TextView txv_exhibitionID,txv_title, txv_artistName,txv_status;


        public OriginalViewHolder(View v) {
            super(v);

            txv_status =v.findViewById(R.id.txv_status);
            txv_title =v.findViewById(R.id.txv_title);
            txv_exhibitionID =v.findViewById(R.id.txv_exhibitionID);
            txv_artistName = v.findViewById(R.id.txv_artistName);

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_applicant_items, parent, false);
        vh = new AdapterAppliedExhibition.OriginalViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof AdapterAppliedExhibition.OriginalViewHolder) {
            final AdapterAppliedExhibition.OriginalViewHolder view = (AdapterAppliedExhibition.OriginalViewHolder) holder;

            final AppliedExhibitionModel o= items.get(position);

            view.txv_exhibitionID.setText("#ID: " + o.getExhibitionID());
            view.txv_title.setText("Title: " + o.getTitle());
            view.txv_artistName.setText("Artist: " + o.getArtistName());
            view.txv_status.setText("Status: " + o.getExStatus() );



           /* view.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent in=new Intent(ctx, ApplicantDetails.class);
                    in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    in.putExtra("ID", o.getID());
                    in.putExtra("exhibitionID",o.getExhibitionID());
                    in.putExtra("artistID",o.getArtistID());
                    in.putExtra("artistName",o.getArtistName());
                    in.putExtra("title",o.getTitle());
                    in.putExtra("desc",o.getArtDesc());
                    in.putExtra("imgName",o.getImageName());
                    in.putExtra("exStatus",o.getExStatus());
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
}
