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

  static doGetPollById = async pollId => {
    const url = config.baseUrl + "api/poll/" + pollId;
    return axios.get(url, AuthUtil.getHeaders());
  };

  static doGetPollComments = async pollId => {
    const url = `${config.baseUrl}api/poll/${pollId}/comments`;
    // const url = config.baseUrl + "api/poll/" + pollId + "/comments";
    return axios.get(url, AuthUtil.getHeaders());
  };

  static doSavePoll = async poll => {
    const url = config.baseUrl + "api/poll";
    const requestBody = {
      title: poll.title,
      description: poll.description,
      startTime: poll.startTime,
      endTime: poll.endTime,
      pollSystemId: poll.pollTypeId,
      assemblyId: poll.assemblyId,
      pollOptions: poll.pollOptions
    };
    return axios.post(url, requestBody, AuthUtil.getHeaders());
  };

  static doVote = async (poll, options) => {
    const url = config.baseUrl + "api/vote";
    const requestBodyOptions = [...options].map(opt => {
      return {
        optionId: opt.optionId,
        preferenceValue: opt.preferenceValue,
        motivation: opt.motivation
      };
    });
    const requestBody = {
      options: requestBodyOptions,
      pollId: poll.pollId
    };
    return axios.post(url, requestBody, AuthUtil.getHeaders());
  };
}

export default PollActions;
