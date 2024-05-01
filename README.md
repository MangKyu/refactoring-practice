# Refactoring Practice 프로젝트 소개
해당 프로젝트는 인텔레지에의 단축키를 연습하기 위한 프로젝트입니다.
해당 프로젝트는 기본적으로 백명석님의 유튜브 [클린코더스](https://www.youtube.com/playlist?list=PLeQ0NTYUDTmMM71Jn1scbEYdLFHz5ZqFA)를 참고하여 작성되었습니다.

## 기존 코드의 문제점 찾기
- 메인 클래스의 역할 문제
  - 메인 클래스의 이름은 Fitness Example인데, 실제 로직은 테스트용 Html을 build하고 있음
  - 따라서 Html을 build 하는 로직을 다른 클래스로 위임시켜주어야 함
- 코드 중복
  - 유사하게 보이는 코드들이 지나치게 많이 반복되고 있음
  - 중복되는 부분을 메서드로 추출하고 재사용할 수 있어야 함


## 리팩토링 시작 전에
- 커버리지와 함께 실행하는 단축키 설정
- 테스트 커버리지를 통해 모든 코드의 변경이 보호되고 있음을 확인하기


## 리팩토링 진행하기
### 1. FitnessExample 클래스와 TestableHtmlBuilder 클래스 분리하기
- 문제점
  1. FitnessExample은 코드를 실행하는 메인 클래스임
  2. 그런데 테스트용 Html을 build하는 로직까지 갖고 있음
  3. 테스트용 Html을 생성하는 클래스를 분리시키기
- 리팩터링
  1. FitnessExample 클래스의 testableHtml 메서드에 Control + T 클릭
  2. Introducte Parameter Object를 클릭 후 진행
- 결과
  1. TestableHtmlBuilder 클래스가 생성됨

### 2. 테스트용 Html을 TestableHtmlBuilder로 위임하기 위한 사전 준비

- 문제점
  1. 테스트용 html을 생성하는 메서드를 TestableHtmlBuilder로 옮기는 것이 자연스러움
- 리팩터링
  1. Option + 윗 방향키로 테스트용 html을 생성 로직 선택
  2. 메서드 추출(Command + Option + M)
  3. 추출된 메서드 구현으로 이동(Command + B)
  4. static 키워드 제거
- 결과
  1. TestableHtmlBuilder를 파라미터로 받아서 Html을 생성하는 메서드가 추출됨

### 3. 테스트용 Html 생성 코드를 TestableHtmlBuilder로 위임하기

- 문제점
  1. 테스트용 html을 생성하는 메서드를 TestableHtmlBuilder로 옮기는 것이 자연스러움
- 리팩터링
  1. 추출한 메서드 위에서 F6(Move Instance Method)
- 결과
  1. 테스트용 html을 생성하는 메서드가 TestableHtmlBuilder로 옮겨짐

### 4. 레코드 클래스인 TestableHtmlBuilder를 일반 클래스로 변경

- 문제점
  1. 자주 사용될 파라미터를 클래스의 필드로 추출하고 싶음
  2. 현재는 레코드 클래스라 필드로의 추출이 어려움
  3. 따라서 레코드 클래스인 TestableHtmlBuilder를 일반 클래스로 변경함
- 리팩터링
  1. TestableHtmlBuilder 위에서 Option +Enter 클릭
  2. convert record to class를 선택하여 일반 클래스로 변경함
- 결과
  1. TestableHtmlBuilder가 일반 클래스로 변경됨

### 5. 자주 사용되는 변수를 필드로 추출함

- 문제점
  1. WikiPage와 StringBuffer는 모든 로직에서 사용됨
  2. 이 상황에서 메서드들을 추출하면 파라미터의 수가 지나치게 많아지게 됨
  3. 또한 해당 필드를 클래스 변수로 옮기면 메서드의 응집도를 높일 수 있음
- 리팩터링
  1. WikiPage, StringBuffer 변수에서 Command + Option + F 클릭
  2. Initialize In을 Constructor로 변경하고 완료함
- 결과
  1. WikiPage, StringBuffer 변수가 클래스 필드로 옮겨짐

### 6. 중복되는 메서드들 제거하기(1차)

- 문제점
  1. 중복적으로 StringBuilder에 문자열을 append하는 로직이 반복됨
  2. buffer.append("!include -setup .").append(pagePathName).append("\n");
     buffer.append("!include -teardown.").append(pagePathName).append("\n");
  3. 위의 메서드에서 setup 부분을 제외하고는 teardown 부분과 동일함
- 리팩터링
  1. setup 부분을 선택(Option + 위)하여 변수로 추출함
  2. teardown 부분을 선택(Option + 위)하여 변수로 추출함
  3. WikiPagePath 변수를 선언하고 buffer에 append 하는 부분을 메서드로 추출하기(Command + Option + M)
- 결과
  1. 중복이 제거된 하나의 메서드가 탄생함

### 7. 중복되는 메서드들 제거하기(2차)

- 문제점
  1. WikiPage를 선언하고 null이 아닐 경우 append 하는 로직이 중복됨
  2. 해당 부분을 하나의 메서드로 추출하여 중복을 제거함
- 리팩터링
  1. 중간에 teardown 텍스트를 추출한 부분이 다른 라인과 패턴 매칭되지 않아서 함께 메서드로 뽑히지 않음
  2. teardown 텍스트를 변수로 선언하는 부분을 위로 옮기기(Command + Shift + 위)
  3. 이후에 중복되는 메서드들을 Command + 위로 선택하여 메서드 추출(Command + Option + M)
  4. 이때 AcceptSignatureChange를 선택하면 위의 다른 메서드들도 유사하게 뽑아지도록 메서드가 변경됨
- 결과
  1. 중복이 제거된 하나의 메서드가 탄생함

### 8. 변수들을 inline 처리하기

- 문제점
  1. 중간에 애매하게 스트링 변수를 선언하는 코드들이 존재함
  2. 인라인 처리하여 최대한 불필요한 라인수를 줄이기
- 리팩터링
  1. setupMode, teardownMode 위에서 인라인 처리(Command + Option + N)
  2. teardown 텍스트를 변수로 선언하는 부분을 위로 옮기기(Command + Shift + 위)
  3. 이후에 중복되는 메서드들을 Command + 위로 선택하여 메서드 추출(Command + Option + M)
  4. 이때 AcceptSignatureChange를 선택하면 위의 다른 메서드들도 유사하게 뽑아지도록 메서드가 변경됨
- 결과
  1. 중복이 제거된 하나의 메서드가 탄생함

### 8. 가독성 좋게 거대한 메서드들을 추출하고 중복 제거

- 문제점
  1. 퍼블릭 메서드에 지나치게 많은 부분이 공개되어 있음
  2. 메서드를 추출하고 추상화하여 퍼블릭 메서드를 간소화하기
- 리팩터링
  1. 추출한 세머드들을 코드 블럭으로 선택(Command + 위)하기
  2. 메서드 추출(Command + Option + M)
  3. 이후에 하나의 If 문으로 합치기
- 결과
  1. 중복이 제거된 하나의 메서드가 탄생함
  2. 하나의 분기 아래에 메서드들이 응집됨

### 9. 불필요한 메서드 접근 제거 및 메서드 추출

- 문제점
  1. pageData에 접근할 때 불필요하게 메서드 호출로 접근하고 있음
  2. 변수 접근을 바로 할 수 있도록 함
  3. 또한 Test 페이지 여부를 검사하는 메서드를 가독성 좋게 분리함
- 리팩터링
  1. pageData 메서드에 접근하는 부분을 인라인 처리(Command + Option + N)
  2. 테스트 패이지인지 검사하는 부분을 메서드 추출(Command + Option + M)
- 결과
  1. 불필요하게 메서드를 통해 접근하지 않고, 변수로 바로 접근함
  2. 테스트 페이지인지 검사하는 읽기 좋은 메서드가 추출됨