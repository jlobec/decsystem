import React from "react";
import { withStyles } from "@material-ui/core";
import List from "@material-ui/core/List";
import ListItem from "@material-ui/core/ListItem";
import Typography from "@material-ui/core/Typography";
import Comment from "./Comment";
import AddComment from "./AddComment";

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
    const commentList = comments.map(comment => {
      const isRemovable = this.props.loggedUser.userId === comment.user.userId;
      return (
        <ListItem className={classes.comment} key={`${comment.commentId}}`}>
          <Comment
            comment={comment}
            handleReply={this.replyComment}
            handleRemove={this.props.removeComment}
            isRemovable={isRemovable}
          />
        </ListItem>
      );
    });
    return (
      <React.Fragment>
        <Typography className={classes.commentsTitle} variant="h6" gutterBottom>
          {`${comments.length} Comments`}
        </Typography>
        <AddComment addComment={this.props.addComment} />
        <List>{commentList}</List>
      </React.Fragment>
    );
  }
}

const styles = theme => ({
  comment: {},
  card: {
    // minWidth: 275,
    // [theme.breakpoints.down("sm")]: {
    //   marginBottom: theme.spacing.unit,
    //   width: "100%"
    // },
    // [theme.breakpoints.up("md")]: {
    //   marginBottom: theme.spacing.unit,
    //   width: "80%",
    //   marginRigth: "10%",
    //   marginLeft: "10%"
    // },
    // [theme.breakpoints.up("lg")]: {
    //   marginBottom: theme.spacing.unit,
    //   width: "80%",
    //   marginRigth: "10%",
    //   marginLeft: "10%"
    // }
  }
});

export default withStyles(styles)(Comments);
