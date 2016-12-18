package io.cde.account.domain;

import org.springframework.data.annotation.Transient;

/**
 * 用户邮箱实体.
 *
 * @author lcl
 */
public class Email {

    /**
     * 邮箱id.
     */
    private String emailId;

    /**
     * 邮箱的地址.
     */
    private String email;

    /**
     * 是否认证 认证为1，其他为0.
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

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(final String emailId) {
        this.emailId = emailId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
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
