package yummy_dvice.com;

import java.io.Serializable;

public class User implements Serializable {

    String user_id;
    String id_new;
    String name;
    int review_count;

    public User(String user_id, String name,int review_count, String id_new){

        this.user_id = user_id;
        this.id_new = id_new;
        this.name = name;
        this.review_count = review_count;
    }


}
