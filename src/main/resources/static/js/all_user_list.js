$(function () {
    $search_group = $("#search_group");
    $border = $(".border");
    $resetBTN = $(".resetBTN");
    $resetForm = $("#resetForm");
    $delBTN = $(".deleteBTN");
    $delForm = $("#delForm");
    $errMsg = $("#errMsg");
    $psw = $("#psw");
    $search_group.css("width",300);
    $border.css("height",10);
    if ($errMsg.val() != null) {
        alert($errMsg.val())
    }
    $resetBTN.click(function () {
        var newpsw = prompt("请输入新密码：");
        if (newpsw != null) {
            $psw.val(newpsw);
            $resetForm.attr("action",$(this).attr("put_uri")).submit();
        }
        return false;
    });
    $delBTN.click(function () {
        $delForm.attr("action",$(this).attr("del_uri")).submit();
        return false;
    });
})