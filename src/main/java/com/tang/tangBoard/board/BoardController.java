package com.tang.tangBoard.board;



import java.io.IOException;
import java.security.Principal;


import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.tang.tangBoard.user.SiteUser;
import com.tang.tangBoard.user.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/board")
public class BoardController {

	private final BoardService boardService;
	 private final UserService userService;
	
	@GetMapping("/list")
	public String list(Model model, @RequestParam(value = "page",defaultValue = "0") int page) {
		Page<Board> paging = this.boardService.getList(page);
		model.addAttribute("paging",paging);
		return "Board_list";
	}
	
	@GetMapping("/detail/{id}")
	public String detail(Model model, @PathVariable("id") Integer id) {
		Board board = this.boardService.getBoard(id);
		model.addAttribute("board",board);
		return "Board_detail";
	}
		@PreAuthorize("isAuthenticated()")
		@GetMapping("/create")
	    public String boardCreate(BoardForm boardForm) {
	        return "Board_create";
	    }
		@PreAuthorize("isAuthenticated()")
		@PostMapping("/create")
		public String boardCreate(@Valid BoardForm boardForm, BindingResult bindingResult,@RequestParam("file") MultipartFile file,Principal principal) {
			if(bindingResult.hasErrors()) {
				return "Board_create";
			} 
			 SiteUser siteUser = this.userService.getUser(principal.getName());
			this.boardService.create(boardForm.getSubject(), boardForm.getContent(), file,siteUser);
			return "redirect:/board/list";
		}
		
		@GetMapping("download/{id}")
		public ResponseEntity<Resource> downloadFile(@PathVariable Integer id) throws IOException {
			Resource resource = boardService.getFile(id);
	        HttpHeaders headers = new HttpHeaders();
	        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"");
	        return ResponseEntity.ok().headers(headers).body(resource);
		}
		
		@PreAuthorize("isAuthenticated()")
		@GetMapping("/modify/{id}")
	    public String boardModify(BoardForm boardForm, @PathVariable("id") Integer id, Principal principal) {
			Board board = this.boardService.getBoard(id);
			if(!board.getAuthor().getUsername().equals(principal.getName())) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
			} 
			boardForm.setSubject(board.getSubject());
			boardForm.setContent(board.getContent());
				return "Board_create";
	    }
		
		 	@PreAuthorize("isAuthenticated()")
		    @PostMapping("/modify/{id}")
		    public String questionModify(@Valid BoardForm boardForm, BindingResult bindingResult, 
		            Principal principal, @PathVariable("id") Integer id) {
		        if (bindingResult.hasErrors()) {
		            return "Board_create";
		        }
		        Board board = this.boardService.getBoard(id);
		        if (!board.getAuthor().getUsername().equals(principal.getName())) {
		            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
		        }
		        this.boardService.modify(board, boardForm.getSubject(), boardForm.getContent());
		        return String.format("redirect:/board/detail/%s", id);
		    }
		
		 	 @PreAuthorize("isAuthenticated()")
		     @GetMapping("/delete/{id}")
		     public String questionDelete(Principal principal, @PathVariable("id") Integer id) {
		         Board question = this.boardService.getBoard(id);
		         if (!question.getAuthor().getUsername().equals(principal.getName())) {
		             throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
		         }
		         this.boardService.delete(question);
		         return "redirect:/";
		     }
		
		
}

