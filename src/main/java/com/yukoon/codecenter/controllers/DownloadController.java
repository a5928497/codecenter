package com.yukoon.codecenter.controllers;

import com.yukoon.codecenter.services.DownloadService;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
public class DownloadController {
    @Autowired
    private DownloadService downloadService;

    //批量导出某一记录下的所有兑换码
    @ResponseBody
    @GetMapping("/exportcodes/{record_id}")
    public void exportallrewardinfo(@PathVariable("record_id")Integer record_id, HttpServletRequest request, HttpServletResponse response) {
        response.reset(); //清除buffer缓存
        Map<String,Object> map = new HashMap<String,Object>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
        response.setHeader("Content-Disposition", "attachment;filename="+sdf.format(new Date())+".xlsx");
        response.setContentType("application/vnd.ms-excel;charset=UTF-8");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        XSSFWorkbook workbook=null;
        try {
            workbook = downloadService.exportCodesByRecordId(record_id);
            OutputStream out = response.getOutputStream();
            BufferedOutputStream bout = new BufferedOutputStream(out);
            bout.flush();
            workbook.write(bout);
            bout.close();
        }catch (Exception e) {
            e.printStackTrace();
            System.out.println("失败");
        }
        System.out.println("成功");
    }
}
