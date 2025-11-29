package com.example.Varsani.Staff.Mentor.Adapter;

import static com.example.Varsani.utils.Urls.URL_APPROVE_STUDENT;

import android.app.Activity;
import android.app.ProgressDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.Varsani.Clients.Models.UserModel;
import com.example.Varsani.R;
import com.example.Varsani.Staff.Mentor.Models.WorkshopApplicantModel;
import com.example.Varsani.utils.SessionHandler;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdapterWorkshopApplicant extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<WorkshopApplicantModel> items;
    private final Activity activity;
    ProgressDialog progressDialog;

    public AdapterWorkshopApplicant(Activity activity, List<WorkshopApplicantModel> items) {
        this.items = items;
        this.activity = activity;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {

        public TextView txv_ID, txv_studentName, txv_status, btn_approve;

        public OriginalViewHolder(View v) {
            super(v);
            txv_status = v.findViewById(R.id.txv_status);
            txv_ID = v.findViewById(R.id.txv_ID);
            txv_studentName = v.findViewById(R.id.txv_studentName);
            btn_approve = v.findViewById(R.id.btn_approve);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_workshop_applicant, parent, false);
        return new AdapterWorkshopApplicant.OriginalViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof AdapterWorkshopApplicant.OriginalViewHolder) {
            final AdapterWorkshopApplicant.OriginalViewHolder view = (AdapterWorkshopApplicant.OriginalViewHolder) holder;

            final WorkshopApplicantModel o = items.get(position);

            if (o.getAttendanceStatus().equals("Pending approval")) {
                view.btn_approve.setVisibility(View.VISIBLE);
            } else {
                view.btn_approve.setVisibility(View.GONE);
            }

            view.txv_ID.setText("#ID: " + o.getID());
            view.txv_studentName.setText("Name: " + o.getStudentNames());
            view.txv_status.setText("Status: " + o.getAttendanceStatus());

            view.btn_approve.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int adapterPosition = view.getAdapterPosition();
                    if (adapterPosition != RecyclerView.NO_POSITION) {
                        WorkshopApplicantModel applicant = items.get(adapterPosition);
                        approveStudent(applicant.getStudentID(), applicant.getWorkshopID(), adapterPosition);
                    }
                }
            });
        }
    }

    public void approveStudent(final String studentID, final String workshopID, final int position) {
        progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage("Approving...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_APPROVE_STUDENT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            Log.e("RESPONSE", response);
                            JSONObject jsonObject = new JSONObject(response);
                            String status = jsonObject.getString("status");
                            String msg = jsonObject.getString("message");

                            Toast toast = Toast.makeText(activity, msg, Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.TOP, 0, 250);
                            toast.show();

                            if (status.equals("1")) {
                                items.remove(position);
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position, items.size());

                                Toast.makeText(activity, "Applicant approved successfully", Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast toast = Toast.makeText(activity, e.toString(), Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.TOP, 0, 250);
                            toast.show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        error.printStackTrace();
                        Toast toast = Toast.makeText(activity, error.toString(), Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.TOP, 0, 250);
                        toast.show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("studentID", studentID);
                params.put("workshopID", workshopID);
                Log.e("PARAMS", "" + params);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        requestQueue.add(stringRequest);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
