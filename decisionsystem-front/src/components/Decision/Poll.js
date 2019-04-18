import React from "react";
import PropTypes from "prop-types";
import Avatar from "@material-ui/core/Avatar";
import Button from "@material-ui/core/Button";
import List from "@material-ui/core/List";
import Typography from "@material-ui/core/Typography";
import withStyles from "@material-ui/core/styles/withStyles";
import Card from "@material-ui/core/Card";
import CardActions from "@material-ui/core/CardActions";
import CardContent from "@material-ui/core/CardContent";
import IconButton from "@material-ui/core/IconButton";
import CommentIcon from "@material-ui/icons/Comment";
import AssignmentTurnedInIcon from "@material-ui/icons/AssignmentTurnedIn";
import CheckCircleOutlineIcon from "@material-ui/icons/CheckCircleOutline";

import PollOption from "./PollOption";

const initialState = {
  loading: false,
  selectedOptions: []
};

class Poll extends React.Component {
  state = { ...initialState };

  getFormattedDate = time => {
    const options = {
      year: "numeric",
      month: "numeric",
      day: "numeric",
      hour: "numeric",
      minute: "numeric",
      hour12: false
    };
    return new Intl.DateTimeFormat("en-US", options).format(new Date(time));
  };

  async componentDidMount() {
    // TODO
  }

  handleSelectOption = pollOptionId => {
    // TODO en funcion del tipo de consulta la seleccion podra ser multiple o no
    const { selectedOptions } = this.state;
    const currentIndex = selectedOptions.indexOf(pollOptionId);
    const newSelectedOptions = [...selectedOptions];

    if (currentIndex === -1) {
      newSelectedOptions.push(pollOptionId);
    } else {
      newSelectedOptions.splice(currentIndex, 1);
    }

    this.setState({
      selectedOptions: newSelectedOptions
    });
  };

  render() {
    const { classes, poll } = this.props;
    const pollOptionsComponent = poll.pollOptions.map(pollOption => {
      return (
        <PollOption
          key={pollOption.pollOptionId}
          id={pollOption.pollOptionId}
          pollOption={pollOption}
          checked={
            this.state.selectedOptions.indexOf(pollOption.pollOptionId) !== -1
          }
          handleSelectOption={this.handleSelectOption}
        />
      );
    });
    return (
      <Card className={classes.card}>
        <CardContent>
          <Typography gutterBottom variant="h5" component="h2">
            {poll.title}
          </Typography>
          <Typography className={classes.pos} color="textSecondary">
            {`From ${this.getFormattedDate(
              poll.startsAt
            )} to ${this.getFormattedDate(poll.endsAt)}`}
          </Typography>
          <Typography component="p">{poll.description}</Typography>
          <List className={classes.pollOptionList}>{pollOptionsComponent}</List>
        </CardContent>
        <CardActions className={classes.actions} disableActionSpacing>
          <IconButton aria-label="Comments">
            <CommentIcon />
          </IconButton>
          <IconButton aria-label="Vote">
            <AssignmentTurnedInIcon />
          </IconButton>
        </CardActions>
      </Card>
    );
  }
}
const styles = theme => ({
  card: {
    minWidth: 275,
    [theme.breakpoints.down("sm")]: {
      marginBottom: theme.spacing.unit,
      width: "100%"
    },
    [theme.breakpoints.up("md")]: {
      marginBottom: theme.spacing.unit * 2,
      width: "80%",
      marginRigth: "10%",
      marginLeft: "10%"
    },
    [theme.breakpoints.up("lg")]: {
      marginBottom: theme.spacing.unit * 2,
      width: "80%",
      marginRigth: "10%",
      marginLeft: "10%"
    }
  },
  pos: {
    marginBottom: 12
  },
  inline: {
    display: "inline"
  },
  pollOptionList: {
    width: "100%",
    maxWidth: 720,
    backgroundColor: theme.palette.background.paper
  },
  actions: {
    display: "flex",
    alignContent: "flex-end"
  }
});

export default withStyles(styles)(Poll);
