package unturnedServer;

import java.awt.*; 
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent; 

//class extends JFrame and implements actionlistener 
@SuppressWarnings("serial")
public class UnturnedGUI extends JFrame{
	 
	//cardlayout and container declerations
    CardLayout card; 
    Container c;
    static boolean newServer;
    
    //server properties
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
    
    
    //misc. needed 
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
    
    static File cmd = new File("server\\steamcmd.exe");
	static String path = cmd.getAbsolutePath();
	private static ArrayList<Servers> servers;
	private Servers server;
	private static String values[];
  
	public UnturnedGUI() {
		getContentPane().setBackground(Color.GREEN);
		setForeground(new Color(0, 100, 0));
		File icon = new File("Images\\icon.png");
		setIconImage(Toolkit.getDefaultToolkit().getImage(icon.getAbsolutePath()));
		setTitle("Unturned Server Builder");
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
        welcomeMessage.setWrapStyleWord(true);
        welcomeMessage.setToolTipText("welcome mesage");
        welcomeMessage.setText("Thank you for using Server builder! please make sure you have a working internet connection and be patient as the program will have to download many files first time it launches! Please follow instructions exactly and you will have your own working unturned servers in no time!");
        welcomeMessage.setRows(5);
        welcomeMessage.setLineWrap(true);
        welcomeMessage.setColumns(20);
        card_1.add(welcomeMessage, BorderLayout.CENTER);
        
        JButton startButton = new JButton("Start");
        startButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		TextAreaLogProgram.printLog(cmd, path);
        		card.next(c);
        	}
        });
        startButton.setToolTipText("Start the program!");
        card_1.add(startButton, BorderLayout.SOUTH);
        
        JPanel card1 = new JPanel();
        getContentPane().add(card1, "name_4426547071500");
        card1.setLayout(new BorderLayout(0, 0));
        
        JLabel lblNewLabel = new JLabel("Wait for the consol to say \"you may press continue\"");
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        card1.add(lblNewLabel, BorderLayout.NORTH);
        
        JScrollPane scrollPane_2 = new JScrollPane();
        card1.add(scrollPane_2, BorderLayout.CENTER);
        
        // Create an instance of javax.swing.JTextArea control
        JTextArea textPane = new JTextArea();
        scrollPane_2.setViewportView(textPane);
        textPane.setEditable(false);
        PrintStream printStream = new PrintStream(new CustomOutputStream(textPane));
        // re-assigns standard output stream and error output stream
        System.setOut(printStream);
        System.setErr(printStream);
        
        JPanel panel = new JPanel();
        card1.add(panel, BorderLayout.SOUTH);
        
        JButton continue1 = new JButton("Continue");
        panel.add(continue1);
        continue1.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		card.next(c);
        	}
        });
        
        JPanel card2 = new JPanel();
        card2.setToolTipText("chose your server!");
        getContentPane().add(card2, "name_4644937493500");
        card2.setLayout(new BorderLayout(0, 0));
        
        JLabel lblserverChooser = new JLabel("Please choose a server");
        lblserverChooser.setHorizontalAlignment(SwingConstants.CENTER);
        card2.add(lblserverChooser, BorderLayout.NORTH);
        
        JScrollPane scrollPane = new JScrollPane();
        card2.add(scrollPane, BorderLayout.CENTER);
        
        JList<String> list = new JList<String>();
        list.setModel(new AbstractListModel<String>() {
        	public int getSize() {
        		return values.length;
        	}
        	public String getElementAt(int index) {
        		return values[index];
        	}
        });
        
        scrollPane.setViewportView(list);
        list.setToolTipText("choose your server!");
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setBorder(new LineBorder(new Color(0, 0, 0)));
        
        JButton continue2 = new JButton("Continue");
        continue2.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
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
        card2.add(continue2, BorderLayout.SOUTH);
        
        JPanel card3 = new JPanel();
        getContentPane().add(card3, "name_4760098863600");
        card3.setLayout(new BorderLayout(0, 0));
        
        JLabel lblNewLabel_1 = new JLabel("please name your new server");
        lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
        card3.add(lblNewLabel_1, BorderLayout.NORTH);
        
        JPanel panel_1 = new JPanel();
        card3.add(panel_1, BorderLayout.CENTER);
        panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        
        textField = new JTextField();
        textField.setToolTipText("name your new server!");
        textField.setText("server name here");
        textField.setColumns(10);
        panel_1.add(textField);
        
        JButton continue3 = new JButton("Continue");
        continue3.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		server.setName(textField.getText());
        		card.next(c);
        	}
        });
        card3.add(continue3, BorderLayout.SOUTH);
        
        JPanel card4 = new JPanel();
        getContentPane().add(card4, "name_4867586771400");
        card4.setLayout(new BorderLayout(0, 0));
        
        JScrollPane scrollPane_1 = new JScrollPane();
        card4.add(scrollPane_1, BorderLayout.CENTER);
        
        JTextArea txtrYouShouldSee = new JTextArea();
        scrollPane_1.setViewportView(txtrYouShouldSee);
        txtrYouShouldSee.setWrapStyleWord(true);
        txtrYouShouldSee.setText("You should see your server launch to load its files. wait till the level is done downloading (it will say 'level loaded 100%') and type \"shut down\" into the consol before continuing. ");
        txtrYouShouldSee.setRows(5);
        txtrYouShouldSee.setLineWrap(true);
        txtrYouShouldSee.setColumns(20);
        
        JButton Continue4 = new JButton("Continue");
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
        getContentPane().add(card5, "name_1969951988600");
        card5.setLayout(new BorderLayout(0, 0));
        
        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setToolTipText("options");
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        card5.add(tabbedPane);
        
        JPanel optionsName = new JPanel();
        tabbedPane.addTab("Name", null, optionsName, null);
        
        JLabel nameLabel = new JLabel("server name");
        optionsName.add(nameLabel);
        
        txtName = new JTextField();
        txtName.setText(serverName);
        optionsName.add(txtName);
        txtName.setColumns(10);
        
        JLabel lblNewLabel_5 = new JLabel("please make name more than 5 letters");
        optionsName.add(lblNewLabel_5);
        
        JPanel optionsPort = new JPanel();
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
        txtPort.setMaximumSize(new Dimension(2147483647, 20));
        txtPort.setText("27015");
        optionsPort.add(txtPort);
        txtPort.setColumns(10);
        
        JSeparator separator = new JSeparator();
        optionsPort.add(separator);
        
        JTextArea txtrIfPlayingOver = new JTextArea();
        txtrIfPlayingOver.setWrapStyleWord(true);
        txtrIfPlayingOver.setText("If playing over internet instead of LAN, please make sure the port is already port forwarded. you can look up on google how to do that. The default port is 27015");
        txtrIfPlayingOver.setLineWrap(true);
        optionsPort.add(txtrIfPlayingOver);
        
        JPanel optionsMap = new JPanel();
        tabbedPane.addTab("Map", null, optionsMap, null);
        
        JScrollPane scrollPane_4 = new JScrollPane();
        scrollPane_4.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane_4.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        optionsMap.add(scrollPane_4);
        
        mapList = new JList<String>();
        scrollPane_4.setViewportView(mapList);
        mapList.setValueIsAdjusting(true);
        mapList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        mapList.setModel(new AbstractListModel<String>() {
        	String[] values = new String[] {"Pei", "Washington", "Russia", "Hawaii", "Germany", "France"};
        	public int getSize() {
        		return values.length;
        	}
        	public String getElementAt(int index) {
        		return values[index];
        	}
        });
        
        JPanel optionsPassword = new JPanel();
        tabbedPane.addTab("Password", null, optionsPassword, null);
        optionsPassword.setLayout(new BoxLayout(optionsPassword, BoxLayout.Y_AXIS));
        
        passwordChecker = new JCheckBox("password protected?");
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
        lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
        optionsPassword.add(lblNewLabel_2);
        
        passwordField = new JTextField("password");
        passwordField.setMaximumSize(new Dimension(2147483647, 20));
        optionsPassword.add(passwordField);
        
        JPanel optionsGamemode = new JPanel();
        tabbedPane.addTab("GameMode", null, optionsGamemode, null);
        
        JLabel lblNewLabel_3 = new JLabel("max players?");
        lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
        
        maxPlayers = new JSlider();
        maxPlayers.addChangeListener(new ChangeListener() {
        	public void stateChanged(ChangeEvent e) {
        		JSlider source = (JSlider)e.getSource();
        		if (!source.getValueIsAdjusting()) {
        			players = (int)source.getValue();;
        		}
        	}
        });
        maxPlayers.setPaintLabels(true);
        maxPlayers.setMajorTickSpacing(5);
        maxPlayers.setMinorTickSpacing(1);
        maxPlayers.setPaintTicks(true);
        maxPlayers.setMaximum(25);
        
        chckbxNewCheckBox_1 = new JCheckBox("PvP?");
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
        view.setModel(new DefaultComboBoxModel<String>(new String[] {"first person", "third person", "both", "vehicle"}));
        
        difficulty = new JComboBox<String>();
        difficulty.setModel(new DefaultComboBoxModel<String>(new String[] {"easy", "medium", "hard"}));
        
        chckbxCheats = new JCheckBox("cheats?");
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
        tabbedPane.addTab("Welcome", null, optionsWelcome, null);
        optionsWelcome.setLayout(new BoxLayout(optionsWelcome, BoxLayout.Y_AXIS));
        
        JLabel lblNewLabel_4 = new JLabel("write welcome message!");
        lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
        optionsWelcome.add(lblNewLabel_4);
        
        JScrollPane scrollPane_5 = new JScrollPane();
        optionsWelcome.add(scrollPane_5);
        
        welcomePane = new JEditorPane();
        scrollPane_5.setViewportView(welcomePane);
        welcomePane.setText(welcome);
        
        
        JPanel panel_2 = new JPanel();
        card5.add(panel_2, BorderLayout.SOUTH);
        
        JButton wiki = new JButton("wiki");
        wiki.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		openWebpage("https://nodecraft.com/support/games/unturned/configuring-your-unturned-server-commands-dat");
        	}
        });
        panel_2.add(wiki);
        
        JButton btnSave = new JButton("Save");
        btnSave.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		if (!txtPort.getText().matches("^[0-9]*$") || txtName.getText().length() < 5) {
        			txtrIfPlayingOver.setText("ERROR: you either put letters in your port, or your server name is less than 5 characters long");
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
        getContentPane().add(card5_1, "name_48160497111000");
        card5_1.setLayout(new BoxLayout(card5_1, BoxLayout.Y_AXIS));
        
        JScrollPane scrollPane_6 = new JScrollPane();
        card5_1.add(scrollPane_6);
        
        JTextPane printResults = new JTextPane();
        printResults.setText("press refresh to check results");
        scrollPane_6.setViewportView(printResults);
        
        JPanel panel_3 = new JPanel();
        card5_1.add(panel_3);
        
        JButton btnBackButton = new JButton("Back");
        btnBackButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		
        		card.previous(c);
        	}
        });
        panel_3.add(btnBackButton);
        
        JButton finalContinue = new JButton("Continue");
        finalContinue.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		saveOptions(servers, server);
        		card.next(c);
        	}});
        panel_3.add(finalContinue);
        
        JButton btnRefresh = new JButton("Refresh");
        btnRefresh.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		printResults.setText("name = " + serverName
                		+ "\nLan = " + LAN
                		+ "\nport = " + port
                		+ "\nmap = " + map
                		+ "\nhas password = " + hasPassword
                		+ "\npassword = " + passText
                		+ "\nmax players = " + players
                		+ "\nPvP = " + PvP
                		+ "\nview = " + perspective
                		+ "\ndifficulty = " + difficult
                		+ "\ncheats = " + cheats
                		+ "\nwelcome = " + welcome);
        	}
        });
        panel_3.add(btnRefresh);
        
        JPanel card6 = new JPanel();
        getContentPane().add(card6, "name_1969961176800");
        card6.setLayout(new BorderLayout(0, 0));
        
        JScrollPane scrollPane_3 = new JScrollPane();
        card6.add(scrollPane_3);
        
        JTextArea finalMessage = new JTextArea();
        finalMessage.setWrapStyleWord(true);
        finalMessage.setLineWrap(true);
        finalMessage.setText("And your server is up and running! thats all you need to do! you may now press the \"End\" button to exit the set up! Thank you for downloading and using unturned server maker! remember that you can type \"shutdown\" to close your server. and above there is a link to the wiki for server commands! Have fun!");
        finalMessage.setColumns(20);
        finalMessage.setRows(5);
        scrollPane_3.setViewportView(finalMessage);
        
        JButton endButton = new JButton("End");
        endButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		SetUpServer.saveServer(servers);
        		SetUpServer.launchServer(server);
        		System.exit(0);
        	}
        });
        card6.add(endButton, BorderLayout.SOUTH);
        
        JButton serverControls = new JButton("server commands!");
        serverControls.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		openWebpage("https://unturned.fandom.com/wiki/Server_Commands");
        	}
        });
        card6.add(serverControls, BorderLayout.NORTH);
        
    }
	
	public static void openWebpage(String urlString) {
        try {
            Desktop.getDesktop().browse(new URL(urlString).toURI());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	public static ArrayList<Servers> saveOptions(ArrayList<Servers> servers, Servers server) {
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
		SetUpServer.choseOptions(server);
		if (newServer == true) {
			servers.add(server);
		} else {
			
		}
		return servers;
	}
	
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
	
	public static void main(String[] args) {
		//load all servers and write commands on open
		servers = SetUpServer.loadServers();
		values = new String[servers.size()];
    	for (int i = 0; i < values.length; i++) {
    		Servers names = new Servers();
    		names = servers.get(i);
    		values[i] = names.getName();
    	}
    	SetUpServer.writeCommands();
          
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
