package synechron.step.framework.objects;

public class STEPTestCaseVO {

	private String testCaseId = "";
	private String testCaseTitle = "";
	private String executeTestCase = "";
	private String testCaseStatus = "";
	private int testCaseDataRowId = 0;
	private String executedSteps = "";
	private String testCaseStartedAt = "";
	private String testCaseEndedAt = "";
	private String testCaseExecutionTime = "";
	private String testCaseScreenshotLoc = "";
	private String testCaseInputData;
	public String getTestCaseId() {
		return testCaseId;
	}
	public void setTestCaseId(String testCaseId) {
		this.testCaseId = testCaseId;
	}
	public String getTestCaseTitle() {
		return testCaseTitle;
	}
	public void setTestCaseTitle(String testCaseTitle) {
		this.testCaseTitle = testCaseTitle;
	}
	public String getTestCaseInputData() {
		return testCaseInputData;
	}
	public void setTestCaseInputData(String testCaseInputData) {
		this.testCaseInputData = testCaseInputData;
	}
	public void setExecuteTestCase(String executeTestCase) {
		this.executeTestCase = executeTestCase;
	}
	public String getExecuteTestCase() {
		return executeTestCase;
	}
	public void setTestCaseStatus(String testCaseStatus) {
		this.testCaseStatus = testCaseStatus;
	}
	public String getTestCaseStatus() {
		return testCaseStatus;
	}
	public void setTestCaseDataRowId(int testCaseDataRowId) {
		this.testCaseDataRowId = testCaseDataRowId;
	}
	public int getTestCaseDataRowId() {
		return testCaseDataRowId;
	}
	public void setExecutedSteps(String executedSteps) {
		this.executedSteps = executedSteps;
	}
	public String getExecutedSteps() {
		return executedSteps;
	}
	public void setTestCaseStartedAt(String testCaseStartedAt) {
		this.testCaseStartedAt = testCaseStartedAt;
	}
	public String getTestCaseStartedAt() {
		return testCaseStartedAt;
	}
	public void setTestCaseEndedAt(String testCaseEndedAt) {
		this.testCaseEndedAt = testCaseEndedAt;
	}
	public String getTestCaseEndedAt() {
		return testCaseEndedAt;
	}
	public void setTestCaseExecutionTime(String testCaseExecutionTime) {
		this.testCaseExecutionTime = testCaseExecutionTime;
	}
	public String getTestCaseExecutionTime() {
		return testCaseExecutionTime;
	}
	public void setTestCaseScreenshotLoc(String testCaseScreenshotLoc) {
		this.testCaseScreenshotLoc = testCaseScreenshotLoc;
	}
	public String getTestCaseScreenshotLoc() {
		return testCaseScreenshotLoc;
	}
	
}
