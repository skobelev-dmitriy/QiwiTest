package rf.digitworld.jobtest.ui.main;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import rf.digitworld.jobtest.R;
import rf.digitworld.jobtest.data.model.BalanceResponce;
import rf.digitworld.jobtest.ui.base.BaseFragment;
import rf.digitworld.jobtest.util.SimpleDividerItemDecoration;

public class BalanceFragment extends BaseFragment implements BalanceMvpView {
    public static final String LOG_TAG="MyLogs";
    @Inject
    BalancePresenter presenter;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    @Bind(R.id.text_error)
    TextView text_error;
    @Bind(R.id.button)
    Button button_retry;
    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;
    BalanceAdapter adapter;
    int id;
    public static Fragment newInstance(Context context,int id) {
        Bundle b = new Bundle();
        b.putInt("id",id);
        return Fragment.instantiate(context, BalanceFragment.class.getName(), b);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }



        View view  = inflater.inflate(R.layout.fragment_balances, container, false);
        ButterKnife.bind(this, view);
        adapter=new BalanceAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));
        recyclerView.setAdapter(adapter);
        adapter.setListener(new BalanceAdapter.OnItemClickListener() {


            @Override
            public void onRetryClick() {
                presenter.syncBalances(id);
            }
        });
        id=getArguments().getInt("id",-1);
        if(id!=-1){
            presenter.syncBalances(id);
        }else{
            text_error.setText(getString(R.string.choose_user));
        }
        button_retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(LOG_TAG,"BalanceFragment onButtonClick");
                //presenter.syncBalances(id);
            }
        });

        return view;
    }

    @Override
    public void onUserClick(int id) {
        this.id=id;
        presenter.syncBalances(id);
    }

    @Override
    public void showBalances(List<BalanceResponce.Balance> balances) {
        recyclerView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        text_error.setVisibility(View.GONE);
        button_retry.setVisibility(View.GONE);
        adapter.setBalances(balances);
    }

    @Override
    public void showError(String error) {
        recyclerView.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        text_error.setVisibility(View.VISIBLE);
        button_retry.setVisibility(View.VISIBLE);
        text_error.setText(error);
    }

    @Override
    public void showError() {
        recyclerView.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        text_error.setVisibility(View.VISIBLE);
        button_retry.setVisibility(View.VISIBLE);
    }

    @Override
    public void showProgress() {
        recyclerView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        text_error.setVisibility(View.GONE);
        button_retry.setVisibility(View.GONE);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        fragmentComponent().injectFragment(this);
        presenter.attachView(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        presenter.detachView();
    }
}
