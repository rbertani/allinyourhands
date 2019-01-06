import React from 'react';
import PropTypes from 'prop-types';
import { withStyles } from '@material-ui/core/styles';
import TextField from '@material-ui/core/TextField';
import Button from '@material-ui/core/Button';
import Grid from '@material-ui/core/Grid';
import { Magnify } from 'mdi-material-ui'

const styles = theme => ({
    
    textField: {
        marginLeft: theme.spacing.unit,
        marginRight: theme.spacing.unit
    },    
    button: {
        marginTop: theme.spacing.unit * 3,
        marginLeft: theme.spacing.unit,
        marginRight: theme.spacing.unit
    },
  
});

class SearchField extends React.Component {
   
    handleChange = keyword => event => {
      
        this.props.handleQuery(event);

    };

    

    render() {
        const { classes } = this.props;

        return (
            <React.Fragment>
                
                <Grid item xs={2} />
                <Grid item xs={5}>
                    <TextField
                        id="outlined-name"
                        label="O que você busca?"
                        className={classes.textField}
                        value={this.props.keyword}
                        onChange={this.handleChange('keyword')}
                        margin="normal"
                        variant="outlined"
                        autoFocus={true}
                        fullWidth={true}
                    />
                </Grid>

                <Grid item xs={2} >
                    <Button 
                       variant="contained"
                       color="primary" 
                       className={classes.button}                         
                       >
                        Buscar <br /> <Magnify />
                    </Button>
                </Grid>
            </React.Fragment>

        );
    }
}

SearchField.propTypes = {
    classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(SearchField);