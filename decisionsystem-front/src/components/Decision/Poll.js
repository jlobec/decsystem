import React from "react";
import PropTypes from "prop-types";
import Avatar from "@material-ui/core/Avatar";
import Button from "@material-ui/core/Button";
import CssBaseline from "@material-ui/core/CssBaseline";
import Typography from "@material-ui/core/Typography";
import withStyles from "@material-ui/core/styles/withStyles";
import Card from "@material-ui/core/Card";
import CardActions from "@material-ui/core/CardActions";
import CardContent from "@material-ui/core/CardContent";
import CustomizedSnackbar from "../common/CustomizedSnackbar";
import axios from "axios";

import { config } from "../../config";

class Poll extends React.Component {
  constructor(props) {
    super(props);
    this.snack = React.createRef();
  }

  getFormattedDate = time => {
    const options = {
      year: "numeric",
      month: "numeric",
      day: "numeric",
      hour: "numeric",
      minute: "numeric",
      hour12: false
    };
    return new Intl.DateTimeFormat("en-US", options).format(new Date(time));
  };

  render() {
    const { classes, poll } = this.props;
    return (
      <React.Fragment>
        <CssBaseline />
        <Card className={classes.card}>
          <CardContent>
            <Typography gutterBottom variant="h5" component="h2">
              {poll.title}
            </Typography>
            <Typography className={classes.pos} color="textSecondary">
              {`From ${this.getFormattedDate(
                poll.startsAt
              )} to ${this.getFormattedDate(poll.endsAt)}`}
            </Typography>

            <Typography component="p">{poll.description}</Typography>
          </CardContent>
        </Card>
        <CustomizedSnackbar innerRef={ref => (this.snack = ref)} />
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
  card: {
    minWidth: 275
  },
  pos: {
    marginBottom: 12
  },
  root: {
    width: "100%",
    maxWidth: 360,
    backgroundColor: theme.palette.background.paper
  },
  inline: {
    display: "inline"
  }
});

export default withStyles(styles)(Poll);
