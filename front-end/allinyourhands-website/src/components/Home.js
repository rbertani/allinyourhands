import React, { Component } from 'react';
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
import SnackbarContent from '@material-ui/core/SnackbarContent';
import CheckCircleIcon from '@material-ui/icons/CheckCircle';
import ErrorIcon from '@material-ui/icons/Error';
import InfoIcon from '@material-ui/icons/Info';
import CloseIcon from '@material-ui/icons/Close';
import WarningIcon from '@material-ui/icons/Warning';
import IconButton from '@material-ui/core/IconButton';
import green from '@material-ui/core/colors/green';
import amber from '@material-ui/core/colors/amber';


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
  success: {
    backgroundColor: green[600],
  },
  error: {
    backgroundColor: theme.palette.error.dark,
  },
  info: {
    backgroundColor: theme.palette.primary.dark,
  },
  warning: {
    backgroundColor: amber[700],
  },
  icon: {
    fontSize: 20,
  },
  iconVariant: {
    opacity: 0.9,
    marginRight: theme.spacing.unit,
  },
  message: {
    display: 'flex',
    alignItems: 'center',
  },
});

function MySnackbarContent(props) {
  const { classes, className, message, onClose, variant, ...other } = props;
  const Icon = variantIcon[variant];

  return (
    <SnackbarContent    
      aria-describedby="client-snackbar"
      message={
        <span id="client-snackbar" className={classes.message}>
          <Icon />
          {message}
        </span>
      }
      action={[
        <IconButton
          key="close"
          aria-label="Close"
          color="inherit"
          className={classes.close}
          onClick={onClose}
        >
          <CloseIcon className={classes.icon} />
        </IconButton>,
      ]}
      {...other}
    />
  );
}

MySnackbarContent.propTypes = {
  classes: PropTypes.object.isRequired,
  className: PropTypes.string,
  message: PropTypes.node,
  onClose: PropTypes.func,
  variant: PropTypes.oneOf(['success', 'warning', 'error', 'info']).isRequired,
};

const MySnackbarContentWrapper = withStyles(styles)(MySnackbarContent);

const variantIcon = {
  success: CheckCircleIcon,
  warning: WarningIcon,
  error: ErrorIcon,
  info: InfoIcon,
};



//function Home(props) {
class Home extends Component {

  constructor() {

    super();

    this.state = {
      keyword : "",
      openedSnack: false,

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
    this.setState({ keyword: event.target.value});
  }

  requestBooksApi = (event) => {

    event.preventDefault();

    if(this.state.keyword != '')
      this.requestBooksApiFinal(1);
    else this.setState({openedSnack: true});


  }

  requestBooksApiFinal = (pageNumber) => {
      
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
    
    const { classes } = this.props; 

    return (

      <div className={classes.root}>

        <Grid container spacing={8} sx={12}>
          <Grid item xs={4} >
          </Grid>
          <Grid item xs={8} >
          </Grid>
        </Grid>

        <Grid container spacing={8} sx={12}>
          <SearchField keyword={this.state.keyword} handleQuery={this.handleQuery}/>
        </Grid>

        <Grid container spacing={8} sx={12}>
          <SearchButtonsBar           
            booksActive={this.props.booksActive}
            videosActive={this.props.videosActive}
            songsActive={this.props.songsActive}
            weatherActive={this.props.weatherActive}
            placesActive={this.props.placesActive}

            requestBooksApi={this.requestBooksApi}

          />
        </Grid>

        <Grid container spacing={8} sx={12}>
          <Divider variant="middle" />
        </Grid>

        <Grid container sx={12}>
          <ResultList booksAreLoaded={this.state.booksAreLoaded} books={this.state.books}/>
        </Grid>

        {this.state.openedSnack ? 
           (
            <Snackbar
            anchorOrigin={{
              vertical: 'bottom',
              horizontal: 'left',
            }}
            open={true}
            autoHideDuration={6000}
            onClose={this.handleClose}
           />

            )
            :
            ( 

              <div></div>

            )
        }

      </div>

    );
  }
}

Home.propTypes = {
  classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(Home);