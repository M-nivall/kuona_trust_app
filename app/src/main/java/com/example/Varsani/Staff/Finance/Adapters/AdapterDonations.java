package com.example.Varsani.Staff.Finance.Adapters;

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
import com.example.Varsani.Staff.Finance.Models.DonationsModel;
import com.example.Varsani.Staff.Finance.DonationDetails;
import com.example.Varsani.utils.SessionHandler;

import java.util.List;

public class AdapterDonations extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<DonationsModel> items;

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


    public AdapterDonations(Context context, List<DonationsModel> items) {
        this.items = items;
        ctx = context;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {

        public TextView txv_donationID,txv_donor, txv_artist,txv_amount,txv_status;


        public OriginalViewHolder(View v) {
            super(v);

            txv_status =v.findViewById(R.id.txv_status);
            txv_donor =v.findViewById(R.id.txv_donor);
            txv_donationID =v.findViewById(R.id.txv_donationID);
            txv_artist = v.findViewById(R.id.txv_artist);
            txv_amount = v.findViewById(R.id.txv_amount);

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_donation_items, parent, false);
        vh = new AdapterDonations.OriginalViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof AdapterDonations.OriginalViewHolder) {
            final AdapterDonations.OriginalViewHolder view = (AdapterDonations.OriginalViewHolder) holder;

            final DonationsModel o= items.get(position);

            view.txv_donationID.setText("#ID: "+o.getDonationID());
            view.txv_donor.setText("Donor: " + o.getDonorName());
            view.txv_artist.setText("Artist: " + o.getArtistName());
            view.txv_status.setText("Status: " + o.getDonationStatus());
            view.txv_amount.setText("Amount: " + o.getAmount());


            view.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent in=new Intent(ctx, DonationDetails.class);
                    in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    in.putExtra("donationID", o.getDonationID());
                    in.putExtra("donorID",o.getDonorID());
                    in.putExtra("artist",o.getArtistName());
                    in.putExtra("donor",o.getDonorName());
                    in.putExtra("artID",o.getArtID());
                    in.putExtra("artistID",o.getArtistID());
                    in.putExtra("amount",o.getAmount());
                    in.putExtra("paymentMethod",o.getPaymentMethod());
                    in.putExtra("refCode",o.getReferenceCode());
                    in.putExtra("donationStatus",o.getDonationStatus());
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
