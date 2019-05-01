import React from "react";
import { withStyles } from "@material-ui/core";
import ListItem from "@material-ui/core/ListItem";
import ListItemSecondaryAction from "@material-ui/core/ListItemSecondaryAction";
import ListItemText from "@material-ui/core/ListItemText";
import ListItemAvatar from "@material-ui/core/ListItemAvatar";
import Avatar from "@material-ui/core/Avatar";
import Typography from "@material-ui/core/Typography";
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
    const user = comment.user;
    return (
      <React.Fragment>
        <ListItemAvatar>
          <Avatar>{`${user.name.charAt(0).toUpperCase()}${user.lastName
            .charAt(0)
            .toUpperCase()}`}</Avatar>
        </ListItemAvatar>
        <ListItemText
          primary={`${user.name} ${user.lastName} `}
          secondary={`${comment.content} `}
        />

        <ListItemSecondaryAction>
          {/* <IconButton
              aria-label="Delete"
              onClick={() => {
                this.handleRemoveMember(assembly, member);
              }}
            >
              <DeleteIcon />
            </IconButton> */}
        </ListItemSecondaryAction>
        <Typography
          component="h1"
          variant="body1"
          className={classes.commentAction}
        >
          Reply
        </Typography>
      </React.Fragment>
    );
  }
}

const styles = theme => ({
  commentAction: {
    // marginRight: "90%"
  }
});

export default withStyles(styles)(Comment);
