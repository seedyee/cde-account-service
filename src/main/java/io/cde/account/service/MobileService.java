package io.cde.account.service;

import java.util.List;

import io.cde.account.domain.Mobile;
import io.cde.account.exception.BizException;

/**
 * @author lcl
 */
public interface MobileService {

    /**
     * 获取指定用户的电话信息.
     *
     * @param accountId 用户id
     * @return 返回用户的电话信息
     * @throws BizException 如果用户id错误则抛异常
     */
    List<Mobile> getMobiles(String accountId) throws BizException;

    /**
     * 给指定用户添加电话信息.
     *
     * @param accountId 用户id
     * @param mobile    要添加的电话信息
     * @throws BizException 若用户id错误则抛异常，若电话号码已经被使用过抛异常
     */
    void addMobile(String accountId, Mobile mobile) throws BizException;

    /**
     * 修改指定用户的电话信息.
     *
     * @param accountId  用户id
     * @param mobileId   邮箱id
     * @param isVerified 要修改的字段，是否认证
     * @throws BizException 如果用户id或邮箱id错误则抛异常
     */
    void updateMobile(String accountId, String mobileId, boolean isVerified) throws BizException;

    /**
     * 修改默认电话是否公开.
     *
     * @param accountId 用户id
     * @param isPublic  是否公开
     * @throws BizException 修改出现问题抛修改失败异常
     */
    void updatePublicMobile(String accountId, boolean isPublic) throws BizException;

    /**
     * 删除指定用户的指定电话.
     *
     * @param accountId 用户id
     * @param mobileId  电话id
     * @throws BizException 如果用户id或邮箱id错误则抛异常
     */
    void deleteMobile(String accountId, String mobileId) throws BizException;
}
