# [BE] 코드컨벤션

---

# 스프링 네이밍 컨벤션

## **Controller**

- 컨트롤러 클래스 안에서 메서드 명을 작성 할 때는 아래와 같은 접미사를 붙인다.
    - orderList() – 목록 조회 유형의 서비스
    - orderDetails() – 단 건 상세 조회 유형의 controller 메서드
    - orderSave() – 등록/수정/삭제 가 동시에 일어나는 유형의 controller 메서드
    - orderAdd() – 등록만 하는 유형의 controller 메서드
    - orderModify() – 수정만 하는 유형의 controller 메서드
    - orderRemove() – 삭제만 하는 유형의 controller 메서드

---

## **Service**

- 서비스 클래스 안에서 메서드 명을 작성 할 때는 아래와 같은 접두사를 붙인다.
    - findOrder() - 조회 유형의 service 메서드
    - addOrder() - 등록 유형의 service 메서드
    - modifyOrder() - 변경 유형의 service 메서드
    - removeOrder() - 삭제 유형의 service 메서드
    - saveOrder() – 등록/수정/삭제 가 동시에 일어나는 유형의 service 메서드

---

## **Mapper**

- Mapper 클래스 안에서 메서드 명을 작성 할 때는 아래와 같은 접두사를 붙인다.
    - selectOrder(); - 조회 유형의 mapper 메서드
    - insertOrder(); - 등록 유형의 mapper 메서드
    - updateOrder(); – 변경 유형의 mapper 메서드
    - deleteOrder(); - 삭제 유형의 mapper 메서드

---

## **Structure**

1. 패키지는 목적별로 묶어 생성한다.
    - common(공통기능 관련), user(유저 관련), Order(주문관련) ....
2. Controller에서는 Service 호출과 Exception 처리만을 담당한다.
    - Controller에서의 비즈니스 로직 구현은 최대한 피한다.
3. 메소드와 클래스는 하나의 목적만을 위해 생성한다.
    - 한개의 메소드는 한가지의 기능만을 가져야 한다.
    - 한개의 클래스 내부에는 같은 목적만을 가진 코드가 존재하여야한다.
4. 메소드와 클래스는 가능한 작게만든다.
    - 여러 기능이 모인 적은 수의 큰 클래스보다는 목적이 뚜렷한 작은 클래스 여러개로 이루어진 시스템이 바람직하다.
5. 도메인명의 Service 생성은 피한다.
    - Order 라는 도메인이 있을 때 OrderService 로 만드는 것은 피한다.
    - OrderService로 만들 경우 그 안에 도메인과 관련된 여러 기능을 넣을 가능성이 높다.
    - 도메인 관련 기능을 세분화하여 Service를 만든다(ex. OrderRegisretService, OrderStatusService .....)

# Java 코드 컨벤션

# 주요 코드 컨벤션 규칙

---

# 파일 공통 요건

---

- 모든 소스, 텍스트 문서 파일의 인코딩은 **UTF-8**로 통일
- 새줄 문자는 **LF(Line Feed, 0x0A)**를 사용. Windows 형식의 CRLF가 섞이지 않도록 편집기와 Git 설정을 확인
    - Git을 쓴다면 .gitattributes 파일 안에 정책을 선언해서 지정된 새줄 문자로 강제 변환하거나 예외가 될 확장자를 지정 가능
        
        ```
        *.c text eol=lf
        *.cpp text eol=lf
        *.h text eol=lf
        
        # exception for visual studio project configuration
        *.sln text eol=crlf
        *.vs text eol=crlf
        *.csproj eol=crlf
        *.props eol=crlf
        *.filters eol=crlf
        ```
        
    - .gitattributes 파일은 디렉토리별로 지정 가능. 전체 프로젝트라면 최상위 디렉토리에 둠
- 파일의 마지막은 새줄 문자 **LF**로 끝나야 함

# 소스 파일의 구조

---

소스 파일은 다음과 같은 순서로 구성됨

1. 라이센스 OR 저작권 정보(있을 경우)
2. package 명세
3. import 명세
4. 최상위 클래스 시작
각 세션은 하나의 빈 줄로 구분

### 라이센스

- 있는 경우에만 적어줌

### package 문

- 개행하지 않음
- 다른 내용에 적용되는 열 제한(최대 100자)는 패키지문에 적용하지 않음

### import 문

- **와일드카드(*, *asterisk)** 으로 가져오지 않음*
    - 이유 1 - 성능 문제 : 패키지의 클래스를 전부 탐색하는 비용이 존재
    - 이유 2 - 중복 이름 클래스 문제 : import한 두 패키지에 같은 이름으로 된 클래스를 사용
- import문도 package문과 동일하게 개행하지 않고 열 제한을 적용하지 않음
- **static import**와 **non-static import**를 따로 모아서 블록을 만듦
    - 블록의 순서는 static ⇒ non-static 순
    - 블록 사이에는 1줄의 개행
    - 블록 내에서 정렬 순서는 ASCII 코드 정렬 순서

### Class 정의

- 소스 내에서 최상위 클래스는 단 하나
- 클래스 멤버 간 순서는 없지만, 논리적 순서가 중요
    - **비슷한 역할의 메소드를 모아놓고, 추상화 단계에 따라 배치**

> ❗ **Overload된 메소드는 절대 흩어놓지 않아야 함**
> 

# 포맷

---

## 괄호 `{}`

---

- **괄호는** if, else, for, do 및 모든 while문에 코드가 없거나 한 줄이라도 **생략하지 않음**
- `{`
    - 앞에 개행하지 않음
    - 뒤에 개행
