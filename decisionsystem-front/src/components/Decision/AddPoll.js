import React from "react";
import { withStyles } from "@material-ui/core";
import Button from "@material-ui/core/Button";
import TextField from "@material-ui/core/TextField";
import Dialog from "@material-ui/core/Dialog";
import DialogActions from "@material-ui/core/DialogActions";
import DialogContent from "@material-ui/core/DialogContent";
import DialogContentText from "@material-ui/core/DialogContentText";
import DialogTitle from "@material-ui/core/DialogTitle";
import InputLabel from "@material-ui/core/InputLabel";
import FormControl from "@material-ui/core/FormControl";
import Select from "@material-ui/core/Select";
import Divider from "@material-ui/core/Divider";
import ListItem from "@material-ui/core/ListItem";
import List from "@material-ui/core/List";
import Typography from "@material-ui/core/Typography";
import DateFnsUtils from "@date-io/date-fns";
import {
  MuiPickersUtilsProvider,
  TimePicker,
  DatePicker
} from "material-ui-pickers";
import axios from "axios";

import { config } from "../../config";

const getStartDefaultValue = () => {
  return new Date();
};

const getEndDefaultValue = () => {
  let now = new Date();
  now.setDate(now.getDate() + 14);
  return now;
};

const initialState = {
  open: false,
  fullScreen: false,
  assemblyOptions: [],
  title: "",
  description: "",
  startTime: "",
  endTime: "",
  pollType: "",
  assemblyId: 0,
  pollOptions: [{ name: "", description: "" }]
};

initialState.startTime = getStartDefaultValue();
initialState.endTime = getEndDefaultValue();

class AddPoll extends React.Component {
  state = { ...initialState };

  getAuth = () => {
    const token = sessionStorage.getItem("jwtToken");
    const auth = {
      headers: { Authorization: "Bearer " + token }
    };
    return auth;
  };

  getUser = async usernameOrEmail => {
    const url = config.baseUrl + "api/user?usernameOrEmail=" + usernameOrEmail;
    return axios.get(url, this.getAuth());
  };

  getUserAssemblies = async () => {
    const url = config.baseUrl + "api/assembly/mine";
    return axios.get(url, this.getAuth());
  };

  async componentDidMount() {
    this.setState({ loading: true });
    const { data: assemblies } = await this.getUserAssemblies();
    if (assemblies) {
      this.setState({
        loading: false,
        assemblyOptions: assemblies.content
      });
    }
  }

  componentWillUnmount() {
    this.setState({ ...initialState });
  }

  handleClickOpen = () => {
    this.setState({ open: true });
  };

  handleClose = () => {
    this.setState({ open: false });
  };

  handleChange = event => {
    this.setState({
      [event.currentTarget.name]: event.currentTarget.value
    });
  };

  handleOptionChange = (event, index) => {
    let optionChanged = { ...this.state.pollOptions[index] };
    optionChanged[event.currentTarget.name] = event.currentTarget.value;

    let newOptions = [...this.state.pollOptions];
    newOptions[index] = optionChanged;

    this.setState({
      pollOptions: newOptions
    });
  };

  handleStartTimeChange = date => {
    this.setState({ startTime: date });
  };

  handleEndTimeChange = date => {
    this.setState({ endTime: date });
  };

  handlePollOptions = classes => {
    let drawRemoveBtn = this.state.pollOptions.length > 1;
    const optionInputs = this.state.pollOptions.map((option, index) => {
      const now = new Date().getTime();
      // Only draw button if there is only an element
      // or it is not the last one on the list
      drawRemoveBtn =
        drawRemoveBtn && index !== this.state.pollOptions.length - 1;
      return (
        <ListItem key={`${index}${now}`}>
          <TextField
            required
            autoFocus
            margin="dense"
            id={`${now}${option.name}`}
            name="name"
            label="Option name"
            type="text"
            value={option.name}
            onChange={event => this.handleOptionChange(event, index)}
            fullWidth
          />
          <TextField
            autoFocus
            margin="dense"
            id={`${now}${option.description}`}
            name="description"
            label="Option description"
            type="text"
            value={option.description}
            onChange={event => this.handleOptionChange(event, index)}
            fullWidth
          />
          {drawRemoveBtn && (
            <Button
              className={classes.btnAddOption}
              variant="contained"
              color="secondary"
              onClick={() => this.handleClickRemoveOption(index)}
            >
              Remove option
            </Button>
          )}
        </ListItem>
      );
    });
    return (
      <div>
        <Button
          className={classes.btnAddOption}
          variant="contained"
          color="secondary"
          onClick={this.handleClickAddOption}
        >
          Add option
        </Button>
        <List>{optionInputs}</List>
      </div>
    );
  };

  handleClickAddOption = () => {
    const newOption = {
      name: "",
      description: ""
    };
    this.setState({
      pollOptions: [...this.state.pollOptions, newOption]
    });
  };

