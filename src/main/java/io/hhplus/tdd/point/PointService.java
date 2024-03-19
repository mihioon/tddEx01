package io.hhplus.tdd.point;

import io.hhplus.tdd.database.PointHistoryTable;
import io.hhplus.tdd.database.UserPointTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class PointService {

    @Autowired
    private UserPointTable userPointTable;
    @Autowired
    private PointHistoryTable pointHistoryTable;

    // 포인트 조회
    public UserPoint getPoint(Long id) {
        UserPoint result = new UserPoint(0L, 0L, 0L);
        try {
            result = userPointTable.selectById(id);
        } catch (InterruptedException e){
            System.out.println(e);
        }
        return result;
    }

    // 포인트 충전/이용내역 조회
    public List<PointHistory> getPointHistory(Long id) {
        List<PointHistory> result = Collections.emptyList();
        if( id == null ){ return result; }
        result = pointHistoryTable.selectAllByUserId(id);
        return result;
    }

    // 포인트 내역 저장
    public PointHistory savePointHistory(Long id, Long amount, TransactionType type){
        PointHistory result = new PointHistory(0L, 0L, null, 0L,0L);
        if( id == null || amount == null || amount <= 0 || type == null ){ return result; }
        if( amount <= 0 ){ return result; }

        try{
            result = pointHistoryTable.insert(id, amount, type, System.currentTimeMillis());
        } catch (InterruptedException e) {
            System.out.println(e);
        }
        return result;
    }

    // 포인트 저장
    public UserPoint saveUserPoint(Long id, Long point){
        UserPoint result = new UserPoint(0L, 0L, 0L);
        if( id == null || point == null || point == 0L){ return result; }
        try{
            result = userPointTable.insertOrUpdate(id, point);
        } catch (InterruptedException e) {
            System.out.println(e);
        }
        return result;
    }

    // 포인트 충전
    public UserPoint addPoint(Long id, Long amount){
        savePointHistory(id, amount, TransactionType.CHARGE);
        Long point = calPointFromHistory(id);
        UserPoint result = saveUserPoint(id, point);
        return result;
    }

    // 포인트 사용
    public UserPoint usePoint(Long id, Long amount){
        savePointHistory(id, amount, TransactionType.USE);
        UserPoint result = new UserPoint(0L, 0L, 0L);
        Long point = calPointFromHistory(id);
        if( point < amount ){ return result; }
        result = saveUserPoint(id, point);
        return result;
    }

    // 금액 -> 포인트 계산
    public Long calAmount(Long amount) {
        if (amount == null){
            return 0L;
        }
        return amount * 10L / 100L;
    }

    // 포인트 내역 계산
    public Long calPointFromHistory(Long id){
        Long result = 0L;
        if( id == null ){ return result; }

        List<PointHistory> pointList = getPointHistory(id);

        if ( !pointList.isEmpty() ){
            for( int i=0; i < pointList.size(); i++){
                TransactionType type = pointList.get(i).type();
                Long point = pointList.get(i).amount();
                if( type == TransactionType.CHARGE ){
                    result = result + point;
                } else if( type == TransactionType.USE ) {
                    result = result - point;
                }
            }
        }
        return result;
    }
}
