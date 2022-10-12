package com.rides.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rides.BaseFragment;
import com.rides.R;
import com.rides.activity.MainActivity;
import com.rides.helper.GenericView;
import com.rides.model.VehicleModel;
import com.rides.util.Constants;


public class VehicleDetailsFragment extends BaseFragment {

    // xml component declaration
    private View rootView;
    private ImageView ivCarPhoto;
    private TextView txtVehicleMake, txtVin, txtColor, txtCarType, txtKmRag, txtEmission;

    private MainActivity mainActivity;

    // class object declaration
    VehicleModel vehicleModel = new VehicleModel();


    public VehicleDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = (MainActivity) getActivity();
        vehicleModel = (VehicleModel) getArguments().getSerializable(getString(R.string.strSelectedItemInfo));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_vehicle_details, container, false);

        init();

        return rootView;
    }

    /**
     * To initialize xml component
     */
    private void init() {
        ivCarPhoto = GenericView.findViewById(rootView, R.id.iv_car_photo);
        txtVehicleMake = GenericView.findViewById(rootView, R.id.txt_vehicle_make);
        txtVin = GenericView.findViewById(rootView, R.id.txt_vin);
        txtColor = GenericView.findViewById(rootView, R.id.txt_color);
        txtCarType = GenericView.findViewById(rootView, R.id.txt_carType);
        txtKmRag = GenericView.findViewById(rootView, R.id.txt_kmrag);
        txtEmission = GenericView.findViewById(rootView, R.id.txt_emission);

        displayDetails();
    }

    /**
     * To display the detail information
     */
    private void displayDetails() {
        txtVehicleMake.setText(vehicleModel.getMakeAndModel());
        txtVin.setText(vehicleModel.getVin());
        txtColor.setText(vehicleModel.getColor());
        txtCarType.setText(vehicleModel.getCarType());
        txtKmRag.setText(String.valueOf(vehicleModel.getKilometrage()));
        txtEmission.setText(calculateEmission());
    }

    private String calculateEmission() {
        Double totalEmission = 0.0;
        Integer km = 0;
        vehicleModel.getKilometrage();
        if (vehicleModel.getKilometrage() > 0) {
            if (vehicleModel.getKilometrage() <= 5000) {
                totalEmission = vehicleModel.getKilometrage() * Constants.FIRST_5000KM;
            } else {
                km = vehicleModel.getKilometrage() - 5000;
                totalEmission = (km * Constants.AFTER_FIRST_5000KM) + 5000;

            }
        }
        return String.valueOf(totalEmission);
    }
}