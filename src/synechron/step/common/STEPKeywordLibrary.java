package synechron.step.common;



import java.awt.AWTException;
import java.io.File;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Arrays;
//import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.StringEntity;
//import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
//import org.apache.poi.util.SystemOutLogger;

/*import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Font;*/

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
//import org.omg.CORBA.portable.InputStream;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;






import synechron.step.STEPFrameworkDriver;
import synechron.step.exceptions.STEPFrameworkConfigException;
import synechron.step.exceptions.STEPFrameworkInvalidIdentifierException;
import synechron.step.exceptions.STEPFrameworkLocationInvalidException;
import synechron.step.exceptions.STEPFrameworkMissingORObjectException;
import synechron.step.flow.STEPFrameworkExecutor;
import synechron.step.flow.STEPFrameworkReader;
import synechron.step.framework.objects.STEPFrameworkVO;
import synechron.step.util.STEPDataUtility;


@SuppressWarnings("unused")
public class STEPKeywordLibrary {

	private static String screenshotLocation = STEPFrameworkDriver.getFrameworkLocation() + "\\output\\Results\\";
	private static String strNewWindow="";
	private static Hashtable<String, String> strRandomValues=new Hashtable<String, String>();
	private static Object strWindowHandlers[];
	private static String strCurrentDate="";
	public static int wincount=1;
	static String apiURL = null;
	static String apiUsername = null;
	static String apiPassword = null;
	static String oracleHost=null;
	static String oracleDBName=null;
	static String oracleUsername=null;
	static String oraclePassword=null;
	static String verticaHost=null;
	static String verticaDBName=null;
	static String verticaUsername=null;
	static String verticaPassword=null;
	static String couchbaseURI=null;
	static Map<String, Object> couchbaseDataMap = new HashMap<String, Object>();
	static boolean couchbaseModelStatus;
	static String CouchbasemodelGenerationStatus = null;
	static ArrayList<ModelTable> dbData;
	static String couchbaseModelType;
	static String couchbaseModelName;

	//String[][] tabArray=null;
	static ArrayList<ModelTable> retreivedFromDB = new ArrayList<ModelTable>();
	static HashMap <String,String> modelDocString = new HashMap<String,String>();

	static ArrayList<ModelTable> AllModels = new ArrayList<ModelTable>();
	static ArrayList<ModelTable> AccountLevel = new ArrayList<ModelTable>();
	static ArrayList<ModelTable> InteractionLookaLike = new ArrayList<ModelTable>();
	static ArrayList<ModelTable> Feedback = new ArrayList<ModelTable>();
	static ArrayList<ModelTable> ActiveModels = new ArrayList<ModelTable>();
	static ArrayList<ModelTable> ActiveFailedModels = new ArrayList<ModelTable>();
	static ArrayList<ModelTable> ProcessingModels = new ArrayList<ModelTable>();
	static ArrayList<ModelTable> FailedModels = new ArrayList<ModelTable>();
	static ArrayList<ModelTable> interestModels = new ArrayList<ModelTable>();
	static ArrayList<ModelTable> demoModels = new ArrayList<ModelTable>();
	static ArrayList<ModelTable> RetargetModels  = new ArrayList<ModelTable>();
	static ArrayList<ModelTable> InactiveModels = new ArrayList<ModelTable>();
	static ArrayList<ModelTable> UIModels = new ArrayList<ModelTable>();
	static ArrayList<ModelTable> CouchbaseModels = new ArrayList<ModelTable>();
	static ArrayList<ModelTable> ActiveAdvAcctModels = new ArrayList<ModelTable>();


	// Added By Vaibhav For ArrayList
	@SuppressWarnings("rawtypes")
	private static Hashtable<String, ArrayList> ArrayRandomValues=new Hashtable<String, ArrayList>();

	//Get APIURL/Username/Password from config xml file

