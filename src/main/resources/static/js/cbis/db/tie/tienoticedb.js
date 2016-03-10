/**
 * Created by lenovo on 2016-03-09.
 */
(function () {
    var db = {

        loadData: function (filter) {
            return $.ajax({
                type:"GET",
                url:"/maintainer/tieNoticeData",
                data:filter,
                dataType:"json"
            });
        },

        deleteItem: function (item) {
            return $.ajax({
                type:"POST",
                url:"/maintainer/deleteTieNotice",
                data:item,
                dataType:"json"
            });
        }

    };

    window.db = db;

}());