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
<link href="<c:url value='/css/index.css' />" type="text/css" rel="stylesheet" />
<script src="<c:url value='/js/jquery.min.js' />" type="text/javascript"></script>

<script src="<c:url value='/js/commonFunction.js' />" type="text/javascript"></script>
<script>
function logout(){
	if (!getSunFrameDoc()) return;
	var targetform = document.forms[0];
	targetform.action = "${pageContext.request.contextPath}/SE_LG_UL/logout";
	targetform.method = "POST";
	targetform.submit();
}

function toUserList(){
	if (!getSunFrameDoc()) return;
	document.getElementById("iframe").src = "${pageContext.request.contextPath}/SE_EM_PE_LS/init";
}

function toRoleList(){
	if (!getSunFrameDoc()) return;
	document.getElementById("iframe").src = "${pageContext.request.contextPath}/SE_AM_RO_LS/init";
}

function toSystemSet(){
	if (!getSunFrameDoc()) return;
	document.getElementById("iframe").src = "${pageContext.request.contextPath}/SE_SM_PA_MA/init";
}

function toExpensesSet(){
	if (!getSunFrameDoc()) return;
	document.getElementById("iframe").src = "${pageContext.request.contextPath}/SE_SM_EP_MA/init";
}

function toExpensesList(){
	if (!getSunFrameDoc()) return;
	document.getElementById("iframe").src = "${pageContext.request.contextPath}/SE_EC_EP_LS/init";
}

function toApprovalList(){
	if (!getSunFrameDoc()) return;
	document.getElementById("iframe").src = "${pageContext.request.contextPath}/SE_AC_AP_LS/init";
}

function clickTree(str){
	$("."+str).each(function(){
		if($(this).css("display") == "none"){
			$(this).css("display","block");
		} else {
			$(this).css("display","none");
		}
		
	});
}

function todoList(){
	if (!getSunFrameDoc()) return;
	document.getElementById("iframe").src = "${pageContext.request.contextPath}/SE_LG_MN_IF/init";
}

function toExportByMonth(){
	if (!getSunFrameDoc()) return;
	document.getElementById("iframe").src = "${pageContext.request.contextPath}/SE_RC_RE_MO/init";
}

function toReportSearch(){
	if (!getSunFrameDoc()) return;
	document.getElementById("iframe").src = "${pageContext.request.contextPath}/SE_RC_RE_LS/init";
}

function toReportInSearch(){
	if (!getSunFrameDoc()) return;
	document.getElementById("iframe").src = "${pageContext.request.contextPath}/SE_RC_RE_IN/init";
}

function toAuthorityList(){
	if (!getSunFrameDoc()) return;
	document.getElementById("iframe").src = "${pageContext.request.contextPath}/SE_SY_AM_AL/init";
}

function init(){
	// 如果没有权限则项目不显示
	var ddArray = $("#index_main_left_box dd");
	for(var i = 0; i< ddArray.length;i++){
		if ($(ddArray[i]).find("a").length == 0) {
			$(ddArray[i]).remove();
		}
	}
}

var I0011 = '<fmt:message key="I0011" />'; 
function getSunFrameDoc(){
	var subFrame = window.frames["iframe"].document || window.frames["iframe"].contentDocument;
	var subForm = subFrame.forms[0];
	var formId = subForm.id;
	if (formId == "seAmRoDeBean" || 
		formId == "seSmPaMaUpBean" || 
		formId == "seSyAmAUBean") {
		// 其他需要判断是否有变更的画面
		if (isFormEdited(subForm)) {
			if (!confirm(I0011)) {
				return false;
			} else {
				return true;
			}
		}
	} else if (formId == "seEcEpDeBean" || formId == "seEcEpCaBean") {
		// 其他需要判断是否有变更的画面
		if (isFormEdited(subForm) || subFrame.getElementById("hasChanged").value == "1") {
			if (!confirm(I0011)) {
				return false;
			} else {
				return true;
			}
		}
	} else if (formId == "seEmPeDeAdBean") {
		// 其他需要判断是否有变更的画面
		if (isFormEdited(subForm) || subFrame.getElementById("updateChange").value == "1") {
			if (!confirm(I0011)) {
				return false;
			} else {
				return true;
			}
		}
	} else {
		// 不需要做check
		return true;
	}
	
	return true;
}


