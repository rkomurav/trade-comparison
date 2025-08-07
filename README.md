# Trade Document Comparison Tool

A Java 21 application that compares trade agreements and term sheets, extracts fields from both documents, and identifies discrepancies between them.

## Features

- Fetch trade agreements (PDF) and term sheets (Excel) from network folders
- Extract key fields from both document types using AI/NLP techniques
- Intelligent field extraction beyond simple pattern matching
- Semantic comparison of fields using natural language processing
- Calculate match percentage using AI-powered similarity scoring
- Present results in JSON format with detailed similarity metrics
- User-friendly Angular frontend

## Technology Stack

- **Backend**: Java 21, Spring Boot
- **Frontend**: Angular, Bootstrap
- **Build Tool**: Gradle
- **Document Processing**: Apache PDFBox, Apache POI
- **AI/ML/NLP**: Apache OpenNLP, Stanford CoreNLP, DeepLearning4J

## Getting Started

### Prerequisites

~~- Java 21 or higher~~
- Gradle 7.0 or higher

### Building the Application

```bash
# Clone the repository
git clone https://github.com/yourusername/trade-comparison-tool.git
cd trade-comparison-tool

# Build the application
./gradlew build
```

### Running the Application

```bash
./gradlew bootRun
```

The application will be available at `http://localhost:8080`

## Usage

1. Open the application in your web browser
2. Enter the network folder path for trade agreements (containing PDF files)
3. Click "Fetch Agreements" and select a trade agreement from the dropdown
4. Enter the network folder path for term sheets (containing Excel files)
5. Click "Fetch Term Sheets" and select a term sheet from the dropdown
6. Click "Compare Documents" to analyze the selected files
7. View the comparison results, including match percentage and any differences found

## Document Format Requirements

### Trade Agreements (PDF)

Trade agreements should be in PDF format and contain fields such as:
- Trade ID
- Counterparty
- Trade Date
- Settlement Date
- Currency
- Notional Amount
- Interest Rate
- Maturity Date

### Term Sheets (Excel)

Term sheets should be in Excel format (.xlsx) with key-value pairs in the first two columns:
- Column A: Field name
- Column B: Field value

## AI Capabilities

The application leverages AI and NLP techniques to enhance document processing and comparison:

### Intelligent Field Extraction
- Uses natural language processing to identify and extract fields from documents
- Goes beyond simple regex pattern matching to understand document context
- Can recognize variations in field names and formats across different document types

### Semantic Comparison
- Employs vector space models to calculate semantic similarity between field values
- Understands that different phrasings can represent the same information
- Provides similarity scores (0-1) for each field comparison instead of binary match/no-match

### Adaptive Learning
- The system can improve its extraction and comparison capabilities over time
- Leverages machine learning models from OpenNLP and Stanford CoreNLP
- Handles edge cases and variations in document formatting

## API Endpoints

The application provides the following REST API endpoints:

- `GET /api/documents/trade-agreements?folderPath={path}` - Get available trade agreements
- `GET /api/documents/term-sheets?folderPath={path}` - Get available term sheets
- `GET /api/documents/compare?tradeAgreementPath={path}&termSheetPath={path}` - Compare documents

## License

This project is licensed under the MIT License - see the LICENSE file for details.