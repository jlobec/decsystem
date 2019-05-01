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

  render() {
    const { classes, results } = this.props;
    const resultRows = results.map((result, index) => {
      const optionRow = <TableCell>{`${result.option.name}`}</TableCell>;
      const rows = result.items.map(item => {
        return (
          <TableRow key={`${index}${new Date()}`}>
            {optionRow}
            <TableCell component="th" scope="row">{`${item.user.name} ${
              item.user.lastName
            }`}</TableCell>
            <TableCell>{`${item.motivation}`}</TableCell>
          </TableRow>
        );
      });
      return rows;
    });
    return (
      <React.Fragment>
        <Table className={classes.table}>
          <TableHead>
            <TableRow>
              <TableCell>Option voted</TableCell>
              <TableCell>User</TableCell>
              <TableCell>Motivation</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>{resultRows}</TableBody>
        </Table>
      </React.Fragment>
    );
  }
}

const styles = theme => ({});

export default withStyles(styles)(TableResults);
