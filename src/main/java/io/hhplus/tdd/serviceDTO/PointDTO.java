package io.hhplus.tdd.serviceDTO;

import io.hhplus.tdd.point.TransactionType;
import lombok.*;

@Data
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PointDTO {
    private Long id;
    private Long amount;
    private Long nowPoint;
    private TransactionType transactionType;
    private Long timeMillis;
}
