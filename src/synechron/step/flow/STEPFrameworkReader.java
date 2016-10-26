package synechron.step.flow;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import synechron.step.STEPFrameworkDriver;
import synechron.step.exceptions.STEPFrameworkConfigException;
import synechron.step.exceptions.STEPFrameworkControllerException;
import synechron.step.exceptions.STEPFrameworkLocationInvalidException;
import synechron.step.framework.objects.STEPFrameworkVO;
import synechron.step.framework.objects.STEPTestCaseStatus;
import synechron.step.framework.objects.STEPTestCaseVO;
import synechron.step.framework.objects.STEPTestSuiteStatus;
import synechron.step.framework.objects.STEPTestSuiteVO;
//Reads all Framework related data
public class STEPFrameworkReader{

	Logger frameworkLogger = STEPFrameworkDriver.getFrameworkLogger();
	//Reads the Configuration Data
	/***
	 * author	: 
	 * Purpose	: To read the STEPConfig.xml file data into STEPFrameworkVO class
	 * @param frameworkLocation
	 * @param frameworkVO
	 * @return
	 * @throws STEPFrameworkLocationInvalidException
	 * @throws STEPFrameworkConfigException
	 * @throws FileNotFoundException
	 */
	public STEPFrameworkVO readConfigData(String frameworkLocation, STEPFrameworkVO frameworkVO) throws STEPFrameworkLocationInvalidException, STEPFrameworkConfigException, FileNotFoundException {

		frameworkLogger.log(Level.INFO, "Reading Config Data...");
		File frameworkLocFile = new File(frameworkLocation);
		if (!frameworkLocFile.isDirectory()) 
		{
			throw new STEPFrameworkLocationInvalidException("Framework Location is not a valid folder.");
		} 
		else 
		{
			frameworkVO.setFrameworkLocation(frameworkLocation);
			try {
				//Read STEPConfig.xml and load data into the frameworkVO.configData HashTable
				DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
				Document xmlConfigDoc = docBuilder.parse(new File(frameworkLocFile.getPath() + "\\input\\Config\\STEPConfig.xml"));
				String autURL = xmlConfigDoc.getElementsByTagName("Application_URL").item(0).getTextContent();
				String projectName = xmlConfigDoc.getElementsByTagName("Project_Name").item(0).getTextContent();
				String projectVersion = xmlConfigDoc.getElementsByTagName("Project_Version").item(0).getTextContent();
				String envName=xmlConfigDoc.getElementsByTagName("Name").item(0).getTextContent();
				String controllerFileName=xmlConfigDoc.getElementsByTagName("Controller_FileName").item(0).getTextContent();
				String Api_Url=xmlConfigDoc.getElementsByTagName("Api_Url").item(0).getTextContent();
				String Api_username=xmlConfigDoc.getElementsByTagName("Api_username").item(0).getTextContent();
				String Api_password=xmlConfigDoc.getElementsByTagName("Api_password").item(0).getTextContent();
				String ORACLE_HOST=xmlConfigDoc.getElementsByTagName("ORACLE_HOST").item(0).getTextContent();
				String ORACLE_DBNAME=xmlConfigDoc.getElementsByTagName("ORACLE_DBNAME").item(0).getTextContent();
				String ORACLE_USERNAME=xmlConfigDoc.getElementsByTagName("ORACLE_USERNAME").item(0).getTextContent();
				String ORACLE_PASSWORD=xmlConfigDoc.getElementsByTagName("ORACLE_PASSWORD").item(0).getTextContent();
				String VERTICA_HOST=xmlConfigDoc.getElementsByTagName("VERTICA_HOST").item(0).getTextContent();
				String VERTICA_DBNAME=xmlConfigDoc.getElementsByTagName("VERTICA_DBNAME").item(0).getTextContent();
				String VERTICA_USERNAME=xmlConfigDoc.getElementsByTagName("VERTICA_USERNAME").item(0).getTextContent();
				String VERTICA_PASSWORD=xmlConfigDoc.getElementsByTagName("VERTICA_PASSWORD").item(0).getTextContent();
				String Couchbase_URI=xmlConfigDoc.getElementsByTagName("Couchbase_URI").item(0).getTextContent();
				
				frameworkVO.setConfigData("Application_URL", autURL);
				frameworkVO.setConfigData("Project_Name", projectName);
				frameworkVO.setConfigData("Project_Version", projectVersion);
				frameworkVO.setConfigData("Environment_Name", envName);
				frameworkVO.setConfigData("Controller_FileName", controllerFileName);
				frameworkVO.setConfigData("Api_Url", Api_Url);
				frameworkVO.setConfigData("Api_username", Api_username);
				frameworkVO.setConfigData("Api_password", Api_password);
				frameworkVO.setConfigData("ORACLE_HOST", ORACLE_HOST);
				frameworkVO.setConfigData("ORACLE_DBNAME", ORACLE_DBNAME);
				frameworkVO.setConfigData("ORACLE_USERNAME", ORACLE_USERNAME);
				frameworkVO.setConfigData("ORACLE_PASSWORD", ORACLE_PASSWORD);
				frameworkVO.setConfigData("VERTICA_HOST", VERTICA_HOST);
				frameworkVO.setConfigData("VERTICA_DBNAME", VERTICA_DBNAME);
				frameworkVO.setConfigData("VERTICA_USERNAME", VERTICA_USERNAME);
				frameworkVO.setConfigData("VERTICA_PASSWORD", VERTICA_PASSWORD);
				frameworkVO.setConfigData("Couchbase_URI", Couchbase_URI);
				
			} catch (ParserConfigurationException e) {
				frameworkLogger.log(Level.SEVERE, "Parser Exception occured while reading STEPConfig.xml" + e.getMessage());
				throw new STEPFrameworkConfigException("Parser Exception occured while reading STEPConfig.xml");
			} catch (SAXException e) {
				frameworkLogger.log(Level.SEVERE, "Exception occured while reading STEPConfig.xml. " + e.getMessage());
				throw new STEPFrameworkConfigException("Exception occured while reading STEPConfig.xml. " + e.getMessage());
			} catch (IOException e) {
				frameworkLogger.log(Level.SEVERE, "Exception occured while reading STEPConfig.xml. " + e.getMessage());
				throw new STEPFrameworkConfigException("Exception occured while reading STEPConfig.xml. " + e.getMessage());
			} catch (NullPointerException e) {
				frameworkLogger.log(Level.SEVERE, "Exception occured while reading STEPConfig.xml. " + e.getMessage());
				throw new STEPFrameworkConfigException("Exception occured while reading STEPConfig.xml. " + e.getMessage());
			}
		}
		frameworkLogger.log(Level.INFO, "Reading Config Data...DONE!");
		return frameworkVO;

	}
	/***
	 * author	: 
	 * Purpose	: To read the Controller.xls file data and keep respective test suites data into testSuiteVO
	 			  Check existence of Controller Excel 2003 file first and, if not found, check for Controller Excel 2007 file
	 * @param frameworkVO
	 * @return
	 * @throws STEPFrameworkControllerException
	 */
	public STEPFrameworkVO readController(STEPFrameworkVO frameworkVO) throws STEPFrameworkControllerException {
		//
		frameworkLogger.log(Level.INFO, "Reading Controller File...");
		File controllerFileXLSX = new File(frameworkVO.getFrameworkLocation() + "\\"+frameworkVO.getConfigData().get("Controller_FileName")+".xlsx");
		File controllerFileXLS = new File(frameworkVO.getFrameworkLocation() + "\\"+frameworkVO.getConfigData().get("Controller_FileName")+".xls");
		FileInputStream controllerIS = null;
		Workbook controllerWB = null;
		if (!controllerFileXLS.exists()) 
		{
			if (!controllerFileXLSX.exists()) 
			{
				frameworkLogger.log(Level.SEVERE, "Could not find the Controller file.");
				throw new STEPFrameworkControllerException("Could not find the Controller file.");
			} 
			else 
			{
				try {
					controllerIS = new FileInputStream(controllerFileXLSX);
					controllerWB = WorkbookFactory.create(controllerIS);
				} catch (FileNotFoundException e) {
					frameworkLogger.log(Level.SEVERE, "Exception occured while reading Controller file: " + e.getMessage());
					throw new STEPFrameworkControllerException("Exception occured while reading Controller file: " + e.getMessage());
				} catch (InvalidFormatException e) {
					frameworkLogger.log(Level.SEVERE, "Exception occured while reading Controller file: " + e.getMessage());
					throw new STEPFrameworkControllerException("Exception occured while reading Controller file: " + e.getMessage());
				} catch (IOException e) {
					frameworkLogger.log(Level.SEVERE, "Exception occured while reading Controller file: " + e.getMessage());
					throw new STEPFrameworkControllerException("Exception occured while reading Controller file: " + e.getMessage());
				}
			}
		} 
		else 
		{
			try {
				controllerIS = new FileInputStream(controllerFileXLS);
				controllerWB = WorkbookFactory.create(controllerIS);
			} catch (FileNotFoundException e) {
				frameworkLogger.log(Level.SEVERE, "Exception occured while reading Controller file: " + e.getMessage());
				throw new STEPFrameworkControllerException("Exception occured while reading Controller file: " + e.getMessage());
			} catch (InvalidFormatException e) {
				frameworkLogger.log(Level.SEVERE, "Exception occured while reading Controller file: " + e.getMessage());
				throw new STEPFrameworkControllerException("Exception occured while reading Controller file: " + e.getMessage());
			} catch (IOException e) {
				frameworkLogger.log(Level.SEVERE, "Exception occured while reading Controller file: " + e.getMessage());
				throw new STEPFrameworkControllerException("Exception occured while reading Controller file: " + e.getMessage());
			}
		}

		//Read all Test Suites from the Controller file
		boolean executeTestSuite;
		String testSuiteName;
		String testSuiteObjRepLocation;
		String testSuiteFileName;
		String testSuiteStatus;
		String testSuiteBrowserType;
		String testURL;
		Sheet controllerSheet = controllerWB.getSheetAt(0);
		for (int testSuiteRowIterator = 1; testSuiteRowIterator <= controllerSheet.getLastRowNum(); testSuiteRowIterator++) 
		{
			Row testSuiteRow = controllerSheet.getRow(testSuiteRowIterator);
			Cell executeTestSuiteCell = testSuiteRow.getCell(0, Row.RETURN_BLANK_AS_NULL);
			if (executeTestSuiteCell.getStringCellValue().equals("Yes")) executeTestSuite = true;
			else executeTestSuite = false;
			Cell testSuiteNameCell = testSuiteRow.getCell(1, Row.RETURN_BLANK_AS_NULL);
			Cell testSuiteLocationCell = testSuiteRow.getCell(2, Row.RETURN_BLANK_AS_NULL);
			Cell testSuiteORLocCell = testSuiteRow.getCell(3, Row.RETURN_BLANK_AS_NULL);
			Cell testSuiteBrowserTypeCell = testSuiteRow.getCell(4, Row.RETURN_BLANK_AS_NULL);
			Cell testSuiteURLCell = testSuiteRow.getCell(5, Row.RETURN_BLANK_AS_NULL);
			testSuiteName = testSuiteNameCell.getStringCellValue();
			testSuiteFileName = testSuiteLocationCell.getStringCellValue();
			testSuiteObjRepLocation = testSuiteORLocCell.getStringCellValue();
			testSuiteBrowserType=testSuiteBrowserTypeCell.getStringCellValue();
			testURL=testSuiteURLCell.getStringCellValue();
			
			if (executeTestSuite) testSuiteStatus = STEPTestSuiteStatus.TO_BE_EXECUTED.toString();
			else testSuiteStatus = STEPTestSuiteStatus.NOT_EXECUTED.toString();
			
			STEPTestSuiteVO testSuiteVO = new STEPTestSuiteVO();
			testSuiteVO.setTestSuiteName(testSuiteName);
			testSuiteVO.setTestSuiteFileName(testSuiteFileName);
			testSuiteVO.setTestSuiteObjRepFileName(testSuiteObjRepLocation);
			testSuiteVO.setTestSuiteStatus(testSuiteStatus);
			testSuiteVO.setTestSuiteBrowserType(testSuiteBrowserType);
			testSuiteVO.setTestSuiteURL(testURL);
			frameworkVO.addTestSuiteToList(testSuiteVO);
		}

		//If length of testSuiteVO list is not greater than 0, throw an exception.
		if (!(frameworkVO.getTestSuiteList().size() > 0)) {
			frameworkLogger.log(Level.SEVERE, "Either no Test Suites have been specified or all Test Suites have 'Execute = No'");
			throw new STEPFrameworkControllerException("Either no Test Suites have been specified or all Test Suites have 'Execute = No'");
		}

		//Close the InputStream and return updated frameworkVO to FrameworkDriver
		try {
			controllerIS.close();
		} catch (IOException e) {
			frameworkLogger.log(Level.SEVERE, e.getMessage());
			throw new STEPFrameworkControllerException(e.getMessage());
		}
		frameworkLogger.log(Level.INFO, "Reading Controller File...DONE!");
		return frameworkVO;
	}
	/***
	 * Author	: 
	 * Purpose	: For each testSuiteVO in the frameworkVO, read all test cases and 
				  add only those TestCases to the associated TestSuiteVO where 
		 		  an associated TestCase work sheet is present in the TestSuite Controller workbook
	 * @param frameworkVO
	 * @return
	 * @throws STEPFrameworkControllerException
	 */
	public STEPFrameworkVO readTestCases(STEPFrameworkVO frameworkVO) throws STEPFrameworkControllerException {
		frameworkLogger.log(Level.INFO, "Reading Controller information...");
		String executeTestCase;
		String testCaseId;
		String testCaseTitle;
		String testCaseInputData;
		int testSuiteIterator = 0;
		int testCaseIterator = 0;

		//Test Suite iterator loop
		for (testSuiteIterator = 0; testSuiteIterator < frameworkVO.getTestSuiteList().size(); testSuiteIterator++) 
		{
			STEPTestSuiteVO testSuiteVO = frameworkVO.getTestSuiteList().get(testSuiteIterator);
			File testSuiteFile = new File(frameworkVO.getFrameworkLocation() + testSuiteVO.getTestSuiteFileName().replace("\\", "\\\\"));

			//If testsuite file is not present, throw an exception;
			//else proceed to read the test suite file
			if (!testSuiteFile.exists()) 
			{
				throw new STEPFrameworkControllerException("Could not find the Test Suite Controller file for Test Suite '" + testSuiteVO.getTestSuiteName() + "'");
			} 
			else 
			{
				try {
					FileInputStream testSuiteIS = new FileInputStream(testSuiteFile);
					Workbook testSuiteWB = WorkbookFactory.create(testSuiteIS);
					Sheet testSuiteTestDataSheet = testSuiteWB.getSheet("TestData");

					//Test Case Iterator loop for each test suite
					for (testCaseIterator = 1; testCaseIterator <= testSuiteTestDataSheet.getLastRowNum(); testCaseIterator++) 
					{
						Row testCaseRow = testSuiteTestDataSheet.getRow(testCaseIterator);
						Cell testCaseExecuteCell = testCaseRow.getCell(0, Row.RETURN_BLANK_AS_NULL);
						Cell testCaseIdCell = testCaseRow.getCell(1, Row.RETURN_BLANK_AS_NULL);
						Cell testCaseTitleCell = testCaseRow.getCell(2, Row.RETURN_BLANK_AS_NULL);
						Cell testCaseInputDataCell = testCaseRow.getCell(3, Row.RETURN_BLANK_AS_NULL);
						if (testCaseExecuteCell.getStringCellValue().equals("Yes")) executeTestCase = STEPTestCaseStatus.TO_BE_EXECUTED.toString();
						else executeTestCase = STEPTestCaseStatus.NOT_EXECUTED.toString();
						STEPTestCaseVO testCaseVO = new STEPTestCaseVO();
						testCaseId = testCaseIdCell.getStringCellValue();
						testCaseInputData=testCaseInputDataCell.getStringCellValue();
						//Validate that the Test Case Sheet exists for every test case present in the Test Suite file
						boolean testCaseSheetFound = false;
						for (int testCaseSheetIterator = 0; testCaseSheetIterator < testSuiteWB.getNumberOfSheets(); testCaseSheetIterator++) {
							if (testCaseId.equals(testSuiteWB.getSheetName(testCaseSheetIterator))) {
								testCaseSheetFound = true;
							}
						}

						//Throw an exception if the Sheet does not exist and the test case has been set to Execute=Yes
						if (!testCaseSheetFound && executeTestCase.equals(STEPTestCaseStatus.TO_BE_EXECUTED.toString())) {
							throw new STEPFrameworkControllerException("Could not find the test case sheet '" + testCaseId + "'" + 
									" in the sheet '" + testSuiteVO.getTestSuiteFileName() + "'");
						}
						//Load a test case VO
						testCaseTitle = testCaseTitleCell.getStringCellValue();
						testCaseVO.setTestCaseId(testCaseId);
						testCaseVO.setTestCaseTitle(testCaseTitle);
						testCaseVO.setTestCaseInputData(testCaseInputData);
						testCaseVO.setExecuteTestCase(executeTestCase);
						testCaseVO.setTestCaseDataRowId(testCaseIterator);

						//Add the test case VO to the Test Suite VO.
						frameworkVO.getTestSuiteList().get(testSuiteIterator).addTestCaseToTestSuite(testCaseVO);
					}
				} catch (FileNotFoundException e) {
					frameworkLogger.log(Level.SEVERE, "Exception occured while reading Test Suite " + frameworkVO.getTestSuiteList().get(testSuiteIterator).getTestSuiteName() + ".  Test Suite file not found");
					throw new STEPFrameworkControllerException("Exception occured while reading Test Suite " + frameworkVO.getTestSuiteList().get(testSuiteIterator).getTestSuiteName() + ".  Test Suite file not found");
				} catch (InvalidFormatException e) {
					frameworkLogger.log(Level.SEVERE, "Exception occured while reading Test Suite " + frameworkVO.getTestSuiteList().get(testSuiteIterator).getTestSuiteName() + ".  Test Suite file not found");
					throw new STEPFrameworkControllerException("Exception occured while reading Test Suite " + frameworkVO.getTestSuiteList().get(testSuiteIterator).getTestSuiteName() + ".  " + e.getMessage());
				} catch (IOException e) {
					frameworkLogger.log(Level.SEVERE, "Exception occured while reading Test Suite " + frameworkVO.getTestSuiteList().get(testSuiteIterator).getTestSuiteName() + ".  Test Suite file not found");
					throw new STEPFrameworkControllerException("Exception occured while reading Test Suite " + frameworkVO.getTestSuiteList().get(testSuiteIterator).getTestSuiteName() + ".  " + e.getMessage());
				} catch (NullPointerException e) {
					frameworkLogger.log(Level.SEVERE, "A required value was missing at row " + testCaseIterator + " in the Test Suite Controller file " + testSuiteVO.getTestSuiteFileName());
					throw new STEPFrameworkControllerException("A required value was missing at row " + testCaseIterator + " in the Test Suite Controller file " + testSuiteVO.getTestSuiteFileName());
				}
			}
		}
		frameworkLogger.log(Level.INFO, "Reading Controller Information...DONE!");
		return frameworkVO;
	}
}
