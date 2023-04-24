package shop.mtcodng.restend.model.account;

import lombok.*;
import shop.mtcodng.restend.core.exception.Exception400;
import shop.mtcodng.restend.core.exception.Exception401;
import shop.mtcodng.restend.core.exception.Exception403;
import shop.mtcodng.restend.model.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "account_tb")
@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Column(unique = true, nullable = false, length = 4)
    private Integer number; // 계좌번호
    @Column(nullable = false, length = 4)
    private Integer password; // 계좌비번
    @Column(nullable = false)
    private Long balance; // 잔액 (기본값 1000원)

    @Column(nullable = false)
    private Boolean status; // true, false

    @Column(nullable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public void checkOwner(Long userId) {
        if (user.getId().longValue() != userId.longValue()) {
            throw new Exception403("계좌 소유자가 아닙니다");
        }
    }

    public void deposit(Long amount) {
        balance = balance + amount;
    }

    public void checkSamePassword(Integer password) {
        if (!this.password.equals(password)) {
            throw new Exception401("계좌 비밀번호 검증에 실패했습니다");
        }
    }

    public void checkBalance(Long amount) {
        if (this.balance < amount) {
            throw new Exception400("amount","계좌 잔액이 부족합니다");
        }
    }

    public void withdraw(Long amount) {
        balance = balance - amount;
    }
}