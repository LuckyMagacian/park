package net.imwork.yangyuanjian.common.assist;

import com.sun.xml.internal.ws.api.EndpointAddress;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelAssist {
//    private Map<File,HSSFWorkbook> map = new HashMap<>();

    private HSSFWorkbook workbook=new HSSFWorkbook();
    //设置工作簿
    public void setWorkbook(File file){
        try {
            //打开文件输入流
            FileInputStream fin=new FileInputStream(file);
            //创建工作簿
            HSSFWorkbook workbook=new HSSFWorkbook(fin);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("读写excel文件,文件["+file.getAbsolutePath()+"]不存在!",e);
        } catch (IOException e) {
            throw new RuntimeException("读写excel文件,文件["+file.getAbsolutePath()+"]io异常!",e);
        }
    }
    //设置工作簿
    public void setWorkbook(HSSFWorkbook workbook){
        this.workbook=workbook;
    }
    //读取指定行 指定数量的内容,页或行不存在则返回null,若cell为空则在list中添加null
    public  List<String> readLine(int pageNum,int rowNum,int start,int colSize){
        List<String> list = new ArrayList<>();

        Sheet sheet=workbook.getSheetAt(pageNum);
        if(sheet==null)
            return null;

        Row row=sheet.getRow(rowNum);
        if(row==null)
            return null;

        for(int i=start;i<colSize+start;i++){
            Cell cell=row.getCell(i);
            if(cell==null)
                list.add(null);
            else
                list.add(cell.getStringCellValue());
        }
        return list;
    }

    public List<String> readRow(int pageNum,int colNum,int start,int rowSize){
        List<String> list = new ArrayList<>();

        Sheet sheet=workbook.getSheetAt(pageNum);
        if(sheet==null)
            return null;

        for(int i=start;i<rowSize+start;i++){
            Row row=sheet.getRow(i);
            if(row==null){
                list.add(null);
                continue;
            }

            Cell cell=row.getCell(colNum);
            if(cell==null){
                list.add(null);
                continue;
            }
            list.add(cell.getStringCellValue());
        }

        return list;

    }


    //读取指定单元格的内容,若页或行或单元格不存在返回null,否则返回单元格内容
    public  String readCell(int pageNum,int rowNum,int colNum){
        Sheet sheet=workbook.getSheetAt(pageNum);
        if(sheet==null)
            return null;
        Row row=sheet.getRow(rowNum);
        if(row==null)
            return null;
        Cell cell=row.getCell(colNum);
        if(cell==null)
            return null;
        return cell.getStringCellValue();
    }
    //设置指定单元格的内容,若页或行不存在,返回false,若单元格不存在则创建单元格并设置值
    public boolean setCell(int pageNum,int rowNum,int colNum,String value){
        Sheet sheet=workbook.getSheetAt(pageNum);
        if(sheet==null)
            return false;
        Row row=sheet.getRow(rowNum);
        if(row==null)
            return false;
        Cell cell=row.getCell(colNum);
        if(cell==null) {
            cell = row.createCell(colNum);
        }
        cell.setCellValue(value);
        return true;
    }


}
