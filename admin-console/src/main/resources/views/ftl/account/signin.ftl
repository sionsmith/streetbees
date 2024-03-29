<#-- @ftlvariable name="" type="com.streetbees.views.SignInView" -->
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="Real Admin - Bootstrap Admin Template">
    <meta name="author" content="Łukasz Holeczek">
    <meta name="keyword" content="Real, Admin, Dashboard, Bootstrap, Admin, Template, Theme, Responsive, Fluid, Retina">
    <link rel="apple-touch-icon-precomposed" sizes="144x144" href="assets/ico/apple-touch-icon-144-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="114x114" href="assets/ico/apple-touch-icon-114-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="72x72" href="assets/ico/apple-touch-icon-72-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="57x57" href="assets/ico/apple-touch-icon-57-precomposed.png">
    <link rel="shortcut icon" href="assets/ico/favicon.png">

    <title>Real Admin - Bootstrap Admin Template</title>

    <!-- Bootstrap core CSS -->
    <link href="assets/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-style">

    <!-- Remove following comment to add Right to Left Support or add class rtl to body -->
    <!-- <link href="assets/css/bootstrap-rtl.min.css" rel="stylesheet"> -->

    <link href="assets/css/jquery.mmenu.css" rel="stylesheet">
    <link href="assets/css/simple-line-icons.css" rel="stylesheet">
    <link href="assets/css/font-awesome.min.css" rel="stylesheet">

    <!-- page css files -->
    <link href="assets/plugins/jquery-ui/css/jquery-ui-1.10.4.min.css" rel="stylesheet">
    <style>
        footer, #usage {
            display: none;
        }
    </style>

    <!-- Custom styles for this template -->
    <link href="assets/css/style.min.css" rel="stylesheet" id="main-style">
    <link href="assets/css/add-ons.min.css" rel="stylesheet">

    <!-- Remove following comment to add Right to Left Support or add class rtl to body -->
    <!-- <link href="assets/css/style.rtl.min.css" rel="stylesheet"> -->

    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>


<!-- BODY options, add following classes to body to change options

    1. 'sidebar-minified'     - Switch sidebar to minified version (width 50px)
    2. 'sidebar-hidden'		  - Hide sidebar
    3. 'rtl'				  - Switch to Right to Left Mode
    4. 'container'			  - Boxed layout
    5. 'static-sidebar'		  - Static Sidebar
    6. 'static-header'		  - Static Header
-->

<body class="login">


<div class="container">
    <div class="row">
        <div class="login-box col-lg-4 col-lg-offset-4 col-sm-6 col-sm-offset-3">

            <div class="header">
                Origin Admin
            </div>

            <form action="index.html" method="post">

                <fieldset>

                    <div class="form-group first">
                        <div class="input-group col-sm-12">
                            <span class="input-group-addon"><i class="fa fa-user"></i></span>
                            <input type="text" class="form-control input-lg" id="username" placeholder="Username or E-mail"/>
                        </div>
                    </div>

                    <div class="form-group last">
                        <div class="input-group col-sm-12">
                            <span class="input-group-addon"><i class="fa fa-key"></i></span>
                            <input type="password" class="form-control input-lg" id="password" placeholder="Password"/>
                        </div>
                    </div>

                    <button type="submit" class="btn btn-primary col-xs-12">Login</button>

                    <p class="small text-center">OR USE</p>

                    <div class="row social">

                        <div class="col-xs-4">
                            <button class="btn btn-sm btn-block btn-facebook"><span>Facebook</span></button>
                        </div><!--/col-->

                        <div class="col-xs-4">
                            <button class="btn btn-sm  btn-block btn-twitter"><span>Twitter</span></button>
                        </div><!--/col-->

                        <div class="col-xs-4">
                            <button class="btn btn-sm btn-block btn-linkedin"><span>LinkedIn</span></button>
                        </div><!--/col-->

                    </div><!--/row-->

                    <div class="row">

                        <div class="col-xs-5">
                            <a class="pull-right" href="page-register.html">Register</a>
                        </div><!--/col-->

                        <div class="col-xs-7">
                            <a class="pull-left" href="page-login.html#">Forgot Password?</a>
                        </div><!--/col-->

                    </div><!--/row-->

                </fieldset>

            </form>

        </div>
    </div><!--/row-->
</div><!--/container-->


<footer>

    <div class="row">

        <div class="col-sm-5">
            &copy; 2014 creativeLabs. <a href="http://bootstrapmaster.com">Admin Templates</a> by BootstrapMaster
        </div><!--/.col-->

        <div class="col-sm-7 text-right">
            Powered by: <a href="http://bootstrapmaster.com/demo/Real/" alt="Bootstrap Admin Templates">Real Admin</a> | Based on Bootstrap 3.2.0 | Built with brix.io <a href="http://brix.io" alt="Brix.io - Bootstrap Builder">Bootstrap Builder</a>
        </div><!--/.col-->

    </div><!--/.row-->

</footer>


<!-- start: JavaScript-->
<!--[if !IE]>-->

<script src="assets/js/jquery-2.1.1.min.js"></script>

<!--<![endif]-->

<!--[if IE]>

<script src="assets/js/jquery-1.11.1.min.js"></script>

<![endif]-->

<!--[if !IE]>-->

<script type="text/javascript">
    window.jQuery || document.write("<script src='assets/js/jquery-2.1.1.min.js'>"+"<"+"/script>");
</script>

<!--<![endif]-->

<!--[if IE]>

<script type="text/javascript">
    window.jQuery || document.write("<script src='assets/js/jquery-1.11.1.min.js'>"+"<"+"/script>");
</script>

<![endif]-->
<script src="assets/js/jquery-migrate-1.2.1.min.js"></script>
<script src="assets/js/bootstrap.min.js"></script>

<!-- page scripts -->


<!-- theme scripts -->
<script src="assets/plugins/pace/pace.min.js"></script>
<script src="assets/js/jquery.mmenu.min.js"></script>
<script src="assets/js/core.min.js"></script>

<!-- inline scripts related to this page -->
<script src="assets/js/pages/login.js"></script>
<script src="assets/plugins/jquery-cookie/jquery.cookie.min.js"></script>
<script src="assets/js/demo.min.js"></script>

<!-- end: JavaScript-->

</body>
</html>