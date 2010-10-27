/**
 * 
 */
package com.osp.ide.resource.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xssf.extractor.XSSFExcelExtractor;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.osp.ide.resource.Activator;
import com.osp.ide.resource.editform.OspFormEditor;
import com.osp.ide.resource.resourceexplorer.ResourceExplorer;
import com.osp.ide.resource.string.OspStringMarkup;
import com.osp.ide.resource.string.OspUIString;

/**
 * @author SRD1_CHJ
 * *.xls poi-3.6-20091214.jar
 * 
 * *.xlsx poi-ooxml-3.620091214.jar
 *        poi-ooxml-schemas-3.6-20091214.jar
 *        xmlbeans-2.3.0.jar
 *        dom4j-1.6.1.jar
 * 
 */
public class ExcelFileReaderPOI {

    private OspUIString string;
    private HSSFWorkbook wb;
    private XSSFWorkbook xb;
    private String filePath;

    public ExcelFileReaderPOI(OspUIString string, String filePath) {
        this.string = string;
        this.filePath = filePath;
        
        if(isXLS())
            readExcelXLS();
        else if(isXLSX())
            readExcelXLSX();
    }

    /**
     * @return
     */
    private boolean isXLS() {
        if(filePath == null || filePath.isEmpty())
            return false;
        File file = new File(filePath);
        String name = file.getName();
        String ext = name.substring(name.indexOf('.'));
        
        if(ext.toUpperCase(Locale.getDefault()).equals(".XLS"))
            return true;
        return false;
    }

    /**
     * @return
     */
    private boolean isXLSX() {
        if(filePath == null || filePath.isEmpty())
            return false;
        File file = new File(filePath);
        String name = file.getName();
        String ext = name.substring(name.indexOf('.'));
        
        if(ext.toUpperCase(Locale.getDefault()).equals(".XLSX"))
            return true;
        return false;
    }

    /**
     * 
     */
    private void readExcelXLS() {
        FileInputStream fis = null;
        POIFSFileSystem fs = null;
        
        try {
            fis = new FileInputStream(filePath);
            fs = new POIFSFileSystem(fis);
            wb = new HSSFWorkbook(fs);
        } catch (FileNotFoundException e) {
            Activator.setErrorMessage("ExcelFileReaderPOI.readExcelXLS()", 
                e.getMessage(), e);
            return;
        } catch (IOException e) {
            Activator.setErrorMessage("ExcelFileReaderPOI.readExcelXLS()", 
                e.getMessage(), e);
            return;
        }
    }

    /**
     * 
     */
    private void readExcelXLSX() {
        XSSFExcelExtractor extractor = null;
        
        try {
            xb = new XSSFWorkbook(filePath);
            
            extractor = new XSSFExcelExtractor(xb);
            extractor.setFormulasNotResults(true);
            extractor.setIncludeSheetNames(false);
            
        } catch (FileNotFoundException e) {
            Activator.setErrorMessage("ExcelFileReaderPOI.readExcelXLSX()", 
                e.getMessage(), e);
            return;
        } catch (IOException e) {
            Activator.setErrorMessage("ExcelFileReaderPOI.readExcelXLSX()", 
                e.getMessage(), e);
            return;
        }
    }

    /**
     * @param sheetIndex
     * @param isOverWrite
     */
    public void importSheet(int sheetIndex, boolean isOverWrite) {
        if(isXLS())
            importSheetXLS(sheetIndex, isOverWrite);
        else if(isXLSX())
            importSheetXLSX(sheetIndex, isOverWrite);
    }

    /**
     * @param sheetIndex
     * @param isOverWrite
     */
    private void importSheetXLS(int sheetIndex, boolean isOverWrite) {
        if(wb == null || sheetIndex < 0 || sheetIndex > wb.getNumberOfSheets() - 1)
            return;
        HSSFSheet sheet = wb.getSheetAt(sheetIndex);
        if(sheet == null)
            return;
        
        int firstRow = sheet.getFirstRowNum();
        HSSFRow row = sheet.getRow(firstRow);
        int firstCol = row.getFirstCellNum();
        int lastCol = row.getLastCellNum();
        
        for(int i=firstCol+1; i<lastCol; i++) {
            importColumn(sheetIndex, i, isOverWrite);
        }
    }

