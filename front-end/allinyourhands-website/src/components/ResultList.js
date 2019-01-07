import React, { Component } from 'react';
import PropTypes from 'prop-types';
import { withStyles } from '@material-ui/core/styles';
import GridList from '@material-ui/core/GridList';
import GridListTile from '@material-ui/core/GridListTile';
import ResultItem from './ResultItem';
import Paper from '@material-ui/core/Paper';
import Typography from '@material-ui/core/Typography';

const styles = theme => ({
  root: {
    display: 'flex',
    flexWrap: 'wrap',
    justifyContent: 'space-around',
    overflow: 'hidden',
    backgroundColor: theme.palette.background.paper,
  },
  paper: {    
    ...theme.mixins.gutters(),
    paddingTop: theme.spacing.unit * 2,
    paddingBottom: theme.spacing.unit * 2,
  },
  icon: {
    color: 'rgba(255, 255, 255, 0.54)',
  },
});


class ResultList extends Component {

  render() {
    const { classes } = this.props;

    return (
      <div className={classes.root}>
        <br /> <br />
        <GridList rows={4} cols={6}>

          {this.props.booksAreLoaded ?
            (
              this.props.books.map(book => {
                const { id, volumeInfo, webReaderLink } = book;

                return (
                  <GridListTile key={id} rows={3} cols={2}>
                    <ResultItem                        
                       searchedContentType={this.props.searchedContentType}
                       titulo={volumeInfo.title} 
                       imagemCard={volumeInfo.imageLink.thumbnail} 
                       descricao="" 
                       informacoesAdicionais={volumeInfo.description}
                       htmlParaLeitura={webReaderLink}
                       setCurrentBookHtml={this.props.setCurrentBookHtml}                       
                       />
                  </GridListTile>
                )
              })
            ) :
            (
              <Paper className={classes.paper} elevation={1}>
                <Typography variant="h4" component="h2">                  
                </Typography>
              </Paper>
            )}
 
        </GridList>
      </div>
    );
  }

}

ResultList.propTypes = {
  classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(ResultList);