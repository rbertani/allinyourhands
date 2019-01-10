import React, { Component } from 'react';
import PropTypes from 'prop-types';
import { withStyles } from '@material-ui/core/styles';
import Grid from "@material-ui/core/Grid";
import ResultItem from './ResultItem';
import BookReader from './BookReader';
import Button from '@material-ui/core/Button';
import { UndoVariant } from 'mdi-material-ui'

const styles = theme => ({
  root: {
    flexGrow: 1
  },
  paper: {
    padding: theme.spacing.unit * 2,
    textAlign: "center",
    color: theme.palette.text.secondary
  },
  button: {
    margin: theme.spacing.unit,
  },

});


class ResultsArea extends Component {

  backAction = () => {
    this.props.setTargetContentDetailed(false);
  }

  render() {
    const { classes } = this.props;

    return (
      <div className={classes.root}>


      {
        this.props.targetContentDetailed === false ?  
        (                                             /* LISTA DE RESULTADOS */

          this.props.searchedContentType === "books" ?
          (
            <Grid container spacing={24}>

            {this.props.books.map(book => {
                  const { id, volumeInfo, webReaderLink } = book;
  
                  return (
                    <Grid item xs={6} md={3}>
                      <ResultItem
                        searchedContentType={this.props.searchedContentType}
                        titulo={volumeInfo.title}
                        imagemCard={volumeInfo.imageLink.thumbnail}
                        descricao={volumeInfo.description}
                        informacoesAdicionais=""
                        htmlParaLeitura={webReaderLink}
                        setCurrentBookHtml={this.props.setCurrentBookHtml}
                        setCurrentContentDetailedType={this.props.setCurrentContentDetailedType}
                        setTargetContentDetailed={this.props.setTargetContentDetailed}
                      />
                    </Grid>
  
                  )
                })
  
  
              }
  
          </Grid>          
          ) :
          (
            this.props.searchedContentType === "places" ?
            (
              <Grid container spacing={24}>
  
                    {this.props.places.map(place => {
                          const { id, name, address, distance, postalCode, imagePreviewURL, categoryName } = place;
          
                          return (
                            <Grid item xs={6} md={3}>
                              <ResultItem
                                searchedContentType={this.props.searchedContentType}
                                titulo={<div dangerouslySetInnerHTML={{__html: name + "<br />" + address  }} /> }
                                imagemCard={imagePreviewURL}
                                descricao={<div dangerouslySetInnerHTML={{__html: "<br />Categoria: " + categoryName+ "<br />CEP: " + postalCode + "<br />Distancia: " + distance }} /> }
                                informacoesAdicionais=""   
                                setTargetContentDetailed={this.props.setTargetContentDetailed}
                                setCurrentContentDetailedType={this.props.setCurrentContentDetailedType}
                              />
                            </Grid>
          
                          )
                        })
          
          
                      }
          
                  </Grid>
            ) :
            (
              this.props.searchedContentType === "all" ?
              (
                <Grid container spacing={24}>
    
                      {this.props.allContents.map(genericContent => {
                            const { id, type, title, description, image, htmlContent } = genericContent;
            
                            return (
                              <Grid item xs={6} md={3}>
                                <ResultItem
                                  searchedContentType={this.props.searchedContentType}
                                  currentContentType={type}
                                  titulo={<div dangerouslySetInnerHTML={{__html: title  }} /> }
                                  imagemCard={image}
                                  descricao={<div dangerouslySetInnerHTML={{__html: description }} /> }
                                  informacoesAdicionais=""   
                                  setTargetContentDetailed={this.props.setTargetContentDetailed}
                                  setCurrentContentDetailedType={this.props.setCurrentContentDetailedType}
                                  setCurrentBookHtml={this.props.setCurrentBookHtml}  
                                  htmlParaLeitura={htmlContent}                             
                                />
                              </Grid>
            
                            )
                          })
            
            
                        }
            
                    </Grid>
              ) :

              (

                <div></div>

              )

            )
          )


        )   
        
        :

        (                                                           /* DETALHE DE UM RESULTADO */     
            
          this.props.currentContentDetailedType === "books" ?

          (
            <div>
              <Button color="primary" className={classes.button} onClick={() => this.backAction()}>
                <UndoVariant />  Voltar
              </Button>
              <BookReader htmlBookContent={this.props.currentBookHtml}></BookReader>
            </div>
          ) :

          ( 
            <div> </div>
          )

        )

      }


      </div>
    );
  }

}

ResultsArea.propTypes = {
  classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(ResultsArea);