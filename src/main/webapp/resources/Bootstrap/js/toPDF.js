var
    form,
 a4 = [640, 841.89]; // for a4 size paper width and height
$(document).ready(function () {
    $(".create_pdf").on("click", function (e) {
        $('body').scrollTop(0);
        var selector = $(e.target).attr('myprint');
        createPDF(selector);
    });
});


function createPDF(selector) {
    form = $(selector);
    getCanvas().then(function (canvas) {
        var cache_width = form.width()
        var height=form.height()
        var
         img = canvas.toDataURL("image/png"),
         doc = new jsPDF({
             unit: 'px',
             orientation:'lanscape',
             format: 'a4'
         });
        pageHeight= doc.internal.pageSize.height;

     // Before adding new content
     y = 500 // Height position of new content
     if (y >= pageHeight)
     {
       doc.addPage();
       y = 0 // Restart height position
     }
        doc.addImage(img, 'JPEG', 20, 20);
        doc.save('techumber-html-to-pdf.pdf');
        form.width(1194);
    });
}

// create canvas object
function getCanvas() {

    form.width(1042).css('max-width', 'none');
    return html2canvas(form, {
        imageTimeout: 2000,
        removeContainer: true
    });
}