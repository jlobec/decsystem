import React from "react";
import { withStyles, Divider } from "@material-ui/core";
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
import List from "@material-ui/core/List";
import ListItem from "@material-ui/core/ListItem";
import ListItemText from "@material-ui/core/ListItemText";
import ListItemAvatar from "@material-ui/core/ListItemAvatar";
import axios from "axios";

import { config } from "../../config";

const initialState = {
  loading: false,
  showMembers: false,
  showPolls: false,
  members: [],
  polls: []
};

class Assembly extends React.Component {
  state = { ...initialState };

  getFormattedDate = assembly => {
    const options = {
      year: "numeric",
      month: "long",
      day: "numeric"
    };
    return new Intl.DateTimeFormat("en-US", options).format(
      new Date(assembly.timecreated)
    );
  };

  getAssemblyMembers = async assembly => {
    const token = sessionStorage.getItem("jwtToken");
    const url =
      config.baseUrl + "api/assembly/" + assembly.assemblyId + "/users";
    const auth = {
      headers: { Authorization: "Bearer " + token }
    };

    return axios.get(url, auth);
  };

  handleShowPolls = () => {
    const showPolls = !this.state.showPolls;
    this.setState({ showPolls: showPolls });
  };

  handleShowMembers = () => {
    const showMembers = !this.state.showMembers;
    this.setState({ showMembers: showMembers });
  };

  buildMembersList = (members, classes) => {
    return members.map(member => {
      return (
        <ListItem alignItems="flex-start">
          <ListItemAvatar>
            <Avatar>{`${member.name
              .charAt(0)
              .toUpperCase()}${member.lastName
              .charAt(0)
              .toUpperCase()}`}</Avatar>
          </ListItemAvatar>
          <ListItemText
            primary={`${member.name} ${member.lastName} `}
            secondary={
              <React.Fragment>
                <Typography
                  component="span"
                  className={classes.inline}
                  color="textPrimary"
                >
                  {`@${member.nickname} `}
                </Typography>
              </React.Fragment>
            }
          />
        </ListItem>
      );
    });
  };

  async componentDidMount() {
    this.setState({ loading: true });
    const { data: members } = await this.getAssemblyMembers(
      this.props.assembly
    );
    if (members) {
      this.setState({
        loading: false,
        members: members.content
      });
    }
  }

  render() {
    const { classes, assembly } = this.props;
    const showHidePolls = this.state.showPolls ? "Hide" : "Show";
    const showHideMembers = this.state.showMembers ? "Hide" : "Show";
    const cardActions = (
      <CardActions>
        <Button size="small" color="primary" onClick={this.handleShowPolls}>
          {showHidePolls} polls
        </Button>
        <Button size="small" color="primary" onClick={this.handleShowMembers}>
          {showHideMembers} members
        </Button>
      </CardActions>
    );

    const membersList = this.state.showMembers && (
      <List className={classes.root}>
        {this.buildMembersList(this.state.members, classes)}
      </List>
    );
    const pollList = this.state.showPolls && (
      <div>
        <p>Poll list</p>
      </div>
    );
    return (
      <React.Fragment>
        <Card className={classes.card}>
          <CardContent>
            <Typography gutterBottom variant="h5" component="h2">
              {assembly.name}
            </Typography>
            <Typography className={classes.pos} color="textSecondary">
              Created at {this.getFormattedDate(assembly)}
            </Typography>

            <Typography component="p">
              {assembly.description}. Members: {assembly.membersCount}, polls:
              {assembly.pollCount}
            </Typography>
          </CardContent>
          {cardActions}
          {membersList}
          {pollList}
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

export default withStyles(styles)(Assembly);
