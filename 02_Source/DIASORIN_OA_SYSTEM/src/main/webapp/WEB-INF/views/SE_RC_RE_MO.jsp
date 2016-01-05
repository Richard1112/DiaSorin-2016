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

<script src="<c:url value='/bootstrap/js/jquery-2.0.3.min.js' />" type="text/javascript"></script>
<script src="<c:url value='/bootstrap/js/bootstrap-3.0.3.min.js' />" type="text/javascript"></script>
<script src="<c:url value='/bootstrap/js/bootstrap-multiselect.js' />" type="text/javascript"></script>
<script src="<c:url value='/bootstrap/js/prettify.js' />" type="text/javascript"></script>
<script src="<c:url value='/bootstrap/js/bootstrap-datepicker.js' />" type="text/javascript"></script>

<script src="<c:url value='/js/commonFunction.js' />" type="text/javascript"></script>
<script>
var I0005 = '<fmt:message key="I0005" />';
$(document).ready(function() {

    $('.inputDate').datepicker({
        format: "yyyy/mm/dd",
        clearBtn: true,
        orientation: "top right",
        minViewMode: 0,
        autoclose: true,
        todayHighlight: true
    }); 

});
function toExport(){
	var targetform = document.forms[0];
	targetform.action = "${pageContext.request.contextPath}/SE_RC_RE_MO/export";
	targetform.method = "POST";
	targetform.submit();
}

function searchSap(){
	var targetform = document.forms[0];
	targetform.action = "${pageContext.request.contextPath}/SE_RC_RE_MO/search";
	targetform.method = "POST";
	targetform.submit();
}

//分页操作
function Action(action) {
	//当前页 
	var curPage=parseInt($("#curPage").val());
	//总页数 
	var totalPage=parseInt($("#totalPage").val());
	//输入页数 
	var pageNum =parseInt($("#pageNum").val());
	
	if(action == "NEXT"){
		if(curPage < totalPage){
			curPage = curPage + 1;
		}
	}
	if(action == "PREV"){
		if(curPage >= 2){				
			curPage = curPage-1;
		}
	}
	if(action == "GO"){	
		if(pageNum>=1 && pageNum <=totalPage){
			curPage = pageNum;
		}
	}
	
	var targetForm = document.forms[0];
	targetForm.action = "${pageContext.request.contextPath}/SE_RC_RE_MO/page?curPage="+curPage;
	targetForm.method = "POST";
	targetForm.submit();
}

function clickAll(){
	var details = document.getElementsByName("detailCheck");
	if (details.length == 0){
		document.getElementById("checkAll").checked = false;
		return;
	}
	if (document.getElementById("checkAll").checked) {
		allDetailCheckOrNot(true);
		$("#isAllChecked").val("1");
		// 恢复所有数据明细数据
		$("#detailId").val($("#hiddenDetailId").val());
		$("#selectAmount").text(fmoney($("#totalAmount").val(),2))
		$("#selectRow").text($("#totalCount").val());
		
		$("#hiddenSelectAmount").val($("#totalAmount").val());
		$("#hiddenSelectCount").val($("#totalCount").val());
	} else {
		allDetailCheckOrNot(false);
		$("#isAllChecked").val("0");
		// 清空所有保存的明细数据
		$("#detailId").val("");
		$("#selectAmount").text("0");
		$("#selectRow").text("0");
		
		$("#hiddenSelectAmount").val("0");
		$("#hiddenSelectCount").val(0);
		
	}
}

function allDetailCheckOrNot(isCheck){
	var details = document.getElementsByName("detailCheck");
	for (var i= 0; i< details.length;i++){
		details[i].checked = isCheck;
	}
}

function clickDetail(str){
	var detailId = $("#detailId").val();
	var selectAmount = $("#selectAmount").text().replace(",","");
	var selectRow = $("#selectRow").text();
	var curAmount = $(str).parent().parent().children("td").eq(4).text().replace(",","");
	if (str.checked) {
		detailId += str.value + ",";
		var result = parseFloat(selectAmount)+parseFloat(curAmount);
		$("#selectAmount").text(fmoney(result,2))
		$("#selectRow").text(parseFloat(selectRow) + 1);
		$("#hiddenSelectAmount").val(result);
		$("#hiddenSelectCount").val($("#selectRow").text());
	} else {
		detailId = detailId.replace(str.value + ",", "");
		var result = parseFloat(selectAmount)-parseFloat(curAmount);
		$("#selectAmount").text(fmoney(result,2));
		$("#selectRow").text(parseFloat(selectRow) - 1);
		$("#hiddenSelectAmount").val(result);
		$("#hiddenSelectCount").val($("#selectRow").text());
	}
	$("#detailId").val(detailId);
	// 这里判断是不是全部选中
	if ($("#selectRow").text() == $("#totalCount").val()) {
		document.getElementById("checkAll").checked = true;
		$("#isAllChecked").val("1");
	} else {
		document.getElementById("checkAll").checked = false;
		$("#isAllChecked").val("0");
	}
}

function init(){
	var isAllChecked = $("#isAllChecked").val();
	if (isAllChecked == "1") {
		// 全部选中
		document.getElementById("checkAll").checked = true;
		allDetailCheckOrNot(true);
	} else {
		// 在判断detailId来判断哪些选中
		var detailId = $("#detailId").val();
		var details = document.getElementsByName("detailCheck");
		for (var i= 0; i< details.length;i++){
			if (detailId.indexOf(details[i].value) != -1) {
				details[i].checked = true;
			} else {
				details[i].checked = false;
			}
			
		}
	}
}
</script>
</head>