</script>
</head>
<body onload="init()">
<form:form>
	<header>
		<div id="index_top">
	    	<div id="index_logo"></div>
	        <div id="index_user">
	        	<a href="#"><img src="<c:url value='/images/index_user.png'/>"/></a>
	        	<a href="#" onclick="todoList()"><span>${userId}</span></a>
	            <a href="#" onclick="logout()"><img src="<c:url value='/images/sign_out.png'/>"/></a>
	        </div>
	        <div class="clear"></div>
	    </div>
	</header>
	<section>
    <div id="index_main_box">
    	<div id="index_main_left_box">
            <dl>
            <dt><a href="#" onclick="clickTree('ddExpense')"><img src="<c:url value='/images/expenses_ico.png'/>"/><fmt:message key="se_lg_mn_expenses" /></a></dt>
            <dd class="ddExpense">
            	<wa:showAtag id="SE_LG_MN_el" onclick="toExpensesList()" value="Expenses List"></wa:showAtag>
            </dd>
            </dl>
            <dl>
            <dt><a href="#" onclick="clickTree('ddApproval')"><img src="<c:url value='/images/approval_ico.png'/>"/><fmt:message key="se_lg_mn_approval" /></a></dt>
            <dd class="ddApproval">
            	<wa:showAtag id="SE_LG_MN_al" onclick="toApprovalList()" value="Approval List"></wa:showAtag>
            </dd>
            </dl>
            <dl>
            <dt><a href="#" onclick="clickTree('ddUser')"><img src="<c:url value='/images/approval_ico.png'/>"/><fmt:message key="se_lg_mn_user" /></a></dt>
            <dd class="ddUser">
            	<wa:showAtag id="SE_LG_MN_ul" onclick="toUserList()" value="User List"></wa:showAtag>
            </dd>
            </dl>
            <dl>
            <dt><a href="#" onclick="clickTree('ddRole')"><img src="<c:url value='/images/approval_ico.png'/>"/><fmt:message key="se_lg_mn_role" /></a></dt>
            <dd class="ddRole">
            	<wa:showAtag id="SE_LG_MN_rl" onclick="toRoleList()" value="Role List"></wa:showAtag>
            </dd>
            </dl>
            <dl>
            <dt><a href="#" onclick="clickTree('ddAuthority')"><img src="<c:url value='/images/approval_ico.png'/>"/><fmt:message key="se_lg_mn_authority" /></a></dt>
            <dd class="ddAuthority">
            	<wa:showAtag id="SE_LG_MN_pl" onclick="toAuthorityList()" value="Authority List"></wa:showAtag>
            </dd>
            </dl>
            <dl>
            <dt><a href="#" onclick="clickTree('ddReport')"><img src="<c:url value='/images/reports_ico.png'/>"/><fmt:message key="se_lg_mn_reports" /></a></dt>
            <dd class="ddReport">
            	<wa:showAtag id="SE_LG_MN_re_sap" onclick="toExportByMonth()" value="Reports For SAP"></wa:showAtag>
            </dd>
            <dd class="ddReport">
            	<wa:showAtag id="SE_LG_MN_re_in" onclick="toReportInSearch()" value="Reports For Summary"></wa:showAtag>
            </dd>
            </dl>
            <dl>
            <dt><a href="#" onclick="clickTree('ddSystem')"><img src="<c:url value='/images/system_ico.png'/>"/><fmt:message key="se_lg_mn_system" /></a></dt>
            <dd class="ddSystem">
            	<wa:showAtag id="SE_LG_MN_es" onclick="toExpensesSet()" value="Expenses Settings"></wa:showAtag>
            </dd>
            <dd class="ddSystem">
            	<wa:showAtag id="SE_LG_MN_ps" onclick="toSystemSet()" value="System Settings"></wa:showAtag>
            </dd>
            </dl>
    	</div>
                  
        <div id="index_main_right_box">
			<iframe style="margin-left:1px;" id="iframe" 
			marginwidth=0 marginheight=0 width=100% height="99%" 
			src="${pageContext.request.contextPath}/SE_LG_MN_IF/init" 
			frameborder=0 allowtransparency="ture"></iframe>
	 
        </div>

    	<div class="clear"></div>
        
    </div>
    </section>
    <footer>
    	<div id="index_bottom_box"><fmt:message key="se_lg_mn_footer" /></div>
	</footer>
</form:form>    
</body>
</html>
