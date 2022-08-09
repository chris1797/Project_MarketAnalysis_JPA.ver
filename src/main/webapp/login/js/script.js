
//로그인페이지 뒷배경사진 시간에 따라 변경

$( document ).ready(function() {
    var arr = ['bg_1.jpg','bg_2.jpg','bg_3.jpg'];
    
    var i = 0;
    setInterval(function(){
        if(i == arr.length - 1){
            i = 0;
        }else{
            i++;
        }
        var img = 'url(../../login/images/'+arr[i]+')';
        $(".full-bg").css('background-image',img); 
     
    }, 4000)


});