package com.yun.yunwsserver.module.message.dtovo;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreType;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;

/**
 * @author: yun
 * @createdOn: 2019-07-12 16:46.
 */

@Data
@NoArgsConstructor
public class MessageDto {
    @NotBlank
    @Length(max = 200)
    private String clientGroupId;

    private String contentJson;

    private Map content;

    private List<IgnoreUserPlatformDto> ignoreList;

    @JsonIgnore
    public void reform() {
        content = (Map) JSON.parse(contentJson);
    }
}
