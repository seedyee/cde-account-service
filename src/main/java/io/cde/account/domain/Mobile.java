package io.cde.account.domain;

import org.springframework.data.annotation.Transient;

/**
 * @author lcl
 */
public class Mobile {

    /**
     * 电话id.
     */
    private String mobileId;

    /**
     * 电话号码.
     */
    private String mobile;

    /**
     * 是否认证 认证为1，不认证为0，默认为0.
     */
    private boolean isVerified;

    /**
     * 是否默认，保留字段.
     */
    @Transient
    private boolean isDefault;

    /**
     * 是否公开，保留字段.
     */
    @Transient
    private boolean isPublic;

    public String getMobileId() {
        return mobileId;
    }

    public void setMobileId(final String mobileId) {
        this.mobileId = mobileId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(final String mobile) {
        this.mobile = mobile;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(final boolean isVerified) {
        this.isVerified = isVerified;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(final boolean isDefault) {
        this.isDefault = isDefault;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(final boolean isPublic) {
        this.isPublic = isPublic;
    }
}
