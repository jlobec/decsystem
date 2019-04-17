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

  doSavePoll = async poll => {};

  savePoll = async poll => {
    // TODO
    console.log(poll);

    let successful = false;
    const { data: userFound } = await this.getUser(this.state.usernameOrEmail);
    console.log("find user result");
    console.log(userFound);
    if (userFound) {
      // Add user
      const { data: addUserResponse } = await this.addUser(
        this.props.assembly,
        userFound
      );
      if (addUserResponse) {
        successful = true;
      }
    }
    this.props.addMember(successful, userFound);
    this.handleClose();

    const snackbarPollAdded = {
      open: true,
      variant: "success",
      message: `Poll '${poll.title}' added successfully`
    };
    this.snack.openWith(snackbarPollAdded);
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
