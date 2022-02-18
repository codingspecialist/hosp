package site.metacoding.hosp.batch;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import site.metacoding.hosp.domain.Hospital;
import site.metacoding.hosp.domain.HospitalRepository;

@Configuration
public class DBInitializer {

    // 스프링부트 시작시에 한번 실행됨
    @Bean
    public CommandLineRunner initDB(HospitalRepository hRepository) {

        return (args) -> {
            // System.out.println("나 1분 마다 실행됨");
            // 1. 담을 그릇 준비
            List<Hospital> hospitals = new ArrayList<>();

            // 2. api 한번 해출 해서 totalCount 확인
            RestTemplate rt = new RestTemplate();

            // 사이즈를 1로 했더니 item이 컬렉션이 아니라서 파싱이 안되서 2로 바꿈.
            int totalCount = 2;

            String totalCountUrl = "http://apis.data.go.kr/B551182/rprtHospService/getRprtHospService?serviceKey=wJmmW29e3AEUjwLioQR22CpmqS645ep4S8TSlqtSbEsxvnkZFoNe7YG1weEWQHYZ229eNLidnI2Yt5EZ3Stv7g==&pageNo=1&numOfRows="
                    + totalCount + "&_type=json";

            ResponseDto totalCountDto = rt.getForObject(totalCountUrl, ResponseDto.class);
            totalCount = totalCountDto.getResponse().getBody().getTotalCount();

            // 3. totalCount = 5136 만큼 한번에 가져오기

            String url = "http://apis.data.go.kr/B551182/rprtHospService/getRprtHospService?serviceKey=wJmmW29e3AEUjwLioQR22CpmqS645ep4S8TSlqtSbEsxvnkZFoNe7YG1weEWQHYZ229eNLidnI2Yt5EZ3Stv7g==&pageNo=1&numOfRows="
                    + totalCount + "&_type=json";
            ResponseDto responseDto = rt.getForObject(url, ResponseDto.class);

            List<Item> items = responseDto.getResponse().getBody().getItems().getItem();
            System.out.println("가져온 데이터 사이즈 : " + items.size());

            // 컬렉션 복사 (타입 다르네 ....)
            hospitals = items.stream().map(
                    (e) -> {
                        return Hospital.builder()
                                .addr(e.getAddr())
                                .mgtStaDd(e.getMgtStaDd())
                                .pcrPsblYn(e.getPcrPsblYn())
                                .ratPsblYn(e.getRatPsblYn())
                                .recuClCd(e.getRecuClCd())
                                .sgguCdNm(e.getSgguCdNm())
                                .sidoCdNm(e.getSidoCdNm())
                                .XPosWgs84(e.getXPosWgs84())
                                .YPosWgs84(e.getYPosWgs84())
                                .yadmNm(e.getYadmNm())
                                .ykihoEnc(e.getYkihoEnc())
                                .xPos(e.getXPos())
                                .yPos(e.getYPos())
                                .build();
                    }).collect(Collectors.toList());

            // 삭제 테스트
            // 기존 데이터 다 삭제하기 (삭제 잘되는지 먼저 테스트 하기위해 yml - ddl auto update 로 변경)
            hRepository.deleteAll();

            // 우선 다시 데이터 추가하고
            // 배치시간에 db에 insert 하기 (하루에 한번 할 예정 우선 테스트 지금 48분 47분으로 세팅하고 서버시작)
            hRepository.saveAll(hospitals);
        };
    }
}
