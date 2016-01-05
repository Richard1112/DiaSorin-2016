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
</head>
<script>

function toApplication(str){
	var targetForm = document.forms[0];
	targetForm.action = "${pageContext.request.contextPath}/SE_EC_EP_LS/toDetail?expenseAppNo="+str;
	targetForm.method = "POST";
	targetForm.submit();
}

function toApprove(str) {
	var targetForm = document.forms[0];
	targetForm.action = "${pageContext.request.contextPath}/SE_AC_AP_LS/toApprove?expenseAppNo="+str;
	targetForm.method = "POST";
	targetForm.submit();
}

function editSelf() {
	var targetForm = document.forms[0];
	targetForm.action = "${pageContext.request.contextPath}/SE_LG_MN_IF/editSelf";
	targetForm.method = "POST";
	targetForm.submit();
}
</script>
<body>
<form:form id="seLgMnIfBean" modelAttribute="seLgMnIfBean" commandName="seLgMnIfBean">
	<div class="position"><fmt:message key="se_lg_mn_if_position" /></div>
    <div class="list_main_box">
        <div class="information_box">
            <div class="information_title"><fmt:message key="se_lg_mn_if_yourinfo" /></div>
            <div class="information_user">
            	<table style="width:100%">
            		<tr height="48">
            			<td width="150px"><fmt:message key="se_lg_mn_if_employeeno" /></td>
            			<td width="250px">&nbsp;${ seLgMnIfBean.employeeNo}</td>
            			<td rowspan="3" style="text-align:right;padding-right:100px">
                        	<c:if test='${ seLgMnIfBean.headPic != "" and seLgMnIfBean.headPic != null }'>
								<img src="${pageContext.request.contextPath}/personalPhoto/${seLgMnIfBean.headPic }" width="140px" height="140px" />
							</c:if>
            			</td>
            		</tr>
            		<tr height="48">
            			<td><fmt:message key="se_lg_mn_if_employeename" /></td>
            			<td>&nbsp;${ seLgMnIfBean.name}</td>
            		</tr>
            		<tr height="48">
            			<td><fmt:message key="se_lg_mn_if_costcenter" /></td>
            			<td>&nbsp;${ seLgMnIfBean.costCenterCode}/${ seLgMnIfBean.costCenter}</td>
            		</tr>
            	</table>
            	<table style="width:100%">
            		<tr>
            			<td class="editMyself" style="text-align:right">
            				<wa:showAtag id="SE_LG_MN_ed" onclick="editSelf();" value="Edit"></wa:showAtag>
            				<!-- <a href="#" onclick="editSelf();">Edit</a>  -->
            			</td>
            		</tr>
            	</table>
            </div>
        </div>
                
        <div class="information_box">
            <div class="information_title"><fmt:message key="se_lg_mn_if_todolist" /></div>
            <div class="information_main">
                <ul>
                	<c:forEach var="beanList" items="${ seLgMnIfBean.applicationBeanList }">
                    	<li>
                    		<a href="#" onclick="toApplication('${beanList.expensesAppNo }')">
                    		${beanList.expensesAppNo }&nbsp;&nbsp;<fmt:message key="se_lg_mn_if_application" />
                    		</a>
                    	</li>
                    </c:forEach>
                    <c:forEach var="beanList" items="${ seLgMnIfBean.rejectBeanList }">
	                    <li>
	                    	<a href="#" onclick="toApplication('${beanList.expensesAppNo }')">
	                    		<fmt:message key="se_lg_mn_if_reject_1" />
	                    		${beanList.expensesAppNo }
	                    		<fmt:message key="se_lg_mn_if_reject_2" />
	                    		${beanList.updUserKey }
	                    	</a>
	                    </li>
                    </c:forEach>
                    <c:forEach var="beanList" items="${ seLgMnIfBean.approveBeanList }">
                    	<li>
                    		<a href="#" onclick="toApprove('${beanList.expenseNo }')">
                    		<fmt:message key="se_lg_mn_if_approve_1" />
                    		${beanList.claimBy }
                    		<c:if test="${beanList.status == '4'}">
                    			<fmt:message key="se_lg_mn_if_approve_3" />
                    		</c:if>
                    		<c:if test="${beanList.status != '4' && beanList.status != '5'}">
                    			<fmt:message key="se_lg_mn_if_approve_2" />
                    		</c:if>
                    		
                    		</a>
                    	</li>
                    </c:forEach>
                </ul>
            </div>
        </div>
    </div>
</form:form>       
</body>
</html>
