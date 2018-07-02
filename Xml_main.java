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
	
	private String s="iterator erase( iterator pos, int real );"; //total string from JIMIN
	private Cppreference practice;
	
	public void init(String retval){
		
			
			retval = retval.replaceAll("(|)", "");
			String[] parts=retval.split(" ");
			this.practice = new Cppreference();
			this.practice.set_return_type(parts[0]);
		    this.practice.set_fn_name(parts[1]);
		    for(int i=2; i<=(parts.length-2); i++)
		    {
		    	if(i%2==0) this.practice.set_arguments(parts[i]);
		    }

			
}
	
    /**
     * list에 들어있는 유저정보를 xml문서로 만드는 메소드
     */
 public void write() {
        
        Document doc = new Document();
        Element root = new Element("scaffolds"); //루트 엘리먼트 생성
  
        doc.setRootElement(root);
       
           
            Element fn = new Element("func_name");
            Element rt = new Element("ret_type");
            Element ags = new Element("args");
            Element SC = new Element("scaffold");
            Element ag=new Element("arg_type");
            ArrayList<Element> Element_list = new ArrayList<Element>();
            int args_size = this.practice.get_args_size();
            
            for(int i=0; i<args_size; i++)
            	Element_list.add(new Element("arg_type"));
            
            rt.setText(this.practice.get_return_type());
            fn.setText(this.practice.get_fn_name());
           
           
            root.addContent(SC);
            root.addContent(fn);
            root.addContent(rt);
            root.addContent(ags);
            
        
            //System.out.println(this.practice.get_args_size());
          
            for(int count=0; count<args_size; count++){
            		
            		Element_list.get(count).setText(this.practice.get_arguments(count));
            		ags.addContent(Element_list.get(count));
            }		
            		
            //ags.addContent(ag);
                  
            
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
		String temp = xml.get_string();
		for(String retval : temp.split(";\n")) {
			xml.init(retval);
			xml.write();
		}	
		//xml.read();	
		
}
	
	
	public String get_string() {
		return this.s;
	}

}
