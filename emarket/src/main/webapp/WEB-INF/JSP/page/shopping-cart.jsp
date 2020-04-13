<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="emarket" tagdir="/WEB-INF/tags"%>


<div id="shoppingCart">
	<c:if test="${CURRENT_ACCOUNT == null }">
		<div class="alert alert-warning hidden-print" role="alert">To make order, please sign in</div>
	</c:if>
	<table class="table table-bordered">
		<thead>
			<tr>
				<th>Product</th>
				<th>Price</th>
				<th>Count</th>
				<th class="hidden-print">Action</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${CURRENT_SHOPPING_CART.items }">
			<tr id="product${item.product.id }" class="item">
				<td class="text-center"><img class="small" src="${item.product.imageLink}" alt="${item.product.name}"><br>${item.product.name}</td>
				<td class="price">$ ${item.product.price }</td>
				<td class="count">${item.count}</td>
				<td class="hidden-print">
				<c:choose>
				<c:when test="${item.count > 1 }">
					<a class="btn btn-danger remove-product" data-id-product="${item.product.id }" data-count="1">Remove one</a><br><br>
					<a class="btn btn-danger remove-product remove-all" data-id-product="${item.product.id }" data-count="${item.count }">Remove all</a>
				</c:when>
				<c:otherwise>
					<a class="btn btn-danger remove-product" data-id-product="${item.product.id }" data-count="1">Remove one</a>
				</c:otherwise>
				</c:choose>
				</td>
			</tr>
			</c:forEach>
			<tr>
				<td colspan="2" class="text-right"><strong>Total:</strong></td>
				<td colspan="2" class="total">$ ${CURRENT_SHOPPING_CART.totalCost}</td>
			</tr>
		</tbody>
	</table>
	<div class="row hidden-print">
		<div class="col-md-4 col-md-offset-4 col-lg-2 col-lg-offset-5">
			<c:choose>
				<c:when test="${CURRENT_ACCOUNT != null }">
					<a href="javascript:void(0);" class="post-request btn btn-primary btn-block" data-url="/order">Make order</a>
				</c:when>
				<c:otherwise>
					<emarket:sign-in classes="btn-block" />
				</c:otherwise>
			</c:choose>
		</div>
	</div>
</div>