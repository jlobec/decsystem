import React, { useImperativeHandle } from "react";
import PropTypes from "prop-types";
import { withStyles } from "@material-ui/core/styles";
import Paper from "@material-ui/core/Paper";
import CssBaseline from "@material-ui/core/CssBaseline";
import Button from "@material-ui/core/Button";
import Typography from "@material-ui/core/Typography";
import TextField from "@material-ui/core/TextField";
import Dialog from "@material-ui/core/Dialog";
import DialogActions from "@material-ui/core/DialogActions";
import DialogContent from "@material-ui/core/DialogContent";
import DialogContentText from "@material-ui/core/DialogContentText";
import DialogTitle from "@material-ui/core/DialogTitle";

import axios from "axios";

import { config } from "../../config";

const initialState = {
  loading: false,
  open: false,
  fullscreen: true,
  user: {
    userId: 0,
    name: "",
    lastName: "",
    email: "",
    nickname: ""
  }
};

class UserInfo extends React.Component {
  state = { ...initialState };

  getAuth = () => {
    const token = sessionStorage.getItem("jwtToken");
    const auth = {
      headers: { Authorization: "Bearer " + token }
    };
    return auth;
  };

  handleChange = event => {
    const changedUser = { ...this.state.user };
    changedUser[event.currentTarget.name] = event.currentTarget.value;
    this.setState({
      user: changedUser
    });
  };

  handleDialogOpen = () => {
    this.setState({ open: true });
  };

  handleDialogClose = () => {
    this.setState({ open: false });
  };

  handleDialogConfirm = async () => {
    const userInfo = { ...this.state.user };
    const url = config.baseUrl + "api/user/" + userInfo.userId;
    const body = {
      name: userInfo.name,
      lastName: userInfo.lastName,
      nickname: userInfo.nickname,
      email: userInfo.email
    };
    try {
      console.log("updateUser");
      const { data: updateUserResult } = await axios.put(
        url,
        body,
        this.getAuth()
      );
      console.log(updateUserResult);
      this.setState({ open: false });
      // Force to re-render component to fetch saved data
      this.forceUpdate();
    } catch (error) {
      this.setState({ open: false });
    }
  };

  getUserInformation = async () => {
    console.log("getting user information");
    const url = config.baseUrl + "api/user/me";
    return axios.get(url, this.getAuth());
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
            Profile
          </Typography>
          <TextField
            margin="normal"
            required
            fullWidth
            id="outlined-name"
            label="Name"
            className={classes.textField}
            name="name"
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
            name="lastName"
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
            name="nickname"
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
            name="email"
            value={user.email}
            onChange={this.handleChange}
            variant="outlined"
          />
          <Button
            type="submit"
            variant="contained"
            color="secondary"
            className={classes.submit}
            onClick={this.handleDialogOpen}
          >
            Save Information
          </Button>
          <Dialog
            fullScreen={this.state.fullScreen}
            open={this.state.open}
            onClose={this.handleDialogClose}
            aria-labelledby="responsive-dialog-title"
          >
            <DialogTitle id="responsive-dialog-title">
              {"Are you sure ?"}
            </DialogTitle>
            <DialogContent>
              <DialogContentText>
                You're about to change your profile information.
              </DialogContentText>
            </DialogContent>
            <DialogActions>
              <Button onClick={this.handleDialogClose} color="primary">
                Cancel
              </Button>
              <Button
                onClick={this.handleDialogConfirm}
                color="primary"
                autoFocus
              >
                Confirm
              </Button>
            </DialogActions>
          </Dialog>
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
