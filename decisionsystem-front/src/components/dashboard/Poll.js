import React from "react";
import PropTypes from "prop-types";
import Avatar from "@material-ui/core/Avatar";
import Button from "@material-ui/core/Button";
import CssBaseline from "@material-ui/core/CssBaseline";
import FormControl from "@material-ui/core/FormControl";
import FormControlLabel from "@material-ui/core/FormControlLabel";
import Checkbox from "@material-ui/core/Checkbox";
import Input from "@material-ui/core/Input";
import InputLabel from "@material-ui/core/InputLabel";
import TaskIcon from "@material-ui/icons/AssignmentTurnedIn";
import Paper from "@material-ui/core/Paper";
import Typography from "@material-ui/core/Typography";
import withStyles from "@material-ui/core/styles/withStyles";
import { Link as RouterLink } from "react-router-dom";
import Link from "@material-ui/core/Link";
import axios from "axios";

import { config } from "../../config";

class Poll extends React.Component {
  render() {
    const { classes, pollInfo } = this.props;

    const options = {
      year: "numeric",
      month: "numeric",
      day: "numeric",
      hour: "numeric",
      minute: "numeric",
      second: "numeric",
      hour12: false
    };
    const fStartTime = new Intl.DateTimeFormat("es-ES", options).format(
      new Date(pollInfo.startedAt)
    );
    const fEndTime = new Intl.DateTimeFormat("es-ES", options).format(
      new Date(pollInfo.endsAt)
    );
    return (
      <React.Fragment>
        <CssBaseline />
        <main className={classes.layout}>
          <p>{pollInfo.pollTitle}</p>
          <p>From {fStartTime}</p>
          <p>To {fEndTime}</p>
        </main>
      </React.Fragment>
    );
  }
}
const styles = theme => ({});

export default withStyles(styles)(Poll);
