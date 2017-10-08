var contextUrl;
if(location.href.indexOf("allinyourhandsweb") > -1){
       contextUrl = "allinyourhandsweb.com";        
}else{
       contextUrl = "tudoemsuasmaos.com.br";
}

var baseURL = "http://" + contextUrl + "/rest/aiyhservice/";
angular.module("services", ["ngResource"]).factory("aiyhStatus", ["$resource", function(e) {
    return e(baseURL + "statusapis/:webVersion")
}]).factory("songService", ["$resource", function(e) {
    return e(baseURL + "songs/:query/:pagenumber/:lyric/:grooveshark")
}]).factory("songCategoriesService", ["$resource", function(e) {
    return e(baseURL + "songs/categories")
}]).factory("artistsByCategoryService", ["$resource", function(e) {
    return e(baseURL + "artists/by/category/:name/:pagenumber/:webVersion")
}]).factory("songsByArtistService", ["$resource", function(e) {
    return e(baseURL + "songs/by/artist/:query/:pagenumber/:lyric/:grooveshark")
}]).factory("streamingUrlService", ["$resource", function(e) {
    return e(baseURL + "streaming/:songid/:ipadress/:streamsessionid")
}]).factory("videoService", ["$resource", function(e) {
    return e(baseURL + "videos/:query")
}]).factory("videoPaginationService", ["$resource", function(e) {
    return e(baseURL + "videos/:query/:categoryid/:nextpagetoken")
}]).factory("videoCategoriesService", ["$resource", function(e) {
    return e(baseURL + "videos/categories/:countryCode")
}]).factory("bookService", ["$resource", function(e) {
    return e(baseURL + "booksv2/:keyword/:pagenumber/:countryCode")
}]).factory("latitudeLongitudeService", ["$resource", function(e) {
    return e(baseURL + "latitude_longitude/:address")
}]).factory("countryService", ["$resource", function(e) {
    return e(baseURL + "countries")
}]).factory("statesService", ["$resource", function(e) {
    return e(baseURL + "countries/states/:countryid")
}]).factory("placesNearService", ["$resource", function(e) {
    return e(baseURL + "places/near_places/:latitude_longitude/:countrycode/:type/:offset")
}]).factory("placesSuggestedService", ["$resource", function(e) {
    return e(baseURL + "places/recommended_places/:latitude_longitude/:countrycode/:type/:offset")
}]).factory("directionsService", ["$resource", function(e) {
    return e(baseURL + "directions/:origin_address/:destiny_address/:mode/:countrycode")
}]).factory("weatherService", ["$resource", function(e) {
    return e(baseURL + "weather/forecast/:latitude_longitude/:countrycode")
}]).factory("recommendedContentsService", ["$resource", function(e) {
    return e(baseURL + "contents/recommended")
}]).factory("emailSenderService", ["$resource", function(e) {
    return e(baseURL + "email/send/:subject/:message")
}]).factory("urlShortnerService", ["$resource", function(e) {
    return e(baseURL + "url/shortner/:longURL")
}]).factory("reportService", ["$resource", function(e) {
    return e(baseURL + "report/send/:reportParameters}")
}]);