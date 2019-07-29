$(function(){
	var key = getQueryString("sessionKey");
	 $.ajax({
        type: "get",
        dataType: "json",
        url: '/teach-web/index/home',
        data: 'sessionKey='+key,
        success: function (data) {
            if (data.code=="0") {
            	alert(data.data.userName);
            }
        }
    });
});

//获取URL中的传参
function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)","i");
    var r = window.location.search.substr(1).match(reg);
    if (r!=null) return r[2]; return null;
}