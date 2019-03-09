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

import axios from "axios";

import { config } from "../../config";

const initialState = {
  loading: false,
  user: {}
};

class UserInfoTable extends React.Component {
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
        <Typography variant="h5" component="h3">
          User Settings
        </Typography>
        <Paper className={classes.root}>
          <form className={classes.form} onSubmit={this.handleSubmit}>
            <Typography variant="h5" component="h3">
              Name
            </Typography>
            <FormControl margin="normal" required fullWidth>
              <Input
                id="name"
                name="name"
                value={user.name}
                onChange={this.handleChange}
                autoFocus
              />
            </FormControl>
            <Typography variant="h5" component="h3">
              Last Name
            </Typography>
            <FormControl margin="normal" required fullWidth>
              <Input
                id="lastName"
                name="lastName"
                value={user.lastName}
                onChange={this.handleChange}
                autoFocus
              />
            </FormControl>
            <Typography variant="h5" component="h3">
              Nickname
            </Typography>
            <FormControl margin="normal" required fullWidth>
              <Input
                id="nick"
                name="nick"
                value={user.nickname}
                onChange={this.handleChange}
                autoComplete="nick"
                autoFocus
              />
            </FormControl>
            <Typography
              className={classes.formlabel}
              variant="h5"
              component="h3"
            >
              Email
            </Typography>
            <FormControl margin="normal" required fullWidth>
              <Input
                id="email"
                name="email"
                value={user.email}
                onChange={this.handleChange}
                autoComplete="email"
                autoFocus
              />
            </FormControl>
            <Button
              type="submit"
              variant="contained"
              color="secondary"
              className={classes.submit}
            >
              Save Information
            </Button>
          </form>
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
  formlabel: {}
});

UserInfoTable.propTypes = {
  classes: PropTypes.object.isRequired
};

export default withStyles(styles)(UserInfoTable);
