import React, { Component } from 'react';
import { BrowserRouter as Router, Route, Link } from "react-router-dom";
import Home from './components/Home';
import Songs from './components/Songs';
import Videos from './components/Videos';
import Books from './components/Books';
import Places from './components/Places';
import Weather from './components/Weather';
import './App.css';



class App extends Component {
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

                  <li className="profile active">
                    <Link to="/"><i className="fa fa-user icon_menus"></i><span className="nav-label">Home</span></Link>
                  </li>
                  <li ng-show="audioActive" className="service">
                    <Link to="/songs"><i className="fa fa-music icon_menus"></i> <span className="nav-label">Músicas</span></Link>
                  </li>
                  <li ng-show="booksActive" className="resume">
                    <Link to="/books"><i className="fa fa-book icon_menus"></i> <span className="nav-label">Livros</span></Link>
                  </li>
                  <li ng-show="videosActive" className="portfolio">
                    <Link to="/Videos"><i className="fa fa-video-camera icon_menus"></i> <span className="nav-label">Vídeos</span></Link>
                  </li>
                  <li ng-show="placesActive" className="blog">
                    <Link to="/places"><i className="fa fa-glass icon_menu"></i> <span className="nav-label">Lugares</span></Link>
                  </li>
                  <li ng-show="weatherActive" className="contact">
                    <Link to="/weather"><i className="fa fa-sun-o icon_menus"></i> <span className="nav-label">Clima</span></Link>
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

            <div className="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
              <div className="modal-dialog">
                <div className="modal-content">
                  <div className="modal-header">
                    <button type="button" className="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span className="sr-only">Fechar</span></button>
                    <h4 className="modal-title" id="myModalLabel">Assistir Video</h4>
                  </div>
                  <div className="modal-body">
                    <center>
                      <iframe width="500" height="281" frameborder="0" webkitallowfullscreen mozallowfullscreen allowfullscreen="true"></iframe>


                    </center>
                  </div>
                  <div className="modal-footer">
                    <button type="button" className="btn btn-default" data-dismiss="modal">Fechar</button>

                  </div>
                </div>
              </div>
            </div>

            <div className="modal fade" id="myModal2" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
              <div className="modal-dialog">
                <div className="modal-content">
                  <div className="modal-header">
                    <button type="button" className="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span className="sr-only">Fechar</span></button>
                    <center><h4 className="modal-title" id="myModalLabel"></h4></center>
                  </div>
                  <div className="modal-body">
                    <center>
                      <p id="placeTitleArea"></p>
                      <center>
                        <img className="img-responsive img-centered" alt="" />
                      </center>
                      <p id="distanceTextArea"></p>
                      <br />
                      <p id="roteToDestinyArea"></p>
                      <br />
                      <br />
                      <div id="streetViewHtmlCodeArea">
                      </div>
                    </center>
                  </div>
                  <div className="modal-footer">
                    <button type="button" className="btn btn-default" data-dismiss="modal">Fechar</button>

                  </div>
                </div>
              </div>
            </div>

            <div className="modal fade" id="myModal3" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
              <div className="modal-dialog">
                <div className="modal-content">
                  <div className="modal-header">
                    <button type="button" className="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span className="sr-only">Fechar</span></button>
                    <h4 className="modal-title" id="myModalLabel">Ouvir a Música</h4>
                  </div>
                  <div className="modal-body songbtns">
                    <center>
                      <img width="100%" height="100%" ></img>
                      <h3>00:00</h3>
                      <div className="progress">
                        <div className="progress-bar" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" style={{ width: '5%' }}>
                          <span id="percentageArea" className="sr-only">0%</span>
                        </div>
                      </div>
                      <button ng-hide="showPauseButton" className="btn btn-warning" ng-click="pausePlayAction()"><span className="glyphicon glyphicon-play"></span></button>
                      <button ng-show="showPauseButton" className="btn btn-warning" ng-click="pausePlayAction()"><span className="glyphicon glyphicon-pause"></span></button>
                      <button className="btn btn-warning" ng-click="stopAction()"><span className="glyphicon glyphicon-stop"></span></button>
                      <br />
                      <p>Para ouvir completo clique abaixo</p>
                      <iframe width="300" height="80" frameborder="0" allowtransparency="true"></iframe>
                      <br />
                      <a href="https://www.spotify.com" target="_blank"><span style={{ fontSize: '14px', color: 'blue' }}>Powered by Spotify</span></a>
                    </center>
                  </div>
                  <div className="modal-footer">
                    <button type="button" className="btn btn-default" data-dismiss="modal">Fechar</button>

                  </div>
                </div>
              </div>
            </div>

            <div className="modal fade" id="myModal4" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
              <div className="modal-dialog">
                <div className="modal-content">
                  <div className="modal-header">
                    <button type="button" className="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span className="sr-only">Fechar</span></button>
                    <h4 className="modal-title" id="myModalLabel">Weather Forecast</h4>
                  </div>
                  <div className="modal-body songbtns">
                    <center>
                      <center>
                        <img className="img-responsive img-centered" alt="" />
                      </center>
                      <p id="weatherForecastTextArea"></p>

                    </center>
                  </div>
                  <div className="modal-footer">
                    <button type="button" className="btn btn-default" data-dismiss="modal">Fechar</button>

                  </div>
                </div>
              </div>
            </div>

            <div className="modal fade" id="myModal7" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
              <div className="modal-dialog">
                <div className="modal-content">
                  <div className="modal-header">
                    <button type="button" className="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span className="sr-only">Fechar</span></button>
                    <h3 className="modal-title" id="myModalLabel"><i className="fa fa-thumbs-down"></i> <span>Resultado Não Encontrado :-(</span></h3>
                  </div>
                  <div className="modal-body songbtns">
                    <center>

                      <h4 id="ResultsNotFoundText"></h4>

                    </center>
                  </div>
                  <div className="modal-footer">
                    <button type="button" className="btn btn-default" data-dismiss="modal">Fechar</button>
                  </div>
                </div>
              </div>
            </div>



          </div>


        </div>

      </Router>


    );
  }
}

export default App;
