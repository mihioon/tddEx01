package io.hhplus.tdd.database;

import io.hhplus.tdd.point.UserPoint;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 해당 Table 클래스는 변경하지 않고 공개된 API 만을 사용해 데이터를 제어합니다.
 */
@Component
public class UserPointTable {
    private Map<Long, UserPoint> table = new HashMap<>();

    public UserPoint selectById(Long id) throws InterruptedException {
        //Thread.sleep(Long.parseLong(String.valueOf(Math.random())) * 200L);
        Thread.sleep((long) (Math.random() * 200L));

        UserPoint userPoint = table.get(id);

        if (userPoint == null) {
            //  1970년 1월 1일 00:00:00 UTC부터 현재까지의 시간을 밀리초(milliseconds) 단위로 반환하는 Java 메서드
            //  long 타입으로 반환
            return new UserPoint(id, 0L, System.currentTimeMillis());
        }
        return userPoint;
    }

    public UserPoint insertOrUpdate(Long id, Long amount) throws InterruptedException {
        //Thread.sleep(Long.parseLong(String.valueOf(Math.random())) * 300L);
        Thread.sleep((long) (Math.random() * 300L));

        UserPoint userPoint = new UserPoint(id, amount, System.currentTimeMillis());
        table.put(id, userPoint);

        return userPoint;
    }
}
