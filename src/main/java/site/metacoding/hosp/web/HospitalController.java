package site.metacoding.hosp.web;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;
import site.metacoding.hosp.domain.Hospital;
import site.metacoding.hosp.domain.HospitalRepository;

@RequiredArgsConstructor
@Controller
public class HospitalController {
    private static final String TAG = "HospitalController : ";
    private final HospitalRepository hRepository;

    // post로 받을때는 아래와 같이 받을 수 있는데 get으로 받을 때는 @Param 해줘야 하구나.... (바보)
    // post, get 둘다 그냥 받을 수 있음 x-www-form-urlencoded 타입은...
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("sidoCdNms", hRepository.mFindSidoCdNm()); // 여러개니까 끝에 복수 적어주자 s
        model.addAttribute("sgguCdNms", hRepository.mFindSggucdnm("강원")); // 여러개니까 끝에 복수 적어주자 s

        // 이제 index 페이지로 요청하면 데이터 안가져옴.
        return "index"; // templates/index.mustache 찾음
    }

    // http://localhost:8000/api/hospital?sidoCdNm=머시기&sgguCdNm=머시기
    @GetMapping("/api/hospital")
    public @ResponseBody List<Hospital> hospitals(String sidoCdNm, String sgguCdNm) {
        System.out.println(TAG + hRepository.mFindSidoCdNm().size());
        return hRepository.mFindHospital(sidoCdNm, sgguCdNm);
    }

    @GetMapping("/api/sggucdnm")
    // 응답도 json으로 할 예정
    public @ResponseBody List<String> sggucdnm(String sidoCdNm) { // 아니네 그냥 쿼리스트링 받으면 되네
        return hRepository.mFindSggucdnm(sidoCdNm);
    }
}
