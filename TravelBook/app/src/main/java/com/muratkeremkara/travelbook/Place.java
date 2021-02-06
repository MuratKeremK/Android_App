package com.muratkeremkara.travelbook;

import java.io.Serializable;

public class Place implements Serializable {

    String name;
    Double latitude;
    Double longitude;

    public  Place (String name, Double latitude, Double longitude){
        this.name=name;
        this.latitude=latitude;
        this.longitude=longitude;

    }
}
