package utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;

public class ExcelFileManager {

    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    public ExcelFileManager(String filePath , String sheetName) {
        try {
//            File file = new File(filePath);
            FileInputStream fileInputStream = new FileInputStream(filePath);
            workbook = new XSSFWorkbook(fileInputStream);
            sheet = workbook.getSheet(sheetName);


        } catch (Exception e) {
            e.printStackTrace();}
    }
    public int getRowCount(){
        int countOfRows = sheet.getPhysicalNumberOfRows();
        return countOfRows;
    }
    public String getCellData(int rowIndex , int columnIndex){
        Cell cell = sheet.getRow(rowIndex).getCell(columnIndex);
        DataFormatter dataFormatter = new DataFormatter() ;
        String cellData = dataFormatter.formatCellValue(cell);
        return cellData;

    }
}
