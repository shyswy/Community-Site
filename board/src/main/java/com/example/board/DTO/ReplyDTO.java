package com.example.board.DTO;


import com.example.board.entitiy.Board;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReplyDTO {
    private Long rno;

    private String text;

    private String replyer;

    private Long bno; //연관된 게시물 객체에서 게시물 번호만 저장한다.

    private LocalDateTime regDate,modDate;

}
