<%--
  Created by IntelliJ IDEA.
  User: AFish
  Date: 2024/11/10
  Time: 22:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 function logout() {
			$.ajax({ 
                type: "GET",
                url: baseUrl + "users/logout",
                beforeSend: function(xhr) {
                    xhr.setRequestHeader("token", window.sessionStorage.getItem('token'));
                },
                success: function(res){               	
                	if(res.code == 0 || res.code == 401){
    					window.sessionStorage.clear();
						window.location.href="${pageContext.request.contextPath}/jsp/login.jsp"
					}else{
						alert(res.msg);	
					}
                },
            });
        }
        function toFront() {
window.location.assign(baseUrl+'front');
}