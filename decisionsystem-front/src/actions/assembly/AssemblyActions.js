import AuthUtil from "../auth/AuthUtil";
import axios from "axios";
import { config } from "../../config";

class AssemblyActions {
  static doGetByPollId = async pollId => {
    const url = `${config.baseUrl}api/assembly?pollId=${pollId}`;
    return axios.get(url, AuthUtil.getHeaders());
  };
}

export default AssemblyActions;
