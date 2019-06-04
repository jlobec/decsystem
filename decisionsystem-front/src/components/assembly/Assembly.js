import React from "react";
import { withStyles } from "@material-ui/core";
import Card from "@material-ui/core/Card";
import CardActions from "@material-ui/core/CardActions";
import CardContent from "@material-ui/core/CardContent";
import Avatar from "@material-ui/core/Avatar";
import IconButton from "@material-ui/core/IconButton";
import DeleteIcon from "@material-ui/icons/Delete";
import Button from "@material-ui/core/Button";
import Typography from "@material-ui/core/Typography";
import List from "@material-ui/core/List";
import ListItem from "@material-ui/core/ListItem";
import ListItemText from "@material-ui/core/ListItemText";
import ListItemAvatar from "@material-ui/core/ListItemAvatar";
import ListItemSecondaryAction from "@material-ui/core/ListItemSecondaryAction";
import AddMember from "./AddMember";
import CustomizedSnackbar from "../common/CustomizedSnackbar";
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

  constructor(props) {
    super(props);
    this.snack = React.createRef();
  }

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

  getAuth = () => {
    const token = sessionStorage.getItem("jwtToken");
    const auth = {
      headers: { Authorization: "Bearer " + token }
    };
    return auth;
  };

  getAssemblyMembers = async assembly => {
    const url =
      config.baseUrl + "api/assembly/" + assembly.assemblyId + "/users";

    return axios.get(url, this.getAuth());
  };

  removeMember = async (assembly, memberId) => {
    const url =
      config.baseUrl + "api/assembly/" + assembly.assemblyId + "/deleteuser";
    const body = { userId: memberId };
    return axios.post(url, body, this.getAuth());
  };

  addMember = (success, member) => {
    let snackbarOptions = {
      open: true,
      variant: "error",
      message: "Could not add member"
    };
    if (success) {
      this.setState({
        members: [...this.state.members, member]
      });
      snackbarOptions = {
        open: true,
        variant: "success",
        message: `User @${member.name} added successfully`
      };
    }
    this.snack.openWith(snackbarOptions);
  };

  handleShowPolls = () => {
    const showPolls = !this.state.showPolls;
    this.setState({ showPolls: showPolls });
  };

  handleShowMembers = () => {
    const showMembers = !this.state.showMembers;
    this.setState({ showMembers: showMembers });
  };

  handleRemoveMember = async (assembly, member) => {
    const memberId = member.userId;
    const { data: removeResult } = await this.removeMember(assembly, memberId);
    if (removeResult) {
      const newMembers = this.state.members
        .slice()
        .filter(member => member.userId !== memberId);
      this.setState({ members: newMembers });
      const snackbarRemovedMember = {
        open: true,
        variant: "success",
        message: `User @${member.name} removed successfully`
      };
      this.snack.openWith(snackbarRemovedMember);
    }
  };

  buildMembersList = (assembly, members, classes) => {
    return members.map(member => {
      return (
        <ListItem alignItems="flex-start" key={member.userId}>
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
          <ListItemSecondaryAction>
            <IconButton
              aria-label="Delete"
              onClick={() => {
                this.handleRemoveMember(assembly, member);
              }}
            >
              <DeleteIcon />
            </IconButton>
          </ListItemSecondaryAction>
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
    const showHideMembers = this.state.showMembers ? "Hide" : "Show";
    const cardActions = (
      <CardActions>
        <Button size="small" color="primary" onClick={this.handleShowMembers}>
          {showHideMembers} members
        </Button>
      </CardActions>
    );

    const membersList = this.state.showMembers && (
      <div>
        <AddMember assembly={assembly} addMember={this.addMember} />
        <List className={classes.root}>
          {this.buildMembersList(assembly, this.state.members, classes)}
        </List>
      </div>
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
    minWidth: 275,
    minHeight: 70,
    [theme.breakpoints.down("sm")]: {
      marginBottom: theme.spacing.unit,
      width: "100%"
    },
    [theme.breakpoints.up("md")]: {
      marginBottom: theme.spacing.unit * 2,
      width: "80%",
      marginRigth: "10%",
      marginLeft: "10%"
    },
    [theme.breakpoints.up("lg")]: {
      marginBottom: theme.spacing.unit * 2,
      width: "80%",
      marginRigth: "10%",
      marginLeft: "10%"
    }
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
