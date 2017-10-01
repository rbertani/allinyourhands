
    
    //  Carregando os banners	
    document.addEventListener("deviceready", function onDeviceReady() {

    	var random = Math.floor((Math.random() * 10) + 1);

    	var plc = '1005458';
    	if(random < 6)
	    	plc = '1005438';  


	    window.AerServSDK.loadInterstitial(plc,
				 function(){ 

				 }, function(message){ 
				 		
				  }, function(){ 

				 }, function(){ 

				 }, function(){ 

				 }, function() { 

				 }, function(name, amount) {  }, function(name, amount) {  }, "","",
				false);	                  
	         
	    window.AerServSDK.loadBanner(plc, 325, 50, AerServSDK.BANNER_BOTTOM,
	              function(){

	                                
	              },
	              function(message){

							              			
	                               
	              },
	              function(){

	                
	              },
	              function(){

	                               
	              },
	              function(){

	                
	                    },
	              function(name, amount){
	                
	              },
	              function(name, amount){
	                
	              },
	              "");

		 
				


	});