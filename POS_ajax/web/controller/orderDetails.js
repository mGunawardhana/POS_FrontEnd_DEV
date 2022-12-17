

function loadAllPurchaseDetails() {

    /** removing table row repeating issue */
    $("#itemDetailsTblBody").empty();

    for (let p of purchaseDetails) {
        let purchaseRow = `<tr>
                        <td>${p.oID}</td>
                        <td>${p.date}</td>
                        <td>${p.cID}</td>
                        <td>${p.iCode}</td>
                        <td>${p.oQty}</td>
                        <td>${p.discount+" %"}</td>
                        <td>${p.Tot}</td>
                   </tr>`;
        $('#itemDetailsTblBody').append(purchaseRow);
    }
}

