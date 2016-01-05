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

<link href="<c:url value='/bootstrap/css/bootstrap-3.0.3.min.css' />" type="text/css" rel="stylesheet" />
<link href="<c:url value='/bootstrap/css/prettify.css' />" type="text/css" rel="stylesheet" />
<link href="<c:url value='/bootstrap/css/bootstrap-datepicker.css' />" type="text/css" rel="stylesheet" />
<link href="<c:url value='/css/cover.css' />" type="text/css" rel="stylesheet" />
<link href="<c:url value='/css/user-common.css' />" type="text/css" rel="stylesheet" />

<script src="<c:url value='/bootstrap/js/jquery-2.0.3.min.js' />" type="text/javascript"></script>
<script src="<c:url value='/bootstrap/js/bootstrap-3.0.3.min.js' />" type="text/javascript"></script>
<script src="<c:url value='/bootstrap/js/prettify.js' />" type="text/javascript"></script>
<script src="<c:url value='/bootstrap/js/bootstrap-datepicker.js' />" type="text/javascript"></script>
<script src="<c:url value='/js/user-common.js' />" type="text/javascript"></script>

<script>
var E0007 = '<fmt:message key="E0007" />';
$(document).ready(function() {
    
    $('.inputDate').datepicker({
    	format: "yyyy/mm/dd",
        clearBtn: true,
        orientation: "top right",
        autoclose: true,
        todayHighlight: true
    }); 
});

function expenseChaim(){
	var targetForm = document.forms[0];
	targetForm.action = "${pageContext.request.contextPath}/SE_EC_EP_CA/init";
	targetForm.method = "POST";
	targetForm.submit();
}

function expenseSearch(){
	if (!validateForm()) return;
	$("#hiddenstatus").val($("#status").val());
	var targetForm = document.forms[0];
	targetForm.action = "${pageContext.request.contextPath}/SE_EC_EP_LS/search";
	targetForm.method = "POST";
	targetForm.submit();
}

// 分页操作
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
	targetForm.action = "${pageContext.request.contextPath}/SE_EC_EP_LS/page?curPage="+curPage;
	targetForm.method = "POST";
	targetForm.submit();
}

function toDetail(str){
	var targetForm = document.forms[0];
	targetForm.action = "${pageContext.request.contextPath}/SE_EC_EP_LS/toDetail?expenseAppNo="+str;
	targetForm.method = "POST";
	targetForm.submit();
}

function toExport(str) {
	var targetForm = document.forms[0];
	targetForm.action = "${pageContext.request.contextPath}/SE_AC_AP_DE/toExportFromList?expenseAppNo="+str;
	targetForm.method = "POST";
	targetForm.submit();
}
var errorImg = '<img src="${pageContext.request.contextPath}/images/error.png"/>';
function validateForm(){
	clearError();
	var notError = true;
	var claimDateFrom  = $("#chaimDateFrom").val();
	var claimDateTo = $("#chaimDateTo").val();
	if (claimDateFrom != "" && claimDateTo != "") {
		if (claimDateFrom > claimDateTo) {
			var message = E0007.replace("{0}", '<fmt:message key="se_ec_ep_ls_msg1" />')
			$("#claimDateError").append("<label>"+message+"</label>");
			notError = false;
		}
		
	}
	return notError;
}

function clearError(){
	
	$("#claimDateError").html(errorImg);
}

</script>
</head>

