import React from 'react';
import PropTypes from 'prop-types';
import { withStyles } from '@material-ui/core/styles';
import TextField from '@material-ui/core/TextField';
import { VideoVintage, BookOpenPageVariant, MapMarker, WeatherPartlycloudy } from 'mdi-material-ui'
import IconButton from '@material-ui/core/IconButton';
import InputAdornment from '@material-ui/core/InputAdornment';
import SearchIcon from '@material-ui/icons/Search';
import Divider from '@material-ui/core/Divider';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import DialogTitle from '@material-ui/core/DialogTitle';
import Button from '@material-ui/core/Button';

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
    iconButton: {
        padding: 10,
    },
});

class SearchFieldDesktop extends React.Component {

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

            <form className={classes.container} noValidate autoComplete="off">
                <TextField
                    id="outlined-search"
                    label="O que você busca?"
                    className={classes.textField}
                    value={this.props.keyword}
                    onChange={this.handleChange('keyword')}
                    margin="normal"
                    variant="outlined"
                    style={{ width: 600, marginLeft: '35%' }}
                    type="search"
                    autoFocus={true}
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
                                    color="primary"
                                >
                                    <VideoVintage />
                                </IconButton>

                                <IconButton
                                    aria-label="Toggle password visibility"
                                    onClick={this.props.requestBooksApi}
                                    color="primary"
                                >
                                    <BookOpenPageVariant />
                                </IconButton>


                                {this.props.geolocalizationEnabled ?
                                    (

                                        <IconButton
                                            aria-label="Toggle password visibility"
                                            onClick={this.searchAction}
                                            style={{ visibility: this.props.placesActive }}
                                            color="primary"
                                        >

                                            <MapMarker />
                                        </IconButton>

                                    ) :
                                    (
                                        <IconButton
                                            aria-label="Toggle password visibility"
                                            onClick={this.handleDialogGeoClickOpen}
                                            style={{ visibility: this.props.placesActive }}
                                            color="secondary"
                                        >

                                            <MapMarker />
                                        </IconButton>
                                    )
                                }


                                <IconButton
                                    aria-label="Toggle password visibility"
                                    onClick={this.searchAction}
                                    color="primary"
                                >
                                    <WeatherPartlycloudy />
                                </IconButton>

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

            </form>


        );
    }
}

SearchFieldDesktop.propTypes = {
    classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(SearchFieldDesktop);