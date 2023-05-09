package com.tang.tangBoard.coment;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.tang.tangBoard.board.Board;
import com.tang.tangBoard.user.SiteUser;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ComentService {

	
	private final ComentRepository comentRepository;
	
	public void create(Board board, String content,SiteUser author) {
		Coment coment = new Coment();
		coment.setContent(content);
		coment.setCreateDate(LocalDateTime.now());
		coment.setBoard(board);
		coment.setAuthor(author);
		this.comentRepository.save(coment);
	}
}
