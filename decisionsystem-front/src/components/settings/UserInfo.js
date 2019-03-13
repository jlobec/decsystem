import React from "react";
import PropTypes from "prop-types";
import { withStyles } from "@material-ui/core/styles";
import Paper from "@material-ui/core/Paper";
import CssBaseline from "@material-ui/core/CssBaseline";
import Button from "@material-ui/core/Button";
import FormControl from "@material-ui/core/FormControl";
import Input from "@material-ui/core/Input";
import InputLabel from "@material-ui/core/InputLabel";
import Typography from "@material-ui/core/Typography";
import TextField from "@material-ui/core/TextField";

import axios from "axios";

import { config } from "../../config";

const initialState = {
  loading: false,
  user: {}
};

class UserInfo extends React.Component {
  state = { ...initialState };

  handleChange = event => {
    const changedUser = { ...this.state.user };
    changedUser[event.currentTarget.name] = event.currentTarget.value;

    this.setState({
      user: changedUser
    });
  };

  getUserInformation = async () => {
    console.log("getting user information");
    const token = sessionStorage.getItem("jwtToken");
    const url = config.baseUrl + "api/user/me";
    const auth = {
      headers: { Authorization: "Bearer " + token }
    };

    return axios.get(url, auth);
  };

  async componentDidMount() {
    this.setState({ loading: true });
    const { data: user } = await this.getUserInformation();
    if (user) {
      console.log(user);
      this.setState({
        loading: false,
        user: user
      });
    }
  }

  render() {
    const { classes } = this.props;
    const user = this.state.user;
    return (
      <React.Fragment>
        <CssBaseline />

        <Paper className={classes.root}>
          <Typography className={classes.formlabel} variant="h5" component="h3">
            User information
          </Typography>
          <TextField
            margin="normal"
            required
            fullWidth
            id="outlined-name"
            label="Name"
            className={classes.textField}
            value={user.name}
            onChange={this.handleChange}
            variant="outlined"
          />
          <TextField
            margin="normal"
            required
            fullWidth
            id="outlined-name"
            label="Last name"
            className={classes.textField}
            value={user.lastName}
            onChange={this.handleChange}
            variant="outlined"
          />
          <TextField
            margin="normal"
            required
            fullWidth
            id="outlined-name"
            label="Nick"
            className={classes.textField}
            value={user.nickname}
            onChange={this.handleChange}
            variant="outlined"
          />
          <TextField
            margin="normal"
            required
            fullWidth
            id="outlined-name"
            label="Email"
            className={classes.textField}
            value={user.email}
            onChange={this.handleChange}
            variant="outlined"
          />
          <Button
            type="submit"
            variant="contained"
            color="secondary"
            className={classes.submit}
          >
            Save Information
          </Button>
        </Paper>
      </React.Fragment>
    );
  }
}

const styles = theme => ({
  root: {
    padding: theme.spacing.unit * 3,
    width: "100%",
    overflowX: "auto"
  },
  formlabel: {},
  textField: {
    marginLeft: theme.spacing.unit,
    marginRight: theme.spacing.unit
  }
});

UserInfo.propTypes = {
  classes: PropTypes.object.isRequired
};

export default withStyles(styles)(UserInfo);
