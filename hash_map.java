package xml;

import java.util.*;
import java.util.HashMap;
import javafx.util.Pair;

/*이 클래스는 hash map 클래스로 excel 파일에 있는 정보들을 저장 및 관리하는 용도로 사용 되었다. Excel 파일의 정보들을 가져와 hashmap을 construct
 * 하고 xml scaffolding 할 경우에 <body> 부분에 넣어줄 부분 (예:1,2)을 가져 오는 function도 사용 되었다.
 */
public class hash_map {

	//excel 파일의 정보들을 저장하는 Hashmap
	private HashMap <String, List<Pair<String, ArrayList<String>>>> mapping = new HashMap<> ();
	public void Construct(String container, String function, ArrayList<String> args){
		/*System.out.println("Construct starting...");
		System.out.println("container : " + container + " function : " + function);*/
		//ArrayList<String> temp = new ArrayList<String> (args);
		Pair<String, ArrayList<String> > pair_k = new Pair<> (function, args);

		//이미 key를 가지고 있는 경우
		if (mapping.containsKey(container))
		{
	        /* List<Pair<String, ArrayList<String>>> values = mapping.get(container);
	         values.add(pair_k);*/
			mapping.get(container).add(pair_k);
			//mapping.put(container, values);
		}
		//hashmap에 아직 해당 key가 없는경우
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


	/*XML scaffolding시 arg부분을 채워줄 요소들을 가져오는 함수
	ret type : ArrayList<String>
	arg : String container (예 : vector, set 등등) , String function (예 : iterator insert( iterator pos, const T& value );)
	  */
	public ArrayList<String> getargs (String container, String function){

		if(mapping.containsKey(container)){
			List<Pair<String, ArrayList<String>>> check = mapping.get(container);
			for (int i = 0; i < check.size(); i++) {
				if( (check.get(i)).getKey()==function)
					return (check.get(i)).getValue();
			}
			return null;
		}
		return null;

	}



}