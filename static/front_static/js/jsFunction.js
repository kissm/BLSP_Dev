/*@cc_on
eval(
    function(props)
    {
        var code = [];
        for (var i = 0; i < props.length; i++)
        {
            var prop = props[i];
            window['_' + prop] = window[prop];
            code.push(prop + '=_' + prop)
        }
        return 'var ' + code.join(',');
    }
    ('document self top parent setInterval clearInterval setTimeout clearTimeout alert confirm prompt open close showModalDialog showModelessDialog'.split(' '))
);
@*/
//2009-12-22

//视图列表中打开一项信息

function jsOpen(strForm, strID, strOther, strWin)
{
//调用说明：

//1. strForm待打开的页面文件名（不用传.aspx后缀）。

//2. strID用来指定打开的页面将显示哪一条记录。

//3. strID为空时表示进行添加记录的操作。

//4. 在打开的页面，接收Request["ID"]，以于读取记录，或添加记录。

//5. strOther是其它需要传递的参数（一个或多个&key=value）。

    if (strID == null) strID = "";
    if (strOther == null) strOther = "";
    
    var iWidth = screen.availWidth - 12;
    var iHeight = screen.availHeight - 50;
    if (/&w=/i.test(strOther))
    {
        iWidth = strOther.replace(/.*&w=(\d*).*/i, "$1");
        strOther = strOther.replace(/&w=\d*/i, "");
    }
    if (/&h=/i.test(strOther))
    {
        iHeight = strOther.replace(/.*&h=(\d*).*/i, "$1");
        strOther = strOther.replace(/&h=\d*/i, "");
    }

    if (strWin == null)
    {
        strWin = "_self";
        if (/&newwin/i.test(strOther))
        {
            strWin = "_blank";
            strOther = strOther.replace(/&newwin/i, "");
        }
    }

    var iLeft = (screen.availWidth - 12 - iWidth) / 2;
    var iTop = (screen.availHeight - 50 - iHeight)  / 2;
    var strStyle = "toolbar=no,scrollbars=yes,status=yes,resizable=yes" +
                   ",width=" + iWidth +
                   ",height=" + iHeight +
                   ",left=" + iLeft +
                   ",top=" + iTop;

    return window.open(strForm + ".aspx?ID=" + strID + strOther, strWin, strStyle);
}

//关闭窗口
function jsClose(strURL)
{
//调用说明：

//1. strURL指定需要定向的页面。

//2. 不传strURL，或strURL为空时，关闭当前窗口。

//3. strURL为"parent"时，重load父窗口后，再关闭当前窗口。

//4. strURL为"parentsubmit"时，刷新父窗口后，再关闭当前窗口。

try
{
    if (strURL == null) strURL = "";
    switch (strURL)
    {
        case "parent":
            if (parent == self)
            {
                if (window.opener)
                {
                    //var url = window.opener.location.href;
                    //window.opener.location = "about:blank";
                    //window.opener.location = url;
                    //window.opener.focus();
                    ////window.opener.location.reload();
                    window.opener.document.forms[0].submit();
                }
                window.opener = null;
                window.close();
            }
            else
            {
                var url = parent.location.href;
                parent.location = "about:blank";
                parent.location = url;
            }
            break;
        case "parentsubmit":
            if (parent == self)
            {
                if (window.opener)
                {
                    window.opener.document.forms[0].submit();
                    window.opener.focus();
                }
                window.opener = null;
                window.close();
            }
            else
            {
                parent.document.forms[0].submit();
            }
            break;
        case "left":  
           if (parent == self)
            {        
                 if (window.opener)
                {
//                    window.opener.parent.leftFrame.location=window.opener.parent.leftFrame.location.href;
                    window.opener.parent.leftFrame.frames["treeFrame"].location=window.opener.parent.leftFrame.frames["treeFrame"].location.href;
                    window.opener.document.forms[0].submit();
                }
                window.opener = null;
                window.close();
             }
              else
             {
//                    parent.leftFrame.location=parent.leftFrame.location.href;
                    parent.leftFrame.frames["treeFrame"].location=parent.leftFrame.frames["treeFrame"].location.href;
                    document.forms[0].submit();
              }
            break;
        case "":
            if (parent == self)
            {
                window.opener = null;
                window.close();
            }
            break;
        default:
            var objLink = document.createElement("A");
            objLink.href = strURL;
            document.body.appendChild(objLink);
            objLink.click();
            //document.location.replace(strURL);
            break;
    }
}
catch (e)
{
}
}

