# README

# Community Site

Spring framework와 Spring JPA, Spring Data JPA, Querydsl, Rest Method를 사용한 댓글 기능 처리를 구현하였습니다.

# Simple Overview

## 게시물 목록 화면

![Untitled](README%20f44be134ab3d4db881e9530ad97dcd2d/Untitled.png)

## 게시물 작성 화면

![Untitled](README%20f44be134ab3d4db881e9530ad97dcd2d/Untitled%201.png)

## 게시물 조회 화면

![Untitled](README%20f44be134ab3d4db881e9530ad97dcd2d/Untitled%202.png)

## 댓글 작성 화면

![Untitled](README%20f44be134ab3d4db881e9530ad97dcd2d/Untitled%203.png)

# 도메인 클래스

![Untitled](README%20f44be134ab3d4db881e9530ad97dcd2d/Untitled%204.png)

## Base Entity

Reply, Board, Member에 공통으로 필요한 데이터를 정의한 추상 클래스. 이메일, 비밀번호, 이름이 포함되어 있다.

![Untitled](README%20f44be134ab3d4db881e9530ad97dcd2d/Untitled%205.png)

## Member

email주소를 Primary key로 설정한다.

![Untitled](README%20f44be134ab3d4db881e9530ad97dcd2d/Untitled%206.png)

## Board

게시물에 포함되어야할 정보들과 Member와 ManyToOne 관계를 형성해준 뒤, 추후 제목이나 내용을 수정하기 위한 함수를 정의한다. Board를 식별하는 Primary Key는 bno라는 게시물 번호를 자동 생성하여 사용한다.

![Untitled](README%20f44be134ab3d4db881e9530ad97dcd2d/Untitled%207.png)

## Reply

댓글에 포함되어야할 정보들과 Board 엔티티와 Many To One 관계를 형성해준다. Reply를 식별하는 Primary Key 역시 자동으로 생성한다.

![Untitled](README%20f44be134ab3d4db881e9530ad97dcd2d/Untitled%208.png)

# DTO

DTO에 대한 설명입니다.

## Page DTO

페이징 처리를 위한 DTO들에 대한 설명입니다.

### PageRequestDTO

화면에 전달되는 목록 데이터를 처리하는 DTO이다.

데이지 정보를 담을 데이터들과 디폴트 생성자, Sort 방식을 파라미터로 받아 Page를 생성하는 

getPageable 메소드로 구성되어 있다.

![Untitled](README%20f44be134ab3d4db881e9530ad97dcd2d/Untitled%209.png)

### PageResultDTO

모든 필드 대상을 생성하고, 접근자를 만들어준다. 유동적인 사용을 위해 제네릭 타입을 통해 DTO와

임의의 Entity 간의 중간다리 역할을 수행한다.

기본 데이터

![Untitled](README%20f44be134ab3d4db881e9530ad97dcd2d/Untitled%2010.png)

메소드

Java Stream의 map, collect를 통해 파라미터로 들어온 해당 엔티티의 적절한 EntityToDTO 함수를 받은 뒤, DTO로 변환하여 저장한 뒤, 페이지 정보를 생성한다.

![Untitled](README%20f44be134ab3d4db881e9530ad97dcd2d/Untitled%2011.png)

파라미터로 들어온 Pageable을 통해 PageList를 생성한다. 

![Untitled](README%20f44be134ab3d4db881e9530ad97dcd2d/Untitled%2012.png)

이후 각 Service 계층에서  PageResultDTO의 생성자에 파라미터로 유동적인 JPQL 결과타입들(Object[]) 과 원하는 DTO로 변환하는 함수를 줘서 해결함으로써, 모든 영역에서 Page 처리를 공통적으로 처리할 수 있게 한다.

## BoardDTO

Board를 컨트롤러와 서비스 영역에서 처리하기 위한 DTO

![Untitled](README%20f44be134ab3d4db881e9530ad97dcd2d/Untitled%2013.png)

연관관계를 가진 Reply, Member 객체에 대해, 필요한 정보만을 저장한다.

## ReplyDTO

Reply를 컨트롤러와 서비스 영역에서 처리하기 위한 DTO

![Untitled](README%20f44be134ab3d4db881e9530ad97dcd2d/Untitled%2014.png)

