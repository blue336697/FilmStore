<%--
  Created by IntelliJ IDEA.
  User: zzzzzzzzzz
  Date: 2021/5/10
  Time: 16:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--将分页条相同的内容抽取出来--%>
<div id="page_nav">
    <%--大于首页页码才显示按钮--%>
    <c:if test="${requestScope.page.pageNo>1}">
        <a href="${requestScope.page.url}&pageNo=1">首页</a>
        <a href="${requestScope.page.url}&pageNo=${requestScope.page.pageNo-1}">上一页</a>
    </c:if>
    <%--页码输出的开始--%>
    <%--情况一：如果总页码小于5，页码的范围是：1到总页码 --%>
    <c:choose>
        <c:when test="${requestScope.page.pageTotal<=5}">
            <c:set var="begin" value="1"/>
            <c:set var="end" value="${requestScope.page.pageTotal}"/>
        </c:when>
        <%--情况二：页码大于5的情况--%>
        <c:when test="${requestScope.page.pageTotal>5}">
            <c:choose>
                <%--小情况1：当前页码为前面三个：1、2、3的情况--%>
                <c:when test="${requestScope.page.pageTotal<=3}">
                    <c:set var="begin" value="1"/>
                    <c:set var="end" value="5"/>

                </c:when>
                <%--小情况2：当前页码为最后3个，8、9、10，页码范围：总页码减4到总页码--%>
                <c:when test="${requestScope.page.pageNo > requestScope.page.pageTotal-3}">
                    <c:set var="begin" value="${requestScope.page.pageTotal-4}"/>
                    <c:set var="end" value="${requestScope.page.pageTotal}"/>
                </c:when>
                <%--小情况3：4、5、6、7 页码范围：当前页码减2到当前页码加2--%>
                <c:otherwise>
                    <c:set var="begin" value="${requestScope.page.pageNo-2}"/>
                    <c:set var="end" value="${requestScope.page.pageNo+2}"/>
                </c:otherwise>
            </c:choose>
        </c:when>
    </c:choose>
    <c:forEach begin="${begin}" end="${end}" var="i">
        <c:if test="${i==requestScope.page.pageNo}">
            【${i}】
        </c:if>
        <c:if test="${i!=requestScope.page.pageNo}">
            <a href="${requestScope.page.url}&pageNo=${i}">
                    ${i}
            </a>
        </c:if>
    </c:forEach>



    <%--如果已经是最后一页了就不用显示了--%>
    <c:if test="${requestScope.page.pageNo<requestScope.page.pageTotal}">
        <a href="${requestScope.page.url}&pageNo=${requestScope.page.pageNo+1}">下一页</a>
        <a href="${requestScope.page.url}&pageNo=${requestScope.page.pageTotal}">末页</a>
    </c:if>

    共${requestScope.page.pageTotal}页，${requestScope.page.pageTotalCount}条记录
    到第<input value="${param.pageNo}" name="pn" id="pn_input"/>页
    <input id="searchPageBtn" type="button" value="确定">
    <script type="text/javascript">
        $(function (){
            //跳到指定页码
            $("#searchPageBtn").click(function (){
                var pageNo = $("#pn_input").val();
                <%--var pageTotal = ${requestScope.page.pageTotal}--%>
                //js提供一个location地址栏对象，其中的href属性可以获取地址栏的地址，并且可读可写（所以我们可以设置跳转的页面）
                location.href = "${pageScope.basePath}${requestScope.page.url}&pageNo=" + pageNo;
            });
        });
    </script>
</div>
