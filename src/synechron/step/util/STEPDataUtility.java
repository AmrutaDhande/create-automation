package synechron.step.util;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
/*import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;*/

import synechron.step.STEPFrameworkDriver;
//import synechron.step.common.STEPKeywordLibrary;
/*import synechron.step.exceptions.STEPFrameworkInvalidIdentifierException;
import synechron.step.exceptions.STEPFrameworkMissingORObjectException;*/

public class STEPDataUtility {

	public static String screenshotDirectory=null;
	public static String getDateString(long longDate) {
		Date date = new Date(longDate);
		return DateFormat.getInstance().format(date);
	}
	/***
	 * Author	: Srikanth K
	 * Purpose	: To get the formated Date from the given date in long format
	 * @param longDate
	 * @return
	 */
	public static String formatDateForXMLReport(long longDate) 
	{
		Date date = new Date(longDate);
		SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		return dateFormater.format(date.getTime());
	}
	/***
	 * Author	: Srikanth K
	 * @param longDate
	 * @return
	 */
	public static String randomNumber() 
	{
		Date date = new Date();
		SimpleDateFormat dateFormater = new SimpleDateFormat("yyyyMMddHHmmss");
		return dateFormater.format(date.getTime());
	}
	/***
	 * Author	: Srikanth K
	 * Purpose	: To get the formated Date from the given date in format & format string
	 * @param longDate
	 * @param dateFormatString
	 * @return
	 * @throws ParseException
	 */
	public static String getDateString(long longDate, String dateFormatString) throws ParseException 
	{
		DateFormat dateFormat = new SimpleDateFormat(dateFormatString);
		Date date = new Date();
		return dateFormat.format(date);
	}
	/***
	 * Author	: Srikanth K
	 * Purpose	: To get the Time Difference between given two dates
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static String getTimeDifferenceString(long startDate, long endDate) 
	{
		long dateDiffHours = 0;
		long dateDiffMins = 0;
		long dateDiffSecs = 0;
		long dateDiff = endDate - startDate;
		dateDiffHours = dateDiff / (60 * 60 * 1000);
		dateDiffMins = (dateDiff - (dateDiffHours * 60 * 60 * 1000)) / (60 * 1000);
		dateDiffSecs = (dateDiff - ((dateDiffHours * (60 * 60 * 1000)) + (dateDiffMins * (60 * 1000)))) / 1000;

		return dateDiffHours + " hours, " + dateDiffMins + " mins, " + dateDiffSecs + " secs";
	}
	public static String getLocation(String strInput)
	{
		int index_OF_Slash = STEPFrameworkDriver.strFilePath.lastIndexOf("\\");		
		String Location = STEPFrameworkDriver.strFilePath.substring(0,index_OF_Slash)+strInput;
		return Location;
	}
	/***
	 * Author	: Srikanth K
	 * Purpose	: Update the Screenshot Directory
	 * @param frameworkLocation
	 * @param browserType
	 */
	public static void updateScreenshotDirectory(String frameworkLocation,String browserType)
	{
		SimpleDateFormat dateFormater = new SimpleDateFormat("dd_MM_yyyy");
		Date d=new Date();
		//dateFormater.format(d);
		screenshotDirectory=dateFormater.format(d);
		File file=new File(frameworkLocation+"\\output\\Results\\"+browserType+"\\ScreenshotLocation\\"+screenshotDirectory);
		if(file.exists())
		{
			File ls[]=file.listFiles();
			for(File f:ls)
				f.delete();
			file.delete();
			file.mkdir();
		}
		else
		{
			file.mkdir();
		}
	}
	
	/***
	 * Author	: Srikanth K
	 * Purpose	: To get the formated Date from the given date in format & format string
	 * @param longDate
	 * @param dateFormatString
	 * @return
	 * @throws ParseException
	 */
	public static Date getFormattedDate(String stringDate, String dateFormatString) throws ParseException 
	{
		SimpleDateFormat formatter=new SimpleDateFormat(dateFormatString);
		Date date = null;
        try
        {
		date=formatter.parse(stringDate);
        }catch(ParseException e)
        {
        	e.printStackTrace();
        }
        
		
		
		return date;
	}	
	
	
	
	
}
