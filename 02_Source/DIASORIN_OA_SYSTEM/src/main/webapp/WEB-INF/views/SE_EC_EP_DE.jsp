<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/tagLib.tld" prefix="wa" %>
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
<link href="<c:url value='/bootstrap/css/bootstrap-3.0.3.min.css' />" type="text/css" rel="stylesheet" />
<link href="<c:url value='/bootstrap/css/bootstrap-multiselect.css' />" type="text/css" rel="stylesheet" />
<link href="<c:url value='/bootstrap/css/prettify.css' />" type="text/css" rel="stylesheet" />
<link href="<c:url value='/bootstrap/css/bootstrap-datepicker.css' />" type="text/css" rel="stylesheet" />
<link href="<c:url value='/css/cover.css' />" type="text/css" rel="stylesheet" />
<link href="<c:url value='/css/user-common.css' />" type="text/css" rel="stylesheet" />

<script src="<c:url value='/bootstrap/js/jquery-2.0.3.min.js' />" type="text/javascript"></script>
<script src="<c:url value='/bootstrap/js/bootstrap-3.0.3.min.js' />" type="text/javascript"></script>
<script src="<c:url value='/bootstrap/js/bootstrap-multiselect.js' />" type="text/javascript"></script>
<script src="<c:url value='/bootstrap/js/prettify.js' />" type="text/javascript"></script>
<script src="<c:url value='/bootstrap/js/bootstrap-datepicker.js' />" type="text/javascript"></script>
<script src="<c:url value='/js/user-common.js' />" type="text/javascript"></script>
<script src="<c:url value='/js/commonFunction.js' />" type="text/javascript"></script>

<script>

var E0002 = '<fmt:message key="E0002" />';
var I0006 = '<fmt:message key="I0006" />';
var E0008 = '<fmt:message key="E0008" />';
var E0007 = '<fmt:message key="E0007" />';
var E0010 = '<fmt:message key="E0010" />';
var I0023 = '<fmt:message key="I0023" />';

var inputTemp = '<span>KM &nbsp;</span><input class="input_date_box" style="width:80px;text-align:right" disabled="disabled">';

$(document).ready(function() {
	dateInit();
});

function dateInit(){
	$('.inputDate').datepicker({
    	format: "yyyy/mm/dd",
        clearBtn: true,
        orientation: "top right",
        autoclose: true,
        todayHighlight: true
    }); 
}

// 删除当前行
function deleteRow(str) {
	if (!confirm(I0006)) {
		return;
	}
	$(str).parent().parent().remove();
	
	$("#hasChanged").val("1");
	// 重新计算总金额
	viewTotalAmount();
}

// 初期化
function init(){
	var expenseTypeArr = document.getElementsByName("expenseType");
	for (var i = 0 ; i< expenseTypeArr.length; i++){
		var expenseValue = $(expenseTypeArr[i]).val();
		var selectObj = $(expenseTypeArr[i]).parent().parent().children("td").eq(0).find("select");
		$(selectObj).val(expenseValue);
		//如果选择的是S025的话。给一个提示
		var amountInput = $(expenseTypeArr[i]).parent().parent().children("td").eq(3).find("input")[0];
		if (expenseValue != null && expenseValue.split(",")[0] == "S025") {
			$(amountInput).parent().append(inputTemp);
			$(amountInput).css("width","50");
		} else {
			$(amountInput).nextAll('input').remove();
			$(amountInput).css("width","130");
		}
	}
	// 获取除标题行以外的所有行
	var rows = $("#detailRow tr:not(:first)");
	for (var i = 0 ; i< rows.length; i++) {
		if ($(rows[i]).attr('id') == "hiddenTr") continue;
		// 判断当前行的下拉框是否已经被选中
		var expenseType = $(rows[i]).children("td").eq(0).children("select").first().val()
		if (expenseType == null || expenseType == "") {
			// 没有选中，说明当前是子项目 同步AJAX操作
			var expenseTypeValue = $(rows[i]).children("td").eq(4).find("input")[1].value;
			var expenseItem = expenseTypeValue.split(",")[0];
			jQuery.ajax({
				type : 'GET',
				contentType : 'application/json',
				url : '${pageContext.request.contextPath}/SE_EC_EP_DE/getCurrentItem?expenseItem='+expenseItem,
				cache : false,
				async : false,
				dataType : 'json',
				success : function(data) {
					if(!data.isException){
						var farExpense = data.farExpense;
						var subItem = data.currentExpenseList;
						var subItemStr = '<select class="select_box" onchange="changeExpenseType(this)" style="width:120px">';
						for (var j = 0 ; j< subItem.length; j++) {
							subItemStr += '<option value="'+subItem[j].expenseCode +','+subItem[j].timeMethod +'">'+subItem[j].expenseName+'</option>';
						}
						subItemStr += '</select>';
						$(rows[i]).children("td").eq(0).append(subItemStr);
						// html生成之后，将其赋值。
						// 费用赋值
						var selectAll = $(rows[i]).children("td").eq(0).find("select");
						$(selectAll[0]).val(farExpense.expenseCode+","+farExpense.timeMethod);
						// 子费用赋值
						$(selectAll[1]).val(expenseTypeValue);
					}
				},
				error : function(data) {
					
				}
			});	
		} 
	}
	viewTotalAmount();
	//初期化的时候显示计费方式
	viewTimeMethodInit();
	//画面是否需要设成不可以编辑
	itemDisabled();
	
}

