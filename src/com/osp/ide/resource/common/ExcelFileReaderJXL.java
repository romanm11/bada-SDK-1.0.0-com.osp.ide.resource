/**
 * 
 */
package com.osp.ide.resource.common;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import com.osp.ide.resource.Activator;
import com.osp.ide.resource.editform.OspFormEditor;
import com.osp.ide.resource.resourceexplorer.ResourceExplorer;
import com.osp.ide.resource.string.OspStringMarkup;
import com.osp.ide.resource.string.OspUIString;

/**
 * @author SRD1_CHJ
 *
 */
public class ExcelFileReaderJXL {

    private OspUIString string;
    private Sheet[] sheets;

    public ExcelFileReaderJXL(OspUIString string, String filePath) {
        this.string = string;
        
        readExcelXLS(filePath);
    }

    /**
     * @param filePath
     */
    private boolean readExcelXLS(String filePath) {
        File readFile = new File(filePath);      
        Workbook workBook;
        
        try {
            workBook = Workbook.getWorkbook(readFile);
        } catch (BiffException e) {
            Activator.setErrorMessage("ExcelFileReader.readExcelXLS()", 
                e.getMessage(), e);
            return false;
        } catch (IOException e) {
            Activator.setErrorMessage("ExcelFileReader.readExcelXLS()", 
                e.getMessage(), e);
            return false;
        }
        
        sheets = workBook.getSheets();
        
        return true;
    }
    
    /**
     * @param sheetIndex
     */
    public void importSheet(int sheetIndex, boolean isOverWrite) {
        if(sheets == null || sheets.length <= 0 || sheetIndex > sheets.length)
            return;
        Sheet sheet = sheets[sheetIndex];
        
        for(int i=1; i<sheet.getColumns(); i++) {
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
     */
    public void importColumn(int sheetIndex, int colIndex, boolean isOverWrite) {
        if(sheets == null || sheets.length <= 0 || sheetIndex > sheets.length)
            return;
        Sheet sheet = sheets[sheetIndex];
        Cell cell = sheet.getCell(colIndex, 0);
        
        String name = cell.getContents();
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
        
        for(int i=1; i<sheet.getRows(); i++) {
            cell = sheet.getCell(0, i);
            String textId = cell.getContents();
            cell = sheet.getCell(colIndex, i);
            String text = cell.getContents();
            
            table.insertImportText(textId, text, isOverWrite);
        }
        
        table.storeXML();
    }

    /**
     * @param sheetIndex
     * @param id
     * @return
     */
    public int getLangIdToColIndex(int sheetIndex, String id) {
        if(sheets == null || sheets.length <= 0 || sheetIndex > sheets.length)
            return -1;
        Sheet sheet = sheets[sheetIndex];
        
        for(int i=1; i<sheet.getColumns(); i++) {
            Cell cell = sheet.getCell(i, 0);
            String title = cell.getContents();
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
        ArrayList<String> ids = new ArrayList<String>();
        if(sheets == null || sheets.length <= 0 || sheetIndex > sheets.length)
            return ids.toArray(new String[0]);
        Sheet sheet = sheets[sheetIndex];
        
        for(int i=1; i<sheet.getColumns(); i++) {
            Cell cell = sheet.getCell(i, 0);
            String id = cell.getContents();
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
        if(sheets == null)
            return 0;
        
        return sheets.length;
    }

    /**
     * @param sheetIndex
     * @return
     */
    public int getRowCount(int sheetIndex) {
        if(sheets == null || sheets.length <= 0 || sheetIndex > sheets.length)
            return 0;
        
        return sheets[sheetIndex].getRows();
    }
   
    /**
     * @param sheetIndex
     * @return
     */
    public int getColCount(int sheetIndex) {
        if(sheets == null || sheets.length <= 0 || sheetIndex > sheets.length)
            return 0;
        
        return sheets[sheetIndex].getColumns();
    }

}
