package com.spider.bussiness.entity;

import com.common.bussiness.entity.BaseEntity;
import javax.persistence.Table;

@Table(name = "comment_info")
public class CommentInfo extends BaseEntity {

    private String mId;

    private String commentId;

    private String commentUId;

    private String commentUName;

    private String createAt;

    private String commentText;

    private String likeNum;

    private String source;

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getCommentUId() {
        return commentUId;
    }

    public void setCommentUId(String commentUId) {
        this.commentUId = commentUId;
    }

    public String getCommentUName() {
        return commentUName;
    }

    public void setCommentUName(String commentUName) {
        this.commentUName = commentUName;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public String getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(String likeNum) {
        this.likeNum = likeNum;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
