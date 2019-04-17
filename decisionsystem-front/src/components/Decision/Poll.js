import React from "react";
import PropTypes from "prop-types";
import Avatar from "@material-ui/core/Avatar";
import Button from "@material-ui/core/Button";
import CssBaseline from "@material-ui/core/CssBaseline";
import Typography from "@material-ui/core/Typography";
import withStyles from "@material-ui/core/styles/withStyles";
import Card from "@material-ui/core/Card";
import CardActions from "@material-ui/core/CardActions";
import CardContent from "@material-ui/core/CardContent";
import CustomizedSnackbar from "../common/CustomizedSnackbar";
import List from "@material-ui/core/List";
import PollOption from "./PollOption";
import axios from "axios";

import { config } from "../../config";

const initialState = {
  loading: false,
  pollOptions: []
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

  getAuth = () => {
    const token = sessionStorage.getItem("jwtToken");
    const auth = {
      headers: { Authorization: "Bearer " + token }
    };
    return auth;
  };

  getPollOptions = async () => {
    const poll = this.props.poll;
    const url = config.baseUrl + "api/poll/" + poll.pollId + "/options";

    return axios.get(url, this.getAuth());
  };

  async componentDidMount() {
    const { data: pollOptions } = await this.getPollOptions();
    if (pollOptions) {
      this.setState({
        pollOptions: pollOptions
      });
    }
  }

  render() {
    const { classes, poll } = this.props;
    const pollOptionsComponent = this.state.pollOptions.map(pollOption => {
      return (
        <PollOption
          key={pollOption.pollOptionId}
          id={pollOption.pollOptionId}
          poll={poll}
          pollOption={pollOption}
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
      </Card>
    );
  }
}
const styles = theme => ({
  card: {
    minWidth: 275,
    marginBottom: theme.spacing.unit * 2
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
  }
});

export default withStyles(styles)(Poll);
