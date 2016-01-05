// JavaScript Document
//$(document).ready(function($){

	/*var aForm = document.getElementsByTagName("form");
	//多个表单
	for (var i = 0; i < aForm.length; i++) WellForm(aForm[i]);*/

//});

//获取class
function getClass(sClass, oParent)
{
	var aClass = [];	
	var reClass = new RegExp("(^| )" + sClass + "( |$)");
	var aElem = (oParent || document).getElementsByTagName("*");
	for (var i = 0; i < aElem.length; i++) reClass.test(aElem[i].className) && aClass.push(aElem[i]);
	return aClass;
}
//class是否存在
function hasClass(obj, sClass)
{
	var reg = new RegExp("(^|\\s)" + sClass + "(\\s|$)");
	return reg.test(obj.className);
}
//添加class
function addClass(obj, sClass)
{
	hasClass(obj, sClass) || (obj.className += " "+sClass);
}
//删除class
function removeClass(obj, sClass)
{
	if (hasClass(obj, sClass))
	{
		var reg = new RegExp("(^|\\s)" + sClass + "(\\s|$)");
		obj.className = obj.className.replace(reg, "");
	}
}
//上一个元素
function prevElement(obj)
{
	return obj.previousSibling || obj.previousElementSibling || null;
}
//下一个元素
function nextElement(obj)
{
	return obj.nextSibling || obj.nextElementSibling || null;
}

