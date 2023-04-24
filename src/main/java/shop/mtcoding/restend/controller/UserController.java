package shop.mtcoding.restend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import shop.mtcoding.restend.core.auth.jwt.MyJwtProvider;
import shop.mtcoding.restend.core.auth.session.MyUserDetails;
import shop.mtcoding.restend.core.exception.Exception403;
import shop.mtcoding.restend.dto.ResponseDTO;
import shop.mtcoding.restend.dto.user.UserRequest;
import shop.mtcoding.restend.dto.user.UserResponse;
import shop.mtcoding.restend.service.UserService;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody @Valid UserRequest.JoinInDTO joinInDTO, Errors errors) {
        UserResponse.JoinOutDTO joinOutDTO = userService.회원가입(joinInDTO);
        ResponseDTO<?> responseDTO = new ResponseDTO<>().data(joinOutDTO);
        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserRequest.LoginInDTO loginInDTO){
        String jwt = userService.로그인(loginInDTO);
        ResponseDTO<?> responseDTO = new ResponseDTO<>().data("로그인완료");
        return ResponseEntity.ok().header(MyJwtProvider.HEADER, jwt).body(responseDTO);
    }

    @GetMapping("/s/user/{id}")
    public ResponseEntity<?> detail(@PathVariable Long id, @AuthenticationPrincipal MyUserDetails myUserDetails){
        if(id.longValue() != myUserDetails.getUser().getId()){
            throw new Exception403("권한이 없습니다");
        }
        UserResponse.DetailOutDTO DetailOutDTO  = userService.유저상세보기(id);
        ResponseDTO<?> responseDTO = new ResponseDTO<>().data(DetailOutDTO);
        return ResponseEntity.ok(responseDTO);
    }
}
