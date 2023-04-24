# 스프링부트 CSR RestAPI With Security, JWT, JPA 총정리

## 1. QLRM
DTO로 DB에서 조회해서 받아야 한다면, QLRM 라이브러리를 사용하고, 직접 @Repository를 생성하자.
```java
    Query query = em.createNativeQuery(sql);
    JpaResultMapper result = new JpaResultMapper();
    AllOutDTO dto = result.uniqueResult(query, AllOutDTO.class);
```

## 2. 전략
- OSIV = false
- 모든 것들은 Lazy 전략
- 서비스단에서 응답 DTO 생성
- OneToMany 사용안하기