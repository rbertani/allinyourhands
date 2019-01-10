import React, { Component } from 'react';
import axios from 'axios';
import { properties } from '../properties.js';
import PropTypes from 'prop-types';
import { withStyles } from '@material-ui/core/styles';
import Grid from '@material-ui/core/Grid';
import SearchFieldMobile from './SearchFieldMobile';
import SearchFieldDesktop from './SearchFieldDesktop';
import ResultList from './ResultList';
import logoImage from '../images/lupa.jpg' 
import {
  BrowserView,
  MobileView    
} from "react-device-detect";

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
  },
  dividerInset: {
    margin: `5px 0 0 ${theme.spacing.unit * 9}px`,
  },

});


class Home extends Component {

  constructor() {

    super();

    this.state = {
      keyword: "",
      searchedContentType: "", // "books", "videos", "places", "weather", "all"
      userGeolocalization : "",
      geolocalizationEnabled: false,
      pagenumber: 1,
      countryCode: "pt",
      books: [],
      places: [],
      allContents: [],
      booksAreLoaded: false,
      bookIsBeingReaded: false,
      currentBookHtml: '',
      placesAreLoaded: false, 
      placesIsBeingDetailed: false,
      allContentsAreLoaded: false, 
      genericContentsIsBeingDetailed: false
      
    };

    this.setSearchedContentType = this.setSearchedContentType.bind(this);
    this.handleQuery = this.handleQuery.bind(this);

    this.requestBooksApi = this.requestBooksApi.bind(this);
    this.requestPlacesApi = this.requestPlacesApi.bind(this);
    this.requestAllApi = this.requestAllApi.bind(this);
    this.pageNextAction = this.pageNextAction.bind(this);
    this.pageBackAction = this.pageBackAction.bind(this);
    this.setCurrentBookHtml = this.setCurrentBookHtml.bind(this);
    this.returnToResultList = this.returnToResultList.bind(this);
    this.handleSnackClose = this.handleSnackClose.bind(this);
    this.handleGetPosition = this.handleGetPosition.bind(this);

    this.handleGetPosition();

  }

  handleGetPosition = () => {

    if (navigator.geolocation) {

      navigator.geolocation.getCurrentPosition(data => {
        let positionUser = {
          lat: data.coords.latitude,
          lng: data.coords.longitude
        };

        this.setState({ geolocalizationEnabled: true, userGeolocalization: positionUser });
        console.log("latitude: " + this.state.userGeolocalization.lat + "  longitude: " + this.state.userGeolocalization.lng);

      });
    }

  };

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
      this.requestBooksApiWithPagination(1);    

  }

  requestBooksApiWithPagination = (pageNumber) => {

    axios.get(properties.apiBaseUrl + `/books?keyword=` + this.state.keyword + '&pagenumber=' + pageNumber + '&countryCode=' + this.state.countryCode)
      .then(({ data }) => {
        console.log(data);
        this.setState({ books: data, booksAreLoaded: true, bookIsBeingReaded: false });
      });
  }

  requestPlacesApi = (event) => {
   
    event.preventDefault();
    this.setSearchedContentType("places");

    if (this.state.keyword !== '')
     this.requestPlacesApiWithPagination(1);

  }

  requestPlacesApiWithPagination = (pageNumber) => {

    let latAndLong = this.state.userGeolocalization.lat + "," + this.state.userGeolocalization.lng;

    axios.get(properties.apiBaseUrl + `/places?query=` + this.state.keyword + '&offsetPlaces=' + pageNumber + '&countryCode=' + this.state.countryCode +'&latAndLong='+latAndLong+'&section=')
      .then(({ data }) => {
        console.log(data);
        this.setState({ places: data, placesAreLoaded: true, placesIsBeingDetailed: false });
      });
  }

  requestAllApi = (event) => {
   
    event.preventDefault();
    this.setSearchedContentType("all");

    if (this.state.keyword !== '')
     this.requestAllApiWithPagination(1);

  }

  requestAllApiWithPagination = (pageNumber) => {
     
    let latAndLong = this.state.userGeolocalization.lat + "," + this.state.userGeolocalization.lng;
    
    axios.get(properties.apiBaseUrl + `/all?query=` + this.state.keyword + '&pageNumber=' + pageNumber + '&countryCode=' + this.state.countryCode +'&latAndLong='+latAndLong+'&section=')
      .then(({ data }) => {
        console.log(data);
        this.setState({ allContents: data, allContentsAreLoaded: true, genericContentsIsBeingDetailed: false });
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

        <center>
          <img src={logoImage} />
        </center>

        
        <MobileView>
            <Grid container spacing={24} xs={12}>
              <SearchFieldMobile
                keyword={this.state.keyword}
                handleQuery={this.handleQuery}
                booksActive={this.props.booksActive}
                videosActive={this.props.videosActive}
                songsActive={this.props.songsActive}
                weatherActive={this.props.weatherActive}
                placesActive={this.props.placesActive}
                geolocalizationEnabled={this.state.geolocalizationEnabled}
                handleGetPosition={this.handleGetPosition}

                requestBooksApi={this.requestBooksApi}
                requestPlacesApi={this.requestPlacesApi}
                requestAllApi={this.requestAllApi}
               
              />
            </Grid>
        </MobileView>
            
        <BrowserView>                  
              <SearchFieldDesktop
                keyword={this.state.keyword}
                handleQuery={this.handleQuery}
                booksActive={this.props.booksActive}
                videosActive={this.props.videosActive}
                songsActive={this.props.songsActive}
                weatherActive={this.props.weatherActive}
                placesActive={this.props.placesActive}
                geolocalizationEnabled={this.state.geolocalizationEnabled}
                handleGetPosition={this.handleGetPosition}

                requestBooksApi={this.requestBooksApi}
                requestPlacesApi={this.requestPlacesApi}
                requestAllApi={this.requestAllApi}
              /> 
        </BrowserView>
        

        <br /> <br />

        <ResultList
            searchedContentType={this.state.searchedContentType}
            books={this.state.books}
            booksAreLoaded={this.state.booksAreLoaded}      
            currentBookHtml={this.state.currentBookHtml}    
            setCurrentBookHtml={this.setCurrentBookHtml}
            bookIsBeingReaded={this.state.bookIsBeingReaded}
            places={this.state.places}
            placesAreLoaded={this.state.placesAreLoaded}
            placesIsBeingDetailed={this.state.placesIsBeingDetailed}
            allContents={this.state.allContents}
            allContentsAreLoaded={this.state.allContentsAreLoaded}
            genericContentsIsBeingDetailed={this.state.genericContentsIsBeingDetailed}
        />
 

      </div>

    );
  }
}

Home.propTypes = {
  classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(Home);
