(function ($) {
	
	    // Worldwide Sales Chart
	    
	  
    var ctx1 = $("#bigdata").get(0).getContext("2d");
    var myChart1 = new Chart(ctx1, {
        type: "bar",
        data: {
            labels: ["2016", "2017", "2018", "2019", "2020", "2021", "2022"],
            datasets: [{
                    label: "USA",
                    data: [15, 30, 55, 65, 60, 80, 95],
                    backgroundColor: "rgba(0, 156, 255, .7)"
                },
                {
                    label: "UK",
                    data: [8, 35, 40, 60, 70, 55, 75],
                    backgroundColor: "rgba(0, 156, 255, .5)"
                },
                {
                    label: "AU",
                    data: [12, 25, 45, 55, 65, 70, 60],
                    backgroundColor: "rgba(0, 156, 255, .3)"
                }
            ]
            },
        options: {
            responsive: true
        }
    });
	})(jQuery)

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

// 지도를 생성합니다
var map = new kakao.maps.Map(mapContainer, mapOption);

//지도 확대 축소를 제어할 수 있는  줌 컨트롤을 생성합니다
var zoomControl = new kakao.maps.ZoomControl();
map.addControl(zoomControl, kakao.maps.ControlPosition.RIGHT);

////////
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

function panTo() {
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

function dataTable(kind) {
	dataKind = kind
	alert(kind)
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
			document.getElementById("selectsales").innerHTML = dataKind;
		},
		error:function(xhr,status, err){
			alert('에러:'+err);
		}
	});
	return false;
}

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
		},
		error:function(xhr,status, err){
			alert('에러:'+err);
		}
	});
	return false;
}

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
		},
		error:function(xhr,status, err){
			alert('에러:'+err);
		}
	});
	return false;
}

function gilevent() {
	var thisgil = document.getElementById("gil").value;
	$.ajax({
		url:'/big/xy',
		method:'post',
		cache:false,
		data: {gil:thisgil},
		dataType:'json',
		success:function(res){
			x_pit = res.x;
			y_pit = res.y;
			start1();
		},
		error:function(xhr,status, err){
			alert('길을 선택해주세요');
		}
	});
	return false;
}

function flask(){
    var first = document.getElementById('first').value
    alert(first);
    $.ajax({
      type : 'post',
      url : 'http://127.0.0.1:5000/dataTable',
      //data : $('#data').serialize(),
      dataType : 'json',
      success : function(res) {
          alert('ajax으로 넘어옴');
          alert(res.sales.MON_SELNG_AMT);
        //alert('요청 성공쓰');;
      },
      error : function() {
        alert('요청 실패쓰');
      }
    });
    return false;
}
	
	

	