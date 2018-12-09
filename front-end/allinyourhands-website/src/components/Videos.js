import React, { Component } from 'react';

class Videos extends Component {
  render() {
    return (
      <section ng-show="videosActive" id="videos" className="layers page_current">


        <div className="page_content">

          <div className="container-fluid no-marg">

            <div className="row row_responsive" style={{ marginBottom: '-20px', marginTop: '-20px;' }}>

              <div className="col-lg-11 section_general">

                <header className="section-header">
                  <h3 className="section-title">Vídeos</h3>
                  <p>Assista o que quiser. Experimente.</p>
                  <div className="border-divider"></div>
                </header>
              </div>
            </div>

            <div className="row row_responsive" style={{ marginBottom: '-20px', marginTop: '-20px' }}>
              <center>
                <form style={{ marginTop: '40px;' }}>
                  <input type="text" placeholder="Procurando por algum vídeo específico ?" className="form-control input-lg"
                    name="search" ng-model="videoQuery" style={{ width: '70%' }} />

                  <div className="row service_intro section_separate" style={{ marginTop: '10px' }}>
                    <button type="submit" ng-click="getVideosByQuery()" className="btn btn_service_intro">Buscar</button>
                  </div>
                </form>
              </center>
            </div>

          </div>


          <div id="portfolios" className="portfolios">

            <center>

              <div id="videosResultArea" tabindex="1" className="grid-wrap">

                <header className="section-header" style={{ marginBottom: '-60px' }}>
                  <p>Quer saber o que as pessoas estão assistindo? Confira abaixo</p>
                </header>

                <li className="label_filter">Filtro</li>
                <li><span className="filter active" data-filter="logo posters photography webdesign">Todos</span></li>
                <li><span className="filter" data-filter="logo">+ Recentes</span></li>
                <li><span className="filter" data-filter="posters">+ Buscados</span></li>

                <div className="grid" id="portfoliolist2" ng-hide="videoSearchStarted">


                  <div className="view view-first portfolio posters webdesign" data-cat="posters webdesign" ng-repeat="recommendedContent in recommendedVideos | limitTo:6"   >

                    <a href="#videos" ng-click="playVideo(recommendedContent.id,recommendedContent.image,recommendedContent.title)" style={{ cursor: 'pointer' }}>
                      <img src="{{recommendedContent.image}}" alt="img01" width="349" style={{ maxHeight: '232px', maxWidth: '349px' }} />

                      <div className="mask" style={{ backgroundColor: 'rgba(0, 0, 0, 0.7)' }}>
                        <h4>Content Name</h4>
                        <p><span className="cat-portfolio">Description</span></p>
                        <div className="portf_detail">

                        </div>

                      </div>
                    </a>
                  </div>

                </div>

                <div className="grid" id="portfoliolist2" ng-show="videoSearchStarted">


                  <div className="view view-first portfolio posters webdesign" data-cat="posters webdesign" ng-repeat="video in videos" >

                    <a href="#videos" ng-click="playVideo(video.id,video.previewImage,video.title)" style={{ cursor: 'pointer' }}>
                      <img src="{{video.previewImage}}" alt="img01" width="349" style={{ maxHeight: '232px', maxWidth: '349px' }} />

                      <div className="mask" style={{ backgroundColor: 'rgba(0, 0, 0, 0.7)' }} >
                        <h4>Video Title</h4>
                        <p><span className="cat-portfolio">Description</span></p>
                        <div className="portf_detail">


                        </div>

                      </div>
                    </a>

                  </div>

                </div>

                <div className="col-md-12 nextprev" ng-show="videoSearchStarted">
                  <br />
                  <button className="btn btn-default btn-lg" ng-click="getVideosPreviousPage()"><span className="glyphicon glyphicon-chevron-left"></span></button>

                  <button className="btn btn-default btn-lg" ng-click="getVideosNextPage()"><span className="glyphicon glyphicon-chevron-right"></span></button>
                </div>

                <div ng-include src="'../adsGoogle.html'" ></div>

              </div>
            </center>

          </div>

        </div>


      </section>

    );
  }
}


export default Videos;