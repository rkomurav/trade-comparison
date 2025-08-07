package com.trade.comparison.model;

import com.trade.comparison.service.AIDocumentService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * Class responsible for comparing trade documents and generating comparison results
 * Now enhanced with AI capabilities for more accurate comparisons
 */
public class DocumentComparison {
    private TradeAgreement tradeAgreement;
    private TermSheet termSheet;
    private Map<String, ComparisonResult> comparisonResults;
    private double matchPercentage;
    
    @Autowired
    private AIDocumentService aiService;
    
    public DocumentComparison(TradeAgreement tradeAgreement, TermSheet termSheet) {
        this.tradeAgreement = tradeAgreement;
        this.termSheet = termSheet;
        this.comparisonResults = new HashMap<>();
    }
    
    /**
     * Compares the trade agreement and term sheet documents using AI-enhanced techniques
     */
    public void compare() {
        if (tradeAgreement == null || termSheet == null) {
            throw new IllegalStateException("Both trade agreement and term sheet must be provided for comparison");
        }
        
        // Extract fields from both documents
        tradeAgreement.extractFields();
        termSheet.extractFields();
        
        // Use AI to enhance document fields extraction
        if (aiService != null) {
            aiService.enhanceDocumentFields(tradeAgreement);
            aiService.enhanceDocumentFields(termSheet);
        }
        
        // Get all unique field keys from both documents
        Set<String> allKeys = new HashSet<>();
        allKeys.addAll(tradeAgreement.getExtractedFields().keySet());
        allKeys.addAll(termSheet.getExtractedFields().keySet());
        
        int totalFields = 0;
        double totalSimilarityScore = 0.0;
        
        // Compare each field using semantic similarity
        for (String key : allKeys) {
            String agreementValue = tradeAgreement.getField(key);
            String termSheetValue = termSheet.getField(key);
            
            // Skip fields that don't exist in both documents
            if (agreementValue == null || termSheetValue == null) {
                ComparisonResult result = new ComparisonResult(key, agreementValue, termSheetValue, false);
                comparisonResults.put(key, result);
                continue;
            }
            
            totalFields++;
            
            // Calculate semantic similarity using AI
            double similarityScore = 0.0;
            if (aiService != null) {
                similarityScore = aiService.calculateSemanticSimilarity(agreementValue, termSheetValue);
            } else {
                // Fallback to traditional comparison if AI service is not available
                String normalizedAgreementValue = normalizeValue(agreementValue);
                String normalizedTermSheetValue = normalizeValue(termSheetValue);
                similarityScore = normalizedAgreementValue.equals(normalizedTermSheetValue) ? 1.0 : 0.0;
            }
            
            totalSimilarityScore += similarityScore;
            
            // Consider a match if similarity is above threshold (0.8 or 80%)
            boolean isMatch = similarityScore >= 0.8;
            
            ComparisonResult result = new ComparisonResult(key, agreementValue, termSheetValue, isMatch, similarityScore);
            comparisonResults.put(key, result);
        }
        
        // Calculate match percentage based on average similarity score
        this.matchPercentage = totalFields > 0 ? (totalSimilarityScore / totalFields) * 100 : 0;
    }
    
    /**
     * Normalizes a value for comparison
     */
    private String normalizeValue(String value) {
        if (value == null) {
            return "";
        }
        
        // Remove currency symbols, commas, and normalize whitespace
        return value.trim()
                .replaceAll("[$€£]", "")
                .replaceAll(",", "")
                .replaceAll("\\s+", " ")
                .toLowerCase();
    }
    
    /**
     * Generates a JSON representation of the comparison results
     */
    public String toJson() {
        JSONObject result = new JSONObject();
        
        // Add document information
        result.put("tradeAgreementFile", tradeAgreement.getFileName());
        result.put("termSheetFile", termSheet.getFileName());
        result.put("matchPercentage", Math.round(matchPercentage * 100.0) / 100.0); // Round to 2 decimal places
        
        // Add comparison details
        JSONArray differences = new JSONArray();
        for (ComparisonResult comparisonResult : comparisonResults.values()) {
            if (!comparisonResult.isMatch()) {
                JSONObject difference = new JSONObject();
                difference.put("field", comparisonResult.getFieldName());
                difference.put("tradeAgreementValue", comparisonResult.getTradeAgreementValue() != null ? 
                        comparisonResult.getTradeAgreementValue() : "N/A");
                difference.put("termSheetValue", comparisonResult.getTermSheetValue() != null ? 
                        comparisonResult.getTermSheetValue() : "N/A");
                differences.put(difference);
            }
        }
        
        result.put("differences", differences);
        return result.toString(2); // Pretty print with 2 space indentation
    }
    
    public TradeAgreement getTradeAgreement() {
        return tradeAgreement;
    }
    
    public TermSheet getTermSheet() {
        return termSheet;
    }
    
    public Map<String, ComparisonResult> getComparisonResults() {
        return comparisonResults;
    }
    
    public double getMatchPercentage() {
        return matchPercentage;
    }
    
    /**
     * Inner class to hold comparison result for a single field
     * Enhanced with similarity score from AI analysis
     */
    public static class ComparisonResult {
        private final String fieldName;
        private final String tradeAgreementValue;
        private final String termSheetValue;
        private final boolean isMatch;
        private final double similarityScore;
        
        public ComparisonResult(String fieldName, String tradeAgreementValue, String termSheetValue, boolean isMatch) {
            this(fieldName, tradeAgreementValue, termSheetValue, isMatch, isMatch ? 1.0 : 0.0);
        }
        
        public ComparisonResult(String fieldName, String tradeAgreementValue, String termSheetValue, 
                               boolean isMatch, double similarityScore) {
            this.fieldName = fieldName;
            this.tradeAgreementValue = tradeAgreementValue;
            this.termSheetValue = termSheetValue;
            this.isMatch = isMatch;
            this.similarityScore = similarityScore;
        }
        
        public String getFieldName() {
            return fieldName;
        }
        
        public String getTradeAgreementValue() {
            return tradeAgreementValue;
        }
        
        public String getTermSheetValue() {
            return termSheetValue;
        }
        
        public boolean isMatch() {
            return isMatch;
        }
        
        public double getSimilarityScore() {
            return similarityScore;
        }
    }
}