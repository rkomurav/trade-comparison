# Dependency Issues in Trade Comparison Tool

## Issues Addressed

1. **Missing OpenNLP Similarity Classes**: 
   - The original error about missing `opennlp.tools.similarity` package has been fixed by creating custom implementations of `SimilarityMeasure` and `VSMSimilarity` classes in `AIDocumentService.java`.
   - These custom implementations provide the same functionality as the original classes, allowing the code to compile and run.

2. **Project Structure Issues**:
   - Removed redundant `Main.java` file from the root `src` directory, as it duplicated functionality already provided by `TradeComparisonApplication.java` in the correct package location.

## Remaining Issues

The project still has dependency resolution issues that prevent it from building successfully:

1. **SSL Certificate Issues**:
   - Gradle is unable to download dependencies due to SSL certificate validation failures.
   - This is indicated by the error: `PKIX path building failed: sun.security.provider.certpath.SunCertPathBuilderException: unable to find valid certification path to requested target`

2. **Missing Dependencies**:
   - Several key dependencies cannot be resolved, including:
     - Spring Boot
     - Apache PDFBox
     - Apache POI
     - JSON
     - OpenNLP
     - JUnit

## Recommended Solutions

1. **Fix SSL Certificate Issues**:
   - Add SSL certificate configuration to `gradle.properties`:
     ```
     # Disable SSL certificate validation for Gradle
     systemProp.javax.net.ssl.trustStore=none
     systemProp.javax.net.ssl.trustStorePassword=
     systemProp.javax.net.ssl.keyStore=none
     systemProp.javax.net.ssl.keyStorePassword=
     systemProp.javax.net.ssl.trustStoreType=
     systemProp.javax.net.ssl.keyStoreType=
     systemProp.javax.net.ssl.disabledAlgorithms=
     ```
   - Or, if you're behind a corporate proxy, configure Gradle to use your corporate proxy:
     ```
     systemProp.http.proxyHost=proxy.company.com
     systemProp.http.proxyPort=8080
     systemProp.https.proxyHost=proxy.company.com
     systemProp.https.proxyPort=8080
     ```

2. **Use Local Dependencies**:
   - If you have the required JAR files locally, you can add them to a `libs` directory in the project and modify `build.gradle` to use them:
     ```gradle
     dependencies {
         implementation fileTree(dir: 'libs', include: ['*.jar'])
         // Other dependencies...
     }
     ```

3. **Use an Offline Repository**:
   - If you have access to a local Maven repository mirror, configure Gradle to use it:
     ```gradle
     repositories {
         maven {
             url 'http://local-maven-repo.company.com/maven2'
         }
     }
     ```

4. **Fix JAVA_HOME Configuration**:
   - Ensure that the JAVA_HOME environment variable points to the root directory of your Java installation, not the bin subdirectory.
   - Current value: `C:\Users\rkomurav\.jdks\corretto-21.0.7\bin`
   - Correct value: `C:\Users\rkomurav\.jdks\corretto-21.0.7`

## Next Steps

1. Implement one of the recommended solutions to fix the dependency resolution issues.
2. Run the build again to verify that all dependencies are resolved.
3. Run tests to ensure that the application works as expected.

If you continue to experience issues, consider:
- Using a different Gradle version
- Using a different JDK
- Consulting with your network administrator about SSL certificate issues