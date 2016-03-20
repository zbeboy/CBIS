/**
 * Created by lenovo on 2016-02-14.
 */
(function () {
    var db = {

        loadData: function (filter) {
            return $.ajax({
                type:"GET",
                url:"/maintainer/gradeData",
                data:filter,
                dataType:"json"
            });
        },

        deleteItem: function (item) {
            return $.ajax({
                type:"POST",
                url:"/maintainer/deleteGrade",
                data:item,
                dataType:"json"
            });
        },



    };

    window.db = db;
    db.majors = majors;
}());