<body>
<form:form id="seEcEpLsBean" modelAttribute="seEcEpLsBean" commandName="seEcEpLsBean">
	<div class="position_none_boots"><fmt:message key="se_ec_ep_ls_position" /></div>
 		
	<div class="list_main_box">
   	  <div class="list_title"><fmt:message key="se_ec_ep_ls_title" /></div> 
        <div class="list_box1">
        	<table border="0" cellpadding="0" cellspacing="0">
            	<tbody>
                	<tr>
                    	<td class="td_font"><fmt:message key="se_ec_ep_ls_tlt" /></td>
                    	<td class="td_box" width="390px">
                          	<form:select path="travelLocationType" cssClass="select_box" style="width:250px">
                   				<form:option value=""></form:option>
                    			<c:forEach var="seList" items="${ TravelTypeSelect }">
                    				<form:option value="${ seList.key }">${ seList.value }</form:option>
                    			</c:forEach>
                    		</form:select>
                        </td>
                        <td class="td_font"></td>
                        <td class="td_box" width="390px">
                        </td>
                    </tr>
                    
                    <tr>
                    	<td class="td_font"><fmt:message key="se_ec_ep_ls_cd" /></td>
                    	<td class="td_box" width="405px">
                            <form:input id="chaimDateFrom" path="chaimDateFrom" cssClass="inputDate input_date_box"/>
                            ~
                            <form:input id="chaimDateTo" path="chaimDateTo" cssClass="inputDate input_date_box"/>
                            <span id="claimDateError" class="error" onmouseover="makeMesDiv(this)" onmouseout="removeMesDiv()">
							<img src="<c:url value='/images/error.png'/>"/>
							</span> 
                        </td>
                        <td class="td_font"><fmt:message key="se_ec_ep_ls_status" /></td>
                        <td class="td_box"> 
                   			<form:select path="status" cssClass="select_box">
                   			<form:option value=""></form:option>
	                   			<c:forEach var="seList" items="${ StatusSelect }">
	                   				<form:option value="${ seList.key }">${ seList.value }</form:option>
	                   			</c:forEach>
                   			</form:select>
                        </td>
                    </tr>
                    
                    <tr>
                        <td class="td_box" colspan="4">
                        	<div class="add_details_center_btn">
                        		<wa:showAtag id="SE_EC_EP_LS_Search" onclick="expenseSearch()" innerImg="/images/search_btn.png"></wa:showAtag>
                        	</div>
                        </td>
                    </tr>
                    <tr>
                   	  <td colspan="2">
                   	  	<div class="add_details_left_btn">
                   	  		<wa:showAtag id="SE_EC_EP_LS_NewClaim" onclick="expenseChaim()" innerImg="/images/new_claim_btn.png"></wa:showAtag>
                   	  	</div>
                   	  </td>
                   	  <td></td>
                        <td class="td_box">
                        	</td>
                    </tr>
                    
                </tbody>
            </table>
        </div>
        <div class="list_box2">
			<table border="0" cellpadding="0" cellspacing="0" style="width:1215px">
				<tbody>
                	<tr class="td_title">
                    	<td width="35px"><fmt:message key="se_ec_ep_ls_deNo" /></td>
                    	<td width="120px"><fmt:message key="se_ec_ep_ls_deAppNo" /></td>
                        <td width="210px"><fmt:message key="se_ec_ep_ls_detlt" /></td>
                        <td width="90px"><fmt:message key="se_ec_ep_ls_decd" /></td>
                        <td width="210px"><fmt:message key="se_ec_ep_ls_detr" /></td>
                        <td width="40px" style="border-right:0px"></td>
                        <td width="70px" style="text-align:left;"><fmt:message key="se_ec_ep_ls_detotal" /></td>
                        <td width="120px"><fmt:message key="se_ec_ep_ls_destatus" /></td>
                        <td width="130px"><fmt:message key="se_ec_ep_ls_nextStep" /></td>
                        <td width="150px"></td>
                    </tr>
                    <c:forEach var="beanList" items="${ seEcEpLsBean.beanList }">
                    <tr>
                    	<td>${ beanList.no }</td>
                    	<td>${ beanList.expensesAppNo }</td>
                        <td>${ beanList.tavelLocationType }</td>
                        <td>${ beanList.applicationDate }</td>
                        <td style="text-align:left;">${ beanList.travelReason }</td>
                        <td style="border-right:0px">CNY</td>
                        <td style="text-align:right;">${ beanList.expenseSum }</td>
                        <td>${ beanList.statusContent }</td>
                        <td>${ beanList.nextAprrovePerson }</td>
                        <td>
                        	<a href="#" onclick="toDetail('${ beanList.expensesAppNo }')">Details</a>
                        	<a href="#" onclick="toExport('${ beanList.expensesAppNo }')">Export</a>
                        </td>
                    </tr>
                    </c:forEach>
            		<c:if test='${ listCount == "0" && searched == "1" }'>
            			<tr>
            				<td colspan="9">
            					<span class="noDateMsg"><fmt:message key="E0009"/></span>
            				</td>
            			</tr>
            		</c:if>
                </tbody>
			</table>
           <c:if test='${ listCount != "0" }'>
            <div class="list_pages_box">
           	  <div class="pages">
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
    </div>
    </form:form>
</body>
</html>
