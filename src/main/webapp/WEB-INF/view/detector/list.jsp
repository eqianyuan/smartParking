<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>驿乾元 - ERP管理系统</title>
    <link rel="stylesheet" href="http://apps.bdimg.com/libs/bootstrap/3.3.0/css/bootstrap.min.css">
    <link href="/css/style.css" rel="stylesheet">
    <link href="/css/view/common.css" rel="stylesheet">
    <link href="/css/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
    <script src="/js/jquery-1.10.1.min.js"></script>
    <script src="/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="/js/common/eqianyuan.page.js"></script>
    <script type="text/javascript" src="/js/common/common_utils.js"></script>
</head>

<body>
<div id="wrapper">

    <!-- Navigation -->
    <nav class="navbar navbar-default navbar-static-top" role="navigation" style="margin-bottom: 0">
        <div class="navbar-header">
            <a class="navbar-brand" href="${pageContext.request.contextPath}">驿乾元ERP管理系统</a>
        </div>

        <ul class="nav navbar-top-links navbar-right">
            <li class="dropdown">
                <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                    <i class="fa fa-user fa-fw"></i> <i class="fa fa-caret-down"></i>
                </a>
                <ul class="dropdown-menu dropdown-user">
                    <li><a href="/system-manage/logout"><i class="fa fa-sign-out fa-fw"></i> 退出登录</a>
                    </li>
                </ul>
            </li>
        </ul>

        <div class="navbar-default sidebar" role="navigation">
            <div class="sidebar-nav navbar-collapse">
                <ul class="nav" id="side-menu">
                    <li class="sidebar-search">
                        <div class="input-group custom-search-form">
                            <input type="text" class="form-control" placeholder="菜单查找...">
                                <span class="input-group-btn">
                                <button class="btn btn-default" type="button">
                                    <i class="fa fa-search"></i>
                                </button>
                            </span>
                        </div>
                    </li>
                    <li>
                        <a href="/system-manage/fiscal-detail/list"><i class="fa fa-dashboard fa-cny"></i> 探测器管理</a>
                    </li>
                </ul>
            </div>
        </div>
        <!-- /.navbar-static-side -->
    </nav>

    <div id="page-wrapper">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">探测器管理列表</h1>
            </div>
            <!-- /.col-lg-12 -->
        </div>
        <!-- /.row -->
        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <a href="/system-manage/gotoAddByFiscalDetail">
                            <button type="button" class="btn btn-default btn-lg">添加探测器</button>
                        </a>
                    </div>
                    <%--<div class="panel-body panel-search">--%>
                        <%--<fieldset class="form-inline">--%>
                            <%--<label contenteditable="true">状态</label> <input placeholder="请输入用户名..." type="text"--%>
                                                                            <%--id="search_by_userName">--%>
                            <%--<label contenteditable="true">上位机编号</label> <input placeholder="请输入用户名..." type="text"--%>
                                                                               <%--id="search_by_userName">--%>
                            <%--<button type="button" class="btn btn-default btn-sm" id="search">筛选查找</button>--%>
                        <%--</fieldset>--%>
                    <%--</div>--%>
                    <div class="panel-body">
                        <div class="dataTable_wrapper">
                            <table width="100%" class="table table-striped table-bordered table-hover"
                                   id="dataTables">
                                <thead>
                                <tr>
                                    <th><input type="checkbox" class="checkAll"/></th>
                                    <th>序列编号</th>
                                    <th>设备代码</th>
                                    <th>状态</th>
                                    <th>归属上位机编号</th>
                                </tr>
                                </thead>
                                <tbody></tbody>
                            </table>
                        </div>
                    </div>
                    <ul class="pagination pagination-right"></ul>
                </div>
            </div>
        </div>
    </div>

</div>
<script>
    $(document).ready(function () {
        var pagination = {
            initStatus: false,      //分页插件初始化状态-true:已经构建了分页插件、false:还未构建
            data: {},
            page: {
                pageNo: 1,
                pageSize: 10
            },
            init: function () {
                //初始化分页
                $(".pagination").createPage({
                    pageCount: pagination.page.pageCount,
                    current: pagination.page.pageNo,
                    initStatus: pagination.initStatus,
                    backFn: function (pageNo) {
                        pagination.page.pageNo = pageNo;
                        pagination.list();
                    }
                });

                pagination.initStatus = true;
            },
            list: function () {
                $("#dataTables tbody").html("");

                $.ajax({
                    type: "POST",
                    url: "/system-manage/detector/dataList",
                    data: $.extend({}, pagination.data, pagination.page),
                    success: function (response) {
                        //设置分页
                        pagination.page.pageNo = response.data.pageNo;
                        pagination.page.pageCount = response.data.pageCount;
                        pagination.init();
                        if (response.data.totalCount > 0) {
                            var tableBody = "";
                            $(response.data.list).each(function () {
                                tableBody += '<tr>'
                                        + '<td><input type="checkbox" class="itemCheck" value="' + this.id + '"/></td>'
                                        + '<td>' + this.id + '</td>'
                                        + '<td>' + this.code + '</td>'
                                        + '<td>' + this.status_cn + '</td>'
                                        + '<td>' + (null == this.parent_id ? "" : this.parent_id) + '</td>'
                                        + '</tr>';
                            });

                            $("#dataTables tbody").html(tableBody);
                        } else {
                            $("#dataTables tbody").html('<tr class="text-center"><td colspan="7">查无数据</td></tr>');
                        }
                    }
                });
            }
        };

        //获取列表数据
        pagination.list();

        $("#search").click(function () {
            pagination.data.user_name = $("#search_by_userName").val();
            pagination.page.pageNo = 1;
            pagination.list();
        });
    });
</script>

</body>

</html>
