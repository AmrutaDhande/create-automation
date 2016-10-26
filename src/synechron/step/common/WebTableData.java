package synechron.step.common;

//import java.util.Comparator;
//import java.util.Date;

public class WebTableData {
	
	String Column1; 	// Status column from all tables
	String Column2; 	// Advertiser/CampaignName/Activity column from respective table
	int column3; 		// Advertiser/CampaignName/Activity ID column from respective table
	String column4;		// Agency/Advertiser/Owner column from respective table
	String start_date;   // Start_Date column from Campaign Webtable
	String end_date;   // End_Date column from Campaign Webtable
	
  //Constructor for "Advertiser/Activity"
	
	WebTableData(String Column1,String Column2,int Column3,String Column4)
	{
		super();
		this.Column1=Column1;
		this.Column2=Column2;
		this.column3=Column3;
		this.column4=Column4;
		
	}
	
	
	//Constructor for "Campaign"
	
		WebTableData(String Column1,String Column2,int Column3,String Column4,String start_date,String end_date)
		{
			super();
			this.Column1=Column1;
			this.Column2=Column2;
			this.column3=Column3;
			this.column4=Column4;
			this.start_date=start_date;
			this.end_date=end_date;
		}


		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result
					+ ((Column1 == null) ? 0 : Column1.hashCode());
			result = prime * result
					+ ((Column2 == null) ? 0 : Column2.hashCode());
			result = prime * result + column3;
			result = prime * result
					+ ((column4 == null) ? 0 : column4.hashCode());
			result = prime * result
					+ ((end_date == null) ? 0 : end_date.hashCode());
			result = prime * result
					+ ((start_date == null) ? 0 : start_date.hashCode());
			return result;
		}


		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			WebTableData other = (WebTableData) obj;
			if (Column1 == null) {
				if (other.Column1 != null)
					return false;
			} else if (!Column1.equals(other.Column1))
				return false;
			if (Column2 == null) {
				if (other.Column2 != null)
					return false;
			} else if (!Column2.equals(other.Column2))
				return false;
			if (column3 != other.column3)
				return false;
			if (column4 == null) {
				if (other.column4 != null)
					return false;
			} else if (!column4.equals(other.column4))
				return false;
			if (end_date == null) {
				if (other.end_date != null)
					return false;
			} else if (!end_date.equals(other.end_date))
				return false;
			if (start_date == null) {
				if (other.start_date != null)
					return false;
			} else if (!start_date.equals(other.start_date))
				return false;
			return true;
		}
		
		


		@Override
		public String toString() {
			return "WebTableData [Column1=" + Column1 + ", Column2=" + Column2
					+ ", column3=" + column3 + ", column4=" + column4
					+ ", start_date=" + start_date + ", end_date=" + end_date
					+ "]";
		}


		public String getColumn1() {
			return Column1;
		}


		public void setColumn1(String column1) {
			Column1 = column1;
		}


		public String getColumn2() {
			return Column2;
		}


		public void setColumn2(String column2) {
			Column2 = column2;
		}


		public int getColumn3() {
			return column3;
		}


		public void setColumn3(int column3) {
			this.column3 = column3;
		}


		public String getColumn4() {
			return column4;
		}


		public void setColumn4(String column4) {
			this.column4 = column4;
		}


		public String getStart_date() {
			return start_date;
		}


		public void setStart_date(String start_date) {
			this.start_date = start_date;
		}


		public String getEnd_date() {
			return end_date;
		}


		public void setEnd_date(String end_date) {
			this.end_date = end_date;
		}
		
		
		/*static final Comparator<WebTableData> SORT_Column2_ASCENDING=new Comparator<WebTableData>()
				{
			      public int compare(WebTableData o1,WebTableData o2)
			      {
			    	  
			    	  String str1=o1.getColumn2().toUpperCase();
			    	  String str2=o2.getColumn2().toUpperCase();
			    	  
			    	  return str1.compareTo(str2);
			    	  
			    	  //return (result * -1);
			      }
				};
		
				static final Comparator<WebTableData> SORT_Column2_DESCENDING=new Comparator<WebTableData>()
						{
					      public int compare(WebTableData o1,WebTableData o2)
					      {
					    	  
					    	  String str1=o1.getColumn2().toUpperCase();
					    	  String str2=o2.getColumn2().toUpperCase();
					    	  
					    	  return str2.compareTo(str1);
					    	  //int result=o1.getColumn2().compareTo(o2.getColumn2());
					    	  //return (result);
					      }
						};*/
		
}
