package com.tang.tangBoard.coment;

import java.security.Principal;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import com.tang.tangBoard.board.Board;
import com.tang.tangBoard.board.BoardService;
import com.tang.tangBoard.user.SiteUser;
import com.tang.tangBoard.user.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequestMapping("/coment")
@RequiredArgsConstructor
@Controller
public class ComentController {

	private final BoardService boardService;
	private final ComentService comentService;
	private final UserService userService;
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/create/{id}")
	public String CreateComent(Model model,@PathVariable("id") Integer id, @Valid ComentForm comentForm, BindingResult bindingResult,Principal principal) {
        Board board = this.boardService.getBoard(id);
        SiteUser siteUser = this.userService.getUser(principal.getName());
        if (bindingResult.hasErrors()) {
            model.addAttribute("board",board);
            return "question_detail";
        }
        this.comentService.create(board, comentForm.getContent(), siteUser);
        return String.format("redirect:/board/detail/%s", id);
    }
	

	
}
