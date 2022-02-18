package site.metacoding.hosp.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

// public 붙여야 하구나
// 순서 틀렸구나! <T, ID>
public interface HospitalRepository extends JpaRepository<Hospital, Integer> {

    // name query 사용시에 어노테이션 필요
    @Query(value = "SELECT * FROM hospital WHERE sidoCdNm = :sidoCdNm AND sgguCdNm = :sgguCdNm AND pcrPsblYn = 'Y'", nativeQuery = true)
    public List<Hospital> mFindHospital(@Param("sidoCdNm") String sidoCdNm, @Param("sgguCdNm") String sgguCdNm);

    // String도 됐었낭? 됨!! 저장하면 안됨..데이터 다시 받아야함 저장은 하지말장 - order by 추가
    @Query(value = "SELECT distinct sidoCdNm FROM hospital order by sidoCdNm", nativeQuery = true)
    public List<String> mFindSidoCdNm();

    // 저장함 ㅠ 다시 배치 돌려야 할듯 // 대문자 안해서 오류났었네
    // 콘솔 색깔좀 나오게 해야 겠당
    // 대소문자좀 통일함. 해깔려서
    @Query(value = "SELECT distinct sgguCdNm FROM HOSPITAL WHERE sidoCdNm = :sidoCdNm order by sgguCdNm", nativeQuery = true)
    public List<String> mFindSggucdnm(@Param("sidoCdNm") String sidoCdNm);
}
