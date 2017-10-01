angular.module("xlat",[]).factory("xlatService",function(){var e="us";e=msieversion()?window.navigator.userLanguage:window.navigator.language,e.length>2&&(e=e.substring(0,2));var t={en:{Title:"WELCOME TO THE CONTENTS PORTAL",SubTitle:"All Entertainment Is In Your Hands",SiteTitle:"All In Your Hands",SiteLogo:"logo.png",SiteLinkRef:"http://allinyourhandsweb.com",SourceLinkRef:"allinyourhandsweb.com",HomeTab:"Home",SongsTab:"Songs",BooksTab:"Books",VideosTab:"Videos",PlacesTab:"Places",WeatherTab:"Weather",ChatTab:"Chat",AllText:"All",SearchGeneralText:"What are you looking for ?",SearchSongsText:"Search a Song",SearchVideosText:"Search a Video",SearchBooksText:"Search a Book by keyword",SearchAddressText:"Street Name",SearchGeneralButtonText:"Search  :)",SearchSongsButtonText:"Search :)",SearchBooksButtonText:"Search",SearchVideosButtonText:"Search :)",SearchPlacesButtonText:"Search",SearchWeatherButtonText:"Check forecast",MostListenedText:"Most Listened",MostReadText:"Most Read",MostWatchedText:"Most Watched",MostViewedText:"Most Viewed",MostVisitedText:"Most Visited",SongThemaText:"How about to find your prefered song ?",VideoThemaText:"Watch what you want. Try it",BookThemaText:"Reading is dreaming with open eyes. Search them.",PlaceThemaText:"Discover places near of you and how to get there ",WeatherThemaText:"See the weather forecast into the world",SongCategoryText:"Select Song Category",SongsBelowText:"Your Songs are below :-)",ArtistsBelowText:"See below the artists of this category :-)",SeeSongsText:"See Songs",BooksBelowText:"Your Books are below :-)",ReadButtonText:"Read",VideoCategoryText:"Select Video Category",VideosBelowText:"Your Videos are below :-)",WatchButtonText:"Watch",CountryNameCombo:"Select Country",StateNameCombo:"Select State",CountryNameComboText:"Country",StateNameComboText:"State",StreetNameInput:"Type the Street Name (optional)",PlaceTypeText:"Select the Type of Place (optional)",PlaceTypeComboText:"Place Type",PlaceTypeFood:"Food",PlaceTypeDrink:"Drinks",PlaceTypeCoffee:"Coffee",PlaceTypeStores:"Stores",PlaceTypeOut:"Outdoors",PlaceTypeHist:"Historic Places",PlaceTypeSpecial:"Special",PlacethemeText:"The Places near of you are below :-)",VisitPlaceTheme:"Visit",DownloadAppText:"Download App",SocialShareText:"Did you like it? Share ;-)",FeedbackText:"Enter Feedback",FeedbackSubText:"We want to hear from you.",FeedbackSendButtonText:"Send",FooterText:"Enjoy All Contents. Keep connected with us!",ReservedText:"reserved",CloseButtonText:"Close",IsOpenText:"Is Open ?",IsOpenTextYes:"Yes",IsOpenTextNo:"No",GeneralSearchNotification:"See Below what I found for you :-)",FeedBackMsg:"Thank you",WeatherForecastNextDays:"See the forecast for the next 6 days",ChatUserNameText:"Your user name",ChatEnterButtonText:"Enter",ChatSendButtonText:"Send",ChatWelcomeText:"Welcome to our Chat, ",ChatSubText:"See below what the users are talking about ",ChatLogoffText:"Logout",ChatUsersText:"Users",NoResultsDefaultText:"Sorry, but we didn't found any result! Try again, please",WeatherErrorMsg:"Unfortunately, an error occurred in weather forecast! Please, try again in some minutes",Partners:"Partners"},pt:{Title:"BEM-VINDO AO PORTAL DE CONTEÚDOS",SubTitle:"Todo Entretenimento em suas mãos",SiteLogo:"logopt.png",SiteLinkRef:"http://tudoemsuasmaos.com.br",SourceLinkRef:"tudoemsuasmaos.com.br",SiteTitle:"Tudo em Suas Mãos",HomeTab:"Home",SongsTab:"Músicas",BooksTab:"Livros",VideosTab:"Vídeos",PlacesTab:"Locais",WeatherTab:"Clima",ChatTab:"Chat",AllText:"Todos",SearchGeneralText:"O que você procura ?",SearchSongsText:"Procure uma Música",SearchVideosText:"Procure um Video",SearchBooksText:"Procure um Livro por palavra-chave",SearchAddressText:"Nome da Rua",SearchGeneralButtonText:"Buscar  :)",SearchSongsButtonText:"Buscar :)",SearchBooksButtonText:"Buscar",SearchVideosButtonText:"Buscar :)",SearchPlacesButtonText:"Buscar",SearchWeatherButtonText:"Ver previsão",MostListenedText:"Mais Ouvidas",MostReadText:"Mais Lidos",MostWatchedText:"Mais Assistidos",MostViewedText:"Mais Visualizados",MostVisitedText:"Mais Visitados",SongThemaText:"Que tal encontrar sua música preferida ?",VideoThemaText:"Assista o que quiser. Experimente.",BookThemaText:"Ler é sonhar com olhos abertos. Busque-os.",PlaceThemaText:"Descubra lugares próximos e como chegar até lá",WeatherThemaText:"Veja a previsão em todo o mundo",SongCategoryText:"Categoria de Música",SongsBelowText:"Suas Músicas estão abaixo :-)",ArtistsBelowText:"Veja abaixo os artistas desta categoria :-)",SeeSongsText:"Confira as Músicas",BooksBelowText:"Seus Livros estão abaixo :-)",ReadButtonText:"Ler",VideoCategoryText:"Categoria de Vídeo",VideosBelowText:"Seus Vídeos estão abaixo :-)",WatchButtonText:"Assistir",CountryNameCombo:"Selecionar País",StateNameCombo:"Selecionar Estado",CountryNameComboText:"País",StateNameComboText:"Estado",StreetNameInput:"Digite o Nome da Rua (opcional)",PlaceTypeText:"Escolha o Tipo de Lugar (opcional)",PlaceTypeComboText:"Tipo de Lugar",PlaceTypeFood:"Comidas",PlaceTypeDrink:"Bebidas",PlaceTypeCoffee:"Cafés",PlaceTypeStores:"Lojas",PlaceTypeOut:"Lugares Abertos",PlaceTypeHist:"Lugares Históricos",PlaceTypeSpecial:"Seleção Especial",PlacethemeText:"Veja abaixo lugares próximos de você :-)",VisitPlaceTheme:"Visite",DownloadAppText:"Baixar App",SocialShareText:"Gostou? Compartilhe ;-)",FeedbackText:"Envie sua opnião",FeedbackSubText:"Queremos ouvir você.",FeedbackSendButtonText:"Enviar",FooterText:"Aproveite Todos Conteúdos. Fique conectado com a gente!",ReservedText:"reservado para ",CloseButtonText:"Fechar",IsOpenText:"Está Aberto ?",IsOpenTextYes:"Sim",IsOpenTextNo:"Não",GeneralSearchNotification:"Veja Abaixo o que encontrei para você :-)",FeedBackMsg:"Obrigado",WeatherForecastNextDays:"Ver a previsão para os próximos 6 dias",ChatUserNameText:"Seu nome de usuario",ChatEnterButtonText:"Entrar",ChatSendButtonText:"Enviar",ChatWelcomeText:"Bem Vindo ao nosso Chat, ",ChatSubText:"Olha só o que estão falando abaixo ",ChatLogoffText:"Sair",ChatUsersText:"Usuarios",NoResultsDefaultText:"Desculpe, mas não encontramos nenhum resultado! Tente novamente, por favor",WeatherErrorMsg:"Infelizmente, ocorreu um erro na previsão do tempo! Por favor, tente novamente em alguns minutos",Partners:"Parceiros"}};return{setCurrentLanguage:function(t){e=t},getCurrentLanguage:function(){return e},xlat:function(o){return t[e][o]}}}).filter("xlat",["xlatService",function(e){return function(t){return e.xlat(t)}}]);