package xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

import javafx.util.Pair;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {
    public static HashMap<String, List<Pair<String, ArrayList<String>>>> ReadExcel() {
        hash_map ExcelHM = new hash_map();
        try {
            File file = new File("C:/Exception/invalid_iterator_list.xlsx");
            FileInputStream fis = new FileInputStream(file);

            XSSFWorkbook wb = new XSSFWorkbook(fis);
            XSSFSheet sheet = wb.getSheetAt(0);

            int rowIndex=0;
            int columnIndex=0;

            int rows=sheet.getPhysicalNumberOfRows();

            System.out.println("testcell formula is : " + sheet.getRow(26).getCell(0).getCellTypeEnum());
            System.out.println("num of rows : " + rows);
            for(rowIndex=1;rowIndex<rows;rowIndex++){

                XSSFRow row = sheet.getRow(rowIndex);
                if(row != null){
                    int cells=row.getPhysicalNumberOfCells();
                    System.out.println("no of cells = " + cells);
                    ArrayList<String> argNumList = new ArrayList<String>(Arrays.asList(row.getCell(3).getStringCellValue().split(",")));
                    ExcelHM.Construct(row.getCell(0).getStringCellValue(),row.getCell(2).getStringCellValue(),argNumList);

/*                    for(columnIndex = 0; columnIndex < cells; columnIndex++){
                        String value = "";
                        XSSFCell cell = row.getCell(columnIndex);
                        if(cell==null){
                            continue;
                        } else {
                            switch (columnIndex){
                                case 0:

                            }
                            switch (cell.getCellTypeEnum()) {
                                case FORMULA:
                                    value = cell.getCellFormula();
                                    break;
                                case NUMERIC:
                                    value = cell.getNumericCellValue() + "";
                                    break;
                                case STRING:
                                    value = cell.getStringCellValue() + "";
                                    break;
                                case BLANK:
                                    value = cell.getBooleanCellValue() + "";
                                    break;
                                case ERROR:
                                    value = cell.getErrorCellValue() + "";
                                    break;
                            }


                            System.out.println("row : " + rowIndex + " column : " + columnIndex + " content : " + value);
                            //한줄씩 받을때마다 hashmap에 추가

                        }*/




                }
            }
            return ExcelHM.get_hashmap();

        } catch (FileNotFoundException fe) {
            System.out.println("FileNotFoundException >> " + fe.toString());
        } catch (IOException ie) {
            System.out.println("IOException >> " + ie.toString());
        }
        return null;
    }
}
