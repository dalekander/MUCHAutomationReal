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
import com.kms.katalon.core.util.KeywordUtil

'click on menu assignment'
WebUI.click(findTestObject('Homepage/Page_Much application/much_ui_homepage_assignment_button'))

'set username login into visitor filter input'
WebUI.setText(findTestObject('Homepage/Page_Much application/much_ui_homepage_visitor_input'), GlobalVariable.username)

'click on search button'
WebUI.click(findTestObject('Assignment Management Page/Page_Much application/much_ui_assignmentpage_search_button'))

'extract the number of filtered visit case and store it in assignedBefore variable'
//String assignedBefore = WebUI.getText(findTestObject('Object Repository/Assignment Management Page/Page_Much application/Filtered Button')).replaceAll("[^0-9.]", "")
String assignedBefore = WebUI.getText(findTestObject('Object Repository/Assignment Management Page/Page_Much application/much_ui_assignmentpage_filtered_number')).replaceAll('[^0-9.]', '')

println ('assigned before: ' + assignedBefore)

'set blank text into visitor filter input / clear the visitor filter input'
WebUI.setText(findTestObject('Homepage/Page_Much application/much_ui_homepage_visitor_input'), "")

'click on visit case type filter'
WebUI.click(findTestObject('Homepage/Page_Much application/much_ui_homepage_visitcasetype_filter'))

'chose EFC on visit case type filter'
WebUI.click(findTestObject('Homepage/Page_Much application/much_ui_homepage_visitcasetype_filter_EFC'))

'click on middle screen to remove the visit case type choice list'
WebUI.click(findTestObject('Assignment Management Page/Page_Much application/much_ui_assignmentpage_middlescreen'))

'click on visit case state filter'
WebUI.click(findTestObject('Homepage/Page_Much application/much_ui_homepage_visitcasestate_filter'))

'chose assigned on visit case state filter'
WebUI.click(findTestObject('Homepage/Page_Much application/much_ui_homepage_visitcasestate_filter_Unassigned'))

'click on middle screen to remove the visit case state choice list'
WebUI.click(findTestObject('Assignment Management Page/Page_Much application/much_ui_assignmentpage_middlescreen'))

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

'click on the first row of checkbox'
rows_table1.get(0).findElements(By.tagName('td')).get(0).click()
GlobalVariable.selectedVisitCaseID_MA = rows_table1.get(0).findElements(By.tagName('td')).get(14).getText()
GlobalVariable.selectedContracNo_MA = rows_table1.get(0).findElements(By.tagName('td')).get(2).getText()
GlobalVariable.selectedCreationDate_MA = rows_table1.get(0).findElements(By.tagName('td')).get(8).getText()

'click on selected button'
WebUI.click(findTestObject('Assignment Management Page/Page_Much application/much_ui_assignmentpage_selected_button'))

boolean found = false

int counter = 0

'looping through visitor list and check if visitor with username login is found, if not, click on next page, loop this maximum 5 times, if more than 5 times, means the visitor does not found'
while ((found == false) || (counter < 5)) {
    WebDriver driver = DriverFactory.getWebDriver()

    'Expected value from Table'
    String ExpectedValue = GlobalVariable.username

    'To locate table'
    WebElement Table = driver.findElement(By.xpath('//table/tbody'))

    'To locate rows of table it will Capture all the rows available in the table'
    List<WebElement> rows_table = Table.findElements(By.tagName('tr'))

    'To calculate no of rows In table'
    int rows_count = rows_table.size()

    'Loop will execute for all the rows of the table'
    Loop: for (int row = 0; row < rows_count; row++) {
        'To locate columns(cells) of that specific row'
        List<WebElement> Columns_row = rows_table.get(row).findElements(By.tagName('td'))

        'To calculate no of columns(cells) In that specific row'
        int columns_count = Columns_row.size()

        println((('Number of cells In Row ' + row) + ' are ') + columns_count)

        'Loop will execute till the last cell of that specific row'
        for (int column = 0; column < columns_count; column++) {
            'It will retrieve text from each cell'
            String celltext = Columns_row.get(column).getText()

            println((((('Cell Value Of row number ' + row) + ' and column number ') + column) + ' Is ') + celltext)

            'Checking if Cell text is matching with the expected value'
            if (celltext == ExpectedValue) {
                'Getting the Country Name if cell text i.e Company name matches with Expected value'
                println('Text present in row number 3 is: ' + Columns_row.get(column).getText())

                Columns_row.get(0).click()

                'After getting the Expected value from Table we will Terminate the loop'
                found = true

                Loop: break
            }
        }
    }
    
    if (found == false) {
        WebUI.click(findTestObject('Object Repository/Assignment Management Page/Page_Much application/much_ui_assignmentpage_nextpage_filter_button'))
    } else {
        break
    }
    
    counter = (counter + 1)
}

'if visitor with username login is checked'
if (found == true) {
	
	'click on selected button'
    WebUI.click(findTestObject('Assignment Management Page/Page_Much application/much_ui_assignmentpage_selected_button'))
	
	'click on confirm button and reassignment is finish'
    WebUI.click(findTestObject('Object Repository/Assignment Management Page/Page_Much application/much_ui_assignmentpage_confirmallsuggestoin_button'))
	
	'now we check the number of visit case assigned after assignment'
	'set the username login into visitor filter'
	WebUI.setText(findTestObject('Homepage/Page_Much application/much_ui_homepage_visitor_input'), GlobalVariable.username)
	
	'click on search button'
	WebUI.click(findTestObject('Assignment Management Page/Page_Much application/much_ui_assignmentpage_search_button'))
	
	'extract the text inside filtered button and put it in assginedAfter variable'
	//String assignedAfter = WebUI.getText(findTestObject('Object Repository/Assignment Management Page/Page_Much application/Filtered Button')).replaceAll("[^0-9.]", "")
	String assignedAfter = WebUI.getText(findTestObject('Object Repository/Assignment Management Page/Page_Much application/much_ui_assignmentpage_filtered_number')).replaceAll('[^0-9.]', '')
	
	println ('assigned after: ' + assignedAfter)
	
	'calculate the difference between assignedBefore and assignedAfter'
	int assignedDif = 0
	assignedDif = Integer.parseInt(assignedAfter) - Integer.parseInt(assignedBefore)
	
	'if the differece is increase by 1 then it is the expected result, if not then it is not'
	if( assignedDif  == 1)
	{
		println('TESTING SUCCESFULL -  Number of assigned visit case to ' + GlobalVariable.username + ' increased by ' + assignedDif)
		
	}
	else
	{
		println('TESTING UNSUCCESSFULL - Number of assigned visit case to ' + GlobalVariable.username + ' is not increased by 1')
		KeywordUtil.markFailedAndStop('TESTING UNSUCCESSFULL - Number of assigned visit case to ' + GlobalVariable.username + ' is not increased by 1')
	}
}