//检查字段的合法性

function jsCheckField(checkFieldList)
{
//调用说明：

//1. 多个需要检查的字段用“:”分隔。

//2. 每个字段包含三个属性：字段控件、中文描述、检查类型，属性之间用“^”分隔。

//3. 检查类型可以省略，默认为非空字符型。

    var fieldObj;
    var fieldInfo;
    var rtnVal = true;
    var fieldList = checkFieldList.split(":");

    for (var i = 0; i < fieldList.length; i++)
    {
        fieldInfo = fieldList[i].split("^");
        fieldObj = eval("document.all." + fieldInfo[0]);

        if (!fieldObj) continue;
        
        if ((fieldInfo[2] == null) || (fieldInfo[2] == "")) fieldInfo[2] = "E";
        
        for (; fieldInfo[2] != ""; fieldInfo[2] = fieldInfo[2].substring(1))
        {
			switch (fieldInfo[2].substring(0,1))
            {
            case "I":
                if ((fieldObj.value) && !isInt(fieldObj.value))
                {
				    alert("“" + fieldInfo[1] + "”应为整数！请重新输入。");
                    rtnVal = false;
                }
                break;
            case "F":
                if ((fieldObj.value) && !isFloat(fieldObj.value))
                {
                    alert("“" + fieldInfo[1] + "”应为数字！请重新输入。");
                    rtnVal = false;
                }
                break;
            case "L":
                if ((fieldObj.value) && fieldObj.value.length>30)
                {
                    alert("“" + fieldInfo[1] + "”标题字数过多！请重新输入。");
                    rtnVal = false;
                }
                break;
            case "E":
                if (fieldObj.tagName.toLowerCase() == "select")
                {
                    if ((fieldObj.selectedIndex < 0) || (fieldObj.options[fieldObj.selectedIndex].value == "请选择"))
                    {
                        alert("请选择“" + fieldInfo[1] + "”！");
                        rtnVal = false;
                    }
                    break;
                }
                if (fieldObj.value == "")
                {
                    alert("请填写“" + fieldInfo[1] + "”！");
                    rtnVal = false;
                }
               
                break;
            }
            if (!rtnVal)
            {
                try
                {
                    fieldObj.focus();
                    fieldObj.select();
                }
                catch (err)
                {
                }
                return false;
            }
        }//循环每一字段的各种检查

    }//循环字段
    return true;
}

//检查整数及上下限

function isInt(strNum)
{
//调用说明：

//1. 本函数有两个动态的参数。

//2. 指定第2参数则检查最小值。

//3. 指定第3参数则检查最大值。

    var strTmp = "" + strNum;

    if (!/^[-|\d]\d*$/.test(strTmp)) return false;

    var val = parseInt(strTmp, 10);
    if (isNaN(val)) return false;

    if (arguments[1] != null)
        if (val < arguments[1]) return false;

    if (arguments[2] != null)
        if (val > arguments[2]) return false;

    return true;
}

//检查小数及上下限

function isFloat(strNum)
{
//调用说明：

//1. 本函数有两个动态的参数。

//2. 指定第2参数则检查最小值。

//3. 指定第3参数则检查最大值。

    var strTmp = "" + strNum;

    if (!/^-?\d*\.?\d*$/.test(strTmp)) return false;

    var val = parseFloat(strNum);
    if (isNaN(val)) return false;

    if (arguments[1] != null)
        if (val < arguments[1]) return false;

    if (arguments[2] != null)
        if (val > arguments[2]) return false;

    return true;
}

//视图列表的全选与全不选

function jsSwitchCheckBox(obj)
{
//调用说明：

//1. 自动判断参数的类型。

//2. 选择框(CheckBox)默认命名：cSelected。

    switch (typeof(obj))
    {
        case "string" :
            obj = (obj == "") ? document.all.cSelected : document.all[obj];
            break;
        case "object" :
            if (obj == null) obj = document.all.cSelected;
            break;
        default:
            obj = document.all.cSelected;
            break;
    }        
    if (obj)
    {
        if (obj.length)
        {
            for (var i = 1; i < obj.length; i++)
            {
                obj[i].checked = obj[0].checked;
            }           
        }
    }
    jsSingleCheckBox(obj);
    
}

