package com.drpashu.sdk.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.drpashu.app.R;
import com.drpashu.app.databinding.ItemPostReplyBinding;
import com.drpashu.app.network.ApiClient;
import com.drpashu.app.network.model.request.PostActionRequest;
import com.drpashu.app.network.model.response.FetchPostsResponse;
import com.drpashu.app.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PostReplyListAdapter extends RecyclerView.Adapter<PostReplyListAdapter.ViewHolder> {
    private Context context;
    private Activity activity;
    private Utils utils;
    private PostActionInterface postActionInterface;
    private List<FetchPostsResponse.Data> fetchPostResponse;

    public PostReplyListAdapter(Context context, Activity activity, List<FetchPostsResponse.Data> fetchPostResponse, PostActionInterface postActionInterface) {
        this.context = context;
        this.activity = activity;
        this.utils = new Utils(context, activity);
        this.fetchPostResponse = fetchPostResponse;
        this.postActionInterface = postActionInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemPostReplyBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.userNameText.setText(fetchPostResponse.get(position).getAuthor());
        holder.binding.timeText.setText(fetchPostResponse.get(position).getDate() + " " + fetchPostResponse.get(position).getTime());
        holder.binding.commentText.setText(fetchPostResponse.get(position).getText());
        holder.binding.like.setText(fetchPostResponse.get(position).getLikes()+"");
        if (fetchPostResponse.get(position).getLiked())
            holder.binding.like.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(context, R.drawable.post_liked), null, null, null);
        else
            holder.binding.like.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(context, R.drawable.post_like), null, null, null);


        if (fetchPostResponse.get(position).getProfilePicture() != null)
            Picasso.get().load(ApiClient.BASE_URL_MEDIA + fetchPostResponse.get(position).getProfilePicture()).into(holder.binding.userImg);

        holder.binding.like.setOnClickListener(v -> {
            utils.invisibleView(holder.binding.like);
            utils.visibleView(holder.binding.progressBarLike);

            PostActionRequest postActionRequest = new PostActionRequest();
            postActionRequest.setPosition(position);
            postActionRequest.setPostId(fetchPostResponse.get(position).getId());
            postActionRequest.setAction("Like");
            postActionRequest.setActionStatus(!fetchPostResponse.get(position).getLiked());
            postActionRequest.setPostAction(false);

            postActionInterface.postAction(postActionRequest);
        });
    }

    @Override
    public int getItemCount() {
        return fetchPostResponse.size();
    }

    public void updateActionOnUi(PostActionRequest postActionRequest, RecyclerView commentRecyclerView) {
        ViewHolder viewHolder = (ViewHolder) commentRecyclerView.findViewHolderForAdapterPosition(postActionRequest.getPosition());
        viewHolder.binding.like.setEnabled(true);
        utils.visibleView(viewHolder.binding.like);
        utils.hideView(viewHolder.binding.progressBarLike);

        if (postActionRequest.getApiStatus()){
            if (postActionRequest.getActionStatus()) {
                fetchPostResponse.get(postActionRequest.getPosition()).setLikes(fetchPostResponse.get(postActionRequest.getPosition()).getLikes()+1);
                viewHolder.binding.like.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(context, R.drawable.post_liked), null, null, null);
            } else {
                fetchPostResponse.get(postActionRequest.getPosition()).setLikes(fetchPostResponse.get(postActionRequest.getPosition()).getLikes()-1);
                viewHolder.binding.like.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(context, R.drawable.post_like), null, null, null);
            }

            fetchPostResponse.get(postActionRequest.getPosition()).setLiked(postActionRequest.getActionStatus());
            viewHolder.binding.like.setText(fetchPostResponse.get(postActionRequest.getPosition()).getLikes() + "");
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ItemPostReplyBinding binding;

        public ViewHolder(ItemPostReplyBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}