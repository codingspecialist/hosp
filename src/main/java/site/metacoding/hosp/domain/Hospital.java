package site.metacoding.hosp.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Entity
public class Hospital {

    // 툴 왜이래...
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // auto_increment

    private String addr; // 병원 주소
    private Integer mgtStaDd; // 운영시작일자
    private String pcrPsblYn; // PCR 검사 여부
    private String ratPsblYn; // 호흡기 전담 클리닉 여부
    private Integer recuClCd; // 요양종별코드 (종합병원11, 병원21, 의원31)
    private String sgguCdNm; // 시군구명 : 김해시
    private String sidoCdNm; // 시도명 : 경상남도
    private Double XPosWgs84; // x좌표 (위도)
    private Double YPosWgs84; // y좌표 (경도)
    private String yadmNm; // 요양기관명
    private String ykihoEnc; // 암호화된 요양 기호
    private Integer xPos; // x좌표 (좌표)
    private Integer yPos; // y좌표 (좌표)
}
