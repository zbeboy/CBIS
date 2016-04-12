/**
 * Created by lenovo on 2016-04-12.
 */
(function () {
    var db = {

        loadData: function (filter) {
            return $.ajax({
                type: "GET",
                url: web_path + "/administrator/autonomicpractice/templateData",
                data: filter,
                dataType: "json"
            });
        },

        deleteItem: function (item) {
            return $.ajax({
                type: "POST",
                url: web_path + "/administrator/autonomicpractice/deleteTemplate",
                data: item,
                dataType: "json"
            });
        }

    };

    window.db = db;

}());