# Sample Documents Creation Summary

## Issue Description
Create sample trade agreements and term sheets

## Implementation Summary

I have created a comprehensive set of sample documents for testing and demonstration purposes with the Trade Comparison Tool. These include:

1. **Sample Trade Agreements** - Three sample trade agreements in text format that can be converted to PDF:
   - `trade_agreement_001.txt` - USD-denominated trade with Acme Financial Services
   - `trade_agreement_002.txt` - EUR-denominated trade with EuroBank AG
   - `trade_agreement_003.txt` - GBP-denominated trade with London Capital Partners

2. **Sample Term Sheets** - Three sample term sheets in text format that can be converted to Excel:
   - `term_sheet_001.txt` - Corresponding to trade agreement 001, using standard field names
   - `term_sheet_002.txt` - Corresponding to trade agreement 002, using alternative field names
   - `term_sheet_003.txt` - Corresponding to trade agreement 003, using alternative field names and additional fields

3. **Templates** - Template files to facilitate the creation of additional sample documents:
   - `trade_agreement_template.txt` - Template for creating new trade agreements
   - `term_sheet_template.txt` - Template with instructions for creating new term sheets

4. **Documentation** - A detailed README.md file in the samples directory that:
   - Documents the directory structure and sample files
   - Verifies that all required fields are present
   - Provides instructions for converting text files to PDF and Excel
   - Explains how to test the sample files with the application
   - Describes expected results

## Directory Structure

```
samples/
├── README.md
├── trade-agreements/
│   ├── trade_agreement_template.txt
│   ├── trade_agreement_001.txt
│   ├── trade_agreement_002.txt
│   └── trade_agreement_003.txt
└── term-sheets/
    ├── term_sheet_template.txt
    ├── term_sheet_001.txt
    ├── term_sheet_002.txt
    └── term_sheet_003.txt
```

## Key Features of Sample Documents

1. **Comprehensive Field Coverage**:
   - All sample documents include the 8 required fields: Trade ID, Counterparty, Trade Date, Settlement Date, Currency, Notional Amount, Interest Rate, and Maturity Date.

2. **Variety in Data**:
   - Different currencies (USD, EUR, GBP)
   - Different counterparties
   - Different dates and amounts
   - Different interest rates

3. **Testing Field Name Variations**:
   - Term sheet 001 uses standard field names
   - Term sheet 002 uses alternative field names (e.g., "Trade Reference" instead of "Trade ID")
   - Term sheet 003 uses different alternative field names and includes additional fields

4. **Realistic Content**:
   - Trade agreements include realistic legal language and structure
   - Term sheets follow the key-value pair format expected by the application

## Usage

The sample documents are ready for testing with the Trade Comparison Tool. Users need to:

1. Convert the text files to the appropriate format (PDF for trade agreements, Excel for term sheets)
2. Use the application to compare corresponding documents

## Next Steps

1. Convert the text files to their final formats (PDF and Excel)
2. Test the documents with the application to verify proper field extraction and comparison
3. Create additional sample documents as needed using the provided templates

## Conclusion

The created sample documents satisfy the requirements specified in the issue description. They provide a comprehensive set of test cases for the Trade Comparison Tool, covering various scenarios and edge cases to ensure the application functions correctly with different document formats and content variations.