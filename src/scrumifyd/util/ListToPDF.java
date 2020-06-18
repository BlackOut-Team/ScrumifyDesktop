package scrumifyd.util;

import be.quodlibet.boxable.BaseTable;
import be.quodlibet.boxable.datatable.DataTable;
import java.awt.AWTException;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javafx.scene.control.Alert;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;

/*
@author afsal-5502
 */
public class ListToPDF {

    public enum Orientation {
        PORTRAIT, LANDSCAPE
    };

//    public boolean doPrintToPdf(List<List> list, File saveLoc, Orientation orientation) throws AWTException {
//        try {
//            if (saveLoc == null) {
//                return false;
//            }
//            if (!saveLoc.getName().endsWith(".pdf")) {
//                saveLoc = new File(saveLoc.getAbsolutePath() + ".pdf");
//            }
//            try ( //Initialize Document
//                    PDDocument doc = new PDDocument()) {
//                PDPage page = new PDPage();
//                //Create a landscape page
//                if (orientation == Orientation.LANDSCAPE) {
//                    page.setMediaBox(new PDRectangle(PDRectangle.A4.getHeight(), PDRectangle.A4.getWidth()));
//                } else {
//                    page.setMediaBox(new PDRectangle(PDRectangle.A4.getWidth(), PDRectangle.A4.getHeight()));
//                }
//
//                doc.addPage(page);
//
//                //Initialize table
//                float margin = 10;
//                float tableWidth = page.getMediaBox().getWidth() - (2 * margin);
//                float yStartNewPage = page.getMediaBox().getHeight() - (2 * margin);
//                float yStart = yStartNewPage;
//                float bottomMargin = 0;
//
//                BaseTable dataTable = new BaseTable(yStart, yStartNewPage, bottomMargin, tableWidth, margin, doc, page, true,
//                        true);
//                DataTable t = new DataTable(dataTable, page);
//                t.addListToTable(list, DataTable.HASHEADER);
////                PDFont bf;
////                File f = new File("c:/windows/fonts/ARBLI___.ttf");
////                bf = PDType0Font.load(doc, f);
//
////                dataTable.drawTitle("ALL SCRUMIFY PROJECTS", bf, 0, tableWidth, yStart, "center", yStartNewPage, true);
//                dataTable.draw();
//                doc.save(saveLoc);
//
//            }
//
//            return true;
//        } catch (IOException ex) {
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setTitle("load pdf");
//            alert.setHeaderText("Results:");
//            alert.setContentText("Error loading pdf!");
//            alert.showAndWait();
//        }
//        return false;
//    }

}
