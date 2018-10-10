function getSelectedRowsCount() {
	var count = 0;
	$(".tbody-selection").find("tr").each(function () {
		var row = $(this);
        if (row.find("input[type='checkbox']").is(":checked")) {
        	count++;
        }
	});
	return count;
}

function allRowsSelected() {
	var rowCount = $(".tbody-selection tr td .mdl-checkbox").length;
	return getSelectedRowsCount() == rowCount;
}

$(function() {
	$(".table-selection").on("click", ".checkbox-table-selection", function() {
		var selectedRowsCount = getSelectedRowsCount();
		var row = $(this).parent().parent().parent();
		if ($(this).is(":checked")) {
			if (!row.hasClass("dont-remove-class-is-select")) {
				row.addClass("is_select");
			}
		} else {
			if (!row.hasClass("dont-remove-class-is-select")) {
				row.removeClass("is_select");
			}
			$(this).removeAttr("checked", "checked");
		}
		if ($(this).attr("id") != $("#checkbox-select-all").attr("id")) {
			var checkbox = document.getElementById("label-select-all");
			if (allRowsSelected()) {
				checkbox.MaterialCheckbox.check();
			} else {
				if (checkbox.MaterialCheckbox != null) {
					checkbox.MaterialCheckbox.uncheck();	
				}					
				$("#th-select-all").removeClass("is_select");	
			}
		}
		if (selectedRowsCount > 0) {
			enableButton(".btn-table-selection-dependency");
		} else {
			disableButton(".btn-table-selection-dependency");
		}
		if (selectedRowsCount == 1) {
			window.postMessage({"action": "showListDetails"}, "*");
			if ($(".section-details").size() > 0) {
				$(".section-list.base_view_space_center_fluid").addClass("base_view_space_center");
				$(".section-list.base_view_space_center_fluid").removeClass("base_view_space_center_fluid");
				$(".section-pagination.base_view_space_bottom_fluid").addClass("base_view_space_bottom");
				$(".section-pagination.base_view_space_bottom_fluid").removeClass("base_view_space_bottom_fluid");
				
				$(".section-list.base_view_space_center_height_fluid_right_left").addClass("base_view_space_right_fluid");
				$(".section-list.base_view_space_center_height_fluid_right_left").removeClass("base_view_space_center_height_fluid_right_left");
				$(".section-pagination.base_view_space_bottom_fluid_right_left").addClass("base_view_space_bottom_right_fluid");
				$(".section-pagination.base_view_space_bottom_fluid_right_left").removeClass("base_view_space_bottom_fluid_right_left");
				$(".t-hide").hide();
				
				$(".section-details").show();
			}
		} else {
			window.postMessage({"action": "hideListDetails"}, "*");
			if ($(".section-details").size() > 0) {
				$(".section-list.base_view_space_center").addClass("base_view_space_center_fluid");
				$(".section-list.base_view_space_center").removeClass("base_view_space_center");
				$(".section-pagination.base_view_space_bottom").addClass("base_view_space_bottom_fluid");
				$(".section-pagination.base_view_space_bottom").removeClass("base_view_space_bottom");
				
				$(".section-list.base_view_space_right_fluid").addClass("base_view_space_center_height_fluid_right_left");
				$(".section-list.base_view_space_right_fluid").removeClass("base_view_space_right_fluid");
				$(".section-pagination.base_view_space_bottom_right_fluid").addClass("base_view_space_bottom_fluid_right_left");
				$(".section-pagination.base_view_space_bottom_right_fluid").removeClass("base_view_space_bottom_right_fluid");
				$(".t-hide").show();
				
				$(".section-details").hide();
			}
		}
	});
});

$(function() {
	$("#checkbox-select-all").change(function() {
		var labels = document.querySelectorAll(".mdl-js-checkbox");
		var isSelectAllChecked = $("#checkbox-select-all").is(":checked");
		for(var i = 0, n = labels.length; i < n; i++){										
			var label = labels[i];
			if ($("#checkbox-select-all").first().parent()[0] == label) {
				continue;
			}
			var checkBox = $(label).find(".checkbox-table-selection").first()[0];
			if(isSelectAllChecked) {				
				if (!checkBox.checked) {
					label.click();
				}				
			} else {
				if (checkBox.checked) {
					label.click();
				}
		  }
		}		
		if( $(this).is(":checked")) {
			$(".tbody-selection").find("tr").each(function () {
				var row = $(this);
				if (!row.hasClass("dont-remove-class-is-select")) {
					row.addClass("is_select");
				}
			});
		} else {
			$(".tbody-selection").find("tr").each(function () {
				var row = $(this);
				if (!row.hasClass("dont-remove-class-is-select")) {
					row.removeClass("is_select");
				}
			});
		}
		if (getSelectedRowsCount() > 0) {
			enableButton(".btn-table-selection-dependency");
		} else {
			disableButton(".btn-table-selection-dependency");
		}
	});
})

$(function() {
	$(".tbody-selection").on("click", ".td-selection", function() {
	    $(".tbody-selection").find("tr").each(function () {
	    	var row = $(this);
	    	if (!row.hasClass("dont-remove-class-is-select")) {
	    		$(this).removeClass("is_select");
	    	}
	 	});
		var elements = document.querySelectorAll(".mdl-js-checkbox");
		for(var i = 0, n = elements.length; i < n; i++) {
			var element = elements[i];
			if (element.MaterialCheckbox != null) {
				element.MaterialCheckbox.uncheck();
			}
		}
		var checkbox = $($(this).parent()).find(".checkbox-table-selection").first();
		checkbox.click();
		if (getSelectedRowsCount() > 0) {
			enableButton(".btn-table-selection-dependency");
		} else {
			disableButton(".btn-table-selection-dependency");
		}
	});
});

function getSelectedRowCodes() {
	var codes = new Array();
    $(".tbody-selection").find("tr").each(function () {
    	var row = $(this);
        if (row.find("input[type='checkbox']").is(":checked")) {
        	var code = row.find("input[type='hidden'][id='input-row-code']").val();
        	codes.push(code);
        }
    });	    
    return codes;
}

function getAllRowCodes() {
	var codes = new Array();
    $(".tbody-selection").find("tr").each(function () {
    	var row = $(this);
    	var code = row.find("input[type='hidden'][id='input-row-code']").val();
    	codes.push(code);
    });	    
    return codes;
}