import React from "react";
import PropTypes from "prop-types";
import withStyles from "@material-ui/core/styles/withStyles";
import PollOption from "./PollOption";
import Comments from "./Comments";
import PollActions from "../../actions/poll/PollActions";
import PollSummary from "./PollSummary";
import CommonUtils from "../../actions/util/CommonUtils";

const initialState = {
  loading: false,
  poll: {},
  comments: []
};

class Poll extends React.Component {
  state = { ...initialState };

  async componentDidMount() {
    this.setState({ loading: true });
    const pollId = this.props.match.params.pollId;
    if (pollId) {
      const foundPoll = await PollActions.doGetPollById(pollId);
      if (foundPoll) {
        this.setState({ poll: foundPoll.data, loading: false });
      }
    }
  }

  render() {
    return (
      <React.Fragment>
        {!CommonUtils.isEmptyObj(this.state.poll) && (
          <PollSummary
            poll={this.state.poll}
            handleVote={this.props.handleVote}
            routingAvailable={false}
          >
            {!CommonUtils.isEmptyArray(this.state.comments) && (
              <Comments comments={this.state.comments} />
            )}
          </PollSummary>
        )}
      </React.Fragment>
    );
  }
}
const styles = theme => ({});

export default withStyles(styles)(Poll);
