import React from "react";
import { withStyles } from "@material-ui/core";
import List from "@material-ui/core/List";
import ListItem from "@material-ui/core/ListItem";
import Fab from "@material-ui/core/Fab";
import AddIcon from "@material-ui/icons/Add";
import Poll from "./Poll";
import AddPoll from "./AddPoll";
import CustomizedSnackbar from "../common/CustomizedSnackbar";
import PollActions from "../../actions/poll/PollActions";
import { Route } from "react-router-dom";
import Link from "@material-ui/core/Link";

const initialState = {
  loading: false,
  polls: []
};

class MyDecisions extends React.Component {
  state = { ...initialState };

  constructor(props) {
    super(props);
    this.addPoll = React.createRef();
    this.snack = React.createRef();
  }

  savePoll = async poll => {
    console.log(poll);
    const { data: savePollResult } = await PollActions.doSavePoll(poll);
    console.log("save poll result");
    console.log(savePollResult);
    if (savePollResult) {
      this.setState({
        polls: [...this.state.polls, savePollResult]
      });
    }
    this.handleShowSnackbarForSavePoll(savePollResult, poll);
  };

  votePoll = async (poll, votedOptionsIds) => {
    const options = [...votedOptionsIds].map(optId => {
      return {
        optionId: optId,
        preferenceValue: 0,
        motivation: ""
      };
    });
    const { data: doVoteResponse } = await PollActions.doVote(poll, options);
    if (doVoteResponse) {
      console.log("doVoteResponse");
      console.log(doVoteResponse);
      const { data: updatedPoll } = await PollActions.doGetPollById(
        poll.pollId
      );
      if (updatedPoll) {
        let newPolls = [...this.state.polls];
        let pollIndex = -1;
        newPolls.forEach((poll, idx) => {
          if (poll.pollId === updatedPoll.pollId) {
            pollIndex = idx;
          }
        });
        console.log("index of modified poll");
        newPolls[pollIndex] = updatedPoll;
        console.log(newPolls);
        this.setState({
          polls: newPolls
        });
      }
    }
    this.handleShowSnackbarForVotePoll(doVoteResponse, poll);
  };

  handleShowSnackbarForVotePoll = (successful, poll) => {
    const okMessage = {
      open: true,
      variant: "success",
      message: `Poll '${poll.title}' voted successfully`
    };
    const errorMessage = {
      open: true,
      variant: "error",
      message: `Poll could not be voted`
    };
    const messageToShow = successful ? okMessage : errorMessage;
    this.snack.openWith(messageToShow);
  };

  handleShowSnackbarForSavePoll = (successful, poll) => {
    const okMessage = {
      open: true,
      variant: "success",
      message: `Poll '${poll.title}' added successfully`
    };
    const errorMessage = {
      open: true,
      variant: "error",
      message: `Poll could not be added`
    };
    const messageToShow = successful ? okMessage : errorMessage;
    this.snack.openWith(messageToShow);
  };

  handleShowAddPoll = async () => {
    this.addPoll.handleClickOpen();
  };

  async componentDidMount() {
    this.setState({ loading: true });
    const { data: openPolls } = await PollActions.doGetOpenPolls();
    if (openPolls) {
      this.setState({
        loading: false,
        polls: openPolls.content
      });
    }
  }

  componentWillUnmount() {
    this.setState({ ...initialState });
  }

  getOpenPolls = (classes, routeProps) => {
    return this.state.polls.map((poll, index) => {
      return (
        <ListItem
          key={`${index}${new Date().getTime()}`}
          className={classes.pollListItem}
        >
          <Poll
            {...routeProps}
            poll={poll}
            summarize={true}
            handleVote={this.votePoll}
          />
        </ListItem>
      );
    });
  };

  render() {
    const { classes, match } = this.props;
    return (
      <React.Fragment>
        <Route
          exact
          path="/dashboard/decisions/"
          render={routeProps => (
            <React.Fragment>
              <List>{this.getOpenPolls(classes, routeProps)}</List>
              <AddPoll
                innerRef={ref => (this.addPoll = ref)}
                savePoll={this.savePoll}
              />
              <Fab
                color="secondary"
                aria-label="Add"
                className={classes.fab}
                onClick={this.handleShowAddPoll}
              >
                <AddIcon />
              </Fab>
              <CustomizedSnackbar innerRef={ref => (this.snack = ref)} />
            </React.Fragment>
          )}
        />
        <Route
          exact
          path="/dashboard/decisions/:pollId"
          render={routeProps => (
            <Poll
              {...routeProps}
              summarize={false}
              handleVote={this.votePoll}
            />
          )}
        />
      </React.Fragment>
    );
  }
}

const styles = theme => ({
  fab: {
    position: "absolute",
    [theme.breakpoints.down("sm")]: {
      bottom: theme.spacing.unit * 3,
      right: theme.spacing.unit * 3
    },
    [theme.breakpoints.up("md")]: {
      bottom: theme.spacing.unit * 3,
      right: theme.spacing.unit * 3
    },
    [theme.breakpoints.up("lg")]: {
      bottom: theme.spacing.unit * 6,
      right: theme.spacing.unit * 6
    }
  },
  pollListItem: {
    alignItems: "center"
  }
});

export default withStyles(styles)(MyDecisions);