function itemDisabled() {
	var hiddenStatus = $("#hiddenStatus").val();
	if (hiddenStatus != "3" && hiddenStatus != "0") {
		$("#purpose").attr("disabled","disabled");
		$("#detailRow").find("input").attr("disabled","disabled");
		$("#detailRow").find("select").attr("disabled","disabled");
	}
}

// 显示总金额
function viewTotalAmount(){
	// 总金额的显示
	var expenseAmountArr = document.getElementsByName("expenseAmount");
	var totalAmount = 0;
	for (var i = 0 ; i< expenseAmountArr.length; i++){
		var expenseItem = $(expenseAmountArr[i]).parent().parent().children("td").eq(0).find("select")[0].value;
		if ("S025" == expenseItem.split(",")[0]) {
			if (expenseAmountArr[i].value != ""){
				var carAmount = parseFloat(expenseAmountArr[i].value)*($("#hiddenCarCate").val());
				totalAmount += carAmount;
				$(expenseAmountArr).next().next().val(fmoney(carAmount,2));
			}
		} else {
			if (expenseAmountArr[i].value != ""){
				totalAmount += parseFloat(expenseAmountArr[i].value);
			}
		}
		
	}
	$("#totalAmount").html(fmoney(totalAmount,2));
}

// 加一行
function addRow(){
	var trHtml = $("#hiddenTr").clone();
	$(trHtml).css("display","");
	$(trHtml).attr("id","");
	$("#detailRow").append(trHtml);
	dateInit();
	$("#hasChanged").val("1");
}

// 金钱计算
function checkAmount(str){
	var amount = $(str).val();
	if (isNaN(amount)) {
		$(str).val("");
	} else {
		$(str).val(amount.trim());
	}
	viewTotalAmount();
}

// 初期化的时候显示计费方式
function viewTimeMethodInit(){
	var expenseTypeArr = document.getElementsByName("expenseType");
	for (var i = 0 ; i< expenseTypeArr.length; i++){
		var expenseValue = $(expenseTypeArr[i]).val();
		var timeMethod = expenseValue.split(",")[1];
		var needHiddenInputFrom = $(expenseTypeArr[i]).parent().parent().children("td").eq(1).find("input")[0];
		var needHiddenInputTo = $(expenseTypeArr[i]).parent().parent().children("td").eq(1).find("input")[1];
		var needHiddenLabel = $(expenseTypeArr[i]).parent().parent().children("td").eq(1).find("label")[0];
		if (timeMethod == "1"){
			// 以天计费
			$(needHiddenInputFrom).attr("type","hidden");
			$(needHiddenLabel).css("display","none");
		} else if (timeMethod == "2") {
			// 以时间段计费
		} else if (timeMethod == "3") {
			// 以月计费
			$(needHiddenInputFrom).attr("type","hidden");
			$(needHiddenLabel).css("display","none");
			
			// 将当前的日期空间变成月份显示
			var needChangeToMonth = $(expenseTypeArr[i]).parent().parent().children("td").eq(1).find("input")[1];
			needChangeToMonth.value = needChangeToMonth.value.substring(0,7);
			datePickerToMonth(needChangeToMonth);
		}
	}
}

