package com.example.board.repository.search;

import com.example.board.entitiy.Board;
import com.example.board.entitiy.QBoard;
import com.example.board.entitiy.QMember;
import com.example.board.entitiy.QReply;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;


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
}
