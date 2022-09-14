var dataKind;
var markers = [];
var x_pit=0;
var y_pit=0;
var xpt;
var ypt;
var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
    mapOption = {
        center: new kakao.maps.LatLng(37.5209, 126.9831), // 지도의 중심좌표 / 좌표 찾는법 : 구글지도에서 찾으려는 위치 선택후 주변검색누르면 좌표확인가능
        level: 8 // 지도의 확대 레벨
    };  

function hidden(){
	//$("#location").hide();
	//$("#location_serch").hide();
	$("#sales_view").hide();
	$("#population_view").hide();
	$("#stores_view").hide();	
	$("#svc_name").hide();
	$("#year").hide();
	$("#quarter").hide();
	$("#check_co").hide();
	$("#data_serch").hide();
	//$("#map").hide();
}

function hidshow(){
	$("#location").show();
	$("#location_serch").show();
	$("#year").show();
	$("#quarter").show();
	$("#data_serch").show();
	//$("#map").show();
}

hidden();


// 지도를 생성합니다
var map = new kakao.maps.Map(mapContainer, mapOption);

//지도 확대 축소를 제어할 수 있는  줌 컨트롤을 생성합니다
var zoomControl = new kakao.maps.ZoomControl();
map.addControl(zoomControl, kakao.maps.ControlPosition.RIGHT);

//좌표찍기
function start1(){
	var geocoder = new kakao.maps.services.Geocoder(), // 좌표계 변환 객체를 생성합니다
		     wtmX = x_pit, // 변환할 WTM X 좌표 입니다
		     wtmY = y_pit; // 변환할 WTM Y 좌표 입니다
	
	var callback = function(result, status) {
		if (status === kakao.maps.services.Status.OK) {
			console.log(result[0].x);	    
			console.log(result[0].y);
			xpt = result[0].x;
			ypt = result[0].y;
		}
	};
	
	//WTM 좌표를 WGS84 좌표계의 좌표로 변환한다
	geocoder.transCoord(wtmX, wtmY, callback, {
	    input_coord: kakao.maps.services.Coords.WTM,
	    output_coord: kakao.maps.services.Coords.WGS84
	});
}

//지도출력
function start2() {
	//마커 제거//
	for (var i=0; i<markers.length; i++){
		markers[i].setMap(null);
	}
	
    var moveLatLon = new kakao.maps.LatLng(ypt, xpt);
    var marker = new kakao.maps.Marker({
        position: new kakao.maps.LatLng(ypt, xpt), // 마커를 표시할 위치입니다
        map: map // 마커를 표시할 지도객체입니다
    })
    
    // 지도 이동시 마커의 지도레벨을 정해줌
    map.setLevel(4, {anchor: new kakao.maps.LatLng(ypt, xpt)});
    
    // 지도 중심을 부드럽게 이동시킵니다
    // 만약 이동할 거리가 지도 화면보다 크면 부드러운 효과 없이 이동합니다
    map.panTo(moveLatLon);
    
    //배열안에 마커를 넣어주면서 마커의 순번을 정해주는 코드
    markers.push(marker)
}

//데이터 종류 선택
function dataTable(kind) {
	dataKind = kind;
	$.ajax({
		url:'/big/kind',
		method:'post',
		cache:false,
		data: {kind:kind},
		dataType:'json',
		success:function(res){
			//html 에서 option 적용하게 만듬.
			document.getElementById("gu").innerHTML = res.gulist;
			document.getElementById("dong").innerHTML = res.donglist;
			document.getElementById("gil").innerHTML = res.gillist;
			
			document.getElementById("dataKind").value = dataKind;
			document.getElementById("svc_name").innerHTML = "<option>서비스명</option>";
			document.getElementById("year").innerHTML = "<option>년도</option>";
			document.getElementById("quarter").innerHTML = "<option>분기</option>";
			
			hidden();
			if(dataKind == "sales"){
				hidshow();
				$("#sales_view").show();
				$("#svc_name").show();
				$("#check_co").show();
				
	        } else if (dataKind =="population") {
				hidshow();
				$("#population_view").show();
				
			}  else {
				hidshow();				
				$("#stores_view").show();
				$("#svc_name").show();
			}
			
		},
		error:function(xhr,status, err){
			alert('에러:'+err);
		}
	});
	return false;
}

//구 찾기
function guevent() {
	var thigu = document.getElementById("gu").value;
	$.ajax({
		url:'/big/dong',
		method:'post',
		cache:false,
		data: {gu:thigu, kind:dataKind},
		dataType:'json',
		success:function(res){
			document.getElementById("dong").innerHTML = res.donglist;
			document.getElementById("gil").innerHTML = res.gillist;
			document.getElementById("svc_name").innerHTML = "<option>서비스명</option>";
			document.getElementById("year").innerHTML = "<option>년도</option>";
			document.getElementById("quarter").innerHTML = "<option>분기</option>";
		},
		error:function(xhr,status, err){
			alert('에러:'+err);
		}
	});
	return false;
}

