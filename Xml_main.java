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
	
	private ArrayList <Scaffold> Scaffold_List=new ArrayList<Scaffold>();

	  /**
     * input string을 Scaffold 클래스로 변환하여 ArrayList<Scaffold>에 저장
     */
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
     * list에 들어있는 Scaffold 정보를 XML로 변환
     */
 public void write() {
        
        Document doc = new Document();
        ArrayList<Scaffold_Element> Scaffold_Elements = new ArrayList<Scaffold_Element>();
   
        Element root = new Element("scaffolds"); //Root Element
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
      
       fo.setIndent(" ");//들여쓰기
       fo.setLineSeparator("\r\n");//new line
       fo.setTextMode(Format.TextMode.TRIM);
        
        try {            
            xout.setFormat(fo);
            xout.output(doc,  new FileWriter("practice.xml"));
        } catch (IOException e) {
            e.printStackTrace();
        }    
        
 }  	

 /**
  * main function
  */
	
	public static void main(String[] args) {
		
		Xml_main xml = new Xml_main();
		xml.init();
		xml.write();
		
	
	}
	
	
	public String get_string() {
		return this.s;
	}

}
