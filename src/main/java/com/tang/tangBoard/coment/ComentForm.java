package com.tang.tangBoard.coment;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ComentForm {
	   @NotEmpty(message = "내용은 필수항목입니다.")
	    private String content;
}
