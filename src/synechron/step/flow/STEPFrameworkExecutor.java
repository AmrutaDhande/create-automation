package synechron.step.flow;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import synechron.step.STEPFrameworkDriver;
import synechron.step.common.STEPKeywordLibrary;
import synechron.step.exceptions.STEPFrameworkExecutorException;
import synechron.step.exceptions.STEPFrameworkMissingORObjectException;
import synechron.step.framework.objects.STEPFrameworkVO;
import synechron.step.framework.objects.STEPTestCaseStatus;
import synechron.step.framework.objects.STEPTestCaseVO;
import synechron.step.framework.objects.STEPTestSuiteStatus;
import synechron.step.testing.STEPTestResultGenerator;
import synechron.step.util.STEPDataUtility;

public class STEPFrameworkExecutor 
{
	private static Hashtable<String, String[]> testSuiteObjectRep = new Hashtable<String, String[]>();  
	private File testSuiteObjectRepFile = null;
	private FileInputStream testSuiteObjectRepIS = null;
	public static List<Workbook> testSuiteObjectRepWB = new ArrayList<Workbook>();
	public static Sheet testDataSheet;
	public static String inputDataValue=null;
	public static Workbook businessFunctionWB = null;
	public static Hashtable<String, String[]> testDataContainer = new Hashtable<String, String[]>();
	//private static String screenshotLocation = STEPFrameworkDriver.getFrameworkLocation() + "\\output\\Results\\";
	Logger frameworkLogger = STEPFrameworkDriver.getFrameworkLogger();
	/***
	 * Author	: 
	 * Purpose	: To read the Business Functions file workbook into businessFunctionWB and start run the Test Suites.
	 * @param frameworkVO
	 * @return
	 * @throws ParserConfigurationException
	 */
	public STEPFrameworkVO runFrameworkTests(STEPFrameworkVO frameworkVO) throws ParserConfigurationException,STEPFrameworkExecutorException{
		try {
			File businessFunctionWBFile = new File(frameworkVO.getFrameworkLocation() + "\\input\\ResuableScenarios\\ReusableScenarios.xls");
			FileInputStream businessFunctionWBFileIS = new FileInputStream(businessFunctionWBFile);
			businessFunctionWB = WorkbookFactory.create(businessFunctionWBFileIS);
			Logger frameworkLogger = STEPFrameworkDriver.getFrameworkLogger();
			frameworkLogger.log(Level.INFO, "Starting Test Execution");
			runTests(frameworkVO, frameworkLogger);
		}
		catch (UnknownHostException e) {
			frameworkLogger.log(Level.SEVERE, "Unknown Host Exception occured while runing Framework Tests" + e.getMessage());
		} catch (InvalidFormatException e) {
			frameworkLogger.log(Level.SEVERE, "Invalid Format Exception occured while runing Framework Tests" + e.getMessage());
		} catch (FileNotFoundException e) {
			frameworkLogger.log(Level.SEVERE, "File Not Found Exception occured while runing Framework Tests" + e.getMessage());
		} catch (IOException e) {
			frameworkLogger.log(Level.SEVERE, "IO Exception occured while runing Framework Tests" + e.getMessage());
		}

		return frameworkVO;
	}
	/***
	 * Author	: 
	 * Purpose	: To read the TestCases of the Respective Test Suites from the frameworkVO class
	 * @param frameworkVO
	 * @param frameworkLogger
	 * @throws IOException
	 * @throws InvalidFormatException
	 * @throws FileNotFoundException
	 * @throws UnknownHostException
	 * @throws ParserConfigurationException
	 */
	private void runTests(STEPFrameworkVO frameworkVO, Logger frameworkLogger) throws IOException,
	InvalidFormatException, FileNotFoundException, UnknownHostException, ParserConfigurationException,STEPFrameworkExecutorException 
	{
		int testSuiteCount = 0;
		int testCaseCount = 0;
		String[] columnData = null;
		testSuiteCount = frameworkVO.getTestSuiteList().size();
		int testSuiteIterator = 0;
		int testCaseIterator =0;
		String testSuiteBrowserType="";
				
		//Loop the Test Suites iteration
		for (testSuiteIterator = 0; testSuiteIterator < testSuiteCount; testSuiteIterator++) 
		{
			if (!frameworkVO.getTestSuiteList().get(testSuiteIterator).getTestSuiteObjRepFileName().isEmpty()) 
			{
				clearTestSuiteObjectRepWB();
				String testSuiteORFiles[]=frameworkVO.getTestSuiteList().get(testSuiteIterator).getTestSuiteObjRepFileName().replace("\\", "\\\\").split(";");
				for(int testSuiteORCounter=0;testSuiteORCounter<testSuiteORFiles.length;testSuiteORCounter++)
				{
					testSuiteObjectRepFile = new File(frameworkVO.getFrameworkLocation() +testSuiteORFiles[testSuiteORCounter].trim());
					testSuiteObjectRepIS = new FileInputStream(testSuiteObjectRepFile);
					Workbook testSuiteORWB=WorkbookFactory.create(testSuiteObjectRepIS);
					setTestSuiteObjectRepWB(testSuiteORWB);
				}
			} 
			else 
			{
				frameworkVO.getTestSuiteList().get(testSuiteIterator).setTestSuiteStatus(STEPTestSuiteStatus.FAILED.toString());
				frameworkVO.getTestSuiteList().get(testSuiteIterator).setTestSuiteMessage("Could not find the Object Repository file specified.");
			}
			File testSuiteFile = new File(frameworkVO.getFrameworkLocation() + frameworkVO.getTestSuiteList().get(testSuiteIterator).getTestSuiteFileName());
			Workbook testSuiteWB = WorkbookFactory.create(new FileInputStream(testSuiteFile));
			testDataSheet = testSuiteWB.getSheet("TestData");
			testDataContainer = new Hashtable<String, String[]>();
			Row testDataLastRow = testDataSheet.getRow(testDataSheet.getLastRowNum());
			Row testDataLabelRow = testDataSheet.getRow(0);
			int columnCount = testDataLastRow.getLastCellNum();
			testSuiteBrowserType=frameworkVO.getTestSuiteList().get(testSuiteIterator).getTestSuiteBrowserType();
			String autURL=frameworkVO.getTestSuiteList().get(testSuiteIterator).getTestURL();
			
			for (int testDataColumnCounter = 3; testDataColumnCounter < columnCount; testDataColumnCounter++) 
			{
				columnData = new String[testDataSheet.getLastRowNum()];
				for (int testDataRowCounter = 1; testDataRowCounter <= testDataSheet.getLastRowNum(); testDataRowCounter++)
				{
					Cell cellData = testDataSheet.getRow(testDataRowCounter).getCell(testDataColumnCounter, Row.CREATE_NULL_AS_BLANK);
					switch(cellData.getCellType()) {
					case (Cell.CELL_TYPE_STRING):
						columnData[testDataRowCounter - 1] = cellData.getStringCellValue();
					break;
					case (Cell.CELL_TYPE_NUMERIC):
						columnData[testDataRowCounter - 1] = Double.toString(cellData.getNumericCellValue());
					break;
					}
				}
				testDataContainer.put(testDataLabelRow.getCell(testDataColumnCounter, Row.CREATE_NULL_AS_BLANK).getStringCellValue(), 
						columnData);
			}

			frameworkLogger.log(Level.INFO, ">>>Starting test case execution for Test Suite " + frameworkVO.getTestSuiteList().get(testSuiteIterator).getTestSuiteName());
			testCaseCount = frameworkVO.getTestSuiteList().get(testSuiteIterator).getTestCaseList().size();
			// Loop the test cases of each test suite from the controller file.
			for (testCaseIterator = 0; testCaseIterator < testCaseCount; testCaseIterator++) 
			{
				frameworkLogger.log(Level.INFO, ">>>>>>Starting execution of test case " + 
				frameworkVO.getTestSuiteList().get(testSuiteIterator).getTestCaseList().get(testCaseIterator).getTestCaseId());
				Sheet testCaseSheet = testSuiteWB.getSheet(frameworkVO.getTestSuiteList().get(testSuiteIterator).getTestCaseList().get(testCaseIterator).getTestCaseId());
				STEPTestCaseVO currTestCaseVO = frameworkVO.getTestSuiteList().get(testSuiteIterator).getTestCaseList().get(testCaseIterator);
				//String autURL = frameworkVO.getConfigData().get("Application_URL");
				long testCaseStartedAt = System.currentTimeMillis();
				frameworkVO.getTestSuiteList().get(testSuiteIterator).getTestCaseList().get(testCaseIterator).setTestCaseStartedAt(STEPDataUtility.formatDateForXMLReport(testCaseStartedAt).replace(" ", "T"));
				if (frameworkVO.getTestSuiteList().get(testSuiteIterator).getTestSuiteStatus().equals("TO_BE_EXECUTED")) 
				{
					if (frameworkVO.getTestSuiteList().get(testSuiteIterator).getTestCaseList().get(testCaseIterator).getExecuteTestCase().equals("TO_BE_EXECUTED")) 
					{
						// call the executeTestCase to execute the test cases
						currTestCaseVO = executeTestCase(testCaseSheet, testDataContainer, 
								currTestCaseVO, autURL,testSuiteBrowserType, frameworkLogger);
					} 
					else 
					{
						currTestCaseVO.setTestCaseStatus("Not Executed");
						currTestCaseVO.setExecutedSteps("Not Executed");
					}
				} 
				else 
				{
					frameworkVO.getTestSuiteList().get(testSuiteIterator).setTestSuiteStatus("Not Executed");
					currTestCaseVO.setTestCaseStatus("Not Executed");
					currTestCaseVO.setExecutedSteps("Not Executed");
				}
				frameworkVO.getTestSuiteList().get(testSuiteIterator).getTestCaseList().get(testCaseIterator).setExecutedSteps(currTestCaseVO.getExecutedSteps());
				frameworkVO.getTestSuiteList().get(testSuiteIterator).getTestCaseList().get(testCaseIterator).setTestCaseStatus(currTestCaseVO.getTestCaseStatus());

				long testCaseEndedAt = System.currentTimeMillis();
				frameworkVO.getTestSuiteList().get(testSuiteIterator).getTestCaseList().get(testCaseIterator).setTestCaseEndedAt(STEPDataUtility.formatDateForXMLReport(testCaseEndedAt).replace(" ", "T"));
				frameworkVO.getTestSuiteList().get(testSuiteIterator).getTestCaseList().get(testCaseIterator).setTestCaseExecutionTime(Long.toString((testCaseEndedAt - testCaseStartedAt)/1000));
				frameworkLogger.log(Level.INFO, "<<<<<<Execution of test case " +frameworkVO.getTestSuiteList().get(testSuiteIterator).getTestCaseList().get(testCaseIterator).getTestCaseId() +" completed.");
				//Create XML Document and Generate HTML with HTML File formats.
				STEPTestResultGenerator.createXMLDocument(frameworkVO,testSuiteIterator,testCaseIterator);
				STEPTestResultGenerator.createHTMLReport(testSuiteIterator,testCaseIterator);
				STEPTestResultGenerator.formatHTML(testSuiteIterator,testCaseIterator);
			}
			frameworkLogger.log(Level.INFO, "<<<Completed test case execution for Test Suite " + frameworkVO.getTestSuiteList().get(testSuiteIterator).getTestSuiteName());

		}
		frameworkLogger.log(Level.INFO, "Completed Test Execution");
	}
	/***
	 * Author	: 
	 * Purpose	: Initialize the WebDriver to run the application based on the browser type and run the test steps from each test case.
	 * @param testCaseSheet
	 * @param testDataContainer
	 * @param currTestCaseVO
	 * @param autURL
	 * @param browser_type
	 * @param frameworkLogger
	 * @return
	 */
	private STEPTestCaseVO executeTestCase(Sheet testCaseSheet, Hashtable<String, String[]>testDataContainer, 
			STEPTestCaseVO currTestCaseVO, String autURL, String browser_type, Logger frameworkLogger) throws STEPFrameworkExecutorException
			{
		try
		{
			//initialize the WebDriver to run the application based on the browser type
			WebDriver driver = initializeWebDriver(browser_type);
			driver.get(autURL);
			boolean flag=false;
			int stepCount = testCaseSheet.getLastRowNum();
			int currStepNum = 1;
			//System.out.println(testCaseSheet.getSheetName());
			for (currStepNum = 1; currStepNum <= stepCount; currStepNum++) 
			{
				Row currStepRow = testCaseSheet.getRow(currStepNum);
				Cell currStepKeywordCell = currStepRow.getCell(0, Row.CREATE_NULL_AS_BLANK);
				String currStepKeyword = currStepKeywordCell.getStringCellValue();
				if (null != currStepKeyword && !currStepKeyword.equals("")) 
				{
					Cell currStepObjectCell = currStepRow.getCell(1, Row.CREATE_NULL_AS_BLANK);
					String currStepObject = currStepObjectCell.getStringCellValue();
					//String inputDataValue=STEPFrameworkDriver.strInputData.split("-")[1];
					Cell currStepObjectValueCell = currStepRow.getCell(Integer.parseInt(currTestCaseVO.getTestCaseInputData().split("-")[1])+1, Row.CREATE_NULL_AS_BLANK);
					String currStepObjectValue = "";
					switch (currStepObjectValueCell.getCellType()) 
					{
					case Cell.CELL_TYPE_STRING: 
						currStepObjectValue = currStepObjectValueCell.getStringCellValue();
						break;
					case Cell.CELL_TYPE_NUMERIC:
						Double doubleValue=currStepObjectValueCell.getNumericCellValue();
						currStepObjectValue = Integer.toString(doubleValue.intValue());
						//currStepObjectValue = Double.toString(currStepObjectValueCell.getNumericCellValue());
						break;
					}
					if (currStepObjectValue.equals("GETDATA")) 
					{
						currStepObjectValue = testDataContainer.get(currStepRow.getCell(3).getStringCellValue())[currTestCaseVO.getTestCaseDataRowId() - 1];
					}
					String currStepStatus = STEPKeywordLibrary.executeStep(currStepKeyword, driver, currStepObject, currStepObjectValue, currTestCaseVO.getTestCaseDataRowId(),browser_type);
					if (!(currStepStatus.contains("::Failed ")) && !currTestCaseVO.getTestCaseStatus().contains((STEPTestCaseStatus.FAILED.toString()))) 
					{
						currTestCaseVO.setTestCaseStatus(STEPTestCaseStatus.PASSED.toString());
					}  
					else 
					{
						currTestCaseVO.setTestCaseStatus(STEPTestSuiteStatus.FAILED.toString());
					}
					currTestCaseVO.setExecutedSteps(currTestCaseVO.getExecutedSteps() + currStepStatus);
					frameworkLogger.log(Level.INFO, "Current Step >>> Keyword: " + currStepKeyword + ", Object: " + currStepObject + ", Value: " + currStepObjectValue);
					frameworkLogger.log(Level.INFO, "CurrStepStatus: " + currStepStatus);
				}
				frameworkLogger.log(Level.INFO, STEPFrameworkExecutor.testSuiteObjectRep.toString());
			}

			if(flag!=true)
				closeWebDriver(browser_type,driver);
		}
		catch (UnknownHostException e) {
			frameworkLogger.log(Level.SEVERE, "Unknown Host Exception occured while Executing TestCase" + e.getMessage());
		} catch (InvalidFormatException e) {
			frameworkLogger.log(Level.SEVERE, "Invalid Format Exception occured while Executing TestCase" + e.getMessage());
		} catch (FileNotFoundException e) {
			frameworkLogger.log(Level.SEVERE, "File Not Found Exception occured while Executing TestCase" + e.getMessage());
		} catch (IOException e) {
			frameworkLogger.log(Level.SEVERE, "IO Exception occured while Executing TestCase" + e.getMessage());
		}
		catch (STEPFrameworkMissingORObjectException e) {
			frameworkLogger.log(Level.SEVERE, "Object Missing Excception while Executing TestCase" + e.getMessage());
		}
		catch (Exception e) {
			frameworkLogger.log(Level.SEVERE, "Exception occured while Executing TestCase" + e.getMessage());
		}
		return currTestCaseVO;
			}