  handleClickRemoveOption = index => {
    const newPollOptions = [...this.state.pollOptions].filter((el, idx) => {
      if (idx !== index) return el;
    });
    this.setState({
      pollOptions: newPollOptions
    });
  };

  handleCreatePoll = async () => {
    console.log("Create the poll");
    const pollToCreate = {
      title: this.state.title,
      description: this.state.description,
      startTime: this.state.startTime,
      endTime: this.state.endTime,
      pollType: this.state.pollType,
      assemblyId: this.state.assemblyId,
      options: this.state.pollOptions
    };
    console.log(pollToCreate);
    // let successful = false;
    // const { data: userFound } = await this.getUser(this.state.usernameOrEmail);
    // console.log("find user result");
    // console.log(userFound);
    // if (userFound) {
    //   // Add user
    //   const { data: addUserResponse } = await this.addUser(
    //     this.props.assembly,
    //     userFound
    //   );
    //   if (addUserResponse) {
    //     successful = true;
    //   }
    // }
    // this.props.addMember(successful, userFound);
    this.handleClose();
  };

  render() {
    const { classes } = this.props;
    const assemblyOptions = this.state.assemblyOptions.map(assembly => {
      return (
        <option key={assembly.assemblyId} value={assembly.assemblyId}>
          {assembly.name}
        </option>
      );
    });
    const pollOptions = this.handlePollOptions(classes);
    return (
      <div>
        <MuiPickersUtilsProvider utils={DateFnsUtils}>
          <Dialog
            fullScreen={this.state.fullScreen}
            open={this.state.open}
            onClose={this.handleClose}
            aria-labelledby="form-dialog-title"
          >
            <DialogTitle id="form-dialog-title">New Poll</DialogTitle>
            <DialogContent>
              <DialogContentText>
                Please enter new poll information.
              </DialogContentText>
              <TextField
                required
                autoFocus
                margin="dense"
                id="title"
                name="title"
                label="Poll title"
                type="text"
                value={this.state.title}
                onChange={this.handleChange}
                fullWidth
              />
              <TextField
                autoFocus
                margin="dense"
                id="description"
                name="description"
                label="Description"
                type="text"
                value={this.state.description}
                onChange={this.handleChange}
                fullWidth
              />
              <DatePicker
                name="startTime"
                margin="normal"
                label="Start date"
                value={this.state.startTime}
                onChange={this.handleStartTimeChange}
              />
              <TimePicker
                name="startTime"
                margin="normal"
                label="Start hour"
                value={this.state.startTime}
                onChange={this.handleStartTimeChange}
              />
              <DatePicker
                name="endTime"
                margin="normal"
                label="End date"
                value={this.state.endTime}
                onChange={this.handleEndTimeChange}
              />
              <TimePicker
                name="endTime"
                margin="normal"
                label="End hour"
                value={this.state.endTime}
                onChange={this.handleEndTimeChange}
              />
              <FormControl
                variant="outlined"
                className={classes.formControl}
                fullWidth
              >
                <InputLabel>Poll type</InputLabel>
                <Select
                  native
                  value={this.state.pollType}
                  onChange={this.handleChange}
                  inputProps={{
                    name: "pollType",
                    id: "pollType"
                  }}
                >
                  <option value="" />
                  <option value={"simple"}>Single option</option>
                </Select>
              </FormControl>
              <FormControl
                variant="outlined"
                className={classes.formControl}
                fullWidth
              >
                <InputLabel>Assembly</InputLabel>
                <Select
                  native
                  value={this.state.assemblyId}
                  onChange={this.handleChange}
                  inputProps={{
                    name: "assemblyId",
                    id: "assemblyId"
                  }}
                >
                  <option value={0} />
                  {assemblyOptions}
                </Select>
              </FormControl>

              {/* <Divider variant="middle" /> */}
              <Typography gutterBottom variant="body1">
                Poll options
              </Typography>
              {pollOptions}
            </DialogContent>
            <DialogActions>
              <Button onClick={this.handleClose} color="primary">
                Cancel
              </Button>
              <Button onClick={this.handleCreatePoll} color="primary">
                Create
              </Button>
            </DialogActions>
          </Dialog>
        </MuiPickersUtilsProvider>
      </div>
    );
  }
}

const styles = theme => ({
  dialog: {
    [theme.breakpoints.down("sm")]: {
      fullScreen: true
    },
    [theme.breakpoints.up("md")]: {
      fullScreen: false
    },
    [theme.breakpoints.up("lg")]: {
      fullScreen: false
    }
  },
  dateTimePicker: {
    marginTop: theme.spacing.unit,
    marginRight: theme.spacing.unit * 2,
    width: 200
  },
  formControl: {
    marginTop: theme.spacing.unit * 2,
    minWidth: 200
  }
});

export default withStyles(styles)(AddPoll);
