package io.hhplus.tdd;

import io.hhplus.tdd.database.PointHistoryTable;
import io.hhplus.tdd.point.PointHistory;
import io.hhplus.tdd.point.PointService;
import io.hhplus.tdd.point.TransactionType;
import io.hhplus.tdd.point.UserPoint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TddApplicationTests {

	@Autowired
	private PointService pointService;

	@Autowired
	private PointHistoryTable pointHistoryTable;

	private final Long id = 10L;
	private final Long amount = 1000L;
	private final Long point = 100L;

	@BeforeEach
	void setUp() {
	}


	@Test
	void contextLoads() {
		// 테스트 케이스의 작성 및 작성 이유를 주석으로 작성
		// 프로젝트 내의 주석을 참고하여 필요한 기능을 작성
	}

	@Test
	void getPointTest() {
		// 1. 아이디값을 조회할 메서드를 호출할 수 있을 것 > 구현
		// 2. 조회한 값을 확인하기까지 null예외가 발생하지 않을 것 > id값이 존재하는 한 어떤 경우든
		// 3. 조회된 값 검증

		// given
		// Long id = 10L;
		// when
		// id값으로 포인트를 조회하기 위한 메서드 호출
		UserPoint result =  pointService.getPoint(id);
		// then
		/* null */
		checkNotNull(result);
		/* 값 확인 */
		assertThat(result.id()).isEqualTo(id);
	}

	@Test
	void saveUserPointTest(){
		// when
		// 매개변수 값이 모두 있을 경우 모든 값 null X
		UserPoint result = pointService.saveUserPoint(id, point);
		// then
		checkNotNull(result);
		assertThat(result.id()).isEqualTo(id); // 그리고 반환되는 id가 같아야 함
		assertThat(result.point()).isEqualTo(point);

		// id == null 경우
		result = pointService.saveUserPoint(null, point);
		checkDefault(result);

		// point == null 경우
		result = pointService.saveUserPoint(id, null);
		checkDefault(result);

		// point == 0 경우
		result = pointService.saveUserPoint(id, 0L);
		checkDefault(result);
	}

	@Test
	void getPointHistoryTest() {
		// 1. 포인트내역값을 조회할 메서드를 호출할 수 있을 것 > 구현
		// 2. 조회내역 결과가 null이 아닌 빈 리스트일 것 > id값 상관없이
		// given
		// when
		List<PointHistory> result = pointService.getPointHistory(id);
		// then
		assertNotNull(result);

		result = pointService.getPointHistory(null);
		assertNotNull(result);
	}

	@Test
	void calAmountTest(){
		// 1. 금액이 주어지면 포인트를 계산할 메서드 구현
		// 2. 반환된 결과가 옳은 결과값일 것 -> 포인트 적립비율 10%로 가정
		// 3. 매개변수 amount 가 null인 경우 오류 X & 0L반환 확인
		// given
		// when
		Long result = pointService.calAmount(amount);
		Long result2 = pointService.calAmount(2000L);
		Long result3 = pointService.calAmount(null);
		// then
		assertEquals(result, 100L);
		assertEquals(result2, 200L);
		assertEquals(result3, 0L);
	}

	/* savePointHistory */
	@Test
	void savePointHistoryTest(){
		// id가 존재한다면 반드시 값 반환
		// when
		PointHistory result = pointService.savePointHistory(id, amount, TransactionType.CHARGE);
		// then
		checkNotNull(result);

		// id 없는 경우 기본객체 반환
		result = pointService.savePointHistory(null, amount, TransactionType.CHARGE);
		checkDefault(result);

		// amount 없는 경우 기본객체 반환
		result = pointService.savePointHistory(id, null, TransactionType.CHARGE);
		checkDefault(result);

		// amount 0이거나 보다 작은 경우 기본객체 반환
		result = pointService.savePointHistory(id, 0L, TransactionType.CHARGE);
		checkDefault(result);
		result = pointService.savePointHistory(id, -1000L, TransactionType.CHARGE);
		checkDefault(result);

		// type 없는 경우 기본객체 반환
		result = pointService.savePointHistory(id, amount, null);
		checkDefault(result);
	}

	@Test
	void calPointFromHistoryTest(){
		// id null
		Long result = pointService.calPointFromHistory(null);
		assertThat(result).isEqualTo(0L);
		result = pointService.calPointFromHistory(0L);
		assertThat(result).isEqualTo(0L);

		// 값이 있는 경우
		// given
		pointService.savePointHistory(id, 1000L, TransactionType.CHARGE);
		pointService.savePointHistory(id, 2000L, TransactionType.CHARGE);
		pointService.savePointHistory(id, 500L, TransactionType.USE);
		pointService.savePointHistory(20L, 500L, TransactionType.CHARGE);
		pointService.savePointHistory(20L, 500L, TransactionType.USE);
		// when
		result = pointService.calPointFromHistory(id);
		Long result2 = pointService.calPointFromHistory(20L);
		// then
		assertThat(result).isEqualTo(2500L);
		assertThat(result2).isEqualTo(0L);
	}

	/* addPoint */
	@Test
	void addPointTest(){
		// given
		// when
		UserPoint result = pointService.addOrUsePoint(id, amount, TransactionType.CHARGE);
		// then
		/* null */
		checkNotNull(result);
		/* 값 확인 */
		assertThat(result.id()).isEqualTo(id);
		assertThat(result.point()).isEqualTo(1000L);

		// addPoint 2회
		result = pointService.addOrUsePoint(id, 2000L, TransactionType.CHARGE);
		assertThat(result.point()).isEqualTo(3000L);
	}

	@Test
	void usePointTest(){
		// given
		UserPoint result = pointService.addOrUsePoint(id, 2000L, TransactionType.CHARGE);
		// when
		result = pointService.addOrUsePoint(id, amount, TransactionType.USE);
		// then
		/* null */
		checkNotNull(result);
		/* 값 확인 */
		assertThat(result.id()).isEqualTo(id);
		assertThat(result.point()).isEqualTo(1000L);

		/* 잔고가 부족할 경우, 포인트 사용은 실패 */
		result = pointService.addOrUsePoint(id, 2000L,  TransactionType.USE);
		checkNotNull(result);
		System.out.println(result);
		assertThat(result.point()).isEqualTo(0L);
	}

	/**************************
	 * 공통 함수
	 **************************/
	void checkNotNull(UserPoint result){
		assertNotNull(result);
		assertThat(result.id()).isNotNull();
		assertThat(result.point()).isNotNull();
		assertThat(result.updateMillis()).isNotNull();
	}

	void checkNotNull(PointHistory result){
		assertNotNull(result);
		assertThat(result.id()).isNotNull();
		assertThat(result.type()).isNotNull();
		assertThat(result.amount()).isNotNull();
		assertThat(result.timeMillis()).isNotNull();
	}

	void checkDefault(UserPoint result){
		assertNotNull(result);
		// 기본객체랑 같은지 확인
		assertThat(result.id()).isEqualTo(0L);
		assertThat(result.point()).isEqualTo(0L);
		assertThat(result.updateMillis()).isEqualTo(0L);
	}

	void checkDefault(PointHistory result){
		assertNotNull(result);
		// 기본객체랑 같은지 확인
		assertThat(result.id()).isEqualTo(0L);
		assertThat(result.userId()).isEqualTo(0L);
		assertThat(result.type()).isEqualTo(null);
		assertThat(result.amount()).isEqualTo(0L);
		assertThat(result.timeMillis()).isEqualTo(0L);
	}
}
