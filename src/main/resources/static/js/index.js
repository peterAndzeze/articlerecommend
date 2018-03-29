function  getCronInfo(id) {
   location.href="/getQuartzInfo?id="+id;
}

/**
 * 停止任务
 * @param id 编号
 */
function stopTask(id) {
    $.ajax({
        url: "/paused",  // 请求连接
        type: 'POST',  // 请求类型
        data: {"id":id},  // post时请求体
        dataType: 'json',  // 返回请求的类型，有text/json两种
        jsonp: 'callback',  // jsonp请求的标志，一般不改动
        jsonpCallback: 'jsonpCallback',  //jsonp请求的函数名
        async: true,   // 是否异步
        cache: true,   // 是否缓存
        timeout:null,  // 设置请求超时
        contentType: 'application/x-www-form-urlencoded',
        success: function(result){
            alert(result)
        },  // 请求成功回调函数
        fail:function (result) {
            alert(result)
        }   // 请求失败回调

    });

}

function  startTask(id) {
    $.ajax({
        url: "/resume",  // 请求连接
        type: 'POST',  // 请求类型
        data: {"id":id},  // post时请求体
        dataType: 'json',  // 返回请求的类型，有text/json两种
        jsonp: 'callback',  // jsonp请求的标志，一般不改动
        jsonpCallback: 'jsonpCallback',  //jsonp请求的函数名
        async: true,   // 是否异步
        cache: true,   // 是否缓存
        timeout:null,  // 设置请求超时
        contentType: 'application/x-www-form-urlencoded',
        success: function(result){
            alert(result)
        },  // 请求成功回调函数
        fail:function (result) {
            alert(result)
        }   // 请求失败回调

    });
}

function deleteQuartz(id){
    /*$.ajax({
        url: "/resume",  // 请求连接
        type: 'POST',  // 请求类型
        data: {"id":id},  // post时请求体
        dataType: 'json',  // 返回请求的类型，有text/json两种
        jsonp: 'callback',  // jsonp请求的标志，一般不改动
        jsonpCallback: 'jsonpCallback',  //jsonp请求的函数名
        async: true,   // 是否异步
        cache: true,   // 是否缓存
        timeout:null,  // 设置请求超时
        contentType: 'application/x-www-form-urlencoded',
        success: function(result){
            alert(result)
        },  // 请求成功回调函数
        fail:function (result) {
            alert(result)
        }   // 请求失败回调

    });*/

    location.href="/deleteQuartz?id="+id;

}
function addQuartz() {
    location.href="/addQuartz";
}
