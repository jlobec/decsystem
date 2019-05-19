import AuthUtil from "../auth/AuthUtil";
import axios from "axios";
import { config } from "../../config";

class PollActions {
  static doGetPollTypes = async () => {
    const url = config.baseUrl + "api/pollsystem";
    return axios.get(url, AuthUtil.getHeaders());
  };

  static doGetOpenPolls = async (pollStatusId, pollTypeId) => {
    let url = config.baseUrl + "api/poll/open";
    if (pollStatusId && pollTypeId) {
      url += `?pollStatusId=${pollStatusId}&pollTypeId=${pollTypeId}`;
    } else if (pollStatusId) {
      url += `?pollStatusId=${pollStatusId}`;
    } else if (pollTypeId) {
      url += `?pollTypeId=${pollTypeId}`;
    }
    return axios.get(url, AuthUtil.getHeaders());
  };

  static doGetPollById = async pollId => {
    const url = config.baseUrl + "api/poll/" + pollId;
    return axios.get(url, AuthUtil.getHeaders());
  };

  static doGetPollComments = async pollId => {
    const url = `${config.baseUrl}api/poll/${pollId}/comments`;
    return axios.get(url, AuthUtil.getHeaders());
  };

  static doGetPollResults = async pollId => {
    const url = `${config.baseUrl}api/poll/${pollId}/results`;
    return axios.get(url, AuthUtil.getHeaders());
  };

  static doGetAllPollStatus = async () => {
    const url = `${config.baseUrl}api/poll/status`;
    return axios.get(url, AuthUtil.getHeaders());
  };

  static doGetAllPollResultsVisibilityOptions = async () => {
    const url = `${config.baseUrl}api/poll/resultsvisibility`;
    return axios.get(url, AuthUtil.getHeaders());
  };

  static doExportPollResults = async pollId => {
    const url = `${config.baseUrl}api/poll/${pollId}/results/export?format=csv`;
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
      resultsVisibilityId: poll.resultsVisibilityId,
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
    console.log("doVote");
    console.log(requestBody);
    return axios.post(url, requestBody, AuthUtil.getHeaders());
  };
}

export default PollActions;
