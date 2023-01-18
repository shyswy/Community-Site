package com.example.board.Service;

import com.example.board.DTO.ReplyDTO;
import com.example.board.entitiy.Board;
import com.example.board.entitiy.Reply;
import com.example.board.repository.ReplyRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService{ //CRUD
    private final ReplyRepository replyRepository;
    @Override
    public Long register(ReplyDTO replyDTO) { //Create
        Reply reply=dtoToEntity(replyDTO);
        replyRepository.save(reply);
        return reply.getRno();
    }

    @Override
    public List<ReplyDTO> getList(Long bno) { //Read
        List<Reply> replyList = replyRepository.getRepliesByBoardOrderByRno(Board.builder().bno(bno).build());
        return replyList.stream().map(reply -> entityToDTO(reply)).collect(Collectors.toList());//모든 reply들을 DTO로
    }

    @Override
    public void modify(ReplyDTO replyDTO) { //Update
        Reply reply= dtoToEntity(replyDTO);
        replyRepository.save(reply);
    }
    @Override
    public void remove(Long rno) {  //Delete
        replyRepository.deleteById(rno);
    }
}

