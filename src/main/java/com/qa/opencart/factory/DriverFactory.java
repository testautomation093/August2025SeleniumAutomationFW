package com.qa.opencart.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

import com.qa.opencart.exceptions.FrameworkException;
import com.qa.opencart.utils.AppError;

public class DriverFactory {

	public WebDriver driver;

	public Properties prop;
	
	public static ThreadLocal<WebDriver> tlDriver=new ThreadLocal<WebDriver>();
	
	private static final Logger log=LogManager.getLogger(DriverFactory.class);
	
	public OptionsManager optionsManager;
	
	public WebDriver initDriver(Properties prop) {
		
		String browser=prop.getProperty("browser");
		
		optionsManager=new OptionsManager(prop);
		
		//System.out.println("Browser Name is : " + browser);
		log.info("Browser Name is : " + browser);

		switch (browser.trim().toLowerCase()) {
		case "chrome":

			//driver = new ChromeDriver();

			tlDriver.set(new ChromeDriver(optionsManager.getChromeOptions()));
			
			break;

		case "firefox":

			//driver = new EdgeDriver();
            tlDriver.set(new FirefoxDriver(optionsManager.getFirefoxOptions()));
			break;

		case "edge":

			//driver = new FirefoxDriver();

			tlDriver.set(new EdgeDriver(optionsManager.getEdgeOptions()));
			break;

		case "safari":

			//driver = new SafariDriver();

			tlDriver.set(new SafariDriver());
			break;

		default:
			
			//System.out.println(AppError.INVALID_BROWSER_NAME);
			log.warn(AppError.INVALID_BROWSER_NAME);
			
			throw new FrameworkException("===Invalid Browser===");
		}

		getDriver().manage().deleteAllCookies();
		getDriver().manage().window().maximize();
	
		getDriver().get(prop.getProperty("url"));
	
		return getDriver();
	}
	
	
	public Properties initProp()
	{
		//mvn clean install -Denv="qa";
		prop=new Properties();
		FileInputStream fp=null;
		
		String envName=System.getProperty("env");
		
		try {
			
			if(envName==null)
			{
			log.info("No Env is given hence running the test case on default env : "+ envName);	
			fp= new FileInputStream("src/test/resources/config/config.properties");
			}
			
			else
			{
				
			switch (envName.toLowerCase().trim()) {
			case "dev":
				log.info("Env is given hence running the test case on : "+ envName +" env");	
				fp= new FileInputStream("src/test/resources/config/config_dev.properties");	
				
				break;
				
			case "uat":
				log.info("Env is given hence running the test case on : "+ envName +" env");	
				fp= new FileInputStream("src/test/resources/config/config_uat.properties");	
				
				break;	
				
			case "prod":
				log.info("Env is given hence running the test case on : "+ envName +" env");	
				fp= new FileInputStream("src/test/resources/config/config_prod.properties");	
				
				break;	

			default:
				
			log.error("Wrong Env Name is Passed : "+ envName);
			
			throw new FrameworkException("===INVALID ENV PASSED===");
			
			}	
				
			}
	
			prop.load(fp);
	
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return prop;
		
	}
	
	
	public static WebDriver getDriver()
	{
		return tlDriver.get();
		
	}
	
	public static File getScreenshotAsFile()
	{
		
		
		File file=((TakesScreenshot)getDriver()).getScreenshotAs(OutputType.FILE);
		
		return file;
		
	}
	

}
