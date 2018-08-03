# XML scaffolding

## Excel 파일 포맷
 엑셀 파일의 첫번째 row의 첫번째 column은 “func type”가 들어가고 두번째 column에는  
        	어떤 function (예: invaliditer)이 들어갈지를 적어준다. (예시참조) 두번째 row에는
     	 차례대로 “container”, “Member Function”, “Function Formats”, 그리고 “arg_num”을 
기입한다. 세번째 row  부터는 두번째 row에 해당하는 정보들을 기입한다. (예시참조)

## Url 받는 방식
컴파일러로 컴파일 후 템플릿 라이브러리 컨테이너의 주소를 입력해준다. 예를 들어 std::vector에 해당하는 url (https://en.cppreference.com/w/cpp/container/vector) 을 컴파일러에 입력한다.

## Xml 파일 저장
Xml 파일 저장위치는 코드 상 (Xml_main.java) 에서 변경 가능하며 파일 이름은 컨테이너 
이름(vector.xml) 으로 저장된다.


* invalid_iterator.xlsx 라는 파일 첨부 하였으니 참고 바랍니다.