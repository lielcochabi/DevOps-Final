import pytest
import logging
import re
from playwright.sync_api import Page, expect

# Configuring a test steps logger
logging.basicConfig(
    level=logging.INFO,
    format='%(asctime)s - %(levelname)s - %(message)s',
    datefmt='%Y-%m-%d %H:%M:%S'
)
logger = logging.getLogger(__name__)

BASE_URL = "http://84.13.82.200:8080/DevOps_Liel_Almog_Almog_Stav_Meshi/index.jsp"

def test_visit_repo_link(page: Page):
    """ Verify the link redirection to the DevOps project repo in a new tab"""
    logger.info(f"Navigating to {BASE_URL}")
    page.goto(BASE_URL)
    
    logger.info("Locating the GitHub Repo link")
    repo_link = page.get_by_role("link", name="Visit our GitHub repository")
    
    logger.info("Verifying link attribute")
    expect(repo_link).to_have_attribute("href", "https://github.com/lielcochabi/DevOps-Final")
    
    logger.info("Clicking the link and waiting for the new tab to open")
    with page.expect_popup() as new_repo_tab:
        repo_link.click()
        
    new_tab = new_repo_tab.value
    logger.info("New tab detected, verifying the GitHub URL")
    expect(new_tab).to_have_url(re.compile(r"github\.com/lielcochabi/DevOps-Final"))


def test_empty_submission(page: Page):
    """ Checking an empty submission flow, expecting no navigation + a value missing message"""
    logger.info(f"Navigating to {BASE_URL}")
    page.goto(BASE_URL)
    
    name_input = page.get_by_role("textbox", name="Please enter your name:")
    
    logger.info("Verifying the text box is currently empty")
    expect(name_input).to_be_empty()
    
    logger.info("Clicking Submit with an empty text box")
    page.get_by_role("button", name="Submit").click()
    
    logger.info("Verifying no navigation occurred")
    expect(page).to_have_url(BASE_URL)
    
    logger.info("Verifying the input is flagged as missing/empty")
    is_invalid = name_input.evaluate("el => el.validity.valueMissing")
    assert is_invalid, "Expected the input to be flagged as missing/empty"
    
    logger.info("Verifying the text of the validation popup")
    tooltip_text = name_input.evaluate("el => el.validationMessage")
    assert "Please fill out this field" in tooltip_text, f"Expected popup text not found. Actual text was: '{tooltip_text}'"
  

def test_unsupported_chars(page: Page):
    """ Checking an unsupported chars submission flow, expecting no input to be written"""
    logger.info(f"Navigating to {BASE_URL}")
    page.goto(BASE_URL)
    
    logger.info("Filling text box with unsupported characters")
    name_input = page.get_by_role("textbox", name="Please enter your name:")
    name_input.fill("!סתיו")

    logger.info("Verifying the text box is empty")
    expect(name_input).to_be_empty()
    
    logger.info("Clicking Submit")
    page.get_by_role("button", name="Submit").click()
    
    logger.info("Verifying no navigation occurred")
    expect(page).to_have_url(BASE_URL)


def test_valid_submission(page: Page):
    """Verify a valid submission navigation and back to home redirection"""
    logger.info(f"Navigating to {BASE_URL}")
    page.goto(BASE_URL)
    
    input_name = "Stav"
    logger.info(f"Filling text box with valid name: {input_name}")
    page.get_by_role("textbox", name="Please enter your name:").fill(input_name)
    
    logger.info("Clicking Submit")
    page.get_by_role("button", name="Submit").click()
    
    logger.info("Searching the new page for the greeting text")
    expect(page.get_by_text(f"Hello, {input_name}!")).to_be_visible()

    logger.info("Locating and clicking the 'Back' link")
    page.get_by_role("link", name="Back to home").click()
    
    logger.info("Verifying redirection back to page")
    expect(page).to_have_url(re.compile("index.jsp"))
  