//自定义表单函数
function WellForm(form)
{
	var i = 0;
	var zIndex = 1;
	var aInput = form.getElementsByTagName("input");	
	var aSelect = form.getElementsByTagName("select");
	var aTextArea = form.getElementsByTagName("textarea");
	form.className = "WellForm";
	
	/* 单行文本框
	var aText = [];
	for (i = 0; i < aInput.length; i++) (aInput[i]["type"] == "text" || aInput[i]["type"] == "password") && aText.push(aInput[i]);
	
	for (i = 0; i < aText.length; i++)
	{
		var oTextL = document.createElement("div");
		var oTextR = document.createElement("div");	
		oTextL.className = "TextL";
		oTextR.className = "TextR";
		aText[i].className = "WellText";
		aText[i].parentNode.insertBefore(oTextL, aText[i]);		
		aText[i].parentNode.insertBefore(oTextR, nextElement(aText[i]));
		//获取焦点
		aText[i].onfocus = function ()
		{
			addClass(this, "WellTextH");
			addClass(prevElement(this), "TextLH");
			addClass(nextElement(this), "TextRH")
		};
		//失去焦点
		aText[i].onblur = function ()
		{
			removeClass(this, "WellTextH");
			removeClass(prevElement(this), "TextLH");
			removeClass(nextElement(this), "TextRH")
		}
	}
	 */
	/* 多行文本框 
	for (i = 0; i < aTextArea.length; i++)
	{
		var oTextArea = document.createElement("div");
		oTextArea.className = "WellTextArea";
		
		//上边框
		var oT = document.createElement("div");
		var oTL = document.createElement("div");
		var oTM = document.createElement("div");		
		oT.className = "T";
		oTL.className = "TL";
		oTM.className = "TM";		
		oTL.appendChild(oTM);
		oT.appendChild(oTL);
		//中间边框
		var oM = document.createElement("div");
		var oMR = document.createElement("div");		
		oM.className = "M";
		oMR.className = "MR";		
		oM.appendChild(oMR);
		//下边框
		var oB = document.createElement("div");
		var oBL = document.createElement("div");
		var oBM = document.createElement("div");		
		oB.className = "B";
		oBL.className = "BL";
		oBM.className = "BM";		
		oBL.appendChild(oBM);
		oB.appendChild(oBL);		
		//插入结构
		aTextArea[i].parentNode.insertBefore(oTextArea, aTextArea[i]);		
		oMR.appendChild(aTextArea[i]);
		oTextArea.appendChild(oT);		
		oTextArea.appendChild(oM);
		oTextArea.appendChild(oB);		
		oTextArea.style.width = oMR.offsetWidth + "px";
		//获取焦点
		aTextArea[i].onfocus = function ()
		{
			var M = this.parentNode.parentNode;
			addClass(M, "MH");
			addClass(prevElement(M), "TH");
			addClass(nextElement(M), "BH")
		};
		aTextArea[i].onblur = function ()
		{
			var M = this.parentNode.parentNode;
			removeClass(M, "MH");
			removeClass(prevElement(M), "TH");
			removeClass(nextElement(M), "BH")
		}
	}
	*/	
	/* 单选框 */
	/*var aRadio = [];
	for (i = 0; i < aInput.length; i++) aInput[i]["type"] == "radio" && aRadio.push(aInput[i]);
	
	for (i = 0; i < aRadio.length; i++)
	{
		var oRadio = document.createElement("div");
		oRadio.className = "WellRadio";	
		
		aRadio[i].parentNode.insertBefore(oRadio, aRadio[i]);
		oRadio.appendChild(aRadio[i]);
		
		aRadio[i].checked && addClass(aRadio[i].parentNode, "WellRadioH");
		
		oRadio.onclick = function ()
		{
			var siblings = getClass("WellRadio", this.parentNode);
			for (i = 0; i < siblings.length; i++)
			{
				removeClass(siblings[i], "WellRadioH");
				siblings[i].children[0].checked = false;
			}
			addClass(this, "WellRadioH");
			this.children[0].click();
			this.children[0].checked = true;
		}
	}*/
	
	/* 复选框 
	var aCheckBox = [];
	for (i = 0; i < aInput.length; i++) aInput[i]["type"] == "checkbox" && aCheckBox.push(aInput[i]);
	
	for (i = 0; i < aCheckBox.length; i++)
	{
		var oCheckBox = document.createElement("div");
		oCheckBox.className = "WellCheckBox";	
		
		aCheckBox[i].parentNode.insertBefore(oCheckBox, aCheckBox[i]);
		oCheckBox.appendChild(aCheckBox[i]);
		
		aCheckBox[i].checked && addClass(aCheckBox[i].parentNode, "WellCheckBoxH");
		
		oCheckBox.onclick = function ()
		{			
			this.children[0].checked = !this.children[0].checked;
			this.children[0].checked ? addClass(this, "WellCheckBoxH") : removeClass(this, "WellCheckBoxH")
		}
	}
	*/
	/* 按钮
	var aButton = [];
	for (i = 0; i < aInput.length; i++) (aInput[i]["type"] == "button" || aInput[i]["type"] == "submit") && aButton.push(aInput[i]);
	
	for (i = 0; i < aButton.length; i++)
	{
		var oBtnL = document.createElement("div");
		var oBtnR = document.createElement("div");		
		oBtnL.className = "ButtonL";
		oBtnR.className = "ButtonR";		
		aButton[i].className = "WellButton";
		aButton[i].parentNode.insertBefore(oBtnL, aButton[i]);
		aButton[i].parentNode.insertBefore(oBtnR, nextElement(aButton[i]));
		//鼠标移入
		aButton[i].onmouseover = function ()
		{
			addClass(this, "WellButtonH");
			addClass(prevElement(this), "ButtonLH");
			addClass(nextElement(this), "ButtonRH");	
		};
		//鼠标移出
		aButton[i].onmouseout = function ()
		{
			removeClass(this, "WellButtonH");
			removeClass(prevElement(this), "ButtonLH");
			removeClass(nextElement(this), "ButtonRH");	
		}
	}
	 */
	/* 下拉菜单 }*/
	for (i = 0; i < aSelect.length; i++)
	{
		var oFragment = document.createDocumentFragment();
		var oSelectL = document.createElement("div");
		var oSelectR = document.createElement("div");
		var oWellSelect = document.createElement("div");
		var oEm = document.createElement("em");
		var oEmH = document.createElement("em");
		var oUl = document.createElement("ul");
		oSelectL.className = "SelectL";
		oSelectR.className = "SelectR";
		oWellSelect.className = "WellSelect";		
		//插入结构
		aSelect[i].parentNode.insertBefore(oSelectL, aSelect[i]);
		aSelect[i].parentNode.insertBefore(oSelectR, nextElement(aSelect[i]));
		oUl.style.width = oWellSelect.style.width =  aSelect[i].offsetWidth + 16 + "px";
		
		for (var j = 0; j < aSelect[i].options.length; j++)
		{
			var oLi = document.createElement("li");
			oLi.innerHTML = aSelect[i].options[j].text;
			oLi["sValue"] = aSelect[i].options[j].value;
			oFragment.appendChild(oLi);
			aSelect[i].options[j].selected && (oEm.innerHTML = aSelect[i].options[j].text);
			if (aSelect[i].options[j].selected){
				oEm.innerHTML = aSelect[i].options[j].text;
			}
		}
		oEmH.onpropertychange = aSelect[i].onchange;
		oEmH.style.display = "none";
		oUl.appendChild(oFragment);
		oWellSelect.appendChild(oEmH);
		oWellSelect.appendChild(oEm);
		oWellSelect.appendChild(oUl);
		aSelect[i].parentNode.insertBefore(oWellSelect, aSelect[i]);
		oWellSelect.appendChild(aSelect[i]);
		
		oWellSelect.onclick = oSelectR.onclick = function (event)
		{
			var o = this.getElementsByTagName("ul")[0] || prevElement(this).getElementsByTagName("ul")[0];
			var aUl = form.getElementsByTagName("ul");
			this.parentNode.style.position = "relative";
			this.parentNode.style.zIndex = zIndex++;
			o.style.display = o.style.display == "block" ? "none" : "block";
			for (i = 0; i < aUl.length; i++)
			{
				if (o == aUl[i]) continue;
				aUl[i].style.display = "none";
			}
			var aLi = o.getElementsByTagName("li");
			
			for (i = 0; i < aLi.length; i++)
			{
				aLi[i].onmouseover = function ()

				{
					this.className = "hover";	
				};
				aLi[i].onmouseout = function ()
				{
					this.className = ""	;
				};
				aLi[i].onclick = function ()
				{
					prevElement(this.parentNode).innerHTML = this.innerHTML;
					nextElement(this.parentNode).value = this.sValue;
					prevElement(prevElement(this.parentNode)).innerHTML = this.sValue;
				}
			}
			
			(event || window.event).cancelBubble = true;
			
			document.onclick = function ()
			{
				o.style.display = "none";	
			}
		}
	}
}


