import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys
import org.openqa.selenium.By as By
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory

'click on menu visit overview'
WebUI.click(findTestObject('Object Repository/Homepage/Page_Much application/much_ui_homepage_visit_overview_button'))

'set username login into visitor filter input'
WebUI.setText(findTestObject('Homepage/Page_Much application/much_ui_homepage_visitor_input'), GlobalVariable.username)

'click on search button'
WebUI.click(findTestObject('Assignment Management Page/Page_Much application/much_ui_assignmentpage_search_button'))

'click on creation date sort asc'
WebUI.click(findTestObject('Assignment Management Page/Page_Much application/much_ui_assignmentpage_creation_date_sort_asc'))

'click on creation date sort desc'
WebUI.click(findTestObject('Assignment Management Page/Page_Much application/much_ui_assignmentpage_creation_date_sort_desc'))

'click on special visit result button'
WebUI.click(findTestObject('Object Repository/Visit Overview/Page_Much application/much_ui_visitoverview_special_visit_result_button'))

'load driver to read from table'
WebDriver driver1 = DriverFactory.getWebDriver()

'To locate table'
WebElement Table1 = driver1.findElement(By.xpath('//table/tbody'))

'To locate rows of table it will Capture all the rows available in the table'
List<WebElement> rows_table1 = Table1.findElements(By.tagName('tr'))

'check on the first row'
rows_table1.get(0).findElements(By.tagName('td')).get(0).click()

'extract data from the third column (visit case id) and store it in selectedVisitCaseID'
GlobalVariable.selectedVisitCaseID_SVR = rows_table1.get(0).findElements(By.tagName('td')).get(3).getText()
GlobalVariable.selectedContracNo_SVR = rows_table1.get(0).findElements(By.tagName('td')).get(18).getText()
GlobalVariable.selectedCreationDate_SVR = rows_table1.get(0).findElements(By.tagName('td')).get(8).getText()

'click on selected button'
WebUI.click(findTestObject('Assignment Management Page/Page_Much application/much_ui_assignmentpage_selected_button'))

'put universal comment variable into universal comment text area'
WebUI.setText(findTestObject('Object Repository/Visit Overview/Page_Much application/much_ui_visitoverview_universalcomment_textarea'), GlobalVariable.specialvisitresult_testing_comment)

'click on continue button'
WebUI.click(findTestObject('Object Repository/Visit Overview/Page_Much application/much_ui_visitoverview_continue_button'))

'click on special visit dropdown'
WebUI.click(findTestObject('Object Repository/Visit Overview/Page_Much application/much_ui_visitoverview_specialvisitresult_dropdown'))

'chose batal penugasan option on that dropdown'
WebUI.click(findTestObject('Object Repository/Visit Overview/Page_Much application/much_ui_visitoverview_specialvisitresult_dropdown_batalpenugasan'))

'click on confirm approval button'
WebUI.click(findTestObject('Object Repository/Visit Overview/Page_Much application/much_ui_visitoverview_confirmapproval_button'))

'clear the visitor input filter'
WebUI.setText(findTestObject('Homepage/Page_Much application/much_ui_homepage_visitor_input'), '')

'put the visit case that was stored in selectedVisitCaseID into visit case id filter input'
WebUI.setText(findTestObject('Homepage/Page_Much application/much_ui_homepage_visitcaseid_input'), GlobalVariable.selectedVisitCaseID_SVR)

String noData = 'empty'

'wait for 6 minutes'
WebUI.delay(360)

int counter = 0

'Search visit case which has visit case id from selectedVisitCaseID, and loop it until it return result No data..., because the correct behavior is that visit case removed from searching. Put 30 repetition maximum, so after more than 30 search still found the visit case, we will consider it failure test'
while((noData != 'No data...') && (counter <= 30))
{
	WebUI.click(findTestObject('Assignment Management Page/Page_Much application/much_ui_assignmentpage_search_button'))
	
	WebDriver driver2 = DriverFactory.getWebDriver()
	
	'To locate table'
	WebElement Table2 = driver1.findElement(By.xpath('//table/tbody'))
	
	'To locate rows of table it will Capture all the rows available in the table'
	List<WebElement> rows_table2 = Table2.findElements(By.tagName('tr'))
	
	noData = rows_table2.get(0).findElements(By.tagName('td')).get(0).getText()
	println('no data: ' + noData)
	
	counter = (counter + 1)
	
	println('counter: ' + counter);
}

'if visit case removed from searching, then put succesfull result, if not put unsucesfull result.'
if(counter <= 30 && noData == 'No data...')
{
	println('TESTING SUCCESFULL - Visit ID: ' + GlobalVariable.selectedVisitCaseID_SVR + ' has removed from much web')
}
else
{
	println('TESTING UNSUCCESFULL - Visit ID: ' + GlobalVariable.selectedVisitCaseID_SVR + ' has not removed from much web')
	KeywordUtil.markFailedAndStop('TESTING UNSUCCESFULL - Visit ID: ' + GlobalVariable.selectedVisitCaseID_SVR + ' has not removed from much web')
}



