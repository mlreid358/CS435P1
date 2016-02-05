import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;



public class task {
	private static String line = "";
	private static boolean DEBUG = false;
	private static final String dataPath = "/home/michael/workspace/workspace/CS_435_P1/sampleData/";
	//"/s/bach/d/under/mikereid/cs435/CS435P1/sampleData/";
	public static void main(String[] args) {
		if(args.length!=0 && args[args.length-1].substring(0, 1).toLowerCase().equals("d")){
			DEBUG = true;
		}
		try {
			File folder = new File(dataPath);
			String[] files = folder.list();
			if(DEBUG){
				System.out.println("files: ");
				for(int i = 0; i<files.length; i++){
					System.out.println("#"+i+" "+files[i]);
				}
			}
			
			for(int j = 0; j<files.length; j++){
				System.out.println("#"+j+" "+files[j]);
				Scanner scanny = new Scanner(new File(dataPath+files[j]));
				String peek = "";
				String author = "";
				String year = "";
				while(scanny.hasNextLine()){
					peek = scanny.nextLine().trim();
					//parse author
					if(peek.length()>6 && peek.substring(0,7).equals("Author:")){
						int i = peek.length()-1;
						while (true){
							if(peek.charAt(i) == ' ') break;
							i--;
						}
						author = peek.substring(i+1, peek.length());
						if(DEBUG)System.out.println(author);
					}//parse year
					if(peek.length()>13 && peek.substring(0,13).equals("Release Date:")){
						if(peek.substring(peek.length()-1,peek.length()).equals("]")){
													//-8 [EBOOK #FN (-4: rem .txt)
							year = peek.substring(0,peek.length()-9-(files[j].trim().length()-4));
							year = year.trim().substring(year.trim().length()-4, year.trim().length());
						}else{ //year is last 4 of trimmed string
							year = peek.substring(peek.length()-4, peek.length());
						}
						if(DEBUG)System.out.println(year);
					}
					
					//parse book
					if(peek.length()>41 && peek.substring(0,41).equals("*** START OF THIS PROJECT GUTENBERG EBOOK")){
						while(scanny.hasNextLine()){
							peek=scanny.nextLine();
							if(peek.length()>7 && peek.substring(0,7).equals("*** END")){
								break;
							}
							line+=peek+" ";
							if(DEBUG){
								System.out.println(year);
								System.out.println(author);
								System.out.println(line);
							}
							
						}//end while
					}
				}clean();
				ngram grams1 = new ngram(line,1,author,year);
				ngram grams2 = new ngram(line,2,author,year);
				grams2.writeOut("test.txt");
				//System.out.println(grams);
				scanny.close();
			}		
		}//end try
		catch (FileNotFoundException e) {
			System.out.println("File "+args[0]+" not found.");
		}

	}
	private static void clean(){
		String cleaned = "_START_ ";
		char c = '\n';
		line = line.toLowerCase();
		String sents = "";
		for(int i = 0; i<line.length(); i++){
			c = line.charAt(i);
			//strip all miscellaneous characters
			if((Character.isAlphabetic(c) || ('0'<=c && c<='9') || c==' ' ) ){// || c == '.' || c == '!' || c == '?' 
				cleaned+=c;
			}
			if((c == '.' || c == '!' || c == '?') && cleaned.length()>8){
				cleaned+=" _END_ ";
				sents+=(cleaned);
				cleaned="_START_ ";
			}
		}
		if(DEBUG) {
			
				System.out.println(sents);
			
		}line = sents;
		return;
	}
	
}
