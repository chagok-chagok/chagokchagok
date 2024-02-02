console.log("ㅋㅋㅋ");
console.log($("#hi").text());
sseForKiosk();

function sseForKiosk() {
    let eventSource = new EventSource("http://localhost:8080/sse/kiosk");

    eventSource.addEventListener("OPEN", function(e){
        let message = e.data;
        $("#contentContainer").append(`<p>[OPEN] : ${message} </p>`);
        // console.log("[CONNECT] : "+message);
    });

    //만차해제시 키오스크 알림
    eventSource.addEventListener("CONGESTION_CLEAR", function(e){
        let message = e.data;
        $("#contentContainer").append(`<p>[CONGESTION_CLEAR] : ${message} </p>`);
        // alert("[CONGESTION_CLEAR] : "+message);
    });

    //차번호 키오스크 전달
    eventSource.addEventListener("CAR_NUM", function(e) {
        let message = e.data;
        $("#contentContainer").append(`<p>[CAR_NUM] : ${message} </p>`);
        // alert("[CAR_NUM] : "+message);
    });

    eventSource.addEventListener("ERROR", function(e) {
        $("#contentContainer").append(`<p>[ERROR] : ${message} </p>`);
        // console.error('Error occurred:', e);
    });
}
