/**
 * ***************************************************************
 * 文件名称：topoOverlay.js
 * 摘   要：地形分析结果显示
 * 作   者：Mr_wang
 * 创建时间：2016-12-28
 * 版权所有：华北计算技术研究所(NCI) 地理信息与图形图像技术研究中心
 * 开发记录：2016-12-28 Mr_wang 创建
 * 			2019-07-05 yanran 重构
 * ****************************************************************
 */

TopoImageOverlay = terramap.declare(
    /** @lends testOverlay# */
    terramap.maps.Overlay,
    {
        imgUrl: null,

        /** ctor
          */
        initialize: function (geocoords, url) {
            terramap.maps.Overlay.prototype.initialize.apply(this, arguments);
            //this._geocoord = geocoord;
            this.pois = geocoords;
            this.imgUrl = url;
        },

        // dtor
        destroy: function () {
            terramap.maps.Overlay.prototype.destroy.apply(this, arguments);
            this.pois = null;
            this.img == null;
            this.imgUrl = null;
        },

        /**
         * 此方法在地图显示更新后由地图调用。
         */
        redraw: function (force) {
            if (!this.imgUrl) {
                return;
            }
            var layer = this.layer;
            var map = layer.map;

            // 计算定位点的像素坐标
            var leftTop, rightBottom;
            var f = map.fromGeoCoordToPixel(this.pois[0]);
            var s = map.fromGeoCoordToPixel(this.pois[1]);
            var lydiv = this.layer.div;
            var lleft = parseInt(lydiv.style.left);
            var ltop = parseInt(lydiv.style.top);

            var minX = Math.min(f.x, s.x);
            var minY = Math.min(f.y, s.y);
            var maxX = Math.max(f.x, s.x);
            var maxY = Math.max(f.y, s.y);
            leftTop = new terramap.Point(minX, minY);

            // 移动div
            this.div.style.left = (leftTop.x - lleft) + 'px';
            this.div.style.top = (leftTop.y - ltop) + 'px';
            this.div.style.width = Math.abs(maxX - minX) + 'px';
            this.div.style.height = Math.abs(maxY - minY) + 'px';

            //this.div.style.border = '1px solid red';
            if (this.img == null) {
                this.img = document.createElement('img');
                this.img.style.width = '100%';
                this.img.style.height = '100%';
                this.div.appendChild(this.img);
            }
            if (this.imgUrl) {
                this.img.src = this.imgUrl;
            }
        },

        CLASS_NAME: 'TopoImageOverlay'
    });







TopoLegendImageOverlay = terramap.declare(
    terramap.maps.Overlay, {
        imgUrl: null,

        /** ctor
         * @param geocoord - Array[{terramap.GeoPoint}] 左上角点及右下角地理坐标，作为定位点
         */
        initialize: function (geocoords, url) {
            terramap.maps.Overlay.prototype.initialize.apply(this, arguments);
            //this._geocoord = geocoord;
            this.pois = geocoords;
            this.imgUrl = url;
        },

        // dtor
        destroy: function () {
            terramap.maps.Overlay.prototype.destroy.apply(this, arguments);
            this.pois = null;
            this.img == null;
            this.imgUrl = null;
        },

        /**
         * 此方法在地图显示更新后由地图调用。
         */
        redraw: function (force) {
            if (!this.imgUrl) {
                return;
            }
            var layer = this.layer;
            var map = layer.map;

            // 计算定位点的像素坐标
            var leftTop, rightBottom;
            var f = map.fromGeoCoordToPixel(this.pois[0]);
            var s = map.fromGeoCoordToPixel(this.pois[1]);
            var lydiv = this.layer.div;
            var lleft = parseInt(lydiv.style.left);
            var ltop = parseInt(lydiv.style.top);

            var minX = Math.min(f.x, s.x);
            var minY = Math.min(f.y, s.y);
            var maxX = Math.max(f.x, s.x);
            var maxY = Math.max(f.y, s.y);
            leftTop = new terramap.Point(maxX, minY);

            // 移动div
            this.div.style.left = (leftTop.x - lleft) + 'px';
            this.div.style.top = (leftTop.y - ltop) + 'px';
            this.div.style.width = Math.abs(100) + 'px';
            this.div.style.height = 'auto';

            this.div.style.border = '1px solid black';
            $(this.div).css({'text-align' : 'center'});
            $(this.div).css({'background' : '#ffffff'});
            if (this.img == null) {
                $(this.div).append($('<span>').text('图 例').css({'width' : '100%', 'color': '#ff0011', 'background': 'white'}));
                this.img = document.createElement('img');
                this.img.style.width = '100%';
                this.img.style.height = '100%';
                this.div.appendChild(this.img);
            }
            if (this.imgUrl) {
                this.img.src = this.imgUrl;
            }
        },

        CLASS_NAME: 'TopoLegendImageOverlay'
    });