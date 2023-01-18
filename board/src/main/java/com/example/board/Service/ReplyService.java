package com.example.board.Service;

import com.example.board.DTO.ReplyDTO;
import com.example.board.entitiy.Board;
import com.example.board.entitiy.Reply;

import java.util.List;

public interface ReplyService {

    Long register(ReplyDTO replyDTO); //댓글 등록

    List<ReplyDTO> getList(Long bno); //특정 게시물의 댓글 목록

    void modify(ReplyDTO replyDTO);// 댓글 수정

    void remove(Long rno); //댓글 삭제

    //ReplyDTO를 Reply객체로 변환 (지니고 있는 Board 객체 역시 처리해야한다)
    default Reply dtoToEntity(ReplyDTO replyDTO){
        Board board = Board.builder()  //ReplyDTO의 Board 번호로 Board Build
                .bno(replyDTO.getBno())
                .build();
        Reply reply=Reply.builder()
                .board(board)           //Build한 board 객체를 추가
                .rno(replyDTO.getRno())
                .text(replyDTO.getText())
                .replyer(replyDTO.getReplyer())
                .build();
        return reply;
    }
    default ReplyDTO entityToDTO(Reply reply){

        ReplyDTO replyDTO=ReplyDTO.builder()
                .rno(reply.getRno())
                .text(reply.getText())
                .replyer(reply.getReplyer())
                .regDate(reply.getRegDate())
                .modDate(reply.getModDate())
                .build();
        return replyDTO;
    }
}