	static {


	    String strFrameworkLocation =System.getProperty("user.dir");
		STEPFrameworkReader fwReader = new STEPFrameworkReader();
		STEPFrameworkVO frameworkVO = new STEPFrameworkVO();
		try {
			frameworkVO = fwReader.readConfigData(strFrameworkLocation, frameworkVO);
		} catch (STEPFrameworkLocationInvalidException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (STEPFrameworkConfigException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		apiURL = frameworkVO.getConfigData().get("Api_Url");
		apiUsername = frameworkVO.getConfigData().get("Api_username");
		apiPassword = frameworkVO.getConfigData().get("Api_password");
		oracleHost= frameworkVO.getConfigData().get("ORACLE_HOST");
		oracleDBName= frameworkVO.getConfigData().get("ORACLE_DBNAME");
		oracleUsername= frameworkVO.getConfigData().get("ORACLE_USERNAME");
		oraclePassword= frameworkVO.getConfigData().get("ORACLE_PASSWORD");
		verticaHost= frameworkVO.getConfigData().get("VERTICA_HOST");
		verticaDBName= frameworkVO.getConfigData().get("VERTICA_DBNAME");
		verticaUsername= frameworkVO.getConfigData().get("VERTICA_USERNAME");
		verticaPassword= frameworkVO.getConfigData().get("VERTICA_PASSWORD");
		couchbaseURI=frameworkVO.getConfigData().get("Couchbase_URI");
	}

	/***
	 * Author	:
	 * Purpose	: To Execute the TestCase Step based on keyword, driver, objectName, ObjectData, testDataRowId and browser_type
	 * @param keyword
	 * @param driver
	 * @param objectName
	 * @param objectData;
	 * @param testDataRowId
	 * @param browser_type
	 * @return
	 * @throws ParseException
	 * @throws JSONException
	 * @throws AWTException
	 */
	public static String executeStep(String keyword, WebDriver driver, String objectName, String objectData, int testDataRowId,String browser_type)throws STEPFrameworkMissingORObjectException,STEPFrameworkInvalidIdentifierException, ParseException, JSONException, AWTException
	{

		String stepExecutionStatus = "";
		if (keyword.equals("SETPAGE"))
		{

			stepExecutionStatus = runSetPage(objectName);

		}
		else if(keyword.equals("SWITCHWINDOW"))
		{

			stepExecutionStatus = runSwitchWindow(driver,objectData);

		}
		else if(keyword.equals("BROWSEFILE"))    //Practice Code
		{

			stepExecutionStatus = runBrowseFile(driver);

		}
		else if (keyword.equals("SWITCHTONESTEDIFRAME"))
		{

			stepExecutionStatus = runSwitchToNestedInFrame(driver, objectName, browser_type);

		}
		else if (keyword.equals("SWITCHTOIFRAME"))
		{

			stepExecutionStatus = runSwitchToInFrame(driver, objectName, browser_type);

		}
		else if (keyword.equals("SETDEFAULT"))
		{

			stepExecutionStatus = runSetDefault(driver);

		}
		else if(keyword.equals("CLOSEWINDOW"))
		{

			stepExecutionStatus = runCloseWindow(driver,objectData);

		}
		else if(keyword.equals("PARENTWINDOW"))
		{

			stepExecutionStatus = runParentWindow(driver,objectData);

		}
		else if(keyword.equals("ALERTACCEPT"))
		{

			stepExecutionStatus = runAlertAccept(driver);

		}
		else if(keyword.equals("CONFIRMMESSAGE"))
		{

			stepExecutionStatus = runConfirmMessage(driver,objectData);

		}
		else if (keyword.equals("CLEARTEXT"))
		{

			stepExecutionStatus = runClearText(driver, objectName, objectData, browser_type);

		}
		else if (keyword.equals("FILEUPLOAD"))
		{

			stepExecutionStatus = runFileUpload(driver, objectName, objectData, browser_type);

		}

		else if (keyword.equals("WINDOWSFILEUPLOAD"))
		{

			stepExecutionStatus = runWindowsFileUpLoad(objectName, objectData);

		}
		else if (keyword.equals("FLASHWINDOWCLICK"))
		{

			stepExecutionStatus = runFlashWindowClick(driver, objectName, objectData, browser_type);

		}
		else if (keyword.equals("KEYBOARDFORSEL"))
		{

			stepExecutionStatus = runKeyboardForSelectionWindow(objectData);

		}
		else if (keyword.equals("TYPE"))
		{

			stepExecutionStatus = runType(driver, objectName, objectData, browser_type);

		}
		else if (keyword.equals("TYPERANDOMNUMBER"))
		{

			stepExecutionStatus = runTypeRandomNumber(driver, objectName, objectData, browser_type);

		}
		else if (keyword.equals("TYPEDATE"))
		{

			stepExecutionStatus = runTypeDate(driver, objectName, objectData, browser_type);

		}
		else if (keyword.equals("VERIFYDATE"))
		{

			stepExecutionStatus = runVerifyDate(driver, objectName, objectData, browser_type);

		}
		else if (keyword.equals("SETDATE"))
		{

			stepExecutionStatus = runSetDate(driver);

		}

		else if (keyword.equals("JQSETDATE"))
		{

			stepExecutionStatus = runJQSetDate(driver,objectData);

		}
		else if (keyword.equals("CLICK"))
		{

			stepExecutionStatus = runClick(driver, objectName, browser_type);

		}
		else if (keyword.equals("JBUTTONCLICK"))
		{

			stepExecutionStatus = runJButtonClick(driver, objectName, browser_type);

		}
		else if (keyword.equals("REPLACECLICK"))
		{

			stepExecutionStatus = runReplaceClick(driver, objectName, objectData, browser_type);

		}
		else if (keyword.equals("PRESSENTER"))
		{

			stepExecutionStatus = runPressEnter(driver, objectName, browser_type);

		}
		else if (keyword.equals("REUSABLESCENARIO"))
		{

			stepExecutionStatus = runReusableScenario(driver, objectName, objectData, testDataRowId, browser_type);

		}
		else if (keyword.equals("VERIFYTEXT"))
		{

			stepExecutionStatus = runVerifyText(driver, objectName, objectData, browser_type);

		}
		else if (keyword.equals("REPLACEVERIFYTEXT"))
		{

			stepExecutionStatus = runReplaceVerifyText(driver, objectName, objectData, browser_type);

		}
		else if (keyword.equals("VERIFYTEXTFIELD"))
		{

			stepExecutionStatus = runVerifyTextField(driver, objectName, objectData, browser_type);

		}
		else if (keyword.equals("VERIFYURL"))
		{

			stepExecutionStatus = runVerifyUrl(driver, objectData, browser_type);

		}
		else if (keyword.equals("VERIFYRANDOMNUMBER"))
		{

			stepExecutionStatus = runVerifyRandomNumber(driver, objectName, objectData, browser_type);

		}
		else if (keyword.equals("VERIFYTEXTFIELDRANDOMNUMBER"))
		{

			stepExecutionStatus = runVerifyTextFieldRandomNumber(driver, objectName, objectData, browser_type);

		}
		else if (keyword.equals("VERIFYTLISTITEM"))
		{

			stepExecutionStatus = runVerifyListItem(driver, objectName, objectData, browser_type);

		}
		else if (keyword.equals("VERIFYTLISTITEMRANDOMNUMBER"))
		{

			stepExecutionStatus = runVerifyListItemRandomNumber(driver, objectName, objectData, browser_type);

		}
		else if (keyword.equals("VERIFYCHECKBOXCHECK"))
		{

			stepExecutionStatus = runVerifyCheckboxCheck(driver, objectName, browser_type);

		}
		else if (keyword.equals("VERIFYCHECKBOXUNCHECK"))
		{

			stepExecutionStatus = runVerifyCheckboxUnCheck(driver, objectName, browser_type);

		}

		else if (keyword.equals("REPLACEVERIFYRANDOMNUMBER"))
		{

			stepExecutionStatus = runReplaceVerifyRandomNumber(driver, objectName, objectData, browser_type);

		}
		else if (keyword.equals("ONMOUSEOVER"))
		{

			stepExecutionStatus = runOnMouseOver(driver, objectName,browser_type);

		}
		else if (keyword.equals("WAIT"))
		{

			stepExecutionStatus = runWait(driver,objectData);

		}
		else if (keyword.equals("WAITFORCLICKABLE"))
		{

			stepExecutionStatus = runWaitForClickable(driver,objectName,objectData);

		}
		else if (keyword.equals("SCROLLANDCLICK"))
		{

			stepExecutionStatus = runScrollAndClick(driver,objectName);

		}
		else if (keyword.equals("SELECTCLICK"))
		{

			stepExecutionStatus = runSelectClick(driver, objectName, objectData, browser_type);

		}
		else if (keyword.equals("SELECTRANDOMCLICK"))
		{

			stepExecutionStatus = runSelectRandomClick(driver, objectName, objectData, browser_type);

		}
		else if (keyword.equals("REPLACESELECTRANDOMCLICK"))
		{

			stepExecutionStatus = runReplaceSelectRandomClick(driver, objectName, objectData, browser_type);

		}
		else if (keyword.equals("REPLACESELECTCLICK"))
		{

			stepExecutionStatus = runReplaceSelectClick(driver, objectName, objectData, browser_type);

		}
		else if (keyword.equals("LISTBOXSELECT"))
		{

			stepExecutionStatus = runListboxSelect(driver, objectName, objectData, browser_type);

		}
		else if (keyword.equals("SELECTBYVALUE"))
		{

			stepExecutionStatus = runSelectByValue(driver, objectName, objectData, browser_type);

		}
		else if (keyword.equals("LISTBOXRANDOMTEXT"))
		{

			stepExecutionStatus = runListBoxRandomText(driver, objectName, objectData, browser_type);

		}
		else if (keyword.equals("RANDOMNUMBER"))
		{

			stepExecutionStatus = runRandomNumber(driver, objectData, browser_type);

		}
		else if (keyword.equals("GETANDSTOREVALUE"))
		{

			stepExecutionStatus = runGetAndStoreValue(driver, objectName, objectData, browser_type);

		}
		else if (keyword.equals("GETANDSTORETEXTFIEDVALUE"))
		{

			stepExecutionStatus = runGetAndStoreTextFieldValue(driver, objectName, objectData, browser_type);

		}
		else if (keyword.equals("GETANDWAIT"))
		{

			stepExecutionStatus = runGetAndWait(driver, objectName, browser_type);

		}
		else if (keyword.equals("WAITANDCLICK"))
		{

			stepExecutionStatus = runWaitAndClick(driver, objectName, objectData, browser_type);

		}
		else if (keyword.equals("MOUSEMOVE"))
		{
			stepExecutionStatus = runMouseMove(driver, objectName, browser_type);

		}
		else if (keyword.equals("REPLACELAUNCHURL"))
		{
			stepExecutionStatus = runReplaceLaunchUrl(driver, objectData, browser_type);

		}
		else if (keyword.equals("LAUNCHURL"))
		{
			stepExecutionStatus = runLaunchUrl(driver, objectData, browser_type);

		}
		else if(keyword.equals("FILESELECT"))
		{

			stepExecutionStatus = runFileSelect(driver, objectName, objectData,browser_type);

		}
		else if (keyword.equals("CHECKBOXCHECK"))
		{

			stepExecutionStatus = runCheckboxCheck(driver, objectName, browser_type);

		}
		else if (keyword.equals("CHECKBOXUNCHECK"))
		{

			stepExecutionStatus = runCheckboxUnCheck(driver, objectName, browser_type);

		}
		else if (keyword.equals("STOREVALUE"))
		{

			stepExecutionStatus = runStoreValue(driver, objectName, objectData);

		}
		else if (keyword.equals("COMPAREVALUE"))
		{

			stepExecutionStatus = runCompareValue(driver, objectName, objectData);

		}
		else if (keyword.equals("CHECKLENGTH"))
		{

			stepExecutionStatus = checkLength(driver, objectName, objectData);

		}
		else if (keyword.equals("OBJECTENABLED"))
		{

			stepExecutionStatus = runObjectEnabled(driver, objectName);

		}
		else if (keyword.equals("OBJECTDISABLED"))
		{

			stepExecutionStatus = runObjectDisabled(driver, objectName);

		}
		else if (keyword.equals("OBJECTDISPLAYED"))
		{

			stepExecutionStatus = runObjectDisplayed(driver, objectName);

		}
		else if (keyword.equals("REPLACEOBJECTDISPLAYED"))
		{

			stepExecutionStatus = runReplaceObjectDisplayed(driver, objectName,objectData);

		}
		else if (keyword.equals("REPLACEOBJECTNOTDISPLAYED"))
		{

			stepExecutionStatus = runReplaceObjectNotDisplayed(driver, objectName,objectData);

		}
		else if (keyword.equals("OBJECTINVISIBLE"))
		{

			stepExecutionStatus = runObjectInvisible(driver, objectName);

		}
		else if (keyword.equals("RADIOBUTTONSELECTED"))
		{

			stepExecutionStatus = runRadioButtonSelected(driver, objectName);

		}
		else if (keyword.equals("SCROLLDOWN"))
		{

			stepExecutionStatus = runScrollDown(driver, objectName);

		}
		else if (keyword.equals("RUNJAVASCRIPT"))
		{

			stepExecutionStatus = runJavaScript(driver, objectName);

		}
		else if (keyword.equals("VERIFYCOLOR"))
		{

			stepExecutionStatus = runVerifyColor(driver,objectName,objectData);

		}
		else if (keyword.equals("EXECUTESQLQUERY"))
		{

			stepExecutionStatus = retriveDB(objectData);

		}
		else if (keyword.equals("VERIFYTURBINEMETRICS"))
		{
			stepExecutionStatus = runVerifyMetricData(driver,objectData);
		}
		else if (keyword.equals("VERIFYTURBINEMODELMETRICS"))
		{
			stepExecutionStatus = runVerifyModelMetricData(driver,objectData);
		}
		else if (keyword.equals("VERIFYTURBINEPROGRESSBAR"))
		{
			stepExecutionStatus = runVerifyProgressBar(driver,objectName,objectData);
		}
		// Don't use this key, implemented is not completed
		/*else if (keyword.equals("VERIFYTURBINESORTING"))
		{
			stepExecutionStatus = runVerifySorting(driver,objectName,objectData);
		}*/
		else if (keyword.equals("COMMENT"))
		{
			stepExecutionStatus = runComment(objectData);
		}
		// Don't use this key, implemented is not completed
		else if (keyword.equals("VERIFYCUSTOMVARIABLE"))
		{
			stepExecutionStatus = verifySetupCustomVar(driver,objectName,objectData);
		}
		else if (keyword.equals("ADDVARIABLE"))
		{
			stepExecutionStatus = runAddHash(objectData);
		}
		/*else if (keyword.equals("GETCOUCHBASEMODELID"))
		{

				stepExecutionStatus = getCouchbaseModelID(objectData);
		}*/
		/*else if (keyword.equals("COMPAREMODEL_IN_COUCHBASE"))
		{

				stepExecutionStatus = verifyCouchbaseModel(objectData);
		}
		else if (keyword.equals("COMPARECUSTOMVARIABLE_COUCHBASE"))
		{

				stepExecutionStatus = verifyCouchbaseCustomVariable(objectData);
		}*/

		// Added By Vaibhav For Dimensions and Metrics verification
		else if (keyword.equals("SCROLLINGBARINDIV"))
		{
			stepExecutionStatus = runScrollIngBarInDiv(driver,objectName);
		}
        // This method added by vaibhav for DoubleClick the Dimension or Metrics element report
		else if (keyword.equals("SELECTDOUBLECLICK"))
		{
			stepExecutionStatus = runSelectDoubleClick(driver, objectName, objectData, browser_type);
		}
        // This method added by vaibhav for GETANDSTOREATTRIBUTEARRAYLIST the Dimension or Metrics element report
		else if (keyword.equals("GETANDSTOREATTRIBUTEARRAYLIST"))
		{
			stepExecutionStatus = runGetAndStoreAttributeArrayList(driver, objectName, objectData, browser_type);
		}
		else if (keyword.equals("VERIFYFULLITEMLIST"))
		{
			stepExecutionStatus = runVerifyFullItemList(driver, objectData, browser_type);
		}
		else if (keyword.equals("VERIFYMETRICDIMENSIONDISABLED"))
		{
			stepExecutionStatus = runVerifyMetricDimensionDisabled(driver, objectName, objectData, browser_type);
		}
		/*else if (keyword.equals("RETRIEVEMODELS_FROM_COUCHBASE"))
		{
			stepExecutionStatus = runCouchbaseModelData(objectData);
		}*/
		else if (keyword.equals("WAITFORINVISIBLE"))
		{
			stepExecutionStatus = runWaitForInvisible(driver,objectName,browser_type);
		}
		else if (keyword.equals("WAITFORVISIBLE"))
		{
			stepExecutionStatus = runWaitForVisible(driver,objectName,browser_type);
		}
		else if (keyword.equals("WAITFORREPLACEVISIBLE"))
		{
			stepExecutionStatus = runWaitForRandomVisible(driver, objectName, objectData, browser_type);
		}
		else if (keyword.equals("WAITFORREPLACEINVISIBLE"))
		{
			stepExecutionStatus = runWaitForRandomInvisible(driver, objectName, objectData, browser_type);
		}

		else if (keyword.equals("VERIFYMODELSINWEBLIST"))
		{
			stepExecutionStatus = runVerifyModelList(driver,objectData);
		}


		else
		{			stepExecutionStatus="::Failed  - '"+keyword+"' keyword is not Found in the Keyword Library. \r\n";
		}
		return stepExecutionStatus;
	}

	public static String runBrowseFile(WebDriver driver){
		try {
			driver.manage().timeouts().implicitlyWait(60,TimeUnit.SECONDS);

		    WebElement we = driver.findElement(By.xpath("//input[@type='file']"));
		    we.click();

			Runtime.getRuntime().exec("E:\\FileUpload.exe");
			return "::Passed - Successfully Verified List.  \r\n";

		} catch (IOException e) {

			return getErrorMessage(e);
		}
	}

	/***
	 * Purpose : Verify the given item is present in the list box or not
	 * @param driver
	 * @param objectName
	 * @param objectData
	 * @param browser_type
	 * @return
	 */
	public static String runVerifyListItem(WebDriver driver,String objectName,String objectData,String browser_type)
	{
		try
		{
			driver.manage().timeouts().implicitlyWait(60,TimeUnit.SECONDS);
			boolean value_check=false;
			int size=driver.findElements(getIdentifier(objectName)).size();
			if(size==0)
				return "::Failed  - '"+objectName + "' element is not found '"+takeScreenshot(driver)+"' \r\n";
			else
			{
				List<WebElement> list_elements=driver.findElements(getIdentifier(objectName));
				if(list_elements.size()!=0)
				{
					for(int count=0;count<list_elements.size();count++)
					{
						if(list_elements.get(count).getText().contains(objectData))
						{
							value_check=true;
							break;
						}
					}
				}

				if(value_check==true)
				{

					return "::Passed - Successfully Verified List '" + objectData + "' is present on '"+objectName+"'.  \r\n";
				}

				return "::Failed  - List '" + objectName + "' is not present '"+takeScreenshot(driver)+"'.  \r\n";
			}
		}

		catch(Exception e)
		{
			return getErrorMessage(e,objectName);
		}
	}


	/***
	 * Purpose : Verify the Random Number Item is present or not in the List box
	 * @param driver
	 * @param objectName
	 * @param objectData
	 * @param browser_type
	 * @return
	 */
	public static String runVerifyListItemRandomNumber(WebDriver driver,String objectName,String objectData,String browser_type)
	{
		try
		{
			driver.manage().timeouts().implicitlyWait(60,TimeUnit.SECONDS);
			boolean value_check=false;
			int size=driver.findElements(getIdentifier(objectName)).size();
			if(size==0)
				return "::Failed  - '"+objectName + "' element is not found '"+takeScreenshot(driver)+"' \r\n";
			else
			{
				List<WebElement> list_elements=driver.findElements(getIdentifier(objectName));
				if(list_elements.size()!=0)
				{
					for(int count=0;count<list_elements.size();count++)
					{
						if(list_elements.get(count).getText().contains(strRandomValues.get(objectData)))
						{
							value_check=true;
							break;
						}
					}
				}

				if(value_check==true)
				{

					return "::Passed - Successfully Verified List '" + objectData + "' is present on '"+objectName+"'.  \r\n";
				}

				return "::Failed  - List '" + objectName + "' is not present '"+takeScreenshot(driver)+"'.  \r\n";
			}
		}

		catch(Exception e)
		{
			return getErrorMessage(e,objectName);
		}
	}
	/***
	 * Purpose: Verify Object is Enabled or not
	 * @param driver
	 * @param objectName
	 * @return
	 */
	public static String runObjectEnabled(WebDriver driver,String objectName)
	{
		try
		{
			driver.manage().timeouts().implicitlyWait(120,TimeUnit.SECONDS);
			int size=driver.findElements(getIdentifier(objectName)).size();
			if(size==0)
				return "::Failed  - '"+objectName + "' element is not found '"+takeScreenshot(driver)+"' \r\n";
			else
			{
				if(driver.findElement(getIdentifier(objectName)).isEnabled()==true)
				{
					return "::Passed - Successfully Checked '"+objectName+"' is enabled. \r\n";
				}
				return "::Failed  - '"+objectName + "' is not enabled '"+takeScreenshot(driver)+"' \r\n";
			}
		}
		catch(STEPFrameworkMissingORObjectException e)
		{
			return "::Failed  - '"+objectName+"' object is missing in the Object Repository. \r\n";
		}
		catch(STEPFrameworkInvalidIdentifierException e)
		{
			return "::Failed  - Invalid identifier for the object "+objectName+" in Object Repository. \r\n";
		}
		catch(Exception e)
		{
			return getErrorMessage(e);
		}
	}
	/***
	 * Purpose : Verify the Object is Displayed or not
	 * @param driver
	 * @param objectName
	 * @return
	 */
	public static String runObjectDisplayed(WebDriver driver,String objectName)
	{
		try
		{
			driver.manage().timeouts().implicitlyWait(120,TimeUnit.SECONDS);
			int size=driver.findElements(getIdentifier(objectName)).size();
			if(size==0)
				return "::Failed  - '"+objectName + "' element is not found '"+takeScreenshot(driver)+"' \r\n";
			else
			{
				if(driver.findElement(getIdentifier(objectName)).isDisplayed()==true)
				{
					return "::Passed - Successfully Checked '"+objectName+"' is displayed. \r\n";
				}
				return "::Failed  - '"+objectName + "' is not displayed '"+takeScreenshot(driver)+"' \r\n";
			}
		}
		catch(STEPFrameworkMissingORObjectException e)
		{
			return "::Failed  - '"+objectName+"' object is missing in the Object Repository. \r\n";
		}
		catch(STEPFrameworkInvalidIdentifierException e)
		{
			return "::Failed  - Invalid identifier for the object "+objectName+" in Object Repository. \r\n";
		}
		catch(Exception e)
		{
			return getErrorMessage(e);
		}
	}


	public static String runReplaceObjectDisplayed(WebDriver driver,String objectName,String objectData)
	{
		try
		{
			driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
			String input=getIdentifier(objectName).toString().replace("XXX", objectData);
			int size=driver.findElements(By.xpath(input.substring(input.indexOf(":")+1, input.length()))).size();
			if(size==0)
				return "::Failed  - '"+objectData + "' element is not found '"+takeScreenshot(driver)+"' \r\n";
			else
			{
				if(driver.findElement(By.xpath(input.substring(input.indexOf(":")+1, input.length()))).isDisplayed()==true)
				{
					return "::Passed - Successfully Checked '"+objectData+"' is displayed. \r\n";
				}
				return "::Failed  - '"+objectData + "' is not displayed '"+takeScreenshot(driver)+"' \r\n";
			}
		}
		catch(STEPFrameworkMissingORObjectException e)
		{
			return "::Failed  - '"+objectName+"' object is missing in the Object Repository. \r\n";
		}
		catch(STEPFrameworkInvalidIdentifierException e)
		{
			return "::Failed  - Invalid identifier for the object "+objectName+" in Object Repository. \r\n";
		}
		catch(Exception e)
		{
			return getErrorMessage(e);
		}
	}

	public static String runReplaceObjectNotDisplayed(WebDriver driver,String objectName,String objectData)
	{
		try
		{
			driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
			String input=getIdentifier(objectName).toString().replace("XXX", objectData);


				if(driver.findElements(By.xpath(input.substring(input.indexOf(":")+1, input.length()))).size()==0)
				{
					return "::Passed - Successfully Checked '"+objectData+"' is not displayed. \r\n";
				}
				return "::Failed  - '"+objectData + "' is displayed '"+takeScreenshot(driver)+"' \r\n";

		}
		catch(STEPFrameworkMissingORObjectException e)
		{
			return "::Failed  - '"+objectName+"' object is missing in the Object Repository. \r\n";
		}
		catch(STEPFrameworkInvalidIdentifierException e)
		{
			return "::Failed  - Invalid identifier for the object "+objectName+" in Object Repository. \r\n";
		}
		catch(Exception e)
		{
			return getErrorMessage(e);
		}
	}

	/***
	 * Purpose: Verify the Object is Invisible or not
	 * @param driver
	 * @param objectName
	 * @return
	 */
	public static String runObjectInvisible(WebDriver driver,String objectName)
	{
		try
		{
			driver.manage().timeouts().implicitlyWait(20,TimeUnit.SECONDS);
			int size=driver.findElements(getIdentifier(objectName)).size();
			if(size==0)
				return "::Passed - Successfully Checked '"+objectName+"' is invisible.  \r\n";
			else
			{
				return "::Failed  - '"+objectName + "' is displayed '"+takeScreenshot(driver)+"' \r\n";
			}
		}
		catch(STEPFrameworkMissingORObjectException e)
		{
			return "::Failed  - '"+objectName+"' object is missing in the Object Repository. \r\n";
		}
		catch(STEPFrameworkInvalidIdentifierException e)
		{
			return "::Failed  - Invalid identifier for the object "+objectName+" in Object Repository. \r\n";
		}
		catch(Exception e)
		{
			return getErrorMessage(e);
		}
	}
	/***
	 * Purpose : Verify RadioButton is Selected or not
	 * @param driver
	 * @param objectName
	 * @return
	 */
	public static String runRadioButtonSelected(WebDriver driver,String objectName)
	{
		try
		{
			driver.manage().timeouts().implicitlyWait(120,TimeUnit.SECONDS);
			int size=driver.findElements(getIdentifier(objectName)).size();
			if(size==0)
				return "::Failed  - '"+objectName + "' element is not found '"+takeScreenshot(driver)+"' \r\n";
			else
			{
				if(driver.findElement(getIdentifier(objectName)).isSelected()==true)
				{
					return "::Passed - Successfully Checked '"+objectName+"' is selected. \r\n";
				}
				return "::Failed  - '"+objectName + "' is not displayed '"+takeScreenshot(driver)+"' \r\n";
			}
		}
		catch(STEPFrameworkMissingORObjectException e)
		{
			return "::Failed  - '"+objectName+"' object is missing in the Object Repository. \r\n";
		}
		catch(STEPFrameworkInvalidIdentifierException e)
		{
			return "::Failed  - Invalid identifier for the object "+objectName+" in Object Repository. \r\n";
		}
		catch(Exception e)
		{
			return getErrorMessage(e);
		}
	}
	/***
	 * Purpose : Run the Java Script of the object
	 * @param driver
	 * @param objectName
	 * @return
	 */
	public static String runJavaScript(WebDriver driver,String objectName)
	{
		try
		{
			driver.manage().timeouts().implicitlyWait(120,TimeUnit.SECONDS);

			int size=driver.findElements(getIdentifier(objectName)).size();
			if(size==0)
				return "::Failed  - '"+objectName + "' element is not found '"+takeScreenshot(driver)+"' \r\n";
			else
			{
				WebElement element=driver.findElement(getIdentifier(objectName));
				JavascriptExecutor jse = (JavascriptExecutor) driver;
				jse.executeScript("arguments[0].scrollIntoView(true);", element);
				return "::Passed - Successfully run the Java Script. \r\n";
			}

		}
		catch(STEPFrameworkMissingORObjectException e)
		{
			return "::Failed  - '"+objectName+"' object is missing in the Object Repository. \r\n";
		}
		catch(STEPFrameworkInvalidIdentifierException e)
		{
			return "::Failed  - Invalid identifier for the object "+objectName+" in Object Repository. \r\n";
		}
		catch(Exception e)
		{
			return getErrorMessage(e);
		}
	}
	/***
	 * Purpose : Verify the object is Disabled or not
	 * @param driver
	 * @param objectName
	 * @return
	 */
	public static String runObjectDisabled(WebDriver driver,String objectName)
	{
		try
		{
			driver.manage().timeouts().implicitlyWait(120,TimeUnit.SECONDS);
			int size=driver.findElements(getIdentifier(objectName)).size();
			if(size==0)
				return "::Failed  - '"+objectName + "' element is not found '"+takeScreenshot(driver)+"' \r\n";
			else
			{
				if(driver.findElement(getIdentifier(objectName)).isEnabled()==false)
				{
					return "::Passed - Successfully Checked '"+objectName+"' is disabled. \r\n";
				}
				return "::Failed  - '"+objectName + "' is not disabled '"+takeScreenshot(driver)+"' \r\n";
			}
		}
		catch(STEPFrameworkMissingORObjectException e)
		{
			return "::Failed  - '"+objectName+"' object is missing in the Object Repository. \r\n";
		}
		catch(STEPFrameworkInvalidIdentifierException e)
		{
			return "::Failed  - Invalid identifier for the object "+objectName+" in Object Repository. \r\n";
		}
		catch(Exception e)
		{
			return getErrorMessage(e);
		}
	}
	/***
	 * Purpose: Store the given value in the strRandomValues HashTable
	 * @param driver
	 * @param objectName
	 * @param objectData
	 * @return
	 */
	public static String runStoreValue(WebDriver driver,String objectName,String objectData)
	{
		try
		{
			driver.manage().timeouts().implicitlyWait(120,TimeUnit.SECONDS);
			strRandomValues.put(objectName, objectData);
			return "::Passed - Successfully Store the value '"+objectData+"'. \r\n";
		}
		catch(Exception e)
		{
			return getErrorMessage(e);
		}
	}
	/***
	 * Purpose : Compare the given value with strRandomValues Hash Table value
	 * @param driver
	 * @param objectName
	 * @param objectData
	 * @return
	 */
	public static String runCompareValue(WebDriver driver,String objectName,String objectData)
	{
		try
		{
			driver.manage().timeouts().implicitlyWait(120,TimeUnit.SECONDS);
			int size=driver.findElements(getIdentifier(objectName)).size();
			if(size==0)
				return "::Failed  - '"+objectName + "' element is not found '"+takeScreenshot(driver)+"' \r\n";
			else
			{
				String actualValue=driver.findElement(getIdentifier(objectName)).getAttribute("value");
				String expectedValue=strRandomValues.get(objectData);

				if(actualValue.equals(expectedValue))
				{
					return "::Passed - Successfully Compare the '"+actualValue+"' with '"+objectName+"'. \r\n";
				}
				return "::Failed  - '"+actualValue+"' is not equal to '"+expectedValue+" '"+takeScreenshot(driver)+"' \r\n";
			}
		}
		catch(STEPFrameworkMissingORObjectException e)
		{
			return "::Failed  - '"+objectName+"' object is missing in the Object Repository. \r\n";
		}
		catch(STEPFrameworkInvalidIdentifierException e)
		{
			return "::Failed  - Invalid identifier for the object "+objectName+" in Object Repository. \r\n";
		}
		catch(Exception e)
		{
			return getErrorMessage(e);
		}
	}


	/***
	 * Purpose : Compare the given value with strRandomValues Hash Table value
	 * @param driver
	 * @param objectName
	 * @param objectData
	 * @return
	 */
	public static String checkLength(WebDriver driver,String objectName,String length)
	{
		try
		{
			driver.manage().timeouts().implicitlyWait(120,TimeUnit.SECONDS);
			int size=driver.findElements(getIdentifier(objectName)).size();
			if(size==0)
				return "::Failed  - '"+objectName + "' element is not found '"+takeScreenshot(driver)+"' \r\n";
			else
			{
				String actualValue=driver.findElement(getIdentifier(objectName)).getAttribute("value");
			//	String expectedValue=strRandomValues.get(objectData);

				if(actualValue.length() <= Integer.valueOf(length))
				{
					return "::Passed - Successfully passed length validation for '"+objectName+"'. \r\n";
				}
				return "::Failed  - length validation for '"+objectName+"'. \r\n";
			}
		}
		catch(STEPFrameworkMissingORObjectException e)
		{
			return "::Failed  - '"+objectName+"' object is missing in the Object Repository. \r\n";
		}
		catch(STEPFrameworkInvalidIdentifierException e)
		{
			return "::Failed  - Invalid identifier for the object "+objectName+" in Object Repository. \r\n";
		}
		catch(Exception e)
		{
			return getErrorMessage(e);
		}
	}

	/***
	 * Purpose : check the check box, if check box is unchecked other wise nothing
	 * @param driver
	 * @param objectName
	 * @param browser_type
	 * @return
	 */
	private static String runCheckboxCheck(WebDriver driver, String objectName,String browser_type)
	{

		try
		{
			driver.manage().timeouts().implicitlyWait(180,TimeUnit.SECONDS);
			int size=driver.findElements(getIdentifier(objectName)).size();
			if(size==0)
				return "::Failed  - '"+objectName + "' element is not found '"+takeScreenshot(driver)+"' \r\n";
			else
			{
				WebElement checkBox=driver.findElement(getIdentifier(objectName));
				if(checkBox.getAttribute("checked") == null)
					checkBox.click();

				return "::Passed - Successfully Checked '"+objectName+"' Checkbox. \r\n";
			}
		}
		catch(STEPFrameworkMissingORObjectException e)
		{
			return "::Failed  - '"+objectName+"' object is missing in the Object Repository. \r\n";
		}
		catch(STEPFrameworkInvalidIdentifierException e)
		{
			return "::Failed  - Invalid identifier for the object "+objectName+" in Object Repository. \r\n";
		}
		catch(Exception e)
		{
			return getErrorMessage(e,objectName);
		}

	}
	/***
	 * Purpose : Uncheck the check box, if check box is checked other wise nothing
	 * @param driver
	 * @param objectName
	 * @param browser_type
	 * @return
	 */
	private static String runCheckboxUnCheck(WebDriver driver, String objectName,String browser_type)
	{

		try
		{
			driver.manage().timeouts().implicitlyWait(180,TimeUnit.SECONDS);
			int size=driver.findElements(getIdentifier(objectName)).size();
			if(size==0)
				return "::Failed  - '"+objectName + "' element is not found '"+takeScreenshot(driver)+"' \r\n";
			else
			{
				WebElement checkBox=driver.findElement(getIdentifier(objectName));
				if(checkBox.getAttribute("checked") != null)
					checkBox.click();

				return "::Passed - Successfully Checked '"+objectName+"' Checkbox. \r\n";
			}
		}
		catch(STEPFrameworkMissingORObjectException e)
		{
			return "::Failed  - '"+objectName+"' object is missing in the Object Repository. \r\n";
		}
		catch(STEPFrameworkInvalidIdentifierException e)
		{
			return "::Failed  - Invalid identifier for the object "+objectName+" in Object Repository. \r\n";
		}
		catch(Exception e)
		{
			return getErrorMessage(e,objectName);
		}

	}
	/***
	 * Purpose : Verify wheather the checkbox is checked or not
	 * @param driver
	 * @param objectName
	 * @param browser_type
	 * @return
	 */
	private static String runVerifyCheckboxCheck(WebDriver driver, String objectName,String browser_type)
	{

		try
		{
			driver.manage().timeouts().implicitlyWait(180,TimeUnit.SECONDS);
			int size=driver.findElements(getIdentifier(objectName)).size();
			if(size==0)
				return "::Failed  - '"+objectName + "' element is not found '"+takeScreenshot(driver)+"' \r\n";
			else
			{
				WebElement checkBox=driver.findElement(getIdentifier(objectName));
				if(checkBox.getAttribute("checked") != null)
					return "::Passed - Successfully Verified "+objectName+"' Checkbox is checked. \r\n";
				return "::Failed  - '"+objectName + "' checkbox is unchecked '"+takeScreenshot(driver)+"' \r\n";
			}
		}
		catch(STEPFrameworkMissingORObjectException e)
		{
			return "::Failed  - '"+objectName+"' object is missing in the Object Repository. \r\n";
		}
		catch(STEPFrameworkInvalidIdentifierException e)
		{
			return "::Failed  - Invalid identifier for the object "+objectName+" in Object Repository. \r\n";
		}
		catch(Exception e)
		{
			return getErrorMessage(e,objectName);
		}

	}
	/***
	 * Purpose : Verify wheather the checkbox is unchecked or not
	 * @param driver
	 * @param objectName
	 * @param browser_type
	 * @return
	 */
	private static String runVerifyCheckboxUnCheck(WebDriver driver, String objectName,String browser_type)
	{

		try
		{
			driver.manage().timeouts().implicitlyWait(180,TimeUnit.SECONDS);
			int size=driver.findElements(getIdentifier(objectName)).size();
			if(size==0)
				return "::Failed  - '"+objectName + "' element is not found '"+takeScreenshot(driver)+"' \r\n";
			else
			{
				WebElement checkBox=driver.findElement(getIdentifier(objectName));
				if(checkBox.getAttribute("checked") == null)
					return "::Passed - Successfully Verified '"+objectName+"' Checkbox is unchecked. \r\n";
				return "::Failed  - '"+objectName + "' checkbox is checked '"+takeScreenshot(driver)+"' \r\n";
			}
		}
		catch(STEPFrameworkMissingORObjectException e)
		{
			return "::Failed  - '"+objectName+"' object is missing in the Object Repository. \r\n";
		}
		catch(STEPFrameworkInvalidIdentifierException e)
		{
			return "::Failed  - Invalid identifier for the object "+objectName+" in Object Repository. \r\n";
		}
		catch(Exception e)
		{
			return getErrorMessage(e,objectName);
		}

	}
	/***
	 * Purpose : Keyboard key selection functionality
	 * @param key_To_Perform
	 * @return
	 */
	private static String runKeyboardForSelectionWindow(String key_To_Perform)
	{

		try
		{
			char character_To_Be_Operated = ' ';
			int char_Asci_Value =  -1;
			if(key_To_Perform.contains("-"))
			{
				character_To_Be_Operated = key_To_Perform.charAt(0);
				char_Asci_Value = character_To_Be_Operated;
			}
			Robot robot = new Robot();
			robot.delay(2000);
			if(key_To_Perform.equals("CONTROLPRESS"))
			{
				robot.keyPress(KeyEvent.VK_CONTROL);
			}
			else if(key_To_Perform.equals("CONTROLRELEASE"))
			{
				robot.keyRelease(KeyEvent.VK_CONTROL);
			}
			else if(key_To_Perform.equals("ENTERPRESS"))
			{
				robot.keyPress(KeyEvent.VK_ENTER);
			}
			else if(key_To_Perform.equals("ENTERRELEASE"))
			{
				robot.keyRelease(KeyEvent.VK_ENTER);
			}
			else if(key_To_Perform.equals("ALTPRESS"))
			{
				robot.keyPress(KeyEvent.VK_ALT);
			}
			else if(key_To_Perform.equals("ALTRELEASE"))
			{
				robot.keyRelease(KeyEvent.VK_ALT);
			}
			else if(key_To_Perform.equals("TABPRESS"))
			{
				robot.keyPress(KeyEvent.VK_TAB);
			}
			else if(key_To_Perform.equals("TABRELEASE"))
			{
				robot.keyRelease(KeyEvent.VK_TAB);
			}
			else if(key_To_Perform.trim().contains("-PRESS"))
			{
				robot.keyPress(char_Asci_Value);
			}
			else if(key_To_Perform.trim().contains("-RELEASE"))
			{
				robot.keyRelease(char_Asci_Value);
			}
			else if(key_To_Perform.trim().contains("DOWNARROWPRESS"))
			{
				robot.keyPress(KeyEvent.VK_DOWN);
			}
			else if(key_To_Perform.trim().contains("DOWNARROWRELEASE"))
			{
				robot.keyRelease(KeyEvent.VK_DOWN);
			}
			else if(key_To_Perform.trim().contains("SHIFTPRESS"))
			{
				robot.keyPress(KeyEvent.VK_SHIFT);
			}
			else if(key_To_Perform.trim().contains("SHIFTRELEASE"))
			{
				robot.keyRelease(KeyEvent.VK_SHIFT);
			}
			else if(key_To_Perform.trim().contains("SPACEBARPRESS"))
			{
				robot.keyPress(KeyEvent.VK_SPACE);
			}
			else if(key_To_Perform.trim().contains("SPACEBARRELEASE"))
			{
				robot.keyRelease(KeyEvent.VK_SPACE);
			}
			else if(key_To_Perform.trim().contains("F5PRESS"))
			{
				robot.keyPress(KeyEvent.VK_F5);
			}
			else if(key_To_Perform.trim().contains("F5RELEASE"))
			{
				robot.keyRelease(KeyEvent.VK_F5);
			}
			else if(key_To_Perform.trim().contains("F12PRESS"))
			{
				robot.keyPress(KeyEvent.VK_F12);
			}
			else if(key_To_Perform.trim().contains("F12RELEASE"))
			{
				robot.keyRelease(KeyEvent.VK_F12);
			}
			else if(key_To_Perform.trim().contains("F4PRESS"))
			{
				robot.keyPress(KeyEvent.VK_F4);
			}
			else if(key_To_Perform.trim().contains("F4RELEASE"))
			{
				robot.keyRelease(KeyEvent.VK_F4);
			}

			return "::Passed - Successfully performed '" + key_To_Perform + "'. \r\n";

		}
		catch (AWTException e)
		{
			return "::Failed  - '" + key_To_Perform + "'was not performed. \r\n";
		}
	}
	public static void setClipboardData(String string)
	{
		StringSelection stringSelection = new StringSelection(string);
		System.out.println("strsel :"+stringSelection);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
	}
	/***
	 * Purpose : Select the file and upload
	 * @param driver
	 * @param objectName
	 * @param objectData
	 * @param browser_type
	 * @return
	 * @throws STEPFrameworkMissingORObjectException
	 * @throws STEPFrameworkInvalidIdentifierException
	 */
	public static String runFileSelect(WebDriver driver,String objectName,String objectData,String browser_type)throws STEPFrameworkMissingORObjectException,STEPFrameworkInvalidIdentifierException
	{
		try
		{
			driver.manage().timeouts().implicitlyWait(15,TimeUnit.SECONDS);
			String Location =STEPFrameworkDriver.strFrameworkLocation+objectData;
			driver.findElement(getIdentifier(objectName)).click();
			setClipboardData(Location);
			Robot robot = new Robot();
			robot.delay(5000);
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
			return "::Passed - Successfully Selected the File '"+objectData+"'. \r\n";
		}
		catch(STEPFrameworkMissingORObjectException e)
		{
			return "::Failed  - '"+objectName+"' object is missing in the Object Repository. \r\n";
		}
		catch(STEPFrameworkInvalidIdentifierException e)
		{
			return "::Failed  - Invalid identifier for the object "+objectName+" in Object Repository. \r\n";
		}
		catch(Exception e)
		{
			System.out.println(e);
			return "::Failed  - File Select on '"+objectName+"' is not performed. \r\n";
		}
	}
	public static String runFlashWindowClick(WebDriver driver,String objectName,String objectData,String browser_type)
	{
		try
		{
			driver.manage().timeouts().implicitlyWait(15,TimeUnit.SECONDS);
			FlashObjectWebDriver flashApp = new FlashObjectWebDriver(driver, objectData);
			flashApp.callFlashObject("click");
			return "::Passed - Successfully clicked the File '"+objectData+"'. \r\n";
		}
		catch(Exception e)
		{
			System.out.println(e);
			return "::Failed  - File Select on '"+objectName+"' is not performed. \r\n";
		}
	}
	/***
	 * Purpose : store the current date in the strCurrentDate Variable
	 * @param driver
	 * @return
	 */
	public static String runSetDate(WebDriver driver)
	{
		try
		{
			driver.manage().timeouts().implicitlyWait(15,TimeUnit.SECONDS);
			Date date=new Date();
			SimpleDateFormat smp=new SimpleDateFormat("MM/dd/yyyy");
			strCurrentDate=smp.format(date);
			return "::Passed - Successfully set the Date. \r\n";
		}
		catch(Exception e)
		{
			return "::Failed  - Unable to set the Date. \r\n";
		}
	}

	/***
	 * Author: Mohamed Abdulkadar
	 * Purpose : Select the Date from JQuery Calendar
	 * @param driver
	 * @param ObjectData
	 * @return
	 */


public static String runJQSetDate(WebDriver driver,String ObjectData)
	{
		try
		{
			driver.manage().timeouts().implicitlyWait(15,TimeUnit.SECONDS);

			String modifieddate=null;

			if (ObjectData.equals("Today"))
			{
				Date date=new Date();
				modifieddate=new SimpleDateFormat("MM-dd-yy").format(date);
				//System.out.println("Today date: "+modifieddate);

			}else
			{
				int increment=Integer.parseInt(ObjectData);
				//Date date=new Date();
				SimpleDateFormat DateStr=new SimpleDateFormat("MM-dd-yy");
				//System.out.println("Date string:"+DateStr);
				Calendar c = Calendar.getInstance();
				c.add(Calendar.DATE,increment);
				modifieddate=(String)(DateStr.format(c.getTime()));
				//System.out.println("Tommorrow date: "+modifieddate);
			}


			//String DateString= ObjectData;

			//System.out.println("Date Entered: "+modifiedDate+"\r\n");

			if (modifieddate.contains("-"))
			{
			String[] parts=modifieddate.split("-");
			int expMon=Integer.parseInt(parts[0]);
			int expDay=Integer.parseInt(parts[1]);
			int expYear=Integer.parseInt(parts[2]);

			if (expYear < 100)
				  expYear +=2000;

			//System.out.println("expMon: "+expMon+" expDay: "+expDay+" expYear : "+expYear +"\r\n");

			WebElement datePickerDiv=driver.findElement(By.id("ui-datepicker-div"));
			//System.out.println(datePickerDiv.getText());

			 			//select the given year
			Select yearSel= new Select(datePickerDiv.findElement(By.className("ui-datepicker-year")));
			yearSel.selectByValue(String.valueOf(expYear));

			//select the given month
			Select monthSel= new Select(datePickerDiv.findElement(By.className("ui-datepicker-month")));
			monthSel.selectByValue(String.valueOf(expMon - 1)); //value start @ 0 so we need the -1



			//get the table
			WebElement calTable= datePickerDiv.findElement(By.className("ui-datepicker-calendar"));

			//click on the correct/given cell/date
			calTable.findElement(By.linkText(String.valueOf(expDay))).click();
			return "::Passed - Successfully clicked the File '"+ObjectData+"'. \r\n";



			}
			else
			{
				throw new IllegalArgumentException("String " + modifieddate + " does not contain -");
			}

	      }
		catch(Exception e)
		{
			return "::Failed  - Date is not in correct format for the Keyword.Please use DD-MM-YY. \r\n";
		}
	}





	/***
	 * Purpose : Type the date in the text field based on the input value
	 * @param driver
	 * @param objectName
	 * @param objectData
	 * @param browser_type
	 * @return
	 */
	private static String runTypeDate(WebDriver driver, String objectName,String objectData,String browser_type)
	{

		try
		{

			driver.manage().timeouts().implicitlyWait(15,TimeUnit.SECONDS);
			int size=driver.findElements(getIdentifier(objectName)).size();
			if(size==0)
				return "::Failed  - '"+objectName + "' element is not found '"+takeScreenshot(driver)+"' \r\n";
			else
			{
				if(objectData.equals(""))
				{
					Date d1=new Date();
					SimpleDateFormat smp=new SimpleDateFormat("MM/dd/yyyy");
					strCurrentDate=smp.format(d1);
					driver.findElement(getIdentifier(objectName)).sendKeys(strCurrentDate);
				}
				else if(!objectData.equals(""))
				{
					Date d1=new Date();
					Calendar c = Calendar.getInstance();
					c.setTime(d1);
					c.add(Calendar.DATE, Integer.parseInt(objectData));
					d1 = c.getTime();
					SimpleDateFormat smp=new SimpleDateFormat("MM/dd/yyyy");
					strCurrentDate=smp.format(d1);
					driver.findElement(getIdentifier(objectName)).sendKeys(strCurrentDate);
				}
			}
			return "::Passed - Successfully entered the text '" + strCurrentDate + "' in the field '" + objectName + "'. \r\n";

		}
		catch(STEPFrameworkMissingORObjectException e)
		{
			return "::Failed  - '"+objectName+"' object is missing in the Object Repository. \r\n";
		}
		catch(STEPFrameworkInvalidIdentifierException e)
		{
			return "::Failed  - Invalid identifier for the object "+objectName+" in Object Repository. \r\n";
		}
		catch(Exception e)
		{
			return getErrorMessage(e,objectName);
		}
	}
	/***
	 * Purpose : Verify the Date value with strCurrentDate HashTable value
	 * @param driver
	 * @param objectName
	 * @param objectData
	 * @param browser_type
	 * @return
	 */
	private static String runVerifyDate(WebDriver driver, String objectName,String objectData,String browser_type)
	{

		try
		{

			driver.manage().timeouts().implicitlyWait(15,TimeUnit.SECONDS);
			int size=driver.findElements(getIdentifier(objectName)).size();
			if(size==0)
				return "::Failed  - '"+objectName + "' element is not found '"+takeScreenshot(driver)+"' \r\n";
			else
			{
				if(objectData.equals(""))
				{
					Date dtDate=new Date();
					SimpleDateFormat smp=new SimpleDateFormat("MM/dd/yyyy");
					strCurrentDate=smp.format(dtDate);
					String expectedDate=driver.findElement(getIdentifier(objectName)).getAttribute("value");
					if (expectedDate.equals(strCurrentDate))
					{
						return "::Passed - Successfully verified the date  '" + strCurrentDate + "' in the field '" + objectName + "'. \r\n";
					}
				}
				else if(!objectData.equals(""))
				{
					Date dtDate=new Date();
					Calendar clCal= Calendar.getInstance();
					clCal.setTime(dtDate);
					clCal.add(Calendar.DATE, Integer.parseInt(objectData));
					dtDate = clCal.getTime();
					SimpleDateFormat smp=new SimpleDateFormat("MM/dd/yyyy");
					strCurrentDate=smp.format(dtDate);
					String expectedDate=driver.findElement(getIdentifier(objectName)).getAttribute("value");
					if (expectedDate.equals(strCurrentDate))
					{
						return "::Passed - Successfully verified the date  '" + strCurrentDate + "' in the field '" + objectName + "'. \r\n";
					}
				}
			}
			return "::Failed  - The Date '" + strCurrentDate + "' is not verified in the field '" + objectName + "'. \r\n";

		}
		catch(STEPFrameworkMissingORObjectException e)
		{
			return "::Failed  - '"+objectName+"' object is missing in the Object Repository. \r\n";
		}
		catch(STEPFrameworkInvalidIdentifierException e)
		{
			return "::Failed  - Invalid identifier for the object "+objectName+" in Object Repository. \r\n";
		}
		catch(Exception e)
		{
			return getErrorMessage(e,objectName);
		}
	}
	/***
	 * Purpose : Launch the Given URL
	 * @param driver
	 * @param objectData
	 * @param browser_type
	 * @return
	 */
	private static String runLaunchUrl(WebDriver driver, String objectData,String browser_type)
	{

		try
		{
			driver.manage().timeouts().implicitlyWait(15,TimeUnit.SECONDS);
			driver.get(objectData);
			return "::Passed - Successfully launch the url '"+objectData+"'. \r\n";

		}
		catch(Exception e)
		{
			return getErrorMessage(e);
		}
	}
	/***
	 * Purpose : Replace the values with given values and launch the URL
	 * @param driver
	 * @param objectData
	 * @param browser_type
	 * @return
	 */
	private static String runReplaceLaunchUrl(WebDriver driver, String objectData,String browser_type)
	{

		try
		{

			driver.manage().timeouts().implicitlyWait(15,TimeUnit.SECONDS);
			String objects[]=objectData.split(";");
			String url=objects[0].replace("XXX", strRandomValues.get(objects[1])).replace("YYY", strRandomValues.get(objects[2]));
			driver.get(url);
			return "::Passed - Successfully launch the url '"+url+"'. \r\n";

		}
		catch(Exception e)
		{
			return getErrorMessage(e);
		}
	}
	/***
	 * Purpose: To Click on the Selected value from List.
	 * @param driver
	 * @param objectName
	 * @param objectData
	 * @param browser_type
	 * @return
	 */
	private static String runSelectClick(WebDriver driver, String objectName,String objectData,String browser_type)
	{

		try
		{

			driver.manage().timeouts().implicitlyWait(15,TimeUnit.SECONDS);
			String input=getIdentifier(objectName).toString().replace("XXX", objectData);
			int size=driver.findElements(By.xpath(input.substring(input.indexOf(":")+1, input.length()))).size();
			if(size==0)
				return "::Failed  - '"+objectName + "' element is not found '"+takeScreenshot(driver)+"' \r\n";
			else
			{
				driver.findElement(By.xpath(input.substring(input.indexOf(":")+1, input.length()))).click();
				return "::Passed - Successfully Select the text '" + objectData + "' in the field '" + objectName + "' and clicked. \r\n";
			}
		}
		catch(STEPFrameworkMissingORObjectException e)
		{
			return "::Failed  - '"+objectName+"' object is missing in the Object Repository. \r\n";
		}
		catch(STEPFrameworkInvalidIdentifierException e)
		{
			return "::Failed  - Invalid identifier for the object "+objectName+" in Object Repository. \r\n";
		}
		catch(Exception e)
		{
			return getErrorMessage(e,objectName);
		}
	}
	/***
	 * Purpose : Select the Random element and click on the element
	 * @param driver
	 * @param objectName
	 * @param objectData
	 * @param browser_type
	 * @return
	 */
	private static String runSelectRandomClick(WebDriver driver, String objectName,String objectData,String browser_type)
	{

		try
		{

			driver.manage().timeouts().implicitlyWait(15,TimeUnit.SECONDS);
			String input=getIdentifier(objectName).toString().replace("XXX", strRandomValues.get(objectData));
			int size=driver.findElements(By.xpath(input.substring(input.indexOf(":")+1, input.length()))).size();
			if(size==0)
				return "::Failed  - '"+objectName + "' element is not found '"+takeScreenshot(driver)+"' \r\n";
			else
			{
				driver.findElement(By.xpath(input.substring(input.indexOf(":")+1, input.length()))).click();
				return "::Passed - Successfully Select the text '" + objectData + "' in the field '" + objectName + "' and clicked. \r\n";
			}
		}
		catch(STEPFrameworkMissingORObjectException e)
		{
			return "::Failed  - '"+objectName+"' object is missing in the Object Repository. \r\n";
		}
		catch(STEPFrameworkInvalidIdentifierException e)
		{
			return "::Failed  - Invalid identifier for the object "+objectName+" in Object Repository. \r\n";
		}
		catch(Exception e)
		{
			return getErrorMessage(e,objectName);
		}
	}
	/***
	 * Purpose : Replaces the values and elect the Random element then click on the element
	 * @param driver
	 * @param objectName
	 * @param objectData
	 * @param browser_type
	 * @return
	 */
	private static String runReplaceSelectRandomClick(WebDriver driver, String objectName,String objectData,String browser_type)
	{

		try
		{

			driver.manage().timeouts().implicitlyWait(15,TimeUnit.SECONDS);
			objectData=objectData.split(";")[0].replace("XXX", strRandomValues.get(objectData.split(";")[1]));
			String input=getIdentifier(objectName).toString().replace("XXX", objectData);
			int size=driver.findElements(By.xpath(input.substring(input.indexOf(":")+1, input.length()))).size();
			if(size==0)
				return "::Failed  - '"+objectName + "' element is not found '"+takeScreenshot(driver)+"' \r\n";
			else
			{
				driver.findElement(By.xpath(input.substring(input.indexOf(":")+1, input.length()))).click();
				return "::Passed - Successfully Select the text '" + objectData + "' in the field '" + objectName + "' and clicked. \r\n";
			}
		}
		catch(STEPFrameworkMissingORObjectException e)
		{
			return "::Failed  - '"+objectName+"' object is missing in the Object Repository. \r\n";
		}
		catch(STEPFrameworkInvalidIdentifierException e)
		{
			return "::Failed  - Invalid identifier for the object "+objectName+" in Object Repository. \r\n";
		}
		catch(NoSuchElementException e)
		{
			return "::Failed  - '"+objectName + "' element is not found '"+takeScreenshot(driver)+"' \r\n";
		}
		catch(Exception e)
		{
			return getErrorMessage(e,objectName);
		}
	}
	/***
	 * Purpose : Replace the text and select the list box and click on the item
	 * @param driver
	 * @param objectName
	 * @param objectData
	 * @param browser_type
	 * @return
	 */
	private static String runReplaceSelectClick(WebDriver driver, String objectName,String objectData,String browser_type)
	{

		try
		{
			driver.manage().timeouts().implicitlyWait(15,TimeUnit.SECONDS);
			String object_Data=objectData.split(";")[0].replace("XXX", strRandomValues.get(objectData.split(";")[1]));
			int size=driver.findElements(getIdentifier(objectName)).size();
			if(size==0)
				return "::Failed  - '"+objectName + "' element is not found '"+takeScreenshot(driver)+"' \r\n";
			else
			{
				WebElement list1=driver.findElement(getIdentifier(objectName));
				List<WebElement> lstOptions=list1.findElements(By.tagName("option"));
				list1.sendKeys(Keys.CONTROL);
				for(WebElement option : lstOptions)
				{
					if(option.getText().equals(object_Data))
					{
						option.click();
						break;
					}

				}
				return "::Passed - Successfully Select the text '" + object_Data + "' in the field '" + objectName + "' and clicked. \r\n";
			}
		}
		catch(STEPFrameworkMissingORObjectException e)
		{
			return "::Failed  - '"+objectName+"' object is missing in the Object Repository. \r\n";
		}
		catch(STEPFrameworkInvalidIdentifierException e)
		{
			return "::Failed  - Invalid identifier for the object "+objectName+" in Object Repository. \r\n";
		}
		catch(NoSuchElementException e)
		{
			return "::Failed  - '"+objectName + "' element is not found '"+takeScreenshot(driver)+"' \r\n";
		}
		catch(Exception e)
		{
			return getErrorMessage(e,objectName);
		}
	}
	/***
	 * Purpose: Move the mouse onto the element based on x & y coordinates.
	 * @param driver
	 * @param objectName
	 * @param browser_type
	 * @return
	 */
	private static String runMouseMove(WebDriver driver, String objectName,String browser_type)
	{

		try
		{
			driver.manage().timeouts().implicitlyWait(15,TimeUnit.SECONDS);
			int size=driver.findElements(getIdentifier(objectName)).size();
			if(size==0)
			{
				return "::Failed  - '"+objectName + "' element is not found '"+takeScreenshot(driver)+"' \r\n";
			}
			else
			{
				org.openqa.selenium.Point coordinates =driver.findElement(getIdentifier(objectName)).getLocation();
				Robot robot = new Robot();
				robot.mouseMove(coordinates.getX()+10,coordinates.getY()+70);
				return "::Passed - Successfully performed Mouse move on "+objectName+". \r\n";
			}
		}
		catch(STEPFrameworkMissingORObjectException e)
		{
			return "::Failed  - '"+objectName+"' object is missing in the Object Repository. \r\n";
		}
		catch(STEPFrameworkInvalidIdentifierException e)
		{
			return "::Failed  - Invalid identifier for the object "+objectName+" in Object Repository. \r\n";
		}
		catch(Exception e)
		{
			return getErrorMessage(e,objectName);
		}
	}
	/***
	 * Purpose: Close the recently opened window
	 * @param driver
	 * @return
	 */
	public static String runCloseWindow(WebDriver driver,String objectData)
	{
		try
		{
			driver.switchTo().window(strWindowHandlers[--wincount].toString());
			driver.close();
			driver.switchTo().window(strWindowHandlers[wincount-1].toString());
			return "::Passed - Successfully closed all other windows.  \r\n";

		}
		catch(Exception e)
		{
			return "::Failed  - Unable to Close Window \r\n";
		}

	}
	/***
	 * Purpose: switch to the Parent window
	 * @param driver
	 * @return
	 */
	public static String runParentWindow(WebDriver driver,String objectData)
	{
		try
		{
			driver.manage().timeouts().implicitlyWait(15,TimeUnit.SECONDS);
			//System.out.println("Parent : "+strParentWindow);
			//driver.switchTo().window(strWindowHandlers.get(objectData));
			return "::Passed - Successfully Switched to  is present.  \r\n";
		}
		catch(Exception e)
		{
			return "::Failed  - Switch Parent Window is not performed. \r\n";
		}
	}
	/***
	 * Purpose: Switch to the newly opened window
	 * @param driver
	 * @return
	 */
	public static String runSwitchWindow(WebDriver driver,String objectData)
	{
		try
		{
			driver.manage().timeouts().implicitlyWait(15,TimeUnit.SECONDS);
			Set<String> handlers=driver.getWindowHandles();
			strWindowHandlers=handlers.toArray();
			for(wincount=0;wincount<strWindowHandlers.length;wincount++)
			{
				System.out.println(strWindowHandlers[wincount].toString());
			}

			driver.switchTo().window(strWindowHandlers[wincount-1].toString());

			return "::Passed - Successfully Switch to '" + strNewWindow + "' is Completed.  \r\n";
		}
		catch(Exception e)
		{
			return "::Failed  - Unable to Switch Window \r\n";
		}
	}
	/***
	 * Purpose : Swithc to the frame to given frame
	 * @param driver
	 * @param objectName
	 * @param browser_type
	 * @return
	 */
	private static String runSwitchToInFrame(WebDriver driver, String objectName,String browser_type)
	{

		try
		{
			driver.manage().timeouts().implicitlyWait(15,TimeUnit.SECONDS);
			int size=driver.findElements(getIdentifier(objectName)).size();
			if(size==0)
				return "::Failed  - '"+objectName + "' element is not found '"+takeScreenshot(driver)+"' \r\n";
			else
			{
				driver.switchTo().frame(driver.findElement(getIdentifier(objectName)));
				return "::Passed - Successfully switched to '" + objectName + "'. \r\n";
			}
		}
		catch(STEPFrameworkMissingORObjectException e)
		{
			return "::Failed  - '"+objectName+"' object is missing in the Object Repository. \r\n";
		}
		catch(STEPFrameworkInvalidIdentifierException e)
		{
			return "::Failed  - Invalid identifier for the object "+objectName+" in Object Repository. \r\n";
		}
		catch(Exception e)
		{
			return getErrorMessage(e,objectName);
		}
	}
	/***
	 * Purpose : Switch to the Nested In Frame
	 * @param driver
	 * @param objectName
	 * @param browser_type
	 * @return
	 */
	private static String runSwitchToNestedInFrame(WebDriver driver, String objectName,String browser_type)
	{

		try
		{
			driver.manage().timeouts().implicitlyWait(15,TimeUnit.SECONDS);
			driver.switchTo().frame(driver.findElement(getIdentifier(objectName.split(";")[0]))).switchTo().frame(driver.findElement(getIdentifier(objectName.split(";")[1])));
			return "::Passed - Successfully switched to '" + objectName + "'. \r\n";
		}
		catch(STEPFrameworkMissingORObjectException e)
		{
			return "::Failed  - '"+objectName+"' object is missing in the Object Repository. \r\n";
		}
		catch(STEPFrameworkInvalidIdentifierException e)
		{
			return "::Failed  - Invalid identifier for the object "+objectName+" in Object Repository. \r\n";
		}
		catch(Exception e)
		{
			System.out.println(e);
			return getErrorMessage(e,objectName);
		}
	}
	private static String runSetDefault(WebDriver driver)
	{

		try
		{
			driver.manage().timeouts().implicitlyWait(15,TimeUnit.SECONDS);
			driver.switchTo().defaultContent();
			return "::Passed - Successfully set to default . \r\n";

		}
		catch(Exception e)
		{
			return "::Failed  - Unable to set Default Window. \r\n";
		}
	}
	/***
	 * Purpose: To generate the Random Number.
	 * @param driver
	 * @param objectData
	 * @param browser_type
	 * @return
	 */
	private static String runRandomNumber(WebDriver driver,String objectData,String browser_type)
	{

		try
		{

			driver.manage().timeouts().implicitlyWait(15,TimeUnit.SECONDS);
			String rndValue=objectData.split("\\|")[1]+STEPDataUtility.randomNumber();
			strRandomValues.put(objectData.split("\\|")[0], rndValue);

			return "::Passed - Successfully Generated Random Value '" + rndValue + "'. \r\n";


		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			return "::Failed  - InputData should contain delimeter (|). \r\n";
		}
		catch(Exception e)
		{
			return getErrorMessage(e);
		}
	}
	/***
	 * Purpose : Get the given object value and store in the strRandomValues Hash Table
	 * @param driver
	 * @param objectName
	 * @param objectData
	 * @param browser_type
	 * @return
	 */
	private static String runGetAndStoreValue(WebDriver driver,String objectName,String objectData,String browser_type)
	{

		try
		{

			driver.manage().timeouts().implicitlyWait(15,TimeUnit.SECONDS);
			int size=driver.findElements(getIdentifier(objectName)).size();
			if(size==0)
			{
				return "::Failed  - '"+objectName + "' element is not found '"+takeScreenshot(driver)+"' \r\n";
			}
			else
			{
				String element_value=driver.findElement(getIdentifier(objectName)).getText();
				strRandomValues.put(objectData, element_value);
				return "::Passed - Successfully Get the value form the object '" + objectName + "'. \r\n";

			}

		}
		catch(STEPFrameworkMissingORObjectException e)
		{
			return "::Failed  - '"+objectName+"' object is missing in the Object Repository. \r\n";
		}
		catch(STEPFrameworkInvalidIdentifierException e)
		{
			return "::Failed  - Invalid identifier for the object "+objectName+" in Object Repository. \r\n";
		}
		catch(Exception e)
		{
			return getErrorMessage(e);
		}
	}
	/***
	 * Purpose : Get the values from Text Field and store in the strRandomValues HashTable
	 * @param driver
	 * @param objectName
	 * @param objectData
	 * @param browser_type
	 * @return
	 */
	private static String runGetAndStoreTextFieldValue(WebDriver driver,String objectName,String objectData,String browser_type)
	{

		try
		{

			driver.manage().timeouts().implicitlyWait(15,TimeUnit.SECONDS);
			int size=driver.findElements(getIdentifier(objectName)).size();
			if(size==0)
			{
				return "::Failed  - '"+objectName + "' element is not found '"+takeScreenshot(driver)+"' \r\n";
			}
			else
			{
				String element_value=driver.findElement(getIdentifier(objectName)).getAttribute("value");
				strRandomValues.put(objectData, element_value);
				return "::Passed - Successfully Get the value form the object '" + objectName + "'. \r\n";

			}

		}
		catch(STEPFrameworkMissingORObjectException e)
		{
			return "::Failed  - '"+objectName+"' object is missing in the Object Repository. \r\n";
		}
		catch(STEPFrameworkInvalidIdentifierException e)
		{
			return "::Failed  - Invalid identifier for the object "+objectName+" in Object Repository. \r\n";
		}
		catch(Exception e)
		{
			return getErrorMessage(e);
		}
	}
	/***
	 * Purpose: Get the Time value from the text and wait for that particular time.
	 * @param driver
	 * @param objectName
	 * @param browser_type
	 * @return
	 */
	private static String runGetAndWait(WebDriver driver,String objectName,String browser_type)
	{

		try
		{

			driver.manage().timeouts().implicitlyWait(15,TimeUnit.SECONDS);
			int size=driver.findElements(getIdentifier(objectName)).size();
			if(size==0)
				return "::Failed  - '"+objectName + "' element is not found '"+takeScreenshot(driver)+"' \r\n";
			else
			{
				String strWaitMsgText=driver.findElement(getIdentifier(objectName)).getText();
				Long lngTime=Long.parseLong((strWaitMsgText.split(":")[1]).trim().split(" ")[0].trim());
				Thread.sleep(lngTime*1000*60);
				return "::Passed - Successfully wait for '" + lngTime + "' Minutes. \r\n";
			}

		}
		catch(STEPFrameworkMissingORObjectException e)
		{
			return "::Failed  - '"+objectName+"' object is missing in the Object Repository. \r\n";
		}
		catch(STEPFrameworkInvalidIdentifierException e)
		{
			return "::Failed  - Invalid identifier for the object "+objectName+" in Object Repository. \r\n";
		}
		catch(NoSuchElementException e)
		{
			return "::Failed  - '"+objectName + "' element is not found '"+takeScreenshot(driver)+"' \r\n";
		}
		catch(Exception e)
		{
			return getErrorMessage(e);
		}
	}
	private static String runWaitAndClick(WebDriver driver,String objectName,String objectData,String browser_type)
	{

		try
		{

			driver.manage().timeouts().implicitlyWait(15,TimeUnit.SECONDS);
			int size=driver.findElements(getIdentifier(objectName.split(";")[0])).size();
			if(size==0)
				return "::Failed  - '"+objectName + "' element is not found '"+takeScreenshot(driver)+"' \r\n";
			else
			{
				String dateTime1=driver.findElement(getIdentifier(objectName.split(";")[0])).getText().trim();
				String dateTime2=driver.findElement(getIdentifier(objectName.split(";")[1])).getText().trim();
				String dateTime3=driver.findElement(getIdentifier(objectName.split(";")[2])).getText().trim();
				String dateTime4=driver.findElement(getIdentifier(objectName.split(";")[3])).getText().trim();
				String dateTime5=driver.findElement(getIdentifier(objectName.split(";")[4])).getText().trim();
				String dateTime6=driver.findElement(getIdentifier(objectName.split(";")[5])).getText().trim();
				driver.findElement(getIdentifier(objectName.split(";")[6])).click();
				while(dateTime1.equals(strRandomValues.get(objectData.split(";")[0]).trim()) || dateTime2.equals(strRandomValues.get(objectData.split(";")[1]).trim()) || dateTime3.equals(strRandomValues.get(objectData.split(";")[2]).trim()) || dateTime4.equals(strRandomValues.get(objectData.split(";")[3]).trim()) || dateTime5.equals(strRandomValues.get(objectData.split(";")[4]).trim()) || dateTime6.equals(strRandomValues.get(objectData.split(";")[5]).trim()) || dateTime1.equals("---") || dateTime2.equals("---") || dateTime3.equals("---") || dateTime4.equals("---") || dateTime5.equals("---") || dateTime6.equals("---"))
				{
					driver.findElement(getIdentifier(objectName.split(";")[6])).click();
					Thread.sleep(50000);
					dateTime1=driver.findElement(getIdentifier(objectName.split(";")[0])).getText();
					dateTime2=driver.findElement(getIdentifier(objectName.split(";")[1])).getText().trim();
					dateTime3=driver.findElement(getIdentifier(objectName.split(";")[2])).getText().trim();
					dateTime4=driver.findElement(getIdentifier(objectName.split(";")[3])).getText().trim();
					dateTime5=driver.findElement(getIdentifier(objectName.split(";")[4])).getText().trim();
					dateTime6=driver.findElement(getIdentifier(objectName.split(";")[5])).getText().trim();
				}
				return "::Passed - Successfully wait and Clicked. \r\n";
			}

		}
		catch(STEPFrameworkMissingORObjectException e)
		{
			return "::Failed  - '"+objectName+"' object is missing in the Object Repository. \r\n";
		}
		catch(STEPFrameworkInvalidIdentifierException e)
		{
			return "::Failed  - Invalid identifier for the object "+objectName+" in Object Repository. \r\n";
		}
		catch(NoSuchElementException e)
		{
			return "::Failed  - '"+objectName + "' element is not found '"+takeScreenshot(driver)+"' \r\n";
		}
		catch(Exception e)
		{
			return getErrorMessage(e);
		}
	}
	/***
	 * Purpose: Scroll the mouse up to that particular element and then click on it.
	 * @param driver
	 * @param objectName
	 * @return
	 */
	private static String runScrollAndClick(WebDriver driver,String objectName)
	{
		try
		{
			driver.manage().timeouts().implicitlyWait(15,TimeUnit.SECONDS);
			int size=driver.findElements(getIdentifier(objectName)).size();
			if(size==0)
				return "::Failed  - '"+objectName + "' element is not found '"+takeScreenshot(driver)+"' \r\n";
			else
			{
				WebElement element = driver.findElement(getIdentifier(objectName));
				int elementPosition = element.getLocation().getY();
				String js = String.format("window.scroll(0, %s)", elementPosition-50);
				((JavascriptExecutor)driver).executeScript(js);
				element.click();
				return "::Passed - Successfully Click on "+objectName+". \r\n";
			}
		}
		catch(STEPFrameworkMissingORObjectException e)
		{
			return "::Failed  - '"+objectName+"' object is missing in the Object Repository. \r\n";
		}
		catch(STEPFrameworkInvalidIdentifierException e)
		{
			return "::Failed  - Invalid identifier for the object "+objectName+" in Object Repository. \r\n";
		}
		catch(NoSuchElementException e)
		{
			return "::Failed  - '"+objectName + "' element is not found '"+takeScreenshot(driver)+"' \r\n";
		}
		catch(Exception e)
		{
			return getErrorMessage(e,objectName);
		}
	}
	/***
	 * Purpose: Wait for the given particular time.
	 * @param driver
	 * @param time_In_Seconds
	 * @return
	 */
	private static String runWait(WebDriver driver,String time_In_Seconds)
	{
		try
		{
			Thread.sleep((Integer.parseInt(time_In_Seconds))*1000);
		}
		catch (NumberFormatException e)
		{
			e.printStackTrace();
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		catch(Exception e)
		{
			return getErrorMessage(e);
		}
		return "::Passed - Successfully waited for "+time_In_Seconds+" seconds. \r\n";
	}
	private static String runWaitForClickable(WebDriver driver,String objectName,String objectData)
	{
		try
		{
			driver.manage().timeouts().implicitlyWait(15,TimeUnit.SECONDS);
			int size=driver.findElements(getIdentifier(objectName)).size();
			if(size==0)
				return "::Failed  - '"+objectName + "' element is not found '"+takeScreenshot(driver)+"' \r\n";
			else
			{
			WebDriverWait wait = new WebDriverWait(driver, Integer.parseInt(objectData));
			wait.until(ExpectedConditions.elementToBeClickable(getIdentifier(objectName)));
			return "::Passed - Successfully waited for object "+objectName+" visible. \r\n";
			}
		}
		catch(STEPFrameworkMissingORObjectException e)
		{
			return "::Failed  - '"+objectName+"' object is missing in the Object Repository. \r\n";
		}
		catch(STEPFrameworkInvalidIdentifierException e)
		{
			return "::Failed  - Invalid identifier for the object "+objectName+" in Object Repository. \r\n";
		}
		catch(NoSuchElementException e)
		{
			return "::Failed  - '"+objectName + "' element is not found '"+takeScreenshot(driver)+"' \r\n";
		}
		catch(Exception e)
		{
			return getErrorMessage(e);
		}
	}
	/***
	 * Purpose: move the mouse to that particular element.
	 * @param driver
	 * @param objectName
	 * @param browser_type
	 * @return
	 */
	private static String runOnMouseOver(WebDriver driver, String objectName,String browser_type)
	{

		try
		{
			driver.manage().timeouts().implicitlyWait(15,TimeUnit.SECONDS);
			int size=driver.findElements(getIdentifier(objectName)).size();
			if(size==0)
				return "::Failed  - '"+objectName + "' element is not found '"+takeScreenshot(driver)+"' \r\n";
			else
			{
				new Actions(driver).moveToElement(driver.findElement(getIdentifier(objectName))).build().perform();
				return "::Passed - Successfully performed Mouse Over on "+objectName+". \r\n";
			}
		}
		catch(STEPFrameworkMissingORObjectException e)
		{
			return "::Failed  - '"+objectName+"' object is missing in the Object Repository. \r\n";
		}
		catch(STEPFrameworkInvalidIdentifierException e)
		{
			return "::Failed  - Invalid identifier for the object "+objectName+" in Object Repository. \r\n";
		}
		catch(NoSuchElementException e)
		{
			return "::Failed  - '"+objectName + "' element is not found '"+takeScreenshot(driver)+"' \r\n";
		}
		catch(Exception e)
		{
			return getErrorMessage(e,objectName);
		}
	}
	/***
	 * Purpose: Replace value with given variable and Verify the text
	 * @param driver
	 * @param objectName
	 * @param objectData
	 * @param browser_type
	 * @return
	 */
	private static String runReplaceVerifyText(WebDriver driver, String objectName,String objectData,String browser_type)
	{
		try
		{
			driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);

			int size=driver.findElements(getIdentifier(objectName)).size();
			System.out.println("Size : "+size);
			if(size==0)
				return "::Failed  - '"+objectName + "' element is not found '"+takeScreenshot(driver)+"' \r\n";
			else
			{

				String object_value=driver.findElement(getIdentifier(objectName)).getText();
				String objectDatas[]=objectData.split(";");
				System.out.println("Count : "+objectDatas.length);
				if(objectDatas.length>2)
				{
					String expectedValue=objectDatas[0].replace("XXX", strRandomValues.get(objectDatas[1]));
					expectedValue=expectedValue.replace("YYY", strRandomValues.get(objectDatas[2]));
					System.out.println("value :"+expectedValue);
					System.out.println("actual value :"+object_value);
					if (object_value.trim().toLowerCase().contains(expectedValue.trim().toLowerCase()))
					{

						return "::Passed - Successfully Replaced & Verified  Text '" + object_value	+ "'.  \r\n";

					}
				}
				else
				{
					String expectedValue=objectDatas[0].replace("XXX", strRandomValues.get(objectDatas[1]));
					System.out.println("value :"+expectedValue);
					System.out.println("actual value :"+object_value);
					if (object_value.trim().toLowerCase().contains(expectedValue.trim().toLowerCase()))
					{

						return "::Passed - Successfully Replaced & Verified  Text '" + object_value	+ "'.  \r\n";

					}
				}
				return "::Failed  - Text '" + object_value + "' is not verified ' on '"+objectName+"' Object name '"+takeScreenshot(driver)+"'.  \r\n";
			}

		}
		catch(STEPFrameworkMissingORObjectException e)
		{
			return "::Failed  - '"+objectName+"' object is missing in the Object Repository. \r\n";
		}
		catch(STEPFrameworkInvalidIdentifierException e)
		{
			return "::Failed  - Invalid identifier for the object "+objectName+" in Object Repository. \r\n";
		}
		catch(NoSuchElementException e)
		{
			return "::Failed  - '"+objectName + "' element is not found '"+takeScreenshot(driver)+"' \r\n";
		}
		catch(Exception e)
		{
			return getErrorMessage(e,objectName);
		}

	}
	/***
	 * Purpose: Verify the expected text with actual text.
	 * @param driver
	 * @param objectName
	 * @param objectData
	 * @param browser_type
	 * @return
	 */
	private static String runVerifyText(WebDriver driver, String objectName,String objectData,String browser_type)
	{
		try
		{
			driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);

			//Next 2 lines for debugging purpose
			//List test1=driver.findElements(getIdentifier(objectName));
			//System.out.println("test1 : "+test1);

			int size=driver.findElements(getIdentifier(objectName)).size();
			if(size==0)
				return "::Failed  - '"+objectName + "' element is not found '"+takeScreenshot(driver)+"' \r\n";
			else
			{

				String actualValue=driver.findElement(getIdentifier(objectName)).getText();
				System.out.println("Actual : "+actualValue);
				System.out.println("Expected Value : "+objectData);
				if (actualValue.toLowerCase().trim().contains(objectData.toLowerCase().trim()))
				{

					return "::Passed - Successfully Verified  Text '" + objectData	+ "'.  \r\n";

				}
				return "::Failed  - Text '" + objectData + "' is not verified '"+takeScreenshot(driver)+"'.  \r\n";
			}

		}
		catch(STEPFrameworkMissingORObjectException e)
		{
			return "::Failed  - '"+objectName+"' object is missing in the Object Repository. \r\n";
		}
		catch(STEPFrameworkInvalidIdentifierException e)
		{
			return "::Failed  - Invalid identifier for the object "+objectName+" in Object Repository. \r\n";
		}
		catch(NoSuchElementException e)
		{
			return "::Failed  - '"+objectName + "' element is not found '"+takeScreenshot(driver)+"' \r\n";
		}
		catch(Exception e)
		{
			return getErrorMessage(e,objectName);
		}

	}
	/***
	 * Purpose: Verify the text in the text field
	 * @param driver
	 * @param objectName
	 * @param objectData
	 * @param browser_type
	 * @return
	 */
	private static String runVerifyTextField(WebDriver driver, String objectName,String objectData,String browser_type)
	{
		try
		{
			driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);

			int size=driver.findElements(getIdentifier(objectName)).size();
			if(size==0)
				return "::Failed  - '"+objectName + "' element is not found '"+takeScreenshot(driver)+"' \r\n";
			else
			{

				String object_value=driver.findElement(getIdentifier(objectName)).getAttribute("value");
				if (object_value.trim().contains(objectData.trim()))
				{

					return "::Passed - Successfully Verified  Text '" + objectData	+ "' in the '"+objectName+"'.  \r\n";

				}
				return "::Failed  - Text '" + objectData + "' is not verified '"+takeScreenshot(driver)+"'.  \r\n";
			}

		}
		catch(STEPFrameworkMissingORObjectException e)
		{
			return "::Failed  - '"+objectName+"' object is missing in the Object Repository. \r\n";
		}
		catch(STEPFrameworkInvalidIdentifierException e)
		{
			return "::Failed  - Invalid identifier for the object "+objectName+" in Object Repository. \r\n";
		}
		catch(NoSuchElementException e)
		{
			return "::Failed  - '"+objectName + "' element is not found '"+takeScreenshot(driver)+"' \r\n";
		}
		catch(Exception e)
		{
			return getErrorMessage(e,objectName);
		}

	}
	/***
	 * Purpose: Verify the given text is matching or not.
	 * @param driver
	 * @param objectName
	 * @param objectData
	 * @param browser_type
	 * @return
	 */
	private static String runVerifyUrl(WebDriver driver, String objectData,String browser_type)
	{
		try
		{
			driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);

			String application_url=driver.getCurrentUrl();
			if (application_url.trim().contains(objectData.trim()))
			{

				return "::Passed - Successfully Verified  url '" + objectData	+ "'.  \r\n";

			}
			return "::Failed  - URL '" + objectData + "' is not verified '"+takeScreenshot(driver)+"'.  \r\n";


		}
		catch(Exception e)
		{
			return getErrorMessage(e);
		}

	}
	/***
	 * Purpose: Verify the Random number element is present or not.
	 * @param driver
	 * @param objectName
	 * @param objectData
	 * @param browser_type
	 * @return
	 */
	private static String runVerifyRandomNumber(WebDriver driver, String objectName,String objectData,String browser_type)
	{
		try
		{
			driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);

			int size=driver.findElements(getIdentifier(objectName)).size();
			if(size==0)
				return "::Failed  - '"+objectName + "' element is not found '"+takeScreenshot(driver)+"' \r\n";
			else
			{
				String actualValue=driver.findElement(getIdentifier(objectName)).getText();
				String expectedValue=strRandomValues.get(objectData);
				if (actualValue.trim().equals(expectedValue.trim()))
				{

					return "::Passed - Successfully Verified  Text '" + expectedValue	+ "'.  \r\n";

				}
				return "::Failed  - Text '" + expectedValue + "' is not verified '"+takeScreenshot(driver)+"'.  \r\n";
			}

		}
		catch(STEPFrameworkMissingORObjectException e)
		{
			return "::Failed  - '"+objectName+"' object is missing in the Object Repository. \r\n";
		}
		catch(STEPFrameworkInvalidIdentifierException e)
		{
			return "::Failed  - Invalid identifier for the object "+objectName+" in Object Repository. \r\n";
		}
		catch(NoSuchElementException e)
		{
			return "::Failed  - '"+objectName + "' element is not found '"+takeScreenshot(driver)+"' \r\n";
		}
		catch(Exception e)
		{
			return getErrorMessage(e,objectName);
		}

	}
	/***
	 * Purpose : Replace the values and verify the Random Number
	 * @param driver
	 * @param objectName
	 * @param objectData
	 * @param browser_type
	 * @return
	 */
	private static String runReplaceVerifyRandomNumber(WebDriver driver, String objectName,String objectData,String browser_type)
	{
		try
		{
			driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);

			int size=driver.findElements(getIdentifier(objectName)).size();
			if(size==0)
				return "::Failed  - '"+objectName + "' element is not found '"+takeScreenshot(driver)+"' \r\n";
			else
			{
				String actualValue=driver.findElement(getIdentifier(objectName)).getText();
				String expectedValues[]=objectData.split(";");
				System.out.println("Values : "+expectedValues[0]+"  "+expectedValues[1]);
				System.out.println(strRandomValues.get(expectedValues[1]));
				String expectedValue=(objectData.split(";")[0]).replace("XXX", strRandomValues.get(objectData.split(";")[1]));
				if (actualValue.trim().equals(expectedValue.trim()))
				{

					return "::Passed - Successfully Verified  Text '" + expectedValue	+ "'.  \r\n";

				}
				return "::Failed  - Text '" + expectedValue + "' is not verified '"+takeScreenshot(driver)+"'.  \r\n";
			}

		}
		catch(STEPFrameworkMissingORObjectException e)
		{
			return "::Failed  - '"+objectName+"' object is missing in the Object Repository. \r\n";
		}
		catch(STEPFrameworkInvalidIdentifierException e)
		{
			return "::Failed  - Invalid identifier for the object "+objectName+" in Object Repository. \r\n";
		}
		catch(NoSuchElementException e)
		{
			return "::Failed  - '"+objectName + "' element is not found '"+takeScreenshot(driver)+"' \r\n";
		}
		catch(Exception e)
		{
			return getErrorMessage(e,objectName);
		}

	}
	/***
	 * Purpose : Verify the Random Number in the Text Field
	 * @param driver
	 * @param objectName
	 * @param objectData
	 * @param browser_type
	 * @return
	 */
	private static String runVerifyTextFieldRandomNumber(WebDriver driver, String objectName,String objectData,String browser_type)
	{
		try
		{
			driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);

			int size=driver.findElements(getIdentifier(objectName)).size();
			if(size==0)
				return "::Failed  - '"+objectName + "' element is not found '"+takeScreenshot(driver)+"' \r\n";
			else
			{
				String actualValue=driver.findElement(getIdentifier(objectName)).getAttribute("value");
				String expectedValue=strRandomValues.get(objectData);
				if (actualValue.trim().equals(expectedValue.trim()))
				{

					return "::Passed - Successfully Verified  Text '" + expectedValue	+ "'.  \r\n";

				}
				return "::Failed  - Text '" + expectedValue + "' is not verified '"+takeScreenshot(driver)+"'.  \r\n";
			}

		}
		catch(STEPFrameworkMissingORObjectException e)
		{
			return "::Failed  - '"+objectName+"' object is missing in the Object Repository. \r\n";
		}
		catch(STEPFrameworkInvalidIdentifierException e)
		{
			return "::Failed  - Invalid identifier for the object "+objectName+" in Object Repository. \r\n";
		}
		catch(NoSuchElementException e)
		{
			return "::Failed  - '"+objectName + "' element is not found '"+takeScreenshot(driver)+"' \r\n";
		}
		catch(Exception e)
		{
			return getErrorMessage(e,objectName);
		}

	}
	/***
	 * Purpose : Run the Reusable Scenario
	 * @param driver
	 * @param objectName
	 * @param objectData
	 * @param testDataRowId
	 * @param browser_type
	 * @return
	 */
	private static String runReusableScenario(WebDriver driver, String objectName,String objectData, int testDataRowId,String browser_type)
	{
		String bfExecutionStatus = "::Executing Reusable Scenario '" + objectName + "'. \r\n";

		try
		{
			Sheet bfSheet = STEPFrameworkExecutor.businessFunctionWB.getSheet(objectName);
			int bfStepCount = bfSheet.getLastRowNum();

			Row bfRow;
			Cell bfKeywordCell, bfElementIdCell, bfValueCell=null;
			String bfKeyword = "", bfElementId = "", bfValue = "";
			for (int i = 1; i <= bfStepCount; i++)
			{

				bfRow = bfSheet.getRow(i);
				bfKeywordCell = bfRow.getCell(0, Row.CREATE_NULL_AS_BLANK);
				bfElementIdCell = bfRow.getCell(1, Row.CREATE_NULL_AS_BLANK);
				bfValueCell = bfRow.getCell(Integer.parseInt(objectData.split("-")[1])+1, Row.CREATE_NULL_AS_BLANK);
				bfKeyword = bfKeywordCell.getStringCellValue();
				bfElementId = bfElementIdCell.getStringCellValue();

				switch (bfValueCell.getCellType())
				{

				case (Cell.CELL_TYPE_STRING):
					bfValue = bfValueCell.getStringCellValue();
				break;
				case (Cell.CELL_TYPE_NUMERIC):
				//bfValue = Double.toString(bfValueCell.getNumericCellValue());
				Double doubleValue=bfValueCell.getNumericCellValue();
				bfValue = Integer.toString(doubleValue.intValue());
				break;

				}

				if (bfValue.equals("GETDATA"))
				{

					bfValue = STEPFrameworkExecutor.testDataContainer.get(bfKeyword)[testDataRowId];

				}
				bfExecutionStatus = bfExecutionStatus + executeStep(bfKeyword, driver, bfElementId, bfValue, testDataRowId,browser_type);

			}

			bfExecutionStatus = bfExecutionStatus + "::Passed - Successfully executed Reusable Scenario '" + objectName + "'. \r\n";
		}
		catch(Exception e)
		{
			return "::Failed - Unable to execute the Reusable Scenario. \r\n";
		}
		return bfExecutionStatus;

	}
	/***
	 * Purpose: Java Script Button Click
	 * @param driver
	 * @param objectName
	 * @param browser_type
	 * @return
	 */
	private static String runJButtonClick(WebDriver driver, String objectName,String browser_type)
	{
		try
		{
			driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
			int size=driver.findElements(getIdentifier(objectName)).size();
			if(size==0)
				return "::Failed  - '"+objectName + "' element is not found '"+takeScreenshot(driver)+"' \r\n";
			else
			{
				WebElement hiddenWebElement =driver.findElement(getIdentifier(objectName));
				((JavascriptExecutor)driver).executeScript("arguments[0].click()",hiddenWebElement);
				return "::Passed - Successfully clicked on '"	+ objectName + "'. \r\n";
			}
		}
		catch(STEPFrameworkMissingORObjectException e)
		{
			return "::Failed  - '"+objectName+"' object is missing in the Object Repository. \r\n";
		}
		catch(STEPFrameworkInvalidIdentifierException e)
		{
			return "::Failed  - Invalid identifier for the object "+objectName+" in Object Repository. \r\n";
		}
		catch(NoSuchElementException e)
		{
			return "::Failed  - '"+objectName + "' element is not found '"+takeScreenshot(driver)+"' \r\n";
		}
		catch(Exception e)
		{
			return getErrorMessage(e,objectName);
		}
	}
	private static String runReplaceClick(WebDriver driver, String objectName,String objectData,String browser_type)
	{

		try
		{

			driver.manage().timeouts().implicitlyWait(50,TimeUnit.SECONDS);
			String input=getIdentifier(objectName).toString().replace("XXX",strRandomValues.get(objectData));
			int size=driver.findElements(By.xpath(input.substring(input.indexOf(":")+1, input.length()))).size();
			if(size==0)
				return "::Failed  - '"+objectName + "' element is not found '"+takeScreenshot(driver)+"' \r\n";
			else
			{
				driver.findElement(By.xpath(input.substring(input.indexOf(":")+1, input.length()))).click();
				return "::Passed - Successfully replace the text '" + objectData + "' in the field '" + objectName + "' and clicked. \r\n";
			}
		}
		catch(STEPFrameworkMissingORObjectException e)
		{
			return "::Failed  - '"+objectName+"' object is missing in the Object Repository. \r\n";
		}
		catch(STEPFrameworkInvalidIdentifierException e)
		{
			return "::Failed  - Invalid identifier for the object "+objectName+" in Object Repository. \r\n";
		}
		catch(Exception e)
		{
			return getErrorMessage(e,objectName);
		}
	}
	/***
	 * Purpose: Click on the given object.
	 * @param driver
	 * @param objectName
	 * @param browser_type
	 * @return
	 */
	private static String runClick(WebDriver driver, String objectName,String browser_type)
	{
		try
		{
			driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
			int size=driver.findElements(getIdentifier(objectName)).size();
			if(size==0)
				return "::Failed  - '"+objectName + "' element is not found '"+takeScreenshot(driver)+"' \r\n";
			else
			{
				WebElement element=driver.findElement(getIdentifier(objectName));
				element.click();
				return "::Passed - Successfully clicked on '"	+ objectName + "'. \r\n";
			}
		}
		catch(STEPFrameworkMissingORObjectException e)
		{
			return "::Failed  - '"+objectName+"' object is missing in the Object Repository. \r\n";
		}
		catch(STEPFrameworkInvalidIdentifierException e)
		{
			return "::Failed  - Invalid identifier for the object "+objectName+" in Object Repository. \r\n";
		}
		catch(NoSuchElementException e)
		{
			return "::Failed  - '"+objectName + "' element is not found '"+takeScreenshot(driver)+"' \r\n";
		}
		catch(Exception e)
		{
			return getErrorMessage(e,objectName);
		}
	}
	/***
	 * Purpose: Press the enter key on the element given.
	 * @param driver
	 * @param objectName
	 * @param browser_type
	 * @return
	 */
	private static String runPressEnter(WebDriver driver, String objectName,String browser_type)
	{
		try
		{
			driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
			int size=driver.findElements(getIdentifier(objectName)).size();
			if(size==0)
				return "::Failed  - '"+objectName + "' element is not found '"+takeScreenshot(driver)+"' \r\n";
			else
			{
				driver.findElement(getIdentifier(objectName)).sendKeys(Keys.ENTER);
				return "::Passed - Successfully clicked on '"	+ objectName + "'. \r\n";
			}
		}
		catch(STEPFrameworkMissingORObjectException e)
		{
			return "::Failed  - '"+objectName+"' object is missing in the Object Repository. \r\n";
		}
		catch(STEPFrameworkInvalidIdentifierException e)
		{
			return "::Failed  - Invalid identifier for the object "+objectName+" in Object Repository. \r\n";
		}
		catch(NoSuchElementException e)
		{
			return "::Failed  - '"+objectName + "' element is not found '"+takeScreenshot(driver)+"' \r\n";
		}
		catch(Exception e)
		{
			return getErrorMessage(e,objectName);
		}
	}
	/***
	 * Purpose: Select and click the item from the listbox
	 * @param driver
	 * @param objectName
	 * @param objectData
	 * @param browser_type
	 * @return
	 */
	private static String runListboxSelect(WebDriver driver, String objectName,String objectData,String browser_type)
	{
		try
		{
			driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
			int size=driver.findElements(getIdentifier(objectName)).size();
			if(size==0)
				return "::Failed  - '"+objectName + "' element is not found '"+takeScreenshot(driver)+"' \r\n";
			else
			{
				WebElement list1=driver.findElement(getIdentifier(objectName));
				List<WebElement> lstOptions=list1.findElements(By.tagName("option"));
				list1.sendKeys(Keys.CONTROL);
				for(WebElement option : lstOptions)
				{
					if(option.getText().equals(objectData))
					{
						option.click();
						break;
					}

				}

				return "::Passed - Successfully selected by option '"	+ objectData + "' on the List box '"+objectName+"'. \r\n";
			}
		}
		catch(STEPFrameworkMissingORObjectException e)
		{
			return "::Failed  - '"+objectName+"' object is missing in the Object Repository. \r\n";
		}
		catch(STEPFrameworkInvalidIdentifierException e)
		{
			return "::Failed  - Invalid identifier for the object "+objectName+" in Object Repository. \r\n";
		}
		catch(NoSuchElementException e)
		{
			return "::Failed  - '"+objectName + "' element is not found '"+takeScreenshot(driver)+"' \r\n";
		}
		catch(Exception e)
		{
			return getErrorMessage(e,objectName);
		}
	}
	private static String runSelectByValue(WebDriver driver, String objectName,String objectData,String browser_type)
	{
		try
		{
			driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
			int size=driver.findElements(getIdentifier(objectName)).size();
			if(size==0)
				return "::Failed  - '"+objectName + "' element is not found '"+takeScreenshot(driver)+"' \r\n";
			else
			{
				Select list1=new Select(driver.findElement(getIdentifier(objectName)));
				list1.selectByValue(objectData);
				return "::Passed - Successfully selected by option '"	+ objectData + "' on the List box '"+objectName+"'. \r\n";
			}
		}
		catch(STEPFrameworkMissingORObjectException e)
		{
			return "::Failed  - '"+objectName+"' object is missing in the Object Repository. \r\n";
		}
		catch(STEPFrameworkInvalidIdentifierException e)
		{
			return "::Failed  - Invalid identifier for the object "+objectName+" in Object Repository. \r\n";
		}
		catch(NoSuchElementException e)
		{
			return "::Failed  - '"+objectName + "' element is not found '"+takeScreenshot(driver)+"' \r\n";
		}
		catch(Exception e)
		{
			return getErrorMessage(e,objectName);
		}
	}
	private static String runListBoxRandomText(WebDriver driver, String objectName,String objectData,String browser_type)
	{
		try
		{
			driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
			int size=driver.findElements(getIdentifier(objectName)).size();
			if(size==0)
				return "::Failed  - '"+objectName + "' element is not found '"+takeScreenshot(driver)+"' \r\n";
			else
			{
				WebElement list1=driver.findElement(getIdentifier(objectName));
				List<WebElement> lstOptions=list1.findElements(By.tagName("option"));
				list1.sendKeys(Keys.CONTROL);
				for(WebElement option : lstOptions)
				{
					String op=option.getText();
					//System.out.println("Option : "+op);
					//System.out.println("Option2 : "+strRandomValues.get(objectData));
					if(op.trim().equals(strRandomValues.get(objectData).trim()))
					{
						option.click();
						break;
					}

				}
				return "::Passed - Successfully selected option '"	+ strRandomValues.get(objectData) + "' on the List box '"+objectName+"'. \r\n";
			}
		}
		catch(STEPFrameworkMissingORObjectException e)
		{
			return "::Failed  - '"+objectName+"' object is missing in the Object Repository. \r\n";
		}
		catch(STEPFrameworkInvalidIdentifierException e)
		{
			return "::Failed  - Invalid identifier for the object "+objectName+" in Object Repository. \r\n";
		}
		catch(NoSuchElementException e)
		{
			return "::Failed  - '"+objectName + "' element is not found '"+takeScreenshot(driver)+"' \r\n";
		}
		catch(Exception e)
		{
			return getErrorMessage(e,objectName);
		}
	}
	/***
	 * Purpose: Type the given text on the given element.
	 * @param driver
	 * @param objectName
	 * @param objectData
	 * @param browser_type
	 * @return
	 */
	private static String runType(WebDriver driver, String objectName,String objectData,String browser_type)
	{

		try
		{

			driver.manage().timeouts().implicitlyWait(15,TimeUnit.SECONDS);
			int size=driver.findElements(getIdentifier(objectName)).size();
			if(size==0)
				return "::Failed  - '"+objectName + "' element is not found '"+takeScreenshot(driver)+"' \r\n";
			else
			{

				driver.findElement(getIdentifier(objectName)).sendKeys(objectData);

				return "::Passed - Successfully entered the text '" + objectData + "' in the field '" + objectName + "'. \r\n";
			}
		}
		catch(STEPFrameworkMissingORObjectException e)
		{
			return "::Failed  - '"+objectName+"' object is missing in the Object Repository. \r\n";
		}
		catch(STEPFrameworkInvalidIdentifierException e)
		{
			return "::Failed  - Invalid identifier for the object "+objectName+" in Object Repository. \r\n";
		}
		catch(NoSuchElementException e)
		{
			return "::Failed  - '"+objectName + "' element is not found '"+takeScreenshot(driver)+"' \r\n";
		}
		catch(Exception e)
		{
			return getErrorMessage(e,objectName);
		}
	}
	/***
	 * Purpose: Clear Text from Text Field.
	 * @param driver
	 * @param objectName
	 * @param objectData
	 * @param browser_type
	 * @return
	 */
	private static String runClearText(WebDriver driver, String objectName,String objectData,String browser_type)
	{

		try
		{

			driver.manage().timeouts().implicitlyWait(15,TimeUnit.SECONDS);
			int size=driver.findElements(getIdentifier(objectName)).size();
			if(size==0)
				return "::Failed  - '"+objectName + "' element is not found '"+takeScreenshot(driver)+"' \r\n";
			else
			{

				driver.findElement(getIdentifier(objectName)).clear();

				return "::Passed - Successfully clear text in the field '" + objectName + "'. \r\n";
			}
		}
		catch(STEPFrameworkMissingORObjectException e)
		{
			return "::Failed  - '"+objectName+"' object is missing in the Object Repository. \r\n";
		}
		catch(STEPFrameworkInvalidIdentifierException e)
		{
			return "::Failed  - Invalid identifier for the object "+objectName+" in Object Repository. \r\n";
		}
		catch(NoSuchElementException e)
		{
			return "::Failed  - '"+objectName + "' element is not found '"+takeScreenshot(driver)+"' \r\n";
		}
		catch(Exception e)
		{
			return getErrorMessage(e,objectName);
		}
	}

	public static String runFileUpload(WebDriver driver,String objectName,String objectData,String browser_type)
	{
		try
		{
			driver.manage().timeouts().implicitlyWait(15,TimeUnit.SECONDS);
			String Location =STEPFrameworkDriver.strFrameworkLocation+objectData;
			System.out.println("Location: "+Location);
			System.out.println(objectName);
			WebElement element=driver.findElement(getIdentifier(objectName));
			element.sendKeys(Location);
			return "::Passed - Successfully Selected the File '"+objectData+"'. \r\n";
		}
		catch(STEPFrameworkMissingORObjectException e)
		{
			return "::Failed  - '"+objectName+"' object is missing in the Object Repository. \r\n";
		}
		catch(STEPFrameworkInvalidIdentifierException e)
		{
			return "::Failed  - Invalid identifier for the object "+objectName+" in Object Repository. \r\n";
		}
		catch(Exception e)
		{
			return "::Failed  - File Select on '"+objectName+"' is not performed. \r\n";
		}
	}
	/***
	 * Purpose: Type the random number in the text field based on the type.
	 * @param driver
	 * @param objectName
	 * @param objectData
	 * @param browser_type
	 * @return
	 */
	private static String runTypeRandomNumber(WebDriver driver, String objectName,String objectData,String browser_type)
	{

		try
		{

			driver.manage().timeouts().implicitlyWait(15,TimeUnit.SECONDS);
			int size=driver.findElements(getIdentifier(objectName)).size();
			if(size==0)
				return "::Failed  - '"+objectName + "' element is not found '"+takeScreenshot(driver)+"' \r\n";
			else
			{

				driver.findElement(getIdentifier(objectName)).sendKeys(strRandomValues.get(objectData));

				return "::Passed - Successfully entered the text '" + strRandomValues.get(objectData) + "' in the field '" + objectName + "'. \r\n";
			}
		}
		catch(STEPFrameworkMissingORObjectException e)
		{
			return "::Failed  - '"+objectName+"' object is missing in the Object Repository. \r\n";
		}
		catch(STEPFrameworkInvalidIdentifierException e)
		{
			return "::Failed  - Invalid identifier for the object "+objectName+" in Object Repository. \r\n";
		}
		catch(org.openqa.selenium.NoSuchElementException e)
		{
			return "::Failed  - '"+objectName + "' element is not found '"+takeScreenshot(driver)+"' \r\n";
		}
		catch(Exception e)
		{
			return getErrorMessage(e,objectName);
		}
	}
	/***
	 * Purpose: Accept the Alert Window.
	 * @param driver
	 * @return
	 */
	public static String runAlertAccept(WebDriver driver)
	{
		try
		{
			driver.switchTo().alert().accept();
			return "::Passed - Successfully accepted alert popup.  \r\n";
		}
		catch(NoAlertPresentException e)
		{
			return "::Failed  - No Alert is not found '"+takeScreenshot(driver)+"' \r\n";
		}
		catch(Exception e)
		{
			return getErrorMessage(e);
		}
	}
	public static String runConfirmMessage(WebDriver driver,String objectData)
	{
		try
		{
			driver.manage().timeouts().implicitlyWait(15,TimeUnit.SECONDS);
			final JFrame parent = new JFrame();
			JOptionPane.showMessageDialog(parent,objectData,"Imp Warning",JOptionPane.WARNING_MESSAGE);
			parent.dispose();
			return "::Passed - Successfully clicked ok on Message Dialog.  \r\n";
		}
		catch(NoAlertPresentException e)
		{
			return "::Failed  - No Alert is not found '"+takeScreenshot(driver)+"' \r\n";
		}
		catch(Exception e)
		{
			return getErrorMessage(e);
		}
	}
	/***
	 * Author	:
	 * Purpose	: Read all the object repository values from the given sheet of the Object Repository Files
	 * @param keywordName
	 * @return
	 */
	private static String runSetPage(String keywordName) {

		try
		{
			int No_of_Workbooks=STEPFrameworkExecutor.testSuiteObjectRepWB.size();
			Sheet objectRepSheet=null;
			for(int count=0;count<=No_of_Workbooks-1;count++)
			{
				Workbook objectRepWB = STEPFrameworkExecutor.getTestSuiteObjectRepWB(count);
				objectRepSheet = objectRepWB.getSheet(keywordName);
				if(objectRepSheet!=null)
					break;
			}
			Hashtable<String, String[]> objectRepKeyValue = new Hashtable<String, String[]>();

			for (int objectRepValueCount = 1; objectRepValueCount <= objectRepSheet.getLastRowNum(); objectRepValueCount++)
			{

				Row objectRepValueRow = objectRepSheet.getRow(objectRepValueCount);
				Cell objectRepObjectNameCell = objectRepValueRow.getCell(0, Row.CREATE_NULL_AS_BLANK);
				String objectName = objectRepObjectNameCell.getStringCellValue();
				Cell objectRepObjectValueCell = objectRepValueRow.getCell(1, Row.CREATE_NULL_AS_BLANK);
				String objectValue = "";
				objectValue = objectRepObjectValueCell.getStringCellValue().replace("\\", "");
				Cell objectRepObjectIdentifierCell = objectRepValueRow.getCell(2, Row.CREATE_NULL_AS_BLANK);
				String identifierValue = objectRepObjectIdentifierCell.getStringCellValue();
				String[] objectIdentiferPair = {objectValue.toString(), identifierValue};
				objectRepKeyValue.put(objectName, objectIdentiferPair);

			}

			STEPFrameworkExecutor.setTestSuiteObjectRep(objectRepKeyValue);
			return "::Passed - Successfully set Page to '" + keywordName + "'. \r\n";

		}
		catch (NullPointerException e)
		{

			return "::Failed  - Could not set the Page to '" + keywordName + "'. \r\n";

		}

	}
	/***
	 * Author	:
	 * Purpose	: return the By class for the objects using UniqueID and Identifiers from Object Repository file of the given object
	 * @param objectName
	 * @return
	 */
	private static By getIdentifier(String objectName) throws STEPFrameworkMissingORObjectException, STEPFrameworkInvalidIdentifierException
	{

		if(STEPFrameworkExecutor.getTestSuiteObjectRep().containsKey(objectName)==false)
		{
			throw new STEPFrameworkMissingORObjectException(objectName);
		}
		if(STEPFrameworkExecutor.getTestSuiteObjectRep().get(objectName)[0]==null)
		{
			throw new STEPFrameworkMissingORObjectException(objectName);
		}

		String identifier=STEPFrameworkExecutor.getTestSuiteObjectRep().get(objectName)[1];
		if (identifier.toLowerCase().equalsIgnoreCase("id"))
		{


			return By.id(STEPFrameworkExecutor.getTestSuiteObjectRep().get(objectName)[0]);

		}
		else if (identifier.toLowerCase().equalsIgnoreCase("cssselector"))
		{

			return By.cssSelector(STEPFrameworkExecutor.getTestSuiteObjectRep().get(objectName)[0].toString());

		}
		else if (identifier.equalsIgnoreCase("xpath"))
		{

			return By.xpath(STEPFrameworkExecutor.getTestSuiteObjectRep().get(objectName)[0].toString());

		}
		else if (identifier.equalsIgnoreCase("className"))
		{
			return By.className(STEPFrameworkExecutor.getTestSuiteObjectRep().get(objectName)[0].toString());

		}
		else if (identifier.equalsIgnoreCase("name"))
		{

			return By.name(STEPFrameworkExecutor.getTestSuiteObjectRep().get(objectName)[0].toString());

		}
		else if (identifier.equalsIgnoreCase("LinkText"))
		{

			return By.linkText(STEPFrameworkExecutor.getTestSuiteObjectRep().get(objectName)[0].toString());

		}
		else if (identifier.equalsIgnoreCase("PartialLinkText"))
		{

			return By.partialLinkText(STEPFrameworkExecutor.getTestSuiteObjectRep().get(objectName)[0].toString());

		}
		else
		{

			throw new STEPFrameworkInvalidIdentifierException(identifier,objectName);

		}

	}

	/***
	 * Author	:
	 * Purpose	: To take the screenshot from the application using web driver
	 			  Store file in the screenshotLocation folder of current execution test results folder
	 * @param driver
	 * @param browser_type
	 * @return
	 */
	private static String takeScreenshot(WebDriver driver)
	{
		String filename= System.currentTimeMillis() + ".png";
		String screenshotLocationFile = screenshotLocation+"\\"+STEPFrameworkDriver.strResultFileName+"\\ScreenshotLocation\\"+filename;

		File screenshotDestination = new File(screenshotLocationFile);

		try
		{
			File screenShot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);

			FileUtils.moveFile(screenShot, screenshotDestination);

		}
		catch(Exception e)
		{
			System.out.println("Exception :"+e);
		}

		return ". <ssloc> ScreenshotLocation\\" + filename 	+ "</ssloc> Error_ScreenShot ";

	}
	public static String getErrorMessage(Exception e)
	{
		if(e.getClass().getName().equals("org.openqa.selenium.UnhandledAlertException"))
		{
			return "::Failed  - Unable to handle Alerts. \r\n";
		}
		else if(e.getClass().getName().equals("org.openqa.selenium.NoSuchElementException"))
		{
			return "::Failed  - No Such Element Exception is Found. \r\n";
		}
		else if(e.getClass().getName().equals("org.openqa.selenium.WebDriverException"))
		{
			return "::Failed  - WebDriver Exception is Found. \r\n";
		}
		else if(e.getClass().getName().equals("org.openqa.selenium.ElementNotVisibleException"))
		{
			return "::Failed  - Element is NotVisible Exception is Found. \r\n";
		}
		else
		{
			return "::Failed  - Exception is found while executing keyword '"+e+"'. \r\n";
		}
	}
	public static String getErrorMessage(Exception e,String objectName)
	{
		if(e.getClass().getName().equals("org.openqa.selenium.UnhandledAlertException"))
		{
			return "::Failed  - '"+objectName+"' is not Found. \r\n";
		}
		else if(e.getClass().getName().equals("org.openqa.selenium.NoSuchElementException"))
		{
			return "::Failed  - No Such Element Exception is Found. \r\n";
		}
		else if(e.getClass().getName().equals("org.openqa.selenium.ElementNotVisibleException"))
		{
			return "::Failed  - Element is NotVisible Exception is Found. \r\n";
		}
		else if(e.getClass().getName().equals("org.openqa.selenium.WebDriverException"))
		{
			return "::Failed  - '"+objectName+"' is not Found. \r\n";
		}
		else
		{
			return "::Failed  - '"+objectName+"' is not Found. \r\n";
		}
	}



	/***
	 * Author: Mohamed Abdulkadar
	 * Purpose: Verify Color Display
	 * @param driver
	 * @param objectName
	 * @param objectData  (Example : RED,AMBER)
	 * @return
	 */
