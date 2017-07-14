package rf.digitworld.jobtest.data.model;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

import java.util.List;

public class UserResponce {
    @Expose
    private int result_code;
    @Expose
    private String message;
    @Expose
    private List<User> users;

    public UserResponce() {
    }

    public int getResult_code() {
        return result_code;
    }

    public List<User> getUsers() {
        return users;
    }

    public String getMessage() {
        return message;
    }



}
