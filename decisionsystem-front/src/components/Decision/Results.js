import React from "react";
import { withStyles } from "@material-ui/core";

const initialState = {};

class Results extends React.Component {
  state = { ...initialState };

  async componentDidMount() {}

  componentWillUnmount() {
    this.setState({ ...initialState });
  }

  render() {
    const { classes, comments } = this.props;
    const commentList = comments.map((comment, index) => {
      return (
        <p>Item</p>
        // <ListItem className={classes.comment} key={`${comment.commentId}}`}>
        //   <Comment comment={comment} handleReply={this.replyComment} />
        // </ListItem>
      );
    });
    return (
      <React.Fragment>
        <Typography className={classes.commentsTitle} variant="h6" gutterBottom>
          {`${comments.length} Comments`}
        </Typography>
        <List>{commentList}</List>
      </React.Fragment>
    );
  }
}

const styles = theme => ({});

export default withStyles(styles)(Results);
