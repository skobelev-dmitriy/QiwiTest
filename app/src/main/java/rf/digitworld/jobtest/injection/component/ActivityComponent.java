package rf.digitworld.jobtest.injection.component;

import android.app.Activity;

import dagger.Component;
import rf.digitworld.jobtest.injection.PerActivity;
import rf.digitworld.jobtest.injection.module.ActivityModule;
import rf.digitworld.jobtest.ui.main.BalanceFragment;
import rf.digitworld.jobtest.ui.main.MainActivity;
import rf.digitworld.jobtest.ui.main.UsersFragment;

/**
 * This component inject dependencies to all Activities across the application
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(MainActivity mainActivity);
    void injectFragment(UsersFragment fragment);
    void injectFragment(BalanceFragment fragment);

}
