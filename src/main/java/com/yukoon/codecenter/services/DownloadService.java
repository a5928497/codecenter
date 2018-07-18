package com.yukoon.codecenter.services;

import com.yukoon.codecenter.entities.*;
import com.yukoon.codecenter.mappers.CodeMapper;
import com.yukoon.codecenter.mappers.UserMapper;
import com.yukoon.codecenter.utils.ExcelUtil;
import org.apache.el.parser.ParseException;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class DownloadService {
    @Autowired
    private CodeMapper codeMapper;
    @Autowired
    private UserMapper userMapper;

    public XSSFWorkbook exportExcel(List<Code2Export> list) throws InvocationTargetException, ClassNotFoundException, IntrospectionException, ParseException, IllegalAccessException {
        List<Excel> excels = new ArrayList<>();
        Map<Integer,List<Excel>> map = new LinkedHashMap<>();
        XSSFWorkbook xssfWorkbook = null;
        //设置标题栏
        excels.add(new Excel("兑换码","code",0));
        excels.add(new Excel("兑换人员","name_bank",0));
        excels.add(new Excel("兑换时间","cahing_date",0));
        map.put(0,excels);
        String excelName = "兑换码";
        xssfWorkbook = ExcelUtil.createExcelFile(Code2Export.class,list,map,excelName);
        return xssfWorkbook;
    }

    //根据record_id导出所有兑换码
    public XSSFWorkbook exportCodesByRecordId(Integer record_id) throws ClassNotFoundException, IntrospectionException, IllegalAccessException, ParseException, InvocationTargetException {
        List<Code2Export> list = new ArrayList<>();
        List<Code> codes = codeMapper.findAllByRecordId(record_id);
        for (Code code:codes) {
            Code2Export code2Export = new Code2Export();
            code2Export.setCode(code.getCode());
            if (code.getStatus() == 2) {
                User user = userMapper.findById(code.getOperator_id());
                code2Export.setName_bank(user.getUsername()+"-"+user.getBank());
                code2Export.setCahing_date(code.getCashing_date());
            }
            list.add(code2Export);
        }
        return exportExcel(list);
    }
}
