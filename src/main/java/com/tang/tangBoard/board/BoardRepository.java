//데이터 처리를 위해서는 실제 데이터베이스와 연동하는 JPA 리포지터리가 필요하다.
package com.tang.tangBoard.board;

import java.util.List;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Integer>{
	Board findBySubject(String subject);
	Board findBySubjectAndContent(String subject, String conetent);
	List<Board> findBySubjectLike(String subject);
	Page<Board> findAll(Pageable pageable);

}
