<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="emarket" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div id="order">
	<c:if test="${CURRENT_MESSAGE != null }">
	<div class="alert alert-success hidden-print" role="alert">${CURRENT_MESSAGE }</div>
	</c:if>
</div>