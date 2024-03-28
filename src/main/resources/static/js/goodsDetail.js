
function getGoodsDetails(){
    $.ajax({
        url:"/indexService/getGoodsDetails",
        data:{
            id:$('#id').val()
        },
        dataType:"json",
        type:"post",
        success:function (data){
            var dataJson = eval(data);
            var goodsDetail = dataJson.body;
            let img = $('<img style="width: 350px;height: 400px; class="img" src="'+ goodsDetail.url+'">');
            let name = $('<td>' + goodsDetail.name + '</td>');
            let price = $('<td>' + goodsDetail.price + '</td>')
            let synopsis = $('<td>' + goodsDetail.synopsis + '</td>');

            $('#img').append(img);
            $('#name').append(name);
            $('#price').append(price);
            $('#synopsis').append(synopsis);
        },
        error:function (){
            alert("系统出现问题");
            window.close();
        }
    });
};




var id;

/*var user = $.cookie('user');*/
//var userJson = eval(user);
window.onload=function (){
    var userId = $.cookie('userId');
    if(userId != null && userId != ''){
        document.getElementById('toLogin').style.display='none';
        document.getElementById('toPersonal').style.display='';
        //$('#userName').val(userJson.name);
    }
    id = $('#id').val();
    getGoodsComment();
    getGoodsDetails();
}

function addGoods(){
    if(userId == null || userId == ""){
        alert("请登录");
        return;
    }
    $.ajax({
        url:"/indexService/addGoods",
        data:{
            id:id,
            userId:$.cookie('userId')
        },
        dataType:"json",
        type:"post",
        success:function (data){
            var dataJson = eval(data);
            alert(dataJson.msg);
        },
        error:function (){
            alert("请求失败");
        }
    });
}

//获取用户评论
function getGoodsComment(){
    $.ajax({
        url:'/goodsService/getGoodsComment',
        data:{
            goodsId:$('#id').val()
        },
        dataType:'json',
        type:'post',
        success:function (data){
            let jsonData = eval(data);
            if(jsonData.code == 200){
                let commentList = jsonData.body;
                $.each(commentList,function (i, val){
                    let tr = $('<tr></tr>');
                    let name = $('<td>' + val.name + '</td>');
                    let time = $('<td>' + val.createTime + '</td>');
                    let comment = $('<td>' + val.comment + '</td>');
                    tr.append(name,time,comment);
                    $('#tbody').append(tr);
                });
            }
            if (jsonData.code == 400){

            }
        },
        error:function (){
            alert("请求失败");
        },
    });
}