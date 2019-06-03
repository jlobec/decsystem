import React from "react";
import { withStyles, Typography } from "@material-ui/core";
import ListItemSecondaryAction from "@material-ui/core/ListItemSecondaryAction";
import ListItemText from "@material-ui/core/ListItemText";
import ListItemAvatar from "@material-ui/core/ListItemAvatar";
import Avatar from "@material-ui/core/Avatar";
import IconButton from "@material-ui/core/IconButton";
import ReplyIcon from "@material-ui/icons/Reply";
import DeleteIcon from "@material-ui/icons/Delete";
import ThumbUpIcon from "@material-ui/icons/ThumbUpAltTwoTone";
import ThumbUpOutlinedIcon from "@material-ui/icons/ThumbUpOutlined";
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

  handleRemoveComment = () => {
    this.props.handleRemove(this.props.comment);
  };

  renderRemovedComment = () => {
    return (
      <React.Fragment>
        <ListItemAvatar>
          <Avatar>{``}</Avatar>
        </ListItemAvatar>
        <ListItemText
          primary={` `}
          secondary={` This comment has been removed. `}
        />
        <ListItemSecondaryAction />
      </React.Fragment>
    );
  };

  render() {
    const { classes, comment, isRemovable } = this.props;
    const user = comment.user;
    if (comment.removed) {
      return this.renderRemovedComment();
    }
    const reactionsNumber = comment.reactions.length;
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

        {isRemovable && (
          <Tooltip title="Delete" aria-label="Delete">
            <IconButton onClick={this.handleRemoveComment}>
              <DeleteIcon />
            </IconButton>
          </Tooltip>
        )}
        {/* <Tooltip title="Reply" aria-label="Reply">
          <IconButton>
            <ReplyIcon />
          </IconButton>
        </Tooltip> */}

        {/* Reactions */}
        {!comment.reactedByUser && (
          <Tooltip title="Like" aria-label="Like">
            <IconButton className={classes.likeButton}>
              <ThumbUpOutlinedIcon />
            </IconButton>
          </Tooltip>
        )}
        {comment.reactedByUser && (
          <IconButton className={classes.likeButton}>
            <ThumbUpIcon className={classes.likedButton} />
          </IconButton>
        )}
        <Typography variant="overline" display="block">
          {reactionsNumber}
        </Typography>
      </React.Fragment>
    );
  }
}

const styles = theme => ({
  likedButton: {
    color: "rgb(66, 140, 244)"
  },
  likeButton: {
    paddingRight: "4px"
  }
});

export default withStyles(styles)(Comment);
