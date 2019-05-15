import AuthUtil from "../auth/AuthUtil";
import axios from "axios";
import { config } from "../../config";

class UserActions {
  static doGetByUsernameOrEmail = async usernameOrEmail => {
    const url = `${config.baseUrl}api/user?usernameOrEmail=${usernameOrEmail}`;
    return axios.get(url, AuthUtil.getHeaders());
  };

  static doGetLoggedUser = async () => {
    const url = `${config.baseUrl}api/user/me`;
    return axios.get(url, AuthUtil.getHeaders());
  };

  static doLogout = () => {
    AuthUtil.logout();
  };
}

export default UserActions;
