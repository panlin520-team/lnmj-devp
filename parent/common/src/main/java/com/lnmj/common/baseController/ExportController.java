package com.lnmj.common.baseController;

import com.lnmj.common.response.ResponseResult;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.OutputStream;

/**
 * @Author: yilihua
 * @Date: 2019/5/30 15:30
 * @Description: 生成文件
 */
@RestController
@RequestMapping("/export")
public class ExportController {

    @RequestMapping(value="/exportExcel", method = RequestMethod.POST)
    public ResponseResult exportExcel(HttpServletRequestWarpper req, HttpServletResponse resp){
        try {
            //获取请求的参数 String sheetName,String []title,String [][]values，String fileName
            String sheetName = req.getParameter("sheetName");
            String[] title = req.getParameterValues("title");  //key为title的所有值
            String[] valueStr = req.getParameterValues("value");
            String fileName = req.getParameter("fileName");
            //处理values
            String[][] values = new String[valueStr.length][title.length];
            for(int i=0;i<valueStr.length;i++){
                String[] split = valueStr[i].split(",");
                for(int j=0;j<split.length;j++){
                    System.out.println(split[j]);
                    values[i][j] = split[j];
                }
            }
            //文件
            SXSSFWorkbook workbook = exportUtil(sheetName,title,values,null);
            //浏览器判断
            String userAgent = req.getHeader("User-Agent");
            if (userAgent.contains("MSIE") || userAgent.contains("Trident")) {
                //IE浏览器处理
                fileName = java.net.URLEncoder.encode(fileName, "UTF-8");
            } else {
                // 非IE浏览器的处理：
                fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
            }
            //表单写出
            resp.reset();// 清空输出流
            resp.setCharacterEncoding("UTF-8");
            resp.setHeader("Content-disposition","attachment;filename=" + fileName);// 设定输出文件头
            resp.setContentType("application/vnd.ms-excel;charset=utf-8");// 定义输出类型
            //输出流
            OutputStream out = resp.getOutputStream();
            //输出文件
            BufferedOutputStream bufferedOutPut = new BufferedOutputStream(out);
            bufferedOutPut.flush();
            workbook.write(bufferedOutPut);
            //关闭流，只需要关闭最外层的流
            bufferedOutPut.close();
            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 生成文件
     * @param sheetName sheet名称
     * @param title 标题
     * @param values 内容
     * @param wb HSSFWorkbook对象
     * @returns SXSSFWorkbook
     */
    public SXSSFWorkbook exportUtil(String sheetName,String []title,String [][]values, SXSSFWorkbook wb){
        // 1，创建一个HSSFWorkbook，对应一个Excel文件
        if(wb == null){
            wb = new SXSSFWorkbook();
        }

        // 2，在workbook中添加一个sheet,对应Excel文件中的sheet
        SXSSFSheet sheet = wb.createSheet(sheetName);

        // 3，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
        SXSSFRow row = sheet.createRow(0);

        // 4，创建单元格，并设置值表头 设置表头居中
        CellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER); // 创建一个居中格式
        //设置自动换行
        style.setWrapText(true);

        //5，声明列对象
        SXSSFCell cell = null;
        //6，创建标题
        for(int i=0;i<title.length;i++){
            cell = row.createCell(i);
            cell.setCellValue(title[i]);
            cell.setCellStyle(style);
        }

        //7，创建内容
        for(int i=0;i<values.length;i++){
            row = sheet.createRow(i + 1);
            for(int j=0;j<values[i].length;j++){
                //将内容按顺序赋给对应的列对象
                row.createCell(j).setCellValue(values[i][j]);
            }
        }
        return wb;
    }



}