function jsSwitchCheckBoxPwd(obj)
{
//调用说明：

//1. 自动判断参数的类型。

//2. 选择框(CheckBox)默认命名：cSelected。

    switch (typeof(obj))
    {
        case "string" :
            obj = (obj == "") ? document.all.cSelected : document.all[obj];
            break;
        case "object" :
            if (obj == null) obj = document.all.cSelected;
            break;
        default:
            obj = document.all.cSelected;
            break;
    }        
    if (obj)
    {
        if (obj.length)
        {
            for (var i = 1; i < obj.length; i++)
            {
                obj[i].checked = obj[0].checked;
            }           
        }
    }
    jsSingleCheckBoxPwd(obj);
    
}

function jsSwitchCheckBoxRecharge(obj)
{
//调用说明：

//1. 自动判断参数的类型。

//2. 选择框(CheckBox)默认命名：cSelected。

    switch (typeof(obj))
    {
        case "string" :
            obj = (obj == "") ? document.all.cSelected : document.all[obj];
            break;
        case "object" :
            if (obj == null) obj = document.all.cSelected;
            break;
        default:
            obj = document.all.cSelected;
            break;
    }        
    if (obj)
    {
        if (obj.length)
        {
            for (var i = 1; i < obj.length; i++)
            {
                obj[i].checked = obj[0].checked;
            }           
        }
    }
    jsSingleCheckBoxRecharge(obj);
    
}

//视图列表过滤不可使用选择框的全选与全不选

function jsSwitchAbleCheckBox(obj)
{
//调用说明：

//1. 自动判断参数的类型。

//2. 选择框(CheckBox)默认命名：cSelected。

    switch (typeof(obj))
    {
        case "string" :
            obj = (obj == "") ? document.all.cSelected : document.all[obj];
            break;
        case "object" :
            if (obj == null) obj = document.all.cSelected;
            break;
        default:
            obj = document.all.cSelected;
            break;
    }

    if (obj)
    {
        if (obj.length)
        {
            for (var i = 1; i < obj.length; i++)
            {
                if (obj[i].disabled == false)
                    obj[i].checked = obj[0].checked;
            }
        }
    }
}

//视图列表的多选结果

function jsSaveSelected(obj, objCache)
{
//调用说明：

//1. 自动判断参数的类型。

//2. 选择框(CheckBox)默认命名：cSelected。

//3. 记住选中项的文本框默认命名：对于选择框数组cXxx，对应的文本框命名为cXxxCache。

//4. 判断两个对象分别为checkbox和text。

    var strTemp = "";
    switch (typeof(obj))
    {
        case "string" :
            obj = (obj == "") ? document.all.cSelected : document.all[obj];
            break;
        case "object" :
            if (obj == null) obj = document.all.cSelected;
            break;
        default:
            obj = document.all.cSelected;
            break;
    }
    if (obj && obj.length)
    {
        if (!(obj[0].tagName.toLowerCase() == "input" && obj[0].type.toLowerCase() == "checkbox")) obj = null;
    }
    else
    {
        if (!(obj.tagName.toLowerCase() == "input" && obj.type.toLowerCase() == "checkbox")) obj = null;
    }

    switch (typeof(objCache))
    {
        case "string" :
            if (objCache == "")
            {
                if (obj) {objCache = (obj.length) ? document.all[obj[0].id + "Cache"] : document.all[obj.id + "Cache"];}
            }
            else
            {
                objCache = document.all[objCache];
            }
            break;
        case "object" :
            if (objCache == null)
            {
                if (obj) {objCache = (obj.length) ? document.all[obj[0].id + "Cache"] : document.all[obj.id + "Cache"];}
            }
            break;
        default:
            if (obj) {objCache = (obj.length) ? document.all[obj[0].id + "Cache"] : document.all[obj.id + "Cache"];}
            break;
    }
    if (!(objCache && objCache.tagName.toLowerCase() == "input" && objCache.type.toLowerCase() == "text")) objCache = null;
    
    if ((obj) && (objCache))
    {
        if (obj.length)
        {
            for (var i = 1; i < obj.length; i++)
            {
                if (obj[i].checked)
                {
                    if (strTemp != "") strTemp += ",";
                    strTemp += obj[i].value;
                }
            }
            objCache.value = strTemp;
        }
        else
        {
            objCache.value = "";
        }
    }
    
    return (objCache.value != "");
}

//返回radio的选中项

