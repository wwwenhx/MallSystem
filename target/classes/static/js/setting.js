
var userId;
/*var user = $.cookie('user');*/
//var userJson = eval(user);
window.onload=function (){
    userId = $.cookie('userId');
    if(userId != null && userId != ''){
        document.getElementById('toLogin').style.display='none';
        document.getElementById('toPersonal').style.display='';
        //$('#userName').val(userJson.name);
    }
    layPage($('#sort').val());
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
    }
}