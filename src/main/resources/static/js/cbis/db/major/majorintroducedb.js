/**
 * Created by lenovo on 2016-02-07.
 */
(function () {
    var db = {

        loadData: function (filter) {
            return $.ajax({
                type:"GET",
                url:"/cbis/maintainer/majorintroducedata",
                data:filter,
                dataType:"json"
            });
        },

        deleteItem: function (item) {
            return $.ajax({
                type:"POST",
                url:"/cbis/maintainer/deletemajorintroduce",
                data:item,
                dataType:"json"
            });
        }

    };

    window.db = db;

}());