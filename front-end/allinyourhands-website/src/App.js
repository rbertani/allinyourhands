import React, { Component } from 'react';
import { BrowserRouter as Router, Route, Link } from "react-router-dom";
import axios from 'axios';
import { properties } from './properties.js';
import Home from './components/Home';
import Songs from './components/Songs';
import Videos from './components/Videos';
import Books from './components/Books';
import Places from './components/Places';
import Weather from './components/Weather';
import { loadProgressBar } from 'axios-progress-bar'
import 'axios-progress-bar/dist/nprogress.css'


class App extends Component {

  constructor(){
    super();

    this.state = {
      homeSelected: "",
      songsSelected: "",
      booksSelected: "",
      videosSelected: "",
      placesSelected: "",
      weatherSelected: "",
      statusApis: {}

    };
  }

  componentDidMount() {   

      loadProgressBar();

      axios.get(properties.apiBaseUrl + `/status`)
      .then(response => {  
        
        if(response.data.statusAPIs.book == 1)
            this.setState({ booksSelected : "block"  });
        else 
            this.setState({ booksSelected : "none"  });

        if(response.data.statusAPIs.audio == 1)
            this.setState({ songsSelected : "block"  });
        else 
            this.setState({ songsSelected : "none"  });

        if(response.data.statusAPIs.weather == 1)
            this.setState({ weatherSelected : "block"  });
        else 
            this.setState({ weatherSelected : "none"  });

        if(response.data.statusAPIs.directions == 1)
            this.setState({ placesSelected : "block"  });
        else 
            this.setState({ placesSelected : "none"  });

       if(response.data.statusAPIs.video == 1)
            this.setState({ videosSelected : "block"  });
        else 
            this.setState({ videosSelected : "none"  });

      });
  }

  selectedCurrentMenuItem = (menu) => {
     
    if(menu === 'home'){
     
    }

    else if(menu === 'songs'){
     
    }

    else if(menu === 'books'){
      
    }

    else if(menu === 'videos'){
     
    }
    
    else if(menu === 'places'){
     
    }

    else if(menu === 'weather'){
     
    }

  }

  convertEnabledValue = (statusApiValue) => {

    //console.log("----> convertEnabledValue called!!");
    
    if(statusApiValue == "1"){     
      return "block";
    }
    else return "none";
  }

 
  render() {
    return (

      <Router>

        <div>

          <div id="wrappers">

            <header id="header">

              <div className="menu_closed" id="menu_closed" title="Close Menu" data-toggle="tooltip">
                <i className="fa fa-times"></i>
              </div>

              <div className="logo">
                <a href="http://tudoemsuasmaos.com.br"><img src={process.env.PUBLIC_URL + '/images/logoAiyh.png'} width="122" height="122" alt="Logo" /></a>
              </div>

              <h4 className="tagline">Bem-Vindo ao Portal de Conteúdos :)</h4>


              <nav className="navigation">
                <ul className="list-unstyled">

                  <li className={this.state.homeSelected}>
                    <Link to="/">
                      <div onClick={() => this.selectedCurrentMenuItem('home')}>
                        <i className="fa fa-user icon_menus"></i><span className="nav-label">Home</span>
                      </div>
                    </Link>
                  </li>
                  <li className="" style={{display: this.state.songsSelected}}>
                    <Link to="/songs">
                      <div onClick={() => this.selectedCurrentMenuItem('songs')}>
                        <i className="fa fa-music icon_menus"></i> <span className="nav-label">Músicas</span>
                      </div>
                     </Link>
                  </li>
                  <li className="" style={{display: this.state.booksSelected}}>
                    <Link to="/books">
                      <div onClick={() => this.selectedCurrentMenuItem('books')}>
                        <i className="fa fa-book icon_menus"></i><span className="nav-label">Livros</span>
                      </div>
                    </Link>
                  </li>
                  <li className="" style={{display: this.state.videosSelected}}>
                    <Link to="/videos">
                      <div onClick={() => this.selectedCurrentMenuItem('videos')}>
                        <i className="fa fa-video-camera icon_menus"></i> <span className="nav-label">Vídeos</span>
                      </div>
                    </Link>
                  </li>
                  <li className="" style={{display: this.state.placesSelected}}>
                    <Link to="/places">
                      <div onClick={() => this.selectedCurrentMenuItem('places')}>
                      < i className="fa fa-glass icon_menu"></i> <span className="nav-label">Lugares</span>
                      </div>
                    </Link>
                  </li>
                  <li className="" style={{display: this.state.weatherSelected}}>
                    <Link to="/weather" onClick={() => this.selectedCurrentMenuItem('weather')}>
                      <div>
                        <i className="fa fa-sun-o icon_menus"></i> <span className="nav-label">Clima</span>
                      </div>
                    </Link>
                  </li>

                </ul>
              </nav>

              <div className="social-ul" id="header_social_ul">
                <ul>
                  <li className="social-twitter"><a href="http://twitter.com/intent/tweet?status=Contents Portal: http://tudoemsuasmaos.com.br" target="_blank"><i className="fa fa-twitter"></i></a></li>
                  <li className="social-facebook"><a href="http://www.facebook.com/sharer/sharer.php?u=http://tudoemsuasmaos.com.br&title=Content Portal" target="_blank"><i className="fa fa-facebook"></i></a></li>
                  <li className="social-google"><a href="https://plus.google.com/share?url=http://tudoemsuasmaos.com.br" target="_blank"><i className="fa fa-google-plus"></i></a></li>
                  <li className="social-linkedin"><a href="http://www.linkedin.com/shareArticle?mini=true&url=http://tudoemsuasmaos.com.br&title=Contents Portal&source=tudoemsuasmaos.com.br" target="_blank"><i className="fa fa-linkedin"></i></a></li>

                </ul>
              </div>

              <div className="copyright">
                <p style={{ marginBottom: 0 }}>© 2018. <a href="#">Ricardo M. Bertani</a></p>
              </div>


            </header>


            <div id="main">

              <Route exact path="/" component={Home} />
              <Route path="/songs" component={Songs} />
              <Route path="/books" component={Books} />
              <Route path="/videos" component={Videos} />
              <Route path="/places" component={Places} />
              <Route path="/weather" component={Weather} />
            </div>

            


          </div>


        </div>

      </Router>


    );
  }
}

export default App;
