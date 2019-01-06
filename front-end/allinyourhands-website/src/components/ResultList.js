import React from 'react';
import PropTypes from 'prop-types';
import { withStyles } from '@material-ui/core/styles';
import GridList from '@material-ui/core/GridList';
import GridListTile from '@material-ui/core/GridListTile';
import ResultItem from './ResultItem';
 
const styles = theme => ({
  root: {
    display: 'flex',
    flexWrap: 'wrap',
    justifyContent: 'space-around',
    overflow: 'hidden',
    backgroundColor: theme.palette.background.paper,
  }, 
  icon: {
    color: 'rgba(255, 255, 255, 0.54)',
  },
});


function ResultList(props) {
  const { classes } = props;

  return (
    <div className={classes.root}>
      <br /> <br />
      <GridList rows={4} cols={6}>       
      
        {/*
        {tileData.map(tile => (           
           Cada Elemento...
        ))}
        */}

        <GridListTile rows={3} cols={2}>
          <ResultItem />
        </GridListTile>

        <GridListTile  rows={3} cols={2}> 
          <ResultItem />
        </GridListTile>

        <GridListTile  rows={3} cols={2}> 
          <ResultItem />
        </GridListTile>

        <GridListTile  rows={3} cols={2}> 
          <ResultItem />
        </GridListTile>


      </GridList>
    </div>
  );
}

ResultList.propTypes = {
  classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(ResultList);