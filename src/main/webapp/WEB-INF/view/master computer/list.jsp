<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
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
    <div id="page-wrapper">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">上位机管理列表</h1>
            </div>
            <!-- /.col-lg-12 -->
        </div>
        <!-- /.row -->
        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <button type="button" class="btn btn-default btn-lg add">注册上位机</button>
                        <button type="button" class="btn btn-danger btn-lg delete">注销上位机</button>
                    </div>
                    <div class="alert alert-warning alert-dismissable hidden operatorTip">
                        <button type="button" class="close" data-dismiss="operatorTip"
                                aria-hidden="true">
                            &times;
                        </button>
                        <span></span>
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
                                    <th>设备名称</th>
                                    <th>状态</th>
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

    <!-- 模态框（Modal） -->
    <div class="modal fade" id="myModal" tabindex="-1" role="dialog"
         aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"
                            aria-hidden="true">×
                    </button>
                    <h4 class="modal-title" id="myModalLabel">
                        上位机管理操作确认
                    </h4>
                </div>
                <div class="modal-body">
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default"
                            data-dismiss="modal">取消
                    </button>
                    <button type="button" class="btn btn-primary confirm">
                        确定
                    </button>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
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
                $(".checkAll").prop("checked", false);

                $.ajax({
                    type: "POST",
                    url: "/system-manage/masterComputer/dataList",
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
                                        + '<td>' + this.name + '</td>'
                                        + '<td>' + this.status_cn + '</td>'
                                        + '</tr>';
                            });

                            $("#dataTables tbody").html(tableBody);
                        } else {
                            $("#dataTables tbody").html('<tr class="text-center"><td colspan="5">查无数据</td></tr>');
                        }
                    }
                });
            }
        };

        //获取列表数据
        pagination.list();

        $("#search").click(function () {
            pagination.page.pageNo = 1;
            pagination.list();
        });

        $(".delete").click(function(){
            //清空ids集合
            ids.length = 0;
            hideOperatorTip();
            //获取当前数据表中，被选中的数据
            $("input[type='checkbox']:checked").each(function(){
                if(!$(this).hasClass("checkAll")){
                    ids.push($(this).val());
                }
            });

            if(ids.length == 0){
                $(".operatorTip").removeClass("hidden").find("span").text('请选择需要注销的数据');
            }else{
                $('#myModal').modal();
                $(".modal-body").text("是否注销所选数据，被注销数据无法还原！");
            }
        });

        $(".operatorTip .close").click(function(){
            hideOperatorTip();
            return false;
        });

        var ids = new Array();
        $(".confirm").click(function(){
            $.ajax({
                type: "post",
                url: "/system-manage/masterComputer/delete",
                traditional: true,
                data: {"id":ids},
                success: function(response){
                    $("#myModal").modal('hide')
                    $(".operatorTip").removeClass("hidden").find("span").text(response.message);
                    if(response.code != "200"){
                        $("#myModal").modal('hide')
                        $(".operatorTip").removeClass("hidden").find("span").text(response.message);
                    }else{
                        pagination.page.pageNo = 1;
                        pagination.list();

                        //3秒后自动关闭警告框
                        setTimeout("hideOperatorTip()", 3000);
                    }
                }
            });
        });

        //注册上位机
        $(".add").click(function(){
            window.location.href = "/system-manage/gotoPage?url=master computer/add";
        });
    });

    /**
     * 隐藏警告框
     */
    function hideOperatorTip(){
        $(".operatorTip").addClass("hidden")
    }
</script>

</body>

</html>
