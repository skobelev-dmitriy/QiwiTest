package rf.digitworld.jobtest.ui.main;

import javax.inject.Inject;

import io.realm.Realm;
import rf.digitworld.jobtest.data.DataManager;
import rf.digitworld.jobtest.data.model.BalanceResponce;
import rf.digitworld.jobtest.ui.base.BasePresenter;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class BalancePresenter extends BasePresenter<BalanceMvpView> {

    private final DataManager mDataManager;
    private Subscription mSubscription;

    final Realm realm = Realm.getDefaultInstance();

    @Inject
    public BalancePresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void attachView(BalanceMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mSubscription != null) mSubscription.unsubscribe();
    }


    public void syncBalances(int id) {
        checkViewAttached();
        getMvpView().showProgress();
        mSubscription = mDataManager.syncBalances(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<BalanceResponce>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                        getMvpView().showError(e.getMessage());
                    }

                    @Override
                    public void onNext(BalanceResponce responce) {
                        if(responce.getResult_code()==0){
                            getMvpView().showBalances(responce.getBalances());
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
