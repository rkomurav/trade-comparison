package com.trade.comparison.model;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Represents a Term Sheet document (typically Excel format)
 */
public class TermSheet extends TradeDocument {
    
    public TermSheet() {
        super();
    }
    
    public TermSheet(String filePath) {
        super(filePath);
    }
    
    @Override
    public void extractFields() {
        try {
            File file = new File(getFilePath());
            if (!file.exists()) {
                throw new IOException("Term Sheet file not found: " + getFilePath());
            }
            
            try (FileInputStream fis = new FileInputStream(file);
                 Workbook workbook = new XSSFWorkbook(fis)) {
                
                // Assume the first sheet contains the term sheet data
                Sheet sheet = workbook.getSheetAt(0);
                
                // Process each row to extract key-value pairs
                for (Row row : sheet) {
                    if (row.getPhysicalNumberOfCells() >= 2) {
                        Cell keyCell = row.getCell(0);
                        Cell valueCell = row.getCell(1);
                        
                        if (keyCell != null && valueCell != null) {
                            String key = getCellValueAsString(keyCell);
                            String value = getCellValueAsString(valueCell);
                            
                            if (key != null && !key.trim().isEmpty()) {
                                // Map Excel field names to standardized field names
                                String standardizedKey = standardizeFieldName(key);
                                addField(standardizedKey, value);
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error extracting fields from Term Sheet: " + e.getMessage());
        }
    }
    
    private String getCellValueAsString(Cell cell) {
        if (cell == null) {
            return "";
        }
        
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getLocalDateTimeCellValue().toString();
                } else {
                    // Format numeric values to avoid scientific notation
                    return String.valueOf(cell.getNumericCellValue());
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                try {
                    return cell.getStringCellValue();
                } catch (Exception e) {
                    try {
                        return String.valueOf(cell.getNumericCellValue());
                    } catch (Exception ex) {
                        return cell.getCellFormula();
                    }
                }
            default:
                return "";
        }
    }
    
    private String standardizeFieldName(String excelFieldName) {
        // Convert Excel field names to match the standardized field names used in TradeAgreement
        String fieldName = excelFieldName.trim().toLowerCase()
                .replace(" ", "")
                .replace("-", "")
                .replace("_", "")
                .replace("#", "")
                .replace(":", "");
        
        // Map common Excel field variations to standard field names
        switch (fieldName) {
            case "tradeid":
            case "tradereference":
            case "tradeno":
                return "tradeId";
            case "counterparty":
            case "counterpartyname":
            case "cp":
            case "counterpartyid":
                return "counterparty";
            case "tradedate":
            case "date":
            case "dateoftrade":
                return "tradeDate";
            case "settlementdate":
            case "settlement":
            case "settledate":
                return "settlementDate";
            case "currency":
            case "ccy":
                return "currency";
            case "notionalamount":
            case "notional":
            case "principal":
            case "amount":
                return "notionalAmount";
            case "interestrate":
            case "rate":
            case "fixedrate":
                return "interestRate";
            case "maturitydate":
            case "maturity":
                return "maturityDate";
            default:
                return fieldName;
        }
    }
}