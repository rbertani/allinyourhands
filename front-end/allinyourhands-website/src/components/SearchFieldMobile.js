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
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import DialogTitle from '@material-ui/core/DialogTitle';
import Button from '@material-ui/core/Button';

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

class SearchFieldMobile extends React.Component {

    constructor() {

        super();
        this.state = {
            anchorEl: null,
            geolizationDialogOpen: false
        }
    }

    handleDialogGeoClickOpen = () => {
        this.setState({ geolizationDialogOpen: true });
    };

    handleDialogGeoClose = () => {
        this.setState({ geolizationDialogOpen: false });
    };

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
        const { classes, fullScreen } = this.props;

        return (

            <React.Fragment>
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

                                            <MenuItem  onClick={this.props.requestPlacesApi}
                                                style={{ visibility: this.props.placesActive }}>
                                                Lugares <br /><MapMarker />
                                            </MenuItem>

                                        ) :
                                        (

                                            <MenuItem onClick={this.handleClose}
                                                style={{ visibility: this.props.placesActive }}
                                                onClick={this.handleDialogGeoClickOpen}
                                            >
                                                Lugares <br /><MapMarker color="secondary" />
                                            </MenuItem>

                                        )
                                    }

                                </Menu>

                            </InputAdornment>
                        ),
                    }}
                />

                <Dialog
                    fullScreen={fullScreen}
                    open={this.state.geolizationDialogOpen}
                    onClose={this.handleDialogGeoClose}
                    aria-labelledby="responsive-dialog-title"
                >
                    <DialogTitle id="responsive-dialog-title">{"Serviço de geolocalização bloqueado"}</DialogTitle>
                    <DialogContent>
                        <DialogContentText>
                            O serviço de geolocalização do seu navegador está bloqueado e por isso este serviço não pode funcionar :-(.
                            Por favor, o desbloqueie e volte a acessar nosso site.
                        </DialogContentText>
                    </DialogContent>
                    <DialogActions>
                        <Button onClick={this.handleDialogGeoClose} color="primary">
                            Ok
                        </Button>
                    </DialogActions>
                </Dialog>

            </React.Fragment>


        );
    }
}

SearchFieldMobile.propTypes = {
    classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(SearchFieldMobile);