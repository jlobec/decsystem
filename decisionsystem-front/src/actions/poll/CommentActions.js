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
}

export default CommentActions;