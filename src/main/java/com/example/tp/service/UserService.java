package com.example.tp.service;

import com.example.tp.domain.entity.UserEntity;
import com.example.tp.domain.repository.UserRepository;
import com.example.tp.dto.UserDto;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {
    private UserRepository userRepository;

    private static final int BLOCK_PAGE_NUM_COUNT = 10; // 블럭에 존재하는 페이지 번호 수
    private static final int PAGE_POST_COUNT = 15; // 한 페이지에 존재하는 게시글 수


    @Transactional
    public void increaseUserTier(UserEntity user, int incrementAmount) {
        int currentTier = user.getTier();
        currentTier += incrementAmount;
        user.setTier(currentTier);
        userRepository.save(user);
    }

    //아이디 중복 검사
    @javax.transaction.Transactional
    public boolean isIdUnique(String id) {
        Optional<UserEntity> userEntityWrapper = userRepository.findById(id);
        return !userEntityWrapper.isPresent();
    }

    //닉네임 중복 검사
    @javax.transaction.Transactional
    public boolean isNicknameUnique(String nickname) {
        Optional<UserEntity> userEntityWrapper = userRepository.findByNickname(nickname);
        return !userEntityWrapper.isPresent();
    }

//    //이름검색
//    @javax.transaction.Transactional
//    public List<UserDto> searchUserName(String nameKeyword) {
//
//        List<UserEntity> userEntities = userRepository.findByNameContaining(nameKeyword);
//
//
//        List<UserDto> userDtoList = new ArrayList<>();
//
//        if (userEntities.isEmpty()) return userDtoList;
//
//
//        for (UserEntity userEntity : userEntities) {
//            userDtoList.add(this.convertEntityToDto(userEntity));
//        }
//
//        return userDtoList;
//    }

    private UserDto convertEntityToDto(UserEntity userEntity) {

        return UserDto.builder()
                .id(userEntity.getId())
                .name(userEntity.getName())
                .nickname(userEntity.getNickname())
                .phone(userEntity.getPhone())
                .tier(userEntity.getTier())
                .filePath(userEntity.getFilePath())
                .imgFullPath("https://" + S3Service.CLOUD_FRONT_DOMAIN_NAME + "/" + userEntity.getFilePath())

                .build();
    }

    public void savePost(UserDto userDto) {
        userRepository.save(userDto.toEntity());
    }

    public List<UserDto> getList() {
        List<UserEntity> userEntityList = userRepository.findAll();
        List<UserDto> userDtoList = new ArrayList<>();

        for (UserEntity userEntity : userEntityList) {
            userDtoList.add(convertEntityToDto(userEntity));
        }

        return userDtoList;
    }

    //비밀번호 암호화
    @Transactional
    public String joinUser(UserDto userDto) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        userDto.setPw(passwordEncoder.encode(userDto.getPw()));
        userDto.setState("0");
        userDto.setTier(0);

        return userRepository.save(userDto.toEntity()).getId();
    }

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        Optional<UserEntity> userEntityWrapper = userRepository.findById(id);

        if (!userEntityWrapper.isPresent()) { // 사용자가 없는 경우
            throw new UsernameNotFoundException(id);
        }

        UserEntity userEntity = userEntityWrapper.get();
        List<GrantedAuthority> authorities = new ArrayList<>();


        // 사용자의 state 값을 가져와서 권한을 설정
        if ("1".equals(userEntity.getState())) {
            authorities.add(new SimpleGrantedAuthority("manager"));
        } else {
            authorities.add(new SimpleGrantedAuthority("user"));
        }


        if (userEntityWrapper == null) {
            throw new UsernameNotFoundException(id);
        }

        return new MemberUser(userEntity.getId(), userEntity.getPw(), authorities, userEntity);
    }

}