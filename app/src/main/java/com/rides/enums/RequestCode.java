package com.rides.enums;



import com.rides.model.VehicleModel;

import java.lang.reflect.Type;


public enum RequestCode {

    API_VEHICLE_LIST(VehicleModel[].class);



    private Class<?> localClass = null;
    private Type localType = null;

    RequestCode(Class<?> localClass) {
        this.localClass = localClass;
    }

     RequestCode(Type localType) {
        this.localType = localType;
        this.localClass = localType.getClass();
    }

    public Class<?> getLocalClass() {
        return localClass;
    }

    public Type getLocalType() {
        return localType;
    }
}
