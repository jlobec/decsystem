import AuthUtil from "../auth/AuthUtil";
import axios from "axios";
import { config } from "../../config";

class NotificationActions {
  static doGetNotSentNotifications = async userId => {
    const url = `${
      config.baseUrl
    }api/notification?userId=${userId}&notSentToUser=true`;
    return axios.get(url, AuthUtil.getHeaders());
  };

  static doGetPendingNotifications = async userId => {
    const url = `${
      config.baseUrl
    }api/notification?userId=${userId}&notCheckedByUser=true`;
    return axios.get(url, AuthUtil.getHeaders());
  };

  static doMarkNotificationAsSent = async notificationId => {
    const url = `${config.baseUrl}api/notification/${notificationId}/sent`;
    return axios.post(url, {}, AuthUtil.getHeaders());
  };

  static doMarkNotificationAsChecked = async notificationId => {
    const url = `${config.baseUrl}api/notification/${notificationId}/checked`;
    return axios.post(url, {}, AuthUtil.getHeaders());
  };
}

export default NotificationActions;
