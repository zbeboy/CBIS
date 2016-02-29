/**
 * Created by lenovo on 2016-02-14.
 */
(function () {
    var db = {

        loadData: function (filter) {
            return $.ajax({
                type:"GET",
                url:"/cbis/maintainer/gradedata",
                data:filter,
                dataType:"json"
            });
        },

        deleteItem: function (item) {
            return $.ajax({
                type:"POST",
                url:"/cbis/maintainer/deletegrade",
                data:item,
                dataType:"json"
            });
        },



    };

    window.db = db;
}());