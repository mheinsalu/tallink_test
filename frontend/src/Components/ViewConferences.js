import React from "react";
import {makeStyles} from "@material-ui/core/styles";
import Table from "@material-ui/core/Table";
import TableBody from "@material-ui/core/TableBody";
import TableCell from "@material-ui/core/TableCell";
import TableContainer from "@material-ui/core/TableContainer";
import TableHead from "@material-ui/core/TableHead";
import TableRow from "@material-ui/core/TableRow";
import Paper from "@material-ui/core/Paper";
import Avatar from "@material-ui/core/Avatar";
import GroupIcon from "@material-ui/icons/Group";
import {Link} from "react-router-dom";
import Typography from "@material-ui/core/Typography";

const useStyles = makeStyles(theme => ({
    table: {
        minWidth: 600
    },
    avatar: {
        margin: theme.spacing(1),
        backgroundColor: theme.palette.secondary.main
    },
    paper: {
        display: "flex",
        flexDirection: "column",
        justifyContent: "center",
        alignItems: "center",
        margin: `10px`,
        height: "100%",
        width: "99%",
        marginTop: theme.spacing(7)
    },
    link: {
        color: "rgba(0,0,0,0.65)",
        textDecoration: "none",
        marginLeft: "10%",
        alignSelf: "flex-start",
        "&:hover": {
            color: "rgba(0,0,0,1)"
        }
    }
}));

export default function ViewConferences() {
    const classes = useStyles();

    const [data, updateData] = React.useState([]);
    const [firstLoad, setLoad] = React.useState(true);

    async function sampleFunc() {
        let response = await fetch("/getAllConferences");
        let body = await response.json();
        updateData(body);
    }

    if (firstLoad) {
        sampleFunc();
        setLoad(false);
    }

    return (
        <div className={classes.paper}>
            <Avatar className={classes.avatar}>
                <GroupIcon/>
            </Avatar>
            <Typography component="h1" variant="h5">
                Conferences
            </Typography>

            <TableContainer
                style={{width: "80%", margin: "10px 10px"}}
                component={Paper}
            >
                <Table className={classes.table} aria-label="simple table">
                    <TableHead>
                        <TableRow style={{backgroundColor: "#F8F8F8"}}>
                            <TableCell align="center">Name</TableCell>
                            <TableCell align="center">Start Date and Time</TableCell>
                            <TableCell align="center">End Date and Time</TableCell>
                            <TableCell align="center">Conference Room ID</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {data?.map(row => (
                            <TableRow key={row.id}>
                                <TableCell align="center">{row.name}</TableCell>
                                <TableCell align="center">{row.startDateTime}</TableCell>
                                <TableCell align="center">{row.endDateTime}</TableCell>
                                <TableCell align="center">{row.conferenceRoom.id}</TableCell>
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
            </TableContainer>

            <Link className={classes.link} to="/">
                {" "}
                <Typography align="left" style={{margin: "10px"}}>
                    &#x2190; Head back Home
                </Typography>{" "}
            </Link>
        </div>
    );
}
