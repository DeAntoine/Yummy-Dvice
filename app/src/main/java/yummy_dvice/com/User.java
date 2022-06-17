package yummy_dvice.com;

import android.widget.Toast;

import java.io.Serializable;

public class User implements Serializable {

    String user_id;
    String id_new;
    String name;
    int review_count;
    String favorites;

    public User(String user_id, String name,int review_count, String id_new, String favorites){

        this.user_id = user_id;
        this.id_new = id_new;
        this.name = name;
        this.review_count = review_count;
        this.favorites = favorites;
    }


    public void addReview(){

        this.review_count = this.review_count + 1;
    }


}
