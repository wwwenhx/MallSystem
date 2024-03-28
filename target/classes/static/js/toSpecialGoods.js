var userId;
/*var user = $.cookie('user');*/
//var userJson = eval(user);

var type = [[${type}]];
function layPage(){
    //分页显示商品
    layui.use(['jquery', 'layer',  'laypage'], function () {
        var $ = layui.jquery,
            layer = layui.layer,
            laypage = layui.laypage;

        var page = 1;//当前页
        var limit = 8;//每页显示的数目
        var resCount, resData ;
        var resPage = renderPage1();
        //渲染展示商品的html页面
        function renderProductHtml(data){
            $('#product').empty();
            let str = "";//用来存储html内容
            if(data.length > 0){
                $.each(data, function(i, val){
                    let div = $('<div style="float: left;padding-left: 12%;padding-top: 3%;width: 150px;height:300px;overflow: hidden;"></div>');
                    let a = $('<a href="javascript:;" onclick="to('+val.id+')"></a>')
                    let img = $('<img style="width: 150px; height: 200px;display: block;vertical-align: middle;" src="' + val.url + '">');
                    let name = $('<p style="padding-top: 1%;">' + val.name + '</p>');
                    let price = $('<p style="padding-top: 1%;">¥' + val.price + '</p>');
                    let sale = $('<p style="padding-top: 1%;">销量:' + val.sale + '</p>');
                    a.append(img,name,price,sale);
                    div.append(a);
                    $('#product').append(div);
                });
            }
        }

        //同步加载商品数据
        function renderProduct(page, limit){
            $.ajax({
                async: false,
                url: '/indexService/getSpecialGoods',
                data: {"page": page, "limit": limit,"name":$('#name').val(),"type":type},
                dataType: 'json',
                success: function(result){
                    console.info(result);
                    resCount = result.count;
                    resData = result.data;
                    renderProductHtml(resData);
                }
            });
        }


        //分页的完整功能
        function renderPage1(){
            renderProduct(page, limit);
            laypage.render({
                elem: 'layuipage'
                ,count: resCount
                ,limit: limit
                ,curr: page
                ,theme: '#FFB800'
                ,layout: ['count', 'prev', 'page', 'next',  'refresh', 'skip']
                ,jump: function(obj, first){
                    console.info(obj);
                    page = obj.curr;
                    limit = obj.limit;
                    console.log(page);
                    if(!first){
                        renderProduct(page, limit);
                    }
                }
            });
        }
    });
};

$('#search').on('click',function (){
    layPage();
});
window.to = function (id){
    window.open('/indexService/goodsDetail?id='+id);
}

window.onload=function (){
    userId = $.cookie('userId');
    if(userId != null && userId != ''){
        document.getElementById('toLogin').style.display='none';
        document.getElementById('toPersonal').style.display='';
        //$('#userName').val(userJson.name);
    }
    layPage();
}
function toLogin(){
    layer.open({
        type: 2,//iframe层
        title: "登陆/注册",//标题
        area: ['40%', '550px'],//弹出区域
        content: 'account',//所需弹出的登录/注册界面
        offset: '50px',//设置弹出层在距离顶部，100px 水平居中
        end: function (){
            window.location.reload();
            return false;
        }
    });
}

function loginOut(){
    $.removeCookie('userId',{path:'/'});
    $.removeCookie('user',{path:'/'});
    window.location.href = "/indexService/index";
}

function toShop(){
    if(userId != null){
        window.location.href = "/indexService/shop";
    }else {
        alert("请登录");
        window.location.reload();
    }
}