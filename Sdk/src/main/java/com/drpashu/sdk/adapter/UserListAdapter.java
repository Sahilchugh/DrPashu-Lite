package com.drpashu.sdk.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.drpashu.app.R;
import com.drpashu.app.databinding.ItemUserBinding;
import com.drpashu.app.network.model.response.UserSearchResponse;

import java.util.List;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.ViewHolder> {
    private Context context;
    private Activity activity;
    private UserSelectionInterface userSelectionInterface;
    private List<UserSearchResponse.Data> userSearchList;

    public UserListAdapter(Context context, Activity activity, List<UserSearchResponse.Data> userSearchList, UserSelectionInterface userSelectionInterface) {
        this.context = context;
        this.activity = activity;
        this.userSearchList = userSearchList;
        this.userSelectionInterface = userSelectionInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemUserBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (userSearchList.get(position).getUserRole() == 0)
            userSearchList.get(position).setUserRoleValue(context.getResources().getString(R.string.role_farmer));
        else if (userSearchList.get(position).getUserRole() == 1)
            userSearchList.get(position).setUserRoleValue(context.getResources().getString(R.string.role_vle));
        else if (userSearchList.get(position).getUserRole() == 2)
            userSearchList.get(position).setUserRoleValue(context.getResources().getString(R.string.role_vet));
        else if (userSearchList.get(position).getUserRole() == 3)
            userSearchList.get(position).setUserRoleValue(context.getResources().getString(R.string.role_admin));

        holder.binding.nameTextView.setText(userSearchList.get(position).getName() + " (" + userSearchList.get(position).getUserRoleValue() + ")");
        holder.binding.emailTextView.setText(userSearchList.get(position).getEmail());
        holder.binding.numberTextView.setText(userSearchList.get(position).getPhone());

        holder.itemView.setOnClickListener(v-> userSelectionInterface.userSelection(userSearchList.get(position).getId()));
    }

    @Override
    public int getItemCount() {
        return userSearchList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ItemUserBinding binding;

        public ViewHolder(ItemUserBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}