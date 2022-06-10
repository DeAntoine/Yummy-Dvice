package yummy_dvice.com;

import android.os.Parcelable;

import java.io.Serializable;

public class Restaurant implements Serializable {

    String business_id;
    String name;
    String address;
    String city;
    String state;
    String postal_code;
    Double latitude;
    Double longitude;
    String image_id;
    float stars;

    public Restaurant(String business_id, String name, String address, String city, String state, String postal_code, Double latitude, Double longitude, float stars, String image_id){

        this.business_id = business_id;
        this.name = name;
        this.address = address;
        this.city = city;
        this.state = state;
        this.stars = stars;
        this.postal_code = postal_code;
        this.latitude = latitude;
        this.longitude = longitude;
        this.image_id = image_id;
    }
}
