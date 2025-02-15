package org.ktb.ktbbedevassignment.infrastructure;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.ktb.ktbbedevassignment.util.TestDataHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import static org.assertj.core.api.Assertions.assertThat;
import static org.ktb.ktbbedevassignment.infrastructure.CompanyTestFixture.TEST_COMPANY_CODE;
import static org.ktb.ktbbedevassignment.infrastructure.CompanyTestFixture.TEST_COMPANY_NAME;
import static org.ktb.ktbbedevassignment.infrastructure.CompanyTestFixture.TEST_NOT_EXIST_COMPANY_CODE;

@JdbcTest
@Import({CompanyRepository.class, TestDataHelper.class})
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CompanyRepositoryTest {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private TestDataHelper testDataHelper;

    @BeforeEach
    void setup() {
        testDataHelper.clearAllTables();
        testDataHelper.insertCompany(TEST_COMPANY_CODE, TEST_COMPANY_NAME);
    }

    @Nested
    @DisplayName("기업 코드로 기업 존재 여부를 조회할 경우")
    class Describe_existsByCompanyCode {
        @Test
        @DisplayName("해당 기업 코드가 존재한다면 true 를 반환한다.")
        void shouldReturnTrueIfCompanyExists() {
            // given
            String companyCode = TEST_COMPANY_CODE;

            // when
            boolean result = companyRepository.existsByCompanyCode(companyCode);

            // then
            assertThat(result).isTrue();
        }

        @Test
        @DisplayName("해당 기업 코드가 존재하지 않는다면 false 를 반환한다.")
        void shouldReturnFalseIfCompanyDoesNotExist() {
            // given
            String companyCode = TEST_NOT_EXIST_COMPANY_CODE;

            // when
            boolean result = companyRepository.existsByCompanyCode(companyCode);

            // then
            assertThat(result).isFalse();
        }
    }
}
