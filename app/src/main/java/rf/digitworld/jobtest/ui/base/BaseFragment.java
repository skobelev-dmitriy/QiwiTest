package rf.digitworld.jobtest.ui.base;

import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;

import rf.digitworld.jobtest.TestApplication;
import rf.digitworld.jobtest.injection.component.ActivityComponent;
import rf.digitworld.jobtest.injection.component.DaggerActivityComponent;
import rf.digitworld.jobtest.injection.module.ActivityModule;

/**
 * Created by Дмитрий on 13.07.2016.
 */
public class BaseFragment extends Fragment {
    private ActivityComponent mActivityComponent;
    public ActivityComponent fragmentComponent() {
        if (mActivityComponent == null) {
            mActivityComponent = DaggerActivityComponent.builder()
                    .activityModule(new ActivityModule(getActivity()))
                    .applicationComponent(TestApplication.get(getActivity()).getComponent())
                    .build();
        }
        return mActivityComponent;
    }


}
