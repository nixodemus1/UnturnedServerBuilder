package unturnedServer;

import com.dosse.upnp.UPnP;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import java.awt.Dialog.ModalExclusionType;
import java.awt.Window.Type;

//class extends JFrame and implements actionlistener 
@SuppressWarnings("serial")
public class UnturnedGUI extends JFrame {

	// cardlayout and container declerations
	CardLayout card;
	Container c;
	static boolean newServer;

	// server properties
	static boolean LAN = true;
	static boolean hasPassword = false;
	static boolean PvP = true;
	static boolean cheats = false;
	static String serverName = "New";
	static String port;
	static int map;
	static String passText = "password";
	static int players;
	static int perspective;
	static int difficult;
	static String welcome;
	String outPut;
	static ArrayList<WorkShop> modList;
	static ArrayList<WorkShop> chosenModList;
	final DefaultListModel<WorkShop> mods = new DefaultListModel<WorkShop>();
    final DefaultListModel<WorkShop> allTheMods = new DefaultListModel<WorkShop>();
    final DefaultListModel<WorkShop> selectedMods = new DefaultListModel<WorkShop>();
	WorkShop mod;
	static boolean isModMap;
	static boolean useModMap;

	// misc. needed
	private static JTextField textField;
	private static JTextField txtName;
	private static JTextField txtPort;
	private static JTextField passwordField;
	private static JList<String> mapList;
	private static JSlider maxPlayers;
	private static JComboBox<String> view;
	private static JComboBox<String> difficulty;
	private static JEditorPane welcomePane;
	private static JCheckBox chckbxCheats;
	private static JCheckBox chckbxNewCheckBox_1;
	private static JCheckBox passwordChecker;
	private static JCheckBox chckbxLAN;
	private PrintStream standardOut;

	static File cmd = new File("server\\steamcmd.exe");
	static String path = cmd.getAbsolutePath();
	private static ArrayList<Servers> servers;
	private Servers server;
	private static String values[];
	private JTextField workShopURL;

