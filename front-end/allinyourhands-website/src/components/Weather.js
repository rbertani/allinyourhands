import React, { Component } from 'react';

class Weather extends Component {
  render() {
    return (
      <section id="clima" ng-show="weatherActive" className="layers blog-content-grid page_current">

        <div className="page_content">


          <div className="container-fluid no-marg">


            <div className="row row_responsive" style={{ marginBottom: '-20px', marginTop: '-20px' }}>


              <div className="col-lg-11 section_general">

                <header className="section-header">
                  <h3 className="section-title">Previsão do Tempo</h3>
                  <p>Veja a previsão em todo o mundo</p>
                  <div className="border-divider"></div>
                </header>
              </div>
            </div>


            <div className="row row_responsive" style={{ marginBottom: '-20px', marginTop: '-20px' }}>
              <div className="col-md-8 col-md-offset-2 search">

                <div className="col-md-6">
                  <header className="section-header" style={{ marginBottom: '-70px' }} >
                    <p>Verificar a previsão nunca foi tão fácil, escolha o seu local e busque</p>
                  </header>
                  <br />
                  <form>
                    <p>Selecionar País</p>
                    <select className="form-control input-lg" ng-model="countryIDandName" ng-change="getStates()" required>
                      <option value="">País</option>
                      <option ng-repeat="country in countries" value="{{country.geonameId+'|'+country.name}}">Nome Pais</option>
                    </select>

                    <p>Selecionar Estado</p>
                    <select className="form-control input-lg" ng-model="stateName" ng-change="updateFinalAddress()" required>
                      <option value="">Estado</option>
                      <option ng-repeat="state in states" value="{{state.name}}">Nome Estado</option>
                    </select>


                    <p>Digite o Nome da Rua (opcional)</p>
                    <input type="text" className="form-control input-lg" placeholder="Nome da Rua" ng-model="streetName" />
                    <br />
                    <br />
                    <button type="submit" className="btn btn-info btn-block btn-lg" ng-click="getWeather()">Ver previsão</button>
                  </form>
                  <br />
                </div>

                <div className="col-md-6">

                  <center style={{ marginLeft: '20px', marginTop: '15px' }} >
                    <img src="{{weather_forecast_image}}" alt="Weather - All In Your Hands" ng-show="weatherReady" />
                    <h3>   graus C° e F° </h3>
                    <p>Max.  </p>
                    <p>Min. </p>
                    <br />
                    <p>Descricao</p>
                    <br />
                    <a ng-click="getWeatherForecast()" style={{ cursor: 'pointer' }}>Ver a previsão para os próximos 6 dias</a>
                  </center>
                  <center style={{ marginLeft: '20px', marginTop: '15px' }} >
                    <h3>Infelizmente, ocorreu um erro na previsÃ£o do tempo! Por favor, tente novamente em alguns minutos</h3>
                  </center>
                </div>
              </div>
            </div>

          </div>


        </div>

      </section>

    );
  }
}


export default Weather;