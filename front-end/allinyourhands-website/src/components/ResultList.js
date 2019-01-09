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
              <div></div>
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