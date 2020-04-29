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
import org.openqa.selenium.By.ByLinkText
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import java.io.FileInputStream as FileInputStream
import java.io.FileNotFoundException as FileNotFoundException
import java.io.IOException as IOException
import java.util.Date as Date
import org.apache.poi.xssf.usermodel.XSSFCell as XSSFCell
import org.apache.poi.xssf.usermodel.XSSFRow as XSSFRow
import org.apache.poi.xssf.usermodel.XSSFSheet as XSSFSheet
import org.apache.poi.xssf.usermodel.XSSFWorkbook as XSSFWorkbook
import java.lang.String as String
import java.awt.Desktop as Desktop
import java.io.File as File
import java.nio.file.*

'click on menu assignment testing'
WebUI.click(findTestObject('Homepage/Page_Much application/much_ui_homepage_assignment_button'))

'set username login into visitor filter input'
WebUI.setText(findTestObject('Homepage/Page_Much application/much_ui_homepage_visitor_input'), GlobalVariable.username)

'click on search button'
WebUI.click(findTestObject('Assignment Management Page/Page_Much application/much_ui_assignmentpage_search_button'))

'extract the number of filtered visit case and store it in assignedBefore variable'
//String assignedBefore = WebUI.getText(findTestObject('Object Repository/Assignment Management Page/Page_Much application/Filtered Button')).replaceAll('[^0-9.]', '')
String assignedBefore = WebUI.getText(findTestObject('Object Repository/Assignment Management Page/Page_Much application/much_ui_assignmentpage_filtered_number')).replaceAll('[^0-9.]', '')

println('assigned before: ' + assignedBefore)

'click on menu button'
WebUI.click(findTestObject('Homepage/Page_Much application/much_ui_homepage_menu_button'))

'click on menu import export'
WebUI.click(findTestObject('Object Repository/Homepage/Page_Much application/much_ui_homepage_import_export_button'))

try {
	'try to delete if file exist'
	Files.deleteIfExists(Paths.get('C:\\Users\\daniel.alekander\\Downloads\\' + GlobalVariable.ImportFileName))
}
catch (NoSuchFileException e) {
	System.out.println('No such File/Directory Exists')
}

'load driver'
WebDriver driver = DriverFactory.getWebDriver()

'Expected value from Table'
String ExpectedValue = GlobalVariable.ExportType

'To locate table'
WebElement Table = driver.findElement(By.xpath('//table[@color="default"]/tbody'))

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
			println('Text present in row number 1 is: ' + Columns_row.get(column).getText())

			'click on generate file button'
			Columns_row.get(3).findElement(By.id('generateFileButton')).click()

			'if generate file button still disabled, keep clicking on refresh button'
			while (Columns_row.get(3).findElement(By.id('generateFileButton')).isEnabled() == false) {
				Columns_row.get(3).findElement(By.xpath('//button[@aria-label=\'Refresh\']')).click()
			}

			'after that click on download file button'
			Columns_row.get(3).findElement(By.id('downloadFileButton')).click()

			'After getting the Expected value from Table we will Terminate the loop'
			found = true

			break
		}
	}
}

'open the downloaded file'
FileInputStream file = new FileInputStream(new File('C:\\Users\\daniel.alekander\\Downloads\\' + GlobalVariable.ImportFileName))

'open the workbook'
XSSFWorkbook workbook = new XSSFWorkbook(file)

'open first sheet'
XSSFSheet sheet = workbook.getSheetAt(0)

'Read data from excel'
//String Data_fromCell = sheet.getRow(0).getCell(1).getStringCellValue()

'write the assigned username to excel in the first row'
sheet.getRow(1).createCell(4).setCellValue(GlobalVariable.username)

String selectedVisitCaseID_EI = sheet.getRow(1).getCell(0).getRawValue()
String selectedContracNo_EI = sheet.getRow(1).getCell(35).getRawValue()


'get all the filled row from excel'
int rowLimit = sheet.getPhysicalNumberOfRows();

'loop through the excel and remove all the row expect the first row (the one that assigned with username)'
for (int rowCounter = 2; rowCounter < rowLimit; rowCounter++)
{
	sheet.removeRow(sheet.getRow(rowCounter));
}

'close the file'
file.close()

'save the file'
FileOutputStream outFile = new FileOutputStream(new File('C:\\Users\\daniel.alekander\\Downloads\\' + GlobalVariable.ImportFileName))

