package com.example.board.Controller;


import com.example.board.DTO.PageRequestDTO;
import com.example.board.Service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
@Slf4j
public class HomeController {
    private final BoardService boardService;


    @GetMapping("/")
    public String home(PageRequestDTO pageRequestDTO, Model model){
        //스프링 MVC 는 파라미터를 자동으로 수집한다. 화면쪽에서 page, size 정보를 url에 담아 보내면
        //적절한 pageRequestDTO 객체를 찾아 수집된다.

        //void>> 자동으로 매핑된 url 리턴.
        // 따라서 /board/list 가 리턴된다!
        log.info("list.........................."+pageRequestDTO);
        //get으로 list 가기전 적절한 페이지 정보 로드후 list.html의 result에에 PageRsultDTO<BoardDTO,Object[]> 타입을 전달
        model.addAttribute("result",boardService.getList(pageRequestDTO));

        return "board/list";
    }




}
