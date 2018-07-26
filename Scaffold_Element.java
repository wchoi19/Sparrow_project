package xml;

import java.util.ArrayList;

import org.jdom2.Element;
//commit test
public class Scaffold_Element {
    Scaffold_Element(){

    }
    public Element SC = new Element("scaffold");
    public Element func_name = new Element("func_name");
    public Element ret_type = new Element("ret_type");
    public Element args = new Element("args");
    public Element body = new Element("body");
    public Element func = new Element("func");
    public Element func_args = new Element("args");
    //public Element func_arg = new Element("arg");

    public ArrayList<Element> Argument_list = new ArrayList<Element>();
    public ArrayList<Element> Func_arg_list = new ArrayList<Element>();




}