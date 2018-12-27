import React, { Component } from 'react';
import axios from 'axios';
import { properties } from '../properties.js';
import BookReader from './BookReader.js';


class Books extends Component {

  constructor(){
    super();

    this.state = {
      keyword : "",
      pagenumber: 1,
      countryCode: "pt",
      books : [],
      booksAreLoaded : false,
      bookIsBeingReaded : false,
      currentBookHtml : ''
    };

    this.handleQuery = this.handleQuery.bind(this);
    this.requestBooksApi = this.requestBooksApi.bind(this);
    this.pageNextAction = this.pageNextAction.bind(this);
    this.pageBackAction = this.pageBackAction.bind(this);
    this.setCurrentBookHtml = this.setCurrentBookHtml.bind(this);
    this.returnToResultList = this.returnToResultList.bind(this);
  }

  handleQuery = (event) => {
    //console.log("VALOR DIGITADO: "+event.target.value);
    this.setState({ keyword: event.target.value});
  }

  requestBooksApi = (event) => {

    event.preventDefault();
    this.requestBooksApiFinal(1);
  }

  requestBooksApiFinal = (pageNumber) => {
   
    this.setState({areBooksLoaded : false});
    axios.get(properties.apiBaseUrl + `/books?keyword=`+this.state.keyword+'&pagenumber='+pageNumber+'&countryCode='+this.state.countryCode)
    .then(({data}) => {        
        console.log(data);
        this.setState({ books : data.books, booksAreLoaded : true, bookIsBeingReaded : false });
    });
  }


  pageNextAction =  (event) => {

    this.requestBooksApiFinal(this.state.pagenumber + 1);
    this.setState({ pagenumber: this.state.pagenumber + 1});
   
  }

  pageBackAction =  (event) => {

    this.requestBooksApiFinal(this.state.pagenumber - 1);
    this.setState({ pagenumber: this.state.pagenumber - 1});

  }

  setCurrentBookHtml = (webReaderLink) => {    
      
    this.setState({bookIsBeingReaded: true, currentBookHtml : webReaderLink});
  }

  returnToResultList = () => {
    this.setState({bookIsBeingReaded: false});
  }



  render() {

    const { books } = this.state;
    const bookIsBeingReaded = this.state.bookIsBeingReaded;
    const booksAreLoaded = this.state.booksAreLoaded;

    return (

      <section id="books" className="layers page_current">

        <div className="page_content">

          <div className="container-fluid no-marg">


          {!bookIsBeingReaded ? 
            (
             <div>
              <div className="row row_responsive" style={{ marginBottom: '-20px', marginTop: '-20px' }}>

                <div className="col-lg-11 section_general">

                  <header className="section-header">
                    <h3 className="section-title">Livros</h3>
                    <p>Ler é sonhar com olhos abertos. Busque-os.</p>
                    <div className="border-divider"></div>
                  </header>
                </div>
              </div>

            
                <div className="row row_responsive" style={{ marginBottom: '-20px', marginTop: '-50px' }}>
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
            ) : 
            (

             <div>

                <div className="row row_responsive" style={{ marginBottom: '-20px', marginTop: '-20px' }}>

                <div className="col-lg-11 section_general">

                  <header className="section-header">
                    <h3 className="section-title">Livros</h3>
                    <p>Abaixo você pode ler o livro escolhido:</p>
                    <div className="border-divider"></div>
                  </header>
                </div>
                </div>


                <div className="row row_responsive" style={{ marginBottom: '-20px', marginTop: '-30px' }}>
                  <center>
                    <form style={{ marginTop: '40px' }}>
                      
                      <div className="row service_intro section_separate" style={{ marginTop: '10px' }}>
                        <button type="submit" onClick={this.returnToResultList} className="btn btn_service_intro">Voltar</button>
                      </div>
                    </form>
                  </center>
                </div>

             </div>

            )}

          </div>

          <div id="portfolios" className="portfolios" >

            <center>
            
              {!bookIsBeingReaded ? 
              ( 
                <div id="booksResultArea" tabIndex="1" className="grid-wrap">
  
                      <div className="grid" id="portfoliolist3">
                        {
                          books.map(book => {
                            const { id, volumeInfo, webReaderLink } = book;
                            return (
                                  <div key={id} className="view view-first portfolio posters webdesign" data-cat="posters webdesign" >

                                    <a href="#" onClick={() => this.setCurrentBookHtml(webReaderLink)} style={{ cursor: 'pointer' }}>
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
      
                          }
                            
                        </div>                   
                          
                    
                    {booksAreLoaded ?
                      (
                          <div className="col-md-12 nextprev">
                            <br />
                            <button className="btn btn-default btn-lg" onClick={this.pageBackAction}><span className="glyphicon glyphicon-chevron-left"></span></button>
                            <button className="btn btn-default btn-lg" onClick={this.pageNextAction}><span className="glyphicon glyphicon-chevron-right"></span></button>
                          </div>
                      ) : (<div></div>)}
                </div>

              ) :

              (
                <div>                               
                  <BookReader htmlBookContent={this.state.currentBookHtml}></BookReader>
                </div>
              )}
              
            </center>

          </div>

        </div>

         
      </section>



    );
  }
}


export default Books;