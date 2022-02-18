document.querySelector("#btn-submit").addEventListener("click", (e) => {

    let sidoCdNm = document.querySelector("#sidoCdNm").value;
    let sgguCdNm = document.querySelector("#sgguCdNm").value;

    //console.log(sidoCdNm);
    //console.log(sgguCdNm);

    getHospital(sidoCdNm, sgguCdNm);
});

let getHospital = async (sidoCdNm, sgguCdNm) => {
    let response = await fetch(`http://3.38.254.72:5000/api/hospital?sidoCdNm=${sidoCdNm}&sgguCdNm=${sgguCdNm}`);
    let responsePasing = await response.json();

    //console.log(responsePasing);
    setHospital(responsePasing);
}

let setHospital = (responsePasing) => {
    let tbodyHospitalDom = document.querySelector("#tbody-hospital");
    tbodyHospitalDom.innerHTML = "";

    responsePasing.forEach((e) => {
        let trEL = document.createElement("tr");

        let tdEL1 = document.createElement("td");
        let tdEL2 = document.createElement("td");
        let tdEL3 = document.createElement("td");

        tdEL1.innerHTML = e.yadmNm;
        tdEL2.innerHTML = e.pcrPsblYn;
        tdEL3.innerHTML = e.addr;

        //console.log(tdEL1);
        //console.log(tdEL2);
        //console.log(tdEL3);

        trEL.append(tdEL1);
        trEL.append(tdEL2);
        trEL.append(tdEL3);

        //console.log("==============");
        //console.log(trEL);
        //console.log("==============");
        tbodyHospitalDom.append(trEL);
    });
}

let setSggucdnm = (responsePasing) => {
    let sgguCdNmDom = document.querySelector("#sgguCdNm");
    // 바보 innerHTML 쓰면 되는걸 (초기화 하기)
    sgguCdNmDom.innerHTML = "";
    //console.log(responsePasing);

    // 리턴이 필요없으니 forEach로
    responsePasing.forEach((e) => {
        // 엘레멘트를 생성해서 넣어야 하구나
        let optionEL = document.createElement("option");
        optionEL.text = e;
        sgguCdNmDom.append(optionEL);
    });


}

let getSggucdnm = async (sidoCdNm) => {
    let response = await fetch(`http://3.38.254.72:5000/api/sggucdnm?sidoCdNm=${sidoCdNm}`);
    let responsePasing = await response.json();
    //console.log(responsePasing);
    setSggucdnm(responsePasing);
}

let sidoCdNmDom = document.querySelector("#sidoCdNm");
sidoCdNmDom.addEventListener("change", (e) => {
    console.log(e.target.value);
    let sidoCdNm = e.target.value;
    // 백틱 숫자 1옆에있는 ` 사용하면 자바스크립트 변수 바인딩 가능!!
    getSggucdnm(sidoCdNm);
});