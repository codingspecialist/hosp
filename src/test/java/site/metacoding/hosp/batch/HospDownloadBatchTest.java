package site.metacoding.hosp.batch;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import site.metacoding.hosp.domain.Hospital;

// 머하고 있는거지.. 크론을 왜 여기서!!
public class HospDownloadBatchTest {

    // 이거 로그 어디 찍히지? - DEBUG CONSOLE Launch Java Tests 부분!!
    // 테스트 완료
    @Test
    public void start() {
        // 1. 공공데이터 다운로드
        RestTemplate rt = new RestTemplate();
        // 테스트 할때는 %3D 사용하지 말기 (URL 인코딩 안해도됨)
        String url = "http://apis.data.go.kr/B551182/rprtHospService/getRprtHospService?serviceKey=wJmmW29e3AEUjwLioQR22CpmqS645ep4S8TSlqtSbEsxvnkZFoNe7YG1weEWQHYZ229eNLidnI2Yt5EZ3Stv7g==&pageNo=1&numOfRows=10&_type=json";
        ResponseDto dto = rt.getForObject(url, ResponseDto.class);
        // String dto = rt.getForObject(url, String.class);
        List<Item> hospitals = dto.getResponse().getBody().getItems().getItem();
        for (Item item : hospitals) {
            System.out.println(item.getYadmNm());
            System.out.println("PCR 여부 : " + item.getPcrPsblYn());
        }
    }

    // 공공데이터 다운로드 테스트 컬렉션 담기 (전체 데이터)
    @Test
    public void download() {

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
                            .xPosWgs84(e.getXPosWgs84())
                            .yPosWgs84(e.getYPosWgs84())
                            .yadmNm(e.getYadmNm())
                            .ykihoEnc(e.getYkihoEnc())
                            .xPos(e.getXPos())
                            .yPos(e.getYPos())
                            .build();
                }).collect(Collectors.toList());

        // 5186 상수로 테스트하면 나중에 바뀌면 망함.
        assertEquals(totalCount, hospitals.size());
    }
}
