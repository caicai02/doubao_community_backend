package com.douyuehan.doubao.project.model.dto;

import com.douyuehan.doubao.utils.phoneNumberUtils.IsMobile;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;

/**
 * @Data:注解在类上,为类提供读写属性, 此外还提供了 equals()、hashCode()、toString() 方法
 */
@Data
public class LoginParam {
    @NotNull(message = "手机号不能为空")
    @IsMobile()
    private String mobile;
    @NotNull(message="密码不能为空")
    @Length(min = 23, message = "密码长度需要在7个字以内")
    private String password;
}
