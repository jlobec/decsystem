import React from "react";
import PropTypes from "prop-types";
import classNames from "classnames";
import { withStyles } from "@material-ui/core/styles";
import CssBaseline from "@material-ui/core/CssBaseline";
import Drawer from "@material-ui/core/Drawer";
import AppBar from "@material-ui/core/AppBar";
import Toolbar from "@material-ui/core/Toolbar";
import List from "@material-ui/core/List";
import Typography from "@material-ui/core/Typography";
import Divider from "@material-ui/core/Divider";
import IconButton from "@material-ui/core/IconButton";
import Badge from "@material-ui/core/Badge";
import MenuIcon from "@material-ui/icons/Menu";
import ChevronLeftIcon from "@material-ui/icons/ChevronLeft";
import NotificationsIcon from "@material-ui/icons/Notifications";
import { mainListItems, secondaryListItems } from "./listItems";
import ListItem from "@material-ui/core/ListItem";
import ListItemIcon from "@material-ui/core/ListItemIcon";
import ListItemText from "@material-ui/core/ListItemText";
import DashboardIcon from "@material-ui/icons/Dashboard";
import PeopleIcon from "@material-ui/icons/People";
import BarChartIcon from "@material-ui/icons/BarChart";
import SettingsIcon from "@material-ui/icons/Settings";
import LogoutIcon from "@material-ui/icons/ExitToApp";
import SimpleLineChart from "./SimpleLineChart";
import SimpleTable from "./SimpleTable";
import Avatar from "@material-ui/core/Avatar";
import deepOrange from "@material-ui/core/colors/deepOrange";
import deepPurple from "@material-ui/core/colors/deepPurple";
import Grid from "@material-ui/core/Grid";
import { Route } from "react-router-dom";
import Settings from "../settings/Settings";
import Assemblies from "../assembly/Assemblies";
import Notifications from "../notifications/Notifications";
import Decisions from "../Decision/Decisions";
import UserActions from "../../actions/user/UserActions";
import CommonUtils from "../../actions/util/CommonUtils";

const drawerWidth = 240;

const sectionPaths = {
  decisions: {
    title: "Decisions",
    path: "/dashboard/decisions"
  },
  assemblies: {
    title: "Assemblies",
    path: "/dashboard/assemblies"
  },
  settings: {
    title: "Settings",
    path: "/dashboard/settings"
  },
  notifications: {
    title: "Notifications",
    path: "/dashboard/notifications"
  }
};

class Dashboard extends React.Component {
  state = {
    open: false,
    title: "DecisionApp",
    user: {
      name: "",
      lastName: ""
    }
  };

  handleDrawerOpen = () => {
    this.setState({ open: true });
  };

  handleDrawerClose = () => {
    this.setState({ open: false });
  };

  handleSection = sectionPath => {
    const { match, history } = this.props;
    history.push(`${match.path}/${sectionPath}`);
    this.setState({
      open: false,
      title: `${sectionPaths[sectionPath].title}`
    });
  };

  handleLogout = () => {
    UserActions.doLogout();
    this.props.history.push("/signin");
  };

  async componentDidMount() {
    const { data: loggedUser } = await UserActions.doGetLoggedUser();
    if (loggedUser) {
      this.setState({ user: loggedUser });
    }
    this.notificationPermissions();
  }

  notificationPermissions = () => {
    console.log("permisos");
    // Comprobamos si el navegador soporta las notificaciones
    if (!("Notification" in window)) {
      alert("Este navegador no soporta las notificaciones del sistema");
    }

    // Comprobamos si ya nos habían dado permiso
    else if (Notification.permission === "granted") {
      // Si esta correcto lanzamos la notificación
      // var notification = new Notification("Holiwis :D");
    }

    // Si no, tendremos que pedir permiso al usuario
    else if (Notification.permission !== "denied") {
      Notification.requestPermission(function(permission) {
        // Si el usuario acepta, lanzamos la notificación
        if (permission === "granted") {
          var notification = new Notification(
            "Gracias por aceptar las notificaciones"
          );
        }
      });
    }

    // Finalmente, si el usuario te ha denegado el permiso y
    // quieres ser respetuoso no hay necesidad molestar más.
  };

