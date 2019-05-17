import React from "react";
import ResponsiveContainer from "recharts/lib/component/ResponsiveContainer";
import {
  BarChart,
  Bar,
  Cell,
  XAxis,
  YAxis,
  CartesianGrid,
  Tooltip,
  Legend
} from "recharts";

const data = [
  { name: "Page A", uv: 4000, pv: 2400 },
  { name: "Page B", uv: 3000, pv: 1398 },
  { name: "Page C", uv: 2000, pv: 9800 },
  { name: "Page D", uv: 2780, pv: 3908 },
  { name: "Page E", uv: 1890, pv: 4800 },
  { name: "Page F", uv: 2390, pv: 3800 },
  { name: "Page G", uv: 3490, pv: 4300 }
];

class SimpleBarChart extends React.Component {
  buildData = (poll, results) => {
    if (
      "Single Option" === poll.pollSystem.name ||
      "Multiple Option" === poll.pollSystem.name
    ) {
      return this.buildDataForChoiceSystem(results);
    }
    if ("Score vote" === poll.pollSystem.name) {
      return this.buildDataForScoreSystem(results);
    }
  };

  buildDataForChoiceSystem = results => {
    const data = [...results].map(result => {
      return {
        name: result.option.name,
        votes: result.items.length
      };
    });
    return {
      xaxiskey: { key: "name", description: "Option name" },
      barkey: { key: "votes", description: "Votes (sum)" },
      data: data
    };
  };

  buildDataForScoreSystem = results => {
    const average = list =>
      list.reduce((prev, curr) => prev + curr) / list.length;
    const data = [...results].map(result => {
      const avgScore = average(
        result.items.map(item => {
          return item.score;
        })
      );
      return {
        name: result.option.name,
        score: avgScore
      };
    });
    return {
      xaxiskey: { key: "name", description: "Option name" },
      barkey: { key: "score", description: "Score (avg)" },
      data: data
    };
  };

  render() {
    const colors = ["#0099ff", "#ff6666", "#009900", "#ff9999"];
    const { xaxiskey, barkey, data } = this.buildData(
      this.props.poll,
      this.props.results
    );
    return (
      <ResponsiveContainer width="99%" height={320}>
        <BarChart
          data={data}
          margin={{ top: 5, right: 30, left: 20, bottom: 5 }}
        >
          <CartesianGrid strokeDasharray="3 3" />
          <XAxis dataKey={xaxiskey.key} />
          <YAxis />
          <Tooltip />
          <Legend />
          <Bar dataKey={barkey.key} name={barkey.description} fill="#8884d8">
            {data.map((entry, index) => {
              const color = colors[index % colors.length];
              return <Cell key={index} fill={color} />;
            })}
          </Bar>
        </BarChart>
      </ResponsiveContainer>
    );
  }
}

export default SimpleBarChart;
