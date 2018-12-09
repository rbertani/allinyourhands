import React, { Component } from 'react';

class Places extends Component {
  render() {
    return (
      <section id="lugares" ng-show="placesActive" className="layers blog-content-grid page_current">

        <div className="page_content">


          <div className="container-fluid no-marg">


            <div className="row row_responsive" style={{ marginBottom: '-20px', marginTop: '-20px' }}>


              <div className="col-lg-11 section_general">

                <header className="section-header">
                  <h3 className="section-title">Lugares</h3>
                  <p>Descubra lugares próximos a você</p>
                  <div className="border-divider"></div>
                </header>
              </div>
            </div>


            <div className="row row_responsive" style={{ marginBottom: '-20px', marginTop: '-20px' }}>
              <div className="col-md-8 col-md-offset-2 search">

                <form>
                  <div className="col-md-6" stystyle={{ marginTop: '-60px' }} >
                    <p>Selecionar País</p>
                    <select className="form-control input-lg" ng-model="countryIDandName" ng-change="getStates()" required >
                      <option value="">País</option>
                      <option ng-repeat="country in countries" value="{{country.geonameId+'|'+country.name}}">Country Name</option>
                    </select>
                    <br />
                    <p>Selecionar Estado</p>
                    <select className="form-control input-lg" ng-model="stateName" ng-change="updateFinalAddress()" required >
                      <option value="">Estado</option>
                      <option ng-repeat="state in states" value="{{state.name}}">Estado Name</option>
                    </select>
                  </div>

                  <div className="col-md-6" stystyle={{ marginTop: '-60px' }}>
                    <p>Digite o Nome da Rua (opcional)</p>
                    <input type="text" className="form-control input-lg" placeholder="Nome da Rua" ng-model="streetName" />
                    <br />
                    <p>Escolha o Tipo de Lugar (opcional)</p>
                    <select className="form-control input-lg" ng-model="placeType">
                      <option value="Food">Comida</option>
                      <option value="Drinks">Bebidas</option>
                      <option value="Coffee">Cafés</option>
                      <option value="Stores">Lojas</option>
                      <option value="Outdoors">Lugares Abertos</option>
                      <option value="Historic Places">Locais Historicos</option>
                      <option value="Special">Seleção Especial</option>
                    </select>

                  </div>
                  <center>
                    <div className="row service_intro section_separate" style={{ marginTop: '20px' }}>
                      <br /><br />
                      <button type="submit" className="btn btn_service_intro" ng-click="getPlaces_()">Buscar</button>
                    </div>
                  </center>
                </form>
              </div>
            </div>

          </div>


          <div id="portfolios" className="portfolios" >

            <center>

              <div id="placesResultArea" tabindex="1" className="grid-wrap">

                <header className="section-header" style={{ marginBottom: ' -60px' }}>
                  <p>Infelizmente não posso contar os lugares que outras pessoas estão buscando, mas logo espero poder mostrar...Enquanto isso, que tal buscar algum lugar próximo de você usando a busca acima ?</p>
                </header>


                <div className="grid" id="portfoliolist4">


                  <div className="view view-first portfolio photography posters" data-cat="photography" ng-repeat="place in places"  >

                    <a href="#places" ng-click="howGetThereAction(place.name,place.address,place.distance)" style={{ cursor: 'pointer' }}>
                      <img src="../images/4.jpg" alt="img01" width="349" style={{ maxHeight: '232px', maxWidth: '349px' }} />

                      <div className="mask" style={{ backgroundColor: 'rgba(0, 0, 0, 0.7)' }} >
                        <h4>Nome Lugar</h4>
                        <p><span className="cat-portfolio"><i className="fa fa-home"></i>Nome Lugar</span></p>
                        <p ng-hide="{{place.phone == ''}}" ><span className="cat-portfolio"><i className="fa fa-phone"></i></span>Telefone</p>
                        <p ng-show="{{place.isOpen}}">Está aberto agora ? Sim </p>
                        <p ng-hide="{{place.isOpen}}">Está aberto agora ? Não </p>


                      </div>
                    </a>

                  </div>

                </div>
                <div className="col-md-12 nextprev" ng-show="placesSearchStarted">
                  <br />
                  <button className="btn btn-default btn-lg" ng-click="getPlacesPreviousPage()"><span className="glyphicon glyphicon-chevron-left"></span></button>

                  <button className="btn btn-default btn-lg" ng-click="getPlacesNextPage()"><span className="glyphicon glyphicon-chevron-right"></span></button>

                </div>

              </div>
            </center>

          </div>

        </div>

      </section>

    );
  }
}


export default Places;