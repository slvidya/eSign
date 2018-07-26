<%-- 
    Document   : AdminNavBar
    Created on : 26 Feb, 2018, 12:35:21 PM
    Author     : Vidya
--%>

<div class="" id="divNavbar">
    <nav class="navbar navbar-default">
        <div class="container-fluid" id="divNavbarStyle">
            <ul class="nav navbar-nav row">
                <li><a class="navbarLinkStyle" href="AdminHome.jsp">Home</a></li>
                <li class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                        Reports
                        <span class="caret"></span>
                    </a>
                    <ul class="dropdown-menu">
                        <li><a href="SummaryReports.jsp">Summary Reports</a></li>
                    </ul>
                </li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li><a href="logout.do?method=signOut" class="btn btn-default"><span class="glyphicon glyphicon-chevron-left"></span> Logout</a></li>
            </ul>
        </div>
    </nav>
</div>