    /**
     * @param sheetIndex
     * @param isOverWrite
     */
    private void importSheetXLSX(int sheetIndex, boolean isOverWrite) {
        if(xb == null || sheetIndex < 0 || sheetIndex > xb.getNumberOfSheets() - 1)
            return;
        XSSFSheet sheet = xb.getSheetAt(sheetIndex);
        if(sheet == null)
            return;
        
        int firstRow = sheet.getFirstRowNum();
        XSSFRow row = sheet.getRow(firstRow);
        int firstCol = row.getFirstCellNum();
        int lastCol = row.getLastCellNum();
        
        for(int i=firstCol+1; i<lastCol; i++) {
            importColumn(sheetIndex, i, isOverWrite);
        }
    }

    /**
     * 
     */
    public void refreshEditor() {
        ResourceExplorer view = ResourceExplorer.getResourceView();
        if(view != null)
            view.reopenStringEditor("");
        OspFormEditor.refreshStringEditor();
    }

    /**
     * @param sheetIndex
     * @param colIndex
     * @param isOverWrite
     */
    public void importColumn(int sheetIndex, int colIndex, boolean isOverWrite) {
        if(isXLS())
            importColumnXLS(sheetIndex, colIndex, isOverWrite);
        else if(isXLSX())
            importColumnXLSX(sheetIndex, colIndex, isOverWrite);
    }

    /**
     * @param sheetIndex
     * @param colIndex
     * @param isOverWrite
     */
    private void importColumnXLS(int sheetIndex, int colIndex, boolean isOverWrite) {
        if(wb == null || sheetIndex < 0 || sheetIndex > wb.getNumberOfSheets() - 1)
            return;
        HSSFSheet sheet = wb.getSheetAt(sheetIndex);
        if(sheet == null)
            return;

        int firstRow = sheet.getFirstRowNum();
        int lastRow = sheet.getLastRowNum();
        
        HSSFRow row = sheet.getRow(firstRow);
        int firstCol = row.getFirstCellNum();
        if(colIndex < firstCol)
            return;
        
        String name = row.getCell(colIndex).getRichStringCellValue().getString();
        String id = OspStringMarkup.getLanguageId(name);
        if(id == null || id.isEmpty()) {
            id = name;
            name = OspStringMarkup.getLanguageName(id);
            if(name == null || name.isEmpty())
                return;
        }
        
        OspStringMarkup table = string.m_Strings.get(id);
        if(table == null)
            table = string.InsertString(id);
        
        for(int i=firstRow + 1; i<=lastRow; i++) {
            row = sheet.getRow(i);
            String textId = row.getCell(firstCol).getRichStringCellValue().getString();
            String text = row.getCell(colIndex).getRichStringCellValue().getString();
            
            table.insertImportText(textId, text, isOverWrite);
        }
    }

    /**
     * @param sheetIndex
     * @param colIndex
     * @param isOverWrite
     */
    private void importColumnXLSX(int sheetIndex, int colIndex, boolean isOverWrite) {
        if(xb == null || sheetIndex < 0 || sheetIndex > xb.getNumberOfSheets() - 1)
            return;
        XSSFSheet sheet = xb.getSheetAt(sheetIndex);
        if(sheet == null)
            return;

        int firstRow = sheet.getFirstRowNum();
        int lastRow = sheet.getLastRowNum();
        
        XSSFRow row = sheet.getRow(firstRow);
        int firstCol = row.getFirstCellNum();
        if(colIndex < firstCol)
            return;
        String id = row.getCell(colIndex).getRichStringCellValue().getString();
        String name = OspStringMarkup.getLanguageName(id);
        if(name == null || name.isEmpty())
            return;
        
        OspStringMarkup table = string.m_Strings.get(id);
        if(table == null)
            table = string.InsertString(id);
        
        for(int i=firstRow + 1; i<=lastRow; i++) {
            row = sheet.getRow(i);
            String textId = row.getCell(firstCol).getRichStringCellValue().getString();
            String text = row.getCell(colIndex).getRichStringCellValue().getString();
            
            table.insertImportText(textId, text, isOverWrite);
        }
    }

