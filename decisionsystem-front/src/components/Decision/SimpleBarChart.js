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
  buildData = results => {
    return [...results].map(result => {
      return {
        name: result.option.name,
        votes: result.items.length
      };
    });
  };

  render() {
    const colors = ["#0099ff", "#ff6666", "#009900", "#ff9999"];
    const data = this.buildData(this.props.results);
    return (
      <ResponsiveContainer width="99%" height={320}>
        <BarChart
          data={data}
          margin={{ top: 5, right: 30, left: 20, bottom: 5 }}
        >
          <CartesianGrid strokeDasharray="3 3" />
          <XAxis dataKey="name" />
          <YAxis />
          <Tooltip />
          <Legend />
          <Bar dataKey="votes" fill="#8884d8">
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
