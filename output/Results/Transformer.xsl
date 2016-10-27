<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">  
  <xsl:template match="/ArrayOfTestResults">
    <html>
      <head>
        <style>

          body {font-family:Trebuchet MS; font-size:5px;}

          td {text-align:center;font-family:Calibri; font-size:12px;}

          th {background-color:#FC975B;color:white;font-family:Calibri;font-size:16px;}

          pre {border: 1px solid #000000;background-color:#fdb78d;color:black;font-family:Calibri; valign:Center; padding: 5px 5px 5px 5px}

          td.Pass {color:Green;font-weight:bold}
          
          td.Fail {color:Red;font-weight:bold}

          td.NoRun {color:Gray;font-weight:bold}

          td.Test {text-align:left;}

          pre {word-wrap:break-word}

        </style>

      </head>

      <body>

        
	  <img src="../../Images/background.jpg" alt="Synechron Test Engineering Program" title="Synechron Test Engineering Program" border="0" />          

        

        <h2 style="font-size:420%" >Test Environment</h2>



        <table border="1" width="40%">

          <tr bgcolor="#9acd32">

            <th>OS Name</th>

            <th>Machine Name</th>

            <th>ProjectName</th>

          </tr>

          <tr>

            <td>
              <xsl:value-of select="TestResults/OS"/>
            </td>

            <td>
              <xsl:value-of select="TestResults/machineName"/>
            </td>

            <td>

              <xsl:value-of select="TestResults/projectName"/>

            </td>
           

          </tr>

        </table>



        <h2 style="font-size:420%">Suite Execution Summary</h2>
        <div>
          <table border="1">
            <tr bgcolor="#9acd32">
              <th>Sr.No.</th>
              <th>Module</th>
              <th>Passed</th>
              <th>Failed</th>
              <th>No Run</th>
              <th>Total</th>
              <th>ExecutionTime</th>
            </tr>
            <xsl:variable name="unique-list-browser" select="//browserType[not(.=following::browserType)]" />
            <xsl:variable name="unique-list" select="//suiteName[not(.=following::suiteName)]" />
            <xsl:for-each select="$unique-list-browser">
            <xsl:variable name="browserType" select="."/>
              <tr>
                <td colspan='7' bgcolor="#B2B232">
                  <b>
                    <xsl:value-of select='$browserType'/>
                  </b>
                </td>
              </tr>
              <xsl:for-each select="$unique-list">
                <xsl:variable name="suiteName" select="."/>                     
                  <xsl:variable name="TC" select="count(/ArrayOfTestResults/TestResults[suiteName=$suiteName][browserType=$browserType])"/>
                  <xsl:variable name="PC" select="count(/ArrayOfTestResults/TestResults[testCaseResult='PASSED'][suiteName=$suiteName][browserType=$browserType])"/>
                  <xsl:variable name="FC" select="count(/ArrayOfTestResults/TestResults[testCaseResult='FAILED'][suiteName=$suiteName][browserType=$browserType])"/>
                  <xsl:variable name="BC" select="count(/ArrayOfTestResults/TestResults[testCaseResult='Not Executed'][suiteName=$suiteName][browserType=$browserType])"/>
                  <xsl:variable name="ET" select="sum(/ArrayOfTestResults/TestResults[suiteName=$suiteName][browserType=$browserType]/executionTime)"/>
                <xsl:choose>
                  <xsl:when test="$TC=0">                    
                  </xsl:when>                  
                  <xsl:otherwise>                                     
                    <tr>
                      <td>
                        <xsl:value-of select="position()" />
                      </td>
                      <td class="Test">
                        <xsl:value-of select="$suiteName"/>
                      </td>
                      <td>
                        <a href='javascript:return 0;'>
                          <xsl:attribute name="onclick">                            
                            <xsl:text>showHideTable('Pass_</xsl:text>                      
                            <xsl:value-of select="$suiteName"/>_<xsl:value-of select="$browserType"/>
                            <xsl:text>_</xsl:text>
                            <xsl:value-of select='$PC'/>
                            <xsl:text>')</xsl:text>
                          </xsl:attribute>
                          <xsl:value-of select='$PC'/>
                        </a>
                      </td>
                      <td>
                        <a href='javascript:return 0;'>
                          <xsl:attribute name="onclick">

                            <xsl:text>showHideTable('Fail_</xsl:text>
                      
                            <xsl:value-of select="$suiteName"/>_<xsl:value-of select="$browserType"/>
                            <xsl:text>_</xsl:text>
                            <xsl:value-of select='$FC'/>

                            <xsl:text>')</xsl:text>

                          </xsl:attribute>
                          <xsl:value-of select='$FC'/>
                        </a>
                      </td>
                      <td>
                        <a href='javascript:return 0;'>
                          <xsl:attribute name="onclick">

                            <xsl:text>showHideTable('NoRun_</xsl:text>

                            <xsl:value-of select="$suiteName"/>_<xsl:value-of select="$browserType"/>
                            <xsl:text>_</xsl:text>
                            <xsl:value-of select='$BC'/>

                            <xsl:text>')</xsl:text>

                          </xsl:attribute>
                          <xsl:value-of select='$BC'/>
                        </a>
                      </td>
                      <td>
                        <a href='javascript:return 0;'>
                          <xsl:attribute name="onclick">

                            <xsl:text>showHideTable('All_</xsl:text>

                            <xsl:value-of select="$suiteName"/>_<xsl:value-of select="$browserType"/>
                            <xsl:text>_</xsl:text>
                            <xsl:value-of select='$TC'/>

                            <xsl:text>')</xsl:text>

                          </xsl:attribute>
                        <xsl:value-of select='$TC'/>
                        </a>
                      </td>
                      <td>
                        <xsl:value-of select="floor($ET div 60)"/>m<xsl:value-of select="format-number($ET mod 60,'#')"/>s
                      </td>
                    </tr>
                  </xsl:otherwise>
                </xsl:choose>
            </xsl:for-each>
              <tr bgcolor='#64E464'>
                <td colspan='2'>
                  <b>
                    <xsl:value-of select='$browserType'/> - Grand Total
                  </b>
                </td>
                <td>
                  <b>
                    <a href='javascript:return 0;'>
                      <xsl:attribute name="onclick">

                        <xsl:text>showHideTable('Pass_Gen_</xsl:text>

                        <xsl:value-of select="$browserType"/>_<xsl:value-of select='count(/ArrayOfTestResults/TestResults[testCaseResult="PASSED"][browserType=$browserType])'/>

                        <xsl:text>')</xsl:text>

                      </xsl:attribute>
                      <xsl:value-of select='count(/ArrayOfTestResults/TestResults[testCaseResult="PASSED"][browserType=$browserType])'/>
                    </a>
                  </b>
                </td>
                <td>
                  <b>
                    <a href='javascript:return 0;'>
                      <xsl:attribute name="onclick">

                        <xsl:text>showHideTable('Fail_Gen_</xsl:text>

                        <xsl:value-of select="$browserType"/>_<xsl:value-of select='count(/ArrayOfTestResults/TestResults[testCaseResult="FAILED"][browserType=$browserType])'/>

                        <xsl:text>')</xsl:text>

                      </xsl:attribute>
                      <xsl:value-of select='count(/ArrayOfTestResults/TestResults[testCaseResult="FAILED"][browserType=$browserType])'/>
                    </a>
                  </b>
                </td>
                <td>
                  <b>
                    <a href='javascript:return 0;'>
                      <xsl:attribute name="onclick">

                        <xsl:text>showHideTable('NoRun_Gen_</xsl:text>

                        <xsl:value-of select="$browserType"/>_<xsl:value-of select='count(/ArrayOfTestResults/TestResults[testCaseResult="Not Executed"][browserType=$browserType])'/>

                        <xsl:text>')</xsl:text>

                      </xsl:attribute>
                      <xsl:value-of select='count(/ArrayOfTestResults/TestResults[testCaseResult="Not Executed"][browserType=$browserType])'/>                      
                    </a>
                  </b>
                </td>
                <td>
                  <b>
                    <a href='javascript:return 0;'>
                      <xsl:attribute name="onclick">

                        <xsl:text>showHideTable('All_Gen_</xsl:text>

                        <xsl:value-of select="$browserType"/>_<xsl:value-of select="$browserType"/>_<xsl:value-of select='count(/ArrayOfTestResults/TestResults[testCaseResult="PASSED"][browserType=$browserType]) + count(/ArrayOfTestResults/TestResults[testCaseResult="FAILED"][browserType=$browserType])+ count(/ArrayOfTestResults/TestResults[testCaseResult="Not Executed"][browserType=$browserType])'/>

                        <xsl:text>')</xsl:text>

                      </xsl:attribute>                      
                        <xsl:value-of select='count(/ArrayOfTestResults/TestResults[testCaseResult="PASSED"][browserType=$browserType]) + count(/ArrayOfTestResults/TestResults[testCaseResult="FAILED"][browserType=$browserType]) + count(/ArrayOfTestResults/TestResults[testCaseResult="Not Executed"][browserType=$browserType])'/>
                      </a>
                    </b>
                </td>
                <td>
                  <xsl:variable name="GET" select='sum(/ArrayOfTestResults/TestResults[browserType=$browserType]/executionTime)'/>
                  <b>
                    <xsl:value-of select="floor($GET div 60)"/>m<xsl:value-of select="format-number($GET mod 60,'#')"/>s
                  </b>
                </td>
              </tr>
            </xsl:for-each>
            <tr bgcolor='#fdb78d'>
              <td colspan='2'>
                <b>Grand Total</b>
              </td>
              <td id="Pass">
                <b>
                  <a href='javascript:return 0;'>
                    <xsl:attribute name="onclick">

                      <xsl:text>showHideTable('Pass_Gen_All_</xsl:text>
                      
                      <xsl:value-of select='count(TestResults[testCaseResult="PASSED"])'/>

                      <xsl:text>')</xsl:text>

                    </xsl:attribute>
                    <xsl:value-of select='count(TestResults[testCaseResult="PASSED"])'/>
                  </a>
                </b>
              </td>
              <td id="Fail">
                <b>
                  <a href='javascript:return 0;'>
                    <xsl:attribute name="onclick">

                      <xsl:text>showHideTable('Fail_Gen_All_</xsl:text>

                      <xsl:value-of select='count(TestResults[testCaseResult="FAILED"])'/>

                      <xsl:text>')</xsl:text>

                    </xsl:attribute>
                    <xsl:value-of select='count(TestResults[testCaseResult="FAILED"])'/>
                  </a>
                </b>
              </td>
              <td id="Blocked">
                <b>
                  <a href='javascript:return 0;'>
                    <xsl:attribute name="onclick">

                      <xsl:text>showHideTable('NoRun_Gen_All_</xsl:text>

                      <xsl:value-of select='count(TestResults[testCaseResult="Not Executed"])'/>

                      <xsl:text>')</xsl:text>

                    </xsl:attribute>
                    <xsl:value-of select='count(TestResults[testCaseResult="Not Executed"])'/>
                  </a>
                </b>
              </td>
              <td id="Total">
                <b>
                  <a href='javascript:return 0;'>
                    <xsl:attribute name="onclick">

                      <xsl:text>showHideTable('All_Gen_All_</xsl:text>

                      <xsl:value-of select='count(TestResults[testCaseResult="PASSED"]) + count(TestResults[testCaseResult="FAILED"])+ count(TestResults[testCaseResult="Not Executed"])'/>

                      <xsl:text>')</xsl:text>

                    </xsl:attribute>
                    <xsl:value-of select='count(TestResults[testCaseResult="PASSED"]) + count(TestResults[testCaseResult="FAILED"]) + count(TestResults[testCaseResult="Not Executed"])'/>
                  </a>
                </b>
              </td>
              <td id="ExecutionTime">
                <xsl:variable name="GET" select='sum(/ArrayOfTestResults/TestResults/executionTime)'/>
                <b>
                  <xsl:value-of select="floor($GET div 60)"/>m<xsl:value-of select="format-number($GET mod 60,'#')"/>s
                </b>
              </td>
            </tr>
          </table>

          <br/>

          <table border="1" style="position: relative; top: 0px; left: 0px;">

            <tr bgcolor="#9acd32">

              <th>Execution Date</th>

              <th>Execution StartTime</th>

              <th>Execution EndTime</th>

              <th>ExecutionTime</th>

            </tr>

            <tr>

              <td>

                <xsl:value-of select="substring-before(TestResults/startTime,'T')"/>

              </td>

              <td>

                <xsl:value-of select="substring-after(TestResults/startTime,'T')"/>

              </td>

              <td>

                <xsl:value-of select="substring-after(TestResults[last()]/endTime,'T')"/>

              </td>

              <td>

                <xsl:value-of select="floor(sum(TestResults/executionTime) div 60)"/>m<xsl:value-of select="format-number((sum(TestResults/executionTime)) mod 60,'#')"/>s

              </td>

            </tr>

          </table>

        </div>

        <h2 style="font-size:420%">Test Results</h2>

        <table id="TestResultsTable" border="1">

            <tr bgcolor="#9acd32">

              <th width="25px">Sr.no.</th>

              <th width="50px">Suite</th>

              <th width="50px">Browser</th>

              <th width="450px">Test Case</th>              

              <th width="125px">Test Result</th>

              <th width="75px">Time</th>

            </tr>

            <xsl:for-each select="TestResults">

              <tr style='display:table-row'>

                <td>

                  <xsl:value-of select="1 + count(preceding-sibling::TestResults)" />

                </td>

                <td class="Test">

                  <xsl:value-of select="suiteName"/>

                </td>
                <td>
                  <xsl:value-of select="browserType" />
                </td>
                <td class="Test">

                  <a href='javascript:return 0;'>

                    <xsl:attribute name="onclick">

                      <xsl:text>showHideDiv('</xsl:text>
                    <xsl:value-of select="testCaseID" />-<xsl:value-of select="testCaseName"/>-<xsl:value-of select="suiteName"/>-<xsl:value-of select="browserType"/>
                    <xsl:text>')</xsl:text>

                    </xsl:attribute>

                    <xsl:value-of select="testCaseID"/>-<xsl:value-of select="testCaseName"/>-<xsl:value-of select="testCaseInputData"/>

                  </a>

                  <div style='display:none'>

                    <xsl:attribute name="id">

                      <xsl:value-of select="testCaseID" />-<xsl:value-of select="testCaseName"/>-<xsl:value-of select="suiteName"/>-<xsl:value-of select="browserType"/>

                    </xsl:attribute>

                    <pre>
                      <xsl:value-of select="testSteps"/>
                    </pre>

                  </div>

                </td>                
                <xsl:variable name="status" select="testCaseResult" />
                <xsl:choose>
                  <xsl:when test="$status='PASSED'">
                    <td class="Pass">Passed</td>
                  </xsl:when>
                  <xsl:when test="$status='FAILED'">
                    <td class="Fail">Failed</td>
                  </xsl:when>
                  <xsl:otherwise>
                    <td class="NoRun">NoRun</td>
                  </xsl:otherwise>
                </xsl:choose>

                <td>
                  <xsl:value-of select="floor(executionTime div 60)"/>m<xsl:value-of select="format-number(executionTime mod 60,'#')"/>s
                </td>

              </tr>
              
            </xsl:for-each>

          </table>
        
        <script type="text/javascript" src="https://www.google.com/jsapi">

          <xsl:text>document.write("hello")</xsl:text>

        </script>



        <script type="text/javascript">



          google.load("visualization", "1", {packages:["corechart"]});

          google.setOnLoadCallback(drawChart);



          function drawChart() {

          var pass=document.getElementById('Pass').innerText;


          var fail=document.getElementById('Fail').innerText;


          var blocked=document.getElementById('Blocked').innerText;


          var data = new google.visualization.DataTable();

          data.addColumn('string', 'Status');

          data.addColumn('number', 'Pass');

          data.addColumn('number', 'Fail');

          data.addColumn('number', 'Blocked');

          data.addRows(3);

          data.setValue(0, 0, 'Status');

          data.setValue(0, 1, parseInt(pass));

          data.setValue(0, 2, parseInt(fail));

          data.setValue(0, 3, parseInt(blocked));


          var chart = new google.visualization.ColumnChart(document.getElementById('chart_div'));

          chart.draw(data, {width: 400, height: 200, title: 'Test Results', legend:'bottom', hAxis: {title:'Status'},

          series: {0:{color: '4D8915', visibleInLegend: true}, 1:{color: 'FF9900', visibleInLegend: true}, 2:{color: 'C3D3B9', visibleInLegend: true}},

          vAxis: {title: 'No. of Tests', titleTextStyle: {color: 'black'}}});



          }



          function showHideDiv(divId)

          {

          var divstyle;divstyle = document.getElementById(divId).style.display;

          if(divstyle.toLowerCase()=='none')

          {document.getElementById(divId).style.display = 'block';}

          else{document.getElementById(divId).style.display = 'none';}

          }

          function showHideTable(divname)
          {
          
          var hideAll = 1;
          var result = divname.split("_",1);
          var module = divname.split("_");
          var rescount;
          var brwType;
          brwType = module[2];
          rescount = module[3];
        

          if(rescount != 0)
          {
          hideAll = 0;
          var tblObj = document.getElementById('TestResultsTable');

          var no_of_rows = tblObj.rows.length;

          
          var tempres,tempres1;
          if(result=='Pass')
          {
          result = 'Passed';
          tempres='Failed';
          tempres1 = 'No Run';
          }
          else if(result=='Fail')
          {
          result = 'Failed';
          tempres='Passed';
          tempres1 = 'No Run';
          }
          else
          {
          tempres = result;
          }
          
          <![CDATA[
          for(var i=1; i<no_of_rows;i++)
          {
          tblObj.rows[i].style.display = 'none';
          }
          for(var i=1; i<no_of_rows;i++)
          {               
                    
                    if(tblObj.rows[i].cells[1].innerHTML == module[1] && tblObj.rows[i].cells[4].innerHTML == result && tblObj.rows[i].cells[2].innerHTML == brwType)
                    {
                      tblObj.rows[i].style.display = '';
                    }               
                    else if(tblObj.rows[i].cells[1].innerHTML == module[1] && result == "All" && tblObj.rows[i].cells[2].innerHTML == brwType)
                    {                      
                      tblObj.rows[i].style.display = '';
                    }               
                    else if(result == "All" && module[1] == "Gen" && tblObj.rows[i].cells[2].innerHTML == brwType)
                    {                      
                      tblObj.rows[i].style.display = '';
                    }               
                    else if(module[1] == "Gen" && tblObj.rows[i].cells[4].innerHTML == result && tblObj.rows[i].cells[2].innerHTML == brwType)
                    { 
                      tblObj.rows[i].style.display = '';
                    }   
                    else if(module[1] == "Gen" && tblObj.rows[i].cells[4].innerHTML == result && brwType == "All")
                    {        
                      tblObj.rows[i].style.display = '';
                    }
                    else if(module[1] == "Gen" && result == "All" && brwType == "All")
                    {        
                      tblObj.rows[i].style.display = '';
                    }
                    else
                    {  
                      if(tblObj.rows[i].cells[4].innerHTML == tempres)
                        tblObj.rows[i].style.display = 'none';
                    }
			    }
          var count = 1;
         for(var k=0; k < no_of_rows; k++)
         {
           if(tblObj.rows[k].style.display != 'none' && k!=0)
           {
           tblObj.rows[k].cells[0].innerHTML = count;
           count ++;
           }
           
        }         
        var arrayOfDivs = document.getElementsByTagName('div');
       
        for(var i = 0; i < arrayOfDivs.length; i++) {        
        if(arrayOfDivs[i].style.display =='block')
        arrayOfDivs[i].style.display = 'none';
        }
        
      
      ]]>

          }
          else
          {
         
          var tblObj = document.getElementById('TestResultsTable');

          var no_of_rows = tblObj.rows.length;
          
          <![CDATA[
          if(hideAll == 1)
        {        
        for(var i=1; i<no_of_rows;i++)
          {                
          
            tblObj.rows[i].style.display = 'none';
          }
        }
          ]]>
       
          
          }
          }


        </script>

        <div id="chart_div" style="position:absolute; width: 360px; height: 180px; top: 100px; right: 25px;">

        </div>

      </body>

    </html>

  </xsl:template>
<xsl:template match="/ArrayOfReportSummary">
	<html>
        <head>
        <style>
		#GrandTotal {background-color:'#C3FDB8';color:black;font-family:Calibri; font-size:12px;}
          body {font-family:Trebuchet MS; font-size:10px;}
	     
          td {text-align:left;font-family:Calibri; font-size:12px;}
          th {background-color:#9abd32;color:white;font-family:Calibri;font-size:16px;}
          pre {border: 1px solid #000000;background-color:navajowhite;color:black;font-family:Calibri;
          valign:Center; padding: 5px 5px 5px 5px}
	  h1 {font-family:Calibri}
          pre {word-wrap:break-word}
        </style>
      </head>
      <body>
        <center>
          <h1>
            Test Execution History
          </h1>
        </center>
      </body>
      </html>
		<table border="1">
		<tr bgcolor="#9acd32">		
		<th>FolderName</th>
		<th>ReportName</th>
		</tr>
		 <xsl:for-each select="IndividualResults">
	      <tr>
	        <td><xsl:value-of select="FolderName"/></td>
	        <td>
	        <xsl:choose>
	        <xsl:when test="ReportExists=1">
	        	<xsl:element name="a">
			      <xsl:attribute name="href">
			         <xsl:value-of select="ReportLink"/>
			      </xsl:attribute>
			         <xsl:value-of select="ReportName"/>
			     </xsl:element>
			</xsl:when>
			<xsl:otherwise>
			<xsl:value-of select="ReportName"/>
			</xsl:otherwise>
			</xsl:choose>			
	        </td>
	      </tr>
	     </xsl:for-each>		
		</table>
	</xsl:template>

</xsl:stylesheet>