public static String runVerifyColor(WebDriver driver,String objectName,String objectData)
	{
		try
		{
			driver.manage().timeouts().implicitlyWait(120,TimeUnit.SECONDS);
			int size=driver.findElements(getIdentifier(objectName)).size();
			if(size==0)
				return "::Failed  - '"+objectName + "' element is not found '"+takeScreenshot(driver)+"' \r\n";
			else
			{
				WebElement element=driver.findElement(getIdentifier(objectName));
				String color=element.getCssValue("color");

				System.out.println("RGB Color in UI: "+color);

				if(color != null)
				{
					if (objectData.equals("AMBER") && color.equals("rgba(255, 191, 0, 1)"))
					{
						return "::Passed - "+objectData+" Color Successfully Displayed . \r\n";
					}else if(objectData.equals("RED") && color.equals("rgba(255, 0, 0, 1)"))
					{
						return "::Passed - "+objectData+" Color Successfully Displayed . \r\n";
					} else
					{
						return "::Failed  - '"+objectData + "' is not displayed '"+takeScreenshot(driver)+"' \r\n";
					}
				}
				return "::Failed  - '"+objectData + "' is not displayed '"+takeScreenshot(driver)+"' \r\n";
			}
		}
		catch(STEPFrameworkMissingORObjectException e)
		{
			return "::Failed  - '"+objectName+"' object is missing in the Object Repository. \r\n";
		}
		catch(STEPFrameworkInvalidIdentifierException e)
		{
			return "::Failed  - Invalid identifier for the object "+objectName+" in Object Repository. \r\n";
		}
		catch(Exception e)
		{
			return getErrorMessage(e);
		}
	}
