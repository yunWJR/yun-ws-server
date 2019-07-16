package com.yun.yunwsserver.module.conversation.dtovo;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author: yun
 * @createdOn: 2019-07-12 14:40.
 */

@Data
@NoArgsConstructor
public class ConversationDto {
    @NotBlank
    @Length(max = 200)
    private String extraCvsId;

    private List<String> extraUserId;

    private String remark;
}
