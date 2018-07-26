<%-- 
    Document   : NavBar
    Created on : 26 Feb, 2018, 12:25:10 PM
    Author     : Vidya
--%>

<!-- DIV - (Navigation bar)  -->
<div class="" id="divNavbar">
    <nav class="navbar navbar-default">
        <div class="container-fluid" id="divNavbarStyle">
            <ul class="nav navbar-nav row">
                <li><a class="navbarLinkStyle" href="Home.jsp">Home</a></li>
                <li class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                        Services
                        <span class="caret"></span>
                    </a>
                    <ul class="dropdown-menu">
                        <li><a href="eSign.jsp">eSign</a></li>
                    </ul>
                </li>
                <li class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                        Reports
                        <span class="caret"></span>
                    </a>
                    <ul class="dropdown-menu">
                        <li><a href="ActivityReports.jsp">Activity Report</a></li>
                    </ul>
                </li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li><a href="logout.do?method=signOut" class="btn btn-default "><span class="glyphicon glyphicon-chevron-left"></span> Logout</a></li>
            </ul>
        </div>
    </nav>
</div>
