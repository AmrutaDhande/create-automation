package synechron.step.testing;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

import com.google.common.io.Files;
import synechron.step.STEPFrameworkDriver;
import synechron.step.framework.objects.STEPFrameworkVO;
public class STEPTestResultGenerator {
	public static STEPFrameworkVO frameworkVObject=null;
	public static java.util.Hashtable<String, String> resultValues=new java.util.Hashtable<String, String>();
	public static String strFileName="";
	public static String reportvalues[];
	/***
	 * Author	: 
	 * Purpose	: To Create XML Document (TestExecutionLog.xml) from the frameworkVO data
	 * @param frameworkVO
	 * @param SuiteIterator
	 * @param CaseIterator
	 * @throws ParserConfigurationException
	 */
	public static void createXMLDocument(STEPFrameworkVO frameworkVO, int SuiteIterator,int CaseIterator) throws ParserConfigurationException 
	{
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = null;
		try 
		{
			docBuilder = docBuilderFactory.newDocumentBuilder();
		} 
		catch (ParserConfigurationException e1) 
		{
			e1.printStackTrace();
		}
		Document reportDocument = docBuilder.newDocument();
		Element allTestResults = reportDocument.createElement("ArrayOfTestResults");
		frameworkVObject=frameworkVO;

		int testSuiteCount = SuiteIterator;
		int testCaseCount = 0;
		for (int testSuiteIterator = 0; testSuiteIterator < testSuiteCount+1; testSuiteIterator++) 
		{
			//testCaseCount = frameworkVO.getTestSuiteList().get(testSuiteIterator).getTestCaseList().size();
			if(testSuiteIterator == SuiteIterator){
				testCaseCount = CaseIterator+1;
			}else{
				testCaseCount = frameworkVO.getTestSuiteList().get(testSuiteIterator).getTestCaseList().size();

			}

			for (int testCaseIterator = 0; testCaseIterator < testCaseCount; testCaseIterator++) 
			{
				Element testCaseElement = reportDocument.createElement("TestResults");
				Element projectName = addXmlElementAndText(reportDocument, "projectName", frameworkVO.getConfigData().get("Project_Name"));
				Element os = addXmlElementAndText(reportDocument, "OS", System.getProperty("os.name"));
				Element machineName = null;
				try {
					machineName = addXmlElementAndText(reportDocument, "machineName", InetAddress.getLocalHost().getHostName());
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
				Element environment = addXmlElementAndText(reportDocument, "environment", frameworkVO.getConfigData().get("Environment_Name"));
				Element projectVer = addXmlElementAndText(reportDocument, "projectVer", frameworkVO.getConfigData().get("Project_Version"));
				Element browserType = addXmlElementAndText(reportDocument, "browserType", frameworkVO.getTestSuiteList().get(testSuiteIterator).getTestSuiteBrowserType());
				Element suiteName = addXmlElementAndText(reportDocument, "suiteName", frameworkVO.getTestSuiteList().get(testSuiteIterator).getTestSuiteName());
				Element testCaseID = addXmlElementAndText(reportDocument, "testCaseID", 
						frameworkVO.getTestSuiteList().get(testSuiteIterator).getTestCaseList().get(testCaseIterator).getTestCaseId());
				Element testCaseName = addXmlElementAndText(reportDocument, "testCaseName", 
						frameworkVO.getTestSuiteList().get(testSuiteIterator).getTestCaseList().get(testCaseIterator).getTestCaseTitle());
				Element testCaseResult = addXmlElementAndText(reportDocument, "testCaseResult", 
						frameworkVO.getTestSuiteList().get(testSuiteIterator).getTestCaseList().get(testCaseIterator).getTestCaseStatus());
				Element testCaseInputData = addXmlElementAndText(reportDocument, "testCaseInputData", 
						frameworkVO.getTestSuiteList().get(testSuiteIterator).getTestCaseList().get(testCaseIterator).getTestCaseInputData());
				Element testSteps = addXmlElementAndText(reportDocument, "testSteps",
						frameworkVO.getTestSuiteList().get(testSuiteIterator).getTestCaseList().get(testCaseIterator).getExecutedSteps());
				Element startTime = addXmlElementAndText(reportDocument, "startTime", 
						frameworkVO.getTestSuiteList().get(testSuiteIterator).getTestCaseList().get(testCaseIterator).getTestCaseStartedAt());
				Element endTime = addXmlElementAndText(reportDocument, "endTime", 
						frameworkVO.getTestSuiteList().get(testSuiteIterator).getTestCaseList().get(testCaseIterator).getTestCaseEndedAt());
				Element executionTime = addXmlElementAndText(reportDocument, "executionTime", 
						frameworkVO.getTestSuiteList().get(testSuiteIterator).getTestCaseList().get(testCaseIterator).getTestCaseExecutionTime());
				testCaseElement.appendChild(projectName);
				testCaseElement.appendChild(os);
				testCaseElement.appendChild(machineName);
				testCaseElement.appendChild(environment);
				testCaseElement.appendChild(projectVer);
				testCaseElement.appendChild(browserType);
				testCaseElement.appendChild(suiteName);
				testCaseElement.appendChild(testCaseID);
				testCaseElement.appendChild(testCaseName);
				testCaseElement.appendChild(testCaseResult);
				testCaseElement.appendChild(testCaseInputData);
				testCaseElement.appendChild(testSteps);
				testCaseElement.appendChild(startTime);
				testCaseElement.appendChild(endTime);
				testCaseElement.appendChild(executionTime);
				allTestResults.appendChild(testCaseElement);
			}
		}
		reportDocument.appendChild(allTestResults);
		try {
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			DOMSource source = new DOMSource(reportDocument);
			StreamResult result = new StreamResult(new File(STEPFrameworkDriver.strFrameworkLocation + "\\output\\Results\\"+STEPFrameworkDriver.strResultFileName+"\\TestExecutionLog.xml"));
			transformer.transform(source, result);
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerFactoryConfigurationError e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
	}
	/***
	 * Author	: 
	 * Purpose	: Create New Text Node in document, create New Element and append the element to the document
	 * @param document
	 * @param elementName
	 * @param elementText
	 * @return
	 */
	private static Element addXmlElementAndText(Document document, String elementName, String elementText) {
		Text text = document.createTextNode(elementText);
		Element element = document.createElement(elementName);
		element.appendChild(text);
		return element;
	}
	/***
	 * Author	: 
	 * Purpose	: To create the Temporary TestExecutionReportTemp.html report file from existing TestExecutionReport.html report 
	 * @param SuiteIterator
	 * @param CaseIteratore
	 */
	public static void formatHTML(int SuiteIterator,int CaseIteratore) {
		File inputHTML = new File(STEPFrameworkDriver.strFrameworkLocation + "\\output\\Results\\"+STEPFrameworkDriver.strResultFileName+"\\TestExecutionReport.html");
		File outputHTML = new File(STEPFrameworkDriver.strFrameworkLocation + "\\output\\Results\\"+STEPFrameworkDriver.strResultFileName+"\\TestExecutionReportTemp.html");
		try {
			FileReader inputHTMLReader = new FileReader(inputHTML);
			BufferedReader inputHTMLBufReader = new BufferedReader(inputHTMLReader);
			int lineCount = 0;
			while (inputHTMLBufReader.readLine() != null) {
				lineCount++;
			}
			String[] currString = new String[lineCount];
			String[] newString = new String[lineCount];
			inputHTMLReader = new FileReader(inputHTML);
			inputHTMLBufReader = new BufferedReader(inputHTMLReader);
			FileWriter outputHTMLWriter = new FileWriter(outputHTML);
			BufferedWriter outputHTMLBufWriter = new BufferedWriter(outputHTMLWriter);
			for (int currLine = 0; currLine < lineCount; currLine++) {
				currString[currLine] = inputHTMLBufReader.readLine();
				newString[currLine] = formatReportString(currString[currLine]);
				outputHTMLBufWriter.write(newString[currLine]);
			}
			outputHTMLBufWriter.close();
			outputHTMLWriter.close();
			inputHTMLBufReader.close();
			inputHTMLReader.close();
			outputHTML = null;
			inputHTML = null;
			File tempHTML = new File(STEPFrameworkDriver.strFrameworkLocation + "\\output\\Results\\"+STEPFrameworkDriver.strResultFileName+"\\TestExecutionReport.html");
			File finalHTML = new File(STEPFrameworkDriver.strFrameworkLocation + "\\output\\Results\\"+STEPFrameworkDriver.strResultFileName+"\\TestExecutionReportTemp.html");
			Files.move(finalHTML, tempHTML);

		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		} catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	/***
	 * Author	: 
	 * Purpose	: To format Report String like (Failed, Passed, links) in the HTML Report.
	 * @param string
	 * @return
	 */
	private static String formatReportString(String string) {
		if (string.contains("<td>FAILED</td>")) {
			string = string.replace("<td>FAILED</td>", "<td><font color=\"#990000\"><b>FAILED</b></font></td>");
		}
		if (string.contains("<td>PASSED</td>")) {
			string = string.replace("PASSED", "<font color=\"#008000\"><b>PASSED</b></font>");
		}
		if (string.contains("<td>Exception</td>")) {
			string = string.replace("EXCEPTION", "<font color=\"#990000\"><b>EXCEPTION</b></font>");
		}
		if (string.contains("&#13;")) {
			string = string.replace("&#13;","<br>");
		}
		if (string.contains("&lt;ssloc&gt;")) {
			String msg = string.substring(0, string.indexOf("&lt;ssloc&gt;"));
			String ssLocationString = string.substring(string.indexOf("&lt;ssloc&gt;") + "&lt;ssloc&gt;".length(), string.indexOf("&lt;/ssloc&gt;"));
			ssLocationString = "<a href='" + ssLocationString + "'>Error Screenshot</a><br>"; 
			string = msg + ssLocationString;
		}
		if (string.contains("Passed - ")) 
			string = string.replace(string, "<font color=\"#008000\">" + string + "</font>");
		if (string.contains("Failed  - ")) 
			string = string.replace(string, "<font color=\"#990000\">" + string + "</font>");
		if (string.contains("Exception ")) 
			string = string.replace(string, "<font color=\"#990000\">" + string + "</font>");
		return string;
	}
	/***
	 * Author	: 
	 * Purpose	: Create the HTML Report from Transformer.xsl and TestExecutionLog.xml files.
	 * @param SuiteIterator
	 * @param CaseIterator
	 */
	public static void createHTMLReport(int SuiteIterator,int CaseIterator) 
	{
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = null;
		Transformer transformer;
		try {
			File xslSource = new File(STEPFrameworkDriver.strFrameworkLocation + "\\output\\Results\\Transformer.xsl");
			StreamSource xslStreamSource = new StreamSource(xslSource);
			transformer = TransformerFactory.newInstance().newTransformer(xslStreamSource);
			docBuilder = docBuilderFactory.newDocumentBuilder();
			File reportXMLFile = new File(STEPFrameworkDriver.strFrameworkLocation + "\\output\\Results\\"+STEPFrameworkDriver.strResultFileName+"\\TestExecutionLog.xml");
			Document reportDocument = docBuilder.parse(reportXMLFile);
			DOMSource reportDocumentSource = new DOMSource(reportDocument);
			File reportHTMLFile = new File(STEPFrameworkDriver.strFrameworkLocation + "\\output\\Results\\"+STEPFrameworkDriver.strResultFileName+"\\TestExecutionReport.html");
			StreamResult reportHTMLStream = new StreamResult(reportHTMLFile);
			transformer.transform(reportDocumentSource, reportHTMLStream);
			reportHTMLStream = null;
			reportHTMLFile = null;

		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerFactoryConfigurationError e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
	}
	/* Function to get the date & time as string */
	/***
	 * Author	: 
	 * Purpose	: To get the current date & time in the given format as sting.
	 * @return
	 */
	public static String getTimeStamp()
	{
		Date nd=new Date();
		DateFormat formatters = new SimpleDateFormat("yyyy_MM_dd_HHmmss");  
		String strdate  = formatters.format(nd);
		return strdate;
	}
	/* Function to add Logos to the Report File */ 
	
}
