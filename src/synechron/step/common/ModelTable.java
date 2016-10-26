package synechron.step.common;

public class ModelTable {
	
	String modelID;
	String modelname;
	String advertiserkey;
	String campaignkey;
	String status;
	
	public ModelTable(String modelID, String modelname, String advertiserkey,
			String campaignkey, String status) {
		super();
		this.modelID = modelID;
		this.modelname = modelname;
		this.advertiserkey = advertiserkey;
		this.campaignkey = campaignkey;
		this.status = status;
	}
	
	public ModelTable(String column1, String column2, String column3,
			String column4) {
		super();
		this.modelID = column1;
		this.modelname = column4;
		this.advertiserkey = column2;
		this.campaignkey = column3;		
	}
	
	public ModelTable(String column5, String column6, 
			String column7) {
		super();
		this.modelname = column5;
		this.advertiserkey = column6;
		this.campaignkey = column7;		
	}
	
	
	

	public ModelTable() {
		// TODO Auto-generated constructor stub
	}

	public String getModelID() {
		return modelID;
	}
	public void setModelID(String modelID) {
		this.modelID = modelID;
	}
	public String getModelname() {
		return modelname;
	}
	public void setModelname(String modelname) {
		this.modelname = modelname;
	}
	public String getAdvertiserkey() {
		return advertiserkey;
	}
	public void setAdvertiserkey(String advertiserkey) {
		this.advertiserkey = advertiserkey;
	}
	public String getCampaignkey() {
		return campaignkey;
	}
	public void setCampaignkey(String campaignkey) {
		this.campaignkey = campaignkey;
	}
	public String getStatus() {
		return status;
	}
		public void setStatus(String status) {
		this.status = status;
	}
	
	

}
