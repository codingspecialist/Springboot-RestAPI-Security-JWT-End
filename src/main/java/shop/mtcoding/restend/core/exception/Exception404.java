package shop.mtcoding.restend.core.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import shop.mtcoding.restend.dto.ResponseDTO;


// 권한 없음
@Getter
public class Exception404 extends RuntimeException {
    public Exception404(String message) {
        super(message);
    }

    public ResponseDTO<?> body(){
        ResponseDTO<String> responseDto = new ResponseDTO<>();
        responseDto.fail(HttpStatus.NOT_FOUND, "notFound", getMessage());
        return responseDto;
    }

    public HttpStatus status(){
        return HttpStatus.NOT_FOUND;
    }
}