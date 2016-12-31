/**
 * Created by leizi on 2016/11/14.
 */
var AdsUrl = "http://127.0.0.1/api";
var count  = 0;
document.write('<script src="jquery-3.1.1.min.js"></script>');
document.write('<script src="jquery.cookie.js"></script>');
console.log("This js script is developed by wu leizi");
var putIdArray = new Array();
var adsIdArray = new Array();
function isNewUser(){
    if ($.cookie('cookieId') == null){
        return true;
    }
    else{
        return false;
    }
}
function adsJsonHandler(data){
    console.log(data.AdsId);
    var expiresDate= new Date();
    expiresDate.setTime(expiresDate.getTime() + (30 * 24 * 3600 * 1000));
    $.cookie('cookieId', data.AdsId, {expires: expiresDate});
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
        console.log("success");
        $.ajax({
            timeout: 1,
            type : "GET",
            url : ".put.gif?cookieId=" + $.cookie('cookieId') + "&putId=" + value.id + "&type=" + value.adsId + "&adsId=" + value.adsId,
        });
    })

}
function adsJsonHandlerError(){
    for (var i = 0; i < count; i++){
        document.getElementById("ads"+i).innerHTML = "Load failed.";
        $.ajax({
            timeout: 1000,
            type : "GET",
            url : ".failed.gif?type=test" ,
        })
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
    console.log(data.AdsId);
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
        console.log("success");
        $.ajax({
            timeout: 1,
            type : "GET",
            url : ".put.gif?cookieId=" + $.cookie('cookieId') + "&putId=" + value.id + "&type=" + value.adsId + "&adsId=" + value.adsId,
        });
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
        //url : AdsUrl,
        //dataType : "json",
        timeout: 1000,
        type : "GET",
        url : ".click.gif?cookieId=" + $.cookie('cookieId') + "&putId=" + putId + "&type=" + adsId + "&adsId=" + adsId ,
        //type : "POST",
        //data: JSON.stringify({"cookieId" : $.cookie('cookieId'), "putId" : putId, "type" : adsId, "adsId" : adsId}),
        success : function (){console.log("click commit");},
        error: function (){console.log("click commit failed");}
    })
}