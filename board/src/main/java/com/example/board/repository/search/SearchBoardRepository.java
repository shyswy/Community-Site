package com.example.board.repository.search;

import com.example.board.entitiy.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SearchBoardRepository {
    Board search1();

    Page<Object[]> searchPage(String type, String keyword, Pageable pageable);
    // PageDTO아닌 이유는 가능하면 Repository에서 DTO를 사용하지 않기 위함.


}
