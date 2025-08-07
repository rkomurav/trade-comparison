package com.trade.comparison.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Base class for trade documents (agreements and term sheets)
 */
public abstract class TradeDocument {
    private String filePath;
    private String fileName;
    private Map<String, String> extractedFields;
    
    public TradeDocument() {
        this.extractedFields = new HashMap<>();
    }
    
    public TradeDocument(String filePath) {
        this();
        this.filePath = filePath;
        this.fileName = filePath.substring(filePath.lastIndexOf('\\') + 1);
    }
    
    public String getFilePath() {
        return filePath;
    }
    
    public void setFilePath(String filePath) {
        this.filePath = filePath;
        this.fileName = filePath.substring(filePath.lastIndexOf('\\') + 1);
    }
    
    public String getFileName() {
        return fileName;
    }
    
    public Map<String, String> getExtractedFields() {
        return extractedFields;
    }
    
    public void setExtractedFields(Map<String, String> extractedFields) {
        this.extractedFields = extractedFields;
    }
    
    public void addField(String key, String value) {
        this.extractedFields.put(key, value);
    }
    
    public String getField(String key) {
        return this.extractedFields.get(key);
    }
    
    /**
     * Method to be implemented by subclasses to extract fields from the document
     */
    public abstract void extractFields();
}