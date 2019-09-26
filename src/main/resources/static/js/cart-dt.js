// https://datatables.net/

$(document).ready( function () {
    var getTotalCost = function() {
        var res = $.ajax({
          type: "GET",
          url: "/springmarket/api/cart_items/cost",
          async: false
        }).done(function(data) {
          return data;
        });
        console.log(res);
        return res;
    }

	 var table = $('.table').DataTable({
	    "ajax": {
	      "url": "/springmarket/api/cart_items",
	      "dataSrc": ""
	    },
        "columns": [
            { "data": "product.title" },
            {
              "data": null,
              "render": function (data, type, row) {
                return '<input type="number" class="qnt-changer" min=1 ord_product_id="' + row.product.id + '" value="' + row.quantity + '"></input>';
              }
            },
            { "data": "totalPrice" }
        ] ,
        "initComplete": function(settings, json) {
          $('#totalCost').text('Итоговая сумма: ' + getTotalCost().responseText);
        }
//        "drawCallback": function(settings) {
//          $('#totalCost').text('Итоговая сумма: ' + getTotalCost().responseText);
//          initAllChangers();
//        }
	 })

    $('.table').on('click', '.qnt-changer', function() {
       $.ajax({
          type: "PUT",
          url: "/springmarket/api/cart_items",
          async: false,
          data: {
            'product_id': $(this).attr('ord_product_id'),
            'quantity': $(this).val()
          }
       }).done(function() {
          table.ajax.reload();
       });
       $('#totalCost').text('Итоговая сумма: ' + getTotalCost().responseText);
    });

//	 var initAllChangers = function() {
//             $('.qnt-changer').on('input', function(event) {
//                 $.ajax({
//                   type: "POST",
//                   url: "/springmarket/api/cart_items/change_qnt",
////                   async: false,
//                   data: {
//                     'product_id': $(this).attr('ord_product_id'),
//                     'qnt': $(this).val()
//                   }
//                 }).done(function() {
//                   table.ajax.reload(initAllChangers());
//                 });
//             })
//         }
});