package com.example.board.Service;

import com.example.board.DTO.BoardDTO;
import com.example.board.DTO.PageRequestDTO;
import com.example.board.DTO.PageResultDTO;
import com.example.board.entitiy.Board;
import com.example.board.entitiy.Member;

public interface BoardService {
    Long register(BoardDTO dto);
    void modify(BoardDTO dto);
    Board DtoToEntity(BoardDTO dto);
    BoardDTO EntityToDTO(Board board,Member writer, Long replyCount);
    PageResultDTO<BoardDTO,Object[]> getList(PageRequestDTO pageRequestDTO);
    BoardDTO get(Long bno);
    void deleteBoardWithReply(Long bno);
}
