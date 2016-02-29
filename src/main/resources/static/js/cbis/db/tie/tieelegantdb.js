/**
 * Created by lenovo on 2016-01-25.
 */
(function () {
    var db = {

        loadData: function (filter) {
           return $.ajax({
               type:"GET",
               url:"/maintainer/tieElegantData",
               data:filter,
               dataType:"json"
           });
        },

        deleteItem: function (item) {
            return $.ajax({
                type:"POST",
                url:"/maintainer/deleteTieElegant",
                data:item,
                dataType:"json"
            });
        }

    };

    window.db = db;

}());