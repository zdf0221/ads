/**
 * Created by leizi on 2016/11/14.
 * 定义广告的标识符是Ads+id 此标签属性为保留字
 * cookie中判断是否有广告信息是AdsId
 */
var AdsUrl = "http://127.0.0.1/api";
var count  = 0;
//document.write('<script src="//code.jquery.com/jquery-3.1.1.min.js" async></script>');
//document.write('<script src="//cdn.bootcss.com/jquery-cookie/1.4.1/jquery.cookie.min.js" async></script>');
document.write('<script src="jquery-3.1.1.min.js"></script>');
document.write('<script src="jquery.cookie.js"></script>');
console.log("This js script is developed by wu leizi");
var putIdArray = new Array();
var adsIdArray = new Array();
function isNewUser(){
    //  判断是否为新用户
    if ($.cookie('cookieId') == null){
        return true;
    }
    else{
        return false;
    }
}
function adsJsonHandler(data){
    console.log(data.AdsId);
    $.cookie('cookieId', data.AdsId);
    $.each(data.AdsItem, function(key, value){
        //console.log(key+"->"+value.id);
        putIdArray[key] = value.id;
        document.getElementById("ads"+key).innerHTML = "<a href='" + value.url+ "' target='_blank'>"+value.content+"</a>";
        document.getElementById("ads"+key).onclick = function() {
            var index = parseInt(this.id.substr(3,5));
            var putId = putIdArray[index];
            var adsId = adsIdArray[index];
            console.log(putId);
            eventClick(putId,adsId);
        };
    })
}
function adsJsonHandlerError(){
    for (var i = 0; i < count; i++){
        document.getElementById("ads"+i).innerHTML = "Load failed.";
    }
}
function getAdsId(){
    $.ajax({
        url : AdsUrl,
        dataType: "json",
        timeout: 1000,
        type : "POST",
        data: JSON.stringify({ "size" : count}),
        success: adsJsonHandler,
        error: adsJsonHandlerError
    });
}
function testAdsJsonHander(data){
    $.each(data, function(key, value){
        //console.log(key+"->"+value.id);
        putIdArray[key] = value.id;
        adsIdArray[key] = value.adsId;
        document.getElementById("ads"+key).innerHTML = "<a href='" + value.url+ "' target='_blank'>"+value.content+"</a>";
        document.getElementById("ads"+key).onclick = function() {
            var index = parseInt(this.id.substr(3,5));
            var putId = putIdArray[index];
            var adsId = adsIdArray[index];
            console.log(putId);
            eventClick(putId,adsId);
        };
    })
}
function getAdsJson(){
    $.ajax({
        url : AdsUrl,
        dataType : "json",
        timeout: 1000,
        type : "POST",
        data: JSON.stringify({"cookieId" : $.cookie('cookieId'), "size" : count}),
        success : testAdsJsonHander,
        error: adsJsonHandlerError
    })
}
function update(){
    if (isNewUser()){
        getAdsId();
    }
    else{
        getAdsJson();
    }
}
function Advertising(){
    document.write('<p id="ads' + count + '">loading</p>');
    count ++;
}
function eventClick(putId, adsId){
    $.ajax({
        url : AdsUrl,
        dataType : "json",
        timeout: 1000,
        type : "POST",
        data: JSON.stringify({"putId" : putId, "adsId" : adsId}),
        success : function (){console.log("click commit");},
        error: function (){console.log("click commit failed");}
    })
}