function jsGetSingleValue(obj)
{
//调用说明：

//1. 自动判断参数的类型。

//2. 判断指定的对象是否为radio。

    var result = "";
    switch (typeof(obj))
    {
        case "string" :
            obj = (obj == "") ? null : document.all[obj];
            break;
        case "object" :
            break;
        default:
            obj = null;
            break;
    }

    if (obj && obj.length)
    {
        if (!(obj[0].tagName.toLowerCase() == "input" && obj[0].type.toLowerCase() == "radio")) obj = null;
    }
    else
    {
        if (!(obj.tagName.toLowerCase() == "input" && obj.type.toLowerCase() == "radio")) obj = null;
    }
    
    if (obj)
    {
        if (obj.length)
        {
            for (var i = 0; i < obj.length; i++)
            {
                if (obj[i].checked)
                {
                    result = obj[i].value;
                    break;
                }
            }
        }
        else
        {
            if (obj.checked) result = obj.value;
        }
    }

    return result;
}

//返回checkbox的选中项

function jsGetMultiValue(obj)
{
//调用说明：

//1. 自动判断参数的类型。

//2. 判断指定的对象是否为checkbox。

    var result = "";
    switch (typeof(obj))
    {
        case "string" :
            obj = (obj == "") ? null : document.all[obj];
            break;
        case "object" :
            break;
        default:
            obj = null;
            break;
    }

    if (obj && obj.length)
    {
        if (!(obj[0].tagName.toLowerCase() == "input" && obj[0].type.toLowerCase() == "checkbox")) obj = null;
    }
    else
    {
        if (!(obj.tagName.toLowerCase() == "input" && obj.type.toLowerCase() == "checkbox")) obj = null;
    }
    
    if (obj)
    {
        if (obj.length)
        {
            for (var i = 0; i < obj.length; i++)
            {
                if (obj[i].checked)
                {
                    if (result != "") result += ",";
                    result += obj[i].value;
                }
            }
        }
        else
        {
            if (obj.checked) result = obj.value;
        }
    }
    
    return result;
}

//显示radio的选中项

function jsShowSingleValue(obj, strValue)
{
//调用说明：

//1. 自动判断参数的类型。

//2. 判断指定的对象是否为radio。

    var result = "";
    switch (typeof(obj))
    {
        case "string" :
            obj = (obj == "") ? null : document.all[obj];
            break;
        case "object" :
            break;
        default:
            obj = null;
            break;
    }

    if (obj && obj.length)
    {
        if (!(obj[0].tagName.toLowerCase() == "input" && obj[0].type.toLowerCase() == "radio")) obj = null;
    }
    else
    {
        if (!(obj.tagName.toLowerCase() == "input" && obj.type.toLowerCase() == "radio")) obj = null;
    }
    
    if (obj)
    {
        if (obj.length)
        {
            for (var i = 0; i < obj.length; i++)
            {
                if (obj[i].value == strValue)
                {
                    obj[i].checked = true;
                    break;
                }
            }
        }
        else
        {
            if (obj.value == strValue)
            {
                obj.checked = true;                        
            }
        }
    }
}

//显示checkbox的选中项

function jsShowMultiValue(obj, strValue, strSeparator)
{
//调用说明：

//1. 自动判断参数的类型。

//2. 判断指定的对象是否为checkbox。

    var result = "";
    switch (typeof(obj))
    {
        case "string" :
            obj = (obj == "") ? null : document.all[obj];
            break;
        case "object" :
            break;
        default:
            obj = null;
            break;
    }

    if (obj && obj.length)
    {
        if (!(obj[0].tagName.toLowerCase() == "input" && obj[0].type.toLowerCase() == "checkbox")) obj = null;
    }
    else
    {
        if (!(obj.tagName.toLowerCase() == "input" && obj.type.toLowerCase() == "checkbox")) obj = null;
    }
    
    if ((strSeparator == null) || (strSeparator == "")) strSeparator = ",";
    
    if (obj)
    {
        if (obj.length)
        {
            var arValue = strValue.split(strSeparator);
            for (var i = 0; i < arValue.length; i++)
            {
                for (var j = 0; j < obj.length; j++)
                {
                    if (obj[j].value == arValue[i])
                    {
                        obj[j].checked = true;
                        break;
                    }
                }
            }
        }
        else
        {
            if (obj.value == strValue)
            {
                obj.checked = true;
            }
        }
    }
}

