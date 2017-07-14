package rf.digitworld.jobtest.ui.main;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import rf.digitworld.jobtest.R;
import rf.digitworld.jobtest.ui.base.BaseActivity;

public class MainActivity extends BaseActivity implements MainEventListener {
    public static final String LOG_TAG="MyLogs";
    @Nullable
    @Bind(R.id.container)
    FrameLayout container;
    @Nullable
    @Bind(R.id.user_container)
    FrameLayout user_container;
    @Nullable
    @Bind(R.id.balance_container)
    FrameLayout balanceContainer;
    ActionBar actionBar;
    Fragment balanceFragment;
    Fragment usersFragment;
    FragmentTransaction fragmentTransaction;

    FragmentManager fm=this.getSupportFragmentManager();
    private boolean mTwoPane;

    private int activeUserId=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        ActionBar actionBar=getSupportActionBar();


        usersFragment=UsersFragment.newInstance(this);
                if (balanceContainer != null) {
                    // The detail container view will be present only in the
                    // large-screen layouts (res/values-w900dp).
                    // If this view is present, then the
                    // activity should be in two-pane mode.
                    balanceFragment = BalanceFragment.newInstance(this, activeUserId);
                    mTwoPane = true;
                    fragmentTransaction = fm.beginTransaction();
                    fragmentTransaction.replace(R.id.user_container, usersFragment);
                    fragmentTransaction.replace(R.id.balance_container, balanceFragment);
                    fragmentTransaction.commit();
                } else {

                    fragmentTransaction = fm.beginTransaction();
                    fragmentTransaction.replace(R.id.container, usersFragment);
                    fragmentTransaction.commit();
                }





    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);

        outState.putInt("userId", activeUserId);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        activeUserId = savedInstanceState.getInt("userId");

    }

    @Override
    public void onUserClick(int id, String name) {
        Log.d(LOG_TAG,"user clicked:"+id);
        activeUserId=id;
        balanceFragment=BalanceFragment.newInstance(this,id);
        fragmentTransaction=fm.beginTransaction();
        if(mTwoPane){
            fragmentTransaction.replace(R.id.balance_container, balanceFragment);

        }else{

            fragmentTransaction.replace(R.id.container, balanceFragment).addToBackStack(null);
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        }
        fragmentTransaction.commit();
    }

    /*@Override
        public void onResume() {
            super.onResume();

        }
        @Override
        public void onStop() {

            super.onStop();
        }

        */
    @Override
    protected void onDestroy() {

        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if(mTwoPane){
            finish();
        }else{

            super.onBackPressed();
        }

    }


}
