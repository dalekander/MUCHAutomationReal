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
import com.kms.katalon.core.testobject.ConditionType as ConditionType
import java.util.Date


'click on menu visit overview'
WebUI.click(findTestObject('Object Repository/Homepage/Page_Much application/much_ui_homepage_visit_overview_button'))

'click on visit case type filter'
WebUI.click(findTestObject('Homepage/Page_Much application/much_ui_homepage_visitcasetype_filter'))

'chose EFC on visit case type filter'
WebUI.click(findTestObject('Homepage/Page_Much application/much_ui_homepage_visitcasetype_filter_LFC'))

'click on middle screen to remove the visit case type choice list'
WebUI.click(findTestObject('Object Repository/Homepage/Page_1580376899608/much_ui_homepage_body_content'))

'click on search button'
WebUI.click(findTestObject('Assignment Management Page/Page_Much application/much_ui_assignmentpage_search_button'))

'click on creation date sort asc'
WebUI.click(findTestObject('Assignment Management Page/Page_Much application/much_ui_assignmentpage_creation_date_sort_asc'))

'click on creation date sort desc'
WebUI.click(findTestObject('Assignment Management Page/Page_Much application/much_ui_assignmentpage_creation_date_sort_desc'))

'load driver for table reading'
WebDriver driver1 = DriverFactory.getWebDriver()

'To locate table'
WebElement Table1 = driver1.findElement(By.xpath('//table/tbody'))

'To locate rows of table it will Capture all the rows available in the table'
List<WebElement> rows_table1 = Table1.findElements(By.tagName('tr'))

'get the visit id'
String visit_id = rows_table1.get(0).findElements(By.tagName('td')).get(1).getText()

'get the contract no'
String contract_no = rows_table1.get(0).findElements(By.tagName('td')).get(16).getText()

'get the dpd'
String dpd = rows_table1.get(0).findElements(By.tagName('td')).get(15).getText()

'get the debt amount'
String debt_amount = rows_table1.get(0).findElements(By.tagName('td')).get(14).getText()

'get the creation date'
String creation_date = rows_table1.get(0).findElements(By.tagName('td')).get(6).getText()

'save current web page url'
String currentPage = WebUI.getUrl()
	
'store current index tab'
int currentTab = WebUI.getWindowIndex()
	
'open new tab in browser'
WebUI.executeJavaScript('window.open();', [])

'switch to new tab'
WebUI.switchToWindowIndex(currentTab+1)
	
'go to BSL URL'
WebUI.navigateToUrl(GlobalVariable.BSL_URL)

'click on create incoming payment'
WebUI.click(findTestObject('Object Repository/BSL Page/Page_Homer Select/bsl_ui_homepage_createincomingpayment'))

'click on repayment channel dropdown'
WebUI.click(findTestObject('Object Repository/BSL Page/Page_Homer Select/bsl_ui_incomingpayment_repaymentchannel'))

'select bca repayment channel option'
WebUI.click(findTestObject('Object Repository/BSL Page/Page_Homer Select/bsl_ui_incomingpayment_repaymentchannel_bca'))

'fill in amount of payment'
WebUI.setText(findTestObject('Object Repository/BSL Page/Page_Homer Select/bsl_ui_incomingpayment_amountofpayment'), debt_amount)

'fill in contract no'
WebUI.setText(findTestObject('Object Repository/BSL Page/Page_Homer Select/bsl_ui_incomingpayment_providedcontractnumber'), contract_no)

today = new Date()
String requestDate = today.format('dd/MM/yyyy');
println(requestDate);

'fill in transaction date'
WebUI.setText(findTestObject('Object Repository/BSL Page/Page_Homer Select/bsl_ui_incomingpayment_transactiondate'), requestDate)

'fill in deposit date'
WebUI.setText(findTestObject('Object Repository/BSL Page/Page_Homer Select/bsl_ui_incomingpayment_depositdate'), requestDate)

'click submit button'
WebUI.click(findTestObject('Object Repository/BSL Page/Page_Homer Select/bsl_ui_incomingpayment_submitpayment'))

'switch back to much tab'
WebUI.switchToWindowIndex(currentTab)

'put the visit case that was stored in selectedVisitCaseID into visit case id filter input'
WebUI.setText(findTestObject('Homepage/Page_Much application/much_ui_homepage_visitcaseid_input'), visit_id)

String noData = 'empty'

'wait for 1.5 hour'
WebUI.delay(5400)

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
	println('TESTING SUCCESFULL - Visit ID: ' + visit_id + ' has removed from much web')
	println(visit_id)
	println(contract_no)
	println(dpd)
}
else
{
	println('TESTING UNSUCCESFULL - Visit ID: ' + visit_id + ' has not removed from much web')
	KeywordUtil.markFailedAndStop('TESTING UNSUCCESFULL - Visit ID: ' + visit_id + ' has not removed from much web')
	println(visit_id)
	println(contract_no)
	println(dpd)
}

//WebUI.switchToWindowIndex(currentTab)
	
//WebUI.navigateToUrl(currentPage)
	
//WebUI.switchToWindowIndex(currentTab+1)

