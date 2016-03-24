/**
 * Created by lenovo on 2016-02-07.
 */
(function () {
    var db = {

        loadData: function (filter) {
            return $.ajax({
                type: "GET",
                url: web_path + "/maintainer/major/majorData",
                data: filter,
                dataType: "json"
            });
        },

        insertItem: function (item) {
            return $.ajax({
                type: "POST",
                url: web_path + "/maintainer/major/saveMajor",
                data: item,
                dataType: "json"
            });
        },

        updateItem: function (item) {
            return $.ajax({
                type: "POST",
                url: web_path + "/maintainer/major/updateMajor",
                data: item,
                dataType: "json"
            });
        },

        deleteItem: function (item) {
            return $.ajax({
                type: "POST",
                url: web_path + "/maintainer/major/deleteMajor",
                data: item,
                dataType: "json"
            });
        }

    };

    window.db = db;

}());