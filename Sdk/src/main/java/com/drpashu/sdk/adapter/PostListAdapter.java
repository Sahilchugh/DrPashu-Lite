package com.drpashu.sdk.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.drpashu.app.R;
import com.drpashu.app.databinding.ItemPostBinding;
import com.drpashu.app.network.ApiClient;
import com.drpashu.app.network.model.request.PostActionRequest;
import com.drpashu.app.network.model.response.FetchPostsResponse;
import com.drpashu.app.utils.Utils;
import com.google.android.exoplayer2.ExoPlayer;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class PostListAdapter extends RecyclerView.Adapter<PostListAdapter.ViewHolder> {
    private Context context;
    private Activity activity;
    private Utils utils;
    private ExoPlayer inputPlayer;
    private PostActionInterface postActionInterface;
    private List<FetchPostsResponse.Data> fetchPostResponse;
    private List<ExoPlayer> exoPlayerList = new ArrayList<>();

    public PostListAdapter(Context context, Activity activity, List<FetchPostsResponse.Data> fetchPostResponse, PostActionInterface postActionInterface) {
        this.context = context;
        this.activity = activity;
        this.utils = new Utils(context, activity);
        this.fetchPostResponse = fetchPostResponse;
        this.postActionInterface = postActionInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemPostBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.userNameText.setText(fetchPostResponse.get(position).getAuthor());
        holder.binding.timeText.setText(fetchPostResponse.get(position).getDate() + " " + fetchPostResponse.get(position).getTime());
        holder.binding.titleText.setText(fetchPostResponse.get(position).getTitle());
        holder.binding.like.setText(fetchPostResponse.get(position).getLikes().toString());
        holder.binding.dislike.setText(fetchPostResponse.get(position).getDislikes().toString());
        holder.binding.comment.setText(fetchPostResponse.get(position).getReplies().toString());

        final SpannableString spannableString = new SpannableString(fetchPostResponse.get(position).getText());
        Linkify.addLinks(spannableString, Linkify.WEB_URLS);
        holder.binding.messageText.setText(spannableString);
        holder.binding.messageText.setMovementMethod(LinkMovementMethod.getInstance());

        try {
            if (fetchPostResponse.get(position).getLiked())
                holder.binding.like.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(context, R.drawable.post_liked), null, null, null);
            else
                holder.binding.like.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(context, R.drawable.post_like), null, null, null);

            if (fetchPostResponse.get(position).getDisliked())
                holder.binding.dislike.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(context, R.drawable.post_disliked), null, null, null);
            else
                holder.binding.dislike.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(context, R.drawable.post_dislike), null, null, null);
        } catch (Exception e){

        }

        if (fetchPostResponse.get(position).getProfilePicture() != null)
            Picasso.get().load(ApiClient.BASE_URL_MEDIA + fetchPostResponse.get(position).getProfilePicture()).into(holder.binding.userImg);

        if (fetchPostResponse.get(position).getImage() != null){
            holder.binding.postImg.setVisibility(View.VISIBLE);
            holder.binding.postVideo.setVisibility(View.GONE);
            Picasso.get().load(ApiClient.BASE_URL_MEDIA + fetchPostResponse.get(position).getImage()).into(holder.binding.postImg);
        }

        if (fetchPostResponse.get(position).getVideo() != null){
            holder.binding.postVideo.setVisibility(View.VISIBLE);
            holder.binding.postImg.setVisibility(View.GONE);

            exoPlayerList.add(utils.playMediaFromUrl(ApiClient.BASE_URL_MEDIA + fetchPostResponse.get(position).getVideo(), holder.binding.postVideo, false));
        }

        if (fetchPostResponse.get(position).getImage() == null && fetchPostResponse.get(position).getVideo() == null){
            holder.binding.postVideo.setVisibility(View.GONE);
            holder.binding.postImg.setVisibility(View.GONE);
        }

        holder.binding.like.setOnClickListener(v -> {
            utils.invisibleView(holder.binding.like);
            utils.visibleView(holder.binding.progressBarLike);
            holder.binding.dislike.setEnabled(false);

            PostActionRequest postActionRequest = new PostActionRequest();
            postActionRequest.setPosition(position);
            postActionRequest.setPostId(fetchPostResponse.get(position).getId());
            postActionRequest.setAction("Like");
            postActionRequest.setActionStatus(!fetchPostResponse.get(position).getLiked());

            postActionInterface.postAction(postActionRequest);
        });

        holder.binding.dislike.setOnClickListener(v -> {
            utils.invisibleView(holder.binding.dislike);
            utils.visibleView(holder.binding.progressBarDisLike);
            holder.binding.like.setEnabled(false);

            PostActionRequest postActionRequest = new PostActionRequest();
            postActionRequest.setPosition(position);
            postActionRequest.setPostId(fetchPostResponse.get(position).getId());
            postActionRequest.setAction("Dislike");
            postActionRequest.setActionStatus(!fetchPostResponse.get(position).getDisliked());

            postActionInterface.postAction(postActionRequest);
        });

        holder.binding.clickableView.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putParcelable("postData", (Parcelable) fetchPostResponse.get(position));
            Navigation.findNavController(v).navigate(R.id.action_nav_dashboard_to_replyFragment, bundle);
        });

        holder.binding.comment.setOnClickListener(v -> holder.binding.clickableView.performClick());

        holder.binding.postVideo.setOnClickListener(v -> {
            pausePlayer();

            inputPlayer = (ExoPlayer) holder.binding.postVideo.getPlayer();
//            inputPlayer = utils.playMediaFromUrl(ApiClient.BASE_URL_MEDIA + fetchPostResponse.get(position).getVideo(), holder.binding.postVideo, false);
        });

        holder.binding.optionImg.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(context, v);
            popupMenu.inflate(R.menu.post_menu);
            popupMenu.show();

            popupMenu.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()) {
                    case R.id.option_report:
                        reportConfirmationDialog(position);
                        return true;
                }
                return false;
            });
        });
    }

    private void reportConfirmationDialog(int position) {
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle(utils.getStringValue(R.string.report_this_post));
        alert.setMessage(utils.getStringValue(R.string.report_msg))
                .setPositiveButton(utils.getStringValue(R.string.report), (dialog, which) -> {
                    PostActionRequest postActionRequest = new PostActionRequest();
                    postActionRequest.setPosition(position);
                    postActionRequest.setPostId(fetchPostResponse.get(position).getId());
                    postActionRequest.setAction("Block");
                    postActionRequest.setActionStatus(true);

                    postActionInterface.postAction(postActionRequest);
                })
                .setNegativeButton(utils.getStringValue(R.string.cancel), null);
        alert.show();
    }

    @Override
    public int getItemCount() {
        return fetchPostResponse.size();
    }

    public void updateActionOnUi(PostActionRequest postActionRequest, RecyclerView recyclerView) {
//        recyclerView.findViewHolderForAdapterPosition(postActionRequest.getPosition()).itemView.findViewById(R.id.progressBarLike).setVisibility(View.GONE);

        ViewHolder viewHolder = (ViewHolder) recyclerView.findViewHolderForAdapterPosition(postActionRequest.getPosition());
        viewHolder.binding.like.setEnabled(true);
        viewHolder.binding.dislike.setEnabled(true);

        if (postActionRequest.getApiStatus()) {
            if (postActionRequest.getAction().equalsIgnoreCase("Like")) {
                utils.visibleView(viewHolder.binding.like);
                utils.hideView(viewHolder.binding.progressBarLike);
                if (postActionRequest.getActionStatus()) {
                    fetchPostResponse.get(postActionRequest.getPosition()).setLikes(fetchPostResponse.get(postActionRequest.getPosition()).getLikes()+1);
                    viewHolder.binding.like.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(context, R.drawable.post_liked), null, null, null);
                } else {
                    fetchPostResponse.get(postActionRequest.getPosition()).setLikes(fetchPostResponse.get(postActionRequest.getPosition()).getLikes()-1);
                    viewHolder.binding.like.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(context, R.drawable.post_like), null, null, null);
                }

                fetchPostResponse.get(postActionRequest.getPosition()).setLiked(postActionRequest.getActionStatus());
                viewHolder.binding.like.setText(fetchPostResponse.get(postActionRequest.getPosition()).getLikes() + "");

                if (fetchPostResponse.get(postActionRequest.getPosition()).getDisliked()){
                    fetchPostResponse.get(postActionRequest.getPosition()).setDislikes(fetchPostResponse.get(postActionRequest.getPosition()).getDislikes()-1);
                    viewHolder.binding.dislike.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(context, R.drawable.post_dislike), null, null, null);

                    fetchPostResponse.get(postActionRequest.getPosition()).setDisliked(false);
                    viewHolder.binding.dislike.setText(fetchPostResponse.get(postActionRequest.getPosition()).getDislikes() + "");
                }
            }
            else if(postActionRequest.getAction().equalsIgnoreCase("Dislike")) {
                utils.visibleView(viewHolder.binding.dislike);
                utils.hideView(viewHolder.binding.progressBarDisLike);

                if (postActionRequest.getActionStatus()) {
                    fetchPostResponse.get(postActionRequest.getPosition()).setDislikes(fetchPostResponse.get(postActionRequest.getPosition()).getDislikes()+1);
                    viewHolder.binding.dislike.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(context, R.drawable.post_disliked), null, null, null);
                } else {
                    fetchPostResponse.get(postActionRequest.getPosition()).setDislikes(fetchPostResponse.get(postActionRequest.getPosition()).getDislikes()-1);
                    viewHolder.binding.dislike.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(context, R.drawable.post_dislike), null, null, null);
                }

                fetchPostResponse.get(postActionRequest.getPosition()).setDisliked(postActionRequest.getActionStatus());
                viewHolder.binding.dislike.setText(fetchPostResponse.get(postActionRequest.getPosition()).getDislikes() + "");

                if (fetchPostResponse.get(postActionRequest.getPosition()).getLiked()){
                    fetchPostResponse.get(postActionRequest.getPosition()).setLikes(fetchPostResponse.get(postActionRequest.getPosition()).getLikes()-1);
                    viewHolder.binding.like.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(context, R.drawable.post_like), null, null, null);

                    fetchPostResponse.get(postActionRequest.getPosition()).setLiked(false);
                    viewHolder.binding.like.setText(fetchPostResponse.get(postActionRequest.getPosition()).getLikes() + "");
                }
            }
        } else {
            if (postActionRequest.getAction().equalsIgnoreCase("Like")){
                utils.visibleView(viewHolder.binding.like);
                utils.hideView(viewHolder.binding.progressBarLike);
            } else if(postActionRequest.getAction().equalsIgnoreCase("Dislike")){
                utils.visibleView(viewHolder.binding.dislike);
                utils.hideView(viewHolder.binding.progressBarDisLike);
            }
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ItemPostBinding binding;

        public ViewHolder(ItemPostBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public void addPlayer() {
        if (exoPlayerList.size() != 0) {
            for (int i=0; i<exoPlayerList.size();i++) {
                if (exoPlayerList.get(i) != null)
                    exoPlayerList.get(i).prepare();
            }
        }
    }

    public void releasePlayer(){
        if (exoPlayerList.size() != 0) {
            for (int i=0; i<exoPlayerList.size();i++) {
                if (exoPlayerList.get(i) != null)
                    exoPlayerList.get(i).release();
            }
        }
//        if (inputPlayer != null){
//            inputPlayer.release();
//            inputPlayer = null;
//        }
    }

    public void pausePlayer(){
        if (inputPlayer != null)
            inputPlayer.pause();
    }
}