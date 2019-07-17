package com.spider.bussiness.entity;

import com.common.bussiness.entity.BaseEntity;
import com.common.pub.pubInter.ChildAnnotation;
import com.common.pub.pubInter.ESIdAnnotation;
import com.common.pub.pubInter.AnalyzerAnnotation;
import javax.persistence.Table;
import java.util.List;

@Table(name = "u_info")
public class UInfo extends BaseEntity {

    @ESIdAnnotation
    private String uId;

    private String uHome;

    @AnalyzerAnnotation
    private String summarize;

    @AnalyzerAnnotation(analyzerName = AnalyzerAnnotation.AnalyzerName.edgeNgramAnalyzer)
    private String address;

    private String fansNum;

    @AnalyzerAnnotation
    private String verify;

    private String profileNum;

    private String followNum;

    @AnalyzerAnnotation(analyzerName = AnalyzerAnnotation.AnalyzerName.edgeNgramAnalyzer)
    private String uName;

    @AnalyzerAnnotation
    private String tag;

    @AnalyzerAnnotation(analyzerName = AnalyzerAnnotation.AnalyzerName.edgeNgramAnalyzer)
    private String school;

    @AnalyzerAnnotation
    private String professional;

    private String extractStr;

    @ChildAnnotation(name = "wb_info")
    private List<WbInfo> wbInfoList;

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getuHome() {
        return uHome;
    }

    public void setuHome(String uHome) {
        this.uHome = uHome;
    }

    public String getSummarize() {
        return summarize;
    }

    public void setSummarize(String summarize) {
        this.summarize = summarize;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFansNum() {
        return fansNum;
    }

    public void setFansNum(String fansNum) {
        this.fansNum = fansNum;
    }

    public String getVerify() {
        return verify;
    }

    public void setVerify(String verify) {
        this.verify = verify;
    }

    public String getProfileNum() {
        return profileNum;
    }

    public void setProfileNum(String profileNum) {
        this.profileNum = profileNum;
    }

    public String getFollowNum() {
        return followNum;
    }

    public void setFollowNum(String followNum) {
        this.followNum = followNum;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getProfessional() {
        return professional;
    }

    public void setProfessional(String professional) {
        this.professional = professional;
    }

    public String getExtractStr() {
        return extractStr;
    }

    public void setExtractStr(String extractStr) {
        this.extractStr = extractStr;
    }

    public List<WbInfo> getWbInfoList() {
        return wbInfoList;
    }

    public void setWbInfoList(List<WbInfo> wbInfoList) {
        this.wbInfoList = wbInfoList;
    }
}