Reply와 ManyToOne 연관관계를 가진 Board에 대해서, Board 전체 객체가 아닌 게시물 번호 정보만 저장한다.

# Interface

## Service

Service 영역에서 사용하는 Interface에 대한 설명이다.

### BoardService

기본적인 CRUD의 기틀을 잡고, Entity와 DTO간의 변환에 대한 함수는 defualt를 사용해서 인터페이스 영역에서 정의한다.

![Untitled](README%20f44be134ab3d4db881e9530ad97dcd2d/Untitled%2015.png)

![Untitled](README%20f44be134ab3d4db881e9530ad97dcd2d/Untitled%2016.png)

### ReplyService

기본적인 CRUD의 기틀을 잡고, Entity와 DTO간의 변환에 대한 함수는 defualt를 사용해서 인터페이스 영역에서 정의한다.

![Untitled](README%20f44be134ab3d4db881e9530ad97dcd2d/Untitled%2017.png)

## Repository

DB에 접근하는 Repository영역이다. Spring Data JPA를 통해 기본적인 쿼리들은 쿼리 메소드로 사용할 수 있고, 특수한 Case들은 @Query 어노테이션을 통해 정의해주었다. 

Search와 같이 여러 엔티티가 복잡하게 Join해야되는 Case는 Querydsl을 통해 별도의 인터페이스에서 사용하였다.

### BoardRepository

게시물과 관련한 쿼리들을 정의한 Repository

![Untitled](README%20f44be134ab3d4db881e9530ad97dcd2d/Untitled%2018.png)

### ReplyRepository

댓글과 관련된 쿼리를 정의한 Repository

![Untitled](README%20f44be134ab3d4db881e9530ad97dcd2d/Untitled%2019.png)

## Search

Querydsl을 통해 Search에 대한 영역을 처리하고자 별도로 생성한 영역. 

![Untitled](README%20f44be134ab3d4db881e9530ad97dcd2d/Untitled%2020.png)

# Functionality

Board(게시물)과 Reply(댓글) 의 CRUD와 필요한 기능에 대한 기능에 대해 설명하는 부분입니다.

## Board

![Untitled](README%20f44be134ab3d4db881e9530ad97dcd2d/Untitled%2021.png)

 

### Page 처리

![Untitled](README%20f44be134ab3d4db881e9530ad97dcd2d/Untitled%2022.png)

JPQL의 결과인 Page<Object[]> 를  적절한 DTO로 변환한다.

가변적인 Sort를 적용하여 0째부터 size만큼을 가져오는 Pageable 객체를 리턴한다.

![Untitled](README%20f44be134ab3d4db881e9530ad97dcd2d/Untitled%2023.png)

Result에 정보를 담는 Query

![Untitled](README%20f44be134ab3d4db881e9530ad97dcd2d/Untitled%2024.png)

PageResultDTO의 생성자.  2개의 파라미터

- 변화시킬 Object[] ( 쿼리 결과에 따라 조정 가능 >> 확장성!)
- 그리고 그 Object를 DTO로 변환시키는 함수

이 두가지를 동적으로 받아서, 모든 Entity들의 Service 계층에서 가변적인 Query의 결과 값을, 가변적인 DTO 타입으로 유동적이게 변환할 수 있게 만듬.

![Untitled](README%20f44be134ab3d4db881e9530ad97dcd2d/Untitled%2025.png)

![Untitled](README%20f44be134ab3d4db881e9530ad97dcd2d/Untitled%2026.png)

구해진 DTO 리스트를 내부에 저장.

### Board Create

Service 인터페이스에서 default를 통해 정의한 Dto To Entity 변환 함수를 통해 파라미터로 들어온 DTO를 엔티티로 변환한 뒤, boardRepository에서 Spring data JPA를 통해 정의된 save 메소드로 저장해준 뒤, 저장된 게시물의 번호를 리턴한다.

![Untitled](README%20f44be134ab3d4db881e9530ad97dcd2d/Untitled%2027.png)

### Board Delete

특정 bno( 게시물 번호) 가진 “댓글”을 삭제하기 위한 쿼리를 BoardRepository에 별도로 정의한다.

![Untitled](README%20f44be134ab3d4db881e9530ad97dcd2d/Untitled%2028.png)

BoardService영역에 특정 게시물 삭제 요청시, 해당 게시물에 대한 reply도 같이 삭제되야한다.

