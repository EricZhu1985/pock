/**
 * 可转售列表js
 * 
 * 2016-04-28
 * 建立此文件
 * 
 */


function loadSellableList() {
	$('#sdg').datagrid('load', {
		after_time: $('#sdg_after_time').textbox('getValue')
	});
}