//동 찾기
function dongevent() {
	var thisdong = document.getElementById("dong").value;
	$.ajax({
		url:'/big/gil',
		method:'post',
		cache:false,
		data: {dong:thisdong, kind:dataKind},
		dataType:'json',
		success:function(res){
			document.getElementById("gil").innerHTML = res.gillist;
			document.getElementById("svc_name").innerHTML = "<option>서비스명</option>";
			document.getElementById("year").innerHTML = "<option>년도</option>";
			document.getElementById("quarter").innerHTML = "<option>분기</option>";
		},
		error:function(xhr,status, err){
			alert('에러:'+err);
		}
	});
	return false;
}

//골목길 찾기
function gilevent() {
	var thisgil = document.getElementById("gil").value;
	$.ajax({
		url:'/big/xy',
		method:'post',
		cache:false,
		data: {gil:thisgil, kind:dataKind},
		dataType:'json',
		success:function(res){
			document.getElementById("trdar_name").value = thisgil;
			x_pit = res.x;
			y_pit = res.y;
			start1();
			
			//검색창
			if(dataKind == "sales" || dataKind == "store"){
				document.getElementById("svc_name").innerHTML = res.svclist;	
				document.getElementById("year").innerHTML = "<option>년도</option>";
				document.getElementById("quarter").innerHTML = "<option>분기</option>";
			} else if(dataKind == "population") {
				document.getElementById("year").innerHTML = res.yearlist;
				document.getElementById("quarter").innerHTML = "<option>분기</option>";
			}
			
			
		},
		error:function(xhr,status, err){
			alert('길을 선택해주세요');
		}
	});
	
	return false;
}

//서비스명 찾기
function svcevent() {
	var thissvc = document.getElementById("svc_name").value;
	var thisgil = document.getElementById("trdar_name").value;
	$.ajax({
		url:'/big/svc',
		method:'post',
		cache:false,
		data: {thissvc:thissvc, kind:dataKind, thisgil:thisgil},
		dataType:'json',
		success:function(res){
			document.getElementById("year").innerHTML = res.yearlist;
			document.getElementById("quarter").innerHTML = "<option>분기</option>";
		},
		error:function(xhr,status, err){
			alert('에러:'+err);
		}
	});
	return false;
}

//년도 찾기
function yearevent() {
	var thisyear = document.getElementById("year").value;
	var thissvc = document.getElementById("svc_name").value;
	var thisgil = document.getElementById("trdar_name").value;
	$.ajax({
		url:'/big/year',
		method:'post',
		cache:false,
		data: {year:thisyear, thissvc:thissvc, kind:dataKind, thisgil:thisgil},
		dataType:'json',
		success:function(res){
			document.getElementById("quarter").innerHTML = res.quarterlist;
		},
		error:function(xhr,status, err){
			alert('에러:'+err);
		}
	});
	return false;
}

//AI 서버(python) 연동
function flask(){
    $.ajax({
      type : 'post',
      url : 'http://127.0.0.1:5000/dataTable',
      data : $('#data').serialize(),
      dataType : 'json',
      success : function(res) {
		if(dataKind == "sales"){
			salesFun(res.rowData);
        } else if (dataKind == "population") {
			populationFun(res.rowData);
		}  else {
			store(res.rowData);
		}
		start2();
      },
      error : function() {
        alert('요청 실패쓰');
      }
    });
    return false;
}


//매출 그래프 생성
var myChart1 = new Chart(document.getElementById("sales1"), {
    type: "bar",
    data: {
        labels: ["월", "화", "수", "목", "금", "토", "일"],
        datasets: [{
                label: "요일별 매출",
                data: [],
                backgroundColor: "rgba(0, 156, 255, .7)",
            }]
        },
});	

var myChart2 = new Chart(document.getElementById("sales2"), {
    type: "bar",
    data: {
        labels: ["00~06시", "06~11시", "11~14시", "14~17시", "17~21시", "21~24시"],
        datasets: [{
                label: "시간별 매출",
                data: [],
                backgroundColor: "rgba(0, 156, 255, .7)"
            }]
        },
});

var myChart3 = new Chart(document.getElementById("sales3"), {
    type: "pie",
    data: {
        labels: ["남성", "여성"],
        datasets: [{
                label: "성별 매출",
                data: [],
                backgroundColor: ['rgb(255, 99, 132)', 'rgb(54, 162, 235)']
            }]
        }
});

