import AuthUtil from "../auth/AuthUtil";
import axios from "axios";
import { config } from "../../config";

class PollActions {
  static doGetPollTypes = async () => {
    const url = config.baseUrl + "api/pollsystem";
    return axios.get(url, AuthUtil.getHeaders());
  };

  static doGetOpenPolls = async () => {
    const url = config.baseUrl + "api/poll/open";
    return axios.get(url, AuthUtil.getHeaders());
  };

  static doSavePoll = async poll => {
    const url = config.baseUrl + "api/poll";
    const requestBody = {
      title: poll.title,
      description: poll.description,
      startTime: poll.startTime,
      endTime: poll.endTime,
      pollTypeId: poll.pollTypeId,
      assemblyId: poll.assemblyId,
      options: poll.pollOptions
    };
    // return axios.post(url, requestBody, AuthUtil.getHeaders());
  };
}

export default PollActions;
