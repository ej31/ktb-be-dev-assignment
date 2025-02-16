package com.example.demo.repository;

import com.example.demo.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Spring Data JPA의 JpaRepository를 상속받아 Company 엔터티에 대한 데이터베이스 연산을 수행하는 인터페이스
@Repository
public interface CompanyRepository extends JpaRepository<Company, String> {
}