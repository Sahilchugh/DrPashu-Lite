package com.drpashu.sdk.network.model.request;

public class PostActionRequest {
    private int position, postId;
    private String action;
    private Boolean actionStatus, apiStatus, postAction;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Boolean getActionStatus() {
        return actionStatus;
    }

    public void setActionStatus(Boolean actionStatus) {
        this.actionStatus = actionStatus;
    }

    public Boolean getApiStatus() {
        return apiStatus;
    }

    public void setApiStatus(Boolean apiStatus) {
        this.apiStatus = apiStatus;
    }

    public Boolean getPostAction() {
        return postAction;
    }

    public void setPostAction(Boolean postAction) {
        this.postAction = postAction;
    }
}