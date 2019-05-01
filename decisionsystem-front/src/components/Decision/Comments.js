import React from "react";
import { withStyles } from "@material-ui/core";
import List from "@material-ui/core/List";
import ListItem from "@material-ui/core/ListItem";
import ListItemSecondaryAction from "@material-ui/core/ListItemSecondaryAction";
import ListItemText from "@material-ui/core/ListItemText";
import Checkbox from "@material-ui/core/Checkbox";
import IconButton from "@material-ui/core/IconButton";
import CommentIcon from "@material-ui/icons/Comment";
import Typography from "@material-ui/core/Typography";
import axios from "axios";
import Comment from "./Comment";
import Card from "@material-ui/core/Card";

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
        <Card className={classes.card}>
          <ListItem key={`${comment.commentId}}`}>
            <Comment comment={comment} handleReply={this.replyComment} />
          </ListItem>
        </Card>
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

const styles = theme => ({
  commentsTitle: {
    marginTop: "5%",
    marginLeft: "2%",
    [theme.breakpoints.down("sm")]: {
      width: "100%"
    },
    [theme.breakpoints.up("md")]: {
      width: "80%",
      marginRigth: "10%",
      marginLeft: "10%"
    },
    [theme.breakpoints.up("lg")]: {
      width: "80%",
      marginRigth: "10%",
      marginLeft: "10%"
    }
  },
  card: {
    minWidth: 275,
    [theme.breakpoints.down("sm")]: {
      marginBottom: theme.spacing.unit,
      width: "100%"
    },
    [theme.breakpoints.up("md")]: {
      marginBottom: theme.spacing.unit,
      width: "80%",
      marginRigth: "10%",
      marginLeft: "10%"
    },
    [theme.breakpoints.up("lg")]: {
      marginBottom: theme.spacing.unit,
      width: "80%",
      marginRigth: "10%",
      marginLeft: "10%"
    }
  }
});

export default withStyles(styles)(Comments);
