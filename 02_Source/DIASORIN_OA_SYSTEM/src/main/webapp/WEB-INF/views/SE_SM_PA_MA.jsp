<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
<link href="<c:url value='/css/list.css' />" type="text/css" rel="stylesheet" />

<link href="<c:url value='/bootstrap/css/bootstrap-3.0.3.min.css' />" type="text/css" rel="stylesheet" />
<link href="<c:url value='/css/cover.css' />" type="text/css" rel="stylesheet" />

<script src="<c:url value='/bootstrap/js/jquery-2.0.3.min.js' />" type="text/javascript"></script>
<script src="<c:url value='/bootstrap/js/bootstrap-3.0.3.min.js' />" type="text/javascript"></script>
<script>

var I0006 = '<fmt:message key="I0006" />';
function addTr(){
	var targetform = document.forms[0];
	targetform.action = "${pageContext.request.contextPath}/SE_SM_PA_MA/toAdd?tabInfo=1";
	targetform.method = "POST";
	targetform.submit();
}

function addEl(){
	var targetform = document.forms[0];
	targetform.action = "${pageContext.request.contextPath}/SE_SM_PA_MA/toAdd?tabInfo=2";
	targetform.method = "POST";
	targetform.submit();
}

function addEi(){
	var targetform = document.forms[0];
	targetform.action = "${pageContext.request.contextPath}/SE_SM_PA_MA/toAdd?tabInfo=3";
	targetform.method = "POST";
	targetform.submit();
}

function addCc(){
	var targetform = document.forms[0];
	targetform.action = "${pageContext.request.contextPath}/SE_SM_PA_MA/toAdd?tabInfo=4";
	targetform.method = "POST";
	targetform.submit();
}

function updateTr(str){
	var targetform = document.forms[0];
	targetform.action = "${pageContext.request.contextPath}/SE_SM_PA_MA/toUpdate?tabInfo=1&code="+str;
	targetform.method = "POST";
	targetform.submit();
}

function updateEl(str){
	var targetform = document.forms[0];
	targetform.action = "${pageContext.request.contextPath}/SE_SM_PA_MA/toUpdate?tabInfo=2&code="+str;
	targetform.method = "POST";
	targetform.submit();
}

function updateEi(str){
	var targetform = document.forms[0];
	targetform.action = "${pageContext.request.contextPath}/SE_SM_PA_MA/toUpdate?tabInfo=3&code="+str;
	targetform.method = "POST";
	targetform.submit();
}

function updateCc(str){
	var targetform = document.forms[0];
	targetform.action = "${pageContext.request.contextPath}/SE_SM_PA_MA/toUpdate?tabInfo=4&code="+str;
	targetform.method = "POST";
	targetform.submit();
}

function deleteTr(str){
	if (!confirm(I0006)){
		return;
	}
	var targetform = document.forms[0];
	targetform.action = "${pageContext.request.contextPath}/SE_SM_PA_MA/toDelete?tabInfo=1&codeNo="+str;
	targetform.method = "POST";
	targetform.submit();
}

function deleteEl(str){
	if (!confirm(I0006)){
		return;
	}
	var targetform = document.forms[0];
	targetform.action = "${pageContext.request.contextPath}/SE_SM_PA_MA/toDelete?tabInfo=2&codeNo="+str;
	targetform.method = "POST";
	targetform.submit();
}

function deleteEi(str){
	if (!confirm(I0006)){
		return;
	}
	var targetform = document.forms[0];
	targetform.action = "${pageContext.request.contextPath}/SE_SM_PA_MA/toDelete?tabInfo=3&codeNo="+str;
	targetform.method = "POST";
	targetform.submit();
}

function deleteCc(str){
	if (!confirm(I0006)){
		return;
	}
	var targetform = document.forms[0];
	targetform.action = "${pageContext.request.contextPath}/SE_SM_PA_MA/toDelete?tabInfo=4&codeNo="+str;
	targetform.method = "POST";
	targetform.submit();
}

function add(){
	if ($("#show1").css("display") == "block"){
		addTr();
	} else if ($("#show2").css("display") == "block"){
		addEl();
	} else if ($("#show3").css("display") == "block"){
		addEi();
	} else if ($("#show4").css("display") == "block"){
		addCc();
	} 
}

</script>

