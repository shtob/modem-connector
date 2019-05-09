package pck;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import jssc.SerialPortList;

public class PortConnector {

	static SerialPort serialPort = new SerialPort("COM15");;
	 int status=0;
	 PortReader pr = new PortReader();
	
	public PortConnector() 
	{
		
		
		//getPortList();
		//sendDatatoPort();
		
		
		
		//dialer();
	}

	
	public void getPortList()
	{
		String[] portNames = SerialPortList.getPortNames();
        
		if (portNames.length == 0) {
		    System.out.println("There are no serial-ports :( You can use an emulator, such ad VSPE, to create a virtual serial port.");
		    System.out.println("Press Enter to exit...");
		    try {
		        System.in.read();
		    } catch (IOException e) {
		         // TODO Auto-generated catch block
		          e.printStackTrace();
		    }
		    return;
		}

		for (int i = 0; i < portNames.length; i++){
		    System.out.println(portNames[i]);
		}
	}
	
	public void openPort()
	{
		try {
			serialPort.openPort();
			
			serialPort.setParams(SerialPort.BAUDRATE_9600,
			                     SerialPort.DATABITS_8,
			                     SerialPort.STOPBITS_1,
			                     SerialPort.PARITY_NONE);
			
			serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_RTSCTS_IN | 
			                              SerialPort.FLOWCONTROL_RTSCTS_OUT);

			serialPort.addEventListener(new PortReader(), SerialPort.MASK_RXCHAR);
			
			
		} catch (SerialPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void closePort()
	{
		try {
			serialPort.closePort();
		} catch (SerialPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void sendDatatoPort(String message)
	{
	
	try {
		
		
	    serialPort.writeString(message);
	    
	    
	}
	catch (SerialPortException ex) {
	    System.out.println("There are an error on writing string to port : " + ex);
	}
	
	
	}
	
	 
	
	public int dialer() { 
	    // status for debug. If status == 4 then you connected successfully
		 
	    // create process of wvdial
	    ProcessBuilder builder = new ProcessBuilder("wvdial");

	    try {
	        // start wvdial
	        final Process process = builder.start();
	        
	        // wvdial listener thread
	        final Thread ioThread = new Thread() {
	            @Override
	            public void run() {
	                try {

	                    final BufferedReader reader = new BufferedReader(
	                            new InputStreamReader(process.getErrorStream()));
	                    // wvdial output line
	                    String line;

	                    while ((line = reader.readLine()) != null) {
	                        // if "local  IP address" line detected set status 1
	                        if (line.contains("local  IP address")) {
	                            status = 1;
	                        }
	                        if (line.contains("remote IP address")) {
	                            status = 2;
	                        }
	                        if (line.contains("primary   DNS address")) {
	                            status = 3;
	                        }
	                        if (line.contains("secondary DNS address")) {
	                            status = 4;
	                        }
	                    }

	                    reader.close();
	                } catch (final Exception e) {
	                }
	            }
	        };
	        // start listener
	        ioThread.start();
	        // wait 6 secs and return status. Some kind of timeout
	        Thread.sleep(6000);
	    } catch (Exception e) {
	    }
	    return status;
	}
	
	
	private static class PortReader implements SerialPortEventListener {

	    @Override
	    public void serialEvent(SerialPortEvent event) {
	        if(/*event.isRXCHAR() &&*/ event.getEventValue() > 0) {
	            try {
	                String receivedData = serialPort.readString(event.getEventValue());
	                System.out.println("Received response: " + receivedData);
	            }
	            catch (SerialPortException ex) {
	                System.out.println("Error in receiving string from COM-port: " + ex);
	            }
	        }
	    }

	}
	
}
