function fmoney(s, n) {  
    n = n > 0 && n <= 20 ? n : 2;
    if (s == "") {
    	return "";
    }
    if (isNaN(s)){
    	return "";
    }
    s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";  
    var l = s.split(".")[0].split("").reverse(), r = s.split(".")[1];  
    t = "";  
    for (i = 0; i < l.length; i++) {  
        t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");  
    }  
    return t.split("").reverse().join("") + "." + r;  
}

function isFormEdited(form) {
    for ( var j = 0; j < form.length; ++j) {
        if (isEdited(form.elements[j])) {
            return true;
        }
    }
    return false;
}

// 判断画面的值是否改变
function isEdited(item) {
    var type = item.type;
    if (!type) {
        return false;
    }
    type = type.toUpperCase();
    if (type == 'TEXT') {
        return item.defaultValue != item.value;
    }
    if (type == 'HIDDEN') {
        return item.defaultValue != item.value;
    }
    if (type == 'TEXTAREA') {
        return item.defaultValue != item.value;
    }
    if (type == 'CHECKBOX') {
        return item.defaultChecked != item.checked;
    }
    if (type ==  'RADIO') {
        return item.defaultChecked != item.checked;
    }
    if (type == 'SELECT-ONE') {
           for (var i = 0; i < item.options.length; i++ ) {
               if (item.options[i].defaultSelected != item.options[i].selected) {
                   return true;
               }
           }
    }
    return false;
}


// 下拉框没有defaultValue
function initializeSelectOne(){
    for (var i = 0; i < document.forms.length; ++i) {
       for (var j = 0; j < document.forms[i].length; ++j) {
          type = document.forms[i].elements[j].type.toUpperCase();
           if ( type == 'SELECT-ONE' ) {
               var flg = 'false';
               for (var k = 0; k < document.forms[i].elements[j].options.length; k++ ) {
                   if(document.forms[i].elements[j].options[k].defaultSelected ){
                       flg = 'true';
                   }
               }
               if(flg === 'false'){
                   document.forms[i].elements[j].options[0].defaultSelected=true;
               }
           }
       }
   }
}

// 下拉框是js改变的情况
function initializeSelectOneForJs(){
    for (var i = 0; i < document.forms.length; ++i) {
       for (var j = 0; j < document.forms[i].length; ++j) {
          type = document.forms[i].elements[j].type.toUpperCase();
           if ( type == 'SELECT-ONE' ) {
               var flg = 'false';
               for (var k = 0; k < document.forms[i].elements[j].options.length; k++ ) {
                   if(document.forms[i].elements[j].options[k].selected ){
                	   document.forms[i].elements[j].options[k].defaultSelected=true;
                   }
               }
           }
           if (type == 'TEXT') {
               document.forms[i].elements[j].defaultValue = document.forms[i].elements[j].value;
           }
       }
   }
}

// 初期化多选框的defaultValue
function initMultiselect() {
    for (var i = 0; i < document.forms.length; ++i) {
        for (var j = 0; j < document.forms[i].length; ++j) {
           type = document.forms[i].elements[j].type.toUpperCase();
            if ( type == 'CHECKBOX' ) {
                if(document.forms[i].elements[j].checked){
                	document.forms[i].elements[j].defaultChecked=true;
                }
            }
        }
    }
}


