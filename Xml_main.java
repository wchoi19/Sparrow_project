package xml;
//practice
import java.io.FileWriter;
import java.io.IOException;

import javafx.util.Pair;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.lang.reflect.Array;
import java.util.ArrayList;
//import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Xml_main {
    private String input_funcname;
    private String input_func;
    //private ArrayList <Scaffold> Scaffold_List = new ArrayList<Scaffold>();
    private String output_filename = "practice.xml";
    private static String container_type;
    private static Pair<String, HashMap <String, List<Pair<String, ArrayList<String>>>>> ExcelReaderResult = ExcelReader.ReadExcel("C:/Exception/invalid_iterator_list.xlsx");
    private static HashMap<String, List<Pair<String, ArrayList<String>>>> HM = ExcelReaderResult.getValue();
    private static String errorName=ExcelReaderResult.getKey();

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
    public static ArrayList<Scaffold> init(String[] input){ //Parsing占쏙옙 HTML占쏙옙 Scaffold 占쏙옙체 占쏙옙占쏙옙 占쏙옙 占쏙옙占쏙옙 占쌉뤄옙

        String input_funcname = input[0];
        String input_func = input[1];

        ArrayList <Scaffold> Scaffold_List = new ArrayList<Scaffold>();
        //String container_type="";

        ArrayList <String> temp_check = new ArrayList <String> ();
        String arg_temp="	";

        boolean isMember;
        //System.out.println("funcname is : " + input_funcname);
        if(input_funcname.matches("std::.+::.+")){
            isMember=true;
            Pattern memFuncPattern = Pattern.compile( "(.+)::(.+)::(.+)");   // pattern of member function names
            Matcher memFuncMatcher = memFuncPattern.matcher(input_funcname);

            if(memFuncMatcher.find()) container_type=memFuncMatcher.group(2);
        } else isMember=false;
        String[] splitted=input_func.split("(\ntemplate.+\\> )|(\n)|(template.+\\>)"); //template

        for(int i=0; i<splitted.length; i++){
            Scaffold_List.add(new Scaffold()); // 占쌕몌옙占쏙옙 Scaffold 占쏙옙체 占쏙옙占쏙옙
            Scaffold_List.get(i).set_isMemberFn(isMember);
            if(isMember) Scaffold_List.get(i).add_arguments(container_type);
            System.out.println("\nsplitted[" + i +"] is : " + splitted[i] + "\n--------------------------------------------------------------");

            Scaffold_List.get(i).set_full_row(splitted[i]);

            String noArgsRegex="([^(]+\\s)?(\\S+)(\\s)(\\S+)\\(\\)(.*?);";
            String spZeroRegex="(^|[^A-Za-z]+)(T)($|[^A-Za-z]+)";

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

                Pattern spZeroPattern=Pattern.compile(spZeroRegex);
                
                while (paramMatcher.find()) {
                    String foundParam = paramMatcher.group(2);
                    Matcher spZeroMatcher=spZeroPattern.matcher(foundParam);
                    if (spZeroMatcher.find()) {
                        System.out.println("_SPARROW_ZERO_ case found. match is : " + spZeroMatcher.group());
                        foundParam=foundParam.replaceAll(spZeroRegex,"$1_SPARROW_ZERO_$3");
                    }

                    Scaffold_List.get(i).add_arguments(foundParam);
                    System.out.println("-------Argument Type is : " + foundParam);
                    arg_temp+=foundParam;
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
            //여기에서 확인해주고 return type을 "duplicate"이런거로 설정해준다음
            //write 에서 return type이 duplicate이면 스킵해라 이렇게 만들면 될듯.
            String check = Scaffold_List.get(i).get_return_type()+Scaffold_List.get(i).get_fn_name()+arg_temp;
            if( !temp_check.contains(check) ){
                temp_check.add(check);
            }
            else{
                Scaffold_List.get(i).set_return_type("duplicate");
            }
        }
        return Scaffold_List;
    }
    /**
     * list占쏙옙 占쏙옙占쏙옙獵占� 占쏙옙占쏙옙占쏙옙占쏙옙占쏙옙 xml占쏙옙占쏙옙占쏙옙 占쏙옙占쏙옙占� 占쌨소듸옙
     */
    public static void write(ArrayList<Scaffold> slToWrite) { //Scaffold 占쏙옙체占썽에 占쏙옙占쏙옙占싹댐옙 xml element占쏙옙 占쏙옙占쏙옙占� 占쏙옙占쏙옙 Scaffold_Element 占쏙옙체 占쏙옙占쏙옙

        Document doc = new Document();
        ArrayList<Scaffold_Element> Scaffold_Elements = new ArrayList<Scaffold_Element>();

        Element root = new Element("scaffolds"); //占쏙옙트 占쏙옙占쏙옙占쏙옙트 占쏙옙占쏙옙
        doc.setRootElement(root);
        int i=0;
        System.out.println("slToWrite size is : " + slToWrite.size());
        for(Scaffold scToWrite : slToWrite) {
            System.out.println(scToWrite.get_fn_name() + " ------ [WRITE] return type is : " + scToWrite.get_return_type());
//				System.out.println(scToWrite.get_return_type().equals("duplicate"));
            if (scToWrite.get_return_type() != "duplicate") { // check duplicate
                String full_row_temp = scToWrite.get_full_row();

                //----------Scaffold_Element 객체 생성
                Scaffold_Elements.add(new Scaffold_Element());

                //----------<scaffold>
                Scaffold_Elements.get(i).SC.setAttribute("type", scToWrite.get_isMemberFn() ? "1" : "0");
                root.addContent(Scaffold_Elements.get(i).SC);

                //----------<func_name> & <ret_type>
                Scaffold_Elements.get(i).ret_type.setText(scToWrite.get_return_type());
                Scaffold_Elements.get(i).func_name.setText(scToWrite.get_fn_name());
                Scaffold_Elements.get(i).SC.addContent(Scaffold_Elements.get(i).func_name);
                Scaffold_Elements.get(i).SC.addContent(Scaffold_Elements.get(i).ret_type);

                //----------<args>
                int args_size = scToWrite.get_args_size();
                for (int j = 0; j < args_size; j++)
                    Scaffold_Elements.get(i).Argument_list.add(new Element("arg_type"));

                Scaffold_Elements.get(i).SC.addContent(Scaffold_Elements.get(i).args);
                for (int count = 0; count < args_size; count++) {
                    Scaffold_Elements.get(i).Argument_list.get(count).setText(scToWrite.get_arguments(count));
                    Scaffold_Elements.get(i).args.addContent(Scaffold_Elements.get(i).Argument_list.get(count));
                }

                //----------<body>
                Scaffold_Elements.get(i).SC.addContent(Scaffold_Elements.get(i).body);
                Scaffold_Elements.get(i).body.addContent(Scaffold_Elements.get(i).func);
                Scaffold_Elements.get(i).func.addContent(Scaffold_Elements.get(i).func_args);

                System.out.println("container_type is : " + container_type);
                List<Pair<String, ArrayList<String>>> FuncRowList = HM.get(container_type); //get function formats from sheet that match container type
                /*for(Pair funcpair : FuncRowList){
                    System.out.println("key of pair is : " + funcpair.getKey());
                    System.out.println("full_row_temp : " + full_row_temp);
                }*/
                System.out.println("FuncRowList.size() is : " + FuncRowList.size());
                ArrayList<String> tempArgNumList = null; //temp container to store arg_nums from sheet

                for (int count = 0; count < FuncRowList.size(); count++) {
                    if (FuncRowList.get(count).getKey().equals(full_row_temp)) { //if function format matches scraped row
                        tempArgNumList = FuncRowList.get(count).getValue(); // store arg_num list
                        System.out.println("invaliditer found");
                        //System.out.println(tempArgNumList + " ---- " + FuncRowList.get(count).getKey());
                        break;
                    }
                }

                if (tempArgNumList != null) {
                    Scaffold_Elements.get(i).func.setAttribute("type", errorName);
                    for (int count = 0; count < tempArgNumList.size(); count++) { //
                        Scaffold_Elements.get(i).Func_arg_list.add(new Element("arg"));
                    }
                    for (int count = 0; count < tempArgNumList.size(); count++) {
                        Scaffold_Elements.get(i).Func_arg_list.get(count).setAttribute("num", tempArgNumList.get(count));
                        Scaffold_Elements.get(i).func_args.addContent(Scaffold_Elements.get(i).Func_arg_list.get(count));
                    }

                }

                //----------<return>
                Scaffold_Elements.get(i).ret_urn.setAttribute("type","unknown");
                Scaffold_Elements.get(i).SC.addContent(Scaffold_Elements.get(i).ret_urn);


                i++;
            }
        }
            System.out.println("i is : " + i);
            System.out.println("error name is : " + ExcelReaderResult.getKey());

            XMLOutputter xout = new XMLOutputter();
            Format fo = xout.getFormat();
            //  fo.setEncoding("UTF-8"); //占싼깍옙占쏙옙占쌘듸옙
            fo.setIndent(" ");//占썽여占쏙옙占쏙옙
            fo.setLineSeparator("\r\n");//占쌕바뀐옙
            fo.setTextMode(Format.TextMode.TRIM);


            try {
                xout.setFormat(fo);
                xout.output(doc, new FileWriter("practice.xml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

	public static void main(String[] args) {

		while(true) {
			ArrayList <Scaffold> Global_Scaffold_List = new ArrayList<Scaffold>();
			Queue<String> urlQueue = MemFuncUrlScraper.MemFuncUrlScrape();
			for(String s : urlQueue) {
				System.out.println("queue element : " + s);
			}
			System.out.println("urlQueue size is : " + urlQueue.size());
			//queue = url scraper가 리턴한 것들
			//while scraped url queue is not empty,
			while(!urlQueue.isEmpty()){

				Global_Scaffold_List.addAll(Xml_main.init(Scraper.scrap(urlQueue.poll())));
			}
			System.out.println("GSL size is : " + Global_Scaffold_List.size());

			for(Scaffold gs : Global_Scaffold_List){
				System.out.println("GSL fn name : " + gs.get_fn_name());
				System.out.println("GSL ret_type : " + gs.get_return_type());
			}

			Xml_main.write(Global_Scaffold_List);

			//if (xml.inputIsValid) {
				//xml.setOutput_filename(xml.init());

			//}
		}
	}


}