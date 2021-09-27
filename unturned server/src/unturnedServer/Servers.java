/**
 * 
 */
package unturnedServer;


/**
 * This is the object and how I hold all the server information as well as the
 * get/set methods. DO NOT CHANGE unless you're VERY familiar with how everything
 * works and fits together
 * 
 * @author nchow
 *
 */
public class Servers {
	
	private String path;
	private String name;
	private boolean LAN;
	private int port = 27015;
	private int map;
	private boolean hasPassword;
	private int maxPlayers;
	private boolean pvp;
	private int difficulty;
	private String password;
	private int perspective;
	private boolean cheats;
	private String welcome;
	
	public Servers() {
		name = "New";
		LAN = true;
		port = 27015;
		map = 0;
		hasPassword = false;
		password = "password";
		maxPlayers = 24;
		pvp = true;
		perspective = 2;
		difficulty = 0;
		cheats = true;
		welcome = "Welcome to my server";
		path = "server\\steamapps\\common\\U3DS\\" + name + ".bat";
	}
	
	public void setName(String userName) {
		this.name = userName;
	}
	
	public void setLan(boolean userLan) {
		this.LAN = userLan;
	}
	
	public void setPort(int userPort) {
		this.port = userPort;
	}
	
	public void setMap(int userMap) {
		this.map = userMap;
	}
	
	public void containsPassoword(boolean userchoice) {
		this.hasPassword = userchoice;
	}
	
	public void setPassword(String userPassword) {
		this.password = userPassword;
	}
	
	public void setPlayers(int userPlayers) {
		this.maxPlayers = userPlayers;
	}
	
	public void setPvp(boolean userPvP) {
		this.pvp = userPvP;
	}
	
	public void setPerspective(int userPerspective) {
		this.perspective = userPerspective;
	}
	
	public void setDifficulty(int userDifficulty) {
		this.difficulty = userDifficulty;
	}
	
	public void setCheats(boolean userCheats) {
		this.cheats = userCheats;
	}
	
	public void setWelcome(String userWelcome) {
		this.welcome = userWelcome;
	}
	
	public void setPath(String userPath) {
		this.path = userPath;
	}
	
	public String getName() {
		return name;
	}
	
	public boolean getLan() {
		return LAN;
	}
	
	public int getPort() {
		return port;
	}
	
	public int getMap() {
		return map;
	}
	
	public boolean passwordChoice() {
		return hasPassword;
	}
	
	public String getPassword() {
		return password;
	}
	
	public int getPlayers() {
		return maxPlayers;
	}
	
	public boolean getPvP() {
		return pvp;
	}
	
	public int getPerspective() {
		return perspective;
	}
	
	public int getDifficulty() {
		return difficulty;
	}
	
	public boolean getCheats() {
		return cheats;
	}
	
	public String getWelcome() {
		return welcome;
	}
	
	public String getPath() {
		return path;
	}

}
