import React from "react";
import { withStyles } from "@material-ui/core";
import List from "@material-ui/core/List";
import ListItem from "@material-ui/core/ListItem";

import Notification from "./Notification";
import NotificationActions from "../../actions/notification/NotificationActions";
import UserActions from "../../actions/user/UserActions";

class Notifications extends React.Component {
  state = {
    notifications: []
  };

  async componentDidMount() {
    this.getPendingNotifications();
  }

  getPendingNotifications = async () => {
    const { data: loggedUser } = await UserActions.doGetLoggedUser();
    if (loggedUser) {
      const {
        data: notifications
      } = await NotificationActions.doGetPendingNotifications(
        loggedUser.userId
      );
      if (notifications) {
        this.setState({ notifications: notifications });
      }
    }
  };

  dismissNotification = async notificationId => {
    const result = await NotificationActions.doMarkNotificationAsChecked(
      notificationId
    );
    if (result) {
      const newNotificationList = [...this.state.notifications].filter(
        notification => {
          return notification.notificationId !== notificationId;
        }
      );

      this.setState({ notifications: newNotificationList });
    }
  };

  buildNotifications = classes => {
    return this.state.notifications.map((notification, index) => {
      return (
        <Notification
          key={notification.notificationId}
          notification={notification}
          dismissNotification={this.dismissNotification}
        />
      );
    });
  };

  render() {
    const { classes } = this.props;
    return (
      <List className={classes.notificationsContainer}>
        {this.buildNotifications(classes)}
      </List>
    );
  }
}

const styles = theme => ({
  notificationListItem: {
    alignItems: "center",
    width: "100%"
  },
  notificationsContainer: {
    width: "100%"
  }
});

export default withStyles(styles)(Notifications);
