package synechron.step.framework.objects;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.w3c.dom.Document;


public class STEPFrameworkVO {

	private Document resultDocument;
	private List<STEPTestSuiteVO> testSuiteList = new ArrayList<STEPTestSuiteVO>();
	private String frameworkLocation;
	private Hashtable<String, String> configData = new Hashtable<String, String>();
	private String frameworkStatus;
	public List<STEPTestSuiteVO> getTestSuiteList() {
		return testSuiteList;
	}
	public void addTestSuiteToList(STEPTestSuiteVO testSuite) {
		this.testSuiteList.add(testSuite);
	}
	public String getFrameworkLocation() {
		return frameworkLocation;
	}
	public void setFrameworkLocation(String frameworkLocation) {
		this.frameworkLocation = frameworkLocation;
	}
	public Hashtable<String, String> getConfigData() {
		return configData;
	}
	public void setConfigData(String key, String value) {
		this.configData.put(key, value);
	}
	public void setResultDocument(Document resultDocument) {
		this.resultDocument = resultDocument;
	}
	public Document getResultDocument() {
		return resultDocument;
	}
	public void setFrameworkStatus(String frameworkStatus) {
		this.frameworkStatus = frameworkStatus;
	}
	public String getFrameworkStatus() {
		return frameworkStatus;
	}
	public void Clear() {
		this.testSuiteList.clear();
	}
}
