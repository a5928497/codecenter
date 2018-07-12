package com.yukoon.codecenter.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true)
public class Code {
    private Integer id;
    private String code;
    private Integer status;
    private Integer reward_id;
    private Integer record_id;
}