- `}`
    - 앞에 개행
    - 뒤에 개행
    - 뒤에 `else` ,`,(쉼표)`, `catch`, `finally`, (do)-`while`가 있으면 개행 하지 않음
- example code
    
    ```java
    return () -> {
      while (condition()) {
        method();
      }
    };
    
    return new MyClass() {
      @Override 
    	public void method() {
        if (condition()) {
          try {
            something();
          } catch (ProblemException e) {
            recover();
          }
        } else if (otherCondition()) {
          somethingElse();
        } else {
          lastThing();
        }
      }
    };
    ```
    

### 빈 블록

- 빈 블록은 괄호를 한 줄에 쓸 수 있음
- 단, 다중 블록(if-else, try-catch-finally 등)이라면 개행 필수
- example code
    
    ```java
    // 허용
      void doNothing() {}
    
      // 허용
      void doNothingElse() {
      }
      // 허용되지 않음 : 멀티 블럭 구문에서는 간결한 빈 블럭을 사용할 수 없다.
      try {
        doSomething();
      } catch (Exception e) {}
    ```
    

## 열 제한

---

- 한 문장은 최대 100글자로 제한. 아래 예외 제외하고 줄 바꿈 규칙에 맞춰 줄 바꿈 해야 함
    1. 열 제한을 따를 수 없는 행
    2. package 및 import 문
    3. 주석에서 shell에 붙여 넣을 수 있는 command line 내용

## 줄 바꿈

---

- 한 줄을 차지하는 코드를 여러 줄로 나누는 것
- 열 제한을 초과하지 않기 위해 또는 가독성을 위해 이용

> 💡 메소드나 지역변수를 이용하여 줄 바꿈 없이 문제 해결 가능
> 

### 줄 바꿈 규칙

- 줄 바꿈의 주요 방식은 더 높은 문법 수준(**higher syntactic level**)에서 끊는 것
1. 연산자같은 상징(operator-like symbol) 앞에서 끊어줌
    - `.` (dot 연산자)
    - 람다의 `::`
    - `<T extends Foo & Bar>`과 같은 타입에서 `&`
    - `catch (FooException | BarException e)`와 같은 catch 블록의 `|`
- 줄 바꿈 시, 첫 번째 줄 뒤의 각 줄은 원래 줄에서 적어도 +4만큼 들여쓰기
    - 연속 줄이 여러 개인 경우, 원하는 대로 들여 쓰기를 +4 이상으로 변경 가능

## 변수 선언

---

1. 모든 변수 선언은 하나에 한 개만 해야 함
    
    ```java
    int a, b; // (X)
    ```
    
    > ❗예외적으로 for문의 헤더에서는 허용됨
    > 
2. 변수는 필요할 때 선언해야 함
    - 지역 변수의 범위를 최소화하기 위해 처음 사용되는 지점에 가깝게 선언해야 함

## 배열 선언

---

1. 배열은 **“block-like constructure”** 으로만 선언되면 됨. 아래의 어느 방식으로든 선언 가능
2. **C 스타일로 배열을 선언하지 않음**
    
    ```java
    String[] args // (O)
    String args[] // (X)
    ```
    

# 네이밍 규칙

---

## 패키지 이름

- 모두 **소문자**
- 연속된 단어는 **언더스코어(`_`)없이 연결**

```
com.example.deepspacenot // (O)
com.example.deepSpace // (X)
com.example.deep_space // (X)
```

## 공통 규칙

- 식별자 (Identifier)는 **아스키코드 글자와 숫자, 언더스코어(`_`) 만 허용**
- 정규표현식 `[^A-Za-z0-9_]` 에 부합해야 함

## 클래스 이름

- **PascalCase**로 작성
    - **첫 글자 대문자**
    - **다음 단어의 첫 글자를 대문자**로 변경
- **명사 OR 명사구**로 작성
- **테스트 클래스는 ‘Test’**로 끝남

## 메소드 이름

- **camelCase**로 작성
    - **첫 글자 소문자**
    - **다음 단어의 첫 글자를 대문자**로 변경
- 메소드 이름은 **동사**로 작성
- `전환 메서드` 혹은 `Builder 패턴 클래스의 메소드` 는 **전치사** 사용 가능

```java
renderHtml() // 동사 사용
toString() // 전환 메소드 전치사 사용
withUserId(String id) // Builder패턴 적용한 클래스의 메서드 전치사 사용
```

## 변수 이름

- **camelCase**로 작성
- **명사 OR 명사구**로 작성

## 상수 이름

- **CONSTANT-CASE**로 작성
    - 모든 글자 대문자
    - 단어와 단어 사이를 (`-`)로 연결

> 🔖 **References**
> 
> 
> [https://newwisdom.tistory.com/96](https://newwisdom.tistory.com/96)
> 
> [https://lanuarius19.tistory.com/entry/JAVA-자바-코드-컨벤션](https://lanuarius19.tistory.com/entry/JAVA-%EC%9E%90%EB%B0%94-%EC%BD%94%EB%93%9C-%EC%BB%A8%EB%B2%A4%EC%85%98)
> 
> [https://naver.github.io/hackday-conventions-java/#line-wrapping](https://naver.github.io/hackday-conventions-java/#line-wrapping)
> 
> [https://sihyung92.oopy.io/af26a1f6-b327-45a6-a72b-c6fcb754e219](https://sihyung92.oopy.io/af26a1f6-b327-45a6-a72b-c6fcb754e219)
> ****[https://google.github.io/styleguide/javaguide.html#s3.4.1-one-top-level-class](https://google.github.io/styleguide/javaguide.html#s3.4.1-one-top-level-class)
>