/***
 * Author	: Mohamed Abdulkadar
 * Purpose	: Retrieve data from Database with single column value and store value in Hash Table
 * @param objectData (Vendor|VariableName|Server|DBName|username|password|SQLQuery
 * @return
 */
public static String retriveDB(String objectData)
{

	try
	{
	System.out.println("Data from ObjectData: "+objectData);
	String[] parameters=objectData.split("\\|");

	String urlstr=null;

	String DBVendor=parameters[0];
    System.out.println("DBVendor Name: "+parameters[0]);
	String Variable=parameters[1];
    System.out.println("Variable Name: "+parameters[1]);
    String query1=GetFormattedQuery(parameters[2]);
    System.out.println("query1 "+parameters[2]);



    String Server=null;
    String DBName=null;
    String Username=null;
    String Password=null;
    String value=null;


    if(DBVendor.toUpperCase().equalsIgnoreCase("VERTICA"))
    {
    Server=STEPKeywordLibrary.verticaHost;
    DBName=STEPKeywordLibrary.verticaDBName;
    Username=STEPKeywordLibrary.verticaUsername;
    Password=STEPKeywordLibrary.verticaPassword;

    urlstr="jdbc:vertica://"+Server+"/"+DBName;


    }else if (DBVendor.toUpperCase().equalsIgnoreCase("ORACLE"))
    {
    	try{
    		Class.forName("oracle.jdbc.driver.OracleDriver");

    	}catch(ClassNotFoundException e)
    	{
    		System.out.println("::Failed - No Oracle JDBC Driver");
    		e.printStackTrace();

    	}

    	Server=STEPKeywordLibrary.oracleHost;
	    System.out.println("Server "+Server);
	    DBName=STEPKeywordLibrary.oracleDBName;
	    System.out.println("DBName "+DBName);
	    Username=STEPKeywordLibrary.oracleUsername;
	    System.out.println("Username "+Username);
	    Password=STEPKeywordLibrary.oraclePassword;
	    System.out.println("Password "+Password);

    	urlstr="jdbc:oracle:thin:@"+Server+"/"+DBName;
    	System.out.println("url conneciton string: "+urlstr);
    }else
    {
    	return "::Failed- Database "+DBVendor+" is not supported.Currently, we are supporting ORACLE and VERTICA";
    }


    Connection con=null;
	Statement stmt =null;


	try {
		con=DriverManager.getConnection(urlstr,Username,Password);

		System.out.println("Connection successfull");


		stmt=con.createStatement();

		ResultSet rs=stmt.executeQuery(query1);


		if(rs.next())
		{
		value=rs.getString(Variable);
		}

		//To avoid null value to be store in hashtable strRandomValues
		if(value==null)
		{
			value="0";
			strRandomValues.put(Variable, value);
			System.out.println("SQL statemet returns null value");
			rs.close();
			stmt.close();
			con.close();
			return "::Fail:- Null value received from the DB. \r\n";
		}else
		{
		strRandomValues.put(Variable, value);
		System.out.println("Actual Value from DB : "+value);
		rs.close();
		stmt.close();
		con.close();
		}


	}catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return getErrorMessage(e);
	}finally
	{
		//finally block used to close resource
		try
		{
			if (stmt != null)
				stmt.close();

		}
		catch(SQLException e)
		{
			return getErrorMessage(e);
		}

		try
		{
			if (con != null)
				con.close();


		}catch(Exception e)
		{
			return getErrorMessage(e);
		}


	}


    return "Data received successfully from the DB. \r\n";

 }catch(ArrayIndexOutOfBoundsException e)
 {
	 return getErrorMessage(e);
 }
}

