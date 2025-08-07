package org.apache.poi.ss.usermodel;

/**
 * Mock implementation of Apache POI Cell interface
 */
public interface Cell {
    /**
     * Get the cell type
     * @return the cell type
     */
    CellType getCellType();
    
    /**
     * Get the string value of the cell
     * @return the string value
     */
    String getStringCellValue();
    
    /**
     * Get the numeric value of the cell
     * @return the numeric value
     */
    double getNumericCellValue();
    
    /**
     * Get the boolean value of the cell
     * @return the boolean value
     */
    boolean getBooleanCellValue();
    
    /**
     * Get the formula of the cell
     * @return the formula
     */
    String getCellFormula();
    
    /**
     * Get the local date time value of the cell
     * @return the local date time value
     */
    java.time.LocalDateTime getLocalDateTimeCellValue();
}