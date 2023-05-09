package com.tang.tangBoard.coment;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;

import com.tang.tangBoard.board.Board;
import com.tang.tangBoard.user.SiteUser;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Coment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "TEXT")
    private String content;
    
    @CreatedDate
    private LocalDateTime createDate;
    
    @ManyToOne
    private Board board;
    
    @ManyToOne //여러개의 답변이 한명의 작성자에게 작성될 수 있으므로 @ManyToOne 관계 성립
	private SiteUser author;
    
    private LocalDateTime modifyDate; 
}