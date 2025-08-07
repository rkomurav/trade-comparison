package com.trade.comparison.service;

import com.trade.comparison.model.TradeDocument;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class AIDocumentServiceTest {

    @InjectMocks
    private AIDocumentService aiDocumentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCalculateSemanticSimilarity() {
        // Test exact matches
        assertEquals(1.0, aiDocumentService.calculateSemanticSimilarity("100000", "100000"), 0.01);
        assertEquals(1.0, aiDocumentService.calculateSemanticSimilarity("John Smith", "John Smith"), 0.01);
        
        // Test similar values with different formats
        double similarityScore = aiDocumentService.calculateSemanticSimilarity("$100,000", "100000");
        assertTrue(similarityScore > 0.8, "Similarity score should be high for normalized values");
        
        // Test different values
        double differentScore = aiDocumentService.calculateSemanticSimilarity("John Smith", "Jane Doe");
        assertTrue(differentScore < 0.5, "Similarity score should be low for different values");
        
        // Test null values
        assertEquals(0.0, aiDocumentService.calculateSemanticSimilarity(null, "test"), 0.01);
        assertEquals(0.0, aiDocumentService.calculateSemanticSimilarity("test", null), 0.01);
        assertEquals(0.0, aiDocumentService.calculateSemanticSimilarity(null, null), 0.01);
    }

    @Test
    void testExtractEntitiesWithNLP() {
        // Test extraction of trade ID
        String text = "The Trade ID is ABC123 for this agreement.";
        Map<String, String> entities = aiDocumentService.extractEntitiesWithNLP(text);
        assertEquals("ABC123", entities.get("tradeId"));
        
        // Test extraction of multiple entities
        String complexText = "Trade ID: XYZ789\nCounterparty: Acme Corp\nTrade Date: 2023-01-15\n" +
                "Settlement Date: 2023-01-20\nCurrency: USD\nNotional Amount: $500,000\n" +
                "Interest Rate: 5.25%\nMaturity Date: 2024-01-15";
        
        Map<String, String> complexEntities = aiDocumentService.extractEntitiesWithNLP(complexText);
        assertTrue(complexEntities.containsKey("tradeId"));
        assertTrue(complexEntities.containsKey("counterparty"));
        assertTrue(complexEntities.containsKey("tradeDate"));
    }

    @Test
    void testEnhanceDocumentFields() {
        // Create a mock document
        TestTradeDocument document = new TestTradeDocument();
        document.addField("tradeId", "ABC123");
        
        // Create text that contains additional fields not in the document
        String rawText = "Trade ID: ABC123\nCounterparty: Acme Corp\nTrade Date: 2023-01-15";
        document.setRawText(rawText);
        
        // Enhance the document
        aiDocumentService.enhanceDocumentFields(document);
        
        // Verify that new fields were added
        assertNotNull(document.getField("counterparty"));
        assertNotNull(document.getField("tradeDate"));
        assertEquals("Acme", document.getField("counterparty"));
        assertEquals("2023-01-15", document.getField("tradeDate"));
        
        // Verify that existing fields were not overwritten
        assertEquals("ABC123", document.getField("tradeId"));
    }
    
    // Test implementation of TradeDocument for testing
    private static class TestTradeDocument extends TradeDocument {
        private String rawText;
        
        public TestTradeDocument() {
            super();
        }
        
        public void setRawText(String rawText) {
            this.rawText = rawText;
        }
        
        @Override
        public void extractFields() {
            // Do nothing for test
        }
        
        @Override
        public String toString() {
            return rawText;
        }
    }
}