$('login').submit(function(){
	let data = $(this).serialize();
	$.ajax({
            type:'post',
            url:'/employee/employeeLogin',
            data:$form.serialize(),
            dataType:'json',
            success:function (data) {
                //业务成功
                if(data.result == true){
                    //跳转后台的首页
                    location.href = '/main.jsp';
                } 
            }
        });
       return false; 
})