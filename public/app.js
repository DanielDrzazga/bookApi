
    let ajaxData = {};
    let ajaxUrl = "http://localhost:8080/books/";
    let ajaxTypeGET = "GET";
    let ajaxTypePOST = "POST";
    let ajaxTypeDELETE = "DELETE";
    let ajaxDataType = "json";

    function loadAllBooks() {
        let books = ajaxGetPostDelete(ajaxUrl, ajaxData, ajaxTypeGET, ajaxDataType);
        loadBooks(books);
    }

    function showHideBookInfo(bookId) {
        let bookTdEl = $("td[id=description" + bookId + "]");

        if (bookTdEl.is(":hidden")) {
            bookTdEl.show();
            let newAjaxUrl = ajaxUrl + bookId;

            let book = ajaxGetPostDelete(newAjaxUrl, ajaxData, ajaxTypeGET, ajaxDataType);
            loadDescriptionOfBook(bookTdEl, book);
        } else {
            bookTdEl.hide().empty();
        }
    }

    function deleteBook(bookId) {
        let newAjaxUrl = ajaxUrl + bookId;
        ajaxGetPostDelete(newAjaxUrl, ajaxData, ajaxTypeDELETE, ajaxDataType);
        deleteBookFromTable(bookId);

        //
        // $.ajax({
        //     url: "http://localhost:8080/books/" + bookId,
        //     type: "DELETE",
        // })
        //     .done()
        //     .fail(function (xhr, status, err) {
        //         alert("xhr status:" + xhr.status + "\nstatus: " + status + "\nerr :" + err);
        //     })
        //     .always(function (xhr, status) {
        //     });

    }

    $('#button').click(function (e) {
        e.preventDefault();
        let newBook = {
            isbn: $('#isbn').val(),
            title: $('#title').val(),
            author: $('#author').val(),
            publisher: $('#publisher').val(),
            type: $('#type').val(),
        };

        let ajaxData = JSON.stringify(newBook);

        clearInputValue(ajaxGetPostDelete(ajaxUrl, ajaxData, ajaxTypePOST, ajaxDataType));

    });

    function ajaxGetPostDelete(ajaxUrl, ajaxData, ajaxType, ajaxDataType) {
        let result = null;
        $.ajax({
            url: ajaxUrl,
            data: ajaxData,
            type: ajaxType,
            dataType: ajaxDataType,
            async: false
        })
            .done(function (book) {
                try {
                    if (book instanceof Array) {
                        result = [];
                        book.forEach(function (el) {
                            result.push(el);
                        });
                    } else {
                        result = book;
                    }
                } catch (e) {
                }
            })
            .fail(function (xhr, status, err) {
                alert("xhr status:" + xhr.status + "\nstatus: " + status + "\nerr :" + err);
            })
            .always(function (xhr, status) {
            });
        return result;
    }

    function loadBooks(result) {
        $.each(result, function (index, book) {
            $('table')
                .append($('<tr>').attr("id", "book_id" + book.id)
                    .append($('<td>').attr("id", "title" + book.id)
                        .append(book.title))
                    .append($('<td style="color: red">Delete</td>').attr("id", "delete" + book.id))
                )
                .append($('<tr>')
                    .append($('<td colspan="2">').attr("id", "description" + book.id).hide()));

            $('td[id=delete' + book.id + ']').one('click', function () {
                deleteBook(book.id);
            });

            $('td[id=title' + book.id + ']').on('click', function () {
                showHideBookInfo(book.id);
            });
        })
    }

    function loadDescriptionOfBook(bookTdEl, book) {
        bookTdEl.append($('<p>').append("isbn: " + book.isbn))
            .append($('<p>').append("author: " + book.author))
            .append($('<p>').append("publisher: " + book.publisher))
            .append($('<p>').append("type: " + book.type));
    }

    function deleteBookFromTable(bookId) {
        $('tr[id=book_id' + bookId + ']').empty()
            .next().remove();
    }

    function clearInputValue() {
        $('input[type=text]').each(function () {
            $(this).val("")
        });
        location.reload();
    }

    loadAllBooks();