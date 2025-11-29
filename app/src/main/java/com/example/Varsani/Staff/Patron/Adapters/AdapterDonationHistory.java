package com.example.Varsani.Staff.Patron.Adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.Varsani.Artists.Adapters.AdapterMyDonations;
import com.example.Varsani.Artists.Models.MyDonationsModel;
import com.example.Varsani.Artists.MyDonationDetails;
import com.example.Varsani.Clients.Models.UserModel;
import com.example.Varsani.R;
import com.example.Varsani.Staff.Patron.DonationHistoryDetails;
import com.example.Varsani.Staff.Patron.Models.DonationHistoryModel;
import com.example.Varsani.utils.SessionHandler;

import java.util.List;

public class AdapterDonationHistory extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<DonationHistoryModel> items;

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


    public AdapterDonationHistory(Context context, List<DonationHistoryModel> items) {
        this.items = items;
        ctx = context;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {

        public TextView txv_title,txv_donor, txv_date,txv_amount,txv_status;


        public OriginalViewHolder(View v) {
            super(v);

            txv_status =v.findViewById(R.id.txv_status);
            txv_donor =v.findViewById(R.id.txv_donor);
            txv_title =v.findViewById(R.id.txv_title);
            txv_date = v.findViewById(R.id.txv_date);
            txv_amount = v.findViewById(R.id.txv_amount);

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_donation_list, parent, false);
        vh = new AdapterDonationHistory.OriginalViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof AdapterDonationHistory.OriginalViewHolder) {
            final AdapterDonationHistory.OriginalViewHolder view = (AdapterDonationHistory.OriginalViewHolder) holder;

            final DonationHistoryModel o= items.get(position);

            view.txv_title.setText("Title: " + o.getTitle());
            view.txv_donor.setText("Artist: " + o.getArtistName());
            view.txv_date.setText("Date: " + o.getDonationDate());
            view.txv_status.setText("Status: " + o.getDonationStatus());
            view.txv_amount.setText("Amount: KES " + o.getAmount());


            view.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent in=new Intent(ctx, DonationHistoryDetails.class);
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
                    in.putExtra("date",o.getDonationDate());
                    in.putExtra("title",o.getTitle());
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
