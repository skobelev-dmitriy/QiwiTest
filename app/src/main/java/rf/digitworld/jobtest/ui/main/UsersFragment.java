package rf.digitworld.jobtest.ui.main;


import android.content.Context;
import android.os.Bundle;
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
import android.widget.Toast;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import rf.digitworld.jobtest.R;
import rf.digitworld.jobtest.data.model.User;
import rf.digitworld.jobtest.ui.base.BaseFragment;
import rf.digitworld.jobtest.util.SimpleDividerItemDecoration;

public class UsersFragment extends BaseFragment implements UserMvpView {
    public static final String LOG_TAG="MyLogs";
    @Inject
    UserPresenter presenter;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    @Bind(R.id.text_error)
    TextView text_error;
    @Bind(R.id.button)
    Button button_retry;
    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;
    UsersAdapter adapter;
    MainEventListener clickListener;
    Context context;
    int selectedId;
    public static Fragment newInstance(Context context) {
        Bundle b = new Bundle();

        return Fragment.instantiate(context, UsersFragment.class.getName(), b);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }



        View view  = inflater.inflate(R.layout.fragment_users, container, false);
        ButterKnife.bind(this, view);
        context=getContext();

        adapter=new UsersAdapter();

        adapter.setListener(new UsersAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(User item) {
                clickListener.onUserClick(item.getId(), item.getName());
            }

            @Override
            public void onRetryClick() {
                presenter.syncUsers(context);
            }
        });
        LinearLayoutManager llm = new LinearLayoutManager(context);
        llm.setAutoMeasureEnabled(false);
        recyclerView.setLayoutManager(llm);
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));
        recyclerView.setAdapter(adapter);

        presenter.loadUsers(context);

        button_retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(LOG_TAG,"UsersFragment onButtonClick");
                presenter.syncUsers(context);
            }
        });
        return view;
    }

    @Override
    public void showUsers(List<User> users) {
        Log.d(LOG_TAG,"UserFragment showUsers: "+users.size()+" items");
        recyclerView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        text_error.setVisibility(View.GONE);
        button_retry.setVisibility(View.GONE);
        adapter.setUsers(users);


    }

    @Override
    public void showError(String error) {
        Log.d(LOG_TAG,"UserFragment showError: "+error);
        recyclerView.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        text_error.setVisibility(View.VISIBLE);
        button_retry.setVisibility(View.VISIBLE);
        text_error.setText(error);
    }

    @Override
    public void showErrorToast(String Toast) {
        Log.d(LOG_TAG,"UserFragment showError");
        recyclerView.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        text_error.setVisibility(View.GONE);
        button_retry.setVisibility(View.GONE);
        android.widget.Toast.makeText(context, "Отсутствует подключение к интернету. Данные показаны из памяти устройства", android.widget.Toast.LENGTH_LONG).show();
    }

    @Override
    public void showProgress() {
        Log.d(LOG_TAG,"UserFragment showProgres");
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
        try{
            clickListener=(MainEventListener)getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString() + " must implement MainEventListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        presenter.detachView();
    }
}
