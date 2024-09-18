# WebOfScience : Selenium Automation Framework

## Purpose

The aim of this project is to automate the process of searching for journals and extracting quartile information from the Web of Science platform using DOI and journal names. This project helps to:
- Reduce manual effort in testing and data retrieval.
- Increase accuracy and speed of data collection from the Web of Science.
- Store the results (such as quartile information) back into an Excel file.

## Project Setup

### Prerequisites:

1. **Java 17** or later:
   - Ensure Java is installed. You can download it from [here](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html).
   - After installation, verify it using the following command in the terminal:
     ```bash
     java -version
     ```

2. **Maven**:
   - Download and install Maven from [here](https://maven.apache.org/download.cgi).
   - Verify it by running:
     ```bash
     mvn -version
     ```

3. **Chrome WebDriver**:
   - Download the correct version of Chrome WebDriver from [here](https://developer.chrome.com/docs/chromedriver/downloads/version-selection?hl=tr) based on the version of Chrome installed on your machine.
   - Make sure to set the path to `chromedriver` in your project as shown below:
     ```java
     System.setProperty("webdriver.chrome.driver", "/path/to/your/chromedriver");
     ```

4. **Apache POI (for Excel)**:
   - Apache POI is used for reading and writing data in Excel files. Maven automatically manages the dependencies for Apache POI.

### Excel File Path Configuration

In this project, the path to the Excel file that contains DOI and journal data is hardcoded in the `ExcelUtils` class. Before running the project, you need to update this path according to your local environment.

1. **Locate the Excel file path in `ExcelUtils`**:
   
   Open the `ExcelUtils` class, and locate the following line:

   ```java
   private static final String FILE_PATH = "/path/to/your/excel/file/data.xlsx";
   ```

2. **Replace the file path**:
   Replace `"/path/to/your/excel/file/data.xlsx"` with the actual path to your Excel file.

   For example, if your Excel file is stored in your Documents folder on a Mac, you would update the file path as follows:

   ```java
   private static final String FILE_PATH = "/Users/your-username/Documents/data.xlsx";
   ```

3. **Ensure the Excel file structure is correct**:
   The Excel file should have a specific structure that the project expects:
   - The **DOI** and **journal name** columns should be present and in the correct order as used by the code.
   - If no DOI is present in a row, the project will skip that row.

### Installing and Running the Project

1. **Clone the repository**:
   ```bash
   git clone https://github.com/your-username/WebOfScienceAutomation.git
   ```

2. **Navigate to the project directory**:
   ```bash
   cd WebOfScienceAutomation
   ```

3. **Configure ChromeDriver**:
   - Set the correct path for the `chromedriver` in the `WebofScienceSearch` class:
     ```java
     System.setProperty("webdriver.chrome.driver", "/path/to/your/chromedriver");
     ```

4. **Configure the Excel file path**:
   - Make sure to update the Excel file path in `ExcelUtils` as explained in the **Excel File Path Configuration** section.

5. **Install dependencies**:
   - Run Maven to download and install all required dependencies using the following command:
     ```bash
     mvn clean install
     ```

6. **Run the tests**:
   - Run the project using Maven:
     ```bash
     mvn test
     ```

## Tools

- **Java** - The project is written in Java, using version 17.
- **Maven** - The project is built using Maven, which manages dependencies and automates test execution.
- **Selenium WebDriver** - Selenium WebDriver is used to automate the interactions with the Web of Science platform, including searching for journals and extracting information.
- **Apache POI** - Apache POI is used for reading from and writing to Excel files.
- **Chrome WebDriver** - ChromeDriver is used to run the tests in a Google Chrome browser.

### Running Tests Locally

To execute the tests locally:

1. Ensure all prerequisites (Java, Maven, ChromeDriver) are installed and properly configured.
2. Clone the project and navigate to the project root directory.
3. Run the following command:
   ```bash
   mvn test
   ```

### Maven Dependencies

The project uses Maven to manage dependencies, and the key dependencies are listed in the `pom.xml` file:

```xml
<dependencies>
    <!-- Selenium WebDriver -->
    <dependency>
        <groupId>org.seleniumhq.selenium</groupId>
        <artifactId>selenium-java</artifactId>
        <version>4.0.0</version>
    </dependency>

    <!-- Apache POI (for Excel) -->
    <dependency>
        <groupId>org.apache.poi</groupId>
        <artifactId>poi</artifactId>
        <version>5.0.0</version>
    </dependency>

    <dependency>
        <groupId>org.apache.poi</groupId>
        <artifactId>poi-ooxml</artifactId>
        <version>5.0.0</version>
    </dependency>
</dependencies>
```

---
