package com.yukoon.codecenter.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true)
public class Record {
    private Integer id;
    private Integer user_id;
    private Integer total;
    private Integer reward_id;
    private Date apply_date;
    private int flag;
}
