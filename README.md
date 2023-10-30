# TimeServlet Java Web Application

[TimeServlet] -^init()-> [TemplateEngine]
[TimeServlet] --^doGet()--> [Client]
[TimeServlet] --^doGet()--> [TimezoneUtil]
[TimezoneUtil] --getTimeZoneFromRequest()--> [TimeZone]
[TimezoneUtil] --getTimeZoneFromRequest()--> [URLEncoder]
[TimeServlet] --^doGet()--> [Cookie]
[TimeServlet] --^doGet()--> [PrintWriter]
[TimeServlet] --^doGet()--> [Context]
[TemplateEngine] -^process()-> [Time-template.html]
[Time-template.html] --Loads HTML template-->
[TimezoneUtil] --^formatCurrentDateWithTimeZone()--> [SimpleDateFormat]
[SimpleDateFormat] --^format()--> [Date]