<body onload="init()">
<form:form id="seRcReMoSapBean" modelAttribute="seRcReMoSapBean" commandName="seRcReMoSapBean">
	<div class="position"><fmt:message key="se_rc_re_mo_position" /></div>
    
    <form:hidden path="isAllChecked"/>
    <form:hidden path="detailId"/>
    <form:hidden path="totalAmount"/>
    <form:hidden path="totalCount"/>
    <form:hidden path="selectAmount" id="hiddenSelectAmount"/>
    <form:hidden path="selectCount" id="hiddenSelectCount"/>
    <form:hidden path="hiddenDetailId"/>
    
	<div class="list_main_box">
   	  <div class="list_title"><fmt:message key="se_rc_re_mo_title" /></div> 
        <div class="list_box1">
        	<table border="0" cellpadding="0" cellspacing="0">
            	<tbody>
                	<tr>
                    	<td class="td_font"><fmt:message key="se_rc_re_mo_status" /></td>
                    	<td class="td_box">
                            <form:select path="status" class="select_box">
                            	<form:option value="4">Approve</form:option>
                    		</form:select>
                        </td>
                        <td class="td_font"><fmt:message key="se_rc_re_mo_date" /></td>
                        <td class="td_box">
                        	<form:input id="dateFrom" path="dateFrom" cssClass="inputDate input_date_box"/>
                            ~
                            <form:input id="dateTo" path="dateTo" cssClass="inputDate input_date_box"/>
                        </td>
                    </tr>
                    <tr>
                      	<td colspan="4">
                        	<div class="add_details_right_btn">
                        		<wa:showAtag id="SE_RC_RE_MO_Search" onclick="searchSap()" innerImg="/images/search_btn.png"></wa:showAtag>
                        	</div>
                        </td>
                    </tr>
                    
                </tbody>
            </table>
        </div>
        <div class="list_box2">
			<table border="0" cellpadding="0" cellspacing="0">
				<tbody>
                	<tr class="td_title">
                    	<td width="45px"><input type="checkbox" onclick="clickAll()" id="checkAll"></td>
                        <td width="200px"><fmt:message key="se_rc_re_mo_costcenter" /></td>
                        <td width="150px"><fmt:message key="se_rc_re_mo_employeeNo" /></td>
                        <td width="150px"><fmt:message key="se_rc_re_mo_expensetype" /></td>
                        <td width="150px"><fmt:message key="se_rc_re_mo_amount" /></td>
                        <td width="300px"><fmt:message key="se_rc_re_mo_comments" /></td>
                    </tr>
                    
                    <c:forEach var="beanList" items="${ seRcReMoSapBean.beanList }">
					  <tr>
						 <td>
						 	<input type="checkbox" name="detailCheck" value="${beanList.expensesDetailsNo}" onclick="clickDetail(this)">
						 </td>
						 <td style="text-align:left">${beanList.costCenter }/${beanList.costCenterName }</td>
						 <td style="text-align:left">${beanList.employeeName}</td>
						 <td>${beanList.expenseType}</td>
						 <td style="text-align:right"><fmt:formatNumber pattern="#,#00.00#" value="${beanList.amount }"></fmt:formatNumber></td>
						 <td style="text-align:left">${beanList.text}</td>
					  </tr>
				  	</c:forEach>
				  	<c:if test='${ listCount == "0" && searched == "1" }'>
            			<tr>
            				<td colspan="6">
            					<span class="noDateMsg"><fmt:message key="E0009"/></span>
            				</td>
            			</tr>
            		</c:if>
                </tbody>
			</table>
			
			
      </div>
      <table class="totalDiv">
         	<tr>
         		<td style="text-align:right;width:200px">
         			<label class="total"><fmt:message key="se_rc_re_mo_totalrows" /></label>&nbsp;
         		</td>
         		<td style="text-align:left;width:200px">
         			<label id="selectRow" class="amount">${ seRcReMoSapBean.selectCount }</label>
         		</td>
         		<td style="text-align:right;width:240px">
         			<label class="total"><fmt:message key="se_rc_re_mo_totalamount" /></label>&nbsp;
         		</td>
         		<td style="text-align:right;width:130px">
         			<label id="selectAmount" class="amount"><fmt:formatNumber pattern="#,#00.00#" value="${ seRcReMoSapBean.selectAmount }" /></label>
         		</td>
         		<td style="text-align:right;width:315px">
         			&nbsp;
         		</td>
         	</tr>
      		</table>
      <c:if test='${ listCount != "0" }'>
			<div class="list_pages_box">
					<div style="float:left">
						<wa:showAtag id="SE_RC_RE_MO_Export" onclick="toExport(); return false" innerImg="/images/export.png"></wa:showAtag>
						</div>
					<div class="pages_min">
					<div class="pages_go_box"><a href="#" onclick="return Action('GO');" class="pages_go" ></a></div>
					<div class="pages_input_boots_box"><input class="pages_input_boots" type="text" id="pageNum" value="${ curPage }"/></div>
					<div class="pages_font">${ curPage }/${ totalPage }</div>
					</div>
				
					<input type="hidden" id="totalPage" value="${ totalPage }"/>
					<input type="hidden" id="curPage" value="${ curPage }"/>
					
					<div class="prev_btn"><a href="#" onclick="return Action('NEXT');"><img src="<c:url value='/images/next_btn.png'/>"/></a></div>
					<div class="prev_btn"><a href="#" onclick="return Action('PREV');"><img src="<c:url value='/images/prev_btn.png'/>"/></a></div>

			</div>
            </c:if>
    </div>
</form:form>
</body>
</html>
