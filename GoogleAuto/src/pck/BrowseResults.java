package pck;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.property.Parent;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Options;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.ProfilesIni;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.Command;
import org.openqa.selenium.remote.CommandExecutor;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.HttpCommandExecutor;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.Response;
import org.openqa.selenium.remote.SessionId;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.openqa.selenium.remote.http.W3CHttpCommandCodec;
import org.openqa.selenium.remote.http.W3CHttpResponseCodec;

import com.sun.jna.*;
import com.sun.jna.platform.win32.ShellAPI;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.win32.*;


class BrowseResults {

	RemoteWebDriver driver;
	static DesiredCapabilities descap;
	
	ProfilesIni profini;
	FirefoxProfile prof;
	FirefoxOptions opts;
	Actions act; 
	JavascriptExecutor jsx;
	
	
	String sfilename = "lastActiveSession.txt";
	String uafilename = "useragentlistAndroid.txt";
	String htmlfilename = "lastHtml.html";
	boolean loadPrevSession = false;
	String link = "https://www.google.com";
	String query = "osta rehvid";
	int stayTime=6666;
	
	
	String[] activedriver = new String[3];
	String[] useragentlist = new String[10000];
	ArrayList <String>cmdLines = new ArrayList();
	String currentHtml;
	String command;
	String cmdGetCon="netsh interface show interface";
	WebElement mSearchBar;
	
	@Test
	void test() throws IOException 
	{
		
		
		//Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();
       // for (NetworkInterface netint : Collections.list(nets))
          //  displayInterfaceInformation(netint);
		
		
		
		//useragentlist = importfile(uafilename);
		//setUserAgent();
		//PortConnector pc = new PortConnector();
		//loadSession(loadPrevSession);
		command = "/C \"netsh interface set interface Wi-Fi enable";
		//executeCMD(command);
		
		// search();
		//gFind();
		//getHtml();
		//gCheckResults();
		
		
		//System.out.println(driver.executeScript("return navigator.userAgent;"));
		//saveSession();
	}

	static void displayInterfaceInformation(NetworkInterface netint) throws SocketException {
        System.out.printf("Display name: %s\n", netint.getDisplayName());
        System.out.printf("Name: %s\n", netint.getName());
        Enumeration<InetAddress> inetAddresses = netint.getInetAddresses();
        for (InetAddress inetAddress : Collections.list(inetAddresses)) {
        	System.out.printf("InetAddress: %s\n", inetAddress);
        }
        System.out.printf("\n");
     }
	
	public void setUserAgent()
	{
		
		int random;
		
		random =ThreadLocalRandom.current().nextInt(0,emptySlotOf(useragentlist));
		
		opts = new FirefoxOptions();
		opts.addPreference("general.useragent.override",useragentlist[random]);	
		
	}
	
