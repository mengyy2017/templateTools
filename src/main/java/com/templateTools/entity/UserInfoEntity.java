package com.templateTools.entity;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "sys_user_info")
public class UserInfoEntity implements Serializable {

    @Id
    private String userinfoId; //用户ID: VARCHAR userinfo_id
    private String dwxxId; //单位信息主键: VARCHAR dwxx_id -- 外键：sys_dwxx
    private String bmxxId; //部门信息ID: VARCHAR bmxx_id -- 外键：sys_buxx
    private String userinfoName; //用户名称: VARCHAR userinfo_name
    private String userinfoDuty; //职务: VARCHAR userinfo_duty
    private String userinfoLoginAccount; //用户账号: VARCHAR userinfo_login_account
    private String userinfoLoginPassword; //用户密码: VARCHAR userinfo_login_password
    private String userinfoPhone; //用户手机: VARCHAR userinfo_phone
    private String userinfoEmail; //用户邮箱: VARCHAR userinfo_email
    private Integer userinfoShowOrder; //显示顺序: INT userinfo_show_order
    private String userinfoIsEnable; //是否启用: CHAR userinfo_is_enable
    private String userinfoIsValid; //是否有效: CHAR userinfo_is_valid

    public String getUserinfoId() {
        return userinfoId;
    }

    public void setUserinfoId(String userinfoId) {
        this.userinfoId = userinfoId;
    }

    public String getDwxxId() {
        return dwxxId;
    }

    public void setDwxxId(String dwxxId) {
        this.dwxxId = dwxxId;
    }

    public String getBmxxId() {
        return bmxxId;
    }

    public void setBmxxId(String bmxxId) {
        this.bmxxId = bmxxId;
    }

    public String getUserinfoName() {
        return userinfoName;
    }

    public void setUserinfoName(String userinfoName) {
        this.userinfoName = userinfoName;
    }

    public String getUserinfoDuty() {
        return userinfoDuty;
    }

    public void setUserinfoDuty(String userinfoDuty) {
        this.userinfoDuty = userinfoDuty;
    }

    public String getUserinfoLoginAccount() {
        return userinfoLoginAccount;
    }

    public void setUserinfoLoginAccount(String userinfoLoginAccount) {
        this.userinfoLoginAccount = userinfoLoginAccount;
    }

    public String getUserinfoLoginPassword() {
        return userinfoLoginPassword;
    }

    public void setUserinfoLoginPassword(String userinfoLoginPassword) {
        this.userinfoLoginPassword = userinfoLoginPassword;
    }

    public String getUserinfoPhone() {
        return userinfoPhone;
    }

    public void setUserinfoPhone(String userinfoPhone) {
        this.userinfoPhone = userinfoPhone;
    }

    public String getUserinfoEmail() {
        return userinfoEmail;
    }

    public void setUserinfoEmail(String userinfoEmail) {
        this.userinfoEmail = userinfoEmail;
    }

    public Integer getUserinfoShowOrder() {
        return userinfoShowOrder;
    }

    public void setUserinfoShowOrder(Integer userinfoShowOrder) {
        this.userinfoShowOrder = userinfoShowOrder;
    }

    public String getUserinfoIsEnable() {
        return userinfoIsEnable;
    }

    public void setUserinfoIsEnable(String userinfoIsEnable) {
        this.userinfoIsEnable = userinfoIsEnable;
    }

    public String getUserinfoIsValid() {
        return userinfoIsValid;
    }

    public void setUserinfoIsValid(String userinfoIsValid) {
        this.userinfoIsValid = userinfoIsValid;
    }
}
