package io.hhplus.tdd.point;

import io.hhplus.tdd.exception.PointException;
import io.hhplus.tdd.serviceDTO.PointDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Slf4j
@RequestMapping("/point")
@RestController
public class PointController {

    @Autowired
    private PointService pointService;

    /**
     * TODO - 특정 유저의 포인트를 조회하는 기능을 작성해주세요.
     */
    // @PathVariable : 웹 요청의 URL 경로에서 변수 값을 추출
    @GetMapping("{id}")
    public Map<String, Object> point(@PathVariable Long id) {
        Map<String, Object> result = null;
        PointDTO pointDTO = PointDTO.builder().id(id).build();
        try {
            PointDTO userPoint = pointService.getPoint(pointDTO);
        } catch (PointException e){

        }
        return result;
    }

    /**
     * TODO - 특정 유저의 포인트 충전/이용 내역을 조회하는 기능을 작성해주세요.
     */
    @GetMapping("{id}/histories")
    public Map<String, Object> history(@PathVariable Long id) {
        Map<String, Object> result = null;
        PointDTO pointDTO = PointDTO.builder().id(id).build();
        List<PointDTO> pointHistoryList = null;
        try {
            pointHistoryList = pointService.getPointHistory(pointDTO);
        } catch (PointException | InterruptedException e){

        }
        return result;
    }

    /**
     * TODO - 특정 유저의 포인트를 충전하는 기능을 작성해주세요.
     */
    @PatchMapping("{id}/charge")
    public Map<String, Object> charge(@PathVariable Long id, @RequestBody Long amount) {
        Map<String, Object> result = null;
        PointDTO pointDTO = PointDTO.builder().id(id).amount(amount).transactionType(TransactionType.CHARGE).build();
        try {
             pointService.addOrUsePoint(pointDTO);
        } catch (PointException | InterruptedException e){

        }
        return result;
    }

    /**
     * TODO - 특정 유저의 포인트를 사용하는 기능을 작성해주세요.
     */
    @PatchMapping("{id}/use")
    public Map<String, Object> use(@PathVariable Long id, @RequestBody Long amount) {
        Map<String, Object> result = null;
        PointDTO pointDTO = PointDTO.builder().id(id).amount(amount).transactionType(TransactionType.USE).build();
        try {
            pointService.addOrUsePoint(pointDTO);
        } catch (PointException | InterruptedException e){

        }
        return result;
    }
}
