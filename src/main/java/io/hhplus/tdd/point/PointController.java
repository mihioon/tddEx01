package io.hhplus.tdd.point;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

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
    public UserPoint point(@PathVariable Long id) {
        UserPoint result = pointService.getPoint(id);
        // return new UserPoint(0L, 0L, 0L);
        return result;
    }

    /**
     * TODO - 특정 유저의 포인트 충전/이용 내역을 조회하는 기능을 작성해주세요.
     */
    @GetMapping("{id}/histories")
    public List<PointHistory> history(@PathVariable Long id) {
        List<PointHistory> result = pointService.getPointHistory(id);
        // return Collections.emptyList();
        return result;
    }

    /**
     * TODO - 특정 유저의 포인트를 충전하는 기능을 작성해주세요.
     */
    @PatchMapping("{id}/charge")
    public UserPoint charge(@PathVariable Long id, @RequestBody Long amount) {
        UserPoint result = pointService.addPoint(id, amount);
        //return new UserPoint(0L, 0L, 0L);
        return result;
    }

    /**
     * TODO - 특정 유저의 포인트를 사용하는 기능을 작성해주세요.
     */
    @PatchMapping("{id}/use")
    public UserPoint use(@PathVariable Long id, @RequestBody Long amount) {
        UserPoint result = pointService.usePoint(id, amount);
        //return new UserPoint(0L, 0L, 0L);
        return result;
    }
}
