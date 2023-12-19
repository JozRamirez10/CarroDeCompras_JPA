<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<jsp:include page="layout/header.jsp"/>
    <h3>${title}</h3>
    <form name="formcarro" action="${pageContext.request.contextPath}/carro/actualizar" method="post">
        <c:choose>
                  <%-- Usando CDI ya no es necesario verificar si != null --%>
        <c:when test="${carro.items.isEmpty()}">
            <div class="alert alert-warning">No hay productos en el carro de compras</div>
        </c:when>
        <c:otherwise>
            <table class="table table-hover table-striped">
                <tr>
                    <th>Id</th>
                    <th>Nombre</th>
                    <th>Precio</th>
                    <th>Cantidad</th>
                    <th>Total</th>
                    <th>Borrar</th>
                </tr>
                <c:forEach items="${carro.items}" var="item">
                    <tr>
                        <td>${item.producto.id}</td>
                        <td>${item.producto.nombre}</td>
                        <td>${item.producto.precio}</td>
                        <td><input type"number" name="cant_${item.producto.id}" value='${item.cantidad}'></td>
                        <td>${item.importe}</td>
                        <td><input type="checkbox" value="${item.producto.id}" name="deleteProductos"></td>
                    </tr>
                </c:forEach>
                <tr>
                    <td colspan="5" style="text-align right">Total:</td>
                    <td>${carro.total}</td>
                </tr>
            </table>
        </c:otherwise>
        </c:choose>
        <a class="btn btn-primary" href="javascript:document.formcarro.submit();">Actualizar carro de compras</a>
    </form>
    <div class="my-2">
        <a class="btn btn-secondary" href="${pageContext.request.contextPath}/index.jsp">Volver</a>
        <a class="btn btn-success" href="${pageContext.request.contextPath}/productos">Seguir comprando</a>
    </div>
<jsp:include page="layout/footer.jsp"/>