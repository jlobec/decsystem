import React from "react";
import PropTypes from "prop-types";
import withStyles from "@material-ui/core/styles/withStyles";
import Paper from "@material-ui/core/Paper";
import Tabs from "@material-ui/core/Tabs";
import Tab from "@material-ui/core/Tab";
import Typography from "@material-ui/core/Typography";
import Comments from "./Comments";
import PollActions from "../../actions/poll/PollActions";
import PollSummary from "./PollSummary";
import Results from "./Results";
import CommonUtils from "../../actions/util/CommonUtils";

function TabContainer(props) {
  return (
    <Typography component="div" style={{ padding: 8 * 3 }}>
      {props.children}
    </Typography>
  );
}

const initialState = {
  loadingPoll: true,
  loadingComments: true,
  loadingResults: true,
  tabValue: 0,
  poll: {},
  comments: [],
  results: []
};

class Poll extends React.Component {
  state = { ...initialState };

  handleTabChange = (event, tabValue) => {
    this.setState({ tabValue });
  };

  async componentDidMount() {
    const pollId = this.props.match.params.pollId;
    if (pollId) {
      const foundPoll = await PollActions.doGetPollById(pollId);
      if (foundPoll) {
        this.setState({ poll: foundPoll.data, loadingPoll: false });
      }
      const comments = await PollActions.doGetPollComments(pollId);
      if (comments) {
        this.setState({
          comments: comments.data.content,
          loadingComments: false
        });
      }
      const results = await PollActions.doGetPollResults(pollId);
      console.log(results);
      if (results) {
        this.setState({
          results: results.data,
          loadingResults: false
        });
      }
    }
  }

  render() {
    const { classes } = this.props;
    const { tabValue } = this.state;
    return (
      !CommonUtils.isEmptyObj(this.state.poll) && (
        <React.Fragment>
          <PollSummary
            poll={this.state.poll}
            handleVote={this.props.handleVote}
            onDetails={true}
            commentsNumber={this.state.comments.length}
          />
          <Paper className={classes.pollDetailPaper}>
            <Tabs
              value={tabValue}
              indicatorColor="primary"
              textColor="primary"
              onChange={this.handleTabChange}
            >
              <Tab label="Comments" />
              <Tab label="Results" />
            </Tabs>
            {tabValue === 0 && (
              <TabContainer>
                {!this.state.loadingComments && (
                  <Comments comments={this.state.comments} />
                )}
              </TabContainer>
            )}
            {tabValue === 1 && (
              <TabContainer>
                {!this.state.loadingResults && (
                  <Results results={this.state.results} />
                )}
              </TabContainer>
            )}
          </Paper>
        </React.Fragment>
      )
    );
  }
}
const styles = theme => ({
  pollDetailPaper: {
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
  },
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
