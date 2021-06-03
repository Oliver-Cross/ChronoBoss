package com.example.chronoboss

class TestingTimer {

    /* only update the graphic when the app is reopened using onResume()
    * this method is separate to the actual tracking
    * we do it by everytime the app is opened, getting the usage stats
    * we check the total time in foreground of the app and compare it t
    * its previous value and take the distance
    * we use this value to update the graphic
     */
    /* we need a way of triggering a notification
     */
    /* we need a way of running our app in the background
    it should poll the operating system periodically and pull usage statistics
    potentially we could do this by checking the app currently in the foreground
    and only pulling stats if it is our target package
    everytime we poll, we take the difference in totalTimeInForeground value
    and we decrement this from the time limit
    we could then have a function isTimeLimitReached which returns a boolean if the time remaining is
    greater than or equal to the time limit
    if this boolean is true, we trigger the notification and we stop the app polling
     */
    /* the update of the graphic is separate to the polling done to keep track of the
    current time. However, we could also simply use the return from the currentTimeLeft
    function and use the value from here so we don't have to do an extra call to the OS
     */
    /* methods are currentTimeUsed (or timeRemaining, int), isLimitReached (boolean),
     onResume (void) , and updateGraphic (void)
     */
    /* we need to figure out where to put these functions */
    /* we also need a way of running code at specific, periodic times
    possibly using AlarmManager. This would be useful both for resetting the time limit
    and for polling the system - if we could trigger the polling code at a specified
    time interval, for example, every minute (we may have to do it less frequently to
    preserve battery initially before we find a better way of doing this)
     */
    /* we need to update the app being tracked in the database etc. when the button is pushed
    in the settings page to refresh the top app
     */
    /* we need to figure out setting the time limit. After adjusting it, the user has to click
    "apply", which will trigger the app to send the current value of the bar to the other
    fragments such as the home screen, to update the timer
     */
    /* we should add functions to the QueryStatsUtils to get other measures from UsageStatsManager
     */
    /* variables we need include
    timeLimit (user sets in settings, transferred to home fragment timer graphic, it is updated
    by the user adjusting the seekbar, then setting "apply")
    topPackage (from QueryStatsUtils)
    appToTrack (from settings, updated when user clicks "get app info" then "apply"
    timeInForeground
    appIcon
    statsForDay
    compareToLimit (progress page)
    setTimeOver() sets amount for progress page
    various measures from QueryStatsUtils
    some kind of object with necessary values to send to database

     */
    /* functions
    resetDay() which should reset the graphic on the home page, update the graph
    getTimeInForeground() which returns a long value of the current time the top package has
    been used in the foreground
    isLimitReached boolean which says if the time limit has been reached - maybe actually put
    this as a global variable
    sendNotifiction - each time the system is polled, check if limit is reached, and if it is,
    send a notification (potentially we only check if the limit is reached once we get close to it)
    stopTracking() which stops the app polling the operating system
     */
    /* safety checks
    permissions - we are currently assuming the user grants permission
    if they don't we need a try/catch that displays a message saying "no permission granted"
    if the user changes the limit, and their current timeInForeground is greater than that limit,
    we need to have a way of handling this - a boolean function checking if valid, if not show message
    "please select a valid time limit"
     */
}