/**
 * Created by lenovo on 2016-01-18.
 */
var anjularScope = null;
/*scope*/
var anjularHttp = null;
/*http*/
var key = '';
/**init param*/
function tieelegantlist($scope, $http) {
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $http.defaults.headers.post[header] = token;
    anjularScope = $scope;
    anjularHttp = $http;
    /*init search*/
    UIkit.search('.uk-search', {
        source: '/admin/searchitems'
    });
    if (!startSearch) {
        action(1);
    } else {
        key = $('#getKey').text();
        anjularScope.article = article;
        initPagination(pagination);
    }

}

/**request*/
function action(pageNum) {
    if (key == null) {
        key = '';
    }
    // Simple POST request example (passing data) :
    anjularHttp({
        method: 'POST',
        url: '/admin/tieelegantlist',
        params: {
            'key': key,
            'pageNum': pageNum
        }
    }).success(function (data, status) {
        if (status) {
            if (data.state) {
                anjularScope.article = data.result;
                initPagination(data.paginationData);
            }
        } else {
            UKalert('网络异常，请稍后重试！');
        }
    });
}