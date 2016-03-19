/**
 * Created by lenovo on 2016-02-07.
 */
(function () {
    var db = {

        loadData: function (filter) {
            return $.ajax({
                type:"GET",
                url:"/maintainer/majorData",
                data:filter,
                dataType:"json"
            });
        },

        insertItem:function(item){
            return $.ajax({
               type:"POST",
                url:"/maintainer/saveMajor",
                data:item,
                dataType:"json"
            });
        },

        updateItem:function(item){
            return $.ajax({
               type:"POST",
                url:"/maintainer/updateMajor",
                data:item,
                dataType:"json"
            });
        },

        deleteItem: function (item) {
            return $.ajax({
                type:"POST",
                url:"/maintainer/deleteMajor",
                data:item,
                dataType:"json"
            });
        },



    };

    window.db = db;

}());