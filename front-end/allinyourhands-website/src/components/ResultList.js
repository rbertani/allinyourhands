import React, { Component } from 'react';
import PropTypes from 'prop-types';
import { withStyles } from '@material-ui/core/styles';
import Grid from "@material-ui/core/Grid";
import ResultItem from './ResultItem';
import BookReader from './BookReader';

const styles = theme => ({
  root: {
    flexGrow: 1
  },
  paper: {
    padding: theme.spacing.unit * 2,
    textAlign: "center",
    color: theme.palette.text.secondary
  }

});


class ResultList extends Component {

  render() {
    const { classes } = this.props;

    return (
      <div className={classes.root}>

       {
          this.props.searchedContentType == "books" ?
          (

              this.props.bookIsBeingReaded ?
                (
                  <div>
                    <BookReader htmlBookContent={this.props.currentBookHtml}></BookReader>
                  </div>
                ) :
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
                            />
                          </Grid>
        
                        )
                      })
        
        
                    }
        
                </Grid>
                )

         ) :

        (
          this.props.searchedContentType == "places" ?
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
                            />
                          </Grid>
        
                        )
                      })
        
        
                    }
        
                </Grid>
          ) :
          (
            this.props.searchedContentType == "all" ?
            (
              <Grid container spacing={24}>
  
                    {this.props.allContents.map(genericContent => {
                          const { id, type, title, description, image, htmlContent } = genericContent;
          
                          return (
                            <Grid item xs={6} md={3}>
                              <ResultItem
                                searchedContentType={type}
                                titulo={<div dangerouslySetInnerHTML={{__html: title  }} /> }
                                imagemCard={image}
                                descricao={<div dangerouslySetInnerHTML={{__html: description }} /> }
                                informacoesAdicionais=""   
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

        }

       

      </div>
    );
  }

}

ResultList.propTypes = {
  classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(ResultList);