//******************************************************************************
/*
 * 数値項目からフォーカスが離れた場合の処理
 *
 * @param obj   対象オブジェクト
 * @param intBu 整数の最大桁数
 * @param decBu 小数の最大桁数
 */
//******************************************************************************
function com_item_blurNumItem(obj, intBu, decBu) {
    obj.value = com_item_groupNum(obj.value, intBu, decBu);
}

//******************************************************************************
/*
 * 数値項目のカンマ編集を行います。
 * ※小数点、-符号は考慮してます。
 *
 * @param strVal 対象文字列
 * @param intBu  整数の最大桁数
 * @param decBu  小数の最大桁数
 */
//******************************************************************************
function com_item_groupNum(strVal, intBu, decBu) {
    if (intBu == null) {
        intBu = 0;
    }
    if (decBu == null) {
        decBu = 0;
    }

    //オリジナル文字列
    var orjVal = strVal;

    //カンマ編集解除を取得
    var objVal = com_item_resetGroupNum(strVal);

    //トリム
    objVal = com_item_trim(objVal);

    var temp = "";

    if (objVal == null || objVal.length == 0) {
        //Null、空はそのまま
        return orjVal;
    }

    if (objVal == ".") {
        objVal = "0.0";
    }

    if (!com_chk_checkMinusDec( objVal ) || isNaN(objVal)) {
        //数値以外はそのまま
        return orjVal;
    }

    //数値変換(0サプレス)して文字列変換
    objVal = objVal - 0 + "";

    if (0 <= objVal.indexOf("-")) {
        //-符号以降を取得
        temp = "-";
        objVal = objVal.substring(1);
    }

    var dec = objVal.indexOf(".") - 0;
    var intStr = objVal;                //整数部の文字列
    var decStr = "";                    //小数部の文字列
    var i = 0;                          //ループカウント用

    if (0 <= dec) {
        //小数点入力あり
        st = dec - 1 ;
        decStr = objVal.substring(dec + 1);     //小数点以降を取得
        intStr = objVal.substring(0, dec);      //整数部の文字列
    }

    if (intBu < intStr.length) {
        //指定された整数部より大きい場合 → 無編集で終わり
        return orjVal;
    }

    if (decBu < decStr.length) {
        //指定された小数部より大きい場合 → 無編集で終わり
        return orjVal;
    } else if (decStr.length < decBu) {
        //指定された小数部より小さい場合 → 後ろに0を付ける
        var zeroCnt = decBu - decStr.length;
        for (i = 0; i < zeroCnt; i++) {
            decStr += "0";
        }
        decStr = "." + decStr;
    } else if (decBu != 0 && (decStr.length == decBu)) {
        decStr = "." + decStr;
    }

    var result = "";
    var cnt = 0;
    for (i = intStr.length - 1; i >= 0; i--) {
        if (cnt == 3) {
            result = "," + result;
            cnt = 0;
        }
        result = intStr.charAt(i) + result;

        cnt++;
    }
    return temp + result + decStr;
}
//******************************************************************************
/*
 * 数値項目にフォーカスが移った場合の処理
 *
 * @param obj 対象オブジェクト
 */
