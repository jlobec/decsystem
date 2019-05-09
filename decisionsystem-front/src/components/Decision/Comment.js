import React from "react";
import { withStyles } from "@material-ui/core";
import ListItemSecondaryAction from "@material-ui/core/ListItemSecondaryAction";
import ListItemText from "@material-ui/core/ListItemText";
import ListItemAvatar from "@material-ui/core/ListItemAvatar";
import Avatar from "@material-ui/core/Avatar";
import IconButton from "@material-ui/core/IconButton";
import ReplyIcon from "@material-ui/icons/Reply";
import Tooltip from "@material-ui/core/Tooltip";

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
        <Tooltip title="Reply" aria-label="Reply">
          <IconButton>
            <ReplyIcon />
          </IconButton>
        </Tooltip>
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
