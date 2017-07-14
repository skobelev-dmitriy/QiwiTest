package rf.digitworld.jobtest.ui.main;

import android.graphics.Color;
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
import rf.digitworld.jobtest.data.model.User;
import rf.digitworld.jobtest.data.model.UserResponce;

public class UsersAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private  OnItemClickListener listener;
     public interface OnItemClickListener {
        void onItemClick(User item);
         void onRetryClick();
    }
    private List<User> userList;
    private int selected_position=-1;
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    @Inject
    public UsersAdapter() {
        userList = new ArrayList<>();
    }

    public void setUsers(List<User> users) {
        userList = users;
    }
    public void setSelected_position(int id){

        notifyItemChanged(selected_position);
        selected_position=id+1;
        notifyItemChanged(selected_position);
    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        if (viewType == TYPE_ITEM) {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_user, parent, false);
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
            final User user = userList.get(position-1);
            ((ItemViewHolder)holder).bind(user, listener);

            if(selected_position == position){
                holder.itemView.setBackgroundColor(Color.BLUE);
            }else{
                holder.itemView.setBackgroundColor(Color.TRANSPARENT);
            }
            ((ItemViewHolder)holder).nameTextView.setText(String.format("%s",
                    user.getName()));


            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // Updating old as well as new positions

                    notifyItemChanged(selected_position);
                    selected_position = position;
                    notifyItemChanged(selected_position);
                    listener.onItemClick(user);
                    // Do your another stuff for your onClick
                }
            });

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
        return userList.size()+1;
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.text_name) TextView nameTextView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
        public void bind(final User item, final OnItemClickListener listener) {

           /* itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    //listener.onItemClick(item);

                }
            });*/
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
