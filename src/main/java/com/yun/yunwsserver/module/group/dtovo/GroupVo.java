package com.yun.yunwsserver.module.group.dtovo;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.yun.yunwsserver.module.group.entity.Group;
import com.yun.yunwsserver.module.group.entity.GroupUserRl;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author: yun
 * @createdOn: 2019-07-12 14:41.
 */

@Data
@NoArgsConstructor
public class GroupVo {
    @JsonUnwrapped
    private Group group;

    private List<GroupUserRl> userList;

    public GroupVo(Group group, List<GroupUserRl> userList) {
        this.group = group;
        this.userList = userList;
    }
}
