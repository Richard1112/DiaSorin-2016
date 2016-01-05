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
<link href="<c:url value='/bootstrap/css/prettify.css' />" type="text/css" rel="stylesheet" />
<link href="<c:url value='/bootstrap/css/bootstrap-datepicker.css' />" type="text/css" rel="stylesheet" />
<link href="<c:url value='/css/cover.css' />" type="text/css" rel="stylesheet" />

<script src="<c:url value='/bootstrap/js/jquery-2.0.3.min.js' />" type="text/javascript"></script>
<script src="<c:url value='/bootstrap/js/bootstrap-3.0.3.min.js' />" type="text/javascript"></script>
<script src="<c:url value='/bootstrap/js/prettify.js' />" type="text/javascript"></script>
<script src="<c:url value='/bootstrap/js/bootstrap-datepicker.js' />" type="text/javascript"></script>
<script>
function upd(roleId){
	var targetForm = document.forms[0];
	var curPage=0;
	if($("#hdListCount").val() == "0"){
		curPage = 1;
	}else{
		curPage = parseInt($("#curPage").val());
	}
	targetForm.action = "${pageContext.request.contextPath}/SE_AM_RO_LS/update?roleId=" + roleId + "&curPage=" + curPage;
	targetForm.method = "POST";
	targetForm.submit();
}

function add(){
	var targetForm = document.forms[0];
	targetForm.action = "${pageContext.request.contextPath}/SE_AM_RO_LS/add";
	targetForm.method = "POST";
	targetForm.submit();
}

var I0006 = '<fmt:message key="I0006" />';
function del(roleId){
	if (!confirm(I0006)){
		return;
	}
	var targetForm = document.forms[0];
	var curPage=parseInt($("#curPage").val());
	targetForm.action = "${pageContext.request.contextPath}/SE_AM_RO_LS/delete?roleId=" + roleId + "&curPage=" + curPage;
	targetForm.method = "POST";
	targetForm.submit();
}

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
	targetForm.action = "${pageContext.request.contextPath}/SE_AM_RO_LS/page?curPage="+curPage;
	targetForm.method = "POST";
	targetForm.submit();
}
</script>
</head>

<body>
<form:form id="seAmRoLsBean" modelAttribute="seAmRoLsBean" commandName="seAmRoLsBean">
	<div class="position_none_boots"><fmt:message key="se_am_ro_ls_position" /></div>
	<input type="hidden" id="hdListCount" value="${ listCount }"/>
	<div class="list_main_box">
   	  <div class="list_title"><fmt:message key="se_am_ro_ls_title" /></div> 
        <div class="list_box1">
        	<table border="0" cellpadding="0" cellspacing="0">
            	<tbody>
                    <tr>
                   	  <td>
                   	  	<div class="add_details_left_btn">
                   	  		<wa:showAtag id="SE_AM_RO_LS_Add" onclick="javascript:add();" innerImg="/images/new_btn.png"></wa:showAtag>
                   	  	</div>
                   	  </td>
                      <td>
                      </td>
                    </tr>
                    
                </tbody>
            </table>
        </div>
        <div class="list_box2">
			<table border="0" cellpadding="0" cellspacing="0">
				<tbody>
                
                	<tr class="td_title">
                        <td width="206px"><fmt:message key="se_am_ro_ls_roleId" /></td>
						<td width="523px"><fmt:message key="se_am_ro_ls_roleName" /></td>
						<td width="261px">&nbsp;</td>
                    </tr>
                    <c:forEach var="beanList" items="${ seAmRoLsBean.beanList }" varStatus="vs">
						<tr>
							<td>${ beanList.roleCode}</td>
							<td>${ beanList.roleName }</td>
							<td class="td_width">
								<wa:showAtag id="SE_AM_RO_LS_Modify" onclick="javascript:upd('${ beanList.roleCode }');" value="modify"></wa:showAtag>&nbsp;&nbsp;
								<wa:showAtag id="SE_AM_RO_LS_Delete" onclick="javascript:del('${ beanList.roleCode }');" value="delete"></wa:showAtag>
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
