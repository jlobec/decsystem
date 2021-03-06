import AuthUtil from "../auth/AuthUtil";
import axios from "axios";
import { config } from "../../config";

class CommentActions {
  static doAddComment = async (userId, pollId, content) => {
    const url = `${config.baseUrl}api/comment`;
    const requestBody = {
      pollId: pollId,
      userId: userId,
      content: content
    };
    return axios.post(url, requestBody, AuthUtil.getHeaders());
  };

  static doRemoveComment = async commentId => {
    const url = `${config.baseUrl}api/comment/${commentId}`;
    return axios.delete(url, AuthUtil.getHeaders());
  };

  static doLikeComment = async commentId => {
    const url = `${config.baseUrl}api/comment/${commentId}/reaction/add`;
    const requestBody = {
      reactionTypeId: 1
    };
    return axios.post(url, requestBody, AuthUtil.getHeaders());
  };

  static doRemoveLikeComment = async commentId => {
    const url = `${config.baseUrl}api/comment/${commentId}/reaction/remove`;
    const requestBody = {
      reactionTypeId: 1
    };
    return axios.post(url, requestBody, AuthUtil.getHeaders());
  };
}

export default CommentActions;
