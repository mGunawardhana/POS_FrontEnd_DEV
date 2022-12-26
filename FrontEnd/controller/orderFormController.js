/*
 *
 *  * Developed by - mGunawardhana
 *  * Contact email - mrgunawardhana27368@gmail.com
 *  * what's app - 071 - 9043372
 *
 */
let baseURL = "http://localhost:8080/deployApp/";

/** array for storing order details */
let orderDetails = [];

/** array for storing order details */
let purchaseDetails = [];

let bool = false;
let orderAmount = 0;

loadAllCustomersToCombo();
loadAllItemToCombo();

let autoGeneratedOrderId = 1;
$('#txtOrderID').val('O00-00' + autoGeneratedOrderId);

/** loading all customer id's in to the combo */
function loadAllCustomersToCombo() {
    $('#orderCustomerID').empty();

    $.ajax({
        url: baseURL + "customer",
        method: "GET",
        dataType: "json",
        success: function (res) {
            for (let customer of res.data) {
                $("#orderCustomerID").append(`<option>${customer.id}</option>`)
            }
        },
        error: function (error) {
            let message = JSON.parse(error.responseText).message;
            alert(message);
        }
    });
}

/** loading all item id's in to the combo */
function loadAllItemToCombo() {
    $('#itemCodeCombo').empty();
    $.ajax({
        url: baseURL + "item",
        method: "GET",
        dataType: "json",
        success: function (res) {
            for (let item of res.data) {
                $("#itemCodeCombo").append(`<option>${item.itemId}</option>`)
            }
        },
        error: function (error) {
            let message = JSON.parse(error.responseText).message;
            alert(message);
        }
    });
}

/** combo operation on oder id */
$('#orderCustomerID').on('click', function () {
    let customerID = $('#orderCustomerID').val();
    $.ajax({
        url: baseURL + "customer",
        method: "GET",
        dataType: "json",
        success: function (res) {
            for (let customer of res.data) {
                if (customer.id === customerID) {
                    $("#selectCusName").val(customer.name);
                    $("#orderCustomerContact").val(customer.address);
                    $("#orderCustomerAddress").val(customer.contact);
                }
            }
        }
    });
});

/** combo operation on oder id */
$('#itemCodeCombo').on('click', function () {
    $.ajax({
        url: baseURL + "item",
        method: "GET",
        dataType: "json",
        success: function (res) {
            for (let item of res.data) {
                if (item.itemId === $('#itemCodeCombo').val()) {
                    $("#selectItemCode").val(item.itemId);
                    $("#txtItemDescription").val(item.itemName);
                    $("#txtItemPrice").val(item.unitPrice);
                    $("#txtQTYOnHand").val(item.qty);
                }
            }
        }
    });
})

/** add to cart option */
$('#btnAddToTable').on('click', function () {
    $("#orderTblBody").empty();
    loadAllOderDetails();

    let tot = parseInt($("#txtItemPrice").val()) * $('#txtQty').val();

    let orderArray = new Order(
        $('#selectItemCode').val(),
        $("#txtItemDescription").val(),
        $("#txtItemPrice").val(),
        $("#txtQty").val(),
        tot,
        $('#txtOrderID').val()
    );

    let qty1;
    let qty2;
    let tot1;
    let tot2;
    let setAmount = 0;

    for (let i = 0; i < orderDetails.length; i++) {
        let test = orderDetails[i];

        if ($('#selectItemCode').val() === test.iCode && $('#txtOrderID').val() === test.orderId) {
            bool = true;
            qty1 = parseInt(test.Qty);
            tot1 = parseInt(test.total);
            qty2 = parseInt($("#txtQty").val());
            tot2 = tot;

            test.iCode = $("#selectItemCode").val();
            test.itemName = $("#txtItemDescription").val();
            test.price = $("#txtItemPrice").val();
            test.Qty = (qty1 + qty2);
            test.total = (tot1 + tot2);

            $('#orderTblBody').empty();

            $('#total').val();//TODO set up total value here
        }
    }

    if (bool === false) {
        orderDetails.push(orderArray);
    }

    loadAllOderDetails();

    let z = $("#txtItemPrice").val() * parseInt($('#txtQty').val());

    let singleOrder = new OrderDetails(
        $('#txtOrderID').val(),
        $('#txtDate').val(),
        $("#orderCustomerID").val(),
        $("#selectItemCode").val(),
        $("#txtQty").val(),
        z,
        $("#txtDiscount").val()
    );

    purchaseDetails.push(singleOrder);
    orderAmount += tot;
});

/** loading all details for cart */
function loadAllOderDetails() {

    /** removing table row repeating issue */
    $("#orderTblBody").empty();

    for (let oDetails of orderDetails) {
        let orderRow = `<tr>
                        <td>${oDetails.iCode}</td>
                        <td>${oDetails.itemName}</td>
                        <td>${oDetails.price}</td>
                        <td>${oDetails.Qty}</td>
                        <td>${oDetails.total}</td>
                   </tr>`;
        $('#orderTblBody').append(orderRow);
        bool = false;
    }
}

$("#btnSubmitOrder").on('click', function () {
    let order_id = $('#txtOrderID').val();
    let order_date = $('#txtDate').val();
    let customer_id = $('#orderCustomerID').val();
    let item_code = $('#itemCodeCombo').val();
    let order_qty = $('#txtQty').val();
    let discount = $('#txtDiscount').val();
    let total = $('#subtotal').val();

    let ob = {
        order_id: order_id,
        order_date: order_date,
        customer_id: customer_id,
        discount: discount
    }

    array.push({code:itCode,name:itName,price:Price,quantity:Quantity,total:total});


    $.ajax({
        url: baseURL + "purchase",
        method: "post",
        dataType: "json",
        data: JSON.stringify(ob),
        contentType: "application/json",
        success: function (resp) {
            alert(resp.message);
        },
        error: function (error) {
            alert(JSON.parse(error.responseText).message);
        }
    });

});

function getItemDetails() {
    let rows = $("#orderTblBody").children().length;
    var array = [];
    for (let i = 0; i < rows; i++) {
        let itCode = $("#orderTblBody").children().eq(i).children(":eq(0)").text();
        let itName = $("#orderTblBody").children().eq(i).children(":eq(2)").text();
        let Price = $("#orderTblBody").children().eq(i).children(":eq(3)").text();
        let Quantity = $("#orderTblBody").children().eq(i).children(":eq(4)").text();
        let total = $("#orderTblBody").children().eq(i).children(":eq(5)").text();
        array.push({code:itCode,name:itName,price:Price,quantity:Quantity,total:total});
    }
    return array;
}




