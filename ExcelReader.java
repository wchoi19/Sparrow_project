package xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.lang.reflect.Array;
import java.util.*;

import javafx.util.Pair;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/*
 * 에러 정보가 담긴 엑셀 파일을 읽은 후 Pair<에러명(string), 함수 정보가 담긴 HashMap>을 리턴하는 클래스.
 */


public class ExcelReader {
    public static Pair<String, HashMap <String, List<Pair<String, ArrayList<String>>>>> ReadExcel(String filePath) {
        hash_map ExcelHM = new hash_map();
        try {
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);

            XSSFWorkbook wb = new XSSFWorkbook(fis);
            XSSFSheet sheet = wb.getSheetAt(0);

            int rowIndex=0;
            int columnIndex=0;

            int rows=sheet.getPhysicalNumberOfRows();

            /*System.out.println("testcell formula is : " + sheet.getRow(26).getCell(0).getCellTypeEnum());
            System.out.println("num of rows : " + rows);*/

            String errorName = sheet.getRow(0).getCell(1).getStringCellValue();
            /*System.out.println("Error name is : " + errorName);*/

            for(rowIndex=2;rowIndex<rows;rowIndex++){ //container, function format, arg_num 열을 읽고 hashmap에 추가

                XSSFRow row = sheet.getRow(rowIndex);
                if(row != null){
                    int cells=row.getPhysicalNumberOfCells();
                    CellType thirdRowType = row.getCell(3).getCellTypeEnum();
                    ArrayList<String>argNumList = new ArrayList<String>();
                    if(thirdRowType==CellType.NUMERIC){ //arg_num이 하나의 숫자일 경우(Celltype.NUMERIC으로 인식되는 경우)
                        double rawCellVal = row.getCell(3).getNumericCellValue();
                        argNumList.add((int)rawCellVal + "");
                    }
                    else if(thirdRowType==CellType.STRING) { //arg_num이 쉼표로 구분된 여러개 숫자일 경우 (Celltype.STRING으로 인식되는 경우)
                        argNumList = new ArrayList<String>(Arrays.asList(row.getCell(3).getStringCellValue().split(",")));
                    }
                    ExcelHM.Construct(row.getCell(0).getStringCellValue(),row.getCell(2).getStringCellValue(),argNumList);
                    }
            }
            return new Pair<>(errorName,ExcelHM.get_hashmap());

        } catch (FileNotFoundException fe) {
            System.out.println("FileNotFoundException >> " + fe.toString());
        } catch (IOException ie) {
            System.out.println("IOException >> " + ie.toString());
        }
        return null;
    }
}
