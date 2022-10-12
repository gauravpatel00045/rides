package com.rides.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.rides.BaseFragment;
import com.rides.R;
import com.rides.abstracts.EndlessRecyclerViewScrollListener;
import com.rides.activity.MainActivity;
import com.rides.adapter.AdpVehicleListRv;
import com.rides.api.ApiList;
import com.rides.api.RestClient;
import com.rides.enums.RequestCode;
import com.rides.helper.CustomDialog;
import com.rides.helper.GenericView;
import com.rides.model.VehicleModel;
import com.rides.util.Debug;

import org.json.JSONException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class VehicleListFragment extends BaseFragment {

    private AppCompatEditText edtNumber;
    private Button btnSubmit;
    private TextView txtNoRecord;
    private View rootView;

    private int totalRecords;


    private AdpVehicleListRv adpVehicleListRv;
    private RecyclerView rvMain;
    private RecyclerView.LayoutManager layoutManager;
    private List<VehicleModel> vehicleList;
    private MainActivity mainActivity;

    public VehicleListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = (MainActivity) getActivity();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_vehicle_list, container, false);
        init();


        rvMain.addOnScrollListener(new EndlessRecyclerViewScrollListener(layoutManager, mainActivity) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) throws JSONException {
                Debug.trace("page ", String.valueOf(page));
                Debug.trace("page totalItemCount ", String.valueOf(totalItemsCount));
                Debug.trace("page totalRecord ", String.valueOf(totalRecords));
                if (totalRecords < totalItemsCount) {
                    adpVehicleListRv.footerStatus(false);
                    callVehicleListApi(true);
                } else {
                    adpVehicleListRv.footerStatus(false);
                }
            }
        });

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    /**
     * To initialize the xml components
     */
    private void init() {

        rvMain = GenericView.findViewById(rootView, R.id.rv_main);
        btnSubmit = GenericView.findViewById(rootView, R.id.btn_submit);

        edtNumber = GenericView.findViewById(rootView, R.id.edt_number);
        txtNoRecord = GenericView.findViewById(rootView, R.id.txt_no_record);

        layoutManager = new LinearLayoutManager(mainActivity, LinearLayoutManager.VERTICAL, false);
        rvMain.setLayoutManager(layoutManager);

    }

    @Override
    public void onClickEvent(View view) {
        super.onClickEvent(view);
        switch (view.getId()) {
            case R.id.btn_submit:
                validateAndCallApi();
                break;
            case R.id.cv_rowItem_cardView:
                VehicleModel vehicleModel = (VehicleModel) view.getTag(R.string.strSelectedItemInfo);
                VehicleDetailsFragment detailsFragment = new VehicleDetailsFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable(getString(R.string.strSelectedItemInfo), vehicleModel);
                detailsFragment.setArguments(bundle);
                mainActivity.pushFragments(detailsFragment, true, true);

                break;

        }
    }

    /**
     * To validate the required input and then api call to get the data from the server
     *
     * @See CustomDialog#showAlert function to display custom popup.
     */
    public void validateAndCallApi() {
        if (edtNumber.getText().toString().trim().isEmpty()) {
            CustomDialog.getInstance().showAlert(mainActivity, getString(R.string.strEnterValidNumber), true, getString(R.string.strDismiss));
        } else if (!TextUtils.isDigitsOnly(edtNumber.getText().toString().trim())) {
            CustomDialog.getInstance().showAlert(mainActivity, getString(R.string.strEnterValidNumber), true, getString(R.string.strDismiss));
        } else if (Integer.parseInt(edtNumber.getText().toString().trim()) < 1 || Integer.parseInt(edtNumber.getText().toString().trim()) > 100) {
            CustomDialog.getInstance().showAlert(mainActivity, getString(R.string.strEnterValidNumber), true, getString(R.string.strDismiss));
        } else {
            callVehicleListApi(true);
        }
    }

    /**
     * To call vehicle list api with necessary parameter value
     */
    public void callVehicleListApi(boolean isDialogRequired) {
        Map<String, String> objParam = new HashMap<>();

        String size = edtNumber.getText().toString().trim();
        RestClient.getInstance().post(mainActivity, Request.Method.GET, ApiList.SERVER_URL + size, objParam, this, RequestCode.API_VEHICLE_LIST, true, isDialogRequired);

    }

    @Override
    public void onRequestComplete(RequestCode requestCode, Object object) {
        switch (requestCode) {
            case API_VEHICLE_LIST:

                vehicleList = (List<VehicleModel>) object;
                vehicleList = VehicleModel.getSortedListByVin(vehicleList);
                Debug.trace("res sorted ", vehicleList.toString());
                Debug.trace("listSize " + vehicleList.size());
                setDataInAdapter();

                break;
        }

    }

    @Override
    public void onRequestError(String error, String status, RequestCode requestCode) {
        switch (requestCode) {
            case API_VEHICLE_LIST:
                CustomDialog.getInstance().showAlert(mainActivity, error, true, getString(R.string.strDismiss));

                break;
            default:
                CustomDialog.getInstance().showAlert(mainActivity, error, true, getString(R.string.error_msg_server));
                break;
        }
    }

    /**
     * To set data in adapter
     */
    public void setDataInAdapter() {

        totalRecords = vehicleList.size();

        if (vehicleList.size() > 0) {
            txtNoRecord.setVisibility(View.GONE);
            adpVehicleListRv = (AdpVehicleListRv) rvMain.getAdapter();
            if (adpVehicleListRv != null && adpVehicleListRv.getItemCount() > 0) {
                adpVehicleListRv.refreshList(vehicleList);
            } else {
                adpVehicleListRv = new AdpVehicleListRv(mainActivity, vehicleList, false);
                rvMain.setAdapter(adpVehicleListRv);
            }

        }
    }
}
