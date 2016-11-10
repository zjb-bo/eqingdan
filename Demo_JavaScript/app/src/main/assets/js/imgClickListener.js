javascript:(function(){
    var imgTags = document.getElementsByTagName("img");
    for(var i = 0;i < imgTags.length;i++){
        imgTags[i].onclick = function(){
            BWF.clickImg(this.src);
        }
    }
})()