package com.trade.comparison.service;

import com.trade.comparison.model.DocumentComparison;
import com.trade.comparison.model.TermSheet;
import com.trade.comparison.model.TradeAgreement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for DocumentService
 */
class DocumentServiceTest {

    @InjectMocks
    private DocumentService documentService;
    
    @TempDir
    Path tempDir;
    
    private Path agreementsDir;
    private Path termsheetsDir;
    
    @BeforeEach
    void setUp() throws IOException {
        MockitoAnnotations.openMocks(this);
        
        // Create test directories
        agreementsDir = tempDir.resolve("agreements");
        termsheetsDir = tempDir.resolve("termsheets");
        Files.createDirectory(agreementsDir);
        Files.createDirectory(termsheetsDir);
        
        // Create sample files
        Files.writeString(agreementsDir.resolve("agreement1.pdf"), "Sample PDF content");
        Files.writeString(agreementsDir.resolve("agreement2.pdf"), "Another PDF content");
        Files.writeString(agreementsDir.resolve("not_agreement.txt"), "Not a PDF file");
        
        Files.writeString(termsheetsDir.resolve("termsheet1.xlsx"), "Sample Excel content");
        Files.writeString(termsheetsDir.resolve("termsheet2.xlsx"), "Another Excel content");
        Files.writeString(termsheetsDir.resolve("not_termsheet.txt"), "Not an Excel file");
    }
    
    @Test
    void testGetAvailableTradeAgreements() throws IOException {
        List<String> agreements = documentService.getAvailableTradeAgreements(agreementsDir.toString());
        
        assertEquals(2, agreements.size());
        assertTrue(agreements.stream().anyMatch(path -> path.endsWith("agreement1.pdf")));
        assertTrue(agreements.stream().anyMatch(path -> path.endsWith("agreement2.pdf")));
        assertFalse(agreements.stream().anyMatch(path -> path.endsWith("not_agreement.txt")));
    }
    
    @Test
    void testGetAvailableTermSheets() throws IOException {
        List<String> termSheets = documentService.getAvailableTermSheets(termsheetsDir.toString());
        
        assertEquals(2, termSheets.size());
        assertTrue(termSheets.stream().anyMatch(path -> path.endsWith("termsheet1.xlsx")));
        assertTrue(termSheets.stream().anyMatch(path -> path.endsWith("termsheet2.xlsx")));
        assertFalse(termSheets.stream().anyMatch(path -> path.endsWith("not_termsheet.txt")));
    }
    
    @Test
    void testGetAvailableTradeAgreementsWithInvalidFolder() {
        Exception exception = assertThrows(IOException.class, () -> {
            documentService.getAvailableTradeAgreements(tempDir.resolve("non_existent_folder").toString());
        });
        
        assertTrue(exception.getMessage().contains("Folder does not exist"));
    }
    
    @Test
    void testGetAvailableTermSheetsWithInvalidFolder() {
        Exception exception = assertThrows(IOException.class, () -> {
            documentService.getAvailableTermSheets(tempDir.resolve("non_existent_folder").toString());
        });
        
        assertTrue(exception.getMessage().contains("Folder does not exist"));
    }
}