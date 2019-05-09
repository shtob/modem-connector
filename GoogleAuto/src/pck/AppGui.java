package pck;

import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.SpringLayout;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import java.awt.GridLayout;
import javax.swing.JSplitPane;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

import jssc.SerialPort;
import jssc.SerialPortEvent;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JScrollPane;
import java.awt.FlowLayout;
import javax.swing.ScrollPaneConstants;
import javax.swing.Box;

public class AppGui {

	private JFrame frame;
	private JTextField comPortName;
	private JTextField messageToPort;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AppGui window = new AppGui();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public AppGui() {
		initialize();
		
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		BrowseResults br = new BrowseResults();
		PortConnector pc = new PortConnector();
		frame = new JFrame();
		frame.setBounds(100, 100, 550, 550);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SpringLayout springLayout = new SpringLayout();
		frame.getContentPane().setLayout(springLayout);
		
		
		JButton btnSave = new JButton("Save");
		springLayout.putConstraint(SpringLayout.NORTH, btnSave, 36, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, btnSave, -10, SpringLayout.EAST, frame.getContentPane());
		frame.getContentPane().add(btnSave);
		
		JButton btnStart = new JButton("Start");
		btnStart.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				PortConnector pc = new PortConnector();
			}
		});
		springLayout.putConstraint(SpringLayout.SOUTH, btnStart, -10, SpringLayout.SOUTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, btnStart, -10, SpringLayout.EAST, frame.getContentPane());
		frame.getContentPane().add(btnStart);
		
		JButton btnLoad = new JButton("Load");
		springLayout.putConstraint(SpringLayout.NORTH, btnLoad, 0, SpringLayout.NORTH, btnSave);
		frame.getContentPane().add(btnLoad);
		
		JTextPane txtpnChooseNetworkAdapters = new JTextPane();
		springLayout.putConstraint(SpringLayout.WEST, btnLoad, 0, SpringLayout.WEST, txtpnChooseNetworkAdapters);
		springLayout.putConstraint(SpringLayout.SOUTH, txtpnChooseNetworkAdapters, -6, SpringLayout.NORTH, btnSave);
		springLayout.putConstraint(SpringLayout.EAST, txtpnChooseNetworkAdapters, 0, SpringLayout.EAST, btnSave);
		frame.getContentPane().add(txtpnChooseNetworkAdapters);
		txtpnChooseNetworkAdapters.setText("Choose Network Adapters");
		
		JScrollPane scrollPane = new JScrollPane();
		springLayout.putConstraint(SpringLayout.WEST, scrollPane, 0, SpringLayout.WEST, txtpnChooseNetworkAdapters);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		springLayout.putConstraint(SpringLayout.NORTH, scrollPane, 5, SpringLayout.SOUTH, btnSave);
		springLayout.putConstraint(SpringLayout.SOUTH, scrollPane, 146, SpringLayout.SOUTH, btnSave);
		springLayout.putConstraint(SpringLayout.EAST, scrollPane, -10, SpringLayout.EAST, frame.getContentPane());
		frame.getContentPane().add(scrollPane);
		scrollPane.setVisible(false);
		
		JPanel adapterPanel = new JPanel();
		scrollPane.setViewportView(adapterPanel);
		adapterPanel.setLayout(new BoxLayout(adapterPanel, BoxLayout.PAGE_AXIS));
		
		JTextPane txtpnAvailibleAdapters = new JTextPane();
		txtpnAvailibleAdapters.setText("Availible Adapters");
		scrollPane.setColumnHeaderView(txtpnAvailibleAdapters);
		
		comPortName = new JTextField();
		springLayout.putConstraint(SpringLayout.NORTH, comPortName, 30, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, comPortName, 10, SpringLayout.WEST, frame.getContentPane());
		frame.getContentPane().add(comPortName);
		comPortName.setColumns(10);
		
		messageToPort = new JTextField();
		springLayout.putConstraint(SpringLayout.NORTH, messageToPort, 26, SpringLayout.SOUTH, comPortName);
		springLayout.putConstraint(SpringLayout.WEST, messageToPort, 10, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, messageToPort, 0, SpringLayout.EAST, comPortName);
		frame.getContentPane().add(messageToPort);
		messageToPort.setColumns(10);
		
		JTextPane txtpnMessage = new JTextPane();
		springLayout.putConstraint(SpringLayout.NORTH, txtpnMessage, 6, SpringLayout.SOUTH, comPortName);
		springLayout.putConstraint(SpringLayout.WEST, txtpnMessage, 0, SpringLayout.WEST, comPortName);
		txtpnMessage.setText("Message");
		frame.getContentPane().add(txtpnMessage);
		
		JTextPane txtpnSerialPort = new JTextPane();
		springLayout.putConstraint(SpringLayout.NORTH, txtpnSerialPort, 10, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, txtpnSerialPort, 10, SpringLayout.WEST, frame.getContentPane());
		txtpnSerialPort.setText("Serial Port");
		springLayout.putConstraint(SpringLayout.SOUTH, txtpnSerialPort, 0, SpringLayout.NORTH, comPortName);
		frame.getContentPane().add(txtpnSerialPort);
		
		JButton btnSend = new JButton("Send");
		btnSend.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) 
			{
				pc.sendDatatoPort(messageToPort.getText());
			}
		});
		springLayout.putConstraint(SpringLayout.NORTH, btnSend, 73, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, btnSend, 6, SpringLayout.EAST, messageToPort);
		springLayout.putConstraint(SpringLayout.SOUTH, btnSend, 0, SpringLayout.SOUTH, messageToPort);
		frame.getContentPane().add(btnSend);
		
		JButton btnOpen = new JButton("Open");
		btnOpen.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) 
			{
				pc.serialPort=new SerialPort(comPortName.getText());
				pc.openPort();
				pc.pr.serialEvent();
				
			}
		});
		springLayout.putConstraint(SpringLayout.NORTH, btnOpen, 0, SpringLayout.NORTH, comPortName);
		springLayout.putConstraint(SpringLayout.WEST, btnOpen, 6, SpringLayout.EAST, comPortName);
		frame.getContentPane().add(btnOpen);
		
		JButton btnClose = new JButton("Close");
		btnClose.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) 
			{
				pc.closePort();
			}
		});
		springLayout.putConstraint(SpringLayout.NORTH, btnClose, 30, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, btnClose, 6, SpringLayout.EAST, btnOpen);
		springLayout.putConstraint(SpringLayout.SOUTH, btnClose, 0, SpringLayout.SOUTH, btnOpen);
		frame.getContentPane().add(btnClose);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		springLayout.putConstraint(SpringLayout.NORTH, scrollPane_1, 6, SpringLayout.SOUTH, messageToPort);
		springLayout.putConstraint(SpringLayout.WEST, scrollPane_1, 0, SpringLayout.WEST, comPortName);
		springLayout.putConstraint(SpringLayout.SOUTH, scrollPane_1, 109, SpringLayout.SOUTH, messageToPort);
		springLayout.putConstraint(SpringLayout.EAST, scrollPane_1, -6, SpringLayout.WEST, scrollPane);
		frame.getContentPane().add(scrollPane_1);
		
		JPanel portPanel = new JPanel();
		scrollPane_1.setViewportView(portPanel);
		portPanel.setLayout(new BoxLayout(portPanel, BoxLayout.PAGE_AXIS));
		
		JTextPane txtpnOutput = new JTextPane();
		txtpnOutput.setText("Output:");
		scrollPane_1.setColumnHeaderView(txtpnOutput);
		btnLoad.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				br.executeCMD(br.cmdGetCon);
				System.out.println(br.cmdLines.size());
				String str;
				for(int i=3;i<br.cmdLines.size()-1;i++)
				{
					str=br.cmdLines.get(i);
					str=str.substring(str.lastIndexOf("ed")+8);
					adapterPanel.setAlignmentX(Component.RIGHT_ALIGNMENT);
					adapterPanel.add(new JCheckBox(str));
				}
				adapterPanel.revalidate();
				scrollPane.setVisible(true);
				//validate();
			}
		});
	}
}
