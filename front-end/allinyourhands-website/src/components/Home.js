import React, { Component } from 'react';


class Home extends Component {
  render() {
    
    return (

      <section id="profile" className="layers page_current">

        <div className="page_content">

          <div className="block-header">

            <div className="row no-marg my_profile">

              <div className="col-xs-12 col-md-12 no-padd info_profile">

                <div className="bg_desc clearfix">
                  <div className="inner-text" style={{ textAlign: 'left' }}>
                    <center>
                      <h2>Tudo em suas Mãos</h2>
                      <small className="myjob">Seu buscador de conteúdos simples e eficiente</small>
                    </center>

                    <div className="feature-desc">
                      {/*<p>Como funciona? Simples: escolha um conteúdo, faça sua busca e reproduza a mídia direto no navegador. ;-) </p> */}
                      <p>ATENÇÃO: POR ENQUANTO APENAS OS LIVROS ESTÃO DISPONÍVEIS PARA PESQUISA. ESTAMOS MIGRANDO O SITE PARA UMA TECNOLOGIA MAIS MODERNA PARA ATENDÊ-LOS MELHOR :-)</p>
                    </div>
                    <br />

                  </div>
                </div>

                <div className="bg_info clearfix" style={{ textAlign: 'center', marginBottom: '40px' }}>
                  <div className="inner-text">
                    <ul>
                      <li className="user_name"><strong><i className="fa fa-user"></i>Autor</strong>  Ricardo M Bertani</li>
                      <li><strong><i className="fa fa-envelope"></i>Contato</strong>  ricardombertani@gmail.com</li>
                      <li><strong><i className="fa fa-globe"></i>Portfolio</strong>  <a href="http://ricardombertani.com.br" target="_blank">http://ricardombertani.com.br</a></li>

                    </ul>
                  </div>
                </div>

              </div>

            </div>

          </div>


          {/*
          <div id="contacts">

            <div id="tabs" className="tab_close" title="" data-toggle="tooltip" data-original-title="Deixe sua opinião">
              <i className="fa fa-envelope"></i>
            </div>

            <div id="tabs_resp" className="tab_close" title="" data-toggle="tooltip" data-original-title="Deixe sua opinião">
              <i className="fa fa-envelope"></i>
            </div>

            <div id="contentContact" className="two">

              <div className="contact_closed" id="contact_closed" title="Close Contact" data-toggle="tooltip">
                <i className="fa fa-times"></i>
              </div>

              <div className="innerpadding">

                <div className="separte-content">
                  <h2>Sua opinião é muito importante</h2>

                  <div id="contact-status"></div>

                  <form action="#" id="contactform" className="contact-form">

                    <div className="row form-group">

                      <div className="col-md-12 col-sm-12 col-xs-12 form-group" id="contact-name">
                        <i className="fa fa-user icon-contact"></i>
                        <input type="text" name="name" className="form-control name-contact" style={{ marginTop: 0 }} />
                      </div>

                      <div className="col-md-12 col-sm-12 col-xs-12 form-group" id="contact-email">
                        <i className="fa fa-envelope icon-contact"></i>
                        <input type="text" name="email" className="form-control email-contact" placeholder="email..."  />
                      </div>
                      

                      <div className="col-md-12 col-sm-12 col-xs-12 form-group" id="contact-message">
                        <i className="fa fa-comments icon-contact"></i>
                        <textarea name="message" cols="90" rows="10" className="form-control message-contact" id="inputError" placeholder="Sua mensagem..." ng-model="emailMessage"></textarea>
                      </div>
                      

                      <div className="col-md-12 col-sm-12 col-xs-12 form-group" style={{ marginBottom: 0 }}>
                        <button type="submit" className="btn btn-block btn-cta btn-cta-contact-2" ng-click="sendMail()">Enviar<i className="fa fa-angle-right"></i></button>
                      </div>

                    </div>

                  </form>


                </div>


                
                <div className="collapser">

                  <p>Se preferir envie um e-mail para: </p>

                  <ul className="info_contact">
                    <li><span><i className="fa fa-envelope"></i></span> ricardombertani@gmail.com</li>
                  </ul>


                </div>
                


              </div> 


            </div>

          </div>*/}

        </div>


      </section>

    );
  }
}


export default Home;