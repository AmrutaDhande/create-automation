package synechron.step;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;

public class STEPReportUtility {

	private Document xmlReportDocument;
	
	public STEPReportUtility() throws ParserConfigurationException {
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
		xmlReportDocument = docBuilder.newDocument();
//		Element xmlReportRootElement = xmlReportDocument.createElement("ArrayOfTestResults");
		
	}
	
	public Document getXMLReportDocument() {
		System.out.println("Report Document: " + xmlReportDocument.toString());
		return xmlReportDocument;
	}
}
