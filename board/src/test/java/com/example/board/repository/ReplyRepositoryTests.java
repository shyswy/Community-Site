package com.example.board.repository;

import com.example.board.DTO.ReplyDTO;
import com.example.board.Service.ReplyService;
import com.example.board.entitiy.Board;
import com.example.board.entitiy.Reply;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.IntStream;

@SpringBootTest
public class ReplyRepositoryTests {
    @Autowired
    private ReplyRepository replyRepository;

    @Autowired
    private ReplyService replyService;

    @Test
    public void insertTest(){//reply insert test
        IntStream.rangeClosed(1,100).forEach(i->{
            Long bno=(long) (Math.random()*100)+1;
            Board board=Board.builder()
                    .bno(bno)
                    .build();
            Reply reply=Reply.builder()
                    .board(board)
                    .replyer("guest"+i)
                    .text("text contents")
                    .build();
            replyRepository.save(reply);
        });
    }

    @Test
    public void testListByBoard(){
        List<Reply> replyList = replyRepository.getRepliesByBoardOrderByRno(
                Board.builder()
                        .bno(77L)
                        .build()
        );
        replyList.forEach(reply -> {
            System.out.println(reply);
        });

    }

    @Test
    public void testGetList(){
        List<ReplyDTO> replyDTOList = replyService.getList(77L);
        replyDTOList.forEach(replyDTO -> {
            System.out.println(replyDTO);
        });
    }
}
