import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;


class SubMap{
	private HashMap<String, Integer> accent = new HashMap<String, Integer>();
	public SubMap(String a){
		accent.put(a, 1);
	}
	public void update(String a){
		if (accent.get(a)!=null){
			accent.put(a, accent.get(a)+1);
		}
		else{
			accent.put(a, 1);
		}

	}
	public HashMap<String, Integer> getHash(){
		return accent;

	}
	
}

public class GetAccents {
	


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
	
	public static void ConstructFreq() throws Exception{
		File fileDir = new File("pg19657.txt");
		   
		HashMap<String, SubMap> ReferenceTable = new HashMap<String, SubMap>();
			
		BufferedReader in = new BufferedReader(new InputStreamReader(
				new FileInputStream(fileDir), "UTF-8"));
		
		String str;
		int place=0;
		String t;
		char tt;
		String t1, t2;
		while ((str = in.readLine()) != null) {
			String[] temp=str.split(" ");
			for (int i=0;i<temp.length;i++){
				t=temp[i].toLowerCase();
				for(int j=0;j<temp[i].length();j++){
					tt=temp[i].charAt(j);
					if (tt=='à' || tt=='â' || tt=='ä' || tt=='æ' || tt== 'ç' || tt=='é' ||tt=='è' ||tt=='ê' ||tt=='ë' ||tt=='î' ||tt== 'ï' ||tt=='ô' ||tt=='œ'||tt=='ù' ||tt=='û'||tt=='ü'){
						t1=temp[i].substring(0, j);
						t2=temp[i].substring(j, j+1);
						if (ReferenceTable.get(t1)!=null){
							ReferenceTable.get(t1).update(t2);
						}
						else{
							ReferenceTable.put(t1, new SubMap(t2));
						}
					}
				}	
			}
		}
		for (Map.Entry<String, SubMap> entry : ReferenceTable.entrySet()) {
		    System.out.println(entry.getKey());
		    for (Map.Entry<String, Integer> entry2 : entry.getValue().getHash().entrySet()){
		    	System.out.print(entry2.getKey()+" ");
		    	System.out.println(entry2.getValue());
		    }
		}


	}
	


	public static void main(String[] args) {
		String str;
		int count = 0;
		ArrayList<String> stringList = new ArrayList<String>();
		
		try {
			ConstructFreq();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/*try {
			File fileDir = new File("fr.txt");

			BufferedReader in = new BufferedReader(new InputStreamReader(
					new FileInputStream(fileDir), "UTF-8"));

			while ((str = in.readLine()) != null) {
				if (FindAccent(str)) {
					stringList.add(str);
				}
				count++;
			}

			in.close();
		} catch (UnsupportedEncodingException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		PrintWriter out1 = null;
		try {
			out1 = new PrintWriter(new File("Accented.txt"), "UTF-8");
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(count);
		for (int i = 0; i < stringList.size(); i++) {
			out1.write(stringList.get(i) + "\n");
		}
		*/

	}

}
