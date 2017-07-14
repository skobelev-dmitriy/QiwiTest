package rf.digitworld.jobtest.ui.main;

import java.util.List;

import rf.digitworld.jobtest.data.model.BalanceResponce;
import rf.digitworld.jobtest.data.model.User;
import rf.digitworld.jobtest.data.model.UserResponce;
import rf.digitworld.jobtest.ui.base.MvpView;

public interface UserMvpView extends MvpView {

    void showUsers(List<User> users);
    void showError(String error);
    void showErrorToast(String error);
    void showProgress();

}
