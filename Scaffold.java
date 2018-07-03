package xml;

import java.util.Vector;
//import java.util.ArrayList;

public class Scaffold {
    
    private String return_type;
    private String fn_name; 
    
    //private String arguments;
    private Vector <String> arguments = new Vector <String>(); 
    
    
    public Scaffold() {        
        
    }
    
    public Scaffold(String return_type, String fn_name, String arguments) {
        super();
        this.return_type = return_type;
        this.fn_name = fn_name;
        this.arguments.add(arguments);
      
        
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
    public void set_arguments(String arguments) {
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
