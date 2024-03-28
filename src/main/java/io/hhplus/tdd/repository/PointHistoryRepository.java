package io.hhplus.tdd.repository;

import io.hhplus.tdd.database.PointHistoryTable;
import io.hhplus.tdd.database.UserPointTable;
import io.hhplus.tdd.point.PointHistory;
import io.hhplus.tdd.point.UserPoint;
import io.hhplus.tdd.serviceDTO.PointDTO;

import java.util.List;
import java.util.stream.Collectors;

public class PointHistoryRepository {
    private final PointHistoryTable pointHistoryTable;

    public PointHistoryRepository(PointHistoryTable pointHistoryTable) {
        this.pointHistoryTable = pointHistoryTable;
    }

    public void insert(PointDTO param) throws InterruptedException {
        pointHistoryTable.insert(param.getId(), param.getAmount(), param.getTransactionType(), System.currentTimeMillis());
    }

    public List<PointDTO> selectAllByUserId(PointDTO param) throws InterruptedException {
        List<PointHistory> pointHistory  = pointHistoryTable.selectAllByUserId(param.getId());

        List<PointDTO> result = pointHistory.stream()
                                    .map(entity -> {
                                        PointDTO dto = new PointDTO();
                                        dto.setId(entity.id());
                                        dto.setAmount(entity.amount());
                                        dto.setTimeMillis(entity.timeMillis());
                                        return dto;
                                    }).collect(Collectors.toList());
        return result;
    }
}
