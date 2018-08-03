package xml;

import java.util.Vector;
//import java.util.ArrayList;
//commit test

/*
    이 클래스는 Scaffold 과정에서 필요한 return type, function name, arguments등의 정보들을 저장 및 관리 하는
    클래스이다. 또한 STL 함수인지 아닌지 또한 분별할 수 있다. 이 기능이 필요한 이유는 xml 작성시 tag에 필요한 정보를
    넣기 위함이다
 */

public class Scaffold {

    private boolean isMemberFn=false;
    private String full_row; // 예 : iterator insert( iterator pos, const T& value );
    private String return_type;
    private String fn_name;
    private Vector <String> arguments = new Vector <String>(); //arguments 는 한개 이상인 경우가 많기 때문에 vector 를 사용


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
    //멤버함수인지 아닌지 set
    public void set_isMemberFn(boolean bool){
        this.isMemberFn=bool;
    }
    //리턴타입 반환
    //return type : String
    public String get_return_type() {
        return return_type;
    }
    //리턴타입 set
    public void set_return_type(String return_type) {
        this.return_type = return_type;
    }
    //함수이름 반환
    //return type : String
    public String get_fn_name() {
        return fn_name;
    }
    //함수이름 set
    public void set_fn_name(String fn_name) {
        this.fn_name = fn_name;
    }
    //arg 반환
    //return type : String
    public String get_arguments(int index) {
        return arguments.get(index);
    }
    //arg set
    public void add_arguments(String arguments) {
        this.arguments.add(arguments);
    }
    //arg vector 사이즈 반환
    //return type : int
    public int get_args_size(){
        return this.arguments.size();
    }

    @Override
    public String toString() {
        return "function [return type=" + return_type + ", function name=" + fn_name + ", arguments=" + arguments + "]";
    }


}