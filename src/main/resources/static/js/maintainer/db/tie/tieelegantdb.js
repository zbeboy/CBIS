/**
 * Created by lenovo on 2016-01-25.
 */
(function () {
    var db = {

        loadData: function (filter) {
            return $.ajax({
                type: "GET",
                url: web_path + "/maintainer/tie/tieElegantData",
                data: filter,
                dataType: "json"
            });
        },

        deleteItem: function (item) {
            return $.ajax({
                type: "POST",
                url: web_path + "/maintainer/tie/deleteTieElegant",
                data: item,
                dataType: "json"
            });
        }

    };

    window.db = db;

}());