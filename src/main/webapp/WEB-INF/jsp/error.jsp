<html>
<head>
    <title>Error</title>
    <link rel="shortcut icon" type="image/gif" href="/resources/favicon.ico"/>
    <link rel="stylesheet" href="resources/style.css"/>
    <link rel="stylesheet" href="resources/bootstrap.min.css"/>
</head>
<body>
    <%--<h1>${message}</h1>--%>
    <%--<div class="home-link-wrapper">--%>
        <%--<a href="/">Go Home</a>--%>
    <%--</div>--%>
    <div class="page-wrap d-flex flex-row align-items-center">
        <div class="container">
            <div class="row justify-content-center">
                <div class="col-md-12 text-center">
                    <span class="display-1 d-block">Something went wrong</span>
                    <div class="mb-4 lead">${message}</div>
                    <%--<div class="mb-4 lead">The page you are looking for was not found.</div>--%>
                    <a href="/" class="btn btn-link" style="size: 20px">Back to Home</a>
                </div>
            </div>
        </div>
    </div>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
</body>
</html>