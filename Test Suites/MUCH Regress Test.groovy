import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.checkpoint.CheckpointFactory as CheckpointFactory
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testcase.TestCaseFactory as TestCaseFactory
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testdata.TestDataFactory as TestDataFactory
import com.kms.katalon.core.testobject.ObjectRepository as ObjectRepository
import com.kms.katalon.core.testobject.TestObject as TestObject

import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile

import internal.GlobalVariable as GlobalVariable

import com.kms.katalon.core.annotation.SetUp
import com.kms.katalon.core.annotation.SetupTestCase
import com.kms.katalon.core.annotation.TearDown
import com.kms.katalon.core.annotation.TearDownTestCase

/**
 * Some methods below are samples for using SetUp/TearDown in a test suite.
 */

/**
 * Setup test suite environment.
 */
@SetUp(skipped = true) // Please change skipped to be false to activate this method.
def setUp() {
	// Put your code here.
}

/**
 * Clean test suites environment.
 */
@TearDown(skipped = true) // Please change skipped to be false to activate this method.
def tearDown() {
	// Put your code here.
}

/**
 * Run before each test case starts.
 */
@SetupTestCase(skipped = false) // Please change skipped to be false to activate this method.
def setupTestCase() {

	WebUI.openBrowser(GlobalVariable.URL)
	
	WebUI.setText(findTestObject('Login Page/Page_Log in to HCI/much_ui_login_page_username'), GlobalVariable.username)
	
	WebUI.setText(findTestObject('Login Page/Page_Log in to HCI/much_ui_login_page_password'), GlobalVariable.password)
	
	WebUI.click(findTestObject('Login Page/Page_Log in to HCI/much_ui_login_page_button'))
	
	if (WebUI.verifyElementPresent(findTestObject('Object Repository/Login Page/Page_Log in to HCI/h1_Your password is going to expire'),
		1, FailureHandling.OPTIONAL)) {
		WebUI.click(findTestObject('Login Page/Page_Log in to HCI/much_ui_login_page_continue'))
	
		WebUI.click(findTestObject('Homepage/Page_Much application/much_ui_homepage_menu_button'))
	} else {
		WebUI.click(findTestObject('Homepage/Page_Much application/much_ui_homepage_menu_button'))
	}
	
}

/**
 * Run after each test case ends.
 */
@TearDownTestCase(skipped = false) // Please change skipped to be false to activate this method.
def tearDownTestCase() {
	

	'PRINT EXPORT IMPORT TESTING RESULT'
	println('PRINT EXPORT IMPORT TESTING RESULT')
	println('Contract No selected:' + GlobalVariable.selectedContracNo_EI)
	println('Visit case Id:' + GlobalVariable.selectedVisitCaseID_EI)
	
	'PRINT MANUAL ASSIGNMENT TESTING RESULT'
	println('PRINT MANUAL ASSIGNMENT TESTING RESULT')
	println('Contract No selected:' + GlobalVariable.selectedContracNo_MA)
	println('Visit case Id:' + GlobalVariable.selectedVisitCaseID_MA)
	println('Creation Date:' + GlobalVariable.selectedCreationDate_MA)
	
	'PRINT REASSIGNMENT TESTING RESULT'
	println('PRINT REASSIGNMENT TESTING RESULT')
	println('Contract No selected:' + GlobalVariable.selectedContracNo_RA)
	println('Visit case Id:' + GlobalVariable.selectedVisitCaseID_RA)
	println('Creation Date:' + GlobalVariable.selectedCreationDate_RA)
	
	'PRINT SPECIAL VISIT RESULT TESTING RESULT'
	println('PRINT SPECIAL VISIT RESULT TESTING RESULT')
	println('Visit Case ID: ' + GlobalVariable.selectedVisitCaseID_SVR)
	println('Contract ID: ' + GlobalVariable.selectedContracNo_SVR)
	println('Creation Date: ' + GlobalVariable.selectedCreationDate_SVR)
	
	'PRINT PAYMENT TESTING RESULT'
	println('PRINT PAYMENT TESTING RESULT')
	println('Visit Case ID: ' + GlobalVariable.visit_id)
	println('Contract ID: ' + GlobalVariable.contract_no)
	println('Creation Date: ' + GlobalVariable.creation_date)

	WebUI.delay(3)
	
	WebUI.closeBrowser()
	
}

/**
 * References:
 * Groovy tutorial page: http://docs.groovy-lang.org/next/html/documentation/
 */