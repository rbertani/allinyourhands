import React from 'react';
import PropTypes from 'prop-types';
import { withStyles } from '@material-ui/core/styles';
import TextField from '@material-ui/core/TextField';
import IconButton from '@material-ui/core/IconButton';
import InputAdornment from '@material-ui/core/InputAdornment';
import SearchIcon from '@material-ui/icons/Search';
import Menu from '@material-ui/core/Menu';
import MenuItem from '@material-ui/core/MenuItem';
import { VideoVintage, BookOpenPageVariant, MapMarker, WeatherPartlycloudy } from 'mdi-material-ui'

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

    constructor() {

        super();
        this.state = {
            anchorEl: null,
        }
    }

    handleClose = () => {
        this.setState({ anchorEl: null });
    };

    handleChange = keyword => event => {

        this.props.handleQuery(event);

    };

    searchAction = (event) => {
        this.setState({ anchorEl: event.currentTarget });
    }

    render() {
        const { classes } = this.props;

        return (
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
                InputProps={{
                    endAdornment: (
                        <InputAdornment position="end">
                            <IconButton
                                aria-label="Toggle password visibility"
                                onClick={this.searchAction}
                            >
                                <SearchIcon />
                            </IconButton>
                            <Menu
                                id="simple-menu"
                                anchorEl={this.state.anchorEl}
                                open={Boolean(this.state.anchorEl)}
                                onClose={this.handleClose}
                            >
                                <MenuItem onClick={this.handleClose} style={{ visibility: this.props.videosActive }}> Vídeos <br /> <VideoVintage /></MenuItem>
                                <MenuItem onClick={this.props.requestBooksApi} 
                                          style={{ visibility: this.props.booksActive }}>
                                    Livros <br /><BookOpenPageVariant />
                                </MenuItem>
                                {this.props.geolocalizationEnabled ?
                                    (

                                    <MenuItem onClick={this.handleClose} style={{ visibility: this.props.placesActive }}> Lugares <br /><MapMarker /></MenuItem>
                                     
                                    ) :
                                    (

                                        <MenuItem onClick={this.handleClose} 
                                                  style={{ visibility: this.props.placesActive }}
                                                  onClick={this.handleClickDisabledPlaces}> Lugares <br /><MapMarker /></MenuItem>
                                    
                                    )
                                }

                            </Menu>
                        </InputAdornment>
                    ),
                }}
            />
        );
    }
}

SearchField.propTypes = {
    classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(SearchField);