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
                <h1 class="page-header">修改上位机</h1>
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
                                    <!-- 数据序列编号 -->
                                    <input class="form-control hidden" name="id" value="${param.id }">
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
                                    <div class="row">
                                        <div class="form-group col-xs-4">
                                            <label>省</label>
                                            <select class="form-control province" name="province">
                                                <option value="">-- 请选择省 --</option>
                                            </select>
                                        </div>
                                        <div class="form-group col-xs-4">
                                            <label>市</label>
                                            <select class="form-control city" name="city">
                                                <option value="">-- 请选择市 --</option>
                                            </select>
                                        </div>
                                        <div class="form-group col-xs-4">
                                            <label>区</label>
                                            <select class="form-control county" name="county">
                                                <option value="">-- 请选择区 --</option>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label>设备地址</label>
                                        <input class="form-control" name="address">
                                        <p class="help-block tip">tip.</p>
                                    </div>
                                    <input type="button" class="btn btn-outline btn-success submit" value="保存">
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
    $(function () {
        /**
         * 获取地区集合
         * params postUrl  请求地址
         * params postData  请求参数
         * params callBack  回调函数
         */
        var getArea = function (postUrl, postData, callBack) {
            $.ajax({
                type: "GET",
                url: postUrl,
                data: postData,
                success: function (resp) {
                    if (resp.code == "200") {
                        eval(callBack + "(resp.data)");
                    } else {
                        $("#form-tip").removeClass("hidden alert-success").addClass("alert-warning").show().find("strong").text(resp.message);
                        $(".submit").removeAttr("disabled");
                    }
                }
            });
        }

        //获取省级数据地址
        var getProvinceUrl = "/getProvince";
        //获取市级数据地址
        var getCityUrl = "/getCity";
        //获取区级数据地址
        var getCountyUrl = "/getCounty";

        /**
         * 获取省级数据
         */
        getArea(getProvinceUrl, null, "setProvince");

        //省点击事件
        $(".province").change(function () {
            getArea(getCityUrl, {provinceId: $(this).val()}, "setCity");
        });

        //市点击事件
        $(".city").change(function () {
            getArea(getCountyUrl, {cityId: $(this).val()}, "setCounty");
        });

        /**
         * 填充省级数据
         */
        var setProvince = function (data) {
            //还原市和区数据
            $(".city").html("<option value=''>-- 请选择市 --</option>");
            $(".county").html("<option value=''>-- 请选择区 --</option>");

            if (data != null && data.length > 0) {
                var option = "<option value=''>-- 请选择省 --</option>";
                $(data).each(function () {
                    option += '<option value="' + this.province_id + '">' + this.province_name + '</option>';
                });

                $(".province").html(option);
            }
        }

        /**
         * 填充市级数据
         */
        var setCity = function (data) {
            //还原区数据
            $(".county").html("<option value=''>-- 请选择区 --</option>");

            if (data != null && data.length > 0) {
                var option = "<option value=''>-- 请选择市 --</option>";
                $(data).each(function () {
                    option += '<option value="' + this.city_id + '">' + this.city_name + '</option>';
                });

                $(".city").html(option);
            }
        }

        /**
         * 填充区级数据
         */
        var setCounty = function (data) {
            if (data != null && data.length > 0) {
                var option = "<option value=''>-- 请选择区 --</option>";
                $(data).each(function () {
                    option += '<option value="' + this.county_id + '">' + this.county_name + '</option>';
                });

                $(".county").html(option);
            }
        }

        $.ajax({
            type: "POST",
            url: "/system-manage/masterComputer/object",
            data: {id: "${param.id}"},
            success: function (response) {
                if(response.code == "200"){
                    var _this = response.data;
                    $("input[name='name']").val(_this.name);
                    $("input[name='code']").val(_this.code);
                    $("select[name='province']").val(this.province);
                    $("select[name='city']").val(this.city);
                    $("select[name='county']").val(this.county);
                }else{
                    $("#form-tip").removeClass("hidden alert-warning").addClass("alert-success").show().find("strong").text(response.message);
                    //3秒后自动关闭警告框
                    setTimeout("hideOperatorTip()", 3000);
                }
            }
        });

        $(".submit").click(function () {
            $(this).attr('disabled', "true")

            $.ajax({
                type: "POST",
                url: "/system-manage/masterComputer/update",
                data: $("form").serialize(),
                success: function (response) {
                    if (response.code == "200") {
                        $("#form-tip").removeClass("hidden alert-warning").addClass("alert-success").show().find("strong").text(response.message);

                        setTimeout(function () {
                            window.location.href = "/system-manage/gotoPage?url=master computer/list";
                        }, 500);
                    } else {
                        $("#form-tip").removeClass("hidden alert-success").addClass("alert-warning").show().find("strong").text(response.message);
                        $(".submit").removeAttr("disabled");
                    }
                }
            });
        });

        $(".back").click(function () {
            window.history.go(-1);
        });
    });

    /**
     * 隐藏警告框
     */
    function hideOperatorTip(){
        $("#form-tip").addClass("hidden")
        window.history.go(-1);
    }

</script>
</body>

</html>
