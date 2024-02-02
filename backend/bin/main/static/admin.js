console.log("ㅋㅋㅋ");
console.log($("#hi").text());
sseForAdmin();

function sseForAdmin() {
    let eventSource = new EventSource("http://localhost:8080/sse/admin");

    eventSource.addEventListener("OPEN", function(e){
        let message = e.data;
        $("#contentContainer").append(`<p>[OPEN] : ${message} </p>`);
        // console.log("[CONNECT] : "+message);
    });

    //자동신고시스템 플로팅알림
    eventSource.addEventListener("SENSOR_REPORT", function(e){
        let message = JSON.parse(e.data);
        let time= message.time;
        let park = message.park;
        $("#contentContainer").append(`<p>[SENSOR_REPORT] : 자동신고시스템 : ${time} | ${park}구역 출차중 차량이 인식됨</p>`);
        // alert("[SENSOR_REPORT] : "+message);
    });

    //대쉬보드,공통바 => 입출차시 실시간 데이터 변경
    eventSource.addEventListener("REALTIME_COMMON", function(e){
        let message = e.data;
        $("#contentContainer").append(`<p>[REALTIME_COMMON] : ${message} </p>`);
        // alert("[REALTIME_COMMON] : "+message);
    });

    eventSource.addEventListener("ERROR", function(e) {
        let message = e.data;
        $("#contentContainer").append(`<p>[ERROR] : ${message} </p>`);
        // console.error('Error occurred:', e);
    });

}
