package rf.digitworld.jobtest.ui.main;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.util.Log;

import java.util.List;

import javax.inject.Inject;

import rf.digitworld.jobtest.TestApplication;
import rf.digitworld.jobtest.data.DataManager;
import rf.digitworld.jobtest.data.model.User;
import rf.digitworld.jobtest.data.model.UserResponce;
import rf.digitworld.jobtest.ui.base.BasePresenter;
import rf.digitworld.jobtest.util.NoConnectivityException;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class UserPresenter extends BasePresenter<UserMvpView> {
    public static final String LOG_TAG="MyLogs";
    private final DataManager mDataManager;
    private Subscription mSubscription;
    final Uri CONTACT_URI = Uri
            .parse("content://rf.digitworld.jobtest.provider/users");

    final String CONTACT_NAME = "name";
    final String CONTACT_ID = "_id";

    @Inject
    public UserPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void attachView(UserMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mSubscription != null) mSubscription.unsubscribe();
    }

    public void loadUsers(final Context context) {
        checkViewAttached();
        getMvpView().showProgress();
        mSubscription = mDataManager.loadUsers(context, CONTACT_URI).asObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<User>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(LOG_TAG,"UserPresenter loadUsers onError "+ e.getMessage());
                        getMvpView().showError(e.getMessage());
                    }

                    @Override
                    public void onNext(List<User> users) {
                        if (users.isEmpty()) {
                            syncUsers(context);

                        } else {
                            getMvpView().showUsers(users);
                        }
                    }
                });
    }

    public void syncUsers(final Context context) {
        checkViewAttached();

        getMvpView().showProgress();
        mSubscription = mDataManager.syncUsers()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<UserResponce>() {
                    @Override
                    public void onCompleted() {
                        loadUsers(context);

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(LOG_TAG,"UserPresenter syncUsers onError "+ e.getMessage());
                        if(e instanceof NoConnectivityException){
                            getMvpView().showErrorToast(e.getMessage());
                            loadUsers(context);
                        }else{
                            getMvpView().showError(e.getMessage());
                        }

                    }

                    @Override
                    public void onNext(UserResponce responce) {
                        Log.d(LOG_TAG, "UserPresenter syncUsers onNext : " + responce.getResult_code());
                        if(responce.getResult_code()==0){

                            List<User> userList=responce.getUsers();

                             context.getContentResolver().delete(CONTACT_URI,null,null);
                            Log.d(LOG_TAG, "UserPresenter syncUsers onNext AfterDelete: " );
                            for (User user:userList ) {
                                Log.d(LOG_TAG, "UserPresenter syncUsers onNext User: " + user.getName());
                                ContentValues cv = new ContentValues();
                                cv.put(CONTACT_NAME, user.getName());
                                cv.put(CONTACT_ID, user.getId());
                                Uri newUri = context.getContentResolver().insert(CONTACT_URI, cv);
                                Log.d(LOG_TAG, "insert, result Uri : " + newUri.toString());
                            }

                        }else{
                            String message=responce.getMessage();
                            if (message!=null){
                                getMvpView().showError(message);
                            }else{
                                getMvpView().showError("Ошибка: "+responce.getResult_code());
                            }

                        }



                    }
                });
    }


}
