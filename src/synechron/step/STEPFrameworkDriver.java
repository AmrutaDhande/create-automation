package synechron.step;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import synechron.step.exceptions.STEPFrameworkConfigException;
import synechron.step.exceptions.STEPFrameworkControllerException;
import synechron.step.exceptions.STEPFrameworkLocationInvalidException;
import synechron.step.flow.STEPFrameworkExecutor;
import synechron.step.flow.STEPFrameworkReader;
import synechron.step.framework.objects.STEPFrameworkVO;

public class STEPFrameworkDriver
{
	public static Logger frameworkLogger;

	public static String strFrameworkLocation ="";
	public static String strFilePath=null;
	public static String strResultFileName=null;
	/***
	 * Author:
	 * @param args
	 * @throws STEPFrameworkConfigException
	 */
	public static void main(String args[]) throws STEPFrameworkConfigException
	{
		//Get the Framework Location to access the input or output files
		strFrameworkLocation =System.getProperty("user.dir");
		strResultFileName="TestResults_"+getTimeStamp();
		File resultFile=new File(strFrameworkLocation+"\\output\\Results\\"+strResultFileName);
		resultFile.mkdir();
		STEPDebugLogger debugLogger = new STEPDebugLogger(strFrameworkLocation);

		try
		{
			setFrameworkLogger(debugLogger.getLogger());
			frameworkLogger.log(Level.INFO, "Starting Framework Execution");
			STEPFrameworkReader fwReader = new STEPFrameworkReader();
			STEPFrameworkVO frameworkVO = new STEPFrameworkVO();
			try
			{
				//Pass the framework home directory to the framework reader and initialize the framework Value Object

				//Read configuration values and load frameworkVO.
				frameworkVO = fwReader.readConfigData(strFrameworkLocation, frameworkVO);

				// Read the framework Controller Excel file
				frameworkVO = fwReader.readController(frameworkVO);

				//If test suites to be executed are > 0, start reading test cases for each test suite
				if (frameworkVO.getTestSuiteList().size() > 0)
				{
					frameworkVO = fwReader.readTestCases(frameworkVO);
				}
				//Log test suite and test case information in the frameworkVO.
				for (int testSuiteIterator = 0; testSuiteIterator < frameworkVO.getTestSuiteList().size(); testSuiteIterator++)
				{
					frameworkLogger.log(Level.INFO, ">>> " + frameworkVO.getTestSuiteList().get(testSuiteIterator).getTestSuiteName() + "(Status: " +
							frameworkVO.getTestSuiteList().get(testSuiteIterator).getTestSuiteStatus() + ")");
					for (int testCaseIterator = 0; testCaseIterator < frameworkVO.getTestSuiteList().get(testSuiteIterator).getTestCaseList().size(); testCaseIterator++)
					{
						frameworkLogger.log(Level.INFO, ">>>>>> " + frameworkVO.getTestSuiteList().get(testSuiteIterator).getTestCaseList().get(testCaseIterator).getTestCaseId());
						frameworkLogger.log(Level.INFO, ">>>>>>>>> " +
								frameworkVO.getTestSuiteList().get(testSuiteIterator).getTestCaseList().get(testCaseIterator).getExecuteTestCase());
					}
				}
				STEPFrameworkExecutor fwExecutor = new STEPFrameworkExecutor();
				frameworkVO = fwExecutor.runFrameworkTests(frameworkVO);
			}
			catch (STEPFrameworkConfigException e)
			{
				frameworkLogger.log(Level.SEVERE, "Framework Configuration Exception: " + e.getStackTrace().toString());
			}
			catch (STEPFrameworkControllerException e)
			{
				frameworkLogger.log(Level.SEVERE, "Framework Controller Exception: " + e.getMessage());
			}
		}
		catch (STEPFrameworkLocationInvalidException e)
		{
			frameworkLogger.log(Level.SEVERE, e.getStackTrace().toString());
		}
		catch (FileNotFoundException e)
		{
			throw new STEPFrameworkConfigException("Exception occured while reading STEPConfig.xml.  File not found.");
		}
		catch (Exception e)
		{ //ParserConfiguration
			e.printStackTrace();
		}


		//}
	}// End of Main
	/***
	 *	Author 	:
	 *	Purpose	: To get the current Framework Location
	 * 	@return
	 */
	public static String getFrameworkLocation() {
		return strFrameworkLocation;
	}//End of getFrameworkLocation

	/***
	 * Author	:
	 * Purpose	: To set the current Framework Location
	 * @param frameworkLocationpe
	 */
	public static void setFrameworkLocation(String frameworkLocation) {

		strFrameworkLocation =System.getProperty("user.dir");
	}// End of setFrameworkLocation
	/***
	 * Author	:
	 * Purpose	: To set the Logger to the frameworkLogger.
	 * @param frameworkLogger
	 */
	public static void setFrameworkLogger(Logger frameworkLogger) {
		STEPFrameworkDriver.frameworkLogger = frameworkLogger;
	}

	/***
	 * Author	:
	 * Purpose	: To get the current Framework Logger.
	 * @return
	 */
	public static Logger getFrameworkLogger() {
		return frameworkLogger;
	}

	/***
	 * Author	:
	 * Purpose	: To get the current Date&Time with given date format.
	 * @return
	 */
	public static String getTimeStamp()
	{
		Date nd=new Date();
		DateFormat formatters = new SimpleDateFormat("dd_MM_yyyyhhmmss");
		String strdate  = formatters.format(nd);
		return strdate;
	}

}