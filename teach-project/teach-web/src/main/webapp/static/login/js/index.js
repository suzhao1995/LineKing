$(function(){
	//初始化图形验证码
	 $.ajax({
        type: "get",
        dataType: "json",
        url: '/teach-web/index/verifyCode',
        data: 'userId=0001',
        success: function (data) {
            if (data != "") {
            	alert(data.code);
            	alert(data.data.randomCode);
            	$("#img").attr("src",data.data.verifyCode);
            }
        }
    });
});

function login(){
	var userId = $("#username").val();
	var pwd = $("#password1").val();
	$.ajax({
        type: "get",
        dataType: "json",
        url: '/teach-web/personalCenter/initCourse',
        data: 'classId=all',
        success: function (data) {
            if (data != "") {
            	alert(data.code);
            }
            if(data.code == "0"){
            	var key = data.data.userInfo.sessionKey;
            	//window.location.href="/teach-web/static/login/home.html?sessionKey="+key;
            }
        }
    });
}