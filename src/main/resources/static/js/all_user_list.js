$(function () {
    $search_group = $("#search_group");
    $border = $(".border");
    $resetBTN = $(".resetBTN");
    $resetForm = $("#resetForm");
    $psw = $("#psw");
    $search_group.css("width",300);
    $border.css("height",10);
    $resetBTN.click(function () {
        var newpsw = prompt("请输入新密码：");
        if (newpsw != null) {
            $psw.val(newpsw);
            $resetForm.attr("action",$(this).attr("put_uri")).submit();
        }
        return false;
    });
})