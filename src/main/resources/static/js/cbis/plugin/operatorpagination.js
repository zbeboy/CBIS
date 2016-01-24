/**
 * Created by lenovo on 2016-01-23.
 */
function initPagination(obj) {
    UIkit.pagination('.uk-pagination', {
        items: obj.totalPages,
        itemsOnPage: 15,
        currentPage: obj.pageNum
    });
}

$('.uk-pagination').on('select.uk.pagination', function (e, pageIndex) {
    action(pageIndex + 1);
});