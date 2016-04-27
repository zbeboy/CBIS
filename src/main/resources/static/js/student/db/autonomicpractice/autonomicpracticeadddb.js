/**
 * Created by lenovo on 2016-04-27.
 */
(function () {
    var db = {

        loadData: function (filter) {
            filter.autonomousPracticeTemplateId = templateId;
            return $.ajax({
                type: "GET",
                url: web_path + "/student/autonomicpractice/autonomicPracticeAdminData",
                data: filter,
                dataType: "json"
            });
        }

    };

    window.db = db;

}());