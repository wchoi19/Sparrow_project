package xml;

import java.io.FileWriter;
import java.io.IOException;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.util.ArrayList;


//// commit test comment

public class Xml_main {
	private String input;
	private ArrayList <Scaffold> Scaffold_List = new ArrayList<Scaffold>();
	private String output_filename = "practice.xml";

	public void setInput(String input){
		this.input=input;
	};
	public void setOutput_filename(String s){this.output_filename=s;}
	public String getOutput_filename() {return output_filename;};
	public void init(){ //Parsing된 HTML로 Scaffold 객체 생성 및 정보 입력

		String[] splitted=input.split(";\n");
		
		for(int i=0; i<splitted.length; i++){
			Scaffold_List.add(new Scaffold());
			splitted[i] = splitted[i].replaceAll("[\\(|\\),]", "");
			System.out.println("splitted[i] is : " + splitted[i]);
			String[] parts=splitted[i].split(" ");
	
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
 	public void write() { //Scaffold 객체들에 상응하는 xml element를 만들기 위해 Scaffold_Element 객체 생성
        
        Document doc = new Document();
        ArrayList<Scaffold_Element> Scaffold_Elements = new ArrayList<Scaffold_Element>();
   
        Element root = new Element("scaffolds"); //루트 엘리먼트 생성
        doc.setRootElement(root);
        
        for(int i =0; i<Scaffold_List.size(); i++){


        	Scaffold_Elements.add(new Scaffold_Element());


        	//scaffold type
			Scaffold_Elements.get(i).SC.setAttribute("type", Scaffold_List.get(i).get_isMemberFn()? "1" : "0");


        	int args_size = Scaffold_List.get(i).get_args_size();

        	for(int j=0; j<args_size; j++)
             	Scaffold_Elements.get(i).Argument_list.add(new Element("arg_type"));

            Scaffold_Elements.get(i).ret_type.setText(Scaffold_List.get(i).get_return_type());
            Scaffold_Elements.get(i).func_name.setText(Scaffold_List.get(i).get_fn_name());

            root.addContent(Scaffold_Elements.get(i).SC);
            Scaffold_Elements.get(i).SC.addContent(Scaffold_Elements.get(i).func_name);
            Scaffold_Elements.get(i).SC.addContent(Scaffold_Elements.get(i).ret_type);
            Scaffold_Elements.get(i).SC.addContent(Scaffold_Elements.get(i).args);

            for(int count=0; count<args_size; count++){
        		
        		Scaffold_Elements.get(i).Argument_list.get(count).setText(Scaffold_List.get(i).get_arguments(count));
        		Scaffold_Elements.get(i).args.addContent(Scaffold_Elements.get(i).Argument_list.get(count));
        }

			Scaffold_Elements.get(i).SC.addContent(Scaffold_Elements.get(i).body);
			Scaffold_Elements.get(i).body.addContent(Scaffold_Elements.get(i).func);
			Scaffold_Elements.get(i).func.addContent(Scaffold_Elements.get(i).func_args);
			Scaffold_Elements.get(i).func_args.addContent(Scaffold_Elements.get(i).func_arg);


            
        }
        XMLOutputter xout = new XMLOutputter();
        Format fo = xout.getFormat();
      //  fo.setEncoding("UTF-8"); //한글인코딩
       fo.setIndent(" ");//들여쓰기
       fo.setLineSeparator("\r\n");//줄바꿈
       fo.setTextMode(Format.TextMode.TRIM);


        try {            
            xout.setFormat(fo);
            xout.output(doc, new FileWriter(this.getOutput_filename()));
        } catch (IOException e) {
            e.printStackTrace();
        }
 	}

	public static void main(String[] args) {

		Xml_main xml = new Xml_main();
		xml.setInput(Scraper.scrap());
		xml.init();

		try {
			xml.write();

		} catch (Exception ee){
			System.out.println("Error - Write Unsuccessful");
		}
	}


}
