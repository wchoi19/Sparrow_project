package xml;

import java.io.FileWriter;
import java.io.IOException;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Xml_main {
	private String input_funcname;
	private String input_func;
	private ArrayList <Scaffold> Scaffold_List = new ArrayList<Scaffold>();
	private String output_filename = "practice.xml";
	private String container_type;

	public boolean inputIsValid; //checks if input from Scraper is a valid cppreference.com url that can be parsed.
	public void setInput(String[] input){
		if(input!=null) {
			inputIsValid=true;
			this.input_funcname = input[0];
			this.input_func = input[1];
		} else {
			inputIsValid=false;
		}
	};
	public void setOutput_filename(String s){this.output_filename=s.replaceAll(":+","_") + ".xml";}
	public String getOutput_filename() {return output_filename;};
	public String init(){ //Parsing된 HTML로 Scaffold 객체 생성 및 정보 입력

		boolean isMember;
		//System.out.println("funcname is : " + input_funcname);
		if(input_funcname.matches("std::.+::.+")){
			isMember=true;
			Pattern memFuncPattern = Pattern.compile( "(.+)::(.+)::(.+)");   // pattern of member function names
			Matcher memFuncMatcher = memFuncPattern.matcher(input_funcname);

			if(memFuncMatcher.find()) this.container_type=memFuncMatcher.group(2);
		} else isMember=false;
		String[] splitted=input_func.split("(\ntemplate.+\\> )|(\n)|(template.+\\>)"); //template

		for(int i=0; i<splitted.length; i++){
			Scaffold_List.add(new Scaffold()); // 줄마다 Scaffold 객체 생성
			Scaffold_List.get(i).set_isMemberFn(isMember);
			if(isMember) Scaffold_List.get(i).add_arguments(container_type);
			System.out.println("\nsplitted[" + i +"] is : " + splitted[i] + "\n--------------------------------------------------------------");

			String noArgsRegex="([^(]+\\s)?(\\S+)(\\s)(\\S+)\\(\\)(.*?);";
			if(!splitted[i].matches(noArgsRegex)) { //if function has 1<= arguments
				Pattern rowPattern = Pattern.compile("(\\S+\\s)?(\\S+)(\\s)(\\S+?)(\\(\\s)(.*?\\s\\))(.+)?");
				Matcher rowMatcher = rowPattern.matcher(splitted[i]);
				String parameters = "";

				if (rowMatcher.find()) {
					Scaffold_List.get(i).set_return_type(rowMatcher.group(2));
					Scaffold_List.get(i).set_fn_name(rowMatcher.group(4));
					parameters = rowMatcher.group(6);

					System.out.println("return_type : " + Scaffold_List.get(i).get_return_type());
					System.out.println("function_name : " + Scaffold_List.get(i).get_fn_name());
					System.out.println("parameters : " + parameters);
				}

				String paramRegex = "(\\S+\\s)??(\\S+)(\\s)(\\S+)(\\s??)(=.+)??(,\\s|\\s\\))";
				Pattern paramPattern = Pattern.compile(paramRegex);
				Matcher paramMatcher = paramPattern.matcher(parameters);

				while (paramMatcher.find()) {

					Scaffold_List.get(i).add_arguments(paramMatcher.group(2));
					System.out.println("-------Argument Type is : " + paramMatcher.group(2));
				}
			} else {
				Pattern noArgsPattern = Pattern.compile(noArgsRegex);
				Matcher noArgsMatcher = noArgsPattern.matcher(splitted[i]);


				if (noArgsMatcher.find()) {
					System.out.println("no-arg function found!");
					Scaffold_List.get(i).set_return_type(noArgsMatcher.group(2));
					Scaffold_List.get(i).set_fn_name(noArgsMatcher.group(4));

					System.out.println("return_type : " + Scaffold_List.get(i).get_return_type());
					System.out.println("function_name : " + Scaffold_List.get(i).get_fn_name());
					System.out.println("parameters : (none)" );
				}

			}

		}
		return input_funcname;
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
			//Scaffold type attribute(member function?)
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
		while(true) {
			Xml_main xml = new Xml_main();
			xml.setInput(Scraper.scrap());
			if (xml.inputIsValid) {
				xml.setOutput_filename(xml.init());
				try {
					xml.write();
					System.out.println("\nWrite successful. Saved as \"" + xml.getOutput_filename() + "\".");
				} catch (Exception ee) {
					System.out.println("Error - Write Unsuccessful");
				}
			}
		}
	}


}