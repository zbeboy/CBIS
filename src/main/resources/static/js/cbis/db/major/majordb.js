/**
 * Created by lenovo on 2016-02-07.
 */
(function () {
    var db = {

        loadData: function (filter) {
            return $.ajax({
                type:"GET",
                url:"/cbis/maintainer/majordata",
                data:filter,
                dataType:"json"
            });
        },

        insertItem:function(item){
            return $.ajax({
               type:"POST",
                url:"/cbis/maintainer/savemajor",
                data:item,
                dataType:"json"
            });
        },

        updateItem:function(item){
            return $.ajax({
               type:"POST",
                url:"/cbis/maintainer/updatemajor",
                data:item,
                dataType:"json"
            });
        },

        deleteItem: function (item) {
            return $.ajax({
                type:"POST",
                url:"/cbis/maintainer/deletemajor",
                data:item,
                dataType:"json"
            });
        },



    };

    window.db = db;

}());