<html>
<head>
<h2>Hello World!</h2>
<script>
    function displayResult(){
        var x=document.getElementById("fname").form.id;
        alert(x);
    }
</script>
</head>
<body>

<form id="form1">
    选择一个文件上传:
    <input type="file" id="fname" size="50"/>
</form>
<button type="button" onclick="displayResult()">显示包含fileupload的表单</button>

</body>
</html>