// 日期控件变成月份
function datePickerToMonth(str){
	
	$(str).datepicker('remove');
	$(str).datepicker({
    	format: "yyyy/mm",
        clearBtn: true,
        orientation: "top right",
        minViewMode: 1,
        autoclose: true,
        todayHighlight: true
    }); 
}

//日期控件变成天数
function datePickerToDay(str){
	$(str).datepicker("remove");
	$(str).datepicker({
    	format: "yyyy/mm/dd",
        clearBtn: true,
        orientation: "top right",
        minViewMode: 0,
        autoclose: true,
        todayHighlight: true
    }); 
}

// 报销项目的变更
function changeExpenseType(str){
	// 去判断是不是拥有子项目
	var expenseItem = $(str).val().split(",")[0];
	jQuery.ajax({
			type : 'GET',
			contentType : 'application/json',
			url : '${pageContext.request.contextPath}/SE_EC_EP_DE/getSubItem?expenseItem='+expenseItem,
			cache : false,
			async : false,
			dataType : 'json',
			success : function(data) {
				if(!data.isException){
					// 先删除除了第一个选择框以外的
					$(str).nextAll('select').remove();
					if (data.hasSub) {
						// 如果有子项目
						var subItem = data.subExpenseList;
						var subItemStr = '<select class="select_box" onchange="changeExpenseType(this)" style="width:120px">';
						for (var i = 0 ; i< subItem.length; i++) {
							subItemStr += '<option value="'+subItem[i].expenseCode +','+subItem[i].timeMethod +'">'+subItem[i].expenseName+'</option>';
						}
						subItemStr += '</select>';
						$(str).parent().append(subItemStr);
						// 日期格式的变更
						showDatePick($(str).next('select'));
					} else {
						// 没有子项目
						showDatePick(str);
					}
				}
			},
			error : function(data) {
				
			}
		});	
	
	//如果选择的是S025的话。给一个提示
	var amountInput = $(str).parent().parent().children("td").eq(3).find("input")[0];
	if (expenseItem == "S025") {
		$(amountInput).val("");
		$(amountInput).parent().append(inputTemp);
		$(amountInput).css("width","50");
	} else {
		$(amountInput).nextAll('input').remove();
		$(amountInput).nextAll('span').remove();
		$(amountInput).css("width","130");
	}
}

// 根据计费方式来展现日期控件
function showDatePick(str){
	var value = $(str).val();
	var timeMethod = value.split(",")[1];
	var needHiddenInput = $(str).parent().parent().children("td").eq(1).find("input")[0];
	var needHiddenLabel = $(str).parent().parent().children("td").eq(1).find("label")[0];
	
	if (timeMethod == "1"){
		// 以天计费
		$(needHiddenInput).attr("type","hidden");
		$(needHiddenLabel).css("display","none");
		var needChangeToDay = $(str).parent().parent().children("td").eq(1).find("input")[1];
		needChangeToDay.value = "";
		datePickerToDay(needChangeToDay);
	} else if (timeMethod == "2") {
		// 以时间段计费
		$(needHiddenInput).attr("type","text");
		$(needHiddenLabel).css("display","");
		$(needHiddenInput).val("");
		$(needHiddenLabel).val("");
		var needChangeToDay = $(str).parent().parent().children("td").eq(1).find("input")[1];
		needChangeToDay.value = "";
		datePickerToDay(needChangeToDay);
	} else if (timeMethod == "3") {
		// 以月计费
		$(needHiddenInput).attr("type","hidden");
		$(needHiddenLabel).css("display","none");
		// 将当前的日期空间变成月份显示
		var needChangeToMonth = $(str).parent().parent().children("td").eq(1).find("input")[1];
		needChangeToMonth.value = needChangeToMonth.value.substring(0,7);
		datePickerToMonth(needChangeToMonth);
	}
}