/***
 * Author	: Mohamed Abdulkadar
 * Purpose	: Retrieve data from Database with single column value and store value in Hash Table
 * @param objectData (Vendor|VariableName|Server|DBName|username|password|SQLQuery
 * @return
 */
public static String retriveDB(String dbType,String sqlQuery)
{

	try
	{

	String urlstr=null;
	String DBVendor=dbType;
	String query1=sqlQuery;

    String Server=null;
    String DBName=null;
    String Username=null;
    String Password=null;


    if(DBVendor.toUpperCase().equalsIgnoreCase("VERTICA"))
    {
    Server=STEPKeywordLibrary.verticaHost;
    DBName=STEPKeywordLibrary.verticaDBName;
    Username=STEPKeywordLibrary.verticaUsername;
    Password=STEPKeywordLibrary.verticaPassword;

    urlstr="jdbc:vertica://"+Server+"/"+DBName;


    }else if (DBVendor.toUpperCase().equalsIgnoreCase("ORACLE"))
    {
    	try{
    		Class.forName("oracle.jdbc.driver.OracleDriver");

    	}catch(ClassNotFoundException e)
    	{
    		System.out.println("::Failed - No Oracle JDBC Driver");
    		e.printStackTrace();

    	}

    	Server=STEPKeywordLibrary.oracleHost;
	    System.out.println("Server "+Server);
	    DBName=STEPKeywordLibrary.oracleDBName;
	    System.out.println("DBName "+DBName);
	    Username=STEPKeywordLibrary.oracleUsername;
	    System.out.println("Username "+Username);
	    Password=STEPKeywordLibrary.oraclePassword;
	    System.out.println("Password "+Password);

    	urlstr="jdbc:oracle:thin:@"+Server+"/"+DBName;
    	System.out.println("url conneciton string: "+urlstr);
    }else
    {
    	return "::Failed- Database "+DBVendor+" is not supported.Currently, we are supporting ORACLE and VERTICA";
    }


    Connection con=null;
	Statement stmt =null;


	try {
		con=DriverManager.getConnection(urlstr,Username,Password);

		System.out.println("Connection successfull");

		stmt=con.createStatement();
		ResultSet rs=stmt.executeQuery(query1);

		int count=0;


		  while (rs.next())
		  {
			  count++;
			  String column1 = (String) rs.getString(1);
			  String column2 = (String) rs.getString(2);
			  String column3 = (String) rs.getString(3);
			  String column4 = (String) rs.getString(4);

			  ModelTable tb1 = new ModelTable(column1,column2,column3,column4);
			  retreivedFromDB.add(tb1);
		  }



		System.out.println(count);
		rs.close();
		stmt.close();
		con.close();



	}catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return getErrorMessage(e);
	}finally
	{
		//finally block used to close resource
		try
		{
			if (stmt != null)
				stmt.close();

		}
		catch(SQLException e)
		{
			return getErrorMessage(e);
		}

		try
		{
			if (con != null)
				con.close();


		}catch(Exception e)
		{
			return getErrorMessage(e);
		}


	}


    return "Data received successfully from the DB. \r\n";

 }catch(ArrayIndexOutOfBoundsException e)
 {
	 return getErrorMessage(e);
 }
}



public static ArrayList<ModelTable> getDbData()
{
	return dbData;
}

/***
 * Author	: Mohamed Abdulkadar
 * Purpose	: Query builder , if user send query with variable.
 * @param query
 *
 */
public static String GetFormattedQuery(String query) {


 String CorrectValue=null;
 String Var2Sub=null;
 String Column=null;
 String finalQuery=null;
 String ColumValue=null;
 String tempVar=null;
 int left=0;
 int right=0;
 int index1=0;

 try
 {
 String[] param=query.split(":");
 finalQuery =query;

 if(param.length>1)
 {
 for (int x=1;x<param.length;x++)
 {
	 String Str=param[x];
	 System.out.println("String to be parsed: "+Str);

         if(param[x].trim().equalsIgnoreCase("AND") || param[x].trim().equalsIgnoreCase("OR"))
         	{
        	 	CorrectValue =param[x];
        	 }else
         		{

         		    left=Str.indexOf(">");
                    right=Str.indexOf("<");
                    index1=Str.indexOf("=");
         		    Var2Sub=Str.substring(right+1, left);
         			ColumValue="'"+(strRandomValues.get(Var2Sub))+"'";
         			Column=Str.substring(0,index1+1);
         			CorrectValue=Column+ColumValue;
         		}

              if(x==1)
              {
              tempVar=CorrectValue;
              }else
              {
            	  tempVar+=CorrectValue;
              }


  }
 finalQuery=param[0].trim()+" "+tempVar;
 System.out.println("Query Builder: "+finalQuery);
 }


} catch(ArrayIndexOutOfBoundsException e)
 {
	 e.printStackTrace();
	 //System.out.println("ERROR :: Query has no proper delimiter |. Example : SELECT COUNT(*) as COUNT FROM TACTIVITY WHERE DELETED_DATE is null AND |ACCOUNTKEY=<1>| AND |STATUS=<'A'>|");

	 return getErrorMessage(e);

  } catch(StringIndexOutOfBoundsException e)
	 {

		 System.out.println("ERROR :: Query has no proper delimiter < and >. Example : SELECT COUNT(*) as variablename FROM CAMPAIGN WHERE STATUS='A' AND DELETED_DATE is null AND ACCOUNTKEY=1 AND :ADVERTISERKEY=<ADVERTISERKEY>:");
		 return getErrorMessage(e);
	 }
return finalQuery;

 }
/***
 * Author	: Mohamed Abdulkadar
 * Purpose	: Turbine, Metric calculations
 * @param objectData
 *
 */
public static String runVerifyMetricData(WebDriver driver,String objectData) {

	String[] param=objectData.split("\\|");


	//TotalVisitorCount verification starting. Expected value is "TotalVisitorCount|UITotalVisitorCount|DBTotalVisitorCount"
	if(param[0].equalsIgnoreCase("TotalVisitorCount"))
    {

     try
     {
   	 String UI_value=strRandomValues.get(param[1]);
   	 System.out.println("Data from Hash table UI_value: "+UI_value);
   	 double DB_value=(double)(Double.parseDouble(strRandomValues.get(param[2]))/1000.00);
   	 String ActualUI_value=null;


   	 System.out.println("DB_value : "+DB_value);


   	 //Keep only the Numeric values from UI
   	 if(UI_value==null)
   	 {
   		return "::Failed  - UI has NULL value. \r\n";
   	 }
   	 int end=UI_value.indexOf("K");

   	     if (end<0)
   	     {
   	    	ActualUI_value=UI_value;
   	    	System.out.println("UI_value if it has no character: "+ActualUI_value);
   	     }else
   	     {
   	    	ActualUI_value=UI_value.substring(0, end);
   	      System.out.println("UI_value if it has character: "+ActualUI_value);
   	     }// End of keeping Numeric values

   	     double act_value=Double.parseDouble(ActualUI_value);
   	     double diff_val;


   		 //Compare UI and DB values
   		 if(DB_value > act_value)
   		 {
   			 diff_val=DB_value - act_value;
   		 }else
   		 {
   			diff_val=act_value - DB_value;
   		 }

   		 if (diff_val < 0.1)
   		 {
   		 return "::Pass  - " +param[1]+ " is matching between UI_value: "+UI_value+" and DB_value: "+DB_value+". \r\n";
   		 }else
   		 {
   			return "::Failed  - " +param[1]+ " is not matching between UI_value: "+UI_value+" and DB_value: "+DB_value+" "+takeScreenshot(driver)+". \r\n";
   		 }
     }catch(NumberFormatException e)
     {
     	e.printStackTrace();
     	return getErrorMessage(e);

     }
     } //End of TotalVisitor Calculation

    else if(param[0].equalsIgnoreCase("AudienceChange"))  // Starting "WEEK ON WEEK AUDIENCE CHANGE" METRIC CALCULATION.Expecting "AudienceChange|UI_ACT_WeekOnWeekAudienceChange|DB_ACT_YESTERDAY_AUDIENCSIZE|DB_AudienceSize_7_days_before"
    {

    	try
    	{
    	String UI_value=strRandomValues.get(param[1]);
    	System.out.println("UI_Value retrieved from UI: "+UI_value);
    	String DB_AudSize1=strRandomValues.get(param[2]); // Data from DB for "Change in Audience Size in Account Level"
    	String DB_AudSize2=strRandomValues.get(param[3]); // Data from DB for "Audience Size 7 days before (Yesterday -7)"
    	//String Exp_result="";
    	String ActualUI_value="";

    	//Converting DB Values to Double for calculation.
      	 double db_audsize1=Double.parseDouble(DB_AudSize1);
      	 double db_audsize2=Double.parseDouble(DB_AudSize2);
      	 double db_exp;

      	 //Formula to calculate the Metric "% Change in Audience Size =[(current audience size - previous audience size)/ previous audience size]*100"
      	   db_exp=(double) ((db_audsize1-db_audsize2)/db_audsize2);
     	   db_exp=(double) (db_exp * 100.00);



      	//Keep only the Numeric values from UI
     	   if(UI_value==null)
     	   {
     		  return "::Failed  - UI has return NULL value. \r\n";
     	   }
       	 int end=UI_value.indexOf("%");

       	     if (end<0)
       	     {
       	    	ActualUI_value=UI_value;
       	    	System.out.println("UI_value if it has no character: "+ActualUI_value);
       	     }else
       	     {
       	    	ActualUI_value=UI_value.substring(0, end);
       	        System.out.println("UI_value if it has character: "+ActualUI_value);
       	     }// End of keeping Numeric values

       	  //Convert UI value as Double from String

       	    double ui_audsize=Double.parseDouble(ActualUI_value);

       	//Compare UI and DB values
       	  double diff=0.00d;
       	  	if(ui_audsize > db_exp)
       	  	{
       	  		diff=(double) (ui_audsize-db_exp) ;
       	  	}else
       	  	{
       	  	    diff=(double) (db_exp - ui_audsize) ;
       	  	}

       	  	if(diff < 0.02)
       	  	{
       	  	  return "::Pass  - " +param[1]+ " is matching between UI_value: "+UI_value+" and Exp_result: "+db_exp+". \r\n";
       	  	}//End of Comparing the value

       	  	   return "::Failed  - " +param[1]+ " is not matching between UI_value: "+UI_value+" and Exp_result: "+db_exp+". \r\n";

    	}catch(NumberFormatException e)
            {
    		return getErrorMessage(e);

            }
    }//End of "Account level Audience Change calculation
        else
       {
    	return "Metric is not supported in the framework. \r\n";
       }



 }//End of runVerifyMetricData Method

/***
 * Author	: Mohamed Abdulkadar
 * Purpose	: Turbine, Metric calculations for a Model
 * @param driver
 * @param objectData
 *
 */
public static String runVerifyModelMetricData(WebDriver driver,String objectData) {

	String[] param=objectData.split("\\|");

	//Starting "Model AudienceSize Count Calculation".Expecting value from User as "AudienceCount|UI_AUDIENCE_SIZE|DB_YESTERDAY_AUDIENCE|DBTotalVisitorCount"
	if(param[0].equalsIgnoreCase("AudienceCount"))
	{
		try
		{
		String UI_AudSize=strRandomValues.get(param[1]);
		String DB_AudSize_Yesteday=strRandomValues.get(param[2]);
		String DB_TotalSize=strRandomValues.get(param[3]);
		String ActualUI_Size="";
		String ActualUI_Pre="";

		if (UI_AudSize==null)
		{
			return " ::Failed - Audience Count from UI is Null. \r\n";
		}

		//Retrieve Actual Audience_Size and Percentage from UI.
		int i=UI_AudSize.indexOf("K");
		System.out.println(i);
		int j=UI_AudSize.indexOf("(");
		System.out.println(j);
		int k=UI_AudSize.indexOf("%");
		System.out.println(k);

  	     if (i>0)
  	     {
  	    	ActualUI_Size=UI_AudSize.substring(0, i);  // Retrieve "Audience_Size" value from UI
  	    	ActualUI_Pre=UI_AudSize.substring(j+1, k);  // Retrieve "Audience Percentage" value from UI
  	    	System.out.println("Audience_Size from UI: "+ActualUI_Size);
  	    	System.out.println("Audience_Percentage from UI: "+ActualUI_Pre);

  	     }else
  	     {

  	    	 return " ::Failed - Audience Count from UI has no K. Actual Value from UI is: "+UI_AudSize+". \r\n";
  	     }// End of Retrieving "Audience_Size" and "Audience Percentage" values from UI

  	     //Convert DB values to Thousands by converting all values as double
  	      double exp_audsize = (double)(Double.parseDouble(DB_AudSize_Yesteday)/1000.00);
  	      double act_audsize = Double.parseDouble(ActualUI_Size);
  	      double db_totalvisitors = (double)(Double.parseDouble(DB_TotalSize)/1000.00);
  	      double act_audPre = Double.parseDouble(ActualUI_Pre);
  	      double exp_AudPre=0.00d;

  	     //Compare Audience Size UI and DB values
       	  double diff_AudSize=0.00d;

       	  	if(act_audsize > exp_audsize)
       	  	{
       	  		diff_AudSize=(double) (act_audsize-exp_audsize) ;

       	  	}else
       	  	{
       	  		diff_AudSize=(double) (exp_audsize - act_audsize) ;
       	  	}
       	    System.out.println("diff_AudSize: "+diff_AudSize);

       	  //calculate expected Audience Size percentage
       	  if(db_totalvisitors>0)
       	  {
       	  	 exp_AudPre = (double)((exp_audsize/db_totalvisitors)*100.00);

       	  }else
       	  {
       		return "Total Visitor count is Zero.Divisor by Zero has been thrown. \r\n";
       	  }

       	  //Compare AudienceSize in Percentage of UI and DB
       	  double diff_AudPre=0.00d;

       	  if(act_audPre > exp_AudPre)
   	  	  {
       		diff_AudPre=(double) (act_audPre-exp_AudPre) ;
   	  	  }else
   	  	    {
   	  		  diff_AudPre=(double) (exp_AudPre - act_audPre) ;
   	  	    }

  	      if ((diff_AudSize < 0.09) && (diff_AudPre < 0.09))
  	      {
  	    	  return ":: Pass - AudienceSizeCount and Percentage is matching. UI value(Actual) is: "+UI_AudSize+".DB value(Expected) is: "+exp_audsize+"K ,"+exp_AudPre+"%. \r\n";
  	      }else
  	      {

  	    	  return ":: Fail - AudienceSizeCount and Percentage is not matching. UI vaue(Actual) is: "+UI_AudSize+".DB value(Expected) is: "+exp_audsize+"K ,"+exp_AudPre+"%."+takeScreenshot(driver)+" \r\n";
  	      }
		}catch(NumberFormatException e)
	     {
	     	e.printStackTrace();
	     	return getErrorMessage(e);

	     }

	}//End of AudienceCount Verification

	else if(param[0].equalsIgnoreCase("AudienceChange"))  // Starting "WEEK ON WEEK AUDIENCE CHANGE" METRIC CALCULATION.Expecting "AudienceChange|UI_ACT_WeekOnWeekAudienceChange|DB_ACT_YESTERDAY_AUDIENCSIZE|DB_AudienceSize_7_days_before"
    {
    	String UI_value=strRandomValues.get(param[1]);
    	System.out.println("UI_Value retrieved from UI: "+UI_value);
    	String DB_AudSize1=strRandomValues.get(param[2]); // Data from DB for "Change in Audience Size in Account Level"
    	String DB_AudSize2=strRandomValues.get(param[3]); // Data from DB for "Audience Size 7 days before (Yesterday -7)"
    	//String Exp_result="";
    	String ActualUI_value="";

    	try
    	{
    	//Converting DB Values to Double for calculation.
      	 double db_audsize1=Double.parseDouble(DB_AudSize1);
      	 double db_audsize2=Double.parseDouble(DB_AudSize2);
      	 double db_exp;

      	 //Formula to calculate the Metric "% Change in Audience Size =[(current audience size - previous audience size)/ previous audience size]*100"
      	 db_exp=(double) (db_audsize1/db_audsize2);
      	 db_exp=(double) (db_exp * 100.00);

      	 if (UI_value==null)
      	 {
      		return "::Failed  - UI has return null value.\r\n";
      	 }
      	//Keep only the Numeric values from UI
       	 int end=UI_value.indexOf("%");

       	     if (end<0)
       	     {
       	    	ActualUI_value=UI_value;
       	    	System.out.println("UI_value if it has no character: "+ActualUI_value);
       	     }else
       	     {
       	    	ActualUI_value=UI_value.substring(0, end);
       	        System.out.println("UI_value if it has character: "+ActualUI_value);
       	     }// End of keeping Numeric values

       	  //Convert UI value as Double from String
       	  double ui_audsize=Double.parseDouble(ActualUI_value);

       	//Compare UI and DB values
       	  double diff=0.00d;
       	  	if(ui_audsize > db_exp)
       	  	{
       	  		diff=(double) (ui_audsize-db_exp) ;
       	  	}else
       	  	{
       	  	    diff=(double) (db_exp - ui_audsize) ;
       	  	}

       	  	if(diff < 0.09)
       	  	{
       	  	  return "::Pass  - " +param[1]+ " is matching between UI_value: "+UI_value+" and Exp_result: "+db_exp+". \r\n";
       	  	}//End of Comparing the value

       	  	   return "::Failed  - " +param[1]+ " is not matching between UI_value: "+UI_value+" and Exp_result: "+db_exp+". \r\n";
    	}catch(NumberFormatException e)
        {
         	e.printStackTrace();
         	return getErrorMessage(e);

         }
    }//End of "Model level Audience Change calculation

		return "Please correct the Metric in Your Test Case. ";
    }//End of runVerifyModelMetricData Method


