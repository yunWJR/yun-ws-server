package com.yun.yunwsserver.module.mguser.entity;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author: yun
 * @createdOn: 2019-07-12 11:07.
 */

public interface MgUserJrp extends JpaRepository<MgUser, Long> {
    boolean existsByInfo_AcctName(String acct);
}
