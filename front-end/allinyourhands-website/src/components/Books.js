import React, { Component } from 'react';

class Books extends Component {
  render() {
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
                    name="search" ng-model="booksKeyword" style={{ width: '70%' }} />

                  <div className="row service_intro section_separate" style={{ marginTop: '10px' }}>
                    <button type="submit" ng-click="getBooks()" className="btn btn_service_intro">Buscar</button>
                  </div>
                </form>
              </center>
            </div>

          </div>

          <div id="portfolios" className="portfolios" >

            <center>

              <div id="booksResultArea" tabindex="1" className="grid-wrap">

                <header className="section-header" style={{ marginBottom: '-60px' }}>
                  <p>Confira abaixo o que as pessoas estão lendo</p>
                </header>


                <div className="grid" id="portfoliolist3" ng-hide="bookSearchStarted">


                  <div className="view view-first portfolio posters webdesign" data-cat="posters webdesign" ng-repeat="recommendedContent in recommendedBooks | limitTo:6" >

                    <a href="#books" ng-click="openSuggestedBook(recommendedContent.id, recommendedContent.name,recommendedContent.type,recommendedContent.artist,recommendedContent.extraInformation, recommendedContent.image)" style={{ cursor: 'pointer' }}>
                      <img src="{{recommendedContent.image}}" alt="img01" width="349" style={{ maxHeight: '232px', maxWidth: '349px' }} />

                      <div className="mask" style={{ backgroundColor: 'rgba(0, 0, 0, 0.7)' }}>
                        <h4>Content Name</h4>
                        <p><span className="cat-portfolio">Artist</span></p>
                        <div className="portf_detail">

                        </div>

                      </div>
                    </a>
                  </div>

                </div>

                <div className="grid" id="portfoliolist3" ng-show="bookSearchStarted">


                  <div className="view view-first portfolio posters webdesign" data-cat="posters webdesign" ng-repeat="book in books"  >

                    <a href="#books" ng-click="openBook( book.id, book.volumeInfo.imageLink.extraLarge, book.volumeInfo.title, book.volumeInfo.authors, book.webReaderLink, book.accessViewStatus)" style={{ cursor: 'pointer' }}>
                      <img src="{{book.volumeInfo.imageLink.extraLarge}}" alt="img01" width="349" style={{ maxHeight: '232px', maxWidth: '349px' }} />

                      <div className="mask" style={{ backgroundColor: 'rgba(0, 0, 0, 0.7)' }}>
                        <h4>Book Title</h4>
                        <p><span className="cat-portfolio">Description</span></p>
                        <div className="portf_detail">


                        </div>

                      </div>
                    </a>

                  </div>

                </div>

                <div className="col-md-12 nextprev" ng-show="bookSearchStarted">
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