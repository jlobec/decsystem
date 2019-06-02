import React from "react";
import { withStyles } from "@material-ui/core";
import ListItem from "@material-ui/core/ListItem";
import ListItemText from "@material-ui/core/ListItemText";
import Button from "@material-ui/core/Button";
import Paper from "@material-ui/core/Paper";
import ListItemSecondaryAction from "@material-ui/core/ListItemSecondaryAction";

class Notification extends React.Component {
  handleDismissNotification = () => {
    const { notification } = this.props;
    this.props.dismissNotification(notification.notificationId);
  };

  render() {
    const { classes, notification } = this.props;
    return (
      <React.Fragment>
        <Paper className={classes.notification}>
          <ListItem key={`${notification.notificationId}}`}>
            <ListItemText primary={` ${notification.content}`} />
            {/* <ListItemSecondaryAction> */}
            <Button
              className={classes.dismissButton}
              variant="outlined"
              color="primary"
              onClick={this.handleDismissNotification}
            >
              Dismiss
            </Button>
          </ListItem>
        </Paper>
      </React.Fragment>
    );
  }
}

const styles = theme => ({
  notification: {
    minWidth: 275,
    minHeight: 70,
    [theme.breakpoints.down("sm")]: {
      marginBottom: theme.spacing.unit,
      width: "100%"
    },
    [theme.breakpoints.up("md")]: {
      marginBottom: theme.spacing.unit * 2,
      width: "80%",
      marginRigth: "10%",
      marginLeft: "10%"
    },
    [theme.breakpoints.up("lg")]: {
      marginBottom: theme.spacing.unit * 2,
      width: "80%",
      marginRigth: "10%",
      marginLeft: "10%"
    }
  },
  dismissButton: {
    margin: theme.spacing.unit
  }
});

export default withStyles(styles)(Notification);
