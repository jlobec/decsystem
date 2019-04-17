import React from "react";
import { withStyles } from "@material-ui/core";
import List from "@material-ui/core/List";
import Fab from "@material-ui/core/Fab";
import AddIcon from "@material-ui/icons/Add";
import Poll from "./Poll";
import AddPoll from "./AddPoll";
import CustomizedSnackbar from "../common/CustomizedSnackbar";
import PollActions from "../../actions/poll/PollActions";

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
    console.log(savePollResult, poll);
    this.handleShowSavePoll(savePollResult);
  };

  handleShowSavePoll = (successful, poll) => {
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

  render() {
    const { classes } = this.props;
    const openPolls = this.state.polls.map((poll, index) => {
      return <Poll key={index} poll={poll} />;
    });
    return (
      <React.Fragment>
        <List>{openPolls}</List>
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
  }
});

export default withStyles(styles)(MyDecisions);
