
var baseURL = "https://s62qqxo46e.execute-api.us-west-2.amazonaws.com/Stage/rest/v1/";

angular.module("services", ["ngResource"]).factory("aiyhStatus", ["$resource", function(e) {
    return e(baseURL + "status")

}]).factory("songService", ["$resource", function(e) {

    return e(baseURL + "songs?q=:q&pagenumber=:pagenumber&lyric=:lyric")

}]).factory("songCategoriesService", ["$resource", function(e) {
    return e(baseURL + "category/songs")

}]).factory("artistsByCategoryService", ["$resource", function(e) {
    return e(baseURL + "category/artists?categoryname=:categoryname&pagenumber=:pagenumber&isportalweb=:isportalweb")

}]).factory("songsByArtistService", ["$resource", function(e) {
    return e(baseURL + "songs/by/artist/:query/:pagenumber/:lyric/:grooveshark")

}]).factory("streamingUrlService", ["$resource", function(e) {
    return e(baseURL + "streaming?songid=:songid&ipadress=:ipadress&sessionid=:sessionid")

}]).factory("videoService", ["$resource", function(e) {
    return e(baseURL + "videos?q=:q")

}]).factory("videoPaginationService", ["$resource", function(e) {
    return e(baseURL + "videos?q=:q&categoryid=:categoryid&nextpagetoken=:nextpagetoken")

}]).factory("videoCategoriesService", ["$resource", function(e) {
    return e(baseURL + "category/videos?countrycode=:countrycode")

}]).factory("bookService", ["$resource", function(e) {
    return e(baseURL + "books?keyword=:keyword&pagenumber=:pagenumber&countryCode=:countryCode")

}]).factory("latitudeLongitudeService", ["$resource", function(e) {
    return e(baseURL + "latitude_longitude?address=:address")

}]).factory("countryService", ["$resource", function(e) {
    return e(baseURL + "countries")

}]).factory("statesService", ["$resource", function(e) {
    return e(baseURL + "states?countryid=:countryid")

}]).factory("placesNearService", ["$resource", function(e) {
    return e(baseURL + "nearplaces?latitude_longitude=:latitude_longitude&countrycode=:countrycode&type=:type&offset=:offset")

}]).factory("placesSuggestedService", ["$resource", function(e) {
    return e(baseURL + "suggestedplaces?latitude_longitude=:latitude_longitude&countrycode=:countrycode&type=:type&offset=:offset")

}]).factory("directionsService", ["$resource", function(e) {
    return e(baseURL + "directions?origin_address=:origin_address&destiny_address=:destiny_address&mode=:mode&countrycode=:countrycode")

}]).factory("weatherService", ["$resource", function(e) {
    return e(baseURL + "weather/forecast?latitude_longitude=:latitude_longitude&countrycode=:countrycode")

}]).factory("recommendedContentsService", ["$resource", function(e) {
    return e(baseURL + "contents/recommended")

}]).factory("emailSenderService", ["$resource", function(e) {
    return e(baseURL +"/email/send/:subject/:message")

}]).factory("urlShortnerService", ["$resource", function(e) {
    return e(baseURL + "url/shortner?longURL=:longURL")

}]).factory("reportService", ["$resource", function(e) {
    return e(baseURL +"/report/send/:messageText")

}]);
