package com.example.board.repository;

import com.example.board.entitiy.Board;
import com.example.board.entitiy.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply,Long> {//객체, PK 타입
    @Modifying
    @Query("DELETE FROM Reply r WHERE r.board.bno =:bno")
    void deleteByBno(@Param("bno") Long bno);
    //게시물(Board) 객체로 해당 게시물에 달린 댓글 가져오기
    List<Reply> getRepliesByBoardOrderByRno(Board board);
}
