import React from 'react';
import PropTypes from 'prop-types';
import { withStyles } from '@material-ui/core/styles';
import classnames from 'classnames';
import Card from "@material-ui/core/Card";
import CardMedia from "@material-ui/core/CardMedia";
import CardContent from "@material-ui/core/CardContent";
import CardActions from "@material-ui/core/CardActions";
import Collapse from "@material-ui/core/Collapse";
import IconButton from '@material-ui/core/IconButton';
import Typography from "@material-ui/core/Typography";
import red from "@material-ui/core/colors/red";
import FavoriteIcon from "@material-ui/icons/Favorite";
import ShareIcon from "@material-ui/icons/Share";
import ExpandMoreIcon from "@material-ui/icons/ExpandMore";


const styles = theme => ({
  card: {
    maxWidth: 400
  },
  media: {
    height: 0,
    paddingTop: "56.25%" // 16:9
  },
  actions: {
    display: "flex"
  },
  expand: {
    transform: "rotate(0deg)",
    marginLeft: "auto",
    transition: theme.transitions.create("transform", {
      duration: theme.transitions.duration.shortest
    })
  },
  expandOpen: {
    transform: "rotate(180deg)"
  },
  avatar: {
    backgroundColor: red[500]
  }
});


class ResultItem extends React.Component {

  constructor() {

    super();

    this.state = { expanded: false };

    this.openResultItem = this.openResultItem.bind(this);

  }

  handleExpandClick = () => {
    this.setState({ expanded: !this.state.expanded });
  };

  openResultItem = () => {

    if (this.props.searchedContentType == "books") {
      this.props.setCurrentBookHtml(this.props.htmlParaLeitura);
    }
  };

  render() {
    const { classes } = this.props;

    return (
      
        <Card className={classes.card}>

          <a href="#" onClick={() => this.openResultItem()} style={{ cursor: 'pointer' }}>
            <CardMedia
              className={classes.media}
              image={this.props.imagemCard}
              title="Paella dish"
            />
          </a>
          <CardContent>
            <Typography component="p">{this.props.titulo}</Typography>
          </CardContent>
          <CardActions className={classes.actions} disableActionSpacing>
            <IconButton aria-label="Add to favorites">
              <FavoriteIcon />
            </IconButton>
            <IconButton aria-label="Share">
              <ShareIcon />
            </IconButton>
            <IconButton
              className={classnames(classes.expand, {
                [classes.expandOpen]: this.state.expanded
              })}
              onClick={this.handleExpandClick}
              aria-expanded={this.state.expanded}
              aria-label="Show more"
            >
              <ExpandMoreIcon />
            </IconButton>
          </CardActions>
          <Collapse in={this.state.expanded} timeout="auto" unmountOnExit>
            <CardContent>
              <Typography paragraph>{this.props.descricao}</Typography>
            </CardContent>
          </Collapse>
        </Card>
 

    );
  }
}

ResultItem.propTypes = {
  classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(ResultItem);