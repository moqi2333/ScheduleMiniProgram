package com.moqi.scheduleminiprogrambackend.util;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ExcelUtil {

    static String[] map={"学生姓名","学号","预约时间","内容","老师记录","学生反馈"};

    public static InputStream createExcel(List<ExcelInfo> excelInfos) {
        int length=excelInfos.size();
        XSSFWorkbook workbook=new XSSFWorkbook();
        XSSFSheet sheet=workbook.createSheet();
        workbook.setSheetName(0,"sheet1");
        XSSFRow startRow=sheet.createRow(0);
        //设置表头,表驱动
        for (int i=0;i<map.length;i++) {
            startRow.createCell(i).setCellValue(map[i]);
        }

        //创建表格内容
        for(int i=0;i<length;i++){
            XSSFRow row=sheet.createRow(i+1);
            row.createCell(0).setCellValue(excelInfos.get(i).getName());
            row.createCell(1).setCellValue(excelInfos.get(i).getStudentId());
            row.createCell(2).setCellValue(excelInfos.get(i).getStartTime());
            row.createCell(3).setCellValue(excelInfos.get(i).getContent());
            row.createCell(4).setCellValue(excelInfos.get(i).getRecord());
            row.createCell(5).setCellValue(excelInfos.get(i).getFeedback());
        }
        ByteArrayOutputStream out=new ByteArrayOutputStream();
        try{
            workbook.write(out);
            out.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ByteArrayInputStream(out.toByteArray());
    }

    /*public static void main(String[] args) throws FileNotFoundException {
        List<ExcelInfo> excelInfos=new ArrayList<>();
        excelInfos.add(new ExcelInfo("1","2","3","4","5","7"));
        InputStream inputStream= ExcelUtil.createExcel(excelInfos);
        OssClientUtil ossClientUtil=new OssClientUtil();
        ossClientUtil.uploadExcel2Oss(inputStream,"记录.xlsx");

    }*/
}
