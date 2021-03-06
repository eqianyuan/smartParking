<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
</head>

<body>

<div id="wrapper">
    <div id="page-wrapper">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">注册探测器</h1>
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
                                        <label>代码</label>
                                        <input class="form-control" name="code">
                                        <p class="help-block tip">tip.</p>
                                    </div>
                                    <input type="button" class="btn btn-default submit" value="添加">
                                    <input type="button" class="btn btn-default back" value="返回">
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
            url: "/system-manage/doAddByFiscalDetail",
            data: $("form").serialize(),
            success: function(response){
                if(response.code == "200"){
                    $("#form-tip").removeClass("hidden alert-warning").addClass("alert-success").show().find("strong").text(response.message);

                    setTimeout(function(){
                        window.location.href = "/system-manage/fiscal-detail/list"
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
