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
import java.util.*;
//import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Xml_main {
    private String input_funcname;
    private String input_func;
    private String output_filename = "practice.xml";
    private String container_type;
    private static Pair<String, HashMap <String, List<Pair<String, ArrayList<String>>>>> ExcelReaderResult = ExcelReader.ReadExcel("C:/Exception/invalid_iterator_list.xlsx");
    private static HashMap<String, List<Pair<String, ArrayList<String>>>> HM = ExcelReaderResult.getValue();
    private static String errorName=ExcelReaderResult.getKey();
    public void setOutput_filename(String s){this.output_filename=s.replaceAll(":+","_") + ".xml";}
    public String getOutput_filename() {return output_filename;};
    public String getContainer_type(){ return this.container_type;}

    /*
     * 함수를 분석하여 Scaffold 객체 생성
     */
    public Pair<String, ArrayList<Scaffold>> init(String[] input){

        String input_funcname = input[0];
        String input_func = input[1];

        ArrayList <Scaffold> Scaffold_List = new ArrayList<Scaffold>();

        ArrayList <String> temp_check = new ArrayList <String> ();
        String arg_temp="	";

        boolean isMember;
        //System.out.println("funcname is : " + input_funcname);
        if(input_funcname.matches("std::.+::.+")){
            isMember=true;
            Pattern memFuncPattern = Pattern.compile( "(.+)::(.+)::(.+)");   // member function들의 제목줄 패턴
            Matcher memFuncMatcher = memFuncPattern.matcher(input_funcname);

            if(memFuncMatcher.find()) container_type=memFuncMatcher.group(2);
        } else isMember=false;
        String[] splitted=input_func.split("(\ntemplate.+?\\> )|(\n)|(template.+?\\>)"); //함수 포맷들 줄로 나누기
        splitted = Arrays.stream(splitted).filter(x -> x.length()>1).toArray(String[]::new); //공백인 줄 필터

        for(int i=0; i<splitted.length; i++){

                Scaffold_List.add(new Scaffold()); // Scaffold 객체 생성
                Scaffold_List.get(i).set_isMemberFn(isMember);
                if (isMember) Scaffold_List.get(i).add_arguments(container_type);
                /*System.out.println("\nsplitted[" + i + "] is : " + splitted[i] + "\n--------------------------------------------------------------");*/

                Scaffold_List.get(i).set_full_row(splitted[i]);

                String spZeroRegex = "(^|[^A-Za-z]+)(T)($|[^A-Za-z]+)"; //T 타입을 _SPARROW_ZERO_ 으로 변환하기 위해 타입명 T를 검출하기 위한 패턴

                String functionRowRegex="(\\S+\\s)?(\\S+)(\\s)(\\S+?)(\\()(.*?)(\\)[^\\)]*;)$"; //함수 regex
                Pattern functionRowPattern = Pattern.compile(functionRowRegex);
                Matcher functionRowMatcher = functionRowPattern.matcher(splitted[i]);
                String parameters = "";

                if (functionRowMatcher.find()) {
                    Scaffold_List.get(i).set_return_type(functionRowMatcher.group(2));
                    Scaffold_List.get(i).set_fn_name(functionRowMatcher.group(4));
                    parameters = functionRowMatcher.group(6); //parameters가 비어있다면, parameter가 없는 함수.

                    //cut the blank spaces at head and tail, if any
                    //parameter의 앞과 뒷부분에 공백이 있을 경우 공백 제거
                    if(parameters.startsWith(" ")) parameters=parameters.substring(1);
                    if(parameters.endsWith(" ")) parameters=parameters.substring(0,parameters.length()-1);

                    /*System.out.println("return_type : " + Scaffold_List.get(i).get_return_type());
                    System.out.println("function_name : " + Scaffold_List.get(i).get_fn_name());
                    System.out.println("parameters : " + parameters);*/
                }

                if(parameters.length()<1){
                    /*System.out.println("function has no parameters.");*/
                } else {
                    //paramRegex에 맞추기 위해 끝에 괄호를 추가. (마지막 parameter임을 표시해주기 위해)
                    parameters += " )";


                    String paramRegex = "(\\S+\\s)??(\\S+)(\\s)(\\S+)(\\s??)(=.+)??(,\\s|\\s\\))"; //parameters의 regex
                    Pattern paramPattern = Pattern.compile(paramRegex);
                    Matcher paramMatcher = paramPattern.matcher(parameters);

                    Pattern spZeroPattern = Pattern.compile(spZeroRegex);

                    while (paramMatcher.find()) {
                        String foundParam = paramMatcher.group(2);
                        Matcher spZeroMatcher = spZeroPattern.matcher(foundParam);
                        if (spZeroMatcher.find()) {
                            /*System.out.println("_SPARROW_ZERO_ case found. match is : " + spZeroMatcher.group());*/
                            foundParam = foundParam.replaceAll(spZeroRegex, "$1_SPARROW_ZERO_$3");
                        }

                        Scaffold_List.get(i).add_arguments(foundParam);
                        /*System.out.println("-------Argument Type is : " + foundParam);*/
                        arg_temp += foundParam;
                    }
                }

                //중복되는 포맷의 함수들 처리
                String check = Scaffold_List.get(i).get_return_type() + Scaffold_List.get(i).get_fn_name() + arg_temp;
                if (!temp_check.contains(check)) {
                    temp_check.add(check);
                } else {
                    Scaffold_List.get(i).set_return_type("duplicate");
                }
        }
        return new Pair<> (container_type, Scaffold_List);
    }

    /*
     * 생성된 Scaffold들을 바탕으로 xml 파일에 쓰기
     */
    public void write(ArrayList<Scaffold> slToWrite, String output_filename) {

        Document doc = new Document();
        ArrayList<Scaffold_Element> Scaffold_Elements = new ArrayList<Scaffold_Element>();

        Element root = new Element("scaffolds");
        doc.setRootElement(root);
        int i=0;
        System.out.println("slToWrite size is : " + slToWrite.size());
        for(Scaffold scToWrite : slToWrite) {
            /*System.out.println(scToWrite.get_fn_name() + " ------ [WRITE] return type is : " + scToWrite.get_return_type());*/
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

                /*System.out.println("container_type is : " + container_type);*/

                if(HM.containsKey(container_type)) {
                    List<Pair<String, ArrayList<String>>> FuncRowList = HM.get(container_type); //get function formats from sheet that match container type

                    /*System.out.println("FuncRowList.size() is : " + FuncRowList.size());*/
                    ArrayList<String> tempArgNumList = null; //temp container to store arg_nums from sheet

                    for (int count = 0; count < FuncRowList.size(); count++) {
                        if (FuncRowList.get(count).getKey().equals(full_row_temp)) { //if function format matches scraped row
                            tempArgNumList = FuncRowList.get(count).getValue(); // store arg_num list
                            /*System.out.println("invaliditer found");*/
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
                }

                //----------<return>
                Scaffold_Elements.get(i).ret_urn.setAttribute("type","unknown");
                Scaffold_Elements.get(i).SC.addContent(Scaffold_Elements.get(i).ret_urn);


                i++;
            }
        }
            /*System.out.println("i is : " + i);*/
            System.out.println("error name is : " + ExcelReaderResult.getKey());

            //WRITE XML
            XMLOutputter xout = new XMLOutputter();
            Format fo = xout.getFormat();

            fo.setIndent("    ");
            fo.setLineSeparator("\r\n");
            fo.setTextMode(Format.TextMode.TRIM);

            output_filename+=".xml";
            try {
                xout.setFormat(fo);
                xout.output(doc, new FileWriter(output_filename));
                System.out.println("Write complete. Saved as " + output_filename);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

	public static void main(String[] args) {

		while(true) { //MemFuncUrlScraper에서 exit이 입력되지 않는한 계속 반복하여 URL 입력을 받음
			ArrayList <Scaffold> Global_Scaffold_List = new ArrayList<Scaffold>(); //init에서 생성된 Scaffold들을 모두 담을 큰 Scaffold들의 리스트
			Queue<String> urlQueue = MemFuncUrlScraper.MemFuncUrlScrape();
	/*		for(String s : urlQueue) {
				System.out.println("queue element : " + s);
			}
			System.out.println("urlQueue size is : " + urlQueue.size());*/

			Xml_main InitInstance = new Xml_main();
			while(!urlQueue.isEmpty()){

				Global_Scaffold_List.addAll(InitInstance.init(Scraper.scrap(urlQueue.poll())).getValue());

			}
			/*System.out.println("GSL size is : " + Global_Scaffold_List.size());

			for(Scaffold gs : Global_Scaffold_List){
				System.out.println("GSL fn name : " + gs.get_fn_name());
				System.out.println("GSL ret_type : " + gs.get_return_type());
			}*/

			InitInstance.write(Global_Scaffold_List,InitInstance.getContainer_type());

		}
	}


}