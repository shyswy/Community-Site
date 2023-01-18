package com.example.board.Controller;

import com.example.board.DTO.ReplyDTO;
import com.example.board.Service.ReplyService;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController        //모든 메서드의 리턴 타입은 기본 JSON.
@Log4j2
@RequestMapping("/replies/")
@RequiredArgsConstructor
public class ReplyController {
    private final ReplyService replyService;
    @GetMapping(value = "/board/{bno}",produces = MediaType.APPLICATION_JSON_VALUE )
    //{bno} << 이 영역을 PathVariable로 처리한다. url 들어오면 들어온 bno 데이터를 변수로 처리할 수 있게 된다.
    public ResponseEntity<List<ReplyDTO>> getListByBoard(@PathVariable("bno") Long bno){
        log.info("bno: "+bno);
        return new ResponseEntity<>(replyService.getList(bno), HttpStatus.OK);
        //메서드 반환 타입은 ResponseEntity라는 객체 이용. HTTP의 상태 코드 등을 같이 전달 가능.
    }

    @PostMapping("")
    public ResponseEntity<Long> register(@RequestBody ReplyDTO replyDTO){
        //@RequestBOdy: JSON으로 들어오는 데이터를 자동으로 해당 타입의 객체로 매핑한다.
        //이를 통해 별도로 JSON을 특정 데이터 타입으로 변환해서 처리할 수 있다.
        log.info(replyDTO);
        Long rno = replyService.register(replyDTO);
        return new ResponseEntity<>(rno,HttpStatus.OK);
    }

    @DeleteMapping("/{rno}")
    public ResponseEntity<String> remove(@PathVariable("rno")Long rno){
        log.info("rno: "+rno);
        replyService.remove(rno);
        return new ResponseEntity<>("success",HttpStatus.OK);// 삭제완료 메세지를 JSON 데이터로 전달.
    }

    @PutMapping("/{rno}")
    public ResponseEntity<String> modify(@RequestBody ReplyDTO replyDTO){
        log.info(replyDTO);
        replyService.modify(replyDTO);
        return new ResponseEntity<>("success",HttpStatus.OK);
    }
}
