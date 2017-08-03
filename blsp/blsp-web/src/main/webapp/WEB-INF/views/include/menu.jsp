<%@ page contentType="text/html;charset=UTF-8" %>
<aside id="sidebar">
    <div class="sidebar-inner">
        <div class="si-inner">
            <div class="profile-menu">
                <a href="">
                	<div title="点击显示隐藏" data-toggle="tooltip" data-placement="bottom">
	                    <div class="profile-pic">
	                    	<c:set var="user_" value="${fns:getUser()}"/>
	                    	<c:if test="${!empty user_.photo}">
	                    		<img src="${user_.photo}" alt="">
	                    	</c:if>
	                    	<c:if test="${empty user_.photo}">
	                        	<img src="${ctxStaticModern}/img/userImg.png" alt="">
	                        </c:if>
	                    </div>
	    
	                    <div class="profile-info">
	                       	${user_.name}    
	                        <i class="md md-arrow-drop-down"></i>
	                    </div>
                    </div>
                </a>
    
                <ul class="main-menu">
                    <li>
                        <a href="${ctx}/sys/user/info"><i class="md md-person"></i>查看个人资料</a>
                    </li>
                    <%--<li>--%>
                        <%--<a href="${ctx}/sys/user/modifyPwd"><i class="md md-settings"></i>修改密码</a>--%>
                    <%--</li>--%>
                    <li>
                        <a href="${ctx}/logout"><i class="md md-exit-to-app"></i>注销</a>
                    </li>
                </ul>
            </div>
    		
            <ul class="main-menu">
            	<c:set var="menuList" value="${fns:getMenuList()}" />
				<c:forEach items="${menuList}" var="menu" varStatus="idxStatus">
					<c:if test="${menu.parent.id eq '1'&&menu.isShow eq '1'}">
						<c:set var="isparent" value="false" />
						<c:forEach items="${menuList}" var="menu2">
							<c:if test="${menu2.parent.id eq menu.id&&menu2.isShow eq '1'}">
								<c:set var="isparent" value="true" />
							</c:if>
						</c:forEach>
						<li ${isparent?'class="sub-menu"':''}>
							<c:if test="${empty menu.href}">
								<a href="javascript:"><i class="md ${menu.icon}"></i>${menu.name}</a>
							</c:if>
							<c:if test="${not empty menu.href}">
								 <a href="${menu.href}"><i class="md"></i>${menu.name}</a>
							</c:if>
							<c:if test="${isparent}">
								<ul>
									<c:forEach items="${menuList}" var="menu2">
										<c:if test="${menu2.parent.id eq menu.id&&menu2.isShow eq '1'}">
											<li><a href="${fn:indexOf(menu2.href, '://') eq -1 ? ctx : ''}${not empty menu2.href ? menu2.href : '/404'}"><i class="md ${menu2.icon}"></i>${menu2.name}</a></li>
										</c:if>
									</c:forEach>
								</ul>
							</c:if>
						</li>
					</c:if>
				</c:forEach>
            </ul>
        </div>
    </div>
</aside>