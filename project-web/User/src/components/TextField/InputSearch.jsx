import {fade, makeStyles, TextField} from "@material-ui/core";
import {SearchOutlined} from "@material-ui/icons";
import PropTypes from "prop-types";
import React from "react";
import {colorWhite1, colorWhite2} from "../color/color";

InputSearch.propTypes = {
    onChange: PropTypes.func,
    params: PropTypes.object.isRequired,
    openSearch: PropTypes.func,
};
InputSearch.defaultProps = {
    onChange: null,
    openSearch: null,
};
const useStyles = makeStyles((theme) => ({
    root: {
        position: "relative",
        display: "flex",
        justifyContent: "flex-end",
        alignItems: "center",
        minHeight: "48px",
    },
    search: {
        display: "flex",
        alignItems: "center",
        height: 38,
        position: "relative",
        borderRadius: theme.shape.borderRadius,
        backgroundColor: fade(theme.palette.common.white, 0.15),
        "&:hover": {
            backgroundColor: fade(theme.palette.common.white, 0.25),
        },
        marginLeft: 0,
        width: "100%",
        "& > div > .MuiAutocomplete-endAdornment": {
            top: "calc(50% - 9px)",
        },
        "&>div>input": {
            fontFamily: "'Open Sans', sans-serif",
            marginLeft: 50,
            height: "100%",
            color: colorWhite1,
            padding: "5px 0 !important",
        },
        "&>div>div>button>span": {
            color: colorWhite1,
        },
        [theme.breakpoints.only("xs")]: {
            display: "none",
        },
        "& .MuiInput-underline:after": {
            borderBottom: "none",
        },
        "& .MuiInput-underline:before": {
            borderBottom: "none",
        },
        "& .MuiInput-underline:hover:not(.Mui-disabled):before": {
            borderBottom: "none",
        },
    },
    icon: {
        position: "absolute",
        transform: "translate(-50%, -40%)",
        top: "50%",
        left: "10%",
        color: colorWhite2,
        display: "inline-block",
        [theme.breakpoints.only("xs")]: {
            display: "none",
        },
    },
    iconMoblie: {
        color: colorWhite1,
        display: "none",
        [theme.breakpoints.only("xs")]: {
            display: "inline-block",
        },
    },
}));

function InputSearch({onChange, params, openSearch}) {
    const classes = useStyles();
    const handleOnChange = (e) => {
        if (onChange) onChange(e.target.value);
    };
    const handleOpenSearchMobie = () => {
        if (openSearch) openSearch();
    };
    return (
        <div className={classes.root}>
            <SearchOutlined className={classes.icon}/>
            <TextField
                className={classes.search}
                {...params}
                onChange={handleOnChange}
            />
            <SearchOutlined
                className={classes.iconMoblie}
                onClick={handleOpenSearchMobie}
            />
        </div>
    );
}

export default InputSearch;