	public String uaType()
	{
		String uaType="three";
		if(!driver.findElements(By.xpath("//div[@id='searchform']//input")).isEmpty()) {uaType = "win10";}
		if(!driver.findElements(By.xpath("//div[@id='main']//form[@action='/search']")).isEmpty()) {uaType = "android";}
		
		
		return uaType;	
	}
	public void search()
	{
		System.out.println("Searching for "+query);
		driver.get(link);
		sleep(1500);
		
		List<WebElement> blocks = new ArrayList<>();
		List<String> searchResults = new ArrayList<>();
		WebElement block;
		String result,adlink;
		String blockXPath1, blockXPath2;
		String selectLinkOpeninNewTab;
		String searchUrl;
		
		
		switch(uaType()) 
        { 
            case "win10": 
                System.out.println("win10 - UA"); 
                sleep(100);
                mSearchBar = driver.findElement(By.xpath("//form[@action='/search']"));
    			mSearchBar.click();
    			sleep(66);
    			mSearchBar = driver.findElement(By.xpath("//input[@aria-label='Otsi']"));
    			mSearchBar.sendKeys(query);
    			sleep(66);
    			mSearchBar.sendKeys(Keys.ENTER);
    			sleep(3000);
    			searchUrl=driver.getCurrentUrl();
    			
    			
    			blockXPath1 = "//li[@class='ads-ad'][*]/div/a/h3";	//Win10
    			
    			blocks = driver.findElementsByXPath(blockXPath1);
    			
    			System.out.println(blocks.size()+" Blocks");
			try {
				for(int i=0;i<blocks.size();i++) 
    			{
    				blocks = driver.findElementsByXPath(blockXPath1);
    				block = blocks.get(i);
    				jsx.executeScript("arguments[0].scrollIntoView(true);", block);
    				sleep(250);
    				//act.clickAndHold(block).build().perform();
    				//act.dragAndDropBy(block, ThreadLocalRandom.current().nextInt(400,600), 150).build().perform();
    				
    				sleep(150);
    				
    				//block.click();
    				jsx.executeScript("arguments[0].click();", block);
    				sleep(stayTime);
    				driver.getCurrentUrl();
    				
    				driver.get(searchUrl);
    				sleep(2500);
    			}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
    			sleep(5000);
    			driver.quit();
    			
                
                break; 
            case "android": 
                System.out.println("android -UA");
                mSearchBar = driver.findElement(By.xpath("//div[@id='main']//form[@action='/search']"));
    			mSearchBar.click();
    			sleep(66);
    			mSearchBar = driver.findElement(By.xpath("//input[@aria-label='Otsi']"));
    			mSearchBar.sendKeys(query);
    			sleep(66);
    			mSearchBar.sendKeys(Keys.ENTER);
    			sleep(6000);
    			WebElement abi;
    			
    			abi = driver.findElementByXPath("//g-bottom-sheet//a[@href='#']"); 	//Android
				jsx.executeScript("arguments[0].click();", abi);
    			
    			
    			
    			blockXPath2 = "//li[@class='ads-fr'][*]/div/div/div/a"; 	//Android
    			blocks = driver.findElementsByXPath(blockXPath2);
    			
    			System.out.println(blocks.size()+" Blocks");
			try {
				for(int i=0;i<blocks.size();i++) 
    			{
    				blocks = driver.findElementsByXPath(blockXPath2);
    				block = blocks.get(i);
    				jsx.executeScript("arguments[0].scrollIntoView(true);", block);
    				sleep(250);
    				//act.clickAndHold(block);
    				act.dragAndDropBy(block, ThreadLocalRandom.current().nextInt(100,300), 50).build().perform();
    				
    				sleep(150);
    				
    				adlink = block.getAttribute("href");
    				searchResults.add(adlink);
    				
    				System.out.println(adlink);
    				
    				
    				
    				selectLinkOpeninNewTab = Keys.chord(Keys.CONTROL,Keys.RETURN);
    				block.sendKeys(selectLinkOpeninNewTab);
    				sleep(stayTime);
    			}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    			
    			sleep(5000);
    			driver.quit();
                break; 
            case "three": 
                System.out.println("Not Win or Android"); 
                break; 
            default: 
                System.out.println("no match"); 
        } 
	}
	
	
	public void executeCMD(String cmd)
	{
		cmdLines.clear();
		WinDef.HWND h = null;
      // Shell32.INSTANCE.ShellExecuteA(h, "runas", "cmd.exe", cmd,null,1); //-----------WORKING
        
        /*try {
			Runtime.getRuntime().exec("cmd /c mybatch.bat.lnk");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
        
		ProcessBuilder builder = new ProcessBuilder //-------------------working
				("cmd.exe", "/c", cmd);
	        builder.redirectErrorStream(true);
	       
	        
	        try {
				Process p = builder.start();
				BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
				String line;
				while (true) {
				    line = r.readLine();
				    if (line == null) { break; }
				    System.out.println(line);
				    cmdLines.add(line);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	
	
	
	
	
	
	public void gFind()
	{
		System.out.println("Searching for "+query);
		driver.get(link);
		sleep(1500);
		WebElement mSearchBar,mSearchBarC,mSearchBarD;
		mSearchBarC=null;
		try {
			//mSearchBar = driver.findElement(By.xpath("/html/body/div/div[3]/form/div[2]/div/div[1]/div/div[1]/input"));
			mSearchBar = driver.findElement(By.xpath("//div[@id='searchform']//input"));
			mSearchBar.click();
			mSearchBar.sendKeys(query);
			sleep(66);
			mSearchBar.sendKeys(Keys.ENTER);
			
		} catch (Exception e) {
			System.out.println("failed, tryin android");
			mSearchBarC = driver.findElement(By.xpath("//div[@id='main']//form[@action='/search']"));
			mSearchBarC.click();
			sleep(66);
			mSearchBarD = driver.findElement(By.xpath("//input[@aria-label='Otsi']"));
			mSearchBarD.sendKeys(query);
			sleep(66);
			mSearchBarD.sendKeys(Keys.ENTER);
			//e.printStackTrace();
		}
		
		
		/*mSearchBarC.click();
		jsx.executeScript("arguments[0].click();", mSearchBar);
		sleep(500);
		
		mSearchBar.sendKeys(query);
		sleep(66);
		mSearchBar.sendKeys(Keys.ENTER);*/
		sleep(500);
		
		//System.out.println(driver.getPageSource());
	}
	
	public void gCheckResults()
	{
		
		jsx.executeScript("window.scrollBy(0,300)", "");
		sleep(2000);
		
		
		List<WebElement> blocks = new ArrayList<>();
		List<WebElement> searchResults = new ArrayList<>();
		WebElement block;
		String result,result2;
		
		//String blockXPath = "/html/body/div[6]/div[3]/div[10]/div[1]/div[2]/div/div[2]/div[2]/div/div/div/div[*]";
		String blockXPath1 = "//li[@class='ads-ad'][*]//a[1]";	//Win10
		String blockXPath2 = "//li[@class='ads-fr'][*]//a[1]"; 	//Android
		blocks = driver.findElementsByXPath(blockXPath1);
		blocks.addAll(driver.findElementsByXPath(blockXPath2));
		System.out.println(blocks.size()+" Blocks");
		for(int i=0;i<blocks.size();i++) 
		{
			blocks = driver.findElementsByXPath(blockXPath2);
			block = blocks.get(i);
			jsx.executeScript("arguments[0].scrollIntoView(true);", block);
			sleep(150);
			//act.clickAndHold(block);
			act.dragAndDropBy(block, 500, 50);
			//jsx.executeScript("arguments[0].click();", block);
			sleep(150);
			result = driver.getCurrentUrl().toString();
			result2 = block.getAttribute("href");
			System.out.println(result2);
			//driver.executeScript("window.history.go(-1)");
			//sleep(1500);
		}
	}
	
	public void getHtml() throws IOException
	{
		BufferedWriter writer = new BufferedWriter(new FileWriter(htmlfilename));
	    writer.write(driver.getPageSource());//save the string representation of the board
	    writer.close();
		
	}
	
	public void saveSession() throws IOException
	{
		String sessionID = ((RemoteWebDriver) driver).getSessionId().toString();
		//System.out.println(sessionID);
		
		HttpCommandExecutor executor = (HttpCommandExecutor) ((RemoteWebDriver)driver).getCommandExecutor();
	    URL url = executor.getAddressOfRemoteServer();
	    //System.out.println(url); 
	    
	    activedriver[0]="Session";
	    activedriver[1]=sessionID;
	    activedriver[2]=url.toString();
	    
	    StringBuilder builder = new StringBuilder();
	    builder.append(activedriver[0]+","+activedriver[1]+","+activedriver[2]);
	    BufferedWriter writer = new BufferedWriter(new FileWriter(sfilename));
	    writer.write(builder.toString());//save the string representation of the board
	    writer.close();
	}
	
	public void loadSession(boolean doit) throws IOException
	{
		System.setProperty("webdriver.gecko.driver","D:\\LIB\\geckodriver-v0.24.0-win64\\geckodriver.exe");
		if(doit)
		
		{
		BufferedReader reader = new BufferedReader(new FileReader(sfilename));
		String line = "";
		
		
		line = reader.readLine();
		
		   String[] cols = line.split(","); //note that if you have used space as separator you have to split on " "
		   int col = 0;
		   for(String  c : cols)
		   {
			   activedriver[col] = c;
			   System.out.println(activedriver[col]);
		      col++;
		   }
		   ;
		
		reader.close();
		
		checkifSessionPresent();
		}
		else { driver = new FirefoxDriver(opts);}
		
		act = new Actions(driver);
		jsx=(JavascriptExecutor)driver;
	}
	
	public String[] importfile(String sfilename) throws IOException
	{
		BufferedReader reader = new BufferedReader(new FileReader(sfilename));
		String line = "";
		ArrayList<String> list = new ArrayList<String>();
		String[] arlist=new String[10000];
		line = reader.readLine();
		int i=0;
		System.out.println(line);
		while((line = reader.readLine())!=null)
		{
		
		   list.add(line);
		   System.out.println(line);
		   arlist[i]=list.get(i);
		   i++;
		}
		System.out.println(list.size());
		reader.close();
		//useragentlist = new String[list.size()];
	
		return arlist;
	}
	
	
	
	public void sleep(int low,int high) 
	{
		try {Thread.sleep(ThreadLocalRandom.current().nextInt(low,high));} 
		catch (InterruptedException e) {e.printStackTrace();}
	}
	
	public void sleep(int time) 
	{
		
		int rnd = ThreadLocalRandom.current().nextInt(80,120);
		
		try {Thread.sleep(time*rnd/100);} 
		catch (InterruptedException e) {e.printStackTrace();}
	}	
	
	public void checkifSessionPresent() throws MalformedURLException 
	{
	
			try {
				URL url= new URL(activedriver[2]);
				SessionId id= new SessionId(activedriver[1]);
				driver = createDriverFromSession(id, url); 
				driver.getCurrentUrl();
				System.out.println("Session Active");
				
				
				
				} catch (Exception ex) {
				System.out.println("Session Not Found. Creating new Session");
				driver = new FirefoxDriver();	
				}
			
	}
	
	
	public static RemoteWebDriver createDriverFromSession(final SessionId sessionId, URL command_executor){
	    CommandExecutor executor = new HttpCommandExecutor(command_executor) {

	    @Override
	    public Response execute(Command command) throws IOException {
	        Response response = null;
	        if (command.getName() == "newSession") {
	            response = new Response();
	            response.setSessionId(sessionId.toString());
	            response.setStatus(0);
	            response.setValue(Collections.<String, String>emptyMap());

	            try {
	                Field commandCodec = null;
	                commandCodec = this.getClass().getSuperclass().getDeclaredField("commandCodec");
	                commandCodec.setAccessible(true);
	                commandCodec.set(this, new W3CHttpCommandCodec());

	                Field responseCodec = null;
	                responseCodec = this.getClass().getSuperclass().getDeclaredField("responseCodec");
	                responseCodec.setAccessible(true);
	                responseCodec.set(this, new W3CHttpResponseCodec());
	            } catch (NoSuchFieldException e) {
	                e.printStackTrace();
	            } catch (IllegalAccessException e) {
	                e.printStackTrace();
	            }

	        } else {
	            response = super.execute(command);
	        }
	        return response;
	    }
	    };

	    return new RemoteWebDriver(executor, new DesiredCapabilities());
	    }
	
	public boolean contains(String obj,String[][] list ) 
	{
		int i;
	    
			for (i = 0; i < list.length; i++) 
			{
				
			    if (list[i][0]!=null&&list[i][0].equals(obj)) {
			    	//System.out.println("check"+i);
			    	return true;
			        
			    }
			}
		

	    return false;
	}
	
	public int emptySlotOf(String[][] list) 
	{
		int slotN=0;

		for(int i = 0; i<list.length; i++) 
		{
		    if(list[i][0]==""||list[i][0]==null)
		    {
		       slotN = i;
		       break;
		    }
		}
		return slotN;
	}
	
	public int emptySlotOf(String[] list) 
	{
		int slotN=0;

		for(int i = 0; i<list.length; i++) 
		{
		    if(list[i]==""||list[i]==null)
		    {
		       slotN = i;
		       break;
		    }
		}
		return slotN;
	}
	
	public static String[][] importData(String fileName, int tabNumber) throws FileNotFoundException, IOException, NullPointerException
    {
 
        String[][] data;
 
        //Create Workbook from Existing File
        InputStream fileIn = new FileInputStream(fileName);
        Workbook wb = WorkbookFactory.create(fileIn);
        Sheet sheet = wb.getSheetAt(tabNumber);
 
        //Define Data & Row Array and adjust from Zero Base Numer
        data = new String[sheet.getLastRowNum()+1][];
        Row[] row = new Row[sheet.getLastRowNum()+1];
        Cell[][] cell = new Cell[row.length][];
 
        //Transfer Cell Data to Local Variable
        for(int i = 0; i < row.length; i++)
        {
            try {
				row[i] = sheet.getRow(i);
 
				//Note that cell number is not Zero Based
				cell[i] = new Cell[row[i].getLastCellNum()];
				data[i] = new String[row[i].getLastCellNum()];
 
				for(int j = 0; j < cell[i].length; j++)
				{
				    cell[i][j] = row[i].getCell(j);
				    data[i][j] = cell[i][j].getStringCellValue();
				}
			} catch (NullPointerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
 
        }
 
        fileIn.close();
        return data;
    }
	
	public static void exportData(String fileName, String tabName, String[][] data) throws FileNotFoundException, IOException
    {
        //Create new workbook and tab
        Workbook wb = new HSSFWorkbook();
        FileOutputStream fileOut = new FileOutputStream(fileName);
        Sheet sheet = wb.createSheet(tabName);
 
        //Create 2D Cell Array
        Row[] row = new Row[data.length];
        Cell[][] cell = new Cell[row.length][];
 
        //Define and Assign Cell Data from Given
        for(int i = 0; i < row.length; i ++)
        {
            row[i] = sheet.createRow(i);
            cell[i] = new Cell[data[i].length];
 
            for(int j = 0; j < cell[i].length; j ++)
            {
                
            	cell[i][j] = row[i].createCell(j);
                cell[i][j].setCellValue(data[i][j]);
            }
 
        }
 
        //Export Data
        wb.write(fileOut);
        fileOut.close();
        wb.close();
 
    }
	
	public interface Shell32 extends ShellAPI, StdCallLibrary {
        Shell32 INSTANCE = (Shell32)Native.loadLibrary("shell32", Shell32.class);

        WinDef.HINSTANCE ShellExecuteA(WinDef.HWND hwnd,
                                      String lpOperation,
                                      String lpFile,
                                      String lpParameters,
                                      String lpDirectory,
                                      int nShowCmd);
    }

}
