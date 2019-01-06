import React from 'react';
import PropTypes from 'prop-types';
import { withStyles } from '@material-ui/core/styles';
import Grid from '@material-ui/core/Grid';
import SearchField from './SearchField';
import SearchButtonsBar from './SearchButtonsBar';
import ResultList from './ResultList';
import Divider from '@material-ui/core/Divider';

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
});


function Home(props) {

  const { classes } = props; 

  return (

    <div className={classes.root}>

      <Grid container spacing={8} sx={12}>
        <Grid item xs={4} >
        </Grid>
        <Grid item xs={8} >
          
        </Grid>        
      </Grid>

      <Grid container spacing={8} sx={12}>
        <SearchField />
      </Grid>

      <Grid container spacing={8} sx={12}>
        <SearchButtonsBar 
            booksActive={props.booksActive}
            videosActive={props.videosActive}
            songsActive={props.songsActive}
            weatherActive={props.weatherActive}
            placesActive={props.placesActive}
        
        /> 
      </Grid>

      <Grid container spacing={8} sx={12}>
        <Divider variant="middle" />
      </Grid>

      <Grid container  sx={12}>
        <ResultList />
      </Grid>


    </div>

  );
}

Home.propTypes = {
  classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(Home);