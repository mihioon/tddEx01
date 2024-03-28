package io.hhplus.tdd.repository;

import io.hhplus.tdd.database.UserPointTable;
import io.hhplus.tdd.point.UserPoint;
import io.hhplus.tdd.serviceDTO.PointDTO;

public class UserPointRepository {
    private final UserPointTable userPointTable;

    public UserPointRepository(UserPointTable userPointTable) {
        this.userPointTable = userPointTable;
    }

    public PointDTO selectById(PointDTO param) throws InterruptedException {
        PointDTO result = null;
        UserPoint userPoint = userPointTable.selectById(param.getId());
        return result;
    }

    public PointDTO insertOrUpdate(PointDTO param) throws InterruptedException {
        PointDTO result = null;
        UserPoint userPoint = userPointTable.selectById(param.getId());
        return result;
    }
}