둘 중 한개만 삭제된다면, DB의 Consistency를 위배하는 것이기에 한 개의 transactoin으로 묶어야한다.

![Untitled](README%20f44be134ab3d4db881e9530ad97dcd2d/Untitled%2029.png)

### Board Update

Board: content, title만 수정가능, 나머진 불가능하다.  따라서 애초에 change메소드도 title, content에 대하여 각각 만든다. + DTO 전체를 받아서, 수정할 때는 변경될 수 있는 내용만 수정한다.

![Untitled](README%20f44be134ab3d4db881e9530ad97dcd2d/Untitled%2030.png)

Spring data JPA에 들어있는 쿼리 메소드인 findById는 Optional. 따라서 Optinal <Board> 받아서 추후 검증하거나, orelse로 검증 후 Board 객체로 사용.

**List**

![Untitled](README%20f44be134ab3d4db881e9530ad97dcd2d/Untitled%2031.png)

List.html에서 로드할 page 정보를 미리 세팅, 이후 “/board/list” 즉 board 디렉토리 안의 list html 리턴 -> list.html로 이동.

url에 page 정보가 들어오면, 페이지에 대한 pageRequestDTO가 수집된다.

만약 최초에   [http://localhost:8080/board/list](http://localhost:8080/board/list)    로 진입시  page, size 설정 없이 PageRequstDTO 생성 되어 생성자가 세팅한 디폴트 값인 1페이지부터 렌더링된다.

**List.html**

![Untitled](README%20f44be134ab3d4db881e9530ad97dcd2d/Untitled%2032.png)

get에서 전해준 result의 page 정보로 Page안의 BoardDTO 객체들 로드한다.

이 곳에서 이루어지는 작업은 아래와 같다.

- url에 원하는 페이지를 url에 담아 보낸다
- 컨트롤러에서 Get 방식으로 매핑 받아 url에 담긴 페이지 정보를 기반으로 pageRequestDTO 를 수집하고, boardService의 getList 메소드로 pageResultDTO를 얻은 뒤 model에 추가하여 다시 front가 해당 pageresultDTO를 “result”란 이름으로 사용할 수 있게 해준다.

### Board Read

**.List.html**

![Untitled](README%20f44be134ab3d4db881e9530ad97dcd2d/Untitled%2033.png)

사용자가 읽을 게시물 클릭시 url에 해당 게시물의 bno, 해당 게시물이 위치한 page 정보, 검색조건(type), 검색 키워드를 담아 보낸다.

![Untitled](README%20f44be134ab3d4db881e9530ad97dcd2d/Untitled%2034.png)

url에 들어있는 bno 정보, page정보를 받고, 받은 bno 정보를 기반으로

.get(bno) 메소드를 통해 적절한 boardDTO 객체를 찾아서 read.html에 “dto”라는 이름으로 전달한다.

**Read.html**

![Untitled](README%20f44be134ab3d4db881e9530ad97dcd2d/Untitled%2035.png)

전달받은 boardDTO 정보를 보여준다.

Modify: read.html 에서 modify 버튼 클릭시

[http://localhost:8080/board/modify?bno=100&page=1&type=&keyword=](http://localhost:8080/board/modify?bno=100&page=1&type=&keyword=)

위 url 정보와 함께 이동한다.

![Untitled](README%20f44be134ab3d4db881e9530ad97dcd2d/Untitled%2036.png)

Read와 동일한 매커니즘으로, 게시물을 get하고, void이므로, modify( modify.html) 로 매핑

**Modify.html**

Content, title외의 데이터는 readonly로 설정하여 content, title외의 데이터는 수정이 불가능하게 구현하였다.

![Untitled](README%20f44be134ab3d4db881e9530ad97dcd2d/Untitled%2037.png)

Modify.html에서 modify 버튼 클릭시

<form action    “/board/modify/”   method =”post” 의 url과 함께 post 방식으로 트리거한다.

![Untitled](README%20f44be134ab3d4db881e9530ad97dcd2d/Untitled%2038.png)

다시 수정했던 게시물의 read화면으로 돌아온다. 이때, page, type,keyword,bno 등 게시물에 대한

정보를 addAttribute 방식으로 넣어준 뒤, read.html에 전달해야 원래의 게시물로 돌아올 수 있다.

(addFlashAttribute는 body에 숨겨 정보가 노출되지 않는다.  Read.html은 url에서 바로 정보가져오므로 flash를 통한 방식은 불가하다.)

Remove도 유사하게 구현하였다.

![Untitled](README%20f44be134ab3d4db881e9530ad97dcd2d/Untitled%2039.png)

## Search

- Querydsl

해당 프로젝트에서 Search에 대한 처리는

Baord, Reply, Member 3개의 엔티티간의 join처리가 필요하기에 Querydsl을 통해 별도의 인터페이스로 처리하여 구현했다.

인터페이스

![Untitled](README%20f44be134ab3d4db881e9530ad97dcd2d/Untitled%2020.png)

구현 클래스

![Untitled](README%20f44be134ab3d4db881e9530ad97dcd2d/Untitled%2040.png)

해당 인터페이스, 구현 클래스를 사용하는 Repository에 extend 추가.

![Untitled](README%20f44be134ab3d4db881e9530ad97dcd2d/Untitled%2041.png)

Querydsl을 사용하지 않을 시 나오는 복잡한 쿼리

![Untitled](README%20f44be134ab3d4db881e9530ad97dcd2d/Untitled%2042.png)

Querydsl을 통한 Board, Reply, Member join처리

![Untitled](README%20f44be134ab3d4db881e9530ad97dcd2d/Untitled%2043.png)

위 코드에서 select부분은 board, member의 email, reply의 count 등

각각의 데이터를 추출한다. Board, Member, Reply 클래스 처럼 정해진 엔티티 객체 단위는 Tuple 타입을 사용해서 아래와 같이 처리한다.

![Untitled](README%20f44be134ab3d4db881e9530ad97dcd2d/Untitled%2044.png)

검색필터(제목, 내용, 작성자), 키워드, pageable(Repository에서 DTO 사용안하는 것이 좋기에 PageDTO로 처리 x) 를 통해 적절한 검색 결과 페이지객체를 받기

**SearchPage**

검색 타입( 제목, 내용, 작성자) 에 따른 적절한 페이지 객체를 받는 메소드

![Untitled](README%20f44be134ab3d4db881e9530ad97dcd2d/Untitled%2045.png)

결과값에 필요한 테이블을 join하고, select로 필요한 attribute만 선별한다.

BooleanExpression board.bno.gt(0L) 로 전체 board 설정( 디폴트, 검색필터가 없다면 전체 선택)

![Untitled](README%20f44be134ab3d4db881e9530ad97dcd2d/Untitled%2046.png)

BooleanBuilder( 마지막에 where절에 사용해서 튜플들을 선별하는 용도)에 디폴트 expression 추가.

이후 검색필터(type, t,c,w 등등…) 에 따라 적절한 conditionalBuilder를 or을 통해 생성 후 추가.

검색조건(type) 없으면 전체 선택, 존재하면 적절하게 선택하게 만든다.

**Orderby** 

![Untitled](README%20f44be134ab3d4db881e9530ad97dcd2d/Untitled%2047.png)

파라미터로 들어온 Pageable 객체의 Sort 객체에 따른( Sort 기준이 될 요소, 내림차순 or 오름차순 등등) Sort 방식을 order by 단에 적용해야하지만, JPQL에서는 Sort 객체를 지원하지 않는다.

따라서 Orderby는 OrderSpecifier를 통해 정의해줘야한다.

Order할 property(정렬 기준이 되는 것)와 Sort방식(ASC,DESC)를 추출하고

(아래와 같이, Sory객체는 여러가지 Sort 방식이 중첩되어있을 수 있다.)

![Untitled](README%20f44be134ab3d4db881e9530ad97dcd2d/Untitled%2048.png)

PathBuilder를 통해 Property를, Order을 통해 Order 방식을 추출하여 OrderSpecifier의 파라미터로 전달하는 것으로 Pageable객체 안의 Sort와 일치하는 orderBy를 적용한다.

**게시물 기준 분류 & 페이지 처리**

![Untitled](README%20f44be134ab3d4db881e9530ad97dcd2d/Untitled%2049.png)

Groupby를 통해 게시물별 분류를 진행하고, Page 처리를 진행하는데, Pageable 객체는 count가 꼭 필요하기에 추가해야한다.

우선 여태 필터링했던 tuple을 파라미터로 들어온 pageable객체의 offset~offset+페이지 크기

만큼만 필터링하여 원하는 페이지를 얻고, 튜플들을 모아놓는 result에 fetch() 해준다.

이후 fetchCount()를 통해 count를 구한다. (PageImpl에서 count가 필요하다)

이후 PageImpl에 result의 각 Tuple들을 Array로 변환해 List<Array> 타입으로 전달하고,

Page 정보를 담은 pageable 객체, Count를 PageImpl에 전달한다.

**boardService 의 getList 변경**

![Untitled](README%20f44be134ab3d4db881e9530ad97dcd2d/Untitled%2050.png)

PageRequestDTO의 정보를 기반으로, searchPage() 를 통해 적절한 PageResultDTO를 찾아 반환한다.

## Reply

Reply관련 처리
Rest 방식이라 불리는 호출을 통해 댓글을 처리한다.
모든 댓글은 게시물의 조회 화면에서 처리되고, Ajaxfmf 이용해서 컨트롤러와 JSON 포맷으로 데이터를 교환하는 방식으로 작성합니다.
브라우저에 ‘/replies/’ 라는 경로를 통해 다음과 같은 작업을 호출합니다.

방식           호출 대상          파라미터          작업                   반환 데이터
Get       /replies/board/{bno}   게시물 번호    해당 게시물의 댓글 조회   JSON 배열
POST     /replies/            JSON 댓글 데이터        댓글 추가           추가된 댓글 번호
DELETE   /replies/{rno}           댓글 번호              댓글 삭제         삭제 결과 문자열
PUT     /replies/{rno}      댓글 번호+ 수정할 내용        댓글 수정        수정 결과 문자열

특정 게시물의 댓글을 보여주는 작업은 게시물 조회화면에서 Ajax를 통해 JSON 포맷의 데이터 교환으로 처리한다.

- 게시물 로딩 후 댓글의 숫자를 클릭하면 게시물에 속한 댓글을 Ajax로 가져와서 화면에 렌더링
-특정 버튼을 클릭 시 새로운 댓글을 입력하는 모달창을 보여주고, Ajax의 POST 방식으로 댓글을 전송한다. 이후 댓글 목록을 새로 가져와서 화면상에 추가한 댓글이 반영되도록 한다.
-댓글 삭제와 수정은 댓글 등록과 동일하게 특정 모달 창에서 처리 가능하다. (추후 Spring Security를 통한 보안 기능 추가 이후 작성자만 수정-삭제가 가능하도록 처리)

위의 기능을 위해선 ReplyRepository에서 특정 게시글 번호에 대한 댓글 목록을 가져오는 기능을 추가해야한다.

### ReplyServiceImpl

Reply에 대한 기본적인 CRUD 메소드를 정의 한다. read와 delete는 Primary Key인 rno를 통해 동작하고, Create와 Update는 replyDTO를 통해 동작한다. 

![Untitled](README%20f44be134ab3d4db881e9530ad97dcd2d/Untitled%2051.png)

### Reply Controller(Rest Method)

ReplyController

![Untitled](README%20f44be134ab3d4db881e9530ad97dcd2d/Untitled%2052.png)

@RestController 를 통해 Rest 방식으로 댓글 데이터를 JSON으로 만들어서 처리할 것이기에, 별도의 화면처리 없이 데이터만을 전송한다.

“/replies/board/{bno}” 의 url 사용시 bno에 해당하는 댓글들을 replyService의 getList를 통해
얻은 뒤, JSON 데이터로 리턴한다.

![Untitled](README%20f44be134ab3d4db881e9530ad97dcd2d/Untitled%2053.png)

### Reply Read

조회 화면에서 사용자가 해당 게시물의 댓글 수를 파악하고, 댓글의 숫자를 클릭할 시, Ajax로 데이터를 처리한다.

Read.html 수정

댓글 추가, 댓글 조회 버튼을 추가한다.

![Untitled](README%20f44be134ab3d4db881e9530ad97dcd2d/Untitled%2054.png)

JavaScript
댓글 조회를 담당하는 replyList에 대한 버튼 클릭 이벤트 발생 시, getListByBoard 가 get 방식으로 매핑되는 /replies/board/ 의 주소에 게시물 번호인 bno를 추가하여 전달하고, 그 결과 값으로 반환되는 JSON 데이터를 getJSON으로 얻고 정보를 렌더링 한다.

![Untitled](README%20f44be134ab3d4db881e9530ad97dcd2d/Untitled%2055.png)

### Reply Create

Add Reply 클릭 시, 모달 창을 띄워서 댓글 내용, 작성자를 입력하고, save를 클릭해 정보를 저장한다.

![Untitled](README%20f44be134ab3d4db881e9530ad97dcd2d/Untitled%2056.png)

**댓글 추가 이벤트 발생 처리**

![Untitled](README%20f44be134ab3d4db881e9530ad97dcd2d/Untitled%2057.png)

**댓글 추가 작업**
Replycount ~ 버튼을 클릭 시  다음과 같은 모달 창이 뜬다.

![Untitled](README%20f44be134ab3d4db881e9530ad97dcd2d/Untitled%2058.png)

**추가된 댓글 저장**

모달창에서 “save” 버튼 클릭시 이벤트.
Post 방식으로 /replies/ url로 매핑하면서 JSON.stringigy(reply) 로 해당 댓글에 대한 JSON 데이터를 같이 전달한다.

![Untitled](README%20f44be134ab3d4db881e9530ad97dcd2d/Untitled%2059.png)

매핑 받은 reply에 대한 JSON 데이터를 @RequestBody를 통해 ReplyDTO 객체로 변환 받은 후,
replyService의 register함수를 통해 저장한다.

### Reply Delete

삭제나 수정을 원하는 댓글을 클릭 시 다음과 같은 수정 모달 창이 뜬다.

![Untitled](README%20f44be134ab3d4db881e9530ad97dcd2d/Untitled%2060.png)

모달 창에 클릭된 댓글의 정보를 렌더링한다.

![Untitled](README%20f44be134ab3d4db881e9530ad97dcd2d/Untitled%2061.png)

모달 창에서 삭제 버튼 클릭 시
댓글의 rno 정보를 url에 넣어주고 post 방식으로 컨트롤러에게 넘겨준다.
이후 컨트롤러에서 JSON 데이터가 리턴 되었는데, 메시지가 Success면 삭제 메시지 출력후 종료.

![Untitled](README%20f44be134ab3d4db881e9530ad97dcd2d/Untitled%2062.png)

Front 영역에서 전달한 url에 들어있는 rno 정보를 기반으로 삭제한다.

![Untitled](README%20f44be134ab3d4db881e9530ad97dcd2d/Untitled%2063.png)

### Reply Modify

Modify 버튼 클릭시 이벤트 처리

![Untitled](README%20f44be134ab3d4db881e9530ad97dcd2d/Untitled%2064.png)

Rno를 url에 담아 put 방식으로 전달한다.

전달 받은 rno에 대해 적절한 replyDTO를 @RequestBody를 통해 얻은 뒤, 수정한다.

![Untitled](README%20f44be134ab3d4db881e9530ad97dcd2d/Untitled%2065.png)

# Test

**Member Insert Test**

Member를 추가하는 기능을 테스트 한다.

![Untitled](README%20f44be134ab3d4db881e9530ad97dcd2d/Untitled%2066.png)

**Board read Test**

게시물을 댓글, 혹은 작성자로 Read하는 기능을 테스트한다.

![Untitled](README%20f44be134ab3d4db881e9530ad97dcd2d/Untitled%2067.png)

**SeeBoardOfMember Test**

게시물의 작성자를 찾는 테스트

![Untitled](README%20f44be134ab3d4db881e9530ad97dcd2d/Untitled%2068.png)

**Modify Test**

게시물 수정 기능에 대한 테스트

![Untitled](README%20f44be134ab3d4db881e9530ad97dcd2d/Untitled%2069.png)

**페이지 Search Test**

주어진 Page 요청에 적절한 Page가 찾아지는지 확인하는 Test

![Untitled](README%20f44be134ab3d4db881e9530ad97dcd2d/Untitled%2070.png)

**ListByBoard Test.**

게시물로 댓글 조회하는 기능을 Test

![Untitled](README%20f44be134ab3d4db881e9530ad97dcd2d/Untitled%2071.png)

**getList Test**

게시물 번호를 통해 reply 목록을 반환하는 getList 메소드의 기능 Test

![Untitled](README%20f44be134ab3d4db881e9530ad97dcd2d/Untitled%2072.png)