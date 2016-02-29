/**
 * Created by lenovo on 2016-02-09.
 */
(function () {
    var db = {

        loadData: function (filter) {
            return $.ajax({
                type:"GET",
                url:"/cbis/maintainer/majortraitdata",
                data:filter,
                dataType:"json"
            });
        },

        deleteItem: function (item) {
            return $.ajax({
                type:"POST",
                url:"/cbis/maintainer/deletemajortrait",
                data:item,
                dataType:"json"
            });
        }

    };

    window.db = db;

}());