//列表操作（默认是删除选中的记录）
function jsListAction(msg, btn, ck, ckCache)
{
//调用说明：

//1. 自动判断参数的类型。

//2. 默认按钮是删除按钮，命名：btnDelete。

//3. 选择框(CheckBox)默认命名：cSelected。

//4. 检查至少选中了一个记录。

//4. 操作之前显示确认警告框。

    switch (typeof(btn))
    {
        case "string" :
            btn = (btn == "") ? document.all.btnDelete : document.all[btn];
            break;
        case "object" :
            if (btn == null) btn = document.all.btnDelete;
            break;
        default:
            btn = document.all.btnDelete;
            break;
    }
    switch (typeof(ck))
    {
        case "string" :
            ck = (ck == "") ? document.all.cSelected : document.all[ck];
            break;
        case "object" :
            if (ck == null) ck = document.all.cSelected;
            break;
        default:
            ck = document.all.cSelected;
            break;
    }
    if (msg == null) msg = "确定删除记录吗？";
    if (typeof(msg) == "object") msg = msg.value ? msg.value : msg.text ? msg.text : msg.innerText;

    if (jsSaveSelected(ck, ckCache))
    {
        if ((msg == "") || window.confirm(msg))
        {
            if (document.all.cSubmitForSave) document.all.cSubmitForSave.value = "1";
            btn.click();
        }
    }
    else
    {
        alert("请先选择记录！");
    }
}

//模态对话框
function jsShowModalDialog(strUrl, args, iWidth, iHeight)
{
    var style = "dialogWidth:" + iWidth + "px;";
    style = style + " dialogHeight:" + iHeight + "px;";
    style = style + "center:yes; help:no; scroll:yes; status:no;";
    return window.showModalDialog(strUrl, args, style);
}

//提交操作
function jsSubmit()
{
    //如果页面定义了提交前预处理

//    if (window.jsPreSubmit != null)
//    {
//        if (jsPreSubmit()) document.forms[0].submit();
//    }
//    else
//    {
//        document.forms[0].submit();
    //    }
    document.forms[0].submit();
}

//屏蔽用户操作
function jsReject()
{
    event.cancelBubble = true;
    event.returnValue = false;
}

//返回值

function jsReturnValue(v)
{
    //由页面提供返回值

    if (window.jsPreReturnValue != null)
    {
        top.returnValue = jsPreReturnValue(v);
    }
    else
    {
        top.returnValue = v;
    }
    
    window.close();
}

//取消并返回

function jsReturnCancel()
{
    top.returnValue = null;
    window.close();
}

//按列标题排序
function jsSort(strField, objField, objDirection)
{
//调用说明：

//1. 自动判断参数的类型。

//2. 存放排序字段的控件默认命名：cSortField。

//3. 存放排序方向的控件默认命名：cSortDirection。

    switch (typeof(objField))
    {
        case "string" :
            objField = (objField == "") ? document.all.cSortField : document.all[objField];
            break;
        case "object" :
            if (objField == null) objField = document.all.cSortField;
            break;
        default:
            objField = document.all.cSortField;
            break;
    }

    switch (typeof(objDirection))
    {
        case "string" :
            objDirection = (objDircetion == "") ? document.all.cSortDirection : document.all[objDirection];
            break;
        case "object" :
            if (objDirection == null) objDirection = document.all.cSortDirection;
            break;
        default:
            objDirection = document.all.cSortDirection;
            break;
    }

    if (objField && objDirection)
    {
        objField.value = strField;
        objDirection.value = (objDirection.value == "") ? " desc" : "";
        jsSubmit();
    }
   

}



//校验手机号码
function jsCheckPhone(objField)
{
//调用说明：

//1. 本函数校验134~139的手机号码。

//2. 本函数允许空号码，若是必填字段，请另行用jsCheckField校验。

//3. 自动判断参数的类型，手机号码的控件默认命名：cPhone。

    switch (typeof(objField))
    {
        case "string" :
            objField = (objField == "") ? document.all.cPhone : document.all[objField];
            break;
        case "object" :
            if (objField == null) objField = document.all.cPhone;
            break;
        default:
            objField = document.all.cPhone;
            break;
    }

    var result = true;
    if (objField)
    {
        result = /^((15[8-9]|13[4-9])[0-9]{8})?$/.test(objField.value);
        if (!result)
        {
            alert("手机号码录入格式：\n\n1.只能用中国移动手机号码\n2.手机号码必须是11位数字");
            objField.focus();
            objField.select();
        }
    }
    return result;
}

