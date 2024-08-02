# Flow Log Parser

## Overview
This program parses a file containing flow log data and maps each row to a tag based on a lookup table. The lookup table is defined as a CSV file with three columns: `dstport`, `protocol`, and `tag`. The combination of `dstport` and `protocol` determines the applicable tag. The output file contains counts of matches for each tag and each port/protocol combination.

## Assumptions
1. The lookup table and flow log data files are well-formed with no null values. The lookup table will always have at least three columns in the specified order.
2. The program is configured to run with JDK 21 or higher.
3. The program has been tested on a Unix machine and should theoretically work on Windows, although it has not been tested on that platform.

## How to Run the Program
1. **Check out the code base from GitHub.**
2. **Build the program with the following command:**
    ```bash
    ./gradlew distZip
    ```
3. **Navigate to the relative directory:**
    ```bash
    cd app/build/distributions
    ```
4. **Unzip the file `app.zip`:**
    ```bash
    unzip app.zip
    ```
5. **Navigate to the directory:**
    ```bash
    cd app/bin
    ```
6. **Execute the program with the following parameters in the specified order:**
    - Look up CSV file path
    - Flow log file path
    - Output file path
    ```bash
    ./app <lookup_csv_file_path> <flow_log_file_path> <output_file_path>
    ```
   All file paths can be provided as relative or absolute paths.

## Testing
- Basic unit tests have been created to cover essential functionality. However, not all paths have been exhaustively tested.
- A simple integration test has been written to test the main function.
- Additional test cases can be added to improve quality assurance.

## Example Usage
```bash
./app ../lookup_table.csv ../flow_log.txt ../output.txt
