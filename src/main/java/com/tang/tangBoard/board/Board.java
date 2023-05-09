package com.tang.tangBoard.board;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;

import com.tang.tangBoard.coment.Coment;
import com.tang.tangBoard.user.SiteUser;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Board {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
	
	@Column(length = 200)
	private String subject;
	
	@Column(length = 200)
	private String content;
	
	@CreatedDate
	private LocalDateTime createDate;

	@OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE)
	private List<Coment> comentList; 
	
	@Column
	private String filename;
	
	@Column
	private String filepath;
	
	@ManyToOne //여러개의 질문이 한명의 작성자에게 작성될 수 있으므로 @ManyToOne 관계 성립
	private SiteUser author;
	
	private LocalDateTime modifyDate; 
}
