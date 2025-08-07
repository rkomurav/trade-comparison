package com.trade.comparison.controller;

import com.trade.comparison.model.DocumentComparison;
import com.trade.comparison.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * REST controller for document comparison operations
 */
@RestController
@RequestMapping("/api/documents")
@CrossOrigin(origins = "*")
public class DocumentComparisonController {
    
    private final DocumentService documentService;
    
    @Autowired
    public DocumentComparisonController(DocumentService documentService) {
        this.documentService = documentService;
    }
    
    /**
     * Get available trade agreements from the network folder
     * 
     * @param folderPath Path to the network folder
     * @return List of available trade agreement files
     */
    @GetMapping("/trade-agreements")
    public ResponseEntity<?> getTradeAgreements(@RequestParam String folderPath) {
        try {
            List<String> agreements = documentService.getAvailableTradeAgreements(folderPath);
            return ResponseEntity.ok(agreements);
        } catch (IOException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to retrieve trade agreements: " + e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
    
    /**
     * Get available term sheets from the network folder
     * 
     * @param folderPath Path to the network folder
     * @return List of available term sheet files
     */
    @GetMapping("/term-sheets")
    public ResponseEntity<?> getTermSheets(@RequestParam String folderPath) {
        try {
            List<String> termSheets = documentService.getAvailableTermSheets(folderPath);
            return ResponseEntity.ok(termSheets);
        } catch (IOException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to retrieve term sheets: " + e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
    
    /**
     * Compare a trade agreement and term sheet
     * 
     * @param tradeAgreementPath Path to the trade agreement file
     * @param termSheetPath Path to the term sheet file
     * @return Comparison results in JSON format
     */
    @GetMapping("/compare")
    public ResponseEntity<?> compareDocuments(
            @RequestParam String tradeAgreementPath,
            @RequestParam String termSheetPath) {
        try {
            DocumentComparison comparison = documentService.compareDocuments(tradeAgreementPath, termSheetPath);
            return ResponseEntity.ok(comparison.toJson());
        } catch (IOException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to compare documents: " + e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
}