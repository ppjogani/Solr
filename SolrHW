package solrAssignment;

import java.io.*;
import java.util.*;
import java.util.regex.*;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.pdf.PDFParser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;



public class SolrHW {
  static MySQLAccess dao = new MySQLAccess();
	
	public static void main(String[] args) throws Exception {
		
	    dao.init();
	    
		int num_files=0;

		File pdfdir = new File("vault1");
		File[] pdfs = pdfdir.listFiles(new PDFFilenameFilter());
		for (File pdf:pdfs) {
			num_files++;
			processfile(pdf);
			//if(num_files==1)break;
		}
		
		dao.close();
	}
	
	public static  void processfile(File f) throws Exception{
		/***** YOUR CODE GOES HERE *****/
		// to update the log file with a search hit, use:
		// updatelog(keyword,f.getName());
		//PDFParser();

		             HashMap<String,Integer> map = new HashMap<String,Integer>();
		             Map<String, Integer> testMap = new HashMap<String, Integer>(1000);
		         // ValueComparator bvc =  new ValueComparator(map);
		          //TreeMap<String,Integer> sorted_map = new TreeMap<String,Integer>(bvc);		   

		            System.out.println("Processing file: " +f);
		            ContentHandler handler = new BodyContentHandler(1000*1024*1024);;
		            String content;
		            //Text extraction
		            InputStream input = new FileInputStream(f);
		            PDFParser parser = new PDFParser();
		            parser.parse(input, handler,new Metadata(), new ParseContext());
		            input.close();
		            content = handler.toString();
		            
		        //  System.out.println(content);
		            content = content.replaceAll("[^a-zA-Z]", " ");
		            content = content.replaceAll(",", " ");
		            content = content.replaceAll("\\.", " ");
		            content = content.replaceAll(":", " ");
		            content = content.replaceAll(";", " ");
		            content = content.replaceAll("\\-", " ");
		            
		            content = content.replaceAll("\\s+", " ");
		            content = content.replaceAll("\\b[a-zA-Z]\\b", "");
		            	          
		            content = content.toLowerCase();
		            
		        //    String w
		            
		    		Set<String> uniqueStrings = new HashSet<String>();
		    		BufferedReader reader = new BufferedReader(new FileReader("stop-words-english1.txt"));
		    		// List<String> words = new ArrayList<String>();
		    		String stopwords="(";
		    		String line;
		    		while ((line = reader.readLine()) != null) { 
		    			uniqueStrings.add(line);
		    		}

		    		for (String s: uniqueStrings){
		    			stopwords+=("\\b+"+s+"\\b+|");
		    		}

		    		stopwords+=")";
		    		
//		    		System.out.println("regex"+stopwords);
		    		content=content.replaceAll(stopwords, "");
		    		
		    		//System.out.println(content);
		            String words[] = content.split("\\s+");
		            for (String e:words){
		            	//System.out.println(e);
		            	if (e=="" || e ==" ") System.out.println("this has nullll or space");
		            }

		            for(int i=0;i<words.length;i++){
		            //System.out.println("The words    "+words[i]);
		            if(map.containsKey(words[i])){
		                map.put(words[i], map.get(words[i])+1);
		            }
		            else
		            {
		               map.put(words[i], 1);
		            }
		        }
		            String key;
		            testMap = sortByValue(map);
		            Set set = testMap.entrySet();
		            Iterator it = set.iterator();
		            while(it.hasNext()){
		                Map.Entry me = (Map.Entry)it.next();
		                key=(String) me.getKey();
		               // System.out.println(key);
		                if (dao.readDataBase(f.toString(), key)) break;
//		                System.out.print(me.getKey() + ": ");
//		                System.out.println(me.getValue());    
		            }
		}

		public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue( Map<K, V> map )
		{
		List<Map.Entry<K, V>> list =
		   new LinkedList<Map.Entry<K, V>>( map.entrySet() );
		Collections.sort( list, new Comparator<Map.Entry<K, V>>()
		{
		   public int compare( Map.Entry<K, V> o1, Map.Entry<K, V> o2 )
		   {
		       return (o2.getValue()).compareTo( o1.getValue() );
		   }
		} );

		Map<K, V> result = new LinkedHashMap<K, V>();
		for (Map.Entry<K, V> entry : list)
		{
		   result.put( entry.getKey(), entry.getValue() );
		}
		return result;
		}
		
		
		
		static class PDFFilenameFilter implements FilenameFilter {
			private Pattern p = Pattern.compile(".*\\.pdf",Pattern.CASE_INSENSITIVE);
			public boolean accept(File dir, String name) {
				Matcher m = p.matcher(name);
				return m.matches();
			}
		}

}
