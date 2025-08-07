# Sample Trade Agreements and Term Sheets

This directory contains sample trade agreements and term sheets for testing and demonstration purposes with the Trade Comparison Tool.

## Directory Structure

- `trade-agreements/`: Contains sample trade agreement files (text format that should be converted to PDF)
- `term-sheets/`: Contains sample term sheet files (text format that should be converted to Excel)

## Sample Files

### Trade Agreements

| Filename | Trade ID | Counterparty | Currency | Description |
|----------|----------|--------------|----------|-------------|
| trade_agreement_001.txt | TR00123456 | Acme Financial Services | USD | $5M fixed-rate loan at 4.25% |
| trade_agreement_002.txt | TR00789012 | EuroBank AG | EUR | €3.5M fixed-rate loan at 2.75% |
| trade_agreement_003.txt | TR00456789 | London Capital Partners | GBP | £2.75M fixed-rate loan at 3.50% |

### Term Sheets

| Filename | Trade ID | Counterparty | Currency | Notes |
|----------|----------|--------------|----------|-------|
| term_sheet_001.txt | TR00123456 | Acme Financial Services | USD | Uses standard field names |
| term_sheet_002.txt | TR00789012 | EuroBank AG | EUR | Uses alternative field names |
| term_sheet_003.txt | TR00456789 | London Capital Partners | GBP | Uses alternative field names and includes additional fields |

## Field Verification

All sample documents contain the required fields as specified in the application:

1. Trade ID
2. Counterparty
3. Trade Date
4. Settlement Date
5. Currency
6. Notional Amount
7. Interest Rate
8. Maturity Date

## Usage Instructions

### Converting Text Files to PDF (Trade Agreements)

1. Open the `.txt` file in a text editor or word processor
2. Format the document as needed (add proper spacing, fonts, etc.)
3. Save or export the document as PDF
4. Place the PDF file in the same directory, using the same base filename (e.g., `trade_agreement_001.pdf`)

### Converting Text Files to Excel (Term Sheets)

1. Open Microsoft Excel or compatible spreadsheet software
2. Create a new workbook
3. Enter the field names from the `.txt` file in Column A
4. Enter the corresponding values in Column B
5. Save the file as `.xlsx` in the same directory, using the same base filename (e.g., `term_sheet_001.xlsx`)

## Testing with the Application

To test these sample files with the Trade Comparison Tool:

1. Convert the text files to PDF (trade agreements) and Excel (term sheets) as described above
2. Start the Trade Comparison Tool application
3. Enter the path to the `samples/trade-agreements` directory when prompted for trade agreements
4. Select one of the trade agreements from the dropdown
5. Enter the path to the `samples/term-sheets` directory when prompted for term sheets
6. Select the corresponding term sheet from the dropdown
7. Click "Compare Documents" to analyze the selected files

## Expected Results

When comparing matching trade agreements and term sheets (e.g., `trade_agreement_001.pdf` with `term_sheet_001.xlsx`), the application should report a high match percentage, as the key fields contain identical information.

When comparing non-matching documents, the application should identify and report the discrepancies between the documents.

## Notes

- The sample term sheets use various alternative field names to demonstrate the application's field name standardization functionality
- Term sheet 003 includes additional fields beyond the required ones to demonstrate that the application can handle extra information
- The text files provided here need to be converted to the appropriate format (PDF for trade agreements, Excel for term sheets) before they can be used with the application