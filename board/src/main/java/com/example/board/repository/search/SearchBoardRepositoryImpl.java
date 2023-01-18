package com.example.board.repository.search;

import com.example.board.entitiy.Board;
import com.example.board.entitiy.QBoard;
import com.example.board.entitiy.QMember;
import com.example.board.entitiy.QReply;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;
import java.util.stream.Collectors;


@Log4j2
public class SearchBoardRepositoryImpl extends QuerydslRepositorySupport implements SearchBoardRepository{



    public SearchBoardRepositoryImpl() {
        super(Board.class);
    }

    @Override
    public Board search1() {
        log.info("search1..........");
        QBoard board =QBoard.board;
        QReply reply=QReply.reply;
        QMember member=QMember.member;
        JPQLQuery<Board> jpqlQuery=from(board);
        jpqlQuery.leftJoin(member).on(board.writer.eq(member));//board의 writer와 member가 같으면 join
        jpqlQuery.leftJoin(reply).on(reply.board.eq(board));//주어진 board와 reply의 board가 일치하면 join
        //다른 엔티티와의 조인: join(), leftJoin(), rightJoin() 사용
        //필요에 따라 .on()으로 Join 조건 추가.

        JPQLQuery<Tuple> tuple= jpqlQuery.select(board,member.email,reply.count());
        tuple.groupBy(board);
        log.info("------------------------------");
        log.info(tuple);
        log.info("------------------------------");
        List<Tuple> result=tuple.fetch();
        return null;

    }

    @Override
    public Page<Object[]> searchPage(String type, String keyword, Pageable pageable) {
        log.info("searchPage............................");
        QBoard board = QBoard.board;
        QMember member = QMember.member;
        QReply reply = QReply.reply;
        JPQLQuery<Board> jpqlQuery=from(board);
        jpqlQuery.leftJoin(member).on(board.writer.eq(member));
        jpqlQuery.leftJoin(reply).on(reply.board.eq(board));
        //여기까지 필요한 Table Join

        //필요한 attribute를 select
        JPQLQuery<Tuple> tuple=jpqlQuery.select(board,member,reply.count());

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        BooleanExpression expression = board.bno.gt(0L);
        /*
        == | eq
        != | ne
        < | lt
        > | gt
        <= | le
        >= | ge
         */
        booleanBuilder.and(expression);//gt(0L) -> 0이상 -> 전체 board
        if(type!=null){//type: tcw  , t  ,   tw  등 검색설정 조합
            String[] typeArr =type.split("");//각각 쪼개기. tcw -> t, c, w
            BooleanBuilder conditionalBuilder =new BooleanBuilder();
            for(String t:typeArr){
                switch (t){
                    case "t":
                        conditionalBuilder.or(board.title.contains(keyword));
                        break;
                    case "w":
                        conditionalBuilder.or(member.email.contains(keyword));
                        break;
                    case "c":
                        conditionalBuilder.or(board.content.contains(keyword));
                }
            }
            booleanBuilder.and(conditionalBuilder);// 키워드 검색 결과 and 전체 board -> 키워드 검색으로 나온 board
        }
        tuple.where(booleanBuilder);// tuple 중, 조건에 맞는 게시물만

        //order by
        Sort sort=pageable.getSort();// Sort 타입은 내부적으로 여러 Sort개체를 지닐 수 있기에 forEach로
        sort.stream().forEach(order->{ //bno-Descending, title-Ascending 2가지가 들어온다.
            Order direction=order.isAscending()?Order.ASC:Order.DESC; //sort가 DESC or ASC
            String prop=order.getProperty(); //pageable의 sort에 들어온 정렬 기준이 되는 attribute
            log.info("prop: "+prop);

            PathBuilder orderByExpression=new PathBuilder(Board.class,"board");
            tuple.orderBy(new OrderSpecifier(direction,orderByExpression.get(prop)));
        });

        tuple.groupBy(board);// 게시물 객체별 분류

        //page 처리
        tuple.offset(pageable.getOffset()); //시작 offset 설정
        tuple.limit(pageable.getPageSize()); //한 페이지에 표시할 수 있는 만큼만

        List<Tuple> result=tuple.fetch();

        log.info(result);

        long count=tuple.fetchCount(); //pageable은 Count가 필요하다!
        log.info("COUNT: "+count);
        return new PageImpl<Object[]>(
                result.stream().map(t -> t.toArray()).collect(Collectors.toList()), pageable, count
        );
        //result를 Stream으로 바꿔 Map을 사용하여 각 튜플을(Tuple) -> List로 변환 후, 다시 List로 collect (toList() )


    }
}
