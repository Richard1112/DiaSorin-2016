<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
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
<script src="<c:url value='/js/jquery.min.js' />" type="text/javascript"></script>
<script>
function submitLogin(){
	var targetform = document.forms[0];
	targetform.action = "${pageContext.request.contextPath}/SE_LG_UL/login";
	targetform.method = "POST";
	targetform.submit();
}


function addEmployee(){
	var curPage;
	if(document.getElementById("totalPage")){
		curPage=parseInt($("#curPage").val());
	}else{
		curPage=0
	}
	var targetform = document.forms[0];
	targetform.action = "${pageContext.request.contextPath}/SE_EM_PE_AD/init?curPage="+curPage;
	targetform.method = "POST";
	targetform.submit();
}

// 修改
function modify(employeeNo){
	var curPage=parseInt($("#curPage").val());
	var targetform = document.forms[0];
	targetform.action = "${pageContext.request.contextPath}/SE_EM_PE_DE/init/" + employeeNo + "?curPage="+curPage;
	targetform.method = "POST";
	targetform.submit();
}

// 删除
var I0006 = '<fmt:message key="I0006" />';
function deleteEmp(employeeNo){
	if (!confirm(I0006)){
		return;
	}
	var curPage=parseInt($("#curPage").val());
	var targetform = document.forms[0];
	targetform.action = "${pageContext.request.contextPath}/SE_EM_PE_LS/deleteEmployee/" + employeeNo + "?curPage="+curPage;
	targetform.method = "POST";
	targetform.submit();
}

// 密码变更
function replacePw(employeeNo){
	var curPage=parseInt($("#curPage").val());
	var targetform = document.forms[0];
	targetform.action = "${pageContext.request.contextPath}/SE_EM_PE_LS/reflashPassword/" + employeeNo + "?curPage="+curPage;;
	targetform.method = "POST";
	targetform.submit();
}

// 检索
function searchEmployee(){
	var targetform = document.forms[0];
	targetform.action = "${pageContext.request.contextPath}/SE_EM_PE_LS/searchEmployee";
	targetform.method = "POST";
	targetform.submit();
}

// 
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
	targetForm.action = "${pageContext.request.contextPath}/SE_EM_PE_LS/page?curPage="+curPage;
	targetForm.method = "POST";
	targetForm.submit();
}

</script>
</head>

<body>
<form:form id="seEmPeLsBean" modelAttribute="seEmPeLsBean" commandName="seEmPeLsBean">
	<div class="position"><fmt:message key="se_em_pe_ls_postion" /></div>
    
    
	<div class="list_main_box">
   	  <div class="list_title"><fmt:message key="se_em_pe_ls_title" /></div> 
        <div class="list_box1">
        	<table border="0" cellpadding="0" cellspacing="0">
            	<tbody>
                	<tr>
                    	<td class="td_font"><fmt:message key="se_em_pe_ls_name" /></td>
                    	<td class="td_box">
                            <form:input path="name" cssClass="input_box"/>
                        </td>
                        <td colspan="2"></td>
                    </tr>
                    <tr>
                    	<td class="td_font"><fmt:message key="se_em_pe_ls_role" /></td>
                    	<td class="td_box">
                            <form:select path="roleCode" class="select_box">
                            	<form:option value=""></form:option>
                    			<c:forEach var="bean" items="${ roleList }">
                    				<form:option value="${ bean.roleCode }">${ bean.roleName }</form:option>
                    			</c:forEach>
                    		</form:select>
                        </td>
                        <td class="td_font"><fmt:message key="se_em_pe_ls_costcenter" /></td>
                    	<td class="td_box">
                            <form:select path="deptCode" class="select_box">
                            	<form:option value=""></form:option>
                    			<c:forEach var="bean" items="${ costCenterList }">
                    				<form:option value="${ bean.costCenterCode }">${ bean.costCenterCode }/${ bean.costCenterName }</form:option>
                    			</c:forEach>
                    		</form:select>
                        </td>
                    </tr>
                    <tr>
                   	  <td colspan="2">
                   	  		<div class="add_details_left_btn">
                   	  			<wa:showAtag id="SE_EM_PE_LS_Add" onclick="addEmployee()" innerImg="/images/new_users.png"></wa:showAtag>
                   	  		</div>
                   	  </td>
                      <td colspan="2">
                        	<div class="add_details_right_btn">
                        		<wa:showAtag id="SE_EM_PE_LS_Search" onclick="searchEmployee()" innerImg="/images/search_btn.png"></wa:showAtag>
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
                    	<td width="160px"><fmt:message key="se_em_pe_ls_dechiname" /></td>
<%--                         <td width="160px"><fmt:message key="se_em_pe_ls_deengname" /></td> --%>
                        <td width="160px"><fmt:message key="se_em_pe_ls_level" /></td>
                        <td width="150px"><fmt:message key="se_em_pe_ls_derole" /></td>
                        <td width="200px"><fmt:message key="se_em_pe_ls_decostcenter" /></td>
                        <td width="350px">&nbsp;</td>
                    </tr>
                    
                    <c:forEach var="beanList" items="${ seEmPeLsBean.beanList }">
					  <tr>
						 <td style="text-align:left">${ beanList.employeeNameCn }</td>
						 <td style="text-align:left">${ beanList.levelName }</td>
						 <td style="text-align:left">${ beanList.roleName }</td>
						 <td style="text-align:left">${ beanList.costCenterCode }/${ beanList.deptName }</td>
						 <td>
							<wa:showAtag id="SE_EM_PE_LS_Modify" onclick="modify('${ beanList.employeeNo }')" value="Modify"></wa:showAtag>
							<wa:showAtag id="SE_EM_PE_LS_Delete" onclick="deleteEmp('${ beanList.employeeNo }')" value="Delete"></wa:showAtag>
							<wa:showAtag id="SE_EM_PE_LS_ChangePw" onclick="replacePw('${ beanList.employeeNo }')" value="Change password"></wa:showAtag>
						 </td>
					  </tr>
				  	</c:forEach>
                </tbody>
			</table>
			<c:if test='${ listCount != "0" }'>
            <div class="list_pages_box">
           	  <div class="pages">
                    <div class="pages_go_box"><a href="#" onclick="return Action('GO');" class="pages_go" ></a></div>
                    <div class="pages_input_box"><input class="pages_input" type="text" id="pageNum" value="${ curPage }"/></div>
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
