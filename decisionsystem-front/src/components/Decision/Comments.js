import React from "react";
import { withStyles } from "@material-ui/core";
import List from "@material-ui/core/List";
import ListItem from "@material-ui/core/ListItem";
import ListItemSecondaryAction from "@material-ui/core/ListItemSecondaryAction";
import ListItemText from "@material-ui/core/ListItemText";
import Checkbox from "@material-ui/core/Checkbox";
import IconButton from "@material-ui/core/IconButton";
import CommentIcon from "@material-ui/icons/Comment";
import axios from "axios";
import Comment from "./Comment";

import { config } from "../../config";
const initialState = {};

class Comments extends React.Component {
  state = { ...initialState };

  async componentDidMount() {}

  componentWillUnmount() {
    this.setState({ ...initialState });
  }

  replyComment = () => {
    // TODO
  };

  render() {
    const { classes, comments } = this.props;
    const commentList = comments.map((comment, index) => {
      return (
        <ListItem key={`${comment.commentId}}`}>
          <List>
            <Comment comment={comment} handleReply={this.replyComment} />
          </List>
        </ListItem>
      );
    });
    return (
      <List>
        {commentList}
        {/* <ListItem
          role={undefined}
          dense
          button
          onClick={this.handleToggle(pollOption.pollOptionId)}
        >
          <Checkbox checked={checked} tabIndex={-1} disableRipple />
          <ListItemText primary={`${pollOption.name} `} />
          <ListItemText secondary={`${pollOption.description}`} />
        </ListItem> */}
      </List>
    );
  }
}

const styles = theme => ({});

export default withStyles(styles)(Comments);
