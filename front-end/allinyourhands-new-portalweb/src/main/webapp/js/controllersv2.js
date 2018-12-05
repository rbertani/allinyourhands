angular.module("root", ["ngRoute","services","ngCookies","growlNotifications","xlat","pubnub.angular.service"]).config(["$routeProvider", "$locationProvider", function($routeProvider,$locationProvider) {
        $routeProvider

            // route for the root page
           /* .when('/', {
                templateUrl : 'mobile/home.html',
                controller  : 'aiyhMainController', 
							
            })
			
			 // route for the home page
            .when('/home', {
                templateUrl : 'mobile/home.html',
                controller  : 'aiyhMainController'
            })*/

			 // route for the books page
            .when('/songs', {
                templateUrl : 'mobile/songs.html',
                controller  : 'aiyhMainController',
							
            })
			
            // route for the books page
            .when('/books', {
                templateUrl : 'mobile/books.html',
                controller  : 'aiyhMainController',
							
            })
			
			 // route for the videos page
            .when('/videos', {
                templateUrl : 'mobile/video.html',
                controller  : 'aiyhMainController'
            })
			
			 // route for the places page
            .when('/places', {
                templateUrl : 'mobile/places.html',
                controller  : 'aiyhMainController'
            })
			
			 // route for the weather page
            .when('/weather', {
                templateUrl : 'mobile/weather.html',
                controller  : 'aiyhMainController'
            })
			
			// route for the chat page
            .when('/chat', {
                templateUrl : 'mobile/chat.html',
                controller  : 'aiyhMainController'
            })

            // route for the contact page
            .when('/contact', {
                templateUrl : 'mobile/contact.html',
                controller  : 'aiyhMainController'
            });
			
			$locationProvider.html5Mode(true);
			
    }]).controller("aiyhMainController", ["$scope","$cookieStore",
        "aiyhStatus","songService","songCategoriesService","artistsByCategoryService", "songsByArtistService",
         "streamingUrlService","videoService","videoPaginationService","videoCategoriesService","bookService","latitudeLongitudeService",
           "countryService","statesService","placesNearService","placesSuggestedService","directionsService",
            "weatherService","recommendedContentsService","emailSenderService","urlShortnerService","reportService","xlatService","PubNub",

        function($scope,$cookieStore,
         aiyhStatus,songService,songCategoriesService,artistsByCategoryService,songsByArtistService,
         streamingUrlService,videoService,videoPaginationService,videoCategoriesService,bookService,latitudeLongitudeService,
           countryService,statesService,placesNearService,placesSuggestedService,directionsService,
           weatherService,recommendedContentsService,emailSenderService,urlShortnerService,reportService,xlatService,PubNub

         ){  
		  			
          $scope.userLogoff = function(){
              $scope.userLogged = false;
              $scope.selectedUserName = '';
              PubNub.ngUnsubscribe({
                channel: $scope.selectedChannel
              });
          };

          $scope.data = {
            username: 'Anonymous ' + Math.floor(Math.random() * 1000)
          };

          $scope.userLogged = false;
          $scope.join = function() {           
            var _ref, _ref1, _ref2;
            $scope.data || ($scope.data = {});
            $scope.data.username = (_ref = $scope.data) != null ? _ref.username : void 0;
            if($scope.selectedUserName != null){
              $scope.data.username = $scope.selectedUserName;
            }
            $scope.data.city = (_ref1 = $scope.data) != null ? _ref1.city : void 0;
            $scope.data["super"] = (_ref2 = $scope.data) != null ? _ref2["super"] : void 0;
            $scope.data.uuid = Math.floor(Math.random() * 1000000) + '__' + $scope.data.username;
            $scope.secretKey = $scope.data["super"] ? 'sec-c-YjM1OTE3M2UtZjVmMS00MjgxLWI3ZjgtNGIyOGZlNjM1ODVm' : null;
            $scope.authKey = $scope.data["super"] ? 'oprAopra' : null;
            PubNub.init({
              subscribe_key: 'sub-c-9b2c802c-8a9d-11e4-9b60-02ee2ddab7fe',
              publish_key: 'pub-c-022f81e4-61d8-4c6c-9aac-293d849cc7bf',
              secret_key: $scope.secretKey,
              auth_key: $scope.authKey,
              uuid: $scope.data.uuid,
              ssl: true
            });
           
            $scope.userLogged = true;

            $scope.newChannel = 'AiyhChannel';
            $scope.createChannel();
            $scope.subscribe($scope.newChannel);


          };

           $scope.controlChannel = '__controlchannel';
           $scope.channels = [];

            // Publish a chat message
           $scope.publish = function() {
              
              if (!$scope.selectedChannel) {
                return;
              }

              if($scope.selectedUserName != null){
                  $scope.data.username = $scope.selectedUserName;
              }

              PubNub.ngPublish({
                channel: $scope.selectedChannel,
                message: {
                  text: $scope.newMessage,
                  user: $scope.data.username
                }
              });

              $scope.subscribe('AiyhChannel');           
                          
              $(".chatbox").animate({ scrollTop: $(".chatbox").height()*3 }, "fast");
              
              return $scope.newMessage = '';

              
              
           };

           // Create a new channel
          $scope.createChannel = function() {
            var channel;
            console.log('createChannel', $scope);
            if (!($scope.data["super"] && $scope.newChannel)) {
              return;
            }
            channel = $scope.newChannel;
            $scope.newChannel = '';
            PubNub.ngGrant({
              channel: channel,
              read: true,
              write: true,
              callback: function() {
                return console.log("" + channel + " all set", arguments);
              }
            });
            PubNub.ngGrant({
              channel: "" + channel + "-pnpres",
              read: true,
              write: false,
              callback: function() {
                return console.log("" + channel + " presence all set", arguments);
              }
            });
            PubNub.ngPublish({
              channel: $scope.controlChannel,
              message: channel
            });
            return setTimeout(function() {
              $scope.subscribe(channel);
              return $scope.showCreate = false;
            }, 100);
          };

           //Select a channel to display chat history & presence
            $scope.subscribe = function(channel) {
              var _ref;
              console.log('subscribe', channel);
              if (channel === $scope.selectedChannel) {
                return;
              }
              if ($scope.selectedChannel) {
                PubNub.ngUnsubscribe({
                  channel: $scope.selectedChannel
                });
              }
              $scope.selectedChannel = channel;
              $scope.messages = [/*'Welcome to ' + channel*/];
              PubNub.ngSubscribe({
                channel: $scope.selectedChannel,
                auth_key: $scope.authKey,
                state: {
                  "city": ((_ref = $scope.data) != null ? _ref.city : void 0) || 'unknown'
                },
                error: function() {
                  return console.log(arguments);
                }
              });
              $scope.$on(PubNub.ngPrsEv($scope.selectedChannel), function(ngEvent, payload) {

                $scope.users = PubNub.ngListPresence($scope.selectedChannel);
                console.log($scope.users);
                return $scope.$apply(function() {
                  var newData, userData;
                  //console.log('Selected Channel: '+$scope.selectedChannel);
                  userData = PubNub.ngPresenceData($scope.selectedChannel);
                  newData = {};
                  $scope.users = PubNub.map(PubNub.ngListPresence($scope.selectedChannel), function(x) {
                    var newX;
                    newX = x;
                    if (x.replace) {
                      newX = x.replace(/\w+__/, "");
                    }
                    if (x.uuid) {
                      newX = x.uuid.replace(/\w+__/, "");
                    }
                    newData[newX] = userData[x] || {};
                    return newX;
                  });
                  return $scope.userData = newData;
                });
              });

              PubNub.ngHereNow({
                channel: $scope.selectedChannel
              });

              $scope.$on(PubNub.ngMsgEv($scope.selectedChannel), function(ngEvent, payload) {
                var msg;
                msg = payload.message.user ? "" + payload.message.user + " :|   " + payload.message.text : "[unknown] " + payload.message;
                return $scope.$apply(function() {
                  return $scope.messages.unshift(msg);
                });
              });

              return PubNub.ngHistory({
                channel: $scope.selectedChannel,
                auth_key: $scope.authKey,
                count: 50
              });
          };

            
        $scope.mySplit = function(string, nb) {
          $scope.array = string.split('|');
          return $scope.result = $scope.array[nb];
        }  

        $scope.listSize = function(list) {
          return list.length;         
        }    
         
         $scope.formatDate = function(d) { //dd-MM-yyyy:HH:mm:SS

            var dd = d.getDate();

            if ( dd < 10 ) dd = '0' + dd

            var mm = d.getMonth()+1;

            if ( mm < 10 ) mm = '0' + mm

            var yyyy = d.getFullYear();

            return dd+'-'+mm+'-'+yyyy+':'+d.getHours()+':'+d.getMinutes()+':'+d.getSeconds();

          } 

          if(isIE()){
            console.log('Browser is IE');
          }else{
            console.log('Browser is Other than IE');
          }     

          console.log('Current Language: '+xlatService.getCurrentLanguage()); 

          console.log('CURRENT URL: '+location.href); 
          if(location.href.indexOf('tudoemsuasmaos') > -1){
              xlatService.setCurrentLanguage('pt');
          }else if(location.href.indexOf('allinyourhandsweb') > -1){
              xlatService.setCurrentLanguage('en');
          }  

          $.getJSON("http://jsonip.com?callback=?", function (data) {
              $scope.ipAdress = data.ip;
              console.log("Current ip: " + $scope.ipAdress);

          });   

          $scope.originalCountryCode = 'us';
          $.getJSON("http://freegeoip.net/json/", function(result){           
            $scope.originalCountryCode = result.country_code.toLowerCase();
            console.log('Country: ' + result.country_name + '\n' + 'Code: ' + $scope.originalCountryCode);
            
          });
                             
          var startValues = aiyhStatus.get(
            {isportalweb:'false'}, 

            function() {
              
              $scope.statusAPIs = startValues.statusAPIs; 
              //$cookieStore.put('streamingSessionIDKey',$scope.statusAPIs.streamingSessionID); 
              console.log('streamingSessionIDKey in scope : '+$scope.statusAPIs.streamingSessionID);

              $scope.audioActive = false;
              $scope.videosActive = false;
              $scope.booksActive = false;
              $scope.weatherActive = false;
              $scope.placesActive = false;             
              $scope.chatActive = false;
              $scope.directionsActive = false;
              // verificando status das apis
              if($scope.statusAPIs.audio == 1){
                  $scope.audioActive = true;
              }
              if($scope.statusAPIs.video == 1){
                  $scope.videosActive = true;
              }
              if($scope.statusAPIs.book == 1){
                   $scope.booksActive = true;
              }
              if($scope.statusAPIs.weather == 1){
                   $scope.weatherActive = true;
              }
              if($scope.statusAPIs.placeNear == 1 || $scope.statusAPIs.placeSug == 1){
                   $scope.placesActive = true;    
              }
              if($scope.statusAPIs.chat == 1){
                    $scope.chatActive = true;
              }
              
              
               // SALVA RELATORIO DE ACESSO A HOME
               if(isMobile.iOS()){
                  $scope.sendReport('-','Home Access','IPHONE ACCESS','Home Access','User','reportPortalWeb','0');
               }else if(isMobile.Android()){
                  $scope.sendReport('-','Home Access','ANDROID ACCESS','Home Access','User','reportPortalWeb','0');
               }else{
                  $scope.sendReport('-','Home Access','BROWSER ACCESS','Home Access','User','reportPortalWeb','0');
               }

               $scope.topMarginPlaces = '-60';
               // no caso de qualquer versao mobile, pequeno ajuste em lugares
               if(isMobile.any()){
                  $scope.topMarginPlaces = '-10';             
               }


             $scope.recommendedSongs  = [];
             $scope.recommendedBooks  = [];
             $scope.recommendedVideos = [];
             $scope.recommendedPlaces = [];    
                 
             var recomContent = recommendedContentsService.get(   

                  function() {    
                        

                        $scope.recommendedContents = recomContent.suggestedContents.suggestedContent;
                        for(i=0;i< $scope.recommendedContents.length;i++){

                          //corrigindo caracteres especiais
                          $scope.recommendedContents[i].name = $scope.changeSpecialCharacters($scope.recommendedContents[i].name);
                         
                           if($scope.recommendedContents[i].image.indexOf('*') > -1){

                                $scope.recommendedContents[i].image = $scope.replaceAll($scope.recommendedContents[i].image,"\\*","/");
                           }

                           if($scope.recommendedContents[i].image == 'images/2.png'){
                              $scope.recommendedContents[i].image = '../images/2.png';
                           }

                           if($scope.recommendedContents[i].type == 'musics'){
                                $scope.recommendedSongs.push($scope.recommendedContents[i]);
                           }else if($scope.recommendedContents[i].type == 'videos'){
                                $scope.recommendedVideos.push($scope.recommendedContents[i]);
                           }else if($scope.recommendedContents[i].type == 'books'){
                                // fazendo ajuste em imagem
                                $scope.recommendedContents[i].image = $scope.suggestBooksCharactersReverseFix($scope.recommendedContents[i].image);                            
                                $scope.recommendedBooks.push($scope.recommendedContents[i]);
                           }else if($scope.recommendedContents[i].type == 'places'){
                                $scope.recommendedPlaces.push($scope.recommendedContents[i]);
                           }

                        }


                                    
             });

              var songCat = songCategoriesService.get(function() {                  
                        $scope.songcategories = songCat.categories.category; 
                        if($scope.songcategories == null){
                           console.log('Song Cat Retry 1 [null]...');
                           var retry1 = songCategoriesService.get(function(){                             
                               $scope.songcategories = retry1.categories.category; 
                               if($scope.songcategories == null){
                                   console.log('Song Cat Retry 2 [null]...');
                                  var retry2 = songCategoriesService.get(function() {  
                                       $scope.songcategories = retry2.categories.category;
                                  });
                               }
                           });
                        } else if($scope.songcategories.length == 0){
                             console.log('Song Cat Retry 1 [lenght is 0]...');
                              var retry = songCategoriesService.get(function(){                              
                               $scope.songcategories = retry.categories.category; 
                               if($scope.songcategories == null){
                                  console.log('Song Cat Retry 2 [null]...');
                                  var retry2 = songCategoriesService.get(function() {  
                                       $scope.songcategories = retry2.categories.category;
                                  });
                               }
                           });
                        }           
              });
             

              $scope.videoCatLang = xlatService.getCurrentLanguage();              
              
              var videoCat = videoCategoriesService.get(
                {countryCode:$scope.videoCatLang},  
                function() {                  
                  $scope.videocategories = videoCat.videoCategories.category; 
                  if($scope.videocategories == null){
                      console.log('Video Cat Retry 1 [is null]...');
                      var retry1 = videoCategoriesService.get(
                        {countryCode:$scope.videoCatLang},  
                        function() { 
                            $scope.videocategories = retry1.videoCategories.category; 
                        });    
                  }         
              });


              var countVar = countryService.get(                                   
                  function() {    
                     
                        $scope.countries = countVar.countries.country;
                        if($scope.countries == null){
                            console.log('Countries Cat Retry 1 [is null]...');
                             var retry1 = countryService.get(  
                              function() {
                                   $scope.countries = retry1.countries.country;
                                   if($scope.countries == null){
                                           console.log('Countries Cat Retry 2 [is null]...');
                                           var retry2 = countryService.get(  
                                            function() {
                                                 $scope.countries = retry2.countries.country;
                                                 if($scope.countries == null){
                                                         console.log('Countries Cat Retry 3 [is null]...');
                                                         var retry3 = countryService.get(  
                                                          function() {
                                                               $scope.countries = retry3.countries.country;
                                                               
                                                          }); 
                                                 }
                                            });   
                                     }
                                });   
                        }
               });

             
          });
          
   

           /*Inicializando variaveis*/
           $scope.songSearchStarted = false;
           $scope.songPageNumber = 1;
           $scope.currentSongsListIndex = 0;
          
           $scope.artistsMode = false;

           $scope.songsByArtistMode = false;

           $scope.currentArtistListIndex = 0;  

           $scope.currentSongByArtistListIndex = 0;            

           $scope.bookSearchStarted = false;

           $scope.videoSearchStarted = false;

           $scope.placesSearchStarted = false;

           $scope.generalSearchDone = false;

           

          $scope.getSongsByQuery = function(){
                
                var Results = songService.get(
                  {q:$scope.songName,pagenumber:$scope.songPageNumber,lyric:'0',grooveshark:true}, 
                  function() {                  
                      $scope.songs = Results.songs.song;  

                      if(angular.isArray($scope.songs)){                                                

                          $scope.songTempArray = [];
                          for(i = $scope.currentSongsListIndex;i < $scope.songs.length;i++){

                              $scope.songs[i].name = $scope.changeSpecialCharacters($scope.songs[i].name);
                              $scope.songTempArray.push($scope.songs[i]);

                             /* var random = Math.floor((Math.random() * 10) + 1);
                              if(random > 7){
                               
                                 $scope.sendReport($scope.songs[i].id,$scope.songs[i].imagePreview,$scope.songs[i].name,'musics',$scope.songs[i].artistName,'#','0');
                              
                              }*/
                          }

                         
                          //atualizando lista atual
                          $scope.songs = $scope.songTempArray;
                          // atualizando indice
                          $scope.currentSongsListIndex = $scope.songs.length;

                      }else{

                         if($scope.songs != null){
                            $scope.songTempArray = [];
                            $scope.songTempArray.push($scope.songs);
                            $scope.songs = $scope.songTempArray;
                         }else{
							 $scope.loadResultsNotFoundPopUp();
						 }
                          
                      }

                      $scope.songSearchStarted = true;
                      
                      $scope.foundSongs = false;
                      if($scope.songs != null){                                                 
                             $scope.foundSongs = true;                         
                      }

                      $scope.artistsMode = false; 
                      
                      var type = 'musics';
                      if(isMobile.iOS()){
                             type += '_IOS';
                      }else if(isMobile.Android()){
                             type += '_Android';             
                      }
                      $scope.sendReport('-','-',$scope.songName,type,'-','reportPortalWeb','0');

					  $('#searchSongs').blur();
                      //if(!$scope.generalSearchDone)
                        //window.location.hash='#songsResultArea'; 

                  });

                 
                 

          };

          $scope.getSongsPreviousPage = function(){
             console.log('getSongsPreviousPage()');
            if($scope.songPageNumber > 0)
              $scope.songPageNumber = $scope.songPageNumber - 1;   
            
                $scope.currentSongsListIndex -=  $scope.songs.length;
                $scope.getSongsByQuery();
                //window.location.hash='#songsResultArea'; 
          };

          $scope.getSongsNextPage = function(){
              console.log('getSongsNextPage()');
              $scope.songPageNumber = $scope.songPageNumber + 1;   
              $scope.getSongsByQuery();
              //window.location.hash='#songsResultArea'; 
          };

                    


          $scope.getSongsCategories = function(){
                
                var Results = songCategoriesService.get(function() {                  
                  $scope.songcategories = Results.categories.category;              
                });

          };


           $scope.artistsCatPageNumber = 1;
           $scope.getArtistsByCategory = function(){
                       
                console.log('selected cat: '+$scope.songCatName);        
                var Results = artistsByCategoryService.get(
                  {categoryname:$scope.songCatName,pagenumber:$scope.artistsCatPageNumber,isportalweb: false},
                  function() {                  
                    $scope.artists = Results.artists.artist;  

                     $scope.artistTempArray = [];
                     for(i = $scope.currentArtistListIndex;i < $scope.artists.length;i++){
                          $scope.artistTempArray.push($scope.artists[i]);
                     }

                     
                      //atualizando lista atual
                      $scope.artists = $scope.artistTempArray;
                      // atualizando indice
                      $scope.currentArtistListIndex = $scope.artists.length;  

                      $scope.artistsMode = true;   

                      $scope.foundArtists = false;
                      if($scope.artists != null){                                                 
                             $scope.foundArtists = true;                         
                      } 

                      //window.location.hash='#artistsResultArea';     
                });

          };

          $scope.getArtistsPreviousPage = function(){
            if($scope.artistsCatPageNumber > 0)
              $scope.artistsCatPageNumber = $scope.artistsCatPageNumber - 1;   
            
                $scope.currentArtistListIndex -=  $scope.artists.length;
                $scope.getArtistsByCategory();
                //window.location.hash='#artistsResultArea'; 
          };

          $scope.getArtistsNextPage = function(){
              $scope.artistsCatPageNumber = $scope.artistsCatPageNumber + 1;   
              $scope.getArtistsByCategory();
              //window.location.hash='#artistsResultArea'; 
          };

         

           $scope.songsArtPageNumber = 1;
           $scope.getSongsByArtist = function(artistName){
                
                $scope.artistName = artistName;
                var Results = songsByArtistService.get(
                  {query:$scope.artistName,pagenumber:$scope.songsArtPageNumber,lyric: '0',grooveshark:true},
                  function() {                  
                    $scope.songs = Results.songs.song;   

                    $scope.songByArtTempArray = [];
                    for(i = $scope.currentSongByArtistListIndex;i < $scope.songs.length;i++){

                          $scope.songs[i].name = $scope.changeSpecialCharacters($scope.songs[i].name);
                          $scope.songByArtTempArray.push($scope.songs[i]);

                         /* var random = Math.floor((Math.random() * 10) + 1);
                          if(random > 7){
                               $scope.sendReport($scope.songs[i].id,$scope.songs[i].imagePreview,$scope.songs[i].name,'musics',$scope.songs[i].artistName,'#','0');
                          }*/
                    }

                    //atualizando lista atual
                    $scope.songs = $scope.songByArtTempArray;
                    // atualizando indice
                    $scope.currentSongByArtistListIndex = $scope.songs.length;  

                    $scope.artistsMode = false;   
                    $scope.songSearchStarted = true;  
                    $scope.songsByArtistMode = true;  


                    $scope.songByArtistSearchStarted = true;

                    $scope.foundSongsByArtist = false;
                     if($scope.songs != null){                                                 
                            $scope.foundSongsByArtist = true;                         
                    }  

                    var type = 'music_categories';
                    if(isMobile.iOS()){
                          type += '_IOS';
                    }else if(isMobile.Android()){
                          type += '_Android';             
                    }
                    $scope.sendReport('-','-',$scope.artistName,type,'-','reportPortalWeb','0');

                    //$scope.scrollToPosition('songsResultArea'); 
                    //window.location.hash='#songsResultArea';  
                });

          };

          $scope.getSongsByArtistPreviousPage = function(){
            console.log('getSongsByArtistPreviousPage()');
            if($scope.songsArtPageNumber > 0)
                $scope.songsArtPageNumber = $scope.songsArtPageNumber - 1;   
            
                $scope.currentSongByArtistListIndex -=  $scope.songs.length;
                $scope.getSongsByArtist($scope.artistName);
                $scope.scrollToPosition('songsResultArea');
          };

          $scope.getSongsByArtistNextPage = function(){
              console.log('getSongsByArtistNextPage()');
              $scope.songsArtPageNumber = $scope.songsArtPageNumber + 1;   
              $scope.getSongsByArtist($scope.artistName);
              $scope.scrollToPosition('songsResultArea');
          };


           $scope.getStreamingURL = function(){
                
                console.log('Cookie: ' + $cookieStore.get('streamingSessionIDKey') + '   store: '+$scope.statusAPIs.streamingSessionID);   
                var Results = streamingUrlService.get(
                  {songid:$scope.songID, ipadress: $scope.ipAdress, sessionid:$scope.statusAPIs.streamingSessionID},
                  function() {    

                    $scope.streamingUrl = Results.streaming_url;                    
                     

                });

          };

     // dois metodos relacionados a streaming faltaram aqui


      $scope.getVideosByQuery = function(){
                               
                var Results = videoService.get(
                  {q:$scope.videoQuery},
                  function() {    
                     
                    $scope.videos = Results.videos.video;
                    $scope.videoNextPageTok =  Results.videos.nextPageToken;
                    $scope.videoSearchStarted = true;
	                
					if(angular.isArray($scope.videos)){
						for(i =0;i<$scope.videos.length;i++){
							 $scope.videos[i].title = $scope.changeSpecialCharacters($scope.videos[i].title);  
						}
					}else{
						if($scope.videos == null)
						    $scope.loadResultsNotFoundPopUp();
						
					}
					
                    $scope.foundVideos = false;
                     if($scope.videos != null){                                                 
                            $scope.foundVideos = true;                         
                    }  

                    var type = 'videos';
                    if(isMobile.iOS()){
                          type += '_IOS';
                    }else if(isMobile.Android()){
                          type += '_Android';             
                    }
                    $scope.sendReport('-','-',$scope.videoQuery,type,'-','reportPortalWeb','0');

                     //if(!$scope.generalSearchDone)
                      //window.location.hash='#videosResultArea'; 
					  $('#searchVideos').blur();
					  
                      
                });

                

          };


       $scope.videoCatID = 'no';
       $scope.getVideosByQueryPagination = function(categoryMode){
                 
                var  vQuery =  '-';         
                if($scope.videoQuery != null){
                       vQuery = $scope.videoQuery;
                }

                if($scope.videoNextPageTok == null){
                  $scope.videoNextPageTok = ' ';
                }

                if(categoryMode == 'ok'){ // em modo de categorias sempre setamos este parametro para vazio
                   $scope.videoNextPageTok = ' ';
                }

                var Results = videoPaginationService.get(
                  {q:vQuery, categoryid: $scope.videoCatID, nextpagetoken:$scope.videoNextPageTok},
                  function() {    
                     
                        $scope.videos = Results.videos.video;
                        $scope.videoNextPageTok =  Results.videos.nextPageToken;
                        $scope.videoSearchStarted = true;

                        for(i =0;i<$scope.videos.length;i++){
                          $scope.videos[i].title = $scope.changeSpecialCharacters($scope.videos[i].title);  
                        }
                        
                        $scope.foundVideos = false;
                        if($scope.videos != null){                                                 
                            $scope.foundVideos = true;                         
                        }  

                        var type = 'video_categories';
                        if(isMobile.iOS()){
                          type += '_IOS';
                        }else if(isMobile.Android()){
                          type += '_Android';             
                        }
                        $scope.sendReport('-','-','video cat id - '+$scope.videoCatID,type,'-','reportPortalWeb','0');


                        //window.location.hash='#videosResultArea'; 

              
            });

              

          };

          $scope.getVideosPreviousPage = function(){

            $scope.videoNextPageTok = $scope.videoPrevPagTok;              
            $scope.getVideosByQueryPagination('nok');
            //window.location.hash='#videosResultArea'; 
          };

          $scope.getVideosNextPage = function(){
             
              $scope.videoPrevPagTok = $scope.videoNextPageTok;
              $scope.getVideosByQueryPagination('nok');
              //window.location.hash='#videosResultArea'; 
          };

                   
                   
          $scope.playVideo = function(videoID, videoImage,videoTitle){

            console.log('VideoID: '+videoID);
            $scope.selectedVideoID = videoID;
            var src =  'http://www.youtube.com/embed/'+$scope.selectedVideoID+'?autoplay=1&origin=http://www.youtube.com'; //http://www.youtube.com/v/'+$scope.selectedVideoID+'?version=3&enablejsapi=1&fs=1';
            //console.log('youtube url: '+src);
            $('#myModal').modal('show');
            $('#myModal iframe').attr('src', src);

            $scope.sendReport($scope.selectedVideoID,videoImage,videoTitle,'videos','-','#','1');
           
          };

        
           $scope.getVideosCategories = function(){
                
                var Results = videoCategoriesService.get(
                  {countrycode:xlatService.getCurrentLanguage()},  
                  function() {   
                    $scope.videocategories = Results.videoCategories.category;              
                });

          };


        $scope.bookPageNumber = 1;

        $scope.getBooks = function(){
                               
                var Results = bookService.get(
                  {keyword:$scope.booksKeyword, pagenumber: $scope.bookPageNumber,countryCode:$scope.originalCountryCode},                 
                  function() {    
                     
                        $scope.books = Results.books;

                        if(angular.isArray($scope.books)){
                                              
                              for(i = 0; i< $scope.books.length; i++){
                                 if($scope.books[i].volumeInfo.imageLink.extraLarge != ''){
                                    $scope.books[i].volumeInfo.imageLink.extraLarge = $scope.books[i].volumeInfo.imageLink.extraLarge;
                                 }else if($scope.books[i].volumeInfo.imageLink.large != ''){
                                    $scope.books[i].volumeInfo.imageLink.extraLarge = $scope.books[i].volumeInfo.imageLink.large;
                                 }else if($scope.books[i].volumeInfo.imageLink.medium != ''){
                                    $scope.books[i].volumeInfo.imageLink.extraLarge = $scope.books[i].volumeInfo.imageLink.medium;
                                 }else if($scope.books[i].volumeInfo.imageLink.thumbnail != ''){
                                    $scope.books[i].volumeInfo.imageLink.extraLarge = $scope.books[i].volumeInfo.imageLink.thumbnail;
                                 }else if($scope.books[i].volumeInfo.imageLink.smallThumbnail != ''){
                                    $scope.books[i].volumeInfo.imageLink.extraLarge = $scope.books[i].volumeInfo.imageLink.smallThumbnail;
                                 }else{
                                    $scope.books[i].volumeInfo.imageLink.extraLarge = '../images/booknail.jpg';
                                 }

                                 $scope.books[i].volumeInfo.title = $scope.changeSpecialCharacters($scope.books[i].volumeInfo.title);
                                 $scope.books[i].volumeInfo.description = $scope.changeSpecialCharacters($scope.books[i].volumeInfo.description);
                              }
                         }else{
                              //console.log('TESTE: ' + $scope.books.pdfDownloadLink);
                              if($scope.books != null){
                                $scope.bookTempArray = [];
                                $scope.bookTempArray.push($scope.books);
                                $scope.books = $scope.bookTempArray;
                              }else{
								                 $scope.loadResultsNotFoundPopUp();
							                }
                         }

                         $scope.foundBooks = false;
                         if($scope.books != null){                                                 
                            $scope.foundBooks = true;                         
                         } 

                         var type = 'books';
                         if(isMobile.iOS()){
                          type += '_IOS';
                         }else if(isMobile.Android()){
                          type += '_Android';             
                         }
                         $scope.sendReport('-','-',$scope.booksKeyword,type,'-','reportPortalWeb','0');


                        $scope.bookSearchStarted = true;
						            $('#searchBooks').blur();  
                       
                         //if(!$scope.generalSearchDone)
                         //window.location.hash='#booksResultArea';
					                                        
                  });
                
                
          };
		  
		
        $scope.getBooksPreviousPage = function(){
            if($scope.bookPageNumber > 0)
              $scope.bookPageNumber -= 1;   
            
            $scope.getBooks();
            //window.location.hash='#booksResultArea';
          };

          $scope.getBooksNextPage = function(){
              $scope.bookPageNumber += 1;   
              $scope.getBooks();
              //window.location.hash='#booksResultArea';
          };


        $scope.getLatitudeLongitude = function(){
                
                var Results = latitudeLongitudeService.get(
                  {address:$scope.addressValue},                  
                  function() {    
                     
                        $scope.latLong = Results.lat_long;
                                    
                 });

          };

          $scope.getCountries = function(){
                               
                var Results = countryService.get(                                   
                  function() {    
                     
                        $scope.countries = Results.countries.country;
                                    
            });

          };

          $scope.getStates = function(){
                     
                					 
                var Results = statesService.get( 
                    {countryid:$scope.countryIDandName.split("|")[0]},                                   
                  function() {    
                     
					    if(isMobile.any())
							document.getElementById("countrySpan").innerHTML=$scope.countryIDandName.split("|")[1];
						
                        $scope.states = Results.states.state;
                        if($scope.states == null){
                          console.log('States retry 1 [is Null]...');
                          var stateRetry1 = statesService.get( 
                                  {countryid:$scope.countryIDandName.split("|")[0]},                                   
                                  function() {    
                                     
                                        $scope.states = stateRetry1.states.state;
                                        if($scope.states == null){
                                           console.log('States retry 2 [is Null]...');
                                            var stateRetry2 = statesService.get( 
                                                  {countryid:$scope.countryIDandName.split("|")[0]},                                   
                                                  function() {    
                                                     
                                                        $scope.states = stateRetry2.states.state;
                                                               
                                                });
                                        }           
                                });
                        } 
							
						
                });

               
          };

          $scope.updateFinalAddress = function(){

			  if(isMobile.any())
				document.getElementById("stateSpan").innerHTML=$scope.stateName;	
			  
              $scope.addressValue = $scope.countryIDandName.split("|")[1] +', '+$scope.stateName;
              console.log('Endereco ainda sem nome de rua: '+$scope.addressValue);
          };
		  
		  if(isMobile.any()){
			  $scope.placesTypes = [];
			  if(location.href.indexOf('tudoemsuasmaos') > -1){
				  $scope.placesTypes.push({name: "Comida"});
				  $scope.placesTypes.push({name: "Bebidas"});
				  $scope.placesTypes.push({name: "Cafes"});
				  $scope.placesTypes.push({name: "Lojas"});
				  $scope.placesTypes.push({name: "Lugares Abertos"});
				  $scope.placesTypes.push({name: "Locais Historicos"});
				  $scope.placesTypes.push({name: "Selecao Especial"});
			  }else if(location.href.indexOf('allinyourhands') > -1){
				  $scope.placesTypes.push({name: "Food"});
				  $scope.placesTypes.push({name: "Drinks"});
				  $scope.placesTypes.push({name: "Coffee"});
				  $scope.placesTypes.push({name: "Stores"});
				  $scope.placesTypes.push({name: "Outdoors"});
				  $scope.placesTypes.push({name: "Historic Places"});
				  $scope.placesTypes.push({name: "Special"});
			  }
			 
		  }

		  $scope.updateKindleOfPlace = function(placeTypeName){
		
			document.getElementById("placeKindSpan").innerHTML=placeTypeName;
			if(location.href.indexOf('tudoemsuasmaos') > -1){
				if(placeTypeName == 'Comida'){
					
					 $scope.placeType = 'Food';
				} else if(placeTypeName == 'Bebidas'){
					 
					 $scope.placeType = 'Drinks';
				} else if(placeTypeName == 'Cafes'){
					
					  $scope.placeType = 'Coffee';
				} else if(placeTypeName == 'Lojas'){
					 
					  $scope.placeType = 'Stores';
				} else if(placeTypeName == 'Lugares Abertos'){
					
					  $scope.placeType = 'Outdoors';
				} else if(placeTypeName == 'Locais Historicos'){
					 
					  $scope.placeType = 'Historic Places';
				} else if(placeTypeName == 'Selecao Especial'){
					
					  $scope.placeType = 'Special';
				} 
			}else{
				$scope.placeType = placeTypeName;
				document.getElementById("placeKindSpan").innerHTML=$scope.placeType; 
				 
			}
		   
		  };
         
          $scope.getPlacesNear = function(){             
                
                if($scope.streetName == null){
                  $scope.addressValue =  $scope.addressValue+', '+$scope.stateName;
                }else{
                  $scope.addressValue =  $scope.addressValue+', '+$scope.streetName;
                }
                console.log('Endereco Final utilizado na pesquisa: '+$scope.addressValue );

                 $scope.countryCode = xlatService.getCurrentLanguage();
               
                var LatLongResult = latitudeLongitudeService.get(
                  {address:$scope.addressValue},                  
                  function() {    
                     
                        $scope.latLong = LatLongResult.lat_long;
                           
                        // necessario inserir o valor em $scope.addressValue
                       var Results = placesNearService.get(
                          {latitude_longitude:$scope.latLong,countrycode:$scope.countryCode,type:$scope.placeType,offset:$scope.placeOffset},                                    
                         function() {    
                     
                            $scope.placesNear = Results.places__near__of__you.places.place;
                                    
                        });            
                 });
                
                 $scope.addressValue = $scope.countryIDandName.split("|")[1] +', '+$scope.stateName;

          };

          $scope.getSuggestedPlaces = function(){
                      
                if($scope.streetName == null){
                  $scope.addressValue =  $scope.addressValue+', '+$scope.stateName;
                }else{
                  $scope.addressValue =  $scope.addressValue+', '+$scope.streetName;
                }
                console.log('Endereco Final utilizado na pesquisa: '+$scope.addressValue );

                $scope.countryCode = xlatService.getCurrentLanguage();

                 var LatLongResult = latitudeLongitudeService.get(
                  {address:$scope.addressValue},                  
                  function() {    
                     
                        $scope.latLong = LatLongResult.lat_long;

                        var Results = placesSuggestedService.get(  
                           {latitude_longitude:$scope.latLong,countrycode:$scope.countryCode,type:$scope.placeType,offset:$scope.placeOffset},                                    
                           function() {    
                     
                            $scope.suggestedPlaces = Results.suggestions.suggestion.place;
                                    
                       });

                   });

                  $scope.addressValue = $scope.countryIDandName.split("|")[1] +', '+$scope.stateName;
          };


          $scope.placeOffset = 1;

          $scope.getPlaces_ = function(){


                if($scope.streetName == null){
                  $scope.addressValue =  $scope.addressValue+', '+$scope.stateName;
                }else{
                  $scope.addressValue =  $scope.addressValue+', '+$scope.streetName;
                }
                console.log('Endereco Final utilizado na pesquisa: '+$scope.addressValue );

                 $scope.countryCode = xlatService.getCurrentLanguage();

                 if($scope.placeType == null){
                    $scope.placeType = 'no';
                 }else if($scope.placeType == ''){
                    $scope.placeType = 'no';
                 }

                 var LatLongResult = latitudeLongitudeService.get(
                  {address:$scope.addressValue},                  
                  function() {    
                     
                        $scope.latLong = LatLongResult.lat_long;
                           
                        // necessario inserir o valor em $scope.addressValue
                       var Results = placesNearService.get(
                          {latitude_longitude:$scope.latLong,countrycode:$scope.countryCode,type:$scope.placeType,offset:$scope.placeOffset},                                    
                         function() {    
                     
                            $scope.placesNear = Results.places__near__of__you.places.place;
                      
                            var Results1 = placesSuggestedService.get(  
                              {latitude_longitude:$scope.latLong,countrycode:$scope.countryCode,type:$scope.placeType,offset:$scope.placeOffset},                                    
                                function() {    
                     
                              $scope.suggestedPlaces = Results1.suggestions.suggestion.place;

                              if($scope.placesNear == null){
                                  $scope.placesNear = [];
                              }

                              if($scope.suggestedPlaces == null){
                                  $scope.suggestedPlaces = [];
                              }


                              $scope.places = $.merge($scope.placesNear,$scope.suggestedPlaces); 

                              console.log('Near: '+$scope.placesNear+'\n Sug: '+$scope.suggestedPlaces+"\n All: "+$scope.places)
                                
                              $scope.placesSearchStarted = true; 

                              $scope.foundPlaces = false;
                              if($scope.places != null){                                                 
                                  $scope.foundPlaces = true;                         
                              }else{
								  $scope.loadResultsNotFoundPopUp();
							  } 


                              var type = 'places';
                              if(isMobile.iOS()){
                                type += '_IOS';
                              }else if(isMobile.Android()){
                                type += '_Android';             
                              }
                              $scope.sendReport('-','-',$scope.addressValue,type,'-','reportPortalWeb','0');

                              //window.location.hash='#placesResultArea';  
							  $.mobile.silentScroll(80);

                                
                           });

                                

                        });            
                 });

                 $scope.addressValue = $scope.countryIDandName.split("|")[1] +', '+$scope.stateName;
                 $scope.partialAddress = $scope.countryIDandName.split("|")[1] +', '+$scope.stateName;  
            

          };

          $scope.getPlacesPreviousPage = function(){
            if($scope.placeOffset > 0)
              $scope.placeOffset = $scope.placeOffset - 1;   
            
            $scope.getPlaces_();
            //window.location.hash='#placesResultArea';  
          };

          $scope.getPlacesNextPage = function(){
              $scope.placeOffset = $scope.placeOffset + 1;   
              $scope.getPlaces_();
              //window.location.hash='#placesResultArea';  
          };

       
           $scope.getDirections = function(){
                             
                var Results = directionsService.get(     
                  {origin_address:$scope.placeOriginAdress,destiny_address:$scope.placDestAdress,mode:'driven',countrycode:$scope.countryCode},                               
                  function() {    
                     
                        $scope.direction = Results.direction;
                                    
            });

          };


          $scope.howGetThereAction = function(placeName,placeAddress,distanceText){
              
            console.log('destiny name: '+placeName);
            $scope.destinyName = placeName;

            $scope.destinyAddress = $scope.partialAddress + ', '+placeAddress;

            $scope.distanceText = distanceText;

            console.log('Partial Address: '+$scope.partialAddress);
            console.log('Original Address: '+$scope.addressValue);
            console.log('Destiny Address: '+$scope.destinyAddress);

            if($scope.addressValue.split(",").length < 3 ){
              if($scope.streetName != null){
                  console.log('completing original address...');
                  $scope.addressValue = $scope.addressValue + ', '+$scope.streetName;
                  console.log('completed address: '+$scope.addressValue);
              }
            }

            $scope.addressValue = $scope.replaceAll($scope.addressValue,'/','');
            $scope.destinyAddress = $scope.replaceAll($scope.destinyAddress,'/','');

            $scope.countryCode = xlatService.getCurrentLanguage();
           
            var Results = directionsService.get(     
                  {origin_address:$scope.addressValue,destiny_address:$scope.destinyAddress,mode:'driven',countrycode:$scope.countryCode},                               
                  function() {    
                     
                          $scope.direction = Results.direction;

                          var steps = $scope.direction.steps.step;
                          var instructions = '';
                          for(i = 0; i< steps.length; i++){
                              instructions += steps[i].html__instructions;
                          }
                          console.log('Instructions take place: '+instructions);

                          var src = $scope.direction.staticMapURL;
                          console.log('static map url: '+src);
                          $('#myModal2').modal('show');
                          $('#myModal2 h4').text($scope.destinyName);
                          $('#myModal2 img').attr('src', $scope.replaceAll(src,'\\*','/')); 


                          if($scope.countryCode.indexOf("pt") > -1){
                            $('#distanceTextArea').text('Distancia: '+$scope.distanceText);        
                          }else{
                            $('#distanceTextArea').text('Distance: '+$scope.distanceText);        
                          }
                                            
                          $('#roteToDestinyArea').html(instructions);   
                          $('#streetViewHtmlCodeArea').html($scope.direction.destinyStreetViewImageURL.replace('<html><body>','').replace('</body></html>',''));  
                             
                          $scope.sendReport('-',src,$scope.destinyName,'places',$scope.destinyAddress,'#','1');

                          var type = 'places_howGetThere';
                          if(isMobile.iOS()){
                              type += '_IOS';
                          }else if(isMobile.Android()){
                              type += '_Android';             
                          }
                          $scope.sendReport('-','-',$scope.destinyName,type,'-','reportPortalWeb','0');
                          
                                    
            });


           
           
          };


		$scope.scroolToPosition = function(elementID){
        

            //Get object
            var SupportDiv = document.getElementById(elementID);
 
            //Scroll to location of SupportDiv on load
            window.scroll(0,$scope.findPos(SupportDiv));
         };


        $scope.weatherReady = false;

        $scope.getWeather = function(){
                  
                $scope.countryCode = xlatService.getCurrentLanguage();
                
                if($scope.streetName == null){
                  $scope.addressValue =  $scope.addressValue+', '+$scope.stateName;
                }else{
                  $scope.addressValue =  $scope.addressValue+', '+$scope.streetName;
                }
                console.log('Endereco Final utilizado na pesquisa: '+$scope.addressValue );

                   var LatLongResult = latitudeLongitudeService.get(
                      {address:$scope.addressValue},                  
                      function() {    
                     
                        $scope.latLong = LatLongResult.lat_long;
             
                         var Results = weatherService.get(   
                          {latitude_longitude:$scope.latLong,countrycode:$scope.countryCode},                                    
                          function() {    
                       
                            $scope.weather = Results.weather;

                            if($scope.weather != null){
                                $scope.weather.temperature = parseInt($scope.weather.temperature);
                                $scope.weather.temp__max = parseInt($scope.weather.temp__max);
                                $scope.weather.temp__min = parseInt($scope.weather.temp__min);

                                $scope.weatherReady = true;

                                if( ($scope.weather.weatherDescription.toLowerCase().indexOf("chuva") > -1) || ($scope.weather.weatherDescription.toLowerCase().indexOf("rain") > -1) ){
                                    $scope.weather_forecast_image = "../images/rainy.png"
                                }else{
                                     $scope.weather_forecast_image = "../images/sun.png"
                                }
                            
                                var type = 'weather';
                                if(isMobile.iOS()){
                                    type += '_IOS';
                                }else if(isMobile.Android()){
                                    type += '_Android';             
                                }
                                $scope.sendReport('-','-',$scope.addressValue,type,'-','reportPortalWeb','0');
								
								$scope.scroolToPosition('weatherField');
								
								
                            }else{
                                $scope.weatherError = true;
                            }
                                      
                      });
                    
                        
                   });   

                   $scope.addressValue = $scope.countryIDandName.split("|")[1] +', '+$scope.stateName;
                   $scope.partialAddress = $scope.countryIDandName.split("|")[1] +', '+$scope.stateName;                 
               
                

          };


          $scope.getWeatherForecast = function(){
             // console.log($scope.weather.forecastText);
              var forecastTextList = $scope.weather.forecastText.split("Min:");
              var d = new Date();
              var weekday = new Array(7);
              if(xlatService.getCurrentLanguage().toLowerCase().indexOf('pt') > -1){
                weekday[0]=  "Domingo";
                weekday[1] = "Segunda";
                weekday[2] = "Terca";
                weekday[3] = "Quarta";
                weekday[4] = "Quinta";
                weekday[5] = "Sexta";
                weekday[6] = "Sabado";
              }else{
                weekday[0]=  "Sunday";
                weekday[1] = "Monday";
                weekday[2] = "Tuesday";
                weekday[3] = "Wednesday";
                weekday[4] = "Thursday";
                weekday[5] = "Friday";
                weekday[6] = "Saturday";
              }
              var title = 'Weather Forecast for the next 6 days';
              if(xlatService.getCurrentLanguage().toLowerCase().indexOf('pt') > -1){
                title = 'Previsao para os proximos 6 dias';
              }
              var dayNumber = d.getDay();
              var indexValue = 0;
              if(dayNumber == 6){
                  indexValue = 0;
              }else indexValue = dayNumber + 1;
             
              var forecastText = '';
              for(i = 1; i< forecastTextList.length; i++){                             
                
                forecastText += '<b>'+weekday[indexValue]+'</b>  Min. ' + $scope.changeSpecialCharacters( $scope.replaceAll(forecastTextList[i],'pesado','pesada') )+'<br/>';
                indexValue++;
              }

               $('#myModal4').modal('show');
               $('#myModal4 h4').text(title);
               $('#weatherForecastTextArea').html(forecastText); 


            

          };
          

          $scope.getRecommendedContents = function(){                             
                var Results = recommendedContentsService.get(   

                  function() {    
                     
                        $scope.suggestedContents = Results.suggestedContents.suggestedContent;
                                    
            });

          };


          if(!isMobile.any()){
            $scope.showPauseButton = true;
          }
              

          $scope.buzzInstance = null;
          $scope.getBuzzSingletonInstance = function(stremingUrl){
             if($scope.buzzInstance == null){
                      $scope.buzzInstance = new buzz.sound( stremingUrl, {
                              formats: [ "ogg", "mp3", "aac" ]
                      });       
             }

             return $scope.buzzInstance;     
          };
                      
          $scope.playAction = function (songIDandURL, songName,songArtist, imagePreview) {
            
          
            $scope.selectSongID = songIDandURL.split("+")[0];			
			$scope.selectSongURL = $scope.audioURLCharactersReverseFix(songIDandURL.split("+")[1]);			
            $scope.selectSongName = songName;
            $scope.selectSongArtist = songArtist;
			
            if( (imagePreview == 'images/play_icon1.png') || (imagePreview == '')){
                imagePreview = '../images/2.png';
            }
            $scope.selectSongImage = imagePreview;
						
			$scope.selectedSong = $scope.getBuzzSingletonInstance($scope.selectSongURL); 
			$scope.playSong();
						   
            /*var Results = streamingUrlService.get(
                  {songid:$scope.selectSongID, ipadress: $scope.ipAdress, streamsessionid:$scope.statusAPIs.streamingSessionID},
                  function() {    

                    $scope.streamingUrl = Results.streaming_url;   
                    if($scope.streamingUrl != null){                 
                       console.log('streamingUrl is not null');
                      if($scope.streamingUrl.indexOf('null') < 0){

                           $scope.selectedSong = $scope.getBuzzSingletonInstance($scope.streamingUrl);     
                         
                           $scope.playSong();
                        
                      }else{
                          console.log('Retry Streaming get!(streaming get failed)');
                          var retry = streamingUrlService.get(
                           {songid:$scope.selectSongID, ipadress: $scope.ipAdress, streamsessionid:$scope.statusAPIs.streamingSessionID},
                          function() {    
                             $scope.streamingUrl = retry.streaming_url; 
                              if($scope.streamingUrl.indexOf('null') < 0){

                                   $scope.selectedSong = $scope.getBuzzSingletonInstance($scope.streamingUrl); 
                                 
                                  
                                   $scope.playSong();
                              }else{
                                        console.log('Retry Streaming get!(streaming get failed)');
                                        var retry5 = streamingUrlService.get(
                                         {songid:$scope.selectSongID, ipadress: $scope.ipAdress, streamsessionid:$scope.statusAPIs.streamingSessionID},
                                        function() {    
                                           $scope.streamingUrl = retry5.streaming_url; 
                                            if($scope.streamingUrl.indexOf('null') < 0){
                                                 $scope.selectedSong = $scope.getBuzzSingletonInstance($scope.streamingUrl); 
                                                                                                
                                                 $scope.playSong();
                                            }else{
                                                console.log('Retry Streaming get!(streaming get failed)');
                                                var retry6 = streamingUrlService.get(
                                                 {songid:$scope.selectSongID, ipadress: $scope.ipAdress, streamsessionid:$scope.statusAPIs.streamingSessionID},
                                                function() {    
                                                   $scope.streamingUrl = retry6.streaming_url; 
                                                    if($scope.streamingUrl.indexOf('null') < 0){
                                                         $scope.selectedSong = $scope.getBuzzSingletonInstance($scope.streamingUrl); 
                                                                                                               
                                                         $scope.playSong();
                                                    }
                                                   
                                                 });
                                             }
                                           
                                         });
                                }
                             
                           });
                      }
                      
                  }else if($scope.streamingUrl == null){
                         console.log('streamingUrl is  null');
                         console.log('Retry 1 Streaming get(streamingURL is null)!');
                         var retry = streamingUrlService.get(
                          {songid:$scope.selectSongID, ipadress: $scope.ipAdress, streamsessionid:$scope.statusAPIs.streamingSessionID},
                          function() {    
                             $scope.streamingUrl = retry.streaming_url; 

                             if($scope.streamingUrl == null){
                                 console.log('streamingUrl is  null');
                                 console.log('Retry 2 Streaming get(streamingURL is null)!');
                                 var retry2 = streamingUrlService.get(
                                  {songid:$scope.selectSongID, ipadress: $scope.ipAdress, streamsessionid:$scope.statusAPIs.streamingSessionID},
                                  function() {    
                                     $scope.streamingUrl = retry2.streaming_url; 
                                     if($scope.streamingUrl.indexOf('null') < 0){
                                         $scope.selectedSong = $scope.getBuzzSingletonInstance($scope.streamingUrl); 
                                                                                  
                                          $scope.playSong();
                                     }else{
                                        console.log('Retry Streaming get!(streaming get failed)');
                                        var retry3 = streamingUrlService.get(
                                         {songid:$scope.selectSongID, ipadress: $scope.ipAdress, streamsessionid:$scope.statusAPIs.streamingSessionID},
                                        function() {    
                                           $scope.streamingUrl = retry3.streaming_url; 
                                            if($scope.streamingUrl.indexOf('null') < 0){
                                                 $scope.selectedSong = $scope.getBuzzSingletonInstance($scope.streamingUrl); 
                                                                                               
                                                 $scope.playSong();
                                            }
                                           
                                         });
                                      }
                                     
                                   });
                              }else{
                                 if($scope.streamingUrl.indexOf('null') < 0){

                                     $scope.selectedSong = $scope.getBuzzSingletonInstance($scope.streamingUrl); 
                                     
                                    
                                     $scope.playSong();
                                 }else{
                                        console.log('Retry Streaming get!(streaming get failed)');
                                        var retry4 = streamingUrlService.get(
                                         {songid:$scope.selectSongID, ipadress: $scope.ipAdress, streamsessionid:$scope.statusAPIs.streamingSessionID},
                                        function() {    
                                           $scope.streamingUrl = retry4.streaming_url; 
                                            if($scope.streamingUrl.indexOf('null') < 0){
                                                 $scope.selectedSong = $scope.getBuzzSingletonInstance($scope.streamingUrl); 
                                                                                                
                                                 $scope.playSong();
                                            }
                                           
                                         });
                                  }
                             }
                             
                           });
                   }
                  
             });*/
                           
                   
            
          };

           $scope.songIsStopped = false;
           $scope.stopAction = function () {

            
           $scope.selectedSong.stop(); 
                   
                        
           $scope.showPauseButton = false;
         
           $scope.songIsStopped = true;

           $scope.buzzInstance = null;
                       

          };

           $scope.pausePlayAction = function () {
            
           
            if($scope.songIsStopped == false){

           
              if($scope.showPauseButton == false){
               
                $scope.showPauseButton = true;
              
              }else{
               
                $scope.showPauseButton = false;
               
              }
            
              $scope.selectedSong.togglePlay();
               
             

            }else{
                
               $scope.showPauseButton = true;
                                           
               $scope.playSong();

            }
                 
           
            
          };

          
          $scope.songPlayerPercentValue = 0;  
          $scope.songWasStarted = false;             
          $scope.playSong = function(){

                 $scope.songWasStarted = true;

                 if(!isMobile.any()){
                  $scope.showPauseButton = true;
                 }
                 $scope.songIsStopped = false;
                   
                
                       
                  $scope.selectedSong.play()
                           .bind( "timeupdate", function() {

                               var time = this.getTime();
                               var duration = this.getDuration();
                               $scope.songPlayerPercentValue  =  Math.floor(buzz.toPercent( time, duration, 2 ));

                               console.log('Percentage: '+$scope.songPlayerPercentValue);

                               var timer = buzz.toTimer( time );
                               //document.getElementById( "timer" ).innerHTML = timer;                            
                                $('#myModal3 h3').text(timer);  
                                $('.progress-bar').css('width', $scope.songPlayerPercentValue+'%').attr('aria-valuenow', $scope.songPlayerPercentValue);
                                $('#percentageArea').text($scope.songPlayerPercentValue+'%');
                                
                            }).bind("loadeddata",function() {
                                 console.log('LOAD DATA  MEDIA PLAYER STATE!!');

                                 console.log('Streaming URL: '+$scope.streamingUrl);


                                 $('#myModal3').modal('show');
                                 $('#myModal3 h4').text($scope.selectSongName);
                                 if($scope.selectSongImage.indexOf('*') > -1){
                                       $scope.selectSongImage = $scope.replaceAll($scope.selectSongImage,"\\*","/");
                                 }
                                 $('#myModal3 img').attr('src',$scope.selectSongImage);
								 $('#myModal3 iframe').attr('src','https://embed.spotify.com/?uri=spotify:track:'+$scope.selectSongID);

                                 $scope.sendReport($scope.selectSongID+'+'+$scope.audioURLCharactersFix($scope.selectSongURL),$scope.selectSongImage,$scope.selectSongName,'musics',$scope.selectSongArtist,'#','1');

                                 if(isMobile.any()){
                                                                 
                                     $scope.showPauseButton = false;
                                     $scope.songIsStopped = false;
                                     return;
                                 }
                            }).bind("loadstart",function() {
                                console.log('LOAD START  MEDIA PLAYER STATE!!');
                                                                                              
                              
                            }).bind("playing",function() {
                                console.log('PLAYING  MEDIA PLAYER STATE!!');
                                 $scope.songIsStopped = false;
                               
                            });
                           

                

                  
          }

                  

          $scope.feedBackWasSent = false;
          $scope.sendMail = function(){

            $scope.emailMessage = $scope.replaceAll($scope.emailMessage,"/","");
            $scope.emailMessage = $scope.replaceAll($scope.emailMessage,"/n","");
			
			$scope.emailEmailClient = $scope.replaceAll($scope.emailEmailClient,"/","");
			$scope.emailEmailClient = $scope.replaceAll($scope.emailEmailClient,"/n","");
			
			$scope.emailNameClient = $scope.replaceAll($scope.emailNameClient,"/","");
			$scope.emailNameClient = $scope.replaceAll($scope.emailNameClient,"/n","");

			$scope.emailFinalMessage = $scope.emailNameClient + '¬'+$scope.emailEmailClient+'¬'+$scope.emailMessage;
			
            emailSenderService.get(
              {subject:'All in Your Hands - Mail',message:$scope.emailFinalMessage},  
              function(){
                  $scope.emailMessage = '';
				  $scope.emailEmailClient = '';
				  $scope.emailNameClient = '';
                  $scope.feedBackWasSent = true;
              });
          };


          $scope.sendReport = function(id,image,name,type,artistName,extraInformation,accessedContent){ 

             var messageToSend = id+'|'+image+'|'+name+'|'+type+'|'+artistName+'|'+extraInformation+'|'+$scope.formatDate(new Date())+'|'+accessedContent;
             messageToSend = $scope.replaceAll(messageToSend,'/','*');
             console.log('Sending Report[DISABLED]: '+messageToSend);

              /*reportService.get(
                  {messageText: messageToSend},                
                  function(){

                  });*/
                
          };

          $scope.selectedRadio = 'songs';
         
          /*General Search*/
          $scope.generalSearch = function(){

             if($scope.selectedRadio == 'songs'){
                  // setando nome da musica
                  $scope.songName = $scope.generalQuerySearch;                 
                  // reiniciando indice
                  $scope.songPageNumber = 1;
                  $scope.getSongsByQuery();
                  
             }else if($scope.selectedRadio == 'books'){
                  $scope.booksKeyword = $scope.generalQuerySearch; 
                  $scope.bookPageNumber = 1;
                  $scope.getBooks();
                  
             }else if($scope.selectedRadio == 'videos'){
                  $scope.videoQuery = $scope.generalQuerySearch; 
                  $scope.getVideosByQuery();
                  
             }

             
          };

          /*Visualizacao de Conteudos*/

           $scope.aspasScape = function(originalText){
             
             return originalText.replace("\"", "&quot;").replace("'", "");
           };
           
            $scope.suggestBooksCharactersFix = function(originalText){
                     
             return originalText.replace("&", "#").replace("http://", "").replace("https://", "").replace("?", "$");
           };
           
           $scope.suggestBooksCharactersReverseFix = function(originalText){

            originalText = originalText.replace("#", "&").replace("$", "?");
            if(originalText.indexOf("http") <= -1){
              originalText = "http://" + originalText;
            }
               
             return originalText;
           };

           $scope.suggestBooksParseToVector = function(originalText){
     
             return originalText.split("#");
           };
		   
		   
		    $scope.audioURLCharactersFix = function(originalText){
                     
             return originalText.replace("&", "#").replace("http://", "").replace("https://", "").replace("?", "$");
           };
           
           $scope.audioURLCharactersReverseFix = function(originalText){

            originalText = originalText.replace("#", "&").replace("$", "?");
            if(originalText.indexOf("http") <= -1){
              originalText = "http://" + originalText;
            }
			
			if(originalText.indexOf('*') > -1){
                originalText = $scope.replaceAll(originalText,"\\*","/");
            }
               
             return originalText;
           };
   

          $scope.openSuggestedBook = function(id,name,type,artist,extraInformation,image){
                          
              artist =  $scope.aspasScape(artist);
              var baseUrl =  $scope.suggestBooksParseToVector(extraInformation)[0];
              var h1Value =  $scope.suggestBooksParseToVector(extraInformation)[1];
              
              $scope.sendReport(id,image,name,type,artist,extraInformation,'1');

                  
              if(image.indexOf("openlibrary") != -1){
                window.open( $scope.replaceAll(extraInformation,'*','/') );
              }else{        
                bookHtml = "<html><header></header><body><iframe frameborder=\"0\" scrolling=\"yes\" style=\"border:0px\" src=\""+$scope.replaceAll(baseUrl,'\\*','/')+
                "&hl="+h1Value+"&lpg=PP1&pg=PP1&output=embed"+"\" width=97% height=97%></iframe></body></html>";
                    
                var newWindow = window.open("_blank");
                newWindow.document.write(bookHtml);
              }
          };


          $scope.openBook = function(id,image,name,artistName,bookHtml,extraInfo) {
            
              image = $scope.suggestBooksCharactersFix(image);
              name = $scope.aspasScape(name);
              artistName = $scope.aspasScape(artistName);
              bookHtml = $scope.aspasScape(bookHtml);
              if(image.indexOf("openlibrary") != -1){
                // obter dateInfo aqui!
                $scope.sendReport('',image,name,'books',artistName,bookHtml,'1');  
              }else{    
                                
                $scope.sendReport(id,image,name,'books',artistName,extraInfo,'1'); 
              }
                

              if(image.indexOf("openlibrary") != -1){
                window.open(bookHtml); // bookHtml neste caso do openlibrary ÃƒÆ’Ã‚Â© um link direto
                
              }else{  
                var newWindow = window.open("_blank");
                newWindow.document.write(bookHtml);
              }
			  			  
            
          };

          /*Eventos em modais quando sao fechados*/
         $('#myModal').on('hidden.bs.modal', function () {
              $('#myModal iframe').removeAttr('src');
          });

         
		  if(isMobile.any()){
			  $('#myModal3').on('hidden.bs.modal', function () {
					$scope.stopAction();                         
				});
		  }else{
			  $('#myModal3').on('hidden.bs.modal', function () {
                $scope.stopAction();                         
			  });
		  
		  }
		  	  


         /*Funcoes auxiliares*/

         $scope.findPos = function findPos(obj) {
            var curtop = 0;
            if (obj.offsetParent) {
              do {
                curtop += obj.offsetTop;
              } while (obj = obj.offsetParent);
            return [curtop];
            }
         };

         
         
         $scope.replaceAll = function(o,t,r,c){
          if(c==1){
            cs="g"
          }else{
            cs="gi"
          }
          var mp=new RegExp(t,cs);
          ns=o.replace(mp,r);
          return ns
        };

		$scope.mobilePageTitle = 'Home';
		
		$scope.bookBarEnabled=false;
		$scope.videosBarEnabled=false;
		$scope.songsBarEnabled=false;
		$scope.placesBarEnabled=false;	
		$scope.weatherBarEnabled=false;	
		$scope.chatBarEnabled=false;	
		$scope.contactBarEnabled=false;	
		$scope.homeBarEnabled=true;
		$scope.startMenuBar = function(mediaType){
			if(mediaType == 'Books'){
				$scope.bookBarEnabled = true;
				$scope.videosBarEnabled = false;
				$scope.songsBarEnabled = false;
				$scope.placesBarEnabled=false;	
				$scope.weatherBarEnabled=false;	
				$scope.chatBarEnabled=false;	
				$scope.contactBarEnabled=false;
				$scope.homeBarEnabled=false;
				if(location.href.indexOf('tudoemsuasmaos') > -1){
					$scope.mobilePageTitle = 'Livros';
				}else if(location.href.indexOf('allinyourhandsweb') > -1){
					$scope.mobilePageTitle = 'Books';
				}else{
					if(xlatService.getCurrentLanguage() == 'pt'){
						$scope.mobilePageTitle = 'Livros';
					}else{
						$scope.mobilePageTitle = 'Books';
					}
				}
				
			} else if(mediaType == 'Videos'){
				$scope.videosBarEnabled = true;
				$scope.bookBarEnabled = false;
				$scope.songsBarEnabled = false;
				$scope.placesBarEnabled=false;	
				$scope.weatherBarEnabled=false;	
				$scope.chatBarEnabled=false;	
				$scope.contactBarEnabled=false;
				$scope.homeBarEnabled=false;
				if(location.href.indexOf('tudoemsuasmaos') > -1){
					$scope.mobilePageTitle = 'Vídeos';
				}else if(location.href.indexOf('allinyourhandsweb') > -1){
					$scope.mobilePageTitle = 'Videos';
				} else{
					if(xlatService.getCurrentLanguage() == 'pt'){
						$scope.mobilePageTitle = 'Vídeos';
					}else{
						$scope.mobilePageTitle = 'Videos';
					}
				}
			}else if(mediaType == 'Songs'){
				$scope.songsBarEnabled = true;
				$scope.bookBarEnabled = false;
				$scope.videosBarEnabled = false;
				$scope.placesBarEnabled=false;	
				$scope.weatherBarEnabled=false;	
				$scope.chatBarEnabled=false;	
				$scope.contactBarEnabled=false;
				$scope.homeBarEnabled=false;
				if(location.href.indexOf('tudoemsuasmaos') > -1){
					$scope.mobilePageTitle = 'Músicas';
				}else if(location.href.indexOf('allinyourhandsweb') > -1){
					$scope.mobilePageTitle = 'Songs';
				}else{
					if(xlatService.getCurrentLanguage() == 'pt'){
						$scope.mobilePageTitle = 'Músicas';
					}else{
						$scope.mobilePageTitle = 'Songs';
					}
				}
			}else if(mediaType == 'Places'){
				$scope.songsBarEnabled = false;
				$scope.bookBarEnabled = false;
				$scope.videosBarEnabled = false;
				$scope.placesBarEnabled=true;	
				$scope.weatherBarEnabled=false;	
				$scope.chatBarEnabled=false;	
				$scope.contactBarEnabled=false;
				$scope.homeBarEnabled=false;
				if(location.href.indexOf('tudoemsuasmaos') > -1){
					$scope.mobilePageTitle = 'Lugares Próximos';
				}else if(location.href.indexOf('allinyourhandsweb') > -1){
					$scope.mobilePageTitle = 'Near Places';
				}else{
					if(xlatService.getCurrentLanguage() == 'pt'){
						$scope.mobilePageTitle = 'Lugares Próximos';
					}else{
						$scope.mobilePageTitle = 'Near Places';
					}
				}
			}else if(mediaType == 'Weather'){
				$scope.songsBarEnabled = false;
				$scope.bookBarEnabled = false;
				$scope.videosBarEnabled = false;
				$scope.placesBarEnabled=false;	
				$scope.weatherBarEnabled=true;	
				$scope.chatBarEnabled=false;	
				$scope.contactBarEnabled=false;
				$scope.homeBarEnabled=false;
				if(location.href.indexOf('tudoemsuasmaos') > -1){
					$scope.mobilePageTitle = 'Previsão do Tempo';
				}else if(location.href.indexOf('allinyourhandsweb') > -1){
					$scope.mobilePageTitle = 'Weather Forecast';
				}else{
					if(xlatService.getCurrentLanguage() == 'pt'){
						$scope.mobilePageTitle = 'Previsão do Tempo';
					}else{
						$scope.mobilePageTitle = 'Weather Forecast';
					}
				}
			} else if(mediaType == 'Chat'){
				$scope.songsBarEnabled = false;
				$scope.bookBarEnabled = false;
				$scope.videosBarEnabled = false;
				$scope.placesBarEnabled=false;	
				$scope.weatherBarEnabled=false;	
				$scope.chatBarEnabled=true;	
				$scope.contactBarEnabled=false;
				$scope.homeBarEnabled=false;
				if(location.href.indexOf('tudoemsuasmaos') > -1){
					$scope.mobilePageTitle = 'Bate-Papo';
				}else if(location.href.indexOf('allinyourhandsweb') > -1){
					$scope.mobilePageTitle = 'Chat';
				}else{
					if(xlatService.getCurrentLanguage() == 'pt'){
						$scope.mobilePageTitle = 'Bate-Papo';
					}else{
						$scope.mobilePageTitle = 'Chat';
					}
				}
			} else if(mediaType == 'Contact'){
				$scope.songsBarEnabled = false;
				$scope.bookBarEnabled = false;
				$scope.videosBarEnabled = false;
				$scope.placesBarEnabled=false;	
				$scope.weatherBarEnabled=false;	
				$scope.chatBarEnabled=false;	
				$scope.contactBarEnabled=true;
				$scope.homeBarEnabled=false;
				if(location.href.indexOf('tudoemsuasmaos') > -1){
					$scope.mobilePageTitle = 'Contato';
				}else if(location.href.indexOf('allinyourhandsweb') > -1){
					$scope.mobilePageTitle = 'Contact';
				}else{
					if(xlatService.getCurrentLanguage() == 'pt'){
						$scope.mobilePageTitle = 'Contato';
					}else{
						$scope.mobilePageTitle = 'Contact';
					}
				}
			} else if(mediaType == 'Home'){
				$scope.songsBarEnabled = false;
				$scope.bookBarEnabled = false;
				$scope.videosBarEnabled = false;
				$scope.placesBarEnabled=false;	
				$scope.weatherBarEnabled=false;	
				$scope.chatBarEnabled=false;	
				$scope.contactBarEnabled=false;
				$scope.homeBarEnabled=true;
				$scope.mobilePageTitle = 'Home';
			} 
						
			
			$('#left_panel').panel('toggle');
		
		};
		
		$scope.loadHomeScreen = function(){
						
			document.location.href="#home";
			
		};
		
		$scope.loadResultsNotFoundPopUp = function(){
			 $('#myModal7').modal('show');
			 if(location.href.indexOf('tudoemsuasmaos') > -1){
					$('#myModal7 h4').text('Infelizmente não conseguimos encontrar o que você procura...por favor, tente novamente com outros termos');
			 }else if(location.href.indexOf('allinyourhandsweb') > -1){
					$('#myModal7 h4').text('Unfortunately we can\'t found what you searched...please, try again with other search term');
			}else{
					if(xlatService.getCurrentLanguage() == 'pt'){
						$('#myModal7 h4').text('Infelizmente não conseguimos encontrar o que você procura...por favor, tente novamente com outros termos');
					}else{
						$('#myModal7 h4').text('Unfortunately we can\'t found what you searched...please, try again with other search term');
					}
			}
			
            					
		};
		
        $scope.changeSpecialCharacters = function(originalText){

          //console.log('ORIGINAL TEXT: '+originalText);
          var hashMap = {
               'Ãƒâ‚¬': 'Ã€',
               'Ãƒ': 'Ã',
               'Ãƒâ€š': 'Ã‚',
               'ÃƒÆ’': 'Ãƒ',
               'Ãƒâ€ž': 'Ã„',
               'Ãƒâ€¦': 'Ã…',
               'Ãƒâ€ ': 'Ã†',
               'Ãƒâ€¡': 'Ã‡',
               'ÃƒË†': 'Ãˆ',
               'Ãƒâ€°': 'Ã‰',
               'ÃƒÅ ': 'ÃŠ',
               'Ãƒâ€¹': 'Ã‹',
               'ÃƒÅ’': 'ÃŒ',
               'Ãƒ': 'Ã',
               'ÃƒÅ½': 'ÃŽ',
               'Ãƒ': 'Ã',
               'Ãƒ': 'Ã',
               'Ãƒâ€˜': 'Ã‘',
               'Ãƒâ€™': 'Ã’',
               'Ãƒâ€œ': 'Ã“',
               'Ãƒâ€': 'Ã”',
               'Ãƒâ€¢': 'Ã•',
               'Ãƒâ€“': 'Ã–',
               'Ãƒâ€”': 'Ã—',
               'ÃƒËœ': 'Ã˜',
               'Ãƒâ„¢': 'Ã™',
               'ÃƒÅ¡': 'Ãš',
               'Ãƒâ€º': 'Ã›',
               'ÃƒÅ“': 'Ãœ',
               'Ãƒ': 'Ã',
               'ÃƒÅ¾': 'Ãž',
               'ÃƒÅ¸': 'ÃŸ',
               'Ãƒ': 'Ã ',
               'ÃƒÂ¡': 'Ã¡',
               'ÃƒÂ¢': 'Ã¢',
               'ÃƒÂ£': 'Ã£',
               'ÃƒÂ¤': 'Ã¤',
               'ÃƒÂ¥': 'Ã¥',
               'ÃƒÂ¦': 'Ã¦',
               'ÃƒÂ§': 'Ã§',
               'ÃƒÂ¨': 'Ã¨',
               'ÃƒÂ©': 'Ã©',
               'ÃƒÂª': 'Ãª',
               'ÃƒÂ«': 'Ã«',
               'ÃƒÂ¬': 'Ã¬',
               'ÃƒÂ­': 'Ã­',
               'ÃƒÂ®': 'Ã®',
               'ÃƒÂ¯': 'Ã¯',
               'ÃƒÂ°': 'Ã°',
               'ÃƒÂ±': 'Ã±',
               'ÃƒÂ²': 'Ã²',
               'ÃƒÂ³': 'Ã³',
               'ÃƒÂ´': 'Ã´',
               'ÃƒÂµ': 'Ãµ',
               'ÃƒÂ¶': 'Ã¶',
               'ÃƒÂ·': 'Ã·',
               'ÃƒÂ¸': 'Ã¸',
               'ÃƒÂ¹': 'Ã¹',
               'ÃƒÂº': 'Ãº',
               'ÃƒÂ»': 'Ã»',
               'ÃƒÂ¼': 'Ã¼',
               'ÃƒÂ½': 'Ã½',
               'ÃƒÂ¾': 'Ã¾',
               'ÃƒÂ¿': 'Ã¿',
               'Ã Ã‚Â³': 'Ã³',
               'Ã ÂƒÃ‚Â±': 'Ã±',
               'Ã£â‚¬': 'Ã€',
               'Ã£': 'Ã',
               'Ã£â€š': 'Ã‚',
               'Ã£Æ’': 'Ãƒ',
               'Ã£â€ž': 'Ã„',
               'Ã£â€¦': 'Ã…',
               'Ã£â€ ': 'Ã†',
               'Ã£â€¡': 'Ã‡',
               'Ã£Ë†': 'Ãˆ',
               'Ã£â€°': 'Ã‰',
               'Ã£Å ': 'ÃŠ',
               'Ã£â€¹': 'Ã‹',
               'Ã£Å’': 'ÃŒ',
               'Ã£': 'Ã',
               'Ã£Å½': 'ÃŽ',
               'Ã£': 'Ã',
               'Ã£': 'Ã',
               'Ã£â€˜': 'Ã‘',
               'Ã£â€™': 'Ã’',
               'Ã£â€œ': 'Ã“',
               'Ã£â€': 'Ã”',
               'Ã£â€¢': 'Ã•',
               'Ã£â€“': 'Ã–',
               'Ã£â€”': 'Ã—',
               'Ã£Ëœ': 'Ã˜',
               'Ã£â„¢': 'Ã™',
               'Ã£Å¡': 'Ãš',
               'Ã£â€º': 'Ã›',
               'Ã£Å“': 'Ãœ',
               'Ã£': 'Ã',
               'Ã£Å¾': 'Ãž',
               'Ã£Å¸': 'ÃŸ',
               'Ã£': 'Ã ',
               'Ã£Â¡': 'Ã¡',
               'Ã£Â¢': 'Ã¢',
               'Ã£Â£': 'Ã£',
               'Ã£Â¤': 'Ã¤',
               'Ã£Â¥': 'Ã¥',
               'Ã£Â¦': 'Ã¦',
               'Ã£Â§': 'Ã§',
               'Ã£Â¨': 'Ã¨',
               'Ã£Â©': 'Ã©',
               'Ã£Âª': 'Ãª',
               'Ã£Â«': 'Ã«',
               'Ã£Â¬': 'Ã¬',
               'Ã£Â­': 'Ã­',
               'Ã£Â®': 'Ã®',
               'Ã£Â¯': 'Ã¯',
               'Ã£Â°': 'Ã°',
               'Ã£Â±': 'Ã±',
               'Ã£Â²': 'Ã²',
               'Ã£Â³': 'Ã³',
               'Ã£Â´': 'Ã´',
               'Ã£Âµ': 'Ãµ',
               'Ã£Â¶': 'Ã¶',
               'Ã£Â·': 'Ã·',
               'Ã£Â¸': 'Ã¸',
               'Ã£Â¹': 'Ã¹',
               'Ã£Âº': 'Ãº',
               'Ã£Â»': 'Ã»',
               'Ã£Â¼': 'Ã¼',
               'Ã£Â½': 'Ã½',
               'Ã£Â¾': 'Ã¾',
               'Ã£Â¿': 'Ã¿',
               'Ã Â³': 'Ã³',
               'Ã Â©': 'Ã©',
			         'Â°':'°',
               'AÃ':'AÇ',
               'Ã§Ã':'ÇÃ',
               'Ã¶':'ö',
               'Ã¡':'Á',
               'Ã¢':'â',
               'Ã©':'é',
               'Ãª':'ê'

            }

             if(originalText != null){
               for(j = 0; j < Object.keys(hashMap).length; j++){
                   if(originalText.indexOf(Object.keys(hashMap)[j]) > -1){                       
                      originalText = $scope.replaceAll(originalText, Object.keys(hashMap)[j] , hashMap[Object.keys(hashMap)[j]], 1 );                      
                   }
               }
             }

             if(originalText != null){
               for(j = 0; j < Object.keys(hashMap).length; j++){
                   if(originalText.indexOf(Object.keys(hashMap)[j]) > -1){                       
                      originalText = $scope.replaceAll(originalText, Object.keys(hashMap)[j] , hashMap[Object.keys(hashMap)[j]], 1 );                      
                   }
               }
             }

            return originalText;
        };
		
	
        
    
  }]);
