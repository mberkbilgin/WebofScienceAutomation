package utilities;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ExcelUtils {

    private static final String FILE_PATH = "/Users/mbb/Desktop/data.xlsx";  // Dosya yolu sabit

    private Workbook workbook;
    private Sheet sheet;

    public ExcelUtils() throws Exception {
        FileInputStream fis = new FileInputStream(new File(FILE_PATH));
        workbook = new XSSFWorkbook(fis);
        sheet = workbook.getSheetAt(0);  // İlk sayfa kullanılıyor
        fis.close();
    }

    // DOI ve dergi isimlerini çekme
    public List<String[]> getDOIAndJournalNames() {
        List<String[]> doiAndJournalNames = new ArrayList<>();
        for (int i = 3; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row != null) {
                Cell doiCell = row.getCell(19);  // DOI sütunu (index 19)
                Cell journalNameCell = row.getCell(20);  // Dergi Adı sütunu (index 20)

                if (doiCell != null && !doiCell.getStringCellValue().trim().isEmpty()) {
                    String doi = doiCell.getStringCellValue();
                    String journalName = journalNameCell != null ? journalNameCell.getStringCellValue() : "";
                    doiAndJournalNames.add(new String[]{doi, journalName});
                }
            }
        }
        return doiAndJournalNames;
    }

    // Quartile seviyesini Excel'e yazma
    public void writeQuartileToExcel(String quartile, int rowIndex) {
        Row row = sheet.getRow(rowIndex + 3);  // Başlık satırını atladığımız için +3 ekleniyor
        if (row != null) {
            Cell cell = row.createCell(22);  // "q-level" sütunu (index 22)
            cell.setCellValue(quartile);
        }

        try (FileOutputStream fos = new FileOutputStream(new File(FILE_PATH))) {
            workbook.write(fos);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Workbook'u kapatma metodu
    public void close() throws Exception {
        if (workbook != null) {
            workbook.close();
        }
    }
}
