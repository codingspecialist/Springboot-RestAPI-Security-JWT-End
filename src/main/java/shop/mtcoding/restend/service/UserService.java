package shop.mtcoding.restend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.mtcoding.restend.core.annotation.MyLog;
import shop.mtcoding.restend.core.auth.jwt.MyJwtProvider;
import shop.mtcoding.restend.core.exception.Exception400;
import shop.mtcoding.restend.core.exception.Exception401;
import shop.mtcoding.restend.core.exception.Exception500;
import shop.mtcoding.restend.dto.user.UserRequest;
import shop.mtcoding.restend.dto.user.UserResponse;
import shop.mtcoding.restend.model.user.User;
import shop.mtcoding.restend.model.user.UserRepository;

import java.util.Optional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @MyLog
    @Transactional
    public UserResponse.JoinOutDTO 회원가입(UserRequest.JoinInDTO joinInDTO){
        try {
            String encPassword = passwordEncoder.encode(joinInDTO.getPassword()); // 60Byte
            joinInDTO.setPassword(encPassword);
            User userPS = userRepository.save(joinInDTO.toEntity());
            return new UserResponse.JoinOutDTO(userPS);
        }catch (Exception e){
            throw new Exception500("회원가입 실패 : "+e.getMessage());
        }
    }

    @MyLog
    public String 로그인(UserRequest.LoginInDTO loginInDTO) {
        Optional<User> userOP = userRepository.findByUsername(loginInDTO.getUsername());
        if(userOP.isPresent()){
            User userPS = userOP.get();
            if(passwordEncoder.matches(loginInDTO.getPassword(), userPS.getPassword())){
                String jwt = MyJwtProvider.create(userPS);
                return jwt;
            }
            throw new Exception401("패스워드 틀렸어");
        }else{
            throw new Exception401("유저네임 없어");
        }
    }

    public UserResponse.DetailOutDTO 유저상세보기(Long id) {
        User userPS = userRepository.findById(id).orElseThrow(
                ()-> new Exception400("id", "해당 유저를 찾을 수 없습니다")

        );
        return new UserResponse.DetailOutDTO(userPS);
    }
}
