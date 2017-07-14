package rf.digitworld.jobtest.data;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rf.digitworld.jobtest.data.local.Db;
import rf.digitworld.jobtest.data.model.BalanceResponce;
import rf.digitworld.jobtest.data.model.User;
import rf.digitworld.jobtest.data.model.UserResponce;
import rf.digitworld.jobtest.data.remote.NetworkService;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action0;
import rf.digitworld.jobtest.util.EventPosterHelper;

@Singleton
public class DataManager {

    private final NetworkService mNetworkService;

    @Inject
    public DataManager(NetworkService networkService) {
        mNetworkService = networkService;
    }



    public Observable<UserResponce> syncUsers() {
        return mNetworkService.getUserList();
    }
    public Observable<List<User>> loadUsers(final Context context, final Uri uri) {
        return Observable.create(new Observable.OnSubscribe<List<User>>() {
            @Override
            public void call(Subscriber<? super List<User>> subscriber) {
                if(!subscriber.isUnsubscribed()){
                    Cursor cursor=null;
                    ContentResolver cr=context.getContentResolver();
                    List<User> userlist=new ArrayList<User>();
                    try{
                        cursor=cr.query(uri,null,null,null, null);
                        if (cursor != null && cursor.getCount() > 0) {
                            while (cursor.moveToNext()) {
                                userlist.add(Db.UserTable.parseCursor(cursor));
                            }
                        }
                        subscriber.onNext(userlist);
                        subscriber.onCompleted();
                    }catch (Exception ex){
                        subscriber.onError(ex);
                    }finally {
                        if (cursor!=null) cursor.close();
                    }
                }
            }
        });
    }
    public Observable<BalanceResponce> syncBalances(int id) {
        return mNetworkService.getUserBalances(id);

    }






}
