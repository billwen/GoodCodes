/**
 * From: Manning, RxJS in action 2017
 * Page: 106
 */

const copyToArray = (arrayLike) => Array.prototype.slice.call(arrayLike);

function debounce(fn, time) {
    let timeoutId;
    return function() {
        const args = [fn, time].concat(copyToArray(arguments));
        clearTimeout(timeoutId);
        timeoutId = setTimeout.apply(window, args);
    };
}

function debounce_example(query) {
    console.log('querying ...');
    let debounce_request = debounce(sendRequest, 1000);

    searchBox.addEventListener('keyup', function(event) { debounce_request(event.target.value); });
}