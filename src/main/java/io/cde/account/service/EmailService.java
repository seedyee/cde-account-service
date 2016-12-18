package io.cde.account.service;

import java.util.List;

import io.cde.account.domain.Email;
import io.cde.account.exception.BizException;

/**
 * @author lcl
 */
public interface EmailService {

    /**
     * 根据用户id获取用户邮箱信息.
     *
     * @param accountId 用户id
     * @return 返回用户的邮箱信息
     * @throws BizException 若用户id错误则抛出异常
     */
    List<Email> getEmails(String accountId) throws BizException;

    /**
     * 为指定用户添加邮箱信息.
     *
     * @param accountId 用户id
     * @param email     要添加的邮箱信息
     * @throws BizException 若用户id错误则抛出异常
     */
    void addEmail(String accountId, Email email) throws BizException;

    /**
     * 修改指定用户的邮箱信息.
     *
     * @param accountId  用户id
     * @param emailId    邮箱id
     * @param isVerified 是否认证
     * @throws BizException 若用户没有关联该邮箱则抛出异常
     */
    void updateEmail(String accountId, String emailId, boolean isVerified) throws BizException;

    /**
     * 设置默认邮箱是否为公开.
     *
     * @param accountId 用户id
     * @param isPublis  是否公开
     * @throws BizException 修改出现问题抛修改失败异常
     */
    void updatePublicEmail(String accountId, boolean isPublis) throws BizException;

    /**
     * 删除指定用户的指定邮箱信息.
     *
     * @param accountId 用户id
     * @param emailId   邮箱id
     * @throws BizException 若用户没有关联该邮箱则抛出异常
     */
    void deleteEmail(String accountId, String emailId) throws BizException;
}
