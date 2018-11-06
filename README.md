
# C++ Template Library Scaffolding Tool

A C++ template analysis tool to be used by Sparrow developers. 
Requires Java.

by Jimin Yook, Wooyoung Choi



## 사용 매뉴얼 Manual
Maven 컴파일러로 컴파일 후 /src/main/java/Xml_main.java (xml.Xml_main.java) 실행한 뒤 템플릿 라이브러리 컨테이너의 주소를 입력해줍니다. 
cppreference의 영문 사이트 주소(https://en.cppreference.com/~)로 입력해야합니다.
예) std::vector에 해당하는 url (https://en.cppreference.com/w/cpp/container/vector) 을 컴파일러에 입력

1. Compile with Maven, 
2.  run /src/main/java/Xml_main.java 
3. Enter container url of template library from the Cppreference english site. 
ex) url for std::vector : https://en.cppreference.com/w/cpp/container/vector

## Excel 파일 형식 Excel Spreadsheet Format
엑셀 파일은 C:/Exceptions에 넣어서, 또는 다른 방식으로 권한 해제를 해야 읽힙니다.

 엑셀 파일에서 첫번째 행의 “func type”부분에 에러명을 입력합니다(예: invaliditer).
 이후 1열에는 컨테이너명, 3열에는 세미콜론(;)까지 포함된 함수 정의를 입력합니다.(예시참조)
 4열에는 arg_num을 입력합니다(1개보다 많을 경우 띄어쓰기 없는 쉼표로 구분) (예시참조)

\* invalid_iterator.xlsx 라는 파일 첨부 하였으니 참고 바랍니다.

Excel files should be placed in placed in a directory that does not require access permissions.

In the "func type" row, enter the name of the error. ex) invaliditer
Enter the name of the container in the 1st column, and the definition of the function including the semicolon in the 3rd column.
Enter arg_num in the 4th column (separate by columns without spaces when more than one argument)

Refer to the example excel file invalid_iterator.xlsx as a guide.

## Xml 파일 저장 Saving as Xml
Xml 파일명은 코드상에서 변경 가능(Xml_main.java:224). 현재는 컨테이너 이름(예: vector.xml)으로 저장되도록 설정 되어있습니다.

The xml file will be saved as the name of the container. ( e.g. : vector.xml). To change this file naming method, change the code at Xml_main.java:224.