var I0011 = '<fmt:message key="I0011" />';
function cancelToPurpose(){
	var form = document.getElementById("seEcEpDeBean");
	if (isFormEdited(form) || $('#hasChanged').val() == "1"){
		if (!confirm(I0011)) {
			return;
		}
	}
	// 返回申请画面
	var targetForm = document.forms[0];
	targetForm.action = "${pageContext.request.contextPath}/SE_EC_EP_DE/detailBackToClaim";
	targetForm.method = "POST";
	targetForm.submit();
}


var I0016 = '<fmt:message key="I0016" />';
function addDetailData(){
	if (!validateForm()) return;
	var rows = $("#detailRow tr:not(:first)");
	if (rows.length == 1) {
		alert(E0010);
		return;
	}
	// 以JSON的格式往后台传值
	var purposeNo = $("#purposeNo").val();
	
	var purpose = $("#purpose").val();

	var jsonData = [];
	for (var i = 0; i < rows.length;i++){
		if ($(rows[i]).attr('id') == "hiddenTr") continue;
		// 当前detailNo
		var detailNo = $(rows[i]).children("td").eq(4).find("input")[2].value;
		// 当前的expenseItem
		var expenseType = $(rows[i]).children("td").eq(0).children("select").last().val();
		if (expenseType == "") {
			var message = E0008.replace("{0}", '<fmt:message key="se_ec_ep_de_deExType" />')
			alert(message);
			return;
		}
		
		// 当前的时间这里直接到后台去判断
		if (!checkExpenseDate(expenseType, rows[i])) return;
		var dateFrom = $(rows[i]).children("td").eq(1).find("input")[0].value;
		var dateTo = $(rows[i]).children("td").eq(1).find("input")[1].value;
		
		// Location
		var location = $(rows[i]).children("td").eq(2).find("input")[0].value;
		if (location == "") {
			var message = E0008.replace("{0}", '<fmt:message key="se_ec_ep_de_delocation" />')
			alert(message);
			return;
		}
		
		// amount
		var amount = $(rows[i]).children("td").eq(3).find("input")[0].value;
		if (amount == "") {
			var message = E0008.replace("{0}", '<fmt:message key="se_ec_ep_de_deamount" />')
			alert(message);
			return;
		}
		
		// comment
		var comment = $(rows[i]).children("td").eq(4).find("input")[0].value;
		
		// 如果是娱乐费用的话备注必须有
		if (expenseType.indexOf("S011") != -1 && comment == ""){
			var message = '<fmt:message key="E0013" />'
			alert(message);
			return;
		}
		
		// 以月计费项目不可以重复CHECK
		if (checkMonthExpense()) {
			alert(E0002);
			return;
		}
		
		var rowDate = {
				purposeNo : purposeNo,
				purpose : purpose,
				detailNo : detailNo,
				expenseType : expenseType,
				dateFrom : dateFrom,
				dateTo : dateTo,
				location : location,
				amount : amount,
				comment : comment
		};
		
		jsonData.push(rowDate);
	}
	// 开始AJAX
	$.ajax({
		type : "POST",
		contentType:'application/json',
		url : '${pageContext.request.contextPath}/SE_EC_EP_DE/saveSession',
		dataType : "json",
		data : JSON.stringify(jsonData), 
		success : function(data) {
			if(!data.isException) {
				// 迁移至申请画面
				var targetform = document.forms[0];
	         	targetform.action = "${pageContext.request.contextPath}/SE_EC_EP_DE/submitDetail";
				targetform.method = "POST";
				targetform.submit();	
			} else {
				// 检测不通过，申请操作上限
			}
		},
		error : function(data) {
		}
	});
}

function checkExpenseDate(expensType, rowObject) {
	var timeMethod = expensType.split(",")[1];
	var dateFrom = $(rowObject).children("td").eq(1).find("input")[0].value;
	var dateTo = $(rowObject).children("td").eq(1).find("input")[1].value;
	var message = E0008.replace("{0}", '<fmt:message key="se_ec_ep_de_deDay" />')
	if (timeMethod == 1) {
		// 天计费
		if (dateTo == "") {
			alert(message);
			return false;
		}
	} else if (timeMethod == 2) {
		if (dateFrom == "" || dateTo == "") {
			alert(message);
			return false;
		}
		// 时间段计费
	} else if (timeMethod == 3) {
		// 月计费
		if (dateTo == "") {
			alert(message);
			return false;
		}
	}
	return true;
}

