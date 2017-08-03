function JeesiteMap(){
    this.array = [];
    this.map = {};
}

/*放入元素*/
JeesiteMap.prototype.put = function (id, obj) {
    if(!this.isExistId(id)){
        this.array.push(id);
    }
    this.map[id] = obj;
    return true;
};
/*判断数组中是否存在此ID*/
JeesiteMap.prototype.isExistId = function(id) {
	for (var order in this.array) {
        if (this.array[order] == id) {
            return true;
        }
    }
    return false;
};
/*根据id取元素*/
JeesiteMap.prototype.getById = function (id) {
    return this.map[id];
};
/*根据ID获取位置*/
JeesiteMap.prototype.getOrderById = function (id) {
    for (var order in this.array) {
        if (this.array[order] == id) {
            return parseInt(order);
        }
    }
    return -1;
};
/*根据位置取元素*/
JeesiteMap.prototype.getByOrder = function (order) {
    return this.map[this.array[order]];
};
/*根据位置获取ID*/
JeesiteMap.prototype.getIdByOrder = function (order) {
    return this.array[order];
};
/*指定位置插入*/
JeesiteMap.prototype.insertByOrder = function (id, pos, obj) {
    this.array.splice(pos, 0, id);
    this.map[id] = obj;
    return true;
};
/*根据id删除*/
JeesiteMap.prototype.removeById = function (id) {
    delete this.map[id];
    for (var item in this.array) {
        if (this.array[item] == id) {
            this.array.splice(item, 1);
        }
    }
    return true;
};
/*根据位置 删除*/
JeesiteMap.prototype.removeByOrder = function (order) {
    var id = this.array[order];
    this.array.splice(order, 1);
    delete  this.map[id];
    return true;

};
/*取得对象数组*/
JeesiteMap.prototype.getArray = function () {
    var array = [];
    for (var p in this.array) {
        if(p!=null&&(this.map[this.array[p]]!=null)){
            array.push(this.map[this.array[p]]);
        }
    }
    return array;
};
/*取得id数组*/
JeesiteMap.prototype.getIdArray = function () {
    return this.array;
}
/*取得map对象*/
JeesiteMap.prototype.getMap = function () {
    return this.map;
}
/*重置nodeConfig*/
JeesiteMap.prototype.empty = function () {
    this.array = [];
    this.map = {};
}
/*返回范围内的id数组*/
JeesiteMap.prototype.getRange = function (start, end) {
    var indexArray = [];
    for (var i = start; i <= end; i++) {
        indexArray.push(this.array[i]);
    }
    return indexArray;
}
/*返回元素个数*/
JeesiteMap.prototype.getLength = function () {
    return this.array.length;
}
/*返回Array值用参数分隔的字符串*/
JeesiteMap.prototype.getArrayIdString = function (target) {
    var ids = "";
	for (var order in this.array) {
        if(order< this.array.length - 1){
	        ids =  ids + this.array[order] + target;
	    }else{
	        ids =  ids + this.array[order];
	    }
    }
    return ids;
}

/*返回Map Value用参数分隔的字符串*/
JeesiteMap.prototype.getMapValueString = function (target) {
    var values = "";
	for (var order in this.array) {
        if(order< this.array.length - 1){
	        values = values + this.map[this.array[order]] + target;
	    }else{
	        values = values + this.map[this.array[order]];
	    }
    }
    return values;
}
/*初始化Array值，参数是用#和@分隔的字符串*/
JeesiteMap.prototype.init= function (data) {
    var arr = new Array();
    var arr1 = new Array();
    var arr2 = new Array();
    if(data != "" && data != null){
         arr = data.split("@");
         arr1 = arr[0].split("#");
         arr2 = arr[1].split("#");
         for(var i in arr1){
             this.put(arr1[i],arr2[i]);
         }
    }
}
/*初始化Array值，参数是用#和@分隔的字符串*/
JeesiteMap.prototype.toObject= function () {
	var result = [];
    for (var order in this.array) {
    	var obj = {};
    	obj.id = this.array[order];
    	obj.value = this.map[this.array[order]];
    	result.push(obj);
    }
    return result;
}