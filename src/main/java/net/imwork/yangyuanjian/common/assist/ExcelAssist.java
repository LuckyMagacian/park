package net.imwork.yangyuanjian.common.assist;

import net.imwork.yangyuanjian.common.annotation.Comment;
import net.imwork.yangyuanjian.common.annotation.EasyLog;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * Excel读写辅助类
 */
@EasyLog(LogFactory.LogLevel.DEBUG)
@Comment("poi读写从0开始计数")
public class ExcelAssist {
    /**读取cell中的值为字符串*/
    private static final Function<Cell,String> getCellValueAsString=c->{
        LogFactory.debug(ExcelAssist.class,"invoke getCellValuesAsString arg["+c+"]");
        if(c==null)
            throw new NullPointerException("arg is null when invoke getCellValueAsString");
        switch (c.getCellTypeEnum()){
            case STRING:return c.getStringCellValue();
            case BOOLEAN:return c.getBooleanCellValue()+"";
            case NUMERIC:{
                String value=c.getNumericCellValue()+"";
                if(value.matches("[0-9]+\\.0*[1-9]+"))
                    return value;
                else
                    return value.substring(0,value.indexOf("."));
            }
            case BLANK:return "";
            default:throw new IllegalArgumentException("can't get string value frome cell !");
        }
    };
    /**excel工作簿*/
    private Workbook workbook=null;
    /**构造方法,接收一个文件*/
    public ExcelAssist(File file){
        try {
            workbook=WorkbookFactory.create(file);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取sheet编号为pageNum,行号为rowNum,上单元格从start开始读取colSize格的内容
     * @param pageNum sheet编号
     * @param rowNum 行号
     * @param start 读取起始列号
     * @param colSize 连续读取列数
     * @return list<String>
     */
    public  List<String> readLine(int pageNum,int rowNum,int start,int colSize){
        List<String> list = new ArrayList<>();
        Sheet sheet=workbook.getSheetAt(pageNum);
        if(sheet==null)
            return null;
        Row row=sheet.getRow(rowNum);
        if(row==null)
            return null;
        for(int i=start;i<=colSize+start;i++){
            Cell cell=row.getCell(i);
            if(cell==null)
                list.add(null);
            else
                list.add(getCellValueAsString.apply(cell));
        }
        return list;
    }

    /**
     * 读取sheet编号为pageNum,列号为colNum,从start开始,读取rowSize个单元格的内容
     * @param pageNum sheet编号
     * @param colNum 列号
     * @param start 读取起始行号
     * @param rowSize 连续读取行数
     * @return  list<String>
     */
    public List<String> readRow(int pageNum,int colNum,int start,int rowSize){
        List<String> list = new ArrayList<>();
        Sheet sheet=workbook.getSheetAt(pageNum);
        if(sheet==null)
            return null;
        for(int i=start;i<=rowSize+start;i++){
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


    /**读取指定单元格的内容,若页或行或单元格不存在返回null,否则返回单元格内容*/
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
    /**设置指定单元格的内容,若页或行不存在,返回false,若单元格不存在则创建单元格并设置值*/
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
