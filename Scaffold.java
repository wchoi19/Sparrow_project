package xml;

import java.util.Vector;
//import java.util.ArrayList;

//commit test

public class Scaffold {

    private boolean isMemberFn=false;
    private String full_row;
    private String return_type;
    private String fn_name;
    private Vector <String> arguments = new Vector <String>();


    public Scaffold() {}
    public Scaffold(boolean isMemberFn, String return_type, String fn_name, String arguments) {
        super();
        this.isMemberFn=isMemberFn;
        this.return_type = return_type;
        this.fn_name = fn_name;
        this.arguments.add(arguments);
    }
    public void set_full_row(String s){ full_row=s; }
    public String get_full_row(){ return full_row; }
    public boolean get_isMemberFn() { return isMemberFn; }
    public void set_isMemberFn(boolean bool){
        this.isMemberFn=bool;
    }
    public String get_return_type() {
        return return_type;
    }
    public void set_return_type(String return_type) {
        this.return_type = return_type;
    }
    public String get_fn_name() {
        return fn_name;
    }
    public void set_fn_name(String fn_name) {
        this.fn_name = fn_name;
    }
    public String get_arguments(int index) {
        return arguments.get(index);
    }
    public void add_arguments(String arguments) {
        this.arguments.add(arguments);
    }
    public int get_args_size(){
        return this.arguments.size();
    }

    @Override
    public String toString() {
        return "function [return type=" + return_type + ", function name=" + fn_name + ", arguments=" + arguments + "]";
    }


}