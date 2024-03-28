package io.hhplus.tdd.point;

import io.hhplus.tdd.exception.PointException;
import io.hhplus.tdd.repository.PointHistoryRepository;
import io.hhplus.tdd.repository.UserPointRepository;
import io.hhplus.tdd.serviceDTO.PointDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class PointService {

    @Autowired
    private UserPointRepository userPointRepository;
    @Autowired
    private PointHistoryRepository pointHistoryRepository;

    final private ConcurrentHashMap<Long, Lock> lockMap = new ConcurrentHashMap<>();

    // 포인트 조회
    public PointDTO getPoint(PointDTO param) throws PointException {
        // Long id
        PointDTO result = null;
        try {
            result = userPointRepository.selectById(param);
        }catch (InterruptedException e) {
            throw new PointException("");
        }
        return result;
    }

    // User 포인트 저장
    public void saveUserPoint(PointDTO param) throws PointException {
        PointDTO result = null;
        if( param.getId() == null || param.getAmount() == null ){
            throw new PointException("없는 값이 존재");
        }
        if( param.getAmount() <= 0 ){
            throw new PointException("값이 0보다 작음");
        }
        try{
            result = userPointRepository.insertOrUpdate(param);
        } catch (InterruptedException e) {
            throw new PointException("");
        }
    }

    // 포인트 충전/이용내역 조회
    public List<PointDTO> getPointHistory(PointDTO param) throws PointException, InterruptedException {
        if( param.getId() == null ){ throw new PointException("id값이 없습니다."); }
        List<PointDTO> result = pointHistoryRepository.selectAllByUserId(param);
        return result;
    }

    // 포인트 history 저장
    public void savePointHistory(PointDTO param) throws PointException {
        if( param.getId() == null || param.getAmount() == null || param.getTransactionType() == null ){
            throw new PointException("없는 값이 존재");
        }
        if( param.getAmount() <= 0 ){
            throw new PointException("값이 0보다 작음");
        }
        try{
            pointHistoryRepository.insert(param);
        } catch (InterruptedException e) {
            throw new PointException("");
        }
    }

    // 포인트 적립 또는 사용
    public void addOrUsePoint(PointDTO param) throws PointException, InterruptedException {
        Lock lock = lockMap.computeIfAbsent(param.getId(), k -> new ReentrantLock());

        try {
            lock.lock();

            Long nowPoint = getPoint(param).getAmount();
            param.setNowPoint(nowPoint);
            pointValidation(param);

            pointHistoryRepository.insert(param); // PointHistory 저장

            // User amount 계산
            Long amount = param.getAmount();
            if(param.getTransactionType() == TransactionType.USE){
                amount = nowPoint - amount;
            }else if(param.getTransactionType() == TransactionType.CHARGE){
                amount = nowPoint + amount;
            }
            param.setAmount(amount);

            saveUserPoint(param); // UserPoint 저장
        } finally {
            lock.unlock();
        }
    }


    // 유효성 체크 : 현재포인트 값이 사용할 포인트 값보다 작으면 예외
    public void pointValidation(PointDTO param) throws PointException {
        if(param.getId() == null|| param.getAmount() == null||param.getTransactionType() == null){
            throw new PointException("없는 값이 존재");
        }
        if( param.getTransactionType() == TransactionType.USE && param.getNowPoint() < param.getAmount() ){
            throw new PointException("사용 가능 포인트 초과");
        }
    }
}