var myChart4 = new Chart(document.getElementById("sales4"), {
    type: "bar",
    data: {
        labels: ["10대", "20대", "30대", "40대", "50대", "60대 이상"],
        datasets: [{
                label: "연령별 매출",
                data: [],
                backgroundColor: "rgba(0, 156, 255, .7)"
            }]
        }
});

//유동인구 그래프 생성
var myChart5 = new Chart(document.getElementById("pupulation1"), {
    type: "pie",
    data: {
        labels: ["남성" ,"여성"],
        datasets: [{
                label: "성별 생활인구 수",
                data: [],
                backgroundColor: ['rgb(255, 99, 132)', 'rgb(54, 162, 235)']
            }]
        }
});

var myChart6 = new Chart(document.getElementById("pupulation2"), {
    type: "bar",
    data: {
        labels: ["10대", "20대", "30대", "40대", "50대", "60대 이상"],
        datasets: [{
                label: "연령별 생활인구 수",
                data: [],
                backgroundColor: "rgba(0, 156, 255, .7)"
            }]
        }
});

var myChart7 = new Chart(document.getElementById("pupulation3"), {
    type: "bar",
    data: {
        labels: ["00~06시", "06~11시", "11~14시", "14~17시", "17~21시", "21~24시"],
        datasets: [{
                label: "시간대별 생활인구 수",
                data: [],
                backgroundColor: "rgba(0, 156, 255, .7)"
            }]
        }
});

var myChart8 = new Chart(document.getElementById("pupulation4"), {
    type: "bar",
    data: {
        labels: ["월", "화", "수", "목", "금", "토", "일"],
        datasets: [{
                label: "요일별 생활인구 수",
                data: [],
                backgroundColor: "rgba(0, 156, 255, .7)"
            }]
        }
});

//매출 그래프 그리기
function salesFun(salesData){
	var sales = salesData;
	var check_co = document.getElementById("check_co").value;
	if (check_co == '금액') {
		var data1 = [sales.MON_SELNG_AMT, sales.TUES_SELNG_AMT, sales.WED_SELNG_AMT, sales.THUR_SELNG_AMT, sales.FRI_SELNG_AMT, sales.SAT_SELNG_AMT, sales.SUN_SELNG_AMT];
		var data2 = [sales.TMZON_00_06_SELNG_AMT, sales.TMZON_06_11_SELNG_AMT, sales.TMZON_11_14_SELNG_AMT, sales.TMZON_14_17_SELNG_AMT, sales.TMZON_17_21_SELNG_AMT, sales.TMZON_21_24_SELNG_AMT];
		var data3 = [sales.ML_SELNG_AMT, sales.FML_SELNG_AMT];
		var data4 = [sales.AGRDE_10_SELNG_AMT, sales.AGRDE_20_SELNG_AMT, sales.AGRDE_30_SELNG_AMT, sales.AGRDE_40_SELNG_AMT, sales.AGRDE_50_SELNG_AMT, sales.AGRDE_60_ABOVE_SELNG_AMT];
	} else {
		var data1 = [sales.MON_SELNG_CO, sales.TUES_SELNG_CO, sales.WED_SELNG_CO, sales.THUR_SELNG_CO, sales.FRI_SELNG_CO, sales.SAT_SELNG_CO, sales.SUN_SELNG_CO];
		var data2 = [sales.TMZON_00_06_SELNG_CO, sales.TMZON_06_11_SELNG_CO, sales.TMZON_11_14_SELNG_CO, sales.TMZON_14_17_SELNG_CO, sales.TMZON_17_21_SELNG_CO, sales.TMZON_21_24_SELNG_CO];
		var data3 = [sales.ML_SELNG_CO, sales.FML_SELNG_CO];
		var data4 = [sales.AGRDE_10_SELNG_CO, sales.AGRDE_20_SELNG_CO, sales.AGRDE_30_SELNG_CO, sales.AGRDE_40_SELNG_CO, sales.AGRDE_50_SELNG_CO, sales.AGRDE_60_ABOVE_SELNG_CO];
	}
	
	var ctx1 = document.getElementById("sales1");
	myChart1.destroy();
    myChart1 = new Chart(ctx1, {
        type: "bar",
        data: {
           labels: ["월", "화", "수", "목", "금", "토", "일"],
            datasets: [{
                    label: "요일별 매출",
                    data: data1,
                    backgroundColor: "rgba(0, 156, 255, .7)"
                }
            ]},
        options: {
            responsive: true
        }
    });
    
	var ctx2 = document.getElementById("sales2");
	myChart2.destroy();
    myChart2 = new Chart(ctx2, {
        type: "bar",
        data: {
        	labels: ["00~06시", "06~11시", "11~14시", "14~17시", "17~21시", "21~24시"],
            datasets: [{
                    label: "시간별 매출",
                    data: data2,
                    backgroundColor: "rgba(0, 156, 255, .7)"
                }
            ]},
        options: {
            responsive: true
        }
    });
    
    var ctx3 = document.getElementById("sales3");
	myChart3.destroy();
    myChart3 = new Chart(ctx3, {
        type: "pie",
        data: {
        	labels: ["여성", "남성"],
            datasets: [{
                    label: "성별 매출",
                    data: data3,
                    backgroundColor: ['rgb(255, 99, 132)', 'rgb(54, 162, 235)']
                }
            ]},
        options: {
            responsive: false
        }
    });
    
    var ctx4 = document.getElementById("sales4");
	myChart4.destroy();
    myChart4 = new Chart(ctx4, {
        type: "bar",
        data: {
        	labels: ["10대", "20대", "30대", "40대", "50대", "60대 이상"],
            datasets: [{
                    label: "연령별 매출",
                    data: data4,
                    backgroundColor: "rgba(0, 156, 255, .7)"
                }
            ]},
        options: {
            responsive: true
        }
    });
    
    document.getElementById("sotre_count").innerHTML = "점포 "+ sales.STOR_CO + "개의 매출 " + check_co;
    
}

