package com.tang.tangBoard.board;



import java.io.File;

import java.io.IOException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tang.tangBoard.DataNotFoundException;
import com.tang.tangBoard.user.SiteUser;
import com.tang.tangBoard.user.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BoardService {

	private final BoardRepository boardRepository;
	private final UserRepository userRepository;
	 
	private static final String UPLOAD_PATH = "C:\\uploads"; // 파일 업로드 경로, 적절한 경로로 수정해주세요.
	/*게시물 목록 */
	public List<Board> getList() {
		return this.boardRepository.findAll();
		
	}
	
	 public Board getBoard(Integer id) {  
	        Optional<Board> board = this.boardRepository.findById(id);
	        if (board.isPresent()) {
	            return board.get();
	        } else {
	            throw new DataNotFoundException("board not found");
	        }
	    }
	 /* 게시물 등록 (제목, 내용, 파일업로드) */
	 public void create(String subject, String content,MultipartFile file,SiteUser user) {
	        Board q = new Board();
	        q.setSubject(subject);
	        q.setContent(content);
	        q.setCreateDate(LocalDateTime.now());
	        q.setAuthor(user);
	        if (file != null && !file.isEmpty()) { // 파일이 업로드된 경우
	        	String originalFilename = file.getOriginalFilename(); // 파일 원본 이름
	        	String extension = originalFilename.substring(originalFilename.lastIndexOf(".")); // 파일 확장자
	        	String savedFilename = UUID.randomUUID().toString(); // 저장될 파일 이름 (랜덤 UUID)
	        	String filePath = savedFilename + extension; // 저장될 파일 경로 (랜덤 UUID + 확장자)

	        	File savedFile = new File(UPLOAD_PATH, filePath); // 저장될 파일 객체 생성

	        	try {
	        	    file.transferTo(savedFile); // 업로드된 파일을 저장될 파일로 저장
	        	} catch (IOException e) {
	        	    e.printStackTrace();
	        	}

	        	q.setFilename(originalFilename); // 업로드된 파일의 원본 이름을 속성에 설정
	        	q.setFilepath(filePath); // 저장된 파일 경로(랜덤 UUID + 확장자)을 속성에 설정
			}
	        
	        
	        this.boardRepository.save(q);
	    }
	 /* 파일 다운로드 */
	 public Resource getFile(Integer id) throws IOException {
	        Board board = getBoard(id);
	        String filepath = board.getFilepath();
	        File file = new File(UPLOAD_PATH, filepath);
	        if (file.exists()) {
	            return new FileSystemResource(file);
	        } else {
	            throw new DataNotFoundException("file not found");
	        }
	    }
	/* 페이징 기능 */
	public Page<Board> getList(int page){
		/* 작성일시 역순으로 조회하기 */
		List<Sort.Order> sorts = new ArrayList<>();
		sorts.add(Sort.Order.desc("createDate"));
		Pageable pageable = PageRequest.of(page, 10,Sort.by(sorts));
		return this.boardRepository.findAll(pageable);
	}
	
	public SiteUser getUser(String username) {
		Optional<SiteUser> siteUser = this.userRepository.findByusername(username);
		  if (siteUser.isPresent()) {
	            return siteUser.get();
	        } else {
	            throw new DataNotFoundException("siteuser not found");
	        }
	    }
	public void modify(Board board, String subject, String content) {
		board.setSubject(subject);
		board.setContent(content);
		board.setModifyDate(LocalDateTime.now());
		this.boardRepository.save(board);
	}
	
	 
	}