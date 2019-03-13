import React from "react";
import { withStyles } from "@material-ui/core";
import Paper from "@material-ui/core/Paper";
import CssBaseline from "@material-ui/core/CssBaseline";
import Card from "@material-ui/core/Card";
import CardActionArea from "@material-ui/core/CardActionArea";
import CardHeader from "@material-ui/core/CardHeader";
import CardActions from "@material-ui/core/CardActions";
import CardContent from "@material-ui/core/CardContent";
import Avatar from "@material-ui/core/Avatar";
import IconButton from "@material-ui/core/IconButton";
import CardMedia from "@material-ui/core/CardMedia";
import Button from "@material-ui/core/Button";
import Typography from "@material-ui/core/Typography";
import MoreVertIcon from "@material-ui/icons/MoreVert";
import pink from "@material-ui/core/colors/pink";
import green from "@material-ui/core/colors/green";
import red from "@material-ui/core/colors/red";
import axios from "axios";

import { config } from "../../config";

const initialState = {
  loading: false
};

const colors = [red[500], pink[500], green[500]];

const getRandomInt = (min, max) => {
  min = Math.ceil(min);
  max = Math.floor(max);
  return Math.floor(Math.random() * (max - min + 1)) + min;
};

class Assembly extends React.Component {
  state = { ...initialState };

  render() {
    const { classes, assembly } = this.props;
    console.log(assembly);
    const options = {
      year: "numeric",
      month: "long",
      day: "numeric"
    };
    const createdAt = new Intl.DateTimeFormat("en-US", options).format(
      new Date(assembly.timecreated)
    );
    const avatarChar = assembly.name.charAt(0).toUpperCase();
    const randomColor = colors[getRandomInt(1, 3)];
    const avatarStyle = {
      "background-color": randomColor
    };
    return (
      <React.Fragment>
        <CssBaseline />
        <Card className={classes.card}>
          {/* <CardHeader
            avatar={
              <Avatar aria-label="Recipe" style={avatarStyle}>
                {avatarChar}
              </Avatar>
            }
            title={assembly.name}
            subheader={`Created on ${createdAt}`}
          /> */}
          <CardContent>
            <Typography gutterBottom variant="h5" component="h2">
              {assembly.name}
            </Typography>
            <Typography className={classes.pos} color="textSecondary">
              Created at {createdAt}
            </Typography>

            <Typography component="p">
              {assembly.description}. Members: {assembly.membersCount}, polls:
              {assembly.pollCount}
            </Typography>
          </CardContent>
          <CardActions>
            <Button size="small" color="primary">
              Show polls
            </Button>
            <Button size="small" color="primary">
              Show members
            </Button>
          </CardActions>
        </Card>
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
  }
});

export default withStyles(styles)(Assembly);
