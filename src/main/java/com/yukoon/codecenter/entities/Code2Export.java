package com.yukoon.codecenter.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
public class Code2Export {
    private String code;
    private String name_bank;
    private Date cahing_date;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName_bank() {
        return name_bank;
    }

    public void setName_bank(String name_bank) {
        this.name_bank = name_bank;
    }

    public Date getCahing_date() {
        return cahing_date;
    }

    public void setCahing_date(Date cahing_date) {
        this.cahing_date = cahing_date;
    }
}
