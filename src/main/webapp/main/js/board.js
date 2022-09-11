//----------광고 게시판쪽의 js---------

	function save() {
		$.ajax({
			url : '/board/ads/save',
			method : 'post',
			cache : false,
			data : $('#input_form').serialize(),
			dataType : 'json',
			success : function(res) {
			
				
				if(res.saved!=0){
					alert('저장성공');	
					location.href="/board/ads/detail/"+res.saved;
				};
			},
			error : function(xhr, status, err) {
				alert('Error:' + err);
			}
		});
		return false;
	}(jQuery);
	
	function edit() {
		var numMac=document.getElementById("numMac").value;
		
		$.ajax({
			url:'/board/ads/edit',
			method:'post',
			cache:false,
			data:$('#edit_form').serialize(),
			dataType:'json',
			success:function(res){ //res는 object
					alert(res.updated ? '수정 성공' : '수정실패'); //res object 안에있는 num
					location.href="/board/ads/detail/"+numMac;
			},
			error:function(xhr,status,err){
				alert(err);
			}
		});
		return false;
	}(jQuery);
	
	function remove(){//comment의 pcode로 불러옴
		
		if(!confirm('해당 게시물 삭제하시겠어요?')) return;
		$.ajax({
			url : '/board/ads/delete/'+numMac,
			method:'get',
			cache:false,
			dataType:'json',
			success:function(res){ //res는 object
					alert(res.deleted && res.commetdeleted ? '삭제실패' : '삭제성공');
					location.href = '/board/ads/list';
			},
			error:function(xhr,status,err){
				alert(err);
			}
		});
		return false;
	}(jQuery);
	
	
		function comment(){
			
		if(msg != ""){
			alert(msg);
			location.href='/board/ads/detail/'+numMac;
		} else {
			$.ajax({
				url:'/board/comment',
				method:'post',
				cache:false,
				data:$('#comment_form').serialize(),
				dataType:'json',
				success:function(res){ //res는 object
						/* alert(res.commented ? '댓글작성성공' : '댓글작성실패'); */
						location.href='/board/ads/detail/'+numMac;
				},
				error:function(xhr,status,err){
					alert(err);
				}
			});
		}
		return false;
	}(jQuery);
	
		function commentdelete(num){
		if(confirm("해당 댓글을 삭제하시겠습니까?")){
			$.ajax({
				url:'/board/comment/delete/'+num,
				method:'get',
				cache:false,
				dataType:'json',
				success:function(res){ //res는 object
						alert(res.deleted ? '댓글이 삭제되었습니다.' : '댓글 삭제가 취소되었습니다.');
						location.href='/board/ads/detail/'+numMac;
				},
				error:function(xhr,status,err){
					alert(err);
				}
			});
		}
		return false;
	}(jQuery);
	
	
	
//----------자유 게시판쪽의 js---------

	function freesave() {
		var form = $('#input_form')[0]
		var data = new FormData(form);

		$.ajax({
			url : '/board/free/save',
			enctype: 'multipart/form-data',
			processData: false,
			contentType: false,
			method : 'post',
			cache : false,
			data : data,
			dataType : 'json',
			success : function(res) {
				
				if(res.saved!=0){
					alert('저장성공');	
					location.href="/board/free/detail/"+res.saved;
				};
			},
			error : function(xhr, status, err) {
				alert('Error:' + err);
			}
		});
		return false;
	}(jQuery);
	
		function freeedit() {
			var form = $('#edit_form')[0]
			var data = new FormData(form);
			var numMac=document.getElementById("numMac").value;
		$.ajax({
			url:'/board/free/edit',
			enctype: 'multipart/form-data',
			processData: false,
			contentType: false,
			method:'post',
			cache:false,
			data:data,
			dataType:'json',
			success:function(res){ //res는 object
					alert(res.updated ? '수정 성공' : '수정실패'); //res object 안에있는 num
					location.href="/board/free/detail/"+numMac;
			},
			error:function(xhr,status,err){
				alert(err);
			}
		});
		return false;
	}(jQuery);
	
	
		function freeremove(){
			if(!confirm('해당 게시물 삭제하시겠어요?')) return;
		$.ajax({
			url : '/board/free/delete/'+numMac,
			method:'get',
			cache:false,
			dataType:'json',
			success:function(res){ //res는 object
			
					alert(res.deleted && res.commetdeleted ? '삭제실패' : '삭제성공');
					location.href = '/board/free/list';
			},
			error:function(xhr,status,err){
				alert(err);
			}
		});
		return false;
	}(jQuery);
	
	
	function freecomment(){
			
		if(msg != ""){
			alert(msg);
			location.href='/board/free/detail/'+numMac;
		} else {
			$.ajax({
				url:'/board/comment',
				method:'post',
				cache:false,
				data:$('#comment_form').serialize(),
				dataType:'json',
				success:function(res){ //res는 object
						/* alert(res.commented ? '댓글작성성공' : '댓글작성실패'); */
						location.href='/board/free/detail/'+numMac;
				},
				error:function(xhr,status,err){
					alert(err);
				}
			});
		}
		return false;
	}(jQuery);
	
		function freecommentdelete(num){
		if(confirm("해당 댓글을 삭제하시겠습니까?")){
			$.ajax({
				url:'/board/comment/delete/'+num,
				method:'get',
				cache:false,
				dataType:'json',
				success:function(res){ //res는 object
						alert(res.deleted ? '댓글이 삭제되었습니다.' : '댓글 삭제가 취소되었습니다.');
						location.href='/board/free/detail/'+numMac;
				},
				error:function(xhr,status,err){
					alert(err);
				}
			});
		}
		return false;
	}(jQuery);


function filedelete(filenum){
	$.ajax({
		url : '/board/file/delete/'+filenum,
		method:'get',
		cache:false,
		dataType:'json',
		success:function(res){ //res는 object
		
				// alert(res.filedeleted ? '파일삭제성공' : '파일삭제실패');
				location.href = '/board/free/detail/'+numMac;
		},
		error:function(xhr,status,err){
			alert(err);
		}
	});
	return false;
}(jQuery);


function filedownload(filenum){
			
	if(msg != ""){
		alert(msg);
		location.href='/board/free/detail/'+numMac;
	} else {
		$.ajax({
			url:'/board/download/'+filenum,
			method:'get',
			cache:false,
			dataType:'json',
			success:function(res){ //res는 object
					location.href='/board/free/detail/'+numMac;
			},
			error:function(xhr,status,err){
				alert(err);
			}
		});
	}
	return false;
}(jQuery);