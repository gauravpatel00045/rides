package com.rides.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.rides.R;
import com.rides.model.VehicleModel;
import com.rides.util.CircleTransform;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.util.List;

public class AdpVehicleListRv extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_ITEM = 1;
    private static final int TYPE_FOOTER = 2;

    private Context mcontext;
    private List<VehicleModel> vehicleList;
    private boolean showFooter;

    public AdpVehicleListRv(Context mContext, List<VehicleModel> vehicleList, boolean showFooter) {
        this.mcontext = mContext;
        this.vehicleList = vehicleList;
        this.showFooter = showFooter;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {

            View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_car_item, parent, false);
            return new ItemViewHolder(layoutView);

        } else if (viewType == TYPE_FOOTER) {

            View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.footer_recyclerview, parent, false);
            return new FooterViewHolder(layoutView);

        }

        throw new RuntimeException("there is no type matches " + viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof FooterViewHolder) {

            FooterViewHolder footerViewHolder = (FooterViewHolder) holder;
            if (showFooter) {
                footerViewHolder.footerLoading.setVisibility(View.VISIBLE);
            } else {
                footerViewHolder.footerLoading.setVisibility(View.GONE);
            }

        } else if (holder instanceof ItemViewHolder) {
            final ItemViewHolder itemViewHolder = (ItemViewHolder) holder;

            VehicleModel vehicleModel = vehicleList.get(position);

            itemViewHolder.cardView.setTag(R.string.strSelectedItemInfo, vehicleModel);

            itemViewHolder.txtModelName.setText(vehicleModel.getMakeAndModel());
            itemViewHolder.txtVin.setText(vehicleModel.getVin());


        }
    }

    @Override
    public int getItemCount() {
        if (vehicleList.isEmpty()) {
            return 0;
        }
        return vehicleList.size() + 1;
    }

    public int getItemViewType(int position) {
        if (position != 0 && position == getItemCount() - 1) {
            return TYPE_FOOTER;
        } else
            return TYPE_ITEM;
    }

    public void footerStatus(boolean status) {

        showFooter = status;
    }

    public void refreshList(List<VehicleModel> vehicleList) {
        this.vehicleList = vehicleList;
        notifyDataSetChanged();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView txtModelName, txtVin;
        private ImageView ivCarPhoto;
        private CardView cardView;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            ivCarPhoto = itemView.findViewById(R.id.iv_rowItem_dp);
            txtModelName = itemView.findViewById(R.id.txt_rowItem_model);
            txtVin = itemView.findViewById(R.id.txt_rowItem_vin);
            cardView = itemView.findViewById(R.id.cv_rowItem_cardView);

        }
    }

    public class FooterViewHolder extends RecyclerView.ViewHolder {

        TextView footerLoading;
        LinearLayout linFooter;

        public FooterViewHolder(View itemView) {
            super(itemView);

            footerLoading = (TextView) itemView.findViewById(R.id.txtLoading);
            linFooter = (LinearLayout) itemView.findViewById(R.id.lin_parent_header);
        }


    }
}