//******************************************************************************
function com_item_focusNumItem(obj) {
    if (obj.readOnly) {
        return false;
    }else {
        obj.value = com_item_resetGroupNum(obj.value);
        obj.select();
    }
}

//******************************************************************************
/*
 * カンマ編集を解除します。
 *
 * @param txtValue 変換対象文字列
 * @return カンマ編集を解除した文字列
 */
//******************************************************************************
function com_item_resetGroupNum(txtValue) {
    var objVal = txtValue + "";
    var result = "";
    if (objVal == null || objVal.length == 0) {
        return objVal;
    }
    for (var i = 0 ; i < objVal.length; i++) {
        if (objVal.charAt(i) != ",") {
            result = result + objVal.charAt(i);
        }
    }
    return result;
}
//******************************************************************************
/*
 * 全半角スペースをトリムします。
 *
 * @param strVal トリム対象文字列
 * @return トリムされた文字列
 */
//******************************************************************************
function com_item_trim(strVal) {
    return com_item_Ltrim(com_item_Rtrim(strVal));
}

//******************************************************************************
/*
 * 左側の全半角スペースをトリムします。
 *
 * @param strVal 左トリム対象文字列
 * @return 左トリムされた文字列
 */
//******************************************************************************
function com_item_Ltrim(strVal) {
    if (strVal == null || strVal.length == 0) {
        return strVal;
    }
    var trimCnt = 0;
    for (trimCnt = 0; trimCnt < strVal.length; trimCnt++) {
        if (escape(strVal.charAt(trimCnt)) != "\%A0" &&
            strVal.charAt(trimCnt) != "&nbsp;" &&
            strVal.charAt(trimCnt) != " " &&
            strVal.charAt(trimCnt) != "　") {
            break;
        }
    }

    return strVal.substring(trimCnt);
}
//******************************************************************************
/*
 * 右側の全半角スペースをトリムします。
 *
 * @param strVal 右トリム対象文字列
 * @return 右トリムされた文字列
 */
