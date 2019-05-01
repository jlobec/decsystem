import React from "react";
import { withStyles } from "@material-ui/core";
import TableResults from "./TableResults";

const initialState = {};

class Results extends React.Component {
  state = { ...initialState };

  async componentDidMount() {}

  componentWillUnmount() {
    this.setState({ ...initialState });
  }

  render() {
    const { classes, results } = this.props;
    return (
      <React.Fragment>
        <TableResults results={results} />
      </React.Fragment>
    );
  }
}

const styles = theme => ({});

export default withStyles(styles)(Results);
