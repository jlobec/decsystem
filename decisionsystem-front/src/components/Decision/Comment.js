import React from "react";
import { withStyles } from "@material-ui/core";
import ListItem from "@material-ui/core/ListItem";
import ListItemSecondaryAction from "@material-ui/core/ListItemSecondaryAction";
import ListItemText from "@material-ui/core/ListItemText";
import Checkbox from "@material-ui/core/Checkbox";
import IconButton from "@material-ui/core/IconButton";
import CommentIcon from "@material-ui/icons/Comment";
import axios from "axios";

import { config } from "../../config";

const initialState = {
  checked: []
};

class Comment extends React.Component {
  state = { ...initialState };

  async componentDidMount() {}

  componentWillUnmount() {
    this.setState({ ...initialState });
  }

  render() {
    const { classes, comment } = this.props;
    return (
      <ListItem
        role={undefined}
        dense
        button
        // onClick={this.handleToggle(pollOption.pollOptionId)}
      >
        <ListItemText primary={`${comment.content} `} />
        {/* <ListItemText secondary={`${pollOption.description}`} /> */}
        {/* <ListItemSecondaryAction>
            <IconButton aria-label="Comments">
              <CommentIcon />
            </IconButton>
          </ListItemSecondaryAction> */}
      </ListItem>
    );
  }
}

const styles = theme => ({});

export default withStyles(styles)(Comment);
