package com.moqi.scheduleminiprogrambackend.util;

import com.moqi.scheduleminiprogrambackend.po.Message;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.List;

public class ExcelUtil {

    static String[] appointmentMap ={"学生姓名","学号","预约时间","内容","老师记录","学生反馈"};

    static String[] messageMap ={"学生姓名","学号","主题","时间","留言内容"};

    static final String TEACHER_NAME="毕菲菲";

    public static InputStream createExcel(List<ExcelInfo> excelInfos) {

        int length=excelInfos.size();
        XSSFWorkbook workbook=new XSSFWorkbook();
        XSSFSheet sheet1=workbook.createSheet();
        workbook.setSheetName(0,"sheet1");
        XSSFRow startRow=sheet1.createRow(0);
        //设置表头,表驱动
        for (int i = 0; i< appointmentMap.length; i++) {
            startRow.createCell(i).setCellValue(appointmentMap[i]);
        }

        //开始构建预约信息的部分
        for(int i=0;i<length;i++){
            XSSFRow row=sheet1.createRow(i+1);
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

    public static InputStream createExcel(List<ExcelInfo> excelInfos,List<MessageInfo> messageInfoList) {

        int length=excelInfos.size();
        XSSFWorkbook workbook=new XSSFWorkbook();
        XSSFSheet sheet1=workbook.createSheet();
        workbook.setSheetName(0,"sheet1");
        XSSFRow startRow=sheet1.createRow(0);
        //设置表头,表驱动
        for (int i = 0; i< appointmentMap.length; i++) {
            startRow.createCell(i).setCellValue(appointmentMap[i]);
        }

        //开始构建预约信息的部分
        for(int i=0;i<length;i++){
            XSSFRow row=sheet1.createRow(i+1);
            row.createCell(0).setCellValue(excelInfos.get(i).getName());
            row.createCell(1).setCellValue(excelInfos.get(i).getStudentId());
            row.createCell(2).setCellValue(excelInfos.get(i).getStartTime());
            row.createCell(3).setCellValue(excelInfos.get(i).getContent());
            row.createCell(4).setCellValue(excelInfos.get(i).getRecord());
            row.createCell(5).setCellValue(excelInfos.get(i).getFeedback());
        }


        //开始构建留言部分
        XSSFSheet sheet2=workbook.createSheet();
        workbook.setSheetName(1,"sheet2");
        startRow=sheet2.createRow(0);
        //设置表头,表驱动
        for (int i=0;i<messageMap.length;i++) {
            startRow.createCell(i).setCellValue(messageMap[i]);
        }
        for (MessageInfo messageInfo : messageInfoList) {
            XSSFRow row=createRow(sheet2);
            String studentName=messageInfo.getStudentName();
            row.createCell(0).setCellValue(studentName);
            row.createCell(1).setCellValue(messageInfo.getStudentId());
            row.createCell(2).setCellValue(messageInfo.getTopic());
            row.createCell(3).setCellValue(messageInfo.getCreateTime());
            List<Message> messageList=messageInfo.getMessageList();
            for(int i=0;i<messageList.size();i++){
                Message message=messageList.get(i);
                row.createCell(i+4).setCellValue((message.getType()==1?TEACHER_NAME:studentName)+"："+message.getContent());
            }
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


    private static XSSFRow createRow(XSSFSheet sheet){
        int rowNow=sheet.getLastRowNum();
        return sheet.createRow(rowNow+1);
    }


    /*public static void main(String[] args) throws FileNotFoundException {
        List<ExcelInfo> excelInfos=new ArrayList<>();
        excelInfos.add(new ExcelInfo("1","2","3","4","5","7"));
        InputStream inputStream= ExcelUtil.createExcel(excelInfos);
        OssClientUtil ossClientUtil=new OssClientUtil();
        ossClientUtil.uploadExcel2Oss(inputStream,"记录.xlsx");

    }*/
}
