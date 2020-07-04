import React, {useEffect} from "react";

import {makeStyles} from "@material-ui/core/styles";
import TableContainer from "@material-ui/core/TableContainer";
import Paper from "@material-ui/core/Paper";
import Avatar from "@material-ui/core/Avatar";
import GroupIcon from "@material-ui/icons/Group";
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

export default function TableWrapper(fetchUrl, tableName, table) {
    const [items, setItems] = React.useState([]);
    const styleClasses = useStyles();

    useEffect(() => {
        fetchItems();
    }, []);

    const fetchItems = async () => {
        const data = await fetch(fetchUrl);
        const items = await data.json();
        console.log(items);
        setItems(items);
    }

    return (
        <div className={styleClasses.paper}>
            <Avatar className={styleClasses.avatar}>
                <GroupIcon/>
            </Avatar>
            <Typography component="h1" variant="h5">
                {tableName}
            </Typography>

            <TableContainer
                style={{width: "80%", margin: "0 10px"}}
                component={Paper}
            >
                {table(items, styleClasses.table)}
            </TableContainer>
        </div>
    );
}