</head>
<body>
<form:form id="seSmPaMaBean" modelAttribute="seSmPaMaBean" commandName="seSmPaMaBean">
	<input type="hidden" id="hdTabInfo" value="${ tabInfo }"/>
	<div class="position"><fmt:message key="se_sm_pa_ma_position" /></div>
    
    
	<div class="list_main_box">
   	  <div class="list_title"><fmt:message key="se_sm_pa_ma_title" /></div> 
      	<input type="hidden" id="hidshow" name="hidshow" />
      	<ul id="myTab" class="nav nav-tabs">
		   <li><a href="#show2" data-toggle="tab"><fmt:message key="se_sm_pa_ma_tab1" /></a></li>
		   <li><a href="#show3" data-toggle="tab"><fmt:message key="se_sm_pa_ma_tab2" /></a></li>
		   <li><a href="#show4" data-toggle="tab"><fmt:message key="se_sm_pa_ma_tab3" /></a></li>
		</ul>
		      	
        <div class="list_box1" style="margin:5px 0px 5px 0px">
        	<table border="0" cellpadding="0" cellspacing="0">
            	<tbody>
                    <tr>
                   	  <td><div class="add_details_left_btn"><a href="#" onclick="javascript:add();"><img src="<c:url value='/images/new_btn.png'/>"/></a></div></td>
                    </tr>
                    
                </tbody>
            </table>
        </div>
      	<div id="myTabContent" class="tab-content">
        <div class="list_box4 tab-pane fade" id="show2">
			<table border="0" cellpadding="0" cellspacing="0">
				<tbody>
                	<tr class="td_title">
                    	<td><fmt:message key="se_sm_pa_ma_lecode" /></td>
                        <td><fmt:message key="se_sm_pa_ma_lename" /></td>
                        <td class="td_width">&nbsp;</td>
                    </tr>
                    
                    <c:forEach var="elBean" items="${ seSmPaMaBean.elBeanList }">
					  <tr>
						 <td>${ elBean.levelCode }</td>
						 <td>${ elBean.levelName }</td>
						 <td>
							<a href="#" onclick="updateEl('${ elBean.levelCode }')"><fmt:message key="common_modify" /></a>
<%-- 							<a href="#" onclick="deleteEl('${ elBean.no }')"><fmt:message key="common_delete" /></a> --%>
							<wa:showAtag id="SE_SM_PA_DE_LevelDelete" onclick="deleteEl('${ elBean.no }')" value="删除"></wa:showAtag>
						 </td>
					  </tr>
				  	</c:forEach>
                    
                </tbody>
			</table>
      	</div>
      
      	<div class="list_box4 tab-pane fade" id="show3">
			<table border="0" cellpadding="0" cellspacing="0">
				<tbody>
                
                	<tr class="td_title">
                    	<td><fmt:message key="se_sm_pa_ma_exicode" /></td>
                        <td><fmt:message key="se_sm_pa_ma_exiname" /></td>
                        <td><fmt:message key="se_sm_pa_ma_exifname" /></td>
                        <td class="td_width">&nbsp;</td>
                    </tr>
                    
                    <c:forEach var="eiBean" items="${ seSmPaMaBean.eiBeanList }">
					  <tr>
						 <td>${ eiBean.expenseCode }</td>
						 <td>${ eiBean.expenseName }</td>
						 <td>${ eiBean.fatherExpenseCode }</td>
						 <td>
							<a href="#" onclick="updateEi('${ eiBean.expenseCode }')"><fmt:message key="common_modify" /></a>
<%-- 							<a href="#" onclick="deleteEi('${ eiBean.no }')"><fmt:message key="common_delete" /></a> --%>
							<wa:showAtag id="SE_SM_PA_DE_ExpenseDelete" onclick="deleteEi('${ eiBean.no }')" value="删除"></wa:showAtag>
						 </td>
					  </tr>
				  	</c:forEach>
                    
                </tbody>
			</table>
      </div>
      
      	
        <div class="list_box4 tab-pane fade" id="show4">
			<table border="0" cellpadding="0" cellspacing="0">
				<tbody>
                
                	<tr class="td_title">
                    	<td><fmt:message key="se_sm_pa_ma_costcencode" /></td>
                        <td><fmt:message key="se_sm_pa_ma_costcenname" /></td>
                        <td class="td_width">&nbsp;</td>
                    </tr>
                    
                    <c:forEach var="ccBean" items="${ seSmPaMaBean.ccBeanList }">
					  <tr>
						 <td>${ ccBean.costCenterCode }</td>
						 <td>${ ccBean.costCenterName }</td>
						 <td>
							<a href="#" onclick="updateCc('${ ccBean.costCenterCode }')"><fmt:message key="common_modify" /></a>
<%-- 							<a href="#" onclick="deleteCc('${ ccBean.no }')"><fmt:message key="common_delete" /></a> --%>
							<wa:showAtag id="SE_SM_PA_DE_CostCenterDelete" onclick="deleteCc('${ ccBean.no }')" value="删除"></wa:showAtag>
						 </td>
					  </tr>
				  	</c:forEach>
                    
                </tbody>
			</table>
      </div>
      </div>
    </div>
</form:form>
</body>
<script>
	$(function () {
		var tabInfo = $("#hdTabInfo").val();
		if (tabInfo != null && tabInfo != "") {
			var tabNumber = tabInfo - 2;
			$('#myTab li:eq('+tabNumber+') a').tab('show');
		} else {
			$('#myTab li:eq(0) a').tab('show');
		}
	});
</script>
</html>
