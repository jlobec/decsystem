import React from "react";
import { withStyles } from "@material-ui/core";
import UserInfoTable from "./UserInfoTable";
import CssBaseline from "@material-ui/core/CssBaseline";

const initialState = {
  loading: false,
  user: {}
};

class Settings extends React.Component {
  state = { ...initialState };

  componentWillUnmount() {
    this.setState({ ...initialState });
  }

  render() {
    const { classes } = this.props;
    return (
      <React.Fragment>
        <CssBaseline />
        <UserInfoTable />
      </React.Fragment>
    );
  }
}

const styles = theme => ({
  chartContainer: {
    marginLeft: -22
  }
});

export default withStyles(styles)(Settings);