    /**
     * @param sheetIndex
     * @param id
     * @return
     */
    public int getLangIdToColIndex(int sheetIndex, String id) {
        if(isXLS())
            return getLangIdToColIndexXLS(sheetIndex, id);
        else if(isXLSX())
            return getLangIdToColIndexXLSX(sheetIndex, id);
        
        return -1;
    }

    /**
     * @param sheetIndex
     * @param id
     * @return
     */
    private int getLangIdToColIndexXLS(int sheetIndex, String id) {
        if(wb == null || sheetIndex < 0 || sheetIndex > wb.getNumberOfSheets() - 1)
            return -1;
        HSSFSheet sheet = wb.getSheetAt(sheetIndex);
        if(sheet == null)
            return -1;
        
        int firstRow = sheet.getFirstRowNum();
        HSSFRow row = sheet.getRow(firstRow);
        
        int firstCol = row.getFirstCellNum();
        int lastCol = row.getLastCellNum();
        for(int i=firstCol+1; i<lastCol; i++) {
            String title = row.getCell(i).getRichStringCellValue().getString();
            if(title != null && title.equals(id))
                return i;
        }
        return -1;
    }

    /**
     * @param sheetIndex
     * @param id
     * @return
     */
    private int getLangIdToColIndexXLSX(int sheetIndex, String id) {
        if(xb == null || sheetIndex < 0 || sheetIndex > xb.getNumberOfSheets() - 1)
            return -1;
        XSSFSheet sheet = xb.getSheetAt(sheetIndex);
        if(sheet == null)
            return -1;
        
        int firstRow = sheet.getFirstRowNum();
        XSSFRow row = sheet.getRow(firstRow);
        
        int firstCol = row.getFirstCellNum();
        int lastCol = row.getLastCellNum();
        for(int i=firstCol+1; i<lastCol; i++) {
            String title = row.getCell(i).getRichStringCellValue().getString();
            if(title != null && title.equals(id))
                return i;
        }
        return -1;
    }

    /**
     * @param sheetIndex
     * @return
     */
    public String[] getLanguageIds(int sheetIndex) {
        if(isXLS())
            return getLanguageXLS(sheetIndex);
        else if(isXLSX())
            return getLanguageXLSX(sheetIndex);
        
        ArrayList<String> ids = new ArrayList<String>();
        return ids.toArray(new String[0]);
    }

    /**
     * @param sheetIndex
     * @return
     */
    private String[] getLanguageXLS(int sheetIndex) {
        ArrayList<String> ids = new ArrayList<String>();
        if(wb == null || sheetIndex < 0 || sheetIndex > wb.getNumberOfSheets() - 1)
            return ids.toArray(new String[0]);
        HSSFSheet sheet = wb.getSheetAt(sheetIndex);
        if(sheet == null)
            return ids.toArray(new String[0]);
        
        int firstRow = sheet.getFirstRowNum();
        HSSFRow row = sheet.getRow(firstRow);
        
        int firstCol = row.getFirstCellNum();
        int lastCol = row.getLastCellNum();
        for(int i=firstCol+1; i<lastCol; i++) {
            String id = row.getCell(i).getRichStringCellValue().getString();
            String name = OspStringMarkup.getLanguageName(id);
            if(name != null && !name.isEmpty())
                ids.add(id);
        }
        
        return ids.toArray(new String[0]);
    }

