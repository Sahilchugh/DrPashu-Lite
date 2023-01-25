package com.drpashu.sdk.network.model.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FetchPostsResponse {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<Data> data;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public class Data implements Parcelable {
        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("author")
        @Expose
        private String author;
        @SerializedName("profile_picture")
        @Expose
        private String profilePicture;
        @SerializedName("text")
        @Expose
        private String text;
        @SerializedName("video")
        @Expose
        private String video;
        @SerializedName("replies")
        @Expose
        private Integer replies;
        @SerializedName("likes")
        @Expose
        private Integer likes;
        @SerializedName("dislikes")
        @Expose
        private Integer dislikes;
        @SerializedName("is_purchase")
        @Expose
        private Object isPurchase;
        @SerializedName("is_blocked")
        @Expose
        private Boolean isBlocked;
        @SerializedName("date")
        @Expose
        private String date;
        @SerializedName("time")
        @Expose
        private String time;
        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("is_liked")
        @Expose
        private Boolean isLiked;
        @SerializedName("is_disliked")
        @Expose
        private Boolean isDisliked;

        protected Data(Parcel in) {
            id = ((Integer) in.readValue((Integer.class.getClassLoader())));
            author = ((String) in.readValue((String.class.getClassLoader())));
            profilePicture = ((String) in.readValue((String.class.getClassLoader())));
            text = ((String) in.readValue((String.class.getClassLoader())));
            video = ((String) in.readValue((String.class.getClassLoader())));
            replies = ((Integer) in.readValue((Integer.class.getClassLoader())));
            likes = ((Integer) in.readValue((Integer.class.getClassLoader())));
            dislikes = ((Integer) in.readValue((Integer.class.getClassLoader())));
            isPurchase = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
            isBlocked = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
            date = ((String) in.readValue((String.class.getClassLoader())));
            time = ((String) in.readValue((String.class.getClassLoader())));
            isLiked = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
            isDisliked = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        }

        public final Creator<Data> CREATOR = new Creator<Data>() {
            public Data createFromParcel(Parcel in) {
                return new Data(in);
            }

            public Data[] newArray(int size) {
                return (new Data[size]);
            }
        };

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getProfilePicture() {
            return profilePicture;
        }

        public void setProfilePicture(String profilePicture) {
            this.profilePicture = profilePicture;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public Integer getReplies() {
            return replies;
        }

        public void setReplies(Integer replies) {
            this.replies = replies;
        }

        public Integer getLikes() {
            return likes;
        }

        public void setLikes(Integer likes) {
            this.likes = likes;
        }

        public Integer getDislikes() {
            return dislikes;
        }

        public void setDislikes(Integer dislikes) {
            this.dislikes = dislikes;
        }

        public Object getIsPurchase() {
            return isPurchase;
        }

        public void setIsPurchase(Object isPurchase) {
            this.isPurchase = isPurchase;
        }

        public Boolean getIsBlocked() {
            return isBlocked;
        }

        public void setIsBlocked(Boolean isBlocked) {
            this.isBlocked = isBlocked;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public Boolean getBlocked() {
            return isBlocked;
        }

        public void setBlocked(Boolean blocked) {
            isBlocked = blocked;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getVideo() {
            return video;
        }

        public void setVideo(String video) {
            this.video = video;
        }

        public Boolean getLiked() {
            return isLiked;
        }

        public void setLiked(Boolean liked) {
            isLiked = liked;
        }

        public Boolean getDisliked() {
            return isDisliked;
        }

        public void setDisliked(Boolean disliked) {
            isDisliked = disliked;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeValue(id);
            dest.writeValue(author);
            dest.writeValue(profilePicture);
            dest.writeValue(text);
            dest.writeValue(video);
            dest.writeValue(replies);
            dest.writeValue(likes);
            dest.writeValue(dislikes);
            dest.writeValue(isPurchase);
            dest.writeValue(isBlocked);
            dest.writeValue(date);
            dest.writeValue(time);
            dest.writeValue(isLiked);
            dest.writeValue(isDisliked);
        }
    }
}