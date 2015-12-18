import java.util.HashSet;
import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.BufferedReader;

public class Keywords {
	private File file;
	private HashSet<String> key;
	private BufferedReader reader;
	public Keywords(){
		file = new File("keywords.dat");
		key = new HashSet<String>();
		try{
			reader = new BufferedReader(new FileReader(file));
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}
		try{
			String s;
			while((s = reader.readLine()) != null){
				key.add(s);
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public boolean isKeyword(String word){
		System.out.println(word);
		if(key.contains(word))
			return true;
		else
			return false;
	}
}