    /**
     * @param sheetIndex
     * @return
     */
    private String[] getLanguageXLSX(int sheetIndex) {
        ArrayList<String> ids = new ArrayList<String>();
        if(xb == null || sheetIndex < 0 || sheetIndex > xb.getNumberOfSheets() - 1)
            return ids.toArray(new String[0]);
        XSSFSheet sheet = xb.getSheetAt(sheetIndex);
        if(sheet == null)
            return ids.toArray(new String[0]);
        
        int firstRow = sheet.getFirstRowNum();
        XSSFRow row = sheet.getRow(firstRow);
        
        int firstCol = row.getFirstCellNum();
        int lastCol = row.getLastCellNum();
        for(int i=firstCol+1; i<lastCol; i++) {
            String id = row.getCell(i).getRichStringCellValue().getString();
            String name = OspStringMarkup.getLanguageName(id);
            if(name != null && !name.isEmpty())
                ids.add(id);
        }
        
        return ids.toArray(new String[0]);
    }

    /**
     * @return
     */
    public int getSheetCount() {
        if(isXLS())
            return getSheetCountXLS();
        else if(isXLSX())
            return getSheetCountXLSX();
        
        return 0;
    }

    /**
     * @return
     */
    private int getSheetCountXLS() {
        if(wb == null)
            return 0;
        
        return wb.getNumberOfSheets();
    }

    /**
     * @return
     */
    private int getSheetCountXLSX() {
        if(xb == null)
            return 0;
        
        return xb.getNumberOfSheets();
    }

    /**
     * @param sheetIndex
     * @return
     */
    public int getRowCount(int sheetIndex) {
        if(isXLS())
            return getRowCountXLS(sheetIndex);
        else if(isXLSX())
            return getRowCountXLSX(sheetIndex);
        
        return 0;
    }
   
    /**
     * @param sheetIndex
     * @return
     */
    private int getRowCountXLS(int sheetIndex) {
        if(wb == null || sheetIndex < 0 || sheetIndex > wb.getNumberOfSheets() - 1)
            return 0;
        HSSFSheet sheet = wb.getSheetAt(sheetIndex);
        if(sheet == null)
            return 0;
        
        int firstRow = sheet.getFirstRowNum();
        int lastRow = sheet.getLastRowNum();
        return lastRow - firstRow + 1;
    }

    /**
     * @param sheetIndex
     * @return
     */
    private int getRowCountXLSX(int sheetIndex) {
        if(xb == null || sheetIndex < 0 || sheetIndex > xb.getNumberOfSheets() - 1)
            return 0;
        XSSFSheet sheet = xb.getSheetAt(sheetIndex);
        if(sheet == null)
            return 0;
        
        int firstRow = sheet.getFirstRowNum();
        int lastRow = sheet.getLastRowNum();
        return lastRow - firstRow + 1;
    }

    /**
     * @param sheetIndex
     * @return
     */
    public int getColCount(int sheetIndex) {
        if(isXLS())
            return getColCountXLS(sheetIndex);
        else if(isXLSX())
            return getColCountXLSX(sheetIndex);
        
        return 0;
    }

    /**
     * @param sheetIndex
     * @return
     */
    private int getColCountXLS(int sheetIndex) {
        if(wb == null || sheetIndex < 0 || sheetIndex > wb.getNumberOfSheets() - 1)
            return 0;
        HSSFSheet sheet = wb.getSheetAt(sheetIndex);
        if(sheet == null)
            return 0;
        
        int firstRow = sheet.getFirstRowNum();
        HSSFRow row = sheet.getRow(firstRow);
        int firstCol = row.getFirstCellNum();
        int lastCol = row.getLastCellNum();

        return lastCol - firstCol;
    }

    /**
     * @param sheetIndex
     * @return
     */
    private int getColCountXLSX(int sheetIndex) {
        if(xb == null || sheetIndex < 0 || sheetIndex > xb.getNumberOfSheets() - 1)
            return 0;
        XSSFSheet sheet = xb.getSheetAt(sheetIndex);
        if(sheet == null)
            return 0;
        
        int firstRow = sheet.getFirstRowNum();
        XSSFRow row = sheet.getRow(firstRow);
        int firstCol = row.getFirstCellNum();
        int lastCol = row.getLastCellNum();

        return lastCol - firstCol;
    }
}