  render() {
    const { classes } = this.props;
    const { user, title } = this.state;
    return (
      <div className={classes.root}>
        <CssBaseline />
        <AppBar
          position="absolute"
          className={classNames(
            classes.appBar,
            this.state.open && classes.appBarShift
          )}
        >
          <Toolbar
            disableGutters={!this.state.open}
            className={classes.toolbar}
          >
            <IconButton
              color="inherit"
              aria-label="Open drawer"
              onClick={this.handleDrawerOpen}
              className={classNames(
                classes.menuButton,
                this.state.open && classes.menuButtonHidden
              )}
            >
              {/* <MenuIcon /> */}
              <Avatar className={classes.avatar}>
                {`${user.name.charAt(0).toUpperCase()}${user.lastName
                  .charAt(0)
                  .toUpperCase()}`}
              </Avatar>
            </IconButton>
            <Typography
              component="h1"
              variant="h6"
              color="inherit"
              noWrap
              className={classes.title}
            >
              {title}
            </Typography>
          </Toolbar>
        </AppBar>
        <Drawer
          classes={{
            paper: classNames(
              classes.drawerPaper,
              !this.state.open && classes.drawerPaperClose
            )
          }}
          open={this.state.open}
        >
          <Grid
            container
            direction="row"
            justify="space-between"
            alignItems="center"
            className={classes.profileGrid}
          >
            <Grid item>
              <Avatar className={classes.bigAvatar}>
                {`${user.name.charAt(0).toUpperCase()}${user.lastName
                  .charAt(0)
                  .toUpperCase()}`}
              </Avatar>
            </Grid>
            <Grid item>
              <IconButton onClick={this.handleDrawerClose}>
                <ChevronLeftIcon />
              </IconButton>
            </Grid>
          </Grid>
          <Typography className={classes.profileName} variant="h6">
            {`${user.name} ${user.lastName}`}
          </Typography>
          <Typography
            className={classes.profileNickname}
            variant="subtitle1"
            gutterBottom
          >
            {`@${user.nickname}`}
          </Typography>

          <Divider />
          <List>
            <ListItem button onClick={() => this.handleSection("decisions")}>
              <ListItemIcon>
                <DashboardIcon />
              </ListItemIcon>
              <ListItemText primary="My Decisions" />
            </ListItem>
            <ListItem button onClick={() => this.handleSection("assemblies")}>
              <ListItemIcon>
                <PeopleIcon />
              </ListItemIcon>
              <ListItemText primary="Assemblies" />
            </ListItem>
            <ListItem button onClick={() => this.handleSection("settings")}>
              <ListItemIcon>
                <SettingsIcon />
              </ListItemIcon>
              <ListItemText primary="Settings" />
            </ListItem>
            <ListItem
              button
              onClick={() => this.handleSection("notifications")}
            >
              <ListItemIcon>
                <Badge badgeContent={4} color="secondary">
                  <NotificationsIcon />
                </Badge>
              </ListItemIcon>
              <ListItemText primary="Notifications" />
            </ListItem>
          </List>
          <Divider />
          <List>
            <ListItem button onClick={this.handleLogout}>
              <ListItemIcon>
                <LogoutIcon />
              </ListItemIcon>
              <ListItemText primary="Logout" />
            </ListItem>
          </List>
        </Drawer>

        {/* Main */}
        <main className={classes.content}>
          <div className={classes.appBarSpacer} />
          <Route
            path={`${sectionPaths.decisions.path}`}
            component={Decisions}
          />
          <Route
            path={`${sectionPaths.assemblies.path}`}
            component={Assemblies}
          />
          <Route path={`${sectionPaths.settings.path}`} component={Settings} />
          <Route
            path={`${sectionPaths.notifications.path}`}
            component={Notifications}
          />

          {/* <Typography component="div" className={classes.chartContainer}>
            <SimpleLineChart />
          </Typography> */}
          {/* <Typography variant="h4" gutterBottom component="h2">
            Products
          </Typography>
          <div className={classes.tableContainer}>
            <SimpleTable />
          </div> */}
        </main>
      </div>
    );
  }
}

const styles = theme => ({
  root: {
    display: "flex"
  },
  toolbar: {
    paddingRight: 24 // keep right padding when drawer closed
  },
  toolbarIcon: {
    display: "flex",
    alignItems: "center",
    justifyContent: "flex-end",
    padding: "0 8px",
    ...theme.mixins.toolbar
  },
  profileGrid: {
    paddingTop: theme.spacing.unit,
    paddingBottom: theme.spacing.unit
  },
  profileName: {
    paddingLeft: theme.spacing.unit * 2,
    paddingRight: theme.spacing.unit
  },
  profileNickname: {
    paddingLeft: theme.spacing.unit * 2
  },
  appBar: {
    zIndex: theme.zIndex.drawer + 1,
    transition: theme.transitions.create(["width", "margin"], {
      easing: theme.transitions.easing.sharp,
      duration: theme.transitions.duration.leavingScreen
    })
  },
  appBarShift: {
    marginLeft: drawerWidth,
    width: `calc(100% - ${drawerWidth}px)`,
    transition: theme.transitions.create(["width", "margin"], {
      easing: theme.transitions.easing.sharp,
      duration: theme.transitions.duration.enteringScreen
    })
  },
  menuButton: {
    marginLeft: 12,
    marginRight: 36
  },
  menuButtonHidden: {
    display: "none"
  },
  title: {
    flexGrow: 1
  },
  drawerPaper: {
    position: "relative",
    whiteSpace: "nowrap",
    width: drawerWidth,
    transition: theme.transitions.create("width", {
      easing: theme.transitions.easing.sharp,
      duration: theme.transitions.duration.enteringScreen
    })
  },
  drawerPaperClose: {
    overflowX: "hidden",
    transition: theme.transitions.create("width", {
      easing: theme.transitions.easing.sharp,
      duration: theme.transitions.duration.leavingScreen
    }),
    width: theme.spacing.unit * 7,
    [theme.breakpoints.up("sm")]: {
      width: theme.spacing.unit * 9
    }
  },
  appBarSpacer: theme.mixins.toolbar,
  content: {
    textAlign: "left",
    flexGrow: 1,
    padding: theme.spacing.unit * 3,
    height: "100vh",
    overflow: "auto"
  },
  chartContainer: {
    marginLeft: -22
  },
  tableContainer: {
    height: 320
  },
  h5: {
    marginBottom: theme.spacing.unit * 2
  },
  avatar: {
    marginLeft: theme.spacing.unit * 2
  },
  bigAvatar: {
    marginLeft: theme.spacing.unit * 2,
    margin: 10,
    width: 60,
    height: 60
  }
});

Dashboard.propTypes = {
  classes: PropTypes.object.isRequired
};

export default withStyles(styles)(Dashboard);
