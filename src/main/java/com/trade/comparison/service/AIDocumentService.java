package com.trade.comparison.service;

import com.trade.comparison.model.TradeDocument;
import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.tokenize.SimpleTokenizer;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.util.Span;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Arrays;
import java.util.HashSet;

/**
 * Service that uses AI/ML/NLP techniques for document processing and comparison
 */
@Service
public class AIDocumentService {

    /**
     * Interface for measuring similarity between two strings
     */
    private interface SimilarityMeasure {
        /**
         * Calculate similarity between two strings
         * @param text1 First text
         * @param text2 Second text
         * @return Similarity score between 0 and 1
         */
        double getSimilarity(String text1, String text2);
    }
    
    /**
     * Vector Space Model implementation of SimilarityMeasure
     */
    private static class VSMSimilarity implements SimilarityMeasure {
        @Override
        public double getSimilarity(String text1, String text2) {
            if (text1 == null || text2 == null || text1.isEmpty() || text2.isEmpty()) {
                return 0.0;
            }
            
            // Simple implementation using word overlap
            String[] words1 = text1.toLowerCase().split("\\s+");
            String[] words2 = text2.toLowerCase().split("\\s+");
            
            HashSet<String> set1 = new HashSet<>(Arrays.asList(words1));
            HashSet<String> set2 = new HashSet<>(Arrays.asList(words2));
            
            // Count common words
            int commonWords = 0;
            for (String word : set1) {
                if (set2.contains(word)) {
                    commonWords++;
                }
            }
            
            // Calculate Jaccard similarity: intersection size / union size
            return (double) commonWords / (set1.size() + set2.size() - commonWords);
        }
    }

    private final Tokenizer tokenizer;
    private final SimilarityMeasure similarityMeasure;

    public AIDocumentService() {
        this.tokenizer = SimpleTokenizer.INSTANCE;
        this.similarityMeasure = new VSMSimilarity();
    }

    /**
     * Uses NLP to extract entities from text that might be missed by regular expressions
     * 
     * @param text The text to analyze
     * @return Map of extracted entities and their values
     */
    public Map<String, String> extractEntitiesWithNLP(String text) {
        Map<String, String> entities = new HashMap<>();
        
        try {
            // Extract dates
            extractDates(text, entities);
            
            // Extract monetary amounts
            extractMonetaryAmounts(text, entities);
            
            // Extract organization names (counterparties)
            extractOrganizations(text, entities);
            
            // Extract trade IDs and other identifiers
            extractIdentifiers(text, entities);
        } catch (Exception e) {
            System.err.println("Error in NLP entity extraction: " + e.getMessage());
        }
        
        return entities;
    }
    
    /**
     * Extracts dates from text using NLP
     */
    private void extractDates(String text, Map<String, String> entities) {
        // In a real implementation, this would use a pre-trained model
        // For now, we'll use a simplified approach
        String[] tokens = tokenizer.tokenize(text);
        
        // Simple date pattern detection (this would be more sophisticated with actual models)
        for (int i = 0; i < tokens.length - 2; i++) {
            if (tokens[i].equalsIgnoreCase("trade") && tokens[i+1].equalsIgnoreCase("date")) {
                if (i + 2 < tokens.length) {
                    entities.put("tradeDate", tokens[i+2]);
                }
            }
            
            if (tokens[i].equalsIgnoreCase("settlement") && tokens[i+1].equalsIgnoreCase("date")) {
                if (i + 2 < tokens.length) {
                    entities.put("settlementDate", tokens[i+2]);
                }
            }
            
            if (tokens[i].equalsIgnoreCase("maturity") && tokens[i+1].equalsIgnoreCase("date")) {
                if (i + 2 < tokens.length) {
                    entities.put("maturityDate", tokens[i+2]);
                }
            }
        }
    }
    
    /**
     * Extracts monetary amounts from text using NLP
     */
    private void extractMonetaryAmounts(String text, Map<String, String> entities) {
        // In a real implementation, this would use a pre-trained model
        String[] tokens = tokenizer.tokenize(text);
        
        // Simple monetary amount detection
        for (int i = 0; i < tokens.length - 2; i++) {
            if (tokens[i].equalsIgnoreCase("notional") && tokens[i+1].equalsIgnoreCase("amount")) {
                if (i + 2 < tokens.length) {
                    entities.put("notionalAmount", tokens[i+2]);
                }
            }
            
            if (tokens[i].equalsIgnoreCase("interest") && tokens[i+1].equalsIgnoreCase("rate")) {
                if (i + 2 < tokens.length) {
                    entities.put("interestRate", tokens[i+2]);
                }
            }
        }
    }
    
    /**
     * Extracts organization names from text using NLP
     */
    private void extractOrganizations(String text, Map<String, String> entities) {
        // In a real implementation, this would use a pre-trained model
        String[] tokens = tokenizer.tokenize(text);
        
        // Simple organization detection
        for (int i = 0; i < tokens.length - 1; i++) {
            if (tokens[i].equalsIgnoreCase("counterparty")) {
                if (i + 1 < tokens.length) {
                    entities.put("counterparty", tokens[i+1]);
                }
            }
        }
    }
    
    /**
     * Extracts identifiers from text using NLP
     */
    private void extractIdentifiers(String text, Map<String, String> entities) {
        // In a real implementation, this would use a pre-trained model
        String[] tokens = tokenizer.tokenize(text);
        
        // Simple identifier detection
        for (int i = 0; i < tokens.length - 2; i++) {
            if (tokens[i].equalsIgnoreCase("trade") && tokens[i+1].equalsIgnoreCase("id")) {
                if (i + 2 < tokens.length) {
                    entities.put("tradeId", tokens[i+2]);
                }
            }
        }
    }
    
    /**
     * Compares two field values using semantic similarity rather than exact matching
     * 
     * @param value1 First value to compare
     * @param value2 Second value to compare
     * @return Similarity score between 0 and 1
     */
    public double calculateSemanticSimilarity(String value1, String value2) {
        if (value1 == null || value2 == null) {
            return 0.0;
        }
        
        // Normalize values
        value1 = normalizeValue(value1);
        value2 = normalizeValue(value2);
        
        // If values are identical after normalization, return 1.0
        if (value1.equals(value2)) {
            return 1.0;
        }
        
        // Calculate semantic similarity using vector space model
        try {
            return similarityMeasure.getSimilarity(value1, value2);
        } catch (Exception e) {
            System.err.println("Error calculating similarity: " + e.getMessage());
            return 0.0;
        }
    }
    
    /**
     * Enhances document fields with AI-extracted information
     * 
     * @param document The document to enhance
     */
    public void enhanceDocumentFields(TradeDocument document) {
        // Get the raw text from the document (this would need to be implemented in TradeDocument)
        String rawText = getRawTextFromDocument(document);
        
        // Extract entities using NLP
        Map<String, String> nlpEntities = extractEntitiesWithNLP(rawText);
        
        // Merge NLP-extracted entities with existing fields
        for (Map.Entry<String, String> entry : nlpEntities.entrySet()) {
            // Only add if the field doesn't already exist or is empty
            if (document.getField(entry.getKey()) == null || document.getField(entry.getKey()).isEmpty()) {
                document.addField(entry.getKey(), entry.getValue());
            }
        }
    }
    
    /**
     * Gets raw text from a document (placeholder method)
     */
    private String getRawTextFromDocument(TradeDocument document) {
        // This would need to be implemented based on the document type
        // For now, we'll just concatenate all field values
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : document.getExtractedFields().entrySet()) {
            sb.append(entry.getKey()).append(" ").append(entry.getValue()).append(" ");
        }
        return sb.toString();
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
}