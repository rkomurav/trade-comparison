# Gradle Wrapper Setup Instructions

This project uses Gradle as its build tool. The Gradle wrapper files have been partially set up, but you need to complete the setup by adding the `gradle-wrapper.jar` file.

## Completing the Gradle Wrapper Setup

1. You need to obtain the `gradle-wrapper.jar` file using one of the following methods:

   - **Option 1**: If you have Gradle installed globally, run:
     ```
     gradle wrapper
     ```

   - **Option 2**: Download the jar file manually from Maven Central:
     ```
     https://repo1.maven.org/maven2/org/gradle/gradle-wrapper/8.5/gradle-wrapper-8.5.jar
     ```
     Rename the downloaded file to `gradle-wrapper.jar` and place it in the `gradle/wrapper/` directory.

   - **Option 3**: Copy the `gradle-wrapper.jar` file from another Gradle project.

2. Once you've added the `gradle-wrapper.jar` file to the `gradle/wrapper/` directory, you can use the Gradle wrapper to build and run the application.

## Building the Application

After completing the Gradle wrapper setup, you can build the application using:

```
# On Windows
.\gradlew.bat build

# On Unix/Linux/macOS
./gradlew build
```

## Running the Application

To run the application:

```
# On Windows
.\gradlew.bat bootRun

# On Unix/Linux/macOS
./gradlew bootRun
```

The application will be available at `http://localhost:8080`

## Troubleshooting

If you encounter permission issues with the `gradlew` script on Unix/Linux/macOS, make it executable:

```
chmod +x gradlew
```

If you encounter any other issues, please refer to the Gradle documentation or open an issue in the project repository.