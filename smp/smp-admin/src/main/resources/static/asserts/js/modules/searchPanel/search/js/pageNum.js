/**
 * Created by ShiYu on 2016/4/21.
 */


var DynamicPagination = function(total) {
	$('#dynamic_pager').bootpag({
		paginationClass: 'pagination',
		next: '<i class="fa fa-angle-right"></i>',
		prev: '<i class="fa fa-angle-left"></i>',
		total: total,
		page: 1,
		maxVisible: 6,
		//href:'javascript:showPlaceName(2)',
		//hrefVariable: '{{1}}',
	}).on("page", function(event, num){
		G_map.clearOverlays();
		showSearchResultList(num);
		//$("#dynamic_pager_content").html("Page " + num + " content here"); // or some ajax content loading...
	});
}

//DynamicPagination(6);

// var DynamicPaginationFav = function(total,type) {
// 	$('#dynamic_fav').bootpag({
// 		paginationClass: 'pagination',
// 		next: '<i class="fa fa-angle-right"></i>',
// 		prev: '<i class="fa fa-angle-left"></i>',
// 		total: total,
// 		page: 1,
// 		maxVisible: 6,
// 		//href:'javascript:showPlaceName(2)',
// 		//hrefVariable: '{{1}}',
// 	}).on("page", function(event, num){
// 		if(type=="poi"){
// 			G_map.clearOverlays();
// 			showCollectList(num);
// 		}else if(type=="route"){
// 			G_map.clearOverlays();
// 			restoreRouteList(num);
// 		}
// 		//$("#dynamic_pager_content").html("Page " + num + " content here"); // or some ajax content loading...
// 	});
// }
