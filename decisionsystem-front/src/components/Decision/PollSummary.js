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
import CheckCircleOutlineIcon from "@material-ui/icons/CheckCircleOutline";
import PollOption from "./PollOption";
import CommonUtils from "../../actions/util/CommonUtils";

const initialState = {
  loading: false,
  selectedOptions: [],
  comments: [],
  poll: {}
};

class PollSummary extends React.Component {
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

  handleIsDisabled = () => {
    return this.props.poll.votedByUser;
  };

  handleIsSelected = pollOption => {
    const poll = this.props.poll;
    if (!poll.votedByUser) {
      return this.state.selectedOptions.indexOf(pollOption.pollOptionId) !== -1;
    } else {
      return pollOption.userVote.voted;
    }
  };

  handleSelectOption = pollOptionId => {
    const poll = this.props.poll;
    if (!poll.votedByUser) {
      // TODO en funcion del tipo de consulta la seleccion
      // podra ser multiple o no, o preferencial (con valor asignado)
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
    }
  };

  handleClickVote = () => {
    this.props.handleVote(this.props.poll, [...this.state.selectedOptions]);
  };

  handleClickComment = () => {
    // TODO show modal
  };

  handleClickTitle = (poll, routingAvailable) => {
    if (routingAvailable) {
      // Clean possible slash at the end
      const currentUrl = CommonUtils.stripTrailingSlash(this.props.match.url);
      this.props.history.push(`${currentUrl}/${poll.pollId}`);
    }
  };

  renderPollOptions = pollOptions => {
    return pollOptions.map(pollOption => {
      return (
        <PollOption
          key={pollOption.pollOptionId}
          id={pollOption.pollOptionId}
          pollOption={pollOption}
          disabled={this.handleIsDisabled}
          checked={this.handleIsSelected(pollOption)}
          handleSelectOption={this.handleSelectOption}
        />
      );
    });
  };

  render() {
    const { classes, poll, routingAvailable } = this.props;
    const pollOptionsComponent = this.renderPollOptions(poll.pollOptions);
    return (
      <Card className={classes.card}>
        <CardContent>
          <Typography
            gutterBottom
            variant="h5"
            component="h2"
            onClick={() => this.handleClickTitle(poll, routingAvailable)}
          >
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
        <CardActions className={classes.actions}>
          {!poll.votedByUser && (
            <Button size="small" color="primary" onClick={this.handleClickVote}>
              Vote
            </Button>
          )}
          {poll.votedByUser && (
            <div>
              <Button
                size="small"
                color="secondary"
                onClick={this.handleClickVote}
              >
                Voted
              </Button>
              <IconButton aria-label="Voted">
                <CheckCircleOutlineIcon />
              </IconButton>
            </div>
          )}
          <IconButton aria-label="Comment" onClick={this.handleClickComment}>
            <CommentIcon />
            {1}
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

export default withStyles(styles)(PollSummary);
