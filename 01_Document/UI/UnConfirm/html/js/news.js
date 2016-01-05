$(document).ready(function() {
	$("#show1").css("display","block");
	$("#hidshow").val("show1");
});

function show(parameter){
	var hidshow = $("#hidshow").val();
	$("#"+hidshow).css("display","none");
	$("#show"+parameter).css("display","block");
	$("#hidshow").val("show"+parameter);
}