//유동인구 그래프 그리기
function populationFun(rowData){
	var population = rowData;
	
	var ctx5 = document.getElementById("pupulation1");
	myChart5.destroy();
    myChart5 = new Chart(ctx5, {
        type: "pie",
        data: {
        labels: ["여성", "남성"],
            datasets: [{
                label: "성별 생활인구 수",
                    data: [population.ML_FLPOP_CO, population.FML_FLPOP_CO],
                    backgroundColor: ['rgb(255, 99, 132)', 'rgb(54, 162, 235)']
                }
            ]},
        options: {
            responsive: false
        }
    });
    
    var ctx6 = document.getElementById("pupulation2");
	myChart6.destroy();
    myChart6 = new Chart(ctx6, {
        type: "bar",
        data: {
        labels: ["10대", "20대", "30대", "40대", "50대", "60대 이상"],
            datasets: [{
                label: "연령별 생활인구 수",
                    data: [population.AGRDE_10_FLPOP_CO, population.AGRDE_20_FLPOP_CO, population.AGRDE_30_FLPOP_CO, population.AGRDE_40_FLPOP_CO, population.AGRDE_50_FLPOP_CO, population.AGRDE_60_ABOVE_SELNG_CO],
                    backgroundColor: "rgba(0, 156, 255, .7)"
                }
            ]},
        options: {
            responsive: true
        }
    });
	
	var ctx7 = document.getElementById("pupulation3");
	myChart7.destroy();
    myChart7 = new Chart(ctx7, {
        type: "bar",
        data: {
        labels: ["00~06시", "06~11시", "11~14시", "14~17시", "17~21시", "21~24시"],
            datasets: [{
                label: "시간대별 생활인구 수",
                    data: [population.TMZON_1_FLPOP_CO, population.TMZON_2_FLPOP_CO, population.TMZON_3_FLPOP_CO, population.TMZON_4_FLPOP_CO, population.TMZON_5_FLPOP_CO, population.TMZON_6_FLPOP_CO],
                    backgroundColor: "rgba(0, 156, 255, .7)"
                }
            ]},
        options: {
            responsive: true
        }
    });

	var ctx8 = document.getElementById("pupulation4");
	myChart8.destroy();
    myChart8 = new Chart(ctx8, {
        type: "bar",
        data: {
        labels: ["월", "화", "수", "목", "금", "토", "일"],
            datasets: [{
                label: "시간대별 생활인구 수",
                    data: [population.MON_FLPOP_CO, population.TUES_FLPOP_CO, population.WED_FLPOP_CO, population.THUR_FLPOP_CO, population.FRI_FLPOP_CO, population.SAT_FLPOP_CO, population.SUN_FLPOP_CO],
                    backgroundColor: "rgba(0, 156, 255, .7)"
                }
            ]},
        options: {
            responsive: true
        }
    });
    
    document.getElementById("population_count").innerHTML = "총 생활인구 : "+ population.TOT_FLPOP_CO+"명";
}

//점포데이터 출력
function store(rowData){
	var store = rowData;
	document.getElementById("store_count").innerHTML = "점포 수: 총 "+store.STOR_CO +"개";
	document.getElementById("store1").innerHTML = store.SIMILR_INDUTY_STOR_CO+"개";
	document.getElementById("store2").innerHTML = store.OPBIZ_RT+"%";
	document.getElementById("store3").innerHTML = store.OPBIZ_STOR_CO+"개";
	document.getElementById("store4").innerHTML = store.CLSBIZ_RT+"%";
	document.getElementById("store5").innerHTML = store.CLSBIZ_STOR_CO+"개";
	document.getElementById("store6").innerHTML = store.FRC_STOR_CO+"개";
}	