/***
 * Author	: Mohamed Abdulkadar
 * Purpose	: Turbine - Progress Bar verification
 * @param driver
 * @param objectName
 * @param objectData
 *
 */
public static String runVerifyProgressBar(WebDriver driver,String objectName,String objectData) {


	String[] param=objectData.split("\\|");

	//Starting "Audience Change" calculation is started.Expected Input data as "DB_MAX_AUDIENCE_SIZE|DB_YESTERDAY_AUDIENCE|DB_YESTERDAY_AUDIENCE_SIZ|DB_AUDIENCE_SIZE_7DAYS_BEFORE"
	//Convert DB values to Thousands by converting all values as double
      try
      {
	  String Model_type=param[0];
	  double DB_YesterdaySize = Double.parseDouble(strRandomValues.get(param[1]));
      double DB_7DayB4Size = Double.parseDouble(strRandomValues.get(param[2]));

      double BlueGraph=0.00d;
      double RedGraph=0.00d;
      double GreenGraph=0.00d;
      boolean BlueElement = false,RedElement = false,GreenElement = false;
      String AudienceSize_BlueBar="";
      String AudienceSize_RedBar="";
      String AudienceSize_GreenBar="";

      //Temporary solution to verify progressing bar
      if(Model_type.equalsIgnoreCase("Interest"))
      {
    	 AudienceSize_BlueBar="Interest_AudienceSize_BlueBar";
         AudienceSize_RedBar="Interest_AudienceSize_RedBar";
         AudienceSize_GreenBar ="Interest_AudienceSize_GreenBar";
      }else
      {
    	  AudienceSize_BlueBar="AudienceSize_BlueBar";
    	  AudienceSize_RedBar="AudienceSize_RedBar";
    	  AudienceSize_GreenBar ="AudienceSize_GreenBar";
      }


      // Calculate Change in AudienceSize Change
      double ChangeInSize= (double) (DB_YesterdaySize - DB_7DayB4Size);

      // Find Graph Value
      if (ChangeInSize <= 0.00)
      {
    	  BlueGraph = DB_YesterdaySize;
    	  RedGraph = ChangeInSize;
      }else
      {
    	  BlueGraph = DB_7DayB4Size;
    	  GreenGraph = ChangeInSize;
      }

      if(BlueGraph > 0)
      {

    	  //String a=blueObjectName+"/div[@class='progress-bar']";
    	  driver.manage().timeouts().implicitlyWait(120,TimeUnit.SECONDS);
    	  int size;

		try{
			size = driver.findElements(getIdentifier(AudienceSize_BlueBar)).size();

    	  if(size==0)


    		  BlueElement=false;
    	         //return "::Failed  - '"+blueObjectName + "' element is not found '"+takeScreenshot(driver)+"' \r\n";

			else
			{
				BlueElement=true;
				if(driver.findElement(getIdentifier(AudienceSize_BlueBar)).isEnabled()==false)
				{
					BlueElement=false;
					//return "::Passed - Blue Progress Bar is displayed. \r\n";
				}


				//return "::Failed  - Blue Progress Bar is not displayed."+takeScreenshot(driver)+"' \r\n";
				    BlueGraph=-1;
			}

		}catch (STEPFrameworkMissingORObjectException e) {
  			// TODO Auto-generated catch block
  			e.printStackTrace();
  			return getErrorMessage(e);
  		} catch (STEPFrameworkInvalidIdentifierException e) {
  			// TODO Auto-generated catch block
  			e.printStackTrace();
  			return getErrorMessage(e);
  		}catch(NumberFormatException e)
  	     {
  	     	e.printStackTrace();
  	     	return getErrorMessage(e);

  	     }
      }

		if(RedGraph < 0)
	      {
	    	  //redObjectName = objectName+"/div[@class='progress-bar progress-bar-danger']";
	    	  driver.manage().timeouts().implicitlyWait(120,TimeUnit.SECONDS);
	    	  int size1;

			try{
				size1 = driver.findElements(getIdentifier(AudienceSize_RedBar)).size();

	    	  if(size1==0)
	    		  RedElement=false;
					//return "::Failed  - '"+redObjectName + "' element is not found '"+takeScreenshot(driver)+"' \r\n";
				else
				{

					if(driver.findElement(getIdentifier(AudienceSize_RedBar)).isEnabled()==false)
					{

						RedElement=false;
						//return "::Passed - Red Progress Bar is displayed. \r\n";
					}
					RedElement=true;
					//return "::Failed  - Red Progress Bar is not displayed."+takeScreenshot(driver)+"' \r\n";
					RedGraph=1;
				}

			}catch (STEPFrameworkMissingORObjectException e) {
	  			// TODO Auto-generated catch block
	  			e.printStackTrace();
	  			return getErrorMessage(e);
	  		} catch (STEPFrameworkInvalidIdentifierException e) {
	  			// TODO Auto-generated catch block
	  			e.printStackTrace();
	  			return getErrorMessage(e);
	  		}
	      }


			if(GreenGraph > 0)
		      {
		    	  //greenObjectName = objectName+"/div[@class='progress-bar progress-bar-success']";
		    	  driver.manage().timeouts().implicitlyWait(120,TimeUnit.SECONDS);
		    	  int size2;

				try{
					size2 = driver.findElements(getIdentifier(AudienceSize_GreenBar)).size();

		    	  if(size2==0)
						GreenElement=false;
		    		  //return "::Failed  - '"+greenObjectName + "' element is not found '"+takeScreenshot(driver)+"' \r\n";
					else
					{

						if(driver.findElement(getIdentifier(AudienceSize_GreenBar)).isEnabled()==false)
						{
							GreenElement=false;
							//return "::Passed - Green Progress Bar is displayed. \r\n";
						}
						GreenElement=true;
						//return "::Failed  - Green Progress Bar is not displayed."+takeScreenshot(driver)+"' \r\n";
						GreenGraph=-1;
					}

				}catch (STEPFrameworkMissingORObjectException e) {
		  			// TODO Auto-generated catch block
		  			e.printStackTrace();
		  			return "::Failed  - '"+objectName+"' object is missing in the Object Repository. \r\n";
		  		} catch (STEPFrameworkInvalidIdentifierException e) {
		  			// TODO Auto-generated catch block
		  			e.printStackTrace();
		  			return "::Failed  - Invalid identifier for the object "+objectName+" in Object Repository. \r\n";
		  		}
		      }

			if ((GreenElement==true) && (BlueElement==true))
			{
				return "::Passed - Blue and Green Progress Bar is displayed. \r\n";

			}else if ((RedElement==true) && (BlueElement==true))
			{
				return "::Passed - Red and Blue Progress Bar is displayed. \r\n";

			}else if(RedElement==true)
			{
				return "::Passed - Only Red Progress Bar is displayed. \r\n";

			}else if(GreenElement==true)
			{
				return "::Passed - Only Green Progress Bar is displayed. \r\n";
			}
			else
			{
				return "::Failed  - Progress Bar is not displayed."+takeScreenshot(driver)+"' \r\n";
			}
      }catch(NumberFormatException e)
      {
    	  return getErrorMessage(e);
    	}

      }

/***
 * Author	: Mohamed Abdulkadar
 * Purpose	: Verify the Sorting in WebTable
 * @param driver
 * @param objectName
 * @param objectData
 * @throws STEPFrameworkInvalidIdentifierException
 * @throws STEPFrameworkMissingORObjectException
 * @throws ParseException
 *
 */
/*public static String runVerifySorting(WebDriver driver,String objectName,String objectData) throws STEPFrameworkMissingORObjectException, STEPFrameworkInvalidIdentifierException, ParseException {

	String[] param=objectData.split("\\|");

	// Get all rows from WebTable
	ArrayList<WebTableData> DataDisplayedAfterSort=getWebTableRowData(driver,objectName,objectData);
	System.out.println("DataDisplayedAfterSort:");

	// Store values in Expected Result Array List
	ArrayList<WebTableData> ExpectedResult=new ArrayList<WebTableData>();
	try {
	ExpectedResult = getDataFromAPI(objectData);
	} catch (ClientProtocolException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return getErrorMessage(e);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return getErrorMessage(e);
	} catch (JSONException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return getErrorMessage(e);
	} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return getErrorMessage(e);
	}


	//Return the Result
	if(DataDisplayedAfterSort.equals(ExpectedResult))
	{
		return "::Passed - Sorting has been successfully verified for "+param[1]+" screen, sorted by "+param[2]+" column"+" in "+param[3]+" order. \r\n";
	}else
	{
		return "::Failed - Sorting is not matching with UI (Actual Vs Expected),  for "+param[1]+" screen, sorted by "+param[2]+" column"+" in "+param[3]+" order. \r\n";
	}
}*/

/***
 * Author	: Mohamed Abdulkadar
 * Purpose	: Get all the column values from WebTable
 * @param driver
 * @param objectName
 * @param objectData
 * @throws STEPFrameworkInvalidIdentifierException
 * @throws STEPFrameworkMissingORObjectException
 * @throws ParseException
 *
 */
public static ArrayList<WebTableData> getWebTableRowData(WebDriver driver,String objectName,String objectData) throws STEPFrameworkMissingORObjectException, STEPFrameworkInvalidIdentifierException {

   //Declare ArrayList to Capture Current Data Displayed in UI
	ArrayList<WebTableData> CurrentDisplayedUIData=new ArrayList<WebTableData>();

	String[] param=objectData.split("\\|");

	//Declare all possible columns of WebTable
	String Column1; 	// Status column from all tables
	String Column2; 	// Advertiser/CampaignName/Activity column from respective table
	int Column3; 		// Advertiser/CampaignName/Activity ID column from respective table
	String Column4;		// Agency/Advertiser/Owner column from respective table
	String start_date;   // Start_Date column from Campaign Webtable
	String end_date;   // End_Date column from Campaign Webtable

	//Get No of Rows Displayed in UI
	driver.manage().timeouts().implicitlyWait(120,TimeUnit.SECONDS);

	int tableRowCount = driver.findElements(getIdentifier(objectName)).size();
	System.out.println("No of rows in the Webtable: "+tableRowCount);

	//Object String from Object Repository file

	String objstr=STEPFrameworkExecutor.getTestSuiteObjectRep().get(objectName)[0].toString();



	if(tableRowCount > 0)

		for(int i=1;i<=tableRowCount;i++)
		{

			Column1=driver.findElement(By.xpath(objstr+"["+i+"]/td[1]")).getText();
			Column2=driver.findElement(By.xpath(objstr+"["+i+"]/td[2]")).getText();
			Column3=Integer.parseInt(driver.findElement(By.xpath(objstr+"["+i+"]/td[3]")).getText());
			Column4=driver.findElement(By.xpath(objstr+"["+i+"]/td[4]")).getText();

			if (param[1].equalsIgnoreCase("Campaign")||param[1].equalsIgnoreCase("Campaigns"))
			{


				start_date=driver.findElement(By.xpath(objstr+"["+i+"]/td[5]")).getText();
				end_date=driver.findElement(By.xpath(objstr+"["+i+"]/td[6]")).getText();

				CurrentDisplayedUIData.add(new WebTableData(Column1,Column2,Column3,Column4,start_date,end_date));

			} else
			{


			CurrentDisplayedUIData.add(new WebTableData(Column1,Column2,Column3,Column4));
			}
		}


	return CurrentDisplayedUIData;

}

/***
 * Author	: Mohamed Abdulkadar
 * Purpose	: Get all the column values from WebTable
 * @param driver
 * @param objectName
 * @param objectData
 * @throws ParseException
 * @throws STEPFrameworkInvalidIdentifierException
 * @throws STEPFrameworkMissingORObjectException
 *
 */
public static ArrayList<WebTableData> getDataFromAPI(String objectData) throws ClientProtocolException, IOException,JSONException, ParseException {


	//HttpClient client = new DefaultHttpClient();
	HttpClient client = HttpClientBuilder.create().build();

	/*//Get APIURL/Username/Password from config xml file
	String strFrameworkLocation =System.getProperty("user.dir");
	STEPFrameworkReader fwReader = new STEPFrameworkReader();
	STEPFrameworkVO frameworkVO = new STEPFrameworkVO();
	try {
		frameworkVO = fwReader.readConfigData(strFrameworkLocation, frameworkVO);
	} catch (STEPFrameworkLocationInvalidException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (STEPFrameworkConfigException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	String apiURL = frameworkVO.getConfigData().get("Api_Url");
	String apiUsername = frameworkVO.getConfigData().get("Api_username");
	String apiPassword = frameworkVO.getConfigData().get("Api_password");*/



	String apiURL=STEPKeywordLibrary.apiURL;
	String apiUsername=STEPKeywordLibrary.apiUsername;
	String apiPassword=STEPKeywordLibrary.apiPassword;

	String[] userInput=objectData.split("\\|");  // 1|advertiser|name|DESC|A|10|1
	String url="";   									//To build url
	String accountkey=userInput[0];   					//From User from which Account to retrieve data
	String entity=userInput[1];  						//From User whether Campaign/Advertiser/Activity
	String OrderByColumn=userInput[2];  				//From User which column to be sorted (Advertiser,ID,agency)
	String orderBy=userInput[3];    					//From User orderBy ASC or DESC
	String filter_status=userInput[4];					//From User Status of Entity "Active/Inactive"
	String row=userInput[5];							// From User how many rows in a page.
	String page=userInput[6]; 							// From User which page
	String col1=null;
	String col2=null;
	String col3=null;
	String col4=null;
	String col5=null;
	String col6=null;

	if(entity.equalsIgnoreCase("Advertiser")||entity.equalsIgnoreCase("Advertisers"))
	{
		entity="";
		entity="advertisers";


		String a_temp=filter_status;
		filter_status="";
		filter_status="advertiser_status:"+a_temp;

		//Possible column values for Advertiser List API call
		col1="status";
		col2="name";
		col3="id";
		col4="agencyName";

		if (OrderByColumn.equalsIgnoreCase("Advertiser")||OrderByColumn.equalsIgnoreCase("name")||OrderByColumn.equalsIgnoreCase("advertisername"))
		{
			OrderByColumn=null;
			OrderByColumn="name";
		}else if (OrderByColumn.equalsIgnoreCase("ID")||OrderByColumn.equalsIgnoreCase("Advertiserkey"))
		{
			OrderByColumn=null;
			OrderByColumn="id";
		}
		else if (OrderByColumn.equalsIgnoreCase("agencyName")||OrderByColumn.equalsIgnoreCase("agency"))
		{
			OrderByColumn=null;
			OrderByColumn="agencyKey";
		}else
		{
			System.out.println("OrderByColumn passed by User is not supported"+OrderByColumn);
		}

		if (orderBy.equalsIgnoreCase("DESC")||orderBy.equalsIgnoreCase("descendingorder")||orderBy.equalsIgnoreCase("descending"))
		{
			orderBy="";
			orderBy="desc";
		}else if (orderBy.equalsIgnoreCase("ASC")||orderBy.equalsIgnoreCase("ascendingorder")||orderBy.equalsIgnoreCase("ascending"))
		{
			orderBy="";
			orderBy="asc";
		}else
		{
			System.out.println("OrderBy passed by User is not supported"+orderBy);
		}

	}else if (entity.equalsIgnoreCase("Campaign")||entity.equalsIgnoreCase("Campaigns"))
	{
		entity="";
		entity="campaigns";

		String a_temp=filter_status;
		filter_status="";
		filter_status="campaign_status:"+a_temp;

		//Possible column values for Advertiser List API call
		col1="status";
		col2="name";
		col3="id";
		col4="advertiserName";
		col5="startDate";
		col6="endDate";

		if (OrderByColumn.equalsIgnoreCase("Campaign")||OrderByColumn.equalsIgnoreCase("name")||OrderByColumn.equalsIgnoreCase("advertisername"))
		{
			OrderByColumn=null;
			OrderByColumn="name";
		}else if (OrderByColumn.equalsIgnoreCase("ID")||OrderByColumn.equalsIgnoreCase("Advertiserkey"))
		{
			OrderByColumn=null;
			OrderByColumn="id";
		}
		else if (OrderByColumn.equalsIgnoreCase("Advertiser")||OrderByColumn.equalsIgnoreCase("advertiserName"))
		{
			OrderByColumn=null;
			OrderByColumn="advertiser_name"; // Column# 7 for AdvertiserName
		}
		else if (OrderByColumn.equalsIgnoreCase("StartDate"))
		{
			OrderByColumn=null;
			OrderByColumn="start_date"; // Column# 4 for Start Date
		}
		else if (OrderByColumn.equalsIgnoreCase("EndDate"))
		{
			OrderByColumn=null;
			OrderByColumn="end_date";
		}
		else
		{
			System.out.println("OrderByColumn passed by User is not supported"+OrderByColumn);
		}

		if (orderBy.equalsIgnoreCase("DESC")||orderBy.equalsIgnoreCase("descendingorder")||orderBy.equalsIgnoreCase("descending"))
		{
			orderBy="";
			orderBy="desc";
		}else if (orderBy.equalsIgnoreCase("ASC")||orderBy.equalsIgnoreCase("ascendingorder")||orderBy.equalsIgnoreCase("ascending"))
		{
			orderBy="";
			orderBy="asc";
		}else
		{
			System.out.println("OrderBy passed by User is not supported"+orderBy);
		}

	}else if (entity.equalsIgnoreCase("Activity")||entity.equalsIgnoreCase("Activitys")||entity.equalsIgnoreCase("Activities"))
	{
		entity="";
		entity="activity";

		String a_temp=filter_status;
		filter_status="";
		filter_status="activity_status"+a_temp;

	}else
	{
		System.out.println("Entity passed by User is not supported"+entity);
	}

	//Build Access Manager API to generate Token
	url=apiURL+"?user="+apiUsername+"&password="+apiPassword;

	HttpGet request = new HttpGet(url);
	JSONObject json=new JSONObject();
	StringEntity se=new StringEntity(json.toString());
	se.setContentType("application/json");
	request.setHeader("Accept", "application/json");
	HttpResponse response = client.execute(request);

	BufferedReader rd = new BufferedReader (new InputStreamReader(response.getEntity().getContent()));
	String line = "";
	String temp= null;
	while ((line = rd.readLine()) != null) {
		if(temp != null){
			temp+=line;
		}else{
			temp=line;
		}
	}

	JSONObject jsonObjLoc = new JSONObject(temp);
	System.out.println("url ="+jsonObjLoc.get("url"));
	System.out.println("accessToken ="+jsonObjLoc.get("accessToken"));

	String serviceURL=(String) jsonObjLoc.get("url");
	String tokenKey=(String) jsonObjLoc.get("accessToken");
	jsonObjLoc.remove("url");
	jsonObjLoc.remove("accessToken");


	//Build Rest API service
	String restApiService=serviceURL+"/accounts/"+accountkey+"/"+entity+"?token="+tokenKey+"&orderByColumn="+OrderByColumn+"&orderBy="+orderBy+"&filterBy="+filter_status+"&rowsPerPage="+row+"&pageNumber="+page;



	request = new HttpGet(restApiService);
	request.setHeader("Accept", "application/json");
	response = client.execute(request);


	rd = new BufferedReader (new InputStreamReader(response.getEntity().getContent()));
	line = "";
	temp= null;
	while ((line = rd.readLine()) != null) {
		if(temp != null){
			temp+=line;
		}else{
			temp=line;
		}
	}

	jsonObjLoc = new JSONObject(temp);
	JSONArray rowArray=(JSONArray) jsonObjLoc.get(entity);

	//Declare ArrayList to Capture Data from API
	ArrayList<WebTableData> DataFromAPI=new ArrayList<WebTableData>();



	for(int i=0;i<rowArray.length();i++)
	{

		String column1=rowArray.getJSONObject(i).get(col1).toString();
		String column2=rowArray.getJSONObject(i).get(col2).toString();
		int column3=Integer.parseInt(rowArray.getJSONObject(i).get(col3).toString());
		String column4=rowArray.getJSONObject(i).get(col4).toString();

		if (entity.equalsIgnoreCase("Campaign")||entity.equalsIgnoreCase("Campaigns"))
		{
			//Date column5=STEPDataUtility.getFormattedDate(rowArray.getJSONObject(i).get(col4).toString(), "MM/DD/YYYY");
			String column5=rowArray.getJSONObject(i).get(col5).toString();

			//Date column6=STEPDataUtility.getFormattedDate(rowArray.getJSONObject(i).get(col4).toString(),"MM/DD/YYYY");
			String column6=rowArray.getJSONObject(i).get(col6).toString();

			DataFromAPI.add(new WebTableData(column1,column2,column3,column4,column5,column6));

		}else
		{

		DataFromAPI.add(new WebTableData(column1,column2,column3,column4));

		 //System.out.println("Status : "+status+" Advertiser name: "+name+" id: "+id+" agencyName: "+agencyname);
		}

		}

	//System.out.println("Count of rows "+rowArray.length());


	return DataFromAPI;




}

/***
 * Purpose: Print Comments in the Test Result
 * @param objectName
 * @return
 */
public static String runComment(String objectName)
{
	return "::COMMENT: - "+objectName+". \r\n";

}

/***
 * Author	: Mohamed Abdulkadar
 * Purpose	: Get all the column values from WebTable
 * @param driver
 * @param objectName
 * @param objectData
 * @throws STEPFrameworkInvalidIdentifierException
 * @throws STEPFrameworkMissingORObjectException
 * @throws ParseException
 *
 */
public static String verifySetupCustomVar(WebDriver driver,String objectName,String objectData) throws STEPFrameworkMissingORObjectException, STEPFrameworkInvalidIdentifierException {


	String[] param=objectData.split("\\|");



	//Get No of Rows Displayed in UI
	driver.manage().timeouts().implicitlyWait(120,TimeUnit.SECONDS);

	int tableRowCount = driver.findElements(getIdentifier(objectName)).size();
	System.out.println("No of rows in the Webtable: "+tableRowCount);

	//Object String from Object Repository file

	String objstr=STEPFrameworkExecutor.getTestSuiteObjectRep().get(objectName)[0].toString();




	int x=0,i=0;
	if(tableRowCount > 0)

		for(;i<=tableRowCount;i++)
		{

			//String Column1=driver.findElement(By.xpath(objstr+"/div[@data-id="+i+"]/div/input[@value='QAINCOME-Desc'])).getText();
			String objectname=objstr+"/div[@data-id='"+i+"']/div/input[@value='XXX']";
			String objectname1=objectname.toString().replace("XXX", strRandomValues.get(param[x]));
			try{
			//WebElement element=driver.findElement(By.xpath(objectname1));


			if(((driver.findElement(By.xpath(objectname1))).isDisplayed())==true)
			{
				//int y=1;
				/*element=driver.findElement(By.xpath((objstr+"/div[@data-id='"+i+"']/div/input[@value='XXX']").toString().replace("XXX", strRandomValues.get(param[y]))));
				if(element.isDisplayed())
				{
					y++;
					element=driver.findElement(By.xpath((objstr+"/div[@data-id='"+i+"']/div/input[@value='XXX']").toString().replace("XXX", strRandomValues.get(param[y]))));
					if(element.isDisplayed())
					{
						//y++;
						//element=driver.findElement(By.xpath((objstr+"/div[@data-id='"+i+"']/div/div/button[@title='XXX']").toString().replace("XXX", param[y])));
						//if (element.isDisplayed())
						//{
							return "::Pass : - Custom variable displayed successfully";
						//}
					}
				}*/
			}
			}catch(Exception e)
			{
				e.printStackTrace();
			}

			x++;
		}


	return "::Fail : - Custom variable is not displayed";

}

private static String runAddHash(String objectData) {

     try{
       String [] param=objectData.split("\\|");

    System.out.println(param[1]);

    strRandomValues.put(param[0], param[1]);

    return "::Passed - Successfully Generated Advertiser Value ";
    }
       catch(ArrayIndexOutOfBoundsException e)
    {
    return "::Failed - InputData should contain delimeter (|). \r\n";
    }
       }

/***
 * Author	: Mohamed Abdulkadar
 * Purpose	: Parser of jsonobject from AccountNames.<Accountkey> Document
 * Expected ObjectData : String
 * @return Map
 * @param jsonString
 * @throws STEPFrameworkInvalidIdentifierException
 * @throws STEPFrameworkMissingORObjectException
 * @throws ParseException
 *
 */

