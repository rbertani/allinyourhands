import React from 'react';
import PropTypes from 'prop-types';
import { withStyles } from '@material-ui/core/styles';
import Button from '@material-ui/core/Button';
import { VideoVintage, BookOpenPageVariant, MapMarker, WeatherPartlycloudy } from 'mdi-material-ui'
import Grid from '@material-ui/core/Grid';

const styles = theme => ({
  button: {
    margin: theme.spacing.unit,
  },
  input: {
    display: 'none',
  },
});

class SearchButtonsBar extends React.Component {

  render() {

    const { classes } = this.props;

    return (

      <React.Fragment>

        <Grid item xs={2} />
        <Grid item xs={10}>
          <Button variant="outlined" color="primary" className={classes.button} style={{ visibility: this.props.videosActive }} >
            Vídeos <br /> <VideoVintage />
          </Button>
          <Button variant="outlined" color="primary" className={classes.button} 
                  style={{ visibility: this.props.booksActive }} onClick={this.props.requestBooksApi}>
            Livros <br /><BookOpenPageVariant />
          </Button>
          <Button variant="outlined" color="primary" className={classes.button} style={{ visibility: this.props.placesActive }}>
            Lugares <br /><MapMarker />
          </Button>
          <Button variant="outlined" color="primary" className={classes.button} style={{ visibility: this.props.weatherActive }}>
            Previsão <br /><WeatherPartlycloudy />
          </Button>

        </Grid>
      </React.Fragment>
    );
  }

}

SearchButtonsBar.propTypes = {
  classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(SearchButtonsBar);