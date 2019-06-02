import AuthUtil from "../auth/AuthUtil";
import axios from "axios";
import { config } from "../../config";

class NotificationActions {
  static doGetUnseenNotifications = async userId => {
    const url = `${config.baseUrl}api/notification?userId=${userId}`;
    return axios.get(url, AuthUtil.getHeaders());
  };

  static doMarkNotificationAsShown = async notificationId => {
    const url = `${config.baseUrl}api/notification/${notificationId}/shown`;
    return axios.post(url, {}, AuthUtil.getHeaders());
  };
}

export default NotificationActions;
