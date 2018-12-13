import React, { Component } from 'react';
import axios from 'axios';
import { properties } from '../properties.js';

class Books extends Component {

  constructor(){
    super();

    this.state = {
      keyword : "",
      pagenumber: "1",
      countryCode: "pt",
      books : [],
      isLoading: false
    };

    this.handleQuery = this.handleQuery.bind(this);
    this.requestBooksApi = this.requestBooksApi.bind(this);

  }

  handleQuery = (event) => {

    console.log("VALOR DIGITADO: "+event.target.value);
    this.setState({ keyword: event.target.value});
  }

  requestBooksApi = (event) => {

    event.preventDefault();

    this.setState({ isLoading: true});

    axios.get(properties.apiBaseUrl + `/books?keyword=`+this.state.keyword+'&pagenumber='+this.state.pagenumber+'&countryCode='+this.state.countryCode)
    .then(response => {        
      this.setState({ books : response.data.books, isLoading: false });
    });
  }

  render() {

    const { isLoading, books } = this.state;

    return (

      <section ng-show="booksActive" id="books" className="layers page_current">

        <div className="page_content">

          <div className="container-fluid no-marg">

            <div className="row row_responsive" style={{ marginBottom: '-20px', marginTop: '-20px' }}>

              <div className="col-lg-11 section_general">

                <header className="section-header">
                  <h3 className="section-title">Livros</h3>
                  <p>Ler é sonhar com olhos abertos. Busque-os.</p>
                  <div className="border-divider"></div>
                </header>
              </div>
            </div>

            <div className="row row_responsive" style={{ marginBottom: '-20px', marginTop: '-20px' }}>
              <center>
                <form style={{ marginTop: '40px' }}>
                  <input type="text" placeholder="Procure um Livro por palavra-chave" className="form-control input-lg"
                    name="search" style={{ width: '70%' }} value={this.state.keyword} onChange={this.handleQuery} />

                  <div className="row service_intro section_separate" style={{ marginTop: '10px' }}>
                    <button type="submit" onClick={this.requestBooksApi} className="btn btn_service_intro">Buscar</button>
                  </div>
                </form>
              </center>
            </div>

          </div>

          <div id="portfolios" className="portfolios" >

            <center>

              <div id="booksResultArea" tabIndex="1" className="grid-wrap">

                {/*<header className="section-header" style={{ marginBottom: '-60px' }}>
                  <p>Confira abaixo o que as pessoas estão lendo</p>
                  </header>*/
                }
               
               <React.Fragment>

                 <div className="grid" id="portfoliolist3">

                  {!isLoading ? (
                    books.map(book => {
                      const { id, volumeInfo } = book;
                      return (
                            <div className="view view-first portfolio posters webdesign" data-cat="posters webdesign" >

                              <a href="#books" ng-click="openBook( id, volumeInfo.imageLink.extraLarge, volumeInfo.title, volumeInfo.authors, webReaderLink, accessViewStatus)" style={{ cursor: 'pointer' }}>
                                <img src={volumeInfo.imageLink.thumbnail} alt="img01" width="349" style={{ maxHeight: '232px', maxWidth: '349px' }} />

                                <div className="mask" style={{ backgroundColor: 'rgba(0, 0, 0, 0.7)' }}>
                                  <h4>{volumeInfo.title}</h4>
                                  <p><span className="cat-portfolio">{volumeInfo.description}</span></p>
                                  <div className="portf_detail">
                                  </div>

                                </div>
                              </a>

                            </div>
                       )
                      })
 
                      ) : (
                        <h3>Loading...</h3>
                      )}
                      
                      </div>                   
                    
               </React.Fragment>

               <div className="col-md-12 nextprev">
                  <br />
                  <button className="btn btn-default btn-lg" ng-click="getBooksPreviousPage()"><span className="glyphicon glyphicon-chevron-left"></span></button>

                  <button className="btn btn-default btn-lg" ng-click="getBooksNextPage()"><span className="glyphicon glyphicon-chevron-right"></span></button>

                </div>
                

              </div>

            </center>

          </div>

        </div>


      </section>

    );
  }
}


export default Books;