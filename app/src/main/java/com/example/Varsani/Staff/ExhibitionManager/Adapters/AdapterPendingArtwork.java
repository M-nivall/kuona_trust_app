package com.example.Varsani.Staff.ExhibitionManager.Adapters;

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
import com.example.Varsani.Staff.ExhibitionManager.ApplicantDetails;
import com.example.Varsani.Staff.ExhibitionManager.ArtworkDetails;
import com.example.Varsani.Staff.ExhibitionManager.Models.ApplicantModel;
import com.example.Varsani.Staff.ExhibitionManager.Models.PendingArtworkModel;
import com.example.Varsani.utils.SessionHandler;

import java.util.List;

public class AdapterPendingArtwork extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<PendingArtworkModel> items;
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


    public AdapterPendingArtwork(Context context, List<PendingArtworkModel> items) {
        this.items = items;
        ctx = context;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {

        public TextView txv_ID,txv_title, txv_artistName,txv_status;


        public OriginalViewHolder(View v) {
            super(v);

            txv_status =v.findViewById(R.id.txv_status);
            txv_title =v.findViewById(R.id.txv_title);
            txv_ID =v.findViewById(R.id.txv_ID);
            txv_artistName = v.findViewById(R.id.txv_artistName);

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_pending_artwork, parent, false);
        vh = new AdapterPendingArtwork.OriginalViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof AdapterPendingArtwork.OriginalViewHolder) {
            final AdapterPendingArtwork.OriginalViewHolder view = (AdapterPendingArtwork.OriginalViewHolder) holder;

            final PendingArtworkModel o= items.get(position);

            view.txv_ID.setText("#ID: " + o.getID());
            view.txv_title.setText("Title: " + o.getTitle());
            view.txv_artistName.setText("Artist: " + o.getArtistName());
            view.txv_status.setText("Status: " + o.getExStatus() );



            view.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent in=new Intent(ctx, ArtworkDetails.class);
                    in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    in.putExtra("ID", o.getID());
                    in.putExtra("artistID",o.getArtistID());
                    in.putExtra("artistName",o.getArtistName());
                    in.putExtra("title",o.getTitle());
                    in.putExtra("desc",o.getArtDesc());
                    in.putExtra("imgName",o.getImageName());
                    in.putExtra("exStatus",o.getExStatus());
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
