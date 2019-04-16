import React from "react";
import { withStyles } from "@material-ui/core";
import List from "@material-ui/core/List";
import Fab from "@material-ui/core/Fab";
import AddIcon from "@material-ui/icons/Add";
import axios from "axios";
import Poll from "./Poll";

import { config } from "../../config";

const initialState = {
  loading: false,
  polls: []
};

class MyDecisions extends React.Component {
  state = { ...initialState };

  getOpenPolls = async () => {
    const token = sessionStorage.getItem("jwtToken");
    const url = config.baseUrl + "api/poll/open";
    const auth = {
      headers: { Authorization: "Bearer " + token }
    };

    return axios.get(url, auth);
  };

  async componentDidMount() {
    this.setState({ loading: true });
    const { data: openPolls } = await this.getOpenPolls();
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
        <Fab color="secondary" aria-label="Add" className={classes.fab}>
          <AddIcon />
        </Fab>
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
