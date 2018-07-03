package xml;

import java.io.FileWriter;
import java.io.IOException;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;


public class Xml_main {
	
	private String s="iterator erase( iterator pos, double pos );\niterator delete( int index );"; //total string from JIMIN
	//private Scaffold practice;
	private ArrayList <Scaffold> Scaffold_List=new ArrayList<Scaffold>();

	
	public void init(){
		
			
		String[] splited=s.split(";\n");
		
		for(int i=0; i<splited.length; i++){
			Scaffold_List.add(new Scaffold());
			splited[i] = splited[i].replaceAll("(|)", "");
			String[] parts=splited[i].split(" ");
	
			Scaffold_List.get(i).set_return_type(parts[0]);
			Scaffold_List.get(i).set_fn_name(parts[1]);
			for(int j=2; j<=(parts.length-2); j++)
		    {
				
		    	if(j%2==0) {
		    		System.out.println(parts[j]);
		    		Scaffold_List.get(i).set_arguments(parts[j]);
		    	}
		    }
		}
	
			
}
	
    /**
     * list에 들어있는 유저정보를 xml문서로 만드는 메소드
     */
 public void write() {
        
        Document doc = new Document();
        ArrayList<Scaffold_Element> Scaffold_Elements = new ArrayList<Scaffold_Element>();
   
        Element root = new Element("scaffolds"); //루트 엘리먼트 생성
        doc.setRootElement(root);
        
        for(int i =0; i<Scaffold_List.size(); i++){
        	Scaffold_Elements.add(new Scaffold_Element());
        	int args_size = Scaffold_List.get(i).get_args_size();
        	 
        	for(int j=0; j<args_size; j++)
             	Scaffold_Elements.get(i).Argument_list.add(new Element("arg_type"));
        	

            Scaffold_Elements.get(i).rt.setText(Scaffold_List.get(i).get_return_type());
            Scaffold_Elements.get(i).fn.setText(Scaffold_List.get(i).get_fn_name());
           
           
            root.addContent(Scaffold_Elements.get(i).SC);
            Scaffold_Elements.get(i).SC.addContent(Scaffold_Elements.get(i).fn);
            Scaffold_Elements.get(i).SC.addContent(Scaffold_Elements.get(i).rt);
            Scaffold_Elements.get(i).SC.addContent(Scaffold_Elements.get(i).ags);
            
       
            for(int count=0; count<args_size; count++){
        		
        		Scaffold_Elements.get(i).Argument_list.get(count).setText(Scaffold_List.get(i).get_arguments(count));
        		Scaffold_Elements.get(i).ags.addContent(Scaffold_Elements.get(i).Argument_list.get(count));
        }		
            
        }
       
            
        XMLOutputter xout = new XMLOutputter();
        Format fo = xout.getFormat();
      //  fo.setEncoding("UTF-8"); //한글인코딩
       fo.setIndent(" ");//들여쓰기
       fo.setLineSeparator("\r\n");//줄바꿈
       fo.setTextMode(Format.TextMode.TRIM);
        
        try {            
            xout.setFormat(fo);
            xout.output(doc,  new FileWriter("practice.xml"));
        } catch (IOException e) {
            e.printStackTrace();
        }    
        
 }  	

/**
 * xml 읽어오기
 */   
// 
//public void read() {
//    List<Cppreference> userList = null;
//    try {
//        File file = new File("practice.xml");
//        FileInputStream in = new FileInputStream(file);
//        SAXBuilder builder = new SAXBuilder();
////        builder.setIgnoringElementContentWhitespace(true);
//            
//        Document doc = builder.build(in);            
//        
//        Element xmlRoot = doc.ge
//        		
//        		tRootElement();
//        
//        List<Element> tempList = xmlRoot.getChildren();
//        userList = new ArrayList<Cppreference>();
//        
//        for(int i = 0; i < tempList.size(); i++) {
//            Element element = (Element) tempList.get(i);
//            
//            String name = element.getName();
//            String value = element.getValue();
//            System.out.println("("+i+") name="+name+"/value="+value);
//                                            
//            Cppreference u = new Cppreference();
//            u.set_return_type(element.getChildTextTrim("name"));
//            u.set_fn_name(element.getChildTextTrim("hp"));
//            u.set_arguments(element.getChildTextTrim("level"));
//           
//            userList.add(u);
//        }
//        
//        for (Cppreference user : userList ) {
//            System.out.println(user);
//        }
//        
//    } catch (Exception e) {
//        e.printStackTrace();
//    }
//    
//}
	
	
	public static void main(String[] args) {
		
		Xml_main xml = new Xml_main();
		xml.init();
		xml.write();
		
	
	}
	
	
	public String get_string() {
		return this.s;
	}

}
