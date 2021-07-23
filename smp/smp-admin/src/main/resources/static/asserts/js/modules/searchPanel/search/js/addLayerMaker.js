/**
 * Created by shiyu on 16-3-7.
 */
(function($) {
	$.fn.addLayerMaker = function(options) {
		var defaults = {
			'type':'city',
			'icon':null,
			'content':'',
			'x' : '0',
			'y' : '0',
			'srsCode':"MGIS:4214",
			'index' :0
		};
		var settings = $.extend({},defaults,options);
		return this.each(function(){
			if (!G_map) {
				return;
			}
			var index = settings.index;
			var geoX=settings.x*3600000;
			var geoY=settings.y*3600000;
			var _sref = settings.srsCode;//g_map.getSpatialReference();//116.3908299*360000, 39.914790*360000
			var geocoord = new terramap.GeoPoint(geoX,geoY, tempSpatialReference);
			geocoord = geocoord.convertTo(G_map.getSpatialReference());
			var content = settings.content;

			var icon = new terramap.maps.Icon();
			icon.image = search_imgs_url + "number/red0"+(settings.index+1)+".png";
			icon.iconSize = new terramap.Size(27, 27);
			icon.iconAnchor = new terramap.Point(14, 27);
			icon.imageMap = [3,9,9,3,16,3,20,8,20,16,15,22,12,30,9,22,3,16,3,9];
			//icon.infoWindowAnchor = new terramap.Point(22, 4);
			var tolay = new terramap.maps.overlay.Marker(geocoord, icon,content);
			tolay.disableDraggable();

			//tolay.closeInfoWindow();
			//tolay.showInfoWindow(null,null,false)
			G_map.addOverlay(tolay);
			tolay.events.register("click",tolay,function(){
				//tolay.closeInfoWindow();
				$("#search-list .showDetail").eq(index).trigger("click");
				//alert(index+'-'+content);
				//$("#cards-level1>li").css("display","none");
				//$("#cards-level0").fadeIn("slow");
				//$("#search-item").parent().fadeIn("slow");
			});
			tolay.events.register("mouseover",tolay,function(){
				//G_map.showInfoWindow(geocoord,content,true);
			});
		})
	};
})(jQuery);