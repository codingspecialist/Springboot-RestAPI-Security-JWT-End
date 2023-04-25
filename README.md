# 스프링부트 CSR RestAPI With Security, JWT, JPA 총정리

## 1. QLRM
DTO로 DB에서 조회해서 받아야 한다면, QLRM 라이브러리를 사용하고, 직접 @Repository를 생성하자.
```txt
    Query query = em.createNativeQuery(sql);
    JpaResultMapper result = new JpaResultMapper();
    AllOutDTO dto = result.uniqueResult(query, AllOutDTO.class);
```

## 2. 전략
- OSIV = false
- 모든 것들은 Lazy 전략
- 서비스단에서 응답 DTO 생성
- OneToMany 사용안하기

## 3. Mock 테스트 어노테이션 정리
```txt
Mock: 실제 객체를 추상화된 가짜 객체로 만들어 테스트에 사용합니다. Mockito 환경에 주입합니다.
InjectMocks: Mock 객체들을 사용하여 진짜 객체를 생성하고, 해당 객체를 테스트 대상 코드에 주입합니다.
MockBean: 스프링의 IoC 컨테이너에 Mock 객체를 주입합니다.
Spy: 실제 객체를 만들어 Mockito 환경에 주입하고, 해당 객체의 일부 메서드만 가짜 구현으로 대체하여 테스트합니다.
SpyBean: 스프링의 IoC 컨테이너에 Spy 객체를 주입합니다.
Mockito와 Spring의 차이점을 요약하자면, Mockito는 단위 테스트를 위한 라이브러리이며, 
Spring은 애플리케이션 개발을 위한 프레임워크입니다. 
Mockito는 단위 테스트에서 사용할 가짜 객체를 생성하고, 주입하는 기능을 제공하고, 
Spring은 IoC 컨테이너를 통해 애플리케이션 개발에 필요한 객체들을 생성하고 관리합니다.
```

## 4. API 문서 만들기
https://github.com/codingspecialist/Springboot-Gradle-RestDoc

## 5. 문서 보는법
http://localhost:8080/docs/api-docs.html