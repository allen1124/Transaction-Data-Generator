import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;

public class Generator {
    private static JDBC jdbc;
    private static Scrapper scrapper;
    public static void main(String [] args) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException, IOException {
        jdbc = new JDBC();
        scrapper = new Scrapper();
        scrapper.getListofSecurities();
        writeStocksToDB();
    }

    private static void writeStocksToDB() throws IOException {
        File excelFile = new File("listofSecurities.xlsx");
        FileInputStream fis = new FileInputStream(excelFile);
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        XSSFSheet sheet = workbook.getSheetAt(0);
        int rowNo = sheet.getLastRowNum();
        for(int i = 3; i < rowNo; i++){
            Row row = sheet.getRow(i);
            if(row.getCell(2) != null && row.getCell(2).toString().equals("Equity")) {
                Stock temp = new Stock();
                temp.setISINCode(row.getCell(6).toString());
                temp.setStockName(row.getCell(1).toString());
                temp.setStockCode(row.getCell(0).toString());
                temp.setCategory(row.getCell(2).toString());
                temp.setBoardLot(Integer.valueOf(row.getCell(4).toString().replaceAll(",", "")));
                if("            ".equals(temp.getISINCode()))
                    temp.setISINCode(temp.getStockCode());
                jdbc.insert(temp);
            }
        }
        workbook.close();
        fis.close();
    }
}
