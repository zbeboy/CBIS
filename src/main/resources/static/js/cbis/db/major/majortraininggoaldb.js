/**
 * Created by lenovo on 2016-02-09.
 */
(function () {
    var db = {

        loadData: function (filter) {
            return $.ajax({
                type:"GET",
                url:"/cbis/maintainer/majortraininggoaldata",
                data:filter,
                dataType:"json"
            });
        },

        deleteItem: function (item) {
            return $.ajax({
                type:"POST",
                url:"/cbis/maintainer/deletemajortraininggoal",
                data:item,
                dataType:"json"
            });
        }

    };

    window.db = db;

}());