//将网页中的表格导出到Excel文件
function jsTableToExcel(strTableName)
{
    var oTable = document.all[strTableName];
    if (oTable == null) return;
    if (oTable.tagName.toLowerCase() != "table") return;
    
    try
    {
        var xcl = new ActiveXObject("Excel.Application");
    }
    catch (err)
    {
        alert("无法创建Excel文件!\n\n可能原因一：没有安装Excel。\n\n可能原因二：IE的安全性限制。");
        return false;
    }
    
    try
    {
        xcl.Workbooks.Add();
        for (var i = 0; i < oTable.rows.length; i++)
        {
            for (var j = 0; j < oTable.rows[i].cells.length; j++)
            {
                xcl.Cells.Item(i+1, j+1) = oTable.rows[i].cells[j].innerText;
            }
        }
        xcl.Cells.EntireColumn.AutoFit();
        xcl.Visible = true;
    }
    catch (err)
    {
        xcl.Visible = true;
    }
}

//为String添加了个method：trim()
String.prototype.trim=function() 
{
    return this.replace(/(^\s*)|(\s*$)/g, "");
}

//添加onLoad事件的处理

function addLoadEvent(func)
{
    var oldFunc = window.onload;
    if (typeof window.onload == "function")
    {
        window.onload = function(){
            oldFunc();
            func();
        }
    }
    else
    {
        window.onload = func;
    }
}




/**************************新增加的JS***********************************************************/
//列表操作（默认是编辑单条记录）
function jsSingleListAction( ck, ckCache,url,other)
{
//调用说明：

//1. 选择框(CheckBox)默认命名：cSelected。

//2. 检查是否只选中了一个记录。

    switch (typeof(ck))
    {
        case "string" :
            ck = (ck == "") ? document.all.cSelected : document.all[ck];
            break;
        case "object" :
            if (ck == null) ck = document.all.cSelected;
            break;
        default:
            ck = document.all.cSelected;
            break;
    }   
    if (jsSaveSelected(ck, ckCache))
    {
        var MultiValue=jsGetMultiValue(ck);
        var length=0;
        if(MultiValue!="")
        {
            var str=MultiValue.split(',');            
            length=str.length;
            if(str[0]=="0")
            {
                length=length-1;
            }
        }
        
       if(length==1)
       { 
           if(other==null)
           {
                jsOpen(url,document.all.cSelectedCache.value,"&newwin");
           }
           else
           {
                jsOpen(url,document.all.cSelectedCache.value,other,"_blank");
           }
       }
       else
       {
            alert("请选择其中一条记录进行编辑！");
       }
    }
    else
    {
        alert("请先选择记录！");
    }
}

function jsSingleListAction2( ck, ckCache,url,cs,other)
{
//调用说明：

//1. 选择框(CheckBox)默认命名：cSelected。

//2. 检查是否只选中了一个记录。

    switch (typeof(ck))
    {
        case "string" :
            ck = (ck == "") ? document.all.cSelected : document.all[ck];
            break;
        case "object" :
            if (ck == null) ck = document.all.cSelected;
            break;
        default:
            ck = document.all.cSelected;
            break;
    }   
    if (jsSaveSelected(ck, ckCache))
    {
        var MultiValue=jsGetMultiValue(ck);
        var length=0;
        if(MultiValue!="")
        {
            var str=MultiValue.split(',');            
            length=str.length;
            if(str[0]=="0")
            {
                length=length-1;
            }
        }
        
       if(length==1)
       { 
           if(other==null)
           {
                jsOpen(url,document.all.cSelectedCache.value,cs+"&newwin");
           }
           else
           {
                jsOpen(url,document.all.cSelectedCache.value,other,"_blank");
           }
       }
       else
       {
            alert("请选择其中一条记录进行编辑！");
       }
    }
    else
    {
        alert("请先选择记录！");
    }
}


//返回checkbox的选中项

