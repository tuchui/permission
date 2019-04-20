package com.mao.params;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class TestVo {
    @NotBlank
    private String msg;
    @NotNull(message = "id 不能为null")
    private Integer id;
}
