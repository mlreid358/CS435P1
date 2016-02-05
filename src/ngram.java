import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class ngram {
	private String sentence = "";
	private int n = 0;
	private String author = "";
	private String year = "";
	private ArrayList<String> grams = new ArrayList<String>(10000);
	private static final boolean DEBUG = false;
	ngram(String s, int n, String a, String y){
		this.sentence = s;
		this.author = a;
		this.year =y;
		this.n = n;
		if(DEBUG){
			System.out.println("Author: "+this.author+"\nYear: "+this.year+"\n"+sentence);
		}
		Scanner scanny = new Scanner(sentence);
		if(n==1){//unigram
			if(!scanny.hasNext()) return;
			scanny.next();
			String temp = "";
			while(scanny.hasNext()){
				temp = scanny.next();
				if(!(temp.equals("_START_")||temp.equals("_END_")))
					grams.add(temp+"\t"+this.author+"\t"+this.year);
			}
		}else{//ngram == 2
			if(!scanny.hasNext()) return;
			String temp ="";
			String old = scanny.next();
			while(scanny.hasNext()){
				temp = scanny.next();
				if(!(old.equals("_END_")&&temp.equals("_START_") || old.equals("_START_")&&temp.equals("_END_")))
					grams.add(old+" "+temp+"\t"+this.author+"\t"+this.year);
				old = temp;
			}
		}//could achieve ngram with nested for loop
	}
	public String toString(){
		String out = "";
		for(int i = 0; i<this.grams.size(); i++){
			out+=this.grams.get(i)+"\n";
		}
		return out;
	}
	public void writeOut(final String fn){
		try {
			PrintWriter pw = new PrintWriter( new File(fn));
			for(int i = 0; i<this.grams.size(); i++){
				pw.println(this.grams.get(i));}
			pw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}
}