// 以月来计费的项目一个月只能申请一次
function checkMonthExpense(){
	var hasSameExpense = false;
	var rows = $("#detailRow tr:not(:first)");
	for (var i = 0; i < rows.length;i++){
		if ($(rows[i]).attr('id') == "hiddenTr") continue;
		// 报销项目
		var expenseType = $(rows[i]).children("td").eq(0).find("select")[0].value;
		// 报销月份
		var dateFrom = $(rows[i]).children("td").eq(1).find("input")[1].value;
			
		if (expenseType.split(",")[1] == 3) {
			var rowsCheck = $("#detailRow tr:not(:first)");
			for (var j = 0; j < rowsCheck.length;j++){
				if ($(rowsCheck[j]).attr('id') == "hiddenTr") continue;
				
				// 报销项目
				var expenseTypeCheck = $(rowsCheck[j]).children("td").eq(0).find("select")[0].value;
				// 报销月份
				var dateFromCheck = $(rowsCheck[j]).children("td").eq(1).find("input")[1].value;
				
				if (i != j && expenseType == expenseTypeCheck && dateFrom == dateFromCheck) {
					hasSameExpense = true;
					break;
				}
			}
			if (hasSameExpense) {
				break;
			}
		}
	}
	return hasSameExpense;
}

var errorImg = '<img src="${pageContext.request.contextPath}/images/error.png"/>';
function validateForm(){
	clearError();
	var notError = true;
	// 出差目的
	var purpose = $("#purpose").val();
	if (purpose == ""){
		var message = E0008.replace("{0}", '<fmt:message key="se_ec_ep_de_msg1" />')
		$("#purposeError").append("<label>"+message+"</label>");
		notError = false;
	}

	return notError;
}

function clearError(){
	$("#purposeError").html(errorImg);
}
</script>
</head>