	/***
	 * Author	: 
	 * Purpose	: Initialize the webdriver to start the application based on browser type.
	 * @param browserType
	 * @param url
	 * @return
	 */
	private WebDriver initializeWebDriver(String browser) throws Exception
	{
		WebDriver driver = null;
		//DesiredCapabilities capabilities = null;
		String file_Path = System.getProperty("user.dir");
		//String DownloadPath = screenshotLocation+"\\"+STEPFrameworkDriver.strResultFileName;
		String DownloadPath = STEPFrameworkDriver.getFrameworkLocation() + "\\output\\Results\\";
		
		if(browser.equals("Firefox"))
		{
			FirefoxProfile profile = new FirefoxProfile();
			
			profile.setPreference("browser.download.manager.showWhenStarting", false);
			profile.setPreference("browser.download.dir", DownloadPath);
			profile.setPreference("browser.download.folderList", 2);
			profile.setPreference("plugin.disable_full_page_plugin_for_types", "application/xls");
			profile.setPreference("pref.downloads.disable_button.edit_actions", true);
			profile.setPreference("browser.helperApps.neverAsk.saveToDisk", "application/xls");
			
			DesiredCapabilities ieCapabilities = DesiredCapabilities.firefox();
			ieCapabilities.setCapability(FirefoxDriver.PROFILE, profile);
			ieCapabilities.setCapability("elementScrollBehavior", 1);
			driver = new FirefoxDriver(ieCapabilities);
			driver.manage().window().maximize();
		}
		else if(browser.equals("Chrome"))
		{
			System.setProperty("webdriver.chrome.driver", file_Path+"\\lib\\ChromeDriver\\chromedriver.exe");
			
			//capabilities = DesiredCapabilities.chrome();
			//capabilities.setCapability("chrome.switches", Arrays.asList("--start-maximized","--disable-popup-blocking"));
			
			HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
			chromePrefs.put("profile.default_content_settings.popups", 0);
			chromePrefs.put("download.default_directory", DownloadPath);
			
			
								
			// To remove message "You are using an unsupported command-line flag: --ignore-certificate-errors.
			// Stability and security will suffer."
			// Add an argument 'test-type'
			DesiredCapabilities capabilities = DesiredCapabilities.chrome();
			ChromeOptions options = new ChromeOptions();
			HashMap<String, Object> chromeOptionsMap = new HashMap<String, Object>();
			options.setExperimentalOption("prefs", chromePrefs);
			
			options.addArguments("test-type");
			/*options.AddUserProfilePreference("download.default_directory", DownloadPath);
			options.AddUserProfilePreference("intl.accept_languages", "nl");
			options.AddUserProfilePreference("disable-popup-blocking", "true");*/
			
			capabilities.setCapability("chrome.binary", file_Path+"\\lib\\ChromeDriver\\chromedriver.exe");
			capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptionsMap);
			capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			capabilities.setCapability(ChromeOptions.CAPABILITY, options);
			capabilities.setCapability(ChromeOptions.CAPABILITY, options);
			
			
			driver = new ChromeDriver(capabilities);//capabilities);
			driver.manage().window().maximize();
		}
		else if(browser.equals("IE"))
		{
			System.setProperty("webdriver.ie.driver", file_Path+"\\lib\\IEDriver\\IEDriverServer.exe");
			DesiredCapabilities ieCapabilities = DesiredCapabilities.internetExplorer();
			ieCapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
			ieCapabilities.setCapability("ignoreZoomSetting", true);
			/*ieCapabilities.setCapability(CapabilityType.BROWSER_NAME, "internet explorer");
			ieCapabilities.setCapability(CapabilityType.VERSION, "9");
			ieCapabilities.setCapability(CapabilityType.PLATFORM, "WINDOWS");
			 */
			 ieCapabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			 ieCapabilities.setCapability("useLegacyInternalServer", false);
			 ieCapabilities.setCapability("enableNativeEvents", false);
			 ieCapabilities.setCapability("ignoreProtectedModeSettings", true);
			 //File f=new File("c:\\logFile.txt");
			 //InternetExplorerDriverService service = new InternetExplorerDriverService.Builder().withLogFile(f).withLogLevel(InternetExplorerDriverLogLevel.TRACE).build();
			 driver = new InternetExplorerDriver(ieCapabilities);
			 driver.manage().window().maximize();
		}
		return driver;
	}
	/***
	 * Author	: 
	 * Purpose	: Close the webdriver instance based on the browser type.
	 * @param browserType
	 * @param driver
	 * @return
	 */
	private WebDriver closeWebDriver(String browserType,WebDriver driver) {
		if(browserType.equals("IE"))
		{
			driver.quit();
		}
		else if(browserType.equals("Firefox"))
		{
			driver.close();
		}
		else if(browserType.equals("Chrome"))
		{
			driver.quit();
		}

		return driver;
	}
	public static void setTestSuiteObjectRep(Hashtable<String, String[]> testSuiteObjectRep) {
		STEPFrameworkExecutor.testSuiteObjectRep = testSuiteObjectRep;
		
		
	}

	public static Hashtable<String, String[]> getTestSuiteObjectRep() {
		return testSuiteObjectRep;
	}
	/***
	 * Author	: 
	 * Purpose	: To set the Test Suite Object Repository WorkBook
	 * @param testSuiteObjectRepWB
	 */
	public static void setTestSuiteObjectRepWB(Workbook testSuiteObjectRepWB) {
		STEPFrameworkExecutor.testSuiteObjectRepWB.add(testSuiteObjectRepWB);
	}
	/***
	 * Author	: 
	 * Purpose	: To get the TestSuite Object Repository work book 
	 * @return
	 */
	public static Workbook getTestSuiteObjectRepWB(int index) {
		return testSuiteObjectRepWB.get(index);
	}
	public static void clearTestSuiteObjectRepWB() {
		STEPFrameworkExecutor.testSuiteObjectRepWB.clear();
	}
}
