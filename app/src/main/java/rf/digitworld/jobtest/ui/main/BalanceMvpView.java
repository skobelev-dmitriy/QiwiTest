package rf.digitworld.jobtest.ui.main;

import java.util.List;

import rf.digitworld.jobtest.data.model.BalanceResponce;
import rf.digitworld.jobtest.data.model.UserResponce;
import rf.digitworld.jobtest.ui.base.MvpView;

public interface BalanceMvpView extends MvpView {

    void showBalances(List<BalanceResponce.Balance> balances);
    void onUserClick(int id);
    void showError(String error);
    void showError();
    void showProgress();

}
