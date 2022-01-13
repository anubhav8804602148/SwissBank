$(document).ready(function() {
    $("#inputUserDp").on("change", function(e){
    	if (e.currentTarget.files[0].size/1024/1024>1) {
            alert("Image file should be less than 1 MB");
            location.reload();
        }
	});
});