workbook.write(outFile)

outFile.close()


'click on assignment_import_tab'
WebUI.click(findTestObject('Object Repository/Import Export Page/Page_Much application/much_ui_importexport_import_assignment_tab'))

'click on input upload file'
WebUI.uploadFile(findTestObject('Object Repository/Import Export Page/Page_Much application/much_ui_importexport_fileinput_button'),
		'C:\\Users\\daniel.alekander\\Downloads\\' + GlobalVariable.ImportFileName)

'click on assignment_import_button'
WebUI.click(findTestObject('Object Repository/Import Export Page/Page_Much application/much_ui_importexport_assignment_import_button'))

'MODIFY JOB DETAILS'
WebDriver driver2 = DriverFactory.getWebDriver()

'To locate table'
WebElement Table2 = driver2.findElement(By.xpath('//table[@id="jobQueueTable"]/tbody'))

'To locate rows of table it will Capture all the rows available in the table'
List<WebElement> rows_table2 = Table2.findElements(By.tagName('tr'))

'load table td'
List<WebElement> Columns_row2 = rows_table2.get(0).findElements(By.tagName('td'))

//while (Columns_row2.get(6).findElement(By.text('Details')).isEnabled() == false) {


'loop through detail disabled button, if it still exist, keep click on refresh button'
while (WebUI.verifyElementNotClickable(findTestObject('Import Export Page/Page_Much application/much_ui_exportimport_button_details_1'),FailureHandling.OPTIONAL)) {
	'click on refresh button'
	WebUI.click(findTestObject('Import Export Page/Page_Much application/much_ui_exportimport_button_refresh_1_jobqueue'))
}

/*
 'loop through detail disabled button, if it still exist, keep click on refresh button'
 while (WebUI.verifyElementPresent(findTestObject('Import Export Page/Page_Much application/button_details_disabled'),1,FailureHandling.OPTIONAL)) {
 'click on refresh button'
 WebUI.click(findTestObject('Import Export Page/Page_Much application/much_ui_exportimport_button_refresh_1_jobqueue'))
 }
 */

/* CODE SALAH
 while (WebUI.verifyElementNotClickable(findTestObject('Import Export Page/Page_Much application/much_ui_exportimport_button_details_1')) == true) {
 println(WebUI.verifyElementNotClickable(findTestObject('Import Export Page/Page_Much application/much_ui_exportimport_button_details_1')))
 'click on refresh button'
 WebUI.click(findTestObject('Import Export Page/Page_Much application/much_ui_exportimport_button_refresh_1_jobqueue'))
 //println(Columns_row2.get(6).findElement(By.linkText('Details')).isEnabled())
 }
 */

'click on refresh button'
//WebUI.click(findTestObject('Import Export Page/Page_Much application/much_ui_exportimport_button_refresh_1_jobqueue'))
//}
'click on first button details'
//Columns_row2.get(6).findElement(By.xpath("//td/button/span[@class='MuiButton-label' and text()='Details'][1]")).click()
WebUI.click(findTestObject('Import Export Page/Page_Much application/much_ui_exportimport_button_details_1'))

'click on confirm button'
WebUI.click(findTestObject('Import Export Page/Page_Much application/much_ui_importexport_success_confirm_button'))

'wait for 20 second'
WebUI.delay(20)

'click on refresh button'
WebUI.click(findTestObject('Import Export Page/Page_Much application/much_ui_exportimport_button_refresh_1_jobqueue'))

'click on menu button'
WebUI.click(findTestObject('Homepage/Page_Much application/much_ui_homepage_menu_button'))

'click on menu assignment'
WebUI.click(findTestObject('Homepage/Page_Much application/much_ui_homepage_assignment_button'))

'set username login into visitor filter input'
WebUI.setText(findTestObject('Homepage/Page_Much application/much_ui_homepage_visitor_input'), GlobalVariable.username)

'click on search button'
WebUI.click(findTestObject('Assignment Management Page/Page_Much application/much_ui_assignmentpage_search_button'))

'extract the number of filtered visit case and store it in assignedBefore variable'
String assignedAfter = WebUI.getText(findTestObject('Object Repository/Assignment Management Page/Page_Much application/much_ui_assignmentpage_filtered_number')).replaceAll('[^0-9.]', '')

println('assigned before: ' + assignedBefore)
println('assigned after: ' + assignedAfter)

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




