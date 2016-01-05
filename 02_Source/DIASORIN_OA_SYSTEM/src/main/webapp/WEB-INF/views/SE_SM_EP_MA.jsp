<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="/WEB-INF/tagLib.tld" prefix="wa" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<link href="<c:url value='/css/list.css' />" type="text/css" rel="stylesheet" />
<link href="<c:url value='/css/jcDate.css' />" type="text/css" rel="stylesheet" />
<script src="<c:url value='/js/jquery.min.js' />" type="text/javascript"></script>
<script>

function expenseChange(){
	//当项目改变时table也是重新赋值
	reflashTable();
}

function init(){
	//初期化的时候给table重新赋值。
	reflashTable();
}

function reflashTable(){
	var expenseCode = $("#expenseCode").val();
	if (expenseCode == ""){
		return;
	} else {
		// 取得当前出差地类型下的数据
		jQuery.ajax({
			type : 'GET',
			contentType : 'application/json',
			url : '${pageContext.request.contextPath}/SE_SM_EP_MA/getList?expenseCode='+expenseCode,
			cache : false,
			async : false,
			dataType : 'json',
			success : function(data) {
				if(data != null){
					// 清空报销项目数据，在进行赋值
					$("#thTravelType").empty();
					$("#expenseUpList").empty();
					
					if (data.titleList != null && data.titleList.length > 0) {
						var head = '<td style="background:#f2f2f2;width:250px">&nbsp;</td>';
						$("#thTravelType").append(head);
						// 这里先加载明细的头目
						$.each(data.titleList,function(i,e){
							$("#thTravelType").append('<td>'+e.value+'</td>');
						});
					}
					if (data.beanList != null && data.beanList.length > 0){
						// 这里再次加载明细项目
						var tbLevelCode = "";
						var count = 0;
						$.each(data.beanList,function(i,e){
							if (tbLevelCode == e.levelCode) {
								$("#trCount_"+count).append('<td><input name="expensesUp" style="text-align:right;padding-right:5px" onblur="checkIsNumber(this)" class= "'+e.travelLocalTypeCode+
										'"  value="'+e.allowExpensesUp+'"/></td>');
							} else {
								// 新的一行开始
								count++;
								$("#expenseUpList").append('<tr id="trCount_'+ count +'">'+
										'<td id="' + e.levelCode + '">'+e.levelName+'</td>'+
										'<td><input name="expensesUp" style="text-align:right;padding-right:5px" onblur="checkIsNumber(this)" class= "'+e.travelLocalTypeCode+'" value="'+e.allowExpensesUp+'"/></td>'+
										'</tr>');
								tbLevelCode = e.levelCode;
							}
						});
						
						
					} else {
						// 没有数据
						$("#list_box2").css("display","none");
					}
					
				}
			},
			error : function(data) {
				
			}
		});	
	}
}

var I0018 = '<fmt:message key="I0018" />'
function expenseSubmit(){
	if (!validate()) return;
	
	// 确认是否提交数据
	if (!confirm(I0018)){
		return;
	}
	// 这里获取报销设定的全部数据
	var expenseCode = $("#expenseCode").val();  //作为param数据传到后台，其他数据作为JSON传到后台
	var tbRow= $("#expenseUpList").find("tr");
	var json = [];
	for (var i = 0;i<tbRow.length;i++){
		var tbCell = $(tbRow[i]).find("td");
		for (var j = 1 ; j< tbCell.length; j++){
			// 这里只循环级别以外的数据
			var paramConent = {
					levelCode:tbCell[0].id,//级别
					travelType:$(tbCell[j]).find("input").attr("class"), //出差地类型
					expenseUp:$(tbCell[j]).find("input").val() //金额上限	
			};
			json.push(paramConent);
		}
	}
	jQuery.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : '${pageContext.request.contextPath}/SE_SM_EP_MA/updateExpense?expenseCode='+expenseCode,
		dataType : 'json',
		data : JSON.stringify(json),
		success : function(data) {
			if(!data.isException){
				// 更新成功
				alert('<fmt:message key="I0009" />');
			} else {
				// 更新失败
				alert('<fmt:message key="I0010" />');
			}
		},
		error : function(data) {
			
		}
	});	
	
}

var E0008 = '<fmt:message key="E0008" />'
function validate(){
	var vali = true;
	var expenseCode = $("#expenseCode").val();
	if (expenseCode == "") {
		vali = false;
	}
	if (!hasInputCheck()){
		var message = E0008.replace("{0}", '<fmt:message key="se_sm_ep_ma_expenseUp" />')
		alert(message);
		vali = false;
	}
	return vali;
}

function hasInputCheck(){
	var hasInput = false;
	var expenseUp = document.getElementsByName("expensesUp");
	for (var i = 0 ;i < expenseUp.length; i++){
		if (expenseUp[i].value != "") {
			hasInput = true;
			break;
		}
	}
	return hasInput;
}

function checkIsNumber(str) {
	var amount = str.value;
	if (isNaN(amount)) {
		$(str).val("");
	} else {
		$(str).val(amount.trim());
	}
}
</script>
</head>

<body onload="init()">
<form:form id="seSmEpMaBean" modelAttribute="seSmEpMaBean" commandName="seSmEpMaBean">
	
	<div class="position"><fmt:message key="se_sm_ep_ma_position" /></div>
    
    
	<div class="list_main_box">
   	  <div class="list_title"><fmt:message key="se_sm_ep_ma_title" /></div> 
        <div class="list_box1">
        	<table border="0" cellpadding="0" cellspacing="0">
            	<tbody>
                	<tr>
                    	<td class="td_font"><fmt:message key="se_sm_ep_ma_expenseType" /></td>
                    	<td class="td_box">
                    		<form:select path="expenseCode" class="select_box" onchange="expenseChange()">
                    			<c:forEach var="seList" items="${ expensesList }">
                    				<form:option value="${ seList.expenseCode }">${ seList.expenseName }</form:option>
                    			</c:forEach>
                    		</form:select>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
        <div class="list_box2">
			<table border="0" cellpadding="0" cellspacing="0">
				<tbody>
					<tr class="td_title" id="thTravelType">
						
                    </tr>
				</tbody>
				<tbody id="expenseUpList">
                
                </tbody>
			</table>
            <div class="list_btn_box">
                <div class="save_center_btn">
                	<wa:showAtag id="SE_SM_EP_MA_Save" onclick="expenseSubmit()" innerImg="/images/save_btn.png"></wa:showAtag>
                </div>
                <div class="clear"></div>
            </div>
      </div>
    </div>
 </form:form>  
</body>
</html>
