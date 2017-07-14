package rf.digitworld.jobtest.ui.main;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import rf.digitworld.jobtest.R;
import rf.digitworld.jobtest.data.model.BalanceResponce;

public class BalanceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<BalanceResponce.Balance> balanceList;
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private  OnItemClickListener listener;
    public interface OnItemClickListener {

        void onRetryClick();
    }

    @Inject
    public BalanceAdapter() {
        balanceList = new ArrayList<>();
    }

    public void setBalances(List<BalanceResponce.Balance> balances) {
        balanceList = balances;
        notifyDataSetChanged();
    }
    public void setListener(BalanceAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        if (viewType == TYPE_ITEM) {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_balance, parent, false);
            return new ItemViewHolder(itemView);
        } else if (viewType == TYPE_HEADER) {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recycler_header, parent, false);
            return new HeaderViewHolder(itemView);
        }

        throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if(holder instanceof ItemViewHolder){
            BalanceResponce.Balance balance = balanceList.get(position-1);
            ((ItemViewHolder)holder).nameTextView.setText(String.format("%s",
                    balance.getCurrencyString()));
        }else if (holder instanceof HeaderViewHolder){
            ((HeaderViewHolder) holder).button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onRetryClick();
                }
            });
        }
    }
    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position))
            return TYPE_HEADER;

        return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }
    @Override
    public int getItemCount() {
        return balanceList.size()+1;
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.text_name) TextView nameTextView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }
     class HeaderViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.button_retry)
        Button button;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }
}
