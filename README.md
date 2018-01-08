# RLC Analyser Set Up Guide
## Product Usage
When the RLC Analyser is started the user is prompted to browse to search for a new folder or RLC to process. Alternatively, the user can also process the default folder/file displayed. Instructions are provided on the top of the window. 

## User Set Up

### 1. Download the JAR file
Download the JAR file supplied on GitHub. This JAR file contains the latest built version of the RLC Analyser.


### 2. Run the JAR
In order to run the JAR file, Java 8 needs to be installed. To run the JAR double click on the icon. Skip the next step if permissions are not denied.
1. On Mac - permissions may need to be changed. Open System Preferences -> Security & Privacy -> General -> Open Anyway. 

## Developer Set Up

### 1. Eclipse Development Environment Set Up
#### 1.1. Install Eclipse and Create Workspace
1. Download the latest [Eclipse Oxygen - Eclipse for RCP ans RAP Developers](http://www.eclipse.org/downloads/packages/eclipse-rcp-and-rap-developers/oxygen1a)
2. Install Eclipse
3. Start Eclipse and create a workspace for the RLC Analyser Project
#### 1.2. Set Up JUnit
Settings Panel | Change
---------------|-------
Window (Menu) -> Preferences -> Java -> JUnit | Select the "add -ea to VM arguments when creating JUnit launch configuration" checkbox

### 2. Import Sources to Eclipse
#### 2.1. Disable "Build Automatically"
1. Uncheck the "Build Automatically" menu item in the "Project" menu.
2. Re-enable automatic building once the code has been successfully imported and the Target Platform configured and refreshed.
#### 2.2. Download the RLC Analyser Project
Either clone the project using Git or download the project into local drives. 
1. In the main page, click the "Clone or Download" button and select from one of the options given.
#### 2.3. Import RLC Analyser Project
1. Open the project import dialog: "File" menu > "Import..." menu item
2. Expand the "Maven" folder, select "Existing Maven Projects" and click the "Next > " button
3. Select the root directory as the directory of the downloaded project location
4. Click "Finish"


### 3. Compiling 
#### 3.1. Compiling the RLC Analyser
In the "Project" menu, check the "Build Automatically" menu item.


### 4. Starting RLC Analyser and Unit Tests
#### 4.1. Start RLC Analyser Program
1. From the "Run" menu, hover over "Run As", the select "Java Application".
2. A pop-up should appear asking to select Java Application. Select "RlcAnalyserConsole". Click "OK".
3. The program should have now started.
#### 4.2 Start Maven build
1. From the "Run" menu, hover over "Run As", then select "Maven Build".


## Contributing to the Project
1. Fork the orionhealth/rlc-analyser repository.
2. Create a new pull request when you want to push changes.
// TO BE COMPLETED: 
3. The pull request must pass the build and the code review process (at least 1 approval?).
