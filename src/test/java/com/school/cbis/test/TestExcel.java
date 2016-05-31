package com.school.cbis.test;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by lenovo on 2016-05-27.
 */
public class TestExcel {

    @Test
    public void testHSExcel() throws IOException {
        Workbook wb = new HSSFWorkbook();
        CreationHelper creationHelper = wb.getCreationHelper();
        Sheet sheet = wb.createSheet("new sheet");
        Row row = sheet.createRow(0);
        Cell cell = row.createCell(0);
        cell.setCellValue(new Date());

        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setDataFormat(
                creationHelper.createDataFormat().getFormat("m/d/yy h:mm")
        );
        cell = row.createCell(1);
        cell.setCellValue(new Date());
        cell.setCellStyle(cellStyle);

        cell = row.createCell(2);
        cell.setCellValue(Calendar.getInstance());
        cell.setCellStyle(cellStyle);

        FileOutputStream fileOut = new
                FileOutputStream("f:/workbook.xls");
        wb.write(fileOut);
        fileOut.close();
    }

    private static HSSFWorkbook readFile(String filename) throws IOException {
        return new HSSFWorkbook(new FileInputStream(filename));
    }

    @Test
    public void testReadExcel() throws IOException {
        HSSFWorkbook wb = readFile("f:/HSSFReadWrite.xls");

        System.out.println("Data dump:\n");

        for (int k = 0; k < wb.getNumberOfSheets(); k++) {
            HSSFSheet sheet = wb.getSheetAt(k);
            int rows = sheet.getPhysicalNumberOfRows();
            System.out.println("Sheet " + k + " \"" + wb.getSheetName(k) + "\" has " + rows + " row(s).");
            for (int r = 0; r < rows; r++) {
                HSSFRow row = sheet.getRow(r);
                if (row == null) {
                    continue;
                }

                int cells = row.getPhysicalNumberOfCells();
                System.out.println("\nROW " + row.getRowNum() + " has " + cells + " cell(s).");
                for (int c = 0; c < cells; c++) {
                    HSSFCell cell = row.getCell(c);
                    String value = null;

                    switch (cell.getCellType()) {

                        case HSSFCell.CELL_TYPE_FORMULA:
                            value = "FORMULA value=" + cell.getCellFormula();
                            break;

                        case HSSFCell.CELL_TYPE_NUMERIC:
                            value = "NUMERIC value=" + cell.getNumericCellValue();
                            break;

                        case HSSFCell.CELL_TYPE_STRING:
                            value = "STRING value=" + cell.getStringCellValue();
                            break;

                        default:
                    }
                    System.out.println("CELL col=" + cell.getColumnIndex() + " VALUE=" + value);
                }
            }
        }
    }
}