public static Map<String, Object> jsonPraser(String jsonString) throws JSONException
{
	JSONObject object = new JSONObject(jsonString);
	Map<String, Object> map = new HashMap<String, Object>();

	@SuppressWarnings("unchecked")
	Iterator<String> keysItr=object.keys();

	while(keysItr.hasNext())
	{
		String key = keysItr.next();
		if(!(key.equals("owner")))
		{
		Object value = object.get(key);

		if(value instanceof JSONArray)
		{
			//value= jsonArrayList((JSONArray) value);
		}else if(value instanceof JSONObject)
		{
			if (object.has("model_names"))
			{
				object=(JSONObject) object.get("model_names");

			}else if (object.has("owner"))
			{
				object=(JSONObject) object.get("owner");

			}
			@SuppressWarnings("unchecked")
			Iterator<String> modelKeys=object.keys();
			while(modelKeys.hasNext())
			{
				String modelId=modelKeys.next();
				String modelname=object.getString(modelId);

				map.put(modelId, modelname);
			}


		}
		}
	}

	return map;
}

/***
 * Author	: Mohamed Abdulkadar
 * Purpose	: Parser of jsonobject from Model.<ID> Document
 * Expected ObjectData : String
 * @return Map
 * @param jsonString
 * @throws STEPFrameworkInvalidIdentifierException
 * @throws STEPFrameworkMissingORObjectException
 * @throws ParseException
 *
 */

public static void jsonModelDocumentPraser(String jsonString) throws JSONException
{
	JSONObject object = new JSONObject(jsonString);

	@SuppressWarnings("unchecked")
	Iterator<String> keysItr=object.keys();

	while(keysItr.hasNext())
	{

		String key = keysItr.next();
		Object value = object.get(key);

		if(key.equalsIgnoreCase("pvt-var-clk"))
		{
			System.out.println("Stop");
		}

		String tkey=null;
		Object tvalue = null;



		if(key.equals("thresholds") && !object.get(key).toString().equals(null)||(key.equals("registered_custom_variables") && !object.get(key).toString().equals(null)))
		{
			if(value instanceof JSONObject)
			{
				@SuppressWarnings("unchecked")
				Iterator<String> thresholdKeys=((JSONObject) value).keys();

				while(thresholdKeys.hasNext())
				{
					tkey=thresholdKeys.next();
					tvalue=((JSONObject) value).get(tkey);
					addMapList(tkey, tvalue);
				}

			}

		}
		else
		{
		if(key.equals("name")|| key.equals("is_active")||key.equals("instances")||key.equals("data")||key.equals("model_description")||key.equals("model_type"))
		{

		if(value instanceof JSONArray  )
		{
			if (!((JSONArray) value).isNull(1))
			{
			value=jsonArrayList((JSONArray) value);
			jsonModelDocumentPraser((String) value);
			}else
			{
				addMapList(key, value);
			}

		}else if(value instanceof JSONObject)
		{
			if(((JSONObject) value).isNull(key))
			{
			value=jsonString((JSONObject) value);
			jsonModelDocumentPraser((String) value);
			}else
			{
				addMapList(key, value);
			}
		}else
		{
			addMapList(key, value);
		}
		}
		}
	}
}

public static void jsonModelStatusCollector(String jsonString) throws JSONException
{

	try
	{
	JSONObject object = new JSONObject(jsonString);
	boolean status = true;
	String modelStatus;

	String modelName = object.getString("name");
	setModelName(modelName);

	if(modelName.equalsIgnoreCase("Model0730Adv")||modelName.equalsIgnoreCase("Test_For_Operator"))
	{
		System.out.println("stopped");
	}


	status = object.getBoolean("is_active");
	SetModelStatus(status);

	String modelType = object.getString("model_type");
	setModelType(modelType);

	if (!modelType.equalsIgnoreCase("INTEREST"))
	{
		JSONObject value2 = object.getJSONObject("current_model_generation_job");
		String curModelGenJobStatus = value2.getString("job_state");

		if (curModelGenJobStatus.equalsIgnoreCase("IN_PROGRESS"))
		{
			modelStatus = "IN_PROGRESS";
			SetModelGenerationStatus(modelStatus);

		}else
		{

			JSONArray value3 = object.getJSONArray("model_generation_job_history");
			int i = value3.length();
			JSONObject object2 = new JSONObject();
			System.out.println("Model Name : "+modelName);
			if(i <= 0)
			{
				modelStatus = "NOT_STARTED";
				SetModelGenerationStatus(modelStatus);

			}else
			{
				object2 = value3.getJSONObject(value3.length() -1);
				modelStatus = object2.getString("job_state");
				SetModelGenerationStatus(modelStatus);
	}
		}

	}else if (modelType.equalsIgnoreCase("INTEREST"))
		{
			String query = "select count(*) as 'varIABCheck' from tiabmap where segment_name='"+modelName+"' and parent_iabmapkey is null";
			String inputdata = "VERTICA|varIABCheck|"+query;
			retriveDB(inputdata);

			if(!strRandomValues.get("varIABCheck").equals("0"))
				{
					modelType = "PARENT_INTEREST_SEGMENT";

				}else
				{
					modelType = "CHILD_INTEREST_SEGMENT";
				}


			JSONArray value3 = object.getJSONArray("model_generation_job_history");
			int i = value3.length();
			JSONObject object2 = new JSONObject();
			System.out.println("Model Name : "+modelName);
			if(i <= 0)
			{
				modelStatus = "NOT_STARTED";


			}else
			{
				object2 = value3.getJSONObject(value3.length() -1);
				modelStatus = object2.getString("job_state");

			}

			SetModelGenerationStatus(modelStatus);
			setModelType(modelType);
		}
	}catch(Exception e)
	{
		e.printStackTrace();
	}


}

public static void setModelName(String name)
{
	couchbaseModelName = name;
}

public static String  getModelName()
{
	return couchbaseModelName;
}

public static void setModelType(String modeltype)
{
	couchbaseModelType = modeltype;
}

public static String  getModelType()
{
	return couchbaseModelType;
}

public static void SetModelStatus(boolean status)
{
	couchbaseModelStatus = status;
}

public static boolean  getModelStatus()
{
	return couchbaseModelStatus;
}

public static void SetModelGenerationStatus(String status)
{
	CouchbasemodelGenerationStatus = status;

}

public static String getModelGenerationStatus()
{
	return CouchbasemodelGenerationStatus;

}



/***
 * Author	: Mohamed Abdulkadar
 * Purpose	:
 * Expected ObjectData :
 * @param objectData
 * @throws STEPFrameworkInvalidIdentifierException
 * @throws STEPFrameworkMissingORObjectException
 * @throws ParseException
 *
 */

/*public static String runCouchbaseModelData(String objectData) throws JSONException
{
	HashMap<String, String> missingModels = new HashMap<String,String>();
	ArrayList<ModelTable> AllModels = new ArrayList<ModelTable>();
	ArrayList<ModelTable> AccountLevel = new ArrayList<ModelTable>();
	ArrayList<ModelTable> InteractionLookaLike = new ArrayList<ModelTable>();
	ArrayList<ModelTable> Feedback = new ArrayList<ModelTable>();
	ArrayList<ModelTable> ActiveModels = new ArrayList<ModelTable>();
	ArrayList<ModelTable> ActiveFailedModels = new ArrayList<ModelTable>();
	ArrayList<ModelTable> ProcessingModels = new ArrayList<ModelTable>();
	ArrayList<ModelTable> FailedModels = new ArrayList<ModelTable>();
	ArrayList<ModelTable> interestModels = new ArrayList<ModelTable>();
	ArrayList<ModelTable> demoModels = new ArrayList<ModelTable>();
	ArrayList<ModelTable> RetargetModels  = new ArrayList<ModelTable>();
	ArrayList<ModelTable> AllCouchbaseModels = new ArrayList<ModelTable>();



	try
	{
	String bucketName = "Configurations";


	Read AccountModelNames Couchbase document
	String jsonString = STEPKeywordLibrary.readCouchbase(bucketName,objectData.split("\\|")[0]);

	JSONObject jsonObjLoc = new JSONObject(jsonString);

	jsonObjLoc=jsonObjLoc.getJSONObject("model_names");

	// Run SQL statement to retrieve data from tmodel table
	retriveDB(objectData.split("\\|")[1],objectData.split("\\|")[2]);

	//Read the All Model's couchbase document
	STEPKeywordLibrary.readCouchbase(bucketName,jsonObjLoc);

	@SuppressWarnings("unchecked")
	Iterator<String> myIter = jsonObjLoc.keys();

	while (myIter.hasNext())
	{
		String modelID = myIter.next();
		String modelname = jsonObjLoc.get(modelID).toString();
		boolean varInterestFlag=false;


		jsonString = modelDocString.get(modelID);

		String advertiserkey = null;
		String campaignkey = null;
		String status=null;
		String modeltype = null;
		boolean isActive;

		//Parse the Model's json String to find the status
		STEPKeywordLibrary.jsonModelStatusCollector(jsonString);

		status = getModelGenerationStatus();
		isActive = getModelStatus();
		modeltype = getModelType();

		ModelTable tb13=new ModelTable(modelID,modelname,advertiserkey,campaignkey,status);
		CouchbaseModels.add(tb13);

		for (ModelTable modelTable : retreivedFromDB)
		{
			advertiserkey = modelTable.getAdvertiserkey();
			campaignkey = modelTable.getCampaignkey();

			if(modelTable.getModelID().equals(modelID))
			{

				advertiserkey = modelTable.getAdvertiserkey();
				campaignkey = modelTable.getCampaignkey();

				if (modeltype.equalsIgnoreCase("FEEDBACK"))
				{

				// All Status Interaction Lookalike/Feedback Model Collections
				ModelTable tb4=new ModelTable(modelID,modelname,advertiserkey,campaignkey,status);
				AllModels.add(tb4);
				}

				if (isActive && modeltype.equalsIgnoreCase("FEEDBACK"))
				{

					ModelTable tb15=new ModelTable(modelID,modelname,advertiserkey,campaignkey,status);
					ActiveAdvAcctModels.add(tb15);

					if((advertiserkey == null) && (campaignkey == null))
					{

						ModelTable tb1=new ModelTable(modelID,modelname,advertiserkey,campaignkey,status);
						AccountLevel.add(tb1);

					} else if((advertiserkey != null) && (campaignkey == null))
					{

						ModelTable tb2=new ModelTable(modelID,modelname,advertiserkey,campaignkey,status);
						InteractionLookaLike.add(tb2);

					} else if((advertiserkey != null) && (campaignkey != null))
					{

						ModelTable tb3=new ModelTable(modelID,modelname,advertiserkey,campaignkey,status);
						Feedback.add(tb3);
					}

						//No of Built Status of Models
						if(status.equalsIgnoreCase("DONE"))
						{
							ModelTable tb5=new ModelTable(modelID,modelname,advertiserkey,campaignkey,status);
							ActiveModels.add(tb5);
							System.out.println(modelname+"is Active Status");
						}

						// Sum of (Built+in-progress+failed) for Interaction Lookalike Section in Mission Control
						if(status.equalsIgnoreCase("IN_PROGRESS") || status.equalsIgnoreCase("DONE")||status.equalsIgnoreCase("FAILED"))
						{
							ModelTable tb6=new ModelTable(modelID,modelname,advertiserkey,campaignkey,status);
							ActiveFailedModels.add(tb6);

							System.out.println(modelname+"is Built+in-progress+failed Status");
						}
						// Processing Status Models
						if(status.equalsIgnoreCase("IN_PROGRESS"))
						{
							ModelTable tb7=new ModelTable(modelID,modelname,advertiserkey,campaignkey,status);
							ProcessingModels.add(tb7);
							System.out.println(modelname+"is IN_PROGRESS Status");
						}
						// Processing Failed Models
						if(status.equalsIgnoreCase("FAILED"))
						{
							ModelTable tb8=new ModelTable(modelID,modelname,advertiserkey,campaignkey,status);
							FailedModels.add(tb8);
							System.out.println(modelname+"is FAILED Status");
						}

						//No of RetargetModels
						if(status.equalsIgnoreCase("IN_PROGRESS") || status.equalsIgnoreCase("DONE"))
						{
							ModelTable tb9=new ModelTable(modelID,modelname,advertiserkey,campaignkey,status);
							RetargetModels.add(tb9);
							System.out.println(modelname+"is RetargetModels Status");
						}

				}

			}else if (!isActive && modeltype.equalsIgnoreCase("FEEDBACK"))
			{

				ModelTable tb12=new ModelTable(modelID,modelname,advertiserkey,campaignkey,status);
				InactiveModels.add(tb12);
				System.out.println(modelname+" is InactiveModels Model. Status : "+isActive);
				varInterestFlag=true;

			}else if (!varInterestFlag)
				{

					advertiserkey = modelTable.getAdvertiserkey();
					campaignkey = modelTable.getCampaignkey();


					if(isActive && modeltype.equalsIgnoreCase("PARENT_INTEREST_SEGMENT"))
					{
						 if (status.equalsIgnoreCase("DONE"))
						 {
						ModelTable tb10=new ModelTable(modelID,modelname,advertiserkey,campaignkey,status);
						interestModels.add(tb10);
						System.out.println(modelname+" is Interest Model. Status : "+isActive);
						varInterestFlag=true;
						 }
					}


					if(isActive && modeltype.equalsIgnoreCase("CHILD_INTEREST_SEGMENT"))
					{

						if (status.equalsIgnoreCase("DONE"))
						 {
						ModelTable tb10=new ModelTable(modelID,modelname,advertiserkey,campaignkey,status);
						interestModels.add(tb10);
						System.out.println(modelname+" is Interest Model. Status : "+isActive);
						varInterestFlag=true;
						 }
					}

					if(isActive && modeltype.equalsIgnoreCase("DEMOGRAPHIC"))
					{

						ModelTable tb11=new ModelTable(modelID,modelname,advertiserkey,campaignkey,status);
						demoModels.add(tb11);
						System.out.println(modelname+" is DemoGrpahic Model. Status : "+isActive);
						varInterestFlag=true;
					}

				}
		}



	}

		int missingModelsCount = missingModels.size();
		int totalModelCounts = AllModels.size();
		int totalAcctModelCounts = AccountLevel.size();
		int totalILALModelCounts = InteractionLookaLike.size();
		int totalFBModelCounts = Feedback.size();
		int totalactiveModels = ActiveModels.size();
		int totalactiveFailModels =ActiveFailedModels.size();
		int totalProcessModels = ProcessingModels.size();
		int totalFailedModels = FailedModels.size();
		int totalinterestModels = interestModels.size();
		int totaldemoModels = demoModels.size();
		int totalRetargetModels = RetargetModels.size();

		strRandomValues.put("builtStatusModelCount",Integer.toString(ActiveModels.size()));
		strRandomValues.put("processingStatusModelCount",Integer.toString(ProcessingModels.size()));
		strRandomValues.put("failedStatusModelCount",Integer.toString(FailedModels.size()));
		strRandomValues.put("activeDemographicCount",Integer.toString(demoModels.size()));
		strRandomValues.put("activeInterestCount",Integer.toString(interestModels.size()));
		strRandomValues.put("activeRetargetCount",Integer.toString(RetargetModels.size()));


         * Start here-- Added New Variable into Hash Table vaibhav

        //To Store Total(Account+Advertiser) Model Count
        strRandomValues.put("totalAccAdvModelCount",Integer.toString(ActiveAdvAcctModels.size()));

        //To Store Account Level Model Count
        strRandomValues.put("totalAccLevelModelCount",Integer.toString(AccountLevel.size()));

        //To Store Advertiser Level Model Count (ILAL+Feedback)
        strRandomValues.put("totalAdvLevelModelCount",Integer.toString(InteractionLookaLike.size()+Feedback.size()));

         * End here-- Added New Variable into Hash Table



		System.out.println("Missing Models: "+missingModelsCount+" . \r\n");
		System.out.println("Total Active Models including Feedback: "+totalModelCounts+" . \r\n");
		System.out.println("Total Active Feedback Models in Account: "+totalAcctModelCounts+" . \r\n");
		System.out.println("Total Active ILAL Models: "+totalILALModelCounts+" . \r\n");
		System.out.println("Total Active Feedback Models: "+totalFBModelCounts+" . \r\n");
		System.out.println("Total Models of ILAL in Mission Control page: "+totalactiveFailModels+" . \r\n");
		System.out.println("Total Built Models: "+totalactiveModels+" . \r\n");
		System.out.println("Total Processing Models: "+totalProcessModels+" . \r\n");
		System.out.println("Total Failed Models: "+totalFailedModels+" . \r\n");
		System.out.println("Total Active Interest Models: "+totalinterestModels+" . \r\n");
		System.out.println("Total Active Demo Models: "+totaldemoModels+" . \r\n");
		System.out.println("Total RetargetModels: "+totalRetargetModels+" . \r\n");
		System.out.println("No of row from DB: "+retreivedFromDB.size()+" . \r\n");
		System.out.println("value from hastable: "+strRandomValues.get("activeRetargetCount"));


	return "::Pass - Success";
	}
	catch(ArrayIndexOutOfBoundsException e)
	{
		return "::Failed  - InputData is not in proper format. \r\n";
	}
	catch(Exception e)
	{
		return getErrorMessage(e);
	}


}*/

/*public static void runExcelReporGenerator(String objectData)
{

FileOutputStream fileOut;

HSSFWorkbook     workbook   = new HSSFWorkbook();
HSSFCellStyle headerStyle = workbook.createCellStyle();
ArrayList<ModelTable> modelData = new ArrayList<ModelTable>();

HSSFFont headerFont = workbook.createFont();
headerFont.setBoldweight(Font.BOLDWEIGHT_BOLD);

headerStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
headerStyle.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
headerStyle.setFillBackgroundColor(HSSFColor.WHITE.index);
headerStyle.setFont(headerFont);

String filename= "ModelData-"+System.currentTimeMillis() + ".xls";
String filenameLocationFile = screenshotLocation+"\\"+STEPFrameworkDriver.strResultFileName+filename;

File reportDestination = new File(filenameLocationFile);

HSSFSheet sheet3 = workbook.createSheet("ALL-MODELS");
HSSFSheet sheet4 = workbook.createSheet("AccountLevel-MODELS");
HSSFSheet sheet5 = workbook.createSheet("ILAL-MODELS");
HSSFSheet sheet6 = workbook.createSheet("Feedback-MODELS");
HSSFSheet sheet7 = workbook.createSheet("Active-MODELS");
HSSFSheet sheet8 = workbook.createSheet("ActiveFailed-MODELS");
HSSFSheet sheet9 = workbook.createSheet("Processing-MODELS");
HSSFSheet sheet10 = workbook.createSheet("Failed-MODELS");
HSSFSheet sheet11 = workbook.createSheet("Interest-MODELS");
HSSFSheet sheet12 = workbook.createSheet("Demo-MODELS");
HSSFSheet sheet13 = workbook.createSheet("Retarget-MODELS");

HSSFRow sessionname = sheet3.createRow(0);
HSSFCell title = sessionname.createCell(0);
title.setCellStyle(headerStyle);
title.setCellValue("ALL-MODELS");

HSSFRow row = sheet3.createRow(5);

HSSFCell cell0 = row.createCell(0);
cell0.setCellStyle(headerStyle);
cell0.setCellValue("Model ID");

HSSFCell cell1 = row.createCell(1);
cell1.setCellStyle(headerStyle);
cell1.setCellValue("Model Name");

HSSFCell cell2 = row.createCell(2);
cell2.setCellStyle(headerStyle);
cell2.setCellValue("AdvertiserKey");

HSSFCell cell3 = row.createCell(3);
cell3.setCellStyle(headerStyle);
cell3.setCellValue("CampaignKey");

HSSFCell cell4 = row.createCell(4);
cell4.setCellStyle(headerStyle);
cell4.setCellValue("Status");
}*/

/***
 * Author	: Mohamed Abdulkadar
 * Purpose	: Add key, value from json object
 * @return void
 * @param key, object
 * @throws STEPFrameworkInvalidIdentifierException
 * @throws STEPFrameworkMissingORObjectException
 * @throws ParseException
 *
 */

public static void addMapList(String key, Object object) throws JSONException
{

	couchbaseDataMap.put(key, object);



}

/***
 * Author	: Mohamed Abdulkadar
 * Purpose	: Handle the JsonArray
 * @return String
 * @param JSONArray
 * @throws STEPFrameworkInvalidIdentifierException
 * @throws STEPFrameworkMissingORObjectException
 * @throws ParseException
 *
 */
public static String jsonArrayList(JSONArray array) throws JSONException
{

    String json=null;
        for(int i = 0; i < array.length(); i++)
    {
    	json=array.getString(i);
    }

   return json;
}

/***
 * Author	: Mohamed Abdulkadar
 * Purpose	: Handle the JsonObject for jsonParsers
 * @return String
 * @param JSONO
 * @throws STEPFrameworkInvalidIdentifierException
 * @throws STEPFrameworkMissingORObjectException
 * @throws ParseException
 *
 */
@SuppressWarnings("static-access")
public static String jsonString(JSONObject object) throws JSONException
{
    String jsonString=null;
    if((object.NULL.equals(null)))
    {
    	jsonString=object.toString();
    }
   return jsonString;
}




/***
 * Author	: Mohamed Abdulkadar
 * Purpose	: Retrive Model ID from "AccountModelNames.<Accountkey>" Document
 * Expected ObjectData : <"AccountModelNames.1" Couchbase DocumentName>|<"UIModelName" Model created via UI, stored in hashtable>|<"LALModelID" hashtable variable to store the Modle ID form Couchbase"
 * @param objectData
 * @throws STEPFrameworkInvalidIdentifierException
 * @throws STEPFrameworkMissingORObjectException
 * @throws ParseException
 *
 */

/*public static String getCouchbaseModelID(String ObjectData) throws JSONException
{

	Map<String, Object> models = new HashMap<String, Object>();	 Map to collect all the Model ID with Name from Couchbase
	String bucketName = "Configurations"; 				Couchbase Buckname
	String[] param = ObjectData.split("\\|");			Split the Object Data
	String search=strRandomValues.get(param[1]);		Retrieve the Model Name from Hashtable to search the document
	String modelID =null;
	String modelname=null;

	if((strRandomValues.get(param[1]).toString()).equals("null"))
	{
		return "::Fail - Model ID is null, looks like it has not been retrieved from Couchbase document. \r\n";
	}


	String jsonString = STEPKeywordLibrary.readCouchbase(bucketName,param[0]); Read the Couchbase document
    Praser the json string to get Model ID with Name
	models=STEPKeywordLibrary.jsonPraser(jsonString);

	for(String key:models.keySet())
	{
		modelID=key;
		modelname = models.get(key).toString();

		if(modelname.equalsIgnoreCase(search))
		{
			strRandomValues.put(param[2],key);
			return "::Passed - Successfully retrieve value from Coucbhase and stored in variable "+search+" with value "+modelID +" of model name"+modelname+"'. \r\n";

		}

	}

	return "::Fail - Not able to find Model in Couchbase Document "+param[0]+". \r\n";
}
*/
/***
 * Author	: Mohamed Abdulkadar
 * Purpose	: Verify Couchbase Document Model.<ID>
 * Expected ObjectData : Model|<LALModelID retrieved from Couchbase and stored in hashtable>|<"name" json obj name >:<"UIModelName" Model name created via UI, stored in hashtable>|<"is_active" model status json obj>:<"false" value to be verified>|<"intances" Jsonarray obj name>:<"thresholds" json obj of model threshold>:<"0.6" threshold value to be verified>
 * @param objectData
 * @throws STEPFrameworkInvalidIdentifierException
 * @throws STEPFrameworkMissingORObjectException
 * @throws ParseException
 *
 */

/*public static String verifyCouchbaseModel(String ObjectData) throws JSONException
{

	try
	{
	String bucketName = "Configurations";    							Couchbase Bucket name
	String[] param = ObjectData.split("\\|");  							Split object data
	String modelDocumentName = "Model."+strRandomValues.get(param[1]); 	Built Couchbase Document Name
	boolean modelnameVerification=false; 								Verification flag
	boolean modelstatusVerification=false;								Verification flag
	boolean modelthresholdVerification=false;							Verification flag
	boolean modeltypeVerification=false;
	String ActualModelName=null;
	String ExpModelName=null;
	String ActModelStatus=null;
	String ExpModelStatus=null;
	String ActModelThreshold=null;
	String ExpModelThreshold=null;
	String ExpModelType=null;
	String ActModelType=null;


	if((strRandomValues.get(param[1]).toString()).equals("null"))
	{
		return "::Fail - Model ID is null, looks like it has not been retrieved from Couchbase document. \r\n";
	}

	Read Couchbase document
	String jsonString = STEPKeywordLibrary.readCouchbase(bucketName,modelDocumentName);

	STEPKeywordLibrary.jsonModelDocumentPraser(jsonString);


	for (int i=2;i<=(param.length)-1;i++)
	{

	String[] search1=null;

	search1=param[i].split("\\:");

	Verification of Model Name
		if(search1[0].equalsIgnoreCase("name"))
		{
			ExpModelName=strRandomValues.get(search1[1]);
			if(!(couchbaseDataMap.get("name") == null))
			{
			ActualModelName=strRandomValues.get(search1[1]);
						if ((couchbaseDataMap.get("name").toString()).equalsIgnoreCase(strRandomValues.get(search1[1])))
			{
			modelnameVerification=true;
			System.out.println("Model name verification "+modelnameVerification);
			}
			}
		}

		Verification of Model Status
		if(search1[0].equalsIgnoreCase("is_active"))
		{
			ExpModelStatus=search1[1];
			if(!(couchbaseDataMap.get("is_active") == null))
			{
			ActModelStatus=couchbaseDataMap.get("is_active").toString();

			if ((couchbaseDataMap.get("is_active").toString().equals(search1[1])))
			{
			modelstatusVerification=true;
			System.out.println("Model status verification "+modelstatusVerification);
			}
			}
		}

		Verification of Model Thresholds
		if(search1[1].equalsIgnoreCase("thresholds"))
		{
			ExpModelThreshold=search1[2];
			if (!(couchbaseDataMap.get(ActualModelName) == null))
			{
			ActModelThreshold=couchbaseDataMap.get(ActualModelName).toString();

			if ((couchbaseDataMap.get(ActualModelName).toString().equals(search1[2])))
			{
				modelthresholdVerification=true;
				System.out.println("Model threshold verification "+modelthresholdVerification);
			}
			}
		}

		Verification of Model Type
		if(search1[0].equalsIgnoreCase("model_type"))
		{
			ExpModelType=search1[1];
			if(!(couchbaseDataMap.get("model_type") == null))
			{
			ActModelType=couchbaseDataMap.get("model_type").toString();

			if ((couchbaseDataMap.get("model_type").toString().equals(search1[1])))
			{
			modeltypeVerification=true;
			System.out.println("Model Type verification "+modeltypeVerification);
			}
			}
		}
	}

	Result
	if(modelnameVerification && modelstatusVerification && modelthresholdVerification )
	{
		return "::Passed - Successfully Verified the Model Details in the Couchbase. \r\n";
	}else
	{
		return "::Failed - Model details are not matchin in Couchbase.\r\n " +
				"Model Name in Couchbase: "+ActualModelName+", Expected ModelName : "+ExpModelName
				+"\r\n, Model Status in Couchbase: "+ActModelStatus+", Expected ModelStatus : "+ExpModelStatus
				+"\r\n, Model Type  in Couchbase: "+ActModelType+", Expected ModelType : "+ExpModelType
				+"\r\n, Model Threshold in Couchbase: "+ActModelThreshold+", Expected ModelThreshold : "+ExpModelThreshold+".\r\n";
	}

	}
	catch(ArrayIndexOutOfBoundsException e)
	{
		return "::Failed  - InputData is not in proper format. \r\n";
	}
	catch(Exception e)
	{
		return getErrorMessage(e);
	}


}*/

