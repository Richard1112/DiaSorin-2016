
//非空check
function nullCheck(obj) {
	if (obj == null || obj == "") {
		return true;
	}
	return false;
}

function mailCheck(obj) {
	if (obj.search(/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/) != -1)
		return true;
	else
		return false;
}

//判断密码是否输入一致  
function issame(str1,str2)
{
	if (str1==str2) {
		return(true);
	}
	else{
		return(false);
	}
}

//判断是否为数字字母下滑线 
function hfEisujiCheck(str){
	var reg=/[^A-Za-z0-9_]/g;
	if (reg.test(str)){
		return false;
	}else{
		return true;
	}
}

function checkMobile(str){
	if(!(/^1[3|4|5|8][0-9]\d{4,8}$/.test(str))){
		return false;
	} else {
		return true;
	}
}
//判断是否是整整  整数：true 不是整整：false
function isInteger(str){
	str= str.replace(/,/g, "");
	if (/^[1-9][0-9]*$/.test(str)) {
		return true;
	}else{
		return false;
	}
}

//长度check
function lengthCheck(str, len) {
	if(str.length > len){
		return true;
	} else {
		return false;
	}
}


//证件号长度check
function idCardLenCheck(str, len) {
	if(str.length != len){
		return true;
	} else {
		return false;
	}
}

