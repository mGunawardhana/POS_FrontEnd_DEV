/*
 * Developed by - mGunawardhana
 * Contact email - mrgunawardhana27368@gmail.com
 * what's app - 071 - 9043372
 */
let baseURL = "http://localhost:8080/dev90/";
getAllItems();

/** save item option */
$("#addItemBtn").on('click', function () {
    let formData = $("#itemFormController").serialize();
    $.ajax({
        url:baseURL+ "item",
        method: "post",
        data: formData,
        dataType:"json",
        success: function (res) {
            getAllItems();
            alert(res.message);
        },error:function (error){
            alert(JSON.parse(error.responseText).message);
        }
    });
});

/** delete item option */
$("#deleteItemBtn").on('click',function () {
    let itemId = $("#srcItemID").val();
    $.ajax({
        url:baseURL+ "item?itemId=" + itemId,
        method: "delete",
        dataType:"json",
        success: function (resp) {
            getAllItems();
            alert(resp.message);
        },error:function (error){
            alert(JSON.parse(error.responseText).message);

        }
    });
});


/** update item option */
$("#updateItemBtn").on('click', function () {

    let itemId = $('#itemIdTxt').val();
    let itemName = $('#ItemNameTxt').val();
    let itemQty = $('#itemQtyTxt').val();
    let unitPrice = $('#unitPriceTxt').val();

    var itemObject = {
        itemId:itemId,
        itemName:itemName,
        qty:itemQty,
        unitPrice:unitPrice
    };

    $.ajax({
        url:baseURL+ "item",
        method: "put",
        contentType: "application/json",
        data: JSON.stringify(itemObject),
        success: function (res) {
            getAllItems();
        }
    });
});

/** getting all item details function */
function getAllItems() {
    $("#itemTblBody").empty();
    $.ajax({
        url:baseURL+ "item",
        success: function (res) {
            for (let i of res) {

                let Id = i.itemId;
                let itemName = i.itemName;
                let itemQty = i.qty;
                let unitPrice = i.unitPrice;


                let row = "<tr>" +
                    "<td>" + Id + "</td>" +
                    "<td>" + itemName + "</td>" +
                    "<td>" + itemQty + "</td>" +
                    "<td>" + unitPrice + "</td>" +
                    "</tr>"
                ;

                $("#itemTblBody").append(row);
            }
            bindRowClickEventsForItems();
        }
    });
}

/** binding click events */
function bindRowClickEventsForItems() {
    $("#itemTblBody>tr").on('click', function () {
        let Id = $(this).children(":eq(0)").text();
        let itemName = $(this).children(":eq(1)").text();
        let itemQty = $(this).children(":eq(2)").text();
        let unitPrice = $(this).children(":eq(3)").text();

        $('#itemIdTxt').val(Id);
        $('#ItemNameTxt').val(itemName);
        $('#itemQtyTxt').val(itemQty);
        $('#unitPriceTxt').val(unitPrice);
    });
}