function jsGetMultiRow(obj)
{
//调用说明：

//1. 自动判断参数的类型。

//2. 判断指定的对象是否为checkbox。

    var result = "";
    switch (typeof(obj))
    {
        case "string" :
            obj = (obj == "") ? null : document.all[obj];
            break;
        case "object" :
            break;
        default:
            obj = null;
            break;
    }

    if (obj && obj.length)
    {
        if (!(obj[0].tagName.toLowerCase() == "input" && obj[0].type.toLowerCase() == "checkbox")) obj = null;
    }
    else
    {
        if (!(obj.tagName.toLowerCase() == "input" && obj.type.toLowerCase() == "checkbox")) obj = null;
    }
    
    if (obj)
    {
        if (obj.length)
        {
            for (var i = 0; i < obj.length; i++)
            {
                if (obj[i].checked)
                {
                   result++;
                }
            }
        }        
    }
    
    return result;
}


 //显示或隐藏                                               
	function doShow(obj,id)                                     
	{             
	//调用说明：

    //1. 当前对象。

    //2.id为需要隐藏或展开的ID。                                           
	    if (!obj) return;                                    
	    try                                                  
	    {                                                    
	    if (obj.src.indexOf('collapse.gif') >=  0)           
	    {                                                    
	        obj.src = '../images/expend.gif';           
	        document.all(id).style.display = "none";
	    }                                                    
    	else                                                 
	    {                                                    
	        obj.src = '../images/collapse.gif';              
	        document.all(id).style.display = '';    
	    }                                                    
	    }                                                        
	    catch(ex)                                                
	    {                                                        
	       alert(ex.message);                                   
	    }                                                        
    }	 
    
    function jsGetMultiValue(obj)
{
//调用说明：

//1. 自动判断参数的类型。

//2. 判断指定的对象是否为checkbox。

    var result = "";
    switch (typeof(obj))
    {
        case "string" :
            obj = (obj == "") ? null : document.all[obj];
            break;
        case "object" :
            break;
        default:
            obj = null;
            break;
    }

    if (obj && obj.length)
    {
        if (!(obj[0].tagName.toLowerCase() == "input" && obj[0].type.toLowerCase() == "checkbox")) obj = null;
    }
    else
    {
        if (!(obj.tagName.toLowerCase() == "input" && obj.type.toLowerCase() == "checkbox")) obj = null;
    }
    
    if (obj)
    {
        if (obj.length)
        {
            for (var i = 0; i < obj.length; i++)
            {
                if (obj[i].checked)
                {
                   result+=obj[i].value+",";
                }
            }
        }        
    }
    if(result!="")
    {
        result=result.substring(0,result.length-1);
    }
    return result;
}

    /******************检测单选按钮。隐藏东西**************************/
    function jsSingleCheckBox(obj)
    {
        switch (typeof(obj))
        {
            case "string" :
                obj = (obj == "") ? document.all.cSelected : document.all[obj];
                break;
            case "object" :
                if (obj == null) obj = document.all.cSelected;
                break;
            default:
                obj = document.all.cSelected;
                break;
        }    
        var MultiValue=jsGetMultiValue(obj);
        var length=0;
        if(MultiValue!="")
        {
            var str=MultiValue.split(',');            
            length=str.length;
            if(str[0]=="0")
            {
                length=length-1;
            }
        }
        if(length==0)
        {
            document.all['lblAdd'].style.display="";
            document.all['lblAdd1'].style.display="none";
            document.all['lblEdit'].style.display="none";
            document.all['lblEdit1'].style.display="";
            document.all['lblDel'].style.display="none";
            document.all['lblDel1'].style.display="";
            if(document.all['lblSingle']!=null)
            {
               document.all['lblSingle'].style.display="none";
               document.all['lblSingle1'].style.display="";
            }
        }
        else if(length==1)
        {
            document.all['lblAdd'].style.display="none";
            document.all['lblAdd1'].style.display="";
            document.all['lblEdit'].style.display="";
            document.all['lblEdit1'].style.display="none";
            document.all['lblDel'].style.display="";
            document.all['lblDel1'].style.display="none";
            if(document.all['lblSingle']!=null)
            {
               document.all['lblSingle'].style.display="";
               document.all['lblSingle1'].style.display="none";
            }
        }
        else
        {
            document.all['lblAdd'].style.display="none";
            document.all['lblAdd1'].style.display="";
            document.all['lblEdit'].style.display="none";
            document.all['lblEdit1'].style.display="";
            document.all['lblDel'].style.display="";
            document.all['lblDel1'].style.display="none";
            if(document.all['lblSingle']!=null)
            {
               document.all['lblSingle'].style.display="none";
               document.all['lblSingle1'].style.display="";
            }
        }
    }
    
    /******************检测“用户管理”单选按钮。隐藏东西**************************/
    function jsSingleCheckBoxPwd(obj)
    {
        switch (typeof(obj))
        {
            case "string" :
                obj = (obj == "") ? document.all.cSelected : document.all[obj];
                break;
            case "object" :
                if (obj == null) obj = document.all.cSelected;
                break;
            default:
                obj = document.all.cSelected;
                break;
        }    
        var MultiValue=jsGetMultiValue(obj);
        var length=0;
        if(MultiValue!="")
        {
            var str=MultiValue.split(',');            
            length=str.length;
            if(str[0]=="0")
            {
                length=length-1;
            }
        }
        if(length==0)
        {
            document.all['lblAdd'].style.display="";
            document.all['lblAdd1'].style.display="none";
            document.all['lblEdit'].style.display="none";
            document.all['lblEdit1'].style.display="";
            document.all['lblDel'].style.display="none";
            document.all['lblDel1'].style.display="";
            document.all['lblPwd'].style.display="none";
            document.all['lblPwd1'].style.display="";
        }
        else if(length==1)
        {
            document.all['lblAdd'].style.display="none";
            document.all['lblAdd1'].style.display="";
            document.all['lblEdit'].style.display="";
            document.all['lblEdit1'].style.display="none";
            document.all['lblDel'].style.display="";
            document.all['lblDel1'].style.display="none";
            document.all['lblPwd'].style.display="";
            document.all['lblPwd1'].style.display="none";
        }
        else
        {
            document.all['lblAdd'].style.display="none";
            document.all['lblAdd1'].style.display="";
            document.all['lblEdit'].style.display="none";
            document.all['lblEdit1'].style.display="";
            document.all['lblDel'].style.display="";
            document.all['lblDel1'].style.display="none";
            document.all['lblPwd'].style.display="";
            document.all['lblPwd1'].style.display="none";
        }
    }
    
    /******************检测“充值记录管理”单选按钮。隐藏东西**************************/
    function jsSingleCheckBoxRecharge(obj)
    {
        switch (typeof(obj))
        {
            case "string" :
                obj = (obj == "") ? document.all.cSelected : document.all[obj];
                break;
            case "object" :
                if (obj == null) obj = document.all.cSelected;
                break;
            default:
                obj = document.all.cSelected;
                break;
        }    
        var MultiValue=jsGetMultiValue(obj);
        var length=0;
        if(MultiValue!="")
        {
            var str=MultiValue.split(',');            
            length=str.length;
            if(str[0]=="0")
            {
                length=length-1;
            }
        }
        if(length==0)
        {
            document.all['lblAdd'].style.display="";
            document.all['lblAdd1'].style.display="none";
             document.all['lblEdit'].style.display="";
            document.all['lblEdit1'].style.display="none";
        }
        else if(length==1)
        {
            document.all['lblAdd'].style.display="none";
            document.all['lblAdd1'].style.display="";
            document.all['lblEdit'].style.display="none";
            document.all['lblEdit1'].style.display="";
        }
        else
        {
            document.all['lblAdd'].style.display="none";
            document.all['lblAdd1'].style.display="";
            document.all['lblEdit'].style.display="none";
            document.all['lblEdit1'].style.display="";
        }
    }
    
    
    
    
