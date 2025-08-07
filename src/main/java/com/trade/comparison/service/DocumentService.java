package com.trade.comparison.service;

import com.trade.comparison.model.DocumentComparison;
import com.trade.comparison.model.TermSheet;
import com.trade.comparison.model.TradeAgreement;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Service for fetching and processing trade documents
 */
@Service
public class DocumentService {
    
    /**
     * Fetches a list of available trade agreement files from the network folder
     * 
     * @param folderPath Path to the network folder containing trade agreements
     * @return List of file paths
     * @throws IOException If folder cannot be accessed
     */
    public List<String> getAvailableTradeAgreements(String folderPath) throws IOException {
        return getFilesInFolder(folderPath, ".pdf");
    }
    
    /**
     * Fetches a list of available term sheet files from the network folder
     * 
     * @param folderPath Path to the network folder containing term sheets
     * @return List of file paths
     * @throws IOException If folder cannot be accessed
     */
    public List<String> getAvailableTermSheets(String folderPath) throws IOException {
        return getFilesInFolder(folderPath, ".xlsx");
    }
    
    /**
     * Helper method to get files with specific extension from a folder
     * 
     * @param folderPath Path to the folder
     * @param extension File extension to filter by
     * @return List of file paths
     * @throws IOException If folder cannot be accessed
     */
    private List<String> getFilesInFolder(String folderPath, String extension) throws IOException {
        Path path = Paths.get(folderPath);
        if (!Files.exists(path)) {
            throw new IOException("Folder does not exist: " + folderPath);
        }
        
        try (Stream<Path> paths = Files.walk(path, 1)) {
            return paths
                    .filter(Files::isRegularFile)
                    .filter(p -> p.toString().toLowerCase().endsWith(extension.toLowerCase()))
                    .map(Path::toString)
                    .collect(Collectors.toList());
        }
    }
    
    /**
     * Loads a trade agreement from the specified file path
     * 
     * @param filePath Path to the trade agreement file
     * @return TradeAgreement object
     * @throws IOException If file cannot be accessed
     */
    public TradeAgreement loadTradeAgreement(String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists() || !file.isFile()) {
            throw new IOException("Trade agreement file not found: " + filePath);
        }
        
        TradeAgreement tradeAgreement = new TradeAgreement(filePath);
        tradeAgreement.extractFields();
        return tradeAgreement;
    }
    
    /**
     * Loads a term sheet from the specified file path
     * 
     * @param filePath Path to the term sheet file
     * @return TermSheet object
     * @throws IOException If file cannot be accessed
     */
    public TermSheet loadTermSheet(String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists() || !file.isFile()) {
            throw new IOException("Term sheet file not found: " + filePath);
        }
        
        TermSheet termSheet = new TermSheet(filePath);
        termSheet.extractFields();
        return termSheet;
    }
    
    /**
     * Compares a trade agreement and term sheet
     * 
     * @param tradeAgreementPath Path to the trade agreement file
     * @param termSheetPath Path to the term sheet file
     * @return DocumentComparison object containing comparison results
     * @throws IOException If files cannot be accessed
     */
    public DocumentComparison compareDocuments(String tradeAgreementPath, String termSheetPath) throws IOException {
        TradeAgreement tradeAgreement = loadTradeAgreement(tradeAgreementPath);
        TermSheet termSheet = loadTermSheet(termSheetPath);
        
        DocumentComparison comparison = new DocumentComparison(tradeAgreement, termSheet);
        comparison.compare();
        return comparison;
    }
}