	public UnturnedGUI() {
		setBackground(Color.BLACK);
		getContentPane().setBackground(new Color(0, 128, 0));
		setForeground(new Color(0, 100, 0));
		File icon = new File("Images\\icon.png");
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\Nick Chowa\\git\\UnturnedServerBuilder\\unturned server\\Images\\icon.png"));
		setTitle("Unturned Server Maker 3.0");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// to get the content
		c = getContentPane();

		// Initialization of object "card"
		// of CardLayout class with 40
		// horizontal space and 30 vertical space .
		card = new CardLayout(5, 5);

		// set the layout
		c.setLayout(card);

		JPanel card_1 = new JPanel();
		getContentPane().add(card_1, "name_4390856995400");
		card_1.setLayout(new BorderLayout(0, 0));

		JTextArea welcomeMessage = new JTextArea();
		welcomeMessage.setBackground(Color.BLACK);
		welcomeMessage.setForeground(Color.GREEN);
		welcomeMessage.setEditable(false);
		welcomeMessage.setWrapStyleWord(true);
		welcomeMessage.setToolTipText("welcome mesage");
		welcomeMessage.setText(
				"Thank you for using Server builder! please make sure you have a working internet connection and be patient as the program will have to download many files first time it launches! Please follow instructions exactly and you will have your own working unturned servers in no time!");
		welcomeMessage.setRows(5);
		welcomeMessage.setLineWrap(true);
		welcomeMessage.setColumns(20);
		card_1.add(welcomeMessage, BorderLayout.CENTER);

		JButton startButton = new JButton("Start");
		startButton.setForeground(new Color(255, 215, 0));
		startButton.setBackground(new Color(0, 100, 0));
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TextAreaLogProgram.printLog(cmd, path);
				card.next(c);
			}
		});
		startButton.setToolTipText("Start the program!");
		card_1.add(startButton, BorderLayout.SOUTH);

		JPanel card1 = new JPanel();
		card1.setBackground(new Color(0, 0, 0));
		getContentPane().add(card1, "name_4426547071500");
		card1.setLayout(new BorderLayout(0, 0));

		JLabel lblNewLabel = new JLabel("Wait for the consol to say \"you may press continue\"");
		lblNewLabel.setForeground(Color.GREEN);
		lblNewLabel.setBackground(new Color(0, 100, 0));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		card1.add(lblNewLabel, BorderLayout.NORTH);

		JScrollPane scrollPane_2 = new JScrollPane();
		card1.add(scrollPane_2, BorderLayout.CENTER);

		// Create an instance of javax.swing.JTextArea control
		JTextArea textPane = new JTextArea();
		textPane.setForeground(Color.GREEN);
		textPane.setBackground(new Color(0, 0, 0));
		scrollPane_2.setViewportView(textPane);
		textPane.setEditable(false);
		//turn the text area into a printstream
		PrintStream printStream = new PrintStream(new CustomOutputStream(textPane));
		// re-assigns standard output stream and error output stream to printstream
		standardOut = System.out;
		System.setOut(printStream);
		System.setErr(printStream);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 0, 0));
		card1.add(panel, BorderLayout.SOUTH);

		JButton continue1 = new JButton("Continue");
		continue1.setBackground(new Color(0, 100, 0));
		continue1.setForeground(new Color(255, 215, 0));
		panel.add(continue1);
		continue1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.setOut(standardOut);
				System.setErr(standardOut);
				card.next(c);
			}
		});

		JPanel card2 = new JPanel();
		card2.setBackground(new Color(0, 0, 0));
		card2.setToolTipText("chose your server!");
		getContentPane().add(card2, "name_4644937493500");
		card2.setLayout(new BorderLayout(0, 0));

		JLabel lblserverChooser = new JLabel("Please choose a server");
		lblserverChooser.setForeground(Color.GREEN);
		lblserverChooser.setHorizontalAlignment(SwingConstants.CENTER);
		card2.add(lblserverChooser, BorderLayout.NORTH);

		JScrollPane scrollPane = new JScrollPane();
		card2.add(scrollPane, BorderLayout.CENTER);

		JList<String> list = new JList<String>();
		list.setBackground(new Color(0, 0, 0));
		list.setForeground(new Color(124, 252, 0));
		DefaultListModel serverModel  = new DefaultListModel();
		for (int i = 0;i < servers.size(); i++) {
			Servers tempWork = servers.get(i);
			serverModel.addElement(tempWork);
		}
		list.setModel(serverModel);
		list.setSelectedIndex(0);

		scrollPane.setViewportView(list);
		list.setToolTipText("choose your server!");
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setForeground(new Color(0, 0, 0));
		splitPane.setBackground(new Color(0, 0, 0));
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane.setAlignmentX(Component.CENTER_ALIGNMENT);
		splitPane.setContinuousLayout(true);
		card2.add(splitPane, BorderLayout.SOUTH);
		
		JButton continue2 = new JButton("Continue");
		continue2.setForeground(new Color(255, 215, 0));
		continue2.setBackground(new Color(0, 128, 0));
		continue2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (list.getSelectedIndex() == -1) {
        			
        		}else if (list.getSelectedIndex() == 0){
        			server = new Servers();
        			newServer = true;
        			card.next(c);
        		}else{
        			newServer = false;
        			server = servers.get(list.getSelectedIndex());
        			serverName = server.getName();
        		    LAN = server.getLan();
        		    port = String.valueOf(server.getPort());
        		    map = server.getMap();
        		    hasPassword = server.passwordChoice();
        		    passText = server.getPassword();
        		    players = server.getPlayers();
        		    PvP = server.getPvP();
        		    perspective = server.getPerspective();
        		    difficult = server.getDifficulty();
        		    welcome = server.getWelcome();
        			setOptions();
        			card.next(c);
        			card.next(c);
        			card.next(c);
        		}
        	}
		});
		splitPane.setLeftComponent(continue2);
		
		JButton deleteButton = new JButton("Delete");
		deleteButton.setForeground(new Color(255, 215, 0));
		deleteButton.setBackground(new Color(0, 100, 0));
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (list.getSelectedIndex() == -1 || list.getSelectedIndex() == 0) {

				} else {
					try {
						SetUpServer.deleteServer(servers, list.getSelectedIndex());
						serverModel.remove(list.getSelectedIndex());
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});
		splitPane.setRightComponent(deleteButton);

		JPanel card3 = new JPanel();
		card3.setBackground(new Color(0, 0, 0));
		getContentPane().add(card3, "name_4760098863600");
		card3.setLayout(new BorderLayout(0, 0));

		JLabel lblNewLabel_1 = new JLabel("please name your new server");
		lblNewLabel_1.setForeground(Color.GREEN);
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		card3.add(lblNewLabel_1, BorderLayout.NORTH);

		JPanel panel_1 = new JPanel();
		card3.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new BorderLayout(0, 0));

		textField = new JTextField();
		textField.setToolTipText("name your new server!");
		textField.setText("server name here");
		textField.setColumns(10);
		panel_1.add(textField, BorderLayout.NORTH);
		
		JTextArea txtpnServerNameMust = new JTextArea();
		txtpnServerNameMust.setForeground(Color.GREEN);
		txtpnServerNameMust.setBackground(new Color(0, 0, 0));
		txtpnServerNameMust.setLineWrap(true);
		txtpnServerNameMust.setWrapStyleWord(true);
		txtpnServerNameMust.setText("server name must be more than 5 characters long. Thank you!");
		txtpnServerNameMust.setEditable(false);
		panel_1.add(txtpnServerNameMust, BorderLayout.CENTER);

		JButton continue3 = new JButton("Continue");
		continue3.setForeground(new Color(255, 215, 0));
		continue3.setBackground(new Color(0, 100, 0));
		continue3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (textField.getText().length() > 5) {
					String text = textField.getText().replace(' ', '_');
					textField.setText(text);
					server.setName(textField.getText());
					serverName = server.getName();
					txtName.setText(serverName);
					card.next(c);
				} else {
					txtpnServerNameMust.setText("name is less than 5 characters");
				}
			}
		});
		card3.add(continue3, BorderLayout.SOUTH);

		JPanel card4 = new JPanel();
		card4.setBackground(new Color(0, 0, 0));
		getContentPane().add(card4, "name_4867586771400");
		card4.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane_1 = new JScrollPane();
		card4.add(scrollPane_1, BorderLayout.CENTER);

		JTextArea txtrYouShouldSee = new JTextArea();
		txtrYouShouldSee.setForeground(Color.GREEN);
		txtrYouShouldSee.setBackground(new Color(0, 0, 0));
		txtrYouShouldSee.setEditable(false);
		scrollPane_1.setViewportView(txtrYouShouldSee);
		txtrYouShouldSee.setWrapStyleWord(true);
		txtrYouShouldSee.setText(
				"You should see your server launch to load its files. wait till the level is done downloading (it will say 'level loaded 100%') and type \"shut down\" into the consol before continuing. ");
		txtrYouShouldSee.setRows(5);
		txtrYouShouldSee.setLineWrap(true);
		txtrYouShouldSee.setColumns(20);
		

		JButton Continue4 = new JButton("Continue");
		Continue4.setForeground(new Color(255, 215, 0));
		Continue4.setBackground(new Color(0, 100, 0));
		Continue4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					server.setPath(SetUpServer.newServer(server));
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				card.next(c);
			}
		});
		card4.add(Continue4, BorderLayout.SOUTH);

		JEditorPane dtrpnWriteYourWelcome = new JEditorPane();
		dtrpnWriteYourWelcome.setText("write your welcome message here!");

		JPanel card5 = new JPanel();
		card5.setBackground(Color.BLACK);
		getContentPane().add(card5, "name_1969951988600");
		card5.setLayout(new BorderLayout(0, 0));

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBackground(Color.LIGHT_GRAY);
		tabbedPane.setForeground(Color.BLACK);
		tabbedPane.setToolTipText("options");
		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		card5.add(tabbedPane);

		JPanel optionsName = new JPanel();
		optionsName.setBackground(Color.BLACK);
		optionsName.setForeground(Color.ORANGE);
		tabbedPane.addTab("Name", null, optionsName, null);

		JLabel nameLabel = new JLabel("server name");
		nameLabel.setForeground(Color.GREEN);
		optionsName.add(nameLabel);

		txtName = new JTextField();
		txtName.setBackground(Color.BLACK);
		txtName.setForeground(Color.GREEN);
		txtName.setText("New");
		optionsName.add(txtName);
		txtName.setColumns(10);

		JLabel lblNewLabel_5 = new JLabel("please make name more than 5 letters");
		lblNewLabel_5.setForeground(Color.GREEN);
		optionsName.add(lblNewLabel_5);

		JPanel optionsPort = new JPanel();
		optionsPort.setBackground(Color.DARK_GRAY);
		tabbedPane.addTab("internet", null, optionsPort, null);
		optionsPort.setLayout(new BoxLayout(optionsPort, BoxLayout.Y_AXIS));

		chckbxLAN = new JCheckBox("LAN?");
		chckbxLAN.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == 1) {
					LAN = true;
				} else {
					LAN = false;
				}
			}
		});
		optionsPort.add(chckbxLAN);

		txtPort = new JTextField();
		txtPort.setBackground(Color.BLACK);
		txtPort.setForeground(Color.GREEN);
		txtPort.setMaximumSize(new Dimension(2147483647, 20));
		txtPort.setText("27015");
		optionsPort.add(txtPort);
		txtPort.setColumns(10);

		JSeparator separator = new JSeparator();
		optionsPort.add(separator);

		JTextArea txtrIfPlayingOver = new JTextArea();
		txtrIfPlayingOver.setForeground(Color.GREEN);
		txtrIfPlayingOver.setBackground(Color.BLACK);
		txtrIfPlayingOver.setWrapStyleWord(true);
		txtrIfPlayingOver.setText(
				"If playing over internet instead of LAN, please make sure the port is already port forwarded. you can look up on google how to do that. The default port is 27015");
		txtrIfPlayingOver.setLineWrap(true);
		optionsPort.add(txtrIfPlayingOver);

		JPanel optionsMap = new JPanel();
		optionsMap.setBackground(Color.BLACK);
		tabbedPane.addTab("Map", null, optionsMap, null);

		JScrollPane scrollPane_4 = new JScrollPane();
		scrollPane_4.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_4.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		optionsMap.add(scrollPane_4);

		mapList = new JList<String>();
		mapList.setForeground(Color.GREEN);
		mapList.setBackground(Color.BLACK);
		scrollPane_4.setViewportView(mapList);
		mapList.setValueIsAdjusting(true);
		mapList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		mapList.setModel(new AbstractListModel<String>() {
			String[] values = new String[] { "Pei", "Washington", "Russia", "Yukon" };

			public int getSize() {
				return values.length;
			}

			public String getElementAt(int index) {
				return values[index];
			}
		});

		JPanel optionsPassword = new JPanel();
		optionsPassword.setBackground(Color.BLACK);
		tabbedPane.addTab("Password", null, optionsPassword, null);
		optionsPassword.setLayout(new BoxLayout(optionsPassword, BoxLayout.Y_AXIS));

		passwordChecker = new JCheckBox("password protected?");
		passwordChecker.setBackground(Color.BLACK);
		passwordChecker.setForeground(Color.GREEN);
		passwordChecker.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == 1) {
					hasPassword = true;
				} else {
					hasPassword = false;
				}
			}
		});
		optionsPassword.add(passwordChecker);

		JLabel lblNewLabel_2 = new JLabel("if so, enter password:");
		lblNewLabel_2.setForeground(new Color(0, 255, 0));
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		optionsPassword.add(lblNewLabel_2);

		passwordField = new JTextField("password");
		passwordField.setBackground(Color.BLACK);
		passwordField.setForeground(Color.GREEN);
		passwordField.setMaximumSize(new Dimension(2147483647, 20));
		optionsPassword.add(passwordField);

		JPanel optionsGamemode = new JPanel();
		optionsGamemode.setBackground(Color.BLACK);
		tabbedPane.addTab("GameMode", null, optionsGamemode, null);

		JLabel lblNewLabel_3 = new JLabel("max players?");
		lblNewLabel_3.setForeground(Color.GREEN);
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);

		maxPlayers = new JSlider();
		maxPlayers.setBackground(Color.BLACK);
		maxPlayers.setForeground(Color.GREEN);
		maxPlayers.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider) e.getSource();
				if (!source.getValueIsAdjusting()) {
					players = (int) source.getValue();
					;
				}
			}
		});
		maxPlayers.setPaintLabels(true);
		maxPlayers.setMajorTickSpacing(5);
		maxPlayers.setMinorTickSpacing(1);
		maxPlayers.setPaintTicks(true);
		maxPlayers.setMaximum(25);

		chckbxNewCheckBox_1 = new JCheckBox("PvP?");
		chckbxNewCheckBox_1.setBackground(Color.BLACK);
		chckbxNewCheckBox_1.setForeground(Color.GREEN);
		chckbxNewCheckBox_1.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == 1) {
					PvP = true;
				} else {
					PvP = false;
				}
			}
		});

		view = new JComboBox<String>();
		view.setForeground(new Color(255, 215, 0));
		view.setBackground(new Color(0, 100, 0));
		view.setModel(
				new DefaultComboBoxModel<String>(new String[] { "first person", "third person", "both", "vehicle" }));

		difficulty = new JComboBox<String>();
		difficulty.setBackground(new Color(0, 100, 0));
		difficulty.setForeground(new Color(255, 215, 0));
		difficulty.setModel(new DefaultComboBoxModel<String>(new String[] { "easy", "medium", "hard" }));

		chckbxCheats = new JCheckBox("cheats?");
		chckbxCheats.setForeground(Color.GREEN);
		chckbxCheats.setBackground(Color.BLACK);
		if (cheats = true) {
			chckbxCheats.setSelected(true);
		} else {
			chckbxCheats.setSelected(false);
		}
		chckbxCheats.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == 1) {
					cheats = true;
				} else {
					cheats = false;
				}
			}
		});
		optionsGamemode.setLayout(new BoxLayout(optionsGamemode, BoxLayout.Y_AXIS));
		optionsGamemode.add(lblNewLabel_3);
		optionsGamemode.add(maxPlayers);
		optionsGamemode.add(chckbxNewCheckBox_1);
		optionsGamemode.add(view);
		optionsGamemode.add(difficulty);
		optionsGamemode.add(chckbxCheats);

		JPanel optionsWelcome = new JPanel();
		optionsWelcome.setBackground(Color.BLACK);
		tabbedPane.addTab("Welcome", null, optionsWelcome, null);
		optionsWelcome.setLayout(new BoxLayout(optionsWelcome, BoxLayout.Y_AXIS));

		JLabel lblNewLabel_4 = new JLabel("write welcome message!");
		lblNewLabel_4.setForeground(Color.GREEN);
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
		optionsWelcome.add(lblNewLabel_4);

		JScrollPane scrollPane_5 = new JScrollPane();
		optionsWelcome.add(scrollPane_5);

		welcomePane = new JEditorPane();
		welcomePane.setForeground(Color.GREEN);
		welcomePane.setBackground(Color.BLACK);
		scrollPane_5.setViewportView(welcomePane);
		welcomePane.setText("Write your welcome message here!");

		JPanel panel_2 = new JPanel();
		panel_2.setBackground(Color.BLACK);
		panel_2.setForeground(Color.GREEN);
		card5.add(panel_2, BorderLayout.SOUTH);

		JButton wiki = new JButton("wiki");
		wiki.setForeground(new Color(255, 215, 0));
		wiki.setBackground(new Color(0, 100, 0));
		wiki.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openWebpage(
						"https://nodecraft.com/support/games/unturned/configuring-your-unturned-server-commands-dat");
			}
		});
		panel_2.add(wiki);

		JButton btnSave = new JButton("Save");
		btnSave.setForeground(new Color(255, 215, 0));
		btnSave.setBackground(new Color(0, 100, 0));
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!txtPort.getText().matches("^[0-9]*$") || txtName.getText().length() < 5) {
					txtrIfPlayingOver.setText(
							"ERROR: you either put letters in your port, or your server name is less than 5 characters long");
					tabbedPane.setSelectedIndex(1);
				} else {
					serverName = txtName.getText();
					port = txtPort.getText();
					map = mapList.getSelectedIndex();
					passText = passwordField.getText();
					perspective = view.getSelectedIndex();
					difficult = difficulty.getSelectedIndex();
					welcome = welcomePane.getText();
					card.next(c);
				}
			}
		});
		panel_2.add(btnSave);

		JPanel card5_1 = new JPanel();
		card5_1.setBackground(new Color(0, 0, 0));
		getContentPane().add(card5_1, "name_48160497111000");
		card5_1.setLayout(new BoxLayout(card5_1, BoxLayout.Y_AXIS));

		JScrollPane scrollPane_6 = new JScrollPane();
		card5_1.add(scrollPane_6);

		JTextPane printResults = new JTextPane();
		printResults.setForeground(Color.GREEN);
		printResults.setBackground(new Color(0, 0, 0));
		printResults.setText("press refresh to check results");
		scrollPane_6.setViewportView(printResults);

		JPanel panel_3 = new JPanel();
		panel_3.setBackground(new Color(0, 128, 0));
		card5_1.add(panel_3);

		JButton btnBackButton = new JButton("Back");
		btnBackButton.setForeground(new Color(255, 215, 0));
		btnBackButton.setBackground(new Color(0, 100, 0));
		btnBackButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				card.previous(c);
			}
		});
		panel_3.add(btnBackButton);

		JButton finalContinue = new JButton("Continue");
		finalContinue.setForeground(new Color(255, 215, 0));
		finalContinue.setBackground(new Color(0, 100, 0));
		finalContinue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					SetUpServer.changeBat(server);
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
				card.next(c);
			}
		});
		panel_3.add(finalContinue);

		JButton btnRefresh = new JButton("Refresh");
		btnRefresh.setForeground(new Color(255, 215, 0));
		btnRefresh.setBackground(new Color(0, 100, 0));
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				printResults.setText("name = " + serverName + "\nLan = " + LAN + "\nport = " + port + "\nmap = " + map
						+ "\nhas password = " + hasPassword + "\npassword = " + passText + "\nmax players = " + players
						+ "\nPvP = " + PvP + "\nview = " + perspective + "\ndifficulty = " + difficult + "\ncheats = "
						+ cheats + "\nwelcome = " + welcome + "\ninternet: " + portChecker(Integer.parseInt(port)));
			}
		});
		panel_3.add(btnRefresh);
		
		// this is where i pulled the workshop card from the .class file
		// its wonky cause its decompiled, hopefully i remember to clean this up
		// and make it legible
		JPanel cardMODS = new JPanel();
		cardMODS.setBackground(new Color(0, 0, 0));
		getContentPane().add(cardMODS, "name_195440423041000");
		cardMODS.setLayout(new BorderLayout(0, 0));
		JTabbedPane tabbedPane_1 = new JTabbedPane(1);
		cardMODS.add(tabbedPane_1, "Center");
		JPanel addMod = new JPanel();
		addMod.setBackground(new Color(0, 128, 0));
		tabbedPane_1.addTab("add mod", null, addMod, null);
		addMod.setLayout(new BoxLayout(addMod, 1));
		JLabel lblNewLabel_7 = new JLabel("copy and past the URL for the workshop item here");
		lblNewLabel_7.setForeground(new Color(255, 255, 0));
		addMod.add(lblNewLabel_7);
		JPanel panel_10 = new JPanel();
		panel_10.setBackground(new Color(0, 0, 0));
		addMod.add(panel_10);
		workShopURL = new JTextField();
		workShopURL.setForeground(new Color(0, 255, 0));
		workShopURL.setBackground(new Color(0, 0, 0));
		panel_10.add(workShopURL);
		workShopURL.setColumns(10);
		JCheckBox chckbxNewCheckBox = new JCheckBox("Map?");
		chckbxNewCheckBox.setBackground(new Color(0, 0, 0));
		chckbxNewCheckBox.setForeground(new Color(0, 255, 0));
		panel_10.add(chckbxNewCheckBox);
		chckbxNewCheckBox.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == 1) {
					isModMap = true;
				} else {
					isModMap = false;
				}
			}
		});
		
		for(int i = 0;i < modList.size();i++) {
			WorkShop tempWork = modList.get(i);
			if (tempWork.getMap() == true) {
				mods.addElement(tempWork);
			}
		}
		JList modMaps = new JList(mods);
		modMaps.setForeground(new Color(0, 255, 0));
		modMaps.setBackground(new Color(0, 0, 0));
		modMaps.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				selectedMods.removeElement(mod);
				if (!(modMaps.getSelectedIndex() == -1 || modMaps.getSelectedIndex() == 0)) {
					selectedMods.addElement((WorkShop) modMaps.getSelectedValue());
				}
				mod = (WorkShop) modMaps.getSelectedValue();
			}
		});
		modMaps.setSelectedIndex(0);
		JButton btnNewButton_1 = new JButton("Add");
		btnNewButton_1.setForeground(new Color(255, 215, 0));
		btnNewButton_1.setBackground(new Color(0, 100, 0));
		btnNewButton_1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if (isModMap == true) {
						mod = WorkShop.getAll(workShopURL.getText(), true);
						modList.add(mod);
						allTheMods.addElement(mod);
						mods.addElement(mod);
					} else {
						mod = WorkShop.getAll(workShopURL.getText(), false);
						modList.add(mod);
						allTheMods.addElement(mod);
					}
				} catch (Throwable e1) {
					e1.printStackTrace();
				}
			}
		});
		addMod.add(btnNewButton_1);
		JPanel SelectMap = new JPanel();
		SelectMap.setBackground(new Color(0, 128, 0));
		tabbedPane_1.addTab("map", null, SelectMap, null);
		SelectMap.setLayout(new BoxLayout(SelectMap, 1));
		JScrollPane scrollPane_8 = new JScrollPane();
		scrollPane_8.setVerticalScrollBarPolicy(22);
		scrollPane_8.setHorizontalScrollBarPolicy(31);
		SelectMap.add(scrollPane_8);
		scrollPane_8.setViewportView(modMaps);
		
		JButton btnNewButton_2 = new JButton("Delete map");
		btnNewButton_2.setForeground(new Color(255, 215, 0));
		btnNewButton_2.setBackground(new Color(0, 100, 0));
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				modList.remove(modMaps.getSelectedValue());
				mods.remove(modMaps.getSelectedIndex());
				SetUpServer.saveMods(modList, server);
			}
		});
		SelectMap.add(btnNewButton_2);
		JPanel listofMods = new JPanel();
		listofMods.setVerifyInputWhenFocusTarget(false);
		listofMods.setFocusable(false);
		listofMods.setBackground(new Color(0, 128, 0));
		tabbedPane_1.addTab("mod list", null, listofMods, null);
		listofMods.setLayout(new BorderLayout(0, 0));
		JLabel lblNewLabel_8 = new JLabel("Mod List >>> Active List");
		lblNewLabel_8.setHorizontalAlignment(0);
		lblNewLabel_8.setForeground(Color.YELLOW);
		listofMods.add(lblNewLabel_8, "North");
		JPanel modView = new JPanel();
		listofMods.add(modView, "Center");
		modView.setLayout(new BoxLayout(modView, 0));
		JScrollPane scrollPane_9 = new JScrollPane();
		scrollPane_9.setVerticalScrollBarPolicy(22);
		scrollPane_9.setHorizontalScrollBarPolicy(31);
		modView.add(scrollPane_9);
		for (int i = 0;i < modList.size(); i++) {
			WorkShop tempWork = modList.get(i);
			if (tempWork.getMap() == false) {
				allTheMods.addElement(tempWork);
			}
		}
		JList allMods = new JList(allTheMods);
		allMods.setBackground(new Color(0, 0, 0));
		allMods.setForeground(new Color(0, 255, 0));
		scrollPane_9.setViewportView(allMods);
		JScrollPane scrollPane_10 = new JScrollPane();
		scrollPane_10.setVerticalScrollBarPolicy(22);
		scrollPane_10.setHorizontalScrollBarPolicy(31);
		modView.add(scrollPane_10);
		JList chosenMods = new JList(selectedMods);
		chosenMods.setBackground(new Color(0, 0, 0));
		chosenMods.setForeground(new Color(0, 255, 0));
		scrollPane_10.setViewportView(chosenMods);
		JPanel buttons = new JPanel();
		buttons.setBackground(new Color(0, 100, 0));
		listofMods.add((Component) buttons, "South");
		JButton btnNewButton_4 = new JButton("Add");
		btnNewButton_4.setForeground(new Color(255, 215, 0));
		btnNewButton_4.setBackground(new Color(0, 0, 0));
		btnNewButton_4.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (((WorkShop) allMods.getSelectedValue()).getMap() == false) {
					selectedMods.addElement((WorkShop) allMods.getSelectedValue());
					allTheMods.remove(allMods.getSelectedIndex());
				}
			}
		});
		buttons.add(btnNewButton_4);
		JButton btnNewButton_5 = new JButton("Remove");
		btnNewButton_5.setForeground(new Color(255, 215, 0));
		btnNewButton_5.setBackground(new Color(0, 0, 0));
		btnNewButton_5.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (((WorkShop) chosenMods.getSelectedValue()).getMap() == false) {
					allTheMods.addElement((WorkShop) chosenMods.getSelectedValue());
					selectedMods.remove(chosenMods.getSelectedIndex());
				}
			}
		});
		buttons.add(btnNewButton_5);
		JButton btnNewButton_3 = new JButton("Delete");
		btnNewButton_3.setForeground(new Color(255, 215, 0));
		btnNewButton_3.setBackground(new Color(0, 0, 0));
		btnNewButton_3.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				modList.remove(allMods.getSelectedIndex());
				allTheMods.remove(allMods.getSelectedIndex());
				SetUpServer.saveMods(modList, server);
			}
		});
		buttons.add(btnNewButton_3);
		JPanel panel_4 = new JPanel();
		panel_4.setBackground(new Color(0, 0, 0));
		cardMODS.add(panel_4, "South");
		JButton btnNewButton = new JButton("Continue");
		btnNewButton.setForeground(new Color(255, 215, 0));
		btnNewButton.setBackground(new Color(0, 100, 0));
		btnNewButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				chosenModList = Collections.list(selectedMods.elements());
				if (modMaps.getSelectedIndex() == -1 || modMaps.getSelectedIndex() == 0) {
					useModMap = false;
				} else {
					useModMap = true;
					modList.remove(0);
				}
				SetUpServer.saveMods(chosenModList, server);
				saveOptions(servers, server, mod);
				card.next(c);
			}
		});
		panel_4.add(btnNewButton);

		// end of modlist section

		JPanel card6 = new JPanel();
		getContentPane().add(card6, "name_1969961176800");
		card6.setLayout(new BorderLayout(0, 0));
		JScrollPane scrollPane_3 = new JScrollPane();
		card6.add(scrollPane_3);

		JTextArea finalMessage = new JTextArea();
		finalMessage.setForeground(Color.GREEN);
		finalMessage.setBackground(new Color(0, 0, 0));
		finalMessage.setWrapStyleWord(true);
		finalMessage.setLineWrap(true);
		finalMessage.setText(
				"And your server is up and running! thats all you need to do! you may now press the \"End\" button to exit the set up! Thank you for downloading and using unturned server maker! remember that you can type \"shutdown\" to close your server. and above there is a link to the wiki for server commands! Have fun!");
		finalMessage.setColumns(20);
		finalMessage.setRows(5);
		scrollPane_3.setViewportView(finalMessage);

		JButton endButton = new JButton("Launch");
		endButton.setForeground(new Color(255, 215, 0));
		endButton.setBackground(new Color(0, 100, 0));
		endButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SetUpServer.saveServer(servers, modList);
				if (LAN == false) {
					portChecker(server.getPort());
					SetUpServer.launchServer(server);
					card.next(c);
				} else {
					SetUpServer.launchServer(server);
					card.next(c);
				}
			}
		});
		card6.add(endButton, BorderLayout.SOUTH);

		JButton serverControls = new JButton("server commands!");
		serverControls.setForeground(new Color(255, 215, 0));
		serverControls.setBackground(new Color(0, 100, 0));
		serverControls.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openWebpage("https://unturned.fandom.com/wiki/Server_Commands");
			}
		});
		card6.add(serverControls, BorderLayout.NORTH);
		JPanel card7 = new JPanel();
		getContentPane().add(card7, "name_12435269527100");
		card7.setLayout(new BorderLayout(0, 0));
		JScrollPane scrollPane_7 = new JScrollPane();
		scrollPane_7.setVerticalScrollBarPolicy(22);
		scrollPane_7.setHorizontalScrollBarPolicy(31);
		card7.add(scrollPane_7);
		JTextArea errorChecker = new JTextArea();
		errorChecker.setLineWrap(true);
		errorChecker.setText(
				"Thanks for checking out Unterned Server Maker! If your done and everything went fine, press \"End.\" Thank you for downloading and using unturned server maker! If anything goes wrong you can reach me at the links i gave in the readme!");
		errorChecker.setForeground(Color.GREEN);
		errorChecker.setBackground(new Color(0, 0, 0));
		scrollPane_7.setViewportView(errorChecker);
		JPanel panel_5 = new JPanel();
		panel_5.setBackground(Color.BLACK);
		card7.add(panel_5, "South");
		JButton programEnder = new JButton("End");
		programEnder.setForeground(new Color(255, 215, 0));
		programEnder.setBackground(new Color(0, 100, 0));
		programEnder.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (LAN == false) {
					UPnP.closePortTCP(server.getPort());
					UPnP.closePortTCP(server.getPort() + 1);
					UPnP.closePortTCP(server.getPort());
					System.exit(0);
				} else {
					System.exit(0);
				}
			}
		});
		panel_5.add(programEnder);
	}

	/**
	 * a quick function to open the website on your default browser (I seriously can
	 * not remember where I got this from, if this was your code I am honestly so
	 * sorry)
	 * 
	 * @param urlString the web address we would like to open
	 * @author stack overflow
	 */
	public static void openWebpage(String urlString) {
		try {
			Desktop.getDesktop().browse(new URL(urlString).toURI());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * this is just a quick function to take the options chosen by the user and
	 * actually put them into the server object
	 * 
	 * @param servers arraylist of servers
	 * @param server  server whose options are currently being set
	 * @param mod     the map mod (if there is one) that will overwrite the default
	 *                map data
	 * @return returns the now modified arraylist of servers
	 */
	public static ArrayList<Servers> saveOptions(ArrayList<Servers> servers, Servers server, WorkShop mod) {
		server.setName(serverName);
		server.setLan(LAN);
		server.setPort(Integer.parseInt(port));
		server.setMap(map);
		server.containsPassoword(hasPassword);
		server.setPassword(passText);
		server.setPlayers(players);
		server.setPvp(PvP);
		server.setPerspective(perspective);
		server.setDifficulty(difficult);
		server.setCheats(cheats);
		server.setWelcome(welcome);
		if (!useModMap) {
			SetUpServer.choseOptions(server);
		} else {
			SetUpServer.modMap(server, mod);
		}
		if (newServer) {
			servers.add(server);
		}
		return servers;
	}

	/**
	 * this is just a quick function to take the options chosen by the user and
	 * actually put them into the server object
	 * 
	 * @author Nixodemus1
	 */
	public static void setOptions() {
		txtName.setText(serverName);
		if (LAN == true) {
			chckbxLAN.setSelected(true);
		} else {
			chckbxLAN.setSelected(false);
		}
		txtPort.setText(port);
		mapList.setSelectedIndex(map);
		if (hasPassword == true) {
			passwordChecker.setSelected(true);
		} else {
			passwordChecker.setSelected(false);
		}
		passwordField.setText(passText);
		maxPlayers.setValue(players);
		if (PvP == true) {
			chckbxNewCheckBox_1.setSelected(true);
		} else {
			chckbxNewCheckBox_1.setSelected(false);
		}
		view.setSelectedIndex(perspective);
		difficulty.setSelectedIndex(difficult);
		welcomePane.setText(welcome);
	}

	/**
	 * This is a implementation of UPnP that attempts to port forward natively
	 * within the software
	 * 
	 * @author Federico Dossena @ https://fdossena.com/
	 * @param PORT the given port for the server
	 * @return an output to show on the results screen so the user can see the issue
	 */
	public static String portChecker(int PORT) {
		String outPut = "Something went wrong";
		try {
			System.out.println("Opening socket...");
			final ServerSocket ss = new ServerSocket(PORT); // starts listening on chosen port
			System.out.println("Socket opened");
			// create a new thread that listens for incoming conenctions
			new Thread() {
				@Override
				public void run() {
					for (;;) {
						try {
							Socket s = ss.accept(); // wait for connections on socket
							System.out.println("Incoming connection from " + s.getInetAddress().getHostAddress());
							s.close(); // close the connection
						} catch (Throwable t) {
							System.err.println("Network error: " + t);
							continue;
						}
					}
				}
			}.start();
			// meanwhile, we try to open the port on the local gateway
			System.out.println("Attempting UPnP port forwarding...");
			if (UPnP.isUPnPAvailable()) { // is UPnP available?
				if (UPnP.isMappedTCP((int) PORT)) { // is the port already mapped?
					System.out.println("UPnP port forwarding not enabled: port is already mapped");
					outPut = "port is already mapped. please change port";
				} else if (UPnP.openPortTCP((int) PORT)) { // try to map port
					System.out.println("UPnP port forwarding enabled");
					outPut = "port forwarding successful!";
					UPnP.openPortTCP((int) (PORT + 1));
					UPnP.openPortTCP((int) (PORT + 2));
				} else {
					System.out.println("UPnP port forwarding failed");
					outPut = "port forwarding failed";
				}
			} else {
				System.out.println("UPnP is not available");
				outPut = "could not find UPnP port forwarding. may not be possible on your router";
			}
		} catch (Throwable t) {
			System.err.println("Network error: " + t);
		}
		return outPut;
	}

	public static void main(String[] args) {
		// load all servers and write commands on open
		servers = SetUpServer.loadServers();
		values = new String[servers.size()];
		for (int i = 0; i < values.length; i++) {
			Servers names = new Servers();
			names = servers.get(i);
			values[i] = names.getName();
		}
		SetUpServer.writeCommands();
		
		// load all mods
		modList = SetUpServer.loadMods();

		// Creating Object of CardLayout class.
		UnturnedGUI cl = new UnturnedGUI();

		// Function to set size of JFrame.
		cl.setSize(400, 400);

		// Function to set visibility of JFrame.
		cl.setVisible(true);

		// Function to set default operation of JFrame.
		cl.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

}
