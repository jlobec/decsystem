import React from "react";
import { withStyles } from "@material-ui/core";
import TableResults from "./TableResults";
import ExpansionPanel from "@material-ui/core/ExpansionPanel";
import ExpansionPanelDetails from "@material-ui/core/ExpansionPanelDetails";
import ExpansionPanelSummary from "@material-ui/core/ExpansionPanelSummary";
import ExpansionPanelActions from "@material-ui/core/ExpansionPanelActions";
import { CSVLink, CSVDownload } from "react-csv";
import Typography from "@material-ui/core/Typography";
import ExpandMoreIcon from "@material-ui/icons/ExpandMore";
import Chip from "@material-ui/core/Chip";
import Button from "@material-ui/core/Button";
import Divider from "@material-ui/core/Divider";
import SimpleBarChart from "./SimpleBarChart";
import PollActions from "../../actions/poll/PollActions";

const initialState = {
  resultsToExport: []
};

class Results extends React.Component {
  state = { ...initialState };

  async componentDidMount() {
    const poll = this.props.poll;
    const { data: resultsToExport } = await PollActions.doExportPollResults(
      poll.pollId
    );
    if (resultsToExport) {
      this.setState({
        resultsToExport: resultsToExport
      });
    }
  }

  componentWillUnmount() {
    this.setState({ ...initialState });
  }

  showDetailedResults = poll => {
    const visibilityConfig = poll.resultsVisibility.name;
    if ("Private" === visibilityConfig) {
      return false;
    }
    if ("Public" === visibilityConfig) {
      return true;
    }
    if ("PrivateUntilClosed" === visibilityConfig) {
      const pollStatus = poll.status.name;
      return "Closed" === pollStatus;
    }
  };

  // exportPollResults = async () => {
  //   const poll = this.props.poll;
  //   const { data: exportResult } = await PollActions.doExportPollResults(
  //     poll.pollId
  //   );
  //   if (exportResult) {
  //     window.open(exportResult);
  //   }
  //   // window.open(PollActions.doExportPollResults(poll.pollId), "_blank");
  // };

  render() {
    const { classes, poll, results } = this.props;
    const showDetailedResults = this.showDetailedResults(poll);
    return (
      <React.Fragment>
        {/* <Typography variant="h6" gutterBottom>
          {`Results publication: ${poll.resultsVisibility.name}`}
        </Typography> */}
        <ExpansionPanel defaultExpanded>
          <ExpansionPanelSummary expandIcon={<ExpandMoreIcon />}>
            <div className={classes.column}>
              <Typography className={classes.heading}>Chart</Typography>
            </div>
          </ExpansionPanelSummary>
          <ExpansionPanelDetails className={classes.details}>
            <SimpleBarChart poll={poll} results={results} />
          </ExpansionPanelDetails>
        </ExpansionPanel>

        {showDetailedResults && (
          <ExpansionPanel defaultExpanded>
            <ExpansionPanelSummary expandIcon={<ExpandMoreIcon />}>
              <div className={classes.column}>
                <Typography className={classes.heading}>
                  Detailed Results
                </Typography>
              </div>
            </ExpansionPanelSummary>
            <ExpansionPanelDetails className={classes.details}>
              <TableResults poll={poll} results={results} />
            </ExpansionPanelDetails>
            <Divider />
            <ExpansionPanelActions>
              <Button size="small" color="primary" variant="outlined">
                <CSVLink
                  data={this.state.resultsToExport}
                  filename={`${poll.title}.csv`}
                  target="_blank"
                >
                  Export
                </CSVLink>
              </Button>
            </ExpansionPanelActions>
          </ExpansionPanel>
        )}
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