/***
 * Author	: Mohamed Abdulkadar
 * Purpose	:
 * Expected ObjectData :
 * @param objectData
 * @throws STEPFrameworkInvalidIdentifierException
 * @throws STEPFrameworkMissingORObjectException
 * @throws ParseException
 *
 */

/*public static String verifyCouchbaseCustomVariable(String ObjectData) throws JSONException
{

	try
	{
	String bucketName = "Configurations";    							Couchbase Bucket name
	String[] param = ObjectData.split("\\|");  							Split object data


	Read Couchbase document
	String jsonString = STEPKeywordLibrary.readCouchbase(bucketName,param[0]);

	STEPKeywordLibrary.jsonModelDocumentPraser(jsonString);

	String[] search1=null;

	search1=param[1].split("\\:");

		if ((couchbaseDataMap.get("src."+strRandomValues.get(search1[1])).toString()).equalsIgnoreCase(strRandomValues.get(search1[0])))
			{
			return "::Pass  - Cusotm Variable Name and Key are available in Couchbase Document: "+param[0]+". \r\n";
			}

		return "::Fail  - Cusotm Variable Name and Key not available in Couchbase Document: "+param[0]+". \r\n";

	}catch(ArrayIndexOutOfBoundsException e)
	{
		return "::Failed  - InputData is not in proper format. \r\n";
	}
	catch(Exception e)
	{
		return "::Fail  - Cusotm Variable Name and Key not available in Couchbase Document. \r\n";
	}


}
*/
/***
 * Purpose: Scroll the page.
 * @param driver
 * @param objectName
 * @param browser_type
 * @return
 */
private static String runScrollDown(WebDriver driver, String objectName)
{
	try
	{
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		int size=driver.findElements(getIdentifier(objectName)).size();
		if(size==0)
			return "::Failed  - '"+objectName + "' element is not found '"+takeScreenshot(driver)+"' \r\n";
		else
		{
			Actions dragger = new Actions(driver);
			WebElement draggableScrollbar = driver.findElement(getIdentifier(objectName));

			//drag downwards
			int NoOfPixelsToScrollDown = 1;

			for(int i=10;i<500;i=i+NoOfPixelsToScrollDown)
			{
				try
				{
					//drag scroll bar 10 times at a time
					dragger.moveToElement(draggableScrollbar).clickAndHold().moveByOffset(0, NoOfPixelsToScrollDown).release().perform();
					Thread.sleep(1000L);

				}catch(Exception e1)
				{

				}

			}

			//WebElement element=driver.findElement(getIdentifier(objectName));
			//element.click();
			return "::Passed - Successfully clicked on '"	+ objectName + "'. \r\n";

		}
	}
	catch(STEPFrameworkMissingORObjectException e)
	{
		return "::Failed  - '"+objectName+"' object is missing in the Object Repository. \r\n";
	}
	catch(STEPFrameworkInvalidIdentifierException e)
	{
		return "::Failed  - Invalid identifier for the object "+objectName+" in Object Repository. \r\n";
	}
	catch(NoSuchElementException e)
	{
		return "::Failed  - '"+objectName + "' element is not found '"+takeScreenshot(driver)+"' \r\n";
	}
	catch(Exception e)
	{
		return getErrorMessage(e,objectName);
	}
}

/*
 *!*!*!**!*!*!**!*!*!**!*!*!**!*!*!**!*!*!**!*!*!**!*!*!**!*!*!**!*!*!**!*!*!**!*!*!**!*!*!**!*!*!**!*!*!**!*!*!**!*!*!**!*!*!**!*!*!**!*!*!**!*!*!**!*!*!**!*!*!*
 *!*!*!* Added By Vaibhav - START Here
 *!*!*!**!*!*!**!*!*!**!*!*!**!*!*!**!*!*!**!*!*!**!*!*!**!*!*!**!*!*!**!*!*!**!*!*!**!*!*!**!*!*!**!*!*!**!*!*!**!*!*!**!*!*!**!*!*!**!*!*!**!*!*!**!*!*!**!*!*!*
 */

/***
 * Author: Vaibhav Shinde
 * Purpose: Scroll the mouse up to that particular element into DIV of the page.
 * @param driver
 * @param objectName
 * @return
 * Used this method for Reporting Page to scroll the Dimensions and Metrics
 */

private static String runScrollIngBarInDiv(WebDriver driver,String objectName)
{
	try
	{
		driver.manage().timeouts().implicitlyWait(15,TimeUnit.SECONDS);
		int size=driver.findElements(getIdentifier(objectName)).size();
		if(size==0)
			return "::Failed  - '"+objectName + "' element is not found '"+takeScreenshot(driver)+"' \r\n";
		else
		{

            Actions dragger = new Actions(driver);
            WebElement draggablePartOfScrollbar = driver.findElement(getIdentifier(objectName));
            int numberOfPixelsToDragTheScrollbarDown = 100;
            dragger.moveToElement(draggablePartOfScrollbar).clickAndHold().moveByOffset(0,numberOfPixelsToDragTheScrollbarDown).release().perform();


			/*	WebElement element = driver.findElement(getIdentifier(objectName));
			JavascriptExecutor jse = (JavascriptExecutor) driver;
            jse.executeScript("arguments[0].scrollIntoView(true);", element); */


            /* Testfor iother code
			 * String blockHeight = "return arguments[0].offsetHeight";
			   String myscript = "arguments[0].scrollTop"+jse.executeScript(blockHeight,element);
               element.click();
               pause(100);
               jse.executeScript(myscript, element);
			   */

			//int elementPosition = element.getLocation().getY();
			//String js = String.format("scroll(0, 250)", elementPosition-50);


			//int elementPosition = element.getLocation().getY();
			//String js = String.format("window.scroll(0, %s)", elementPosition-50);
			//((JavascriptExecutor)driver).executeScript(js);
			//element.click();
			return "::Passed - Successfully Scrolled on "+objectName+". \r\n";
		}
	}
	catch(STEPFrameworkMissingORObjectException e)
	{
		return "::Failed  - '"+objectName+"' object is missing in the Object Repository. \r\n";
	}
	catch(STEPFrameworkInvalidIdentifierException e)
	{
		return "::Failed  - Invalid identifier for the object "+objectName+" in Object Repository. \r\n";
	}
	catch(NoSuchElementException e)
	{
		return "::Failed  - '"+objectName + "' element is not found '"+takeScreenshot(driver)+"' \r\n";
	}
	catch(Exception e)
	{
		return getErrorMessage(e,objectName);
	}
}

/***
 * Author: Vaibhav Shinde
 * Purpose: To Double Click on the Selected value from Metrics or Dimensions List.
 * @param driver
 * @param objectName
 * @param objectData
 * @param browser_type
 * @return
 */
private static String runSelectDoubleClick(WebDriver driver, String objectName,String objectData,String browser_type)
{

	try
	{

		driver.manage().timeouts().implicitlyWait(15,TimeUnit.SECONDS);
		String input=getIdentifier(objectName).toString().replace("XXX", objectData);
		int size=driver.findElements(By.xpath(input.substring(input.indexOf(":")+1, input.length()))).size();
		if(size==0)
			return "::Failed  - '"+objectName + "' element is not found '"+takeScreenshot(driver)+"' \r\n";
		else
		{
			WebElement dblClick = driver.findElement(By.xpath(input.substring(input.indexOf(":")+1, input.length())));
			Actions builder = new Actions(driver);
			builder.doubleClick(dblClick).perform();
			return "::Passed - Successfully DoubleClick on the '" + objectData + "' in the field '" + objectName + "' and double clicked. \r\n";
		}
	}
	catch(STEPFrameworkMissingORObjectException e)
	{
		return "::Failed  - '"+objectName+"' object is missing in the Object Repository. \r\n";
	}
	catch(STEPFrameworkInvalidIdentifierException e)
	{
		return "::Failed  - Invalid identifier for the object "+objectName+" in Object Repository. \r\n";
	}
	catch(Exception e)
	{
		return getErrorMessage(e,objectName);
	}
}

/***
 * Purpose: To Store Element List in the ArrayList by collecting the elements via getAttribute method
 * Author: Vaibhav Shinde
 * @param driver
 * @param objectName
 * @param objectData  (Passed objectData parameter as "SnapShotDimensions|Title" (i.e. SnapShotDimensions is ArrayName and Title is attribute)
 * @param browser_type
 * @return
 */
private static String runGetAndStoreAttributeArrayList(WebDriver driver, String objectName ,String objectData,String browser_type)
{
	try
	{
		driver.manage().timeouts().implicitlyWait(15,TimeUnit.SECONDS);

		ArrayList<String> Array_list_elements =new ArrayList<String>();
		String strElementName = objectData.split("\\|")[0];        //Attribute Element Array List
		String strElementAttrName = objectData.split("\\|")[1];   // Attribute Name

		int size=driver.findElements(getIdentifier(objectName)).size();


		if(size==0)
			return "::Failed  - '"+objectName + "' element is not found '"+takeScreenshot(driver)+"' \r\n";
		else
		{

			List<WebElement> list_elements=driver.findElements(getIdentifier(objectName));
			if(list_elements.size()!=0)
			{
				int nullArrayCount =0 ;
				for(int count=0;count<list_elements.size();count++)
				{
				  if (list_elements.get(count).getAttribute(strElementAttrName) == null)  // Verifing the Attribute Name is invalid for Element or not
					{
					  nullArrayCount=nullArrayCount+1;
					}
					Array_list_elements.add(list_elements.get(count).getAttribute(strElementAttrName));
				}

				// Verifing here the generated array is NULL then returning the Attribute name is invalid
				if (nullArrayCount==list_elements.size())
				{
					return "::Failed  - '"+strElementAttrName+"'Attribute Name is not found in the '"+objectName + "'. '"+takeScreenshot(driver)+"' \r\n";
				}

				// Storing the Attribute ArrayList in the HashTable
				ArrayRandomValues.put(objectData.split("\\|")[0], Array_list_elements);

				/* for(int count=0;count<Array_list_elements.size();count++)
				{
					System.out.println("From Static Array Value Store "+ArrayRandomValues.get(objectData.split("\\|")[0]).get(count));
				}*/

				}
			return "::Passed - Successfully Stored the Attributes Elements ArrayList'"+strElementName+ "'. \r\n";
  	    }
	}
	catch(ArrayIndexOutOfBoundsException e)
	{
		return "::Failed  - InputData should contain delimeter (|). \r\n";
	}
	catch(STEPFrameworkMissingORObjectException e)
	{
		return "::Failed  - '"+objectName+"' object is missing in the Object Repository. \r\n";
	}
	catch(STEPFrameworkInvalidIdentifierException e)
	{
		return "::Failed  - Invalid identifier for the object "+objectName+" in Object Repository. \r\n";
	}
	catch(Exception e)
	{
		return getErrorMessage(e);
	}
}

/***
 * Author: Vaibhav Shinde
 * Purpose: To Verify and Compare the Array List Item from the List. This method is work with "runGetAndStoreAttributeArrayList" method
 * @param driver
 * @param objectData  (Passed objectData parameter as "SnapShotDimensions|Activity Id|Activity Name|Advertiser Id"
 *                     (i.e. SnapShotDimensions is ArrayName and Activity Id, Activity Name  etc.. are expected List Item which checks with SnapShotDimensions ArrayName)
 * @param browser_type
 * @return
 */
private static String runVerifyFullItemList(WebDriver driver,String objectData,String browser_type)
{
	try
	{

		driver.manage().timeouts().implicitlyWait(60,TimeUnit.SECONDS);
		String ExpectedElementNotFound = "";
		//String actualValue = "";
		String expectedValue = "";
		int size=objectData.length();
		if(size==0)
			return "::Failed  - '"+objectData + "' length is zero'"+takeScreenshot(driver)+"' \r\n";
		else
		{
			// Taking the Actual ItemList ArrayName which list are stored globally
			String ActualArray_List_elements = objectData.split("\\|")[0];  //SnapShotDimensions|Activity Id|Activity Name|Advertiser Id|Advertiser Name|...

			// Creating Expected ItemList into Array to compare with actual list
			String[] ExpectedItemListValues=objectData.split("\\|");  //SnapShotDimensions|Activity Id|Activity Name|Advertiser Id|Advertiser Name|...

			//Converting String Array into Arraylist for Comparing ArrayList value
			ArrayList<String> ExpectedArray_list_elements =new ArrayList<String>(Arrays.asList(ExpectedItemListValues));

			//Remove the Actual ArrayList Element Name  from the Array List
			ExpectedArray_list_elements.remove(0); //To Remove ActualArray_List_elements Name from String List (i.e. it looks like:- Activity Id|Activity Name|Advertiser Id|Advertiser Name|...)

			for(int count=0;count<ExpectedArray_list_elements.size();count++)
			{
			   //actualValue  = ArrayRandomValues.get(ActualArray_List_elements).get(count).toString().trim().toLowerCase();
				expectedValue = ExpectedArray_list_elements.get(count).toString();

				if (expectedValue!= null && ArrayRandomValues.get(ActualArray_List_elements).contains(expectedValue)== false)
				{
					ExpectedElementNotFound = ExpectedElementNotFound +expectedValue+", ";
				}

/*				if (expectedValue.equalsIgnoreCase(actualValue)==false && expectedValue != null)
				{
					ExpectedElementNotFound = ExpectedElementNotFound +expectedValue+", ";
				}
*/
			}

			if (ExpectedElementNotFound.length()>0)
			{
				return "::Failed  - List '" + ExpectedElementNotFound + "' is not present/not match with sequence on '"+ActualArray_List_elements+"'. "+takeScreenshot(driver)+"'.  \r\n";
			}
				return "::Passed - Successfully Verified Full Item List '" + objectData + "' is present on '"+ActualArray_List_elements+"'.  \r\n";

			/*if (ArrayRandomValues.get(ActualArray_List_elements).equals(ExpectedArray_list_elements))
			{
				return "::Passed - Successfully Verified Full Item List '" + objectData + "' is present on '"+ActualArray_List_elements+"'.  \r\n";
			}
			else
			{
				return "::Failed  - List '" + objectData + "' is not present on '"+ActualArray_List_elements+"'. "+takeScreenshot(driver)+"'.  \r\n";
			}
			*/
		}
	}
	catch(ArrayIndexOutOfBoundsException e)
	{
		return "::Failed  - InputData should contain delimeter (|). \r\n";
	}
	catch(Exception e)
	{
		return getErrorMessage(e,objectData);
	}
}

/***
 * Purpose: Verify unwanted Dimensions and Metrics are disabled after selected Metrics by user using getAttribute Method
 * @Author: Vaibhav Shinde
 * @param driver
 * @param objectName
 * @param objectData
 * @param browser_type
 * @return
 */
private static String runVerifyMetricDimensionDisabled(WebDriver driver, String objectName,String objectData,String browser_type)
{
	try
	{
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		String input=getIdentifier(objectName).toString().replace("XXX", objectData);
		int size=driver.findElements(By.xpath(input.substring(input.indexOf(":")+1, input.length()))).size();
		if(size==0)
			return "::Failed  - '"+input + "' element is not found '"+takeScreenshot(driver)+"' \r\n";
		else
		{
			String object_value=driver.findElement(By.xpath(input.substring(input.indexOf(":")+1, input.length()))).getAttribute("aria-disabled");
			if (object_value.trim().contains("true"))
			{
				return "::Passed - Successfully Verified '" + objectData	+ "'is disabled in the '"+objectName+"'.  \r\n";
			}
 			    return "::Failed  - '" + objectData + "' is not disabled '"+takeScreenshot(driver)+"'.  \r\n";
		}

	}
	catch(STEPFrameworkMissingORObjectException e)
	{
		return "::Failed  - '"+objectName+"' object is missing in the Object Repository. \r\n";
	}
	catch(NullPointerException  e)
	{
		return "::Failed  - Specified '"+ objectData + "' is not disabled. \r\n";
	}
	catch(Exception e)
	{
		return getErrorMessage(e,objectName);
	}

}

public static String runWaitForInvisible(WebDriver driver,String objectName,String browser_type)
{
	WebDriverWait wait = new WebDriverWait(driver,40);


	try {

		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		int size=driver.findElements(getIdentifier(objectName)).size();

		if(size == 0)
		{
			return "::Failed  - '"+objectName + "' element is not found '"+takeScreenshot(driver)+"' \r\n";
		}else
		{
			wait.until(ExpectedConditions.invisibilityOfElementLocated(getIdentifier(objectName)));
			return "::Pass  - ' Successfully waited still "+objectName + "' element is invisible. \r\n";
		}

	} catch (STEPFrameworkMissingORObjectException e) {
		// TODO Auto-generated catch block
		return "::Failed  - '"+objectName+"' object is missing in the Object Repository. \r\n";
	} catch (STEPFrameworkInvalidIdentifierException e) {
		// TODO Auto-generated catch block
		return "::Failed  - '"+objectName+"' object is Invalid identifier. \r\n";
	}catch(Exception e)
	{
		return "::Fail  - ' Element "+objectName + "' is still visible after timeout of 40 Seconds. \r\n";
	}


}

public static String runWaitForVisible(WebDriver driver,String objectName,String browser_type)
{
	WebDriverWait wait = new WebDriverWait(driver,40);


	try {

		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		int size=driver.findElements(getIdentifier(objectName)).size();

		if(size == 0)
		{
			return "::Failed  - '"+objectName + "' element is not found '"+takeScreenshot(driver)+"' \r\n";
		}else
		{
			wait.until(ExpectedConditions.visibilityOfElementLocated(getIdentifier(objectName)));
			return "::Pass  - ' Successfully waited still "+objectName + "' element is visible. \r\n";
		}

	} catch (STEPFrameworkMissingORObjectException e) {
		// TODO Auto-generated catch block
		return "::Failed  - '"+objectName+"' object is missing in the Object Repository. \r\n";
	} catch (STEPFrameworkInvalidIdentifierException e) {
		// TODO Auto-generated catch block
		return "::Failed  - '"+objectName+"' object is Invalid identifier. \r\n";
	}catch(Exception e)
	{
		return "::Fail  - ' Element "+objectName + "' is invisible after timeout of 40 Seconds. \r\n";
	}


}

public static String runVerifyModelList(WebDriver driver,String objectData)
{
		ArrayList<ModelTable> missingModel = new ArrayList<ModelTable>();
		UIModels.clear();


		String column1=null;
		String column2=null;
		String column3=null;
		boolean flag=false;
		int size;

		/*//Check previous page is available in the screen.
		size = driver.findElements(By.xpath("//div[@id='interectiontable_wrapper']/div[2]/div[3]/div/span[1]")).size();
		if(size != 0)
		{
			driver.findElement(By.xpath("//div[@id='interectiontable_wrapper']/div[2]/div[3]/div/span[1]")).click();
			runWait(driver,"2");
		}*/

		//Find out initial no of rows in the page.
		size = driver.findElements(By.xpath("//table[@id='interectiontable']/tbody/tr[1]/td[3]/div")).size();

		//Read the records from webtable if no of rows is not equal to Zero
		if(size != 0)
		{
			for (int i=1;i<=size;i++)
			{
				column1 = driver.findElement(By.xpath("//table[@id='interectiontable']/tbody/tr["+i+"]/td[3]/div")).getText().toString();
				column2 = driver.findElement(By.xpath("//table[@id='interectiontable']/tbody/tr["+i+"]/td[5]/div/div[2]")).getText().toString();
				column3 = driver.findElement(By.xpath("//table[@id='interectiontable']/tbody/tr["+i+"]/td[6]")).getText().toString();

				ModelTable tb1=new ModelTable(column1,column2,column3);
				UIModels.add(tb1);

				flag=false;


				if(objectData.equalsIgnoreCase("Failed"))
				{

						for(ModelTable modelTable :FailedModels )
						{
							if(modelTable.getModelname().equalsIgnoreCase(column1))
								{
									System.out.println("Model is matching: "+column1);
									flag=true;
									break;
								}
						}

				}
				else if(objectData.equalsIgnoreCase("Active"))
				{
						for(ModelTable modelTable :ActiveFailedModels )
							{
								if(modelTable.getModelname().equalsIgnoreCase(column1))
								{
									System.out.println("Model is matching: "+column1);
									flag=true;
									break;
								}
							}

				}
				else if(objectData.equalsIgnoreCase("Inactive"))
				{

						for(ModelTable modelTable :InactiveModels )
							{
								if(modelTable.getModelname().equalsIgnoreCase(column1))
								{
									System.out.println("Model is matching: "+column1);
									flag=true;
									break;
								}
							}

				}
				else if(objectData.equalsIgnoreCase("Processing"))
				{
					for(ModelTable modelTable :ProcessingModels)
						{
							if(modelTable.getModelname().equalsIgnoreCase(column1))
							{
								System.out.println("Model is matching: "+column1);
								flag=true;
								break;
							}
						}

				}
				else if(objectData.equalsIgnoreCase("All"))
				{
					for(ModelTable modelTable :CouchbaseModels)
						{
							if(modelTable.getModelname().equalsIgnoreCase(column1))
								{
									System.out.println("Model is matching: "+column1);
									flag=true;
									break;
								}
						}

				}

				if (!flag)
				{
					ModelTable tb2=new ModelTable(column1,column2,column3);
					missingModel.add(tb2);

				}

			}

			//Check next page is avaiable in the pagination.
			size = driver.findElements(By.xpath("//div[@id='interectiontable_wrapper']/div[2]/div[3]/div/span[@class='paginate_enabled_next']")).size();

			//If next page is not equal to zero continue read the records, else make the size=0 to move out from the loop.
			if(size != 0)
				{
				driver.findElement(By.xpath("//div[@id='interectiontable_wrapper']/div[2]/div[3]/div/span[@class='paginate_enabled_next']")).click();
				runWait(driver,"2");
				size = driver.findElements(By.xpath("//table[@id='interectiontable']/tbody/tr")).size();
				}else
				{
					size = 0;
				}
		}	else
		{
			if(objectData.equalsIgnoreCase("Failed"))
			{

				if(FailedModels.size()== 0)
				{
					return "::Passed  - Webtable records are matching, but no records. \r\n";
				}else
				{
					return "::Failed  - Records in Webtable are not matching."+takeScreenshot(driver)+"\r\n";
				}

			}
			else if(objectData.equalsIgnoreCase("Active"))
			{

				if(ActiveFailedModels.size()== 0)
				{
					return "::Passed  - Webtable records are matching, but no records. \r\n";
				}else
				{
					return "::Failed  - Records in Webtable are not matching."+takeScreenshot(driver)+" \r\n";
				}

			}
			else if(objectData.equalsIgnoreCase("Inactive"))
			{

				if(InactiveModels.size()== 0)
				{
					return "::Passed  - Webtable records are matching, but no records. \r\n";
				}else
				{
					return "::Failed  - Records in Webtable are not matching."+takeScreenshot(driver)+" \r\n";
				}

			}
			else if(objectData.equalsIgnoreCase("Processing"))
			{

				if(ProcessingModels.size()== 0)
				{
					return "::Passed  - Webtable records are matching, but no records. \r\n";
				}else
				{
					return "::Failed  - Records in Webtable are not matching."+takeScreenshot(driver)+" \r\n";
				}


			}
			else if(objectData.equalsIgnoreCase("All"))
			{
				if(CouchbaseModels.size()== 0)
				{
					return "::Passed  - Webtable records are matching, but no records. \r\n";
				}else
				{
					return "::Failed  - Records in Webtable are not matching."+takeScreenshot(driver)+" \r\n";
				}

			}

		}


		if(missingModel.size() > 0)
		{
			for(ModelTable itr : missingModel)
			{
				System.out.println(itr.getModelname());
			}
			return "::Failed  - Webtable records are not matching."+takeScreenshot(driver)+" \r\n";
		}else
			{
				return "::Passed  - Webtable records are matching. \r\n";
			}


	}

public static String runWaitForRandomVisible(WebDriver driver,String objectName,String objectData,String browser_type)
{
	WebDriverWait wait = new WebDriverWait(driver,40);


	try {

		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		String input=getIdentifier(objectName).toString().replace("XXX", strRandomValues.get(objectData));
		int size=driver.findElements(By.xpath(input.substring(input.indexOf(":")+1, input.length()))).size();

		if(size == 0)
		{
			return "::Failed  - '"+objectName + "' element is not found '"+takeScreenshot(driver)+"' \r\n";
		}else
		{
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(input.substring(input.indexOf(":")+1, input.length()))));
			return "::Passed  - ' Successfully waited still "+objectName + "' element is visible. \r\n";
		}

	} catch (STEPFrameworkMissingORObjectException e) {
		// TODO Auto-generated catch block
		return "::Failed  - '"+objectName+"' object is missing in the Object Repository. \r\n";
	} catch (STEPFrameworkInvalidIdentifierException e) {
		// TODO Auto-generated catch block
		return "::Failed  - '"+objectName+"' object is Invalid identifier. \r\n";
	}catch(Exception e)
	{
		return "::Failed  - ' Element "+objectName + "' is invisible after timeout of 40 Seconds. \r\n";
	}


}

public static String runWaitForRandomInvisible(WebDriver driver,String objectName,String objectData,String browser_type)
{
	WebDriverWait wait = new WebDriverWait(driver,40);


	try {

		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		String input=getIdentifier(objectName).toString().replace("XXX", strRandomValues.get(objectData));
		int size=driver.findElements(By.xpath(input.substring(input.indexOf(":")+1, input.length()))).size();

		if(size == 0)
		{
			return "::Failed  - '"+objectName + "' element is not found '"+takeScreenshot(driver)+"' \r\n";
		}else
		{
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(input.substring(input.indexOf(":")+1, input.length()))));
			return "::Passed  - ' Successfully waited still "+objectName + "' element is invisible. \r\n";
		}

	} catch (STEPFrameworkMissingORObjectException e) {
		// TODO Auto-generated catch block
		return "::Failed  - '"+objectName+"' object is missing in the Object Repository. \r\n";
	} catch (STEPFrameworkInvalidIdentifierException e) {
		// TODO Auto-generated catch block
		return "::Failed  - '"+objectName+"' object is Invalid identifier. \r\n";
	}catch(Exception e)
	{
		return "::Failed  - ' Element "+objectName + "' is still visible after timeout of 40 Seconds. \r\n";
	}


}

/***This method is used to enter file upload path from windows.
 * Autor:Gisha George
 * @param objectName
 * @param objectData
 * @return
 * @throws AWTException
 */

public static String runWindowsFileUpLoad(String objectName,String objectData) throws AWTException
{
		StringSelection ss=new StringSelection(objectData);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss,null);

		Robot robot=new Robot();
		robot.delay(1000);
		//This step clicks on 'Browse' button
		robot.keyPress(KeyEvent.VK_ENTER);
		//This step clicks on 'File name' textbox
		robot.keyPress(KeyEvent.VK_ENTER);
		//Next two steps does "Ctrl+V"
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		//robot.keyPress(KeyEvent.VK_V);
		//robot.keyPress(KeyEvent.VK_CONTROL);
		//This step clicks on 'Open' button
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyPress(KeyEvent.VK_ENTER);
		return "::Passed  - ' Successfully uploaded file from "+objectName + "' \r\n";


}


}



