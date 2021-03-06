function DateDifference(sDate, eDate) {
    var char;
    if (sDate.indexOf('-') != -1) {
        char = '-';
    }
    else {
        char = '/';
    }
    var oDate1 = sDate.split(char);
    if (eDate.indexOf('-') != -1) {
        char = '-';
    }
    else {
        char = '/';
    }
    var oDate2 = eDate.split(char);
    var strDateS = new Date(oDate1[0], oDate1[1] - 1, oDate1[2]);
    var strDateE = new Date(oDate2[0], oDate2[1] - 1, oDate2[2]);
    return parseInt(Math.abs(strDateS - strDateE) / 1000 / 60 / 60 / 24);
}

//返回上一级
function GoBack() {
    window.history.go(-1);
}

//字符串替换全部
String.prototype.replaceAll = function (s1, s2) {
    return this.replace(new RegExp(s1, "gm"), s2);
}
//去掉首尾空格
String.prototype.trim = function () {
    return this.replace(/(^\s*)|(\s*$)/g, "");
};

//首字母小写
String.prototype.toLowerFirst = function () {
    if (this == null || this == '') return this;
    if (this.length == 1) return this.toLowerCase();
    return this.substring(0, 1).toLowerCase() + this.substring(1);
};

//首字母大写
String.prototype.toUpperFirst = function () {
    if (this == null || this == '') return this;
    if (this.length == 1) return this.toUpperCase();
    return this.substring(0, 1).toUpperCase() + this.substring(1);
};

//去掉字符串中下划线
String.prototype.replaceUnderline = function () {
    var org = "_";
    var oldString = this;
    var newString = "";
    var first = 0;
    while (oldString.indexOf(org) != -1) {
        first = oldString.indexOf(org);
        if (first != oldString.Length) {
            newString = newString + oldString.substring(0, first);
            oldString = oldString.substring(first + org.length);
            oldString = oldString.toUpperFirst();
        }
    }
    newString = newString + oldString;
    return newString;
};


//设为首页
function SetHome(obj, url) {
    try {
        obj.style.behavior = 'url(#default#homepage)';
        obj.setHomePage(url);
    } catch (e) {
        if (window.netscape) {
            try {
                netscape.security.PrivilegeManager.enablePrivilege("UniversalXPConnect");
            } catch (e) {
                alert("抱歉，此操作被浏览器拒绝！\n\n请在浏览器地址栏输入“about:config”并回车然后将[signed.applets.codebase_principal_support]设置为'true'");
            }
        } else {
            alert("抱歉，您所使用的浏览器无法完成此操作。\n\n您需要手动将【" + url + "】设置为首页。");
        }
    }
}
//收藏
function AddFavorite(title, url) {
    try {
        window.external.addFavorite(url, title);
    }
    catch (e) {
        try {
            window.sidebar.addPanel(title, url, "");
        }
        catch (e) {
            alert("抱歉，您所使用的浏览器无法完成此操作。\n\n加入收藏失败，请进入新网站后使用Ctrl+D进行添加");
        }
    }
}

//获取url参数
function Request(strName) {

    var strHref = location.href;
    var intPos = strHref.indexOf("?");

    var strRight = strHref.substr(intPos + 1);

    var arrTmp = strRight.split("&");

    for (var i = 0; i < arrTmp.length; i++) {

        var arrTemp = arrTmp[i].split("=");

        if (arrTemp[0].toUpperCase() == strName.toUpperCase()) return arrTemp[1];

    }

    return "";
}

$.fn.ChangeColor = function (style) {
    var oldColor = $(this).css(style);
    if (oldColor == null) {
        return;
    }
    var rgb = oldColor.split('rgb(').join('').split(')').join("").split(', ');

    var r = parseInt(rgb[0]);
    var g = parseInt(rgb[1]);
    var b = parseInt(rgb[2]);

    var px = 30;

    if (r + g + b > 382) {
        r = r > px ? r - px : r;
        g = g > px ? g - px : g;
        b = b > px ? b - px : b;
    } else {
        r = r > 255 - px ? r : r + px;
        g = g > 255 - px ? g : g + px;
        b = b > 255 - px ? b : b + px;
    };
    $(this).hover(function () {
        $(this).css(style, "#" + ((1 << 24) + (r << 16) + (g << 8) + b).toString(16).slice(1));
    }, function () {
        $(this).css(style, oldColor);
    });
};

//判断是否为数字
function IsNumber(obj) {
    try {
        return !isNaN(obj);
    } catch(e){
        return false;
    }
}

//循环滚动，可通过iScrollAmount=0来暂停滚动
//AutoScroll('ulNotice', 21, data.Content.length, 1);
function AutoScroll(element, lineheight, linecount, scrollAmount) {
    function run() {
        oMarquee.scrollTop += iScrollAmount;
        if (oMarquee.scrollTop == iLineCount * iLineHeight) oMarquee.scrollTop = 0;
        if (oMarquee.scrollTop % iLineHeight == 0) { window.setTimeout(run, intervalTime); }
        else { window.setTimeout(run, speed); }
    }

    var speed = 10;//滚动时间，越小越快
    var intervalTime = 2500;//滚动间隔时间
    var oMarquee = (typeof element == 'string') ? document.getElementById(element) : element; //滚动对象 
    var iLineHeight = lineheight; //单行高度，像素 
    var iLineCount = linecount; //实际行数 
    var iScrollAmount = scrollAmount; //每次滚动高度，像素 

    oMarquee.innerHTML += oMarquee.innerHTML;
    window.setTimeout(run, 2000);
}

//动态翻页
function scrollPage(element, delay, speed, lineHeight) {
    var numpergroup = 5;
    var slideBox = (typeof element == 'string') ? document.getElementById(element) : element;
    var delay = delay || 1000;
    var speed = speed || 20;
    var lineHeight = lineHeight || 20;
    var tid = null, pause = false;
    var liLength = slideBox.getElementsByTagName('li').length;
    var lack = numpergroup - liLength % numpergroup;
    for (i = 0; i < lack; i++) {
        slideBox.appendChild(document.createElement("li"));
    };
    var start = function () {
        tid = setInterval(slide, speed);
    };
    var slide = function () {
        if (pause) return;
        slideBox.scrollTop += 2;
        if (slideBox.scrollTop % lineHeight == 0) {
            clearInterval(tid);
            for (i = 0; i < numpergroup; i++) {
                slideBox.appendChild(slideBox.getElementsByTagName('li')[0]);
            }
            slideBox.scrollTop = 0;
            setTimeout(start, delay);
        }
    };
    slideBox.onmouseover = function () { pause = true; };
    slideBox.onmouseout = function () { pause = false; };
    setTimeout(start, delay);
}

//根据ID选中元素内文字
function SelectText(element) {
    if (element.length == 0) return;
    if (element.createTextRange) {//IE浏览器
        var range = document.body.createTextRange();
        range.moveToElementText(this);
        range.select();
    } else if (window.getSelection) {//非IE浏览器
        var selection = window.getSelection();
        var range = document.createRange();
        range.selectNodeContents(element[0]);
        selection.removeAllRanges();
        selection.addRange(range);
    }
}

//生成GUID字符串
function newGuid() {
    var d = new Date().getTime();
    var uuid = 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function (c) {
        var r = (d + Math.random() * 16) % 16 | 0;
        d = Math.floor(d / 16);
        return (c == 'x' ? r : (r & 0x3 | 0x8)).toString(16);
    });
    return uuid;
};