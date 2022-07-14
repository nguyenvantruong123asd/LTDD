import {Button, makeStyles} from "@material-ui/core";
import PropTypes from "prop-types";
import React from "react";

const useStyles = makeStyles((theme) => ({
    CustomButton: {
        // Some CSS
        background: "linear-gradient(45deg, #f1c40f 30%, #f39c12 90%)",
        backgroundSize: "200%",
        transition: "0.3s",
        "&:hover": {
            backgroundPosition: "right",
        },
        border: 0,
        color: "white",
        height: 40,
        padding: "0 30px",
        boxShadow: "0 3px 5px 2px rgba(255, 105, 135, .3)",
        borderRadius: "25px",
    },
}));
Button1.propTypes = {
    title: PropTypes.string,
};

Button1.defaultProps = {
    title: "",
};

function Button1(props) {
    const {title} = props;
    const classes = useStyles();
    return <Button className={classes.CustomButton}>{title}</Button>;
}

export default Button1;
