import React from "react";
import { withStyles } from "@material-ui/core";
import NotificationActions from "../../actions/notification/NotificationActions";
import UserActions from "../../actions/user/UserActions";

class NotificationListener extends React.Component {
  fetchNotifications = async () => {
    const { data: loggedUser } = await UserActions.doGetLoggedUser();

    const {
      data: notifications
    } = await NotificationActions.doGetUnseenNotifications(loggedUser.userId);

    if (notifications) {
      for (var index = 0; index < notifications.length; index++) {
        this.createNotification(notifications[index].content);
        this.markNotificationAsShown(notifications[index].notificationId);
      }
    }
  };

  createNotification = title => {
    var options = {
      body: ``
    };
    var n = new Notification(title, options);
    setTimeout(n.close.bind(n), 5000);
  };

  markNotificationAsShown = async notificationId => {
    const {
      data: result
    } = await NotificationActions.doMarkNotificationAsShown(notificationId);
    if (result) {
      // TODO OK Jose Luis
    }
  };

  componentDidMount() {
    this.fetchNotifications();
    this.timer = setInterval(() => this.fetchNotifications(), 5000);
  }

  componentWillUnmount() {
    clearInterval(this.timer);
    this.timer = null;
  }

  render() {
    return <div />;
  }
}

const styles = theme => ({});

export default withStyles(styles)(NotificationListener);
