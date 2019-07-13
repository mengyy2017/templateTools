package com.spider.bussiness.entity;

import com.common.bussiness.entity.BaseEntity;
import com.common.pub.pubInter.ChildAnnotation;
import com.common.pub.pubInter.ESIdAnnotation;
import com.common.pub.pubInter.ForeignKeyAnnotation;
import javax.persistence.Table;
import java.util.List;

@Table(name = "wb_info")
public class WbInfo extends BaseEntity {

    @ESIdAnnotation
    private String mId;

    @ForeignKeyAnnotation
    private String uId;

    private String wbContent;

    private String wbContentHtml;

    private String wbMedia;

    private String wbMediaHtml;

    private String wbTransfer;

    private String wbTransferHtml;

    private String wbComment;

    private String wbCommentHtml;

    private String wbPraised;

    private String wbPraisedHtml;

    private String timeInfo;

    private String deviceInfo;

    private String wbRefContent;

    private String wbRefContentHtml;

    private String wbRefTransfer;

    private String wbRefTransferHtml;

    private String wbRefComment;

    private String wbRefCommentHtml;

    private String wbRefPraised;

    private String wbRefPraisedHtml;

    @ChildAnnotation(name = "comment_info")
    private List<CommentInfo> commentInfoList;

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getWbContent() {
        return wbContent;
    }

    public void setWbContent(String wbContent) {
        this.wbContent = wbContent;
    }

    public String getWbContentHtml() {
        return wbContentHtml;
    }

    public void setWbContentHtml(String wbContentHtml) {
        this.wbContentHtml = wbContentHtml;
    }

    public String getWbMedia() {
        return wbMedia;
    }

    public void setWbMedia(String wbMedia) {
        this.wbMedia = wbMedia;
    }

    public String getWbMediaHtml() {
        return wbMediaHtml;
    }

    public void setWbMediaHtml(String wbMediaHtml) {
        this.wbMediaHtml = wbMediaHtml;
    }

    public String getWbTransfer() {
        return wbTransfer;
    }

    public void setWbTransfer(String wbTransfer) {
        this.wbTransfer = wbTransfer;
    }

    public String getWbTransferHtml() {
        return wbTransferHtml;
    }

    public void setWbTransferHtml(String wbTransferHtml) {
        this.wbTransferHtml = wbTransferHtml;
    }

    public String getWbComment() {
        return wbComment;
    }

    public void setWbComment(String wbComment) {
        this.wbComment = wbComment;
    }

    public String getWbCommentHtml() {
        return wbCommentHtml;
    }

    public void setWbCommentHtml(String wbCommentHtml) {
        this.wbCommentHtml = wbCommentHtml;
    }

    public String getWbPraised() {
        return wbPraised;
    }

    public void setWbPraised(String wbPraised) {
        this.wbPraised = wbPraised;
    }

    public String getWbPraisedHtml() {
        return wbPraisedHtml;
    }

    public void setWbPraisedHtml(String wbPraisedHtml) {
        this.wbPraisedHtml = wbPraisedHtml;
    }

    public String getTimeInfo() {
        return timeInfo;
    }

    public void setTimeInfo(String timeInfo) {
        this.timeInfo = timeInfo;
    }

    public String getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(String deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public String getWbRefContent() {
        return wbRefContent;
    }

    public void setWbRefContent(String wbRefContent) {
        this.wbRefContent = wbRefContent;
    }

    public String getWbRefContentHtml() {
        return wbRefContentHtml;
    }

    public void setWbRefContentHtml(String wbRefContentHtml) {
        this.wbRefContentHtml = wbRefContentHtml;
    }

    public String getWbRefTransfer() {
        return wbRefTransfer;
    }

    public void setWbRefTransfer(String wbRefTransfer) {
        this.wbRefTransfer = wbRefTransfer;
    }

    public String getWbRefTransferHtml() {
        return wbRefTransferHtml;
    }

    public void setWbRefTransferHtml(String wbRefTransferHtml) {
        this.wbRefTransferHtml = wbRefTransferHtml;
    }

    public String getWbRefComment() {
        return wbRefComment;
    }

    public void setWbRefComment(String wbRefComment) {
        this.wbRefComment = wbRefComment;
    }

    public String getWbRefCommentHtml() {
        return wbRefCommentHtml;
    }

    public void setWbRefCommentHtml(String wbRefCommentHtml) {
        this.wbRefCommentHtml = wbRefCommentHtml;
    }

    public String getWbRefPraised() {
        return wbRefPraised;
    }

    public void setWbRefPraised(String wbRefPraised) {
        this.wbRefPraised = wbRefPraised;
    }

    public String getWbRefPraisedHtml() {
        return wbRefPraisedHtml;
    }

    public void setWbRefPraisedHtml(String wbRefPraisedHtml) {
        this.wbRefPraisedHtml = wbRefPraisedHtml;
    }

    public List<CommentInfo> getCommentInfoList() {
        return commentInfoList;
    }

    public void setCommentInfoList(List<CommentInfo> commentInfoList) {
        this.commentInfoList = commentInfoList;
    }
}
