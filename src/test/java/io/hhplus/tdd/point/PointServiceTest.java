package io.hhplus.tdd.point;

import io.hhplus.tdd.exception.PointException;
import io.hhplus.tdd.repository.PointHistoryRepository;
import io.hhplus.tdd.repository.UserPointRepository;
import io.hhplus.tdd.serviceDTO.PointDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PointServiceTest {
    @InjectMocks
    private PointService pointService;

    // Mock 객체 생성
    @Mock
    private UserPointRepository userPointRepository;
    @Mock
    private PointHistoryRepository pointHistoryRepository;

    @BeforeEach
    public void setUp() {

    }

    //
    @Test
    void getPoint() throws InterruptedException, PointException {
        PointDTO pointDTO = PointDTO.builder().id(1L).amount(1000L).build();
        when(userPointRepository.selectById(any(PointDTO.class))).thenReturn(pointDTO);

        PointDTO result = PointDTO.builder().build();

        result = pointService.getPoint(pointDTO);

        /* 값 확인 */
        assertThat(result.getId()).isEqualTo(pointDTO.getId());
        assertThat(result.getAmount()).isEqualTo(pointDTO.getAmount());
    }

    @Test
    void saveUserPointTest() throws InterruptedException, PointException {
        PointDTO pointDTO = PointDTO.builder().id(1L).amount(1000L).build();
        when(userPointRepository.insertOrUpdate(any(PointDTO.class))).thenReturn(pointDTO);

        pointService.saveUserPoint(pointDTO);
    }

    @Test
    @DisplayName("id가 없을 때 오류")
    void saveUserPointTest1() throws InterruptedException, PointException {
        PointDTO pointDTO = PointDTO.builder().amount(1000L).build();
        lenient().when(userPointRepository.insertOrUpdate(any(PointDTO.class))).thenReturn(pointDTO);

        assertThrows(PointException.class, () -> pointService.saveUserPoint(pointDTO));
    }

    @Test
    @DisplayName("amount가 없을 때 오류")
    void saveUserPointTest2() throws InterruptedException, PointException {
        PointDTO pointDTO = PointDTO.builder().id(1L).build();
        lenient().when(userPointRepository.insertOrUpdate(any(PointDTO.class))).thenReturn(pointDTO);

        assertThrows(PointException.class, () -> pointService.saveUserPoint(pointDTO));
    }

    @Test
    @DisplayName("저장하려는 포인트가 0일때 오류")
    void saveUserPointTest3() throws InterruptedException, PointException {
        PointDTO pointDTO = PointDTO.builder().id(1L).amount(0L).build();
        lenient().when(userPointRepository.insertOrUpdate(any(PointDTO.class))).thenReturn(pointDTO);

        assertThrows(PointException.class, () -> pointService.saveUserPoint(pointDTO));
    }

    @Test
    @DisplayName("저장하려는 포인트가 0보다 작을 때 오류")
    void saveUserPointTest4() throws InterruptedException, PointException {
        PointDTO pointDTO = PointDTO.builder().id(1L).amount(-1000L).build();
        lenient().when(userPointRepository.insertOrUpdate(any(PointDTO.class))).thenReturn(pointDTO);

        assertThrows(PointException.class, () -> pointService.saveUserPoint(pointDTO));
    }

}