import React from "react";
import { withStyles } from "@material-ui/core";
import TableResults from "./TableResults";
import ExpansionPanel from "@material-ui/core/ExpansionPanel";
import ExpansionPanelDetails from "@material-ui/core/ExpansionPanelDetails";
import ExpansionPanelSummary from "@material-ui/core/ExpansionPanelSummary";
import ExpansionPanelActions from "@material-ui/core/ExpansionPanelActions";
import Typography from "@material-ui/core/Typography";
import ExpandMoreIcon from "@material-ui/icons/ExpandMore";
import Chip from "@material-ui/core/Chip";
import Button from "@material-ui/core/Button";
import Divider from "@material-ui/core/Divider";

const initialState = {};

class Results extends React.Component {
  state = { ...initialState };

  async componentDidMount() {}

  componentWillUnmount() {
    this.setState({ ...initialState });
  }

  render() {
    const { classes, poll, results } = this.props;
    return (
      <React.Fragment>
        {/* https://material-ui.com/demos/expansion-panels/ */}
        {/* Seccion Secondary heading and Columns */}
        {/* This way the table could be collapsed and a Export button could be added 
          at the botom */}

        <ExpansionPanel defaultExpanded>
          <ExpansionPanelSummary expandIcon={<ExpandMoreIcon />}>
            <div className={classes.column}>
              <Typography className={classes.heading}>Table</Typography>
            </div>
            {/* <div className={classes.column}>
              <Typography className={classes.secondaryHeading}>
                Option chose by user
              </Typography>
            </div> */}
          </ExpansionPanelSummary>
          <ExpansionPanelDetails className={classes.details}>
            {/* TODO align properly */}
            {/* <Typography className={classes.secondaryHeading}>
              {`Poll ${poll.title} `}
            </Typography> */}
            <TableResults results={results} />
          </ExpansionPanelDetails>
          <Divider />
          <ExpansionPanelActions>
            <Button size="small" color="primary">
              Export
            </Button>
          </ExpansionPanelActions>
        </ExpansionPanel>
      </React.Fragment>
    );
  }
}

const styles = theme => ({
  root: {
    width: "100%"
  },
  heading: {
    fontSize: theme.typography.pxToRem(15)
  },
  secondaryHeading: {
    fontSize: theme.typography.pxToRem(15),
    color: theme.palette.text.secondary
  },
  icon: {
    verticalAlign: "bottom",
    height: 20,
    width: 20
  },
  details: {
    alignItems: "center"
  },
  column: {
    flexBasis: "33.33%"
  },
  helper: {
    borderLeft: `2px solid ${theme.palette.divider}`,
    padding: `${theme.spacing.unit}px ${theme.spacing.unit * 2}px`
  },
  link: {
    color: theme.palette.primary.main,
    textDecoration: "none",
    "&:hover": {
      textDecoration: "underline"
    }
  }
});

export default withStyles(styles)(Results);
