package shop.mtcoding.restend.dto;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import shop.mtcoding.restend.dto.user.UserResponse;

@Getter
public class ResponseDTO<T> {
    private Integer status; // 에러시에 의미 있음.
    private String msg; // 에러시에 의미 있음. ex) badRequest
    private T data; // 에러시에는 구체적인 에러 내용 ex) username이 입력되지 않았습니다

    public ResponseDTO(){
        this.status = HttpStatus.OK.value();
        this.msg = "성공";
    }

    public ResponseDTO(T data){
        this.status = HttpStatus.OK.value();
        this.msg = "성공";
        this.data = data; // 응답할 데이터 바디
    }

    public ResponseDTO(HttpStatus httpStatus, String msg, T data){
        this.status = httpStatus.value();
        this.msg = msg; // 에러 제목
        this.data = data; // 에러 내용
    }
}
