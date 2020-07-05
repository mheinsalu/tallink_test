import React from "react";
import Table from "@material-ui/core/Table";
import TableBody from "@material-ui/core/TableBody";
import TableCell from "@material-ui/core/TableCell";
import TableHead from "@material-ui/core/TableHead";
import TableRow from "@material-ui/core/TableRow";

const ConferencesTable = (items, tableClass) => {
    return (
        <Table className={tableClass} aria-label="simple table">
            <TableHead>
                <TableRow style={{backgroundColor: "#F8F8F8"}}>
                    <TableCell align="center">Name</TableCell>
                    <TableCell align="center">Start Date and Time</TableCell>
                    <TableCell align="center">End Date and Time</TableCell>
                    <TableCell align="center">Conference Room ID</TableCell>
                </TableRow>
            </TableHead>
            <TableBody>
                {items.map(row => (
                    <TableRow key={row.id}>
                        <TableCell align="center">{row.name}</TableCell>
                        <TableCell align="center">{row.startDateTime}</TableCell>
                        <TableCell align="center">{row.endDateTime}</TableCell>
                        <TableCell align="center">{row.conferenceRoom.id}</TableCell>
                    </TableRow>
                ))}
            </TableBody>
        </Table>
    );
}
export default ConferencesTable;