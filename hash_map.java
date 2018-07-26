package xml;

import java.util.*;
import java.util.HashMap;
import javafx.util.Pair;


public class hash_map {
	
	private HashMap <String, List<Pair<String, ArrayList<String>>>> mapping = new HashMap<> ();
	
	public void Construct(String container, String function, ArrayList<String> args){
		
		ArrayList<String> temp = new ArrayList<String> (args);
		Pair<String, ArrayList<String> > pair_k = new Pair<> (function, temp);
		
		  if (temp.contains(container))
	      {
	         List<Pair<String, ArrayList<String>>> values = mapping.get(container);
	         values.add(pair_k);
	         mapping.put(container, values);
	      }
	  
	      else
	      {
	         List <Pair<String, ArrayList<String>>> values = new ArrayList<> ();
	         values.add(pair_k);
	         mapping.put(container, values);
	      }
		  
	
	}

	public HashMap <String, List<Pair<String, ArrayList<String>>>> get_hashmap(){
		return mapping;
	}
	public List<Pair<String, ArrayList<String>>> getValues(String key){
	       return mapping.get(key);	   
	}


	
//	public boolean check_List(String container, String function){
//		
//		if(mapping.containsKey(container)){
//			List<Pair<String, ArrayList<Integer>>> check = mapping.get(container);
//			for (int i = 0; i < check.size(); i++) {
//				if( (check.get(i)).getKey()==function) return true;
//			}
//			return false;
//		}
//		else return false;
//		
//	}
//	public ArrayList<String> getargs (String container, String function){
//		
//		if(mapping.containsKey(container)){
//		List<Pair<String, ArrayList<String>>> check = mapping.get(container);
//		for (int i = 0; i < check.size(); i++) {
//			if( (check.get(i)).getKey()==function) 
//				return (check.get(i)).getValue();
//		}
//		return null;
//		}
//		return null;
//		
//	}
//	
}