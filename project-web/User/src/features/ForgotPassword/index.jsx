import {useSnackbar} from "notistack";
import React, {useEffect, useState} from "react";
import {useHistory} from "react-router-dom";
import {useRecoilValue} from "recoil";
import userApi from "../../api/userApi";
import {DataUser} from "../../app/DataUser";
import {isEmpty} from "../../components/tools/Tools";
import FormForgotPassword from "./components/Form/FormForgotPassword";
import FormForgotPasswordCode from "./components/Form/FormForgotPasswordCode";
import FormForgotPasswordPass from "./components/Form/FormForgotPasswordPass";

function ForgotPassword(props) {
    // const { path } = useRouteMatch();
    const {push} = useHistory();
    const {enqueueSnackbar} = useSnackbar();
    const [showProcess, setShowProcess] = useState(false);
    const [step, setStep] = useState(1);
    const [data, setData] = useState({
        username: "",
        code: "",
        password: "",
    });
    const dataUser = useRecoilValue(DataUser);
    // check redirect
    useEffect(() => {
        if (!isEmpty(dataUser.user)) {
            push("/");
        }
    });
    const handleOnSubmit = async (values, show) => {
        setShowProcess(show);
        const tmpData = await userApi.forgotPassword(values);
        console.log("ForgotPassword", tmpData);
        if (!!!tmpData.status) {
            const tmpData2 = {...data};
            tmpData2.username = tmpData.username;
            setData(tmpData2);
            // response data username
            setStep(2);
        } else {
            setShowProcess(!show);
            enqueueSnackbar(tmpData.data.message.vi, {variant: "error"});
        }
    };
    const handleOnSubmitCode = async (values) => {
        const tmpData = await userApi.forgotPasswordCode(values);
        console.log(tmpData);
        if (!!!tmpData.status) {
            const tmpData2 = {...data};
            tmpData2.code = values.code;
            setData(tmpData2);
            // response data username
            setStep(3);
        } else {
            enqueueSnackbar(tmpData.data.message.vi, {variant: "error"});
        }
    };
    const handleOnSubmitPass = async (values) => {
        const tmpData = await userApi.forgotPasswordPass(values);
        if (!!!tmpData.status) {
            const tmpData2 = {...data};
            tmpData2.password = values.password;
            setData(tmpData2);
            // response data username
            setStep(1);
            push("/auth/login");
        }
    };

    return (
        <>
            {step === 1 ? (
                <FormForgotPassword
                    onSubmit={handleOnSubmit}
                    showProcess={showProcess}
                />
            ) : step === 2 ? (
                <FormForgotPasswordCode onSubmit={handleOnSubmitCode} data={data}/>
            ) : (
                <FormForgotPasswordPass onSubmit={handleOnSubmitPass} data={data}/>
            )}
        </>
    );
}

export default ForgotPassword;
