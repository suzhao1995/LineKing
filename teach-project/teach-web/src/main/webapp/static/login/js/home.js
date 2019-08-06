//$(function(){
//	var key = getQueryString("sessionKey");
//	 $.ajax({
//        type: "get",
//        dataType: "json",
//        url: '/teach-web/index/home',
//        data: 'sessionKey='+key,
//        success: function (data) {
//            if (data.code=="0") {
//            	alert(data.data.userName);
//            }
//        }
//    });
//});

//获取URL中的传参
function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)","i");
    var r = window.location.search.substr(1).match(reg);
    if (r!=null) return r[2]; return null;
}
var url = "";
var fileName = "";
var upLoadId = "";
function upload(){
	var formData = new FormData($('#uploadForm')[0]);
	$.ajax({
      type: "post",
      url: '/teach-web/componant/upLoadFile',
      data: formData,
      cache : false,
      processData : false,
	  contentType : false,
      success: function (data) {
          if("0" == data.code){
        	  alert(data.data.message);
        	  url = data.data.sectionUrl;
        	  fileName = data.data.updateFileName;
        	  upLoadId = data.data.upLoadId;
          }else{
        	  alert(data.message);
          }
          
      }
  });
}

function show(){
	$.ajax({
      type: "get",
      dataType: "json",
      url: '/teach-web/test/getImage',
      data: 'url='+url+"&fileName="+fileName+"&upLoadId="+upLoadId,
      success: function (data) {
          if (data.code=="0") {
        	  var list = data.data;
        	  $("#showImg").each(function(indx,obj){
	        	  for(var i = 0; i < list.length; i++){
	        		  var html1= '<input class="code-img" src="'+list[i]+'" type="image" id="img">';
	        		  $(this).html(html1);
	        	  }
        	  });
          }
      }
  });
}