//******************************************************************************
function com_item_Rtrim(strVal) {
    if (strVal == null || strVal.length == 0) {
        return strVal;
    }
    var trimCnt = 0;
    for (trimCnt = strVal.length - 1; 0 <= trimCnt; trimCnt--) {
        if (escape(strVal.charAt(trimCnt)) != "\%A0" &&
            strVal.charAt(trimCnt) != " " &&
            strVal.charAt(trimCnt) != "　") {
            break;
        }
    }

    return strVal.substring(0, trimCnt + 1);
}
function com_chk_checkMinusDec(objVal, intbu, decbu) {
    var str = objVal;
    var minusFlg = false;
    var decFlg = false;

    //パターンマッチ 0〜9,"-","."以外はＮＧ
    if (str.match(/[^0-9|^\-|^.]/g)) {
        return false;
    }

    //パターンマッチ "-","."のみはＮＧ
    if (str.match(/[^\-|^.]/g)) {
    } else {
        return false;
    }

    //小数点の数を確認
    var cn = 0;
    for (var i = 0; i < str.length; i++) {
        if (str.charAt(i) == ".") {
            decFlg=true;
            cn++;
        }
    }

    //"."が２つ以上はエラー
    //頭に小数点が入力された場合はＮＧ
    //一番後ろに小数点が入力された場合はＮＧ
    if( 2 <= cn ){
        return false;
    }

    //"-"の入力個数を取得
    cn = 0;
    for (i = 0; i < str.length; i++) {
        if (str.charAt(i) == "-") {
            minusFlg=true;
            cn++;
        }
    }

    //"-"が２つ以上入力されている場合はＮＧ
    //"-"が入力されていて、かつ先頭に"-"がない場合はＮＧ
    if (2 <= cn || (cn == 1 && str.charAt(0) != "-")) {
        return false;
    }

    //小数点以下のチェック
    if (decFlg){
        //小数点以下の桁数チェック
        var idx = str.lastIndexOf(".");
        var syo = str.substring(idx);   //"."含む後ろを取得
        var len = syo.length-1;         //小数点以下の桁数
        if (len == 0) {
            //小数点以下の入力がない場合はＮＧ
            return false;
        }
        if (decbu < len) {
            //小数点以下の桁数がオーバーしている場合はＮＧ
            return false;
        }
    }

    //整数部の桁数チェック
    var sei = "";
    len = 0;
    if (decFlg) {
        //小数点が入力された場合
        sei = str.substring(0, str.indexOf("."));
    } else {
        sei = str;
    }
    len = sei.length;
    if (minusFlg) {
        //マイナスが入力された場合は-1
        len--;
    }
    if (intbu < len) {
        return false;
    }

    return true;
}
//消息提示
function makeMesDiv(obj){
	var divFormError = document.createElement('div');
	var formErrorContent = document.createElement('div');
	//var arrow = document.createElement('div');
	$(divFormError).addClass("formError");
	$(divFormError).addClass($(obj).attr("name"));
	$(formErrorContent).addClass("formErrorContent");
	//$(arrow).addClass("formErrorArrow");

	$("body").append(divFormError);
	//$(divFormError).append(arrow);
	$(divFormError).append(formErrorContent);
	//$(arrow).html('<div class="line10"></div><div class="line9"></div><div class="line8"></div><div class="line7"></div><div class="line6"></div><div class="line5"></div><div class="line4"></div><div class="line3"></div><div class="line2"></div><div class="line1"></div>');
	$(formErrorContent).html($(obj).text());

	callerTopPosition = $(obj).offset().top;
	callerleftPosition = $(obj).offset().left;
	callerWidth =  $(obj).width();
	callerHeight =  $(obj).height();
	inputHeight = $(divFormError).height();

	callerleftPosition = callerleftPosition + callerWidth +10;
	callerTopPosition = callerTopPosition  -inputHeight + 33;

	$(divFormError).css({
		top:callerTopPosition,
		left:callerleftPosition,
		opacity:0
	});
	$(divFormError).fadeTo("fast",0.8);
}

function removeMesDiv(){
	$("div[class='formError']").each(function(){
		$(this).remove();
	});
}