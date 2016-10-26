package synechron.step.framework.objects;

import java.util.ArrayList;
import java.util.List;

public class STEPTestSuiteVO {

	private List<STEPTestCaseVO> testCaseList = new ArrayList<STEPTestCaseVO>();
	private String testSuiteName;
	private String testSuiteObjRepLocation;
	private String testSuiteFileLocation;
	private String testSuiteBrowserType;
	private String testSuiteStatus;
	private String testSuiteMessage;
	private String testURL;
	
	public List<STEPTestCaseVO> getTestCaseList() {
		return testCaseList;
	}
	public void addTestCaseToTestSuite(STEPTestCaseVO testCaseVO) {
		this.testCaseList.add(testCaseVO);
	}
	public String getTestSuiteName() {
		return testSuiteName;
	}
	public void setTestSuiteName(String testSuiteName) {
		this.testSuiteName = testSuiteName;
	}
	public void setTestSuiteObjRepFileName(String testSuiteObjRepLocation) {
		this.testSuiteObjRepLocation = testSuiteObjRepLocation;
	}
	public String getTestSuiteObjRepFileName() {
		return testSuiteObjRepLocation;
	}
	public void setTestSuiteFileName(String testSuiteLocation) {
		this.testSuiteFileLocation = testSuiteLocation;
	}
	public String getTestSuiteFileName() {
		return testSuiteFileLocation;
	}
	public void setTestSuiteStatus(String testSuiteStatus) {
		this.testSuiteStatus = testSuiteStatus;
	}
	public String getTestSuiteStatus() {
		return testSuiteStatus;
	}
	public void setTestSuiteMessage(String testSuiteMessage) {
		this.testSuiteMessage = testSuiteMessage;
	}
	public String getTestSuiteMessage() {
		return testSuiteMessage;
	}
	public void setTestSuiteBrowserType(String testSuiteBrowserType) {
		this.testSuiteBrowserType = testSuiteBrowserType;
	}
	public String getTestSuiteBrowserType() {
		return testSuiteBrowserType;
	}
	
	public void setTestSuiteURL(String testSuiteURL) {
		this.testURL = testSuiteURL;
	}
	public String getTestURL() {
		return testURL;
	}
}
