import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class task {
	private static String line = "";
	private static boolean DEBUG = true;
	public static void main(String[] args) {
		if(args.length!=0 && args[args.length-1].substring(0, 1).toLowerCase().equals("d")){
			DEBUG = true;
		}
		try {
			Scanner scanny = new Scanner(new File("sample2.txt"));
			String peek = "";
			String author = "";
			String year = "";
			while(scanny.hasNextLine()){
				peek = scanny.nextLine();
				if(peek.length()>6 && peek.substring(0,7).equals("Author:")){
					int i = peek.length()-1;
					while (true){
						if(peek.charAt(i) == ' ') break;
						i--;
					}
					author = peek.substring(i+1, peek.length());
				}if(peek.length()>13 && peek.substring(0,13).equals("Release Date:")){
					year = peek.substring(peek.length()-4, peek.length());
				}
				if(peek.length()>41 && peek.substring(0,41).equals("*** START OF THIS PROJECT GUTENBERG EBOOK")){
					while(true){
						peek=scanny.nextLine();
						if(peek.substring(0,7).equals("*** END")){
							break;
						}
						line+=peek+" ";
						if(DEBUG){
							System.out.println(year);
							System.out.println(author);
							System.out.println(line);
						}
						clean();
					}//end while
				}
			}				
			scanny.close();
		}//end try
		catch (FileNotFoundException e) {
			System.out.println("File "+args[0]+" not found.");
		}

	}
	private static void clean(){
		String cleaned = "_START_ ";
		char c = '\n';
		line = line.toLowerCase();
		ArrayList<String> sents = new ArrayList<String>();
		for(int i = 0; i<line.length(); i++){
			c = line.charAt(i);
			//strip all miscellaneous characters
			if((Character.isAlphabetic(c) || ('0'<=c && c<='9') || c==' ' ) ){// || c == '.' || c == '!' || c == '?' 
				cleaned+=c;
			}
			if((c == '.' || c == '!' || c == '?') && cleaned.length()>8){
				cleaned+=" _END_";
				sents.add(cleaned);
				cleaned="_START_ ";
			}
		}
		if(DEBUG) {
			for(int i = 0; i<sents.size(); i++){
				System.out.println(sents.get(i));
			}
		}
		return;
	}
}
