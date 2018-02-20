<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<title>文件上传</title>
<link rel="stylesheet" href="/css/upfile/uploadfile.css">
<link rel="stylesheet" href="/css/upfile/uploadfile.custom.css">
</head>
<body>
    <form id="newsform" method="post" action="/up"  enctype="multipart/form-data" >    
        <div id="fileuploader"></div>
    </form>
    
    <form id="newsform" method="post" action="/encoding"  enctype="multipart/form-data" >    
        <input type="text" name="fileId" >
        <input type="submit" >
    </form>
    
    
    <a href="/encoding?fileId=你猜！！@#￥%">点击跳转</a>
</body>
<script
  src="http://code.jquery.com/jquery-3.3.1.min.js"
  integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8="
  crossorigin="anonymous"></script>
<script src="/js/upfile/jquery.uploadfile.min.js"></script>
<script>
$(document).ready(function() {

    $("#fileuploader").uploadFile({
        url:"/up", //后台处理方法
        fileName:"myfile",   //文件的名称，此处是变量名称，不是文件的原名称只是后台接收的参数
        dragDrop:true,  // 可以取消
        abortStr:"取消",
        sequential:true,  //按顺序上传
        sequentialCount:1,  //按顺序上传
        autoSubmit :"false",  //取消自动上传
        acceptFiles:"*" , //限制上传文件格式
        extErrorStr:"上传文件格式不对",
        maxFileCount:10,       //上传文件数量
        maxFileSize:1024*1024, //大小限制1M
        sizeErrorStr:"上传文件不能大于1M", 
        dragDropStr: "<span><b>附件拖放于此</b></span>",
        showFileCounter:false,
        returnType:"json",  //返回数据格式为json
        onSuccess:function(files,data,xhr,pd)  //上传成功事件，data为后台返回数据
        {
            //将返回的上传文件id动态加入的表单中，用于提交表单时返回给后台。
           var newsform = $("#newsform");
           if( data.status==1){
                for( var i=0;i<data.data.length;i++){
                    var inputNode='<input type="hidden" id="'+data.data[i].fileId+'" name="fileIds" value="'+data.data[i].fileId+'" >';
                    newsform.append(inputNode);
                }
            }else{
                alert("上传失败");
            } 
        },
        /* showDelete: true,//删除按钮
        statusBarWidth:600,
        dragdropWidth:600,
            //删除按钮执行的方法
        deleteCallback: function (data, pd) {
             var fileId=data.data[0].fileId;
             $.post("control/news/deleteFile.action", {fileId:fileId},
                    function (resp,textStatus, jqXHR) {
                        alert("delete ok");
                        //alert(textSatus);
              }); 
            //删除input标签
            $("#"+fileId).remove();
            pd.statusbar.hide(); //You choice.
        } */
    });
});
</script> 
</html>