function checkNull(id, message) {
    if ($.trim($(id).val()) == "") {
        $(id).next().html('<span id="sLoginName" class="iconWrong">' + message + '</span>');
        return false;
    }
    else {
        $(id).next().html('<span id="sLoginName" class="iconOk"></span>');
        return true;
    }
}

function checkReg(id, reg, message) {
    if (!reg.test($.trim($(id).val()))) {
        $(id).next().html('<span id="sLoginName" class="iconWrong">' + message + '</span>');
        return false;
    }
    else {
        $(id).next().html('<span id="sLoginName" class="iconOk"></span>');
        return true;
    }
}

function checkLength(id, len1, len2, message) {
    if ($(id).val().length < len1 || $(id).val().length > len2) {
        $(id).next().html('<span id="sLoginName" class="iconWrong">' + message + '</span>');
        return false;
    }
    else {
        $(id).next().html('<span id="sLoginName" class="iconOk"></span>');
        return true;
    }
}

function checkEqual(id,id1, message) {
    if ($(id).val() != $(id1).val()) {
        $(id1).next().html('<span id="sLoginName" class="iconWrong">' + message + '</span>');
        return false;
    }
    else {
        $(id1).next().html('<span id="sLoginName" class="iconOk"></span>');
        return true;
    }
}
