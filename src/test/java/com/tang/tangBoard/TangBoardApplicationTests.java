package com.tang.tangBoard;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.tang.tangBoard.board.BoardService;

@SpringBootTest
class TangBoardApplicationTests {

	@Autowired
<<<<<<< HEAD
	private BoardService boardService;
	@Test
	void contextLoads() {
		for(int i=1; i<=100; i++) {
			String subject = String.format("테스트데이터:[%03d]",i);
			String content = "내용무";
			this.boardService.create(subject, content, null);
		}
	}
	
=======
    private BoardService questionService;

    @Test
    void testJpa() {
        for (int i = 1; i <= 300; i++) {
            String subject = String.format("테스트 데이터입니다:[%03d]", i);
            String content = "내용무";
            this.questionService.create(subject, content, null, null);
        }
    }
>>>>>>> 7d01f6b (게시판 만들기 수정)

}
