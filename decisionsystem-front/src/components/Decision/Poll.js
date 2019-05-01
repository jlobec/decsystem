import React from "react";
import PropTypes from "prop-types";
import withStyles from "@material-ui/core/styles/withStyles";
import PollOption from "./PollOption";
import Card from "@material-ui/core/Card";
import Comments from "./Comments";
import PollActions from "../../actions/poll/PollActions";
import PollSummary from "./PollSummary";
import CommonUtils from "../../actions/util/CommonUtils";

const initialState = {
  loadingPoll: true,
  loadingComments: true,
  poll: {},
  comments: []
};

class Poll extends React.Component {
  state = { ...initialState };

  async componentDidMount() {
    const pollId = this.props.match.params.pollId;
    if (pollId) {
      const foundPoll = await PollActions.doGetPollById(pollId);
      if (foundPoll) {
        this.setState({ poll: foundPoll.data, loadingPoll: false });
      }
      const comments = await PollActions.doGetPollComments(pollId);
      console.log(comments);
      if (comments) {
        this.setState({
          comments: comments.data.content,
          loadingComments: false
        });
      }
    }
  }

  render() {
    const { classes } = this.props;

    return (
      !CommonUtils.isEmptyObj(this.state.poll) && (
        <React.Fragment>
          <PollSummary
            poll={this.state.poll}
            handleVote={this.props.handleVote}
            onDetails={true}
            commentsNumber={this.state.comments.length}
          />
          {!this.state.loadingComments && (
            <Comments comments={this.state.comments} />
          )}
        </React.Fragment>
      )
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
      marginBottom: theme.spacing.unit,
      width: "80%",
      marginRigth: "10%",
      marginLeft: "10%"
    },
    [theme.breakpoints.up("lg")]: {
      marginBottom: theme.spacing.unit,
      width: "80%",
      marginRigth: "10%",
      marginLeft: "10%"
    }
  }
});

export default withStyles(styles)(Poll);
