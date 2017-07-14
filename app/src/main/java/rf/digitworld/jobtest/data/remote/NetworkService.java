package rf.digitworld.jobtest.data.remote;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.RxJavaCallAdapterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rf.digitworld.jobtest.data.model.BalanceResponce;
import rf.digitworld.jobtest.data.model.UserResponce;
import rf.digitworld.jobtest.util.ConnectivityInterceptor;
import rx.Observable;

public interface NetworkService {

    String ENDPOINT = "https://w.qiwi.com/mobile/testtask/";

    @GET("index.json")
    Observable<UserResponce> getUserList();
    @GET("users/{id}/index.json")
    Observable<BalanceResponce> getUserBalances(@Path("id") int id );

    /******** Helper class that sets up a new services *******/
    class Creator {

        public static NetworkService newNetworkService(Context context) {
            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                    .create();
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(new ConnectivityInterceptor(context))
                    .build();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(NetworkService.ENDPOINT)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
            return retrofit.create(NetworkService.class);
        }
    }
}
