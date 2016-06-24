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
</head>

<body>

<div id="wrapper">
    <div id="page-wrapper">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">注册上位机</h1>
            </div>
            <!-- /.col-lg-12 -->
        </div>
        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-default">
                    <div class="panel-body">
                        <div class="row">
                            <div class="col-lg-6">
                                <div id="form-tip" class="alert hidden">
                                    <strong></strong>
                                </div>
                                <form role="form">
                                    <div class="form-group">
                                        <label>设备名称</label>
                                        <input class="form-control" name="name">
                                        <p class="help-block tip">tip.</p>
                                    </div>
                                    <div class="form-group">
                                        <label>设备代码</label>
                                        <input class="form-control" name="code" placeholder="内容只能是数字">
                                        <p class="help-block tip">tip.</p>
                                    </div>
                                    <div class="form-group col-xs-4">
                                        <label>省</label>
                                        <select class="form-control">
                                            <option>1</option>
                                            <option>2</option>
                                            <option>3</option>
                                            <option>4</option>
                                            <option>5</option>
                                        </select>
                                    </div>
                                    <div class="form-group col-xs-4">
                                        <label>市</label>
                                        <select class="form-control">
                                            <option>1</option>
                                            <option>2</option>
                                            <option>3</option>
                                            <option>4</option>
                                            <option>5</option>
                                        </select>
                                    </div>
                                    <div class="form-group col-xs-4">
                                        <label>区</label>
                                        <select class="form-control">
                                            <option>1</option>
                                            <option>2</option>
                                            <option>3</option>
                                            <option>4</option>
                                            <option>5</option>
                                        </select>
                                    </div>
                                    <input type="button" class="btn btn-outline btn-success submit" value="注册">
                                    <input type="button" class="btn btn-outline btn-info back" value="返回">
                                </form>
                            </div>
                        </div>
                        <!-- /.row (nested) -->
                    </div>
                    <!-- /.panel-body -->
                </div>
                <!-- /.panel -->
            </div>
            <!-- /.col-lg-12 -->
        </div>
    </div>
</div>

<script>
$(function(){
    $(".submit").click(function(){
        $(this).attr('disabled',"true")

        $.ajax({
            type: "POST",
            url: "/system-manage/masterComputer/add",
            data: $("form").serialize(),
            success: function(response){
                if(response.code == "200"){
                    $("#form-tip").removeClass("hidden alert-warning").addClass("alert-success").show().find("strong").text(response.message);

                    setTimeout(function(){
                        window.location.href = "/system-manage/gotoPage?url=master computer/list";
                    }, 500);
                }else{
                    $("#form-tip").removeClass("hidden alert-success").addClass("alert-warning").show().find("strong").text(response.message);
                    $(".submit").removeAttr("disabled");
                }
            }
        });
    });

    $(".back").click(function(){
        window.history.go(-1);
    });
});

</script>
</body>

</html>
