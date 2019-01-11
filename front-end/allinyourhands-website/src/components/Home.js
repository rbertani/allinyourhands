import React, { Component } from 'react';
import axios from 'axios';
import { properties } from '../properties.js';
import PropTypes from 'prop-types';
import { withStyles } from '@material-ui/core/styles';
import Grid from '@material-ui/core/Grid';
import SearchFieldMobile from './SearchFieldMobile';
import SearchFieldDesktop from './SearchFieldDesktop';
import ResultsArea from './ResultsArea';
import logoImage from '../images/lupa.jpg'
import {
  BrowserView,
  MobileView
} from "react-device-detect";
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import DialogTitle from '@material-ui/core/DialogTitle';
import Button from '@material-ui/core/Button';
import TextField from '@material-ui/core/TextField';
import InputAdornment from '@material-ui/core/InputAdornment';
import IconButton from '@material-ui/core/IconButton';
import SearchIcon from '@material-ui/icons/Search';
import { ThumbUp, ThumbDownOutline } from 'mdi-material-ui'

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
      address: "",
      userGeolocalization: "",
      formattedUserGeolocalization: "",
      geolocalizationEnabled: false,
      pagenumber: 1,
      nextpagetoken: '',
      countryCode: "pt",
      books: [],
      places: [],
      allContents: [],
      currentContentDetailedType: '',  // tipo do conteúdo que está sendo detalhado
      targetContentDetailed: false,   // informa se o conteúdo está sendo detalhado
      currentBookHtml: '', // conteudo html de um livro
      localizationDetectFailDialogOpen: false

    };

    this.setSearchedContentType = this.setSearchedContentType.bind(this);
    this.setTargetContentDetailed = this.setTargetContentDetailed.bind(this);
    this.setCurrentContentDetailedType = this.setCurrentContentDetailedType.bind(this);
    this.handleQuery = this.handleQuery.bind(this);
    this.requestBooksApi = this.requestBooksApi.bind(this);
    this.requestPlacesApi = this.requestPlacesApi.bind(this);
    this.requestAllApi = this.requestAllApi.bind(this);
    this.requestGeolocalization = this.requestGeolocalization.bind(this);
    this.pageNextAction = this.pageNextAction.bind(this);
    this.pageBackAction = this.pageBackAction.bind(this);
    this.setCurrentBookHtml = this.setCurrentBookHtml.bind(this);
    this.returnToResultList = this.returnToResultList.bind(this);
    this.handleSnackClose = this.handleSnackClose.bind(this);
    this.handleGetPosition = this.handleGetPosition.bind(this);
    this.handleDialogGeoClickOpen = this.handleDialogGeoClickOpen.bind(this);
    this.handleDialogInputChange = this.handleDialogInputChange.bind(this);
    this.handleGeofailOkDialogAction = this.handleGeofailOkDialogAction.bind(this);

    this.handleGetPosition();
   
  }

  componentDidMount = () =>{
    if (this.state.geolocalizationEnabled === false) { // não foi possível determinar a localização     
      this.handleDialogGeoClickOpen();
    }
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

  setTargetContentDetailed = (contentDetailed) => {
    this.setState({ targetContentDetailed: contentDetailed });
  }

  setCurrentContentDetailedType = (currentContentTypeValue) => {
    this.setState({ currentContentDetailedType: currentContentTypeValue });
  }

  handleQuery = (event) => {
    this.setState({ keyword: event.target.value });
  }

  handleDialogInputChange = (event) => {
    this.setState({ address: event.target.value });
  }

  handleDialogGeoClickOpen = () => {   
    this.setState({ localizationDetectFailDialogOpen: true });
  };

  handleDialogGeoClose = () => {
    this.setState({ localizationDetectFailDialogOpen: false });
  };

  handleGeofailOkDialogAction = () => {
    if(this.state.address.trim !== ""){
      this.requestGeolocalization();
      this.handleDialogGeoClose();
    }
  }

  
  requestBooksApi = (event) => {

    event.preventDefault();
    this.setSearchedContentType("books");
    this.setTargetContentDetailed(false);

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
    this.setTargetContentDetailed(false);

    if (this.state.keyword !== '')
      this.requestPlacesApiWithPagination(1);

  }

  requestPlacesApiWithPagination = (pageNumber) => {
   
    if(this.state.formattedUserGeolocalization === ""){    
      this.setState({ formattedUserGeolocalization: this.state.userGeolocalization.lat + "," + this.state.userGeolocalization.lng });
    }

    axios.get(properties.apiBaseUrl + `/places?query=` + this.state.keyword + '&offsetPlaces=' + pageNumber + '&countryCode=' + this.state.countryCode + '&latAndLong=' + this.state.formattedUserGeolocalization + '&section=')
      .then(({ data }) => {
        console.log(data);
        this.setState({ places: data, placesAreLoaded: true, placesIsBeingDetailed: false });
      });
  }

  requestAllApi = (event) => {

    event.preventDefault();
    this.setSearchedContentType("all");
    this.setTargetContentDetailed(false);

    if (this.state.keyword !== '')
      this.requestAllApiWithPagination(1);

  }

  requestAllApiWithPagination = (pageNumber) => {

    if(this.state.formattedUserGeolocalization === ""){    
      this.setState({ formattedUserGeolocalization: this.state.userGeolocalization.lat + "," + this.state.userGeolocalization.lng });
    }

    axios.get(properties.apiBaseUrl + '/all?query=' + this.state.keyword + '&pageNumber=' + pageNumber + '&nextpagetoken=' + this.state.nextpagetoken + '&countryCode=' + this.state.countryCode + '&latAndLong=' + this.state.formattedUserGeolocalization + '&section=')
      .then(({ data }) => {
        console.log(data);
        this.setState({ allContents: data, allContentsAreLoaded: true, genericContentsIsBeingDetailed: false });
      });
  }

  requestGeolocalization = () => {
    axios.get(properties.apiBaseUrl + '/geolocalization?address=' + this.state.address)
      .then(({ data }) => {
        console.log(data);

        if(data !== ""){
          this.setState({ formattedUserGeolocalization: data, geolocalizationEnabled: true });          
        }

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

    const { classes, fullScreen } = this.props;

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

        <ResultsArea
          searchedContentType={this.state.searchedContentType}
          books={this.state.books}
          booksAreLoaded={this.state.booksAreLoaded}
          currentBookHtml={this.state.currentBookHtml}
          setCurrentBookHtml={this.setCurrentBookHtml}
          bookIsBeingReaded={this.state.bookIsBeingReaded}
          places={this.state.places}
          allContents={this.state.allContents}
          targetContentDetailed={this.state.targetContentDetailed}
          setTargetContentDetailed={this.setTargetContentDetailed}
          currentContentDetailedType={this.state.currentContentDetailedType}
          setCurrentContentDetailedType={this.setCurrentContentDetailedType}
        />


        <Dialog
          fullScreen={fullScreen}
          open={this.state.localizationDetectFailDialogOpen}
          onClose={this.handleDialogGeoClose}
          aria-labelledby="responsive-dialog-title2"
        >
          <DialogTitle id="responsive-dialog-title2">{"Não conseguimos te localizar"}</DialogTitle>
          <DialogContent>
            <DialogContentText>
              Para que um de nossos serviços funcione corretamente precisamos saber sua localização, mas não conseguimos detectá-labelledby
              automaticamente. Você poderia nos informar seu endereço (ou algum ponto de referência próximo)?
            </DialogContentText>
            <TextField
                    id="outlined-name"
                    label="O que você busca?"
                    className={classes.textField}
                    value={this.props.keyword}
                    onChange={this.handleDialogInputChange}
                    margin="normal"
                    variant="outlined"
                    autoFocus={true}
                    fullWidth={true}
                    InputProps={{
                        endAdornment: (
                            <InputAdornment position="end">

                                <IconButton
                                    aria-label="Toggle password visibility"
                                    onClick={this.searchAction}
                                >
                                    <SearchIcon />
                                </IconButton>
                                
                            </InputAdornment>
                        ),
                    }}
                />
          </DialogContent>
          <DialogActions>
            <Button onClick={this.handleGeofailOkDialogAction} color="primary">
              <ThumbUp /> Ok, Pode Seguir
            </Button>
            <Button onClick={this.handleDialogGeoClose} color="primary">
               <ThumbDownOutline /> Não quero informar
            </Button>
          </DialogActions>
        </Dialog>

        

      </div>

    );
  }
}

Home.propTypes = {
  classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(Home);
