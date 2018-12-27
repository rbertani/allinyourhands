import React, { Component } from 'react';

class Songs extends Component {
  render() {
    return (
      <section ng-show="audioActive" id="songs" className="layers page_current">


        <div className="page_content">


          <div className="container-fluid no-marg">


            <div className="row row_responsive" style={{ marginBottom: '-20px', marginTop: '-20px' }}>


              <div className="col-lg-11 section_general">

                <header className="section-header">
                  <h3 className="section-title">Músicas</h3>
                  <p>Que tal encontrar sua música preferida ? </p>
                  <div className="border-divider"></div>
                  <p>Lembrando que neste caso não podemos oferecer o conteúdo completo, pois respeitamos os direitos autorais...Mas oferecemos o link da música completa no Spotify ;-) </p>
                </header>
              </div>
            </div>

            <div className="row row_responsive" style={{ marginBottom: '-20px', marginTop: '-20px' }}>
              <center>
                <form style={{ marginTop: '40px' }}>
                  <input type="text" placeholder="Que Música ou Artista você procura ?" className="form-control input-lg"
                    name="search" ng-model="songName" style={{ width: '70%' }} />

                  <div className="row service_intro section_separate" style={{ marginTop: '10px' }}>
                    <a href="#" ng-click="getSongsByQuery()" className="btn btn_service_intro">Buscar</a>
                  </div>
                </form>
              </center>
            </div>

          </div>


          <div id="portfolios2" className="portfolios" >

            <center>
              <div className="grid-wrap" tabindex="1" id="songsResultArea">

                <header className="section-header" style={{ marginBottom: '-60px' }}>
                  <p>Abaixo você confere as Músicas que as outras pessoas estão curtindo</p>
                </header>

                <div className="grid" id="portfoliolist2" ng-hide="songSearchStarted">


                  <div className="view view-first portfolio photography" data-cat="photography" ng-repeat="recommendedContent in recommendedSongs | limitTo:6"   >

                    <a href="#songs" ng-click="playAction(recommendedContent.id,recommendedContent.name,recommendedContent.artist,recommendedContent.image)" style={{ cursor: 'pointer' }}>
                      <img  src={process.env.PUBLIC_URL + '/images/2.png'} alt="img01" width="349" height="232" style={{ maxHeight: '232px', maxWidth: '349px' }} />
                     
                      <div className="mask" style={{ backgroundColor: 'rgba(0, 0, 0, 0.7)' }}>
                        <h4>Content Name</h4>
                        <p><span className="cat-portfolio">Artist</span></p>

                        <div className="portf_detail">

                        </div>

                      </div>
                    </a>

                  </div>

                </div>

                <div className="grid" id="portfoliolist2" ng-show="songSearchStarted">

                  <div className="view view-first portfolio photography" data-cat="photography" ng-repeat="song in songs"   >

                    <a href="#songs" ng-click="playAction(song.id+'+'+song.url,song.name,song.artistName,song.imagePreview)" style={{ cursor: 'pointer' }} >
                      <img src={process.env.PUBLIC_URL +'/images/2.png'} alt="img01" width="349" height="232" style={{ maxHeight: '232px', maxWidth: '349px' }} />
                     
                      <div className="mask" style={{ backgroundColor: 'rgba(0, 0, 0, 0.7)' }}>
                        <h4>Song Name</h4>
                        <p><span className="cat-portfolio">Artist Name</span></p>

                        <div className="portf_detail">

                        </div>

                      </div>
                    </a>

                  </div>

                </div>

                <div className="col-md-12 nextprev" ng-show="songSearchStarted">
                  <br />

                  <button className="btn btn-default btn-lg" ng-click="getSongsPreviousPage()" ng-hide="songsByArtistMode"><span className="glyphicon glyphicon-chevron-left"></span></button>

                  <button className="btn btn-default btn-lg" ng-click="getSongsNextPage()" ng-hide="songsByArtistMode"><span className="glyphicon glyphicon-chevron-right"></span></button>
                </div>
                
              </div>
            </center>

          </div>

        </div>

      </section>

    );
  }
}


export default Songs;