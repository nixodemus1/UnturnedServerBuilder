package unturnedServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * this creates the workshop object that will hold all the info on the mods you
 * would like installed. also includes all getters/setters once again, unless
 * you are very familiar with how it works, do not attempt to change
 * 
 * @author Nixodemus1
 * 
 */
public class WorkShop {
	private String URL = "";
	private long idNumber = 0L;
	private boolean workShopMap = false;
	private String name = "";

	String getURL() {
		return this.URL;
	}

	long getID() {
		return this.idNumber;
	}

	boolean getMap() {
		return this.workShopMap;
	}

	String getName() {
		return this.name;
	}

	void setURL(String input) {
		this.URL = input;
	}

	void setID(Long ID) {
		this.idNumber = ID;
	}

	void setMap(boolean map) {
		this.workShopMap = map;
	}

	void setName(String modName) {
		this.name = modName;
	}

	/**
	 * This function is supposed to get the info from the URL to the steam workshop
	 * page, parse it so it can be read by the program, and then get/set all the
	 * info for the object
	 * 
	 * @author Nixodemus1
	 * @throws MalformedURLException if anything is wrong with the URL, throw
	 *                               exception
	 * @throws IOException           throws input/output error if it can not be
	 *                               read/found
	 * @param site  the copied URL leading to the steam page of the workshop mod
	 * @param isMap boolean telling us if the workshop mod is a map or not
	 */
	static WorkShop getAll(String site, boolean isMap) throws Throwable {
		boolean flag = false;
		WorkShop mod = new WorkShop();
		mod.setURL(site);
		String[] str = site.split("=");
		if (str[1].contains("&")) {
			String[] name = str[1].split("&");
			mod.setID(Long.parseLong(name[0]));
		} else {
			mod.setID(Long.parseLong(str[1]));
		}
		mod.setMap(isMap);
		URL url = new URL(site);
		InputStream is = url.openStream();
		try {
			Throwable throwable = null;
			try (BufferedReader br = new BufferedReader(new InputStreamReader(is));) {
				String line;
				while ((line = br.readLine()) != null) {
					if (!line.contains("<title>") || flag)
						continue;
					String[] titleLine = line.split("::");
					String modName = titleLine[1];
					String title = modName.substring(0, modName.length() - 8);
					mod.setName(title);
					flag = true;
				}
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
			throw new MalformedURLException("URL is malformed!!");
		} catch (IOException e) {
			e.printStackTrace();
			throw new IOException();
		}
		return mod;
	}

	public String toString() {
		return String.valueOf(this.name) + " map: " + this.workShopMap + " " + this.idNumber;
	}
}