<body onload="init();initializeSelectOneForJs();">
<form:form id="seEcEpDeBean" modelAttribute="seEcEpDeBean" commandName="seEcEpDeBean">
	<form:hidden path="hasChanged"/>
	<div class="position_none_boots"><fmt:message key="se_ec_ep_de_position" /></div>
    
    
	<div class="list_main_box">
   	  <div class="list_title"><fmt:message key="se_ec_ep_de_title" /></div> 
        <div class="list_box1">
        	<table border="0" cellpadding="0" cellspacing="0">
            	<tbody>
                    <tr>
                    	<td class="td_font"><fmt:message key="se_ec_ep_de_purpose" /></td>
                    	<td colspan="3" class="td_box">
                           <form:input path="purpose" cssClass="purpose_box"/>
                           <span id="purposeError" class="error" onmouseover="makeMesDiv(this)" onmouseout="removeMesDiv()">
							<img src="<c:url value='/images/error.png'/>"/>
							</span>
                        </td>
                    </tr>
                    
                </tbody>
            </table>
        </div>
        <div class="list_box3">
        
        	<table border="0" cellpadding="0" cellspacing="0" style="min-width:1125px">
            	<tbody id="detailRow">
                	<tr class="td_title">
                		<td width="270px"><fmt:message key="se_ec_ep_de_deExType" /></td>
                    	<td width="245px"><fmt:message key="se_ec_ep_de_deDay" /></td>
                        <td width="180px"><fmt:message key="se_ec_ep_de_delocation" /></td>
                        <td width="180px"><fmt:message key="se_ec_ep_de_deamount" /></td>
                        <td width="190px"><fmt:message key="se_ec_ep_de_decomm" /></td>
                        <td width="72px">&nbsp;</td>
                    </tr>
                    <c:forEach var="beanList" items="${ seEcEpDeBean.beanList }" varStatus="status">
                    <tr height="40px" <c:if test="${beanList.rejectErrorFlg == '1'}">class="errorTr"</c:if>>
                    	<td style="text-align:left;padding-left:5px;">
                    		<select class="select_box" onchange="changeExpenseType(this)" style="width:120px">
                    			<option value=""></option>
	                   			<c:forEach var="seList" items="${ expenseItemList }">
	                   				<option value="${ seList.expenseCode},${ seList.timeMethod}">${ seList.expenseName }</option>
	                   			</c:forEach>
                            </select>
                    	</td>
                        <td style="text-align:left;padding-left:10px;">
                        	<input class="inputDate input_little_box" value="${beanList.dayFrom }" id="datePick_${ status.index}">
                        	<label>~</label>
                        	<input class="inputDate input_little_box" value="${beanList.dayTo }">
                        </td>
                        <td>
                    		<input class="input_date_box" value="${beanList.location }" style="width:170px">
                    	</td>
                        <td>
                        	<input class="input_date_box" style="width:130px;text-align:right" value="${beanList.expenseAmount }" name="expenseAmount" onblur="checkAmount(this)" >
                        </td>
                        <td>
                        	<input class="input_date_box" value="${beanList.comments }">
                        	<input type="hidden" value="${beanList.expenseType},${ beanList.timeMethod}" name="expenseType" />
                        	<input type="hidden" value="${beanList.no }"/>
                        </td>
                        <td style="text-align:center">
                        	<c:if test="${ seEcEpDeBean.appStatus == 0 || seEcEpDeBean.appStatus == 3 }">
                        	<a href="#" onclick="deleteRow(this)">Delete</a>
                        	</c:if>
                        </td>
                    </tr>
                    </c:forEach>
                    <tr height="40px" style="display:none" id="hiddenTr">
                    	<td style="text-align:left;padding-left:5px;">
                    		<select class="select_box" onchange="changeExpenseType(this)" style="width:120px">
                    			<option value=""></option>
	                   			<c:forEach var="seList" items="${ expenseItemList }">
	                   				<option value="${ seList.expenseCode},${ seList.timeMethod}">${ seList.expenseName }</option>
	                   			</c:forEach>
                            </select>
                    	</td>
                        <td style="text-align:left;padding-left:10px;">
                        	<input class="inputDate input_little_box" value="">
                        	<label>~</label>
                        	<input class="inputDate input_little_box" value="">
                        </td>
                        <td>
                    		<input class="input_date_box" value="" style="width:170px">
                    	</td>
                        <td>
                        	<input class="input_date_box" style="width:130px;text-align:right" value="" name="expenseAmount" onblur="checkAmount(this)">
                        </td>
                        <td>
                        	<input class="input_date_box" value="">
                        	<input type="hidden" value="" name="expenseType" id="" />
                        	<input type="hidden" value=""/>
                        </td>
                        <td style="text-align:center">
                        	<wa:showAtag id="SE_EC_EP_DE_DeleteItem" onclick="deleteRow(this)" value="Delete"></wa:showAtag>
                        </td>
                    </tr>
                </tbody>
            </table>
            
            
            
            
          
      </div>
      <table class="totalDiv">
            	<tr>
            		<td style="width:730px;text-align:right">
            			<label class="total"><fmt:message key="common_totalCNY" /></label>&nbsp;
            		</td>
            		<td style="text-align:right;padding-right:275px">
            			<label id="totalAmount" class="amount">0.00</label>
            		</td>
            	</tr>
      </table>
      <div class="list_btn_box">
		<c:if test="${ seEcEpDeBean.appStatus == 0 || seEcEpDeBean.appStatus == 3 }">
			<div class="addrow_btn">
				<wa:showAtag id="SE_EC_EP_DE_AddItem" onclick="addRow()" innerImg="/images/addItem.png"></wa:showAtag>
			</div>
		</c:if>
		<c:if test="${ seEcEpDeBean.appStatus == 0 || seEcEpDeBean.appStatus == 3 }">
			<div class="finish_btn">
				<wa:showAtag id="SE_EC_EP_DE_Finish" onclick="addDetailData()" innerImg="/images/finish_btn.png"></wa:showAtag>
			</div>
		</c:if>
		<div class="cancel_btn"><a href="#" onclick="cancelToPurpose()"><img src="<c:url value='/images/cancel_btn.png'/>"/></a></div>
		<div class="clear"></div>
     </div>
    </div>
    <form:hidden path="purposeNo"/>
    <input type="hidden" value="${ seEcEpDeBean.appStatus }" id="hiddenStatus"/>
    <input type="hidden" value="${ CARRATE }" id="hiddenCarCate"/>
    
</form:form>
</body>
</html>
