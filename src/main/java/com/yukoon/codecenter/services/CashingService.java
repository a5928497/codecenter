package com.yukoon.codecenter.services;

import com.yukoon.codecenter.entities.Code;
import com.yukoon.codecenter.mappers.CodeMapper;
import com.yukoon.codecenter.utils.CodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CashingService {
    @Autowired
    private CodeMapper codeMapper;


}
