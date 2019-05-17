import React from "react";
import { withStyles } from "@material-ui/core";
import Table from "@material-ui/core/Table";
import TableBody from "@material-ui/core/TableBody";
import TableCell from "@material-ui/core/TableCell";
import TableHead from "@material-ui/core/TableHead";
import TableRow from "@material-ui/core/TableRow";

const initialState = {};

class TableResults extends React.Component {
  state = { ...initialState };

  async componentDidMount() {}

  componentWillUnmount() {
    this.setState({ ...initialState });
  }

  buildHead = poll => {
    if (
      "Single Option" === poll.pollSystem.name ||
      "Multiple Option" === poll.pollSystem.name
    ) {
      return this.buildHeadForChoiceSystem();
    }
    if ("Score vote" === poll.pollSystem.name) {
      return this.buildHeadForScoreSystem();
    }
  };

  buildResultRows = (poll, results) => {
    if (
      "Single Option" === poll.pollSystem.name ||
      "Multiple Option" === poll.pollSystem.name
    ) {
      return this.buildResultRowsForChoiceSystem(results);
    }
    if ("Score vote" === poll.pollSystem.name) {
      return this.buildResultRowsForScoreSystem(results);
    }
  };

  buildHeadForChoiceSystem = () => {
    return (
      <TableRow>
        <TableCell>User</TableCell>
        <TableCell>Option voted</TableCell>
      </TableRow>
    );
  };

  buildHeadForScoreSystem = () => {
    return (
      <TableRow>
        <TableCell>User</TableCell>
        <TableCell>Option</TableCell>
        <TableCell>Score</TableCell>
      </TableRow>
    );
  };

  buildResultRowsForChoiceSystem = results => {
    const resultRows = results.map((result, index) => {
      const optionRow = <TableCell>{`${result.option.name}`}</TableCell>;
      const rows = result.items.map(item => {
        return (
          <TableRow key={`${index}${new Date()}`}>
            <TableCell component="th" scope="row">{`${item.user.name} ${
              item.user.lastName
            }`}</TableCell>
            {optionRow}
          </TableRow>
        );
      });
      return rows;
    });
    return resultRows;
  };

  buildResultRowsForScoreSystem = results => {
    const resultRows = results.map((result, index) => {
      const optionRow = <TableCell>{`${result.option.name}`}</TableCell>;
      const rows = result.items.map(item => {
        return (
          <TableRow key={`${index}${new Date()}`}>
            <TableCell component="th" scope="row">{`${item.user.name} ${
              item.user.lastName
            }`}</TableCell>
            {optionRow}
            <TableCell>{`${item.score}`}</TableCell>
          </TableRow>
        );
      });
      return rows;
    });
    return resultRows;
  };

  render() {
    const { classes, poll, results } = this.props;
    const header = this.buildHead(poll);
    const resultRows = this.buildResultRows(poll, results);
    return (
      <React.Fragment>
        <Table className={classes.table}>
          <TableHead>{header}</TableHead>
          <TableBody>{resultRows}</TableBody>
        </Table>
      </React.Fragment>
    );
  }
}

const styles = theme => ({});

export default withStyles(styles)(TableResults);
