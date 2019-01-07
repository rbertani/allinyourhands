import React, { Component } from 'react';
import ReactDOM from 'react-dom';
import axios from 'axios';
import { properties } from '../properties.js';
import PropTypes from 'prop-types';
import { withStyles } from '@material-ui/core/styles';
import Grid from '@material-ui/core/Grid';
import SearchField from './SearchField';
import SearchButtonsBar from './SearchButtonsBar';
import ResultList from './ResultList';
import Divider from '@material-ui/core/Divider';
import Snackbar from '@material-ui/core/Snackbar';
import BookReader from './BookReader';
import GeolocationDetector from './GeolocationDetector';


const styles = theme => ({
  root: {
    flexGrow: 1,
  },
  paper: {
    padding: theme.spacing.unit * 2,
    textAlign: 'center'
  },
  button: {
    margin: theme.spacing.unit
  }

});


class Home extends Component {

  constructor() {

    super();

    this.state = {
      keyword: "",
      searchedContentType: "", // "books", "videos", "places", "weather", "all"
      openedSnack: false,
      snackVertical: "bottom",
      snackHorizontal: "left",
      latitude: "",
      longitude: "",

      pagenumber: 1,
      countryCode: "pt",
      books: [],
      booksAreLoaded: false,
      bookIsBeingReaded: false,
      currentBookHtml: ''
    };

    this.setSearchedContentType = this.setSearchedContentType.bind(this);
    this.handleQuery = this.handleQuery.bind(this);

    this.requestBooksApi = this.requestBooksApi.bind(this);
    this.pageNextAction = this.pageNextAction.bind(this);
    this.pageBackAction = this.pageBackAction.bind(this);
    this.setCurrentBookHtml = this.setCurrentBookHtml.bind(this);
    this.returnToResultList = this.returnToResultList.bind(this);
    this.handleSnackClose = this.handleSnackClose.bind(this);


  }

  setSearchedContentType = (contentType) => {
    this.setState({ searchedContentType: contentType });
  }

  handleQuery = (event) => {
    this.setState({ keyword: event.target.value });
  }

  requestBooksApi = (event) => {

    event.preventDefault();

    this.setSearchedContentType("books");

    if (this.state.keyword !== '')
      this.requestBooksApiFinal(1);
    else this.setState({ openedSnack: true });


  }

  requestBooksApiFinal = (pageNumber) => {

    axios.get(properties.apiBaseUrl + `/books?keyword=` + this.state.keyword + '&pagenumber=' + pageNumber + '&countryCode=' + this.state.countryCode)
      .then(({ data }) => {
        console.log(data);
        this.setState({ books: data.books, booksAreLoaded: true, bookIsBeingReaded: false });
      });
  }


  pageNextAction = (event) => {

    this.requestBooksApiFinal(this.state.pagenumber + 1);
    this.setState({ pagenumber: this.state.pagenumber + 1 });

  }

  pageBackAction = (event) => {

    this.requestBooksApiFinal(this.state.pagenumber - 1);
    this.setState({ pagenumber: this.state.pagenumber - 1 });

  }

  setCurrentBookHtml = (webReaderLink) => {

    this.setState({ bookIsBeingReaded: true, currentBookHtml: webReaderLink });
  }

  returnToResultList = () => {
    this.setState({ bookIsBeingReaded: false });
  }

  handleSnackClose = () => {
    this.setState({ openedSnack: false });
  };

  render() {

    const { classes } = this.props;

    return (

      <div className={classes.root}>

        <Grid container spacing={24}>
          <Grid item xs={4} >
          </Grid>
          <Grid item xs={8} >
          </Grid>
        </Grid>

        <Grid container spacing={24}>
          <SearchField keyword={this.state.keyword} handleQuery={this.handleQuery} />
        </Grid>

        <Grid container spacing={24}>
          <SearchButtonsBar

            booksActive={this.props.booksActive}
            videosActive={this.props.videosActive}
            songsActive={this.props.songsActive}
            weatherActive={this.props.weatherActive}
            placesActive={this.props.placesActive}
            requestBooksApi={this.requestBooksApi}

          />
        </Grid>

       
      {
       this.state.searchedContentType == "books" ?
       (

        this.state.bookIsBeingReaded ?
        (
            <div>
              <BookReader htmlBookContent={this.state.currentBookHtml}></BookReader>
            </div>
         ) :
         (
            <ResultList
              booksAreLoaded={this.state.booksAreLoaded}
              books={this.state.books}
              setCurrentBookHtml={this.setCurrentBookHtml}
              searchedContentType={this.state.searchedContentType}
            />
          )
        
       ):

       (
        <div></div>
        )
        
        }

        <GeolocationDetector />

      </div>

    );
  }
}

Home.propTypes = {
  classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(Home);
