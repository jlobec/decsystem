import React from "react";
import { withStyles } from "@material-ui/core";
import axios from "axios";
import Poll from "../dashboard/Poll";

import { config } from "../../config";

const initialState = {
  loading: false,
  polls: []
};

class MyDecisions extends React.Component {
  state = { ...initialState };

  getOpenPolls = async () => {
    const token = sessionStorage.getItem("jwtToken");
    const url = config.baseUrl + "api/poll/open";
    const auth = {
      headers: { Authorization: "Bearer " + token }
    };

    return axios.get(url, auth);
  };

  async componentDidMount() {
    this.setState({ loading: true });
    const { data: openPolls } = await this.getOpenPolls();
    if (openPolls) {
      console.log(openPolls);
      this.setState({
        loading: false,
        polls: openPolls.content
      });
    }
  }

  componentWillUnmount() {
    this.setState({ ...initialState });
  }

  render() {
    const openPolls = this.state.polls.map((poll, index) => {
      return <Poll key={index} pollInfo={poll} />;
    });
    return (
      <div>
        <p>My open decisions: </p>
        {openPolls}
      </div>
    );
  }
}

const styles = theme => ({});

export default withStyles(styles)(MyDecisions);
