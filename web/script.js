function createGuid() {
    function s4() {
        return Math.floor((1 + Math.random()) * 0x10000)
            .toString(16)
            .substring(1);
    }
    return s4() + s4() + '-' + s4() + '-' + s4() + '-' +
        s4() + '-' + s4() + s4() + s4();
}

var id = createGuid();
$('#stackId').text(id);

var baseUri = window.location.pathname + 'api/stack/' + id + '/';

$('#pushButton').on('click', function() {
    var value = $('#pushValue').val();
    if (value !== '') {
        $.ajax(baseUri + 'push/' + value)
            .done(function() {
                $('#pushResult').text('pushed ' + value + ' to the stack');
            });
    } else {
        $('#pushResult').text('invalid value');
    }
});

$('#popButton').on('click', function() {
    $.ajax(baseUri + 'pop')
        .done(function(value, textStatus, xhr) {
            $('#popResult').text(xhr.status === 200 ?
                'popped ' + value + ' from the stack' :
                'nothing popped from the stack');
        });
});

$('#listButton').on('click', function() {
    $.ajax(baseUri + 'list')
        .done(function(array) {
            $('#listResult').text('stack content: [' + array.join(', ') + ']');
        });
});