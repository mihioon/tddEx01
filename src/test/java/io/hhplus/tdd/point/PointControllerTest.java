package io.hhplus.tdd.point;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest
class PointControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mvc;

    // 동시에 여러건의 요청이 들어올 경우 처리해서 값이 같아야 함
    // add or use 두 요청일때 모두 같은 메서드 우선 호출한다음 분리

    @BeforeEach
    void setUp() {
        this.mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void addOrUsePointTest() throws Exception {
        int requestNumber = 50;
        ExecutorService executorService = Executors.newFixedThreadPool(requestNumber);
        CountDownLatch latch = new CountDownLatch(requestNumber);
        // 초기 충전 및 값 검증용 변수
        Long initialAmount = 5000L;
        mvc.perform(patch("/point/1/charge")
                        .content(String.valueOf(initialAmount))
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk());

        List<Future<String>> futures = new ArrayList<>();

        Long chargeAmount = 1000L; // 충전량
        Long useAmount = 3000L; // 사용량

        // 병렬로 api 호출하기
        for (int i = 0; i < requestNumber; i++) {
            executorService.submit(() -> {
                try {
                    if (ThreadLocalRandom.current().nextBoolean()) {
                        // 충전
                        mvc.perform(patch("/point/1/charge")
                                        .content(String.valueOf(chargeAmount))
                                        .contentType(MediaType.APPLICATION_JSON))
                                        .andExpect(status().isOk());
                    } else {
                        mvc.perform(patch("/point/1/use")
                                        .content(String.valueOf(useAmount))
                                        .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk());
                    }
                } catch (Exception e) {
                    return "Exception occurred in thread " + Thread.currentThread().getName() + ": " + e.getMessage();
                } finally {
                    latch.countDown(); // 각 스레드가 종료될 때마다 countDown() 호출
                }
                return null;
            });
        }

        latch.await(); // 모든 스레드 시작 대기
        executorService.shutdown(); // 스레드 풀 종료

        //모르겠습니다...
    }
}