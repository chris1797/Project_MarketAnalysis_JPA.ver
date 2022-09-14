(function ($) {
	
	    // Worldwide Sales Chart
    var ctx1 = $("#sales").get(0).getContext("2d");
    var myChart1 = new Chart(ctx1, {
        type: "bar",
        data: {
            labels: ["2017", "2018", "2019", "2020", "2021"],
            datasets: [{
                    label: "년도별 총 매출량(억)",
                    data: [1038343, 1079570, 869818, 947314, 930156],
                    backgroundColor: "rgba(0, 156, 255, .7)"
                }]
            },
        options: {
            responsive: true
        }
    });
     var ctx2 = $("#pop").get(0).getContext("2d");
    var myChart1 = new Chart(ctx2, {
        type: "bar",
        data: {
            labels: ["10대", "20대", "30대", "40대", "50대", "60대 이상"],
            datasets: [{
                    label: "2018년",
                    data: [28, 31, 31, 30, 28, 47],
                    backgroundColor: "rgba(0, 156, 255, .7)"
                },
                {
                    label: "2019년",
                    data: [27, 31, 31, 29, 28, 46],
                    backgroundColor: "rgba(0, 156, 255, .5)"
                },
                {
                    label: "2020년",
                    data: [27, 31, 31, 30, 28, 45],
                    backgroundColor: "rgba(0, 156, 255, .3)"
                },
                {
                    label: "2021년",
                    data: [26, 30, 31, 29, 28, 43],
                    backgroundColor: "rgba(0, 156, 156, .3)"
                }
                ]
            },
        options: {
            responsive: true
        }
    });
     var ctx3 = $("#store").get(0).getContext("2d");
    var myChart1 = new Chart(ctx3, {
        type: "bar",
        data: {
            labels: ["2017", "2018", "2019", "2020", "2021"],
            datasets: [{
                    label: "개업 점포 수",
                    data: [160456, 127072, 123727, 111370, 111149],
                    backgroundColor: "rgba(0, 156, 255, .7)"
                },
                {
                    label: "패업 점포 수",
                    data: [160799, 154955, 140378, 126540, 106154],
                    backgroundColor: "rgba(0, 156, 255, .5)"
                }
            ]
            },
        options: {
            responsive: true
        }
    });
    
     
 
	
	
	})(jQuery);
	
	

	