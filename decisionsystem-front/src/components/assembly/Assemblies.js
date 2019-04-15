import React from "react";
import { withStyles } from "@material-ui/core";
import CssBaseline from "@material-ui/core/CssBaseline";
import axios from "axios";
import Assembly from "./Assembly";

import { config } from "../../config";

const initialState = {
  loading: false,
  assemblies: []
};

class Assemblies extends React.Component {
  state = { ...initialState };

  getAssemblies = async () => {
    const token = sessionStorage.getItem("jwtToken");
    const url = config.baseUrl + "api/assembly/mine";
    const auth = {
      headers: { Authorization: "Bearer " + token }
    };

    return axios.get(url, auth);
  };

  async componentDidMount() {
    this.setState({ loading: true });
    const { data: assemblies } = await this.getAssemblies();
    if (assemblies) {
      // console.log(assemblies);
      this.setState({
        loading: false,
        assemblies: assemblies.content
      });
    }
  }

  componentWillUnmount() {
    this.setState({ ...initialState });
  }

  render() {
    const assemblies = this.state.assemblies.map((assembly, index) => {
      return <Assembly key={assembly.assemblyId} assembly={assembly} />;
    });
    return (
      <React.Fragment>
        <div>{assemblies}</div>
      </React.Fragment>
    );
  }
}

const styles = theme => ({});

export default withStyles(styles)(Assemblies);
