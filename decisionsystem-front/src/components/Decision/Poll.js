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
import CustomizedSnackbar from "../common/CustomizedSnackbar";
import CommentActions from "../../actions/poll/CommentActions";
import CommonUtils from "../../actions/util/CommonUtils";
import UserActions from "../../actions/user/UserActions";

function TabContainer(props) {
  return (
    <Typography component="div" style={{ padding: 8 * 3 }}>
      {props.children}
    </Typography>
  );
}

class Poll extends React.Component {
  state = {
    loadingPoll: true,
    loadingComments: true,
    loadingResults: true,
    tabValue: 0,
    poll: {},
    comments: [],
    results: [],
    user: this.props.user
  };

  constructor(props) {
    super(props);
    this.snack = React.createRef();
  }

  handleTabChange = (event, tabValue) => {
    this.setState({ tabValue });
  };

  async componentDidMount() {
    if (CommonUtils.isEmptyObj(this.state.user)) {
      const { data: loggedUser } = await UserActions.doGetLoggedUser();
      if (loggedUser) {
        this.setState({ user: loggedUser });
      }
    }
    const pollId = this.props.match.params.pollId;
    if (pollId) {
      const { data: foundPoll } = await PollActions.doGetPollById(pollId);
      if (foundPoll) {
        this.setState({ poll: foundPoll, loadingPoll: false });
      }
      const { data: comments } = await PollActions.doGetPollComments(pollId);
      if (comments) {
        this.setState({
          comments: comments.content,
          loadingComments: false
        });
      }
      const { data: results } = await PollActions.doGetPollResults(pollId);
      // console.log(results);
      if (results) {
        this.setState({
          results: results,
          loadingResults: false
        });
      }
    }
  }

  componentWillUnmount() {
    this.setState({
      loadingPoll: true,
      loadingComments: true,
      loadingResults: true,
      tabValue: 0,
      poll: {},
      comments: [],
      results: [],
      user: this.props.user
    });
  }

  addComment = async comment => {
    const { user, poll } = this.state;
    const { data: savedComment } = await CommentActions.doAddComment(
      user.userId,
      poll.pollId,
      comment.content
    );
    if (savedComment) {
      this.setState({
        comments: [...this.state.comments, savedComment]
      });
    }
    this.handleShowSnackbarForAddComment(savedComment);
  };

  removeComment = async comment => {
    const commentId = comment.commentId;
    const { data: removedComment } = await CommentActions.doRemoveComment(
      commentId
    );

    if (removedComment) {
      const newComments = [...this.state.comments];
      const foundIndex = newComments.findIndex(
        comment => comment.commentId === commentId
      );
      newComments[foundIndex] = removedComment;
      this.setState({
        comments: newComments
      });
    }
    this.handleShowSnackbarForRemoveComment(removedComment);
  };

  handleShowSnackbarForAddComment = successful => {
    const okMessage = {
      open: true,
      variant: "success",
      message: `Comment added successfully`
    };
    const errorMessage = {
      open: true,
      variant: "error",
      message: `Comment could not be added`
    };
    const messageToShow = successful ? okMessage : errorMessage;
    this.snack.openWith(messageToShow);
  };

  handleShowSnackbarForRemoveComment = successful => {
    const okMessage = {
      open: true,
      variant: "success",
      message: `Comment removed successfully`
    };
    const errorMessage = {
      open: true,
      variant: "error",
      message: `Comment could not be removed`
    };
    const messageToShow = successful ? okMessage : errorMessage;
    this.snack.openWith(messageToShow);
  };

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
                  <Comments
                    poll={this.state.poll}
                    loggedUser={this.state.user}
                    comments={this.state.comments}
                    addComment={this.addComment}
                    removeComment={this.removeComment}
                  />
                )}
              </TabContainer>
            )}
            {tabValue === 1 && (
              <TabContainer>
                {!this.state.loadingResults && (
                  <Results
                    poll={this.state.poll}
                    results={this.state.results}
                  />
                )}
              </TabContainer>
            )}
          </Paper>
          <CustomizedSnackbar innerRef={ref => (this.snack = ref)} />
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
