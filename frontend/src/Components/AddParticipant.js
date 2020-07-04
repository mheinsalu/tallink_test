import React, {useEffect} from "react";
import Button from "@material-ui/core/Button";
import CssBaseline from "@material-ui/core/CssBaseline";
import TextField from "@material-ui/core/TextField";
import Grid from "@material-ui/core/Grid";
import Typography from "@material-ui/core/Typography";
import {makeStyles} from "@material-ui/core/styles";
import Container from "@material-ui/core/Container";
import Select from 'react-select'

const useStyles = makeStyles(theme => ({
    paper: {
        marginTop: theme.spacing(7),
        display: "flex",
        flexDirection: "column",
        alignItems: "center"
    },
    avatar: {
        margin: theme.spacing(1),
        backgroundColor: theme.palette.secondary.main
    },
    form: {
        width: "100%", // Fix IE 11 issue.
        marginTop: theme.spacing(3)
    },
    submit: {
        margin: theme.spacing(3, 0, 2)
    },
    textField: {
        marginLeft: theme.spacing(1),
        marginRight: theme.spacing(1),
        width: "100%"
    }
}));

export default function AddParticipant() {
    const classes = useStyles();

    const [fullName, setFullName] = React.useState("");
    const [dateOfBirth, setDateOfBirth] = React.useState("");
    const [conferenceOptions, setConferenceOptions] = React.useState([]);
    const [selectedValue, setSelectedValue] = React.useState("");
    const [message, setMessage] = React.useState("Nothing saved in the session");

    const handleFullNameChange = event => setFullName(event.target.value);
    const handleDateOfBirthChange = event => setDateOfBirth(event.target.value);
    const handleSelectedValueChange = event => setSelectedValue(event.value);

    useEffect(() => {
        fetchConferences();
    }, []);

    const fetchConferences = async () => {
        const data = await fetch("/getAllConferences");
        const items = await data.json();
        let options = items.map(conference => ({label: conference.name, value: conference}));
        setConferenceOptions(options);
    }

    async function sendJsonData(data) {
        const response = await fetch("/addParticipantToConference", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(data) // body data type must match "Content-Type" header
        });
        let body = await response.text();
        setMessage(body);
    }

    const handleSubmit = () => {
        let convertedDate = convertDate(dateOfBirth);
        const inputData = {participant: {fullName: fullName, dateOfBirth: convertedDate}, conference: selectedValue};
        sendJsonData(inputData);
    };

    function convertDate(date) {
        let dateToConvert = new Date(date);
        let day = dateToConvert.getDate();
        if (day < 10) {
            day = "0" + day;
        }
        let month = dateToConvert.getMonth() + 1;
        if (month < 10) {
            month = "0" + month;
        }
        let formatted_date = day + "-"
            + month + "-"
            + dateToConvert.getFullYear();
        return formatted_date;
    }

    return (
        <Container component="main" maxWidth="xs">
            <CssBaseline/>
            <div className={classes.paper}>

                <Typography component="h1" variant="h5">
                    Add Participant to Conference
                </Typography>
                <form className={classes.form} noValidate>
                    <Grid container spacing={2}>
                        <Grid item xs={12}>
                            <TextField
                                variant="outlined"
                                required
                                fullWidth
                                id="fullName"
                                value={fullName}
                                label="Full Name"
                                name="fullName"
                                autoComplete="fullName"
                                onChange={handleFullNameChange}
                            />
                        </Grid>
                        <Grid item xs={12}>
                            <TextField
                                autoComplete="dateOfBirth"
                                name="dateOfBirth"
                                variant="outlined"
                                type="date"
                                format="dd/MM/yyyy"
                                label="Date of Birth"
                                InputLabelProps={{
                                    shrink: true,
                                }}
                                required
                                fullWidth
                                value={dateOfBirth}
                                id="dateOfBirth"
                                onChange={handleDateOfBirthChange}
                            />
                        </Grid>
                        <Grid item xs={12}>
                            <Select
                                placeholder={"Select Conference *"}
                                value={conferenceOptions.find(obj => obj.value === selectedValue)}
                                options={conferenceOptions}
                                onChange={handleSelectedValueChange}
                            />
                        </Grid>
                    </Grid>

                    <Button
                        fullWidth
                        variant="contained"
                        color="primary"
                        preventDefault
                        className={classes.submit}
                        onClick={handleSubmit}
                    >
                        Save
                    </Button>

                </form>
                <Typography style={{margin: 7}} variant="body1">
                    Status: {message}
                </Typography>
            </div>
        </Container>
    );
}
