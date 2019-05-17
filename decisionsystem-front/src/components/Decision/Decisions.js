import React from "react";
import { withStyles } from "@material-ui/core";
import List from "@material-ui/core/List";
import ListItem from "@material-ui/core/ListItem";
import Fab from "@material-ui/core/Fab";
import AddIcon from "@material-ui/icons/Add";
import PollSummary from "./PollSummary";
import Poll from "./Poll";
import AddPoll from "./AddPoll";
import CustomizedSnackbar from "../common/CustomizedSnackbar";
import PollActions from "../../actions/poll/PollActions";
import UserActions from "../../actions/user/UserActions";
import { Route } from "react-router-dom";
import Tooltip from "@material-ui/core/Tooltip";
import Button from "@material-ui/core/Button";
import Grid from "@material-ui/core/Grid";
import FilterListIcon from "@material-ui/icons/FilterList";
import PollFilters from "./PollFilters";

const initialState = {
  loading: false,
  filterDrawerOpen: false,
  polls: [],
  user: {}
};

class Decisions extends React.Component {
  state = { ...initialState };

  constructor(props) {
    super(props);
    this.addPoll = React.createRef();
    this.snack = React.createRef();
  }

  toggleFilterDrawer = open => () => {
    this.setState({
      filterDrawerOpen: open
    });
  };

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

  votePoll = async (poll, votedOptions) => {
    const options = [...votedOptions].map(option => {
      return {
        optionId: option.pollOptionId,
        preferenceValue: option.value,
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

  handleFilterPolls = async (pollStatusId, pollTypeId) => {
    this.setState({ loading: true });
    const { data: filteredPolls } = await PollActions.doGetOpenPolls(
      pollStatusId,
      pollTypeId
    );
    if (filteredPolls) {
      this.setState({
        loading: false,
        polls: filteredPolls.content,
        filterDrawerOpen: false
      });
    }
  };

  async componentDidMount() {
    this.setState({ loading: true });
    const { data: openPolls } = await PollActions.doGetOpenPolls();
    if (openPolls) {
      this.setState({
        loading: false,
        polls: openPolls.content
      });
      const { data: loggedUser } = await UserActions.doGetLoggedUser();
      if (loggedUser) {
        this.setState({
          user: loggedUser
        });
      }
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
          <PollSummary
            {...routeProps}
            poll={poll}
            handleVote={this.votePoll}
            onDetails={false}
          />
        </ListItem>
      );
    });
  };

  render() {
    const { classes } = this.props;
    return (
      <React.Fragment>
        <Route
          exact
          path="/dashboard/decisions/"
          render={routeProps => (
            <React.Fragment>
              <div className={classes.buttonFiltersContainer}>
                <Grid container justify="flex-end">
                  <Button
                    variant="contained"
                    color="default"
                    onClick={this.toggleFilterDrawer(true)}
                  >
                    Filters
                    <FilterListIcon />
                  </Button>
                </Grid>
              </div>
              <PollFilters
                open={this.state.filterDrawerOpen}
                toggleFilterDrawer={this.toggleFilterDrawer}
                handleFilterPolls={this.handleFilterPolls}
              />
              <List>{this.getOpenPolls(classes, routeProps)}</List>
              <AddPoll
                innerRef={ref => (this.addPoll = ref)}
                savePoll={this.savePoll}
              />
              <Tooltip title="Add Poll" aria-label="Add">
                <Fab
                  color="secondary"
                  aria-label="Add"
                  className={classes.fab}
                  onClick={this.handleShowAddPoll}
                >
                  <AddIcon />
                </Fab>
              </Tooltip>
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
              user={this.state.user}
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
  },
  buttonFiltersContainer: {
    minWidth: 275,
    [theme.breakpoints.down("sm")]: {
      paddingRight: theme.spacing.unit * 2,
      marginBottom: theme.spacing.unit,
      width: "100%"
    },
    [theme.breakpoints.up("md")]: {
      paddingRight: theme.spacing.unit * 2,
      marginBottom: theme.spacing.unit * 2,
      width: "80%",
      marginRigth: "10%",
      marginLeft: "10%"
    },
    [theme.breakpoints.up("lg")]: {
      paddingRight: theme.spacing.unit * 2,
      marginBottom: theme.spacing.unit * 2,
      width: "80%",
      marginRigth: "10%",
      marginLeft: "10%"
    }
  }
});

export default withStyles(styles)(Decisions);
