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

class TddApplicationTests {

	@Autowired
	private PointService pointService;


	private final Long id = 10L;
	private final Long amount = 1000L;
	private final Long point = 100L;

	@BeforeEach
	void setUp() {
	}


//	@Test
//	void calAmountTest(){
//		// 1. 금액이 주어지면 포인트를 계산할 메서드 구현
//		// 2. 반환된 결과가 옳은 결과값일 것 -> 포인트 적립비율 10%로 가정
//		// 3. 매개변수 amount 가 null인 경우 오류 X & 0L반환 확인
//		// given
//		// when
//		Long result = pointService.calAmount(amount);
//		Long result2 = pointService.calAmount(2000L);
//		Long result3 = pointService.calAmount(null);
//		// then
//		assertEquals(result, 100L);
//		assertEquals(result2, 200L);
//		assertEquals(result3, 0L);
//	}
//
//	/* savePointHistory */
//	@Test
//	void savePointHistoryTest(){
//		// id가 존재한다면 반드시 값 반환
//		// when
//		PointHistory result = pointService.savePointHistory(id, amount, TransactionType.CHARGE);
//		// then
//		checkNotNull(result);
//
//		// id 없는 경우 기본객체 반환
//		result = pointService.savePointHistory(null, amount, TransactionType.CHARGE);
//		checkDefault(result);
//
//		// amount 없는 경우 기본객체 반환
//		result = pointService.savePointHistory(id, null, TransactionType.CHARGE);
//		checkDefault(result);
//
//		// amount 0이거나 보다 작은 경우 기본객체 반환
//		result = pointService.savePointHistory(id, 0L, TransactionType.CHARGE);
//		checkDefault(result);
//		result = pointService.savePointHistory(id, -1000L, TransactionType.CHARGE);
//		checkDefault(result);
//
//		// type 없는 경우 기본객체 반환
//		result = pointService.savePointHistory(id, amount, null);
//		checkDefault(result);
//	}
//
//	@Test
//	void calPointFromHistoryTest(){
//		// id null
//		Long result = pointService.calPointFromHistory(null);
//		assertThat(result).isEqualTo(0L);
//		result = pointService.calPointFromHistory(0L);
//		assertThat(result).isEqualTo(0L);
//
//		// 값이 있는 경우
//		// given
//		pointService.savePointHistory(id, 1000L, TransactionType.CHARGE);
//		pointService.savePointHistory(id, 2000L, TransactionType.CHARGE);
//		pointService.savePointHistory(id, 500L, TransactionType.USE);
//		pointService.savePointHistory(20L, 500L, TransactionType.CHARGE);
//		pointService.savePointHistory(20L, 500L, TransactionType.USE);
//		// when
//		result = pointService.calPointFromHistory(id);
//		Long result2 = pointService.calPointFromHistory(20L);
//		// then
//		assertThat(result).isEqualTo(2500L);
//		assertThat(result2).isEqualTo(0L);
//	}
//
//	/* addPoint */
//	@Test
//	void addPointTest(){
//		// given
//		// when
//		UserPoint result = pointService.addOrUsePoint(id, amount, TransactionType.CHARGE);
//		// then
//		/* null */
//		checkNotNull(result);
//		/* 값 확인 */
//		assertThat(result.id()).isEqualTo(id);
//		assertThat(result.point()).isEqualTo(1000L);
//
//		// addPoint 2회
//		result = pointService.addOrUsePoint(id, 2000L, TransactionType.CHARGE);
//		assertThat(result.point()).isEqualTo(3000L);
//	}
//
//	@Test
//	void usePointTest(){
//		// given
//		UserPoint result = pointService.addOrUsePoint(id, 2000L, TransactionType.CHARGE);
//		// when
//		result = pointService.addOrUsePoint(id, amount, TransactionType.USE);
//		// then
//		/* null */
//		checkNotNull(result);
//		/* 값 확인 */
//		assertThat(result.id()).isEqualTo(id);
//		assertThat(result.point()).isEqualTo(1000L);
//
//		/* 잔고가 부족할 경우, 포인트 사용은 실패 */
//		result = pointService.addOrUsePoint(id, 2000L,  TransactionType.USE);
//		checkNotNull(result);
//		System.out.println(result);
//		assertThat(result.point()).isEqualTo(0L);
//	}

}
