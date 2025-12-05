package com.codeit.sb06deokhugamteam2.comment;

import com.codeit.sb06deokhugamteam2.comment.repository.CommentRepository;
//import com.codeit.sb06deokhugamteam2.review.entity.Review;
import com.codeit.sb06deokhugamteam2.review.adapter.out.entity.Review;
import com.codeit.sb06deokhugamteam2.review.domain.port.ReviewRepository;
import com.codeit.sb06deokhugamteam2.user.entity.User;
import com.codeit.sb06deokhugamteam2.user.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Transactional
public class CommentIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;


    @Test
    @DisplayName("댓글 생성 API 성공")
    void createComment_success() throws Exception {
        //given

        //사용자 생성 및 저장
        User user = User.builder()
                .email("test@mail.com")
                .nickname("testUser")
                .password("1234")
                .build();

        userRepository.saveAndFlush(user);

        //리뷰 생성 및 저장

        UUID reviewId = UUID.randomUUID();

        Review review = new Review()
                .id(reviewId)
                .user(user)
                .commentCount()
                .
                .

    }
}
