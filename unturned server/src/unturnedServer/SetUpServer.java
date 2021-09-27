/**
 * Main package for the whole Server Maker
 */
package unturnedServer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This is the soul of the USM and where all the main commands run from
 * @author Nixodemus1
 * 
 */
public class SetUpServer {
	//these grab the steamcmd and creat a path as well as initialize an arraylist of servers
	static File cmd = new File("server\\steamcmd.exe");
	static String path = cmd.getAbsolutePath();
	static int serverChoice = 0;
	private static ArrayList<Servers> servers;
	
	/**
	 * This function takes whatever is currently being read and prints the results
	 * out to a file. I believe I used this to show the steamcmd text on screen
	 * in the gui
	 * @author Nixodemus1
	 * @throws IOException throws input/output error if it can not be read/found
	 * @param process The readable output of the steamcmd
	 */
	public static void printResults(Process process) throws IOException {
	    BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
	    String line = "";
	    while ((line = reader.readLine()) != null) {
	        System.out.println(line);
	    }
	    reader.close();
	}
	
	/**
	 * upon first startup, this function is called to write the command list that
	 * steamcmd will read from and run.
	 * @author Nixodemus
	 * @throws IOException will throw an input/output exception if file can not be
	 * 						found/read
	 */
	public static void writeCommands() {
		try {
		      File myObj = new File("server//Commands.txt");
		      if (myObj.createNewFile()) {
		        System.out.println("File created: " + myObj.getName());
		        FileWriter myWriter = new FileWriter(myObj);
		        myWriter.write(String.format("%s%n","//download and keep unturned updated"));
		        myWriter.write(String.format("%s%n","//"));
		        myWriter.write(String.format("%s%n","@ShutdownOnFailedCommand 1"));
		        myWriter.write(String.format("%s%n","@NoPromptForPassword 1"));
		        myWriter.write(String.format("%s%n","login anonymous"));
		        myWriter.write(String.format("%s%n","app_update 1110390"));
		        myWriter.write(String.format("%s%n","quit"));
		        myWriter.close();
		        System.out.println("Successfully wrote to the file.");
		      } else {
		        System.out.println("Command file found.");
		      }
		    } catch (IOException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
	}
	
	/**
	 * This looks for a file containing the data of all the servers you have crated
	 * if the file is not there it will create a new one.
	 * @author Nixodemus1
	 * @throws IOException will throw an input/output exception if file can not be
	 * 						read for whatever reason
	 * @return this returns the newly loaded servers to be put in the arraylist
	 */
	public static ArrayList<Servers> loadServers() {
		try {
		      File myObj = new File("server//Servers.txt");
		      servers = new ArrayList<Servers>();
		      if (myObj.createNewFile()) {
		        System.out.println("File created: " + myObj.getName());
		        Servers server = new Servers();
		        FileWriter myWriter = new FileWriter(myObj);
		        myWriter.write(String.format("%s%n","------------------------------"));
		        myWriter.write(String.format("%s%n","New"));
		        myWriter.write(String.format("%b%n", true));
		        myWriter.write(String.format("%d%n", 27015));
		        myWriter.write(String.format("%d%n", 0));
		        myWriter.write(String.format("%b%n", true));
		        myWriter.write(String.format("%s%n", "password"));
		        myWriter.write(String.format("%d%n", 24));
		        myWriter.write(String.format("%b%n", true));
		        myWriter.write(String.format("%d%n", 2));
		        myWriter.write(String.format("%d%n", 0));
		        myWriter.write(String.format("%b%n",true));
		        myWriter.write(String.format("%s%n","Welcome to my server"));
		        myWriter.write(String.format("%s%n", path));
		        myWriter.close();
		        servers.add(server);
		        System.out.println("Successfully wrote to the file.");
		      } else {
		        System.out.println("Found " + myObj.getName() + " loading...");
		        Scanner sc = new Scanner(myObj);
		        while (sc.hasNextLine()) {
		        	Servers server = new Servers();
		        	String line = sc.nextLine();
		        	server.setName(sc.next());
		    		server.setLan(Boolean.parseBoolean(sc.next()));
		    		server.setPort(sc.nextInt());
		    		server.setMap(Integer.parseInt(sc.next()));
		    		server.containsPassoword(Boolean.parseBoolean(sc.next()));
		    		server.setPassword(sc.next());
		    		server.setPlayers(Integer.parseInt(sc.next()));
		    		server.setPvp(Boolean.parseBoolean(sc.next()));
		    		server.setPerspective(Integer.parseInt(sc.next()));
		    		server.setDifficulty(Integer.parseInt(sc.next()));
		    		server.setCheats(Boolean.parseBoolean(sc.next()));
		    		line = sc.nextLine();
		    		server.setWelcome(sc.nextLine());
		    		server.setPath(sc.nextLine());
		        	servers.add(server);
		        	System.out.println(line);
		        }
		        sc.close();
		      }
		    } catch (IOException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
		return servers;
	}
	
	/**
	 * This will run the the command.txt through steam CMD on startup in order to
	 * make sure that the game is updated so you dont have to do it manually every time
	 * 
	 * @author Nixodemus1
	 * @throws IllegalArguemntException if it can not find the path to either the steamCMD
	 * 									or the command.txt list for whatever reason
	 * @throws IOException will throw an input/output error if the file could not be read/found
	 */
	public static void keepUpdated() {
		try {
			if (! cmd.exists()) {
				   throw new IllegalArgumentException("The file " + path + " does not exist");
				}
			Process process = Runtime.getRuntime().exec(path + " +runscript Commands.txt");
			// deal with OutputStream to send inputs
			process.getOutputStream();
			// deal with InputStream to get ordinary outputs
			process.getInputStream();
			// deal with ErrorStream to get error outputs
			process.getErrorStream();
			printResults(process);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This function is made to return whichever server was chosen out of the list
	 * of server that are available
	 * @param servers the arraylist of servers the player owns
	 * @return serverChoice the specific server that the player has chosen (is a server object)
	 * @author Nixodemus1
	 */
	public static int chooseServer(ArrayList<Servers> servers) {
		int serverChoice;
		Scanner sc = new Scanner(System.in);
		for (int i = 0; i < servers.size(); i++) {
        	Servers server = servers.get(i);
        	System.out.println(server.getName() + " " + i);
        }
		serverChoice = sc.nextInt();
		sc.close();
		return serverChoice;
	}
	
	/**
	 * Creates a new server.
	 * @param chosenServer a blank server object waiting to be personalized
	 * @return the path to that brand new created server
	 * @throws IOException throws input/ouput error if it can not find/read the files
	 */
	public static String newServer(Servers chosenServer) throws IOException {
		String name = chosenServer.getName();
		@SuppressWarnings("unused")
		boolean LAN = chosenServer.getLan();
		File server = new File("server\\steamapps\\common\\U3DS\\" + name + ".bat");
		String path = server.getAbsolutePath();
		if (LAN = true) {
			System.out.println("File created: " + server.getName());
		    FileWriter myWriter = new FileWriter(server);
		    try {
		    	myWriter.write(" start \"\" \"%~dp0ServerHelper.bat\" +LanServer/" + name);
		    	myWriter.close();
		    } catch (IOException e) {
				e.printStackTrace();
			}
		    System.out.println("Successfully wrote to the file.");
		} else {
			System.out.println("have you port forwarded?");
		    System.out.println("File created: " + server.getName());
		    FileWriter myWriter = new FileWriter(server);
		    try {
		    	myWriter.write(" start \"\" \"%~dp0ServerHelper.bat\" +InternetServer/" + name);
				myWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		    System.out.println("Successfully wrote to the file.");
		}
		File directory = new File("server\\steamapps\\common\\U3DS\\Servers\\" + name);
		if (!directory.exists()) {
			Process process = Runtime.getRuntime().exec(path);
			printResults(process);
		}
		return path;
	}
	
	/**
	 * This method takes the server options the player has chosen, and uses that to
	 * edit the server to reflect the chosen options. turns it into data the server
	 * can actually read. as well as updates the object with the current options
	 * 
	 * @param chosenServer the current server with all of its options
	 * @return chosenServer gives back the server after it has updated the options.
	 * @throws IOException throws input/output excpetion if it cant read/find the file
	 * @author Nixodemus1
	 */
	public static Servers choseOptions(Servers chosenServer) {
		String name = chosenServer.getName();
		int map = chosenServer.getMap();
		int port = chosenServer.getPort();
		boolean hasPassword = chosenServer.passwordChoice();
		String password = chosenServer.getPassword();
		int perspective = chosenServer.getPerspective();
		boolean cheats = chosenServer.getCheats();
		boolean pvp = chosenServer.getPvP();
		int maxPlayers = chosenServer.getPlayers();
		int difficulty = chosenServer.getDifficulty();
		String welcome = chosenServer.getWelcome();
		try {
			File myObj = new File("server//steamapps//common//U3DS//Servers//" + name + "//Server//Commands.dat");
			System.out.println("File opened: " + myObj.getName());
			FileWriter myWriter = new FileWriter(myObj);
			if (map == 0) {
				myWriter.write(String.format("%s %s%n","Map", "PEI"));
			} else if(map == 1) {
				myWriter.write(String.format("%s %s%n","Map", "Washington"));
			} else if(map == 2) {
				myWriter.write(String.format("%s %s%n","Map", "Russia"));
			} else if(map == 3) {
				myWriter.write(String.format("%s %s%n","Map", "Hawaii"));
			} else if(map == 4) {
				myWriter.write(String.format("%s %s%n","Map", "Germany"));
			} else {
				myWriter.write(String.format("%s %s%n","Map", "France"));
			}
			myWriter.write(String.format("%s %s%n","Port", port));
			myWriter.write(String.format("%s %s%n","Name", name));
			if (hasPassword == false) {
				myWriter.write(String.format("%s%n",""));
			} else {
				myWriter.write(String.format("%s %s%n","Password", password));
			}
			if (perspective == 0) {
				myWriter.write(String.format("%s %s%n","Perspective", "First"));
			} else if(perspective == 1) {
				myWriter.write(String.format("%s %s%n","Perspective", "Third"));
			} else if(perspective == 2) {
				myWriter.write(String.format("%s %s%n","Perspective", "Both"));
			} else {
				myWriter.write(String.format("%s %s%n","Perspective", "Vehicle"));
			}
			if (cheats == false) {
				myWriter.write(String.format("%s %s%n","Cheats", "off"));
			} else {
				myWriter.write(String.format("%s %s%n","Cheats", "on"));
			}
			if (pvp == true) {
				myWriter.write(String.format("%s%n",""));
			} else {
				myWriter.write(String.format("%s%n","pve"));
			}
			myWriter.write(String.format("%s %s%n","maxplayers", maxPlayers));
			if (difficulty == 0) {
				myWriter.write(String.format("%s %s%n","mode", "Easy"));
			} else if (difficulty == 1){
				myWriter.write(String.format("%s %s%n","mode", "Normal"));
			} else if (difficulty == 2) {
				myWriter.write(String.format("%s %s%n","mode", "Hard"));
			} else {
				myWriter.write(String.format("%s %s%n","mode", "Gold"));
			}
			myWriter.write(String.format("%s %s%n","welcome", welcome));
			myWriter.write(String.format("%s%n","Sync"));
			myWriter.close();
			System.out.println("Successfully wrote to the file.");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return chosenServer;
	}
	
	/**
	 * saves all the servers the player has made with its updated options to a file
	 * so it can be read later.
	 * @param servers array list of saved servers that will be saved to file
	 * @throws IOException input/output exception for when file cant be found/read
	 * @author Nixodemus1
	 */
	public static void saveServer(ArrayList<Servers> servers) {
		File myObj = new File("server//Servers.txt");
	    System.out.println("File opening: " + myObj.getName());
        try {
        FileWriter myWriter = new FileWriter(myObj);
        for (int i = 0; i < servers.size(); i++) {
        	Servers server = servers.get(i);
        	myWriter.write(String.format("%s%n","------------------------------"));
        	myWriter.write(String.format("%s%n", server.getName()));
        	myWriter.write(String.format("%b%n", server.getLan()));
        	myWriter.write(String.format("%d%n", server.getPort()));
        	myWriter.write(String.format("%d%n", server.getMap()));
        	myWriter.write(String.format("%b%n", server.passwordChoice()));
        	myWriter.write(String.format("%s%n", server.getPassword()));
        	myWriter.write(String.format("%d%n", server.getPlayers()));
        	myWriter.write(String.format("%b%n", server.getPvP()));
        	myWriter.write(String.format("%d%n", server.getPerspective()));
        	myWriter.write(String.format("%d%n", server.getDifficulty()));
        	myWriter.write(String.format("%b%n", server.getCheats()));
        	myWriter.write(String.format("%s%n", server.getWelcome()));
        	myWriter.write(String.format("%s%n", server.getPath()));
        }
        myWriter.close();
        } catch (IOException e) {
        	e.printStackTrace();
        }
        System.out.println("Successfully saved your servers.");
	}
	
	/**
	 * launches the server. Its right there in the name, I dont know what you 
	 * were expecting
	 * @param chosenServer the server that will be launched
	 * @author Nixodemus1
	 */
	public static void launchServer(Servers chosenServer) {
		String path = chosenServer.getPath();
		Process process;
		try {
			process = Runtime.getRuntime().exec(path);
			printResults(process);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
