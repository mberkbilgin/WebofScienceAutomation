package utilities;

import java.util.List;

public class JournalAutomation {

    public static void main(String[] args) {
        try {
            // ExcelUtils nesnesini oluştur (parametre yerine default FILE_PATH kullanılacak)
            ExcelUtils excelUtils = new ExcelUtils();
            WebofScienceSearch webOfScience = new WebofScienceSearch();

            // Excel'den DOI ve dergi isimlerini çek
            List<String[]> doiAndJournalNames = excelUtils.getDOIAndJournalNames();

            // Her DOI ve Dergi ismi için işlem yap
            for (int i = 0; i < doiAndJournalNames.size(); i++) {
                String[] doiAndJournal = doiAndJournalNames.get(i);
                String doi = doiAndJournal[0];
                String journalName = doiAndJournal[1];

                System.out.println("Processing DOI: " + doi + ", Journal: " + journalName);

                // WebofScience'ta arama yap ve Q-Level'i al
                String qLevel = webOfScience.searchByDOIAndJournal(doi, journalName);

                // Sonucu Excel'e yaz
                excelUtils.writeQuartileToExcel(qLevel, i);  // Başlık satırını atlamaya gerek yok
            }

            // Kaynakları serbest bırak (close metodu eklendi)
            excelUtils.close();
            webOfScience.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
