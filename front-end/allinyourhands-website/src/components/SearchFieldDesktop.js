import React from 'react';
import PropTypes from 'prop-types';
import { withStyles } from '@material-ui/core/styles';
import TextField from '@material-ui/core/TextField';
import { VideoVintage, BookOpenPageVariant, MapMarker, WeatherPartlycloudy } from 'mdi-material-ui'
import IconButton from '@material-ui/core/IconButton';
import InputAdornment from '@material-ui/core/InputAdornment';
import SearchIcon from '@material-ui/icons/Search';
import Divider from '@material-ui/core/Divider';

const styles = theme => ({
    container: {
        display: 'flex',
        flexWrap: 'wrap',
    },
    textField: {
        marginLeft: theme.spacing.unit,
        marginRight: theme.spacing.unit,
    },
    dense: {
        marginTop: 16,
    },
    menu: {
        width: 200,
    },
    divider: {
        width: 1,
        height: 28,
        margin: 4,
    },
});

class SearchFieldDesktop extends React.Component {

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

            <form className={classes.container} noValidate autoComplete="off">
                <TextField
                    id="outlined-full-width"
                    label="O que você busca?"
                    className={classes.textField}
                    style={{ width: 600, marginLeft: '35%' }}
                    value={this.props.keyword}
                    onChange={this.handleChange('keyword')}
                    placeholder="Placeholder"
                    fullWidth
                    margin="dense"
                    variant="outlined"
                    autoFocus={true}
                    InputLabelProps={{
                        shrink: true,
                    }}
                    InputProps={{
                        endAdornment: (
                            <InputAdornment position="end">
                                <IconButton
                                    aria-label="Toggle password visibility"
                                    onClick={this.searchAction}
                                >
                                    <SearchIcon />
                                </IconButton>

                                <Divider className={classes.divider} />

                                <IconButton
                                    aria-label="Toggle password visibility"
                                    onClick={this.searchAction}
                                >
                                    <VideoVintage />
                                </IconButton>

                                <IconButton
                                    aria-label="Toggle password visibility"
                                    onClick={this.searchAction}
                                >
                                    <BookOpenPageVariant />
                                </IconButton>


                                {this.props.geolocalizationEnabled ?
                                    (
                                       
                                        <IconButton
                                            aria-label="Toggle password visibility"
                                            onClick={this.searchAction}
                                            style={{ visibility: this.props.placesActive }}>
                                            <MapMarker />
                                        </IconButton>

                                    ) :
                                    (
                                        <IconButton
                                            aria-label="Toggle password visibility"
                                            onClick={this.searchAction}
                                            style={{ visibility: this.props.placesActive }}>

                                            <MapMarker />
                                        </IconButton>
                                    )
                                }


                                <IconButton
                                    aria-label="Toggle password visibility"
                                    onClick={this.searchAction}
                                >
                                    <WeatherPartlycloudy />
                                </IconButton>

                            </InputAdornment>
                        ),
                    }}
                />

            </form>


        );
    }
}

SearchFieldDesktop.propTypes = {
    classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(SearchFieldDesktop);