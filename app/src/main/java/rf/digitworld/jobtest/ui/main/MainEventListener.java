package rf.digitworld.jobtest.ui.main;

import rf.digitworld.jobtest.ui.base.MvpView;

public interface MainEventListener extends MvpView {

    void onUserClick(int id, String name);

}
