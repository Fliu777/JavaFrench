import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class SubMap {
	// private TreeMap accent= new TreeMap();
	private HashMap<String, Integer> accent = new HashMap<String, Integer>();
	
	public SubMap(String a) {
		accent.put(a, 1);
	}

	public void update(String a) {
		if (accent.get(a) != null) {
			accent.put(a, accent.get(a) + 1);
		} else {
			accent.put(a, 1);
		}

	}

	public HashMap<String, Integer> getHash() {
		return accent;

	}

}

public class GetAccents {
	static HashMap<String, SubMap> ReferenceTable = new HashMap<String, SubMap>();

	public static boolean FindAccent(String temp) {
		for (int i = 0; i < temp.length(); i++) {
			if (!(temp.charAt(i) <= 'z' && temp.charAt(i) >= 'a' || temp
					.charAt(i) <= 'Z' && temp.charAt(i) >= 'A')) {
				return true;
			}
		}
		return false;

	}

	public static int LocateAccent(String temp) {
		for (int i = 0; i < temp.length(); i++) {
			if (!(temp.charAt(i) <= 'z' && temp.charAt(i) >= 'a' || temp
					.charAt(i) <= 'Z' && temp.charAt(i) >= 'A')) {
				return i;
			}
		}
		return -1;

	}

	public static void ConstructFreq(String file) throws Exception {
		File fileDir = new File(file);

		BufferedReader in = new BufferedReader(new InputStreamReader(
				new FileInputStream(fileDir), "UTF-8"));
		
		//printer
	/*	  PrintWriter out1 = null; try { out1 = new PrintWriter(new
		  File("Accented.txt"), "UTF-8"); } catch (FileNotFoundException |
		  UnsupportedEncodingException e) { // TODO Auto-generated catch block
		  e.printStackTrace(); }*/

		String str;
		String t;
		char tt;
		String t1, t2;
		boolean flag=false;
		while ((str = in.readLine()) != null) {
			String[] temp = str.split(" |-|\\.|_|'|,|;|\\?");
			for (int i = 0; i < temp.length; i++) {
				t = temp[i].toLowerCase();
				flag=true;
				for (int j = 0; j < t.length(); j++) {
					tt = t.charAt(j);
					if (tt == 'à' || tt == 'â' || tt == 'ä' || tt == 'æ'
							|| tt == 'ç' || tt == 'é' || tt == 'è' || tt == 'ê'
							|| tt == 'ë' || tt == 'î' || tt == 'ï' || tt == 'ô'
							|| tt == 'œ' || tt == 'ù' || tt == 'û' || tt == 'ü') {
						t1 = t.substring(0, j);
						if (flag){
							//out1.write(t+"\n");
							flag=false;
						}


						// last letter
						if ((j + 1) == t.length()) {
							t2 = t.substring(j, j + 1) + " ";
						} else {
							t2 = t.substring(j, j + 2);
						}
						if (ReferenceTable.get(t1) != null) {
							ReferenceTable.get(t1).update(t2);
						} else {
							ReferenceTable.put(t1, new SubMap(t2));
						}
					}
				}
			}
		}
		//out1.close();
		/*for (Map.Entry<String, SubMap> entry : ReferenceTable.entrySet()) {
			System.out.println(entry.getKey());
			for (Map.Entry<String, Integer> entry2 : entry.getValue().getHash()
					.entrySet()) {
				System.out.print(entry2.getKey() + " ");
				System.out.println(entry2.getValue());
			}
		}*/

	}

	public static void main(String[] args) {
		String str;
		int count = 0;
		ArrayList<String> stringList = new ArrayList<String>();

		try {
			ConstructFreq("Accented.txt");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		while (true) {
			Scanner sc = new Scanner(System.in);
			System.out.println("Enter query");
			String query = sc.next();
			for (Map.Entry<String, Integer> entry2 : ReferenceTable.get(query)
					.getHash().entrySet()) {
				System.out.print(entry2.getKey() + " ");
				System.out.println(entry2.getValue());
			}
		}

		/*
		 * try { File fileDir = new File("fr.txt");
		 * 
		 * BufferedReader in = new BufferedReader(new InputStreamReader( new
		 * FileInputStream(fileDir), "UTF-8"));
		 * 
		 * while ((str = in.readLine()) != null) { if (FindAccent(str)) {
		 * stringList.add(str); } count++; }
		 * 
		 * in.close(); } catch (UnsupportedEncodingException e) {
		 * System.out.println(e.getMessage()); } catch (IOException e) {
		 * System.out.println(e.getMessage()); } catch (Exception e) {
		 * System.out.println(e.getMessage()); }
		 * 
		 * PrintWriter out1 = null; try { out1 = new PrintWriter(new
		 * File("Accented.txt"), "UTF-8"); } catch (FileNotFoundException |
		 * UnsupportedEncodingException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } System.out.println(count); for (int i = 0; i <
		 * stringList.size(); i++) { out1.write(stringList.get(i) + "\n"); }
		 */

	}

}
