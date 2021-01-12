/**
 * 
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
 * @author nchow
 *
 */
public class SetUpServer {
	static File cmd = new File("server\\steamcmd.exe");
	static String path = cmd.getAbsolutePath();
	static int serverChoice = 0;
	private static ArrayList<Servers> servers;
	
	
	public static void printResults(Process process) throws IOException {
	    BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
	    String line = "";
	    while ((line = reader.readLine()) != null) {
	        System.out.println(line);
	    }
	    reader.close();
	}
	
	
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
	
	public static String newServer(Servers chosenServer) throws IOException {
		String name = chosenServer.getName();
		boolean LAN = chosenServer.getLan();
		File server = new File("server\\steamapps\\common\\U3DS\\" + name + ".bat");
		String path = server.getAbsolutePath();
		if (LAN = true) {
			if (server.createNewFile()) {
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
		        System.out.println("File already exists.");
		      }
		} else {
			Boolean portforward = false;
			System.out.println("have you port forwarded?");
			portforward = LAN;
			if (server.createNewFile() && portforward == true) {
		        System.out.println("File created: " + server.getName());
		        FileWriter myWriter = new FileWriter(server);
		        try {
					myWriter.write(" start \"\" \"%~dp0ServerHelper.bat\" +InternetServer/" + name);
					myWriter.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		        System.out.println("Successfully wrote to the file.");
		      } else {
		        System.out.println("File already exists.");
		      }
		}
		File directory = new File("server\\steamapps\\common\\U3DS\\Servers\\" + name);
		if (!directory.exists()) {
			Process process = Runtime.getRuntime().exec(path);
			printResults(process);
		}
		return path;
	}
	
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
