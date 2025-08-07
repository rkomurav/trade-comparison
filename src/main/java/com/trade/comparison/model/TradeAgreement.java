package com.trade.comparison.model;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents a Trade Agreement document
 */
public class TradeAgreement extends TradeDocument {
    
    public TradeAgreement() {
        super();
    }
    
    public TradeAgreement(String filePath) {
        super(filePath);
    }
    
    @Override
    public void extractFields() {
        try {
            File file = new File(getFilePath());
            if (!file.exists()) {
                throw new IOException("Trade Agreement file not found: " + getFilePath());
            }
            
            try (PDDocument document = PDDocument.load(file)) {
                PDFTextStripper stripper = new PDFTextStripper();
                String text = stripper.getText(document);
                
                // Extract common fields from trade agreement
                extractTradeId(text);
                extractCounterparty(text);
                extractTradeDate(text);
                extractSettlementDate(text);
                extractCurrency(text);
                extractNotionalAmount(text);
                extractInterestRate(text);
                extractMaturityDate(text);
                // Add more field extractions as needed
            }
        } catch (IOException e) {
            System.err.println("Error extracting fields from Trade Agreement: " + e.getMessage());
        }
    }
    
    private void extractTradeId(String text) {
        extractFieldWithPattern(text, "Trade ID[:\\s]+(\\w+)", "tradeId");
    }
    
    private void extractCounterparty(String text) {
        extractFieldWithPattern(text, "Counterparty[:\\s]+([\\w\\s]+)", "counterparty");
    }
    
    private void extractTradeDate(String text) {
        extractFieldWithPattern(text, "Trade Date[:\\s]+(\\d{2}/\\d{2}/\\d{4}|\\d{4}-\\d{2}-\\d{2})", "tradeDate");
    }
    
    private void extractSettlementDate(String text) {
        extractFieldWithPattern(text, "Settlement Date[:\\s]+(\\d{2}/\\d{2}/\\d{4}|\\d{4}-\\d{2}-\\d{2})", "settlementDate");
    }
    
    private void extractCurrency(String text) {
        extractFieldWithPattern(text, "Currency[:\\s]+([A-Z]{3})", "currency");
    }
    
    private void extractNotionalAmount(String text) {
        extractFieldWithPattern(text, "Notional Amount[:\\s]+([$€£]?\\s?[\\d,]+\\.?\\d*)", "notionalAmount");
    }
    
    private void extractInterestRate(String text) {
        extractFieldWithPattern(text, "Interest Rate[:\\s]+(\\d+\\.?\\d*%)", "interestRate");
    }
    
    private void extractMaturityDate(String text) {
        extractFieldWithPattern(text, "Maturity Date[:\\s]+(\\d{2}/\\d{2}/\\d{4}|\\d{4}-\\d{2}-\\d{2})", "maturityDate");
    }
    
    private void extractFieldWithPattern(String text, String patternString, String fieldName) {
        Pattern pattern = Pattern.compile(patternString, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            addField(fieldName, matcher.group(1).trim());
        }
    }
}